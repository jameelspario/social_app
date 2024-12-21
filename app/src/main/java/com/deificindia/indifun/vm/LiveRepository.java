package com.deificindia.indifun.vm;

import androidx.lifecycle.MutableLiveData;

import com.deificindia.indifun.Model.retro.UserFollower;
import com.deificindia.indifun.Model.retro.UserFollowing;
import com.deificindia.indifun.Model.retro.UserFriends;
import com.deificindia.indifun.Model.retro.UserGroupList;
import com.deificindia.indifun.rest.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveRepository {
    private static LiveRepository newsRepository;

    public static LiveRepository getInstance(){
        if (newsRepository == null){
            newsRepository = new LiveRepository();
        }
        return newsRepository;
    }

    public LiveRepository(){

    }

    /*201026 @Anuj*/
    public MutableLiveData<UserFriends> user_friends(String userId) {
        MutableLiveData<UserFriends> data = new MutableLiveData<>();

        Call<UserFriends> call = AppConfig.loadInterface().user_friends(userId);
        call.enqueue(new Callback<UserFriends>() {
            @Override
            public void onResponse(Call<UserFriends> call, Response<UserFriends> response) {

                if (response.isSuccessful()) {
                    UserFriends resdata = response.body();
                    data.setValue(resdata);
                }

            }

            @Override
            public void onFailure(Call<UserFriends> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
            }
        });

        return data;
    }
    public MutableLiveData<UserFollowing> user_following(String userId) {
        MutableLiveData<UserFollowing> data = new MutableLiveData<>();

        Call<UserFollowing> call = AppConfig.loadInterface().user_following(userId);
        call.enqueue(new Callback<UserFollowing>() {
            @Override
            public void onResponse(Call<UserFollowing> call, Response<UserFollowing> response) {

                if (response.isSuccessful()) {
                    UserFollowing resdata = response.body();
                    data.setValue(resdata);
                }

            }

            @Override
            public void onFailure(Call<UserFollowing> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
            }
        });

        return data;
    }
    public MutableLiveData<UserFollower> user_follower(String userId) {
        MutableLiveData<UserFollower> data = new MutableLiveData<>();

        Call<UserFollower> call = AppConfig.loadInterface().user_follower(userId);
        call.enqueue(new Callback<UserFollower>() {
            @Override
            public void onResponse(Call<UserFollower> call, Response<UserFollower> response) {

                if (response.isSuccessful()) {
                    UserFollower resdata = response.body();
                    data.setValue(resdata);
                }

            }

            @Override
            public void onFailure(Call<UserFollower> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
            }
        });

        return data;

    }
    public MutableLiveData<UserGroupList> user_groups_list(String userId) {
        MutableLiveData<UserGroupList> data = new MutableLiveData<>();

        Call<UserGroupList> call = AppConfig.loadInterface().user_groups_list(userId);
        call.enqueue(new Callback<UserGroupList>() {
            @Override
            public void onResponse(Call<UserGroupList> call, Response<UserGroupList> response) {

                if (response.isSuccessful()) {
                    UserGroupList resdata = response.body();
                    data.setValue(resdata);
                }

            }

            @Override
            public void onFailure(Call<UserGroupList> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
            }
        });

        return data;
    }




}
