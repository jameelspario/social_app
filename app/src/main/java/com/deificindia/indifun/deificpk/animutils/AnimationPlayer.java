package com.deificindia.indifun.deificpk.animutils;


import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.URLs.GifgBaseUrl;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.ToStream;
import com.deificindia.indifun.deificpk.modals.ChatModal;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.fires.m.UserJoinedInfo;
import com.google.gson.Gson;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AnimationPlayer {

    public static final String TAG = "AnimaPalyer";

    protected static final int TYPE_GIFT_JSON = 1;
    protected static final int TYPE_GIFT_SVGA = 2;
    protected static final int TYPE_GIFT_GIF = 3;
    protected static final int TYPE_GIFT_SEQ1 = 4;
    public static final int MIN_LEVEL_TO_PLAY_ANIMATION = 0;

    public static final String SVGAANIMATION = "svga";
    public static final String JSONANIMATION = "json";
    public static final String CACHE = "dir-enter-animation";

    public static final String URL = "url";
    public static final String ASSETS = "assets";
    public static final String RAW = "raw";


    public Queue<GeneralModalData> giftQueue_ASSETS = new LinkedList<>();
    public Queue<GiftInfo2> giftQueue_LOCALSENT = new LinkedList<>();
    public Queue<ChatModal> giftQueue_RECIVED = new LinkedList<>();

    public Queue<UserJoinedInfo> enterQueue = new LinkedList<>();

    private Handler handler = new Handler(Looper.getMainLooper());

    private boolean isPlayingFullScreenGift, isPlayingEnterAnimation;

    LottieAnimationView lottie, lottieEnter;
    SVGAImageView svga, svgaEnter;

    private GiftReadyListener listener1;
    EnterAnimationPlayerListener enterAnimationPlayerListener;

    private HandlerThread handlerThread;
    private Handler animationHandler;

    public interface GiftPlayedListener {

    }

    public void init(View v){
        //inflate(getContext(), R.layout.layout_animation_items, this);

        lottie = v.findViewById(R.id.liveStreaming_fullscreenGift);
        lottieEnter = v.findViewById(R.id.lottie_enter_animation);

        svga = v.findViewById(R.id.svga_mage_seq_image_view);
        svgaEnter = v.findViewById(R.id.svga_enter_animation);

        handlerThread = new HandlerThread("AnimationPlayerThreadHandler");
        handlerThread.start();
        animationHandler = new Handler(handlerThread.getLooper());

    }

    public interface EnterAnimationPlayerListener{
        void onStartPlaying(UserJoinedInfo obj);
    }

    public void setListener(GiftReadyListener listener) {
        this.listener1 = listener;
    }
    public void setEnterListener(EnterAnimationPlayerListener enterAnimationPlayerListener) {
        this.enterAnimationPlayerListener = enterAnimationPlayerListener;
    }

   /* public AnimationPlayer(Context context) {
        super(context);
        init();
    }

    public AnimationPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }*/

    public void sendFullScreenGift(GiftInfo2 modal){
        loge("AnimPlayer", "send", new Gson().toJson(modal));
        giftQueue_LOCALSENT.offer(modal);
        sendToQueue();
    }

    public void recievedFullScrenGift(ChatModal modal){
        giftQueue_RECIVED.offer(modal);
        sendToQueue();
    }

    void senderGiftAnimation(GiftInfo2 info){
        if(info.type==TYPE_GIFT_JSON) {
            playFullScreenLottieAnim(info);
        } else if(info.type==TYPE_GIFT_SVGA) {
            playFullScreenSvga(info);
        }else {
            isPlayingFullScreenGift = false;
            recycleQueue();
        }
    }

    /*emergency play full screen gift pausing other animation*/
    public void generalFullScrenGift(GeneralModalData modal, GiftReadyListener listener){
        giftQueue_ASSETS.offer(modal);
        sendToQueue();
    }


    synchronized void sendToQueue(){
        recycleQueue();
    }

    void recycleQueue(){
        animationHandler.post(background());
    }

    Runnable background(){
        return () -> {
            loge("AnimPlayer", "background sent", ""+isPlayingFullScreenGift, ""+giftQueue_LOCALSENT.size());
            if(isPlayingFullScreenGift) return;

            if (giftQueue_LOCALSENT.size() > 0) {
                //informNext(new AnimEnqueueService.ModalData(new int[]{LOCALSENT}), resultReceiver);
                GiftInfo2 gif = giftQueue_LOCALSENT.poll();
                loge("AnimPlayer", "background sent", new Gson().toJson(gif));
                if(gif!=null) {
                    isPlayingFullScreenGift =true;

                    handler.post(()-> senderGiftAnimation(gif));

                    return;
                }
            } else {
                if (giftQueue_RECIVED.size() > 0) {
                    ChatModal gif = giftQueue_RECIVED.poll();
                    if(gif!=null) {
                        isPlayingFullScreenGift = true;
                        handler.post(()-> rcvdGiftAnimation(gif));

                        return;
                    }
                }
            }

            isPlayingFullScreenGift = false;

            //next ---
        };
    }



    /*
     * 1- play local file ... to be sent gift : GiftInfo2
     *       - send gift info to firebase to inform other user in broad
     *       - update gift point for sender and rcvr
     *
     * */

    void rcvdGiftAnimation(ChatModal info){
        loge("AnimPlayer", "anim", new Gson().toJson(info));
        if(info.gifttype==TYPE_GIFT_JSON) {
            playFullScreenLottieAnim(info);
        } else if(info.gifttype==TYPE_GIFT_SVGA) {
            playFullScreenSvga(info);
        } else{
            isPlayingFullScreenGift = false;
            recycleQueue();
        }
    }

    /*
    * 2- play rcved gift : ChatModal
    *       - play gift sender info on startPlaying == onGiftStart()
    *
    * */

    protected void playFullScreenLottieAnim(Object obj){

        String jsonurl = "";
        if(obj instanceof GiftInfo2){
            jsonurl = ((GiftInfo2) obj).animation;
        } else if(obj instanceof ChatModal){
            jsonurl = ((ChatModal) obj).giftanim;
        }

        playFullScreenLottieAnim2(GifgBaseUrl + jsonurl, new LocalAnimationListener() {

            @Override
            public void start() {
                if(listener1!=null) listener1.onGiftStart(obj);
            }

            @Override
            public void end() {
                json_playingEnd();
            }

            @Override
            public void error() {
                json_playingEnd();
            }
        });

    }

    protected void playFullScreenLottieAnim2(String jsonurl, LocalAnimationListener local){
        loge("AnimPlayer", "playing json", jsonurl);
        try {
            ///GifgBaseUrl+info.json_animation
            lottie.setVisibility(View.VISIBLE);
            //lottieAnimationView.setAnimation(gifturl, null);
            lottie.setAnimationFromUrl(jsonurl);
            //lottieAnimationView.setAnimation(new InputStream(), null);

            lottie.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    local.start();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    local.end();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    local.end();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

            });

            lottie.playAnimation();

        } catch (IllegalStateException e){
            loge("AnimPlayer", "json", e.getMessage());
            e.printStackTrace();
            local.error();
        }
    }

    protected void playFullScreenSvga(Object obj){

        String url = "";
        if(obj instanceof GiftInfo2){
            url = ((GiftInfo2) obj).animation;
        } else if(obj instanceof ChatModal){
            url = ((ChatModal) obj).giftanim;
        }

        playFullScreenSvga1(GifgBaseUrl + url, new LocalAnimationListener() {
            @Override
            public void start() {
                if(listener1!=null) listener1.onGiftStart(obj);
            }

            @Override
            public void end() {
                svg_playingEnd();
            }

            @Override
            public void error() {
                svg_playingEnd();
            }
        });

    }

    private void json_playingEnd(){
        lottie.cancelAnimation();
        lottie.setVisibility(View.GONE);
        if(listener1!=null) listener1.onGiftPlayEnd(null);
        isPlayingFullScreenGift = false;
        recycleQueue();

        loge("AnimPlayer", "json playing end");

    }

    protected void playFullScreenSvga1(String url, LocalAnimationListener local){

        loge("AnimPlayer", "playing json", url);
        svga.setVisibility(View.VISIBLE);
        //SVGAImageView animationView = new SVGAImageView(this);
        svga.setBackgroundColor(Color.TRANSPARENT);
        //setContentView(animationView);
        svga.setLoops(1);

        svga.setCallback(new SVGACallback() {
            @Override
            public void onPause() { }

            @Override
            public void onFinished() {
                local.end();
            }

            @Override
            public void onRepeat() { }

            @Override
            public void onStep(int i, double v) { }
        });

        try { // new URL needs try catch.
            SVGAParser svgaParser = SVGAParser.Companion.shareParser();
            svgaParser.setFrameSize(100,100);
            svgaParser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    Log.d("##","## FromNetworkActivity load onComplete");
                    svga.setVideoItem(videoItem);
                    svga.startAnimation();

                    local.start();
                }
                @Override
                public void onError() {
                    local.error();
                }


            },null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            local.error();
        }

       /* try {
            SVGAParser svgaParser = SVGAParser.Companion.shareParser();
            svgaParser.setFrameSize(100,100);
            //svgaParser._decodeFromInputStream();
            svgaParser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    Log.d(TAG,"## FromNetworkActivity load onComplete");
                   *//* svga.setVideoItem(videoItem);
                    svga.startAnimation();*//*



                    SVGADrawable drawable = new SVGADrawable (videoItem);
                    svga.setImageDrawable (drawable);
                    svga.startAnimation ();

                    local.start();


                }
                @Override
                public void onError() { svg_playingEnd(); }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            local.error();
        }*/

    }

    private void svg_playingEnd(){
        svga.setVisibility(View.GONE);
        svga.setImageDrawable(null);
        if(listener1!=null) listener1.onGiftPlayEnd(null);
        isPlayingFullScreenGift = false;
        recycleQueue();

        loge("AnimPlayer", "svga playing end");
    }

    public void enterAnimScreenGift(UserJoinedInfo modal){

        animationHandler.post(()->{
            enterQueue.offer(modal); //insert
            cyclicEnterAnimationCalculation();
        });

    }

    /*
    *
    *
    *
    * */

    void cyclicEnterAnimationCalculation(){
        if(isPlayingEnterAnimation) return;

        if(enterQueue.size()>0){

            UserJoinedInfo gift = enterQueue.poll(); //retrive&remove
            if(gift!=null) {
                isPlayingEnterAnimation =true;

                ///calculate stream

                try {
                    gift.inputStream = ToStream.instance().calcStream(gift.user_animation, gift.animation_from);
                    handler.post(()-> {

                        try {
                            enterAnimation(gift);
                        } catch (Exception e) {
                            e.printStackTrace();
                            onPlayingEnterAnimationEnd("");
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    onPlayingEnterAnimationEnd("");
                }

                return;
            }

            onPlayingEnterAnimationEnd("");
        }
    }

    void enterAnimation(UserJoinedInfo info) throws Exception{
        if(info.user_animation.endsWith(".json")){
            playLottieEnter(info);
        } else if(info.user_animation.endsWith(".svga")){
            playsvgaEnter(info);
        } else {
            isPlayingEnterAnimation = false;
            cyclicEnterAnimationCalculation();
        }
    }

    protected void playLottieEnter(UserJoinedInfo info) throws Exception {
            ///GifgBaseUrl+info.json_animation
            lottieEnter.setVisibility(View.VISIBLE);
            //lottieAnimationView.setAnimation(gifturl, null);
            //lottieEnter.setAnimationFromUrl(info.animation);
            //lottieEnter.setAnimation(info.user_animation);
            lottieEnter.setAnimation(info.inputStream, CACHE);

            lottieEnter.playAnimation();
            lottieEnter.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if(enterAnimationPlayerListener!=null)
                        enterAnimationPlayerListener.onStartPlaying(info);

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    onPlayingEnterAnimationEnd("json");
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

            });

           //

    }

    protected void playsvgaEnter(UserJoinedInfo info) throws Exception {

        svgaEnter.setVisibility(View.VISIBLE);
        svgaEnter.setBackgroundColor(Color.TRANSPARENT);
        svgaEnter.setLoops(1);

        svgaEnter.setCallback(new SVGACallback() {
            @Override
            public void onPause() { }

            @Override
            public void onFinished() {
                onPlayingEnterAnimationEnd("svga");
            }

            @Override
            public void onRepeat() { }

            @Override
            public void onStep(int i, double v) { }
        });


        SVGAParser svgaParser = SVGAParser.Companion.shareParser();
        svgaParser.setFrameSize(100,100);

             /* svgaParser.decodeFromURL(new URL(GifgBaseUrl+info.animation), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    Log.d("##","## FromNetworkActivity load onComplete");
                    svga.setVideoItem(videoItem);
                    svga.startAnimation();

                    if(enterAnimationPlayerListener!=null)
                        enterAnimationPlayerListener.onStartPlaying(info);
                }
                @Override
                public void onError() {
                    onPlayingEnterAnimationEnd("svga");
                }


            },null);*/

            /*svgaParser.decodeFromAssets(info.user_animation, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                Log.e("zzzz", "onComplete: ");
                svgaEnter.setVideoItem(videoItem);
                svgaEnter.stepToFrame(0, true);
                if(enterAnimationPlayerListener!=null)
                    enterAnimationPlayerListener.onStartPlaying(info);
            }

            @Override
            public void onError() {
                Log.e("zzzz", "onComplete: ");
                onPlayingEnterAnimationEnd("svga");
            }

        }, null);*/

        svgaParser.decodeFromInputStream(info.inputStream,
                CACHE,
                new SVGAParser.ParseCompletion() {
                    @Override
                    public void onComplete(@NonNull SVGAVideoEntity svgaVideoEntity) {
                        svgaEnter.setVideoItem(svgaVideoEntity);
                        svgaEnter.stepToFrame(0, true);

                        if(enterAnimationPlayerListener!=null)
                            enterAnimationPlayerListener.onStartPlaying(info);
                    }

                    @Override
                    public void onError() {
                        onPlayingEnterAnimationEnd("svga");
                    }
                }, true, list -> {

                }, null);


        //


    }

    void onPlayingEnterAnimationEnd(String gift_type){

        svgaEnter.setImageDrawable(null);
        svgaEnter.setVisibility(View.GONE);
        lottieEnter.cancelAnimation();
        lottieEnter.setVisibility(View.GONE);

        isPlayingEnterAnimation = false;
        cyclicEnterAnimationCalculation();
    }

    public static class GeneralModalData{

    }


    public interface GiftReadyListener {
        void onGiftReady(Object obj);
        void onGiftStart(Object obj);
        void onGiftPlayEnd(@Nullable Object obj);
    }

    interface LocalAnimationListener{
        void start();
        void end();
        void error();
    }


}
