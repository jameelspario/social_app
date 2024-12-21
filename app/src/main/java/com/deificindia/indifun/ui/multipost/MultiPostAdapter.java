package com.deificindia.indifun.ui.multipost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.interfaces.OnItemClickListener;
import com.deificindia.indifun.ui.frames.SixteenByNineFrame;
import com.deificindia.indifun.ui.frames.SquareFrame;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MultiPostAdapter extends RecyclerView.Adapter<MultiPostAdapter.MultiPostHolder> {

    private static final int IMAGE_VIEW_ID = 1 << 4;

    List<String> items = new ArrayList<>();

    OnItemClickListener<String> listener;

    public MultiPostAdapter(List<String> items, OnItemClickListener<String> listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MultiPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_view_2, parent, false);
        return new MultiPostHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MultiPostHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class MultiPostHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public MultiPostHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_view);

        }

        void bind(String model){

            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.get()
                    .load(model)
                    .into(image);

            image.setOnClickListener(v->{
                if(listener!=null) listener.onItemClick(getBindingAdapterPosition(), model);
            });

        }
    }


}
