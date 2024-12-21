package com.deificindia.indifun.deificpk.animutils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deificindia.indifun.R;

import static com.deificindia.indifun.Utility.UiUtils.onUserJoinAnimation;

public class RoomEnterAnima extends FrameLayout {

    public RelativeLayout parent, head, body, tail;
    ImageView imgHead, imgTail;
    public TextView _userName, _tv_level;


    public RoomEnterAnima(@NonNull Context context) {
        super(context);
    }

    public RoomEnterAnima(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomEnterAnima(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RoomEnterAnima(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(String usrnam, String level){
       View view = LayoutInflater.from(getContext()).inflate(R.layout.room_enter_anima_layout, this, true);

        parent = view.findViewById(R.id.parent);
        head = view.findViewById(R.id.head_layout);
        body = view.findViewById(R.id.body_layout);
        tail = view.findViewById(R.id.tail_layout);

        imgHead = view.findViewById(R.id.img_head);
        imgTail = view.findViewById(R.id.img_tail);

        _tv_level = view.findViewById(R.id.tv_level);
        _userName = view.findViewById(R.id.tv_username);

        Spannable span = onUserJoinAnimation(usrnam, "joined channel", Color.WHITE);
        _userName.setText(span);

        if(level=="2"){
            imgHead.setImageResource(R.drawable.anihead02);
        }else if(level=="3"){
            imgHead.setImageResource(R.drawable.anihead03);
        }else if(level=="4"){
            imgHead.setImageResource(R.drawable.anihead04);
        }

     //   levelSetting(level);

    }

    void levelSetting(long level){
        switch ((int) level){
            case 0: level_0_1(level); break;
            case 1: level_0_1(level); break;
            case 2: level_0_1(level); break;
            case 3: level_0_1(level); break;
            case 4: level_0_1(level); break;
            case 5: level_0_1(level); break;
            case 6: level_0_1(level); break;
            case 7: level_0_1(level); break;
            case 8: level_0_1(level); break;
            case 9: level_0_1(level); break;
            default: level_0_1(level);
        }
    }

    void level_0_1(long level){
        parent.setBackgroundResource(R.drawable.entry_1_5);
        head.setBackgroundResource(R.drawable.entry_tail_1_5);
        imgHead.setImageResource(R.drawable.entry_head_1_5);
        body.setBackgroundResource(R.drawable.entry_body__1_5);
        //tail.setBackgroundResource(R.drawable.body_layout);
        //imgTail.setImageResource(R.drawable.entry_tail_1_5);
        _tv_level.setText(level + " Lvl");
    }

    /*void level_1_2(String level){
        parent.setBackgroundResource(R.color.av_green);
        head.setBackgroundResource(R.color.colorAccent);
        imgHead.setImageResource(R.drawable.emoji_261d);
        body.setBackgroundResource(R.drawable.emoji_1f30d);
        //tail.setBackgroundResource(R.drawable.body_layout);
        //imgTail.setImageResource(R.drawable.entry_tail_1_5);
        _tv_level.setText(level + " Lvl");
    }*/
}
