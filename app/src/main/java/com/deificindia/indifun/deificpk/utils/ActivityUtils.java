package com.deificindia.indifun.deificpk.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.deificindia.indifun.Activities.UserDetailsActivityActivity;
import com.deificindia.indifun.Model.retro.LiveResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.deificpk.acts.IndiActivity;
import com.deificindia.indifun.deificpk.acts.LivePrepareActivity;
import com.deificindia.indifun.deificpk.modals.Clip;
import com.deificindia.indifun.dialogs.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static com.deificindia.indifun.deificpk.utils.Const.*;


public class ActivityUtils {

    public static ActivityOptionsCompat optionFade(Context cnx){
        return ActivityOptionsCompat.makeCustomAnimation(cnx, R.anim.fade_in,  R.anim.fade_out);
    }

    public static void startLivePrepAsOwner(Context cnx){
        Intent intent = new Intent(cnx, LivePrepareActivity.class);

        intent.putExtra(KEY_IS_OWNER, true);

        //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) cnx);
        ActivityOptionsCompat options2 = ActivityOptionsCompat.makeCustomAnimation(cnx,R.anim.fade_in,  R.anim.fade_out);

        cnx.startActivity(intent, options2.toBundle());
    }

    public static void startLive(Context cnx, String room, String broadid, String owneravtar, long avtarttype){
        Intent intent = new Intent(cnx, IndiActivity.class); //start broad

        String fuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle bundle = new Bundle();
        Clip clp = new Clip();

        clp.id = 0;
        clp.isowner = true;
        clp.roomid = getRoomId();
        clp.roomname = room;
        clp.ownerfuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        clp.broadcastid = broadid.isEmpty()?0:Long.parseLong(broadid);
        clp.owneravtar = owneravtar;
        clp.avtartype = avtarttype;
        clp.broad_join_identity = Constants.roomIdentity(true, fuid);


        bundle.putParcelable(KEY_CLIP_DATA, clp);

        bundle.putInt(KEY_BUNDLE_CLIP_ID, 0);
        bundle.putInt(KEY_BUNDLE_CLIP_TAB, 0);
        bundle.putBoolean(KEY_BUNDLE_IS_OWNER, true);

        intent.putExtra(KEY_BUNDLE, bundle);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) cnx);

        cnx.startActivity(intent/*, options.toBundle()*/);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void openUserDetailsActivity2(Context cnx, String uuid, String username, ActivityOptionsCompat option){
        Intent intent = new Intent(cnx, UserDetailsActivityActivity.class);

        intent.putExtra("UID", uuid);
        intent.putExtra("NAME", username);

        cnx.startActivity(intent, option.toBundle());

    }

    public static void openUserDetailsActivity3(Context context, String uuid, String username,  View view){
        Intent intent = new Intent(context, UserDetailsActivityActivity.class);
        intent.putExtra("UID", uuid);
        intent.putExtra("NAME", username);
         loge("getf",uuid+username);
        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                view, Objects.requireNonNull(ViewCompat.getTransitionName(view))
        );

        context.startActivity(intent, option.toBundle());

    }

    public static void topFrameText(FrameLayout frame, String text){
        //FrameLayout frame = view.findViewById(R.id.topFrame);
        View v = LayoutInflater.from(frame.getContext()).inflate(R.layout.layout_top_item_view, frame, false);
        frame.addView(v);
        RelativeLayout rel = v.findViewById(R.id.parent_relative_layout);
        TextView tv = v.findViewById(R.id.txt_top_layout_50);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        rel.setBackgroundColor(color);

        tv.setText(text);
    }

    public static String getRoomId(){
        return  UUID.randomUUID().toString().replace("-","");
    }


    public static void joinBroad1(LiveEntity2 clp, int tab_id, View view) {
        if(clp.is_kick>0){
            DialogUtils.infoDialog(view.getContext(), "Warning",
                    "You are kicked out of this broad and you can't join this broad for 48 hours.", Dialog::dismiss);

            return;
        }

        if(clp.is_blocked>0){
            DialogUtils.infoDialog(view.getContext(), "Warning",
                    "You are blocked from this broad.", Dialog::dismiss);

            return;
        }

        Intent intent = new Intent(view.getContext(), IndiActivity.class); //join broad

        Bundle bundle = new Bundle();

        //bundle.putParcelable(KEY_CLIP_DATA, clp);
        bundle.putInt(KEY_BUNDLE_CLIP_ID, clp.id);
        bundle.putInt(KEY_BUNDLE_CLIP_TAB, tab_id);
        bundle.putBoolean(KEY_BUNDLE_IS_OWNER, false);

        intent.putExtra(KEY_BUNDLE, bundle);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) view.getContext(),
                view,
                ViewCompat.getTransitionName(view));

        view.getContext().startActivity(intent, options.toBundle());

    }


}
