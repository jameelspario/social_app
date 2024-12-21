package com.deificindia.indifun.deificpk.gif;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.FrameLayout;

import com.deificindia.indifun.Utility.Player;
import com.deificindia.indifun.Utility.RxSequenceAnimation;
import com.deificindia.indifun.deificpk.gif.mod.JsoObj;
import com.deificindia.indifun.deificpk.gif.mod.Parent;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;

public class GifParser {
    public static final String TAG = "GifParser";
    public static final String SEQ = "seq";
    public static final String INFO = "info";
    Activity context;
    FrameLayout frameLayout;
    RxSequenceAnimation rxSequenceAnimation;

    Parent dataPlayer;

    String parentFolder;
    Handler mHandler;

    Runnable runnable = ()->{

    };

    public void finish(){
        if(this.frameLayout!=null) {
            this.frameLayout.removeAllViews();
            if(rxSequenceAnimation!=null) rxSequenceAnimation.stop();
        }
    }

    public GifParser(Activity context) {
        this.context = context;
    }

    public GifParser container(FrameLayout frameLayout){
        this.frameLayout = frameLayout;
        this.mHandler = new Handler(Looper.myLooper());
        return this;
    }

    public static GifParser instance(Activity context){
        return new GifParser(context);
    }

    public GifParser parse(String folder){
        this.parentFolder = folder;

       // new Thread(()->{
            InputStream ins = readAssetsInfo(folder);
            dataPlayer = input2Gson(ins);

            if(dataPlayer!=null){
                //if(dataPlayer.seq!=null){
                    //AnimationDrawable drawable = readSeq(dataPlayer.seq.loop);

                player.onPostExecute(readImages(), dataPlayer);
               /* }else {
                    player.onPostExecute(readImages(), dataPlayer);
                }*/
            }
      //  }).start();

        return this;
    }

    Player player = new Player(){

        @Override
        public void onPostExecute(String[] msg, Parent data) {
            loge(TAG, "starting anima...");
            mHandler.postDelayed(runnable, data.length*1000);
           /* if(data.firework){
                new Handler(Looper.myLooper()).post(()->{
                    super.play3(context, frameLayout);
                });
            }*/
            if(data.json!=null && !data.json.isEmpty()){
                for(JsoObj obj:data.json){
                    lottiePlayer(obj.on*1000, obj.name, obj.loop);
                }
            }

            if(data.svga!=null){
                /*for(JsoObj obj:data.svga){
                    lottiePlayer(obj.on*1000, obj.name, obj.loop);
                }*/
                super.play3(frameLayout, parentFolder+"/"+data.svga.name, data);
            }

            if(msg!=null && data.seq!=null) {
                //new Handler(Looper.myLooper()).post(()->{
                rxSequenceAnimation = super.play0(frameLayout, parentFolder, msg, data);

               // });
            }
        }

        @Override
        public void onEnd() {

        }
    };

    void lottiePlayer(long delay, String jsonFile, boolean looop){
        //new Handler(Looper.myLooper()).postDelayed(()->{
            player.play2(frameLayout, false, parentFolder+"/"+jsonFile, looop, delay);
        //}, delay);
    }






    File getFilePath(String foldername){
        return new File("/data/data/" + context.getPackageName() + "/gifts/"+foldername);
    }

    InputStream readAssetsInfo(String folder){
        InputStream is=null;
        try {
             is = context.getAssets().open(folder+"/"+INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    InputStream readRaw(String FILENAME_WITHOUT_EXTENSION){
        //InputStream XmlFileInputStream = getResources().openRawResource(R.raw.taskslists5items); // getting XML

        InputStream ins = context.getResources().openRawResource(
                context.getResources().getIdentifier(FILENAME_WITHOUT_EXTENSION,
                        "raw", context.getPackageName()));
        return ins;
    }

    Parent input2Gson(InputStream in){
        Reader reader = null;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Parent result  = new Gson().fromJson(reader, Parent.class);

        loge("GifParser", new Gson().toJson(result));

        return result;
    }


    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public AnimationDrawable readSeq(boolean oneshot){
        AnimationDrawable animation = new AnimationDrawable();
        animation.setOneShot(oneshot);
        try {

            // to reach asset
            AssetManager assetManager = context.getAssets();
            // to get all item in dogs folder.
            String[] images = assetManager.list(this.parentFolder+"/"+SEQ);
            // to keep all image
            //Drawable[] drawables = new Drawable[images.length];
            // the loop read all image in dogs folder and  aa
            loge("Gif", ""+images.length);
            for (int i = 0; i < images.length; i++) {
                loge("Gif", ""+images[i]);
                InputStream inputStream = context.getAssets().open(this.parentFolder+"/"+SEQ+"/"+ images[i]);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                //drawables[i] = drawable;
                animation.addFrame(drawable, 200);
            }


        } catch (IOException e) {
            // you can print error or log.
        }

        return animation;
    }

    private String[] readImages(){
        AssetManager assetManager = context.getAssets();
        // to get all item in dogs folder.
        String[] images = new String[0];
        try {
            images = assetManager.list(this.parentFolder+"/"+SEQ);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }

    private AnimationDrawable readFiles(File seqFolder){
        AnimationDrawable animation = new AnimationDrawable();
        animation.setOneShot(true);

        if(seqFolder.isDirectory()){
            List<File> listFile = Arrays.asList(seqFolder.listFiles());
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
