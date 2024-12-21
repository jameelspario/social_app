package com.deificindia.indifun.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;

import com.deificindia.indifun.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationUtils {
 
    private static String TAG = NotificationUtils.class.getSimpleName();
    String NOTIFICATION_CHANNEL_ID = "101";
    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void sendNotification(String typ, String title, String message1, String imageUrl,String gotourl){
       // log(""+message1);
        if (TextUtils.isEmpty(message1))
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        /*if(typ.equals(YOUTUBE)) {
            intent = new Intent(mContext, LiveNoticeActivity.class);
            intent.putExtra("YTDATA", obj);
            intent.setAction(Long.toString(System.currentTimeMillis()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }*//*else if(typ.equals(EXTERNAL)){
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(gotourl));
        }else {
            intent = new Intent(mContext, NotificationViewActivity.class);
            intent.putExtra("NoticeData", new NoticeData(0, "NOTICELIVE", title, message1, imageUrl));
            intent.setAction(Long.toString(System.currentTimeMillis()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }*/
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        noticebuilder(title, message1, imageUrl, pendingIntent);
    }

    private void noticebuilder(String title, String message1, String imageUrl, PendingIntent pendingIntent){

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);

            //Configure Notification Channel
            notificationChannel.setDescription("Toppers Club Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        if (!TextUtils.isEmpty(imageUrl)) {
            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                Bitmap bitmap = getBitmapFromURL(imageUrl);
                if (bitmap != null) {
                    showBigNotification(notificationManager, bitmap, title, message1, pendingIntent, defaultSound);
                }else {
                    showSmallNotice(notificationManager, title, message1,pendingIntent, defaultSound);
                }
            }

        }else{
            showSmallNotice(notificationManager, title, message1,pendingIntent, defaultSound);
        }
    }

    private void showSmallNotice(NotificationManager notificationManager,
                                 String title,
                                 String message,
                                 PendingIntent pend,
                                 Uri defaultSound){

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_speaker_on)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(message)
                .setContentIntent(pend)
                //.setStyle(style)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_back_black))
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX);

        notifyNotice(notificationManager, notificationBuilder.build());
    }

    private void showBigNotification(NotificationManager notificationManager,
                                     Bitmap bitmap,
                                     String title, String message,
                                     //String timeStamp,
                                     PendingIntent pend,
                                     Uri alarmSound)
    {

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_speaker_on)
                .setTicker(title)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pend)
                .setSound(alarmSound)
                .setStyle(bigPictureStyle)
                //.setWhen(getTimeMilliSec(timeStamp))
                .setWhen(System.currentTimeMillis())
                //.setSmallIcon(R.mipmap.ic_tc_tc)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_back_black));

        notifyNotice(notificationManager, notificationBuilder.build());
    }
 
    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
 
    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private void notifyNotice(NotificationManager notificationManager, Notification notification){
        clearNotifications(mContext);
        notificationManager.notify(getNoticeId(), notification);
    }
 
    public static long getTimeMilliSec(String timeStamp) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getNoticeId(){
        return  (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
    }
}
