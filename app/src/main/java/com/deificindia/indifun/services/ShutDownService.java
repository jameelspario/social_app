package com.deificindia.indifun.services;

import static com.deificindia.indifun.Utility.IndifunApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;
import com.deificindia.indifun.db.LiveAppDb;
import com.deificindia.indifun.db.LiveRepo2;
import com.deificindia.indifun.fires.FireConst;
import com.deificindia.indifun.vm.LiveRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/* * */

public class ShutDownService extends Service {

    public ShutDownService() { }

    public static void startService(Context context, String com){
        Intent serviceIntent = new Intent(context, ShutDownService.class);
        serviceIntent.putExtra("COMMAND", com);
        ContextCompat.startForegroundService(context, serviceIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);*/

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Indifun")
                .setContentText("Closing services....")
                .setSmallIcon(R.drawable.logootp)
                //.setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        //do heavy work on a background thread

        String command = intent.getStringExtra("COMMAND");
        close_broad(command);
        //stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void close_broad(String com){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(com.equals("clear")) {
            LiveRepo2.goOfflineFireLive(getApplicationContext());
        }

        if (user != null) {
            FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                    .child(user.getUid())
                    .removeValue();
        }

        LiveRepo2.pk_delete(getApplicationContext());
        LiveRepo2.call_delete(getApplicationContext());

        stopSelf();
    }

}