package com.deificindia.indifun.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.deificindia.indifun.Activities.LeaderBoardActivity;
import com.google.auto.value.AutoValue;

import static com.deificindia.indifun.Utility.Logger.loge;

public class CircleReavealAnim {

    public interface OnAnimFinish{
        void onFinish();
    }

    public static void circleRevealDialog(View parentView, int cx, int cy, boolean b, AnimatorListenerAdapter listener) {
        circleRevealAnim(b, parentView, cx, cy, listener);
    }

    public static void circleRevealDialog(View parentView, View fromView, boolean b, AnimatorListenerAdapter listener) {

        int cx = (int) (fromView.getX() + (fromView.getWidth()/2));
        int cy = (int) (fromView.getY() + (fromView.getHeight()/2));

        circleRevealAnim(b, parentView, cx, cy, listener);
    }

    private static void circleRevealAnim(boolean b, View view, int cx, int cy, AnimatorListenerAdapter listener){

        loge("circleRevealDialog" , ""+cx, ""+cy);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        if(b){
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx,cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(500);
            revealAnimator.start();

        } else {

            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            if(listener!=null) { anim.addListener(listener); }

           /* new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            }*/

            anim.setDuration(500);
            anim.start();
        }
    }

//////////////////////////////////////////////////////////////////
    public static void activityReaveal(View rootLayout, int cx, int cy, boolean b, OnAnimFinish listener){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(b){
                float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

                // create the animator for this view (the start radius is zero)
                Animator circularReveal = ViewAnimationUtils
                        .createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
                circularReveal.setDuration(700);
                circularReveal.setInterpolator(new AccelerateInterpolator());

                // make the view visible and start the animation
                rootLayout.setVisibility(View.VISIBLE);
                circularReveal.start();
            }else {
                float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
                Animator circularReveal = ViewAnimationUtils
                        .createCircularReveal(rootLayout, cx, cy, finalRadius, 0);

                circularReveal.setDuration(700);
                circularReveal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        rootLayout.setVisibility(View.INVISIBLE);
                        if(listener!=null) listener.onFinish();
                    }
                });

                circularReveal.start();
            }
        }else {
            if(listener!=null) listener.onFinish();
        }
    }

    public static void startUserDetailsAct(Activity activity, View fromView, Intent intent){
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, fromView, "transition");

        int[] location = new int[2];
        fromView.getLocationInWindow(location);

        int revealX = (int) (location[0] + fromView.getWidth() / 2);
        int revealY = (int) (location[1] + fromView.getHeight() / 2);

        //Intent intent = new Intent(activity, LeaderBoardActivity.class);
        intent.putExtra(LeaderBoardActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(LeaderBoardActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
    ///////////////////////////////////////////////////////////////////////////////

    public static void registerCircularRevealAnimation(final Context context, final View view, final RevealAnimationSetting revealSettings, final int startColor, final int endColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    int cx = revealSettings.getCenterX();
                    int cy = revealSettings.getCenterY();
                    int width = revealSettings.getWidth();
                    int height = revealSettings.getHeight();
                    int duration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);

                    //Simply use the diagonal of the view
                    float finalRadius = (float) Math.sqrt(width * width + height * height);
                    Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius).setDuration(duration);
                    anim.setInterpolator(new FastOutSlowInInterpolator());
                    anim.start();
                    startColorAnimation(view, startColor, endColor, duration);
                }
            });
        }
    }

    static void startColorAnimation(final View view, final int startColor, final int endColor, int duration) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(startColor, endColor);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setBackgroundColor((Integer) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(duration);
        anim.start();
    }

    /////////zoom from////////
    public static void zoomFrom(View parentView, View fromView, boolean b, OnAnimFinish listener){
        int cx1 = (int) (fromView.getX() + (fromView.getWidth()/2));
        int cy1 = (int) (fromView.getY() + (fromView.getHeight()/2));

        int cx = parentView.getWidth()/2;
        int cy = cy1-parentView.getHeight()/2;

        zoomFrom(parentView, cx, cy, b, listener);
    }

    public static void zoomFrom(View parentView, float cx, float cy, boolean b, OnAnimFinish listener){
        if(b) {
            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(parentView,
                    PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                    //PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f),
                    PropertyValuesHolder.ofFloat("translationX", cx, 0.0f),
                    PropertyValuesHolder.ofFloat("translationY", cy, 0.0f)
            );
            scaleDown.setDuration(3000);
            scaleDown.start();
        }else {
            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(parentView,
                    PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.0f),
                    //PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f),
                    PropertyValuesHolder.ofFloat("translationX", 0.0f, cx),
                    PropertyValuesHolder.ofFloat("translationY",  0.0f, cy)
            );
            scaleDown.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(listener!=null) listener.onFinish();
                }

                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            scaleDown.setDuration(3000);
            scaleDown.start();
        }
    }
}




