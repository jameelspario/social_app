package com.deificindia.indifun.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.deificindia.chat.MessageActivity;
import com.deificindia.chat.Notifications.OreoNotification;
import com.deificindia.chat.Notifications.Token;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.interfaces.FcmEvents;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

import io.karn.notify.Notify;
import io.karn.notify.entities.Payload;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.interfaces.FcmEvents.triggerEvent;

import androidx.core.app.NotificationCompat;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        onNotificationEvent(remoteMessage);

        try {
            if (remoteMessage != null && remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
                //Log.d(TAG, "Message data payload: " + remoteMessage.getData());
                String mobile = IndifunApplication.getInstance().getCredential().getContact().replace("+", "");
                if(mobile!=null && Objects.equals(remoteMessage.getFrom(), "/topics/" + mobile)){
                    loge(TAG, "Message data payload notice "+mobile);
                    newsNotification(remoteMessage);
                }
                if(Objects.equals(remoteMessage.getFrom(), "/topics/" + Config.NEWS)){
                    Log.e(TAG, "Message data payload notice");
                    newsNotification(remoteMessage);
                }


            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
        }catch (Exception e){}
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        if (firebaseUser != null) {
            updateToken(refreshToken);
            Log.e("Refreshed token:", token);
        }

        MySharePref.saveData(getApplicationContext(), MySharePref.FIRETOKEN, token);

    }

    private void newsNotification(final RemoteMessage remoteMessage) {
        //Log.e(TAG, "newsNotification "+toGson(remoteMessage.getData()));
        Map<String, String> data = remoteMessage.getData();

        String title = data.get("title");
        String body = data.get("body");
        //loge(TAG, "newsNotification", ""+toGson(data), ""+title, ""+body);
       /* Notify
                .with(context)
                .content { // this: Payload.Content.Default
            title = "New dessert menu"
            text = "The Cheesecake Factory has a new dessert for you to try!"
        }
    .show()*/

        Notify.Companion.with(getApplicationContext())
                .content(aDefault -> {
                     aDefault.setTitle(title);
                     aDefault.setText(body);
                     return null;
                }).show(101);
        /*
        Map<String, String> data = remoteMessage.getData();
        Logger.loge("Notice", data.toString());
        String baseurl = data.get("baseurl");
        String fullurl = data.get("fullurl");
        String version = data.get("version");
        */

        //if(baseurl!=null) STRING("LIVEURL", baseurl);
        //if(fullurl!=null) STRING("LIVEURL2", fullurl);
        //if(version!=null) VERSIONS("MENUSURLS", Long.parseLong(version));


    }

    //////////////////////////////////////////////////////////////////////////////
    private void onNotificationEvent(RemoteMessage remoteMessage){
        String sented = remoteMessage.getData().get("sented");
        String user = remoteMessage.getData().get("user");

        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        String currentUser = preferences.getString("currentuser", "none");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        assert sented != null;
        if (firebaseUser != null && sented.equals(firebaseUser.getUid())){
            if (!currentUser.equals(user)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendOreoNotification(remoteMessage);
                } else {
                    sendNotification(remoteMessage);
                }
            }
        }
    }

    private void sendOreoNotification(RemoteMessage remoteMessage){
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        assert user != null;
        int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userid", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoNotification oreoNotification = new OreoNotification(this);
        Notification.Builder builder = oreoNotification.getOreoNotification(title, body, pendingIntent,
                defaultSound, icon);

        int i = 0;
        if (j > 0){
            i = j;
        }

        oreoNotification.getManager().notify(i, builder.build());

    }

    private void sendNotification(RemoteMessage remoteMessage) {

        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        assert user != null;
        int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userid", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        assert icon != null;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);
        NotificationManager noti = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int i = 0;
        if (j > 0){
            i = j;
        }

        assert noti != null;
        noti.notify(i, builder.build());
    }

    private void updateToken(String refreshToken) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(refreshToken);
        assert firebaseUser != null;
        reference.child(firebaseUser.getUid()).setValue(token);
    }


}
