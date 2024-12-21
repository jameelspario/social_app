package com.deificindia.chat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;


import com.bumptech.glide.Glide;
import com.deificindia.chat.Fragments.ChatsFragment;
import com.deificindia.chat.Fragments.UsersFragment;
import com.deificindia.chat.Model.Chat;
import com.deificindia.chat.Model.User;
import com.deificindia.firebasefragment.PostFirebaseFragment;
import com.deificindia.indifun.Activities.ProfileActivity;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.fragments.ProfileFragment;
import com.deificindia.indifun.modals.Post;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.HashMap;


public class ChatActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;
    ImageView back;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setStatusBarColor(getResources().getColor(R.color.LightSalmon));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.getWindow().setColorMode(getResources().getColor(R.color.black));
//        }
        setContentView(R.layout.activity_chat1);
//UsersFragment fragment2=new UsersFragment();
//FragmentManager fg=getSupportFragmentManager();
//fg.beginTransaction().replace(R.id.cont,fragment2).commit();


//        getSupportActionBar().hide();

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                Intent foll=new Intent(ChatActivity.this, .class);
//                 getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(foll);
//                startActivity(new Intent(ChatActivity.this, DashBoard.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
username.setVisibility(View.GONE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                username.setText(user.getUsername());
                username.setVisibility(View.GONE);
//                if (user.getImageURL().equals("default")){
//                    profile_image.setImageResource(R.drawable.ic_user);
//                } else {

                    //change this
                    Glide.with(getApplicationContext()).load(URLs.UserImageBaseUrl+user.getImageURL()).into(profile_image);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        @SuppressLint("WrongViewCast")
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);


        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                int unread = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        Chat chat = snapshot.getValue(Chat.class);
                        assert chat != null;
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && !chat.isIsseen()) {
                            unread++;
                            Log.d("aya", chat.getReceiver());
                        }
                    } catch (Exception ignored) {
                    }
                }
                try {
                    if (unread == 0){
                        viewPagerAdapter.addFragment(new ChatsFragment(), "All Messages");
                    } else {

                        viewPagerAdapter.addFragment(new ChatsFragment(), "(" + unread + ") All Messages");
//                        chatcount.setText(unread);
//                        chatcount.setVisibility(View.VISIBLE);
                    }


                    viewPagerAdapter.addFragment(new UsersFragment(), "Users");
//                viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");

                    viewPager.setAdapter(viewPagerAdapter);

                    tabLayout.setupWithViewPager(viewPager);

                }catch (Exception ignored)
                {

                }

            }        @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

   public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final ArrayList<Fragment> fragments;
        private final ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        // Ctrl + O

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}
