package com.deificindia.indifun.crashLytics;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deificindia.indifun.R;


public final class DefaultErrorActivity extends AppCompatActivity {

    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        super.onCreate(savedInstanceState);

        TypedArray a = obtainStyledAttributes(R.styleable.AppCompatTheme);
        if (!a.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        }
        a.recycle();

        setContentView(R.layout.crash_default_error_activity);
        Button restartButton = findViewById(R.id.crash_error_activity_restart_button);

        final CrashConfig config = CrashActivity.getConfigFromIntent(getIntent());

        if (config == null) {
            finish();
            return;
        }

        if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
            restartButton.setText(R.string.customactivityoncrash_error_activity_restart_app);
            restartButton.setOnClickListener(v -> CrashActivity.restartApplication(DefaultErrorActivity.this, config));
        } else {
            restartButton.setOnClickListener(v -> CrashActivity.closeApplication(DefaultErrorActivity.this, config));
        }

        Button moreInfoButton = findViewById(R.id.crash_error_activity_more_info_button);

        if (config.isShowErrorDetails()) {
            moreInfoButton.setOnClickListener(v -> {
                AlertDialog dialog = new AlertDialog.Builder(DefaultErrorActivity.this)
                        .setTitle(R.string.customactivityoncrash_error_activity_error_details_title)
                        .setMessage(CrashActivity.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent()))
                        .setPositiveButton(R.string.customactivityoncrash_error_activity_error_details_close, null)
                        .setNeutralButton(R.string.customactivityoncrash_error_activity_error_details_copy,
                                (dialog1, which) -> copyErrorToClipboard())
                        .show();
                TextView textView = dialog.findViewById(android.R.id.message);
                if (textView != null) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._12dp));
                }
            });
        } else {
            moreInfoButton.setVisibility(View.GONE);
        }

        Integer defaultErrorActivityDrawableId = config.getErrorDrawable();
        ImageView errorImageView = findViewById(R.id.crash_error_activity_image);

        if (defaultErrorActivityDrawableId != null) {
            errorImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), defaultErrorActivityDrawableId, getTheme()));
        }
    }

    private void copyErrorToClipboard() {
        String errorInformation = CrashActivity.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent());

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText(getString(R.string.customactivityoncrash_error_activity_error_details_clipboard_label), errorInformation);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(DefaultErrorActivity.this, R.string.customactivityoncrash_error_activity_error_details_copied, Toast.LENGTH_SHORT).show();
        }
    }
}
