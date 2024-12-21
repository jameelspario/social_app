package com.deificindia.firebaseAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.firebaseModel.GiftSenderModel;
import com.deificindia.firebasefragment.Comment;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.actionsheets.LiveRoomUserListActionSheet;
import com.deificindia.indifun.deificpk.widgets.LiveRoomUserLayout;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;

public class RoomListAdapterFirebase extends RecyclerView.Adapter<RoomListAdapterFirebase.ImageViewHolder>{
    private Context mContext;
    private List<GiftSenderModel> mComment = new ArrayList<>();
    private String postid;

    private FirebaseUser firebaseUser;
    private String isdatefind,isdatefind1;

    private LiveRoomUserListActionSheet.OnUserSelectedListener mOnUserSelectedListener;
    @SuppressLint("SimpleDateFormat")
    public RoomListAdapterFirebase(Context context){
        mContext = context;
        this.postid = postid;
        isdatefind=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
//        isdatefind1=new SimpleDateFormat("mm").format(new Timestamp(System.currentTimeMillis()));
    }
    public void append(List<GiftSenderModel> userList) {
        mComment.addAll(userList);
            notifyDataSetChanged();
        }
    @NonNull
    @Override
    public RoomListAdapterFirebase.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_live_user_list, parent, false);
        return new RoomListAdapterFirebase.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomListAdapterFirebase.ImageViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final GiftSenderModel comment = mComment.get(position);

        Picasso.get().load(URLs.UserImageBaseUrl+comment.avtar)
                // Picasso.get().load("https://saajcraftarena.com/image/sofa.jpg")
                .placeholder(R.drawable.img_user_default)
                .error(R.drawable.img_user_default)
                .into(holder.circleImageView);

        holder.frameLayout.setBackgroundResource(R.drawable.frame_1);

        holder.itemView.setOnClickListener(v->{

            if (_listener!=null && comment.broadUid!=null && !comment.fuid.isEmpty()) _listener.onTopGifterUserClick(comment);
        });

    }

    @Override
    public int getItemCount() {
        loge("sixe", String.valueOf(mComment));
        return mComment.size();
    }

    public void setOnLiveUserClick(LiveRoomUserLayout.UserLayoutListener listener) {
        this._listener=listener;
    }

    public void update(List<GiftSenderModel> items) {
        mComment = items;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        FrameLayout frameLayout;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profile_image);
            frameLayout = itemView.findViewById(R.id.profileFrame);
        }
    }

    private LiveRoomUserLayout.UserLayoutListener _listener;
//    public void setOnLiveUserClick(RoomListAdapterFirebase.UserLayoutListener _listener){
//        this._listener = _listener;
//    }
}
