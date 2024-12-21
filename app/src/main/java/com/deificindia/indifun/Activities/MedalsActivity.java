package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.indifun.fragments.PostFollowFragment;
import com.deificindia.indifun.fragments.GameFragment;
import com.deificindia.indifun.fragments.MyMedalFragment;
import com.deificindia.indifun.fragments.SocialFragment;
import com.deificindia.indifun.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MedalsActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    private ImageView img_back,applogo1;
    private TextView txt_header_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medals);

        img_back=findViewById(R.id.img_back);
        txt_header_title=findViewById(R.id.txt_header_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout = findViewById(R.id.FragmentTab);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.viewpager, new PostFollowFragment());
        transaction.commit();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new SocialFragment(), "Social");
        adapter.addFragment(new GameFragment(), "Game");
        adapter.addFragment(new MyMedalFragment(), "My medals");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(this).inflate(R.layout.custom_medal_tab,null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }

        viewPager.setOffscreenPageLimit(1);
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