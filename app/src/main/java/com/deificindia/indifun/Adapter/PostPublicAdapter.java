package com.deificindia.indifun.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Activities.MomentNotification;
import com.deificindia.indifun.Model.MyImage;
import com.deificindia.indifun.Model.abs_plugs.HotpostResult;
import com.deificindia.indifun.Model.posts.Hotpostmodel;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.Utility.api;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.holders.HorizontalLoadingHolder;
import com.deificindia.indifun.interfaces.OnCommentUserClickListener;
import com.deificindia.indifun.rest.RestWork;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.LikeCommentPanel;
import com.deificindia.indifun.ui.TagView;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrlTemp;
import static com.deificindia.indifun.Utility.UiUtils.setGenderTag;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.holders.HorizontalLoadingHolder.VIEW_TYPE_LOADING;
import static com.deificindia.indifun.rest.RetroCallListener.FOLLOW_POST;
import static com.deificindia.indifun.rest.RetroCallListener.ONFOLLOWCLICK;

public class PostPublicAdapter extends AbstractAdapter<HotpostResult>{

    int islie=0;
    private String self_wuid;

    OnCommentUserClickListener _listener;

    public void setOnCommentUserClickListener(OnCommentUserClickListener listener){
        this._listener = listener;
        this.self_wuid = IndifunApplication.getInstance().getCredential().getId();
    }

    public PostPublicAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        return new HotPostViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.post_public_adapter_layout, parent, false)
        );
    }

    @Override
    protected void bindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder1(((HotPostViewHolder) holder), position);
    }

    public void onBindViewHolder1(final HotPostViewHolder holder, int position) {
        final HotpostResult data = items_list.get(position);
        holder.hotpostnam.setText(data.getUser_name());
        loge("username12", ""+data.getUser_name());
        loge("profilepic", ""+data.getProfile_picture());
        Picasso.get().load(UserImageBaseUrlTemp+data.getProfile_picture()).placeholder(R.drawable.img_user_default).into(holder.hotpostdp);
//        if(data.getProfile_picture()!=null && !data.getProfile_picture().isEmpty()){
//            Picasso.get().load(data.getProfile_picture()).placeholder(R.drawable.img_user_default).into(holder.hotpostdp);
//           // UiUtils.setAvtarRounded(data.getProfile_picture(), context(), holder.hotpostdp);
//        }

        if(data.getTime_milli()>0){
            holder.hotposttime.setText(Logger.dateFormat(data.getTime_milli()));
        }else holder.hotposttime.setText(data.getTime());

        if(data.getDistance()!=null){
            holder.hotpostdistance.setText(data.getDistance());
        } else holder.hotpostdistance.setText("");

        if(data.getWatsapp()!=null) holder.watsap.setText(data.getWatsapp());
        else holder.watsap.setVisibility(View.GONE);

        if(data.getContent()!=null) holder.tvMessage.setText(data.getContent());
        else holder.tvMessage.setVisibility(View.GONE);

        holder.hotpostdp.setOnClickListener(v->{
            ActivityUtils.openUserDetailsActivity3(context(),
                    data.getPost_by(),
                    data.getUser_name(), holder.parent_zoom);

        });

        holder.hotpostnam.setOnClickListener(v->{
            ActivityUtils.openUserDetailsActivity3(context(),
                    data.getPost_by(),
                    data.getUser_name(), holder.parent_zoom);
        });


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
        ////////////////////////////////////
        loge("postPublicAdap", ""+data.getIs_likes());
        if(data.getIs_likes()==1){
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
            if(data.getIs_likes() < 1) {
               holder.likeCommentPanel.setIsLiked(1);
               on_like(position, holder.likeCommentPanel);
            }
        });

        //if(data.getAge()!=null) holder.genderTag.getTagText().setText(""+data.getAge());
        //if(data.getGender()!=null) setGenderTag(holder.genderTag, data.getGender());

        if(data.getAge()!=null && data.getGender()!=null){
            holder.userTags
                    .addTo(GENDER)
                    .updateGender(data.getAge(),  data.getGender());
        }

        holder.userTags.addTo(UserTags.LEVEL)
                .updateLevel(data.getLevel() + " Lvl");

        if(data.getImage()!=null && data.getImage().size()>0) {
            holder.bind(data.getImage());
        }else holder.recyclerView.setVisibility(View.GONE);


        loge("PostPublic", data.getPost_by(), self_wuid);
        if(data.getPost_by().equals(self_wuid)){
            holder.follow_button.setVisibility(View.GONE);
        }else {

            if(data.getIs_following()>0){
                RestWork.changeFolowButton(holder.follow_button, 1);
            }else {
                RestWork.changeFolowButton(holder.follow_button, 3);
            }

            holder.follow_button.setOnClickListener(v -> {
                if(data.getIs_following()>0) {
                    holder.follow_button.setEnabled(false);
                    holder.unFollowApi(position);
                }else {
                    holder.follow_button.setEnabled(false);
                    holder.FollowApi(position);
                }
            });
        }

    }


    public class HotPostViewHolder extends RecyclerView.ViewHolder {

        View parent_zoom;
        CardView cardView;
        ImageView hotpostdp;
        TextView hotpostnam, hotposttime, hotpostdistance, watsap, tvMessage;
        LinearLayout genderTag;
        AppCompatButton follow_button;
        RecyclerView recyclerView;

        LikeCommentPanel likeCommentPanel;
        UserTags userTags;


        public HotPostViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_item);
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            hotpostdp = itemView.findViewById(R.id.hotpostdp);
            hotpostnam = itemView.findViewById(R.id.hotpostname);


            hotposttime = itemView.findViewById(R.id.hotposttime);
            hotpostdistance = itemView.findViewById(R.id.hotpostdistance);
            watsap = itemView.findViewById(R.id.tvWatsap);
            tvMessage = itemView.findViewById(R.id.tvMessage);

            recyclerView = itemView.findViewById(R.id.imagelayout);
            follow_button = itemView.findViewById(R.id.button);

            likeCommentPanel = itemView.findViewById(R.id.like_commentPanel);

            genderTag = itemView.findViewById(R.id.genderTag);
            userTags = UserTags.instance().container(genderTag);
        }


        void bind(List<MyImage> images) {
            ImageListAdapter adapter = new ImageListAdapter(context(), images, 0);
            recyclerView.setLayoutManager(new GridLayoutManager(context(), 3));
            recyclerView.addItemDecoration(new EqualSpacingItemDecoration(5));

            recyclerView.setAdapter(adapter);
        }

        void FollowApi(int pos) {
            RestWork.changeFolowButton(follow_button, 2);
            RetroCalls.instance().callType(ONFOLLOWCLICK)
                    .withUID()
                    .stringParam(items_list.get(pos).getPost_by())
                    .setOnFail((type_result, code, message) -> {
                        RestWork.changeFolowButton(follow_button, 0);
                    })
                    .follow_user((type_result, obj) -> {
                        if (obj != null) {
                            if (obj.getStatus() == 1) {
                                RestWork.changeFolowButton(follow_button, 1);
                                items_list.get(pos).updateFollow(1);
                            } else {
                                if(obj.getStatus()==0){
                                    RestWork.changeFolowButton(follow_button, 1);
                                    items_list.get(pos).updateFollow(1);
                                    return;
                                }
                                RestWork.changeFolowButton(follow_button, 0);
                                items_list.get(pos).updateFollow(0);
                            }
                        }
                    });
        }

        void unFollowApi(int pos) {
            RestWork.changeFolowButton(follow_button, 2);
            RetroCalls.instance().callType(ONFOLLOWCLICK)
                    .withUID()
                    .stringParam(items_list.get(pos).getPost_by())
                    .setOnFail((type_result, code, message) -> {
                        RestWork.changeFolowButton(follow_button, 1);
                    })
                    .unfollow_friend((type_result, obj) -> {
                        if (obj != null) {
                            PostModal ob = (PostModal) obj;
                            if (ob.getStatus() == 1) {
                                RestWork.changeFolowButton(follow_button, 0);
                                items_list.get(pos).updateFollow(0);
                            } else {
                                RestWork.changeFolowButton(follow_button, 1);
                                items_list.get(pos).updateFollow(1);
                            }
                        }
                    });
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
    }

    public void onPostComment(int pos){
        items_list.get(pos).updateTotal_comments();
        Log.d("findlist", String.valueOf(items_list));
        notifyItemChanged(pos);


    }


}
