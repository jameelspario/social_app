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
import com.deificindia.indifun.holders.LiveFollowRecomendedHolder;
import com.deificindia.indifun.interfaces.LiveHotFirePagingListener;
import com.deificindia.indifun.rest.RestWork;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class LiveFollowRecomendedFirePagingAdapter extends FirestoreRecyclerAdapter<BroadList, LiveFollowRecomendedHolder> {

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

    public LiveFollowRecomendedFirePagingAdapter(@NonNull FirestoreRecyclerOptions<BroadList> options,
                                                 Context cnx, LiveHotFirePagingListener listener) {
        super(options);
        this.context = cnx;
        this._listener=listener;
    }

    @NonNull
    @Override
    public LiveFollowRecomendedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_follow_recomended, parent, false);
        return new LiveFollowRecomendedHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull LiveFollowRecomendedHolder holder, int i, @NonNull BroadList data) {
        holder.follow_name.setText(data.ownername);

        if (data.owneravtar != null)
            UiUtils.setAvtar(data.owneravtar, context, holder.avtar);

        holder.avtar.setOnClickListener(v -> {
            if (data.roomid != null) {
               // ActivityUtils.joinBroad(context, data);
            } else {
                Toast.makeText(context, "This broadcast could not be joined.", Toast.LENGTH_LONG).show();
            }
        });

        holder.follow_button1.setOnClickListener(v ->{
            RestWork.FollowApi(holder.follow_button1, data.owneruidweb);
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
