package com.deificindia.indifun.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.ui.TagView;

public class LiveHotHolder extends RecyclerView.ViewHolder{
    public AppCompatImageView imgicon;
    public TextView hotusername, tvwhatsup,tqp ;
    public TagView  hotfriends;
    public LinearLayout tagviewGender, live_type_item;
    public View itemView;

    public LiveHotHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView.findViewById(R.id.item_hot_adapter_parent);
        //this.itemView.setTransitionName(itemView.getContext().getString(R.string.trans_live_activity_anim));
        imgicon = itemView.findViewById(R.id.hotimage);
        hotusername = itemView.findViewById((R.id.hotusername));
        tvwhatsup = itemView.findViewById((R.id.tvwhatsup));
        tqp = itemView.findViewById((R.id.tvwhatsupone));

        hotfriends = itemView.findViewById((R.id.tagviewFollower));
        hotfriends.init();

        tagviewGender = itemView.findViewById((R.id.tagviewGender));
        live_type_item = itemView.findViewById((R.id.live_type_item));




    }

}
