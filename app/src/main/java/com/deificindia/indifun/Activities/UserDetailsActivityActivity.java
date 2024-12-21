package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.deificindia.indifun.Adapter.ImageSliderAdapter;
import com.deificindia.indifun.Model.ImagesParsble;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.KotUtils;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.anim.CircleReavealAnim;
import com.deificindia.indifun.bindingmodals.otheruserprofile.Gift;
import com.deificindia.indifun.bindingmodals.otheruserprofile.Intrest;
import com.deificindia.indifun.bindingmodals.otheruserprofile.Language;
import com.deificindia.indifun.bindingmodals.otheruserprofile.MomentPic;
import com.deificindia.indifun.bindingmodals.otheruserprofile.OtherUserProfile;
import com.deificindia.indifun.bindingmodals.otheruserprofile.OtherUserProfileResult;
import com.deificindia.indifun.bindingmodals.otheruserprofile.TopFans;
import com.deificindia.indifun.databinding.ActivityUserDetailsActivityBinding;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.fragments.ImageViewFragment;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.RestWork;
import com.deificindia.indifun.rest.RetroCallListener;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.imagelistview.MyImageModal;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Activities.LeaderBoardActivity.EXTRA_CIRCULAR_REVEAL_X;
import static com.deificindia.indifun.Activities.LeaderBoardActivity.EXTRA_CIRCULAR_REVEAL_Y;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;
import static com.deificindia.indifun.rest.RetroCallListener.ONFOLLOWCLICK;

public class UserDetailsActivityActivity extends AppCompatActivity {

    public static final String IMG_PARAM = "imgparam";

    ActivityUserDetailsActivityBinding binding;
    String other_user_id, self_user_id;
    private int dotscount;
    private ImageView[] dots;
    RetroCallListener.OnSuccessListener onsuccess;

    UserTags userTags;

    int revealX, revealY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details_activity);

        //binding = ActivityUserDetailsActivityBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        init_data();
        listeners();
        getUserProfile();
    }

    class FragmentAdapter extends FragmentStateAdapter{

        ImagesParsble images;

        public FragmentAdapter(@NonNull FragmentManager fm, Lifecycle lc) {
            super(fm, lc);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new ImageViewFragment();
            Bundle args = new Bundle();

            if(images!=null) args.putString(IMG_PARAM, images.getImages().get(position));

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            if(images!=null) return images.getImages().size();
            return 0;
        }

        public void setImages(ImagesParsble images) {
            this.images = images;
        }

    }

    void init_data(){
        String name = getIntent().getStringExtra("NAME");
        other_user_id = getIntent().getStringExtra("UID");
        binding.profileName.setText(name!=null?name:"");
 loge("other",other_user_id);
        self_user_id = IndifunApplication.getInstance().getCredential().getId();
    }

    void listeners(){
        binding.mback.setOnClickListener(v->onBackPressed());

        binding.like.setOnClickListener(v-> {});

        binding.momentRecycler.init(1);

        userTags = UserTags.instance().container(binding.chips);
    }


    private void followuser() {
        RetroCalls.instance().callType(ONFOLLOWCLICK)
                .withUID()
                .stringParam(other_user_id)
                .setOnFail((type_result, code, message) -> {

                })
                .follow_user((type_result, obj) -> {
                    if(obj!=null){

//                          follow_user  binding.followBtn1.setText("Following");
                            binding.followBtn1.setClickable(false);
                            binding.like.setVisibility(View.VISIBLE);
                    }
                });
    }

    void getUserProfile(){
        loge("UserDtaeils", "profile", other_user_id, self_user_id);
        AppConfig.loadInterface()
                .other_user_profile(self_user_id, other_user_id)
        .enqueue(new Callback<OtherUserProfile>() {
            @Override
            public void onResponse(Call<OtherUserProfile> call, Response<OtherUserProfile> response) {
                shimmerStop();
                if(response.isSuccessful()){
                    OtherUserProfile userProfile = response.body();
                    if(userProfile!=null && userProfile.getStatus()==1 && userProfile.getResult()!=null){

                        setData(userProfile.getResult());

                    }
                }


            }


            @Override
            public void onFailure(Call<OtherUserProfile> call, Throwable t) {
                //shimmerStop();
            }
        });
    }

    void shimmerStop(){
        binding.shimmerview.stopShimmer();
        binding.shimmerview.setVisibility(View.GONE);
    }



    void setData(OtherUserProfileResult d){
        //loge("OtherUserId", self_user_id, ""+d.getId());
        if(self_user_id.equals(d.getId())){
            binding.followBtn1.setVisibility(View.GONE);
        }else {
            if(d.getIs_following()>0){
                RestWork.changeFolowButton(binding.followBtn1, 1);
                binding.followBtn1.setText("Following");
            }else {
                RestWork.changeFolowButton(binding.followBtn1, 3);
                                binding.followBtn1.setText("Follow");
                binding.followBtn1.setOnClickListener(v->followuser());

            }
            System.out.println("followposition"+d.getIs_following());
            
        }

        if(d.getAge()!=null && d.getGender()!=null){
            userTags.addTo(GENDER)
                    .updateGender(d.getAge(),  d.getGender());
        }

        if(d.getLevel()!=null) {
            userTags.addTo(LEVEL)
                    .updateLevel(d.getLevel() + " Lvl");
        }

        if(d.getCntryFlag()!=null){
            binding.flag.setVisibility(View.VISIBLE);
        }else {
            binding.flag.setVisibility(View.GONE);
        }

        if(d.getWhatsup()!=null){
            binding.largetext.setText(d.getWhatsup());
        }else binding.cardstatus.setVisibility(View.GONE);

        if(d.getProfilePicture()!=null) {
//            FragmentAdapter fa = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
            List<String> ls = new ArrayList<>();
            ls.add(d.getProfilePicture());
            loge("333",d.getProfilePicture());
//            fa.setImages(new ImagesParsble(ls));
            ImageSliderAdapter isa = new ImageSliderAdapter(this, ls);
            binding.imgviewpager.setAdapter(isa);

            dotscount = isa.getCount();
            dots = new ImageView[dotscount];

            for(int i = 0; i < dotscount; i++){

                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_activ_dots));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                binding.SliderDots.addView(dots[i], params);

            }

            if( dotscount==1){

            }else{
                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));

            }

            binding.imgviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    for(int i = 0; i< dotscount; i++){
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_activ_dots));
                    }

                   // dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }

        ///About me
        if(d.getRelationship()!=null) binding.relationText.setText(d.getRelationship());

        if(d.getMomentPic()!=null && d.getMomentPic().size()>0){
            binding.momentcount.setVisibility(View.VISIBLE);

            List<MyImageModal> img1 = new ArrayList<>();
            for(MomentPic mp:d.getMomentPic()){
                img1.add(new MyImageModal(URLs.UserPostImagesBaseUrl+mp.getImage()));

            }
            if(d.getMomentPic()!=null){
                binding.momentRecycler.updateData(img1);
                //binding.momentRecycler.setVisibility(View.VISIBLE);
            }
            else{
                binding.momentRecycler.setVisibility(View.VISIBLE);
            }


            binding.momentRecycler.setCount("Moments", String.valueOf(d.getMomentPicCount()));
           // Log.d("Moments", String.valueOf(img1));
        }
        if(d.getRelationship()!=null){
            binding.relationText.setText(d.getRelationship());
            if(d.getRelationship().equals("1")){
                binding.relationText.setText("Single");
            }else if(d.getRelationship().equals("2")){
                binding.relationText.setText("In a relationship");
            }else if(d.getRelationship().equals("3")){
                binding.relationText.setText("Married");
            }else{
                binding.relationText.setText("Privacy");
            }
        }



        if(d.getLanguage()!=null && d.getLanguage().size()>0) {
            List<String> langs = new ArrayList<>();
            for(Language l : d.getLanguage()){
                langs.add(l.getLanguageName());
            }
            binding.langtags.setTags(langs);
        }

        if(d.getIntrest()!=null && d.getIntrest().size()>0) {
            List<String> intrst = new ArrayList<>();
            for(Intrest i : d.getIntrest()){
                intrst.add(i.getName());
            }
            binding.interesttag.setTags(intrst);
        }

        if (d.getTopFans()!=null && d.getTopFans().size()>0){
            binding.fansCount.setText(String.valueOf(d.getTopFans().size()));
           // KotUtils.obj.filter(d.getTopFans());
          //  binding.fansLinear
            binding.topfansRecycler.init(0,1);
            binding.topfansRecycler.updateData(KotUtils.obj.filter(d.getTopFans()));
            binding.topfansLinear.setOnClickListener(v->{
                Intent top=new Intent(getApplicationContext(),TopFanListLayout.class);
              //  top.putParcelableArrayListExtra("topfans",KotUtils.obj.filter(d.getTopFans()));
                startActivity(top);
            });

        }


       // binding.cityText.setText(d.city);
        if(d.getGift()!=null && d.getGift().size()>0) {
            binding.giftLinear.setVisibility(View.VISIBLE);
            binding.giftno.setText("" + Math.max(d.getGiftCount(), 0));
            List<MyImageModal> img2 = new ArrayList<>();

            Map<String, Gift> map = new HashMap<>();
            Map<String, MyImageModal> map1 = new HashMap<>();



            for(Gift mp:d.getGift()){
                Gift gift=map.get(mp.getGiftId());
                if(gift!=null ){
                   // map.put(mp.getGiftId(),gift);
                   // gift.add(mp);
                    gift.count++;
                    map.put(mp.getGiftId(), gift);
                }else{
                    mp.count++;
                    map.put(mp.getGiftId(), mp);
                }

                map1.put(mp.getGiftId()
                ,new MyImageModal(URLs.GifgBaseUrl+mp.getCover()));
//                img2.add(new MyImageModal(URLs.GifgBaseUrl+mp.getCover()));

            }

            binding.giftsRecycler.init(0,1);
            binding.giftsRecycler.updateData(new ArrayList<>(map1.values()));

            binding.giftslinear.setOnClickListener(v->{
                Intent postgift=new Intent(getApplicationContext(),GiftPostLayout.class);
//                for(String diff:map.keySet()){
//                   Gift> gift1= map.get([diff);
//                }
                ArrayList<Gift> gift = new ArrayList<>();
                for(String s:map.keySet()){
                    gift.add(map.get(s));
                }
                System.out.println("123 "   + new Gson().toJson(gift));
                postgift.putParcelableArrayListExtra("gift", gift);
                startActivity(postgift);
            });
        }

        if(d.getLevel()!=null) binding.levelText.setText(d.getLevel());

        if(d.getMedals()!=null && d.getMedals().size()>0){
            binding.medalslinear.setVisibility(View.VISIBLE);
            binding.medalno.setText(""+Math.max(d.getMedalsCount(),0));
        }

        if(d.getDistance()!=null){
            binding.tdt.setText(d.getDistance());
        }else binding.tdt.setText("Unknown");

        if(d.getLocation()!=null){
            binding.cityText.setText(d.getLocation());
        }
       binding.wdt.setText(d.getId());

        binding.lsLinear.setVisibility(View.GONE);
       if(d.getJoin_date()!=null){
           binding.dateText.setText(d.getJoin_date());
       }else binding.dateText.setText("Unknown");

       loge("jointdate",d.getJoin_date());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

}