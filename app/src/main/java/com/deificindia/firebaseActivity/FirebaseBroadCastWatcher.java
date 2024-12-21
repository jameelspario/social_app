package com.deificindia.firebaseActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.firebaseAdapter.BroadCastAdapter;
import com.deificindia.indifun.Activities.BroadcastsWatchActivity;
import com.deificindia.indifun.R;
import com.deificindia.indifun.modals.Post;
import com.deificindia.indifun.pojo.broadcastwatchedpojo.ResultItem;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseBroadCastWatcher extends AppCompatActivity {
    private ImageView img_back, applogo1, deleltbroadcasts;
    private TextView txt_header_title;
    private Dialog myDialog;
    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    private RecyclerView broadcastlist;
    private TextView nobroadcast;
    private BroadCastAdapter broadCastAdapter;
    private List<Post> mpost;
    LoadingAnimView loadingAnimView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_broad_cast_watcher);
        img_back = findViewById(R.id.img_back);
        loadingAnimView=findViewById(R.id.loading);

        nobroadcast = findViewById(R.id.nobroadcast);
        nobroadcast.setVisibility(View.GONE);
        broadcastlist = findViewById(R.id.broadcastlist);
        txt_header_title = findViewById(R.id.txt_header_title);
        deleltbroadcasts = findViewById(R.id.deleltbroadcasts);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        deleltbroadcasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(FirebaseBroadCastWatcher.this);
                alert.setTitle(getString(R.string.alert));
                alert.setMessage(getString(R.string.deletemsg));
                alert.setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                               // deletebroadcast();

                                dialog.dismiss();

                            }
                        }).setNegativeButton(getString(R.string.cancel1), null);

                AlertDialog alert1 = alert.create();
                alert1.show();
            }
        });

       LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        broadcastlist.setLayoutManager(linearLayoutManager);
        mpost=new ArrayList<>();
        broadCastAdapter=new BroadCastAdapter(getApplicationContext(),mpost);
        broadcastlist.setAdapter(broadCastAdapter);
readPosts();
//        getBroadInfo();
    }
    private void readPosts() {
        loadingAnimView.startloading();
        //    final String[] rando = new String[1];
       String fr= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BroadWtacher");

        ref.child(fr).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mpost.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post post = snapshot.getValue(Post.class);
                            Log.d("Findda", String.valueOf(dataSnapshot.getChildren()));
                            mpost.add(post);
                            loadingAnimView.stopAnim();

                        }
                        loadingAnimView.showError();
                        nobroadcast.setVisibility(View.VISIBLE);
                        broadCastAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

    }



}
