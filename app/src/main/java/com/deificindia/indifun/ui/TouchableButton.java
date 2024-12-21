package com.deificindia.indifun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class TouchableButton extends androidx.appcompat.widget.AppCompatImageView {
    public TouchableButton(Context context) {
        super(context);
    }

    public TouchableButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchableButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


}
