          package com.deificindia.indifun.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.Activities.MomentNotification;
import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.Model.MyImage;
import com.deificindia.indifun.Model.abs_plugs.HotpostResult;
import com.deificindia.indifun.Model.posts.Hotpostmodel;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.holders.HorizontalLoadingHolder;
import com.deificindia.indifun.interfaces.OnCommentUserClickListener;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.LikeCommentPanel;
import com.deificindia.indifun.ui.TagView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.UiUtils.setGenderTag;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.holders.HorizontalLoadingHolder.VIEW_TYPE_LOADING;
import static com.deificindia.indifun.rest.RetroCallListener.FOLLOW_POST;

public class PostFollowAdapter extends AbstractAdapter<HotpostResult> {

    OnCommentUserClickListener _listener;

    public void setOnCommentUserClickListener(OnCommentUserClickListener listener){
        this._listener = listener;
    }

    public PostFollowAdapter(Activity activity) {
        super(activity);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.post_follow_adapter_layout, parent, false)
        );
    }

    @Override
    protected void bindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder1(((ViewHolder) holder), position);
    }

    public void onBindViewHolder1(ViewHolder holder, int position) {

        HotpostResult data = items_list.get(position);

        if(data==null) return;

        if(data.getUser_name()!=null)
            holder.tv_name.setText(data.getUser_name());

        if (data.getProfile_picture() != null && !data.getProfile_picture().isEmpty()) {
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+data.getProfile_picture())
                    .error(R.drawable.no_image)
                    .into(holder.iv_avtar);
        }

        if(data.getTime_milli()>0){
            holder.hotposttime.setText(Logger.dateFormat(data.getTime_milli()));
        }else holder.hotposttime.setText(data.getTime());

        if(data.getDistance()!=null){
            holder.hotpostdistance.setText(data.getDistance());
        }else  holder.hotpostdistance.setText("");

        if(data.getWatsapp()!=null){
            holder.watsap.setVisibility(View.VISIBLE);
            holder.watsap.setText(data.getWatsapp());
        }else {
            holder.watsap.setVisibility(View.GONE);
        }

        if(data.getContent()!=null) holder.tvMessage.setText(data.getContent());
        else holder.tvMessage.setVisibility(View.GONE);


        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context(), MomentNotification.class);
            intent.putExtra("name", data.getUser_name());
            intent.putExtra("gender", data.getGender());
            intent.putExtra("age", data.getAge());
            intent.putExtra("ppic", data.getProfile_picture());
            intent.putExtra("whtsup", data.getWatsapp());
            intent.putExtra("islike", data.getIs_likes());
            intent.putExtra("militime", data.getTime_milli());
            intent.putExtra("tcomment", data.getTotal_comments());
            intent.putExtra("time", data.getTime());
            intent.putExtra("tlike", data.getTotal_likes());
            intent.putExtra("postId", data.getId());
            intent.putParcelableArrayListExtra("imagelist", (ArrayList<? extends Parcelable>) data.getImage());

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context(),
                    Pair.create(holder.cardView, ViewCompat.getTransitionName(holder.cardView)));

            context().startActivity(intent, optionsCompat.toBundle());
        });
        holder.iv_avtar.setOnClickListener(v->{
            ActivityUtils.openUserDetailsActivity3(context(),
                    data.getPost_by(),
                    data.getUser_name(), holder.parent_zoom
            );
        });

        holder.tv_name.setOnClickListener(v->{
            ActivityUtils.openUserDetailsActivity3(context(),
                    data.getPost_by(),
                    data.getUser_name(), holder.parent_zoom
            );
        });
        ////////////////////////////////////
        loge("postFollowAdap", ""+data.getIs_likes());
        if(data.getIs_likes()>0){
           holder.likeCommentPanel.setIsLiked(1);
           holder.likeCommentPanel.likeEnable(false);
        }else {
            holder.likeCommentPanel.setIsLiked(0);
        }

        holder.likeCommentPanel.setLikeCount(String.valueOf(data.getTotal_likes()));
        holder.likeCommentPanel.setComment(String.valueOf(data.getTotal_comments()));
        holder.likeCommentPanel.setOnUserClickListener(()->{
            if(_listener!=null){
                _listener.onComment(position,
                        data.getId(),
                        data.getPost_by(),
                        null
                );
            }
        }, ()->{
            Constants.playLikeHeartAnimation(context(), new ControllModal(1, "heart_like_anim.json"));
            if(data.getIs_likes() < 1) {
               holder.likeCommentPanel.setIsLiked(1);
               on_like(position, holder.likeCommentPanel);
            }
        });

        if(data.getAge()!=null && data.getGender()!=null){
            holder.userTags.addTo(UserTags.GENDER)
                    .updateGender(data.getAge(),  data.getGender());
        }

        holder.userTags.addTo(UserTags.LEVEL)
                .updateLevel(data.getLevel() + " Lvl");

        if(data.getImage()!=null && data.getImage().size()>0) {
            holder.recyclerView.setVisibility(View.VISIBLE);
            holder.bind(data.getImage());
        }else holder.recyclerView.setVisibility(View.GONE);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View parent_zoom;
        CardView cardView;
        ImageView iv_avtar;
        LinearLayout genderTag;

        TextView tv_name, hotposttime, hotpostdistance, watsap, tvMessage;

        RecyclerView recyclerView;

        LikeCommentPanel likeCommentPanel;
        UserTags userTags;

        public ViewHolder(View itemView) {
            super(itemView);
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            cardView = itemView.findViewById(R.id.card_item);
            iv_avtar = itemView.findViewById(R.id.iv_avtar);

            tv_name = itemView.findViewById(R.id.tv_name);
            recyclerView = itemView.findViewById(R.id.imageslist_recycler);
            likeCommentPanel = itemView.findViewById(R.id.like_commentPanel);

            hotposttime = itemView.findViewById(R.id.hotposttime);
            hotpostdistance = itemView.findViewById(R.id.hotpostdistance);
            watsap = itemView.findViewById(R.id.tvWatsap);
            tvMessage = itemView.findViewById(R.id.tvMessage);

            genderTag = itemView.findViewById(R.id.genderTag);
            userTags = UserTags.instance().container(genderTag);

        }

        void bind(List<MyImage> images){
            ImageListAdapter adapter = new ImageListAdapter(context(), images,0);
            recyclerView.setLayoutManager(new GridLayoutManager(context(), 3));
            recyclerView.addItemDecoration(new EqualSpacingItemDecoration(5));
            recyclerView.setAdapter(adapter);
        }

    }

    void on_like(int pos, LikeCommentPanel lott){
        RetroCalls.instance().callType(FOLLOW_POST)
                .withUID()
                .stringParam(items_list.get(pos).getId(), "")
                .intParam(1)
                .setOnFail((a,b,c)->{
                    lott.setIsLiked(0);
                })
                .follow_post((a,obj)->{
                    if(obj!=null){
                        PostModal ob = (PostModal) obj;
                        if(ob.getStatus()==1){
                            items_list.get(pos).updateTotal_Likes();
                            notifyItemChanged(pos);
                            if(this._listener!=null) this._listener.onLikeClick();
                        }else {
                            lott.setIsLiked(0);
                        }
                    }
                });

               /* .listeners((call_type,  obj) -> {
                    if(obj!=null){
                        PostModal ob = (PostModal) obj;
                        if(ob.getStatus()==1){
                            task.get(pos).updateTotal_Likes();
                            notifyItemChanged(pos);
                            if(this._listener!=null) this._listener.onLikeClick();
                        }else {
                            lott.setIsLiked(0);
                        }
                    }
                }, (type, code, message) -> {
                    lott.setIsLiked(0);
                });*/
    }

    public void onPostComment(int pos, View view){
        items_list.get(pos).updateTotal_comments();
        notifyItemChanged(pos);
        //view.setEnabled(false);
       // view.setClickable(false);
    }

}
