package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        animationView = findViewById(R.id.animation_view);

        animationView.loop(false);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startHome();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animationView.playAnimation();
    }

    void startHome(){
        //new Handler(Looper.myLooper()).postDelayed(()->{
            Intent intent = new Intent(WelcomeScreenActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
       // }, 6000);

    }


}