package com.deificindia.indifun.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.chat.ChatActivity;
import com.deificindia.chat.MessageActivity;
import com.deificindia.chat.Model.User;
import com.deificindia.firebaseActivity.FirebaseBroadCastWatcher;
import com.deificindia.firebaseActivity.FollowerActivityList;
import com.deificindia.firebaseActivity.PostingActivityF;
import com.deificindia.firebaseModel.Golivemodelfir;
import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.firebasefragment.FollowerFragmentFirebase;
import com.deificindia.firebasefragment.FollowingFragmentFirebase;
import com.deificindia.indifun.Activities.ActivityUserLevel;
import com.deificindia.indifun.Activities.AristocarticityCenter;
import com.deificindia.indifun.Activities.BroadcastsWatchActivity;
import com.deificindia.indifun.Activities.EditProfileActivity;
import com.deificindia.indifun.Activities.FFFGActivity;

import com.deificindia.indifun.Activities.IncomeActivity;
import com.deificindia.indifun.Activities.MedalsActivity;
import com.deificindia.indifun.Activities.MessagesActivity;
import com.deificindia.indifun.Activities.OnlineCustomerService;
import com.deificindia.indifun.Activities.ProfileActivity;
import com.deificindia.indifun.Activities.RechargeCoins;
import com.deificindia.indifun.Activities.SettingActivity;
import com.deificindia.indifun.Activities.SingupActivity;
import com.deificindia.indifun.Activities.WelcomeScreenActivity;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.ImagePickerActivity;
import com.deificindia.indifun.Utility.Indifun;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.dialogs.UserBlockListDialog;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
//import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.MySharePref.ISPROFILECOMPLETE;
import static com.deificindia.indifun.Utility.MySharePref.saveData;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;

public class ProfileFragment extends Fragment {
    private ImageView goprofile;
    private CircleImageView profile_image;
    private String following,groups,followers,friends,Diamonds;
    String gold_coins ;
    String grade ,amount , tonext ;
    private TextView friends1,following1,followers1,groups1,
            profile_name,profile_id, profilecompletepercentage;
    private LinearLayout layFriend, layFollowing, layFollower, layGroup, layBlockedUser;
    private int pImage;
    private ConstraintLayout profilell;
    private FrameLayout profileframe;
    private LinearLayout genderTag;
    private UserTags userTags;
    TextView tv_trans_name_message,
            tv_trans_name_level,
            tv_trans_name_broadcast_watch,
            tv_block,
            tv_online_customer_service_name,
            tv_trans_name_setting,

    mTvCoinsCounts, mTvdiamondCounts;
    LinearLayout   click_cashout;
    ImageView img_message,
            img_level,
            img_broadcast_watch,
            img_block,
            img_online_service,
            img_setting;
    String u_id;
    int levelfire;
    DatabaseReference databaseReference;
    Uri uri;
    private static final int REQUEST_IMAGE = 100;
    private String picturePath,number, name, email, dob, gender_str, age;
    private Bitmap bitmap;
    LinearLayout messageLayout,req_pay, userlevel, rl_broadcastswathched, onlinecustomerrl, rl_setting;
    RelativeLayout recharge_coin_card_view;
    Button  pay_request_btn ;
FirebaseUser firebaseUser;
    TextView tv_friends, tv_following, tv_followers;
    private Result credencial;
   String username,profilepic;
String fuid,useid,fullname;
String iddata;
DatabaseReference reference;
LinearLayout rl_mall1,aristrocracy;
    String miUrlOk = "";
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    StorageReference storageRef;
    private Uri mImageUri;
    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        friends1 = v.findViewById(R.id.friends1);

        storageRef = FirebaseStorage.getInstance().getReference("file");
        profileframe = v.findViewById(R.id.profileFrame);
        rl_mall1=v.findViewById(R.id.rl_mall1);
        rl_mall1.setOnClickListener(new OnFFGCLick());
        pay_request_btn =v.findViewById(R.id.pay_request) ;
        req_pay = v.findViewById(R.id.req_pay) ;
        aristrocracy = v.findViewById(R.id.aristrocracy) ;
        aristrocracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aristrocracy=new Intent(getContext(),AristocarticityCenter.class);
                startActivity(aristrocracy);
            }
        });
         pay_request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLiveAct();
            }
        });
        following1 = v.findViewById(R.id.following1);
        groups1 = v.findViewById(R.id.groups1);
        followers1 = v.findViewById(R.id.followers1);

        tv_friends = v.findViewById(R.id.tv_trans_fffg_friend);
        tv_following = v.findViewById(R.id.tv_trans_fffg_following);
        tv_followers = v.findViewById(R.id.tv_trans_fffg_followers);

        genderTag = v.findViewById(R.id.genderTags);

        profile_image=v.findViewById(R.id.profile_image);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMediaDialog();
            }
        });

        profile_name=v.findViewById(R.id.profile_name);
        profile_id=v.findViewById(R.id.profile_id);
        profilell=v.findViewById(R.id.profilell);
        profilecompletepercentage=v.findViewById(R.id.profilecompletepercentage);

        tv_trans_name_message=v.findViewById(R.id.tv_trans_name_message);
        img_message=v.findViewById(R.id.img_message);

        tv_trans_name_broadcast_watch=v.findViewById(R.id.tv_trans_name_broadcast_watch);
        img_broadcast_watch=v.findViewById(R.id.img_broadcast_watch);

        tv_trans_name_setting=v.findViewById(R.id.tv_trans_name_setting);
        img_setting=v.findViewById(R.id.img_setting);
        click_cashout=v.findViewById(R.id.click_cashout);
        click_cashout.setOnClickListener(new OnFFGCLick());

        mTvCoinsCounts=v.findViewById(R.id.tvcoins);
        mTvdiamondCounts=v.findViewById(R.id.tvdimonds);

        tv_online_customer_service_name=v.findViewById(R.id.tv_online_customer_service_name);
        img_online_service=v.findViewById(R.id.img_online_service);

        tv_trans_name_level=v.findViewById(R.id.tv_trans_name_level);
        img_level=v.findViewById(R.id.img_level);

        layFriend=v.findViewById(R.id.lay_friend);
        layFollowing=v.findViewById(R.id.lay_following);
        layFollower=v.findViewById(R.id.lay_followers);
        layGroup=v.findViewById(R.id.lay_groups);

        layFriend.setOnClickListener(new OnFFGCLick());
        layFollowing.setOnClickListener(new OnFFGCLick());
        layFollower.setOnClickListener(new OnFFGCLick());
        layGroup.setOnClickListener(new OnFFGCLick());

        messageLayout = v.findViewById(R.id.messageLayout);
        messageLayout.setOnClickListener(new OnFFGCLick());
        v.findViewById(R.id.rechargecoinsrl).setOnClickListener(new OnFFGCLick());
        userlevel = v.findViewById(R.id.userlevel);
        userlevel.setOnClickListener(new OnFFGCLick());
        v.findViewById(R.id.rl_medalshall).setOnClickListener(new OnFFGCLick());
        rl_broadcastswathched = v.findViewById(R.id.rl_broadcastswathched);
        rl_broadcastswathched.setOnClickListener(new OnFFGCLick());
        v.findViewById(R.id.rl_aristocracycenter).setOnClickListener(new OnFFGCLick());
        layBlockedUser = v.findViewById(R.id.rl_mall);
        layBlockedUser.setOnClickListener(new OnFFGCLick());
        onlinecustomerrl = v.findViewById(R.id.onlinecustomerrl);
        onlinecustomerrl.setOnClickListener(new OnFFGCLick());
        rl_setting = v.findViewById(R.id.rl_setting);
        rl_setting.setOnClickListener(new OnFFGCLick());

        recharge_coin_card_view = v.findViewById(R.id.recharge_coin_card_view);

        credencial = IndifunApplication.getInstance().getCredential();

        getFollowers();
        getdashboard();
        get_grades();
        getCoinsDiamondRecord();


        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
             //   username=user.getFull_name();

                profile_name.setText(user.getFull_name());
                profilepic=user.getImage();
                fuid=user.getFuid();
                useid=user.getId1();
                iddata=user.getId1();
                loge("hello ", iddata);
                fullname=user.getFull_name();
                loge("hello ", fullname);
                profile_id.setText(user.getUid());

                userTags = UserTags.instance().container(genderTag);
                userTags.addTo(GENDER)
                        .updateGender(user.getAge(),
                                user.getGender());

                if(user.getLevel() != null&& !user.getLevel().isEmpty()){

                    profileframe.setBackgroundResource(LevelControll.getLevelFrame(user.getLevel()));
                    userTags.addTo(LEVEL)
                            .updateLevel(user.getLevel() + " Lvl");

                }else{
                    defaultImage();
                    Picasso.get()
                            .load(URLs.UserImageBaseUrl+user.getImage())
                            .error(pImage)
                            .into(profile_image);
                }


                if (user.getImage() != null && !user.getImage().isEmpty()) {
            defaultImage();
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+user.getImage())
                    .error(pImage)
                    .into(profile_image);



        } else {
            defaultImage();
        }
                profilell.setOnClickListener(v15 -> {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra("UID", iddata);
                    intent.putExtra("NAME",fullname );

                    loge("fullname",fullname+iddata);

                    ActivityOptionsCompat options1 = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(),
                            Pair.create(genderTag, ViewCompat.getTransitionName(genderTag)),
                            Pair.create(profile_name, ViewCompat.getTransitionName(profile_name)),
                            Pair.create(profile_image, ViewCompat.getTransitionName(profile_image)));
                    startActivity(intent, options1.toBundle());

                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }
    private void getprofileframe() {
        String FUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        RetroCalls.instance().callType(27)
                .stringParam(FUID)
                .joiner_name((type_result, obj2) -> {
                    if(obj2.getResult()!=null){
                        //if(obj2.getResult().level!=null && !obj2.getResult().level.isEmpty()){
                            profileframe.setBackgroundResource(
                                    LevelControll.getLevelFrame(String.valueOf(obj2.getResult().level!=null&&!obj2.getResult().level.isEmpty())));

                    }
                });
    }
    public void defaultImage(){

        if(credencial.getGender().equals("Male")){
           pImage = R.drawable.ic_user__1_;
        } else{
            pImage = R.drawable.ic_user;
        }
        profile_image.setImageDrawable(ContextCompat.getDrawable(getContext(),pImage));
    }
    private void getFollowers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
              .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("followers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                followers1.setText(""+dataSnapshot.getChildrenCount());
                friends1.setText(""+dataSnapshot.getChildrenCount());
                loge("count", String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Follow")
               .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("UnFollow");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                following1.setText(""+dataSnapshot.getChildrenCount());

                loge("down", String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getdashboard() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = Progress_Dialogue.showProgressDialog(getActivity(), "Loading Please Wait...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USERDASHBOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                following=jsonObject.optString("following");
                                followers=jsonObject.optString("followers");
                                friends=jsonObject.optString("friends");
                                Diamonds=jsonObject.optString("level");
                                loge("dai",jsonObject.optString("golden_coins")) ;
                                groups=jsonObject.optString("groups");
                                gold_coins=jsonObject.optString("golden_coins");

                            } else {
                                myDialog.dismiss();

                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();


                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                Log.d("u_id",IndifunApplication.getInstance().getCredential().getId()) ;
                return params;
            }
        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
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
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    public void updateIuploadmage(){

            Result result = IndifunApplication.getInstance().getCredential();
            FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails");
            String postid = reference.push().getKey();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("image", miUrlOk);
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if(task.isSuccessful()){
                      //  Toast.makeText(getActivity(), "Profile update", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    private void openMediaDialog() {
        Dexter.withActivity(getActivity())
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
        ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
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
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
                    picturePath = Constants.encodeTobase64(bitmap);
                    updateprofile(picturePath);
                   // signUpMethod1(picturePath,number, name, email, dob, gender_str, age);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void updateprofile(String picturePath) {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getContext(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATEROFILE,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("user_profile"+response);
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
                                uploadImage_10(jsonObject.getString("profile_picture"));
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

                                saveData(getContext(), "ldata", jsonObject + "");
                                String u_id = jsonObject.getString("id");
                                String u_userid = jsonObject.getString("uid");
                                String u_name = jsonObject.getString("full_name");
                                String u_email = jsonObject.getString("email");
                                String u_contact = jsonObject.getString("contact");
                                saveData(getContext(), "U_ID", u_id);
//
//                                Intent intent = new Intent(getContext(), HomeActivity.class);
//                                startActivity(intent);
//                                finish();
                            } else {
                                myDialog.dismiss();
                                System.out.println(""+obj.optString("message"));
                             //   Toast.makeText(getContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
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
                        System.out.println(""+error.getMessage());
                        //Toast.makeText(EditProfileActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Result result=IndifunApplication.getInstance().getCredential();
                Map<String, String> params = new HashMap<>();
                params.put("full_name", result.getFullName());
                params.put("gender", result.getGender());
                params.put("whatsup",result.getWhatsup()==null?"":result.getWhatsup());
                params.put("country", result.getCountry());
                params.put("state", result.getState());
                params.put("city", result.getCity());
                params.put("image",  picturePath);
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
            //    Toast.makeText(getContext(), ""+params, Toast.LENGTH_SHORT).show();
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }




    private void uploadImage_10(String profile_picture) {
        String level= String.valueOf(1);
        String gold_coin= String.valueOf(0);


        // progressDialog.show();
        if (mImageUri != null) {
            final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + (mImageUri));
            loge("imgeurhh", String.valueOf(picturePath));
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
                        loge("imgeurhh", profile_picture);
                        Result result = IndifunApplication.getInstance().getCredential();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserDetails");
                        String postid=databaseReference.push().getKey();
                        HashMap<String, Object> hashMap = new HashMap<>();

                        hashMap.put("image",  profile_picture);

                databaseReference.
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void getCoinsDiamondRecord() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getContext(), "Wait...");

        RetroCalls.instance()
                .stringParam(IndifunApplication.getInstance().getCredential().user_token)
                .setOnFail((a, b, c) -> {
                    myDialog.dismiss();
                }).know_golden_coin((type_result, obj2) -> {

            myDialog.dismiss();

            if (obj2 != null && obj2.status == 1 && obj2.getResult() != null) {
                levelfire = obj2.getResult().golden_coin == null
                        || obj2.getResult().golden_coin.isEmpty() ? 0 : Integer.parseInt(obj2.getResult().golden_coin);
               // updateFirebaseLevel();
                mTvCoinsCounts.setText(String.valueOf(levelfire));

            }
        });
    }


    class OnFFGCLick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lay_friend:
                    gotTofffgActivity(0, v);
                    break;
                case R.id.lay_following:
                    gotTofffgActivity(1, v);
                    break;
                case R.id.lay_followers:
                    gotTofffgActivity(2, v);
                    break;
                case R.id.lay_groups:
                    gotTofffgActivity(3, v);
                    break;
                case R.id.messageLayout:
                    startAct(ChatActivity.class, messageLayout);

                    break;
                case R.id.userlevel:
                    startAct(ActivityUserLevel.class, userlevel);
                    break;
                case R.id.rl_broadcastswathched:
                    startAct(BroadcastsWatchActivity.class, rl_broadcastswathched);
                    break;
                case R.id.onlinecustomerrl:
                    startAct(OnlineCustomerService.class, onlinecustomerrl);
                    break;
                case R.id.rl_setting:

                    startAct(SettingActivity.class, rl_setting);
                    break;

                case R.id.rl_medalshall:
                    startActivity( new Intent(getActivity(), MedalsActivity.class));
                    break;

                case R.id.rl_aristocracycenter:
                    startActivity(new Intent(getActivity(), AristocarticityCenter.class));
                    break;
                case R.id.rl_mall:
                    int cx = (int) (layBlockedUser.getX() + (layBlockedUser.getWidth()/2));
                    int cy = (int) (layBlockedUser.getY() + (layBlockedUser.getHeight()/2));
                    UserBlockListDialog.opeDialog(getChildFragmentManager(), cx, cy);
                    break;

                case R.id.rechargecoinsrl:
                    startAct(RechargeCoins.class, recharge_coin_card_view);
                    break;
                case R.id.click_cashout:
                    startAct(IncomeActivity.class, click_cashout);
                    break;
                case R.id.rl_mall1:
                    startActivity( new Intent(getActivity(), FollowerActivityList.class));
                    break;
            }
        }
    }

    void gotTofffgActivity(int n, View view){
        Intent intent = new Intent(getContext(), FFFGActivity.class);
        intent.putExtra("TAB", n);
        intent.putExtra("FriendsN", friends1.getText().toString());
        intent.putExtra("FollowingN", following1.getText().toString());
        intent.putExtra("FollowersN", followers1.getText().toString());
        intent.putExtra("GroupsN", groups1.getText().toString());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity()/*, view, ViewCompat.getTransitionName(view)*/);

        startActivity(intent, options.toBundle());
    }

    void startAct(Class<?> cls, View view){
        Intent intent = new Intent(getActivity(), cls);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                view, ViewCompat.getTransitionName(view));
        startActivity(intent, options.toBundle());
    }

    private void get_grades() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
    //    myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");

        StringRequest stringRequest = new StringRequest("https://deificindia.com/indifun/panel/api/get_payment_grade?user_id=USER10627",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                             //   myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");

                                 grade=jsonObject.optString("grade");
                                userTags.addTo(grade)
                                        .updateLevel(grade + " Grade");
                                 amount=jsonObject.optString("amount");
                                 tonext=jsonObject.optString("to_next_grade");
                               // String sta = jsonObject.getString("status") ;
                              Log.d("obje",grade+amount+tonext) ;


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // myDialog.dismiss();

                        Toast.makeText(getActivity(), getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                return params;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    void startLiveAct(){
        //loge(TAG, "status 0 ", ""+picturePath);
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADD_Payment_request,
                response -> {

                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.d("ess",response) ;
                        String res = obj.getString("result");
                           myDialog.dismiss();

                    }
                    catch (JSONException e) {
                        Log.d("ero",e.toString());
                        myDialog.dismiss();
                        return;
                    }
                },
                error -> {

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", MySharePref.getUserId());
                params.put("user_name",credencial.getFullName());
                params.put("grade",  grade);
                params.put("payable_amount", amount);
                params.put("status", "0");
                //Log.d("ess",MySharePref.getUserId()+credencial.getFullName()+grade+amount) ;
                return params;
            }
        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


}


