package com.deificindia.chat.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.deificindia.chat.Model.Chat;
import com.deificindia.indifun.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;
    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl){
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());
        String timeAgo=calculteTimeAgo(chat.getTime());
        holder.get_time.setText(timeAgo);
        if(timeAgo.equals("0 minutes ago")){
            holder. get_time.setText("Just now");
        }else{
            holder. get_time.setText(timeAgo);
        }
        Glide.with(mContext).load(chat.getImage()).into(holder.messageImage);
        holder.messageImage.setVisibility(View.VISIBLE);
//        Log.d("findim",chat.getImageurl());
     //   Toast.makeText(mContext, ""+chat.getImage(), Toast.LENGTH_SHORT).show();

        holder.messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(mContext, MainActivity2.class);
//                intent.putExtra("image_url",chat.getImage());
//                mContext.startActivity(intent);
            }
        });

        if (imageurl.equals("default")){
            holder.profile_image.setImageResource(R.drawable.ic_user);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }
        if (chat.getImage()==null){
            holder.messageImage.setVisibility(View.GONE);
            holder.show_message.setVisibility(View.VISIBLE);
            holder.get_time.setVisibility(View.VISIBLE);

        }else{
            holder.messageImage.setVisibility(View.VISIBLE);
            holder.show_message.setVisibility(View.GONE);
            holder.get_time.setVisibility(View.VISIBLE);
        }

        if (position == mChat.size()-1){
            if (chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            } else {
                holder.txt_seen.setText("Delivered");
            }
        } else {
            holder.txt_seen.setVisibility(View.GONE);
        }

    }



    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen,get_time;
        public AppCompatImageView messageImage;
        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            get_time=itemView.findViewById(R.id.gettime);
            messageImage = itemView.findViewById(R.id.message_image_layout);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
    private String calculteTimeAgo(String dateago) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try{
//            long time=sdf.parse(dateago).getTime();
//            long when=sdf.parse(String.valueOf(dateago)).getTime();
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