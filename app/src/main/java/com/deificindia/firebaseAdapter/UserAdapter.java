package com.deificindia.firebaseAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.chat.Model.User;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


import static android.content.Context.MODE_PRIVATE;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ImageViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    private boolean isFragment;

    private FirebaseUser firebaseUser;
private SimpleDateFormat simpleDateFormat;
private String isdatefind;
String profileid;
    private OnItemClick onItemClick;

    @SuppressLint("SimpleDateFormat")
    public UserAdapter(Context context, List<User> users, boolean isFragment){
        mContext = context;
        mUsers = users;
        this.isFragment = isFragment;
         isdatefind=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_newstarfirebase, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        profileid= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final User user = mUsers.get(position);

       // holder.btn_follow1.setVisibility(View.VISIBLE);
        holder.imageView.setVisibility(View.GONE);
        isFollowing(user.getFuid(), holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btn_follow1.getText().toString().equals("follow")) {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("UnFollow").child(user.getId()).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
                            .child("followers").child(firebaseUser.getUid()).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                                    .child("UnFollow").child(user.getId()).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
                                    .child("followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        Picasso.get()
                .load(URLs.UserImageBaseUrl+user.getImage())
                .error(R.drawable.avatar)
                .into(holder.image_profile);
//       if(user.getImageURL().equals("default")){
//           holder.image_profile.setImageResource(R.drawable.ic_user);
//
//           holder.username.setText(user.getUsername());
//       }else{
        if(user.getLevel() != null&& !user.getLevel().isEmpty()){

            holder.profileframe.setBackgroundResource(LevelControll.getLevelFrame(user.getLevel()));
            holder.userTags.addTo(LEVEL)
                    .updateLevel(user.getLevel() + " Lvl");

        }

           holder.username.setText(user.getUsername());
         //  Glide.with(mContext.getApplicationContext()).load(URLs.UserImageBaseUrl+user.getImageURL()).into(holder.image_profile);
//       }
//holder.relativeLayout.setVisibility(View.GONE);
        if (user.getId().equals(firebaseUser.getUid())){
            holder.btn_follow1.setVisibility(View.GONE);
            holder.relativeLayout.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
            holder.imageviewf.setVisibility(View.GONE);
        }else{
            holder.imageView.setVisibility(View.GONE);
            holder.imageviewf.setVisibility(View.GONE);
        }
        holder.userTags.addTo(GENDER)
                .updateGender(user.getGender(),  user.getGender());

        holder.image_profile.setOnClickListener(v ->{

            ActivityUtils.openUserDetailsActivity3(mContext,
                    user.getUser_id(),
                    user.getFull_name(),
                    holder.parent_zoom);
            Toast.makeText(mContext, ""+user.getUser_id(), Toast.LENGTH_SHORT).show();
        });
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (holder.btn_follow1.getText().toString().equals("follow")) {
//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                            .child("UnFollow").child(profileid).setValue(true);
//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
//                            .child("followers").child(firebaseUser.getUid()).setValue(true);
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(view.getRootView().getContext());
//                    builder.setCancelable(true);
//                    builder.setMessage(R.string.sureexist);
//                    android.app.AlertDialog dialog1 = builder.create();
//                    dialog1.show();
//                    addNotification(user.getId());
//                } else {
//
//
//                    AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
//                                   builder.setCancelable(true);
//                                   builder.setMessage(R.string.sureexist);
//                                   builder.setNegativeButton("No" , new DialogInterface.OnClickListener() {
//                                       @Override
//                                       public void onClick(DialogInterface dialog, int which) {
//                                           dialog.dismiss();
//                                       }
//                                   });
//                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                                    .child("UnFollow").child(profileid).removeValue();
//                            FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
//                                    .child("followers").child(firebaseUser.getUid()).removeValue();
//                        }
//                    });
//                    AlertDialog dialog1 = builder.create();
//                    dialog1.show();
//
//
//                                       }
//            }
//        });
    }

    private void addNotification(String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NotificationsFollower").child(userid);
//        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "started following you");
        hashMap.put("time",isdatefind);
        hashMap.put("postid", userid);
        hashMap.put("ispost", false);

        reference.push().setValue(hashMap);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public TextView fullname,btn_follow1;
        public AppCompatImageView image_profile;
        public ImageView btn_follow;
public SimpleDateFormat sd;
public LinearLayout relativeLayout,genderTag;
        View parent_zoom;
        UserTags userTags;
        FrameLayout profileframe;
        ImageView imageView;
        LinearLayout imageviewf;
        public ImageViewHolder(View itemView) {
            super(itemView);
            profileframe=itemView.findViewById(R.id.profileFrame);
            imageviewf=itemView.findViewById(R.id.imageviewf);
            imageView=itemView.findViewById(R.id.animation_view);
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            relativeLayout=itemView.findViewById(R.id.rectclick);
            username = itemView.findViewById(R.id.tvName);
//            fullname = itemView.findViewById(R.id.fullname);
            image_profile = itemView.findViewById(R.id.imgAvtar);
            btn_follow = itemView.findViewById(R.id.btn_follow);
            btn_follow1=itemView.findViewById(R.id.btn_follow1);
            genderTag = itemView.findViewById(R.id.tagLay);
            userTags = UserTags.instance().container(genderTag);
        }
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {

        void getPosition(); //pass any things
    }
//    private void showDialog(){
//        final Dialog dialog = new Dialog(mContext);
//        dialog.setContentView(R.layout.design);
//        dialog.setTitle("Dialog Box");
//        Button button1 = (Button) dialog.findViewById(R.id.yes);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        Button button = (Button) dialog.findViewById(R.id.not);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
private void isFollowing(final String userid,final ImageView imageView) {


    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
            .child("Follow").child(firebaseUser.getUid()).child("UnFollow");
    reference.addValueEventListener(new ValueEventListener() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.child(userid).exists()) {
                //  button.setText("UnFollow");
                imageView.setImageResource(R.drawable.checkmark3);
                imageView.setVisibility(View.GONE);
                /// button.setTextColor(R.color.red);
                //button.setTextColor(R.color.badge_background_color);
            } else {
                // button.setText("follow");
                imageView.setImageResource(R.drawable.addicons);
                // button.setTextColor(R.color.green);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    }}