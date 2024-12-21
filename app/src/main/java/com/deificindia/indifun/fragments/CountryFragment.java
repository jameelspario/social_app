package com.deificindia.indifun.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.chat.Model.User;
import com.deificindia.indifun.Adapter.ExploreCountryAdapter;
import com.deificindia.indifun.Adapter.ExploreUsersByCountry;
import com.deificindia.indifun.Adapter.HotAdapter;
import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.Model.CountryUserResult;
import com.deificindia.indifun.Model.CountryUsers;
import com.deificindia.indifun.Model.retro.TrendingModal;
import com.deificindia.indifun.Model.retro.TrendingResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.StartSnapHelper;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.interfaces.OnLoadMoreListener;
import com.deificindia.indifun.modals.Post;
import com.deificindia.indifun.ui.PaginationScrollListener;
import com.deificindia.indifun.vm.CountryViewmodel;
import com.deificindia.indifun.vm.DataWrapper;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.ui.swipe.SwipeRefreshLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.deificindia.indifun.Utility.EqualSpacingItemDecoration.GRID;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.Utility.UiUtils.setSwipeRefreshColor;
import static com.deificindia.indifun.deificpk.utils.UserTags.LEVEL;

public class CountryFragment extends Fragment  {

    RecyclerView countryList, userList;

    SwipeRefreshLayout swipeRefreshLayout;
    LoadingAnimView loadingAnimView;
    ImageView leftArrow, rightArrow;
    CountryViewmodel countryViewmodel;
    ExploreCountryAdapter exploreCountryAdapter;
    ExploreUsersByCountry exploreCountryAdapterUsers;
    LinearLayoutManager linearLayoutManager;
    HotAdapter hotAdapter;
    private List<Post> postList;


    boolean programaticallyScrolled;
    int currentVisibleItem = 0;
    int selectedCountryId = 0;

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        countryList = view.findViewById(R.id.countryRecycler);
        userList = view.findViewById(R.id.countryUserRecycler);
        leftArrow = view.findViewById(R.id.leftArrow);
        rightArrow = view.findViewById(R.id.rightArrow);

       // rightArrow.setOnClickListener(this);

       // swipeRefreshLayout =  view.findViewById(R.id.swipe_refresh);
        loadingAnimView = view.findViewById(R.id.loadinganim);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        userList.setLayoutManager(gridLayoutManager);
        hotAdapter = new HotAdapter(getActivity(),postList);
        postList = new ArrayList<>();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //y'our method
                Log.d("runing","rinnon");
                readPosts();

            }
        }, 0, 2000);

        //loadhotData();
        userList.addItemDecoration(new EqualSpacingItemDecoration(5, GRID));
        userList.setAdapter(hotAdapter);





     //   setSwipeRefreshColor(swipeRefreshLayout);
        //swipeRefreshLayout

//        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
//
//        countryList.setHasFixedSize(false);
//        countryList.setLayoutManager(linearLayoutManager);
//        //countryList.setScrollBarSize(0);
//        countryList.addItemDecoration(new EqualSpacingItemDecoration(8, EqualSpacingItemDecoration.HORIZONTAL));
//        SnapHelper snapHelper = new StartSnapHelper();
//        snapHelper.attachToRecyclerView(countryList);

     //  initExploreUser();
      //  initExploreUserfire();
//        countryViewmodel = ViewModelProviders.of(this).get(CountryViewmodel.class);
//        countryList.setAdapter(exploreCountryAdapter);
        //loadingAnimView = view.findViewById(R.id.loadinganim);
      //  setupAdapterCountry();
        //call_country_viewmodel();
//        swipeRefreshLayout.setRefreshing(true);
//       // loadingAnimView.startloading();
//        swipeRefreshLayout.setOnRefreshListener(()->{
//           // loadById(selectedCountryId);
//        });

    }

//    private void handleWritingViewNavigationArrows(boolean scroll) {
//        if (currentVisibleItem == (countryList.getAdapter().getItemCount() - 1)) {
//            rightArrow.setVisibility(View.GONE);
//            leftArrow.setVisibility(View.VISIBLE);
//        } else if (currentVisibleItem != 0) {
//            rightArrow.setVisibility(View.VISIBLE);
//            leftArrow.setVisibility(View.VISIBLE);
//        } else if (currentVisibleItem == 0) {
//            leftArrow.setVisibility(View.GONE);
//            rightArrow.setVisibility(View.VISIBLE);
//        }
//        if (scroll) {
//            countryList.smoothScrollToPosition(currentVisibleItem);
//        }
//    }

//    void setupAdapterCountry(){
//        if(exploreCountryAdapter ==null) {
//            exploreCountryAdapter = new ExploreCountryAdapter(getContext());
//            countryList.setAdapter(exploreCountryAdapter);
//            exploreCountryAdapter.setOnCountryClickListener((pos, country) -> loadById(country.getId()));
//        }else {
//            exploreCountryAdapter.notifyDataSetChanged();
//        }
//    }
    
//    void call_country_viewmodel(){
//        isLoadingMore = true;
//        countryViewmodel.getCountry().observe(this.getActivity(), result -> {
//            if (result.data != null && result.data.getStatus() == 1 && result.data.getResult() != null) {
//                List<CountryNamesResult.MyCountry> res = result.data.getResult();
//                exploreCountryAdapter.updateCountry(res);
//                if(res.get(0)!=null){
//                    selectedCountryId = res.get(0).getId();
//                    loadById(selectedCountryId);
//                }
//            }else {
//
//                loadingAnimView.setErrText("");
//                swipeRefreshLayout.setRefreshing(false);
//                //txtcountryname.setVisibility(View.GONE);
//            }
//
//            //swipeRefreshLayout.setRefreshing(false);
//        });
//    }

    ////////////////Explore Country USers//////////////////////////////////////////
//    private PaginationScrollListener scrollListener;
//    int index = 0; //offset
//    int size = 5;
//    boolean isLoadingMore;
//    boolean reachedEnd;

//    void initExploreUser(){
//        LinearLayoutManager linear2 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//        userList.setHasFixedSize(false);
//        userList.setLayoutManager(linear2);
//        userList.addItemDecoration(new EqualSpacingItemDecoration(5));
//
//        exploreCountryAdapterUsers = new ExploreUsersByCountry(getContext());
//        userList.setAdapter(exploreCountryAdapterUsers);
//
//        scrollListener=new PaginationScrollListener(linear2);
//        scrollListener.setOnLoadMoreListener(() -> {
//            if(!isLoadingMore && !reachedEnd){
//                //LoadMoreExploreUser();
//            }
//        });
//
//        userList.addOnScrollListener(scrollListener);
//    }
    public void initExploreUserfire(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        userList.setLayoutManager(gridLayoutManager);
        hotAdapter = new HotAdapter(getActivity(),postList);
        postList = new ArrayList<>();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //y'our method
                Log.d("runing","rinnon");
                readPosts();

            }
        }, 0, 2000);

        //loadhotData();
        userList.addItemDecoration(new EqualSpacingItemDecoration(5, GRID));
        userList.setAdapter(hotAdapter);
//        scrollListener=new PaginationScrollListener(gridLayoutManager);
//        scrollListener.setOnLoadMoreListener(() -> {
//            if(!isLoadingMore && !reachedEnd){
//               // LoadMoreExploreUser();
//                new Timer().scheduleAtFixedRate(new TimerTask() {
//                    @Override
//                    public void run() {
//                        //y'our method
//                        Log.d("runing","rinnon");
//                        readPosts();
//
//                    }
//                }, 0, 2000);
//
//            }
//        });
//
//        userList.addOnScrollListener(scrollListener);
    }
    private void readPosts() {
        //    final String[] rando = new String[1];
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Go");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Post post = snapshot.getValue(Post.class);
                          loge("Fly", String.valueOf(dataSnapshot.getChildren()));
                        postList.add(post);

                    }

                }

                hotAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });

    }
//    private void LoadMoreExploreUser() {
//        index++;
//        exploreCountryAdapterUsers.addLoadingView();
//        isLoadingMore = true;
//        call_country_users_viewmodel(selectedCountryId);
//    }
//
//    void loadById(int countryid){
//        index = 0;
//        userList.setAdapter(null);
//        userList.setAdapter(exploreCountryAdapterUsers);
//        //exploreCountryAdapterUsers.addLoadingView();
//        isLoadingMore = true;
//        selectedCountryId = countryid;
//        call_country_users_viewmodel(countryid);
//    }

//    void call_country_users_viewmodel(int countryid){
//        loadingAnimView.startloading();
//        swipeRefreshLayout.setRefreshing(true);
//        countryViewmodel.getCountryUsers(countryid, index, size)
//                .observe(this.getActivity(), result -> {
//                    if (result.data != null && result.data.getStatus() == 1 && result.data.getResult() != null) {
//                        List<TrendingResult> res = result.data.getResult();
//
//                        setUsers(res);
//                        loadingAnimView.stopAnim();
//
//                    }else {
//                        if(index>0){
//                            reachedEnd();
//                            reachedEnd = true;
//                        }else {
//
//                            loadingAnimView.setErrText("");
//                        }
//                        loadingAnimView.showError();
//                    }
//
//                    swipeRefreshLayout.setRefreshing(false);
//                });
//    }

//    void setUsers(List<TrendingResult> list){
//        exploreCountryAdapterUsers.addData(list);
//
//        reachedEnd();
//    }
//
//    void reachedEnd(){
//        exploreCountryAdapterUsers.removeLoadingView();
//        scrollListener.setLoaded();
//        isLoadingMore = false;
//    }

//    int p;
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.rightArrow:
//                //p = linearLayoutManager.findFirstVisibleItemPosition() - 1;
//                //countryList.smoothScrollToPosition(p);
//                //checkVisibility();
//                countryList.smoothScrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
//                break;
//            case R.id.leftArrow:
//                //p = linearLayoutManager.findLastVisibleItemPosition() + 1;
//                //countryList.smoothScrollToPosition(p);
//                //checkVisibility();
//                if (linearLayoutManager.findFirstVisibleItemPosition() > 0) {
//                    countryList.smoothScrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() - 1);
//                } else {
//                    countryList.smoothScrollToPosition(0);
//                }
//                break;
//        }
//    }

//    public void checkVisibility() {
//        if (p < 1) {
//            rightArrow.setVisibility(View.GONE);
//            leftArrow.setVisibility(View.VISIBLE);
//        } else if (p >= (countryList.getAdapter().getItemCount() - 1)) {
//            rightArrow.setVisibility(View.VISIBLE);
//            leftArrow.setVisibility(View.GONE);
//        } else {
//            rightArrow.setVisibility(View.VISIBLE);
//            leftArrow.setVisibility(View.VISIBLE);
//        }
//    }

}