package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.deificindia.indifun.fragments.BroadcasterLvFragment;
import com.deificindia.indifun.fragments.UserlevelFragment;
import com.deificindia.indifun.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityUserLevel extends AppCompatActivity {

    ViewPager ulViewpage;
    TabLayout UserlevelTab;
    ImageView ulBack;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_level);
        ulViewpage = findViewById(R.id.ulViewpage);
        UserlevelTab = findViewById(R.id.userLevelTab);
        ulBack = findViewById(R.id.ulBack);
        getTabs();
        ulBack.setOnClickListener(v -> onBackPressed());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    public  void getTabs(){
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(UserlevelFragment.getInstance(), "User Lv.");
        viewPagerAdapter.addFragment(BroadcasterLvFragment.getInstance(), "Broadcaster Lv.");

        ulViewpage.setAdapter(viewPagerAdapter);

        UserlevelTab.setupWithViewPager(ulViewpage);

    }

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

}