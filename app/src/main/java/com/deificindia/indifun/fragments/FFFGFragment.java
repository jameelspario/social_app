package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deificindia.chat.Adapter.UserAdapter;
import com.deificindia.chat.Model.User;
import com.deificindia.indifun.Adapter.FFFGAdapter;
import com.deificindia.indifun.Model.retro.UserFollowerResult;
import com.deificindia.indifun.Model.retro.UserFollowingResult;
import com.deificindia.indifun.Model.retro.UserFriendsResult;
import com.deificindia.indifun.Model.retro.UserGroupListResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Indifun;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.vm.FFFGViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import static com.deificindia.indifun.Utility.Sharedpref.getData;

import static com.deificindia.indifun.Utility.MySharePref.getData;
import static com.deificindia.indifun.Utility.UiUtils.setSwipeRefreshColor;


public class FFFGFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_PARAM1 = "param1";

    private int mParam1 = 0;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    FFFGAdapter adapter;
    FFFGViewModel liveViewModel;
    UserAdapter userAdapter;
    List<User> userList;
    String uid;


    List<UserFriendsResult> list_friends = new ArrayList<>();
    List<UserFollowingResult> list_following = new ArrayList<>();
    List<UserFollowerResult> list_follower = new ArrayList<>();
    List<UserGroupListResult> list_grouplist = new ArrayList<>();

    public FFFGFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_f_f_f_g, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }

        uid = IndifunApplication.getInstance().getCredential().getId();

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new SpacesItemDecoration(5));

        setSwipeRefreshColor(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new FFFGAdapter(getContext(), mParam1);


        liveViewModel = ViewModelProviders.of(this).get(FFFGViewModel.class);
//        liveViewModel.init();

        swipeRefreshLayout.setRefreshing(true);
        switch (mParam1) {
            case 0:
                adapter.setList_friends(list_friends);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.post(this::init_friends);
                break;
            case 1:
                adapter.setList_following(list_following);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.post(this::init_following);
                break;
            case 2:
                adapter.setList_follower(list_follower);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.post(this::init_follower);
                break;
            case 3:
                adapter.setList_grouplist(list_grouplist);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.post(this::init_grouplist);
                break;

        }
    }

    /*20/10/2020*/
    void init_friends() {
        //FFFGActivity.changeTitle(null,0);
        liveViewModel.get_friends(uid).observe(this.getActivity(), userFriends -> {
            if(userFriends.data!=null &&  userFriends.data.getStatus()==1 && userFriends.data.getResult()!=null) {

                Set<UserFriendsResult> s = new HashSet<UserFriendsResult>(userFriends.data.getResult());
                list_friends = new ArrayList<UserFriendsResult>(s);
                adapter.addFriends(list_friends);

            }

            swipeRefreshLayout.setRefreshing(false);
        });
    }

    void init_following() {
        liveViewModel.user_following(uid).observe(this.getActivity(), userFollowing -> {
            if(userFollowing.data!=null && userFollowing.data.getStatus()==1 && userFollowing.data.getResult()!=null) {

                Set<UserFollowingResult> s = new HashSet<UserFollowingResult>(userFollowing.data.getResult());
                list_following = new ArrayList<UserFollowingResult>(s);
                adapter.addFollowing(list_following);

            }
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    void init_follower() {
        liveViewModel.get_follower(uid).observe(this.getActivity(), userFollower -> {
            if(userFollower.data!=null && userFollower.data.getStatus()==1 && userFollower.data.getResult()!=null) {
                Set<UserFollowerResult> s = new HashSet<UserFollowerResult>(userFollower.data.getResult());
                list_follower = new ArrayList<UserFollowerResult>(s);
                adapter.addFollowers(list_follower);

            }
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    void init_grouplist() {
        liveViewModel.user_groups_list(uid).observe(this.getActivity(), userGroupList -> {
            if(userGroupList.data!=null && userGroupList.data.getStatus()==1 && userGroupList.data.getResult()!=null) {
                list_grouplist.clear();
                list_grouplist.addAll(userGroupList.data.getResult());
                adapter.notifyDataSetChanged();

            }
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onRefresh() {
        switch (mParam1) {
            case 0:
                init_friends();
                break;
            case 1:
                init_following();
                break;
            case 2:
                init_follower();
                break;
            case 3:
                init_grouplist();
                break;

        }
    }
}