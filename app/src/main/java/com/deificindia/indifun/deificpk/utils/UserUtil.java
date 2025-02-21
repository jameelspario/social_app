package com.deificindia.indifun.deificpk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.File;
import java.util.Random;

public class UserUtil {
    private static final String LOG_FOLDER_NAME = "logs";
    private static final String LOG_FILE_NAME_RTC = "agora-rtc.log";
    private static final String LOG_FILE_NAME_RTM = "agora-rtm.log";
    private static final String LOG_APP_NAME = "agoralive-app.log";

    public static int getUserProfileIcon(String userId) {
        try {
            long intUserId = Long.valueOf(userId);
            int size = Global.Constants.PROFILE_BG_RES.length;
            int index = (int) (intUserId % size);
            return Global.Constants.PROFILE_BG_RES[index];
        } catch (NumberFormatException e) {
            return Global.Constants.PROFILE_BG_RES[0];
        }
    }

    public static int getRndProfile(){
        int indx = new Random().nextInt(Global.Constants.PROFILE_BG_RES.length);
        return Global.Constants.PROFILE_BG_RES[indx];
    }

    public static Drawable getUserRoundIcon(Resources resources) {
        int res = UserUtil.getRndProfile();
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(
                resources, BitmapFactory.decodeResource(resources, res));
        drawable.setCircular(true);
        return drawable;
    }

    public static String getUserText(String userId, String userName) {
        return !TextUtils.isEmpty(userName) ? userName : userId;
    }

    public static String rtcLogFilePath(Context context) {
        return logFilePath(context, LOG_FILE_NAME_RTC);
    }

    public static String rtmLogFilePath(Context context) {
        return logFilePath(context, LOG_FILE_NAME_RTM);
    }

    public static File appLogFolder(Context context) {
        File folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), LOG_FOLDER_NAME);
        if (!folder.exists() && !folder.mkdirs()) folder = null;
        return folder;
    }

    public static String appLogFolderPath(Context context) {
        File folder = appLogFolder(context);
        return folder != null && folder.exists() ? folder.getAbsolutePath() : "";
    }

    private static String logFilePath(Context context, String name) {
        File folder = appLogFolder(context);
        if (folder != null && !folder.exists() && !folder.mkdir()) return "";
        else return new File(folder, name).getAbsolutePath();
    }
}
