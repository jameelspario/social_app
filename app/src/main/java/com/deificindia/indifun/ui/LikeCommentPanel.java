package com.deificindia.indifun.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.DrawableUtils;

public class LikeCommentPanel extends RelativeLayout implements View.OnClickListener {

    OnComentClick _listener1;
    OnLikeClick _listener2;

    public void setOnUserClickListener(OnComentClick listener1, OnLikeClick listener2){
        this._listener1 = listener1;
        this._listener2 = listener2;
    }

    public TextView like, comment;
    public AppCompatImageView likeButton, iv_comment;

    LinearLayout like_panel, comment_panel;

    public LikeCommentPanel(Context context) {
        super(context);
        initView(context);
    }

    public LikeCommentPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LikeCommentPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context){

        View v = LayoutInflater.from(context).inflate(R.layout.like_comment_panel, this, true);

        like_panel = v.findViewById(R.id.like_panel);
        comment_panel = v.findViewById(R.id.comment_panel);

        likeButton = v.findViewById(R.id.likeimage);
        iv_comment = v.findViewById(R.id.commentimage);

        like = v.findViewById(R.id.tv_likecount);
        comment = v.findViewById(R.id.tv_commentcount);

        like_panel.setOnClickListener(this);
        comment_panel.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.like_panel:
                if(_listener2!=null) _listener2.onLike();
                break;
            case R.id.comment_panel:
                if(_listener1!=null) _listener1.onComment();
                break;
        }
    }

    public void likeEnable(boolean b){
        like_panel.setEnabled(b);
    }

    public void setLikeCount(String likes){ this.like.setText(likes); }

    public void setIsLiked(int b){

        int draw = b>0?R.color.red:R.color.gray;
        //likeButton.setBackgroundTintList(draw);
        DrawableUtils.changeFill(getContext(),likeButton, draw);

        //like_panel.setEnabled(b>0?true:false);
    }

    public void setComment(String comm){ this.comment.setText(comm); }

    public interface OnComentClick{
        void onComment();
    }

    public interface OnLikeClick{
        void onLike();
    }


}
