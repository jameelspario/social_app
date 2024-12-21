package com.deificindia.indifun.Adapter;

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

import com.bumptech.glide.Glide;
import com.deificindia.indifun.Model.Likemodel_Result;
import com.deificindia.indifun.Model.PostCommentList_Result;
import com.deificindia.indifun.Model.PostLikeList_Result;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {

    private final List<PostLikeList_Result> postLikeList_results;
    private Context context;

    public LikeAdapter(List<PostLikeList_Result> postLikeList_results, Context context) {
        this.postLikeList_results = postLikeList_results;
        this.context = context;
    }


    @NonNull
    @Override
    public LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.like_adapter_layout, parent, false);
        return new LikeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(LikeViewHolder holder, int position) {
        PostLikeList_Result data = postLikeList_results.get(position);
        if (data.getProfile_picture()!= null && !data.getProfile_picture().isEmpty()) {
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+data.getProfile_picture())
                    .error(R.drawable.no_image)
                    .into(holder.oupic);
//            Log.d("vbnm",data.getProfile_picture());

        }
    }

    @Override
    public int getItemCount() {
        return postLikeList_results.size();
    }

    public static  class LikeViewHolder extends RecyclerView.ViewHolder {
        CircleImageView oupic;

        public LikeViewHolder(@NonNull View itemView) {
            super(itemView);
            oupic = itemView.findViewById(R.id.oupic);
        }
    }
}
