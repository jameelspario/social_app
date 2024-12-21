package com.deificindia.indifun.Adapter;

import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.COINSENDER;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.FOLLOWERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.FollowAddModel;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.Model.abs2Modals.LeaderBoardModel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.api;
import com.deificindia.indifun.db.LiveAppDb;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.rest.RetroCallListener;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardHolder>{

    int type;
    private List<LeaderBoardModel> items = new ArrayList<>();
    private Context context;

    public LeaderBoardAdapter(Context context) {
        this.context = context;
    }

    public void update(int typ, List<LeaderBoardModel> boardCoin) {
        this.type =typ;
        this.items = boardCoin;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LeaderBoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.diamond_profile, parent, false);
        LeaderBoardHolder viewHolder = new LeaderBoardHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardHolder holder, int position) {
        holder.bind2(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void follow(int pos, LeaderBoardModel modal){
        RetroCalls.instance()
                .withUID()
                .stringParam(modal.getUserId())
                .follow_user((type_result, obj2) -> {
                    if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null){
                        modal.setIs_following(1);
                        if(type==FOLLOWERS){
                            modal.setFollowersCount(modal.getFollowersCount()+1);
                        }

                        notifyItemChanged(pos, modal);
                    }
                });
    }

    class LeaderBoardHolder extends RecyclerView.ViewHolder {

        View parent_zoom;
        CircleImageView profile_image;
        ImageView icon_tag, gender, star;
        TextView txt1, txt2, txt4, txt5, lv1;
        LinearLayout relative3, tags_layout;
        RelativeLayout draw;
        FrameLayout profile_frame;
        SimpleDraweeView simpleDraweeView;

        UserTags userTags;

        public LeaderBoardHolder(@NonNull View itemView) {
            super(itemView);
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            profile_image = itemView.findViewById(R.id.circle_image);
            profile_frame = itemView.findViewById(R.id.profile_frame);
            simpleDraweeView = itemView.findViewById(R.id.simpledrawee);
            icon_tag = itemView.findViewById(R.id.icon_tag);

            tags_layout = itemView.findViewById(R.id.tags_layout);

            star = itemView.findViewById(R.id.star);
            txt1 = itemView.findViewById(R.id.txt1); //serial
            txt2 = itemView.findViewById(R.id.txt2); //name
            txt4 = itemView.findViewById(R.id.txt4); //msg
            txt5 = itemView.findViewById(R.id.txt5);  //number count

            draw = itemView.findViewById(R.id.draw);
            relative3 = itemView.findViewById(R.id.relative3);
            userTags = UserTags.instance().container(tags_layout);

        }

        void bind2(LeaderBoardModel data){
            txt2.setText(data.getFullName());

            Picasso.get().load(api.IMAGE_URL+data.getProfilePicture())
                    .error(ContextCompat.getDrawable(context, R.drawable.img_user_default))
                    .into(profile_image);

            if(data.getGender()!=null){
                userTags.addTo(UserTags.GENDER).updateGender(data.getAge(), data.getGender());
            }

            if(data.getLevel()!=null){
                userTags.addTo(UserTags.LEVEL).updateLevel(data.getLevel());
            }

            if(type==COINSENDER){
                txt4.setText("Sent: ");
                txt5.setText(""+data.getCoinCount());
                icon_tag.setImageResource(R.drawable.coin2);

                if(data.getCoinCount()>0){
                    profile_frame();
                }

            }else if(type==FOLLOWERS){
                txt4.setText("Gained: ");
                txt5.setText(""+data.getFollowersCount());
                icon_tag.setImageResource(R.drawable.person);

                if(data.getFollowersCount()>0){
                    profile_frame();
                }
            }

            profile_image.setOnClickListener(v->{
                ActivityUtils.openUserDetailsActivity3(context, data.getUserId(),
                        data.getFullName(),
                        parent_zoom

                );
            });

            if(data.getIs_live()>0){
                simpleDraweeView.setImageResource(R.drawable.ic_living);
            } else {
                if(data.getIs_following() == 0 ){
                    simpleDraweeView.setImageResource(R.drawable.add);
                }

                if(data.getIs_following()>0 && data.getIs_friend() ==0){
                    simpleDraweeView.setImageResource(R.drawable.tick_ok1);
                }

                if(data.getIs_following() > 0 && data.getIs_friend() > 0){
                    simpleDraweeView.setImageResource(R.drawable.ic_friend);
                }
            }

            simpleDraweeView.setOnClickListener(v->{
                if(data.getIs_live()>0) {
                    //open live broad
                    if(data.getFirebase_uid()!=null) {
                        LiveAppDb.databaseWriteExecutor.execute(()->{
                            LiveEntity2 ent = IndifunApplication.getInstance().liveDao.liveById(1, data.getFirebase_uid());
                            LiveAppDb.handler.post(()->{
                                ActivityUtils.joinBroad1(ent, 0, v);
                            });
                        });
                    }
                } else {
                    if(data.getIs_following() == 0 ){
                        simpleDraweeView.setEnabled(false);
                        follow(getBindingAdapterPosition(), data);
                    }
                }
            });

            if(getBindingAdapterPosition()>2){
                profile_frame.setVisibility(View.GONE);
                txt1.setVisibility(View.VISIBLE);
                txt1.setText(""+(getBindingAdapterPosition()+1));
            }else{
                txt1.setVisibility(View.GONE);
            }
        }


        void profile_frame(){
            if(getBindingAdapterPosition()==0)
                profile_frame.setBackgroundResource(R.drawable.crown_gold);

            if(getBindingAdapterPosition()==1)
                profile_frame.setBackgroundResource(R.drawable.crown_silver);

            if(getBindingAdapterPosition()==2)
                profile_frame.setBackgroundResource(R.drawable.crown_bronze);

        }


    }

}
