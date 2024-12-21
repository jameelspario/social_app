package com.deificindia.indifun.deificpk.animutils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.deificindia.indifun.Utility.DeviceUtils;

import java.util.ArrayList;


public class GiftAnim extends FrameLayout {

    private ArrayList<View> views = new ArrayList<>();


    boolean animating=false;

    public GiftAnim(@NonNull Context context) {
        super(context);
    }

    public GiftAnim(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GiftAnim(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GiftAnim(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void generateView(View view){

       //views.add(view);
       addView(view);
      // view.setVisibility(GONE);
        //return view;
    }

    public void animateMe(View imageView, boolean remove) {
       /* if(animating) return;
        View imageView = null;
        if(views.size() > 0 && views.get(0)!=null) {
            imageView = views.get(0);
            animating = true;
        }else {
            animating = false;
            return;
        }*/

        if(imageView==null) return;
        imageView.setVisibility(VISIBLE);
        int screewidth = DeviceUtils.getScreenWidthPixels(getContext());
        int viewWidth = imageView.getWidth();
        int center = screewidth/2-viewWidth/2;

        ObjectAnimator lftToRgt = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, new float[]{ (screewidth+viewWidth), 0.0f})
                .setDuration(800); // to animate left to right

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play( lftToRgt);//.before( rgtToLft );

        animatorSet.addListener(new animListener(this, imageView, 1, false));

        //animatorSet.setDuration(500);
        animatorSet.start();



    }

    void stepEnd(boolean remove, View imageView){

        int viewWidth = imageView.getWidth();
        ObjectAnimator rgtToLft = ObjectAnimator
                .ofFloat(imageView, View.TRANSLATION_X, 0.0f, -viewWidth)
                .setDuration(800); // to animate right to left

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play( rgtToLft);//.before( rgtToLft );

        animatorSet.addListener(new animListener(this, imageView, 2, remove));

        //animatorSet.setDuration(500);
        animatorSet.start();
    }

    public static final class animListener implements Animator.AnimatorListener {
        GiftAnim imageFloatView;
        int step = 1;
        View view;
        boolean remove;

        animListener(GiftAnim goldHeartFlyView, View view, int step,boolean remove) {
            this.imageFloatView = goldHeartFlyView;
            this.view = view;
            this.step = step;
            this.remove = remove;
        }

        public void onAnimationCancel(Animator animator) { }

        public void onAnimationEnd(Animator animator) {
            //if(step==1){
                //this.imageFloatView.stepEnd(remove, view);
           /* }else{
                if(this.remove) {
                    this.imageFloatView.removeOnEnd(this.view);
                   // if(this.imageFloatView.views.size()>0 && this.imageFloatView.views.get(0)!=null)
                    this.imageFloatView.views.remove(this.view);
                    //this.imageFloatView.views.removeFirst();
                }


                this.imageFloatView.animateMe(remove);

*/

            if(step==2){
                if(this.remove) this.imageFloatView.removeOnEnd(this.view);
            }else {
            new Thread(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
                this.imageFloatView.stepEnd(true, view);
            }

        }

        public void onAnimationRepeat(Animator animator) { }

        public void onAnimationStart(Animator animator) { }
    }

    public final void removeOnEnd(View view) {
        //ViewUtil.removeChild(this);
        removeView(view);

    }


}
