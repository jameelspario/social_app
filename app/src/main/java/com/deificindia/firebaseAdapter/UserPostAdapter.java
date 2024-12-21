package com.deificindia.firebaseAdapter;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.chat.MessageActivity;
import com.deificindia.chat.Model.User;
import com.deificindia.firebaseActivity.CommentsActivity;
import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.indifun.Adapter.ImageListAdapter;
import com.deificindia.indifun.Model.MyImage;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.KotUtils;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.ui.LikeCommentPanel;
import com.deificindia.indifun.ui.multipost.MultiPostImageView;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;


import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.ImageViewholder> {
    private Context mContext;
    private List<firebaseUserModel> mUsers;
    private boolean isFragment;

    private FirebaseUser firebaseUser;
    private SimpleDateFormat simpleDateFormat;
    private String isdatefind;
    private OnItemClick onItemClick;
    int bgcolor;
    int gendertag;
    int mode=0;


    public void addData(List<firebaseUserModel> users) {
        this.mUsers.addAll(users);
        notifyDataSetChanged();
    }

    public firebaseUserModel getItemAtPosition(int position) {
        return mUsers.get(position);
    }
    public void addLoadingView() {
        //add loading item
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mUsers.add(null);
                notifyItemInserted(mUsers.size() - 1);
            }
        });
    }

    public void removeLoadingView() {
        //Remove loading item
        mUsers.remove(mUsers.size() - 1);
        notifyItemRemoved(mUsers.size());
    }
    @SuppressLint("SimpleDateFormat")
    public UserPostAdapter(Context context, List<firebaseUserModel> users, boolean isFragment) {
        mContext = context;
        mUsers = users;
        this.isFragment = isFragment;
        isdatefind = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
    }

    public UserPostAdapter(FragmentActivity activity) {

    }


    @NonNull
    @Override
    public UserPostAdapter.ImageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_public_adapter_layout, parent, false);
        return new UserPostAdapter.ImageViewholder(view);

//
//        if(viewType== Constant.VIEW_TYPE_ITEM) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.post_public_adapter_layout, parent, false);
//            return new UserPostAdapter.ImageViewholder(view);
//        } else if(viewType==Constant.VIEW_TYPE_LOADING) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.post_public_adapter_layout, parent, false);
//            return new UserPostAdapter.ImageViewholder(view);
//        }
//        return null;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserPostAdapter.ImageViewholder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final firebaseUserModel user = mUsers.get(position);
       // Picasso.get().load(user.getImage()).into(holder.hotpostdp);
        holder.hotpostnam.setText(user.getFull_name());
        //getUserInfo(holder.hotpostdp, holder.hotpostnam, user.getId());
        if(user.getImage()!=null && !user.getImage().isEmpty())
            Picasso.get().load(URLs.UserImageBaseUrl+user.getImage()).placeholder(R.drawable.ic_user)
                    .into(holder.hotpostdp);

        isFollowing(user.getId(), holder.imageView);
        isLiked(user.getPostid(), holder.like);
        nrLikes(holder.likes, user.getPostid());
        getCommetns(user.getPostid(), holder.comments);
        if(user.getDescription()==null){
            holder.tvMessage.setVisibility(View.GONE);
        }else {
            holder.tvMessage.setText(user.getDescription());
        }
        if (user.getId().equals(firebaseUser.getUid())){
            holder.btnfollow.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
            holder.linera3.setVisibility(View.GONE);
        }else{
            holder.btnfollow.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.linera3.setVisibility(View.VISIBLE);
        }
        //if(user.getLevel() != null&& !user.getLevel().isEmpty()){
            holder.profileframe.setBackgroundResource(LevelControll.getLevelFrame(String.valueOf(user.getLevel())));
            holder.userTags.addTo(LEVEL)
                    .updateLevel(user.getLevel() + " Lvl");
        holder.userTags.addTo(GENDER).updateGender(user.getGender(),  user.getGender());

        if(user.getLongitude().isEmpty()&&user.getLattitude().isEmpty()){
            loge("sshss",user.getLattitude()+user.getLongitude());
          //  Toast.makeText(mContext, "no data found", Toast.LENGTH_SHORT).show();
        }else{
            Location startPoint=new Location("locationA");
            String c = user.getLattitude();
            String d =user.getLongitude();
            startPoint.setLatitude(Double.parseDouble(c));
            startPoint.setLongitude(Double.parseDouble(d));
            Location endPoint=new Location("locationA");
            String a = user.getLattitude();
            String b = user.getLongitude(); ;
            endPoint.setLatitude(Double.parseDouble(a));
            endPoint.setLongitude(Double.parseDouble(b));
            double distance=startPoint.distanceTo(endPoint) /1000;
            Formatter formatter = new Formatter();
            formatter.format("%.2f", distance);
            holder.hotpostdistance.setText(formatter.toString()+"km away");
        }


        String timget = calculteTimeAgo(user.getIsdate());
        if(timget.equals("0 minutes ago")){
            holder.hotposttime.setText("Just now");
        }else{
            holder. hotposttime.setText(timget);
        }
        holder.city.setText(user.getCity());
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid", user.getPostid());
                intent.putExtra("publisherid", user.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });


        if(user.getLink()!=null && user.getLink().size()>0) {

            holder.multiPostImageView.init(KotUtils.obj.filterImage(user.getLink()), (pos, item) -> {

            });
        }

        holder.hotpostdp.setOnClickListener(v ->{
            ActivityUtils.openUserDetailsActivity3(mContext,
                    user.getUser_id(),
                    user.getFull_name(),
                    holder.parent_zoom);
            loge("uid",user.getUser_id());
        });
        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.like.getTag().equals("like")) {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(user.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);
                    if(user.getId().equals(firebaseUser.getUid())){
                        loge("djhjj",firebaseUser.getUid());
                        //  Toast.makeText(mContext, ""+firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                    }else{
                        addNotification(user.getId(), user.getPostid());
                    }

                    // holder.likes.setVisibility(View.VISIBLE);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(user.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                    // holder.likes.setVisibility(View.GONE);
                }
            }
        });
        holder.linera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userid",user.getId());
                mContext.startActivity(intent);
            }
        });
        holder.linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "jkhdfhjk", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid", user.getPostid());
                intent.putExtra("publisherid", user.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

//        holder.btn_folloq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (holder.btnfollow.getText().toString().equals("follow")) {
//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                            .child("UnFollow").child(user.getId()).setValue(true);
//
//                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
//                            .child("followers").child(firebaseUser.getUid()).setValue(true);
//                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
//                    builder.setCancelable(true);
//                    builder.setMessage(R.string.sureexist);
//                    AlertDialog dialog1 = builder.create();
//                    dialog1.show();
//                    addNotification(user.getId());
//                } else {
//
//
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
//                                    .child("UnFollow").child(user.getId()).removeValue();
//                            FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
//                                    .child("followers").child(firebaseUser.getUid()).removeValue();
//                        }
//                    });
//                    AlertDialog dialog1 = builder.create();
//                    dialog1.show();
//
//
//                }
//            }
//        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                        .child("UnFollow").child(user.getId()).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
                        .child("followers").child(firebaseUser.getUid()).setValue(true);
                addNotification(user.getId());
                holder.imageView.playAnimation();

            }
        });

    }

    private void addNotification(String userid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NotificationsFollower").child(userid);
//        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "started following you");
        hashMap.put("time", isdatefind);
        hashMap.put("postid", userid);
        hashMap.put("ispost", false);

        reference.push().setValue(hashMap);
    }

    private void addNotification(String userid, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid)
                ;
//        String from = reference.push().child(userid).getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "liked your post");
        hashMap.put("time",isdatefind);
        hashMap.put("postid", postid);
        hashMap.put("ispost", false);
        hashMap.put("read",false);
//        hashMap.put("from",from);
        reference.push().setValue(hashMap);
    }
    private void nrLikes(final TextView likes, String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()==0) {
                    likes.setText("like");
                    likes.setVisibility(View.VISIBLE);
                } else {
                    likes.setText(dataSnapshot.getChildrenCount() + " "+"likes ");
                    likes.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void isLiked(final String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_heart);
                    imageView.setTag("liked");
                } else{
                    imageView.setImageResource(R.drawable.jeart);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void userdetails(String user_id,String image){
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


    }

    @Override
    public int getItemCount() {
        Log.e("itemc", String.valueOf(mUsers));
        return  mUsers == null ? 0 : mUsers.size();
    }

    public class ImageViewholder extends RecyclerView.ViewHolder {
        View parent_zoom;
        CardView cardView;
        ImageView hotpostdp,like;
        TextView hotpostnam, hotposttime,comments, hotpostdistance, watsap, tvMessage, btnfollow,likes;
        LinearLayout genderTag;
        LinearLayout linear2;
        LinearLayout follow_button,relativeLayout;
        RecyclerView recyclerView;
        LinearLayout linear1,linera3;
        TextView city,gender;
        LikeCommentPanel likeCommentPanel;
        UserTags userTags;
        LinearLayout btn_folloq;
        ImageView btn_follow1;
        FrameLayout profileframe;
        LottieAnimationView imageView;

        MultiPostImageView multiPostImageView;

        public ImageViewholder(@NonNull View itemView) {
            super(itemView);
            profileframe=itemView.findViewById(R.id.profileFrame);
            imageView=itemView.findViewById(R.id.animation_view);
            likes = itemView.findViewById(R.id.likecount);
            linear2 = itemView.findViewById(R.id.linear2);
            linera3 = itemView.findViewById(R.id.chatopen);
            cardView = itemView.findViewById(R.id.card_item);
            linear1 = itemView.findViewById(R.id.linear1);
            like = itemView.findViewById(R.id.like_icon);
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            hotpostdp = itemView.findViewById(R.id.hotpostdp);
            hotpostnam = itemView.findViewById(R.id.hotpostname);
             city=itemView.findViewById(R.id.location);
            //gender=itemView.findViewById(R.id.gender);
            btn_follow1=itemView.findViewById(R.id.btn_follow1);
            comments=itemView.findViewById(R.id.comemntcount);
            multiPostImageView = itemView.findViewById(R.id.multi_post_view);
            hotposttime = itemView.findViewById(R.id.hotposttime);
            hotpostdistance = itemView.findViewById(R.id.hotpostdistance);
            watsap = itemView.findViewById(R.id.tvWatsap);
            tvMessage = itemView.findViewById(R.id.message);
            btnfollow = itemView.findViewById(R.id.btn_follow);
            btn_folloq = itemView.findViewById(R.id.btn_folloq);
            genderTag = itemView.findViewById(R.id.gender);
            userTags = UserTags.instance().container(genderTag);
        }
    }

    public void setOnItemClick(OnItemClick onItemClick) {
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
                    comments.setText("Comments");
                }else{
                    comments.setText(""+dataSnapshot.getChildrenCount());
                    comments.setPaintFlags(comments.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private String calculteTimeAgo(long dateago) {
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

    private void isFollowing(final String userid,final LottieAnimationView imageView) {


        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("UnFollow");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
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

                } else {
                    imageView.setSpeed(-1F);
                    imageView.playAnimation();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public int getItemViewType(int position) {
        return mUsers.get(position) == null ? Constant.VIEW_TYPE_LOADING : Constant.VIEW_TYPE_ITEM;
    }

    private void getUserInfo(final ImageView imageView, final TextView username, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("UserDetails").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                com.deificindia.chat.Model.User user = dataSnapshot.getValue(User.class);
                assert user != null;
                username.setText(user.getFull_name());
                Picasso.get().load(user.getImage()).placeholder(R.drawable.ic_user)
                        .into(imageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
