        package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.deificindia.chat.Model.User;
import com.deificindia.firebaseAdapter.PostRecommendedFirebase;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.interfaces.OnFilterUpdate;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.ui.PaginationScrollListener;
import com.deificindia.indifun.ui.swipe.SwipeRefreshLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.Utility.KeyClass.SAVE_AGE1;
import static com.deificindia.indifun.Utility.KeyClass.SAVE_GENDER;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.MySharePref.getData;
import static com.deificindia.indifun.Utility.MySharePref.getIntData;
import static com.deificindia.indifun.Utility.UiUtils.setSwipeRefreshColor;
import static com.deificindia.indifun.dialogs.FilterBottomSheetDialog.dataset;
import static com.deificindia.indifun.interfaces.Event.setOnFilterUpdate;
import static com.deificindia.indifun.rest.RetroCallListener.POST_RECOMMENDED;

public class PostRecommendedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        OnFilterUpdate {

    private RecyclerView recview;
    private PostRecommendedFirebase adapter;
    private List<User> userList;

    private PaginationScrollListener scrollListener;
    int index = 0; //offset
    int size = 10;

    SwipeRefreshLayout swipeRefreshLayout;
    LoadingAnimView loadingAnimView;

    public PostRecommendedFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_recommended, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recview = view.findViewById(R.id.recomrecycler);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        loadingAnimView = view.findViewById(R.id.lottieanim);

        swipeRefreshLayout.setOnRefreshListener(this);
        setSwipeRefreshColor(swipeRefreshLayout);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recview.setHasFixedSize(true);
//        recview.setLayoutManager(linearLayoutManager);
        recview.addItemDecoration(new EqualSpacingItemDecoration(5));
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL
                , false);
        recview.setLayoutManager(layoutManager);
        // search_bar = findViewById(R.id.search_bar);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        userList = new ArrayList<>();
        adapter = new PostRecommendedFirebase(getActivity(), userList, true);
        recview.setAdapter(adapter);
        loadAll();


        scrollListener=new PaginationScrollListener(layoutManager);
        scrollListener.setOnLoadMoreListener(() -> {
            loadMore();

        });

        recview.addOnScrollListener(scrollListener);

        setOnFilterUpdate(this);

        firstLoad();
    }

    void initAdapter(){
        adapter = new PostRecommendedFirebase(getActivity());
        recview.setAdapter(adapter);
    }

    private void readUsers() {
        userList.clear();

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("UserDetails").orderByChild("gender").equalTo("Female");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                    loge( "onDataChange",String.valueOf(dataSnapshot.getChildren()) );
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void readUsers_male() {
        userList.clear();
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("UserDetails").orderByChild("gender").equalTo("Male");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                    loge( "onDataChange",String.valueOf(dataSnapshot.getChildren()) );
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void readUsers_all() {
        userList.clear();

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("UserDetails");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                    loge( "onDataChange",String.valueOf(dataSnapshot.getChildren()) );
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void onRefresh() { firstLoad(); }

    void firstLoad(){
        index = 0;
        userList.clear();
        loadAll();
    }

    void loadMore(){
        index++;
        loadAll();
    }

    void reachedEnd(){

        scrollListener.setLoaded();
    }

    void loadData(){
        swipeRefreshLayout.setRefreshing(true);
        loadingAnimView.startloading();

        String gender_selected = getData(getContext(), SAVE_GENDER,"All");

        int age_selected = getIntData(getContext(), SAVE_AGE1,0);

        String startAge="", endAge="";

        if(gender_selected.equalsIgnoreCase("All")){
            gender_selected = "";
        }

        String age_selected_str = "";
        if(dataset.get(age_selected).equalsIgnoreCase("All")){
            age_selected_str = "All";
        }else age_selected_str = dataset.get(age_selected);

        if(age_selected_str.contains("-")){
            String[] str = age_selected_str.split("-");
            if(str.length>1){
                startAge = str[0];
                endAge = str[1];
            }
        }

        RetroCalls.instance().callType(POST_RECOMMENDED)
                .withUID()
                .stringParam(gender_selected, startAge, endAge)
                .intParam(index, size)
                .post_recommended((a,b)->{
                    if (b != null && b.getStatus() == 1 && b.getResult() != null) {
                        reachedEnd();
//                        adapter.addData(b.getResult());
                        loadingAnimView.stopAnim();
                    }else{
                        if(index>0){
                            reachedEnd();
//                            adapter.reachedEnd(true);
                        } else {
                            loadingAnimView.showError();
                            loadingAnimView.setErrText("");
                        }
                    }

                    swipeRefreshLayout.setRefreshing(false);
                });
    }




    @Override
    public void onFileter(boolean ischanged) {
        if(ischanged) {
            firstLoad();
//            readUsers_all();
        }
    }

public void loadAll(){
    String gender_selected = getData(getContext(), SAVE_GENDER,"All");
    if (gender_selected == "All") {

        readUsers_all();
    }else if(gender_selected=="Male") {
        readUsers_male();
    }else if(gender_selected=="Female"){
        readUsers();
    }else{
        readUsers();
    }
}

}



