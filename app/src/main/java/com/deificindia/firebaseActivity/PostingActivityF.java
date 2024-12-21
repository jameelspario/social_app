package com.deificindia.firebaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.deificindia.chat.Model.User;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.squareup.picasso.Picasso;
//import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.deificindia.indifun.Utility.Logger.loge;

public class PostingActivityF extends AppCompatActivity {
    EditText etpost;
    RelativeLayout submit;
    TextView textView;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    ImageView choose;
    ImageView close,imageView;
    String isdate;
    String miUrlOk = "";
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    StorageReference storageRef;
    private Uri mImageUri;

    //execute java
    private LottieAnimationView animationView;

    String level,etmess,imageuplod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_f);
        isdate=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
       // progressDialog = new ProgressDialog(PostingActivityF.this);

        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setVisibility(View.GONE);
//        progressDialog.setMessage("Uploading ..........");
        imageView=findViewById(R.id.imageView1);
        etpost=findViewById(R.id.enterpostcontent);
        submit=findViewById(R.id.btn_continue);
        close=findViewById(R.id.imgClose);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("UserDetails");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                User user=snapshot.getValue(User.class);
                level=user.getLevel();
                imageuplod=user.getImage();
                loge("hdhd",level+imageuplod);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new PostFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.posti,frag,"Test Fragment");
                transaction.commit();
            }
        });


        storageRef = FirebaseStorage.getInstance().getReference("posts");


        textView=findViewById(R.id.text);
        choose=findViewById(R.id.postimages);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                animationView.setVisibility(View.VISIBLE);
                etmess=etpost.getText().toString().trim();
                if(etmess.isEmpty()){
                    Toast.makeText(PostingActivityF.this, "Write text", Toast.LENGTH_SHORT).show();
                }else if(mImageUri==null){
                    Toast.makeText(PostingActivityF.this, "Please select an image", Toast.LENGTH_SHORT).show();

                }else{
                    uploadImage_10();
                }

            }
        });
    }
    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PostingActivityF.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (options[item].equals("Take Photo"))
                {
                    //CropImage.activity().start(PostingActivityF.this);
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
        ContentResolver cR = getContentResolver();
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
//            Picasso.get().load(mImageUri).into(imageView);
//          //  Toast.makeText(getApplicationContext(), ""+mImageUri, Toast.LENGTH_SHORT).show();
//
//
//        } else {
//            Toast.makeText(getApplicationContext(), "Something gone wrong!", Toast.LENGTH_SHORT).show();
////            startActivity(new Intent(MessageActivity.this, MessageActivity.class));
////            finish();
//        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadImage_10() {

        animationView.playAnimation();

        animationView.setVisibility(View.VISIBLE);
        // progressDialog.show();
        if (mImageUri != null) {
            final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
          loge("imgeurhh", String.valueOf(mImageUri));
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
                        loge("imgeurhh", miUrlOk);
                        Result result = IndifunApplication.getInstance().getCredential();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("User_one");
                        String postid=databaseReference.push().getKey();
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("link", miUrlOk);
                        hashMap.put("description",etmess);
                        hashMap.put("user_id", MySharePref.getUserId());
                        hashMap.put("full_name",MySharePref.getFullname());
                        hashMap.put("gender",result.getGender());
                        hashMap.put("image",imageuplod);
                        hashMap.put("isdate",isdate);
                        hashMap.put("level",level);
                        hashMap.put("Age",result.getAge());
                        hashMap.put("longitude",result.getLongitude());
                        hashMap.put("lattitude",result.getLongitude());
                        hashMap.put("city",result.getCity());
                        hashMap.put("postid",postid);
                        hashMap.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            //    progressDialog.dismiss();
                                animationView.cancelAnimation();
                                animationView.setVisibility(View.GONE);
                                textView.setText("Image Uploaded Successfully");
                                Fragment frag = new PostFragment();
                                FragmentManager manager = getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.add(R.id.posti,frag,"Test Fragment");
                                transaction.commit();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}