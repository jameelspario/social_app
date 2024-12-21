package com.deificindia.indifun.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.indifun.Activities.FullSizeImageActivity;
import com.deificindia.indifun.Model.ImagesParsble;
import com.deificindia.indifun.Model.MyImage;
import com.deificindia.indifun.R;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostImageListAdapter extends RecyclerView.Adapter<PostImageListAdapter.PostImageListHolder> {

    public void update(ArrayList<Image> choosenImages) {
        this.items = choosenImages;
        notifyDataSetChanged();
    }

    public interface OnItemRemovedListener{
        void onItemRemoved(int pos);
    }

    OnItemRemovedListener removedListener;

    ArrayList<Image> items; //for local image list

    public PostImageListAdapter(ArrayList<Image> items, OnItemRemovedListener removedListener) {
        this.removedListener = removedListener;
        this.items = items;
    }

    @NonNull
    @Override
    public PostImageListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostImageListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_view_post_def_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostImageListHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PostImageListHolder extends RecyclerView.ViewHolder{
        ImageView imgClose, imgImage;

        public PostImageListHolder(@NonNull View itemView) {
            super(itemView);

            imgImage = itemView.findViewById(R.id.myimage);
            imgClose = itemView.findViewById(R.id.myimageClose);

        }

        void bind(Image model){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Glide.with(imgImage)
                        .load(model.getUri())
                        .into(imgImage);
            } else {
                Glide.with(imgImage)
                        .load(model.getPath())
                        .into(imgImage);
            }

            imgClose.setOnClickListener(v->{
                try {
                    items.remove(getBindingAdapterPosition());
                    notifyItemRemoved(getBindingAdapterPosition());
                    if (removedListener != null) {
                        removedListener.onItemRemoved(getBindingAdapterPosition());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });

            imgImage.setOnClickListener(v->{
                    Intent intent = new Intent(v.getContext(), FullSizeImageActivity.class);
                    intent.putParcelableArrayListExtra("URLLOCAL", items);
                    intent.putExtra("TAB", getAdapterPosition());

                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(),
                        v, ViewCompat.getTransitionName(v)
                );

                v.getContext().startActivity(intent, option.toBundle());

            });
        }

    }
}
