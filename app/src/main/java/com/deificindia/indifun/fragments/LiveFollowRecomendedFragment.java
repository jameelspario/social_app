package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

import com.deificindia.indifun.Adapter.FollowAdapterRecomended;
import com.deificindia.indifun.Model.GetFollow_Result;
import com.deificindia.indifun.Model.TotalFamily;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.retro.LiveResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.fires.adapters.LiveFollowRecomendedFirePagingAdapter;
import com.deificindia.indifun.rest.LoadInterface;
import com.deificindia.indifun.rest.RetroConfig2;
import com.deificindia.indifun.services.ControllService;
import com.deificindia.indifun.vm.DataWrapper;
import com.deificindia.indifun.vm.FireViewModel;
import com.deificindia.indifun.vm.LiveViewModel;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.vm.LiveVm;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.EqualSpacingItemDecoration.GRID;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.fires.FireFun.BROADCASTERS;

public class LiveFollowRecomendedFragment extends Fragment {

    private RecyclerView recyclerView;
    String str_uid;
    FollowAdapterRecomended followAdapterRecomended;
    LoadingAnimView loadingAnimView;
    LiveFollowRecomendedFirePagingAdapter adapter;
    LiveVm vm;
    public LiveFollowRecomendedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_follow_recomended, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        str_uid = MySharePref.getUserId();
        //viewModel = ViewModelProviders.of(this).get(FireViewModel.class);
        vm = new ViewModelProvider(this).get(LiveVm.class);

        loadingAnimView = view.findViewById(R.id.loadinganim);

        /*recomended*/
        recyclerView = view.findViewById(R.id.recycler_follow_homepage_recommended);
        //swiperefresh =  view.findViewById(R.id.swipe_refresh);
        //swiperefresh.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(5,GRID));

        followAdapterRecomended = new FollowAdapterRecomended(getActivity());
        recyclerView.setAdapter(followAdapterRecomended);

       //loadData();
        liveRecommended();

    }

//    void loadData(){
//        loadingAnimView.startwatingLive();
//
//        vm.liveREcommended().observe(getViewLifecycleOwner(), liveEntities -> {
//            //loge("LiveHot", toGson(liveEntities));
//            if(liveEntities==null || liveEntities.isEmpty()){
//                //loadingAnimView.showError();
//                loadingAnimView.setErrText("Checking for online users");
//                followAdapterRecomended.update(new ArrayList<>());
//            }else {
//                loadingAnimView.stopAnim();
//                followAdapterRecomended.update(liveEntities);
//            }
//        });
//    }

   public void liveRecommended(){
        Call<GetFollow_Result> call = RetroConfig2.createService(LoadInterface.class,true)
                .follow_homepage_recommended();
        call.enqueue(new Callback<GetFollow_Result>() {
            @Override
            public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {
                GetFollow_Result resdata = response.body();
                if(resdata!=null && resdata.getStatus()==1 && resdata.getResult()!=null){
                            System.out.println("findllll"+resdata.getStatus());
                    Toast.makeText(getContext(), ""+resdata.getResult(), Toast.LENGTH_SHORT).show();
                    followAdapterRecomended.update(response.body().getResult());
                }else{

                }
            }
            @Override
            public void onFailure(Call<GetFollow_Result> call, Throwable t) {

            }
        });
    }
}