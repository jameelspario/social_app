package com.deificindia.indifun.deificpk.widgets;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.util.Pair;

import com.deificindia.indifun.R;
import com.deificindia.indifun.events.IndiLiveFrag;

import org.greenrobot.eventbus.EventBus;

import static com.deificindia.indifun.events.IndiLiveFrag.IndiLiveFrag_PAGER_DISENABLE;
import static com.deificindia.indifun.events.IndiLiveFrag.IndiLiveFrag_PAGER_ENABLE;


public class LiveBottomButtonLayout extends RelativeLayout implements View.OnClickListener {
    public static final int ROLE_AUDIENCE = 1;
    public static final int ROLE_HOST = 2;
    public static final int ROLE_OWNER = 3;

    boolean isOwner;

    public interface LiveBottomButtonListener {
        void onLiveBottomLayoutShowMessageEditor();
    }

    private RelativeLayout live_bottom_button_layout_parent;
    public AppCompatTextView mFun4label;
    public AppCompatImageView mFun4;
    protected AppCompatImageView mFun3;
    protected AppCompatImageView mFun2;
    protected AppCompatImageView mFun1;
    protected AppCompatTextView mInputText;
    private int mRole;
    private boolean mLight;

    private boolean ismuted;
    boolean touchOn;

    private LiveBottomButtonListener mListener;

    public LiveBottomButtonLayout(Context context) {
        super(context);
        //init();
    }

    public LiveBottomButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //init();
    }

    public void init() {
        init(false);
    }

    public void init(boolean lightMode) {
        mLight = lightMode;
        int layout = mLight ?
                R.layout.live_bottom_button_layout_light :
                R.layout.live_bottom_button_layout;

        LayoutInflater.from(getContext()).inflate(layout, this, true);
        live_bottom_button_layout_parent = findViewById(R.id.live_bottom_button_layout_parent);
        mFun1 = findViewById(R.id.live_bottom_btn_fun1);
        mFun2 = findViewById(R.id.live_bottom_btn_fun2);
        mFun3 = findViewById(R.id.live_bottom_btn_fun3);
        mFun4 = findViewById(R.id.live_bottom_btn_fun4);
        mFun4label = findViewById(R.id.live_bottom_btn_fun4_label);

       // mFun1.setVisibility(isVirtualImage ? View.GONE : View.VISIBLE);

        mInputText = findViewById(R.id.live_bottom_message_input_hint);
        mInputText.setMovementMethod(new ScrollingMovementMethod());
        mInputText.setOnClickListener(this);

       // live_bottom_button_layout_parent.setOnTouchListener(touchlistener);

    }


    public void setRole(boolean role) {
        mRole = role?ROLE_OWNER:ROLE_AUDIENCE;
        isOwner = role;
        /*if (mRole == ROLE_OWNER) {
            int musicRes = mLight
                    ? R.drawable.live_bottom_button_music_light
                    : R.drawable.live_bottom_button_music;
            mFun1.setImageResource(musicRes);
            //mFun2.setVisibility(mVirtualImage ? View.GONE : View.VISIBLE);
            mFun2.setImageResource(R.drawable.live_bottom_button_beauty);
        } else if (mRole == ROLE_HOST) {
            //mFun2.setVisibility(mVirtualImage ? View.GONE : View.VISIBLE);
            mFun2.setImageResource(R.drawable.live_bottom_button_beauty);
            //mFun1.setImageResource(R.drawable.live_bottom_btn_gift);
        } else if (mRole == ROLE_AUDIENCE) {
            mFun2.setVisibility(View.GONE);
            //mFun1.setImageResource(R.drawable.live_bottom_btn_gift);
            mFun1.setImageResource(R.drawable.live_bottom_btn_present);
            // mMore.setImageResource(R.drawable.ic_heart_red_1);
        }*/

        if (isOwner) {

            mFun1.setVisibility(GONE);
            int musicRes = mLight
                    ? R.drawable.live_bottom_button_music_light
                    : R.drawable.live_bottom_button_music;
            mFun2.setImageResource(musicRes);
            //mFun2.setVisibility(GONE);
            ///mFun3.setImageResource(R.drawable.live_bottom_button_beauty);
            mFun3.setVisibility(GONE);
            mFun4label.setVisibility(GONE);
            mFun4.setImageResource(R.drawable.live_bottom_btn_more);
        } else {
            mFun1.setVisibility(View.GONE);
            mFun2.setVisibility(View.GONE);
            //mFun2.setImageResource(R.drawable.live_bottom_btn_gift);
            mFun3.setImageResource(R.drawable.live_bottom_btn_present);
            mFun4.setImageResource(R.drawable.ic_heart_red_1);
        }

    }

    public void setLiveBottomButtonListener(LiveBottomButtonListener listener) {
        mListener = listener;
    }

    public void setMusicPlaying(boolean playing) {
        if (mRole == ROLE_OWNER) mFun2.setActivated(playing);
    }

    public void setBeautyEnabled(boolean enabled) {
        if (mRole == ROLE_OWNER) mFun3.setActivated(enabled);
    }

    public Pair<Float, Float> xy(){
        return new Pair<Float, Float>(mFun4.getX(), mFun4.getY());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getResources().getDimensionPixelSize(R.dimen.live_bottom_layout_height);
        setMeasuredDimension(width, height);
        int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    /**
     * Clear all global settings that are no longer needed
     * after leaving the live room
     *
     */
   /* public void clearStates(IndifunApplication application) {
        application.config().setCurrentMusicIndex(-1);
    }*/

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.live_bottom_message_input_hint) {
            if (mListener != null && !ismuted) mListener.onLiveBottomLayoutShowMessageEditor();
        }
    }

    public void setIsmuted(boolean ismuted) {
        this.ismuted = ismuted;
        mFun3.setEnabled(!ismuted);
        mFun4.setEnabled(!ismuted);
        mInputText.setEnabled(!ismuted);
    }


    OnTouchListener touchlistener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    touchOn = false;
                    performClick();
                    //invalidate();
                    //EventBus.getDefault().post(new IndiLiveFrag(IndiLiveFrag_PAGER_DISENABLE));
                    return true;
                case MotionEvent.ACTION_DOWN:
                    touchOn = true;
                    performClick();
                    //EventBus.getDefault().post(new IndiLiveFrag(IndiLiveFrag_PAGER_ENABLE));
                    return true;
                case MotionEvent.ACTION_CANCEL:
                    touchOn = false;
                    performClick();
                    //EventBus.getDefault().post(new IndiLiveFrag(IndiLiveFrag_PAGER_DISENABLE));
                    return true;
            }
            return false;
        }
    };



}
