package com.deificindia.firebaseAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.chat.Model.User;
import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.firebasefragment.Comment;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Comment> mComment;
    private String postid;

    private FirebaseUser firebaseUser;
    private String isdatefind, isdatefind1;


    @SuppressLint("SimpleDateFormat")
    public CommentAdapter(Context context, List<Comment> comments, String postid) {
        mContext = context;
        mComment = comments;
        this.postid = postid;
        isdatefind = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
//        isdatefind1=new SimpleDateFormat("mm").format(new Timestamp(System.currentTimeMillis()));
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
        return new ImageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Comment comment = mComment.get(position);
        holder.comment.setText(comment.getComment());
        String timr = calculteTimeAgo(comment.getTime());
        holder.timeupdate.setText(timr);

        if (timr.equals("0 minutes ago")) {
            holder.timeupdate.setText("Just now");
        } else {
            holder.timeupdate.setText(timr);
        }

        isLiked(comment.getCommentid(), holder.likenew);

        nrLikes(holder.likes, comment.getCommentid());

        holder.likenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.likenew.getTag().equals("like")) {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(comment.getCommentid())
                            .child(firebaseUser.getUid()).setValue(true);
                  //  addNotification(comment.getPublisher(), comment.getCommentid());
                    // holder.likes.setVisibility(View.VISIBLE);
                    if (comment.getPublisher().equals(firebaseUser.getUid())) {
                        Log.d("fdshgh", comment.getPublisher());
                        //Toast.makeText(mContext, ""+firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                    } else {
                        addNotification(comment.getPublisher(), comment.getCommentid());
                    }
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(comment.getCommentid())
                            .child(firebaseUser.getUid()).removeValue();
                    // holder.likes.setVisibility(View.GONE);
                }
            }
        });
//        getCommetns(comment.getPostid(), holder.reply1);
//        holder.reply1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent (mContext, CommentsActivity.class);
//                intent.putExtra("postid",comment.getComment());
//                intent.putExtra("publisherid", comment.getPublisher());
//                mContext.startActivity(intent);
//            }
//        });
        getUserInfo(holder.image_profile, holder.username, comment.getPublisher());
//        holder.username.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(mContext, Profilenew.class);
//                intent.putExtra("postid",comment.getComm ent());
//                intent.putExtra("publisherid", comment.getPublisher());
//                mContext.startActivity(intent);
//            }
//        });


//        holder.image_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, HomeActivity.class);
//                intent.putExtra("publisherid", comment.getPublisher());
//                mContext.startActivity(intent);
//            }
//        });
        if (comment.getPostid().equals(firebaseUser.getUid())) {
            holder.delete_comment.setVisibility(View.GONE);
        } else {
            holder.delete_comment.setVisibility(View.VISIBLE);
        }
        holder.delete_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (comment.getPublisher().equals(firebaseUser.getUid())) {

                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setIcon(R.mipmap.ic_launcher);
                    alertDialog.setTitle("Do you want to delete?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase.getInstance().getReference("Comments")
                                            .child(postid).child(comment.getCommentid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(mContext, "Deleted!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                //  return true;
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (comment.getPublisher().equals(firebaseUser.getUid())) {

                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setIcon(R.drawable.ic_app_icon);
                    alertDialog.setTitle("Do you want to delete?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase.getInstance().getReference("Comments")
                                            .child(postid).child(comment.getCommentid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(mContext, "Deleted!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                return false;
            }
        });

    }





    @Override
    public int getItemCount() {
        return mComment.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, likenew, delete_comment;
        public TextView username, comment, timeupdate, reply1, likes;

        public ImageViewHolder(View itemView) {
            super(itemView);
            image_profile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
            likenew = itemView.findViewById(R.id.likew);
            timeupdate = itemView.findViewById(R.id.time);
            reply1 = itemView.findViewById(R.id.reply);
            likes = itemView.findViewById(R.id.likes);
            delete_comment = itemView.findViewById(R.id.delete_comment);
        }
    }

    //    private void getCommetns(String commentid, final TextView comments){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(commentid);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                comments.setText("View All "+dataSnapshot.getChildrenCount()+" Comments");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
    private void nrLikes(final TextView likes, String commentid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(commentid);
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
//                    likes.setText("Like first");
                    likes.setVisibility(View.GONE);
                } else {
                    likes.setText(dataSnapshot.getChildrenCount() + " " + "likes ");
                    likes.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addNotification(String userid, String commentid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "liked your comment");
        hashMap.put("time", isdatefind);
        hashMap.put("commentid", commentid);
        hashMap.put("postid", postid);

        hashMap.put("ispost", true);

        reference.push().setValue(hashMap);
    }

    private void isLiked(final String postid, final ImageView imageView) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.ic_heart);
//                    imageView.setTag("liked");
                } else {
                    imageView.setImageResource(R.drawable.heart_off);
//                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String calculteTimeAgo(String dateago) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
//            long time=sdf.parse(dateago).getTime();
            long time = sdf.parse(String.valueOf(dateago)).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void getUserInfo(final ImageView imageView, final TextView username, String fuid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("UserDetails").child(fuid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                if (user.getImage().equals("default")) {
                    imageView.setImageResource(R.drawable.heart_off);
                    username.setText(user.getFull_name());
                } else {
                    Picasso.get().load(URLs.UserImageBaseUrl+user.getImage())
                            .placeholder(R.drawable.ic_user)
                    .into(imageView);
                  //  Glide.with(mContext.getApplicationContext()).load(URLs.UserImageBaseUrl + user.getImage()).into(imageView);

                    username.setText(user.getFull_name());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}