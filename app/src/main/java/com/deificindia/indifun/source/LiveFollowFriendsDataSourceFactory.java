package com.deificindia.indifun.source;

import androidx.paging.DataSource;

import com.deificindia.indifun.deificpk.modals.BroadList;

public class LiveFollowFriendsDataSourceFactory extends DataSource.Factory<Integer, BroadList>{
    @Override
    public DataSource<Integer, BroadList> create() {
        return new LiveFollowFriendsDataSource();
    }
}
