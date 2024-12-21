package com.deificindia.indifun.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.R;

import java.util.Random;
/*
* loadingAnimView.startloading();
*
* loadingAnimView.stopAnim();
*
*  loadingAnimView.showError();
*  loadingAnimView.setErrText("No data");
* */
public class LoadingAnimView extends RelativeLayout {


    String[] loadinganim = new String[]{"835-loading.json", "5316-loading.json", "8771-loading.json"};
    LottieAnimationView lottieAnimationView;
    View ErrorLay;
    TextView ErrorText;

    public LoadingAnimView(Context context) {
        super(context);
        init(context);
    }

    public LoadingAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context cnx){
        LayoutInflater.from(cnx).inflate(R.layout.loading_anima_view, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        lottieAnimationView = findViewById(R.id.imgError);
        ErrorLay = findViewById(R.id.errorLayout);
        ErrorText = findViewById(R.id.tvErro);
    }

    public void showError(/*"nodata.json"*/){
        ErrorLay.setVisibility(View.VISIBLE);

        if(lottieAnimationView.isAnimating())
            lottieAnimationView.cancelAnimation();

        lottieAnimationView.setAnimation("67908-duck.json");
        setErrText("");
        playAnim(0);
    }
//    public void followndUnfollowa(){
//        if(lottieAnimationView.isAnimating())
//            lottieAnimationView.cancelAnimation();
//        lottieAnimationView.setAnimation("65098-check.json");
//        setErrText("");
//        playAnim(0);
//    }
    public void startloading(){
        if(lottieAnimationView.isAnimating())
            lottieAnimationView.cancelAnimation();

        //int ran =  new Random().nextInt(loadinganim.length);
        lottieAnimationView.setAnimation("9159-loader.json");
        setErrText("");
        playAnim(0);
    }

    //live prepare
    public void waiting_clock(){
        if(lottieAnimationView.isAnimating())
            lottieAnimationView.cancelAnimation();
        lottieAnimationView.setAnimation("go.json");
        setErrText("");
        playAnim(0);
    }

    public void playLikeAnim(){
        if(lottieAnimationView.isAnimating())
            lottieAnimationView.cancelAnimation();
        lottieAnimationView.setAnimation("heart_like_anim.json");
        setErrText("");
        playAnim(1);
    }


    public void startwatingLive(String message){
        if(lottieAnimationView.isAnimating())
            lottieAnimationView.cancelAnimation();

        int w = Math.min(Resources.getSystem().getDisplayMetrics().widthPixels,
                Resources.getSystem().getDisplayMetrics().heightPixels);

        LayoutParams params = (LayoutParams) lottieAnimationView.getLayoutParams();
        params.height = w/2;
        params.width = w/2;

        lottieAnimationView.setLayoutParams(params);

        lottieAnimationView.setAnimation("8428-loader.json");
        setErrText(message);
        playAnim(0);
    }

    public void startwatingLive(){
        startwatingLive("Wating live...");
    }


    public void stopAnim(){
        if(lottieAnimationView.isAnimating())
            lottieAnimationView.cancelAnimation();

        ErrorLay.setVisibility(View.GONE);
    }

    public void playAnim(int repeat){
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(repeat == 0);
        //lottieAnimationView.setRepeatCount(repeat);
    }

    public void setErrText(String txt){
        ErrorText.setText(txt);
    }

}
