package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deificindia.chat.Model.User;
import com.deificindia.indifun.Adapter.FollowAdapter;
import com.deificindia.indifun.Model.GetFollow_Result;
import com.deificindia.indifun.Model.retro.LiveResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.fires.adapters.LiveFollowFriendsFirePagingAdapter;
import com.deificindia.indifun.rest.LoadInterface;
import com.deificindia.indifun.rest.RetroConfig2;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.vm.LiveVm;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.deificindia.indifun.Utility.EqualSpacingItemDecoration.GRID;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.fires.FireFun.BROADCASTERS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveFollowFriendsFragment extends Fragment {

    private RecyclerView recyclerView;
    String str_uid;

    FollowAdapter followAdapter;
    LoadingAnimView loadingAnimView;

    LiveVm vm;

    public LiveFollowFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_follow_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        str_uid = MySharePref.getUserId();

        vm = new ViewModelProvider(this).get(LiveVm.class);

        //viewModel = ViewModelProviders.of(this).get(FireViewModel.class);

        loadingAnimView = view.findViewById(R.id.loadinganim);

        recyclerView =  view.findViewById(R.id.follow_homepage);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(5,GRID));
        loadingAnimView = view.findViewById(R.id.loadinganim);

        followAdapter = new FollowAdapter(getActivity());
        recyclerView.setAdapter(followAdapter);
        liveRecommended();
       // loadData();
    }


    void loadData(){
        loadingAnimView.startwatingLive();

        vm.liveFollow().observe(getViewLifecycleOwner(), liveEntities -> {
           loge("LiveHot", toGson(liveEntities));
            if(liveEntities==null || liveEntities.isEmpty())
            {
                //loadingAnimView.showError();
                loadingAnimView.setErrText("Checking for online users");
                followAdapter.update(new ArrayList<>());
            }
            else {
                loadingAnimView.stopAnim();
              //  followAdapter.update(liveEntities);
            }
        });
    }

    public void liveRecommended(){
        Call<GetFollow_Result> call = RetroConfig2.createService(LoadInterface.class,true)
                .follow_homepage();
        call.enqueue(new Callback<GetFollow_Result>() {
            @Override
            public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {
                GetFollow_Result resdata = response.body();
                if(resdata!=null && resdata.getStatus()==1 && resdata.getResult()!=null){
                    System.out.println("findvideo"+resdata.getStatus());
                   // Toast.makeText(getContext(), "findllll"+resdata.getResult(), Toast.LENGTH_SHORT).show();
                        followAdapter.update(response.body().getResult());
                }else{

                }
            }

            @Override
            public void onFailure(Call<GetFollow_Result> call, Throwable t) {

            }
        });
    }

}