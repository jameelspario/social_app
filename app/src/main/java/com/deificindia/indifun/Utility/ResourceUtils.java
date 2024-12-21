package com.deificindia.indifun.Utility;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
//import base.common.app.AppInfoUtils1;
//import com.mico.common.util.DeviceUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ResourceUtils {
    private static int densityDpi;
    private static float mDensity;
    private static int mScreenHeight;
    private static int mScreenWidth;

    public static float dp2PX(Context cnx, float f2) {
        return getDensity(cnx) * f2;
    }

    public static int dpToPX(Context cnx, float f2) {
        return Math.round(getDensity(cnx) * f2);
    }

    public static int getColor(Context cnx, int i2) {
        return ContextCompat.getColor(cnx, i2);
    }

    public static ColorStateList getColorStateList(Context cnx, int i2) {
        return ContextCompat.getColorStateList(cnx, i2);
    }

    public static float getDensity(Context cnx) {
        if (mDensity <= 0.0f) {
            mDensity = getResources(cnx).getDisplayMetrics().density;
        }
        return mDensity;
    }

    public static int getDensityDpi(Context cnx) {
        if (densityDpi <= 0) {
            densityDpi = getResources(cnx).getDisplayMetrics().densityDpi;
        }
        return densityDpi;
    }

    public static float getDimen(Context cnx, int i2) {
        return getResources(cnx).getDimension(i2);
    }

    public static int getDimensionPixelSize(Context cnx, int i2) {
        return getResources(cnx).getDimensionPixelSize(i2);
    }

    public static Drawable getDrawable(Context cnx, int i2) {
        return ContextCompat.getDrawable(cnx, i2);
    }

    public static ArrayList<Long> getLongArray(Context cnx, int i2) {
        ArrayList<Long> arrayList = new ArrayList<>();
        try {
            for (int i3 : getResources(cnx).getIntArray(i2)) {
                arrayList.add(Long.valueOf((long) i3));
            }
        } catch (Throwable th) {
            //Ln.e(th);
        }
        return arrayList;
    }

    public static String getQuantityString(Context cnx, int i2, int i3) {
        try {
            return getResources(cnx).getQuantityString(i2, i3);
        } catch (Throwable th) {
            //Ln.e(th);
            return "";
        }
    }

    public static Resources getResources(Context cnx) {
        return cnx.getResources();
    }

    public static int getScreenHeight(Context cnx) {
        if (mScreenHeight <= 0) {
            mScreenHeight = DeviceUtils.getScreenHeightPixels(cnx);
        }
        return mScreenHeight;
    }

    public static int getScreenWidth(Context cnx) {
        if (mScreenWidth <= 0) {
            mScreenWidth = DeviceUtils.getScreenWidthPixels(cnx);
        }
        return mScreenWidth;
    }

    public static ArrayList<String> getStringArray(Context cnx, int i2) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            arrayList.addAll(Arrays.asList(getResources(cnx).getStringArray(i2)));
        } catch (Throwable th) {
            //Ln.e(th);
        }
        return arrayList;
    }

    public static int resourceInteger(Context cnx, int i2) {
        if (i2 == -1) {
            return 0;
        }
        try {
            return getResources(cnx).getInteger(i2);
        } catch (Throwable th) {
            //Ln.e(th);
            return 0;
        }
    }

    public static String resourceString(Context cnx, int i2, Object... objArr) {
        try {
            return getResources(cnx).getString(i2, objArr);
        } catch (Throwable th) {
            //Ln.e(th);
            return "";
        }
    }

    public static String resourceString(String str, Object... objArr) {
        try {
            return String.format(str, objArr);
        } catch (Throwable th) {
            //Ln.e(th);
            return "";
        }
    }

    public static String resourceString(Context cnx, int i2) {
        if (i2 == -1) {
            return "";
        }
        try {
            return cnx.getString(i2);
        } catch (Throwable th) {
            //Ln.e(th);
            return "";
        }
    }

    public static String resourceString(Context cnx, Locale locale, int i2, Object... objArr) {
        try {
            return String.format(locale, resourceString(cnx, i2), objArr);
        } catch (Throwable th) {
            //Ln.e(th);
            return "";
        }
    }
}
