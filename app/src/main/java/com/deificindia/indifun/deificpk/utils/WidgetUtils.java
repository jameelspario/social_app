package com.deificindia.indifun.deificpk.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;
import com.deificindia.indifun.bindingmodals.otheruserprofile.Language;
import com.deificindia.indifun.ui.tag.ChipView;
import com.deificindia.indifun.ui.tag.TAGView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class WidgetUtils {


    public static ChipView setChip(Context context, LinearLayout mLayout, int bgcolor /*R.color.blue_alpha*/,
                                   int txtcolor /* R.color.white*/, String lable, Uri uri){

        ChipView.Builder builder = new ChipView.Builder(context)
                .label(lable)
                .backgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, bgcolor)))
                .labelColor(ColorStateList.valueOf(ContextCompat.getColor(context, txtcolor)))
                .hasAvatarIcon(true)
                .avatarIcon(uri)
                .deletable(false);

        ChipView chipView2 =  builder.build();
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 15);

        mLayout.addView(chipView2);
       // chipView2.setLayoutParams(params);

        return chipView2;

    }

    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId)
            throws Resources.NotFoundException {
        /** Return a Resources instance for your application's package. */
        Resources res = context.getResources();
        /**
         * Creates a Uri which parses the given encoded URI string.
         * @param uriString an RFC 2396-compliant, encoded URI
         * @throws NullPointerException if uriString is null
         * @return Uri for this given uri string
         */
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
        /** return uri */
        return resUri;
    }

    public static String countToString(int number) {
        if (number <  1e3f) {
            return String.valueOf(number);
        } else if (number < 1e6f) {
            int quotient = (int) (number / 1e3f);
            return String.format(Locale.getDefault(), "%dK", quotient);
        } else if (number < 1e9f) {
            int quotient = (int) (number / 1e6f);
            return String.format(Locale.getDefault(), "%dM", quotient);
        } else {
            int quotient = (int) (number / 1e9f);
            return String.format(Locale.getDefault(), "%dB", quotient);
        }
    }


}
