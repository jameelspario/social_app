package com.deificindia.indifun.fragments;

import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.COINSENDER;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.GLOBAL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deificindia.indifun.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class LeaderBoardFragment extends Fragment {

    int frag_type = COINSENDER;
    int global_local = GLOBAL;

    ViewPager2 viewPager;
    TabLayout tabLayout;

    public LeaderBoardFragment() {}

    public LeaderBoardFragment(int frag_type) {
        this.frag_type = frag_type;
    }

    public LeaderBoardFragment(int frag_type, int global_local) {
        this.frag_type = frag_type;
        this.global_local = global_local;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.FragmentTab);
        viewPager = view.findViewById(R.id.viewpager);

        setUpPager();

    }

    private void setUpPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), getLifecycle(), global_local, frag_type);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Hourly");
                            break;
                        case 1:
                            tab.setText("Daily");
                            break;
                        case 2:
                            tab.setText("7 Day");
                    }
                }
        ).attach();

       /* for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab2,null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }*/
    }

    static class ViewPagerAdapter extends FragmentStateAdapter {

        int frag_type = COINSENDER;
        int _global = GLOBAL;

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,int global, int frag_type) {
            super(fragmentManager, lifecycle);
            this._global = global;
            this.frag_type = frag_type;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return getFrag(position);
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        Fragment getFrag(int pos){
            return new LeaderBoardDcfFragment(_global, frag_type, pos);
        }
    }


}