package com.deificindia.indifun.deificpk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.bumptech.glide.gifdecoder.StandardGifDecoder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.nio.ByteBuffer;

public class GifAnima {


    Context context;
    File mResource;
    ImageView imageView;
    OnAnimaEnd listener;

    public static GifAnima instance(){
        return new GifAnima();
    }



    public GifAnima with(File mResource, ImageView imageView, OnAnimaEnd onAnimaEnd) {
        this.context = imageView.getContext();
        this.mResource = mResource;
        this.imageView = imageView;
        this.listener = onAnimaEnd;
        return this;
    }

    public void show() {
                                      ///url//file
        Glide.with(context).asGif().load(mResource).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                        Target<GifDrawable> target, boolean isFirstResource) {
                Log.e("GifAnima", "onLoadFailed: "+e.getMessage() );
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model,
                                           Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {

                resource.setLoopCount(1);
                resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {

                    @Override
                    public void onAnimationStart(Drawable drawable) {
                        super.onAnimationStart(drawable);
                        ready();
                    }

                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        //do whatever after specified number of loops complete
                        dismiss();
                    }
                });

                return false;
            }
        }).into(imageView);

    }

    void dismiss(){

        if(this.listener!=null) this.listener.onEnd();

    }

    void ready(){
        if(this.listener!=null) this.listener.onReady();
    }

    private GiftGifDrawable getSelfStoppedGifDrawable(GifDrawable drawable) {
        GifBitmapProvider provider = new GifBitmapProvider(Glide.get(context).getBitmapPool());
        Transformation transformation = drawable.getFrameTransformation();
        if (transformation == null) {
            transformation = new CenterCrop();
        }

        ByteBuffer byteBuffer = drawable.getBuffer();
        StandardGifDecoder decoder = new StandardGifDecoder(provider);
        decoder.setData(new GifHeaderParser().setData(byteBuffer).parseHeader(),byteBuffer);
        Bitmap bitmap = drawable.getFirstFrame();
        if (bitmap == null) {
            decoder.advance();
            bitmap = decoder.getNextFrame();
        }

        return new GiftGifDrawable(context, decoder, transformation, 0, 0, bitmap);
    }

    private static class GiftGifDrawable extends GifDrawable {
        GifDecoder gifDecoder;
        GiftGifDrawable(Context context, GifDecoder gifDecoder, Transformation<Bitmap> frameTransformation,
                        int targetFrameWidth, int targetFrameHeight, Bitmap firstFrame) {
            super(context, gifDecoder, frameTransformation, targetFrameWidth, targetFrameHeight, firstFrame);
            this.gifDecoder = gifDecoder;
        }
    }

    public interface OnAnimaEnd{
        void onReady();
        void onEnd();
    }


}
