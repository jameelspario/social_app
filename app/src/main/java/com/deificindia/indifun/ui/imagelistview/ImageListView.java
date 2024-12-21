package com.deificindia.indifun.ui.imagelistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.Utility.api;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;
import static org.greenrobot.eventbus.EventBus.TAG;


public class ImageListView extends RelativeLayout {

    ImageViewAdapter adapter;
    TextView tvtitle;
    RecyclerView recyclerView;

    int type_image = 0;
    private static final int TEXT_COLOR = Color.argb(100, 11, 193, 170);


    public void updateData(List<MyImageModal> images)
    { this.adapter.updateImages(images); }

    public void clearData(){ this.recyclerView.setAdapter(null); }

    public void setCount(String heading, String count){
        String text = heading + "  " + count;
        SpannableString messageSpan = new SpannableString(text);
        messageSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, heading.length() + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        messageSpan.setSpan(new ForegroundColorSpan(TEXT_COLOR), heading.length() + 2, messageSpan.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvtitle.setVisibility(VISIBLE);
        tvtitle.setText(messageSpan);
    }

    public ImageListView(@NonNull Context context) {
        super(context);
    }

    public ImageListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int barelink=0;
    public void init(int typ, int barelink){
        this.barelink = barelink;
        init(typ);
    }
    public void init(int typ){
        this.type_image=typ;

        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_image_list_view, this, true);
        tvtitle = v.findViewById(R.id.tvtitle);
        recyclerView = v.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(4));

        this.adapter = new ImageViewAdapter();
        recyclerView.setAdapter(this.adapter);

    }

    class ImageViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<MyImageModal> images = new ArrayList<>();

        public ImageViewAdapter() { }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ImageViewHolderCircular(
                            LayoutInflater.from(getContext()).inflate(R.layout.item_circular_image, parent, false)
                    );


        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyImageModal modal = images.get(position);

            ((ImageViewHolderCircular)holder).bind(modal);

        }

        @Override
        public int getItemCount() { return images.size(); }


        public void updateImages(List<MyImageModal> images){
            this.images=images;
            notifyDataSetChanged();
        }
    }

    class ImageViewHolderCircular extends RecyclerView.ViewHolder{

        AppCompatImageView circleImageView;
        public ImageViewHolderCircular(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.img1);
        }
        void bind(MyImageModal img){
            circleImageView.setVisibility(VISIBLE);
            if (barelink==0)
            bindImage(circleImageView, img.imageUrl());
            else
                bindImage(circleImageView, img.image_link);
        }

    }


    void bindImage(ImageView iv, String link){
        System.out.println(link);
        if(type_image==1){
            UiUtils.setAvtarRounded(link, getContext(), iv, R.drawable.no_image);
        }else {
            Picasso.get().load(link)
                    .error(R.drawable.no_image)
                    .into(iv);

        }


    }



}
