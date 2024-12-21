package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deificindia.indifun.Adapter.CommentAdapter;
import com.deificindia.indifun.Adapter.ImageListAdapter;
import com.deificindia.indifun.Adapter.LikeAdapter;
import com.deificindia.indifun.Model.MyImage;
import com.deificindia.indifun.Model.PostCommentList_Responce;
import com.deificindia.indifun.Model.PostLikeList_Responce;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.R;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.LikeCommentPanel;
import com.deificindia.indifun.ui.TagView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.UiUtils.setGenderTag;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;

public class MomentNotification extends AppCompatActivity {

    private ImageView BACK, SENDBUTTON;
    private RecyclerView IMAGELAYOUT, LIKE_RECYCLE, COMMENT_RECYCLE;
    private CircleImageView MYPIC;
    private TextView MYNAME, LIKETIMING, TVWHATSUP;
    //private TagView MYGENDER;
    private LikeCommentPanel LIKE_COMENT;
    private EmojiconEditText emojiconEditText;
    LinearLayout tagslayout;
    UserTags userTags;

    private String mName, mPpic, mGender, mAge, mWhtsup, mTime, mTlike, mTcomment, mIslike, mPostId;
    private long mMilitime;
    private ArrayList<MyImage> imagesList;
//    private int mTlike, mTcomment, mIslike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_notification);

        BACK = findViewById(R.id.NotiBack);
        SENDBUTTON = findViewById(R.id.sendButton);
        IMAGELAYOUT = findViewById(R.id.imagelayout);
        LIKE_RECYCLE = findViewById(R.id.like_recycle);
        COMMENT_RECYCLE = findViewById(R.id.comment_recycle);
        MYPIC = findViewById(R.id.mypic);
        MYNAME = findViewById(R.id.myname);
        LIKETIMING = findViewById(R.id.liketiming);
        TVWHATSUP = findViewById(R.id.tvWatsap);
        tagslayout = findViewById(R.id.tagslayout);
        userTags = UserTags.instance().container(tagslayout);
        //MYGENDER = findViewById(R.id.mygender);
        //MYGENDER.init();

        LIKE_COMENT = findViewById(R.id.like_commentPanel);
        emojiconEditText = findViewById(R.id.emojicon_edit_text2);

        Intent mIntent = getIntent();
        mName = mIntent.getStringExtra("name");
        mGender = mIntent.getStringExtra("gender");
        mAge = mIntent.getStringExtra("age");
        mPpic = mIntent.getStringExtra("ppic");
        mWhtsup = mIntent.getStringExtra("whtsup");
        mIslike = mIntent.getStringExtra("islike");
        mMilitime = mIntent.getLongExtra("militime", 0);
        mTcomment = mIntent.getStringExtra("tcomment");
        mTime = mIntent.getStringExtra("time");
        mTlike = mIntent.getStringExtra("tlike");
        mPostId = mIntent.getStringExtra("postId");
        imagesList = mIntent.getParcelableArrayListExtra("imagelist");

        setPost();
        getLike(mPostId);
        getComment(mPostId);

        BACK.setOnClickListener(v -> OnBackPress());

        SENDBUTTON.setOnClickListener(v->{

        });

        emojiconEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendButtonControll();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        sendButtonControll();

    }

    void sendButtonControll(){
        SENDBUTTON.setEnabled(!emojiconEditText.getText().toString().trim().isEmpty());
    }

    private void getLike(String mPostId) {
        Call<PostLikeList_Responce> call = AppConfig.loadInterface().user_postlikes_list(mPostId);
        call.enqueue(new Callback<PostLikeList_Responce>() {
            @Override
            public void onResponse(Call<PostLikeList_Responce> call, Response<PostLikeList_Responce> response) {
                PostLikeList_Responce res = response.body();
                if(res.getResult()!=null && !res.getResult().isEmpty() && res.getStatus().equals("1")) {
                    LIKE_RECYCLE.setVisibility(View.VISIBLE);
                    LikeAdapter likeAdapter = new LikeAdapter(res.getResult(), MomentNotification.this);
                    LIKE_RECYCLE.setLayoutManager(new GridLayoutManager(MomentNotification.this, 6));
                    LIKE_RECYCLE.addItemDecoration(new EqualSpacingItemDecoration(5));
                    LIKE_RECYCLE.setAdapter(likeAdapter);
                }
            }

            @Override
            public void onFailure(Call<PostLikeList_Responce> call, Throwable t) {
                Toast.makeText(MomentNotification.this, "error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getComment(String mPostId) {
        Call<PostCommentList_Responce> call = AppConfig.loadInterface().user_postcomment_list(mPostId);
        call.enqueue(new Callback<PostCommentList_Responce>() {
            @Override
            public void onResponse(Call<PostCommentList_Responce> call, Response<PostCommentList_Responce> response) {
                PostCommentList_Responce result = response.body();
                if(result.getResult()!=null && !result.getResult().isEmpty() && result.getStatus().equals("1")){
                    COMMENT_RECYCLE.setVisibility(View.VISIBLE);
                    CommentAdapter commentAdapter = new CommentAdapter(result.getResult(), MomentNotification.this);
                    COMMENT_RECYCLE.setLayoutManager(new LinearLayoutManager(MomentNotification.this));
                    COMMENT_RECYCLE.addItemDecoration(new EqualSpacingItemDecoration(5));
                    COMMENT_RECYCLE.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onFailure(Call<PostCommentList_Responce> call, Throwable t) {
                Toast.makeText(MomentNotification.this, "error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPost() {
        if(imagesList!=null && imagesList.size()>0) {
            IMAGELAYOUT.setVisibility(View.VISIBLE);
            setImage(imagesList);
        }else IMAGELAYOUT.setVisibility(View.GONE);

        MYNAME.setText(mName);
        LIKE_COMENT.setLikeCount(mTlike);
        LIKE_COMENT.setComment(mTcomment);

        if(mMilitime>0){
            LIKETIMING.setText(Logger.dateFormat(mMilitime));
        }else LIKETIMING.setText(mTime);

        if (mPpic != null && !mPpic.isEmpty()) {
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+mPpic)
                    .placeholder(R.drawable.img_user_default)
                    .error(R.drawable.img_user_default)
                    .into(MYPIC);
        }

        if(mWhtsup!=null){
            TVWHATSUP.setVisibility(View.VISIBLE);
            TVWHATSUP.setText(mWhtsup);
        }else {
            TVWHATSUP.setVisibility(View.GONE);
        }

        //if(mAge!=null) MYGENDER.getTagText().setText(""+mAge);
        //if(mGender!=null) setGenderTag(MYGENDER, mGender);

        if(mAge!=null && mGender!=null){
            userTags
                    .addTo(GENDER)
                    .updateGender(mAge, mGender);
        }
    }

    private void setImage(ArrayList<MyImage> imagesList) {
        ImageListAdapter imageListAdapter = new ImageListAdapter(this, imagesList, 0);
        IMAGELAYOUT.setLayoutManager(new GridLayoutManager(this, 3));
        IMAGELAYOUT.addItemDecoration(new EqualSpacingItemDecoration(5));
        IMAGELAYOUT.setAdapter(imageListAdapter);
    }

    private void OnBackPress() {
        super.onBackPressed();
    }

}