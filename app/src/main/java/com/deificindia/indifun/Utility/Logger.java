package com.deificindia.indifun.Utility;

import android.util.Log;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

    public static void loge(String... ss){
        StringBuilder sb = new StringBuilder();
        for (String s: ss){
            sb.append(s);
            sb.append(" | ");
        }

        Log.e("TAG", sb.toString());
    }

    public static void logpk(String... ss){
        StringBuilder sb = new StringBuilder();
        for (String s: ss){
            sb.append(s);
            sb.append(" | ");
        }

        Log.e("PK", sb.toString());
    }

    public static void logd(String TAG, String message) {
        int maxLogSize = 2000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            android.util.Log.d(TAG, message.substring(start, end));
        }
    }

    public static String  toGson(String tag,Object obj){
        String reslt = new Gson().toJson(obj);
        //loge("gson:"+tag, reslt);
        return reslt;
    }

    public static String  toGson(Object obj){
        String reslt = new Gson().toJson(obj);
        //loge("gson:", reslt);
        return ""+reslt;
    }

    public static void rep2String(){

    }

    public static String dateFormat(long milliSeconds){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
