package com.deificindia.indifun.dialogs;

import static com.deificindia.indifun.Utility.Logger.loge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.indifun.Adapter.BlockListAdapter;
import com.deificindia.indifun.Adapter.ImageListAdapter;
import com.deificindia.indifun.Model.MyImage;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.GridSpacingItemDecoration;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.anim.CircleReavealAnim;
import com.deificindia.indifun.services.UploadPostService;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.anim.CircleReavealAnim;
import com.deificindia.indifun.fragments.PostFragment;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.services.UploadPostService;
import com.deificindia.indifun.ui.NestedRecycleView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PostDialog extends DialogFragment {

    public static final int SPACING = 5;
    int cx, cy;

    private ImageView imgClose;
    private RelativeLayout btn_continue;
    private EditText enterpostcontent;
    private ImageView postimages, opendialog;
    ConstraintLayout constraintLayout;
    private AlertDialog alertDialog;
    private Dialog postDialog;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    PostImageListAdapter imageListAdapter;

    private ArrayList<Image> choosenImages = new ArrayList<>();

    ConstraintSet rootSet, postSet;
    GridSpacingItemDecoration gridSpacingItemDecoration;

    public PostDialog() {}

    public PostDialog(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_addpost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setOnShowListener(dialogInterface->{
            CircleReavealAnim.circleRevealDialog(view, cx, cy, true, null);
        });

        view.findViewById(R.id.imgClose).setOnClickListener(v->{
            CircleReavealAnim.circleRevealDialog(view, cx, cy, false, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dismissAllowingStateLoss();
                }
            });

        });

        getDialog().setOnKeyListener((dialogInterface, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK){
                CircleReavealAnim.circleRevealDialog(view, cx, cy, false, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        dismissAllowingStateLoss();
                    }
                });
                return true;
            }

            return false;
        });

     /*   recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new BlockListAdapter();

        recyclerView.setAdapter(adapter);

        loadingAnimView = view.findViewById(R.id.lottieanim);*/

        imgClose = view.findViewById(R.id.imgClose);
        enterpostcontent = view.findViewById(R.id.enterpostcontent);

        constraintLayout = view.findViewById(R.id.relativeLayout2);

        rootSet = new ConstraintSet();
        rootSet.clone(constraintLayout);
        postSet = new ConstraintSet();
        postSet.clone(getContext(), R.layout.layout_item_addpost_imageview);

        postimages = view.findViewById(R.id.postimages);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        recyclerView = view.findViewById(R.id.recyclerView2);


        btn_continue = view.findViewById(R.id.btn_continue);

        imgClose.setOnClickListener(v-> dismissAllowingStateLoss());
        btn_continue.setOnClickListener(v->{
            //post data -----
            if (enterpostcontent.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.enterpostcontent), Toast.LENGTH_SHORT).show();
            }else if(choosenImages==null || choosenImages.isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.pleaseuploadpostimage), Toast.LENGTH_SHORT).show();
            }else {

                myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
                UploadPostService.startAction(getContext(), enterpostcontent.getText().toString().trim(), choosenImages, m->{
                    loge("PostFrag service", ""+new Gson().toJson(m));

                    new Handler(Looper.getMainLooper()).post(()->{
                        if(m.size()>0) {
                            pushToFirebase(enterpostcontent.getText().toString().trim(), m);
                            //uploadImage_10(enterpostcontent.getText().toString().trim(), m);
                            myDialog.dismiss();
                            Toast.makeText(getContext(), "Post submitted.", Toast.LENGTH_SHORT).show();
                            dismissAllowingStateLoss();
                        } else {
                            myDialog.dismiss();
                            Toast.makeText(getContext(), "Failed to update post", Toast.LENGTH_SHORT).show();
                            dismissAllowingStateLoss();
                        }
                    });


                });
            }
        });

        postimages.setOnClickListener(v->{
            openMediaDialog();
        });

        gridSpacingItemDecoration = getDecore(3);

        initREcycler();

    }


    void initREcycler(){
        imageListAdapter = new PostImageListAdapter(choosenImages, pos -> {
            if(choosenImages.size()==0){
                toRoot();
            }
        });

        recyclerView.removeItemDecoration(gridSpacingItemDecoration);

        int span = 3;
        if(choosenImages.size()==1){
            span = 1;
            gridSpacingItemDecoration = getDecore(1);
        }else if(choosenImages.size()==2){
            span = 2;
            gridSpacingItemDecoration =getDecore(2);
        }else {
            gridSpacingItemDecoration =getDecore(3);
        }

        recyclerView.addItemDecoration(gridSpacingItemDecoration);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), span));
        recyclerView.setAdapter(imageListAdapter);

    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {

            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();

        } catch (IllegalStateException e) {
            Log.d("UserBlockListDialog", "Exception", e);
        }
    }

    public static void opeDialog(FragmentManager manager, int x, int y){
        PostDialog dialog = new PostDialog(x, y);

        manager.beginTransaction()
                .add(dialog, "PostDialog")
                .commitAllowingStateLoss();
    }


    void toPostMode(){
        TransitionManager.beginDelayedTransition(constraintLayout);
        postSet.applyTo(constraintLayout);
    }

    void toRoot(){
        TransitionManager.beginDelayedTransition(constraintLayout);
        rootSet.applyTo(constraintLayout);
    }

    GridSpacingItemDecoration getDecore(int span){
        return new GridSpacingItemDecoration(span, SPACING, false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    private void openMediaDialog() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ImagePicker.shouldHandleResult(requestCode, resultCode, data, 500)) {
            choosenImages  = ImagePicker.getImages(data);
            loge("PostDialog", "choo "+ choosenImages.size());
            if(imageListAdapter!=null && choosenImages.size()>0 ){
                //imageListAdapter.notifyDataSetChanged();
                initREcycler();
                toPostMode();

            }


        }
    }

    void pushToFirebase(String message, ArrayList<String> param2){
        Result result = IndifunApplication.getInstance().getCredential();
        String fuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User_one");
        String postid=databaseReference.push().getKey();

        firebaseUserModel model = new firebaseUserModel();
        model.fuid = fuid;
        model.full_name = result.getFullName();
        model.gender = result.getGender();
        model.age = result.getAge();
        model.link = param2;
        model.user_id = result.getId();
        model.id = fuid;
        model.image = result.getProfilePicture();
        model.postid = postid;
        model.level = 1;
        model.description = message;
        model.isdate = System.currentTimeMillis();
        model.whatsup = message;
        model.city = result.getCity();
        model.longitude = result.getLongitude();
        model.lattitude = result.getLatitude();

       /* HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("link", param2);
        hashMap.put("description", message);
        hashMap.put("user_id", MySharePref.getUserId());
        hashMap.put("full_name",MySharePref.getFullname());
        hashMap.put("gender",result.getGender());
        hashMap.put("image",result.getProfilePicture());
        hashMap.put("isdate", System.currentTimeMillis());
        hashMap.put("level", 1);
        hashMap.put("Age",result.getAge());
        hashMap.put("longitude",result.getLongitude());
        hashMap.put("lattitude",result.getLongitude());
        hashMap.put("city",result.getCity());
        hashMap.put("postid",postid);
        hashMap.put("id", fuid);
        hashMap.put("fuid", fuid);
        hashMap.put("publisher", fuid);*/

        databaseReference.push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }
}
