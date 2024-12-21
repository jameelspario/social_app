package com.deificindia.indifun.deificpk.tools;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MediaPlayerHelper {

    private MediaPlayer player;
    private boolean isReleased = false;

    public boolean isPlaying() {
        if (player == null || isReleased) {
            return false;
        }
        return player.isPlaying();
    }

    private void preplay(final MediaPlayer.OnCompletionListener listener){
        if (!isReleased && player != null) {
            player.release();
            player = null;
            isReleased = true;
        }
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnCompletionListener(listener);
    }

    public void playFile(Context context, Uri uri/*Uri.parse("http")*/, final MediaPlayer.OnCompletionListener listener) throws IOException {
        preplay(listener);
        player.setDataSource(context, uri);
        preparestart();
    }

    public void playFile(String path, final MediaPlayer.OnCompletionListener listener) throws IOException {
        preplay(listener);
        player.setDataSource(path);
        preparestart();
    }

    private void preparestart() throws IOException {
        player.prepare();
        player.start();

        isReleased = false;
    }

    public boolean stopPlaying() {
        if (player == null) return false;
        if (!player.isPlaying()) return false;
        player.stop();
        player.release();
        isReleased = true;
        return true;
    }

    public int getAudioId(){
        if (player!=null)
            return player.getAudioSessionId();

        return -1;
    }

}
