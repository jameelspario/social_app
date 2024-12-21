package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deificindia.chat.Model.User;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AccountActivity extends AppCompatActivity {


    private ImageView img_back,profile_image;
    private TextView txt_header_title,profile_name,profile_id;
    private RelativeLayout rl_account;
    private RelativeLayout logoutrl;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        img_back=findViewById(R.id.img_back);
        txt_header_title=findViewById(R.id.txt_header_title);
        profile_image=findViewById(R.id.profile_image);
        profile_id=findViewById(R.id.profile_id);
        profile_name=findViewById(R.id.profile_name);
        logoutrl=findViewById(R.id.logoutrl);

        txt_header_title.setText("Account");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        setuserdetails();

//        if (IndifunApplication.getInstance().getCredential().getProfilePicture() != null && !IndifunApplication.getInstance().getCredential().getProfilePicture().isEmpty()) {
//
//            Picasso.get()
//                    .load(URLs.UserImageBaseUrl+IndifunApplication.getInstance().getCredential().getProfilePicture())
//                    .error(R.drawable.no_image)
//                    .into(profile_image);
//        } else {
//            profile_image.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
//        }
//        profile_name.setText(IndifunApplication.getInstance().getCredential().getFullName());
//        profile_id.setText(IndifunApplication.getInstance().getCredential().getUid());

        logoutrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AccountActivity.this);
                alert.setTitle(getString(R.string.app_name));
                alert.setMessage(getString(R.string.logoutmsg))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                IndifunApplication.getInstance().logout();

                                Intent i = new Intent(AccountActivity.this, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                finish();

                            }
                        }).setNegativeButton(getString(R.string.cancel1), null);

                AlertDialog alert1 = alert.create();
                alert1.show();
            }
        });
    }

    public void setuserdetails(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user.getImage() != null && !user.getImage().isEmpty()) {

                    Picasso.get()
                            .load(URLs.UserImageBaseUrl+user.getImage())
                            .error(R.drawable.no_image)
                            .into(profile_image);
                } else {
                    profile_image.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                }
                profile_name.setText(user.getFull_name());
                profile_id.setText(user.getUid());




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStackImmediate();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}
