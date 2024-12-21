package com.deificindia.indifun.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.deificindia.indifun.holders.LiveFollowFriendsHolder;
import com.deificindia.indifun.modals.Result;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_CALL_MODE;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_PK_MODE;

public class FollowAdapter extends RecyclerView.Adapter<LiveFollowFriendsHolder> {

    private Activity context;
    private List<LiveResult> list = new ArrayList<>();

    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    FirebaseUser firebaseUser;
    public FollowAdapter(Activity activity){
        this.context = activity;
    }

    @Override
    public LiveFollowFriendsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_follow, parent, false);
        return new LiveFollowFriendsHolder(view);
    }

    public void update(List<LiveResult> hotmodel){
        this.list=hotmodel;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final LiveFollowFriendsHolder holder, final int pos) {
//        if(type.equals("bfollow")){
        LiveResult data = list.get(pos);
        holder.follow_name.setText(data.getUser_name());
      //  loge("sddf", String.valueOf(data.broadid));
//        broadusername=data.;
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        if (data.getImage() != null && !data.getImage().isEmpty()) {
            //String avtar = (data.owneravtartype == 0 ? URLs.UserImageBaseUrl : URLs.UserImageBaseUrlTemp) + data.owneravtar;
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+data.getImage())
                    .error(R.drawable.no_image)
                    .into(holder.follow_icon);
        }

//        if(data.no_of_friends>0){
//            holder.tagviewFollower.setTagImage(R.drawable.person);
//            holder.tagviewFollower.changeBg(R.drawable.bg_name_gray);
//            holder.tagviewFollower.getTagText().setText(""+data.no_of_friends);
//        }
//
//        if(data.roomname!=null){
//            holder.tvWhatsap.setText(data.roomname);
//        }



        holder.follow_icon.setOnClickListener(v -> {
            /*if (data.broadid>0 && data.is_blocked < 1){
                Clip clp = new Clip();

                clp.id = data.id;
                clp.isowner = false;
                clp.n = 2;
                clp.ownerfuid = data.fuid;
                clp.roomid = data.roomid;
                clp.owneravtar = data.image;///data.owneravtartype==0?res.getProfilePicture():data.image;
                clp.avtartype = data.owneravtartype;
                clp.ownername = data.full_name;
                clp.is_following = data.is_following;

                clp.is_kick = data.is_kick;
                clp.is_blocked = data.is_blocked;
                clp.is_mute = data.is_mute;

                clp.broad_join_identity = Constants.roomIdentity(false, data.fuid);


                ActivityUtils.joinBroad1(context, clp, holder.itemView);
                //Toast.makeText(context, "firebase id"+data.fuid, Toast.LENGTH_SHORT).show();
                //Log.e("onBindViewHolder: ", String.valueOf(data.broadid));
            }else{
                Toast.makeText(context, "This broadcast could not be joined.", Toast.LENGTH_LONG).show();
            }*/

         //   ActivityUtils.joinBroad1(data, Const.KEY_TAB_FRIEND, holder.itemView);
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
