package com.deificindia.indifun.deificpk.widgets;


import static com.deificindia.indifun.Utility.Logger.loge;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.transition.TransitionManager;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.R;
import com.deificindia.indifun.deificpk.utils.Const;
import com.deificindia.indifun.deificpk.utils.UserUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.twilio.video.VideoView;


public class PkLayout extends LinearLayout {
    private static final int TIMER_TICK_PERIOD = 1000;

    private AppCompatTextView mLeftPoint;
    private AppCompatTextView mRightPoint;
    public VideoView mLeftVideoContainer;
    public VideoView mRightVideoContainer;
    private AppCompatTextView mRemainsText;
    public LiveRemoteHostNameLayout remoteHostNameLayout;

    ConstraintLayout parentcontainer, progressContainer;
    LottieAnimationView lottie_animation;
    private ImageView pk_left_background_profile_image, pk_right_background_profile_image;
    private TextView pk_left_background_text, pk_right_background_text;
    private RelativeLayout leftContainer, rightcontainer;
    private Guideline guideVertical, guideProgress;

    private int mResultIconWidth;

    private AppCompatImageView mPkResultImage;
    public AppCompatImageView mSpeaker;

    private long mTimerStopTimestamp;
    private Handler mTimerHandler;
    private CountDownRunnable mCountDownRunnable = new CountDownRunnable();

    LiveRemoteHostNameLayout.OnAddClickListener _listener;

    boolean isOwner;
    int screen_height = 480;


    private class CountDownRunnable implements Runnable {
        @Override
        public void run() {
            long current = System.currentTimeMillis();
            mRemainsText.setText(timestampToCountdown(mTimerStopTimestamp - current));
            mTimerHandler.postDelayed(mCountDownRunnable, TIMER_TICK_PERIOD);
        }
    }

    public PkLayout(Context context) {
        super(context);
        init();
    }

    public PkLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setHeight(int height){
        screen_height = height;
        invalidate();
    }

    private void init() {
        Resources resources = getResources();
        mResultIconWidth = resources.getDimensionPixelSize(R.dimen.live_pk_result_icon_size);

        LayoutInflater.from(getContext()).inflate(R.layout.pk_video_layout, this, true);


        guideVertical = findViewById(R.id.guide_vertical);
        guideProgress = findViewById(R.id.guide_progress);

        mRemainsText = findViewById(R.id.pk_host_remaining_time_text);
        mSpeaker = findViewById(R.id.img_speakerPk);

        mLeftVideoContainer = findViewById( R.id.pk_host_video_layout_left_container);
        mRightVideoContainer = findViewById(R.id.pk_host_video_layout_right_container);

        pk_left_background_profile_image = findViewById(R.id.pk_left_background_profile_image);
        pk_right_background_profile_image = findViewById(R.id.pk_right_background_profile_image);
        pk_left_background_text = findViewById(R.id.pk_left_background_text);
        pk_right_background_text = findViewById(R.id.pk_right_background_text);

        leftContainer = findViewById(R.id.leftContainer);
        rightcontainer = findViewById(R.id.rightContainer);

        remoteHostNameLayout = findViewById(R.id.pk_video_layout_other_host_name);
        remoteHostNameLayout.init();

        mLeftPoint = findViewById(R.id.pk_progress_left_text);
        mRightPoint = findViewById(R.id.pk_progress_right_text);
        lottie_animation = findViewById(R.id.lottie_on_progress);

        parentcontainer = findViewById(R.id.pk_host_pk_layout);
        progressContainer = findViewById(R.id.layout_progress);


    }

   /* public void setHost(boolean isHost) {
        mToOtherRoomBtn.setVisibility(isHost ? View.GONE : View.VISIBLE);
    }

    public void setOnClickGotoPeerChannelListener(OnClickListener listener) {
        mToOtherRoomBtn.setOnClickListener(listener);
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*int mHeight = (screen_height/2);
        int mMaxWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(mMaxWidth, mHeight);
        int widthSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);*/
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setPoints(String localPoint, String remotePoint) {
        try {
            setPoints(Long.parseLong(localPoint), Long.parseLong(remotePoint));
            mLeftPoint.setText(localPoint);
            mRightPoint.setText(remotePoint);
        } catch (Exception e){
            e.printStackTrace();
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

    public void setPoints(long localPoint, long remotePoint) {

        if (localPoint < 0 && remotePoint < 0) {
            return;
        }

        long totalPoint = localPoint + remotePoint;

        float perc = (float) localPoint / (float) totalPoint;

        loge("PkL", ""+perc);

        if(perc < 0.1) perc = 0.1f;
        if(perc >= 1.0) perc = 0.9f;

        if(perc==0 && localPoint == 0) perc = 0.1f;
        if(localPoint == remotePoint) perc = 0.5f;

        setProgress(perc);

    }



    void  setProgress(float percent /*0.07f 7% => range: 0 <-> 1*/){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(progressContainer);
        constraintSet.setGuidelinePercent(R.id.guide_progress, percent);
        TransitionManager.beginDelayedTransition(progressContainer);
        constraintSet.applyTo(progressContainer);
    }

    public void toggleLeftVide(int b, String message, String url){
        switch (b){
            case 1:
                pk_left_background_profile_image.setVisibility(VISIBLE);
                Picasso.get().load(url)
                        .placeholder(R.drawable.profile_image_3)
                        .error(R.drawable.profile_image_3)
                        .into(pk_left_background_profile_image);
                pk_left_background_text.setVisibility(VISIBLE);
                break;
            case 0:
                pk_left_background_profile_image.setVisibility(GONE);
                pk_left_background_text.setVisibility(GONE);
                break;
        }

        pk_left_background_text.setText(message);
    }

    public void toggleRighttVide(int b, String message, String url){
        switch (b){
            case 1:
                pk_right_background_profile_image.setVisibility(VISIBLE);
                Picasso.get().load(url)
                        .placeholder(R.drawable.profile_image_3)
                        .error(R.drawable.profile_image_3)
                        .into(pk_right_background_profile_image);
                pk_right_background_text.setVisibility(VISIBLE);
                break;
            case 0:
                pk_right_background_profile_image.setVisibility(GONE);
                pk_right_background_text.setVisibility(GONE);
                break;
        }

        pk_right_background_text.setText(message);
    }

    public VideoView getLeftVideoLayout() {
        return mLeftVideoContainer;
    }
    public VideoView getRightVideoLayout() {
        return mRightVideoContainer;
    }

    public void setPKHostName(String name, String fuid, String wuid, boolean isSubscribed) {
        remoteHostNameLayout.setName(name, fuid, wuid, isSubscribed);
    }
    public void setPKHostAvtar(String avtar, long typ) {
        if(avtar==null){
            remoteHostNameLayout.setIcon(UserUtil.getUserRoundIcon(getResources()));
        }else {
            remoteHostNameLayout.setAvtarByLink(avtar, typ);
        }
    }

    public void remoteHostNameClickListener(LiveRemoteHostNameLayout.OnAddClickListener listener){
        _listener = listener;
        remoteHostNameLayout.setListener(listener);
    }

    public void startCountDownTimer(long remaining, boolean isOwner) {
        this.isOwner = isOwner;
        //loge("PkLayout", "startCountDownTimer", ""+remaining);
        mTimerStopTimestamp = System.currentTimeMillis() + remaining;
        mTimerHandler = new Handler(getContext().getMainLooper());
        mTimerHandler.post(() -> mRemainsText.setText(timestampToCountdown(remaining)));
        mTimerHandler.postDelayed(this::stopCountDownTimer, remaining);
        mTimerHandler.postDelayed(mCountDownRunnable, TIMER_TICK_PERIOD);
    }

    public void stopCountDownTimer() {
        if (mTimerHandler != null) {
            mTimerHandler.removeCallbacksAndMessages(null);
        }
    }

    private String timestampToCountdown(long remaining) {
        //loge("timestampToCountdown", "startCountDownTimer", ""+remaining);
        if (remaining < 1000) {
            onPkEnd();
            return "00:00";
        }

        long seconds = remaining / 1000;
        long minute = seconds / 60;
        int remainSecond = (int) seconds % 60;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = remainSecond < 10 ? "0" + remainSecond : "" + remainSecond;
        return minuteString + ":" + secondString;
    }


    /**
     * Set PK result of current PK session.
     * @param result pk result of current room owner.
     */
    public void setResult(int result) {
        mPkResultImage = new AppCompatImageView(getContext());
        if (result == Const.PK_RESULT_LOSE || result == Const.PK_RESULT_WIN) {
            mPkResultImage.setImageResource(result==Const.PK_RESULT_LOSE? R.drawable.looser:R.drawable.winner);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    mResultIconWidth, mResultIconWidth);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            RelativeLayout container = result == Const.PK_RESULT_LOSE
                    ? rightcontainer
                    : leftContainer;
            container.addView(mPkResultImage, params);
        } else if (result == Const.PK_RESULT_DRAW || result == Const.PK_RESULT_UNKNOWN) {
            mPkResultImage.setImageResource(result == Const.PK_RESULT_DRAW?R.drawable.draw:R.drawable.draw);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = 100;
            params.width = 100;
            //params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mPkResultImage.setId(View.generateViewId());
            mPkResultImage.setLayoutParams(params);
            ConstraintSet set = new ConstraintSet();
            set.clone(parentcontainer);
            parentcontainer.addView(mPkResultImage);
            set.connect(mPkResultImage.getId(), ConstraintSet.START, parentcontainer.getId(), ConstraintSet.START);
            set.connect(mPkResultImage.getId(), ConstraintSet.TOP, parentcontainer.getId(), ConstraintSet.TOP);
            set.connect(mPkResultImage.getId(), ConstraintSet.END, parentcontainer.getId(), ConstraintSet.END);
            set.connect(mPkResultImage.getId(), ConstraintSet.BOTTOM, parentcontainer.getId(), ConstraintSet.BOTTOM);
            set.applyTo(parentcontainer);

        }
    }

    public void removeResult() {
        if (mPkResultImage == null) {
            return;
        }

        ViewGroup parent = (ViewGroup) mPkResultImage.getParent();
        if (parent != null) {
            parent.removeView(mPkResultImage);
            mPkResultImage = null;
        }
    }

    void onPkEnd(){
        if(_listener!=null){
            _listener.onPkEnd();
        }
    }




}
