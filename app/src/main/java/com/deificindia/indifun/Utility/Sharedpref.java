package com.deificindia.indifun.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

import static android.content.Context.MODE_PRIVATE;

public class Sharedpref {
    private static Sharedpref spObj;
    private static SharedPreferences sp;
    private Boolean value =false;
    private SharedPreferences.Editor editor;
    private static HashSet<String> USER_ID=new HashSet<String>();
    private static HashSet<String> USER_NAME=new HashSet<String>();
    private static HashSet<String> USER_CONTACT=new HashSet<String>();
    private static HashSet<String> USER_=new HashSet<String>();
    private static HashSet<String> bottle_price=new HashSet<String>();
    private static HashSet<String> base_price=new HashSet<String>();
    private static HashSet<String> img=new HashSet<String>();
    private static HashSet<String> cat_id=new HashSet<String>();
    private static HashSet<String> name=new HashSet<String>();
//    public static ArrayList<NotificationModel> dataitem = new ArrayList<>();
//    public static ArrayList<QuestionAnswerModel> dataitem1 = new ArrayList<>();

    public void clearAll()
    {
        editor.clear();
        editor.commit();
    }
    public Sharedpref(Context context)
    {
        sp = context.getSharedPreferences("BholaDairy", Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    //    public static void addPreferencesUserData(Context context, String key, ArrayList<String> responseEmpAttendence) {
//        SharedPreferences.Editor editor = context.getSharedPreferences("Preferences_", MODE_PRIVATE).edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(responseEmpAttendence);
//        editor.putString(key, json);
//        editor.commit();
//    }
//
//    public static ArrayList<String> getMenuData(Context context, String key) {
//        SharedPreferences prefs = context.getSharedPreferences("Preferences_", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = prefs.getString(key, "");
//        Type type = new TypeToken<ArrayList<String>>() {
//        }.getType();
//        ArrayList<String> arrays = gson.fromJson(json, type);
//        return arrays;
//
//    }
    public void setCourseID(String courseID)
    {
        editor.putString("courseId",courseID);
        editor.commit();
    }

    public String getCourseID()
    {
        String t = sp.getString("courseId", "0");
        return t;
    }
    public void setPaymentPlanID(String paymentPlanID)
    {
        editor.putString("paymentPlanID",paymentPlanID);
        editor.commit();
    }

    public String getPaymentPlanID()
    {
        String Plan = sp.getString("paymentPlanID", "0");
        return Plan;
    }
    public void setPaymentStatus(String paymentStatus)
    {
        editor.putString("paystatus",paymentStatus);
        editor.commit();
    }

    public String getPaymentStatus()
    {
        String paymentStatus = sp.getString("paystatus", "0");
        return paymentStatus;
    }
    public void setCourseSelectedID(String courseSelectedID)
    {
        editor.putString("courseSelectedID",courseSelectedID);
        editor.commit();
    }

    public String getCourseSelectedID()
    {
        String spString = sp.getString("courseSelectedID", "0");
        return spString;
    }
    public void setSubjectId(String subjectId)
    {
        editor.putString("subjectId",subjectId);
        editor.commit();
    }

    public String getSubjectId()
    {
        String t = sp.getString("subjectId", "0");
        return t;
    }

    public void setButton(Boolean button)
    {
        editor.putBoolean("button",button);
        editor.commit();
    }

    public Boolean getButton()
    {
        Boolean t = sp.getBoolean("button", false);
        return t;
    }
    public void setSubjectName(String subjectName)
    {
        editor.putString("subjectName",subjectName);
        editor.commit();
    }

    public String getSubjectName()
    {
        String subjectName = sp.getString("subjectName", "0");
        return subjectName;
    }
    public void setSubjectImage(String subjectImage)
    {
        editor.putString("sub_img",subjectImage);
        editor.commit();
    }

    public String getSubjectImage()
    {
        String sub_img = sp.getString("sub_img", "0");
        return sub_img;
    }

    public void setChapterId(String chapterId)
    {
        editor.putString("chapterId",chapterId);
        editor.commit();
    }

    public String getChapterId()
    {
        String chapterid = sp.getString("chapterId", "0");
        return chapterid;
    }
    public void setChapterName(String chapterName)
    {
        editor.putString("chapterName",chapterName);
        editor.commit();
    }

    public String getChapterName()
    {
        String chapterName = sp.getString("chapterName", "0");
        return chapterName;
    }
    public void setChapterImg(String chapterImg)
    {
        editor.putString("chapterImg",chapterImg);
        editor.commit();
    }

    public String getChapterImg()
    {
        String order_status = sp.getString("chapterImg", "0");
        return order_status;
    }
    public void setVideoLink(String videoLink)
    {
        editor.putString("videoLink",videoLink);
        editor.commit();
    }

    public String getVideoLink()
    {
        String t = sp.getString("videoLink", "0");
        return t;
    }

    public void setNotificationId(String notificationId)
    {
        editor.putString("notificationId",notificationId);
        editor.commit();
    }

    public String getNotificationId()
    {
        return sp.getString("notificationId", "0");
    }

    public void setLectureID(String lectureID)
    {
        editor.putString("lecture_id",lectureID);
        editor.commit();
    }

    public String getLectureID()
    {
        String lecture_id = sp.getString("lecture_id", "0");
        return lecture_id;
    }

    public void setTestID(String testID)
    {
        editor.putString("testID",testID);
        editor.commit();
    }

    public String getTestID()
    {
        String testID = sp.getString("testID", "0");
        return testID;
    }
    public void setPlan_id(String plan_id)
    {
        editor.putString("plan_id",plan_id);
        editor.commit();
    }

    public String getPlan_id()
    {
        String t = sp.getString("plan_id", "0");
        return t;
    }
    public void setSearch(String plan_id)
    {
        editor.putString("plan_id",plan_id);
        editor.commit();
    }

    public String getSearch()
    {
        String t = sp.getString("plan_id", "0");
        return t;
    }

    public void setCatId(String productName)
    {
        editor.putString("productname",productName);
        editor.commit();
    }

    public String getCatId()
    {
        String t = sp.getString("productname", "0");
        return t;
    }
    public void setPincode(String pincode)
    {
        editor.putString("pincode",pincode);
        editor.commit();
    }

    public String getPincode()
    {
        String t = sp.getString("pincode", "0");
        return t;
    }
    public void setClicked(String clicked)
    {
        editor.putString("clicked",clicked);
        editor.commit();
    }

    public String getClicked()
    {
        String t = sp.getString("pincode", "0");
        return t;
    }
    public void setEmailId(String emailId)
    {
        editor.putString("emailId",emailId);
        editor.commit();
    }

    public String getEmailId()
    {
        String t = sp.getString("emailId", "0");
        return t;
    }
    public void setTransaction_Id(String transactionId)
    {
        editor.putString("transactionId",transactionId);
        editor.commit();
    }

    public String getTransaction_Id()
    {
        String t = sp.getString("transactionId", "0");
        return t;
    }
    public void setSignUid(String signUid)
    {
        editor.putString("signUid",signUid);
        editor.commit();
    }

    public String getSignUid()
    {
        String t = sp.getString("signUid", "0");
        return t;
    }
    public void setCat_Name(String catName)
    {
        editor.putString("catName",catName);
        editor.commit();
    }
    public String getOffer()
    {
        String t = sp.getString("offer", "0");
        return t;
    }
    public void setOffer(String offer)
    {
        editor.putString("offer",offer);
        editor.commit();
    }
    public String getCat_Name()
    {
        String s = sp.getString("catName", "0");
        return s;
    }

    public void setCouponAmt(String couponAmt)
    {
        editor.putString("couponAmt",couponAmt);
        editor.commit();
    }
    public String getCouponAmt()
    {
        String s = sp.getString("couponAmt", "0");
        return s;
    }
    public void setWalletCouponAmt(String walletCouponAmt)
    {
        editor.putString("walletCouponAmt",walletCouponAmt);
        editor.commit();
    }
    public String getWalletCouponAmt()
    {
        String s = sp.getString("walletCouponAmt", "0");
        return s;
    }


    public void setTest_time(String test_time)
    {
        editor.putString("test_time",test_time);
        editor.commit();
    }

    public String getTest_time()
    {
        String test_time = sp.getString("test_time", "0");
        return test_time;
    }
    public void setOTP(String otp)
    {
        editor.putString("otp",otp);
        editor.commit();
    }

    public String getOTP()
    {
        String otp = sp.getString("otp", "0");
        return otp;
    }
    public void setScore(String score)
    {
        editor.putString("score",score);
        editor.commit();
    }

    public String getScore()
    {
        String score = sp.getString("score", "0");
        return score;
    }
    public void setAttempt(String pa)
    {
        editor.putString("pa",pa);
        editor.commit();
    }

    public String getAttempt()
    {
        String pa = sp.getString("pa", "0");
        return pa;
    }
    public void setUnAttempt(String tm)
    {
        editor.putString("totalAmount",tm);
        editor.commit();
    }

    public String getUnAttempt()
    {
        String amount = sp.getString("totalAmount", "0");
        return amount;
    }
    public void setUserId(String userId)
    {
        editor.putString("UserId",userId);
        editor.commit();
    }

    public String getUserId()
    {
        String userId = sp.getString("UserId", "0");
        return userId;
    }
    public void setDateID(String dateID)
    {
        editor.putString("dateID",dateID);
        editor.commit();
    }

    public String getDateID()
    {
        String dateID = sp.getString("dateID", "0");
        return dateID;
    }


    public void setActivity(String activity)
    {
        editor.putString("activity",activity);
        editor.commit();
    }

    public String getActivity()
    {
        String activity = sp.getString("activity", "0");
        return activity;
    }
    public void setBuyType(String buyType)
    {
        editor.putString("buyType",buyType);
        editor.commit();
    }

    public String getBuyType()
    {
        String buyType = sp.getString("buyType", "0");
        return buyType;
    }
    public void setTotalTime(String value) {
        editor.putString("value",value);
        editor.commit();
    }
    public String getTotalTime()
    {
        String value = sp.getString("value", "0");
        return value;
    }
    public void setTotalMark(String totalMark) {
        editor.putString("totalMark",totalMark);
        editor.commit();
    }
    public String getTotalMark()
    {
        String totalMark = sp.getString("totalMark", "0");
        return totalMark;
    }
    public void setUnanswered(String unanswered) {
        editor.putString("unanswered",unanswered);
        editor.commit();
    }
    public String getUnanswered()
    {
        String unanswered = sp.getString("unanswered", "0");
        return unanswered;
    }
    public void setUser_name(String user_name) {
        editor.putString("user_name",user_name);
        editor.commit();
    }
    public String getUser_name()
    {
        String value = sp.getString("user_name", "0");
        return value;
    }
    public void setUser_email(String user_email) {
        editor.putString("user_email",user_email);
        editor.commit();
    }
    public String getUser_email()
    {
        String value = sp.getString("user_email", "0");
        return value;
    }

    public void setUser_mobile(String user_mobile) {
        editor.putString("user_mobile",user_mobile);
        editor.commit();
    }
    public String getUser_mobile()
    {
        String value = sp.getString("user_mobile", "0");
        return value;
    }
    public void setAddress_Pincode(String address_pincode) {
        editor.putString("address_pincode",address_pincode);
        editor.commit();
    }
    public String getAddress_Pincode()
    {
        String value = sp.getString("address_pincode", "0");
        return value;
    }
    public void setOtp_get(String Otp_get) {
        editor.putString("Otp_get",Otp_get);
        editor.commit();
    }
    public String getOtp_get()
    {
        String value = sp.getString("Otp_get", "0");
        return value;
    }

    public void setCoupon_id(String coupon_id) {
        editor.putString("coupon_id",coupon_id);
        editor.commit();
    }
    public String getCoupon_id()
    {
        String value = sp.getString("coupon_id", "0");
        return value;
    }
    public void setNcertMedium(String ncertMedium) {
        editor.putString("ncertMedium",ncertMedium);
        editor.commit();
    }
    public String getNcertMedium()
    {
        String ncertMedium = sp.getString("ncertMedium", "0");
        return ncertMedium;
    }
    public void setWallet_Amount(int wallet_amount) {
        editor.putInt("wallet_amount",wallet_amount);
        editor.commit();
    }
    public int getWallet_Amount()
    {
        int value = sp.getInt("wallet_amount", 0);
        return value;
    }
    public void setValue(String value) {
        editor.putString("value",value);
        editor.commit();
    }
    public String getValue()
    {
        String value = sp.getString("value", "0");
        return value;
    }


    public void setAddressId(String addressId) {
        editor.putString("addressId",addressId);
        editor.commit();
    }
    public String getAddressId()
    {
        String value = sp.getString("addressId", "0");
        return value;
    }
    public void setAllproductId(String allproduct) {
        editor.putString("allproduct",allproduct);
        editor.commit();
    }
    public String getAllproductId()
    {
        String value = sp.getString("allproduct", "0");
        return value;
    }
    public void setAllquantity(String allquantity) {
        editor.putString("allquantity",allquantity);
        editor.commit();
    }
    public String getAllquantity()
    {
        String value = sp.getString("allquantity", "0");
        return value;
    }
    public void setb_price(String bPrice) {
        editor.putString("bPrice",bPrice);
        editor.commit();
    }
    public String getb_price()
    {
        String value = sp.getString("bPrice", "0");
        return value;
    }
    public void setFragment(String fragment) {
        editor.putString("fragment",fragment);
        editor.commit();
    }
    public String getFragment()
    {
        String fragment = sp.getString("fragment", "0");
        return fragment;
    }
    public void setPincodeId(String pincodeId) {
        editor.putString("pincodeId",pincodeId);
        editor.commit();
    }
    public String getPincodeId()
    {
        String pincodeId = sp.getString("pincodeId", "0");
        return pincodeId;
    }
    public void setStatusOrder(String statusOrder) {
        editor.putString("statusOrder",statusOrder);
        editor.commit();
    }
    public String getStatusOrder()
    {
        String statusOrder = sp.getString("statusOrder", "0");
        return statusOrder;
    }
    public void setOrder(String order) {
        editor.putString("Order",order);
        editor.commit();
    }
    public String getOrder()
    {
        String Order = sp.getString("Order", "0");
        return Order;
    }

    public static void saveData(Context context, String key, String value) {
        sp = context.getSharedPreferences("coaching", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getData(Context context, String key, String value) {
        sp = context.getSharedPreferences("coaching", MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void DeleteData(Context context) {
        sp = context.getSharedPreferences("coaching", MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static void NullData(Context context, String key) {
        sp = context.getSharedPreferences("coaching", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, null);
        editor.commit();
    }

//
//    public static void addnotificationModel(Context context, ArrayList<NotificationModel> dataitem2) {
//
//        SharedPreferences prefs = context.getSharedPreferences("coaching", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(dataitem2);
//        editor.putString("Notification", json);
//        editor.apply();
//    }
//
//
//    public static ArrayList<NotificationModel> getnotificationModel(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences("coaching", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = prefs.getString("Notification", null);
//        Type type = new TypeToken<ArrayList<NotificationModel>>() {
//        }.getType();
//
//        if (dataitem == null) {
//            dataitem = new ArrayList<>();
//        } else {
//            dataitem.clear();
//        }
//        dataitem = gson.fromJson(json, type);
//
//        return dataitem;
//    }
//
//    public static void addoptionselected(Context context, ArrayList<QuestionAnswerModel> dataitem1) {
//
//        SharedPreferences prefs = context.getSharedPreferences("coaching", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(dataitem1);
//        editor.putString("Option", json);
//        editor.apply();
//    }
//
//
//    public static ArrayList<QuestionAnswerModel> getoptionselected(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences("coaching", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = prefs.getString("Option", null);
//        Type type = new TypeToken<ArrayList<QuestionAnswerModel>>() {
//        }.getType();
//
//        if (dataitem1 == null) {
//            dataitem1 = new ArrayList<>();
//        } else {
//            dataitem1.clear();
//        }
//        dataitem1 = gson.fromJson(json, type);
//
//        return dataitem1;
//    }
}
