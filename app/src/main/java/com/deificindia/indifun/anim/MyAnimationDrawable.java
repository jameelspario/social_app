package com.deificindia.indifun.anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import java.io.IOException;
/*
    //Get your ImageView
    View image = MainActivity.this.findViewById(R.id.presentation);

    //Create AnimationDrawable
    final AnimationDrawable animation = new MyAnimationDrawable(this, displayMetrics.widthPixels, displayMetrics.heightPixels) {

        @Override
        void onAnimationFinish() {
            //Do something when finish animation
        }
    };
    animation.setOneShot(true); //dont repeat animation
    //This is just to say that my AnimationDrawable has 72 frames with 50 milliseconds interval
    try {
        //Always load same bitmap, anyway you load the right one in draw() method in MyAnimationDrawable
        Bitmap bmp = BitmapFactory.decodeStream(MainActivity.this.getAssets().open("presentation/intro_00000.jpg"));
        for (int i = 0; i < 72; i++) {
            animation.addFrame(new BitmapDrawable(getResources(), bmp), 50);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    //Set AnimationDrawable to ImageView
    if (Build.VERSION.SDK_INT < 16) {
        image.setBackgroundDrawable(animation);
    } else {
        image.setBackground(animation);
    }

    //Start animation
    image.post(new Runnable() {

        @Override
        public void run() {
            animation.start();
        }

    });
* */
public abstract class MyAnimationDrawable extends AnimationDrawable {

    private Context context;
    private int current;
    private int reqWidth;
    private int reqHeight;
    private int totalTime;

    public MyAnimationDrawable(Context context, int reqWidth, int reqHeight) {
        this.context = context;
        this.current = 0;
        //In my case size of screen to scale Drawable
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
        this.totalTime = 0;
    }

    @Override
    public void addFrame(Drawable frame, int duration) {
        super.addFrame(frame, duration);
        totalTime += duration;
    }

    @Override
    public void start() {
        super.start();
        new Handler().postDelayed(new Runnable() {

            public void run() {
                onAnimationFinish();
            }
        }, totalTime);
    }

    public int getTotalTime() {
        return totalTime;
    }

    @Override
    public void draw(Canvas canvas) {
        try {
            //Loading image from assets, you could make it from resources
            Bitmap bmp = BitmapFactory.decodeStream(context.getAssets().open("presentation/intro_000"+(current < 10 ? "0"+current : current)+".jpg"));
            //Scaling image to fitCenter
            Matrix m = new Matrix();
            m.setRectToRect(new RectF(0, 0, bmp.getWidth(), bmp.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
            //Calculating the start 'x' and 'y' to paint the Bitmap
            int x = (reqWidth - bmp.getWidth()) / 2;
            int y = (reqHeight - bmp.getHeight()) / 2;
            //Painting Bitmap in canvas
            canvas.drawBitmap(bmp, x, y, null);
            //Jump to next item
            current++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract void onAnimationFinish();


}
