package com.deificindia.indifun.Utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.deificindia.indifun.R;

import java.io.ByteArrayOutputStream;

public class DrawableUtils {

    public static void changeFill(Context ctx, ImageView view, int col){ //R.color.hr_black
        view.setColorFilter(ctx.getResources().getColor(col), PorterDuff.Mode.SRC_IN);
    }

    public static void changeDrawablecolor(Context context, int draw /*R.drawable.my_drawable*/, int color /*R.color.red*/){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, draw);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, context.getResources().getColor(color));
    }


    /*
    viewWidth = imageView.getWidth();
    viewHeight = imageView.getHeight();

    imageView.setImageBitmap(
    decodeSampledBitmapFromResource(getResources(), R.id.myimage, viewWidth, viewHeight));
    * */
    public static Bitmap decodeBitmapWithGiveSizeFromResource(Resources res, int resId,
                                                              int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "temp_profile", null);
        return Uri.parse(path);
    }
}
