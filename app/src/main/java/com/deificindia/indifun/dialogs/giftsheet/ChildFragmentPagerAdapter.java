package com.deificindia.indifun.dialogs.giftsheet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.deificindia.indifun.Utility.Partition;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;

import java.util.List;

public class ChildFragmentPagerAdapter extends FragmentStateAdapter {

    int gift_category = 1;
    List<List<GiftInfo2>> listinlist;

    public ChildFragmentPagerAdapter(@NonNull FragmentManager fragmentManager,
                                     @NonNull Lifecycle lifecycle, int gift_category, List<List<GiftInfo2>> infoList) {
        super(fragmentManager, lifecycle);
        this.gift_category = gift_category;
        this.listinlist = infoList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        //if(listinlist!=null){
            return ChildFragment.newInstance(gift_category, position, listinlist.get(position));
       /* }else {
            return ChildFragment.newInstance(gift_category, position, infoList);
        }*/

    }

    @Override
    public int getItemCount() {
        return listinlist.size();
    }

}
