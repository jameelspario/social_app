package com.deificindia.indifun.Adapter;

import static com.deificindia.indifun.Utility.Logger.loge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.Model.retro.LiveResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.db.LiveEntity;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.deificpk.modals.Clip;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.Const;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.modals.Post;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.ui.TagView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class HotAdapter2 extends RecyclerView.Adapter<HotAdapter2.LiveHotHolder> {

    private List<LiveResult> hotmodelList = new ArrayList<>();
    private Context context;
    FirebaseUser firebaseUser;
    String broadusername, broadid, broadkey;
    String roomid, gender, title1, imageu;

    public void update(List<LiveResult> postList) {
        this.hotmodelList = postList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotAdapter2.LiveHotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //   LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.hot_adapter_layout, parent, false);
//        return new LiveHotHolder(view);
        View view = LayoutInflater.from(context).inflate(R.layout.hot_adapter_layout, parent, false);
        return new LiveHotHolder(view);
    }

    public static int getRandomColorCode() {

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(HotAdapter2.LiveHotHolder holder, int position) {
        final LiveResult data = hotmodelList.get(position);
        holder.bind(data);

    }

    @Override
    public int getItemCount() {
        return hotmodelList.size();
    }

    public static class LiveHotHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView imgicon;
        public TextView hotusername, whatsup, title, followers;
        public LinearLayout tagviewGender, live_type_item;
        private CardView cv;
        public LottieAnimationView animationView;

        public LiveHotHolder(View itemView) {
            super(itemView);

            imgicon = itemView.findViewById(R.id.hotimage);

            animationView = itemView.findViewById(R.id.animation_view);
            tagviewGender = itemView.findViewById((R.id.tagviewGender));
            hotusername = itemView.findViewById((R.id.hotusername));
            followers = itemView.findViewById((R.id.txt_followers));

            live_type_item = itemView.findViewById((R.id.live_type_item));

            cv = itemView.findViewById(R.id.avtarCard);

            whatsup = itemView.findViewById((R.id.tvwhatsupone));
            title = itemView.findViewById((R.id.title));

        }

        void bind(LiveResult data) {

            //cv.setCardBackgroundColor(getRandomColorCode());
            //title.setText(data.roomname);
            hotusername.setText(data.getUser_name());

            if (data.getImage() != null && !data.getImage().isEmpty()) {
                // String avtar = (data.owneravtartype == 0 ? URLs.UserImageBaseUrl : URLs.UserImageBaseUrlTemp) + data.owneravtar;
                Picasso.get()
                        .load(URLs.UserImageBaseUrl + data.getImage())
                        .error(R.drawable.no_image)
                        .into(imgicon);
            }

            if (data.getGender() != null) {
                UserTags.instance().container(tagviewGender)
                        .addTo(UserTags.GENDER)
                        .updateGender(data.getGender(), data.getGender());

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    animationView.pauseAnimation();
//
//                    Clip clp = new Clip();
//
//                    clp.broad_join_identity = Constants.roomIdentity(false, data.owneruid);
//
//                    ActivityUtils.joinBroad1(data, Const.KEY_TAB_UNIVERSAL, itemView);
//
//                }
//
//            });

                TextView tv = new TextView(live_type_item.getContext());
                tv.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(param);
                if (data.state == 3) {
                    live_type_item.addView(tv);
                    tv.setText("PK");
                } else if (data.state == 7) {
                    live_type_item.addView(tv);
                    tv.setText("CALLING");
                } else {
                    live_type_item.removeAllViews();
                }


            }


        }


    }
}
