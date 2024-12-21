package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.abs_plugs.ProfileGalleryModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.diffs.ProfileGalleryDiff;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileGalleryAdapter extends RecyclerView.Adapter<ProfileGalleryAdapter.MyHolder> {

    Context context;
    List<ProfileGalleryModal> list = new ArrayList<>();


    public ProfileGalleryAdapter(Context context, List<ProfileGalleryModal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_profile_gallery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class MyHolder extends RecyclerView.ViewHolder{

         ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.userimage1);

            itemView.setOnClickListener(v->{
                if(listenr!=null){
                    listenr.onItemSelect(getAdapterPosition(), list.get(getLayoutPosition()), imageView);
                }
            });
        }

        void bind(ProfileGalleryModal modal){
            if(modal.id > 0){

                Picasso.get()
                        .load(URLs.UserImageBaseUrl + modal.image)
                        .error(R.drawable.plus_1)
                        .into(imageView);
            }

        }
    }


    public void updateList(List<ProfileGalleryModal> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProfileGalleryDiff(this.list, newList));
        diffResult.dispatchUpdatesTo(this);
    }


    private OnItemSelect listenr;
    public interface OnItemSelect{
        void onItemSelect(int pos, ProfileGalleryModal modal, ImageView imageView);
    }

    public void setOnItemSelect(OnItemSelect listenr){
        this.listenr = listenr;
    }
}
