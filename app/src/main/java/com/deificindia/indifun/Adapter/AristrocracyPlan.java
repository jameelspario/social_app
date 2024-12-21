package com.deificindia.indifun.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.AristocracyPlan;
import com.google.firebase.database.collection.LLRBNode;

import java.text.MessageFormat;
import java.util.List;


public class AristrocracyPlan extends RecyclerView.Adapter<AristrocracyPlan.AristoHolder> {

    List<AristocracyPlan> aristoPrivileges;
    int position=0;

    OnItemSelected listener;
    public interface OnItemSelected{
        void onItemSelect(String aristocracy_center_id,String val,String month);
    }


    public AristrocracyPlan(List<AristocracyPlan> aristoPrivileges,OnItemSelected listener) {
        this.listener = listener;
        this.aristoPrivileges = aristoPrivileges;
    }

    @NonNull
    @Override
    public AristrocracyPlan.AristoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AristrocracyPlan.AristoHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_aristo_plan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AristrocracyPlan.AristoHolder holder, int position) {
        holder.bind(aristoPrivileges.get(position));

    }

    @Override
    public int getItemCount() {
        return aristoPrivileges.size();
    }

    class AristoHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView coin,amount,save,hot;
        CardView linearLayout;
        LinearLayout linearLayout1;
        RelativeLayout relatice;

        public AristoHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            amount = itemView.findViewById(R.id.text1);
            coin = itemView.findViewById(R.id.text2);
            save = itemView.findViewById(R.id.text3);
            hot = itemView.findViewById(R.id.hot);
            linearLayout = itemView.findViewById(R.id.cardView);
            linearLayout1 = itemView.findViewById(R.id.linearLayout);
            relatice = itemView.findViewById(R.id.relatice);
        }

        @SuppressLint({"ResourceAsColor", "SetTextI18n"})
        void bind(AristocracyPlan res){
//           if(res.getDurstion_month()!=null && res.getDurstion_month().isEmpty()){
               amount.setText(""+res.getDurstion_month() + " Month");
//           }

            coin.setText(res.getCoin());
            save.setText("Save"+res.getSave_discount()+"% ");
            if(res.getBest_offer()!=null && !res.getBest_offer().isEmpty()){
                hot.setText("Hot");
                if(res.getBest_offer().equals("1")){
                    hot.setVisibility(View.VISIBLE);
                }else{
                    hot.setVisibility(View.GONE);
                }
            }else{
                hot.setVisibility(View.GONE);
            }

            if(position==getAdapterPosition()){
                relatice.setBackgroundResource(R.drawable.item_outline);
                amount.setTextColor(R.color.red);
                coin.setTextColor(R.color.red);
                save.setTextColor(R.color.red);
            }else{
                relatice.setBackgroundResource(0);
                amount.setTextColor(R.color.black);
                coin.setTextColor(R.color.black);
                save.setTextColor(R.color.black);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   position=getAdapterPosition();
                   notifyDataSetChanged();

                    listener.onItemSelect(res.getAristocracy_center_id(),res.getCoin(), res.getDurstion_month());

                }
            });
        }
    }
}
