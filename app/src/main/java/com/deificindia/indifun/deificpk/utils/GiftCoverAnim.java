package com.deificindia.indifun.deificpk.utils;

import android.animation.Animator;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

import static com.deificindia.indifun.Utility.URLs.GifgBaseUrl;
import static com.deificindia.indifun.deificpk.utils.Const.loge;

public class GiftCoverAnim {

    public static final String TAG = "GiftCoverAnim";
    public static final String COVER = URLs.GifgBaseUrl;

    FrameLayout topContainer;
    String url;

    public static GiftCoverAnim instance(){ return new GiftCoverAnim(); }
    public GiftCoverAnim container(FrameLayout container){
        this.topContainer = container;
        return this;
    }

    public GiftCoverAnim file(String endurl){
        this.url = endurl;
        return this;
    }

    public void removeViewFromTop(){
        if(this.topContainer!=null && this.topContainer.getChildCount() > 0) this.topContainer.removeAllViews();
    }

    public GiftCoverAnim play(){
        if(this.url.endsWith(".png") || this.url.endsWith(".PNG")){
            this.staticPng(COVER+this.url);
        }
        if(this.url.endsWith(".json")){
            playJson(true, COVER+this.url, true);
        }
        if(this.url.endsWith(".svga")){
            playSvga(COVER+this.url, true);
        }

        return this;
    }

    void playJson(boolean isyrl, String jsonFileFromAssets, boolean loop){
        try {
            LottieAnimationView anim = new LottieAnimationView(topContainer.getContext());
            removeViewFromTop();
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
                        removeViewFromTop();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.playAnimation();
        }catch (IllegalStateException e){ }

    }

    void playSvga(String url, boolean loop){

        SVGAImageView animationView = new SVGAImageView(topContainer.getContext());
        animationView.setBackgroundColor(Color.TRANSPARENT);
        animationView.setLoops(loop?0:1);

        animationView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {
               // loge(TAG, "onPause 1");
            }

            @Override
            public void onFinished() {
               // loge(TAG, "onFinished 1");
                if(!loop) removeViewFromTop();
            }

            @Override
            public void onRepeat() {
               // loge(TAG, "onRepeat 1");
            }

            @Override
            public void onStep(int i, double v) {
                //loge(TAG, "onStep " + i);
            }
        });

        removeViewFromTop();
        topContainer.addView(animationView);

        try { // new URL needs try catch.
            SVGAParser svgaParser = SVGAParser.Companion.shareParser();
            svgaParser.setFrameSize(100,100);
            svgaParser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    Log.d("##","## FromNetworkActivity load onComplete");
                    animationView.setVideoItem(videoItem);
                    animationView.startAnimation();

                }
                @Override
                public void onError() { }


            },null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    void staticPng(String url){
        ImageView img = new ImageView(topContainer.getContext());
        removeViewFromTop();
        topContainer.addView(img);

        Picasso.get().load(url)
                .error(topContainer.getContext().getResources().getDrawable(R.drawable.empty_gift))
                .into(img);
    }

}
