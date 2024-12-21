package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.KeyClass;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.fragments.OtpFragment;
import com.deificindia.indifun.fragments.PhoneFragment;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.vm.LoginVm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.MySharePref.PHONE1;
import static com.deificindia.indifun.Utility.MySharePref.USERID;

public class LoginActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private static final String TAG = "LoginActivity";
    private TextView txt_header_title;
    private Spinner country_spinner;
    private String number;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private View parent;
    //firebase auth object
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private LoginVm loginVm;
    private int otp_phone = 0;
    private boolean showingBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginVm  = new ViewModelProvider(this).get(LoginVm.class);
        mAuth = FirebaseAuth.getInstance();

        bindView();
        viewSetup();

        if(savedInstanceState==null){
            replaceFragment(new PhoneFragment(), "PhoneFrag");
            setHeader("Login");
        }else {
            showingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        getFragmentManager().addOnBackStackChangedListener(this);

    }

    private void bindView() {
        parent =  findViewById(R.id.parent);
        txt_header_title =  findViewById(R.id.txt_header_title);
    }

    private void setHeader(String s){ txt_header_title.setText(s); }

    private void viewSetup() {

        loginVm.livelistener().observe(this, item->{
            switch (item.TYPE){
                case 1:
                    replaceFragment(new PhoneFragment(), "PhoneFrag");
                    setHeader("Login");
                    otp_phone=0;
                    break;
                case 2:
                    //replaceFragment(new OtpFragment(), "OtpFrag");
                    flipCard("");
                    otp_phone=1;
                    break;
                case 3:

                    MySharePref.saveData(getApplicationContext(), PHONE1, item.data);
                    /*if(mAuth.getCurrentUser()!=null){
                        loginperform(item.data);
                        return;
                    }*/

                    number = item.data;
                    sendVerificationCode(item.data);
                    break;
                case 4:
                    verifyVerificationCode(item.data);
                    break;
                case 5:
                    resendVerificationCode(mResendToken);
                    break;
            }
        });


        findViewById(R.id.img_back).setOnClickListener(vv->{
            //viewModel.sendData(new LoginVmModal(2));
            if(otp_phone==0){
                //replaceFragment(new OtpFragment(), "OtpFrag");
                //otp_phone=1;
                finish();
            }else if(otp_phone==1){
                replaceFragment(new PhoneFragment(), "PhoneFrag");
                setHeader("Login");
                otp_phone=0;
            }else {
                finish();
            }
        });
    }


    ///////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    private void sendVerificationCode(String mobile) {
        myDialog = DialogUtils.showProgressDialog(LoginActivity.this, "Getting OTP...");
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            //Getting the code sent by SMS
            String code = credential.getSmsCode();
            Log.d(TAG, "onVerificationCompleted:" + credential);
            signInWithPhoneAuthCredential(credential);
            closedialog();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e);
            closedialog();
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
                snack("Invalid request");
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
                snack("Otp quota exceeded");
            }

            // Show a message and update the UI
            // ...
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;

            closedialog();
            //replaceFragment(OtpFragment.newInstance(number, ""), "OtpFrag");
            flipCard(number);
            otp_phone=1;
            // ...
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        closedialog();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            removeFragment();
                            txt_header_title.setText("Waiting...");
                            loadingProfile();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                snack("The verification code entered was invalid");
                            }
                        }
                    }
                });
    }


    private void verifyVerificationCode(String code) {
        myDialog = DialogUtils.showProgressDialog(LoginActivity.this, "Verifying OTP...");
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
        PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(number)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .setForceResendingToken(token)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // ForceResendingToken from callbacks
    }

    void snack(String message){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
    }

///////////////////////////////////////////////////////////////////////////////////////////////

    private void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
       .beginTransaction()
        .replace(R.id.container5, fragment, tag)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .commit();
    }

    private void flipCard(String phone) {
        if (showingBack) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        showingBack = true;

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.container5,  OtpFragment.newInstance(phone, ""))
                .addToBackStack(null)
                .commit();
    }

    private void removeFragment() {
        if (getSupportFragmentManager().findFragmentById(R.id.container5) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(
                            getSupportFragmentManager().findFragmentById(R.id.container5)




                   ).commit();
        }
    }

    void loadingProfile(){
        if(otp_phone==0) {
            FirebaseAuth.getInstance().signOut();
            return;
        }

        loginperform(number);

    }

    void closedialog(){
        if(myDialog!=null && myDialog.isShowing()) myDialog.dismiss();
    }
//////////////////////////////////////////////////////////////////////////////////////////////

    private void loginperform2(final String mobile) {
       // RetroConfig.createService(LoadInterface.class)
        AppConfig.loadInterface().
                login(mobile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    loge(TAG, "res "+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
/*
                try {
                    Result res = response.body().getResult();
                    String data = new Gson().toJson(res);
                    loge(TAG, "data", data);
                    //convertDataToStr(response.body().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loge(TAG, "errr "+t.getMessage());
            }
        });
    }

    void convertDataToStr(String response){
        try {
            JSONObject obj = new JSONObject(response);
            if (obj.optInt("status") == 1) {
                myDialog.dismiss();
                JSONObject jsonObject = obj.optJSONObject("result");
                Result result = new Result();
                if(jsonObject.getString("id")!=null)
                    result.setId(jsonObject.getString("id"));
                if(jsonObject.getString("uid")!=null)
                    result.setUid(jsonObject.getString("uid"));
                if(jsonObject.getString("full_name")!=null)
                    result.setFullName(jsonObject.getString("full_name"));
                if(jsonObject.getString("email")!=null)
                    result.setEmail(jsonObject.getString("email"));
                result.setFirebaseUid(jsonObject.getString("firebase_uid"));
                if(jsonObject.getString("contact")!=null)
                    result.setContact(jsonObject.getString("contact"));
                if(jsonObject.getString("latitude")!=null)
                    result.setLatitude(jsonObject.getString("latitude"));
                if(jsonObject.getString("longitude")!=null)
                    result.setLongitude(jsonObject.getString("longitude"));
                if(jsonObject.getString("profile_picture")!=null)
                    result.setProfilePicture(jsonObject.getString("profile_picture"));
                if(jsonObject.getString("age")!=null)
                    result.setAge(jsonObject.getString("age"));
                if(jsonObject.getString("dob")!=null)
                    result.setDob(jsonObject.getString("dob"));
                // result.setBio(jsonObject.getString("bio"));
                if(jsonObject.getString("gender")!=null)
                    result.setGender(jsonObject.getString("gender"));
                //result.setLanguage(jsonObject.getString("language"));
                if(jsonObject.getString("relationship")!=null)
                    result.setRelationship(jsonObject.getString("relationship"));
                if(jsonObject.getString("country")!=null)
                    result.setCountry(jsonObject.getString("country"));
                if(jsonObject.getString("state")!=null)
                    result.setState(jsonObject.getString("state"));
                if(jsonObject.getString("city")!=null)
                    result.setCity(jsonObject.getString("city"));
                if(jsonObject.getString("is_verified")!=null)
                    result.setIsVerified(jsonObject.getString("is_verified"));
                //if(jsonObject.getString("level")!=null)
                //    result.setLevel(jsonObject.getString("level"));

                if(jsonObject.getString("user_token")!=null)
                    result.setUser_token(jsonObject.getString("user_token"));

                new Thread(()->{
                    IndifunApplication.getInstance().saveCredential(result);

                    try {
                        MySharePref.saveData(getApplicationContext(), USERID, jsonObject.getString("id"));
                        MySharePref.saveData(getApplicationContext(), PHONE1, jsonObject.getString("contact"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         switchActivity();
                     }
                 });
                }).start();



            }

            else {
                MySharePref.saveData(getApplicationContext(),PHONE1,number);
                myDialog.dismiss();
//
                //Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
               // switchActivity();
                Intent intent = new Intent(LoginActivity.this, SingupActivity.class);
                intent.putExtra(KeyClass.PHONE_NUM, number);
                startActivity(intent);
                finish();

            }

        } catch (JSONException e) {
            myDialog.dismiss();
            Toast.makeText(LoginActivity.this, "json: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            showError(e);
        }
    }

    private void loginperform(final String mobile) {

        myDialog = DialogUtils.showProgressDialog(LoginActivity.this, "Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_lOGIN,
                response -> {
                    loge(TAG, "", new Gson().toJson(response));
                    convertDataToStr(response);
                },
                error -> {
                    myDialog.dismiss();
                    showError(error);
                    Toast.makeText(LoginActivity.this, "volly: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("contact", mobile);
                return params;
            }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
               *//* Map<String, String> params = new HashMap<String, String>();
                params.put(AppConstants.TastyTiffinKEY, AppConstants.TastyTiffinKEYVALUE);
                return params;*//*
               return null;
            }*/
        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    void switchActivity(){
        Result res = IndifunApplication.getInstance().getCredential();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(res!=null && user!=null){

            if(res.user_token!=null && res.getId()!=null && res.getFullName()!=null && res.getFirebaseUid().equals(user.getUid())){
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra(KeyClass.PHONE_NUM, number);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(LoginActivity.this, SingupActivity.class);
                intent.putExtra(KeyClass.PHONE_NUM, number);
                startActivity(intent);
                finish();
            }
        }
    }

    private void showError(Throwable e) {
        loge(TAG, "Error", e.getMessage());
        String message = "";
        try {
            if (e instanceof IOException) {
                message = "No internet connection!";
            } else if (e instanceof HttpException) {
                HttpException error = (HttpException) e;
                String errorBody = Objects.requireNonNull(error.response()).message();
                JSONObject jObj = new JSONObject(errorBody);

                message = jObj.getString("error");
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (TextUtils.isEmpty(message)) {
            message = "Unknown error occurred!";
        }

        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public void onBackStackChanged() {
        showingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }
}