package com.deificindia.indifun.deificpk.actionsheets;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.deificindia.indifun.dialogs.giftsheet.ParentFragment;
import com.deificindia.indifun.dialogs.giftsheet.TabModal;

public class ParentFragmentPagerAdapter2 extends FragmentStateAdapter {

    final int PAGE_COUNT = 3;
    private TabModal tabTitles1[] = new TabModal[] {
            new TabModal("Small", 3),
            new TabModal("Jackpot",2),
            new TabModal("Big", 1)};

    public ParentFragmentPagerAdapter2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ParentFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}
