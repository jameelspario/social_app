package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Activities.GiftPostLayout;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.bindingmodals.otheruserprofile.Gift;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GiftPostAdapter extends RecyclerView.Adapter<GiftPostAdapter.ViewHolder>{

    Context context;
    ArrayList<Gift> gifts;

    public GiftPostAdapter(Context context, ArrayList<Gift> gifts) {
        this.context = context;
        this.gifts = gifts;
    }

    @NonNull
    @NotNull
    @Override
    public GiftPostAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gift_coinpost, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GiftPostAdapter.ViewHolder holder, int position) {

     Gift gidd=  gifts.get(position);
        Picasso.get().load(URLs.GifgBaseUrl+gidd.getCover()).into(holder.imageView);
        holder.TextCount.setText(String.valueOf(gidd.count>99?"99+":gidd.count));
//        holder.textViewGift.setText();
        holder.textViewGift.setVisibility(View.GONE);
    }



    @Override
    public int getItemCount() {
        return gifts==null?0:gifts.size();
    }

    public void setClickListener(GiftPostLayout giftPostLayout) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewGift,TextCount;
        private ImageView imageView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageget);
            textViewGift=itemView.findViewById(R.id.text_gift);
            TextCount=itemView.findViewById(R.id.textcount);
        }
    }
}
