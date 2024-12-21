package com.deificindia.firebasefragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deificindia.chat.MessageActivity;
import com.deificindia.indifun.Adapter.ImageListAdapter;
import com.deificindia.indifun.Model.MyImage;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.fragments.PostFragment;
import com.deificindia.indifun.modals.Result;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.nguyenhoanglam.imagepicker.model.Image;
//import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.deificindia.indifun.Utility.Logger.loge;
import static tvi.webrtc.ContextUtils.getApplicationContext;


public class PostFirebaseFragment extends Fragment {

RecyclerView recyclerView2;
EditText etpost;
RelativeLayout submit;
TextView textView;
    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    int index = 0;
    ImageView choose;
    ImageView close,imageView;
    ImageListAdapter imageListAdapter;
    List<MyImage> images;
    String isdate;
    String etmessage;
    String image,miUrlOk;
    private ArrayList<Image> choosenImages = new ArrayList<>();
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    StorageReference storageRef;
    private Uri mImageUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static String filePath = null;
    public String drawerimage;
    public static String filePathTemp = null;
    public static File mediaFile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_addpost1, container, false);
        isdate=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading ..........");
      //  recyclerView2=view.findViewById(R.id.recyclerView2);
        imageView=view.findViewById(R.id.imageView1);
        Glide.with(getContext()).load(mImageUri).into(imageView);
        etpost=view.findViewById(R.id.enterpostcontent);
        submit=view.findViewById(R.id.btn_continue);
        close=view.findViewById(R.id.imgClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostFragment postFirebaseFragment=new PostFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.constraint23, postFirebaseFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                etmessage=textView.getText().toString().trim();
                if(etmessage.isEmpty()){
                    Toast.makeText(getContext(), "Write some words", Toast.LENGTH_SHORT).show();

                }else{
                    uploadImage_10();
                  //  upload(view);

                }
                

            }
        });
        textView=view.findViewById(R.id.text);
        choose=view.findViewById(R.id.postimages);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                choose();
                selectImage();
            }
        });
        return view;
    }
//    public void choose() {
//        //we will pick images
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        startActivityForResult(intent, PICK_IMG);
//
//    }


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
//                    CropImage.activity()
//                            .start(getActivity());
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("LongLogTag")
    private String getFileExtension(Uri uri) {
        ContentResolver cR = requireContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            mImageUri = result.getUri();
////             imageView.setImageDrawable(mImageUri);
//            Toast.makeText(getContext(), ""+mImageUri, Toast.LENGTH_SHORT).show();
//
//          //  sendMessage(fuser.getUid(), userid,miUrlOk);
//
//        } else {
//            Toast.makeText(getContext(), "Something gone wrong!", Toast.LENGTH_SHORT).show();
////            startActivity(new Intent(MessageActivity.this, MessageActivity.class));
////            finish();
//        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadImage_10() {

//        final LoadingDialog loadingDialog=new LoadingDialog(PostStatusActivity.this);
//        loadingDialog.startLoadingDialog();
        progressDialog.show();

        if (mImageUri != null) {
            final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            uploadTask = fileReference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        assert downloadUri != null;
                        miUrlOk = downloadUri.toString();

                        Result result = IndifunApplication.getInstance().getCredential();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("User_one");
                        String postid=databaseReference.push().getKey();
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("link", miUrlOk);
                        hashMap.put("description",etpost.getText().toString());
                        hashMap.put("user_id", MySharePref.getUserId());
                        hashMap.put("full_name",MySharePref.getFullname());
                        hashMap.put("gender",result.getGender());
                        hashMap.put("image",result.getProfilePicture());
                        hashMap.put("isdate",isdate);
                        hashMap.put("Age",result.getAge());
                        hashMap.put("longitude",result.getLongitude());
                        hashMap.put("lattitude",result.getLongitude());
                        hashMap.put("city",result.getCity());
                        hashMap.put("postid",postid);
                        hashMap.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseReference.child(postid).setValue(hashMap);
                        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                progressDialog.dismiss();
                                textView.setText("Image Uploaded Successfully");
                                //    send.setVisibility(View.GONE);
                               // ImageList.clear();

                            }

                        });

                    } else {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
//        } else {
//            Toast.makeText(PostStatusActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
//        }
    }
    @SuppressLint("SetTextI18n")
    public void upload(View view) {

        textView.setText("Please Wait ... If Uploading takes Too much time please press the button again ");
        progressDialog.show();
        final StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("ImageFolder");
//        for (uploads=0; uploads < ImageList.size(); uploads++) {
            Uri Image  = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child("image/"+Image.getLastPathSegment());

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);
                            SendLink(url);
                        }
                    });

                }
            });





    }

    private void SendLink(String url) {
        Result result = IndifunApplication.getInstance().getCredential();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User_one");
        String postid=databaseReference.push().getKey();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("link", url);
        hashMap.put("description",etpost.getText().toString());
        hashMap.put("user_id", MySharePref.getUserId());
        hashMap.put("full_name",MySharePref.getFullname());
        hashMap.put("gender",result.getGender());
        hashMap.put("image",result.getProfilePicture());
        hashMap.put("isdate",isdate);
        hashMap.put("Age",result.getAge());
hashMap.put("longitude",result.getLongitude());
        hashMap.put("lattitude",result.getLongitude());
        hashMap.put("city",result.getCity());
        hashMap.put("postid",postid);
        hashMap.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.child(postid).setValue(hashMap);
        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressDialog.dismiss();
                textView.setText("Image Uploaded Successfully");
            //    send.setVisibility(View.GONE);
                ImageList.clear();

            }

        });

    }
}

