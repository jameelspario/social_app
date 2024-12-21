package com.deificindia.firebaseActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.deificindia.firebaseAdapter.CommentAdapter;
import com.deificindia.firebasefragment.Comment;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.fragments.PostFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;



public class CommentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    EditText addcomment;
    ImageView image_profile;
    ImageView post;

    String postid,commentid;
    String publisherid;
    ImageView back;
    TextView Reset;
    FirebaseUser firebaseUser;
    String isdatefind,isdatefind1;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.back_to_home_button);
        getSupportActionBar().setTitle("Comments");
//        back=(ImageView)findViewById(R.id.back);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleOnBackPress();
//            }
//
//            private void handleOnBackPress() {
//                PostFragment frag = new PostFragment();
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.add(R.id.commentf,frag,"Post Fragment");
//                transaction.commit();
////
////                Intent i = new Intent(getApplicationContext(),PostFragment);
////                getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                startActivity(i);
////                finish();
//
//            }
//        });
        Intent intent = getIntent();
        postid = intent.getStringExtra("postid");
        commentid=intent.getStringExtra("commentid");
        publisherid = intent.getStringExtra("publisherid");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
   //     mLayoutManager.setStackFromEnd(true);
    //    mLayoutManager.setReverseLayout(true);
   //     recyclerView.scrollToPosition(commentAdapter.getItemCount() - 1);
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, commentList, postid);
        recyclerView.setAdapter(commentAdapter);



        post = findViewById(R.id.post);
        addcomment = findViewById(R.id.add_comment);
        image_profile = findViewById(R.id.image_profile);
         isdatefind=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addcomment.getText().toString().equals("")){
                    Toast.makeText(CommentsActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                } else {
                    addComment();
                }
            }
        });

        getImage();
        readComments();

    }

        public static String getTimeAgo() {
        long time = 0;

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";




        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    private void addComment(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);

         commentid = reference.push().getKey();
  if(publisherid.equals(firebaseUser.getUid())){
    //  Toast.makeText(this, ""+publisherid, Toast.LENGTH_SHORT).show();
      HashMap<String, Object> hashMap = new HashMap<>();
      hashMap.put("comment", addcomment.getText().toString());
      hashMap.put("publisher", firebaseUser.getUid());
      hashMap.put("commentid", commentid);
      hashMap.put("postid", postid);
      hashMap.put("time", isdatefind);

      reference.child(commentid).setValue(hashMap);
      addcomment.setText("");
  }else {

      HashMap<String, Object> hashMap = new HashMap<>();
      hashMap.put("comment", addcomment.getText().toString());
      hashMap.put("publisher", firebaseUser.getUid());
      hashMap.put("commentid", commentid);
      hashMap.put("postid", postid);
      hashMap.put("time", isdatefind);

      reference.child(commentid).setValue(hashMap);
      addNotification();
      addcomment.setText("");
  }
    }

//    private void addComments(){
//        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Comments").child(commentid);
//
//        String commentid = reference.push().getKey();
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("comment", addcomment.getText().toString());
//        hashMap.put("publisher", firebaseUser.getUid());
//        hashMap.put("commentid", commentid);
//        hashMap.put("time",isdatefind);
//
//        reference.child(commentid).setValue(hashMap);
//        addNotification();
//        addcomment.setText("");
//    }


    private void addNotification(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(publisherid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "commented: "+addcomment.getText().toString());
        hashMap.put("postid", postid);
        hashMap.put("commentid", commentid);
        hashMap.put("ispost", true);
        hashMap.put("time",isdatefind);
        reference.push().setValue(hashMap);
    }

    private void getImage(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // User user = dataSnapshot.getValue(User.class);
             ///   String username1 = dataSnapshot.child("username").getValue().toString();
                String imageUrl = dataSnapshot.child("image").getValue().toString();
//                assert user != null;
                if (imageUrl.equals("default")) {
                    image_profile.setImageResource(R.drawable.ic_user);
                } else {
                    Glide.with(getApplicationContext()).load(URLs.UserImageBaseUrl+imageUrl).into(image_profile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void readComments(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Comment comment = snapshot.getValue(Comment.class);
                    commentList.add(comment);
                }

                commentAdapter.notifyDataSetChanged();
                Collections.reverse(commentList);
              //  commentAdapter.notifyItemRangeChanged(1, commentAdapter.getItemCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

