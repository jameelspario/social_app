package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.widgets.LiveRoomUserLayout;
import com.deificindia.indifun.interfaces.Firelistener;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;

public class LiveUserListAdapter extends RecyclerView.Adapter<LiveUserListAdapter.Holder> {

    public static final String TAG = "LiveUserListAdapter";
    List<RoomUserInfo> rankUsers = new ArrayList<>();
    Context context;

    public LiveUserListAdapter(Context context, List<RoomUserInfo> rankUsers) {
        this.context = context;
    }

    public LiveUserListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_live_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(rankUsers.get(position));
//        RoomUserInfo rom=new RoomUserInfo();
//        Picasso.get().load(rom.getAvtar())
//                //Picasso.get().load("https://saajcraftarena.com/image/sofa.jpg")
//                .placeholder(R.drawable.img_user_default)
//                .error(R.drawable.img_user_default)
//                .into(holder.circleImageView);
//
//        Timber.tag("findimage").d(rom.getAvtar());
    }

    @Override
    public int getItemCount() {
        return rankUsers.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        FrameLayout frameLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profile_image);
            frameLayout = itemView.findViewById(R.id.profileFrame);
        }

        void bind(RoomUserInfo m){

           /* Picasso.get().load(URLs.UserImageBaseUrl+m.avtar)
                    .placeholder(R.drawable.img_user_default)
                    .error(R.drawable.img_user_default)
                    .into(circleImageView);

            if(m.level!=null) {
                frameLayout.setBackgroundResource(
                        LevelControll.getLevelFrame(m.level)
                );
            }*/
            Log.d("ava",m.avtar) ;
            Picasso.get().load(URLs.UserImageBaseUrl+m.avtar)
           // Picasso.get().load("https://saajcraftarena.com/image/sofa.jpg")
                    .placeholder(R.drawable.img_user_default)
                    .error(R.drawable.img_user_default)
                    .into(circleImageView);
            frameLayout.setBackgroundResource(
                        R.drawable.frame_1);

//            itemView.setOnClickListener(v->{
//                loge(TAG, ""+toGson(m));
//                if (_listener!=null && m.fuid!=null && !m.fuid.isEmpty()) _listener.onTopGifterUserClick(m);
//            });
        }
    }

    public void update1(List<RoomUserInfo> newList){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCall(rankUsers, newList));
        rankUsers.clear();
        rankUsers.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void update(List<RoomUserInfo> newList){
        rankUsers = newList;
        notifyDataSetChanged();
    }

    class DiffCall extends DiffUtil.Callback{

        List<RoomUserInfo> olddata;
        List<RoomUserInfo> newdata;

        public DiffCall(List<RoomUserInfo> olddata, List<RoomUserInfo> newdata) {
            this.olddata = olddata;
            this.newdata = newdata;
        }

        @Override
        public int getOldListSize() {
            return olddata.size();
        }

        @Override
        public int getNewListSize() {
            return newdata.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return olddata.get(oldItemPosition).fuid.equals(newdata.get(newItemPosition).fuid);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return !olddata.get(oldItemPosition).avtar.equals(newdata.get(newItemPosition).avtar);
        }
    }

    private LiveRoomUserLayout.UserLayoutListener _listener;
    public void setOnLiveUserClick(LiveRoomUserLayout.UserLayoutListener _listener){
        this._listener = _listener;
    }


}
