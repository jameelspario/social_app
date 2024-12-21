package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.TopFansDays;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.bindingmodals.otheruserprofile.TopFans;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;

public class DaysTopFans extends RecyclerView.Adapter<DaysTopFans.ViewHolder> {


    Context context;
    List<TopFans> topFans;

    public DaysTopFans(Context context, List<TopFans> topFans) {
        this.context = context;
        this.topFans = topFans;
    }

    public void updatetopfans(List<TopFans> topFans){
        this.topFans=topFans;
        notifyDataSetChanged();
    }
    @NonNull
    @NotNull
    @Override
    public DaysTopFans.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_topcontent, parent, false);
        return new DaysTopFans.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DaysTopFans.ViewHolder holder, int position) {
        TopFans topFans11= topFans.get(position);
        holder.username.setText(topFans11.getFull_name());
        holder.calcultedcoin.setText(""+topFans11.getCoincollect());
        holder.userTags.addTo(GENDER)
                .updateGender(topFans11.getGender(),  topFans11.getGender());

        if(topFans11.getLevel() != null && !topFans11.getLevel().isEmpty()){

            holder.circleImageView.setBackgroundResource(LevelControll.getLevelFrame(topFans11.getLevel()));
            holder.userTags.addTo(LEVEL)
                    .updateLevel(topFans11.getLevel() + " Lvl");

        }
        Picasso.get()
                .load(URLs.UserImageBaseUrl+topFans11.getProfilepic())
                .error(R.drawable.avatar)
                .into(holder.circleImageView);
    }



    @Override
    public int getItemCount() {
        return topFans==null?0:topFans.size();
    }

    public void setClickListener(TopFansAdapater giftPostLayout) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout circlefan1;
        private CircleImageView circleImageView;
        private TextView username,calcultedcoin;
        private LinearLayout linearLayout;
        private AppCompatButton follow;
        private UserTags userTags;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            circlefan1=itemView.findViewById(R.id.circleframe);
            circleImageView=itemView.findViewById(R.id.circlefan1);
            username=itemView.findViewById(R.id.username);
            calcultedcoin=itemView.findViewById(R.id.contributed_count);
            linearLayout=itemView.findViewById(R.id.chips);
            follow=itemView.findViewById(R.id.follow_btn1);
            //  genderTag = itemView.findViewById(R.id.chips);
            userTags = UserTags.instance().container(linearLayout);


        }
    }



}
