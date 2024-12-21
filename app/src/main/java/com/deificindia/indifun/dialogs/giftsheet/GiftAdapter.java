package com.deificindia.indifun.dialogs.giftsheet;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;

import com.deificindia.indifun.deificpk.utils.GiftCoverAnim;

import java.util.ArrayList;
import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<GiftInfo2> giftlist = new ArrayList<>();
    int mSelected = -1;

    int tabindex =1;
    int pageindex =1;
    Context context;

    String mValueFormat;

    public GiftAdapter(List<GiftInfo2> giftlist, int tabindex, int pageindex, Context cnx) {
        mValueFormat = cnx.getResources().getString(R.string.live_room_gift_action_sheet_value_format_2);
        this.giftlist = giftlist;
        this.tabindex = tabindex;
        this.pageindex = pageindex;
        this.context = cnx;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        GiftViewHolder giftViewHolder = (GiftViewHolder) holder;
        if(giftViewHolder.giftCoverAnim!=null)
            giftViewHolder.giftCoverAnim.removeViewFromTop();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GiftViewHolder(LayoutInflater.from(context).
                inflate(R.layout.action_gift_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GiftInfo2 info = giftlist.get(position);
        GiftViewHolder giftViewHolder = (GiftViewHolder) holder;

        giftViewHolder.name.setVisibility(info.name!=null? View.VISIBLE:View.GONE);
        if(info.name!=null) giftViewHolder.name.setText(info.name);

        giftViewHolder.value.setText(String.format(mValueFormat, info.point));
        giftViewHolder.setPosition(position);

        giftViewHolder.itemView.setActivated(mSelected == position);

       /* Picasso.get().load(URLs.GifgBaseUrl+info.getCover())
                .error(context.getResources().getDrawable(R.drawable.empty_gift))
                .into(giftViewHolder.icon);*/

        giftViewHolder.giftCoverAnim = GiftCoverAnim.instance().container(giftViewHolder.container)
                .file(info.cover)
                .play();
    }



    void resetIcon(View v){
        new Handler(Looper.myLooper()).postDelayed(()->{
            v.animate().scaleX(1.0f).scaleY(1.0f).start();
        }, 500);
    }

    @Override
    public int getItemCount() {
        return giftlist.size();
    }

    private class GiftViewHolder extends RecyclerView.ViewHolder {
        FrameLayout container;
        AppCompatTextView name;
        AppCompatTextView value;
        int position;
        GiftCoverAnim giftCoverAnim;


        GiftViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.live_room_action_sheet_gift_item_icon);
            name = itemView.findViewById(R.id.live_room_action_sheet_gift_item_name);
            value = itemView.findViewById(R.id.live_room_action_sheet_gift_item_value);

            itemView.setOnClickListener(view -> {
                mSelected = position;

                notifyDataSetChanged();
                GiftInfo2 gif2 = giftlist.get(position);
                //gif2.setSelected(true);
                GiftItemListener.trigger(position, gif2);

            });
        }

        void setPosition(int position) {
            this.position = position;
        }
    }


    public void updateList(ArrayList<GiftInfo2> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new GiftDiffCall(this.giftlist, newList));
        diffResult.dispatchUpdatesTo(this);
    }


}
