package com.deificindia.firebaseAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;

public class JoinerAdapterFir extends RecyclerView.Adapter<JoinerAdapterFir.ImageViewHolder> {
    private Context mcontext;
    private List<RoomUserInfo> mUserList = new ArrayList<>();

    public JoinerAdapterFir(Context context, List<RoomUserInfo> userList) {
        mcontext = context;
        mUserList=userList;
    }

    public void append(List<RoomUserInfo> userList) {
        mUserList.addAll(userList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public JoinerAdapterFir.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.action_room_user_list_item, parent, false);
        return new JoinerAdapterFir.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinerAdapterFir.ImageViewHolder holder, int position) {
        RoomUserInfo profile = mUserList.get(position);

        holder.name.setText(profile.getJoiner_name());
        loge("sudhir ", profile.getJoiner_name());
        if (profile.getImage() != null && !profile.getImage().isEmpty()) {
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+profile.getImage())
                    .error(R.drawable.no_image)
                    .into(holder.icon);
        }
       // Picasso.get().load(URLs.UserImageBaseUrl+profile.getImage()).placeholder(R.drawable.ic_user).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        loge("sudh ", String.valueOf(mUserList));
        return  mUserList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView icon;
        AppCompatTextView name;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.live_room_action_sheet_user_list_item_icon);
            name = itemView.findViewById(R.id.live_room_action_sheet_user_list_item_name);
        }
    }
}
