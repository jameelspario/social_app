package com.deificindia.indifun.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.logpk;

public class DownloadGift {

    private String TAG = "DownloadGift";
    private static final int BUFFER_SIZE = 4096;


    private DownloadGiftCallbacks mCallbacks;
    private DownloadTask mTask;
    private Context context;
    private String fullUrl;
    private String foldername;
    File extractedFolder;
    File zipfilePath1;

    public static DownloadGift instance(){
        return new DownloadGift();
    }

    public DownloadGift with(Context context, String fullUrl, String name){
        this.context = context;
        this.fullUrl = fullUrl;
        this.foldername = name;
        return this;
    }

    public DownloadGift listener(DownloadGiftCallbacks mCallbacks){
        this.mCallbacks = mCallbacks;
        return this;
    }

    public void init(){
        extractedFolder = new File("/data/data/" + context.getPackageName() + "/gifts/"+foldername.replace(".zip", ""));
        zipfilePath1 = new File("/data/data/" + context.getPackageName() + "/gifts/"+foldername);

        if(extractedFolder.exists()){
            //read files
            if(mCallbacks!=null) mCallbacks.onPostExecute(readFiles(extractedFolder));
        }else{
            if(zipfilePath1.exists()){
                //unzip

                try {
                    unzip(zipfilePath1.getAbsolutePath(), extractedFolder.getAbsolutePath());
                    //zipfilePath.delete();
                    if(mCallbacks!=null) mCallbacks.onPostExecute(readFiles(extractedFolder));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                //download
                logpk(TAG, "download zip");
                File gameDir = new File("/data/data/" + context.getPackageName() + "/gifts");
                gameDir.mkdirs();
                // Create and execute the background task.
                mTask = new DownloadTask();
                mTask.execute(fullUrl, "/data/data/" + context.getPackageName() + "/gifts/"+foldername);
            }
        }
    }

    private AnimationDrawable readFiles(File fil){
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

    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        zipfilePath1.delete();
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }


    private class DownloadTask extends AsyncTask<String, Integer, AnimationDrawable> {

        @Override
        protected AnimationDrawable doInBackground(String... args) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            String destinationFilePath = "";
            try {
                URL url = new URL(args[0]);
                destinationFilePath = args[1];

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    //return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
                    return null;
                }

                // download the file
                input = connection.getInputStream();

                Log.d("DownloadFragment ", "destinationFilePath=" + destinationFilePath);
                new File(destinationFilePath).createNewFile();
                output = new FileOutputStream(destinationFilePath);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //return e.toString();
                return null;
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }

            File f = new File(destinationFilePath);

            Log.d("DownloadFragment ", "f.getParentFile().getPath()=" + f.getParentFile().getPath());
            Log.d("DownloadFragment ", "f.getName()=" + f.getName().replace(".zip", ""));
            //unpackZip(f.getParentFile().getPath(), f.getName().replace(".zip", ""));
            try {
                unzip(zipfilePath1.getAbsolutePath(), extractedFolder.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return readFiles(extractedFolder);
        }

        @Override
        protected void onPostExecute(AnimationDrawable s) {
            super.onPostExecute(s);
            Log.d("DownloadFragment ", "completed");
            if(mCallbacks!=null) mCallbacks.onPostExecute(s);
        }
    }



}
