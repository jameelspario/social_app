package com.deificindia.indifun.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.deificindia.indifun.Activities.LeaderBoardActivity;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;

import com.deificindia.indifun.deificpk.utils.BroadApiCall;
import com.deificindia.indifun.ui.spatab.InditabView;



public class LiveFragment extends AbstractFragment {
String level ;
    ViewPager2 pager2;
    //TabLayout tabLayout;
    InditabView indiTabView;
    ImageView call_board, startliveroom;

    public LiveFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        pager2 = v.findViewById(R.id.viewpager);
        //tabLayout = v.findViewById(R.id.FragmentTab);
        indiTabView = v.findViewById(R.id.inditab);
        call_board = v.findViewById(R.id.call_board);
        startliveroom = v.findViewById(R.id.startliveroom);

        setUpPager();

        call_board.setOnClickListener(v2->{
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity(), call_board, "transition");
            int revealX = (int) (call_board.getX() + call_board.getWidth() / 2);
            int revealY = (int) (call_board.getY() + call_board.getHeight() / 2);

            Intent intent = new Intent(getContext(), LeaderBoardActivity.class);
            intent.putExtra(LeaderBoardActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
            intent.putExtra(LeaderBoardActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

            ActivityCompat.startActivity(getContext(), intent, options.toBundle());

            //Intent i = new Intent(getActivity(), LeaderBoardActivity.class);
            //startActivity(i);
        });

        startliveroom.setOnClickListener(v3->{
            centerBottun();
        });
    }

    private void setUpPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), getLifecycle());
        pager2.setAdapter(adapter);

        indiTabView.setuptab(getContext(), "Follow", "Universal");
        indiTabView.changeSelection(0);
        indiTabView.setViewPager(pager2);
        indiTabView.setListener(null);
    }

    static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return getFrag(position);
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        Fragment getFrag(int pos){
            if(pos==1){
                return new LiveHotFragment();
            }else {
                return new LiveFollowFragment();
            }
        }
    }

    void centerBottun(){
        BroadApiCall.instance().with(getContext(), getChildFragmentManager(),
                IndifunApplication.getInstance().getCredential().getId())
                .with(startliveroom)
                .start();
    }



}