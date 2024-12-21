package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.deificindia.firebasefragment.FollowingFragmentFire;
import com.deificindia.firebasefragment.FollowingFragmentFirebase;
import com.deificindia.indifun.anim.CircleReavealAnim;
import com.deificindia.indifun.fragments.CountryFragment;
import com.deificindia.indifun.fragments.FFFGFragment;
import com.deificindia.indifun.R;
import com.deificindia.indifun.fragments.NewStarFragment;
import com.deificindia.indifun.ui.spatab.InditabView;

import static com.deificindia.indifun.Utility.Logger.loge;

public class FFFGActivity extends AppCompatActivity {

    ViewPager2 pager2;
    TextView tvh;

    InditabView indiTabView;
    String friends, following, followers, groups,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_f_f_g);

         int n = getIntent().getIntExtra("TAB", 0);
         friends = getIntent().getStringExtra("FriendsN");
         following = getIntent().getStringExtra("FollowingN");
         followers = getIntent().getStringExtra("FollowersN");
         groups = getIntent().getStringExtra("GroupsN");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
//        title = intent.getStringExtra("title");
         View rootLayout = findViewById(R.id.rootLayout);
        pager2 = findViewById(R.id.viewpager);
        indiTabView = findViewById(R.id.inditab);

        findViewById(R.id.imgBack).setOnClickListener(v-> onBackPressed());
        tvh = findViewById(R.id.tvHeading);
        changeTitle(n);

        setUpPager(n);
        pager2.setCurrentItem(n, false);

       /* ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {


                    rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }*/

    }

    void setUpPager(int n){

        loge("FFFGActivity", ""+n);
        Fragmentdapter adapter = new Fragmentdapter(getSupportFragmentManager(), getLifecycle());
        pager2.setAdapter(adapter);

        indiTabView.setuptab(this, "Friends", "Followings", "Followers");
        indiTabView.changeSelection(n);
        indiTabView.setViewPager(pager2);
        indiTabView.setListener(new InditabView.TabSelectionChange() {
            @Override
            public void onSelected(int index) {
                changeTitle(index);
            }

            @Override
            public void onPagerScrolled(int pos) { }
        });


    }

    static class Fragmentdapter extends FragmentStateAdapter{

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
        Fragment getFragment(int pos) {
            if (pos == 1) {
                return new FollowingFragmentFirebase();
            } else {
                Fragment fragment = new FollowingFragmentFire();
                Bundle args = new Bundle();
                args.putInt(FollowingFragmentFire.ARG_PARAM1, pos);
                fragment.setArguments(args);
                return fragment;
            }

        }
    }

    public void changeTitle(int n){

        switch (n) {
            case 0:
                tvh.setText("Friends"+ "("+ friends + ")");
                break;
            case 1:
                tvh.setText("Followings"+ "("+ following + ")");
                break;
            case 2:
                tvh.setText("Followers"+ "("+ followers + ")");
                break;
            case 3:
               tvh.setText("Groups"+ "("+ groups + ")");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

}