package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import com.deificindia.chat.Model.User;
import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.LocationUtility;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.deificpk.utils.BroadApiCall;
import com.deificindia.indifun.dialogs.DailyCheckinDialog;
import com.deificindia.indifun.fragments.ExploreFragment;
import com.deificindia.indifun.fragments.LiveFragment;
import com.deificindia.indifun.fragments.PostFragment;
import com.deificindia.indifun.fragments.ProfileFragment;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.services.ControllService;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.deificindia.indifun.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.deificindia.indifun.Utility.Constants.HOMEBROADLOCAL;
import static com.deificindia.indifun.Utility.Constants.HOMEBROADLOCALACTION;
import static com.deificindia.indifun.Utility.KeyClass.SAVE_AGE1;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.Utility.MySharePref.DAILY_HEART;
import static com.deificindia.indifun.Utility.MySharePref.saveIntData;
import static com.deificindia.indifun.fcm.Config.subscribetopic;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    String level1;
    private static final int NETWORK_CHECK_INTERVAL = 10000;
    private static final int MAX_PERIODIC_APP_ID_TRY_COUNT = 5;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    int level,levelfire;
    public static Boolean streamopen = false;

    BottomNavigationView bottomNavigationView;

    private int mAppIdTryCount;
    FrameLayout topContainer;
    FirebaseAuth firebaseAuth;
    boolean mRequestingLocationUpdates;
    LocationRequest locationRequest;
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
                //loge(TAG, "Latitude:" + location.getLatitude(), " Longitude:" + location.getLongitude());

                MySharePref.saveLocation(HomeActivity.this, location.getLatitude(), location.getLongitude());
                double a = location.getLatitude();
                double b = location.getLongitude();
                //Log.d("goti?","data"+a+b) ;

//                getAddress( location.getLatitude(),location.getLongitude()) ;

            }
        }
    };

    DatabaseReference reference;
    String firebaseUser;
    LocationUtility locationUtility;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startService(new Intent(HomeActivity.this, ControllService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastlistener, new IntentFilter(HOMEBROADLOCALACTION));
        subscribetopic();
        playservice();
        requestPermissions();
        checkPermissions();
        getlevelFire();

        topContainer = findViewById(R.id.top_container_frame);
        locationUtility = LocationUtility.getInstance().setIntials(this, topContainer);
        locationUtility.starLocation();

        String arg = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Result result = IndifunApplication.getInstance().getCredential();

        loge("hhsdsd", MySharePref.getLevel());
        Log.d("Firebase", FirebaseAuth.getInstance().getCurrentUser().getUid());
        initNavigation();
        openFrag(0);
        handler();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(firebaseUser);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                //   username=user.getFull_name();

                //   level1=user.getLevel();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        openFrag(openedFrag);
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            startLocationUpdates();
        }


    }

    void playservice() {
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext())
                == ConnectionResult.SUCCESS) {
            loge(TAG, "The SafetyNet Attestation API is available.");
        } else {
            loge(TAG, "Prompt user to update Google Play services.");
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }
    ///////////////////////////////////////////////////////////////////////

    private void initNavigation() {

        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int selectedId = item.getItemId();
                if (selectedId == R.id.i_empty) {
                    centerBottun();
                    return false;
                } else if (selectedId == R.id.navigation_home) {
                    // fm.beginTransaction().hide(active).show(fragment1LiveFragment).commit();
                    /// active = fragment1LiveFragment;
                    openFrag(0);
                } else if (selectedId == R.id.navigation_ex) {
                    // fm.beginTransaction().hide(active).show(fragment2ExploreFragment).commit();
                    // active = fragment2ExploreFragment;
                    openFrag(1);
                } else if (selectedId == R.id.navigation_post) {
                    //fm.beginTransaction().hide(active).show(fragment3PostFragment).commit();
                    //active = fragment3PostFragment;
                    openFrag(2);
                } else if (selectedId == R.id.navigation_pro) {
                    //fm.beginTransaction().hide(active).show(fragment4ProfileFragment).commit();
                    //active = fragment4ProfileFragment;
                    openFrag(3);
                    //AbstractFire.openCall(HomeActivity.this, null, true, 2, "0YU3juKldBOquZaXVLjtdggx3qb2");
                    //AbstractFire.openCall(HomeActivity.this, null, true, 2, "kLapj2xOYYh2MxrqawCG7XzjeyL2");

                }


                return true;
            }

        });
    }

    //===================================================================
    int openedFrag = -1;

    void openFrag(int ind) {
        switch (ind) {
            case 0:
                if (openedFrag != 0)
                    replaceFragment(new LiveFragment(), "LiveFragment");
                break;
            case 1:
                if (openedFrag != 1)
                    replaceFragment(new ExploreFragment(), "ExploreFragment");
                break;
            case 2:
                if (openedFrag != 2)
                    replaceFragment(new PostFragment(), "PostFragment");

                break;
            case 3:
                if (openedFrag != 3)
                    replaceFragment(new ProfileFragment(), "ProfileFragment");

                break;

        }
        openedFrag = ind;
        /*Pair<Boolean, String> p = Constants.userUid("qwertyuioplkjhgfdsa", "jhgfdsa");
        loge(""+p.first, p.second);*/
        hideKeyboard(HomeActivity.this);
    }//

    private void replaceFragment(Fragment fragment, String tag) {
        removeFrag();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void removeFrag() {
        if (getSupportFragmentManager().findFragmentById(R.id.container) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.container))
                    .commitAllowingStateLoss();
        }
    }

    private void topFrame(boolean add, Fragment fragment, String tag) {
        if (topContainer.getChildCount() > 0) topContainer.removeAllViews();
        if (add) {
            removeTop();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.top_container_frame, fragment, tag)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        } else {
            removeTop();
        }

    }

    void startAct(Class<?> cls, View view) {
        Intent intent = new Intent(getApplicationContext(), cls);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) getApplicationContext(),
                view, ViewCompat.getTransitionName(view));
        startActivity(intent, options.toBundle());
    }


    private void removeTop() {
        if (getSupportFragmentManager().findFragmentById(R.id.top_container_frame) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.top_container_frame))
                    .commitAllowingStateLoss();
        }
    }

    void centerBottun() {
        hideKeyboard(HomeActivity.this);
        //startLive();
        getlevel();
    }

    private void getlevel() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(HomeActivity.this, "Wait...");

        RetroCalls.instance()
                .stringParam(IndifunApplication.getInstance().getCredential().user_token)
                .setOnFail((a, b, c) -> {
                    myDialog.dismiss();
                }).know_golden_coin((type_result, obj2) -> {

            myDialog.dismiss();

            if (obj2 != null && obj2.status == 1 && obj2.getResult() != null) {
                level = obj2.getResult().level == null
                        || obj2.getResult().level.isEmpty() ? 0 : Integer.parseInt(obj2.getResult().level);

                if (level > 4) {
                    startLive();
                } else {
                    showAlertDialog();
                }
            }
        });
    }
    private void getlevelFire() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(HomeActivity.this, "Wait...");

        RetroCalls.instance()
                .stringParam(IndifunApplication.getInstance().getCredential().user_token)
                .setOnFail((a, b, c) -> {
                    myDialog.dismiss();
                }).know_golden_coin((type_result, obj2) -> {

            myDialog.dismiss();

            if (obj2 != null && obj2.status == 1 && obj2.getResult() != null) {
                levelfire = obj2.getResult().level == null
                        || obj2.getResult().level.isEmpty() ? 0 : Integer.parseInt(obj2.getResult().level);
                updateFirebaseLevel();

            }
        });
    }


    public void updateFirebaseLevel(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("UserDetails");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("level", String.valueOf(levelfire));
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    System.out.println(""+levelfire);
                 //   Toast.makeText(HomeActivity.this, ""+levelfire, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom, null);
        //alertDialog=new AlertDialog.Builder(R.style.TransparentDialog,null);
        alertDialog.setView(customLayout);

        // send data from the AlertDialog to the Activity
        ImageView icclose = customLayout.findViewById(R.id.closeall);
        Button upgrade = customLayout.findViewById(R.id.upgrade);
//                int level = Integer.parseInt(obj2.getResult().level);
        TextView textView = customLayout.findViewById(R.id.textcount);
        textView.setText("" + level);
        TextView textView1 = customLayout.findViewById(R.id.userlevel);
        TextView textView2 = customLayout.findViewById(R.id.text);

        AlertDialog alert = alertDialog.create();
        icclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RechargeCoins = new Intent(HomeActivity.this, RechargeCoins.class);
//                     getIntent().addFlags()
                startActivity(RechargeCoins);
            }
        });

        alert.setCanceledOnTouchOutside(false);

        alert.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(HomeActivity.this, ControllService.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastlistener);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.top_container_frame) != null) {
            moveTaskToBack(true);
            return;
        }
        if (topContainer.getChildCount() > 0) {
            topContainer.removeAllViews();
        } else {
            moveTaskToBack(true);
        }

    }

    void startLocationUpdates() {


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

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

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            startLocationUpdates();
                            return;
                        }
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
                                Log.e(TAG, "Location settings are not satisfied. Attempting to upgrade location settings ");
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                 mRequestingLocationUpdates = false;
                        }

                    }
                });

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(HomeActivity.this, 1);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        //updateUI();
                        break;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void onLocationPermissionGiven(){
        IndifunApplication.getInstance().dailyTask();

    }


    private void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }
        fusedLocationClient.removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                    }
                });
    }

    void startLive() {
        BroadApiCall.instance()
                .with(this, getSupportFragmentManager(), IndifunApplication.getInstance().getCredential().getId())
                //.with(bottomNavigationView.findViewById(R.id.i_empty))
        .start();
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////

    void handler(){
        new Handler(Looper.myLooper()).postDelayed(this::checkcheckin, 5000);
    }
    
    void checkcheckin(){
        RetroCalls.instance()
                .callType(16)
                .withUID()
                .withFUID()
                .update_checkintime((type_result, obj2) -> {
                    if(type_result==16 && obj2!=null && obj2.getStatus()==1){
                        long res = obj2.getResult().getCheckin_time();
                        long res2 = obj2.getResult().getTimecurrent();
                        saveIntData(this, DAILY_HEART, obj2.getResult().getHeart_daily());
                        loge(TAG, "curr "+ res, ""+res2);
                        if(res==0){
                            openDialogDailyChein();
                            return;
                        }

                        passed24H(res2, res);
                       // showcase1();

                    }
                });
    }

    void passed24H(long curr, long past){
        if (curr >= past +24*60*60*1000) {
            // time has elapsed
            openDialogDailyChein();
        }
    }

    void openDialogDailyChein(){
        if(!isFinishing()) {
            DailyCheckinDialog dialog2 = new DailyCheckinDialog();

            getSupportFragmentManager().beginTransaction()
                    .add(dialog2, "DailyCheckinDialog")
                    .commitAllowingStateLoss();
           // dialog2.show(getSupportFragmentManager(), "fragmentManager");
        }
    }


    private BroadcastReceiver broadcastlistener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent ) {
            if(intent!=null && intent.getAction().equals(HOMEBROADLOCALACTION)){
                ControllModal data = intent.getParcelableExtra(HOMEBROADLOCAL);
                if(data!=null){
                    loge( TAG, "Thrad", "", toGson(data));
                    playAnima(data);
                }
            }
        }
    };

    void playAnima(ControllModal data){
        switch (data.TYPE){
            case 1: playTopLottieAnim(data.data, false); break;
        }
    }

    void playTopLottieAnim(String jsonFileFromAssets, boolean loop){
        playTopLottieAnim(false, jsonFileFromAssets, loop);
    }

    void playTopLottieAnim(boolean isyrl, String jsonFileFromAssets, boolean loop){
        try {
            LottieAnimationView anim = new LottieAnimationView(this);
            removeViewFromTop();
            topContainer.addView(anim);
            anim.loop(loop);

            if(isyrl) anim.setAnimationFromUrl(jsonFileFromAssets);
            else anim.setAnimation(jsonFileFromAssets);

            anim.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if(!loop){
                        removeViewFromTop();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.playAnimation();
        }catch (IllegalStateException e){ }

    }

    void removeViewFromTop(){
        if(topContainer.getChildCount()>0) topContainer.removeAllViews();
    }


    void webp(){
        SimpleDraweeView draweeView = new SimpleDraweeView(this);
        topContainer.addView(draweeView);

        draweeView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        //new ImageUriProvider(this);
        //draweeView.setImageURI(ShowcaseApplication.Companion.getImageUriProvider().createWebpTranslucentUri());


    }

    void gif(){
        SimpleDraweeView draweeView = new SimpleDraweeView(this);
        topContainer.addView(draweeView);
        draweeView.setController(
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(ImageRequest.fromUri("http://s3.amazonaws.com/giphygifs/media/4aBQ9oNjgEQ2k/giphy.gif"))
                        .setAutoPlayAnimations(true)
                        .build());

    }


    boolean playing;


    public void webp(View view) {

    }


    public void animati(View view) {
        //startActivity(new Intent(HomeActivity.this, Test1Activity.class));
        //startActivity(new Intent(HomeActivity.this, IndiActivity.class));

    }

    public void beautymode(){
        //startActivity(new Intent(HomeActivity.this, MainActivity.class));

    }

    public void btn3(View view) {
       /* Map<String, Object> map = new HashMap<>();
        map.put("local", FieldValue.increment(2));
        FirebaseFirestore.getInstance().collection(BROADCASTERS)
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(map, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {

                });*/

       /* String selfuid = "rtertyetyety";
            Map<FieldPath, Object> update = new HashMap<>();
            update.put(FieldPath.of("calls", "rtertyetyety", "state"), 2);
*/

       /* FirebaseFirestore.getInstance().collection(BROADCASTERS)
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update(FieldPath.of("calls", "rtertyetyety2", "state"), 2)
                .addOnSuccessListener(aVoid -> {

                });*/

       /* FieldPath path = FieldPath.of("calls", selfuid);

        FirebaseFirestore.getInstance()
                .collection(BROADCASTERS)
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update(path, FieldValue.delete());*/

        //LiveFireFun.instance().readPkUsers();
        //LiveFireFun.test();
        //throw new RuntimeException("I'm a cool exception and I crashed the main thread!");
        /*EntityCall call = new EntityCall(1, 2);
        call.jfuid = "qwerty";
        call.owner = "asdfghjl";
        //LiveFireFun.instance().joinCall(call);
        LiveFireFun.instance().updateToLive(4,"qwertyuiop", call, "a", "b", "c");*/

        try {
            Result credencial = IndifunApplication.getInstance().getCredential();
            String token = credencial.user_token;

            RetroCalls.instance()
                    .stringParam(token)
                    .know_golden_coin((type_result, obj2) -> {
                        loge(TAG, "gold", new Gson().toJson(obj2));
                    });
        } catch (Exception e){

        }


    }

    boolean isplaying;
    void alphamv(){
        if(isplaying){
           topContainer.removeAllViews();
        }else {
            //topContainer.removeAllViews();
            /*AlphaMovieView alpha = findViewById(R.id.video_player1);
            //AttributeSet att = new AttributeSet();
            //alpha.vide
            //topContainer.addView(alpha);
            alpha.setOnVideoEndedListener(new AlphaMovieView.OnVideoEndedListener() {
                @Override
                public void onVideoEnded() {
                    loge(TAG, "onVideoEnded");
                    Toast.makeText(HomeActivity.this, "End", Toast.LENGTH_LONG).show();
                }
            });

            alpha.setVideoFromAssets("big-explosion.mov");*/
        }

        isplaying = !isplaying;


    }

    public void alphamv1(View view) {
       //startActivity(new Intent(this, Test2Activity.class));
        //startActivity(new Intent(this, WelcomeScreenActivity.class));

        //showcase1();
    }


        //ShowcaseUtils.showSequence(ShowcaseUtils.showCaseBuilder());
        /*Activity activity = this;
        BubbleShowCaseSequence case1 = ShowcaseUtils.instance(activity)
                .showSequence(
                        ShowcaseUtils.showCaseBuilder(activity, bottomNavigationView.findViewById( R.id.i_empty), "Broadcast", "Click here to start new broadcast.")
                );*/

        //case1.show();

        /*Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("image1.png");
        list.add("image2.png");
        list.add("image3.png");
        list.add("image4.png");

        map.put("data", "sdfdgfd");
        map.put("images", list);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Post")
                .child("newPost");

        String k = ref.push().getKey();

        ref.child(k).setValue(map);
*/



}