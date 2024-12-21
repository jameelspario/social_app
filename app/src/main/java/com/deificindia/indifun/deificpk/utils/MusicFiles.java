package com.deificindia.indifun.deificpk.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.List;

public class MusicFiles {

    public interface Musiclistner{

        void amusiclist(List<AudioModel> tempAudioList);

    }
    Musiclistner musiclistner;
    Context context;
    public MusicFiles(Musiclistner musiclistner) {
        this.musiclistner = musiclistner;
    }

    public void getMusiclist(Context context) {
       this.context=context;
       new Thread(new Runnable() {
           @Override
           public void run() {
               List<AudioModel> tmusic=   getAllAudioFromDevice(context);

               new Handler(Looper.getMainLooper()).post(new Runnable() {
                   @Override
                   public void run() {
                       musiclistner.amusiclist(tmusic);
                   }
               });

           }
       }).start();

    }

    public List<AudioModel> getAllAudioFromDevice(final Context context) {

        final List<AudioModel> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST,};
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {

                AudioModel audioModel = new AudioModel();
                String path = c.getString(0);
                String album = c.getString(1);
                String artist = c.getString(2);

                String name = path.substring(path.lastIndexOf("/") + 1);

                audioModel.setName(name);
                audioModel.setAlbum(album);
                audioModel.setArtist(artist);
                audioModel.setPath(path);

                Log.e("Name :" + name, " Album :" + album);
                Log.e("Path :" + path, " Artist :" + artist);

                tempAudioList.add(audioModel);
            }
            c.close();
        }

        return tempAudioList;
    }
}
