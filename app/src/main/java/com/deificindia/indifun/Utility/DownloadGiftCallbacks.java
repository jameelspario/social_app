package com.deificindia.indifun.Utility;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.annotations.NonNull;

import static com.deificindia.indifun.Utility.Logger.loge;


public abstract class DownloadGiftCallbacks {

    public abstract void onPostExecute(AnimationDrawable msg);
    public abstract void onGif(Bundle bundle);
    public abstract void onEnd();

    public void play(Context context, @NonNull FrameLayout topContainer, AnimationDrawable animation){

        /*
         void down(){
        DownloadGift.instance()
                .with(this, "https://www.dropbox.com/s/vt4netrgxpgswhg/kissme1.zip?dl=1", "kissme1.zip")
                .listener(new DownloadGiftCallbacks() {
                    @Override
                    public void onPostExecute(AnimationDrawable msg) {
                        super.play(HomeActivity.this, topContainer, msg);
                    }

                    @Override
                    public void onEnd() {

                    }
                })
        .init();
    }
        *
        * */
        if(animation!=null && animation.getNumberOfFrames()>0) {
            ImageView anim = new ImageView(context);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            anim.setLayoutParams(params);

            anim.setScaleType(ImageView.ScaleType.FIT_CENTER);


            if (topContainer.getChildCount() > 0)
                topContainer.removeAllViews();

            topContainer.addView(anim);


            anim.setBackground(animation);

            //  anim.setBackgroundResource(R.drawable.seque_diya /*R.drawable.spin_animation*/);
            // Get the background, which has been compiled to an AnimationDrawable object.
            AnimationDrawable frameAnimation = (AnimationDrawable) anim.getBackground();

            frameAnimation.setCallback(new AnimationDrawableCallback(frameAnimation, anim) {

                @Override
                public void onAnimationComplete() {
                    anim.setBackgroundResource(0);
                    anim.setVisibility(View.GONE);
                    topContainer.removeView(anim);
                    onEnd();
                }
            });
            // Start the animation (looped playback by default).
            anim.post(() -> {
                frameAnimation.start();
            });
        }else {
            Log.e("DownloadGiftCallbacks", "play: onEnd" );
            onEnd();
        }

    }

    public void playImageSequence(@NonNull LottieAnimationView topContainer, AnimationDrawable animation){
        if(animation!=null && animation.getNumberOfFrames()>0) {

            topContainer.setBackground(animation);

            AnimationDrawable frameAnimation = (AnimationDrawable) topContainer.getBackground();

            frameAnimation.setCallback(new AnimationDrawableCallback(frameAnimation, topContainer) {

                @Override
                public void onAnimationComplete() {
                    topContainer.setBackgroundResource(0);
                    //topContainer.setVisibility(View.GONE);
                    onEnd();
                }
            });
            // Start the animation (looped playback by default).
            topContainer.post(() -> {
                frameAnimation.start();
            });
        }else {
            Log.e("DownloadGiftCallbacks", "play: onEnd" );
            onEnd();
        }
    }

    Bundle _bundle;
    public DownloadGiftCallbacks withBundle(Bundle bundle){
        this._bundle = bundle;
        return this;
    }

    public DownloadGiftCallbacks check_sequnce_or_gif(String path){
        File file = new File(path);
        if(file.isDirectory()) {
            List<File> listFile = Arrays.asList(file.listFiles());
            deciferExtension(listFile);

        } else {
            Uri fileUri = Uri.fromFile(file);
            String fileExt = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString());
            if(fileExt.equals("gif")){
                _bundle.putSerializable("file", file);
                onGif(_bundle);
            }
        }

        return this;
    }

    void deciferExtension(List<File> listFile){
        for(File file:listFile){
            if(file.isDirectory()) {
                check_sequnce_or_gif(file.getAbsolutePath());
                break;
            }else {
                Uri file2 = Uri.fromFile(file);
                String fileExt = MimeTypeMap.getFileExtensionFromUrl(file2.toString());
                if(fileExt.equals("gif")){
                    //play gif....
                    _bundle.putSerializable("file", file);
                    onGif(_bundle);
                    break;
                } else {
                    //play sequence....
                    buildSequence(listFile);
                    break;
                }
            }
        }
    }

    void buildSequence(List<File> listFile){
        Collections.sort(listFile, (o1, o2) -> {
            if (o1 == null) return -1;
            else if (o2 == null) return 1;
            return o1.getName().compareTo(o2.getName());
        });


    }


    private AnimationDrawable readSequnce(File fil){
        AnimationDrawable animation = new AnimationDrawable();
        animation.setOneShot(true);

        if(fil.isDirectory()){
            List<File> listFile = Arrays.asList(fil.listFiles());
            Collections.sort(listFile, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if (o1 == null) return -1;
                    else if (o2 == null) return 1;
                    return o1.getName().compareTo(o2.getName());
                }
            });
            //Arrays.sort(listFile);
            for(File file:listFile){
                Log.e("DownloadGift", "listFilesForFolder: "+file.getName());
                //Log.e("DownloadGift", "listFilesForFolder: "+fileEntry.getAbsolutePath());
                Drawable d = Drawable.createFromPath(file.getAbsolutePath());
                if(d!=null) animation.addFrame(d, 200);
            }
        }

        return animation;
    }


}
