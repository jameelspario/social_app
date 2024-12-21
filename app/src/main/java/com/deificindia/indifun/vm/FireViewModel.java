package com.deificindia.indifun.vm;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import com.deificindia.indifun.source.LiveFollowFriendsDataSourceFactory;

import io.reactivex.Observable;

public class FireViewModel extends ViewModel {

    private LiveFollowFriendsDataSourceFactory dataSourceFactory;
    private PagedList.Config config;

    public FireViewModel() {
        dataSourceFactory = new LiveFollowFriendsDataSourceFactory();

        config = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(25)
                .build();
    }

    public Observable<PagedList> getPagedListObservable(){
        return new RxPagedListBuilder(dataSourceFactory, config).buildObservable();
    }


}
