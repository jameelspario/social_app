package com.deificindia.firebaseAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.firebasefragment.Comment;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.modals.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;

public class BroadCastAdapter extends RecyclerView.Adapter<BroadCastAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Post> mComment;
    private String postid;
    private FirebaseUser firebaseUser;
    @SuppressLint("SimpleDateFormat")
    public BroadCastAdapter(Context context, List<Post> comments){
        mContext = context;
        mComment = comments;
        this.postid = postid;
      //  isdatefind=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));

    }

    @NonNull
    @Override
    public BroadCastAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.broad_adapter, parent, false);
        return new BroadCastAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BroadCastAdapter.ImageViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Post comment = mComment.get(position);
        holder.username.setText(comment.broad_user);
        holder.broadcastdesc.setText(comment.title1);
        holder.userTags.addTo(GENDER)
                .updateGender(comment.gender1,  comment.gender1);

        if(!comment.ispk){
            holder.broadcastaction.setVisibility(View.VISIBLE);
        }else
        {
            holder.broadcastaction.setVisibility(View.GONE);
        }

        if (comment.image != null && !comment.image.isEmpty()) {
            Picasso.get()
                    .load(URLs.UserImageBaseUrl + comment.image)
                    .error(R.drawable.no_image)
                    .into(holder.userimage);
        }

    }
    @Override
    public int getItemCount() {
        loge("fing", String.valueOf(mComment.size()));
        return mComment.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private TextView username, broadcastdesc, broadcasttime, broadcastaction;
        private ImageView userimage,deleltbroadcasts1;
        UserTags userTags;
        LinearLayout genderTag;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            genderTag = itemView.findViewById(R.id.tagLay);
            userTags = UserTags.instance().container(genderTag);
           userimage = itemView.findViewById(R.id.userimage);
           deleltbroadcasts1 = itemView.findViewById(R.id.deleltbroadcasts1);
           username = itemView.findViewById(R.id.username);
          broadcastdesc = itemView.findViewById(R.id.broadcastdesc);
           broadcasttime = itemView.findViewById(R.id.broadcasttime);
            broadcastaction = itemView.findViewById(R.id.broadcastaction);
        }
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
}
