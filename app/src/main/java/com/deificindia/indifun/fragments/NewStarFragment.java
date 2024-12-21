package com.deificindia.indifun.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.deificindia.chat.Model.User;
import com.deificindia.firebaseAdapter.NewStarAdapter;
import com.deificindia.firebaseAdapter.Utilss;
import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.indifun.Adapter.GlobalAdapter;
import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.Model.retro.NewstarModal;
import com.deificindia.indifun.Model.retro.TrendingResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.PaginationScrollListener;
import com.deificindia.indifun.vm.LiveViewModel;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.ui.swipe.SwipeRefreshLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.deificindia.firebaseAdapter.Utilss.DataCache;
import static com.deificindia.firebaseAdapter.Utilss.show;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.UiUtils.setSwipeRefreshColor;
import static com.deificindia.indifun.rest.RetroCallListener.NEWSTAR_INDIA;
import static com.deificindia.indifun.rest.RetroCallListener.TRENDING;
import static java.util.Objects.requireNonNull;

public class NewStarFragment extends Fragment {

    public static final String ARG_PARAM1 = "VIEWTYPE";
    RecyclerView recyclerView;
    private List<String> followingList;
    private List<NewstarModal.MyResult> newstar_results = new ArrayList<>();
    private List<CountryNamesResult.MyCountry> country_results = new ArrayList<>();
    private List<TrendingResult> trending_results = new ArrayList<>();
    LiveViewModel liveViewModel;
    GlobalAdapter globalAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    LoadingAnimView loadingAnimView;
    String str_uid;
    int type_data = 0;
    private PaginationScrollListener scrollListener;
    int index = 0; //offset
    int size = 7;
    boolean isLoadingMore;
//    boolean reachedEnd;
    private NewStarAdapter userAdapter;
    private List<User> userList;
    SVGAImageView mGiftAnimSIV;
    SVGAParser mSVGAParse;

    private ProgressBar pb;
    private boolean mIsLoading = false;
    private int mPostsPerPage = 8;
    private Boolean isScrolling = false;
    private int currentScientists, totalScientists, scrolledOutScientists;
    public ArrayList<User> allPagesScientists = new ArrayList<>();
    private List<User> currentPageScientists = new ArrayList<>();
    private Boolean reachedEnd = false;
    EditText search_bar;
   //private List<String> followingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_new_star, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        loadingAnimView = view.findViewById(R.id.loadinganim);
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(5));

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        search_bar = view.findViewById(R.id.search_bar);
        pb=view.findViewById(R.id.mProgressBarLoad);

        userList=new ArrayList<>();
        userAdapter = new NewStarAdapter(getContext(),userList,true);

        recyclerView.setAdapter(userAdapter);

        if(DataCache.size() > 0){
            userAdapter.addAll(DataCache);
        }else{
            getUsers(null);
        }

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString());
              //  Utilss.search();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                searchUsers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchUsers(editable.toString());
            }
        });





        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //check for scroll state
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentScientists = layoutManager.getChildCount();
                totalScientists = layoutManager.getItemCount();
                scrolledOutScientists = ((LinearLayoutManager) recyclerView.getLayoutManager()).
                        findFirstVisibleItemPosition();

                if (isScrolling && (currentScientists + scrolledOutScientists ==
                        totalScientists)) {
                    isScrolling = false;

                    if (dy > 0) {
                        // Scrolling up
                        if(!reachedEnd){
                            getUsers(userAdapter.getLastItemId());
                            pb.setVisibility(View.VISIBLE);
                        }else{
                            //show(getContext(),"No More Item Found");
                        }


                    } else {
                        // Scrolling down
                    }
                }





            }
        });
        return view;
    }
    public static void show(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
    private void searchUsers(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("UserDetails")
                .orderByChild("full_name")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // List<User> userModelss = new ArrayList<>();
                userList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        user.setKey(snapshot.getKey());
                        userList.add(user);

                    }

                    userAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getContext(), "No item found", Toast.LENGTH_SHORT).show();
                   // Utilss.show("No item found");
                }
                Utilss.hideProgressBar(pb);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getUsers(String nodeId) {
        String firebaseUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mIsLoading=true;
        loadingAnimView.startloading();
        pb.setVisibility(View.VISIBLE);
        Query query ;

        if (nodeId == null)
            query = FirebaseDatabase.getInstance().getReference()
                    .child("UserDetails")
                    .orderByKey()
                    .limitToFirst(mPostsPerPage);
        else
            query = FirebaseDatabase.getInstance().getReference()
                    .child("UserDetails")
                    .orderByKey()
                    .startAt(nodeId)
                    .limitToFirst(mPostsPerPage);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> userModels = new ArrayList<>();
                if(dataSnapshot != null && dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(ds.getChildrenCount() > 0){
                            User scientist=ds.getValue(User.class);
                            requireNonNull(scientist).setKey(ds.getKey());
                            if(Utilss.scientistExists(ds.getKey())){
                                reachedEnd = true;
                                loadingAnimView.stopAnim();
                            }else{

                                if(scientist.getFuid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                    loge("dgdg",firebaseUser);

                                }else{
                                    reachedEnd=false;

                                    DataCache.add(scientist);
                                    userModels.add(scientist);
                                    currentPageScientists = userModels;
                                    currentScientists=userModels.size();
                                    loadingAnimView.stopAnim();
                                    Log.e( "onDataChange ",String.valueOf(dataSnapshot.getChildren()) );
                                }


                            }
                        }else{
                            Toast.makeText(getContext(), "Ds count 0", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                else{
                    loadingAnimView.stopAnim();
                    loadingAnimView.showError();
                    show(getContext(), "Data Doesn't Exists or is Null");
                }
                if(!reachedEnd){
                    userAdapter.addAll(userModels);
                }
                mIsLoading = false;
                pb.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mIsLoading = false;
                pb.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}