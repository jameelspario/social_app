package com.deificindia.indifun.fragments.ContributionRankFrag;

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
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs2Modals.LeaderBoardModel;
import com.deificindia.indifun.Model.contribution.ContributionModel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.ListSorting;
import com.deificindia.indifun.interfaces.Event;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.LoadInterface;
import com.deificindia.indifun.rest.RetroCalls;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResultContributionRank extends Fragment {
    public static final int COINSENDER = 1;
    public static final int FOLLOWERS = 2;
    public static final int LOCAL = 1;

    int global_local = 0;
    int data_type= 0;
    String  _dtfamily;

    RecyclerView recyclerview;
    TextView txt_message;
    ContributionAdapter leaderBoardAdapter;

    public ResultContributionRank(int frag_type, String family_ids, int data_type) {

        this.global_local = frag_type;
        this._dtfamily=family_ids;
        this.data_type = data_type;
    }
//
//    public ResultContributionRank(int frag_type, int data_type) {
//        this.global_local = frag_type;
//        this.data_type = data_type;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leader_board_dcf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.recyclerview);
        txt_message = view.findViewById(R.id.txt_message);

        leaderBoardAdapter = new ContributionAdapter(getContext());
           System.out.println("qweee"+_dtfamily);
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
        LoadInterface loadInterface = AppConfig.loadInterface();
        Call<ObjectModal<List<ContributionModel>>> call;
        switch (global_local){
            case 0:
                if(data_type==0) call= loadInterface.contribution(_dtfamily);
                else if( data_type==1)  call= loadInterface.contribution_monthly(_dtfamily);
                else  call = loadInterface.contribution_weekly(_dtfamily);

                /*calls.listeners((type_result, code, message) -> {
                }).leaderBoardDiamond((type_result, obj2) -> {
                    if(type_result==110 && obj2!=null && obj2.getStatus()==1) {
                        Collections.sort(obj2.getResult(), new ListSorting.shortDiamondsResult());
                        leaderBoardAdapter.diamond(0, obj2.getResult());
                    }
                });*/

                break;
            case 1:
                if( data_type==0) call =loadInterface.sent();
                else if( data_type==1) call =loadInterface.sent_monthly();
                else  call = loadInterface.sent_weekly();



                break;
            case 2:

                if(data_type==0) call = loadInterface.received();
                else if(data_type==1) call = loadInterface.received_monthly();

                else call = loadInterface.received_weekly();


                break;
            default:

                if(data_type==0) call = loadInterface.recharged();
                else if(data_type==1) call = loadInterface.received_monthly();

                else call = loadInterface.recharged_weekly();


                break;
        }
        call.enqueue(new Callback<ObjectModal<List<ContributionModel>>>() {
            @Override
            public void onResponse(Call<ObjectModal<List<ContributionModel>>> call, Response<ObjectModal<List<ContributionModel>>> response) {
                assert response.body() != null;
                leaderBoardAdapter.update(response.body().getResult());
            }

            @Override
            public void onFailure(Call<ObjectModal<List<ContributionModel>>> call, Throwable t) {

            }
        });
    }

    void str(String s){
        txt_message.setVisibility(View.VISIBLE);
        txt_message.setText(s);
    }

}