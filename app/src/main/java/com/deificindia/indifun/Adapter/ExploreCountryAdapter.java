package com.deificindia.indifun.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.Model.CountryUserResult;
import com.deificindia.indifun.Model.retro.TrendingResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.holders.HorizontalLoadingHolder;
import com.deificindia.indifun.ui.CircleImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.URLs.CountryFlagImages;
import static com.deificindia.indifun.Utility.UiUtils.setAvtar;
import static com.deificindia.indifun.holders.HorizontalLoadingHolder.VIEW_TYPE_LOADING;


public class ExploreCountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<CountryNamesResult.MyCountry> list_country = new ArrayList<>();

    Context context;

    public static final int COUNTRY = 1;

    int selectedCountry = 0;

    public ExploreCountryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case COUNTRY:
                return new ExploreHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false)
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
            case COUNTRY:
                ((ExploreHolder) holder).bind(list_country.get(position));
                break;
            case VIEW_TYPE_LOADING:
                ((HorizontalLoadingHolder)holder).bind();
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list_country.get(position) == null ? VIEW_TYPE_LOADING : COUNTRY;
    }

    @Override
    public int getItemCount() {
        return list_country == null ? 0 : list_country.size();
    }

    public void addLoadingView() {
        //add loading item
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                list_country.add(null);
                notifyItemInserted(list_country.size() - 1);
            }
        });
    }

    public void removeLoadingView() {
        //Remove loading item
        list_country.remove(list_country.size() - 1);
        notifyItemRemoved(list_country.size());
    }


    class ExploreHolder extends RecyclerView.ViewHolder{
        View parent;
        CircleImageView imgFlag;
        TextView placeholder;

        public ExploreHolder(@NonNull View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.image_flag);
            placeholder = itemView.findViewById(R.id.tvPlaceholder);
            parent = itemView.findViewById(R.id.parentlay);

            itemView.setOnClickListener(v->{
                //context.startActivity(new Intent(context, CountryWiseUser.class));
                selectedCountry = getAdapterPosition();
                if(_listener!=null) _listener.onCountryClick(getAdapterPosition(), list_country.get(getAdapterPosition()));

                notifyDataSetChanged();
            });
        }

        void bind(CountryNamesResult.MyCountry data) {

             if(data.getFlag()!=null && !data.getFlag().equals("")){
                 //placeholder.setVisibility(View.GONE);
                 Picasso.get().load(CountryFlagImages+data.getFlag()).into(imgFlag);
                    //Uri uri = Uri.parse(CountryFlagImages+s);
                    //imgFlag.setImageURI(uri);
             }
             placeholder.setText(data.getCountry());


            if(getAdapterPosition()==selectedCountry){
                //parent.setBackgroundColor(context.getResources().getColor(R.color.blue_dialog));
                parent.setBackgroundResource(R.drawable.bg_flag_draw_selected);
                placeholder.setTextColor(context.getResources().getColor(R.color.white));
            }else {
                //parent.setBackgroundColor(context.getResources().getColor(R.color.white));
                parent.setBackgroundResource(R.drawable.bg_flag_draw_white);
                placeholder.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }

    OnCountryClickListener _listener;

    public interface OnCountryClickListener{
        void onCountryClick(int pos, CountryNamesResult.MyCountry country);
    }

    public void setOnCountryClickListener(OnCountryClickListener listener){
        this._listener = listener;
    }

    public void updateCountry(List<CountryNamesResult.MyCountry> list){
        list_country.clear();
        list_country.addAll(list);
        notifyDataSetChanged();
    }



}
