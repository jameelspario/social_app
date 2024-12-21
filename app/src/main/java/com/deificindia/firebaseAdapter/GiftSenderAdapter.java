package com.deificindia.firebaseAdapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.firebaseModel.GiftSenderModel;
import com.deificindia.indifun.Adapter.HotAdapter;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.widgets.LiveRoomUserLayout;
import com.deificindia.indifun.modals.Post;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;

public class GiftSenderAdapter extends RecyclerView.Adapter<GiftSenderAdapter.ImageViewHolder> {
    private List<GiftSenderModel> hotmodelList = new ArrayList<>();
    private Context mcontext;
    public GiftSenderAdapter(Context context, List<GiftSenderModel> postlist) {
        mcontext = context;
        hotmodelList=postlist;


    }

//    public void update(List<GiftSenderModel> postList){
//        hotmodelList=postList;
//        notifyDataSetChanged();
//    }
    @NonNull
    @Override
    public GiftSenderAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_live_user_list, parent, false);
        return new GiftSenderAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftSenderAdapter.ImageViewHolder holder, int position) {
        final GiftSenderModel data = hotmodelList.get(position);

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(data.uid)){
            Toast.makeText(mcontext, "jh", Toast.LENGTH_SHORT).show();
        }else{
            if (data.avtar != null && !data.avtar.isEmpty()) {
                Picasso.get()
                        .load(URLs.UserImageBaseUrl+data.avtar)
                        .error(R.drawable.no_image)
                        .into(holder.profile);
            }
        }

       // Picasso.get().load(UserImageBaseUrl+data.getImage()).placeholder(R.drawable.bg_level).into(holder.profile);
        loge("userimage",UserImageBaseUrl+data.avtar);
    }

    @Override
    public int getItemCount()
    {
        Log.e("fgi ", String.valueOf(hotmodelList));
        return hotmodelList.size();
    }

//    private LiveRoomUserLayout.UserLayoutListener listener;
//    public void setOnLiveUserClick(LiveRoomUserLayout.UserLayoutListener listener) {
//        this.listener = listener;
//    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile;
       FrameLayout fram;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            fram=itemView.findViewById(R.id.profileFrame);
            profile=itemView.findViewById(R.id.profile_image);

        }
    }
}
