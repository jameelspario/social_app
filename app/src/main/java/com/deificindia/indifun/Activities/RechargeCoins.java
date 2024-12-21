package com.deificindia.indifun.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.deificindia.indifun.fragments.GoldenCoinFragment;
import com.deificindia.indifun.fragments.SilverCoinFragment;
import com.deificindia.indifun.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RechargeCoins extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    private ImageView img_back;
    private TextView txt_header_title, coinrecordstv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_coins);
        img_back = findViewById(R.id.img_back);
        txt_header_title = findViewById(R.id.txt_header_title);
        coinrecordstv = findViewById(R.id.coinrecordstv);
        tabLayout = findViewById(R.id.FragmentTab);
        viewPager = findViewById(R.id.viewpager);
        txt_header_title.setText("Recharge Coins");
        img_back.setOnClickListener(v -> onBackPressed());

        coinrecordstv.setOnClickListener(v ->
                startActivity(new Intent(RechargeCoins.this, CoinRecordActivity.class),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this, coinrecordstv, ViewCompat.getTransitionName(coinrecordstv)).toBundle())
        );

        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GoldenCoinFragment(), "Golden Coins");
        //adapter.addFragment(new SilverCoinFragment(), "Silver Coins");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(RechargeCoins.this).inflate(R.layout.coinscustom_tab, null);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}
