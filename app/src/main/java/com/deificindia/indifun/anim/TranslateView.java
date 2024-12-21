package com.deificindia.indifun.anim;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Created by spario on 3/25/2018.
 */

public class TranslateView {

    public static TranslateView instance(){
        return new TranslateView();
    }

    public void resetX(View view, int to){
        view.setX(to);
    }

    public void leftOut(View view){ //<----
        view.animate().translationX(-getScreenWidth());
    }

    public void center(View view){ //<----
        view.animate().translationX(0);
    }

    public void rightOut(View view){ //---->
        view.animate().translationX(getScreenWidth());
    }

    public void keyboardClose(View view){
        view.animate().translationX(-getScreenHeight());
    }

    public void keyboardOpen(View view){
        view.animate().translationX(0);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public int getRelHeight(View v){
        Rect rectf = new Rect();
        v.getLocalVisibleRect(rectf);
        return rectf.height();
    }

    public static int getRelBottom(View v){
        Rect rectf = new Rect();
        v.getLocalVisibleRect(rectf);
        return rectf.bottom;
    }
    public static int getRelLeft(View v){
        Rect rectf = new Rect();
        v.getLocalVisibleRect(rectf);
        return rectf.left;
    }

    public static int getRelRight(View v){
        Rect rectf = new Rect();
        v.getLocalVisibleRect(rectf);
        return rectf.right;
    }


    public static int getRelTop(View v){
        Rect rectf = new Rect();
        v.getGlobalVisibleRect(rectf);
        return rectf.top;
    }

    public static Rect getRes(View v, int gl){
        Rect rectf = new Rect();

        if(gl==0) {
            //For coordinates location relative to the parent
            v.getLocalVisibleRect(rectf);
        }else {
            //For coordinates location relative to the screen/display
            v.getGlobalVisibleRect(rectf);
        }
        Log.d("WIDTH        :", String.valueOf(rectf.width()));
        Log.d("HEIGHT       :", String.valueOf(rectf.height()));
        Log.d("left         :", String.valueOf(rectf.left));
        Log.d("right        :", String.valueOf(rectf.right));
        Log.d("top          :", String.valueOf(rectf.top));
        Log.d("bottom       :", String.valueOf(rectf.bottom));

        return rectf;
    }

    public static void touch(ConstraintLayout myLayout){
        myLayout.setOnTouchListener(
                new ConstraintLayout.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent m) {
                        // Perform tasks here
                        Log.e("TOUCH", String.valueOf(m.getX()));
                        v.performClick();
                        return true;
                    }

                }
        );
    }
    public static int getPx(Context ctx, int dp){
        float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static float getPxF(Context ctx, float dp){
        float scale = ctx.getResources().getDisplayMetrics().density;
        return (dp * scale);
    }

    public static int dpToPx(Context ctx,int dp) {
        float density = ctx.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public static int getScreenWidth(Activity cnx){
        Display display = cnx.getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        final int width = point.x; // screen width
        return width;

        //return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Activity cnx){
        Display display = cnx.getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        final int h = point.y;
        return h;
    }

    //setActivityDiemention(this,getScreenWidth(this) - 20, getScreenHeight(this) - 20);
    public static final void setActivityDiemention(Activity activity ,int width,int hieght) {
        android.view.WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.width = width; //WindowManager.LayoutParams.MATCH_PARENT;
        params.height = hieght; //params.width;
        activity.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    // To animate view slide out from left to right
    public void slideToRight(View view){
        TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    // To animate view slide out from right to left
    public void slideToLeft(View view){
        TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }



    // To animate view slide out from bottom to top
    public void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from top to bottom
    public void slideUp(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0, -view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        view.startAnimation(animate);

    }

    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0, -view.getHeight(), 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        view.startAnimation(animate);

    }

    public void centerRect(View view){
        Rect bounds = new Rect();
        view.getDrawingRect(bounds);
        int centerX = bounds.centerX();
        int centerY = bounds.centerY();
    }


}
