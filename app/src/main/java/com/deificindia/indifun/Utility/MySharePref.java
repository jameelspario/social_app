package com.deificindia.indifun.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MySharePref {

    public static final String FIRETOKEN = "firetoken";
    static ExecutorService service = Executors.newSingleThreadExecutor();

    public static final String USERID = "userId";
    public static final String PHONE1 = "mob";
    public static final String PHONE2 = "mob0";
    public static final String ISOTPVARIFIED = "ISOTPVARIFIED";
    public static final String BATTLETIME = "BATTLETIME";
    public static final String ISPROFILECOMPLETE = "ISPROFILECOMPLETE";
    public static final String ISDAILYTASKSTARTED = "ISDAILYTASKSTARTED";

    public static final String BROADPOINT = "BROADPOINT";
    public static final String USERPOINTS = "USERPOINTS";
    public static final String DAILY_HEART = "DAILY_HEART";
    public static final String LOCATION = "LOCATION";
    public static final String IMAGE="image";
    public static final String FULLNAME="full_name";




    public static SharedPreferences sp;

    public static void saveData(Context context, String key, String value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getFullname(){
        return IndifunApplication.getInstance().getCredential().getFullName();
    } public static String getAge(){
        return IndifunApplication.getInstance().getCredential().getAge();
    } public static String getLevel(){
        return IndifunApplication.getInstance().getCredential().getLevel();
    } public static String getCity(){
        return IndifunApplication.getInstance().getCredential().getCity();
    } public static String getCountry(){
        return IndifunApplication.getInstance().getCredential().getCountry();
    }
    public static String getContact(){
        return IndifunApplication.getInstance().getCredential().getContact();
    }

    public static String getDailyHeart(){
        return IndifunApplication.getInstance().getCredential().getDiamond();
    }  public static String getDob(){
        return IndifunApplication.getInstance().getCredential().getDob();
    }
    public static String getSexulity(){
        return IndifunApplication.getInstance().getCredential().getIsBroadcasting();
    }
    public static String getUid(){
        return IndifunApplication.getInstance().getCredential().getUid();
    }

    public static String getUserId(){
        return IndifunApplication.getInstance().getCredential().getId();
    }
public static String getBroadcastPoint(){
        return IndifunApplication.getInstance().getCredential().getBroadcastPoint();
}



    public static String getData(Context context, String key, String value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void saveIntData(Context context, String key, int value) {
        sp = context.getSharedPreferences("parampara", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntData(Context context, String key, int value) {
        sp = context.getSharedPreferences("parampara", Context.MODE_PRIVATE);
        return sp.getInt(key, value);
    }


    public static void saveBoolData(Context context, String key, boolean value) {
        sp = context.getSharedPreferences("parampara", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolData(Context context, String key, boolean value) {
        sp = context.getSharedPreferences("parampara", Context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    public static void deleteData(Context context) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static void NullData(Context context, String key) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, null);
        editor.commit();
    }

    public static class LocationModal{
        public double latitiude;
        public double longitude;

        public LocationModal(double lat, double lng) {
            latitiude = lat;
            longitude = lng;
        }
    }

    public static void saveLocation(Context context, double lat, double lng){
        service.execute(()->{
            saveData(context, LOCATION, new Gson().toJson(new LocationModal(lat, lng)));
        });
    }

    public static LocationModal getLocation(Context context){
        Future<LocationModal> futer = service.submit(() -> {
            String loc = getData(context, LOCATION, null);
            if(loc!=null){
                return new Gson().fromJson(loc, LocationModal.class);
            }
            return null;
        });

        try {
            return futer.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }



}
