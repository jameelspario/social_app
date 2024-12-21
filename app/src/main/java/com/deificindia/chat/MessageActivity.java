package com.deificindia.chat;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.chat.Adapter.MessageAdapter;

import com.deificindia.chat.Fragments.APIService;
import com.deificindia.chat.Model.Chat;
import com.deificindia.chat.Model.User;
import com.deificindia.chat.Notifications.Client;
import com.deificindia.chat.Notifications.Data;
import com.deificindia.chat.Notifications.MyResponse;
import com.deificindia.chat.Notifications.Sender;
import com.deificindia.chat.Notifications.Token;
import com.deificindia.indifun.Activities.ProfileActivity;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;



import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;


public class MessageActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MessageActivity";
    CircleImageView profile_image;
    TextView username;
    ImageView back;
    FirebaseUser fuser;
    DatabaseReference reference;

    ImageView btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    Intent intent;

    ValueEventListener seenListener;

    String userid;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    APIService apiService;
    String texttime;
    boolean notify = false;
    JsonObject jsonObject;
    String MyFinalValue;
    private String online;

    ImageView camopen;
    private Uri mImageUri;
    String miUrlOk = "default";
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    StorageReference storageRef;
    Button record;
    private MediaRecorder mRecorder;

    private final String mLocalFilePath = null;
    private LinearLayout attachmentLayout;
    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        //for API-8 (doing only in main activity)
//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setNavigationIcon(back_to_home_button);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().hide();

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(getApplicationContext(), ChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
            }
        });
        storageRef = FirebaseStorage.getInstance().getReference("chat");


        attachmentLayout = (LinearLayout) findViewById(R.id.menu_attachments);

        texttime=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));


        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        ImageButton btnDocument = (ImageButton) findViewById(R.id.menu_attachment_camera);
//        ImageButton btnCamera = (ImageButton) findViewById(R.id.menu_attachment_camera);
//        ImageButton btnGallery = (ImageButton) findViewById(R.id.menu_attachment_gallery);
//        ImageButton btnAudio = (ImageButton) findViewById(R.id.menu_attachment_audio);
//        ImageButton btnLocation = (ImageButton) findViewById(R.id.menu_attachment_location);
//        ImageButton btnContact = (ImageButton) findViewById(R.id.menu_attachment_contact);



        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        camopen=findViewById(R.id.opencam);
        camopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectImage();
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(
                        MessageActivity.this,R.style.BottomSheetDialogTheme
                );
                View bottomSheetView=LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.menu_attacment,(LinearLayout)findViewById(R.id.menu_attachments)

                );

                bottomSheetView.findViewById(R.id.menu_attachment_camera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        CropImage.activity()
//                                .start(MessageActivity.this);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }

        });
        intent = getIntent();
        userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String msg = text_send.getText().toString();

                if (!msg.equals("")) {
                    sendMessageText(fuser.getUid(), userid, msg);
                   // Toast.makeText(MessageActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                            }
                text_send.setText("");
               // btn_send.setVisibility(View.VISIBLE);
            }
        });
//        CoordinatorLayout container = (CoordinatorLayout) findViewById(R.id.rty);
//        FrameLayout container = (FrameLayout) findViewById(R.id.rty);
//        @SuppressLint("InflateParams")
//        View inflatedLayout= getLayoutInflater().inflate(R.layout.menu_attacment, null, false);
//        container.addView(inflatedLayout);

        reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                username.setText(user.getFull_name());

                    Glide.with(getApplicationContext()).load(UserImageBaseUrl+user.getImage()).into(profile_image);


                readMesagges(fuser.getUid(), userid, UserImageBaseUrl+user.getImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(userid);
    }

            private void stopRecording() {

        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
       // mStatusTv.setText(getString(R.string.record_finished));
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
//        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mLocalFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
       // mStatusTv.setText(getString(R.string.record_started));
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
//                    CropImage.activity()
//                            .start(MessageActivity.this);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    private void seenMessage(final String userid){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        Chat chat = snapshot.getValue(Chat.class);
                        assert chat != null;
                        if (chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid)) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("isseen", true);
                            snapshot.getRef().updateChildren(hashMap);
                        }
                    } catch (Exception ignored) {
                       dataSnapshot.getKey();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendMessageText(String sender, final String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        hashMap.put("time",texttime);

        reference.child("Chats").push().setValue(hashMap);


        // add user to chat fragment
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fuser.getUid())
                .child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRef.child("id").setValue(userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid)
                .child(fuser.getUid());
        chatRefReceiver.child("id").setValue(fuser.getUid());

        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (notify) {
                    assert user != null;
                    sendNotifiaction(receiver, user.getFull_name(), msg);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sendMessage(String sender, final String receiver,String message) {
        if (this.mImageUri != null) {
            final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(this.mImageUri));
            uploadTask = fileReference.putFile(this.mImageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@io.reactivex.annotations.NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        miUrlOk = downloadUri.toString();
                        Log.d("imag",miUrlOk);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("sender", sender);
                        hashMap.put("receiver", receiver);
                        hashMap.put("message",message);
                        hashMap.put("isseen", false);
                        hashMap.put("image", miUrlOk);
                        hashMap.put("time", texttime);

                        reference.child("Chats").push().setValue(hashMap);

                        // add user to chat fragment
                        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                                .child(fuser.getUid())
                                .child(userid);

                        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    chatRef.child("id").setValue(userid);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                                .child(userid)
                                .child(fuser.getUid());
                        chatRefReceiver.child("id").setValue(fuser.getUid());
                        final String nex = message;
                        reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(fuser.getUid());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                if (notify) {
                                    assert user != null;
                                    sendNotifiaction(receiver, user.getFull_name(), nex);
                                }
                                notify = false;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                }
            });

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    private void sendNotifiaction(String receiver, final String full_name, final String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.drawable.appicon, full_name + ": " + message, "New Message",
                            userid);
                    Sender sender = new Sender(data, token.getToken());
                    Log.d("sender", String.valueOf(sender));

                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if (response.code() == 200) {

                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMesagges(final String myid, final String userid, final String imageurl){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {

                        Chat chat = snapshot.getValue(Chat.class);
                        assert chat != null;
                        if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                                chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                            mchat.add(chat);
                        }

                        messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
                        recyclerView.setAdapter(messageAdapter);
                    }catch (Exception ignored){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void currentUser(String userid){
        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        editor.putString("currentuser", userid);
        editor.apply();
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(fuser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
        currentUser(userid);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        status("offline");
        currentUser("none");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            mImageUri = result.getUri();
//
//            sendMessage(fuser.getUid(), userid,miUrlOk);
//
//        } else {
//            Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MessageActivity.this, MessageActivity.class));
//            finish();
//        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
