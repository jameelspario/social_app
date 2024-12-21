package com.deificindia.firebasefragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.chat.Model.User;
import com.deificindia.firebaseAdapter.UserPostAdapter;
import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.indifun.R;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FirePostPublicFrag extends Fragment implements FirebaseAuth.AuthStateListener {

    private RecyclerView recyclerView;
    private UserPostAdapter userAdapter;
    private List<firebaseUserModel> userList;
    LoadingAnimView loadingAnimView;
    private DatabaseReference reference;

    private Query postQuery;
    private String newestPostId;
    private String oldestPostId;
    private int startAt = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    NestedScrollView nested_scroll;
    private List<String> followingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_post, container, false);
        loadingAnimView = view.findViewById(R.id.lottieanim);

        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);
        nested_scroll=view.findViewById(R.id.nested_scroll);
        recyclerView = view.findViewById(R.id.hotlist);

        recyclerView.setHasFixedSize(true);
//        mAuth.addAuthStateListener(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
       // search_bar = findViewById(R.id.search_bar);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(false);
        userList = new ArrayList<>();
        userAdapter = new UserPostAdapter(getActivity(), userList, true);
        recyclerView.setAdapter(userAdapter);

         swipeRefreshLayout.setOnRefreshListener(() -> {
             readUsers();
         });

        nested_scroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.

                if(oldestPostId!=null && userAdapter!=null){
                    loadMore();
                }
            }
        });

        readUsers();

        return view;

    }

    @Override
    public void onPause() {
        swipeRefreshLayout.setRefreshing(false);
        super.onPause();
    }

    private void readUsers() {
        userList.clear();
        swipeRefreshLayout.setRefreshing(true);

        reference = FirebaseDatabase.getInstance().getReference("User_one");
        reference.limitToFirst(5)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   firebaseUserModel user = snapshot.getValue(firebaseUserModel.class);
                       oldestPostId=snapshot.getKey();
                       userList.add(user);
                    Log.e( "onDataChange ",String.valueOf(dataSnapshot.getChildren()) );

                }

                swipeRefreshLayout.setRefreshing(false);
                userAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    public void loadMore() {

        reference = FirebaseDatabase.getInstance().getReference("User_one");
        // Refresh items
        ///HERE "oldestPostId" IS THE KEY WHICH I GET THE LAST RECORDS FROM THE FIREBASE
        reference.orderByKey().startAfter(oldestPostId).limitToFirst(5)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot child :snapshot.getChildren()){
                            firebaseUserModel user = child.getValue(firebaseUserModel.class);
                            oldestPostId=snapshot.getKey();
                            userList.add(user);
                            // Toast.makeText(getContext(), ""+oldestPostId, Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        userAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    @Override
    public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {

    }



}
