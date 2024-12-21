package com.deificindia.indifun.source;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.fires.FirebaseRepository;

public class LiveFollowFriendsDataSource
        extends ItemKeyedDataSource<Integer, BroadList> {

    private FirebaseRepository firebaseRepository;

    public LiveFollowFriendsDataSource(){
        firebaseRepository = new FirebaseRepository();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<BroadList> callback) {

        firebaseRepository.getAtpRanks(0, params.requestedLoadSize, callback);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<BroadList> callback) {
        firebaseRepository.getAtpRanks(params.key, params.requestedLoadSize, callback);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<BroadList> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull BroadList item) {
        return 0;
    }



}
