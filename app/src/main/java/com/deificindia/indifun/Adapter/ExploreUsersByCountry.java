package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.Model.retro.TrendingResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.holders.HorizontalLoadingHolder;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.PaginationScrollListener;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.UiUtils.setAvtar;
import static com.deificindia.indifun.holders.HorizontalLoadingHolder.VIEW_TYPE_LOADING;

public class ExploreUsersByCountry extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<TrendingResult> list_country_user = new ArrayList<>();
    Context context;
    boolean isLoading;

    public ExploreUsersByCountry(Context context) {
        this.context = context;
    }

    public static final int USERS = 1;



    public void addData(List<TrendingResult> dataViews) {
        list_country_user.addAll(dataViews);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case USERS:
                return new ExploreHolderUser(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending, parent, false)
                );
            case VIEW_TYPE_LOADING:
                return new HorizontalLoadingHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_loading, parent, false)
                );
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case USERS:
                ((ExploreHolderUser)holder).bind(list_country_user.get(position));
                break;
            case VIEW_TYPE_LOADING:
                ((HorizontalLoadingHolder)holder).bind();
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list_country_user.get(position) == null ? VIEW_TYPE_LOADING : USERS;
    }

    @Override
    public int getItemCount() {
        return list_country_user == null ? 0 : list_country_user.size();
    }

    public void addLoadingView() {
        //add loading item
        isLoading = true;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                list_country_user.add(null);
                notifyItemInserted(list_country_user.size() - 1);
            }
        });
    }

    public void removeLoadingView() {
        //Remove loading item
        if(isLoading) {
            list_country_user.remove(list_country_user.size() - 1);
            notifyItemRemoved(list_country_user.size());
            isLoading = false;
        }
    }


    class ExploreHolderUser extends RecyclerView.ViewHolder{

        View parent_zoom;
        AppCompatImageView user_avtar;
        TextView tvname, tvwhasupp;
        LinearLayout tags;

        ImageView imgOnline;
        SimpleDraweeView simpleDraweeView;

        UserTags userTags;

        public ExploreHolderUser(@NonNull View itemView) {
            super(itemView);
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            imgOnline = itemView.findViewById(R.id.imgOnline);
            user_avtar = itemView.findViewById(R.id.imgAvtar);
            tvname = itemView.findViewById(R.id.tvName);
            tvwhasupp = itemView.findViewById(R.id.tvMessage);
            tags = itemView.findViewById(R.id.tagLay);
            simpleDraweeView = itemView.findViewById(R.id.simpleDraweeView);

            user_avtar.setOnClickListener(v->{
                ActivityUtils.openUserDetailsActivity3
                        (context, list_country_user.get(getAdapterPosition()).getId(),
                                list_country_user.get(getAdapterPosition()).getFull_name(),
                                parent_zoom);
            });

            userTags = UserTags.instance().container(tags);

        }

        void bind(TrendingResult data){
            tvname.setText(data.getFull_name());
            setAvtar(data.getProfile_picture(), context, user_avtar);

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


        }

    }


}
