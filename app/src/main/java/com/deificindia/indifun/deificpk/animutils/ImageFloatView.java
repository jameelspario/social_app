package com.deificindia.indifun.deificpk.animutils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deificindia.indifun.Utility.DeviceUtils;
import com.deificindia.indifun.Utility.ResourceUtils;
import com.deificindia.indifun.Utility.UiUtils;


public class ImageFloatView extends FrameLayout {

    private int minScreen = 0;

    public ImageFloatView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ImageFloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageFloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ImageFloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(Context context){
        this.minScreen = Math.min(ResourceUtils.getScreenHeight(context), ResourceUtils.getScreenWidth(context));
    }

    public ImageView generateImage(){
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPivotX(0.0f);
        imageView.setPivotY(0.0f);

        return imageView;
    }

    public void setToLayout(ImageView imageView){
        LayoutParams lp = new LayoutParams(50, 50);
        addView(imageView, lp);
    }

    public void setImageRes(ImageView imageView, int res /*R.drawable.img*/) {
        try {
            if (!UiUtils.isNull(imageView)) {
                imageView.setImageResource(res);
            }
        } catch (Throwable th) {
            //Ln.e(th);
        }
    }

    public final void animateMe(ImageView imageView, float f2, float f3, float f4, float f5, boolean remove) {
            AnimatorSet animatorSet = new AnimatorSet();
            float dpToPx = (float) DeviceUtils.dpToPx(30);
            float f6 = dpToPx / f5;
            float f7 = dpToPx / ((float) 2);
            animatorSet.playTogether(new Animator[]{
                    ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, new float[]{0.0f, (f3 - f2) - f7}),
                    ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, new float[]{0.0f, (f4 - f7) - ((((float) this.minScreen) / 2.0f) - (f5 / 2.0f))}),
                    ObjectAnimator.ofFloat(imageView, View.SCALE_X, new float[]{1.0f, f6}),
                    ObjectAnimator.ofFloat(imageView, View.SCALE_Y, new float[]{1.0f, f6})
            });
            if (remove) {
                animatorSet.addListener(new animListener(this, imageView));
            }
            animatorSet.setDuration(500);
            animatorSet.start();
    }

    public static final class animListener implements Animator.AnimatorListener {
        ImageFloatView imageFloatView;
        View view;

        animListener(ImageFloatView goldHeartFlyView, View view) {
            this.imageFloatView = goldHeartFlyView;
            this.view = view;
        }

        public void onAnimationCancel(Animator animator) { }

        public void onAnimationEnd(Animator animator) {
            this.imageFloatView.removeOnEnd(this.view);
        }

        public void onAnimationRepeat(Animator animator) { }

        public void onAnimationStart(Animator animator) { }
    }

    public final void removeOnEnd(View view) {
        //ViewUtil.removeChild(this);
        removeView(view);

    }


}
