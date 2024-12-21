package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deificindia.indifun.R;

import com.google.android.material.tabs.TabLayout;

public class LiveFollowFragment extends Fragment {

    public LiveFollowFragment() { }

    TabLayout tabLayout;
    Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_follow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.frag_tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new LiveFollowFriendsFragment();
                        break;
                    case 1:
                        fragment = new LiveFollowRecomendedFragment();
                        break;
                }
                updateFrag(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

       /* for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab2,null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }*/

        updateFrag(fragment = new LiveFollowFriendsFragment());
    }

    private void updateFrag(Fragment fragment){
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container2, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }


}