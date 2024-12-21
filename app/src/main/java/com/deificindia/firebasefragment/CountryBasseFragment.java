package com.deificindia.firebasefragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

import com.deificindia.firebaseAdapter.CountryBaseAdapter;
import com.deificindia.indifun.Adapter.ExploreCountryAdapter;
import com.deificindia.indifun.Adapter.HotAdapter;
import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.StartSnapHelper;
import com.deificindia.indifun.modals.Post;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.ui.swipe.SwipeRefreshLayout;
import com.deificindia.indifun.vm.CountryViewmodel;
import com.deificindia.indifun.vm.LiveVm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.deificindia.indifun.Utility.EqualSpacingItemDecoration.GRID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CountryBasseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountryBasseFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView hotrecycle;
    private CountryBaseAdapter hotAdapter;
    private List<Post> postList;
    LoadingAnimView loadingAnimView;
    FirebaseUser fuser;
    String postid;
    String keys;
    FirebaseFirestore firebaseFirestore;
    RecyclerView countryList, userList;

    SwipeRefreshLayout swipeRefreshLayout;
    ImageView leftArrow, rightArrow;
    CountryViewmodel countryViewmodel;
    ExploreCountryAdapter exploreCountryAdapter;
    LinearLayoutManager linearLayoutManager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CountryBasseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CountryBasseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CountryBasseFragment newInstance(String param1, String param2) {
        CountryBasseFragment fragment = new CountryBasseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_basse, container, false);

        countryList = view.findViewById(R.id.countryRecycler);
        leftArrow = view.findViewById(R.id.leftArrow);
        rightArrow = view.findViewById(R.id.rightArrow);
        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
//
        countryList.setHasFixedSize(false);
        countryList.setLayoutManager(linearLayoutManager);
        //countryList.setScrollBarSize(0);
        countryList.addItemDecoration(new EqualSpacingItemDecoration(8, EqualSpacingItemDecoration.HORIZONTAL));
        SnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(countryList);
        countryViewmodel = ViewModelProviders.of(this).get(CountryViewmodel.class);
        countryList.setAdapter(exploreCountryAdapter);
        loadingAnimView = view.findViewById(R.id.loadinganim);
        setupAdapterCountry();
        call_country_viewmodel();


        hotrecycle = view.findViewById(R.id.hotrecycler);
        loadingAnimView = view.findViewById(R.id.lottieanim);
        FirebaseAuth.getInstance().getCurrentUser();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        hotrecycle.setLayoutManager(gridLayoutManager);
        hotAdapter = new CountryBaseAdapter(getActivity(), postList);
        postList = new ArrayList<>();
        loadhotData();
        hotrecycle.addItemDecoration(new EqualSpacingItemDecoration(5, GRID));
        hotrecycle.setAdapter(hotAdapter);
        //callAdapter();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //  FirebaseUser fr = FirebaseAuth.getInstance().getCurrentUser();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //y'our method
                Log.d("runing", "rinnon");
                readPosts();

            }
        }, 0, 2000);


        return view;
    }

    LiveVm vm;

    void loadhotData() {
        loadingAnimView.startloading();
        vm = new ViewModelProvider(this).get(LiveVm.class);
        vm.liveHot().observe(getViewLifecycleOwner(), Post -> {
            //loge("LiveHot", toGson(liveEntities));
            if (Post == null || Post.isEmpty()) {
                //loadingAnimView.showError();
                loadingAnimView.setErrText("");
                hotAdapter.update(new ArrayList<>());
            } else {
                loadingAnimView.stopAnim();
                hotAdapter.update(postList);
            }
        });
    }


    private void readPosts() {
        //    final String[] rando = new String[1];
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Go");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Post post = snapshot.getValue(Post.class);
                        //  loge("Fly", String.valueOf(dataSnapshot.getChildren()));
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
    void setupAdapterCountry(){
        if(exploreCountryAdapter ==null) {
            exploreCountryAdapter = new ExploreCountryAdapter(getContext());
            countryList.setAdapter(exploreCountryAdapter);
//            exploreCountryAdapter.setOnCountryClickListener((pos, country) ->
//                    loadById(country.getId()));
        }else {
            exploreCountryAdapter.notifyDataSetChanged();
        }
    }

    void call_country_viewmodel(){
       // isLoadingMore = true;
        countryViewmodel.getCountry().observe(this.getActivity(), result -> {
            if (result.data != null && result.data.getStatus() == 1 && result.data.getResult() != null) {
                List<CountryNamesResult.MyCountry> res = result.data.getResult();
                exploreCountryAdapter.updateCountry(res);
                if(res.get(0)!=null){
//                    selectedCountryId = res.get(0).getId();
//                    loadById(selectedCountryId);
                }
            }else {

                loadingAnimView.setErrText("");
                swipeRefreshLayout.setRefreshing(false);
                //txtcountryname.setVisibility(View.GONE);
            }

            //swipeRefreshLayout.setRefreshing(false);
        });
}
//        private void LoadMoreExploreUser() {
//        index++;
//        exploreCountryAdapterUsers.addLoadingView();
//        isLoadingMore = true;
//        call_country_users_viewmodel(selectedCountryId);
//    }

//    void loadById(int countryid){
//        index = 0;
//        userList.setAdapter(null);
//        userList.setAdapter(exploreCountryAdapterUsers);
//        //exploreCountryAdapterUsers.addLoadingView();
//        isLoadingMore = true;
//        selectedCountryId = countryid;
//        call_country_users_viewmodel(countryid);
//    }
    int p;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rightArrow:
                //p = linearLayoutManager.findFirstVisibleItemPosition() - 1;
                //countryList.smoothScrollToPosition(p);
                //checkVisibility();
                countryList.smoothScrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
                break;
            case R.id.leftArrow:
                //p = linearLayoutManager.findLastVisibleItemPosition() + 1;
                //countryList.smoothScrollToPosition(p);
                //checkVisibility();
                if (linearLayoutManager.findFirstVisibleItemPosition() > 0) {
                    countryList.smoothScrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() - 1);
                } else {
                    countryList.smoothScrollToPosition(0);
                }
                break;
        }
    }

    public void checkVisibility() {
        if (p < 1) {
            rightArrow.setVisibility(View.GONE);
            leftArrow.setVisibility(View.VISIBLE);
        } else if (p >= (countryList.getAdapter().getItemCount() - 1)) {
            rightArrow.setVisibility(View.VISIBLE);
            leftArrow.setVisibility(View.GONE);
        } else {
            rightArrow.setVisibility(View.VISIBLE);
            leftArrow.setVisibility(View.VISIBLE);
        }
    }

}