package com.deificindia.indifun.deificpk.animutils;

import android.content.Context;
import android.graphics.drawable.LevelListDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.deificpk.utils.WidgetUtils;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.TagView;
import com.skydoves.androidribbon.ShimmerRibbonView;
import com.squareup.picasso.Picasso;


import static com.deificindia.indifun.Utility.LevelUtils.levelSetting;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;

public class GiftAnimView extends RelativeLayout {

    View parent;
    //ShimmerRibbonView myshimmer;
    CircleImageView imguser;
    TextView username;
    LinearLayout tagview;
    TextView message;

    String imgurl;
    String usrname;
    long xp = 0;
    String msg;

    public GiftAnimView(Context context) {
        super(context);
    }

    public GiftAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GiftAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_gift_anim_layout, this, true);

        parent = v.findViewById(R.id.parent);
        //myshimmer = v.findViewById(R.id.myshimmer);
        imguser = v.findViewById(R.id.imguser);
        username = v.findViewById(R.id.username);
        tagview = v.findViewById(R.id.layUserinfo);

        message = v.findViewById(R.id.message);

    }


    //public void setData(String userprofileimage, String imgurl, String uname, long xp, String msg){
    public void setData(String imgurl, String uname, long xp, String msg){
        this.imgurl = imgurl;
        this.usrname = uname;
        this.xp = xp;
        this.msg = msg;

        setupdata();
    }

   void setupdata(){
        if(imgurl!=null && imguser!=null) Picasso.get().load(UserImageBaseUrl+imgurl).into(imguser);
        if(usrname!=null && username!=null) username.setText(usrname);
        if(msg!=null && username!=null) message.setText(msg);

        if(tagview!=null){
            String l = LevelControll.getLevel(xp);
            tag(l, LevelControll.getUsernameColor(l), R.color.white);
        }

        String level = LevelControll.getLevel(this.xp);
       //bg(levelSetting(level));
   }

    void tag(String lvl, int bg, int text){
        if(tagview.getChildCount() > 0) tagview.removeAllViews();
       /* WidgetUtils.setChip(getContext(), tagview,
                bg,
                text,
                lvl + " Lvl.",
                WidgetUtils.getUriToResource(getContext(), R.drawable.ic_star3));*/

        UserTags.instance().container(tagview)
                .addTo(UserTags.OTHER)
                .update(bg, text,lvl + " Lvl.", R.drawable.ic_star3, 0);
    }



    void bg(int parentbg){
        if(parent!=null) parent.setBackgroundResource(parentbg>0?parentbg:R.drawable.bg_blue_gift);
        //if(myshimmer!=null) myshimmer.setBackgroundResource(parentbg>0?parentbg:R.drawable.bg_blue_gift);
    }



}
