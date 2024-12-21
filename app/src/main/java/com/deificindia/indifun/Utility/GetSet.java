package com.deificindia.indifun.Utility;

public class GetSet {
    private static boolean isLogged = false;
    private static String userId = null;
    private static String token = null;
    private static String userName = null;
    private static String fullName = null;
    private static String imageUrl = null;
    private static String first_time = null;
    private static String number = null;


    public static void Logingin(String userId, String userName, String fullName, String imageUrl, String first_time, String token) {
        GetSet.isLogged = true;
        GetSet.userId = userId;
        GetSet.userName = userName;
        GetSet.fullName = fullName;
        GetSet.imageUrl = imageUrl;
        GetSet.first_time = first_time;
        GetSet.token = token;
    }

    public static void contactNumber(String number){
        GetSet.number = number;
    }

    public static boolean isLogged() {
        return isLogged;
    }

    public static void setLogged(boolean isLogged) {
        GetSet.isLogged = isLogged;
    }

    public static String getUserId() {
        return userId;
    }


    public static void setUserId(String userId) {
        GetSet.userId = userId;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        GetSet.token = token;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        GetSet.userName = userName;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        GetSet.fullName = fullName;
    }

    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        GetSet.imageUrl = imageUrl;
    }


    public static String getNumber() {
        return number;
    }

    public static void setNumber(String number) {
        GetSet.number = number;
    }

    public static void logout() {
        GetSet.isLogged = false;
        GetSet.setUserId(null);
        GetSet.setUserName(null);
        GetSet.setImageUrl(null);
        GetSet.setFullName(null);
        GetSet.setToken(null);

    }


}
