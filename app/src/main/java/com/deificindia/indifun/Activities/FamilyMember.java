package com.deificindia.indifun.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.ImagePickerActivity;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.fragments.ContentFragment;
import com.deificindia.indifun.fragments.FamilyDaily;
import com.deificindia.indifun.fragments.FamilyMonthly;
import com.deificindia.indifun.fragments.FamilyTotal;
import com.deificindia.indifun.fragments.LeaderBoardFragment;
import com.deificindia.indifun.fragments.TopFans30Days;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.COINSENDER;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.FOLLOWERS;

public class FamilyMember extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
   PagerAdapter adapter;
   Button create_family;
   String picturePath,picturepath1;
   Uri mImageUri,mImageUri1;
   Bitmap bitmap,bitmap1;
    CircleImageView imageIcon;
    ImageView backgroundimage;
    EditText create_familyname,create_level,create_aboutus;
    String createfamilyname,createlevel,createabout;
    ImageView close;
    private static final int REQUEST_IMAGE = 100;
    private static final int REQUEST_IMAGE1 = 100;
   int level;
   int cam;
    private  String valu_of_card,value_id,value_month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        create_family = (Button) findViewById(R.id.create_family);
        create_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlevel();
            }
        });

        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*if (tab.getPosition() == 0) {
                    replaceFragment(new LeaderBoardFragment(0, isglobal_local));
                } else*/
//                if (tab.getPosition() == 0) {
//                    replaceFragment(new LeaderBoardFragment(COINSENDER, isglobal_local));
//                } else {
//                    replaceFragment(new LeaderBoardFragment(FOLLOWERS, isglobal_local));
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }


    private void setupViewPager (ViewPager viewPager){
        adapter = new PagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new FamilyTotal(), "Total",R.color.black);
        adapter.addFragment(new FamilyMonthly(), "Monthly", R.color.black);
        adapter.addFragment(new FamilyDaily(), "Daily", R.color.black);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));


        }


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
//                    Toast.makeText(TopFanListLayout.this,
//                            "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }
    private void getlevel() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(FamilyMember.this, "Wait...");

        RetroCalls.instance()
                .stringParam(IndifunApplication.getInstance().getCredential().user_token)
                .setOnFail((a, b, c) -> {
                    myDialog.dismiss();
                }).know_golden_coin((type_result, obj2) -> {

            myDialog.dismiss();

            if (obj2 != null && obj2.status == 1 && obj2.getResult() != null) {
                level = obj2.getResult().level == null
                        || obj2.getResult().level.isEmpty() ? 0 : Integer.parseInt(obj2.getResult().level);

                if (level > 7) {
                    dialogopen();
                   // startLive();
                } else {
                    showAlertDialog();
                }
            }
        });
    }
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FamilyMember.this,R.style.BottomSheetDialogTheme);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom, null);
        //alertDialog=new AlertDialog.Builder(R.style.TransparentDialog,null);
        alertDialog.setView(customLayout);

        ImageView icclose = customLayout.findViewById(R.id.closeall);
        ImageView image12 = customLayout.findViewById(R.id.image);
        image12.setVisibility(View.VISIBLE);
        Button upgrade = customLayout.findViewById(R.id.upgrade);
        upgrade.setText("Ok");
//                int level = Integer.parseInt(obj2.getResult().level);
        TextView textView = customLayout.findViewById(R.id.textcount);
        textView.setText("" + level);
        TextView textView1 = customLayout.findViewById(R.id.userlevel);
        TextView textView2 = customLayout.findViewById(R.id.text);
        textView2.setText("User can only create a family when they exceed the level 9");
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
                finish();
            }
        });

        alert.setCanceledOnTouchOutside(false);

        alert.show();
    }

    private void suscription() {
        try {

            Result credencial = IndifunApplication.getInstance().getCredential();
            String token = credencial.user_token;
            loge("tokenfind",token);
            RetroCalls.instance()
                    .stringParam(token,value_id,valu_of_card,value_month)
                    .subscription((type_result, obj2) -> {
                        System.out.println("messageprint"+obj2.getStatus());
                    });
        } catch (Exception e){

        }

    }


    private void openMediaDialog() {
        Dexter.withActivity(FamilyMember.this)
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
    private void showSettingsDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(FamilyMember.this);
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
        Uri uri = Uri.fromParts("package", FamilyMember.this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(FamilyMember.this, new ImagePickerActivity.PickerOptionListener() {
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
        Intent intent = new Intent(FamilyMember.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        if(cam==0){
            startActivityForResult(intent, REQUEST_IMAGE);
        }if(cam==1){
            startActivityForResult(intent, REQUEST_IMAGE1);
        }


    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(FamilyMember.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
  if(cam==0){
      startActivityForResult(intent, REQUEST_IMAGE);
  }
if(cam==1){
    startActivityForResult(intent, REQUEST_IMAGE1);
}

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                mImageUri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    bitmap = MediaStore.Images.Media.getBitmap(FamilyMember.this.getContentResolver(), mImageUri);
                    picturePath = Constants.encodeTobase64(bitmap);
                    loge("picture",picturePath);
                    imageIcon.setImageBitmap(bitmap);
                  //  updateprofile(picturePath);
                    // signUpMethod1(picturePath,number, name, email, dob, gender_str, age);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(requestCode==REQUEST_IMAGE1){
            if (resultCode == Activity.RESULT_OK) {
                mImageUri1 = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    bitmap1 = MediaStore.Images.Media.getBitmap(FamilyMember.this.getContentResolver(), mImageUri1);
                    picturepath1 = Constants.encodeTobase64(bitmap1);
                    loge("picture",picturepath1);
                 backgroundimage.setImageBitmap(bitmap1);

                    //  updateprofile(picturePath);
                    // signUpMethod1(picturePath,number, name, email, dob, gender_str, age);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void dialogopen(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FamilyMember.this,R.style.myFullscreenAlertDialogStyle);
        final View customLayout = getLayoutInflater().inflate(R.layout.family_dialog_name, null);
        //alertDialog=new AlertDialog.Builder(R.style.TransparentDialog,null);
        alertDialog.setView(customLayout);
       imageIcon=customLayout.findViewById(R.id.clickicon);
       create_family=customLayout.findViewById(R.id.create_family);
        create_familyname=customLayout.findViewById(R.id.familytext);
       create_level=customLayout.findViewById(R.id.familyabout);
        close=customLayout.findViewById(R.id.close);
        imageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cam=0;
                openMediaDialog();
            }
        });
        backgroundimage=customLayout.findViewById(R.id.frameimage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        backgroundimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cam=1;
                openMediaDialog();
            }
        });
       create_aboutus=customLayout.findViewById(R.id.familyabout1);
        create_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(FamilyMember.this, "Hit Up", Toast.LENGTH_SHORT).show();
                Vliadte();
            }
        });




        AlertDialog alert = alertDialog.create();


        alert.setCanceledOnTouchOutside(false);

        alert.show();
    }

public void Vliadte(){
    createfamilyname=create_familyname.getText().toString().trim();
    createlevel=create_level.getText().toString().trim();
    createabout=create_aboutus.getText().toString().trim();
    if (picturePath==null) {
        Toast.makeText(FamilyMember.this, getString(R.string.pleaseuploadimage), Toast.LENGTH_SHORT).show();
        return;
    }
        if (picturepath1==null) {
            Toast.makeText(FamilyMember.this, getString(R.string.pleaseuploadimage), Toast.LENGTH_SHORT).show();
            return;
        }
    if(createfamilyname.isEmpty()){
        Toast.makeText(this, "Create Family name", Toast.LENGTH_SHORT).show();

    }else if(createlevel.isEmpty()){
        Toast.makeText(this, "Create level", Toast.LENGTH_SHORT).show();

    }else if(createabout.isEmpty()){
        Toast.makeText(this, "Create About us", Toast.LENGTH_SHORT).show();


    }else{
        create_family();
    }

}

    private void create_family() {
         ProgressDialog mProgressDialog;

        mProgressDialog = new ProgressDialog(FamilyMember.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.onStart();
         try {

            Result credencial = IndifunApplication.getInstance().getCredential();
            String token = credencial.user_token;
            loge("tokenfind",token);
            RetroCalls.instance().setOnFail((a,b,c)->{
                Toast.makeText(this, ""+c, Toast.LENGTH_SHORT).show();
               // myDialog.dismiss();
                mProgressDialog.dismiss();
            })
                    .stringParam(token,createfamilyname,createlevel,createabout,picturePath,picturepath1)
                    .create_family((type_result, obj2) -> {
                        System.out.println("messageprint"+obj2.getStatus());
                        Toast.makeText(this, ""+obj2.getStatus(), Toast.LENGTH_SHORT).show();
                    });

             mProgressDialog.dismiss();
           // finish();
        } catch (Exception e){
          //  myDialog.dismiss();
             mProgressDialog.dismiss();
        }

    }

    public class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> myFragments = new ArrayList<>();
        private final List<String> myFragmentTitles = new ArrayList<>();
        private Context context;



        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        public void addFragment(Fragment fragment, String title, int black) {
            myFragments.add(fragment);
            myFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return myFragments.get(position);
        }

        @Override
        public int getCount() {
            return myFragments.size();
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab_item.xml` with a TextView and ImageView
            View v = LayoutInflater.from(context).inflate(R.layout.custom_tab_item, null);
            TextView tv = (TextView) v.findViewById(R.id.tab_item_txt);
            tv.setText(myFragmentTitles.get(position));
            return v;
        }

    }

}