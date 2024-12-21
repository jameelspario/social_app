package com.deificindia.indifun.Utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.modals.Result;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.MySharePref.BATTLETIME;

public class Constants {
    public static final String PARAMETER_SEP = "&";
    public static final String PARAMETER_EQUALS = "=";
    public static final String TRANS_URL = "https://secure.ccavenue.com/transaction/initTrans";

    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female";
    public static final String THREADEVENT = "THREADEVENT";
    public static final String CONTROLLTHREAD = "CONTROLLTHREAD";
    public static final String HOMEBROADLOCALACTION = "HOMEBROADLOCALACTION";
    public static final String HOMEBROADLOCAL = "HOMEBROADLOCAL";

    public static List<String> pkTimers = new LinkedList<>(Arrays.asList("3 minutes", "5 minutes", "10 minutes"));

    public static final String Spinner_const = "Choose your battle time";
    public static final String[] battle_times = {Spinner_const, "5 min", "15 min", "30 min"};

    public static final int BLOCK=1;
    public static final int MUTE=2;
    public static final int KICK=3;
    public static final int UNBLOCK=4;



    public static long getPkTimeasLong(Context cnx){
        String str = MySharePref.getData(cnx, BATTLETIME, Spinner_const);
        if(str.equals(Spinner_const)){
            return 0L;
        }

        switch (str){
            case "5 min":
                return 5*60;
            case "15 min":
                return 15*60;
            case "30 min":
                return 30*60;
        }

        return 0L;
    }

    public static String roomIdentity(boolean isOwner, String broadcasteruid){
        FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
        if(curr==null) return null;

        if(isOwner){
            return String.format("%s%s", (curr.getUid()), (curr.getUid()));
        }else {
            return String.format("%s%s", (curr.getUid()), (broadcasteruid));
        }
    }

    //starting part of identity
    public static Pair<Boolean, String> userUid(String useridentity, String ownerUid){
        if(useridentity.equals(ownerUid+ownerUid)){
            return new Pair<>(true, ownerUid);
        }
        int index = useridentity.lastIndexOf(ownerUid);
        //Log.e("userUid", "userUid: "+index );
        if(index > -1) {
            String str = useridentity.substring(0, index);
            return new Pair<>(false, str);
        }
        return new Pair<>(false, "");
    }

    public static boolean isRoomUser(String identity, String owner ){
        String getOwnerFromIdentity = getOwnerUid(identity, owner);
        loge("isRoomUser",  identity, owner, getOwnerFromIdentity);
        return getOwnerFromIdentity.equals(owner);
    }

    public static boolean isRoomUser(String identity, String owner, String user){
        if(identity.equals(owner+owner)){
            return true;
        }

        if(identity.equals(user+owner)) return true;

        return false;
    }


    //end part of identity
    public static String getOwnerUid(String useridentity, String ownerUid){

        if(useridentity.equals(ownerUid+ownerUid)) return ownerUid;
        int index = useridentity.indexOf(ownerUid);
        if(index > -1) {
            return useridentity.substring(index);
        }

        return "";
    }

    public static String get14uid1(String uid){
        return uid.substring(0, 13);
    }


    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static <T> List<T> shrinkTo(List<T> list, int newSize) {
        return list.subList(0, newSize - 1);
    }

    public static boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }

    /////////////////////////////////////////////
    ////////////////Local Broad/////////////////
    public static final int ENDWEBBROACAST = 1;
    public static final int REMOVEBROACAST = 2;
    public static final int STARTCALLLISTENER = 3;
    public static final int ENDCALL = 4;
    public static final int LIVEHOT = 5;
    public static final int LIVEFOLLOW = 6;
    public static final int LIVERECOMEDED = 7;
    public static final int UPDATEGIFTPOINT = 8;
    public static final int GETTOPGIFTER = 9;
    public static final int BLOACKUSER = 10;
    public static final int UPDATESERVERTIMEANDPKID = 11;
    public static final int RESETUPDATESERVERTIMEANDPKID = 12;
    public static final int CALLTOUSER = 13;
    public static final int EDNCALLSTAT = 14;
    public static final int GET_JOINERNAMEINFO = 15;
    public static final int STARTCALLMODE = 16;
    public static final int JOIN_CALL = 17;
    public static final int ACCEPTCALL_OR_REJECTCALL = 19;
    public static final int CLOSE_CALL_MODE = 18;

    public  static void removeBroacast(Context cnx, ControllModal data){
        Intent i = new Intent(THREADEVENT);
        i.putExtra(CONTROLLTHREAD, data);
        LocalBroadcastManager.getInstance(cnx).sendBroadcast(i);
    }

    public  static void endWebBroacast(Context cnx, ControllModal data){
        Intent i = new Intent(THREADEVENT);
        i.putExtra(CONTROLLTHREAD, data);
        LocalBroadcastManager.getInstance(cnx).sendBroadcast(i);
    }

    public  static void endCall(Context cnx, ControllModal data){
        Intent i = new Intent(THREADEVENT);
        i.putExtra(CONTROLLTHREAD, data);
        LocalBroadcastManager.getInstance(cnx).sendBroadcast(i);
    }

    public  static void updateLiveGiftPoints(Context cnx, ControllModal data){
        Intent i = new Intent(THREADEVENT);
        i.putExtra(CONTROLLTHREAD, data);
        LocalBroadcastManager.getInstance(cnx).sendBroadcast(i);
    }

    public  static void GETTOPGIFTER(Context cnx, ControllModal data){
        Intent i = new Intent(THREADEVENT);
        i.putExtra(CONTROLLTHREAD, data);
        LocalBroadcastManager.getInstance(cnx).sendBroadcast(i);
    }

    public  static void sendToService(Context cnx, ControllModal data){
        Intent i = new Intent(THREADEVENT);
        i.putExtra(CONTROLLTHREAD, data);
        LocalBroadcastManager.getInstance(cnx).sendBroadcast(i);
    }

    ///////////local broad to home////////////////
    public  static void playLikeHeartAnimation(Context cnx, ControllModal data){
        Intent i = new Intent(HOMEBROADLOCALACTION);
        i.putExtra(HOMEBROADLOCAL, data);
        LocalBroadcastManager.getInstance(cnx).sendBroadcast(i);
    }


}
