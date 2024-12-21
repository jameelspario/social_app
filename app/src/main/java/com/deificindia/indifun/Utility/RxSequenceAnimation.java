package com.deificindia.indifun.Utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.deificpk.gif.GifParser;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class RxSequenceAnimation {
    private static final int[] PNG_RESOURCES1 = new int[]{
           /* R.drawable.sequence_frame_00,
            R.drawable.sequence_frame_01,
            R.drawable.sequence_frame_02*/
    };

    String[] images;

    private static final String TAG = "rx-seq-anim";
    private final Resources mResource;
    private final ImageView mImageView;
    private Context context;
    private final byte[][] RAW_PNG_DATA;
    private final byte[] buff = new byte[1024];
    private Disposable disposable;

    String parentFolder;

    public RxSequenceAnimation(Resources resources, String folder, String[] images, ImageView imageView) {
        this.mResource = resources;
        this.images = images;
        this.mImageView = imageView;
        this.parentFolder = folder;
        this.context = imageView.getContext();
        this.RAW_PNG_DATA = new byte[images.length][];


    }

    public void setRes(ImageView img){
        readToByte(0);
        img.setImageBitmap(getBit(0));
    }

    public void start(int fps) {

        // .subscribe(LogErrorSubscriber.newInstance(TAG));*/
        disposable = Observable.interval(fps, TimeUnit.MILLISECONDS)
                .map(l -> {
                    int i = (int) (l % images.length);
                    readToByte(i);
                    // decode directly from RAM - only one full blown bitmap is in RAM at a time
                    return getBit(i);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(bitmap -> mImageView.setImageBitmap(bitmap))
                .subscribe(bitmap -> Log.e(TAG, "accept "),
                        throwable -> Log.e(TAG, "accept " + String.valueOf(throwable.getMessage())));
    }

    private void readToByte(int i){
        if (RAW_PNG_DATA[i] == null) {
            // read raw png data (compressed) if not read already into RAM
            try {
                RAW_PNG_DATA[i] = read2(i);
            } catch (IOException e) {
                Log.e(TAG, "IOException " + String.valueOf(e));
            }
            Log.d(TAG, "decoded " + i + " size " + RAW_PNG_DATA[i].length);
        }
    }

    private Bitmap getBit(int i){
        return BitmapFactory.decodeByteArray(RAW_PNG_DATA[i], 0, RAW_PNG_DATA[i].length);
    }

    public void stop() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private byte[] read(int resId) throws IOException {
        return streamToByteArray(inputStream(resId));
    }

    private byte[] read2(int resId) throws IOException {
        return streamToByteArray(readInputStreamFromAssets(resId));
    }

    private InputStream readInputStreamFromAssets(int i) throws IOException {
        return context.getAssets().open(this.parentFolder+"/"+ GifParser.SEQ+"/"+ images[i]);
    }

    private InputStream inputStream(int id) {
        return mResource.openRawResource(id);
    }

    private byte[] streamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read(buff, 0, buff.length)) > 0) {
            baos.write(buff, 0, i);
        }
        byte[] bytes = baos.toByteArray();
        is.close();
        return bytes;
    }
}
