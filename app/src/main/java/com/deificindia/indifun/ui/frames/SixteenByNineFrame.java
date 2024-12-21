package com.deificindia.indifun.ui.frames;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SixteenByNineFrame extends FrameLayout {
    public SixteenByNineFrame(@NonNull Context context) {
        super(context);
    }

    public SixteenByNineFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SixteenByNineFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(getLayoutParams().height== ViewGroup.LayoutParams.WRAP_CONTENT){
            int mHeight = View.MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4), View.MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, mHeight);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
