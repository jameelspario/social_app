package com.deificindia.indifun.Utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Process;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
//import com.facebook.appevents.codeless.internal.Constants;
import java.util.Iterator;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

public class DeviceUtils {
    public static int dip2px(Activity activity, float f2) {
        return Math.round(TypedValue.applyDimension(1, f2, getDisplayMetrics(activity)));
    }

    public static int dp2px(Context context, int i2) {
        return (int) ((((float) i2) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int dpToPx(int i2) {
        return Math.round(((float) i2) * Resources.getSystem().getDisplayMetrics().density);
    }

    public static double getDensity(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (double) displayMetrics.density;
    }

    public static String getDeviceScreen(Context context) {
        return "height=" + getScreenHeightPixels(context) + ",width:" + getScreenWidthPixels(context) + ",density:" + getScreenDensity(context);
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static float getScreenDensity(Context context) {
        float f2;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            f2 = displayMetrics.density;
        } catch (Throwable th) {
            //Ln.e(th);
            f2 = 0.0f;
        }
        if (f2 != 0.0f) {
            return f2;
        }
        try {
            f2 = context.getResources().getDisplayMetrics().density;
            //Ln.d("default screenDensity is zero, use backup:" + f2);
            return f2;
        } catch (Throwable th2) {
            //Ln.e(th2);
            return f2;
        }
    }

    public static int getScreenHeightPixels(Context context) {
        int screenHeightPixelsReal = getScreenHeightPixelsReal(context);
        return screenHeightPixelsReal == 0 ? getScreenHeightPixelsVirtual(context) : screenHeightPixelsReal;
    }

    public static int getScreenHeightPixelsReal(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        } catch (Throwable th) {
            //Ln.e(th);
            return 0;
        }
    }

    public static int getScreenHeightPixelsVirtual(Context context) {
        try {
            return context.getResources().getDisplayMetrics().heightPixels;
        } catch (Throwable th) {
            //Ln.e(th);
            return 0;
        }
    }

    public static int getScreenWidthPixels(Context context) {
        int i2;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(displayMetrics);
            i2 = displayMetrics.widthPixels;
        } catch (Throwable th) {
            //Ln.e(th);
            i2 = 0;
        }
        if (i2 != 0) {
            return i2;
        }
        try {
            i2 = context.getResources().getDisplayMetrics().widthPixels;
            //Ln.d("default screenWidth is zero, use backup:" + i2);
            return i2;
        } catch (Throwable th2) {
            //Ln.e(th2);
            return i2;
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static int getStatusBarHeightPixels(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "Android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getVisibleFrameHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    public static boolean isScreenLock(Context context) {
        try {
            return ((KeyguardManager) context.getSystemService(KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode();
        } catch (Throwable th) {
            //Ln.e(th);
            return false;
        }
    }

    public static boolean isSpecifyProcess(Context context, String str) {
        try {
            int myPid = Process.myPid();
            String str2 = "";
            Iterator<ActivityManager.RunningAppProcessInfo> it = ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).getRunningAppProcesses().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ActivityManager.RunningAppProcessInfo next = it.next();
                if (next.pid == myPid) {
                    str2 = next.processName;
                    break;
                }
            }
            //Ln.d("packageName:" + str + ",processName:" + str2);
            return str.equals(str2);
        } catch (Throwable th) {
            //Ln.e(th);
            return false;
        }
    }

    public static int px2dp(Context context, int i2) {
        return (int) ((((float) i2) / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static float pxToDp(float f2) {
        return f2 / (((float) Resources.getSystem().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static float pxToSp(int i2) {
        return ((float) i2) / Resources.getSystem().getDisplayMetrics().scaledDensity;
    }

    public static float sp2px(Context context, float f2) {
        return TypedValue.applyDimension(2, f2, context.getResources().getDisplayMetrics());
    }

    public static float spToPx(int i2) {
        return ((float) i2) * Resources.getSystem().getDisplayMetrics().scaledDensity;
    }

    public static int dpToPx(float f2) {
        return Math.round(f2 * Resources.getSystem().getDisplayMetrics().density);
    }
}
