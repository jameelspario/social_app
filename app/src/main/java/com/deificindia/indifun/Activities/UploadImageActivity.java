package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;

//import com.deificindia.indifun.Adapter.PhotoUploadAdapter;
//import com.deificindia.indifun.Model.UploadModel;
import com.deificindia.indifun.R;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;

public class UploadImageActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Uri mImageCaptureUri;
    private static final int GALLERY_REQUEST_CODE = 1001;
    private static final int CAMERA_REQUEST_CODE = 1002;
    private RecyclerView recyclerView;

//    private PhotoUploadAdapter mPhotosUploadAdapter;
//    private ArrayList<UploadModel> mPhotosUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        recyclerView = findViewById(R.id.recyclerView);
        tabLayout = findViewById(R.id.tabLayout);

//        mPhotosUploads = new ArrayList<>();
//        mPhotosUploadAdapter = new PhotoUploadAdapter(this, this, this, mPhotosUploads);

    }

    public void takeFromCamera(){

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //File f = new File(android.os.Environment.getExternalStorageDirectory(), QuickHelp.getRandomString(10) +".jpg");
        //mImageCaptureUri = Uri.fromFile(f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

}