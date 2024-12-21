package com.deificindia.indifun.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.util.Pair;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.DeviceUtils;
import com.deificindia.indifun.deificpk.animutils.RoomEnterAnima;
import com.deificindia.indifun.anim.interpolators.BounceInterpolator;

public class AnimUtils {

    public static void room_enter_animation(Context context, int wid, LinearLayout linearLayout,
                                            String usernam, String lvl, onEnterAnimEnd listener){
        // layout_room_enter_animation
        if(linearLayout.getChildCount()>0) linearLayout.removeAllViews();

        RoomEnterAnima roomEnterAnima = new RoomEnterAnima(context);
        linearLayout.addView(roomEnterAnima);
        roomEnterAnima.init(usernam, lvl);

        //float halfW = screenWith/4.0f;
        int w = linearLayout.getWidth();
        int w4 = w/4;


        // translationX to move object along x axis
        // next values are position value
        float startvalue = w+roomEnterAnima.getWidth();

        ObjectAnimator lftToRgt = ObjectAnimator
                .ofFloat(roomEnterAnima,"translationX", startvalue, w4 )
                .setDuration(800);
        lftToRgt.setInterpolator(new OvershootInterpolator());

        ObjectAnimator pauseAnim = ObjectAnimator
                .ofFloat(roomEnterAnima,"translationX", w4, w4 )
                .setDuration(1000); // to animate left to right

        ObjectAnimator rgtToLft = ObjectAnimator
                .ofFloat(roomEnterAnima,"translationX", w4, -w)
                .setDuration(1200); // to animate right to left
        rgtToLft.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        //animatorSet.play(lftToRgt); // manage sequence
        //animatorSet.play(lftToRgt).before(rgtToLft);
        animatorSet.playSequentially(lftToRgt, pauseAnim, rgtToLft);
        //animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                linearLayout.removeView(roomEnterAnima);
                if(listener!=null) listener.onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        animatorSet.start(); // play the animation
    }

    public interface onEnterAnimEnd{
        void  onEnd();
    }
//////////////////////////////////////////////////////////

    public static void playHeartAnim(RelativeLayout parent, ImageView img, Pair<Float, Float> source, int screenHeight) {
        /*ImageView img = new ImageView(this);
        img.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        img.setImageResource(R.drawable.ic_heart_blue_1);
        relativeLayout.addView(img);*/

        //img.setScaleX((float) (Math.random() * 0.5f + .1f));
        img.setScaleX(0.5f);
        img.setScaleY(img.getScaleX());
        float starW = img.getScaleX();
        float starH = img.getScaleY();

        float containerW = parent.getWidth();
        float containerH = parent.getHeight();

        AnimatorSet animatorSet = new AnimatorSet();
        //ObjectAnimator mover = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, -starH, containerH + starH);
        ObjectAnimator mover = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y,  containerH, -starH);
        mover.setInterpolator(new AccelerateInterpolator(1f));

        ObjectAnimator moverx = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, source.first, (float) (Math.random()*containerW+starW));
        mover.setInterpolator(new LinearInterpolator());

        ObjectAnimator rotator = ObjectAnimator.ofFloat(img, View.ROTATION, (float) (Math.random() * 80));
        rotator.setInterpolator(new LinearInterpolator());

        ObjectAnimator fade = ObjectAnimator.ofFloat(img, View.ALPHA, (float) (Math.random() * 0.1));
        rotator.setInterpolator(new LinearInterpolator());


        animatorSet.playTogether(mover, rotator, moverx, fade);
        animatorSet.setDuration((long) (Math.random() * 1500 + 1000));
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler(Looper.myLooper()).postDelayed(()->{
                    parent.removeView(img);
                }, 10);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });

        animatorSet.start();


    }


    public static void animateMe(LinearLayout parent, ImageView imageView, int screenHeight) {

        AnimatorSet animatorSet = new AnimatorSet();
        int parent_h = parent.getHeight()/4;
        int parent_w = parent.getWidth();
        int parent_bottom = TranslateView.getRelBottom(parent);
        int parent_rel_left = TranslateView.getRelLeft(parent);
        int parent_rel_right = TranslateView.getRelRight(parent);

       /* float dpToPx = (float) DeviceUtils.dpToPx(30);
        Path path = new Path();
        path.moveTo(imageView.getX(), imageView.getY());
        path.lineTo(getRndX(parent), 50.5f)
        path.lineTo(getRndX(parent), 19.79f)
        path.lineTo(getRndX(parent), 7.5f)
        path.lineTo(getRndX(parent), 38.21f)
        path.lineTo(getRndX(parent), 7.5f)
        path.lineTo(getRndX(parent), TranslateView.getRelTop(parent));*/

        animatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, parent_bottom-imageView.getHeight(), -parent.getHeight()),
                //ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, parent_w/2, getRandIn(parent, imageView)),

                //ObjectAnimator.ofFloat(imageView, View.X, View.Y, path),
                ObjectAnimator.ofFloat(imageView, View.ALPHA, 1.0f, 0.1f)
        });


/*
        animatorSet.playSequentially(new Animator[]{
                new Animator[]{
                ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, parent_bottom-imageView.getHeight(), -(screenHeight/4)*3),
                ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, parent_w/2, getRandIn(parent, imageView))},
        });*/

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //animateMe2(parent, imageView, screenHeight);
                parent.removeView(imageView);
            }
        });

        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    public static void animateMe2(LinearLayout parent, ImageView imageView, int screen) {

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, (screen/4)*3, -screen/2),
                /* ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X,
                         getRandIn(parent, imageView),
                         getRandIn(parent, imageView)),*/
                ObjectAnimator.ofFloat(imageView, View.ALPHA, 1.0f, 0.1f)
        });

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                parent.removeView(imageView);
            }
        });

        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    public static void topGiftAnimView(Context context, LinearLayout parent, View imageView) {

        int screewidth = DeviceUtils.getScreenWidthPixels(context);
        int viewWidth = imageView.getWidth();
        int center = screewidth/2-viewWidth/2;

        ObjectAnimator lftToCenter = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, screewidth+viewWidth, 0)
                .setDuration(900);
        lftToCenter.setInterpolator(new OvershootInterpolator());

        ObjectAnimator pauseAnim = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, 0, 0)
                .setDuration(1000);

        ObjectAnimator centerToRgt = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, 0, -viewWidth)
                .setDuration(1200);
        centerToRgt.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(lftToCenter, pauseAnim);
        //animatorSet.setDuration(3900);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //parent.removeView(imageView);
                new Handler(Looper.myLooper()).postDelayed(()->{
                    topGiftAnimViewLeft(parent, imageView);
                }, 1000);
            }
        });

        animatorSet.start();

    }

    public static void topGiftAnimViewLeft(LinearLayout parent, View imageView) {

        int viewWidth = imageView.getWidth();

        ObjectAnimator centerToRgt = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, 0, -viewWidth)
                .setDuration(1000); // to animate left to right

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(centerToRgt);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                parent.removeView(imageView);
            }
        });

        animatorSet.start();

    }

    //////////////////////////////////////////////////////////////////////////////
    public static void sideGiftAnim(LinearLayout parent, View imageView, int viewWidth) {


        ObjectAnimator lftToCenter = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X,  0)
                .setDuration(1000); // to animate left to right

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(lftToCenter);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                new Handler(Looper.myLooper()).postDelayed(()->{
                    sideGiftAnim_2(parent, imageView);
                }, 1000);

            }
        });

        animatorSet.start();

    }

    public static void sideGiftAnim_2(LinearLayout parent, View imageView) {

        int viewWidth = imageView.getWidth();

        ObjectAnimator lftToCenter = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, 0, -viewWidth)
                .setDuration(1000); // to animate left to right

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(lftToCenter);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                parent.removeView(imageView);
            }
        });

        animatorSet.start();

    }


    public static void PlaylikedAnimation(Context context){
        LottieAnimationView lottieAnimationView = new LottieAnimationView(context);


    }

    public static void bounceAnim(Context context, View v){
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);

        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        v.startAnimation(myAnim);
    }



}
