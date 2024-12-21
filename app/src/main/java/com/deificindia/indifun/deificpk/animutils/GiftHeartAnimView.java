package com.deificindia.indifun.deificpk.animutils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.deificindia.chat.Model.User;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.DrawableUtils;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import static com.deificindia.indifun.deificpk.utils.Const.loge;

public class GiftHeartAnimView extends RelativeLayout {

    RelativeLayout relBg;
    CircleImageView imguser;
    TextView username;
    TextView tv_mesage, tv_no_of_heart;
    ImageView img_heart;

    String imgurl;
    String usrname;
    String msg;
    int parentbg;
    int gift_icon;
    String err;

    public GiftHeartAnimView(Context context) {
        super(context);
    }

    public GiftHeartAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GiftHeartAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void init(String imgurl, String uname, String msg, int gift_icon, int parentbg){
        setData(imgurl,uname, msg,gift_icon,parentbg);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.gift_heart_anim_view, this, true);

        relBg = v.findViewById(R.id.infoLay);
        imguser = v.findViewById(R.id.ima);
        username = v.findViewById(R.id.txt_name);
        tv_mesage = v.findViewById(R.id.txt_gift);
        img_heart = v.findViewById(R.id.heart_image);
        tv_no_of_heart = v.findViewById(R.id.tv_no_of_heart);

        if(imgurl!=null) {
            Picasso.get().load(imgurl).into(imguser);
        }
        if(username!=null) username.setText(usrname);
        if(msg!=null) tv_mesage.setText(msg);
        if(gift_icon>0)
            img_heart.setImageResource(gift_icon);

        if(parentbg>0) {
            DrawableUtils.changeDrawablecolor(getContext(),R.drawable.bg_gift_heart_anim_1, R.color.def_user_color);
            relBg.setBackgroundResource(R.drawable.bg_gift_heart_anim_1);
        }
    }


    public void setData(String imgurl, String uname, String msg, int gift_icon, int parentbg){
        this.imgurl = imgurl;
        this.usrname = uname;
        this.msg = msg;
        this.parentbg = parentbg;
        this.gift_icon = gift_icon;
    }
//
    public void updateGiftCount(int count){
        tv_no_of_heart.setText(count);
    }

    public TextView getTv_no_of_heart() {
        return tv_no_of_heart;
    }

    public ImageView getImg_heart() {
        return img_heart;
    }


}
