package com.deificindia.indifun.Utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.util.Pair;
import androidx.core.view.LayoutInflaterCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.deificindia.indifun.R;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.ui.TagView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.pixplicity.sharp.Sharp;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.deificindia.indifun.Utility.Logger.loge;

public class UiUtils {

    public static final int VISIBLE = 1;
    public static final int GONE = 0;
    public static final int INVISIBLE = 2;

    public static final String GENDER = "GENDER";
    public static void setSwipeRefreshColor(SwipeRefreshLayout swipeRefreshColor){
        swipeRefreshColor.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }

    public static void setSwipeRefreshColor(com.deificindia.indifun.ui.swipe.SwipeRefreshLayout swipeRefreshColor){
        /*swipeRefreshColor.setColorScheme(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);*/
    }

    public static void svgGlide(){



        /*requestBuilder = Glide.with(mActivity)
                .using(Glide.buildStreamModelLoader(Uri.class, mActivity), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_facebook)
                .error(R.drawable.ic_web)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());*/

        /*Uri uri = Uri.parse("http://upload.wikimedia.org/wikipedia/commons/e/e8/Svg_example3.svg");
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(mImageView);*/
    }

    public static int randomedrawable(Context cnx){
        final TypedArray imgs = cnx.getResources().obtainTypedArray(R.array.imagesbg);
        final Random rand = new Random();
        final int rndInt = rand.nextInt(imgs.length());
        final int resID = imgs.getResourceId(rndInt, 0);
        return resID;
    }

    public static int randBw(int min, int max){
        final Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }



//    /*
//     UiUtils.fetchSvg(context, URLs.CountryFlagImages + data.getFlag(), imgFlag, new UiUtils.onLoadData() {
//                        @Override
//                        public void onSuccess() {
//                            placeholder.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onFail() {
//                            placeholder.setText(data.getCountry());
//                        }
//                    });
//
//    */

    private static OkHttpClient httpClient;


    public static String generateRandomString(boolean numeric, boolean lower,boolean upper, int length) {
        // You can customize the characters that you want to add into
        // the random strings
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        StringBuilder sb2 = new StringBuilder();
        //String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        if(numeric) sb2.append(NUMBER);
        if(lower) sb2.append(CHAR_LOWER);
        if(upper) sb2.append(CHAR_UPPER);

        String DATA_FOR_RANDOM_STRING = sb2.toString();

        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }

    public static void setGenderTag(TagView tag, String gender, String age){
        tag.getTagText().setText(age);
        setGenderTag(tag, gender);
    }

    public static void setGenderTag(TagView tag, String gender){
        if(gender.equalsIgnoreCase("Male")){
            tag.setTagImage(R.drawable.ic_male_gender);
            tag.changeBg(R.drawable.bg_male);
        }else {
            tag.setTagImage(R.drawable.ic_female_sign);
            tag.changeBg(R.drawable.bg_female);
        }
    }

    public static void setSmallGenderTag(Context context, LinearLayout linearLayout, String gender, String age){
        setSmallGenderTag(context, linearLayout, R.color.white, gender, age);
    }

    public static void setSmallGenderTag(Context context, LinearLayout linearLayout, int tint, String gender, String age){
        View v = LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.layout_tag2, linearLayout, false);
        LinearLayout parent = v.findViewById(R.id.parent);
        ImageView img = v.findViewById(R.id.image);
        TextView txt = v.findViewById(R.id.text);

        if(linearLayout.getChildCount() > 0) linearLayout.removeAllViews();
        linearLayout.addView(v);
        if(gender.equalsIgnoreCase("Male")){
            img.setImageResource(R.drawable.ic_male_gender);
            parent.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_male));
        }else {
            img.setImageResource(R.drawable.ic_female_sign);
            parent.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_female));
        }
        img.setColorFilter(ContextCompat.getColor(context, tint
               /* R.color.COLOR_YOUR_COLOR*/), android.graphics.PorterDuff.Mode.MULTIPLY);
        txt.setText(age);

    }

    public static void setLevelTag(TagView tag, String points_xp){
        tag.getTagText().setText(LevelControll.getLevelFrame("1000"));
        tag.setTagImage(R.drawable.ic_xp);
        tag.changeBg(R.drawable.ic_user_level_1);

    }

    public static void setGenderTag(Context context, LinearLayout parent, String gender, String age){
        TagView tag = new TagView(context);
        parent.removeAllViews();
        parent.addView(tag);
        tag.init();
        setGenderTag(tag, gender);
        tag.getTagText().setText(""+age);

    }


    public static void setAvtarByLink(String link, long typ, ImageView mIconImageView) {
        setAvtarByLink(link, typ, mIconImageView, R.drawable.img_user_default);
    }

    public static void setAvtarByLink(String link, long typ, ImageView mIconImageView, @DrawableRes int placeholder) {
        String lik = URLs.avtarBaseUrl(typ)+link;
        Picasso.get().load(lik)
                .placeholder(placeholder)
                .error(placeholder)
                .into(mIconImageView);
    }


    public static void setAvtar(String str, Context context, ImageView intoimg){
        Picasso.get().load(api.IMAGE_URL+str).error(R.drawable.img_user_default)
                .into(intoimg);
    }

    public static void setAvtarRounded(String fullurl, ImageView intoimg){
        setAvtarRounded(URLs.UserImageBaseUrl+fullurl, intoimg.getContext(), intoimg, R.drawable.img_user_default);
    }

    public static void setAvtarRounded(String fullurl, Context context, ImageView intoimg){
        setAvtarRounded(URLs.UserImageBaseUrl+fullurl, context, intoimg, R.drawable.img_user_default);
    }

    public static void setAvtarRounded(String fullurl, Context context, ImageView intoimg , int onError){
        Picasso.get().load(fullurl)
                //.error(ContextCompat.getDrawable(context, onError))
                .into(intoimg, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) intoimg.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        intoimg.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError(Exception e) {
                        intoimg.setImageResource(onError);
                    }
                });

    }

    public static void setHtmlText(TextView tv, CharSequence str){
        Spanned text;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text = Html.fromHtml(str.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            text = Html.fromHtml(str.toString());
        }

        tv.setText(text);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Boolean isNetworkAvailable(Application application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

    public static boolean ensureNotNull(Object... objArr) {
        for (Object isNull : objArr) {
            if (isNull(isNull)) {
                return false;
            }
        }
        return true;
    }

    public static int getCollectionSize(Collection collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static String getStringNotNull(String str) {
        return isNull(str) ? "" : str;
    }

    public static boolean isEmptyArray(Object[] objArr) {
        return isNull(objArr) || isZero(objArr.length);
    }

    public static boolean isEmptyByte(byte[] bArr) {
        return isNull(bArr) || isZero(bArr.length);
    }

    public static boolean isEmptyCollection(Collection collection) {
        return isNull(collection) || isZero(collection.size());
    }

    public static boolean isEmptyString(String str) {
        if (isNull(str)) {
            return true;
        }
        return isZero(str.trim().length());
    }

    public static boolean isEquals(Object obj, Object obj2) {
        return obj != null && obj.equals(obj2);
    }

    public static boolean isEqualsAllowNull(Object obj, Object obj2) {
        boolean z = true;
        boolean z2 = obj != null;
        boolean z3 = obj2 != null;
        if (z2 && z3) {
            return obj.equals(obj2);
        }
        if (z2 || z3) {
            z = false;
        }
        return z;
    }

   /* public static boolean isIntentExists(Context context, Intent intent) {
        return isNotEmptyCollection(context.getPackageManager().queryIntentActivities(intent, 65536));
    }*/

    public static boolean isNotEmptyCollection(Collection collection) {
        return !isEmptyCollection(collection);
    }

    public static boolean isNotEmptyString(String str) {
        return !isEmptyString(str);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isZero(int i2) {
        return i2 == 0;
    }

    public static boolean isZeroDouble(double d) {
        return d == 0.0d;
    }

    public static boolean isZeroLong(long j2) {
        return j2 == 0;
    }

    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    public static boolean isEquals(String str, String str2) {
        boolean isEmpty = TextUtils.isEmpty(str);
        boolean isEmpty2 = TextUtils.isEmpty(str2);
        if (isEmpty || isEmpty2) {
            return false;
        }
        return str.equals(str2);
    }


    public static SpannableString onUserJoinAnimation(String user, String message, int messageColor){
        String text = user + ":  " + message;
        SpannableString messageSpan = new SpannableString(text);
        messageSpan.setSpan(new StyleSpan(Typeface.BOLD),
                0, user.length() + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        messageSpan.setSpan(new ForegroundColorSpan(messageColor),
                0, user.length() + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return messageSpan;
    }


    public static void webpSupport(SimpleDraweeView draweeView, int imgres){
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(imgres).build();
        //draweeView.setImageURI(imageRequest.getSourceUri());
        draweeView.setController(
                Fresco.newDraweeControllerBuilder()
                        .setAutoPlayAnimations(true)
                        .setOldController(draweeView.getController())
                        .setUri(imageRequest.getSourceUri())
                .build());
    }

    public static String getRndAnima(){
        String[] draw = new String[]{
                "https://www.dropbox.com/s/guxdyhsnz6bcx1s/gift_anim_bell.gif?dl=1",
                "https://www.dropbox.com/s/3v1823fnnkeygaq/gift_anim_cake.gif?dl=1",
                "https://www.dropbox.com/s/oxj89m9l95inlsz/gift_anim_diamond.gif?dl=1",
                "http://deificindia.com/indifun/panel/assets/images/gift/taj-anim.gif"
        };
        final Random rand = new Random();
        final int rndInt = rand.nextInt(draw.length);
        final String resID = draw[rndInt>-1?rndInt:0];
        return resID;
    }
    public static int rndDrawableGift(){
        int[] draw = new int[]{R.drawable.bg_red, R.drawable.bg_blue_gift, R.drawable.bg_green_gift};
        final Random rand = new Random();
        final int rndInt = rand.nextInt(draw.length);
        final int resID = draw[rndInt>-1?rndInt:0];
        return resID;
    }


    public static void makeLinks(TextView tv, Pair<String, View.OnClickListener>... pairs){
        SpannableString spannableString = new SpannableString(tv.getText());
        for ( Pair<String, View.OnClickListener> link:pairs){
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {
                    Selection.setSelection((Spannable)((TextView)view).getText(), 0);
                    view.invalidate();
                    link.second.onClick(view);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };
            int startIndexOfLink = tv.getText().toString().indexOf(link.first);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setMovementMethod(LinkMovementMethod.getInstance());  // without LinkMovementMethod, link can not click
        tv.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    public static void viewGone1(View... v){
        for (View vi:v){
            vi.setVisibility(View.GONE);
        }
    }

    public static String agotime(Date past){
        StringBuilder agotime = new StringBuilder();

            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes= TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours= TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days= TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<60){
                agotime.append(seconds).append(" seconds");
            }
            else if(minutes<60){
                agotime.append(minutes).append(" minutes");
            }
            else if(hours<24){
                agotime.append(hours).append(" hours");
            }
            else {
                agotime.append(days).append(" days");
            }

        return agotime.append(" ago").toString();
    }

    public static String setAgoTime(String date, String time){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date past = format.parse(date+'T'+time);
            return agotime(past);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String agotime(long miliSec){
        Date date = new Date(miliSec);
        return agotime(date);
    }

    public static void gone(View view){
        view.animate()
                .translationY(view.getHeight())
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                    }
                });
    }

    public static void visible(View view){
        view.animate()
                .translationY(0)
                .alpha(1.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        //view.setVisibility(View.GONE);
                    }
                });
    }


    public static void liveType(LinearLayout linearLayout){
        ImageView img = new ImageView(linearLayout.getContext());

        if(linearLayout.getChildCount()>0) linearLayout.removeAllViews();
        linearLayout.addView(img);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(24, 100);
        img.setLayoutParams(params);


    }

    public static String coolNumberFormat(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        DecimalFormat format = new DecimalFormat("0.#");
        String value = format.format(count / Math.pow(1000, exp));
        return String.format("%s%c", value, "kMBTPE".charAt(exp - 1));
    }


}
