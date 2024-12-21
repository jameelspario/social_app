package com.deificindia.indifun.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.deificindia.indifun.R;

public class MyMedalFragment extends Fragment {

    public MyMedalFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mymedals_hourly, container, false);

        return v;
    }
}
