package com.deificindia.indifun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class OrientationAwareRecyclerView extends RecyclerView {

    float lastX, lastY;
    boolean scrolling;
    public OrientationAwareRecyclerView(@NonNull Context context) {
        super(context);
    }

    public OrientationAwareRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OrientationAwareRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        final LayoutManager lm = getLayoutManager();

        if (lm == null) {
            return super.onInterceptTouchEvent(e);
        }

        boolean allowScroll = true;

        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                lastX = e.getX();
                lastY = e.getY();
                // If we were scrolling, stop now by faking a touch release
                if (scrolling) {
                    MotionEvent newEvent = MotionEvent.obtain(e);
                    newEvent.setAction(MotionEvent.ACTION_UP);
                    return super.onInterceptTouchEvent(newEvent);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // We're moving, so check if we're trying
                // to scroll vertically or horizontally
                // so we don't intercept the wrong event.
                float currentX = e.getX();
                float currentY = e.getY();
                float dx = Math.abs(currentX - lastX);
                float dy = Math.abs(currentY - lastY);
                allowScroll = dy > dx ? lm.canScrollVertically() : lm.canScrollHorizontally();
                break;
            }
        }

        if (!allowScroll) {
            return false;
        }

        return super.onInterceptTouchEvent(e);
    }
}
