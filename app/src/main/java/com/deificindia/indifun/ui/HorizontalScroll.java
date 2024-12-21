package com.deificindia.indifun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class HorizontalScroll extends HorizontalScrollView {

    public HorizontalScroll(Context p_context, AttributeSet p_attrs)
    {
        super(p_context, p_attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent p_event)
    {
          if (getParent() != null) {
            switch (p_event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }
        return super.onInterceptTouchEvent(p_event);
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent p_event)
    {
        if (p_event.getAction() == MotionEvent.ACTION_MOVE && getParent() != null)
        {
            getParent().requestDisallowInterceptTouchEvent(true);
        }



        return super.onTouchEvent(p_event);
    }*/
}
