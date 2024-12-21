package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.MySharePref.ISPROFILECOMPLETE;

import static com.deificindia.indifun.Utility.MySharePref.PHONE1;
import static com.deificindia.indifun.Utility.MySharePref.saveData;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.DrawableUtils;
import com.deificindia.indifun.Utility.ListSorting;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.base.BasicBase;
import com.deificindia.indifun.fragments.PostFragment;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.Utility.ImagePickerActivity;
import com.deificindia.indifun.Utility.Indifun;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//import com.squareup.okhttp.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SingupActivity extends BasicBase implements View.OnClickListener {
    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_age;
    private TextView edt_dob;
    Spinner spinnerCounry;
    ImageView refreshLocation;

    DatePicker datePicker;
    private RelativeLayout btn_continue;
    private String  number;
    private ImageView profile_image;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private static final int REQUEST_IMAGE = 100;
    private String picturePath,miUrlOk;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    StorageReference storageRef;
    Uri mImageUri;
    DatabaseReference databaseReference;
    private TextView uploadphototv, tvInfo;
    private LinearLayout uploadll;
    String u_id;
    ImageView imale, ifemale;
    TextView tmale, tfemale;
    LinearLayout lmale, lfemlae;
    int selected_gender = 1;
    String gender_str;
    Bitmap bitmap;
    String mycountry;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

//        getSupportActionBar().hide();
//
//        if(IndifunApplication.getInstance().getCredential()==null || FirebaseAuth.getInstance().getCurrentUser()==null){
//            startActivity(new Intent(SingupActivity.this, LoginActivity.class));
//            return;
//        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        findid();
        number = MySharePref.getData(getApplicationContext(),PHONE1,"");
        storageRef = FirebaseStorage.getInstance().getReference("file");

        time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));

       /* Intent i = getIntent();
        number = i.getStringExtra(KeyClass.PHONE_NUM);*/
        //System.out.println("number" + number);
        /*rb_group.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
            boolean isChecked = checkedRadioButton.isChecked();
            if (isChecked) {
                str_gender = checkedRadioButton.getText().toString();
            }
        });*/

        tvInfo.setText(String.format("You are LoggedIn with %s Phone number, do you want to login with diffrent number? click here", number));
        UiUtils.makeLinks(tvInfo, new Pair<String, View.OnClickListener>("click here", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                IndifunApplication.getInstance().logout();
                startActivity(new Intent(SingupActivity.this, LoginActivity.class));
                finish();
            }
        }));

    }

    private void findid() {
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_age = findViewById(R.id.edt_age);
        edt_dob = findViewById(R.id.edt_dob);
        btn_continue = findViewById(R.id.btn_continue);
        edt_dob.setOnClickListener(this);
        btn_continue.setOnClickListener(this);


        profile_image = findViewById(R.id.profile_image);
        uploadphototv=findViewById(R.id.uploadphototv);
        uploadll=findViewById(R.id.uploadll);
        spinnerCounry=findViewById(R.id.countrySpinner);
        tvInfo=findViewById(R.id.tvInfo);

        imale=findViewById(R.id.img_male);
        ifemale=findViewById(R.id.img_female);
        tmale=findViewById(R.id.tv_male);
        tfemale=findViewById(R.id.tv_female);
        lmale=findViewById(R.id.l_male);
        lfemlae=findViewById(R.id.l_female);
        refreshLocation=findViewById(R.id.refereshLocation);

        uploadll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMediaDialog();
            }

        });

        refreshLocation.setActivated(false);
        refreshLocation.setOnClickListener(v->{
            locationStart();
        });

        locationStart();
        getCountryNames();
        onGenderClick();

    }

    void onGenderClick(){
        lmale.setOnClickListener(v->{
            genderselection(1);
        });
        lfemlae.setOnClickListener(v->{
            genderselection(2);
        });

        DrawableUtils.changeFill(this, imale, R.color.gray);
        tmale.setTextColor(getResources().getColor(R.color.gray));
        DrawableUtils.changeFill(this, ifemale, R.color.gray);
        tfemale.setTextColor(getResources().getColor(R.color.gray));
        selected_gender = 0;
    }

    void genderselection(int i){
        switch (i){
            case 1:
                DrawableUtils.changeFill(this, imale, R.color.colorPrimary);
                tmale.setTextColor(getResources().getColor(R.color.colorPrimary));
                DrawableUtils.changeFill(this, ifemale, R.color.gray);
                tfemale.setTextColor(getResources().getColor(R.color.gray));
                selected_gender = 1;
                gender_str = "Male";
                break;
            case 2:
                DrawableUtils.changeFill(this, imale, R.color.gray);
                tmale.setTextColor(getResources().getColor(R.color.gray));
                DrawableUtils.changeFill(this, ifemale, R.color.colorPrimary);
                tfemale.setTextColor(getResources().getColor(R.color.colorPrimary));
                selected_gender = 2;
                gender_str = "Female";
                break;
        }
    }


    void initSpinner(ArrayList<String> arrayList){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCounry.setSelection(3);
        spinnerCounry.setAdapter(arrayAdapter);




        spinnerCounry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mycountry = parent.getItemAtPosition(position).toString();
                if(position==3){
                    Toast.makeText(SingupActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void openMediaDialog() {
        Dexter.withActivity(SingupActivity.this)
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

    private void launchCameraIntent() {
        Intent intent = new Intent(SingupActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(SingupActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);


        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SingupActivity.this);
        builder.setTitle(getString(R.string.givepermissions));
        builder.setMessage(getString(R.string.pleasegivepermissions));
        builder.setPositiveButton(getString(R.string.app_name), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                mImageUri = data.getParcelableExtra("path");
              //  mImageUri=data.getStringExtra(picturePath);
                // You can update this bitmap to your server
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageUri);
                    picturePath = Constants.encodeTobase64(bitmap);

                    profile_image.setImageBitmap(bitmap);

                    Log.d("picture",picturePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }



   // @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v == edt_dob) {
            calenderDialog();
        } else if (v == btn_continue) {
            validation();
        }
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NewApi")
    private void validation() {
        String name = edt_name.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String dob = edt_dob.getText().toString().trim();
        String age = edt_age.getText().toString().trim();
        if (profile_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.no_image).getConstantState()) {
            Toast.makeText(SingupActivity.this, getString(R.string.pleaseuploadimage), Toast.LENGTH_SHORT).show();
            return;
        } else if (name.isEmpty()) {
            Indifun.toast(SingupActivity.this, "Please fill Name", "e");
            return;
        } else if (email.isEmpty()) {
            Indifun.toast(SingupActivity.this, "Please fill email", "e");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Indifun.toast(SingupActivity.this, "Please fill valid email", "e");
            return;
        } else if (dob.isEmpty()) {
            Indifun.toast(SingupActivity.this, "Please select Date of Birthday", "e");
            return;
        } else if (age.isEmpty()) {
            Indifun.toast(SingupActivity.this, "Please fill age", "e");
            return;
        }else if(mycountry==null && mycountry.isEmpty()){
            Indifun.toast(SingupActivity.this, "Please select your country", "e");
            return;
        }

//        if(!getBool(SingupActivity.this) || mLastLocation==null){
//            Indifun.toast(SingupActivity.this, "Location is not available, click the location icon on right to refresh your location.", "e");
//            return;
//        }

        signUpMethod1(picturePath,number, name, email, dob, gender_str, age);
//        uploadImage_11();

    }

    private void calenderDialog() {

        final Dialog dialog = new Dialog(SingupActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_calender);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        datePicker = dialog.findViewById(R.id.datePicker);

        ((RelativeLayout) dialog.findViewById(R.id.btn_save)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                edt_dob.setText(getCurrentDate());

                //Converting String to Date
                SimpleDateFormat sdf = null;
                SimpleDateFormat sdf2 = null;
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                //System.out.println(sdf2.format(sdf.parse(date)));
                String dob1 = null;
                try {
                    dob1 = sdf2.format(sdf.parse(edt_dob.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = formatter.parse(dob1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Converting obtained Date object to LocalDate object
                Instant instant = date.toInstant();
                ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
                LocalDate givenDate = zone.toLocalDate();
                LocalDate givenDate1 = Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
//                Calculating the difference between given date to current date.
                Period period = Period.between(givenDate, LocalDate.now());
                 System.out.print("year"+period.getYears()+" years "+period.getMonths()+" and "+period.getDays()+" days");
                edt_age.setText(String.valueOf(period.getYears()));
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    private String getCurrentDate() {
        StringBuilder builder = new StringBuilder();
        builder.append(datePicker.getYear() + "-");
        builder.append((datePicker.getMonth() + 1) + "-");//month is 0 based
        builder.append(datePicker.getDayOfMonth());
        return builder.toString();
    }

    private void signUpMethod1(final String picturePath,final String number, final String name, final String email, final String birthday, final String gender, final String age) {

        myDialog = DialogUtils.showProgressDialog(SingupActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SIGNUP,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        myDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                JSONObject jsonObject = obj.optJSONObject("result");
                                Result result = new Result();
                                result.setId(jsonObject.getString("id"));
                                result.setUid(jsonObject.getString("uid"));
                                result.setFullName(jsonObject.getString("full_name"));
                                result.setEmail(jsonObject.getString("email"));
                                result.setFirebaseUid(jsonObject.getString("firebase_uid"));
                                result.setContact(jsonObject.getString("contact"));
                                result.setLatitude(jsonObject.getString("latitude"));
                                result.setLongitude(jsonObject.getString("longitude"));
                                result.setAge(jsonObject.getString("age"));
                                result.setDob(jsonObject.getString("dob"));
                                 result.setProfilePicture(jsonObject.getString("profile_picture"));
                                miUrlOk=jsonObject.getString("profile_picture");
                             //   result.setBio(jsonObject.getString("bio"));
                                result.setGender(jsonObject.getString("gender"));

                                //result.setLanguage(jsonObject.getString("language"));
                                result.setRelationship(jsonObject.getString("relationship"));
                                result.setCountry(jsonObject.getString("country"));
                                result.setState(jsonObject.getString("state"));
                                result.setCity(jsonObject.getString("city"));
                                result.setIsVerified(jsonObject.getString("is_verified"));

                                result.setUser_token(jsonObject.getString("user_token"));

                                IndifunApplication.getInstance().saveCredential(result);

                                saveData(getApplicationContext(), "ldata", jsonObject + "");

                                u_id = jsonObject.getString("id");
                                saveData(getApplicationContext(), "U_ID", u_id);
                                MySharePref.saveBoolData(getApplicationContext(), ISPROFILECOMPLETE, true);

                                if(jsonObject.getString("full_name")!=null
                                        && jsonObject.getString("firebase_uid") !=null
                                        && jsonObject.getString("user_token")!=null) {
                                     //   uploadImage_10();
                                    addToFire(jsonObject.getString("profile_picture"));
                                    Intent intent = new Intent(SingupActivity.this, WelcomeScreenActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            Toast.makeText(SingupActivity.this, "json "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        if(error.getMessage().contains("image")){
                            //Toast.makeText(SingupActivity.this, "All field is required, set your profile image.", Toast.LENGTH_SHORT).show();
                            Indifun.toast(SingupActivity.this, "All field is required, set your profile image.", "e");
                            return;
                        }
                        //All keys and values must be non-null
                        Toast.makeText(SingupActivity.this, "volly "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("contact", number);
                params.put("email", email);
                params.put("age", age);
                params.put("full_name", name);
                params.put("gender", gender);
                params.put("dob", edt_dob.getText().toString());
                params.put("image", picturePath);
                params.put("country", mycountry);
                params.put("longitude", mLastLocation==null?"":String.valueOf(mLastLocation.getLongitude()));
                params.put("latitude", mLastLocation==null?"":String.valueOf(mLastLocation.getLatitude()));
                params.put("firebase_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return params;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }




    private void addToFire( String profile_picture) {

        String level= String.valueOf(1);
        String gold_coin= String.valueOf(0);

        loge("imgeurhh", miUrlOk);
        Result result = IndifunApplication.getInstance().getCredential();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserDetails");
        String postid=databaseReference.push().getKey();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("contact", number);
        hashMap.put("email", edt_email.getText().toString());
        hashMap.put("age", edt_age.getText().toString());
        hashMap.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("full_name", edt_name.getText().toString());
        hashMap.put("gender", gender_str);
        hashMap.put("dob", edt_dob.getText().toString());
        hashMap.put("image",  profile_picture);
        hashMap.put("country", mycountry);
        hashMap.put("id1",result.getId());
        hashMap.put("status", "offline");
        hashMap.put("level",level);
        hashMap.put("time",time);
//        hashMap.put("id",FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("user_id",result.getId());
        hashMap.put("gold_coin",gold_coin);
        hashMap.put("uid",result.getUid());
//        hashMap.put("search", edt_name.toLowerCase());
        hashMap.put("longitude", mLastLocation==null?"":String.valueOf(mLastLocation.getLongitude()));
        hashMap.put("latitude", mLastLocation==null?"":String.valueOf(mLastLocation.getLatitude()));
        hashMap.put("firebase_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("fuid", FirebaseAuth.getInstance().getCurrentUser().getUid());
       databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull @NotNull Task<Void> task) {
            loge("find",new Gson().toJson(miUrlOk));
           }
       });


    }



    void getCountryNames(){
        Call<CountryNamesResult> call = AppConfig.loadInterface().getCountry();
        call.enqueue(new Callback<CountryNamesResult>() {
            @Override
            public void onResponse(Call<CountryNamesResult> call, retrofit2.Response<CountryNamesResult> response) {

                CountryNamesResult result = response.body();

                loge("SIGNUP", new Gson().toJson(result));
                if(result!=null &&  result.getStatus()==1 && result.getResult()!=null){
                    ArrayList<String> newdata = new ArrayList<>();
                    Collections.sort(result.getResult(),new ListSorting.ShortCountry());
                    for (CountryNamesResult.MyCountry country:result.getResult()){
                        newdata.add(country.getCountry());
                    }
                    newdata.add("Other");
                    initSpinner(newdata);

                }
            }

            @Override
            public void onFailure(Call<CountryNamesResult> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onSharedPrefChange(String k) {
        if(k.equals(KEY_LOCATION_UPDATES_REQUESTED)){
            if(getBool(SingupActivity.this)){
                refreshLocation.setActivated(true);
            }
        }
    }

}