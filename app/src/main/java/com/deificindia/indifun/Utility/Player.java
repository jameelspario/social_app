package com.deificindia.indifun.Utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.deificpk.gif.mod.Parent;
import com.deificindia.indifun.deificpk.gif.mod.Seq;
import com.deificindia.indifun.deificpk.utils.Const;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.URLs.GifgBaseUrl;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;


public abstract class Player {

    //public abstract void onPostExecute(AnimationDrawable msg, Parent data);
    public abstract void onPostExecute(String[]  msg, Parent data);
    public abstract void onEnd();



    public RxSequenceAnimation play0(FrameLayout frameLayout, String folder, String[] images, Parent data){
        ImageView anim = new ImageView(frameLayout.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        anim.setLayoutParams(params);

        anim.setScaleType(ImageView.ScaleType.FIT_CENTER);

        frameLayout.addView(anim);

        RxSequenceAnimation rx = new RxSequenceAnimation(frameLayout.getResources(), folder, images, anim);


        anim.post(() -> {

            data.seq.point(frameLayout, anim);
            zooin(anim, data.seq, () -> {

            });
            rx.setRes(anim);
            if(data.seq.zoomout) {
                new Handler(Looper.myLooper()).postDelayed(() -> {
                    zooout(anim, data.seq, null);
                }, (data.length * 1000) - (data.seq.zoomout_duration * 1000));
            }

            new Handler(Looper.myLooper()).postDelayed(() -> {
                rx.start(10);
            }, 1000);

        });

        return rx;
    }

    public void play1(FrameLayout frameLayout, AnimationDrawable animation, Parent data){
        if(animation!=null && animation.getNumberOfFrames()>0) {
            loge("Play1", ""+animation.getNumberOfFrames());
            ImageView anim = new ImageView(frameLayout.getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            anim.setLayoutParams(params);

            anim.setScaleType(ImageView.ScaleType.FIT_CENTER);

            frameLayout.addView(anim);

            anim.setBackground(animation);

            //  anim.setBackgroundResource(R.drawable.seque_diya /*R.drawable.spin_animation*/);
            // Get the background, which has been compiled to an AnimationDrawable object.
            AnimationDrawable frameAnimation = (AnimationDrawable) anim.getBackground();

            frameAnimation.setCallback(new AnimationDrawableCallback(frameAnimation, anim) {

                @Override
                public void onAnimationComplete() {
                    /*anim.setBackgroundResource(0);
                    anim.setVisibility(View.GONE);
                    topContainer.removeView(anim);
                    onEnd();*/
                }

            });
            // Start the animation (looped playback by default).
            anim.post(() -> {
                frameAnimation.start();
                data.seq.point(frameLayout, anim);
                zooin(anim, data.seq, null);
                if(data.seq.zoomout) {
                    new Handler(Looper.myLooper()).postDelayed(() -> {
                        zooout(anim, data.seq, null);
                    }, (data.length * 1000) - (data.seq.zoomout_duration * 1000));
                }
            });


        }else {
            Log.e("DownloadGiftCallbacks", "play: onEnd" );
            onEnd();
        }

    }

    public void zooin(ImageView imageView, Seq seq, ShouldStart listenerAdapter){
        List<Animator> animators = new ArrayList<>();


        long zoominduration = seq.zoomin_duration*1000;

        AnimatorSet animatorSet = new AnimatorSet();

        if(seq.zoomin){
            ObjectAnimator zoomx = ObjectAnimator
                    .ofFloat(imageView, View.SCALE_X, 0.1f, 1.0f)
                    .setDuration(zoominduration);

            animators.add(zoomx);

            ObjectAnimator zoomy = ObjectAnimator
                    .ofFloat(imageView, View.SCALE_Y, 0.1f, 1.0f)
                    .setDuration(zoominduration);

            animators.add(zoomy);
        }

        if(seq.fade){
            ObjectAnimator fadein = ObjectAnimator
                    .ofFloat(imageView, View.ALPHA, 0, 1)
                    .setDuration(zoominduration);

            animators.add(fadein);
        }

        if(seq.from!=null &&  !seq.from.isEmpty() && seq.pointFrom!=null){
            ObjectAnimator transX = ObjectAnimator
                    .ofFloat(imageView, View.TRANSLATION_X, seq.pointFrom.x, seq.pointCenter.x)
                    .setDuration(zoominduration);

            ObjectAnimator transY = ObjectAnimator
                    .ofFloat(imageView, View.TRANSLATION_Y, seq.pointFrom.y, seq.pointCenter.y)
                    .setDuration(zoominduration);

            animators.add(transX);
            animators.add(transY);
        }


        if(animators.size()>0) {
            animatorSet.playTogether(animators);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation, boolean isReverse) {
                    if(listenerAdapter!=null){
                        listenerAdapter.shouldstart();
                    }
                }
            });
            animatorSet.start();
        }else {
            if(listenerAdapter!=null){
                listenerAdapter.shouldstart();
            }
        }
    }

    public void zooout(ImageView imageView, Seq seq, AnimatorListenerAdapter listenerAdapter){
        List<Animator> animators = new ArrayList<>();

        long zoominduration = seq.zoomout_duration*1000;

        AnimatorSet animatorSet = new AnimatorSet();

        if(seq.fade){
            ObjectAnimator fadein = ObjectAnimator
                    .ofFloat(imageView, View.ALPHA, 1, 0)
                    .setDuration(zoominduration);

            animators.add(fadein);
        }


        if(seq.zoomout){
            ObjectAnimator zoomx = ObjectAnimator
                    .ofFloat(imageView, View.SCALE_X, 1.0f, 0.1f)
                    .setDuration(zoominduration);

            ObjectAnimator zoomy = ObjectAnimator
                    .ofFloat(imageView, View.SCALE_Y, 1.0f, 0.1f)
                    .setDuration(zoominduration);

            animators.add(zoomx);
            animators.add(zoomy);
        }

        if(seq.to!=null &&  !seq.to.isEmpty() && seq.pointTo!=null){
            ObjectAnimator transX = ObjectAnimator
                    .ofFloat(imageView, View.TRANSLATION_X, seq.pointCenter.x, seq.pointTo.x)
                    .setDuration(zoominduration);

            ObjectAnimator transY = ObjectAnimator
                    .ofFloat(imageView, View.TRANSLATION_Y, seq.pointCenter.y, seq.pointTo.y)
                    .setDuration(zoominduration);

            animators.add(transX);
            animators.add(transY);
        }


        if(animators.size()>0) {
            animatorSet.playTogether(animators);

            if(listenerAdapter!=null)
                animatorSet.addListener(listenerAdapter);

            animatorSet.start();
        }
    }


    public void play2(FrameLayout topContainer, boolean isyrl, String jsonFileFromAssets, boolean loop, long delay){
        try {
            LottieAnimationView anim = new LottieAnimationView(topContainer.getContext());
            topContainer.addView(anim);
            anim.loop(loop);

            if(isyrl) anim.setAnimationFromUrl(jsonFileFromAssets);
            else anim.setAnimation(jsonFileFromAssets);

            anim.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if(!loop){
                        topContainer.removeView(anim);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            new Handler(Looper.myLooper()).postDelayed(()->{ anim.playAnimation(); }, delay);


        }catch (IllegalStateException e){ }

    }

    protected void play3(FrameLayout topContainer,String url, Parent data
            /*ChatModal gift*/){

        Const.loge("Player", "playFullScreenSvga", url);
        SVGAImageView animationView = new SVGAImageView(topContainer.getContext());
        animationView.setBackgroundColor(Color.TRANSPARENT);
        //setContentView(animationView);
        animationView.setLoops(1);

        animationView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {
                Const.loge("Player", "onPause 1");
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onRepeat() {
                Const.loge("Player", "onRepeat 1");
            }

            @Override
            public void onStep(int i, double v) {
                //loge(TAG, "onStep " + i);
            }
        });

        topContainer.addView(animationView);
        ////////////////////////////////////////////////////////////////////////
        data.svga.point(topContainer, animationView);
        zooin(animationView, data.svga, null);
        if(data.svga.zoomout) {
            new Handler(Looper.myLooper()).postDelayed(() -> {
                zooout(animationView, data.svga, null);
            }, (data.length * 1000) - (data.svga.zoomout_duration * 1000));
        }

        new Handler(Looper.myLooper()).postDelayed(() -> {
            loadAnimation(animationView, url);
        }, 1000);

    }

    private void loadAnimation(SVGAImageView animationView, String svgaAssets /*"MerryChristmas.svga"*/) {
        SVGAParser svgaParser = SVGAParser.Companion.shareParser();
        svgaParser.setFrameSize(100,100);
       /* svgaParser.decodeFromAssets(svgaAssets, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                //animationView.setVideoItem(videoItem);
                //animationView.startAnimation();

                SVGADrawable drawable = new SVGADrawable (videoItem);
                animationView.setImageDrawable (drawable);
                animationView.startAnimation ();
            }
            @Override
            public void onError() {

            }
        });*/
    }

    private void loadUrl(SVGAImageView animationView, String url/*GifgBaseUrl+url*/){
       /* try { // new URL needs try catch.
            SVGAParser svgaParser = SVGAParser.Companion.shareParser();
            svgaParser.setFrameSize(100,100);
            svgaParser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    Log.d("##","## FromNetworkActivity load onComplete");
                    animationView.setVideoItem(videoItem);
                    animationView.startAnimation();

                    *//*SVGADrawable drawable = new SVGADrawable (videoItem);
                    svgaImageView .setImageDrawable (drawable);
                    svgaImageView .startAnimation ();*//*
                }
                @Override
                public void onError() {

                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
    }


    public void play4(Activity act, FrameLayout  frame){
       /* SkyView skyView = new SkyView(act);
        skyView.setTransparent(true);
        frame.addView(skyView);
        skyView.setActivity(act);
        skyView.frequency = 30;
        skyView.fireworksGenerationMode = SkyView.MODE_CONTINUOUS;*/

        //sfvTrack.setZOrderOnTop(true);    // necessary
        //SurfaceHolder sfhTrackHolder = sfvTrack.getHolder();
        //sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT);
    }

    interface ShouldStart{
        void shouldstart();
    }



}
