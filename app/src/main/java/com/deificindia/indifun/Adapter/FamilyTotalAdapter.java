package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Activities.FamilyDetailsJoin;
import com.deificindia.indifun.Model.TotalFamily;
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

public class FamilyTotalAdapter extends RecyclerView.Adapter<FamilyTotalAdapter.ViewHolder> {


    Context context;
    List<TotalFamily> topFans;

    public FamilyTotalAdapter(Context context, List<TotalFamily> topFans) {
        this.context = context;
        this.topFans = topFans;
    }

    public void updatetopfans(List<TotalFamily> topFans){
        this.topFans=topFans;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public FamilyTotalAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_family_total, parent, false);
        return new FamilyTotalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FamilyTotalAdapter.ViewHolder holder, int position) {
        TotalFamily topFans11= topFans.get(position);
        holder.username.setText(topFans11.getFamily_name());
        holder.find_ranking.setText(topFans11.getTotal_member());
        holder.totalcount.setText(topFans11.getTotal_point());
        holder.max_user.setText(topFans11.getMax_user());

            holder.findid.setText(""+topFans11.getFamily_id());

        if(topFans11.getLevel() != null && !topFans11.getLevel().isEmpty()){

        //    holder.circleImageView.setBackgroundResource(LevelControll.getLevelFrame(topFans11.getLevel()));
          try{
              holder.userTags.addTo(LEVEL)
                      .updateLevel(topFans11.getLevel() + " Lvl", Color.parseColor(topFans11.getColor()));


          }catch (Exception e){

          }

        }
        Picasso.get()
                .load(URLs.FamilYUserIcon+topFans11.getFamily_icon())
                .error(R.drawable.avatar)
                .into(holder.circleImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details=new Intent(context, FamilyDetailsJoin.class);
                details.putExtra("family_id",""+topFans11.getFamily_id());
//                details.putExtra("family_name",""+topFans11.getFamily_id());
//                details.putExtra("family_level",""+topFans11.getFamily_id());
//                details.putExtra("family_id",""+topFans11.getFamily_id());
                context.startActivity(details);
            }
        });
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
        private TextView username,calcultedcoin,totalcount,find_ranking,max_user,findid;
        private LinearLayout linearLayout;
        private AppCompatButton follow;
        private UserTags userTags;
        private FrameLayout findframe;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
       //     circlefan1=itemView.findViewById(R.id.circleframe);
            circleImageView=itemView.findViewById(R.id.circlefan1);
            username=itemView.findViewById(R.id.username);
            calcultedcoin=itemView.findViewById(R.id.contributed_count);
            linearLayout=itemView.findViewById(R.id.chips);
            totalcount=itemView.findViewById(R.id.totalcount);
            find_ranking=itemView.findViewById(R.id.contribu);
            totalcount=itemView.findViewById(R.id.totalcount);
            max_user=itemView.findViewById(R.id.contribu1);
            findframe=itemView.findViewById(R.id.findframe);
            findid=itemView.findViewById(R.id.findid);
        //    follow=itemView.findViewById(R.id.follow_btn1);
            //  genderTag = itemView.findViewById(R.id.chips);
            userTags = UserTags.instance().container(linearLayout);


        }
    }



}
