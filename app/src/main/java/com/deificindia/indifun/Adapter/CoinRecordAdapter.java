package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.abs_plugs.CoinRecordResult;
import com.deificindia.indifun.R;

import java.util.List;

public class CoinRecordAdapter extends RecyclerView.Adapter<CoinRecordAdapter.HolderClass> {
    List<CoinRecordResult> lits;
    Context context;

    public CoinRecordAdapter(List<CoinRecordResult> lits, Context context) {
        this.lits = lits;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderClass(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_record, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HolderClass holder, int position) {
        holder.bind(lits.get(position));

    }

    @Override
    public int getItemCount() { return lits.size(); }

    static class HolderClass extends RecyclerView.ViewHolder{

        TextView tvName, tvdatetime,costamount,totalcount,transction;

        public HolderClass(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTitle);
            totalcount = itemView.findViewById(R.id.coin_count);
            costamount = itemView.findViewById(R.id.cost_amount);
            transction = itemView.findViewById(R.id.transcationid);
            tvdatetime = itemView.findViewById(R.id.tvdatetime);
        }

        void bind(CoinRecordResult result){
            tvName.setText(result.getPaidFor());
            transction.setText(result.getTransectionId());
            totalcount.setText(result.getCoin_count());
            costamount.setText(result.getPaidAmount());
            tvdatetime.setText(result.getEntryDate() + "  " + result.getTime());
        }
    }
}
