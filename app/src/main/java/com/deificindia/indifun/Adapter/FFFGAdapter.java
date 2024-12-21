package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Activities.ProfileActivity;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.Model.retro.UserFollowerResult;
import com.deificindia.indifun.Model.retro.UserFollowingResult;
import com.deificindia.indifun.Model.retro.UserFriendsResult;
import com.deificindia.indifun.Model.retro.UserGroupListResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.rest.RestWork;
import com.deificindia.indifun.rest.RetroCallListener;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.TagView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import static com.deificindia.indifun.Utility.URLs.UserGroupBaseUrl;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;
import static com.deificindia.indifun.rest.RetroCallListener.ONFOLLOWCLICK;

public class FFFGAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int mParam1 = 0;
    private ImageView add_btn;


    List<UserFriendsResult> list_friends;
    List<UserFollowingResult> list_following;
    List<UserFollowerResult> list_follower;
    List<UserGroupListResult> list_grouplist;

    public static final int OTHER = 1;
    public static final int GROUPV = 2;

    public FFFGAdapter(Context context, int mParam1) {
        this.context = context;
        this.mParam1 = mParam1;
    }

    public void setList_friends(List<UserFriendsResult> list_friends) {
        this.list_friends = list_friends;
    }

    public void setList_following(List<UserFollowingResult> list_following) {
        this.list_following = list_following;
    }

    public void setList_follower(List<UserFollowerResult> list_follower) {
        this.list_follower = list_follower;
    }

    public void setList_grouplist(List<UserGroupListResult> list_grouplist) {
        this.list_grouplist = list_grouplist;
    }

    @Override
    public int getItemViewType(int position) {
        return mParam1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = null;
        switch (viewType) {
            case 0:
            case 1:
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fffg, parent, false);
                return new ViewHolderClass(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ffggroup, parent, false);
                return new GroupHolderUser(view);

        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                ((ViewHolderClass) holder).bindfriend(list_friends.get(position));
                break;
            case 1:
                ((ViewHolderClass) holder).bindfollowing(list_following.get(position));
                break;
            case 2:
                ((ViewHolderClass) holder).bindfollower(list_follower.get(position));
                break;
            case 3:
                ((GroupHolderUser) holder).bindgroup(list_grouplist.get(position));
                break;

        }
    }

    @Override
    public int getItemCount() {
        switch (mParam1) {
            case 0:
                return list_friends.size();
            case 1:
                return list_following.size();
            case 2:
                return list_follower.size();
            case 3:
                return list_grouplist.size();

        }
        return 0;
    }

    public void addFriends(List<UserFriendsResult> list) {
        this.list_friends = list;
        notifyDataSetChanged();
    }

    public void addFollowing(List<UserFollowingResult> list) {
        this.list_following = list;
        notifyDataSetChanged();
    }
    public void addFollowers(List<UserFollowerResult> list) {
        this.list_follower = list;
        notifyDataSetChanged();
    }


    class ViewHolderClass extends RecyclerView.ViewHolder{

        View parent_zoom;
        CircleImageView imgAvtar;
        ImageView genderimage, addbtn;
        TextView tvName, tvMessage, gendertext, levelText;
        LinearLayout lay;
        AppCompatButton unfollow;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            parent_zoom = itemView.findViewById(R.id.fffLayout);
            imgAvtar = itemView.findViewById(R.id.imgAvtar);

            addbtn = itemView.findViewById(R.id.add_btn);
            tvName = itemView.findViewById(R.id.tvName);
            //tvMessage = itemView.findViewById(R.id.tvMessage);
            lay = itemView.findViewById(R.id.gendertag);
            genderimage = itemView.findViewById(R.id.genderimage);
            gendertext = itemView.findViewById(R.id.gendertext);
            levelText = itemView.findViewById(R.id.levelText);
            unfollow = itemView.findViewById(R.id.button);


        }

        public void bindfriend(UserFriendsResult data){
            addbtn.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_friend));
            levelText.setText(data.getLevel());
            tvName.setText(data.getFull_name());
            if(data.getGender()!=null && data.getAge()!=null)
                genderTag(data.getGender(), data.getAge());
            // To Show Profile
            if (data.getProfile_picture() != null && !data.getProfile_picture().isEmpty()) {

                Picasso.get().load(UserImageBaseUrl+data.getProfile_picture())
                        .error(R.drawable.no_image)
                        .into(imgAvtar);
            } else {
                imgAvtar.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.no_image));
            }

            imgAvtar.setOnClickListener(v->{
                ActivityUtils.openUserDetailsActivity3(context, data.getFriendId(), data.getFull_name(),
                        parent_zoom);
            });

        }
        public void bindfollowing(UserFollowingResult data){
//            addbtn.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_add_friend));
            levelText.setText(data.getLevel());
            tvName.setText(data.getFull_name());
            frndIcon(data.getFrnd_icon());
            if(data.getGender()!=null && data.getAge()!=null)
                genderTag(data.getGender(), data.getAge());
            // To Show Profile
            if (data.getProfile_picture() != null && !data.getProfile_picture().isEmpty()) {

                Picasso.get().load(UserImageBaseUrl+data.getProfile_picture())
                        .error(R.drawable.no_image)
                        .into(imgAvtar);
            } else {
                imgAvtar.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.no_image));
            }

            imgAvtar.setOnClickListener(v->{
                ActivityUtils.openUserDetailsActivity3(context, data.getFollowingId(), data.getFull_name(),
                        parent_zoom
                );
            });

            unfollow.setVisibility(View.VISIBLE);
            RestWork.changeFolowButton("Unfollow", unfollow, 1);

            unfollow.setOnClickListener(v->{
                RestWork.changeFolowButton(unfollow, 2);
                RetroCalls.instance().callType(ONFOLLOWCLICK)
                        .withUID()
                        .stringParam(data.getFollowingId())
                        .setOnFail((type_result, code, message) -> {
                            RestWork.changeFolowButton("Unfollow", unfollow, 1);
                        })
                        .unfollow_friend((type_result, obj) -> {
                            if (obj != null) {
                                if (((PostModal) obj).getStatus() == 1) {
                                    //addbtn.setBackgroundResource(0);
                                    list_following.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                            }
                        });
            });

        }
        public void bindfollower(UserFollowerResult data){
            levelText.setText(data.getLevel());
//            addbtn.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_add_use));
            tvName.setText(data.getFull_name());
            frndIcon2(data.getFrnd_icon());
            if(data.getGender()!=null && data.getAge()!=null)
                genderTag(data.getGender(), data.getAge());
            // To Show Profile
            if (data.getProfile_picture() != null && !data.getProfile_picture().isEmpty()) {

                Picasso.get().load(UserImageBaseUrl+data.getProfile_picture())
                        .error(R.drawable.no_image)
                        .into(imgAvtar);
            } else {
                imgAvtar.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.no_image));
            }

            imgAvtar.setOnClickListener(v->{
                ActivityUtils.openUserDetailsActivity3(context, data.getUserId(), data.getFull_name(),
                        parent_zoom);
            });

        }


        void frndIcon2(int number){
            int frnd;
            if (number == 1){
                frnd = R.drawable.ic_follq;
                addbtn.setOnClickListener(v -> {
                    RetroCalls.instance().callType(ONFOLLOWCLICK)
                            .withUID()
                            .stringParam(list_follower.get(getAdapterPosition()).getUserId())
                            .setOnFail((type_result, code, message) -> {

                            })
                            .follow_user((type_result, obj) -> {
                                if(obj!=null){
                                    if(obj.getStatus()==1){
                                        frndIcon(number);
                                    }else {

                                    }
                                }
                            });
                            /*.listeners((call_type, obj) -> {


                            }, (type, code, message) -> {

                            }*/

                });
            }
            else{
                frnd = R.drawable.ic_friend;
//                add_btn.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_friends));
            }
            addbtn.setImageDrawable(ContextCompat.getDrawable(context,frnd));
        }

        void frndIcon(int number){
            int frnd;
            if (number == 1){
                frnd = R.drawable.ic_user__2_;
            }
            else{
                frnd = R.drawable.ic_friend;
//                add_btn.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_friends));
            }
            addbtn.setImageDrawable(ContextCompat.getDrawable(context,frnd));

        }

        void genderTag(String gender, String age){
            int colour;
            int gTag;
            if(gender.equals("Male")){
                gTag = R.drawable.ic_male_gender;
                colour = R.drawable.background_female;
//                lay.setBackgroundColor(colour);
            }else {
                colour = R.drawable.background_age;
                gTag = R.drawable.ic_female_sign;
            }
            gendertext.setText(age);
            genderimage.setImageDrawable(ContextCompat.getDrawable(context,gTag));
            lay.setBackgroundResource(colour);
//            lay.getTagText().setTextColor(context.getResources().getColor(R.color.black));


            //tag.setupViewData(R.drawable.btn_bg, gTag, age);

        }

        void setText(TextView tv, int img /*R.drawable.image*/){
            SpannableStringBuilder ssb = new SpannableStringBuilder(" Hello world!");
            ssb.setSpan(new ImageSpan(context, img), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tv.setText(ssb, TextView.BufferType.SPANNABLE);
        }
    }

    class GroupHolderUser extends RecyclerView.ViewHolder{
        CircleImageView GroupAvtar;
        ImageView LeaveGroup;
        TextView GroupName, GroupDiscription, distanceText, numberMember, line ;
        TagView tag;

        ConstraintLayout groupLayout;
        public GroupHolderUser(@NonNull View itemView) {
            super(itemView);
            GroupAvtar = itemView.findViewById(R.id.GroupAvtar);
            GroupName = itemView.findViewById(R.id.GroupName);
            LeaveGroup = itemView.findViewById(R.id.leaveGroup);
            distanceText = itemView.findViewById(R.id.distanceText);
            numberMember = itemView.findViewById(R.id.numberMember);
            GroupDiscription = itemView.findViewById(R.id.GroupDescription);
            groupLayout = itemView.findViewById(R.id.groupLayout);
            line = itemView.findViewById(R.id.line);

            groupLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent i = new Intent(context, ProfileActivity.class);
//                    i.putExtra("UID", IndifunApplication.getInstance().getCredential().getId());
//                    i.putExtra("NAME", IndifunApplication.getInstance().getCredential().getFullName());
//                    context.startActivity(i);
                }
            });

        }

        public void bindgroup(UserGroupListResult data){
            if (data.getDistance() != null){
                distanceText.setText(data.getDistance());
            }
            else{
                distanceText.setVisibility(View.GONE);
                line.setVisibility(View.GONE);

            }

            numberMember.setText(data.getMember()+" Members");
            GroupName.setText(data.getGroupName());
            GroupDiscription.setText(data.getDescription());

            //frndIcon3(data.getFrnd_icon());


            // To Show Profile
            if (data.getImage() != null && !data.getImage().isEmpty()) {

                Picasso.get().load(UserGroupBaseUrl+data.getImage())
                        .error(R.drawable.profile_image_1)
                        .into(GroupAvtar);
            } else {
                GroupAvtar.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.profile_image_1));
            }

        }

        void frndIcon3(int number){
            int frnd;
            if (number == 1){
                frnd = R.drawable.ic_delete_black_24dp;
//                addbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        RetroCalls.instance().callType(ONFOLLOWCLICK)
//                                .withUID(context)
//                                .stringParam(list_follower.get(getAdapterPosition()).getUserId())
//                                .listeners((call_type, obj) -> {
//                                    if(obj!=null){
//                                        PostModal ob = (PostModal) obj;
//                                        if(ob.getStatus()==1){
//                                            frndIcon(number);
//                                        }else {
//
//                                        }
//                                    }
//
//                                }, (type, code, message) -> {
//
//                                });
//                    }
//                });
            }
            else{
                frnd = R.drawable.delete;
//                add_btn.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_friends));
            }
            LeaveGroup.setImageDrawable(ContextCompat.getDrawable(context,frnd));
        }

    }
}
