package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.Recommendadmodel;
import com.deificindia.indifun.Model.abs.StringModal;
import com.deificindia.indifun.Model.abs_plugs.HotpostResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.deificpk.utils.WidgetUtils;
import com.deificindia.indifun.holders.HorizontalLoadingHolder;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.TagView;
import com.deificindia.indifun.ui.imagelistview.ImageListView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;
import static com.deificindia.indifun.Utility.UiUtils.setGenderTag;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.holders.HorizontalLoadingHolder.VIEW_TYPE_LOADING;

/*
*
* */
public class PostRecommendadAdapter extends AbstractAdapter<Recommendadmodel> {

    public PostRecommendadAdapter(Context activity) {
        super(activity);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        return new RecommendadViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.post_recommendad_adapter_layout, parent, false)
        );
    }

    @Override
    protected void bindViewHolder2(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder1(((RecommendadViewHolder) holder), position);
    }

    public void onBindViewHolder1(@NonNull RecommendadViewHolder holder, int position) {
        final Recommendadmodel mList = items_list.get(position);

        holder.recomname.setText(mList.getFullName());

        /*if(mList.getAge()!=null)
            holder.tagGender.getTagText().setText(mList.getAge());

        if(mList.getGender()!=null){
            setGenderTag(holder.tagGender, mList.getGender());
        }*/

        //UiUtils.setSmallGenderTag(context, holder.tagGender, mList.getGender(), mList.getAge());

        holder.userTags.addTo(GENDER)
                .updateGender(mList.getAge(),  mList.getGender());

        holder.userTags.addTo(UserTags.LEVEL)
                .updateLevel(mList.getLevel() + " Lvl");

        Picasso.get().load(mList.getProfilePicture())
                .into(holder.userimage);

        if(mList.getWhatsup()!=null){
            holder.tvWhatsup.setText(mList.getWhatsup());
        }else holder.tvWhatsup.setVisibility(View.GONE);

        if(mList.getIsBroadcasting().equalsIgnoreCase("1")){
            holder.liveLayout.setVisibility(View.VISIBLE);
            holder.imageListView.setVisibility(View.GONE);
            Picasso.get().load(UserImageBaseUrl+mList.getProfilePicture()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.liveLayout.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }else {
            holder.imageListView.setVisibility(View.VISIBLE);
            holder.liveLayout.setVisibility(View.GONE);
        }
        holder.userimage.setOnClickListener(v ->{
           /* ActivityUtils.openUserDetailsActivity(context,
                    mList.getId(),
                    mList.getFullName()
            );*/

            ActivityUtils.openUserDetailsActivity3(context(),
                    mList.getId(),
                    mList.getFullName(),
                    holder.parent_zoom);
        });

        holder.himessage.setOnClickListener(v->{
            sendHiiMessage(mList.getUid());
        });

    }

    private void sendHiiMessage(String id1) {

        RetroCalls.instance().withUID().stringParam(id1, "Hii...")
                .setOnFail((a,b,c)->{

                }).user_new_message((a,b)->{
            if(b!=null && b.getStatus()==1 && b.getResult()!=null){

            }
        });
    }


    public static class RecommendadViewHolder extends RecyclerView.ViewHolder {
        View parent_zoom;
        ImageView img1like;
        CircleImageView userimage, imgLive;
        TextView recomname, tvWhatsup;
        LinearLayout tagGender;
        View liveLayout;
        ImageListView imageListView;
        LinearLayout himessage;

        UserTags userTags;

        public RecommendadViewHolder(@NonNull View itemView) {
            super(itemView);
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
