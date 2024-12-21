package com.deificindia.indifun.deificpk.acts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.AsyncPagedListDiffer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.deificindia.indifun.R;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.frags.ClipDataSource;
import com.deificindia.indifun.deificpk.frags.ClipPageDataSource;
import com.deificindia.indifun.deificpk.frags.LoadingState;
import com.deificindia.indifun.deificpk.frags.VerticalFramgment;
import com.deificindia.indifun.deificpk.frags.VerticalParentFragment;
import com.deificindia.indifun.deificpk.frags.VisibilityAware;
import com.deificindia.indifun.deificpk.modals.Clip;
import com.deificindia.indifun.deificpk.utils.DiffUtilCallback;
import com.deificindia.indifun.events.IndiActEvent;
import com.deificindia.indifun.events.IndiLiveFrag;
import com.deificindia.indifun.services.ShutDownService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.utils.Const.KEY_BUNDLE;
import static com.deificindia.indifun.deificpk.utils.Const.KEY_BUNDLE_IS_OWNER;
import static com.deificindia.indifun.events.IndiLiveFrag.IndiLiveFrag_PAGER_DISENABLE;
import static com.deificindia.indifun.events.IndiLiveFrag.IndiLiveFrag_PAGER_ENABLE;

public class IndiActivity extends IndiBaseActivity {

    private static final String TAG = "IndiActivity";

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private PlayerSliderFragmentViewModel mModel;
    private ViewPager2 pager2;

    FrameLayout topFrame;

    boolean isowner;

    @Override
    protected void onPermissionGranted() {
        if(topFrame!=null) {
            topFrame.removeAllViews();
            topFrame.setVisibility(View.GONE);
        }
        initViews();
    }

    @Override
    protected void onSendGift(int position, GiftInfo2 info) {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepScreenOn(getWindow());

        setContentView(R.layout.activity_indi);
        topFrame = findViewById(R.id.parent_live_topFrame);
        //ActivityUtils.topFrameText(topFrame, "Need Camera Permission");

    }

    void initViews(){
        hide(true);
        Intent intent = getIntent();

        Bundle params = intent.getBundleExtra(KEY_BUNDLE);

        isowner = params.getBoolean(KEY_BUNDLE_IS_OWNER);
        params.putInt(ClipDataSource.PARAM_COUNT, 5);

        PlayerSliderFragmentViewModel.Factory factory =
                new PlayerSliderFragmentViewModel.Factory(params, isowner);
        mModel = new ViewModelProvider(this, factory).get(PlayerSliderFragmentViewModel.class);

        PlayerSliderAdapter adapter = new PlayerSliderAdapter(getSupportFragmentManager(), getLifecycle());
        pager2 = findViewById(R.id.parent_live_view_pager);
        pager2.setAdapter(adapter);
       //// pager2.setUserInputEnabled(false);

        mModel.clips.observe(this, list -> {

            adapter.submitList(list);
            if (mModel.current > 0) {
                pager2.setCurrentItem(mModel.current, false);
            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.v(TAG, "Selected page is " + position + "; previous was " + mModel.current + ".");
                if (mModel.current != position) {
                    VisibilityAware hidden = (VisibilityAware) adapter.getFragmentByPosition(mModel.current);
                    if (hidden != null) {
                        hidden.setVisibleOrNot(adapter.getItem(mModel.current).owneruid, false);
                    }
                }

                VisibilityAware visible = (VisibilityAware) adapter.getFragmentByPosition(position);
                if (visible != null) {
                   // adapter.notifyDataSetChanged();
                    visible.setVisibleOrNot(adapter.getItem(position).owneruid, true);
                }

              /*  mModel.current = position;
                if (getResources().getBoolean(R.bool.prefetching_enabled)) {
                    Clip next = null;
                    try {
                        next = adapter.getItem(position + 1);
                    } catch (Exception ignore) {
                    }

                    if (next != null) {
                        Log.v(TAG, "Pre-fetching clip #" + next.id);
                        *//*HttpProxyCacheServer proxy = MainApplication.getContainer().get(HttpProxyCacheServer.class);
                        CacheUtil.prefetch(proxy, next.screenshot);
                        CacheUtil.prefetch(proxy, next.video);*//*
                    }
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                mModel.scrolling = state == ViewPager2.SCROLL_STATE_DRAGGING;
            }
        });

        //globacTouchListener();
    }

    private class PlayerSliderAdapter extends FragmentStateAdapter {

        private final AsyncPagedListDiffer<LiveEntity2> mDiffer;


        public PlayerSliderAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            mDiffer = new AsyncPagedListDiffer<>(
                    new AdapterListUpdateCallback(this),
                    new AsyncDifferConfig.Builder<>(new DiffUtilCallback<LiveEntity2>(x -> x.id)).build()
            );
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull FragmentViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            loge("IndiAct", "detached", ""+holder.getBindingAdapterPosition());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            LiveEntity2 clip = getItem(position);

            if(isowner){
                return VerticalFramgment.newInstance(clip, isowner);
            }else {
                return VerticalParentFragment.instance(clip, isowner);
            }
        }

        @Nullable
        public Fragment getFragmentByPosition(int position) {
            return getSupportFragmentManager().findFragmentByTag("f" + getItemId(position));
        }

        public LiveEntity2 getItem(int position) {
            return mDiffer.getItem(position);
        }

        @Override
        public int getItemCount() {
            return mDiffer.getItemCount();
        }

        public void submitList(PagedList<LiveEntity2> list) {
            mDiffer.submitList(list);
        }
    }


    public static class PlayerSliderFragmentViewModel extends ViewModel {

        public PlayerSliderFragmentViewModel(@NonNull Bundle params, boolean mowner) {
            PagedList.Config config = new PagedList.Config.Builder()
                    .setPageSize(params.getInt(ClipDataSource.PARAM_COUNT))
                    .build();
            factory = new ClipPageDataSource.Factory(params, mowner);
            state = Transformations.switchMap(factory.source, input -> input.state);
            clips = new LivePagedListBuilder<>(factory, config).build();
        }

        public int current = 0;
        public final LiveData<PagedList<LiveEntity2>> clips;
        public final ClipPageDataSource.Factory factory;
        public final LiveData<LoadingState> state;
        public boolean scrolling = false;
        public int viewed = 0;

        private static class Factory implements ViewModelProvider.Factory {

            private final boolean mowner;
            @NonNull private final Bundle mParams;

            public Factory(@NonNull Bundle params, boolean ads) {
                mParams = params;
                mowner = ads;
            }

            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                //noinspection unchecked
                return (T)new PlayerSliderFragmentViewModel(mParams, mowner);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIndiLiveFrag(IndiLiveFrag event){

        switch (event.WHAT){
            case IndiLiveFrag_PAGER_ENABLE:
                pager2.setUserInputEnabled(true);
                break;
            case IndiLiveFrag_PAGER_DISENABLE:
                pager2.setUserInputEnabled(false);
                break;
            case 3:
                hide(event.boo);
                break;
            case 4: finish(); break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if(EventBus.getDefault().hasSubscriberForEvent(IndiActEvent.class)){
            EventBus.getDefault().post(new IndiActEvent(1));
        }
    }

    @Override
    protected void onDestroy() {
        ShutDownService.startService(getApplicationContext(), "");
        super.onDestroy();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
    }
}