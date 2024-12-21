package com.deificindia.indifun.deificpk.actionsheets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.deificindia.indifun.Utility.IndifunApplication;

public abstract class AbstractActionSheet extends RelativeLayout {
    public AbstractActionSheet(Context context) {
        super(context);
    }

    public AbstractActionSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractActionSheet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface AbsActionSheetListener {

    }

    public abstract void setActionSheetListener(AbsActionSheetListener listener);

    protected IndifunApplication application() {
        return (IndifunApplication) getContext().getApplicationContext();
    }
}