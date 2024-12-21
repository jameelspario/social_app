package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.fragments.ContributionRankFrag.ContributionRank;
import com.deificindia.indifun.fragments.FamilyDaily;
import com.deificindia.indifun.fragments.FamilyMonthly;
import com.deificindia.indifun.fragments.FamilyTotal;
import com.deificindia.indifun.fragments.LeaderBoardDcfFragment;
import com.deificindia.indifun.fragments.LeaderBoardFragment;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.COINSENDER;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.FOLLOWERS;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.GLOBAL;

public class FamilyContributionRank extends AppCompatActivity {
    TabLayout tabLayout, tab_layout12;
    ViewPager viewPager;
    private ImageView back;
    private int isglobal_local = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_contribution_rank);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout12 = (TabLayout) findViewById(R.id.tab_layout1);
viewPager = (ViewPager) findViewById(R.id.viewpager);
back=findViewById(R.id.back);
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent back_contri=new Intent(FamilyContributionRank.this,FamilyDetailsJoin.class);
        startActivity(back_contri);
    }
});

        tab_layout12.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                replaceFragment(new ContributionRank(tab.getPosition(),0));
//                if (tab.getPosition() == 0) {
//                    replaceFragment(new ContributionRank(COINSENDER));
//                } else {
//                    replaceFragment(new ContributionRank(FOLLOWERS, isglobal_local));
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
       // viewPager.setCurrentItem(0);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
