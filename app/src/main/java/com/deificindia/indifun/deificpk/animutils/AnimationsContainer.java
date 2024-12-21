package com.deificindia.indifun.deificpk.animutils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;


import com.deificindia.indifun.deificpk.gif.GifParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

/**
 * animation = AnimationsContainer.getInstance(R.array.loading_anim, 58).createProgressDialogAnim(imageView);
 *
 *                     if(!switchBtn()){
 *                         animation.start();
 *                     }else {
 *                         animation.stop();
 */

public class AnimationsContainer {

    public static final String TAG = "AnimationsContainer";
    public int FPS = 58;  // 每秒播放帧数，fps = 1/t，t-动画两帧时间间隔
    private String[] resId; /*R.array.loading_anim*/; //图片资源
    private Context mContext;
    // 单例
    private static AnimationsContainer mInstance;


    public AnimationsContainer(Context context){
        this.mContext = context;
    }
    //获取单例
    public static AnimationsContainer getInstance(Context context, int fps) {
        if (mInstance == null)
            mInstance = new AnimationsContainer(context);
        mInstance.setResId(fps);
        return mInstance;
    }

    public void setResId( int fps){
        //this.resId = resId;
        this.FPS = fps;
    }
//    // 从xml中读取资源ID数组
//    private int[] mProgressAnimFrames = getData(resId);

    /**
     * @param imageView
     * @return progress dialog animation
     */
    public FramesSequenceAnimation createProgressDialogAnim(String parentFolder,ImageView imageView, String[] resId) {
        return new FramesSequenceAnimation(parentFolder, imageView, resId, FPS);
    }


    /**
     * 循环读取帧---循环播放帧
     */
    public class FramesSequenceAnimation {
        private int[] mFrames1; // 帧数组
        private byte[][] mFramesByte; // 帧数组
        private String parentFolder;
        private String[] images;
        private int mIndex; // 当前帧
        private boolean mShouldRun; // 开始/停止播放用
        private boolean mIsRunning; // 动画是否正在播放，防止重复播放
        private SoftReference<ImageView> mSoftReferenceImageView; // 软引用ImageView，以便及时释放掉
        private Handler mHandler;
        private int mDelayMillis;
        private OnAnimationStoppedListener mOnAnimationStoppedListener; //播放停止监听

        private Bitmap mBitmap = null;
        private BitmapFactory.Options mBitmapOptions;//Bitmap管理类，可有效减少Bitmap的OOM问题

        public FramesSequenceAnimation(String parentFolder, ImageView imageView, String[] images, int fps) {
            mHandler = new Handler();
            //mFrames = frames;
            this.parentFolder = parentFolder;
            this.images = images;
            mFramesByte = new byte[images.length][];
            mIndex = -1;
            mSoftReferenceImageView = new SoftReference<ImageView>(imageView);
            mShouldRun = false;
            mIsRunning = false;
            mDelayMillis = 1000 / fps;//帧动画时间间隔，毫秒

            imageView.setImageBitmap(bit(zerothByte()));

            // 当图片大小类型相同时进行复用，避免频繁GC
            if (Build.VERSION.SDK_INT >= 11) {
                Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                Bitmap.Config config = bmp.getConfig();
                mBitmap = Bitmap.createBitmap(width, height, config);
                mBitmapOptions = new BitmapFactory.Options();
                //设置Bitmap内存复用
                mBitmapOptions.inBitmap = mBitmap;//Bitmap复用内存块，类似对象池，避免不必要的内存分配和回收
                mBitmapOptions.inMutable = true;//解码时返回可变Bitmap
                mBitmapOptions.inSampleSize = 1;//缩放比例
            }
        }
        //循环读取下一帧
       /* private int getNext() {
            mIndex++;
            if (mIndex >= mFrames.length)
                mIndex = 0;
            return mFrames[mIndex];
        }*/

        private byte[] getNextByte() {
            mIndex++;
            if (mIndex >= mFramesByte.length)
                mIndex = 0;

            if (mFramesByte[mIndex] == null) {
                // read raw png data (compressed) if not read already into RAM
                try {
                    mFramesByte[mIndex] = read2(parentFolder, images[mIndex]);
                } catch (IOException e) {
                    Log.e(TAG, "IOException " + String.valueOf(e));
                }
                Log.d(TAG, "decoded " + mIndex + " size " + mFramesByte[mIndex].length);
            }
            return mFramesByte[mIndex];
        }

        private byte[] zerothByte(){
            if (mFramesByte[0] == null) {
                // read raw png data (compressed) if not read already into RAM
                try {
                    mFramesByte[0] = read2(parentFolder, images[0]);
                } catch (IOException e) {
                    Log.e(TAG, "IOException " + String.valueOf(e));
                }
                Log.d(TAG, "decoded " + mIndex + " size " + mFramesByte[0].length);
            }
            return mFramesByte[0];
        }

        private Bitmap bit(byte[] zero){
            return BitmapFactory.decodeByteArray(zero, 0, zero.length);
        }

        /**
         * 播放动画，同步锁防止多线程读帧时，数据安全问题
         */
        public synchronized void start() {
            mShouldRun = true;
            if (mIsRunning)
                return;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ImageView imageView = mSoftReferenceImageView.get();
                    if (!mShouldRun || imageView == null) {
                        mIsRunning = false;
                        if (mOnAnimationStoppedListener != null) {
                            mOnAnimationStoppedListener.AnimationStopped();
                        }
                        return;
                    }

                    mIsRunning = true;
                    //新开线程去读下一帧
                    mHandler.postDelayed(this, mDelayMillis);

                    if (imageView.isShown()) {
                        byte[] imageRes = getNextByte();
                        if (mBitmap != null) { // so Build.VERSION.SDK_INT >= 11
                            Bitmap bitmap = null;
                            try {
                                //bitmap = BitmapFactory.decodeResource(imageView.getResources(), imageRes, mBitmapOptions);
                                bitmap = BitmapFactory.decodeByteArray(imageRes, 0, imageRes.length);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap);
                            } else {
                                //imageView.setImageResource(imageRes);
                                mBitmap.recycle();
                                mBitmap = null;
                            }
                        } else {
                            //imageView.setImageResource(imageRes);
                        }
                    }

                }
            };

            mHandler.post(runnable);
        }

        /**
         * 停止播放
         */
        public synchronized void stop() {
            mShouldRun = false;
        }

        /**
         * 设置停止播放监听
         * @param listener
         */
        public void setOnAnimStopListener(OnAnimationStoppedListener listener){
            this.mOnAnimationStoppedListener = listener;
        }
    }

    /**
     * 从xml中读取帧数组
     * @param resId
     * @return
     */
    private int[] getData(int resId){
        TypedArray array = mContext.getResources().obtainTypedArray(resId);

        int len = array.length();
        int[] intArray = new int[array.length()];

        for(int i = 0; i < len; i++){
            intArray[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        return intArray;
    }

    /**
     * 停止播放监听
     */
    public interface OnAnimationStoppedListener{
        void AnimationStopped();
    }

    private byte[] read2(String parentFolder, String filename) throws IOException {
        return streamToByteArray(readInputStreamFromAssets(parentFolder, filename));
    }

    private InputStream readInputStreamFromAssets(String parentFolder, String filename) throws IOException {
        return mContext.getAssets().open(parentFolder+"/"+ GifParser.SEQ+"/"+ filename);
    }

    private final byte[] buff = new byte[1024];
    private byte[] streamToByteArray(InputStream is) throws IOException {
        java.io.ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read(buff, 0, buff.length)) > 0) {
            baos.write(buff, 0, i);
        }
        byte[] bytes = baos.toByteArray();
        is.close();
        return bytes;
    }

}