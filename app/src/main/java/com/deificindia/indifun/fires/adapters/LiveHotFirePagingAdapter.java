package com.deificindia.indifun.fires.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.holders.LiveHotHolder;
import com.deificindia.indifun.interfaces.LiveHotFirePagingListener;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LiveHotFirePagingAdapter extends FirestoreRecyclerAdapter<BroadList, LiveHotHolder> {

    Context context;
    LiveHotFirePagingListener _listener;

    public LiveHotFirePagingAdapter(@NonNull FirestoreRecyclerOptions<BroadList> options,
                                    Context context, LiveHotFirePagingListener listener) {
        super(options);
        this.context=context;
        this._listener=listener;
    }


    @Override
    protected void onBindViewHolder(@NonNull LiveHotHolder holder, int position, @NonNull BroadList data) {

        holder.hotusername.setText(data.ownername);

        if(data.owneravtar!=null) UiUtils.setAvtar(data.owneravtar, this.context, holder.imgicon);

        if(data.friends!=null){
            holder.hotfriends.setTagImage(R.drawable.person);
            holder.hotfriends.changeBg(R.drawable.bg_name_gray);
            holder.hotfriends.getTagText().setText(data.friends);
        }else holder.hotfriends.setVisibility(View.GONE);

        if(data.ogender!=null){
           /* holder.tagviewGender.setVisibility(View.VISIBLE);
            UiUtils.setGenderTag(holder.tagviewGender, data.ogender);
            holder.tagviewGender.getTagText().setText(data.oage);*/

        }else {
            holder.tagviewGender.setVisibility(View.GONE);
        }

        if(data.roomname!=null){
            holder.tvwhatsup.setText(data.roomname);
        }

        holder.itemView.setOnClickListener(v -> {
            //Toast.makeText(context, hotmodelList.get(getAdapterPosition()).getUser_name() + "was clicked", Toast.LENGTH_LONG).show();
            /*if(data.getAdd_broadcast_id() !=null && data.getAdd_broadcast_title() !=null)
                CallActivity.joinSingleLiveActivity(context, data);
            else
                Toast.makeText(context, "This broadcast could not be joined.", Toast.LENGTH_LONG).show();*/
           // ActivityUtils.joinBroad(context, data);
        });
    }

    @NonNull
    @Override
    public LiveHotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LiveHotHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_adapter_layout, parent, false));
    }

   /* @Override
    protected void onError(@NonNull Exception e) {
        super.onError(e);
        loge("LiveHotFirePagingAdapter", e.getMessage());
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        if(_listener!=null) _listener.onStateChange(state);
    }*/

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
