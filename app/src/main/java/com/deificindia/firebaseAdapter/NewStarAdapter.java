 package com.deificindia.firebaseAdapter;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.chat.Model.User;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.LikeCommentPanel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;
import static com.deificindia.indifun.rest.RetroCallListener.ONFOLLOWCLICK;

 public class NewStarAdapter extends RecyclerView.Adapter<NewStarAdapter.ImageViewholder> {
    private Context mContext;
    private List<User> mUsers;
    private boolean isFragment;

    private FirebaseUser firebaseUser;
    private SimpleDateFormat simpleDateFormat;
    private String isdatefind;
    private NewStarAdapter.OnItemClick onItemClick;
    int bgcolor;
    int gendertag;
    String usersId,otheruserid;
     private int mBackground;
     private int[] mMaterialColors;

    @SuppressLint("SimpleDateFormat")
    public NewStarAdapter(Context context, List<User> users, boolean isFragment) {
        mContext = context;
        mUsers = users;
//        this.mUsers=new ArrayList<>();
        this.isFragment = isFragment;
        isdatefind = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
    }

//     public NewStarAdapter() {
//         this.mUsers=new ArrayList<>();
//     }


    @NonNull
    @Override
    public NewStarAdapter.ImageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        prepareLetterIcons(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_newstarfirebase, parent, false);
        return new NewStarAdapter.ImageViewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewStarAdapter.ImageViewholder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = mUsers.get(position);
        holder.hotpostnam.setText(user.getFull_name());
        loge("hotuser", user.getImage());
        otheruserid=user.getUser_id();
        System.out.println("otheruser"+user.getUser_id());
        isFollowing(user.getFuid(), holder.imageView);
//        isLiked(holder.imageView,user.getFuid());
        usersId = user.getFuid();
      //  loge("hotuser1", user.getUser_id());
        Picasso.get().load(URLs.UserImageBaseUrl+user.getImage()).placeholder(R.drawable.avatar).into(holder.imgAvtar);
        if (user.getFuid().equals(firebaseUser.getUid())){
            holder.btnfollow.setVisibility(View.GONE);
            holder.Unfollow.setVisibility(View.GONE);
            holder.follow_button.setVisibility(View.GONE);
        }
        holder.userTags.addTo(GENDER)
                .updateGender(user.getGender(),  user.getGender());

        if(user.getLevel() != null&& !user.getLevel().isEmpty()){

            holder.profileframe.setBackgroundResource(LevelControll.getLevelFrame(user.getLevel()));
            holder.userTags.addTo(LEVEL)
                    .updateLevel(user.getLevel() + " Lvl");
        }
        holder.imgAvtar.setOnClickListener(v -> {
            ActivityUtils.openUserDetailsActivity3(mContext,
                    user.getUser_id(),
                    user.getFull_name(),
                    holder.parent_zoom);
            loge("uid",user.getUser_id());
           // Toast.makeText(mContext, "" + user.getUser_id(), Toast.LENGTH_SHORT).show();

        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                        .child("UnFollow").child(user.getFuid()).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getFuid())
                        .child("followers").child(firebaseUser.getUid()).setValue(true);

                RetroCalls.instance().callType(ONFOLLOWCLICK)
                        .withUID()
                        .stringParam(otheruserid)
                        .setOnFail((type_result, code, message) -> {

                        })
                        .follow_user((type_result, obj) -> {
                            if(obj!=null){

                                System.out.println("followid"+obj.getMessage());
                                Toast.makeText(mContext, ""+obj.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                addNotification(user.getId());
                holder.imageView.playAnimation();
            }



//                else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
//                    builder.setCancelable(true);
//                    builder.setMessage(R.string.sureexist);
//                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                                    .child("UnFollow").child(user.getFuid()).removeValue();
//                            FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getFuid())
//                                    .child("followers").child(firebaseUser.getUid()).removeValue();
//
//                           /// holder.Unfollow.setVisibility(View.VISIBLE);
////                            holder.Unfollow.setImageResource(R.drawable.plus_1);
//                        }
//                    });
//                    AlertDialog dialog1 = builder.create();
//                    dialog1.show();
//                }

        });

    }
    private void addNotification(String userid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NotificationsFollower")
                .child(userid);
//        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "started following you");
        hashMap.put("time", isdatefind);
        hashMap.put("postid", userid);
        hashMap.put("ispost", false);
        reference.push().setValue(hashMap);
    }
    @Override
    public int getItemCount() {
        Log.e("itemc", String.valueOf(mUsers));
        return mUsers.size();
    }


     public void addAll(List<User> newUsers) {
         int initialSize = mUsers.size();
         mUsers.addAll(newUsers);
         notifyItemRangeInserted(initialSize, newUsers.size());
     }

//     public String getLastItemId() {
//         return (String) mUsers.get(mUsers.size() - 1).getKey();
//     }
     private void prepareLetterIcons(Context c){
         TypedValue mTypedValue = new TypedValue();
         c.getTheme().resolveAttribute(R.attr.selectableItemBackground,
                 mTypedValue, true);
         mMaterialColors = c.getResources().getIntArray(R.array.colors);
         mBackground = mTypedValue.resourceId;
     }

     public String getLastItemId() {
         Log.e("itemc", String.valueOf(mUsers));
        return String.valueOf(mUsers.get(mUsers.size()-1).getKey());
     }

     public class ImageViewholder extends RecyclerView.ViewHolder {
        View parent_zoom;
        CardView cardView;
        ImageView hotpostdp,like;
        TextView hotpostnam,btnfollow;
        LinearLayout genderTag;
        LinearLayout linear2;
        LinearLayout follow_button,relativeLayout;
        AppCompatImageView imaage;
        RecyclerView recyclerView;
        LinearLayout linear1;
        ImageView message;
        LikeCommentPanel likeCommentPanel;
        UserTags userTags;
        CircleImageView imgAvtar;
        ImageView follow,Unfollow;
        FrameLayout profileframe;
        LottieAnimationView imageView;
       // ImageView imageView;
        private LottieAnimationView animationView,animationView1;
        public ImageViewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.animation_view);
            Unfollow=itemView.findViewById(R.id.ff);
            btnfollow = itemView.findViewById(R.id.btn_follow1);
            hotpostnam=itemView.findViewById(R.id.tvName);
            follow_button = itemView.findViewById(R.id.rectclick);
            imgAvtar = itemView.findViewById(R.id.imgAvtar);
            genderTag = itemView.findViewById(R.id.tagLay);
            userTags = UserTags.instance().container(genderTag);
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            profileframe=itemView.findViewById(R.id.profileFrame);
        }
    }

    public void setOnItemClick(NewStarAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {

        void getPosition(); //pass any things
    }
    private void getCommetns(String postId, final TextView comments){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0){
                    Paint tr=new Paint();
                    tr.setColor(Color.RED);

                    comments.setPaintFlags(comments.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                    comments.setText("No comments yet");
                }else{
                    comments.setText("All "+dataSnapshot.getChildrenCount()+" Comments");
                    comments.setPaintFlags(comments.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private String calculteTimeAgo(String dateago) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try{
//            long time=sdf.parse(dateago).getTime();
            long time=sdf.parse(String.valueOf(dateago)).getTime();
            long now=System.currentTimeMillis();
            CharSequence ago= DateUtils.getRelativeTimeSpanString(time,now,DateUtils.MINUTE_IN_MILLIS);
            return ago+"";
        }catch (ParseException e){
            e.printStackTrace();
        }
        return "";
    }

     private void isLiked(final String postid, final ImageView imageView) {

         final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
         DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                 .child("Follow").child(firebaseUser.getUid()).child("UnFollow");
         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 if (dataSnapshot.child(firebaseUser.getUid()).exists()) {
                     imageView.setImageResource(R.drawable.ic_heart);
                     imageView.setTag("liked");
                 } else {
                     imageView.setImageResource(R.drawable.jeart);
                     imageView.setTag("like");
                 }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
     }

    private void isFollowing(final String userid,final LottieAnimationView imageView) {


        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("UnFollow");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userid).exists()) {
                    imageView.setSpeed(1f);
                    imageView.addAnimatorListener(new Animator.AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animation) { }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            imageView.removeAnimatorListener(this);
                            imageView.setProgress(0.8f);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) { }

                        @Override
                        public void onAnimationRepeat(Animator animation) { }
                    });
                    imageView.playAnimation();
                    //imageView.setFrame(1);
                   // imageView.setImageResource(R.drawable.checkmark3);
                    //imageView.setVisibility(View.GONE);
                } else {
                    imageView.setSpeed(-1F);
                    imageView.playAnimation();
                    //imageView.setFrame(0);
                    //imageView.playAnimation();
//                    imageView.setImageResource(R.drawable.addicons);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

