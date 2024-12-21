package com.deificindia.indifun.ui;

import android.content.Context;

import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.squareup.picasso.Picasso;

import static com.deificindia.indifun.Utility.Logger.loge;

public class CircleImageFrameView extends RelativeLayout {

    CircleImageView circleImageView;
    FrameLayout frameImage;

    public CircleImageFrameView(Context context) {
        super(context);

    }

    public CircleImageFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public CircleImageFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.circle_image_frame_view, this, false);

        circleImageView = view.findViewById(R.id.profile_image);
        frameImage = view.findViewById(R.id.profileFrame);

    }

    public void updateImageFrame1(String link, int frame){
        updateImageFrame(URLs.UserImageBaseUrl+link, frame);
    }

    public void updateProfile1(String link){
        Picasso.get().load(URLs.UserImageBaseUrl+link).into(circleImageView);
    }

    public void updateProfileBitmap(Uri uri){
        Picasso.get().load(uri).into(circleImageView);
    }

    public void updateProfile(String link){
        Picasso.get().load(link).into(circleImageView);
    }

    public void updateImageFrame(String link, int frame){
        loge("CircleImageFrame", link, ""+frame);
        updateProfile(link);

        if(frame > 0) {
            frameImage.setBackgroundResource(frame);
            /*Picasso.get().load(frame).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    frameImage.setba(new BitmapDrawable(getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });*/
        }


    }
}
