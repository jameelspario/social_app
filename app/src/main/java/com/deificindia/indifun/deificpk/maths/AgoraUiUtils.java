package com.deificindia.indifun.deificpk.maths;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.widget.LinearLayout;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;
import com.deificindia.indifun.ui.tag.ChipView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AgoraUiUtils {

    public static ChipView setChip(Context context, LinearLayout mLayout, String lable, Uri uri){

        ChipView.Builder builder = new ChipView.Builder(context)
                .label(lable)
                .backgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue_alpha)))
                .labelColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)))
                .hasAvatarIcon(true)
                .avatarIcon(uri)
                .deletable(false);

        ChipView chipView2 =  builder.build();

        mLayout.addView(chipView2);

        return chipView2;

    }

    public static ChipView setChip2(Context context, LinearLayout mLayout, int bgcolor, String lable, int labelcolor, Uri uri){

        ChipView.Builder builder = new ChipView.Builder(context)
                .label(lable)
                .backgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, bgcolor)))
                .labelColor(ColorStateList.valueOf(ContextCompat.getColor(context, labelcolor)))
                .hasAvatarIcon(true)
                .avatarIcon(uri)
                .deletable(false);

        ChipView chipView2 =  builder.build();

        mLayout.addView(chipView2);

        return chipView2;

    }

    public static ChipView setChip3(Context context, LinearLayout mLayout, int bgcolor, String lable, int labelcolor, int resId){

        ChipView.Builder builder = new ChipView.Builder(context)
                .label(lable)
                .backgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, bgcolor)))
                .labelColor(ColorStateList.valueOf(ContextCompat.getColor(context, labelcolor)))
                .hasAvatarIcon(true)
                .avatarIcon(getUriToResource(context, resId))
                .deletable(false);

        ChipView chipView2 =  builder.build();

        mLayout.removeAllViews();
        mLayout.addView(chipView2);

        return chipView2;

    }

    public static Uri getUriToResource(@NonNull Context context,
                                             @AnyRes int resId)
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

    public static InputStream uri2InputStream(Context context, Uri uri){
        try {
            return context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatValue(double value) {
        int power;
        String suffix = " KMBT";
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int)StrictMath.log10(value);
        value = value/(Math.pow(10,(power/3)*3));
        formattedNumber=formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power/3);
        return formattedNumber.length()>4 ?  formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }


}
