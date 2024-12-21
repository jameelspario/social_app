package com.deificindia.indifun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;



public class FiveByEightRelativeLayout extends RelativeLayout {
    public FiveByEightRelativeLayout(Context context) {
        super(context);
    }

    public FiveByEightRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FiveByEightRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
        if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            val sixteenNineHeight = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthMeasureSpec) * 9 / 16, View.MeasureSpec.EXACTLY)
            super.onMeasure(widthMeasureSpec, sixteenNineHeight)
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        * */

        if(getLayoutParams().width== ViewGroup.LayoutParams.WRAP_CONTENT){
            int width = getContext().getResources().getDisplayMetrics().widthPixels;
            int contentwidth = View.MeasureSpec.getSize(widthMeasureSpec);
            int mMaxWidth = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthMeasureSpec) * 5 / 8, View.MeasureSpec.EXACTLY);
            super.onMeasure(mMaxWidth, heightMeasureSpec);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }



}
