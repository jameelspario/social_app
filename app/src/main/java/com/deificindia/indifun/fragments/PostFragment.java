package com.deificindia.indifun.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.deificindia.firebaseActivity.PostingActivityF;
import com.deificindia.firebasefragment.FirePostPublicFrag;
import com.deificindia.firebasefragment.PostFirebaseFragment;
import com.deificindia.indifun.Activities.ZoomOutPageTransformer;
import com.deificindia.indifun.Adapter.ImageListAdapter;
import com.deificindia.indifun.Model.MyImage;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.LoadingDialog;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.anim.CircleReavealAnim;
import com.deificindia.indifun.anim.TranslateView;
import com.deificindia.indifun.dialogs.PostDialog;
import com.deificindia.indifun.dialogs.FilterBottomSheetDialog;
import com.deificindia.indifun.Utility.ImagePickerActivity;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.dialogs.PostDialog;
import com.deificindia.indifun.services.UploadPostService;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.ui.spatab.InditabView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.kroegerama.imgpicker.BottomSheetImagePicker;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;

public class PostFragment extends Fragment implements BottomSheetImagePicker.OnImagesSelectedListener {
    private static final int REQUEST_IMAGE = 100;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    StorageReference storageRef;
    private Uri mImageUri;
    ViewPager2 viewPager;
   // TabLayout tabLayout;
    InditabView indiTabView;

    private ImageView  call_board;
    private RelativeLayout btn_continue;
    private EditText enterpostcontent;
    private ImageView postimages, opendialog;
    ConstraintLayout constraintLayout;
    private AlertDialog alertDialog;
    private Dialog postDialog;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;

    private ArrayList<Image> choosenImages = new ArrayList<>();
    String date_;
    RecyclerView recyclerView;
    NestedScrollView nestedScrollView;
    ImageListAdapter imageListAdapter;
    List<MyImage> images;

    LoadingAnimView loadingAnimView;
    private final int PICK_IMAGE_REQUEST = 71;
    int pageNumber = 0;
    FirebaseStorage storage;
    StorageReference storageReference;
    public PostFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_post, container, false);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //tabLayout = v.findViewById(R.id.FragmentTab);
        indiTabView = v.findViewById(R.id.inditab);
        viewPager = v.findViewById(R.id.viewpager);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        call_board = v.findViewById(R.id.call_board);
        opendialog = v.findViewById(R.id.opendialog);
//        storageRef = FirebaseStorage.getInstance().getReference("posts");
        call_board.setVisibility(View.VISIBLE);
//        date_=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        setupViewPager();

        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.viewpager, new PostFollowFragment());
        transaction.commit();*/

        clicklisteners(v);

        return v;
    }

    void clicklisteners(View v1){
        opendialog.setOnClickListener(v->{

            if(pageNumber==2){
                FilterBottomSheetDialog filterDialog = new FilterBottomSheetDialog();
                filterDialog.show(getChildFragmentManager(), "FilterDialog");
            }else {
               // showDiag();
               // showpost();
                 PostDialog.opeDialog(getChildFragmentManager(), (int) v.getX(), (int)v.getY());
//                PostFirebaseFragment postFirebaseFragment=new PostFirebaseFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.constraint23, postFirebaseFragment ); // give your fragment container id in first parameter
//                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                transaction.commit();




            }



        });
    }



private void showpost(){
    final View dialogView = View.inflate(getContext(), R.layout.layout_addpost,null);
    postDialog = new Dialog(getContext(), R.style.FullScreenDialog);
    postDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //STYLE_NO_FRAME
        /*TranslateView.setActivityDiemention(getActivity(),
                TranslateView.getScreenWidth(getActivity()) - 20,
                TranslateView.getScreenHeight(getActivity()) - 20);*/

    postDialog.setContentView(dialogView);
    dialogView.findViewById(R.id.imgClose).setOnClickListener(v->{

        circleAnim(dialogView, false, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                postDialog.dismiss();
                dialogView.setVisibility(View.INVISIBLE);
            }
        });
    });

    postDialog.setOnShowListener(dialogInterface -> {
        //CircleReavealAnim.circleRevealDialog(dialogView, cx, cy, true, null)
        circleAnim(dialogView, true, null);
    });

    postDialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
        if (i == KeyEvent.KEYCODE_BACK){
            circleAnim(dialogView, false, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    postDialog.dismiss();
                    dialogView.setVisibility(View.INVISIBLE);
                }
            });
            return true;
        }

        return false;
    });
    postDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    postDialog.show();
}
    private void showDiag() {
        if(recyclerView!=null) recyclerView.setAdapter(null);
        choosenImages = new ArrayList<>();

        final View dialogView = View.inflate(getContext(), R.layout.layout_addpost,null);

        postDialog = new Dialog(getContext(), R.style.FullScreenDialog);
        postDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //STYLE_NO_FRAME
        /*TranslateView.setActivityDiemention(getActivity(),
                TranslateView.getScreenWidth(getActivity()) - 20,
                TranslateView.getScreenHeight(getActivity()) - 20);*/

        postDialog.setContentView(dialogView);

        dialogView.findViewById(R.id.imgClose).setOnClickListener(v->{

            circleAnim(dialogView, false, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    postDialog.dismiss();
                    dialogView.setVisibility(View.INVISIBLE);
                }
            });
        });

        postDialog.setOnShowListener(dialogInterface -> {
            //CircleReavealAnim.circleRevealDialog(dialogView, cx, cy, true, null)
            circleAnim(dialogView, true, null);
        });

        postDialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK){
                circleAnim(dialogView, false, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        postDialog.dismiss();
                        dialogView.setVisibility(View.INVISIBLE);
                    }
                });
                return true;
            }

            return false;
        });


        //////////////////////////////
        enterpostcontent = dialogView.findViewById(R.id.enterpostcontent);
        postimages = dialogView.findViewById(R.id.postimages);
        btn_continue = dialogView.findViewById(R.id.btn_continue);
        recyclerView = dialogView.findViewById(R.id.recyclerView2);
        nestedScrollView = dialogView.findViewById(R.id.nestedScrollView);
        constraintLayout = dialogView.findViewById(R.id.relativeLayout2);

        changeConstraintLay();

        imageListAdapter = new ImageListAdapter(choosenImages, getContext(), 1, pos -> {
            choosenImages.remove(pos);
            imageListAdapter.notifyDataSetChanged();

            changeConstraintLay();

        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(imageListAdapter);

        postimages.setOnClickListener(v1 -> openMediaDialog());
        //recyclerView.setOnClickListener(v2 ->  openMediaDialog());

        btn_continue.setOnClickListener(v1 -> {
            if (enterpostcontent.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.enterpostcontent), Toast.LENGTH_SHORT).show();
               /* } else  if (postimages.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.add_a_photo).getConstantState()) {
                    Toast.makeText(getActivity(), getString(R.string.pleaseuploadpostimage), Toast.LENGTH_SHORT).show();*/
            }else if(choosenImages==null || choosenImages.isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.pleaseuploadpostimage), Toast.LENGTH_SHORT).show();
            }else {
                myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
                //addPost3(choosenImages);
                UploadPostService.startAction(getContext(), enterpostcontent.getText().toString().trim(), choosenImages, m->{
                    loge("PostFrag service", ""+m);
                    //uploadImage_11();
                    myDialog.dismiss();

                    circleAnim(dialogView, false, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            alertDialog.dismiss();
                        }
                    });

                });
            }

        });

        postDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        postDialog.show();

    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void uploadImage_11() {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
//
//        String postid = reference.push().getKey();
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("postid", postid);
//        hashMap.put("postimage", choosenImages);
//        hashMap.put("dated", date_);
//        hashMap.put("userprofile", MySharePref.IMAGE);
//        hashMap.put("description", enterpostcontent.getText().toString());
//        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
//        assert postid != null;
//        reference.child(postid).setValue(hashMap);
//
////        loadingDialog.dismissDialog();
////
////        startActivity(new Intent(PostStatusActivity.this, DashBoard.class));
////        finish();
//
//    }
//






    void circleAnim(View dialogView, boolean b, AnimatorListenerAdapter listener){
        CircleReavealAnim.circleRevealDialog(dialogView, opendialog, b, listener);
    }

    void changeConstraintLay(){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) postimages.getLayoutParams();
        loge("PAOST fRAg", "0");
        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);

        if(choosenImages.size()>0){
            params.width = 24;
            params.height = 24;
            //set.connect(postimages.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
            //set.connect(postimages.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            set.clear(postimages.getId(), ConstraintSet.RIGHT);
            set.clear(postimages.getId(), ConstraintSet.BOTTOM);
            set.clear(nestedScrollView.getId(), ConstraintSet.TOP);
            set.connect(nestedScrollView.getId(), ConstraintSet.TOP, postimages.getId(), ConstraintSet.BOTTOM, 0);
        }else {

            loge("PAOST fRAg", "1");
            params.width = 150;
            params.height = 150;
            //set.connect(postimages.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
            //set.connect(postimages.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            set.connect(postimages.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 0);
            set.connect(postimages.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, 0);
            set.clear(nestedScrollView.getId(), ConstraintSet.TOP);
            set.connect(nestedScrollView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 0);
        }

        postimages.setLayoutParams(params);
        set.applyTo(constraintLayout);
    }


    private void openMediaDialog() {
        //loge("PostFragment", "openMediaDialog");
        ImagePicker.with(this)
                .setFolderMode(true)
                .setFolderTitle("Album")
                .setRootDirectoryName(Environment.DIRECTORY_DCIM)
                .setDirectoryName("Image Picker")
                .setMultipleMode(true)
                .setShowNumberIndicator(true)
                .setMaxSize(9)
                .setLimitMessage("You can select up to 9 images")
                .setSelectedImages(choosenImages)
                .setRequestCode(500)
                .start();

    }


    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent2();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
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
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent2() {

       /* BSImagePicker multiSelectionPicker = new BSImagePicker.Builder("com.deificindia.indifun")
                .isMultiSelect() //Set this if you want to use multi selection mode.
                .setMinimumMultiSelectCount(1) //Default: 1.
                .setMaximumMultiSelectCount(9) //Default: Integer.MAX_VALUE (i.e. User can select as many images as he/she wants)
                .setMultiSelectBarBgColor(android.R.color.white) //Default: #FFFFFF. You can also set it to a translucent color.
                .setMultiSelectTextColor(R.color.primary_text) //Default: #212121(Dark grey). This is the message in the multi-select bottom bar.
                .setMultiSelectDoneTextColor(R.color.colorAccent) //Default: #388e3c(Green). This is the color of the "Done" TextView.
                .setOverSelectTextColor(R.color.error_text) //Default: #b71c1c. This is the color of the message shown when user tries to select more than maximum select count.
                .disableOverSelectionMessage() //You can also decide not to show this over select message.
                .build();

        multiSelectionPicker.show(getChildFragmentManager(), "picker");*/

        /*new PickerMulti().setThings(getActivity(), getChildFragmentManager(), GALLERY_TYPE_IMAGE,
                GALLERY_MODE_MULTI).onClickListener(new InterfaceGallery.InterfaceSingleModeItemClickListener() {
            @Override
            public void getResponse(ModelChild modelChild) {
                loge("Explorer", ""+toGson(modelChild));
            }
        }).setColumnCount(4).showPicker();*/

    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            /*if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    picturePath.add(encodeTobase64(bitmap));
                    //addAvatar.setVisibility(View.VISIBLE);
                    postimages.setImageBitmap(bitmap);
                    *//*imgUser.setImageBitmap ( bitmap );
                    strImg1=encodeTobase64(bitmap);*//*
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }

        if (ImagePicker.shouldHandleResult(requestCode, resultCode, data, 500)) {
            choosenImages  = ImagePicker.getImages(data);
            // Do stuff with image's path or id. For example:
            /*for (Image image :choosenImages) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    *//*Glide.with(context)
                            .load(image.uri)
                            .into(imageView)*//*
                } else {
                    *//*Glide.with(context)
                            .load(image.path)
                            .into(imageView)*//*
                }
            }*/
            if(imageListAdapter!=null &&    choosenImages.size()>0 ){
                imageListAdapter.update(choosenImages);
                //postimages.setVisibility(View.GONE);
                changeConstraintLay();

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

    private void setupViewPager() {
        Fragmentdapter adapter = new Fragmentdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);


        indiTabView.setuptab(getContext(), "Follow", "Public", "Recomended");
        indiTabView.changeSelection(0);
        indiTabView.setViewPager(viewPager);
       // indiTabView.setListener(null);
        indiTabView.setListener(new InditabView.TabSelectionChange() {
            @Override
            public void onSelected(int index) { }

            @Override
            public void onPagerScrolled(int pos) {
                if(pos==2){
                    opendialog.setImageResource(R.drawable.ic_filter);
                }else {
                    opendialog.setImageResource(R.drawable.ic_add_file__1_);
                }
                pageNumber = pos;
            }
        });

    }

    static class Fragmentdapter extends FragmentStateAdapter {

        public Fragmentdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return getFragment(position);
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        Fragment getFragment(int pos){
            if(pos==0) { return new PostFollowFragment(); }
            else if(pos==1) { return  new FirePostPublicFrag(); }
            else { return new PostRecommendedFragment(); }

        }
    }


    @Override
    public void onImagesSelected(@NotNull List<? extends Uri> list, @org.jetbrains.annotations.Nullable String s) {
        loge("PostFrag", ""+list.size());
    }

   /* @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {
            loge("ExploreFrag","loadImage", imageUri.toString());
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        loge("ExploreFrag","onMultiImageSelected", uriList.toString());
        for(Uri u:uriList){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), u);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        picturePath.add(encodeTobase64(bitmap));
                    }
                }).start();

        }
        if(picturePath!=null && !picturePath.isEmpty()){
            postimages.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        imageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(boolean isMultiSelecting, String tag) {
        loge("ExploreFrag", "onCancelled", ""+isMultiSelecting);
    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        loge("ExploreFrag","onSingleImageSelected", uri.toString());
    }*/

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

public void uploadtask(){}

}