package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deificindia.indifun.R;
import com.deificindia.indifun.fragments.ContentFragment;
import com.deificindia.indifun.fragments.TopFans30Days;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TopFanListLayout extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_fan_list_layout);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        if (viewPager != null) {
            setupViewPager(viewPager);
        }
    }
    private void setupViewPager (ViewPager viewPager){
        adapter = new PagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new ContentFragment(), "30 days");
        adapter.addFragment(new TopFans30Days(), "Total");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));


        }


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
//                    Toast.makeText(TopFanListLayout.this,
//                            "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }

       public class PagerAdapter extends FragmentPagerAdapter {
            private final List<Fragment> myFragments = new ArrayList<>();
            private final List<String> myFragmentTitles = new ArrayList<>();
            private Context context;



            public PagerAdapter(FragmentManager fm, Context context) {
                super(fm);
                this.context = context;
            }

            public void addFragment(Fragment fragment, String title) {
                myFragments.add(fragment);
                myFragmentTitles.add(title);
            }

            @Override
            public Fragment getItem(int position) {
                return myFragments.get(position);
            }

            @Override
            public int getCount() {
                return myFragments.size();
            }

            public View getTabView(int position) {
                // Given you have a custom layout in `res/layout/custom_tab_item.xml` with a TextView and ImageView
                View v = LayoutInflater.from(context).inflate(R.layout.custom_tab_item, null);
                TextView tv = (TextView) v.findViewById(R.id.tab_item_txt);
                tv.setText(myFragmentTitles.get(position));
                return v;
            }

        }

}

