package com.deificindia.indifun.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.retro.TrendingResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.ui.CircleImageView;
import com.facebook.drawee.view.SimpleDraweeView;

import static com.deificindia.indifun.Utility.UiUtils.setAvtar;

public class ExploreTrendingHolder<T> extends RecyclerView.ViewHolder{
    View parent_zoom;
    CircleImageView user_avtar;
    TextView tvname, tvwhasupp;
    LinearLayout tags;

    ImageView imgOnline;
    SimpleDraweeView simpleDraweeView;

    UserTags userTags;

    public ExploreTrendingHolder(@NonNull View itemView) {
        super(itemView);
        parent_zoom = itemView.findViewById(R.id.parent_zoom);
        imgOnline = itemView.findViewById(R.id.imgOnline);
        user_avtar = itemView.findViewById(R.id.imgAvtar);
        tvname = itemView.findViewById(R.id.tvName);
        tvwhasupp = itemView.findViewById(R.id.tvMessage);
        tags = itemView.findViewById(R.id.tagLay);
        simpleDraweeView = itemView.findViewById(R.id.simpleDraweeView);

        userTags = UserTags.instance().container(tags);

    }

    public void bind(T d){

        if(d instanceof TrendingResult){
            TrendingResult data = (TrendingResult) d;
            tvname.setText(data.getFull_name());
            setAvtar(data.getProfile_picture(), user_avtar.getContext(), user_avtar);

            //user_rank++;
            //tvRank.setText(String.valueOf(user_rank));
            if(data.getWhatsup()!=null)
                tvwhasupp.setText(data.getWhatsup());

            if(data.getAge()!=null && data.getGender()!=null){
                //UiUtils.setGenderTag(context, tags, data.getGender(),data.getAge());
                userTags.addTo(UserTags.GENDER)
                        .updateGender(data.getAge(),  data.getGender());
            }

            userTags.addTo(UserTags.LEVEL)
                    .updateLevel((data.getLevel()>0?data.getLevel():0) +" Lvl");

            if(data.getIs_online()>0){
                imgOnline.setBackgroundResource(R.drawable.bg_online);
            }else imgOnline.setBackgroundResource(R.drawable.bg_offline);

            if(data.getIs_broadcasting()>0){
                UiUtils.webpSupport(simpleDraweeView, R.drawable.ic_living);
            }

            user_avtar.setOnClickListener(v->{
                ActivityUtils.openUserDetailsActivity3
                        (user_avtar.getContext(), data.getId(),
                                data.getFull_name(),
                                parent_zoom);
            });
        }


    }
}
