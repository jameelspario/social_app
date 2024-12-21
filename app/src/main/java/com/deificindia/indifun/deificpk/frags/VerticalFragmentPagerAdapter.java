package com.deificindia.indifun.deificpk.frags;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VerticalFragmentPagerAdapter  extends FragmentPagerAdapter {

/*
 MyAdapter mAdapter;
    VerticalViewPager mPager;
* mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
* */
    PageChangeListener pageChangeListener;
    public VerticalFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, PageChangeListener pageChangeListener) {
        super(fm, behavior);
        this.pageChangeListener = pageChangeListener;
    }

    @Override
    public int getCount() {
        return pageChangeListener.getCount();
    }

    @Override
    public Fragment getItem(int position) {
        return pageChangeListener.getItem(position);
    }
}
