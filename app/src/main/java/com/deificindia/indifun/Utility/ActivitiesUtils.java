package com.deificindia.indifun.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityOptionsCompat;

import com.deificindia.indifun.Activities.ChatActivity;
import com.deificindia.indifun.Activities.SplashActivity;

public class ActivitiesUtils {

    public static final String MESSENGER_ID = "MESSENGER_ID";
    public static final String FROMWUID = "FROMWUID";
    public static final String USERNAME = "USERNAME";

    public static void open_chat(Context context,ActivityOptionsCompat options, String messengerId, String from_wuid, String username){
        Intent in = new Intent(context, ChatActivity.class);

        in.putExtra(MESSENGER_ID, messengerId);
        in.putExtra(FROMWUID, from_wuid);
        in.putExtra(USERNAME, username);

        context.startActivity(in, options.toBundle());
    }

    public static void goTo(Context context, Class<?> cls){
        Intent mLoginIntent = new Intent(context, cls);
        context.startActivity(mLoginIntent);
    }

}
