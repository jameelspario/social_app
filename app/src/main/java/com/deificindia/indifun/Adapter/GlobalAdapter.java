package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.holders.ExploreNewStarHolder;
import com.deificindia.indifun.holders.ExploreTrendingHolder;
import com.deificindia.indifun.holders.HorizontalLoadingHolder;

import java.util.ArrayList;
import java.util.List;


import static com.deificindia.indifun.Utility.UiUtils.setAvtar;
import static com.deificindia.indifun.holders.HorizontalLoadingHolder.VIEW_TYPE_LOADING;

public class GlobalAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int NEWSTAR = 0;
    public static final int COUNTRY = 1;
    public static final int TRENDING = 2;

    int view_type = 0;
    public int user_rank = 0;
    Context context;

    List<T> items_list = new ArrayList<>();
    private boolean isLoading;

    public GlobalAdapter(int view_type, Context context) {
        this.view_type = view_type;
        this.context = context;
    }

    public void clearData(){
        items_list.clear();
        notifyDataSetChanged();
    }

    public void addData(List<T> dataViews) {
        items_list.addAll(dataViews);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case NEWSTAR:
                return new ExploreNewStarHolder<>(
                        LayoutInflater.from(context).inflate(R.layout.item_newstar, parent, false)
                );
            case COUNTRY:
                return new CountryHolder<>(
                        LayoutInflater.from(context).inflate(R.layout.item_country, parent, false)
                );
            case TRENDING:
                return new ExploreTrendingHolder<>(
                        LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)
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
            case NEWSTAR:
                ((ExploreNewStarHolder) holder).bind(items_list.get(position));
                break;
            case COUNTRY:
                ((CountryHolder) holder).bind(items_list.get(position));
                break;
            case TRENDING:
                ((ExploreTrendingHolder)holder).bind(items_list.get(position));
                break;
            case VIEW_TYPE_LOADING:
                ((HorizontalLoadingHolder)holder).bind();
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items_list.get(position)==null ? VIEW_TYPE_LOADING : view_type;
    }

    @Override
    public int getItemCount() {
        return items_list==null?0:items_list.size();
    }

    public void addLoadingView() {
        //add loading item
        isLoading = true;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                items_list.add(null);
                notifyItemInserted(items_list.size() - 1);
            }
        });
    }

    public void removeLoadingView() {
        //Remove loading item
        if(isLoading) {
            items_list.remove(items_list.size() - 1);
            notifyItemRemoved(items_list.size());
            isLoading = false;
        }
    }

    /*----view holder classes-----*/
    class CountryHolder<T> extends RecyclerView.ViewHolder{

        ImageView imgFlag;
        TextView placeholder;

        public CountryHolder(@NonNull View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.imgFlag);
            placeholder = itemView.findViewById(R.id.tvPlaceholder);

        }

        public void bind(T d/*String imgurl, String country*/){

        }
    }



}
