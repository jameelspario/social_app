package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deificindia.indifun.R;

public class UpdateActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView tvHead, tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update);

        progressBar = findViewById(R.id.progress_bar);
        tvHead = findViewById(R.id.text_head1);
        tvProgress = findViewById(R.id.text_progress);

    }
}