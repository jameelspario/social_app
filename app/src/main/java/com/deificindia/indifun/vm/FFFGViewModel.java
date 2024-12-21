package com.deificindia.indifun.vm;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deificindia.indifun.Model.retro.UserFollower;
import com.deificindia.indifun.Model.retro.UserFollowing;
import com.deificindia.indifun.Model.retro.UserFriends;
import com.deificindia.indifun.Model.retro.UserGroupList;
import com.deificindia.indifun.rest.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.Logger.toGson;

public class FFFGViewModel extends ViewModel {

    /*201026 @Anuj*/
    private MutableLiveData<UserFriends> mutableUserFriends;
    private MutableLiveData<UserFollowing> mutableUserFollowing;
    private MutableLiveData<UserFollower> mutableUserFollower;
    private MutableLiveData<UserGroupList> mutableUserGroupList;

    private LiveRepository newsRepository;

    /* @anuj*/
    /*26/10/2020*/
// FRIENDS
    public MutableLiveData<DataWrapper<UserFriends>> get_friends(String userId) {
        MutableLiveData<DataWrapper<UserFriends>> data = new MutableLiveData<>();

        Call<UserFriends> call = AppConfig.loadInterface().user_friends(userId);
        call.enqueue(new Callback<UserFriends>() {
            @Override
            public void onResponse(Call<UserFriends> call, Response<UserFriends> response) {

                if (response.isSuccessful()) {
                    UserFriends resdata = response.body();
                    Log.e("Result", "onResponse:"+toGson(resdata) ,null );
//                    Toast.makeText(,"Response is"+response, Toast.LENGTH_LONG)
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else {
                    data.setValue(new DataWrapper<>(null, ""));
                    Log.e("Result", "else", null);
                }
            }

            @Override
            public void onFailure(Call<UserFriends> call, Throwable t) {
                Log.e("Result", "onResponse:"+t.getMessage() ,null );
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, ""));
            }
        });

        return data;
    }
    ///-----------------------------------------
    // USER FOLLOWING
    public MutableLiveData<DataWrapper<UserFollowing>> user_following(String userId) {
        MutableLiveData<DataWrapper<UserFollowing>> data = new MutableLiveData<>();

        Call<UserFollowing> call = AppConfig.loadInterface().user_following(userId);
        call.enqueue(new Callback<UserFollowing>() {
            @Override
            public void onResponse(Call<UserFollowing> call, Response<UserFollowing> response) {

                if (response.isSuccessful()) {
                    UserFollowing resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else
                    data.setValue(new DataWrapper<>(null, ""));

            }

            @Override
            public void onFailure(Call<UserFollowing> call, Throwable t) {
                data.setValue(new DataWrapper<>(null, ""));
            }
        });

        return data;
    }
    ///-----------------------------------------
    public MutableLiveData<DataWrapper<UserFollower>> get_follower(String userId) {
        MutableLiveData<DataWrapper<UserFollower>> data = new MutableLiveData<>();

        Call<UserFollower> call = AppConfig.loadInterface().user_follower(userId);
        call.enqueue(new Callback<UserFollower>() {
            @Override
            public void onResponse(Call<UserFollower> call, Response<UserFollower> response) {

                if (response.isSuccessful()) {
                    UserFollower resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else
                    data.setValue(new DataWrapper<>(null, ""));

            }

            @Override
            public void onFailure(Call<UserFollower> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, ""));
            }
        });

        return data;
    }
    ///-----------------------------------------
    public MutableLiveData<DataWrapper<UserGroupList>> user_groups_list(String userId) {
        MutableLiveData<DataWrapper<UserGroupList>> data = new MutableLiveData<>();

        Call<UserGroupList> call = AppConfig.loadInterface().user_groups_list(userId);
        call.enqueue(new Callback<UserGroupList>() {
            @Override
            public void onResponse(Call<UserGroupList> call, Response<UserGroupList> response) {

                if (response.isSuccessful()) {
                    UserGroupList resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));;
                }else
                    data.setValue(new DataWrapper<>(null, ""));

            }

            @Override
            public void onFailure(Call<UserGroupList> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, ""));
            }
        });

        return data;
    }

}
