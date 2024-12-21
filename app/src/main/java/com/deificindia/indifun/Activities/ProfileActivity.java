package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.Adapter.HomeViewPagerAdapter2;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.ImagePickerActivity;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.bindingmodals.otheruserprofile.Language;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.pojo.getuserprofilegallerypojo.ResultItem;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.TagView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import co.lujun.androidtagview.TagContainerLayout;
import okhttp3.MultipartBody;

import static com.deificindia.indifun.Utility.Logger.loge;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE =100 ;
    private MultipartBody.Part multi_driver_image;
    private ImageView img_back,addaphoto;
    private ImageView addaphoto1,editprofile,editinterst;

    private int position = 0;
    private ImageView[] dots;
    ViewPager viewPager;
    LinearLayout sliderDotspanel, tagsLayout;
    RelativeLayout sliders;
    private LinearLayout momnetll,interstll,langauell;
    private int dotscount;
    ArrayList<ResultItem> resultItems = new ArrayList<>();
    private ImageView editlangaue;
    ArrayList<com.deificindia.indifun.pojo.userinterstpojo.ResultItem> userinterstresultItems = new ArrayList<>();
    ArrayList<com.deificindia.indifun.pojo.alllangaugespojo.ResultItem> userlangaueresultItems = new ArrayList<>();

    private String picturePath;
    private TextView username,totalmoments,relationshiptv,currentlocationtv;
    private RecyclerView momentlist;
    private MOmnetsAdapter mOmnetsAdapter;

    private TextView nolanguages,nointerst;

    private TagContainerLayout tagsLang, tagsInterest;

    private String userUid;
    private String user_name;
    private CardView circle_layout;


    UserTags userTags;

    Map<String, TagView> tagsMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findid();

    }

    private void findid() {
        userUid = getIntent().getStringExtra("UID");
        user_name = getIntent().getStringExtra("NAME");


loge("hellofind",user_name+userUid);
        img_back =  findViewById(R.id.img_back);
        addaphoto =  findViewById(R.id.addaphoto);
        sliders =  findViewById(R.id.sliders);
        viewPager =  findViewById(R.id.viewPager);
        sliderDotspanel =  findViewById(R.id.SliderDots);
        username =  findViewById(R.id.username);

        tagsLayout =  findViewById(R.id.tagslayout);

        totalmoments = findViewById(R.id.totalmoments);
        addaphoto1 = findViewById(R.id.addaphoto1);
        totalmoments = findViewById(R.id.totalmoments);

        momentlist=findViewById(R.id.momentlist);
        momnetll=findViewById(R.id.momnetll);
        relationshiptv=findViewById(R.id.relationshiptv);

        tagsLang=findViewById(R.id.langtags);
        tagsInterest=findViewById(R.id.interesttags);

        currentlocationtv=findViewById(R.id.currentlocationtv);
        interstll=findViewById(R.id.interstll);
        editprofile=findViewById(R.id.editprofile);
        langauell=findViewById(R.id.langauell);
        editlangaue=findViewById(R.id.editlangaue);
        nolanguages=findViewById(R.id.nolanguages);
        nointerst=findViewById(R.id.nointerst);
        editinterst=findViewById(R.id.editinterst);


        userTags = UserTags.instance().container(tagsLayout);
        circle_layout=findViewById(R.id.card_layout);
        circle_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent find=new Intent(getApplicationContext(),FamilyMember.class);
                startActivity(find);
            }
        });
        img_back.setOnClickListener(v -> onBackPressed());
        
        addaphoto.setOnClickListener(v -> {
            startActivity(
                    new Intent(ProfileActivity.this, ProfileGallery.class),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, Objects.requireNonNull(ViewCompat.getTransitionName(v))).toBundle());
        });

        addaphoto1.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, ProfileGallery.class),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, Objects.requireNonNull(ViewCompat.getTransitionName(v))).toBundle()));

        editprofile.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, Objects.requireNonNull(ViewCompat.getTransitionName(v))).toBundle()));

        editlangaue.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, LanguageList.class),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, langauell, Objects.requireNonNull(ViewCompat.getTransitionName(langauell))).toBundle()));

        editinterst.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, InterstList.class),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, interstll, Objects.requireNonNull(ViewCompat.getTransitionName(interstll))).toBundle()));

        username.setText(user_name);
        matchuser();

        getuserprofilegallery();
        getusermoments();
        getuserinterest();
        getuserlanguage();

    }

    String user_relationShipStatus;
    String userCountry;
    String userCity;
    String userState;
    String userAge;
    String userGender;

    void matchuser(){
        user_relationShipStatus = getResources().getString(R.string.notfilledin);
        Result ceredential = IndifunApplication.getInstance().getCredential();

        if(ceredential!=null && userUid.equalsIgnoreCase(ceredential.getId())){
            if(ceredential.getRelationship()!=null && !ceredential.getRelationship().isEmpty()){

                if(ceredential.getRelationship().equalsIgnoreCase("1")) {
                    user_relationShipStatus="Single";
                }else if(ceredential.getRelationship().equalsIgnoreCase("2")) {
                    user_relationShipStatus="In A Relationship";
                }else if(ceredential.getRelationship().equalsIgnoreCase("3")) {
                    user_relationShipStatus="Married";
                }else if(ceredential.getRelationship().equalsIgnoreCase("4")) {
                    user_relationShipStatus="Privacy";
                }
            }else {
                user_relationShipStatus=getString(R.string.notfilledin);
            }

            if(ceredential.getCountry()!=null && !ceredential.getCountry().isEmpty() && ceredential.getCity()!=null && !ceredential.getCity().isEmpty() && ceredential.getState()!=null && !ceredential.getState().isEmpty()){
                userCountry=ceredential.getCity()+" , "+ceredential.getState()+" , "+ceredential.getCountry();
            }else {
                userCountry=getString(R.string.notfilledin);
            }

            userAge = ceredential.getAge();

            userGender = ceredential.getGender();

            setPrimaryData();
        }else{

            addaphoto1.setVisibility(View.GONE);
            editprofile.setVisibility(View.GONE);
            editinterst.setVisibility(View.GONE);
            editlangaue.setVisibility(View.GONE);
            addaphoto.setVisibility(View.GONE);

        }
    }


    void setPrimaryData(){
        relationshiptv.setText(user_relationShipStatus);
        currentlocationtv.setText(userCountry);

        userTags.addTo(UserTags.GENDER)
                .updateGender(userAge,  userGender);

    }


    private void openMediaDialog() {
        Dexter.withActivity(ProfileActivity.this)
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
        Intent intent = new Intent(ProfileActivity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(ProfileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);


        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
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
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    picturePath = encodeTobase64(bitmap);
                    addaprofilegallery(picturePath);
                    //addAvatar.setVisibility(View.VISIBLE);
                   // profile_image.setImageBitmap(bitmap);
                    /*imgUser.setImageBitmap ( bitmap );
                    strImg1=encodeTobase64(bitmap);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private String getEncodedString(Bitmap bitmap) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

      /* or use below if you want 32 bit images

       bitmap.compress(Bitmap.CompressFormat.PNG, (0–100 compression), os);*/
        byte[] imageArr = os.toByteArray();
        //imageArr1 = imageArr;
        return Base64.encodeToString(imageArr, Base64.DEFAULT);

    }

    private void init1() {
        HomeViewPagerAdapter2 viewPagerAdapter = new HomeViewPagerAdapter2(ProfileActivity.this);
        if (viewPagerAdapter != null) {
            viewPagerAdapter.clearData();
            dotscount = 0;
            dots = null;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.removeAllViews();

        }
        //viewPager.startAutoScroll();
      //  viewPager.setInterval(3000);
       // viewPager.setCycle(true);
       // viewPager.setStopScrollWhenTouch(true);
        viewPager.setAdapter(viewPagerAdapter);
        ArrayList<ResultItem> channelBeans = new ArrayList<>();

        for (int i = 0; i < resultItems.size(); i++) {
            channelBeans.clear();
            channelBeans.add(resultItems.get(i));

            viewPagerAdapter.addItem(channelBeans);
        }
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            if (ProfileActivity.this != null) {
                dots[i] = new ImageView(ProfileActivity.this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.nonactive_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                sliderDotspanel.addView(dots[i], params);
            }

        }

        if (dotscount > 0) {
            dots[position].setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.active_dot));
        } else {
            sliderDotspanel.setVisibility(View.GONE);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //   dots = new ImageView[dotscount];

                for (int i = 0; i < dotscount; i++) {
                    if (ProfileActivity.this != null) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this , R.drawable.nonactive_dot));
                    }
                }
                if (ProfileActivity.this  != null) {
                    dots[position].setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this , R.drawable.active_dot));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //moment pic list
    private void getuserprofilegallery() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(ProfileActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETUSEPROFILEGALLERY,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                sliders.setVisibility(View.VISIBLE);
                                momnetll.setVisibility(View.VISIBLE);
                                myDialog.dismiss();
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setUserId(qstnArray.getString("user_id"));
                                    resultItem.setImage(qstnArray.getString("image"));
                                    resultItem.setEntryDate(qstnArray.getString("entry_date"));
                                    resultItems.add(resultItem);
                                }

                                totalmoments.setText(String.valueOf(resultItems.size()));
                                mOmnetsAdapter = new MOmnetsAdapter(ProfileActivity.this);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(ProfileActivity.this, 1, LinearLayoutManager.HORIZONTAL, false);

                                GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                                    @Override
                                    public int getSpanSize(int position) {
                                        // for position 0 use only one row. for this position it will take all rows
                                        if (position == 0) {
                                            return 6;
                                        }

                                        return 2;
                                    }
                                };
                                //  innercatlist.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                momentlist.setLayoutManager(gridLayoutManager);
                                momentlist.setAdapter(mOmnetsAdapter);

                                mOmnetsAdapter.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems.get(i));

                                    mOmnetsAdapter.addItem(channelBeans);
                                }
                                init1();


                            } else {
                                myDialog.dismiss();
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                momnetll.setVisibility(View.GONE);
                                sliders.setVisibility(View.GONE);
                                mOmnetsAdapter = new MOmnetsAdapter(ProfileActivity.this);
                                momentlist.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                                momentlist.setAdapter(mOmnetsAdapter);
                                mOmnetsAdapter.notifyDataSetChanged();
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userUid);
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private void getusermoments() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(ProfileActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETUSERMOMENTS,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                sliders.setVisibility(View.VISIBLE);
                                momnetll.setVisibility(View.VISIBLE);
                                myDialog.dismiss();
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setUserId(qstnArray.getString("user_id"));
                                    resultItem.setImage(qstnArray.getString("image"));
                                    resultItem.setEntryDate(qstnArray.getString("entry_date"));
                                    resultItems.add(resultItem);
                                }

                                totalmoments.setText(String.valueOf(resultItems.size()));
                                mOmnetsAdapter = new MOmnetsAdapter(ProfileActivity.this);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(ProfileActivity.this, 1, LinearLayoutManager.HORIZONTAL, false);

                                GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                                    @Override
                                    public int getSpanSize(int position) {
                                        // for position 0 use only one row. for this position it will take all rows
                                        if (position == 0) {
                                            return 6;
                                        }

                                        return 2;
                                    }
                                };
                                //  innercatlist.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                momentlist.setLayoutManager(gridLayoutManager);
                                momentlist.setAdapter(mOmnetsAdapter);

                                mOmnetsAdapter.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems.get(i));

                                    mOmnetsAdapter.addItem(channelBeans);
                                }
                                init1();


                            } else {
                                myDialog.dismiss();
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                momnetll.setVisibility(View.GONE);
                                sliders.setVisibility(View.GONE);
                                mOmnetsAdapter = new MOmnetsAdapter(ProfileActivity.this);
                                momentlist.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                                momentlist.setAdapter(mOmnetsAdapter);
                                mOmnetsAdapter.notifyDataSetChanged();
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userUid);
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private class MOmnetsAdapter extends RecyclerView.Adapter<MOmnetsAdapter.ViewHolder> {

        private ArrayList<ResultItem> resultItems1 = new ArrayList<>();
        private Context context;


        public MOmnetsAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.momentlist_row, parent, false);


            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            if (resultItems1.get(position).getImage() != null && !resultItems1.get(position).getImage().isEmpty()) {

                Picasso.get()
                        .load(URLs.UserImageBaseUrl + resultItems1.get(position).getImage())
                        .error(R.drawable.no_image)
                        .into(holder.userimage);
            } else {
                holder.userimage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            }
            /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), HistoryDetailActivity1.class)
                            .putExtra(AppConstants.CUSTOMERNAME, deliveryOrderDataItems1.get(position).getCustomerName())
                            .putExtra(AppConstants.ORDERID, deliveryOrderDataItems1.get(position).getDeliveryOrderRand())
                            .putExtra(AppConstants.SUBTOTAL, deliveryOrderDataItems1.get(position).getProductPayment())
                            .putExtra(AppConstants.EPLADDRESS, deliveryOrderDataItems1.get(position).getPlAddress() + " , " + deliveryOrderDataItems1.get(position).getEPlCityName() + " , " + deliveryOrderDataItems1.get(position).getEPlCountryName())
                            .putExtra(AppConstants.APLADDRESS, deliveryOrderDataItems1.get(position).getPlAddress() + " , " + deliveryOrderDataItems1.get(position).getAPlCityName() + " , " + deliveryOrderDataItems1.get(position).getAPlCountryName())
                            .putExtra(AppConstants.EDLADDRESS, deliveryOrderDataItems1.get(position).getDlAddress() + " , " + deliveryOrderDataItems1.get(position).getEDlCityName() + " , " + deliveryOrderDataItems1.get(position).getEDlCountryName())
                            .putExtra(AppConstants.ADLADDRESS, deliveryOrderDataItems1.get(position).getDlAddress() + " , " + deliveryOrderDataItems1.get(position).getADlCityName() + " , " + deliveryOrderDataItems1.get(position).getADlCountryName())
                            .putExtra(AppConstants.DELNOTE, deliveryOrderDataItems1.get(position).getDlNote())
                            .putExtra(AppConstants.DISTANCE, holder.distancetv.getText().toString().trim())
                            .putExtra(AppConstants.DELORDERID, deliveryOrderDataItems1.get(position).getDeliveryOrderId() )
                            .putExtra(AppConstants.DELORDERSTATUS, deliveryOrderDataItems1.get(position).getDeliveryOrderStatus() )
                            .putExtra(AppConstants.PAYMENTTYPE, deliveryOrderDataItems1.get(position).getProductPaymentMethod()));
                    ;

                }
            });*/
        }

        @Override
        public int getItemCount() {
            return resultItems1.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void addItem(ArrayList<ResultItem> channelBeans) {

            resultItems1.addAll(channelBeans);
            notifyDataSetChanged();
        }

        public void clearData() {
            resultItems1.clear(); // clear list
            notifyDataSetChanged(); // let your adapter know about the changes and reload view.
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView userimage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.userimage = itemView.findViewById(R.id.userimage);
            }
        }
    }


    private void addaprofilegallery(String picturePath1) {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(ProfileActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADDUSEPROFILEGALLERY,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                getuserprofilegallery();


                            } else {
                                myDialog.dismiss();
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userUid);
                params.put("image", picturePath1 /*IndifunApplication.getInstance().getCredential().getId()*/);
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    //getinterests
    private void getuserinterest() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(ProfileActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETINTERSTUSER,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                if (userinterstresultItems != null) {
                                    userinterstresultItems.clear();
                                }
                                tagsInterest.setVisibility(View.VISIBLE);
                                nointerst.setVisibility(View.GONE);
                                myDialog.dismiss();
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    com.deificindia.indifun.pojo.userinterstpojo.ResultItem resultItem = new com.deificindia.indifun.pojo.userinterstpojo.ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setUserId(qstnArray.getString("user_id"));
                                    resultItem.setIntrestId(qstnArray.getString("intrest_id"));
                                    resultItem.setName(qstnArray.getString("name"));
                                    userinterstresultItems.add(resultItem);
                                }

                               /* interstAdapter = new InterstAdapter(ProfileActivity.this);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(ProfileActivity.this, 1, LinearLayoutManager.HORIZONTAL, false);

                                GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                                    @Override
                                    public int getSpanSize(int position) {
                                        // for position 0 use only one row. for this position it will take all rows
                                        if (position == 0) {
                                            return 6;
                                        }

                                        return 2;
                                    }
                                };
                                //  innercatlist.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                interestlist.setLayoutManager(gridLayoutManager);
                                interestlist.setAdapter(interstAdapter);

                                interstAdapter.notifyDataSetChanged();*/
                                /*ArrayList<com.deificindia.indifun.pojo.userinterstpojo.ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < userinterstresultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(userinterstresultItems.get(i));

                                    interstAdapter.addItem(channelBeans);
                                }*/
                                updateInterest();

                            } else {
                                myDialog.dismiss();
                                if (userinterstresultItems != null) {
                                    userinterstresultItems.clear();
                                }
                                tagsInterest.setVisibility(View.GONE);
                                nointerst.setVisibility(View.VISIBLE);
                               /* interstAdapter = new InterstAdapter(ProfileActivity.this);
                                interestlist.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                                interestlist.setAdapter(interstAdapter);
                                interstAdapter.notifyDataSetChanged();*/
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            //Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userUid);
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    void updateInterest(){
        List<String> langs = new ArrayList<>();
        for(com.deificindia.indifun.pojo.userinterstpojo.ResultItem l: userinterstresultItems){
            langs.add(l.getName());
        }
        tagsInterest.setTags(langs);
    }


    //user langaugelist
    private void getuserlanguage() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(ProfileActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETLANGUAGELIST,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                if (userlangaueresultItems != null) {
                                    userlangaueresultItems.clear();
                                }

                                tagsLang.setVisibility(View.VISIBLE);
                                nolanguages.setVisibility(View.GONE);

                                myDialog.dismiss();
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    com.deificindia.indifun.pojo.alllangaugespojo.ResultItem resultItem = new com.deificindia.indifun.pojo.alllangaugespojo.ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setStatus(qstnArray.getString("status"));
                                    resultItem.setLanguage(qstnArray.getString("language"));

                                    if(resultItem.getStatus().equalsIgnoreCase("1")) {
                                        userlangaueresultItems.add(resultItem);
                                    }
                                }

                               /* langaugeAdapter = new LangaugeAdapter(ProfileActivity.this);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(ProfileActivity.this, 1, LinearLayoutManager.HORIZONTAL, false);

                                GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                                    @Override
                                    public int getSpanSize(int position) {
                                        // for position 0 use only one row. for this position it will take all rows
                                        if (position == 0) {
                                            return 6;
                                        }

                                        return 2;
                                    }
                                };
                                //  innercatlist.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                languagelist.setLayoutManager(gridLayoutManager);
                                languagelist.setAdapter(langaugeAdapter);

                                langaugeAdapter.notifyDataSetChanged();*/
                                /*ArrayList<com.deificindia.indifun.pojo.alllangaugespojo.ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < userlangaueresultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(userlangaueresultItems.get(i));

                                    langaugeAdapter.addItem(channelBeans);
                                }*/

                                updateLang();


                            } else {
                                myDialog.dismiss();
                                if (userlangaueresultItems != null) {
                                    userlangaueresultItems.clear();
                                }
                                tagsLang.setVisibility(View.GONE);
                                nolanguages.setVisibility(View.VISIBLE);

                               /* langaugeAdapter = new LangaugeAdapter(ProfileActivity.this);
                                languagelist.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                                languagelist.setAdapter(langaugeAdapter);
                                langaugeAdapter.notifyDataSetChanged();*/
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userUid);
                return params;
            }
        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    void updateLang(){
        List<String> langs = new ArrayList<>();
        for(com.deificindia.indifun.pojo.alllangaugespojo.ResultItem l : userlangaueresultItems){
            langs.add(l.getLanguage());
        }
        tagsLang.setTags(langs);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}