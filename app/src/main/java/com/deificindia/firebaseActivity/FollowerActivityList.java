package com.deificindia.firebaseActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deificindia.firebaseAdapter.FollowerAdapter;
import com.deificindia.firebaseModel.FollowerNotificaiton;
import com.deificindia.indifun.R;
import com.deificindia.indifun.fragments.PostFragment;
import com.deificindia.indifun.fragments.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FollowerActivityList extends AppCompatActivity {
    ImageView back;
    TextView Reset;
    private RecyclerView recyclerView;
    private FollowerAdapter followerAdapter;
    private List<FollowerNotificaiton> followNotificationList;
    LinearLayout linearLayout;
    Button Dash;
    String gt;
    int p=0;
    private ImageView img_back, applogo1, deleltbroadcasts;
    String userid;
    FirebaseUser fuser;
    DatabaseReference reference;
    ValueEventListener seenListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_list);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recycler12);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        followNotificationList = new ArrayList<>();
        followerAdapter = new FollowerAdapter(getApplicationContext(),followNotificationList);
        recyclerView.setAdapter(followerAdapter);
        recyclerView.setVisibility(View.VISIBLE);

        seenMessage(userid);
        readNotifications();
    }

    public void onBackPressed() {
//        Fragment frag = new ProfileFragment();
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.posyi,frag,"Test Fragment");
//        transaction.commit();
        supportFinishAfterTransition();
    }


    private void seenMessage(final String userid){
        reference = FirebaseDatabase.getInstance().getReference("NotificationsFollower");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        FollowerNotificaiton notification = snapshot.getValue(FollowerNotificaiton.class);
                        assert notification != null;
                        if (notification.getUserid().equals(fuser.getUid()) && notification.getUserid().equals(userid)) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("ispost", true);
                            snapshot.getRef().updateChildren(hashMap);
                        }
                    } catch (Exception ignored) {

                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void readNotifications(){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().
                getReference("NotificationsFollower").
                child(firebaseUser.getUid());
        final int unread=0;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                followNotificationList.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        FollowerNotificaiton notification = snapshot.getValue(FollowerNotificaiton.class);
                        assert notification != null;
                        followNotificationList.add(notification);
                        gt=followNotificationList.toString();
//                        if(notification.isIspost()){
//
//                        }

                    }
                }
                Collections.reverse(followNotificationList);
                followerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}