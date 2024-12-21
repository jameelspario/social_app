package com.deificindia.indifun.fragments.ContributionRankFrag;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deificindia.indifun.R;
import com.deificindia.indifun.fragments.LeaderBoardDcfFragment;
import com.deificindia.indifun.fragments.LeaderBoardFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.COINSENDER;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.GLOBAL;


public class ContributionRank extends Fragment {
    int frag_type = COINSENDER;
    int global_local = GLOBAL;
     String family_ids;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    public ContributionRank() {}
    public ContributionRank(int frag_type) {
        this.frag_type = frag_type;
    }
    public ContributionRank(int frag_type, int global_local) {
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
        return inflater.inflate(R.layout.fragment_contribution_rank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.FragmentTab);
        viewPager = view.findViewById(R.id.viewpager);
        Intent intent =getActivity().getIntent();
        family_ids = intent.getStringExtra("family_id");
        System.out.println("family_id"+family_ids);
        setUpPager();

    }

    private void setUpPager() {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), getLifecycle(), global_local, frag_type);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Total");
                            break;
                        case 1:
                            tab.setText("Monthly");
                            break;
                        case 2:
                            tab.setText("Daily");
                    }

                }
        ).attach();


    }
     class ViewPagerAdapter extends FragmentStateAdapter {

        int frag_type = COINSENDER;
        int _global = GLOBAL;

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int global, int frag_type) {
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
            return new ResultContributionRank(frag_type,family_ids, pos);
        }
    }


}