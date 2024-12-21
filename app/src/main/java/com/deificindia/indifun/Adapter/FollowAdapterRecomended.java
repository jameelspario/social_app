package com.deificindia.indifun.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.retro.LiveResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.db.LiveEntity;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.deificpk.modals.Clip;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.Const;
import com.deificindia.indifun.holders.LiveFollowRecomendedHolder;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.RestWork;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_CALL_MODE;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_PK_MODE;

public class FollowAdapterRecomended extends RecyclerView.Adapter<LiveFollowRecomendedHolder> {
    private Activity context;
    private List<LiveResult> list = new ArrayList<>();
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
FirebaseUser firebaseUser;
    String broadusername,broadid,broadkey;
    String roomid;
    public FollowAdapterRecomended(Activity activity){
        this.context = activity;
    }
    @Override
    public LiveFollowRecomendedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_follow_recomended, parent, false);
        return new LiveFollowRecomendedHolder(view);
    }
    public void update(List<LiveResult> hotmodel){
        list=hotmodel;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final LiveFollowRecomendedHolder holder, final int pos) {
//        if(type.equals("bfollow")){
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        LiveResult data = list.get(pos);

        holder.follow_name.setText(data.getUser_name());

        if (data.getImage() != null && !data.getImage().isEmpty()) {
               Picasso.get()
                    .load(URLs.UserImageBaseUrl+data.getImage())
                    .error(R.drawable.no_image)
                    .into(holder.avtar);
        }

        holder.avtar.setOnClickListener(v -> {

         //   ActivityUtils.joinBroad1(data, Const.KEY_TAB_RECOMENDED, holder.itemView);

        });





        switch ((int)data.state){
            case STAT_CALL_MODE:
                holder.live_type_item.setBackgroundResource(R.drawable.bg_live_1);
                break;
            case STAT_PK_MODE:
                holder.live_type_item.setBackgroundResource(R.drawable.bg_live_2);
                break;
            default:
                holder.live_type_item.setBackgroundResource(0);
        }

    }

    @Override
    public int getItemCount() {

        return list.size();
    }



}
