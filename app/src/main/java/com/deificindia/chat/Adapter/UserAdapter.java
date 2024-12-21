package com.deificindia.chat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.chat.MessageActivity;
import com.deificindia.chat.Model.Chat;
import com.deificindia.chat.Model.User;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final Context mContext;
    private final List<User> mUsers;

    private boolean ischat;

    String theLastMessage;

    public UserAdapter(Context mContext, List<User> mUsers,boolean ischat){
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat=ischat;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = mUsers.get(position);
        holder.username.setText(user.getUsername());
//        String timeAgo=calculteTimeAgo(user.getTime());
//        if(timeAgo.equals("0 minutes ago")){
//            holder.texttime.setText("Just now");
//        }else{
//            holder.texttime.setText(timeAgo);
//        }

//        if (user.getImageURL().equals("default")){
//            holder.profile_image.setImageResource(R.drawable.ic_user);
//        } else {
//            Glide.with(mContext).load(URLs.UserImageBaseUrl+user.getImageURL()).into(holder.profile_image);

//        }

        Picasso.get().load(URLs.UserImageBaseUrl+user.getImageURL()).placeholder(R.drawable.avatar)
                .into(holder.profile_image);
        if (ischat){
            lastMessage(user.getId(), holder.last_msg);
            holder.last_msg.setVisibility(View.VISIBLE);
        } else {
            holder.last_msg.setVisibility(View.GONE);
        }

        if (ischat){
            if (user.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        } else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra("userid", user.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        //Collections.sort(mUsers, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(mUsers, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });
        return mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;
        private final ImageView img_on;
        private final ImageView img_off;
        private final TextView last_msg;
public  TextView texttime;
        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            last_msg = itemView.findViewById(R.id.last_msg);
          //  texttime=itemView.findViewById(R.id.findtime1);
        }
    }

    //check for last message
    private void lastMessage(final String userid, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        Chat chat = snapshot.getValue(Chat.class);
                        if (firebaseUser != null && chat != null) {
                            if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                    chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                                theLastMessage = chat.getMessage();
                                Log.d("last",theLastMessage);
                            }
                        }
                    }catch (Exception ignored){

                    }
                }

                if (theLastMessage.equals("default")) {
                    last_msg.setText("No Message");
                    Log.d("last11",theLastMessage);

                } else {
                    last_msg.setText(theLastMessage);
                    Log.d("last",theLastMessage);
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
}
