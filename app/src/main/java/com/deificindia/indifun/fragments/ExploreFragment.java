package com.deificindia.indifun.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deificindia.firebasefragment.CountryBasseFragment;
import com.deificindia.indifun.R;
import com.deificindia.indifun.anim.PagerAnimations;
import com.deificindia.indifun.ui.spatab.InditabView;

public class ExploreFragment extends Fragment {

    ViewPager2 pager2;
    //TabLayout tabLayout;
    InditabView indiTabView;

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.explore_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pager2 = view.findViewById(R.id.viewpager);
        //tabLayout = view.findViewById(R.id.FragmentTab);
        indiTabView = view.findViewById(R.id.inditab);
        pager2.setPageTransformer(new PagerAnimations.DepthPageTransformer2());

        setUpPager();
    }

    void setUpPager(){
        Fragmentdapter adapter = new Fragmentdapter(getChildFragmentManager(), getLifecycle());
        pager2.setAdapter(adapter);

       /* new TabLayoutMediator(tabLayout, pager2,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Newstar");
                            break;
                        case 1:
                            tab.setText("Country");
                            break;
                        case 2:
                            tab.setText("Trending");
                            break;
                    }

                }
        ).attach();*/

      /*  for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab,null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }*/

        indiTabView.setuptab(getContext(), "Newstar", "Country", "Trending");
        indiTabView.changeSelection(0);
        indiTabView.setViewPager(pager2);
        indiTabView.setListener(null);

    }

    static class Fragmentdapter extends FragmentStateAdapter {

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

        Fragment getFragment(int pos){
            if(pos==1){
                return new CountryBasseFragment();
            }else {
                Fragment fragment = new NewStarFragment();
                Bundle args = new Bundle();
                args.putInt(NewStarFragment.ARG_PARAM1, pos);
                fragment.setArguments(args);
                return fragment;
            }
        }

    }

}