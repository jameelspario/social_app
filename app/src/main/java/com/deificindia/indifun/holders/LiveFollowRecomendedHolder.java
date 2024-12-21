package com.deificindia.indifun.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;

public class LiveFollowRecomendedHolder  extends RecyclerView.ViewHolder {
    public AppCompatImageView avtar;
    public TextView follow_name;
    public AppCompatButton follow_button1;
    public View itemView;
    public LinearLayout live_type_item;

    public LiveFollowRecomendedHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.itemView.setTransitionName(itemView.getContext().getString(R.string.trans_live_activity_anim));
        avtar = itemView.findViewById(R.id.avtar);
        follow_name = itemView.findViewById(R.id.follow_name);
        follow_button1 = itemView.findViewById(R.id.btn_id);
        live_type_item = itemView.findViewById((R.id.live_type_item2));


    }


}
