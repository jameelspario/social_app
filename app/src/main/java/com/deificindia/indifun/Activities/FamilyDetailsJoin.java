package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deificindia.indifun.Model.FamilyDetails;
import com.deificindia.indifun.Model.FamilyDetailsRes;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.bindingmodals.otheruserprofile.OtherUserProfile;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;

public class FamilyDetailsJoin extends AppCompatActivity {
private TextView family_name,family_id,family_about,family_points,family_level,family_join_level;
 private CircleImageView family_icon,top_family1,top_family2,top_family3;
 private FrameLayout family_background,family_icon_frame;
 private ImageView framebackground1;
 Button applyjoin;
 String UserId,JoinUs,AlerdayJoin;
 private RelativeLayout contribute;
 private CircleImageView frame1,frame2,frame3;
 int status;
    Result result;
 private String family_ids,familyname,familyabout,familypoint,familylevel1,familylevel2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_details_join);
        Intent intent =getIntent();
        family_ids=intent.getStringExtra("family_id");
        result=IndifunApplication.getInstance().getCredential();
        UserId=result.getId();
        JoinUs="yes";
        loge("tokenfind",UserId+JoinUs);
        Log.e( "findid",family_ids );
        family_name=findViewById(R.id.family_name);
        family_id=findViewById(R.id.family_id);
        family_about=findViewById(R.id.family_about);
        family_points=findViewById(R.id.family_point1);
        family_level=findViewById(R.id.family_levelcount);
        family_join_level=findViewById(R.id.family_levelcountour);
        family_icon=findViewById(R.id.frame_image);
        family_background=findViewById(R.id.framebackground);
        framebackground1=findViewById(R.id.framebackground1);
        family_icon_frame=findViewById(R.id.frame1_back);
        family_name=findViewById(R.id.family_name);
        frame1=findViewById(R.id.frame_top1);
        frame2=findViewById(R.id.frame_top2);
        frame3=findViewById(R.id.frame_top3);
        applyjoin=findViewById(R.id.applyjoin);
        contribute=findViewById(R.id.contribute);
        contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contri=new Intent(FamilyDetailsJoin.this,FamilyContributionRank.class);
                contri.putExtra("family_id",family_ids);
                startActivity(contri);
            }

        });
        Join_family();
//        if(status==1){
//            applyjoin.setText(AlerdayJoin);
//        }else if(status==2){
//            applyjoin.setText(AlerdayJoin);
//        }
//        else{
//            applyjoin.setText("apply_to_join");
//        }


        applyjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Join_family();
            }
        });
        getUserProfile();
    }
    void getUserProfile(){
        loge("UserDtaeils", "profile", family_ids);
        AppConfig.loadInterface()
                .total_details(family_ids)
                .enqueue(new Callback<FamilyDetailsRes>() {
                    @Override
                    public void onResponse(Call<FamilyDetailsRes> call, Response<FamilyDetailsRes> response) {
                      //  shimmerStop();
                        if(response.isSuccessful()){
                            FamilyDetailsRes userProfile = response.body();
                            if(userProfile!=null && userProfile.getStatus()==1 && userProfile.getResult()!=null){

                                setData(userProfile.getResult());

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<FamilyDetailsRes> call, Throwable t) {

                    }

                });
    }

    private void setData(FamilyDetails result) {
        family_name.setText(result.getFamily_name());
        family_id.setText(family_ids);
        family_about.setText(result.getFamily_about());
        family_level.setText(result.getFamily_level());
        family_join_level.setText(result.getFamily_next_level());
        family_points.setText(result.getFamily_point());


        Picasso.get().load(URLs.FamilYUserIcon+result.getFamily_icon())
                .placeholder(R.drawable.no_image)
                .into(family_icon);


       System.out.println("topuser"+ String.valueOf(result.getTop_users_images()));
      for(int i=0; i<result.getTop_users_images().size(); i++){
         if(i==0 && !result.getTop_users_images().get(i).isEmpty()) {

             Picasso.get().load(URLs.UserImageBaseUrl+result.getTop_users_images().get(i))
                     .placeholder(R.drawable.no_image)
                     .into(frame1);
             return;
         }
         if(i==1 && !result.getTop_users_images().get(i).isEmpty()) {

             Picasso.get().load(URLs.UserImageBaseUrl+result.getTop_users_images().get(i))
                     .placeholder(R.drawable.no_image)
                     .into(frame2);
             return;
         }
         if(i==2  && !result.getTop_users_images().get(i).isEmpty()) {

             Picasso.get().load(URLs.UserImageBaseUrl+result.getTop_users_images().get(i))
                     .placeholder(R.drawable.no_image)
                     .into(frame2);
             return;
         }

      }

        Picasso.get().load(URLs.FamilYUserIcon+result.getFamily_background())
                .placeholder(R.drawable.no_image)
                .into(framebackground1);
     //   family_background.setBackground(Drawable.createFromPath(result.getFamily_background()));
        if(result.getFamily_level() != null&& !result.getFamily_level().isEmpty()){

            family_icon_frame.setBackgroundResource(LevelControll.getLevelFrame(result.getFamily_level()));

        }

    }
    public void Join_family(){
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = Progress_Dialogue.showProgressDialog(FamilyDetailsJoin.this, "Loading Please Wait...");
        try {
            Result credencial = IndifunApplication.getInstance().getCredential();
            String token = credencial.user_token;
            loge("tokenfind",token);
            RetroCalls.instance().setOnFail((a,b,c)->{
                loge("errorfind",c);
        })
                    .stringParam(UserId,family_ids,JoinUs)
                    .family_join_request((type_result, obj2) -> {


                            System.out.println("find"+obj2);
                     //   Toast.makeText(this, ""+obj2.getMessage(), Toast.LENGTH_SHORT).show();
                            myDialog.dismiss();
                        status= obj2.getStatus();
                        AlerdayJoin=obj2.getMessage();
                        if(status==1){
                            applyjoin.setText(AlerdayJoin);
                        }else if(status==2){
                            applyjoin.setText(AlerdayJoin);
                        }
                        else{
                            applyjoin.setText("apply_to_join");
                        }

                    });
        } catch (Exception e){
              myDialog.dismiss();
        }
    }
}