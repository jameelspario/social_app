package com.deificindia.firebaseAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.LevelUtils;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.deificpk.actionsheets.LiveRoomUserListActionSheet;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.deificpk.utils.UserUtil;
import com.deificindia.indifun.deificpk.widgets.LiveRoomUserLayout;
import com.deificindia.indifun.fires.m.UserJoinedInfo;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;

public class ListJoinAdapter extends RecyclerView.Adapter<ListJoinAdapter.ImageViewHolder>{
    private Context mContext;
    private List<UserJoinedInfo> mComment;
    private String postid;

    private FirebaseUser firebaseUser;
    private String isdatefind,isdatefind1;

    private LiveRoomUserListActionSheet.OnUserSelectedListener mOnUserSelectedListener;
    @SuppressLint("SimpleDateFormat")
    public ListJoinAdapter(Context context, List<UserJoinedInfo> comments){
        mContext = context;
        mComment = comments;
        this.postid = postid;
        isdatefind=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
    }

    @NonNull
    @Override
    public ListJoinAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.action_room_user_list_item, parent, false);
        return new ListJoinAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListJoinAdapter.ImageViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UserJoinedInfo profile = mComment.get(position);
        try {
            holder.name.setText(profile.user_name);
            Picasso.get().load(URLs.UserImageBaseUrl + profile.user_avtar).placeholder(R.drawable.avatar).into(holder.icon);
            holder.itemView.setOnClickListener(view -> {
                if (mOnUserSelectedListener != null) mOnUserSelectedListener
                        .onActionSheetUserListItemSelected(profile);
            });

            holder.itemView.setOnClickListener(view -> {
                if (mOnUserSelectedListener != null) mOnUserSelectedListener
                        .onActionSheetUserListItemSelected(profile);
            });

            if (profile.user_frame != null && !profile.user_frame.isEmpty()) {
                LevelUtils.instance().setLevelFrame(profile.user_frame, holder.profileframe);
            }


            holder.userTags.addTo(LEVEL).updateLevel(profile.user_level);

            holder.userTags.addTo(GENDER).updateGender(profile.user_age, profile.user_gender);

            holder.icon.setOnClickListener(v -> {
                ActivityUtils.openUserDetailsActivity3(mContext,
                        profile.user_fuid,
                        profile.user_name,
                        holder.parent_zoom);

            });
        }catch (Exception e){
            e.printStackTrace();
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }
    @Override
    public int getItemCount() {
        return mComment==null?0:mComment.size();
    }

    public void setOnLiveUserClick(LiveRoomUserLayout.UserLayoutListener listener) {
        this._listener=listener;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        CircleImageView icon;
        FrameLayout profileframe;
        AppCompatTextView name;
        View parent_zoom;
        UserTags userTags;
        LinearLayout genderTag;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.live_room_action_sheet_user_list_item_icon);
            name = itemView.findViewById(R.id.live_room_action_sheet_user_list_item_name);
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            profileframe=itemView.findViewById(R.id.profileFrame);
            genderTag = itemView.findViewById(R.id.tagLay);
            userTags = UserTags.instance().container(genderTag);
        }
    }

    private LiveRoomUserLayout.UserLayoutListener _listener;

}
