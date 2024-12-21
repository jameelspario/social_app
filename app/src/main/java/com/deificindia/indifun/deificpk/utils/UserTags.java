package com.deificindia.indifun.deificpk.utils;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;
import com.deificindia.indifun.ui.tag.TAGView;
import com.google.firebase.firestore.auth.User;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserTags {

    public static final String GENDER = "GENDER";
    public static final String LEVEL = "LEVEL";
    public static final String OTHER = "OTHER";

    LinearLayout tagContainer;

    Map<String, TAGView> tagsStore = new LinkedHashMap<>();

    TAGView tagView;

    public static UserTags instance(){ return new UserTags(); }

    public UserTags container(LinearLayout tagContainer){
        this.tagContainer = tagContainer;
        return this;
    }

    public UserTags addTo(String key){
        if(this.tagsStore.isEmpty() && this.tagContainer.getChildCount() > 0) this.tagContainer.removeAllViews();

        if(tagsStore.containsKey(key)){
            this.tagView = this.tagsStore.get(key);

        }else {
            this.tagView = new TAGView(this.tagContainer.getContext());
            this.tagContainer.addView(this.tagView);
            this.tagsStore.put(key, this.tagView);

        }

        if(tagsStore.size()>1){
            //this.tagContainer.removeAllViews();
            for (String tag:tagsStore.keySet()){
                this.tagView = this.tagsStore.get(tag);
                LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) this.tagView.getLayoutParams();
                param.rightMargin = this.tagView.dip2px(4);
                this.tagView.setLayoutParams(param);
            }
        }

        //Log.e("UserTAg", "addTo: "+tagsStore.keySet().toString() );
        return this;
    }

    public UserTags updateGender(String age, String gender){
        int bgcolor;
        int gendertag;
        if(gender.equalsIgnoreCase("Male")){
            bgcolor = R.color.male_color;
            gendertag = R.drawable.ic_male_gender;

        }else {
            bgcolor = R.color.female_color;
            gendertag = R.drawable.ic_female_sign;

        }

        return update(bgcolor, R.color.white, age, gendertag, R.color.white);
    }


    public UserTags updateLevel(int background, String level){
        int ico = R.drawable.ic_star3;
        return update(background>0?background:R.color.male_color, R.color.white, level, ico, 0);
    }

    public UserTags updateLevel(String level){
        int bgcolor = R.color.male_color;
        return updateLevel(bgcolor, level);
    }
    public UserTags updateLevel(String level1,int color){
        return updateLevel(color, level1);
    }

    public boolean exist(String k){ return this.tagsStore.containsKey(k); }
    public TAGView get(String k){ return this.tagsStore.get(k); }
    public void updateLabel(String txt){
        this.tagView.setText(txt);
    }


    public UserTags update(int bgcolor /*R.color.blue_alpha*/,
                                 int txtcolor /* R.color.white*/,
                                 String lable,
                                 int icon,
                                 int tint){

      /*<com.deificindia.indifun.ui.tag.TAGView
        android:layout_width="wrap_content"
        android:layout_height="@dimen/_24dp"
        app:tag_icon="@drawable/img_user_default"
        app:tag_text="Level 2"
        app:tag_radius="18dp"
        app:tag_color="#9d55b8"
        app:tag_text_color="#ffffff"
        app:tag_text_size="10sp"
        app:tag_divider="2dp"
        app:tag_image_width="18dp"
        app:tag_padding_top="2dp"
        app:tag_padding_bottom="2dp"
        app:tag_padding_right="4dp"
        app:tag_padding_left="@dimen/_2dp"/>*/

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                this.tagContainer.getContext().getResources().getDimensionPixelSize(R.dimen._16dp));

        tagView.setLayoutParams(param);

        tagView.setIcon(icon);
        if(tint>0) tagView.iconTint(tint);

        tagView.setImageWidth(10);
        tagView.setRadius(16);
        tagView.setText(lable,
                this.tagContainer.getContext().getResources().getColor(txtcolor),
                this.tagContainer.getContext().getResources().getDimensionPixelSize(R.dimen._10dp));

        tagView.setBackgroundColor(this.tagContainer.getContext().getResources().getColor(bgcolor));
        tagView.setDividerWidth(4);
        tagView.setPadding(6, 4, 6, 4);


        return this;

    }

    public UserTags update2(String tag,
            int bgcolor /*R.color.blue_alpha*/,
                             int txtcolor /* R.color.white*/,
                             String lable,
                             int icon,
                             int tint){

        if(exist(tag)){
            this.tagView = get(tag);
            if(this.tagView!=null) updateLabel(lable);
        }else {
            addTo(tag).update2(bgcolor, txtcolor, lable, icon, tint);
        }

        return this;
    }

    public UserTags update2(int bgcolor /*R.color.blue_alpha*/,
                           int txtcolor /* R.color.white*/,
                           String lable,
                           int icon,
                           int tint){

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                this.tagContainer.getContext().getResources().getDimensionPixelSize(R.dimen._16dp));

        this.tagView.setLayoutParams(param);

        this.tagView.setIcon(icon);
        if(tint>0) this.tagView.iconTint(tint);

        this.tagView.setImageWidth(14);
        this.tagView.setRadius(16);
        this.tagView.setText(lable,
                this.tagContainer.getContext().getResources().getColor(txtcolor),
                this.tagContainer.getContext().getResources().getDimensionPixelSize(R.dimen._10dp));

        if(bgcolor>0)
            this.tagView.setBackgroundColor(this.tagContainer.getContext().getResources().getColor(bgcolor));

        this.tagView.setDividerWidth(4);
        this.tagView.setPadding(2, 4, 6, 4);


        return this;

    }


//    public void update2(int blue_alpha_2, int white, String s, int ic_star3) {
//    }
}
