package com.deificindia.indifun.fires.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.holders.LiveFollowFriendsHolder;
import com.deificindia.indifun.interfaces.LiveHotFirePagingListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LiveFollowFriendsFirePagingAdapter extends FirestoreRecyclerAdapter<BroadList, LiveFollowFriendsHolder> {

    Context context;
    LiveHotFirePagingListener _listener;


    public static DiffUtil.ItemCallback<BroadList> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<BroadList>() {
                @Override
                public boolean areItemsTheSame(@NonNull BroadList rank, @NonNull BroadList rankTwo) {
                    return rank.owneruid.equals(rankTwo.owneruid);
                }

                @Override
                public boolean areContentsTheSame(@NonNull BroadList rank, @NonNull BroadList rankTwo) {
                    return rank.state==rankTwo.state;
                }
            };


    public LiveFollowFriendsFirePagingAdapter(@NonNull FirestoreRecyclerOptions<BroadList> options,
                                              Context cnx, LiveHotFirePagingListener listener) {
        super(options);
        this.context = cnx;
        this._listener=listener;

    }


    @NonNull
    @Override
    public LiveFollowFriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_follow, parent, false);
        return new LiveFollowFriendsHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull LiveFollowFriendsHolder holder, int i, @NonNull BroadList data) {


        holder.follow_name.setText(data != null ? data.ownername : "NONE");

        if(data.owneravtar!=null) UiUtils.setAvtar(data.owneravtar, context, holder.follow_icon);

      /*  if(data.getFriends()!=null){
            holder.tagviewFollower.setTagImage(R.drawable.person);
            holder.tagviewFollower.changeBg(R.drawable.bg_name_gray);
            holder.tagviewFollower.getTagText().setText(data.getFriends());
        }
*/
        if(data.whatsupp!=null){
            holder.tvWhatsap.setText(data.whatsupp);
        }

        holder.follow_icon.setOnClickListener(v -> {

            if(data.roomid !=null) {
                //CallActivity.joinSingleLiveActivity(context, data);
                //ActivityUtils.joinBroad(context, data);
            }else
                Toast.makeText(context, "This broadcast could not be joined.", Toast.LENGTH_LONG).show();
        });
    }


    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        super.onError(e);
        if(_listener!=null) _listener.onStateChange(e);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }
}
