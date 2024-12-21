package com.deificindia.indifun.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.retro.NewstarIndiaResult;
import com.deificindia.indifun.Model.retro.NewstarModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.ui.CircleImageView;

import static com.deificindia.indifun.Utility.UiUtils.setAvtar;

public class ExploreNewStarHolder<T> extends RecyclerView.ViewHolder{

    View parent_zoom;
    CircleImageView user_avtar;
    TextView tvname, tvwhasupp;
    LinearLayout tags;

    UserTags userTags;

    public ExploreNewStarHolder(@NonNull View itemView) {
        super(itemView);
        parent_zoom = itemView.findViewById(R.id.parent_zoom);
        user_avtar = itemView.findViewById(R.id.imgAvtar);
        tvname = itemView.findViewById(R.id.tvName);
        tvwhasupp = itemView.findViewById(R.id.tvMessage);
        tags = itemView.findViewById(R.id.tagLay);

        userTags = UserTags.instance().container(tags);
    }

    public void bind(T/*NewstarModal.MyResult*/ d){
        //if(d instanceof NewstarIndiaResult) {
        //    NewstarIndiaResult data = (NewstarIndiaResult) d;
        if(d instanceof NewstarModal.MyResult) {
            NewstarModal.MyResult data = (NewstarModal.MyResult) d;
            tvname.setText(data.getFull_name());
            setAvtar(data.getProfile_picture(), user_avtar.getContext(), user_avtar);

            if (data.getAge() != null && data.getGender() != null) {
                userTags.addTo(UserTags.GENDER)
                        .updateGender(data.getAge(), data.getGender());
            }

            user_avtar.setOnClickListener(v->{
                ActivityUtils.openUserDetailsActivity3
                        (user_avtar.getContext(), data.getId(),
                                data.getFull_name(),
                                parent_zoom
                        );
            });
        }
    }
}
