package com.deificindia.indifun.Utility;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckValidation {
    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String PHONE_PATTERN = "[0-9][0-9]{9}";


    public static boolean isValidEmailAddress(String emailvalue)
    {
        boolean result=false;
        try
        {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(emailvalue);
            result=matcher.matches();
            Log.e("EmailResult"," "+result);
        }
        catch (Exception ex)
        {
            result = false;
            Log.e("Email"," "+ex);
        }
        return result;
    }
    public static boolean isValidPhone(String phonenumber)
    {
        boolean result=false;
        try
        {
            Pattern pattern = Pattern.compile(PHONE_PATTERN);
            Matcher matcher = pattern.matcher(phonenumber);
            result=matcher.matches();
            Log.e("MobileResult"," "+result);
        }
        catch (Exception ex)
        {
            result = false;
            Log.e("Phone Number"," "+ex);
        }
        return result;
    }
}
