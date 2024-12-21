package com.deificindia.indifun.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.ui.TagView;

public class LiveFollowFriendsHolder extends RecyclerView.ViewHolder {

    public AppCompatImageView follow_icon;
    public TextView follow_name, tvWhatsap;
    public TagView tagviewFollower;
    public View itemView;
    public LinearLayout live_type_item;

    public LiveFollowFriendsHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.itemView.setTransitionName(itemView.getContext().getString(R.string.trans_live_activity_anim));
        follow_icon = itemView.findViewById(R.id.follow_icon);
        follow_name = itemView.findViewById(R.id.follow_name);
        tvWhatsap = itemView.findViewById(R.id.tvwhatsup);
        tagviewFollower = itemView.findViewById(R.id.tagviewFollower);
        tagviewFollower.init();

        live_type_item = itemView.findViewById((R.id.live_type_item3));

    }


}
