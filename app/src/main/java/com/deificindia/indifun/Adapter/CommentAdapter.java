package com.deificindia.indifun.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.PostCommentList_Result;
import com.deificindia.indifun.Model.PostLikeList_Result;
import com.deificindia.indifun.Model.retro.Commentmodel_Result;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final List<PostCommentList_Result> postCommentList_results;
    private Context context;

    public CommentAdapter(List<PostCommentList_Result> postCommentList_results, Context context) {
        this.postCommentList_results = postCommentList_results;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_adapter_layout, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        PostCommentList_Result data = postCommentList_results.get(position);
        if (data.getProfile_picture()!= null && !data.getProfile_picture().isEmpty()) {
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+data.getProfile_picture())
                    .error(R.drawable.no_image)
                    .into(holder.whocommentimage);
        }

        if(data.getFull_name()!=null && !data.getFull_name().isEmpty()){
            holder.commentname.setText(data.getFull_name());
        }

        if (data.getComments()!=null && !data.getComments().isEmpty()){
            holder.maincomment.setText(data.getComments());
        }

        if(data.getTime()>0){
            holder.comenttime.setText(Logger.dateFormat(data.getTime()));
        }

    }

    @Override
    public int getItemCount() {
        return postCommentList_results.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        CircleImageView whocommentimage;
        TextView commentname, maincomment, comenttime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            whocommentimage = itemView.findViewById(R.id.whocommentimage);
            commentname = itemView.findViewById(R.id.commentname);
            maincomment = itemView.findViewById(R.id.maincomment);
            comenttime = itemView.findViewById(R.id.comenttime);
        }
    }
}
