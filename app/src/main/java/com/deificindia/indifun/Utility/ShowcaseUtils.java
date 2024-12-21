package com.deificindia.indifun.Utility;

import android.app.Activity;
import android.view.View;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowcaseUtils {

    Activity context;

    public ShowcaseUtils(Activity context) {
        this.context = context;
    }

    public static ShowcaseUtils instance(Activity con){
        return new ShowcaseUtils(con);
    }


    public static BubbleShowCaseBuilder showCaseBuilder(Activity activity, View target, String title, String description){
        return new BubbleShowCaseBuilder(activity)
                .title(title)
                .description(description)
                .targetView(target);
    }

    public BubbleShowCaseSequence showSequence(BubbleShowCaseBuilder... builders) {
        List<BubbleShowCaseBuilder> showlist = new ArrayList<>(Arrays.asList(builders));
        return new BubbleShowCaseSequence().addShowCases(showlist);
    }

    public void showcase(){
        /*new GuideView.Builder(this)
                .setTitle("Guide Title Text")
                .setContentText("Guide Description Text\n .....Guide Description Text\n .....Guide Description Text .....")
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
                .setTargetView(view)
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .build()
                .show();*/




    }
}
