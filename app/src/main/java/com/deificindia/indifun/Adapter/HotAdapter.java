package com.deificindia.indifun.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
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
import com.deificindia.indifun.R;

import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.Indifun;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.db.LiveEntity;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.deificindia.indifun.Utility.Logger.loge;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.LiveHotHolder> {

    private List<Post> hotmodelList = new ArrayList<>();
    private Context context;
    FirebaseUser firebaseUser;
    String broadusername,broadid,broadkey;
    String roomid,gender,title1,imageu;

    public HotAdapter(Context context, List<Post> postList) {
        this.context = context;
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

    }

    public void update(List<Post> postList){
        this.hotmodelList=postList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotAdapter.LiveHotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     //   LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.hot_adapter_layout, parent, false);
//        return new LiveHotHolder(view);
        View view = LayoutInflater.from(context).inflate(R.layout.hot_adapter_layout, parent, false);
        return new LiveHotHolder(view);
    }
    public int getRandomColorCode(){

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(HotAdapter.LiveHotHolder holder, int position) {
        final Post data = hotmodelList.get(position);


//        holder.hotusername.setText(data.getTitle());
//        Timber.tag("aaaa").d(data.getTitle());
        roomid=data.getRoomid();

        holder.cv.setCardBackgroundColor(getRandomColorCode());
        loge("roomid",data.getRoomid());
         broadusername=data.getOwnername();
         broadid=data.owneruid;
         holder.title.setText(data.getRoomname());
         broadkey=data.owneruid;
         gender=data.ogender;
         title1=data.getRoomname();
        imageu=data.getOwneravtar();
        holder.hotusername.setText(data.getOwnername());
//        loge("username",data.getFull_name());
        //loge("username1",data.getOwneravtartype());

        if (data.getOwneravtar() != null && !data.getOwneravtar().isEmpty()) {
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+data.getOwneravtar())
                    .error(R.drawable.no_image)
                    .into(holder.imgicon);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage_12();
                uploadImage_123();
                holder.animationView.pauseAnimation();
               // Log.d("dd", String.valueOf(data.broadid) +data.is_blocked);
               // loge("ddrtrt", String.valueOf(data.getBroadid()) +data.is_blocked);
            if(data.getBroadid()> 0  && data.is_blocked < 1) {

                Clip clp = new Clip();

                clp.id = data.getId();
                clp.isowner = false;
                clp.n = 0;
                clp.ownerfuid = data.owneruid;
                clp.roomid = data.getRoomid();
                clp.owneravtar = data.getOwneravtar();//*/data.owneravtartype==0?res.getProfilePicture():data.image;
                clp.avtartype = data.getOwneravtartype();
                clp.ownername = data.getOwnername();
                clp.is_following = data.is_following;

                clp.is_kick = data.is_kick;
                clp.is_blocked = data.is_blocked;
                clp.is_mute = data.is_mute;

                clp.broad_join_identity = Constants.roomIdentity(false, data.owneruid);

                //ActivityUtils.joinBroad1(context, clp, holder.itemView);
                //ActivityUtils.joinBroad23(context, data, 0, holder.itemView);

            }
            else
                Toast.makeText(context, "This broadcast could not be joined.", Toast.LENGTH_LONG).show();

            }




        });


        if(data.ogender!=null){
            UserTags.instance().container(holder.tagviewGender)
                    .addTo(UserTags.GENDER)
                    .updateGender(data.oage, data.ogender);
        }
//        if(data.lattitude!=null && data.longitude!=null){
//
//            Location startPoint=new Location("locationA");
//            String c = data.getLattitude();
//            String d =data.getLongitude();
//            startPoint.setLatitude(Double.parseDouble(c));
//            startPoint.setLongitude(Double.parseDouble(d));
//            loge("DISTANCEq", c+d);
//
//            Location endPoint=new Location("locationA");
//            String a = data.getLattitude();
//            String b = data.getLongitude(); ;
//            endPoint.setLatitude(Double.parseDouble(a));
//            endPoint.setLongitude(Double.parseDouble(b));
//            loge("DISTANCEw", a+b);
//            double distance=startPoint.distanceTo(endPoint) /100;
//loge("DISTANCE1", String.valueOf(distance));
//            loge("DISTANCE", String.valueOf(distance));
//            Formatter formatter = new Formatter();
//            formatter.format("%.2f", distance);
//            holder.tqp.setText(formatter.toString()+"miles away");
//        }else{
//            Toast.makeText(context, "location null", Toast.LENGTH_SHORT).show();
//            holder.tqp.setText("Location not found");
//        }





//        switch ((int)data.state){
//            case STAT_CALL_MODE:
//                holder.live_type_item.setBackgroundResource(R.drawable.bg_live_1);
//                break;
//            case STAT_PK_MODE:
//                holder.live_type_item.setBackgroundResource(R.drawable.bg_live_2);
//                break;
//            default:
//                holder.live_type_item.setBackgroundResource(0);
//        }

    }

    public static class LiveHotHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView imgicon;
        public TextView hotusername, tvwhatsup,tqp ,location,title;
        public TagView hotfriends;
        public LinearLayout tagviewGender, live_type_item;
        CardView cv  ;
        public LottieAnimationView animationView;
//        public View itemView;
        public LiveHotHolder(View itemView) {
            super(itemView);
            animationView=itemView.findViewById(R.id.animation_view);
            imgicon = itemView.findViewById(R.id.hotimage);
            cv = itemView.findViewById(R.id.avtarCard) ;
            hotusername = itemView.findViewById((R.id.hotusername));
            tvwhatsup = itemView.findViewById((R.id.tvwhatsup));
            tqp = itemView.findViewById((R.id.tvwhatsupone));
            title = itemView.findViewById((R.id.title));
            //tqp = itemView.findViewById((R.id.tvwhatsupone));

            hotfriends = itemView.findViewById((R.id.tagviewFollower));
            hotfriends.init();

            tagviewGender = itemView.findViewById((R.id.tagviewGender));
            live_type_item = itemView.findViewById((R.id.live_type_item));



        }


    }

    @Override
    public int getItemCount() {
        Log.e("getcount ", String.valueOf(hotmodelList));
        return hotmodelList.size();
    }
    private void uploadImage_12() {
        Result r= IndifunApplication.getInstance().getCredential();
        DatabaseReference rf = FirebaseDatabase.getInstance().getReference("GOLIVE1");
         String rw=rf.push().getKey();
        boolean isgolive=true;
        HashMap<String, Object> hashMap = new HashMap<>();
       hashMap.put("Joiner_id",FirebaseAuth.getInstance().getCurrentUser().getUid());
       hashMap.put("Joiner_name",r.getFullName());
       hashMap.put("broad_user",broadusername);
        hashMap.put("broad_id",firebaseUser.getUid());
        hashMap.put("owneruid",broadkey);
        hashMap.put("isgolive",isgolive);
        hashMap.put("roomid",roomid);
//        hashMap.put("image",r.getProfilePicture());
        hashMap.put("image",r.getProfilePicture());
        rf.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMap);
    }
    private void uploadImage_123() {
        Result r= IndifunApplication.getInstance().getCredential();
        DatabaseReference rf = FirebaseDatabase.getInstance().getReference("BroadWtacher");
        String rw=rf.push().getKey();
        boolean isgolive=true;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Joiner_id",FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("Joiner_name",r.getFullName());
        hashMap.put("broad_user",broadusername);
        hashMap.put("broad_id",firebaseUser.getUid());
        hashMap.put("owneruid",broadkey);
        hashMap.put("isgolive",isgolive);
        hashMap.put("roomid",roomid);
        hashMap.put("gender1",gender);
        hashMap.put("title1",title1);
        hashMap.put("image",imageu);
     //   hashMap.put("image",r.getProfilePicture());
        rf.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(rw).setValue(hashMap);
    }
}
