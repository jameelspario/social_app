package com.deificindia.indifun.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Activities.ChatActivity;
import com.deificindia.indifun.Model.retro.ChatModel_Result;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.ActivitiesUtils;
import com.deificindia.indifun.Utility.URLs;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<ChatModel_Result> chatmodelList;
    private Context context;
    public String agotime;

    public ChatAdapter(List<ChatModel_Result> chatmodelList, Context context) {
        this.chatmodelList = chatmodelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_adapter_layout, parent, false);
        return new ChatViewHolder(view);

    }

    public String setAgoTime(String date, String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date past = format.parse(date+'T'+time);
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes= TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours= TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days= TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<60){
                agotime = (seconds + " seconds");
            }
            else if(minutes<60){
                agotime = (minutes + " minutes");
            }
            else if(hours<24){
                agotime = (hours + " hours");
            }
            else {
                agotime = (days + " days");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("time",e.getMessage());
        }
        return agotime + " ago";
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        final ChatModel_Result nList = chatmodelList.get(position);
        holder.senderName.setText(nList.getUsername_from());

        holder.msgTiming.setText(setAgoTime(nList.getDate(), nList.getTime()));

        if(!nList.getImage_from().isEmpty())
            Picasso.get().load(URLs.UserImageBaseUrl+nList.getImage_from()).into(holder.senderDP);

        holder.itemView.setOnClickListener(v -> {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) context,
                    holder.senderName,
                    ViewCompat.getTransitionName(holder.senderName));

            ActivitiesUtils.open_chat(context, options,
                    nList.getId(), nList.getUser_from(), nList.getUsername_from());

        });
    }

//    }

    @Override
    public int getItemCount() {
        return chatmodelList.size();
    }

    public static  class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView senderName, msg, msgTiming;
        ImageView senderDP;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.senderName);
            msg = itemView.findViewById(R.id.msg);
            msgTiming = itemView.findViewById(R.id.msgTiming);
            senderDP = itemView.findViewById(R.id.SenderDP);
        }
    }
}

