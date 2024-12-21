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

import com.deificindia.firebaseAdapter.UserPostAdapter;
import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.indifun.Adapter.PostFollowAdapter;
import com.deificindia.indifun.Model.abs_plugs.HotpostResult;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.dialogs.CommentBottomSheetDialog;
import com.deificindia.indifun.interfaces.OnCommentUserClickListener;
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
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.UiUtils.setSwipeRefreshColor;
import static com.deificindia.indifun.rest.RetroCallListener.FOLLOW_POST;
import static com.deificindia.indifun.rest.RetroCallListener.POST_FOLLOW;

public class PostFollowFragment extends Fragment  {

    private RecyclerView rv_followe;

    CommentBottomSheetDialog bottomSheet;
    SwipeRefreshLayout swipeRefreshLayout;

    LoadingAnimView loadingAnimView;
    private PostFollowAdapter adapter;
    private UserPostAdapter userAdapter;
    private List<firebaseUserModel> userList;
    boolean ispostingcomment = false;
    private List<String> followingList1;
    private PaginationScrollListener scrollListener;

    public PostFollowFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_follow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_followe = view.findViewById(R.id.rv_followe);
     //   swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        loadingAnimView = view.findViewById(R.id.lottieanim);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_followe.setHasFixedSize(false);
        rv_followe.setLayoutManager(layoutManager);

        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        userList = new ArrayList<>();
        userAdapter = new UserPostAdapter(getActivity(), userList, true);
        rv_followe.setAdapter(userAdapter);

        checkFollowing1();
    }

    private void readUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User_one");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()) return;
                System.out.println("children"+dataSnapshot.getChildrenCount());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    firebaseUserModel user = snapshot.getValue(firebaseUserModel.class);

                    if(user!=null){
                        for (String id : followingList1) {
                            if (user.getId().equals(id)) {
                                userList.add(user);
                            }
                        }
                    }
                }

                userAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                loadingAnimView.playLikeAnim();
//                loadingAnimView.showError();
            }
        });
    }

    private void checkFollowing1() {
        loadingAnimView.startloading();
        followingList1 = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("UnFollow");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                followingList1.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        followingList1.add(snapshot.getKey());
                        readUsers();
                        loadingAnimView.stopAnim();
                        //loadingAnimView.showError();
                    }

                }else{
                    loadingAnimView.showError();

                       }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadingAnimView.showError();
            }
        });
    }

}