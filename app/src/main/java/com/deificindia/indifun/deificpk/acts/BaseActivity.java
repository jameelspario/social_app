package com.deificindia.indifun.deificpk.acts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.actionsheets.AbstractActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.BeautySettingActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.LiveRoomSettingActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.PkRoomListActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.toolactionsheet.LiveRoomToolActionSheet;
import com.deificindia.indifun.deificpk.modals.LiveConfig;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.twilio.video.CameraCapturer;

import java.util.Stack;
import java.util.UUID;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public abstract class BaseActivity extends AppCompatActivity {

    private static final int TOAST_SHORT_INTERVAL = 2000;
    private static final int CAMERA_MIC_PERMISSION_REQUEST_CODE = 1;

    protected static final int ACTION_SHEET_VIDEO = 0;
    protected static final int ACTION_SHEET_BEAUTY = 1;
    protected static final int ACTION_SHEET_BG_MUSIC = 2;
    protected static final int ACTION_SHEET_GIFT = 3;
    protected static final int ACTION_SHEET_TOOL = 4;
    protected static final int ACTION_SHEET_VOICE = 5;
    protected static final int ACTION_SHEET_INVITE_AUDIENCE = 6;
    protected static final int ACTION_SHEET_ROOM_USER = 7;
    protected static final int ACTION_SHEET_PK_ROOM_LIST = 8;
    protected static final int ACTION_SHEET_PRODUCT_LIST = 9;
    protected static final int ACTION_SHEET_PRODUCT_INVITE_ONLINE_SHOP = 10;

    private static final int ACTION_SHEET_DIALOG_STYLE_RES = R.style.live_room_dialog;


    protected int systemBarHeight;
    protected int displayHeight;
    protected int displayWidth;

    private long mLastToastTime;

    private Stack<AbstractActionSheet> mActionSheetStack = new Stack<>();
    private BottomSheetDialog mSheetDialog;

    protected abstract void onPermissionGranted();
    protected abstract void onSendGift(int position, GiftInfo2 info);
    //protected abstract void userinforemedofpk(PkInfo d2);

    protected IndifunApplication app(){
        return IndifunApplication.getInstance();
    }

    protected LiveConfig config(){
        return app().config();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setGlobalLayoutListener();
        systemBarHeight = getStatusBarHeight();

        getDisplaySize();
    }



    private void setGlobalLayoutListener() {
        final View layout = findViewById(Window.ID_ANDROID_CONTENT);
        ViewTreeObserver observer = layout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                onGlobalLayoutCompleted();
            }
        });
    }

    protected void onGlobalLayoutCompleted() {
        checkPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /////////Permission///////////////////////////////////
    protected void checkPermissions() {
        if (!checkPermissionForCameraAndMicrophone()) {
            requestPermissionForCameraAndMicrophone();
        } else {
            //createAudioAndVideoTracks();
            onPermissionGranted();
        }
    }

    protected boolean checkPermissionForCameraAndMicrophone() {
        int resultCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int resultMic = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return resultCamera == PackageManager.PERMISSION_GRANTED &&
                resultMic == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionForCameraAndMicrophone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                    CAMERA_MIC_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                    CAMERA_MIC_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {
            boolean cameraAndMicPermissionGranted = true;

            for (int grantResult : grantResults) {
                cameraAndMicPermissionGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
            }

            if (cameraAndMicPermissionGranted) {
                onPermissionGranted();
            } else {
                Toast.makeText(this,
                        "permissions needed",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    //////////////////////////////////////////////////////////////
    protected void keepScreenOn(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void getDisplaySize() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        displayWidth = metric.widthPixels;
        displayHeight = metric.heightPixels;
    }

    public void hideStatusBar(Window window, boolean darkText) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        int flag = SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && darkText) {
            flag = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }

        window.getDecorView().setSystemUiVisibility(flag |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    protected void hideStatusBar(boolean darkText) {
        hideStatusBar(getWindow(), darkText);
    }

    protected void hide(boolean b){
        View decorView = getWindow().getDecorView();
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        if(b){
            // Hide Status Bar.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                getWindow().setDecorFitsSystemWindows(true);
            }else {
                //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
               // decorView.setSystemUiVisibility(uiOptions);

                int uiOptions2 = View.SYSTEM_UI_FLAG_IMMERSIVE|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                decorView.setSystemUiVisibility(uiOptions2);
            }
        }else {
            // Show Status Bar.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                getWindow().setDecorFitsSystemWindows(false);
            } else {
                //int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
                //decorView.setSystemUiVisibility(uiOptions);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    private int getStatusBarHeight() {
        int id = getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        return id > 0 ? getResources().getDimensionPixelSize(id) : id;
    }

    protected void showShortToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    protected void showLongToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    protected void showToast(String message, int length) {
        long current = System.currentTimeMillis();
        if (current - mLastToastTime > TOAST_SHORT_INTERVAL) {
            // avoid showing the toast too frequently
            Toast.makeText(this, message, length).show();
            mLastToastTime = current;
        }
    }


    ////////////Dialogs/////////////////
    @SuppressLint("ResourceAsColor")
    public Dialog showDialog(int title, int message,
                             final View.OnClickListener positiveClickListener) {
        final Dialog dialog = new Dialog(this, R.style.live_room_dialog_center_in_window);
        dialog.setContentView(R.layout.live_room_dialog);
        AppCompatTextView titleTextView = dialog.findViewById(R.id.dialog_title);
        titleTextView.setText(title);
        AppCompatTextView msgTextView = dialog.findViewById(R.id.dialog_message);
        msgTextView.setText(message);
        msgTextView.setTextColor(R.color.white);
        dialog.findViewById(R.id.dialog_negative_button)
                .setOnClickListener(view -> dialog.dismiss());
        dialog.findViewById(R.id.dialog_positive_button)
                .setOnClickListener(positiveClickListener);
        hideStatusBar(dialog.getWindow(), false);
        dialog.show();
        return dialog;
    }

    protected Dialog showDialog(int title, int message,
                                int positiveText, int negativeText,
                                final View.OnClickListener positiveClickListener,
                                final View.OnClickListener negativeClickListener) {
        String titleStr = getResources().getString(title);
        String messageStr = getResources().getString(message);
        return showDialog(titleStr, messageStr, positiveText, negativeText,
                positiveClickListener, negativeClickListener);
    }

    public Dialog showDialog(String title, String message,
                                int positiveText, int negativeText,
                                final View.OnClickListener positiveClickListener,
                                final View.OnClickListener negativeClickListener) {
        final Dialog dialog = new Dialog(this,
                R.style.live_room_dialog_center_in_window);
        dialog.setContentView(R.layout.live_room_dialog);

        AppCompatTextView titleTextView = dialog.findViewById(R.id.dialog_title);
        titleTextView.setText(title);

        AppCompatTextView msgTextView = dialog.findViewById(R.id.dialog_message);
        msgTextView.setText(message);

        AppCompatTextView negativeButton = dialog.findViewById(R.id.dialog_negative_button);
        negativeButton.setText(negativeText);
        negativeButton.setOnClickListener(negativeClickListener);

        AppCompatTextView positiveButton = dialog.findViewById(R.id.dialog_positive_button);
        positiveButton.setText(positiveText);
        positiveButton.setOnClickListener(positiveClickListener);

        hideStatusBar(dialog.getWindow(), false);
        dialog.show();
        return dialog;
    }

    protected Dialog showSingleButtonConfirmDialog(String title, String message,
                                                   final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(this,
                R.style.live_room_dialog_center_in_window);
        dialog.setContentView(R.layout.live_room_dialog_single_button);
        AppCompatTextView titleTextView = dialog.findViewById(R.id.dialog_title);
        titleTextView.setText(title);
        AppCompatTextView msgTextView = dialog.findViewById(R.id.dialog_message);
        msgTextView.setText(message);
        dialog.findViewById(R.id.dialog_positive_button).setOnClickListener(listener);
        hideStatusBar(dialog.getWindow(), false);
        dialog.show();
        return dialog;
    }

    protected Dialog showSingleButtonConfirmDialog(int title, int message,
                                                   final View.OnClickListener listener) {
        String titleStr = getResources().getString(title);
        String messageStr = getResources().getString(message);
        return showSingleButtonConfirmDialog(titleStr, messageStr, listener);
    }
    ////////////////camera/////////////////////
    protected CameraCapturer.CameraSource getAvailableCameraSource() {
        return (CameraCapturer.isSourceAvailable(CameraCapturer.CameraSource.FRONT_CAMERA)) ?
                (CameraCapturer.CameraSource.FRONT_CAMERA) :
                (CameraCapturer.CameraSource.BACK_CAMERA);
    }

    ////////////////////////////////////////////////
    protected void showActionSheetDialog(final AbstractActionSheet sheet) {
        dismissActionSheetDialog();

        mSheetDialog = new BottomSheetDialog(this, ACTION_SHEET_DIALOG_STYLE_RES);
        mSheetDialog.setCanceledOnTouchOutside(true);
        mSheetDialog.setContentView(sheet);
        hideStatusBar(mSheetDialog.getWindow(), false);

        mSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (mActionSheetStack.isEmpty()) {
                    // Happens only in case of errors.
                    return;
                }

                if (sheet != mActionSheetStack.peek()) {
                    // When this action sheet is not at the top of
                    // stack, it means that a new action sheet
                    // is about to be shown and it needs a fallback
                    // history, and this sheet needs to be retained.
                    return;
                }

                // At this moment, we want to fallback to
                // the previous action sheet if exists.
                mActionSheetStack.pop();
                if (!mActionSheetStack.isEmpty()) {
                    AbstractActionSheet sheet = mActionSheetStack.peek();
                    ((ViewGroup) sheet.getParent()).removeAllViews();
                    showActionSheetDialog(mActionSheetStack.peek());
                }
            }
        });

        mSheetDialog.show();
    }

    protected AbstractActionSheet showActionSheetDialog(int sheetType, int liveType, boolean isHost, boolean newStack,
                                                        AbstractActionSheet.AbsActionSheetListener listener) {
        AbstractActionSheet actionSheet;
        switch (sheetType) {
          case ACTION_SHEET_BEAUTY:
                actionSheet = new BeautySettingActionSheet(this);
                break;
              /*case ACTION_SHEET_BG_MUSIC:
                actionSheet = new BackgroundMusicActionSheet(this);
                break;*/
            case ACTION_SHEET_GIFT:
                //actionSheet = new GiftActionSheet(this);
                //break;
                showGiftBottomSheet();
                return null;
           case ACTION_SHEET_TOOL:
                actionSheet = new LiveRoomToolActionSheet(this);
                ((LiveRoomToolActionSheet) actionSheet).setHost(isHost);
                break;
           /*  case ACTION_SHEET_VOICE:
                actionSheet = new VoiceActionSheet(this);
                break;*/
           /* case ACTION_SHEET_INVITE_AUDIENCE:
                actionSheet = new InviteUserActionSheet(this);
                break;*/
           /* case ACTION_SHEET_ROOM_USER:
                actionSheet = new LiveRoomUserListActionSheet(this);
                break;*/
            case ACTION_SHEET_PK_ROOM_LIST:
                actionSheet = new PkRoomListActionSheet(this, null);
                break;
           /* case ACTION_SHEET_PRODUCT_LIST:
                actionSheet = new ProductActionSheet(this);
                break;
            case ACTION_SHEET_PRODUCT_INVITE_ONLINE_SHOP:
                actionSheet = new OnlineUserInviteCallActionSheet(this);
                break;*/
            default:
                actionSheet = new LiveRoomSettingActionSheet(this);
                ((LiveRoomSettingActionSheet) actionSheet).setFallback(!newStack);
                ((LiveRoomSettingActionSheet) actionSheet).setLiveType(liveType);
        }

        actionSheet.setActionSheetListener(listener);
        if (newStack) mActionSheetStack.clear();
        mActionSheetStack.push(actionSheet);
        showActionSheetDialog(actionSheet);
        return actionSheet;
    }

    protected void showCustomActionSheetDialog(boolean newStack, AbstractActionSheet sheet) {
        if (newStack) mActionSheetStack.clear();
        mActionSheetStack.push(sheet);
        showActionSheetDialog(sheet);
    }

    protected void showGiftBottomSheet(){
       /* GiftBottomSheetDialog bottomSheet = new GiftBottomSheetDialog();
        bottomSheet.setActionSheetListener((position, info) -> {
            onSendGift(position, info);
            bottomSheet.dismiss();
        });
        bottomSheet.show(getSupportFragmentManager(), "GiftBottomSheetDialog");*/
    }

    protected void dismissActionSheetDialog() {
        if (mSheetDialog != null && mSheetDialog.isShowing()) {
            mSheetDialog.dismiss();
        }
    }

    public static String getRoomId(){
        return  UUID.randomUUID().toString().replace("-","");
    }

    protected boolean is30NotSecPast(long past){
        if(past+30*1000 < System.currentTimeMillis()){
            return false;
        }
        return true;
    }

}
