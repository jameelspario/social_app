package com.deificindia.indifun.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.deificindia.indifun.R;

public class RoundCornerLinearLayout extends LinearLayout {

    float radius = 0;

    int startcolor = 0xFF757775, endcolor= 0xFF151515;

    public RoundCornerLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public RoundCornerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundCornerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context context, @Nullable AttributeSet attrs){
        if(attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerLinearLayout);
            radius = typedArray.getFloat(R.styleable.RoundCornerLinearLayout_rcll_corner, radius);
            startcolor = typedArray.getInt(R.styleable.RoundCornerLinearLayout_rcll_startcolor, startcolor);
            endcolor = typedArray.getInteger(R.styleable.RoundCornerLinearLayout_rcll_endcolor, endcolor);

        }

        setBackGroundColor(startcolor, endcolor);
    }


    public void setBackGroundColor(int startColor, int endColor){
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, //set a gradient direction
                new int[] {startColor, endColor, startColor}); //set the color of gradient
        gradientDrawable.setCornerRadius(radius); //set corner radius

        //Apply background to your view
        setBackground(gradientDrawable);
    }

}
