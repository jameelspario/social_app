package com.deificindia.indifun.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.chat.Model.User;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.MySharePref.saveData;

public class EditProfileActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;

    private ImageView img_back;
    private TextView txt_header_title, edt_dob, getcurentlocation;
    private EditText edt_name, edt_email, edt_mobile, edt_age, edt_whatsup, edt_cureentlocaton;
    private RadioGroup rb_group, rb_relationshipstatus;
    private RadioButton male, female, single, inarelationship, mrried, privacy;
    private RelativeLayout btn_continue;
    private String str_gender;
    private String str_relationshipstatus;
    private String country, city, state;
    private String latitude, longitude;
    private GpsTracker gpsTracker;
    private CircleImageView circle_layout;
    LocationRequest mLocationRequest;
    private LocationManager locationManager;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        img_back = findViewById(R.id.img_back);
        txt_header_title = findViewById(R.id.txt_header_title);

        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_age = findViewById(R.id.edt_age);
        edt_whatsup = findViewById(R.id.edt_whatsup);
        edt_dob = findViewById(R.id.edt_dob);
        edt_cureentlocaton = findViewById(R.id.edt_cureentlocaton);
        rb_group = findViewById(R.id.rb_group);
        rb_relationshipstatus = findViewById(R.id.rb_relationshipstatus);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        single = findViewById(R.id.single);
        mrried = findViewById(R.id.mrried);
        inarelationship = findViewById(R.id.inarelationship);
        privacy = findViewById(R.id.privacy);
        btn_continue = findViewById(R.id.btn_continue);
        getcurentlocation = findViewById(R.id.getcurentlocation);
         firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        //getprofile();
      //  setdata();
        setuserdetails();
        txt_header_title.setText(getString(R.string.updateprofile));
        locationManager = (LocationManager) EditProfileActivity.this.getSystemService(Context.LOCATION_SERVICE);

        getcurrentlatlong();

        getcurentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getcurrentlatlong();
               /* if (location != null) {
                    System.out.println("Provider " + provider + " has been selected.");
                    onLocationChanged(location);
                } else {

                }*/
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rb_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {

                    str_gender = checkedRadioButton.getText().toString();
                }
            }
        });

        rb_relationshipstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    str_relationshipstatus = checkedRadioButton.getText().toString();
                }
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if (str_gender==null  || str_gender.isEmpty()) {
                    str_gender = male.getText().toString();
                   // Toast.makeText(EditProfileActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }

                else if (str_relationshipstatus==null || str_relationshipstatus.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please Select Relationship Status", Toast.LENGTH_SHORT).show();
                } else if (edt_whatsup.getText().toString().trim().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please Enter Your What's Up Thought", Toast.LENGTH_SHORT).show();
                } else if (edt_cureentlocaton.getText().toString().trim().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please Select Your Current Location", Toast.LENGTH_SHORT).show();
                } else {
                    updateprofile();
                    updateProfile();
                }
            }
        });
    }


    public void getcurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                EditProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                EditProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            gpsTracker = new GpsTracker(EditProfileActivity.this);
            if (gpsTracker.canGetLocation()) {
                double lat = gpsTracker.getLatitude();
                double longi = gpsTracker.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                if ((latitude != null && !latitude.isEmpty()) && (longitude != null && !longitude.isEmpty())) {
                    getCompleteAddressString1(lat, longi);
                    //   onMapReady(mMap);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();

                }
            } else {
                GoogleApiClient googleApiClient = new GoogleApiClient.Builder(EditProfileActivity.this)
                        .addApi(LocationServices.API).build();
                googleApiClient.connect();
                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(10000);
                locationRequest.setFastestInterval(10000 / 2);

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);

                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:
                                Log.i("", "All location settings are satisfied.");
                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i("", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the result
                                    // in onActivityResult().
                                    status.startResolutionForResult(EditProfileActivity.this, 1);
                                } catch (IntentSender.SendIntentException e) {
                                    Log.i("", "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Log.i("", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                                break;
                        }
                    }
                });
                getcurrentLocation();
            }

        }
    }

    private void getcurrentlatlong() {
        locationManager = (LocationManager) EditProfileActivity.this.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getcurrentLocation();
        }
    }


    private void OnGPS() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(EditProfileActivity.this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(EditProfileActivity.this, 1);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
        //getcurrentLocation();
    }


    private void getCompleteAddressString1(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                city = returnedAddress.getLocality();
                state = returnedAddress.getAdminArea();
                country = returnedAddress.getCountryName();
                edt_cureentlocaton.setText(city + " , " + state + " , " + country);


            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    public void setuserdetails(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                edt_name.setText(user.getFull_name());
                edt_email.setText(user.getEmail());
                edt_mobile.setText(user.getContact());
                edt_dob.setText(user.getDob());
                edt_age.setText(user.getAge());

                if (user.getWhatsup() != null && !user.getWhatsup().isEmpty()) {
                    edt_whatsup.setText(user.getWhatsup());
                }else{
                    edt_whatsup.setText("What's Up");
                }

                if (user.getGender() != null && !user.getGender().isEmpty() && user.getGender().equalsIgnoreCase("Male")) {
                    male.setChecked(true);
                } else if (user.getGender() != null && !user.getGender().isEmpty() && user.getGender().equalsIgnoreCase("Female")) {
                    female.setChecked(true);
                }

                if (user.getRelationship() != null && !user.getRelationship().isEmpty() && user.getRelationship().equalsIgnoreCase("1")) {
                    single.setChecked(true);
                } else if (user.getRelationship() != null && !user.getRelationship().isEmpty() && user.getRelationship().equalsIgnoreCase("2")) {
                    inarelationship.setChecked(true);
                } else if (user.getRelationship() != null && !user.getRelationship().isEmpty() && user.getRelationship().equalsIgnoreCase("3")) {
                    mrried.setChecked(true);
                } else if (user.getRelationship() != null && !user.getRelationship().isEmpty() && user.getRelationship().equalsIgnoreCase("4")) {
                    privacy.setChecked(true);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setdata(){
        Result result = IndifunApplication.getInstance().getCredential();
        edt_name.setText(result.getFullName());
        edt_email.setText(result.getEmail());
        edt_mobile.setText(result.getContact());
        edt_dob.setText(result.getDob());
        edt_age.setText(result.getAge());

        if (result.getWhatsup() != null && !result.getWhatsup().isEmpty()) {
            edt_whatsup.setText(result.getWhatsup());
        }else{
            edt_whatsup.setText("What's Up");
        }

        if (result.getGender() != null && !result.getGender().isEmpty() && result.getGender().equalsIgnoreCase("Male")) {
            male.setChecked(true);
        } else if (result.getGender() != null && !result.getGender().isEmpty() && result.getGender().equalsIgnoreCase("Female")) {
            female.setChecked(true);
        }

        if (result.getRelationship() != null && !result.getRelationship().isEmpty() && result.getRelationship().equalsIgnoreCase("1")) {
            single.setChecked(true);
        } else if (result.getRelationship() != null && !result.getRelationship().isEmpty() && result.getRelationship().equalsIgnoreCase("2")) {
            inarelationship.setChecked(true);
        } else if (result.getRelationship() != null && !result.getRelationship().isEmpty() && result.getRelationship().equalsIgnoreCase("3")) {
            mrried.setChecked(true);
        } else if (result.getRelationship() != null && !result.getRelationship().isEmpty() && result.getRelationship().equalsIgnoreCase("4")) {
            privacy.setChecked(true);
        }
    }

    private void updateprofile() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(EditProfileActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATEROFILE,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                Result result = new Result();
                                result.setId(jsonObject.getString("id"));
                                result.setUid(jsonObject.getString("uid"));
                                result.setFullName(jsonObject.getString("full_name"));
                                result.setEmail(jsonObject.getString("email"));
                                result.setContact(jsonObject.getString("contact"));
                                result.setLatitude(jsonObject.getString("latitude"));
                                result.setLongitude(jsonObject.getString("longitude"));
                                result.setAge(jsonObject.getString("age"));
                                result.setDob(jsonObject.getString("dob"));
                                result.setProfilePicture(jsonObject.getString("profile_picture"));
                                //   result.setBio(jsonObject.getString("bio"));
                                result.setGender(jsonObject.getString("gender"));
                                //result.setLanguage(jsonObject.getString("language"));
                                result.setRelationship(jsonObject.getString("relationship"));
                                result.setCountry(jsonObject.getString("country"));
                                result.setState(jsonObject.getString("state"));
                                result.setCity(jsonObject.getString("city"));
                                result.setIsVerified(jsonObject.getString("is_verified"));

                                result.setUser_token(jsonObject.getString("user_token"));
                                result.setFirebaseUid(jsonObject.getString("firebase_uid"));

                                IndifunApplication.getInstance().saveCredential(result);

                                saveData(getApplicationContext(), "ldata", jsonObject + "");
                                String u_id = jsonObject.getString("id");
                                String u_userid = jsonObject.getString("uid");
                                String u_name = jsonObject.getString("full_name");
                                String u_email = jsonObject.getString("email");
                                String u_contact = jsonObject.getString("contact");
                                saveData(getApplicationContext(), "U_ID", u_id);

                                Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                myDialog.dismiss();
                                Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            //Toast.makeText(EditProfileActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        //Toast.makeText(EditProfileActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("full_name", edt_name.getText().toString().trim());
                params.put("gender", str_gender);
                params.put("whatsup", edt_whatsup.getText().toString().trim());
                if (str_relationshipstatus.equalsIgnoreCase("Single")) {
                    params.put("relationship", "1");
                } else if (str_relationshipstatus.equalsIgnoreCase("In a relationship")) {
                    params.put("relationship", "2");
                } else if (str_relationshipstatus.equalsIgnoreCase("Married")) {
                    params.put("relationship", "3");
                } else if (str_relationshipstatus.equalsIgnoreCase("Privacy")) {
                    params.put("relationship", "4");
                }
                params.put("country", country);
                params.put("state", state);
                params.put("city", city);
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
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


    private void updateProfile(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(firebaseUser.getUid());

        HashMap<String, Object> params = new HashMap<>();
        params.put("full_name", edt_name.getText().toString().trim());
        params.put("gender", str_gender);
        params.put("whatsup", edt_whatsup.getText().toString().trim());
        if (str_relationshipstatus.equalsIgnoreCase("Single")) {
            params.put("relationship", "1");
        } else if (str_relationshipstatus.equalsIgnoreCase("In a relationship")) {
            params.put("relationship", "2");
        } else if (str_relationshipstatus.equalsIgnoreCase("Married")) {
            params.put("relationship", "3");
        } else if (str_relationshipstatus.equalsIgnoreCase("Privacy")) {
            params.put("relationship", "4");
        }
        params.put("country", country);
        params.put("state", state);
        params.put("city", city);
        params.put("uid", IndifunApplication.getInstance().getCredential().getUid());
        reference.updateChildren(params);
       // loadingDialog.dismissDialog();
//        startActivity(new Intent(EditActivity.this, Profilenew.class));
//        finish();
        Toast.makeText(getApplicationContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();
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
