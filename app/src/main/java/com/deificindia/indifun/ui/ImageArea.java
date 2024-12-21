package com.deificindia.indifun.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.anim.interpolators.BounceInterpolator;
import com.deificindia.indifun.Utility.DrawableUtils;

/*

 <ImageArea
            android:id="@+id/img_video_mode"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:ia_src="@drawable/camera_automation_active"
            android:layout_marginTop="4dp"
            android:layout_marginBottom="4dp"
            android:layout_marginStart="8dp"
            android:layout_marginEnd="8dp"
            app:ia_title="Video Mode" />

* */
public class ImageArea extends RelativeLayout {

    ImageView img;
    TextView tv;
    View parent;

    int src = R.drawable.circle_white;
    boolean _imageonly, _textonly;
    String title = "title";
    int txt_color = 0;
    int left=15, top=15, right=15, bottom=15;
    int padding = 0;
    int imagesize;

    OnImageClickListener listener;

    boolean listenClickOnEndAnim = false;

    public ImageArea(Context context) {
        super(context);
        init(context, null);
    }

    public ImageArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageArea(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = getResources();

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageArea);
            //_hint = typedArray.getString(R.styleable.EditTextView_txt_hint);
            _imageonly = typedArray.getBoolean(R.styleable.ImageArea_ia_imageonly, false);
            _textonly = typedArray.getBoolean(R.styleable.ImageArea_ia_textonly, false);
            imagesize =  typedArray.getDimensionPixelSize(R.styleable.ImageArea_ia_imagesize, resources.getDimensionPixelSize(R.dimen.image_area_image_size));

            src = typedArray.getResourceId(R.styleable.ImageArea_ia_src, src);
            title = typedArray.getString(R.styleable.ImageArea_ia_title);
            txt_color = typedArray.getInt(R.styleable.ImageArea_ia_text_color, getResources().getColor(R.color.black));

            left = typedArray.getResourceId(R.styleable.ImageArea_ia_left, left);
            top = typedArray.getResourceId(R.styleable.ImageArea_ia_top, top);
            right = typedArray.getResourceId(R.styleable.ImageArea_ia_right, right);
            bottom = typedArray.getResourceId(R.styleable.ImageArea_ia_bottom, bottom);
            padding = typedArray.getInteger(R.styleable.ImageArea_ia_padding, padding);

            typedArray.recycle();
        }

       inflater.inflate(R.layout.image_area, this);

    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);

        parent = findViewById(R.id.parent);
        img = findViewById(R.id.image);
        tv = findViewById(R.id.tvTitle);

        parent.setOnClickListener(v -> {
            bounceAnim(parent);
            if(!listenClickOnEndAnim) trigger();
        });

        //changeLayout_margin();
        img.setImageResource(src);
        tv.setText(title);
        /*if(txt_color>0){
            tv.setTextColor(txt_color);
        }*/

        if(_imageonly) imageOnly();
        if(_textonly) textOnly();

        changeHW(imagesize);
    }

    public void setSrc(int s){
        img.setImageResource(s);
    }

    private void bounceAnim(View v){
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);

        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if(listenClickOnEndAnim) trigger();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        v.startAnimation(myAnim);
    }

    public void toggle(int color){
       DrawableUtils.changeFill(getContext(), img, color);
    }

    public interface OnImageClickListener{
        void onClickIA(View v);
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener){
        this.listener = onImageClickListener;
    }

    private void trigger(){
        if(listener!=null) listener.onClickIA(this);
    }

    private void changeLayout_margin(){
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.setMargins(left, top, right, bottom);
        img.setLayoutParams(param);
        img.setPadding(padding, padding, padding, padding);
        img.invalidate();
    }

    public void selected_color(int selected){
        switch (selected){
            case 1: // selected
                tv.setTextColor(getResources().getColor(R.color.white));
                //changeFill(getContext(), img, R.color.black_trans_90);
                img.setImageResource(R.drawable.camera_automation_active);
                break;
            case 2: // unselected
                tv.setTextColor(getResources().getColor(R.color.gray_trans_90));
                //changeFill(getContext(), img, R.color.gray_trans_90);
                img.setImageResource(R.drawable.camera_automation_inactive);
                break;
        }
    }

    public void imageOnly(){
        _imageonly = true;
        tv.setVisibility(GONE);
    }

    public void textOnly(){
        _textonly = true;
        img.setVisibility(GONE);
    }

    public void changeHW(int hw){
        LayoutParams params = (LayoutParams) img.getLayoutParams();
        params.width = hw;
        params.height = hw;
        img.setLayoutParams(params);
    }

}
