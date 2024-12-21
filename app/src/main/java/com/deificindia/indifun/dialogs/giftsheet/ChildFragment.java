package com.deificindia.indifun.dialogs.giftsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;

import java.util.List;

public class ChildFragment extends Fragment {
    public static final String ARG_PAGE2 = "ARG_PAGE2";

    private int mPage;
    private int pageonpage;
    List<GiftInfo2> infoList;
    RecyclerView recyclerView;


    public ChildFragment(int mPage, int pageonpage, List<GiftInfo2> infoList) {
        this.mPage = mPage;
        this.pageonpage = pageonpage;
        this.infoList = infoList;
    }

    public static Fragment newInstance(int mPage, int pageonpage, List<GiftInfo2> infoList){
        Fragment fragment = new ChildFragment(mPage, pageonpage, infoList);
        Bundle args = new Bundle();
        args.putInt(ChildFragment.ARG_PAGE2, 0);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE2);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift_page_child, container, false);
        recyclerView =  view.findViewById(R.id.recyclerView3);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(10));


        GiftAdapter giftAdapter = new GiftAdapter(infoList, mPage, pageonpage, getContext());
        recyclerView.setAdapter(giftAdapter);


        return view;
    }
}
