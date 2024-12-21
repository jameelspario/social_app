package com.deificindia.indifun.dialogs.giftsheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;

import java.util.ArrayList;
import java.util.List;

public class ChildPageAdapter extends RecyclerView.Adapter<ChildPageAdapter.ChildPageHolder>{
    public List<List<GiftInfo2>> listinlist = new ArrayList<>();
    private int category;
    RecyclerView.RecycledViewPool recycledViewPool2 = new RecyclerView.RecycledViewPool();
    Context context;
    private String valFormatter;

    public ChildPageAdapter(int category, Context context) {
        valFormatter = context.getResources().getString(R.string.live_room_gift_action_sheet_value_format_2);
        this.category = category;
        this.context = context;
    }

    public void updateItems(List<List<GiftInfo2>> listin){
        this.listinlist = listin;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChildPageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChildPageHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gift_category_child, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ChildPageHolder holder, int position) {
        holder.bind(listinlist.get(position));
    }

    @Override
    public int getItemCount() { return listinlist.size(); }

    class ChildPageHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerVie_giftItem;

        public ChildPageHolder(@NonNull View itemView) {
            super(itemView);

            recyclerVie_giftItem =  itemView.findViewById(R.id.recyclerVie_giftItem);

        }

        void bind(List<GiftInfo2> item){
            recyclerVie_giftItem.setLayoutManager(new GridLayoutManager(context, 4));
            recyclerVie_giftItem.addItemDecoration(new EqualSpacingItemDecoration(100));
            recyclerVie_giftItem.setRecycledViewPool(recycledViewPool2);
            recyclerVie_giftItem.setHasFixedSize(true);

            //GiftAdapter giftAdapter = new GiftAdapter(valFormatter, item, category, context);
            //recyclerVie_giftItem.setAdapter(giftAdapter);
        }
    }
}
