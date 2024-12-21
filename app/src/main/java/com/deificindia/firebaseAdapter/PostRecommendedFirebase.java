package com.deificindia.firebaseAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.chat.Model.User;
import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.imagelistview.ImageListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;

public class PostRecommendedFirebase extends RecyclerView.Adapter<PostRecommendedFirebase.ImageViewholder> {
    private Context mContext;
    private List<User> mUsers;
    private boolean isFragment;

    private FirebaseUser firebaseUser;
    private SimpleDateFormat simpleDateFormat;
    private String isdatefind,fuid;
    private UserPostAdapter.OnItemClick onItemClick;
    int bgcolor;
    int gendertag;
//    public PostRecommendedFirebas(Context activity) {
//        super(activity);
//    }
    @SuppressLint("SimpleDateFormat")
    public PostRecommendedFirebase(Context activity, List<User> users, boolean isFragment) {
        mContext = activity;
        mUsers = users;
        this.isFragment = isFragment;
        isdatefind = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
    }

    public PostRecommendedFirebase(FragmentActivity activity) {

    }

    @NonNull
    @Override
    public PostRecommendedFirebase.ImageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_recommendad_adapter_layout, parent, false);
        return new PostRecommendedFirebase.ImageViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewholder holder, int position) {
        final User user = mUsers.get(position);
        holder.recomname.setText(user.getFull_name());
fuid=user.getFuid();
loge("user",fuid);
//        if(user.getAge()!=null)
//            holder.tagGender.getgetTagText().setText(user.getAge());
//
//        if(user.getGender()!=null){
//            setGenderTag(holder.tagGender, user.getGender());
//        }
        Picasso.get()
                .load(user.getImage())
                .error(R.drawable.avatar)
                .into(holder.userimage);
        //UiUtils.setSmallGenderTag(context, holder.tagGender, mList.getGender(), mList.getAge());
        if(user.getLevel() != null&& !user.getLevel().isEmpty()){

            holder.profileframe.setBackgroundResource(LevelControll.getLevelFrame(user.getLevel()));
            holder.userTags.addTo(LEVEL)
                    .updateLevel(user.getLevel() + " Lvl");

        }
        holder.userTags.addTo(GENDER)
                .updateGender(user.getGender(),  user.getGender());

//        holder.userTags.addTo(UserTags.LEVEL)
//                .updateLevel(user. + " Lvl");

//        Picasso.get().load(UserImageBaseUrl+user.getImage())
//                .into(holder.userimage);

        if(user.getWhatsup()!=null){
            holder.tvWhatsup.setText(user.getWhatsup());
        }else holder.tvWhatsup.setVisibility(View.GONE);

//        if(user.getIsBroadcasting().equalsIgnoreCase("1")){
//            holder.liveLayout.setVisibility(View.VISIBLE);
//            holder.imageListView.setVisibility(View.GONE);
//            Picasso.get().load(UserImageBaseUrl+user.getImage()).into(new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    holder.liveLayout.setBackground(new BitmapDrawable(bitmap));
//                }
//
//                @Override
//                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                }
//            });
//        }else {
//            holder.imageListView.setVisibility(View.VISIBLE);
//            holder.liveLayout.setVisibility(View.GONE);
//        }
        holder.userimage.setOnClickListener(v ->{

            ActivityUtils.openUserDetailsActivity3(mContext,
                    user.getUser_id(),
                    user.getFull_name(),
                    holder.parent_zoom);
            Toast.makeText(mContext, ""+user.getUser_id()+user.getFull_name(), Toast.LENGTH_SHORT).show();
        });

        holder.himessage.setOnClickListener(v->{
//            sendHiiMessage();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails");
            String  hi="Hi...";
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("hi",hi);
//        String finalHi = hi;
            reference.child(fuid)
                    .updateChildren(hashMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                                builder.setCancelable(true);
                                builder.setMessage("You have send a message of "+hi +user.getFull_name()+"");
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                Toast.makeText(mContext, ""+ hi, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        });



}

//    private Context context() {
//        return context();
//    }

    private void sendHiiMessage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails");
        String  hi="Hi...";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("hi",hi);
//        String finalHi = hi;
        reference.child(fuid)
                .updateChildren(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(mContext, ""+ hi, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ImageViewholder extends RecyclerView.ViewHolder{
        View parent_zoom;
        ImageView img1like;
        CircleImageView userimage, imgLive;
        TextView recomname, tvWhatsup;
        LinearLayout tagGender;
        View liveLayout;
        ImageListView imageListView;
        LinearLayout himessage;
FrameLayout profileframe;
        UserTags userTags;
        public ImageViewholder(@NonNull View itemView) {
            super(itemView);
            profileframe=itemView.findViewById(R.id.profileFrame);
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            userimage = itemView.findViewById(R.id.recomimage);
            imgLive = itemView.findViewById(R.id.imgLive);

            recomname = itemView.findViewById(R.id.username);
            tvWhatsup = itemView.findViewById(R.id.tvWhatsup);

            img1like = itemView.findViewById(R.id.imageView6);
            liveLayout = itemView.findViewById(R.id.liveLayout);

            imageListView = itemView.findViewById(R.id.imgMomentImages);
            himessage = itemView.findViewById(R.id.llend);

            tagGender = itemView.findViewById(R.id.gendertag);
            userTags = UserTags.instance().container(tagGender);
        }
    }
}
