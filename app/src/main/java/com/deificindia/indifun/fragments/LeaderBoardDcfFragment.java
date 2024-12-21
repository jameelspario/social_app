package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deificindia.indifun.Adapter.LeaderBoardAdapter;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.ListSorting;
import com.deificindia.indifun.interfaces.Event;
import com.deificindia.indifun.rest.RetroCalls;

import java.util.Collections;


public class LeaderBoardDcfFragment extends Fragment {

    public static final int COINSENDER = 1;
    public static final int FOLLOWERS = 2;
    public static final int GLOBAL = 0;
    public static final int LOCAL = 1;

    int global_local=0;
    int frag_type = 0;
    int data_type= 0;

    RecyclerView recyclerview;
    TextView txt_message;
    LeaderBoardAdapter leaderBoardAdapter;

    public LeaderBoardDcfFragment() {}

    public LeaderBoardDcfFragment(int global_local, int frag_type, int data_type) {
        this.global_local = global_local;
        this.frag_type = frag_type;
        this.data_type = data_type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leader_board_dcf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.recyclerview);
        txt_message = view.findViewById(R.id.txt_message);

        leaderBoardAdapter = new LeaderBoardAdapter(getContext());

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.addItemDecoration(new EqualSpacingItemDecoration(2));
        recyclerview.setAdapter(leaderBoardAdapter);

        Event.setGlobalStateChange(isglobal -> {
            global_local = isglobal;
            initDataFetch();
        });

        initDataFetch();

    }

    void initDataFetch(){

        switch (frag_type){
            case 0:
                RetroCalls calls;
                if(global_local==0 && data_type==0) calls = RetroCalls.instance().callType(110);
                else if(global_local==0 && data_type==1) calls = RetroCalls.instance().callType(120);
                else if(global_local==0 && data_type==2) calls = RetroCalls.instance().callType(130);
                else if(global_local==1 && data_type==0) calls = RetroCalls.instance().callType(140);
                else if(global_local==1 && data_type==1) calls = RetroCalls.instance().callType(150);
                else if(global_local==1 && data_type==2) calls = RetroCalls.instance().callType(160);
                else calls = RetroCalls.instance().callType(110);

                /*calls.listeners((type_result, code, message) -> {
                }).leaderBoardDiamond((type_result, obj2) -> {
                    if(type_result==110 && obj2!=null && obj2.getStatus()==1) {
                        Collections.sort(obj2.getResult(), new ListSorting.shortDiamondsResult());
                        leaderBoardAdapter.diamond(0, obj2.getResult());
                    }
                });*/

                break;
            case 1:
                RetroCalls calls1;
                if(global_local==0 && data_type==0) calls1 = RetroCalls.instance().callType(110);
                else if(global_local==0 && data_type==1) calls1 = RetroCalls.instance().callType(120);
                else if(global_local==0 && data_type==2) calls1 = RetroCalls.instance().callType(130);
                else if(global_local==1 && data_type==0) calls1 = RetroCalls.instance().callType(140);
                else if(global_local==1 && data_type==1) calls1 = RetroCalls.instance().callType(150);
                else if(global_local==1 && data_type==2) calls1 = RetroCalls.instance().callType(160);
                else calls1 = RetroCalls.instance().callType(110);

                calls1.listeners((type_result, code, message) -> {
                    str(message);

                }).leaderBoarcoin((type_result, obj2) -> {
                    if(type_result==110 && obj2!=null && obj2.getStatus()==1) {
                        if(obj2.getStatus()==1){
                            txt_message.setVisibility(View.GONE);
                            Collections.sort(obj2.getResult(), new ListSorting.shortCoinsResult());
                            leaderBoardAdapter.update(COINSENDER, obj2.getResult());
                        }
                        else if (obj2.getStatus()==0){
                            str(obj2.getMessage());
                        }

                    }
                });
                break;
            case 2:
                RetroCalls calls2;
                if(global_local==0 && data_type==0) calls2 = RetroCalls.instance().callType(110);
                else if(global_local==0 && data_type==1) calls2 = RetroCalls.instance().callType(120);
                else if(global_local==0 && data_type==2) calls2 = RetroCalls.instance().callType(130);
                else if(global_local==1 && data_type==0) calls2 = RetroCalls.instance().callType(140);
                else if(global_local==1 && data_type==1) calls2 = RetroCalls.instance().callType(150);
                else if(global_local==1 && data_type==2) calls2 = RetroCalls.instance().callType(160);
                else calls2 = RetroCalls.instance().callType(110);

                calls2.listeners((type_result, code, message) -> {
                    str(message);
                }).leaderBoarfollower((type_result, obj2) -> {
                    if(type_result==110 && obj2!=null) {
                        if(obj2.getStatus()==1) {
                            txt_message.setVisibility(View.GONE);
                            Collections.sort(obj2.getResult(), new ListSorting.shortFollowerResult());
                            leaderBoardAdapter.update(FOLLOWERS, obj2.getResult());
                        }
                        else if (obj2.getStatus()==0){
                            str(obj2.getMessage());
                        }
                    }
                });
                break;
        }
    }

    void str(String s){
        txt_message.setVisibility(View.VISIBLE);
        txt_message.setText(s);
    }




}