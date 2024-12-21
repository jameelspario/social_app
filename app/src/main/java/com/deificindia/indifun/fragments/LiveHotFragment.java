package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deificindia.indifun.Adapter.HotAdapter2;
import com.deificindia.indifun.Model.GetFollow_Result;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.modals.Post;
import com.deificindia.indifun.rest.LoadInterface;
import com.deificindia.indifun.rest.RetroConfig2;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.vm.LiveVm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.EqualSpacingItemDecoration.GRID;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.deificpk.utils.Const.loge;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveHotFragment extends Fragment {


    private RecyclerView hotrecycle;
    private HotAdapter2 hotAdapter;
//    private List<Post> postList;
    LoadingAnimView loadingAnimView;
    FirebaseUser fuser;
    String postid;
    String keys;

    LiveVm vm;

    public LiveHotFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_hot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseAuth.getInstance().getCurrentUser();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        vm = new ViewModelProvider(this).get(LiveVm.class);

        initView(view);
    }

    void initView(View view){
        hotrecycle = view.findViewById(R.id.hotrecycler);
        loadingAnimView = view.findViewById(R.id.lottieanim);
        FirebaseAuth.getInstance().getCurrentUser();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        hotrecycle.setLayoutManager(gridLayoutManager);
        hotrecycle.addItemDecoration(new EqualSpacingItemDecoration(5, GRID));
        hotAdapter = new HotAdapter2(getActivity());
        hotrecycle.setAdapter(hotAdapter);
        liveRecommended();
      //  loadhotData();
    }


//    void loadhotData(){
//        loadingAnimView.startwatingLive();
//
//        vm.liveHot().observe(getViewLifecycleOwner(), Post -> {
//            loge("LiveHot", toGson(Post));
//            if(Post ==null || Post.isEmpty()){
//                //loadingAnimView.showError();
//                loadingAnimView.setErrText("Checking for online users");
//                hotAdapter.update(new ArrayList<>());
//                loadingAnimView.startwatingLive();
//            }else {
//                loadingAnimView.stopAnim();
//              //  hotAdapter.update(Post);
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
                    Toast.makeText(getContext(), "findllll"+resdata.getResult(), Toast.LENGTH_SHORT).show();
                    hotAdapter.update(response.body().getResult());
                }else{

                }
            }
            @Override
            public void onFailure(Call<GetFollow_Result> call, Throwable t) {

            }
        });
    }



}