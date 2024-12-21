package com.deificindia.indifun.deificpk.frags;

import androidx.fragment.app.Fragment;

public interface PageChangeListener {
    int getCount();
    Fragment getItem(int position);
}
