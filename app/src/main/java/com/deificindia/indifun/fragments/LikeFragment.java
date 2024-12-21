package com.deificindia.indifun.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.deificindia.indifun.Adapter.LikeAdapter;
import com.deificindia.indifun.Model.LikeModel_Response;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.rest.AppConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.MySharePref.USERID;

public class LikeFragment extends Fragment {
    private RecyclerView likelist;
    private LikeAdapter likeAdapter;
    private SwipeRefreshLayout Notiswipe;
    private List<LikeModel_Response> likemodelList = new ArrayList<>();

    public  static LikeFragment getInstance(){
        LikeFragment likeFragment = new LikeFragment();
        return likeFragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saved){
        View view = inflater.inflate(R.layout.like_fragment_layout, container,false);
        Notiswipe = view.findViewById(R.id.Notiswipe);
        likelist = view.findViewById(R.id.likelist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        likelist.setLayoutManager(linearLayoutManager);
        getdata();

        Notiswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
                Notiswipe.setRefreshing(false);
            }
        });

        return  view;
    }


    private void getdata()
    {
        String uid = MySharePref.getUserId();
        Call<LikeModel_Response> likemodelList = AppConfig.loadInterface().getByUserid(uid);
        likemodelList.enqueue(new Callback<LikeModel_Response>() {
            @Override
            public void onResponse(Call<LikeModel_Response> call, Response<LikeModel_Response> response) {
                LikeModel_Response List = response.body();
                //likelist.setAdapter(new LikeAdapter(List.getResult(), getContext()));
               // Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LikeModel_Response> call, Throwable t) {
               // Toast.makeText(getContext(), "error occcured", Toast.LENGTH_LONG).show();

            }
        });
    }
}
