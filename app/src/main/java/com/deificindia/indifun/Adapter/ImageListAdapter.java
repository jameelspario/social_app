package com.deificindia.indifun.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.indifun.Activities.FullSizeImageActivity;
import com.deificindia.indifun.Model.MyImage;
import com.deificindia.indifun.Model.posts.Hotpostmodel;
import com.deificindia.indifun.Model.ImagesParsble;
import com.deificindia.indifun.R;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MyImage> images; //for post image list
    ArrayList<Image> images2; //for local image list
    Context context;
    int typelist = 0;


    private OnRemoveItem onRemoveItem;
    public interface OnRemoveItem{
        void onRemove(int pos);
    }

    public ImageListAdapter(Context cnx, List<MyImage> images, int typelist) {
        this.context = cnx;
        this.images = images;
        this.typelist = typelist;
    }

    public ImageListAdapter(ArrayList<Image> choosenImages, Context context, int typelist, OnRemoveItem listener) {
        this.images2 = choosenImages;
        this.context = context;
        this.typelist = typelist;
        this.onRemoveItem = listener;
    }

    public void update(ArrayList<Image> choosenImages){
        this.images2 = choosenImages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return typelist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
            return new HolderClassTypeOne(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_view_post_one_item, parent, false));
        }else {
            return new HolderClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==1){
            ((HolderClassTypeOne)holder).bind(images2.get(position));
        }else {
            ((HolderClass)holder).bind(images.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(typelist==1){
            return images2.size();
        }else {
            return images.size();
        }
    }

    class HolderClass extends RecyclerView.ViewHolder{

        ImageView imageView;

        public HolderClass(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.myimage);

            itemView.setOnClickListener(v -> {
                if(context!=null) {
                    Intent intent = new Intent(context, FullSizeImageActivity.class);
                    ImagesParsble parsble = new ImagesParsble();
                    if (typelist == 0) {
                        List<String> arr = new ArrayList<>();
                        for (MyImage img : images) {
                            arr.add(img.getPostImage());
                        }
                        parsble.setImages(arr);
                        intent.putExtra("URL", parsble);
                    } else {
                        intent.putParcelableArrayListExtra("URLLOCAL", images2);
                    }

                    intent.putExtra("TAB", getAdapterPosition());

                    ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                            imageView, ViewCompat.getTransitionName(imageView)
                    );

                    context.startActivity(intent, option.toBundle());
                }
            });
        }

        void bind(MyImage model){
            if(model.getImage()!=null)
                Picasso.get().load(model.getPostImage()).into(imageView);
        }

    }

    class HolderClassTypeOne extends RecyclerView.ViewHolder{

        ImageView imageView, imgClose;

        public HolderClassTypeOne(@NonNull View itemView) {
            super(itemView);

            imgClose = itemView.findViewById(R.id.myimageClose);
            imageView = itemView.findViewById(R.id.myimage);
        }

        void bind(Image model){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Glide.with(context)
                        .load(model.getUri())
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(model.getPath())
                        .into(imageView);
            }

            imgClose.setOnClickListener(v->{
                if(onRemoveItem!=null) onRemoveItem.onRemove(getAdapterPosition());
               /* images2.remove(getAdapterPosition());
                notifyDataSetChanged();*/
            });

            imageView.setOnClickListener(v -> {
                if(context!=null) {
                    Intent intent = new Intent(context, FullSizeImageActivity.class);
                    intent.putParcelableArrayListExtra("URLLOCAL", images2);
                    intent.putExtra("TAB", getAdapterPosition());

                    ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                            imageView, ViewCompat.getTransitionName(imageView)
                    );

                    context.startActivity(intent, option.toBundle());
                }
            });
        }
    }



}
