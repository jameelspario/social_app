package com.deificindia.indifun.deificpk.acts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.deificindia.chat.Notifications.Sender;
import com.deificindia.indifun.Activities.HomeActivity;
import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.Model.abs_plugs.AddBroadResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.ImagePickerActivity;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.LoadingDialog;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.deificpk.modals.Clip;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.CameraCapturerCompat;
import com.deificindia.indifun.deificpk.utils.Const;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.deificpk.widgets.ClearableEditText;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.ui.TagView;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.internal.common.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.protobuf.StringValue;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.twilio.video.CameraCapturer;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.deificindia.indifun.Utility.Indifun.toast;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.modals.LiveConfig.*;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.rest.RetroCallListener.IS_BROADCASTING;

public class LivePrepareActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = LivePrepareActivity.class.getName();
    private static final int REQUEST_IMAGE = 200;

    private AppCompatTextView mStartBroadBtn;
    private AppCompatTextView tvName;
    private AppCompatImageView mCloseBtn;
    private AppCompatImageView mSwitchBtn;
    //private AppCompatImageView mRandomBtn;
    private RelativeLayout mEditLayout;
    private AppCompatTextView mEditHint;
    private ClearableEditText mEditText;
    private AppCompatImageView mBeautyBtn;
    private AppCompatImageView mSettingBtn;
    private CircleImageView circleImageFrameView;
    private FrameLayout profileFrame;
    private LinearLayout linearLayout;
    double lattitu_;
    String time;
    double longitu_;
    private LoadingAnimView loadingAnimView;
    private Dialog mExitDialog;
    private Dialog myDialog;
    String roomid;
    private Map<String, TagView> maps = new HashMap<>();
    FirebaseUser fuser;
    private String picturePath;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    boolean mRequestingLocationUpdates;
    LocationRequest locationRequest;
    String token;
    String room_name;
    String broadid;
    protected Clip mClip;
    String postid;
    int level1;
    String level, coin, myxp, diamond, heart, brodcastpoint, message, success, is_broadcasting;
    int friends, isonline;
    int idsf;
    private boolean isgolive = true;

    DatabaseReference reference;
    FusedLocationProviderClient fusedLocationClient;
    private LocationSettingsRequest mLocationSettingsRequest;

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }

            for (Location location : locationResult.getLocations()) {
                // Update UI with location data
                // ...
                lattitu_ = location.getLatitude();
                longitu_ = location.getLongitude();
                Log.d(TAG, "Latitude:" + location.getLatitude() + location.getLongitude());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPermissionGranted() {
        initUI();
    }

    @Override
    protected void onSendGift(int position, GiftInfo2 info) {

    }

    private void initUI() {
        setContentView(R.layout.activity_live_prepare);
//        mClip = requireArguments().getParcelable(ARG_CLIP);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        View topLayout = findViewById(R.id.prepare_top_btn_layout);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) topLayout.getLayoutParams();
        params.topMargin += systemBarHeight;
        topLayout.setLayoutParams(params);
        getcoins();
        getlevel();
        mEditText = findViewById(R.id.room_name_edit);
        // mEditText.addTextChangedListener(this);

        /*mNameTooLongToastMsg = String.format(getResources().getString(
                R.string.live_prepare_name_too_long_toast_format), MAX_NAME_LENGTH);*/

        mStartBroadBtn = findViewById(R.id.live_prepare_go_live_btn);
        tvName = findViewById(R.id.tvName);
        mCloseBtn = findViewById(R.id.live_prepare_close);
        mSwitchBtn = findViewById(R.id.live_prepare_switch_camera);
        //mRandomBtn = findViewById(R.id.random_btn);
        mEditLayout = findViewById(R.id.prepare_name_edit_layout);
        mEditHint = findViewById(R.id.room_name_edit_hint);
        mBeautyBtn = findViewById(R.id.live_prepare_beauty_btn);
        mSettingBtn = findViewById(R.id.live_prepare_setting_btn);

        mBeautyBtn.setEnabled(false);
        mSettingBtn.setEnabled(false);

        localvideoView = findViewById(R.id.local_preview_layout);
        circleImageFrameView = findViewById(R.id.profile_image);
        profileFrame = findViewById(R.id.profileFrame);

        linearLayout = findViewById(R.id.tagslayout);
        loadingAnimView = findViewById(R.id.loadinganim);

        mStartBroadBtn.setOnClickListener(this);
        //mRandomBtn.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        mSwitchBtn.setOnClickListener(this);
        mBeautyBtn.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);
        circleImageFrameView.setOnClickListener(this);


     /*   mCameraChannel = (CameraVideoChannel) VideoModule.instance().
                getVideoChannel(ChannelManager.ChannelID.CAMERA);
        mPreprocessor = (PreprocessorFaceUnity) VideoModule.instance().
                getPreprocessor(ChannelManager.ChannelID.CAMERA);

        mLocalPreviewLayout = findViewById(R.id.local_preview_layout);
        changeUIStyles();
        checkFUAuth();*/

        changeUIStyles();
        //is_broadcasting();
        createAudioAndVideoTracks();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

    }

    private void changeUIStyles() {
        hideStatusBar(true);

        mCloseBtn.setImageResource(R.drawable.close_button_white);
        mSwitchBtn.setImageResource(R.drawable.switch_camera_white);
        //mRandomBtn.setImageResource(R.drawable.random_button_white);
        mEditHint.setTextColor(getResources().getColor(R.color.gray_lightest));
        mEditText.setTextColor(getResources().getColor(android.R.color.white));
        mEditLayout.setBackgroundResource(R.drawable.room_edit_layout_bg_dark_gray);

        Result result = IndifunApplication.getInstance().getCredential();
        /*circleImageFrameView.updateImageFrame1(
                result.getProfilePicture(),
                Constants.getFrameByXp(result.getPoint()!=null?result.getPoint():"1")
        );*/
        Picasso.get().load(URLs.UserImageBaseUrl + result.getProfilePicture())
                .placeholder(R.drawable.img_user_default)
                .error(R.drawable.img_user_default)
                .into(circleImageFrameView);
        //Frame to be changeg
       /* profileFrame.setBackgroundResource(
                Constants.getFrameByXp(
                        !obj2.getResult().getMy_xp().isEmpty()?Long.parseLong(obj2.getResult().getMy_xp()):1)
        );*/

        //updateProfile1()
        tvName.setText(result.getFullName());

//        TagView taggender;
//        if(maps.get("GENDER")!=null){
//            taggender = maps.get("GENDER");
//            UiUtils.setGenderTag(taggender, result.getGender(), result.getAge());
//
//        }else {
//            taggender = new TagView(this);
//            taggender.init();
//            linearLayout.addView(taggender);
//
//            maps.put("GENDER", taggender);
//            UiUtils.setGenderTag(taggender, result.getGender(), result.getAge());
//
//        }
//
        UserTags.instance().container(linearLayout).addTo(GENDER)
                .updateGender(result.getAge(), result.getGender());

//        if(maps.get("LEVEL")!=null){
//            taggender = maps.get("LEVEL");
//            UiUtils.setLevelTag(taggender, result.getPoint());
//
//        }else {
//            taggender = new TagView(this);
//            taggender.init();
//            linearLayout.addView(taggender);
//            maps.put("LEVEL", taggender);
//            UiUtils.setLevelTag(taggender, result.getPoint());
//        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.live_prepare_go_live_btn:
                startLiveAct();
                break;
            case R.id.live_prepare_close:
                onBackPressed();
                break;
            case R.id.live_prepare_switch_camera:
                swichcam();
                break;
            case R.id.live_prepare_beauty_btn:
                break;
            case R.id.live_prepare_setting_btn:
                break;
            case R.id.profile_image:
                openMediaDialog();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        mExitDialog = showDialog(R.string.end_live_streaming_title_owner_2,
                R.string.end_live_streaming_message_owner_2, view -> {
                    dismissDialog();
                    supportFinishAfterTransition();
                });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void dismissDialog() {
        if (mExitDialog != null && mExitDialog.isShowing()) {
            mExitDialog.dismiss();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    LocalVideoTrack localVideoTrack;
    VideoView localvideoView;
    private CameraCapturerCompat cameraCapturerCompat;

    private void createAudioAndVideoTracks() {
        // Share your microphone
        //localAudioTrack = LocalAudioTrack.create(this, true, LOCAL_AUDIO_TRACK_NAME);
        // Share your camera
        try {
            cameraCapturerCompat = new CameraCapturerCompat(this, getAvailableCameraSource());
            localVideoTrack = LocalVideoTrack.create(this,
                    true,
                    cameraCapturerCompat.getVideoCapturer(),
                    LOCAL_VIDEO_TRACK_NAME);
            localvideoView.setMirror(true);
            localVideoTrack.addRenderer(localvideoView);
        } catch (IllegalStateException e) {

        }
    }

    void swichcam() {
        if (cameraCapturerCompat != null) {
            CameraCapturer.CameraSource cameraSource = cameraCapturerCompat.getCameraSource();
            cameraCapturerCompat.switchCamera();
            boolean fronCam = cameraSource == CameraCapturer.CameraSource.BACK_CAMERA;
            config().isBackCam = fronCam;
            localvideoView.setMirror(fronCam);
        }
    }

    void is_broadcasting() {
        RetroCalls.instance().callType(IS_BROADCASTING)
                .withUID()
                .setOnFail((a, b, c) -> {

                }).is_broadcasting((type_result, obj2) -> {
            if (obj2 != null && obj2.getStatus() == 1 && obj2.getResult() != null) {
                Constants.endWebBroacast(this, new ControllModal(1,
                        MySharePref.getUserId(), obj2.getResult().getId(), ""));
                // loge("clip",obj2.getResult().getId());

            }
        });
    }

    void startLiveAct() {
        Handler handler = new Handler();

        room_name = mEditText.getText().toString();
        if (room_name.isEmpty()) {
            showShortToast("Title is required.");
            return;
        }

        if(!(lattitu_ >0.0 && longitu_>0.0)){
         //   showShortToast("Location permission is required, make sure your GPS is on.");
            startLocationUpdates();
            fetchLastLocation();
            //return;
        }

        loadingAnimView.waiting_clock();

        mStartBroadBtn.setEnabled(false);
        final Runnable r = new Runnable() {
            public void run() {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADDBROAD,
                        response -> {
//                    loadingAnimView.stopAnim();
                            mStartBroadBtn.setEnabled(true);
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.optInt("status") == 1) {
                                    JSONObject jsonObject = obj.optJSONObject("result");
                                    if (jsonObject != null) {
                                        AddBroadResult result = new Gson().fromJson(jsonObject.toString(), AddBroadResult.class);
                                        loge(TAG, "status 1", response, result.getUserName());
                                        ActivityUtils.startLive(LivePrepareActivity.this, room_name,
                                                result.getId(), /*broadcast id*/
                                                result.getTempProfile(),
                                                result.getTempProfile() != null && !result.getTempProfile().isEmpty() ? 1 : 0);

                                        finish();
                                        loadingAnimView.stopAnim();
                                        return;
                                    }
                                }
                            } catch (JSONException e) {
                                showStatus(e.getMessage());
                                e.printStackTrace();
                                return;
                            }
                            showStatus("Could not go live, try later.");
                        },
                        error -> {
                            loadingAnimView.stopAnim();
                            mStartBroadBtn.setEnabled(true);
                            showStatus(error.getMessage());
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id", MySharePref.getUserId());
                        params.put("title", room_name);
                        params.put("broadcasting_type", "3");
                        params.put("temp_profile", picturePath == null ? "" : picturePath);
                        params.put("latitude", String.valueOf(lattitu_));
                        params.put("longitude", String.valueOf(longitu_));

                        return params;
                    }
                };
                IndifunApplication.getInstance().addToRequestQueue(stringRequest);

                // handler.postDelayed(this, 10000);
            }
        };

        handler.postDelayed(r, 4000);

    }

    void showStatus(String message) {
        toast(this, "Can not go live this moment, try later.", "e");
        loge("LivePrep", "" + message);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (localVideoTrack == null && cameraCapturerCompat != null && checkPermissionForCameraAndMicrophone()) {
            localVideoTrack = LocalVideoTrack.create(this,
                    true,
                    cameraCapturerCompat.getVideoCapturer(),
                    LOCAL_VIDEO_TRACK_NAME);

            localVideoTrack.addRenderer(localvideoView);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (localVideoTrack != null) {
//         localVideoTrack   if (localParticipant != null) {
//                localParticipant.unpublishTrack(localVideoTrack);
//            }

            localVideoTrack.release();
            localVideoTrack = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stopLocationUpdates();

        if (localVideoTrack != null) {
            localVideoTrack.release();
            localVideoTrack = null;
        }

        super.onDestroy();

    }

    public static String getRoomId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void getcoins() {

        StringRequest stringRequest = new StringRequest("http://deificindia.com/indifun/panel/api/joiner_name?firebase_uid=" + FirebaseAuth.getInstance().getCurrentUser().getUid(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {

                                JSONObject jsonObject = obj.optJSONObject("result");
                                level = jsonObject.getString("level");
                                heart = jsonObject.getString("heart");
                                myxp = jsonObject.getString("my_xp");
                                diamond = jsonObject.getString("diamond");
                                brodcastpoint = jsonObject.getString("broadcast_point");
                                friends = Integer.parseInt(jsonObject.getString("friends"));
                                message = jsonObject.getString("message");
                                isonline = Integer.parseInt(jsonObject.getString("is_online"));
                                success = jsonObject.getString("status");
                                is_broadcasting = jsonObject.getString("is_broadcasting");
//                                idsf= (int) jsonObject.get("id");
                                idsf = Integer.parseInt(jsonObject.getString("id"));

                            }

                            Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            //myDialog.dismiss();
                         //   Toast.makeText(getApplicationContext(), getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //myDialog.dismiss();
                      //  Toast.makeText(getApplicationContext(), getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                return params;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }
    private void getlevel() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(LivePrepareActivity.this, "Wait...");

        RetroCalls.instance()
                .stringParam(IndifunApplication.getInstance().getCredential().user_token)
                .setOnFail((a, b, c) -> {
                    myDialog.dismiss();
                }).know_golden_coin((type_result, obj2) -> {

            myDialog.dismiss();

            if (obj2 != null && obj2.status == 1 && obj2.getResult() != null) {
                level1 = obj2.getResult().level == null
                        || obj2.getResult().level.isEmpty() ? 0 : Integer.parseInt(obj2.getResult().level);

            }
        });
    }


    public void uploadImage_112() {
        Result result = IndifunApplication.getInstance().getCredential();
        FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails");

        String postid = reference.push().getKey();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("id1", result.getId());
        hashMap.put("status", "offline");
        hashMap.put("level", level1);
        hashMap.put("gold_coin", brodcastpoint);
        hashMap.put("my_xp", myxp);
        hashMap.put("heart", heart);
        hashMap.put("firebase_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("fuid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap);
    }

    ///////////////////////////////////////////////////////////////////////
    private void openMediaDialog() {
        Dexter.withContext(LivePrepareActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LivePrepareActivity.this);
        builder.setTitle(getString(R.string.givepermissions));
        builder.setMessage(getString(R.string.pleasegivepermissions));
        builder.setPositiveButton(getString(R.string.app_name), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(LivePrepareActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE, ActivityUtils.optionFade(LivePrepareActivity.this).toBundle());
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(LivePrepareActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        startActivityForResult(intent, REQUEST_IMAGE, ActivityUtils.optionFade(LivePrepareActivity.this).toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    picturePath = Constants.encodeTobase64(bitmap);
                    circleImageFrameView.setImageURI(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void startLocationUpdates() {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SettingsClient mSettingsClient = LocationServices.getSettingsClient(this);

        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.e(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        fusedLocationClient.requestLocationUpdates(locationRequest,
                                locationCallback,
                                Looper.myLooper());

                        mRequestingLocationUpdates = true;
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.e(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(LivePrepareActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                //Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                    }
                });


    }

    private void stopLocationUpdates() {

        fusedLocationClient.removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                    }
                });
    }


    private void fetchLastLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                showPermissionAlert();
                return;
            }
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.e("LAST LOCATION: ", location.toString());
                        }
                    }
                });

    }

    private void showPermissionAlert(){
        if (ActivityCompat.checkSelfPermission(LivePrepareActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(LivePrepareActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(LivePrepareActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        loge(TAG, ""+grantResults[0]);
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // permission was denied, show alert to explain permission
                    showPermissionAlert();
                }else{
                    //permission is granted now start a background service
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        fetchLastLocation();
                    }
                }
            }
            break;
        }
    }


}
















