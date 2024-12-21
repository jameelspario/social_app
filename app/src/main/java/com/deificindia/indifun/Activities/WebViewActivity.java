package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Progress_Dialogue;

import static com.deificindia.indifun.Utility.Logger.loge;

public class WebViewActivity extends AppCompatActivity {


    private ImageView img_back, applogo1;
    private TextView txt_header_title;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private WebView webViewAndroid;
    private String value, htmlcontent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        img_back = findViewById(R.id.img_back);
        txt_header_title = findViewById(R.id.txt_header_title);
        webViewAndroid = findViewById(R.id.webViewAndroid);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        value = i.getStringExtra(AppConstants.VALUE);
        htmlcontent = i.getStringExtra(AppConstants.HTMLCONTENT);
        //myDialog = DialogUtils.showProgressDialog(WebViewActivity.this, "Loading Please Wait...");

        loge("WebView", value, htmlcontent);

        txt_header_title.setText(value);
        WebSettings Ws = webViewAndroid.getSettings();
        Ws.setJavaScriptEnabled(true);
        webViewAndroid.setWebViewClient(new WebViewClient());
        webViewAndroid.loadData(htmlcontent, "text/html", "UTF-8");
        // Wv.loadUrl("javascript:window.HTMLOUT.processHTML("+ htmlcontent+")");

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myDialog.dismiss();
            }
        }, 5000);*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStackImmediate();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}
