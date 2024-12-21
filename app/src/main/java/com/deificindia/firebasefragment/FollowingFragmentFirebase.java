package com.deificindia.firebasefragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deificindia.chat.Model.User;
import com.deificindia.firebaseAdapter.UserAdapter;
import com.deificindia.indifun.R;
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
import java.util.Objects;

import static com.deificindia.indifun.Utility.Logger.loge;


public class FollowingFragmentFirebase extends Fragment {
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<User> userList;
    private List<String> idList;
    String profileid;
    LoadingAnimView loadingAnimView;
    public FollowingFragmentFirebase() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_following_firebase, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        loadingAnimView=view.findViewById(R.id.loading);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        userList=new ArrayList<>();
        userAdapter=new UserAdapter(getContext(),userList,false);
        recyclerView.setAdapter(userAdapter);
//        getFollowers();

        profileid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        getFollowing();
        return view;


    }

    private void getFollowing() {
        loadingAnimView.startloading();
        idList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("UnFollow");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        idList.add(snapshot.getKey());
                        String ar = String.valueOf(snapshot.getKey());
                        Log.d("ghghghh", ar);
                        showUsers();
                        loadingAnimView.stopAnim();
                    }
                }else{
                    loadingAnimView.showError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void showUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
//                    userList.add(user);
                    for (String id : idList){
//                        assert user != null;
                        if (user.getFuid().equals(id)){
                            userList.add(user);
                        }
                    }
                }
                userAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}