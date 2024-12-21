package com.deificindia.indifun.deificpk.frags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.db.LiveAppDb;
import com.deificindia.indifun.db.LiveEntity;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.db.LiveRepo;
import com.deificindia.indifun.deificpk.modals.Clip;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.deificindia.indifun.deificpk.utils.Const.*;

public class ClipPageDataSource  extends PageKeyedDataSource<Integer, LiveEntity2> implements ClipDataSource{

    private final boolean mowner;
    private final Bundle current_bundle;
    private final Integer selected_page;
    private final Integer selected_tab; //1=> friends | 2=> recommended 3=> universal

    private Integer last_page;

    private Integer current_page;
    private Integer next_page_index;
    private Integer previous_page_index;


    private final int mSeed = new Random().nextInt(99999 - 1000) + 1000;

    private int currentPage;
    int nextKey;

    public final MutableLiveData<LoadingState> state = new MutableLiveData<>(LoadingState.IDLE);

    public ClipPageDataSource(@NonNull Bundle params, boolean mowner1) {
        current_bundle = params;
        selected_page = params.getInt(KEY_BUNDLE_CLIP_ID);
        selected_tab = params.getInt(KEY_BUNDLE_CLIP_TAB);
        mowner = mowner1;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, LiveEntity2> callback) {
        //callback.onResult(responseItems, null, FIRST_PAGE + 1)

        if(mowner){
            List<LiveEntity2> ll = new ArrayList<>();

            Clip clp = current_bundle.getParcelable(KEY_CLIP_DATA);

            ll.add(clp.getOwnerInfo());

            callback.onResult(ll, null, null);
        }else {
            LiveAppDb.databaseWriteExecutor.execute(() -> {

                last_page = IndifunApplication.getInstance().liveDao.count_live();

                LiveEntity2 prev = IndifunApplication.getInstance().liveDao.universal2(1, selected_page+1);

                if(prev!=null){
                    next_page_index = selected_page+1;
                }else next_page_index = null;


                List<LiveEntity2> ll = new ArrayList<>();

                ll.add(getCurrentData(selected_page));
                
                loge("next_page_index", ""+selected_page, ""+last_page, ""+next_page_index);

                callback.onResult(ll, null, next_page_index);

            });
        }

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, LiveEntity2> callback) {
        //val key = if (params.key > 1) params.key - 1 else 0
        //callback.onResult(responseItems, key)

       /* if(!mAds1){
            if(params.key>1){
                int key = params.key -1;
                LiveRepo.readEntityById(key, d->{
                    if(d!=null && d.fuid!=null && d.roomid!=null){
                        List<Clip> l = new ArrayList<>();

                        Clip clp = new Clip();
                        clp.id = d.id;
                        clp.isowner = false;
                        clp.ownerfuid = d.fuid;
                        clp.roomid = d.roomid;

                        clp.broad_join_identity = Constants.roomIdentity(false, d.fuid);

                        l.add(clp);
                        callback.onResult(l, key);
                    }
                });
            }

        }*/
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, LiveEntity2> callback) {
       //val key = params.key + 1
        //callback.onResult(responseItems, key)
        /*if(!mowner){

            LiveRepo.liveUsersFilter(first_clip.ownerfuid, first_clip.n, d->{
                if(d!=null && !d.isEmpty()){

                    nextKey = (params.key == d.size()) ? null : params.key+1;

                    List<Clip> l = new ArrayList<>(d);

                    callback.onResult(l, null);
                }
            });
        }*/

        LiveAppDb.databaseWriteExecutor.execute(() -> {

            last_page = IndifunApplication.getInstance().liveDao.count_live();

            Integer key = params.key;

            loge("After", ""+key);

            LiveEntity2 next = IndifunApplication.getInstance().liveDao.universal2(1, key+1);
            if(next!=null){
                next_page_index++;
            }else next_page_index = null;


            List<LiveEntity2> ll = new ArrayList<>();
            ll.add(getCurrentData(key));
            callback.onResult(ll, next_page_index);
        });


    }

    private LiveEntity2 getCurrentData(Integer row_id){
        /*if(current_tab==1) ifExist = IndifunApplication.getInstance().liveDao.loadFriend1(1,1,0,0,0, owner_fuid);
        else if(current_tab==2) ifExist = IndifunApplication.getInstance().liveDao.loadRecommended1(1,1,1,0,0,0, owner_fuid);
        else if(current_tab==3) ifE = IndifunApplication.getInstance().liveDao.universal1(1,0,0,0, owner_fuid);
*/
        return IndifunApplication.getInstance().liveDao.universal2(1, row_id);

    }


    public static class Factory extends DataSource.Factory<Integer, LiveEntity2> {
     private final boolean mowner1;
        @NonNull public Bundle params;

        public MutableLiveData<ClipPageDataSource> source = new MutableLiveData<>();

        public Factory(@NonNull Bundle params, boolean mowner) {
            mowner1 = mowner;
            this.params = params;
        }

        @NonNull
        @Override
        public DataSource<Integer, LiveEntity2> create() {
            ClipPageDataSource source = new ClipPageDataSource(params, mowner1);
            this.source.postValue(source);
            return source;
        }
    }

}
