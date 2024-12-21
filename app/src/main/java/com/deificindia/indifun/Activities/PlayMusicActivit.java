package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.KotUtils;
import com.deificindia.indifun.deificpk.utils.AudioModel;
import com.deificindia.indifun.deificpk.utils.MusicFiles;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicActivit extends AppCompatActivity {
    private ListView listview;
    private String mediaPath;
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer mediaPlayer = new MediaPlayer();

    ProgressBar songsLoadingProgressBar;
    ArrayList<String> songNames=new ArrayList<>();

    /**
     * We will scan and load all mp3 files in a background thread via asynctask.
     */
    // Use AsyncTask to read all mp3 file names

    /*
     *OPEN PLAYER ACTIVITY PASSING THE SONG TO PLAYER
     */
    private void openPlayerActivity(int position)
    {
        Intent i=new Intent(this,PlayerActivity.class);
        i.putExtra("SONG_KEY",songs.get(position));
        startActivity(i);
    }



    /**
     * When the activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        songsLoadingProgressBar=findViewById(R.id.myProgressBar);
        listview = findViewById(R.id.mListView);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openPlayerActivity(position);
            }
        });

        new MusicFiles(tempAudioList -> {
            Gson gs=new Gson();
            System.out.println("list"+ new Gson().toJson(tempAudioList) );
            ArrayAdapter<String> songList =  new ArrayAdapter<>(PlayMusicActivit.this, android.R.layout.simple_list_item_1, KotUtils.obj.filter1(tempAudioList));
            listview.setAdapter(songList);
            songs = KotUtils.obj.filter1(tempAudioList);
          //  listview.KotUtils.obj.filter1(tempAudioList);

        }).getMusiclist(this);

    }


    /** Called when the activity is stopped */
    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) mediaPlayer.reset();
    }



}