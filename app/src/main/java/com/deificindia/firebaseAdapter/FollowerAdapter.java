package com.deificindia.firebaseAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.deificindia.chat.Model.User;
import com.deificindia.firebaseModel.FollowerNotificaiton;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.http.Url;

import static android.content.Context.MODE_PRIVATE;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;

public class FollowerAdapter  extends RecyclerView.Adapter<FollowerAdapter.ImageViewHolder> {
    private FirebaseUser firebaseUser;
    private Context mContext;
    private List<FollowerNotificaiton> mNotification;
    private AlertDialog dialog;
    private Activity context;
    public FollowerAdapter(Context context, List<FollowerNotificaiton> followNotification) {
        mContext = context;
        mNotification = followNotification;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.followernotificaiton, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final FollowerNotificaiton followNotification = mNotification.get(position);

        holder.text.setText(followNotification.getText());
        String finTime = calculteTimeAgo(followNotification.getTime());
        holder.time.setText(finTime);


        getUserInfo(holder.image_profile, holder.username, followNotification.getUserid());
        isFollowing(followNotification.getUserid(), holder.followback);
        holder.followback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.followback.getText().toString().equals("follow")) {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("UnFollow").child(followNotification.getUserid()).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(followNotification.getUserid())
                            .child("followers").child(firebaseUser.getUid()).setValue(true);

//                    addNotification(user.getId());
                } else {

//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                            .child("UnFollow").child(followNotification.getUserid()).removeValue();
//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(followNotification.getUserid())
//                            .child("followers").child(firebaseUser.getUid()).removeValue();

               AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setCancelable(true);
                    builder.setMessage(R.string.sureexist);
                    builder.setNegativeButton("No" , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                                    .child("UnFollow").child(followNotification.getUserid()).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("Follow").child(followNotification.getUserid())
                                    .child("followers").child(firebaseUser.getUid()).removeValue();
                        }
                    });
                   AlertDialog dialog1 = builder.create();
                    dialog1.show();

                }



}




        });
//        if (.isRead()) {
//
//            holder.itemView.setBackgroundColor(Color.WHITE);
//            i++;
//            Log.d("value", String.valueOf(i));
//
//        } else {
//            holder.itemView.setBackgroundColor(Color.GRAY);
//        }

//        preferences.set(Constants.count_, String.valueOf(i));
//        preferences.commit();
//
//        String a = preferences.get(Constants.count_);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followNotification.isIspost()) {
//                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
//                    editor.putString("postid", followNotification.getPostid());
//                    editor.apply();
//
//
//                    Intent intent=new Intent (mContext, PostDetails.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);

                } else {
//                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
//                    editor.putString("profileid", followNotification.getUserid());
//                    editor.apply();
//                    Intent intent=new Intent (mContext, Profilenew.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);

                }

            }
        });

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followNotification.isIspost()) {
//                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
//                    editor.putString("postid", followNotification.getPostid());
//                    editor.apply();
//                    Intent intent=new Intent (mContext, PostDetails.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);

                } else {
//                    SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
//                    editor.putString("profileid", followNotification.getUserid());
//                    editor.apply();
//                    Intent intent=new Intent (mContext, Profilenew.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);

                }
            }
        });

//
//        if (followNotification.isIspost()) {
//            holder.post_image.setVisibility(View.VISIBLE);
//            getPostImage(holder.post_image, followNotification.getPostid());
//
//        } else {
//          //  holder.post_image.setVisibility(View.GONE);
//        }
    }



    @Override
    public int getItemCount() {
        Log.d("fghjk", String.valueOf(mNotification));
        return mNotification.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, post_image;
        public TextView username, text,time,commentid,followback;

        public ImageViewHolder(View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
           // post_image = itemView.findViewById(R.id.post_image);
            username = itemView.findViewById(R.id.username);
            text = itemView.findViewById(R.id.comment);
            time=itemView.findViewById(R.id.time);
            followback=itemView.findViewById(R.id.btn_followback);
        }
    }

    private void getUserInfo(final ImageView imageView, final TextView username, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("UserDetails").child(userid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                username.setText(user.getFull_name());
                Picasso.get().load(user.getImage()).placeholder(R.drawable.ic_user)
                        .into(imageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private String calculteTimeAgo(String dateago) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try{
//            long time=sdf.parse(dateago).getTime();
            long time=sdf.parse(String.valueOf(dateago)).getTime();
            long now=System.currentTimeMillis();
            CharSequence ago= DateUtils.getRelativeTimeSpanString(time,now,DateUtils.MINUTE_IN_MILLIS);
            return ago+"";
        }catch (ParseException e){
            e.printStackTrace();
        }
        return "";
    }
//    private void getPostImage(final ImageView post_image, String postid){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
//                .child("Posts").child(postid);
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Post post = dataSnapshot.getValue(Post.class);
//                assert post != null;
//                Glide.with(mContext).load(post.getPostimage()).into(post_image);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
    private void isFollowing(final String userid, final TextView button){


        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("UnFollow");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userid).exists()){
                    button.setText("UnFollow");
                    button.setTextColor(R.color.red);
                } else{
                    button.setText("follow");
                    button.setTextColor(R.color.green);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });    }

}