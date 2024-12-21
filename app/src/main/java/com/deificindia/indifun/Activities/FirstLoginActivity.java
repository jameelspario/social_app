package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.google.firebase.auth.FirebaseAuth;

import static com.deificindia.indifun.Utility.MySharePref.ISOTPVARIFIED;
import static com.deificindia.indifun.Utility.MySharePref.getBoolData;

public class FirstLoginActivity extends AppCompatActivity {

    private CardView first_login;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_login);

        first_login = findViewById(R.id.first_login);
        img = findViewById(R.id.splashimage);

        first_login.setOnClickListener(v -> {
            Intent i = new Intent(FirstLoginActivity.this, LoginActivity.class);
            startActivity(i);
        });

        setBackgroundImage();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void setBackgroundImage(){
        img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.splashbg));
    }
}