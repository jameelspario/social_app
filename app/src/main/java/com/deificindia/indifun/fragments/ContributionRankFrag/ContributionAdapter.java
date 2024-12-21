package com.deificindia.indifun.fragments.ContributionRankFrag;

import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.deificpk.utils.UserTags.OTHER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Adapter.LeaderBoardAdapter;
import com.deificindia.indifun.Model.TotalFamily;


import com.deificindia.indifun.Model.contribution.ContributionModel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ContributionAdapter extends RecyclerView.Adapter<ContributionAdapter.ContrivViewholder> {
    int type;
    private List<ContributionModel> items = new ArrayList<>();
private Context context;
    public ContributionAdapter(Context context) {
        this.context = context;
    }



    @SuppressLint("NotifyDataSetChanged")
    public void update( List<ContributionModel> boardCoin) {

        this.items = boardCoin;
        notifyDataSetChanged();
    }


    @NonNull
    @NotNull
    @Override
    public ContributionAdapter.ContrivViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.contribution_rank, parent, false);
        ContributionAdapter.ContrivViewholder viewHolder = new ContributionAdapter.ContrivViewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ContributionAdapter.ContrivViewholder holder, int position) {
        ContributionModel contributionModel= items.get(position);

        holder.mcontri_id.setText(contributionModel.getUser_id());
        System.out.print("fusername"+contributionModel.getName());
        holder.mcountri_username.setText(contributionModel.getName());
        holder.mcontri_contribution.setText(""+contributionModel.getCoin());
        Picasso.get().load(URLs.UserImageBaseUrl+contributionModel.getAvtar())
                .placeholder(R.drawable.no_image)
                .into(holder.mcontri_circle);
        holder.userTags.addTo(GENDER)
                .updateGender(contributionModel.getAge(),contributionModel.getGender());
        holder.mcontri_circle.setOnClickListener(v ->{
            ActivityUtils.openUserDetailsActivity3(context,
                    contributionModel.getUser_id(),
                    contributionModel.getName(),
                    holder.parent_zoom);
        });
    }

    @Override
    public int getItemCount() {
        return items==null ? 0 : items.size();
    }


    public class ContrivViewholder extends RecyclerView.ViewHolder {
        private CircleImageView mcontri_circle;
        private TextView mcontri_id,mcountri_username,mcontri_contribution;
        private LinearLayout mcontri_ages,mcontri_gender;
        private UserTags userTags,usert1;
        View parent_zoom;
        public ContrivViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            mcontri_circle=itemView.findViewById(R.id.mcontri_circleimage);
            mcontri_id=itemView.findViewById(R.id.mcontri_ids);
            mcountri_username=itemView.findViewById(R.id.mcontri_username);
            mcontri_contribution=itemView.findViewById(R.id.mcontri_contributionrank);
            mcontri_ages=itemView.findViewById(R.id.chips);;
            parent_zoom = itemView.findViewById(R.id.parent_zoom);
            userTags = UserTags.instance().container(mcontri_ages);

        }
    }
}
