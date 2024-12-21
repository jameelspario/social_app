package com.deificindia.indifun.deificpk.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.deificindia.indifun.R;
import com.deificindia.indifun.ui.AgoraLvl;

import static com.deificindia.indifun.Utility.Logger.logpk;

public class ItemLayout extends LinearLayout {

    protected AgoraLvl tagLevel, tagDiamond, tagHeart, tagStar;

    public ItemLayout(Context context) {
        super(context);
        initView(context);
    }

    public ItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.item_item_layout, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setupTags();
    }

    public void setupTags(){
        tagLevel = findViewById(R.id.tagLevel);
        tagDiamond = findViewById(R.id.tagDiamond);
        tagHeart = findViewById(R.id.tagHeart);
        tagStar = findViewById(R.id.tagStar);


        tagLevel.setTagImage(R.drawable.stars);
        tagDiamond.setTagImage(R.drawable.gift_07_diamond);
        tagHeart.setTagImage(R.drawable.heart_on);
        tagStar.setTagImage(R.drawable.star_on);

        tagLevel.changeBg(R.drawable.bg_tag_fill_stroke_1);
        tagDiamond.changeBg(R.drawable.bg_tag_fill_stroke_1);
        tagHeart.changeBg(R.drawable.bg_tag_fill_stroke_1);
        tagStar.changeBg(R.drawable.bg_tag_fill_stroke_1);

       /* tagLevel.getParent1().setPadding(15, 5, 15, 5);
        tagDiamond.getParent1().setPadding(15, 5, 15, 5);
        tagHeart.getParent1().setPadding(15, 5, 15, 5);
        tagStar.getParent1().setPadding(15, 5, 15, 5);*/
    }

    public void setData(String user_level, String user_diamond, String user_heart, String user_xp){

        logpk("ItemLay", "setData", user_level, user_diamond, user_heart, user_xp );

        if(user_level!=null && user_level.equals("")){
            tagLevel.setVisibility(VISIBLE);
            tagLevel.getTagText().setText(user_level);
        }else {

        }

        if(user_diamond!=null && user_diamond.equals("")){
            tagDiamond.setVisibility(VISIBLE);
            tagDiamond.getTagText().setText(user_diamond);
        }else {

        }

        if(user_heart!=null && user_heart.equals("")){
            tagHeart.setVisibility(VISIBLE);
            tagHeart.getTagText().setText(user_heart);
        }else {

        }

        if(user_xp!=null && user_xp.equals("")){
            tagStar.setVisibility(VISIBLE);
            tagStar.getTagText().setText(user_xp);
        }else {

        }



    }




}
