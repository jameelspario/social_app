package com.deificindia.indifun.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import com.deificindia.indifun.R;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.deificpk.utils.WidgetUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

public class LevelUtils {


    public static LevelUtils instance(){
        return new LevelUtils();
    }


    public void setLevelFrame(String url, View frame){
        setLevelFrame1(URLs.UserLEVELANIMATIONUrl+url, frame);
    }

    private void setLevelFrame1(String url, View frame){
         Picasso.get().load(url).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    frame.setBackground(new BitmapDrawable(frame.getContext().getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
    }

    public int parseColor(String color /*#F97C3C*/){
        return Color.parseColor(color);
    }

    void tem(LinearLayout genderTag){
        UserTags userTags = UserTags.instance().container(genderTag);
        userTags.addTo(UserTags.LEVEL).updateLevel("1");
        userTags.addTo(UserTags.GENDER).updateGender("25", "Male");

        userTags
                .update2("HEART", 0, R.color.white,
                        WidgetUtils.countToString(23234),
                        R.drawable.ic_heart_red_1,
                        0);
    }



    public static int levelSetting(String level){
        switch (level){
            case "0": return R.drawable.bg_blue_gift;
            case "1": return R.drawable.bg_gift_info_1;
            case "2": return R.drawable.bg_gift_info_1;
            case "3": return R.drawable.bg_gift_info_1;
            case "4": return R.drawable.bg_gift_info_1;
            case "5": return R.drawable.bg_gift_info_1;
            case "6": return R.drawable.bg_gift_info_1;
            case "7": return R.drawable.bg_gift_info_1;
            case "8": return R.drawable.bg_gift_info_1;
            case "9": return R.drawable.bg_gift_info_1;
            default: return R.drawable.bg_blue_gift;
        }
    }

}
