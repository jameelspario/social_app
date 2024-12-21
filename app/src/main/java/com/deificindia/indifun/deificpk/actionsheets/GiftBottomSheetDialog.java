package com.deificindia.indifun.deificpk.actionsheets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.deificindia.indifun.R;

import com.deificindia.indifun.Utility.MyDatasKt;
import com.deificindia.indifun.Utility.Partition;
import com.deificindia.indifun.db.TempDb;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.dialogs.giftsheet.GiftItemListener;
import com.deificindia.indifun.dialogs.giftsheet.OnGiftItemSelectedListener;
import com.deificindia.indifun.dialogs.giftsheet.ParentFragmentPagerAdapter;
import com.deificindia.indifun.dialogs.giftsheet.TabModal;
import com.deificindia.indifun.ui.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.deificindia.indifun.Utility.Logger.loge;

@SuppressLint("ViewConstructor")
public class GiftBottomSheetDialog extends AbstractActionSheet
        implements View.OnClickListener,
        OnGiftItemSelectedListener, LifecycleObserver {

    private GiftActionSheetListener mListener;

    public void registerLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    public GiftBottomSheetDialog(Context context, FragmentManager manager) {
        super(context);

        this.manager = manager;


    }

    @Override
    public void setActionSheetListener(AbsActionSheetListener listener) { }



    public interface GiftActionSheetListener {
        void onActionSheetGiftSend(int position, GiftInfo2 info);
    }

    public void setActionSheetListener(GiftActionSheetListener listener) {
        this.mListener = listener;
    }

    ViewPager2 viewPager2;
    //RecyclerView recyclerview_gift_1;
    LinearLayoutManager layoutManager;
    PagerSlidingTabStrip tabLayout;
    FragmentManager manager;

    private String mValueFormat;

    private int mSelected = -1;

    GiftInfo2 giftInfo;

    @Override
    public void onItemSelected(int pos, GiftInfo2 info) {
        mSelected = pos;
        giftInfo = info;
    }

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gift_bottom_sheet_dialog, container, false);
    }
*/
    public void init1() {

        LayoutInflater.from(getContext()).inflate(R.layout.gift_bottom_sheet_dialog, this, true);

        mValueFormat = getResources().getString(R.string.live_room_gift_action_sheet_value_format);

        viewPager2 = findViewById(R.id.viewpager2);
        tabLayout = findViewById(R.id.tabLayout);

        AppCompatTextView sendBtn = findViewById(R.id.live_room_action_sheet_gift_send_btn);
        GiftItemListener.setOnGiftItemSelectedListener(this);
        sendBtn.setOnClickListener(this);

        initPagers();

        //List<TabModal> modals = new ArrayList<>(Arrays.asList(tabTitles));
        //ParentTabAdapter parentTabAdapter = new ParentTabAdapter(modals);
        //layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
       /* recyclerview_gift_1.setLayoutManager(layoutManager);
        //ViewCompat.setNestedScrollingEnabled(recyclerview_gift_1, false);
        recyclerview_gift_1.setHasFixedSize(true);
        recyclerview_gift_1.setAdapter(parentTabAdapter);*/

    }

    public void init() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.gift_bottom_sheet_dialog, this, true);

        mValueFormat = getResources().getString(R.string.live_room_gift_action_sheet_value_format);

        viewPager2 = view.findViewById(R.id.viewpager2);
        tabLayout = view.findViewById(R.id.tabs);

        viewPager2.setSaveEnabled(false);
        AppCompatTextView sendBtn = findViewById(R.id.live_room_action_sheet_gift_send_btn);
        GiftItemListener.setOnGiftItemSelectedListener(this);
        sendBtn.setOnClickListener(this);

      /*  FragmentManager manager = getFragmentManager(getContext());
        if(fm!=null)*/
        loge("FitSheet", ""+manager);
        initPagers(manager);
    }

    public void initPagers(FragmentManager manager){
        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                /*Toast.makeText(HomeActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();*/
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

       // viewPager2.setAdapter(new ParentFragmentPagerAdapter2(manager, this));
        //tabLayout.setViewPager(viewPager2);

    }


    public void initPagers(){
        /*tabLayout.addTab(tabLayout.newTab().setText("Small"));
        tabLayout.addTab(tabLayout.newTab().setText("Jackpot"));
        tabLayout.addTab(tabLayout.newTab().setText("Big"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loge("GiftBottomSheetDialog","initPagers", ""+tab.getPosition());
                layoutManager.scrollToPositionWithOffset(tab.getPosition(), 0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

       *//* recyclerview_gift_1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int pos1 = getCurrentItem(recyclerview_gift_1);
                tabLayout.selectTab(tabLayout.getTabAt(pos1), true);
               // RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos1);
            }
        });*//*


        SnapHelper startSnapHelper = new SnappyRecyclerView();
        startSnapHelper.attachToRecyclerView(recyclerview_gift_1);*/

    }

    private int getCurrentItem(RecyclerView recyclerview){
        return ((LinearLayoutManager)recyclerview.getLayoutManager()).findFirstVisibleItemPosition();
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.live_room_action_sheet_gift_send_btn)
        {
            Log.d("clicked","clicked") ;
            if (mListener != null && mSelected != -1) {

                mListener.onActionSheetGiftSend(mSelected, giftInfo);

            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        Log.e("TAG", "================================>>>> lifecycle owner STARTED");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        Log.e("TAG", "================================>>>> lifecycle owner STOPED");
    }


    class ParentTabAdapter extends RecyclerView.Adapter<ParentTabAdapter.TabHolder>{

        List<TabModal> tabModals = new ArrayList<>();
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();

        public ParentTabAdapter(List<TabModal> tabModals) {
            this.tabModals = tabModals;
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull TabHolder holder) {
            super.onViewDetachedFromWindow(holder);
            if(holder.disposable!=null && !holder.disposable.isDisposed()) holder.disposable.dispose();
        }

        @NonNull
        @Override
        public TabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TabHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gift_category, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TabHolder holder, int position) {
            holder.bing(tabModals.get(position));
        }

        @Override
        public int getItemCount() {
            return tabModals.size();
        }

        class TabHolder extends RecyclerView.ViewHolder{

            RecyclerView recyclerViewChild;
            LinearLayoutManager linearLayoutManager;
            LinearLayout tabLayoutChild;
            //ChildPageAdapter childPageAdapter;
            Disposable disposable;

            private int dotscount;
            ImageView[] dots;

            public TabHolder(@NonNull View itemView) {
                super(itemView);
                recyclerViewChild = itemView.findViewById(R.id.photos_viewpager);
                tabLayoutChild = itemView.findViewById(R.id.tab_layout);
            }

            void bing(TabModal modal){
                /*childPageAdapter = new ChildPageAdapter(modal.categoriez);
                linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                recyclerViewChild.setLayoutManager(linearLayoutManager);
                recyclerViewChild.setHasFixedSize(true);
                recyclerViewChild.setRecycledViewPool(recycledViewPool);
                recyclerViewChild.setAdapter(childPageAdapter);*/

/*                tabLayoutChild.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        linearLayoutManager.scrollToPositionWithOffset(tab.getPosition(), 0);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) { }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) { }
                });
*/


               /* recyclerViewChild.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        int pos1 = getCurrentItem(recyclerViewChild);
                        tabLayoutChild.selectTab(tabLayoutChild.getTabAt(pos1), true);
                    }
                });*/


                callApi(modal.categoriez);
            }

            void callApi(int page){

                disposable = Observable.fromCallable(() ->
                        MyDatasKt.__tempDao().getGiftByCat(page))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(giftInfo2s -> {
                            if(giftInfo2s!=null && giftInfo2s.size()>0) {
                                List<List<GiftInfo2>> lis = new ArrayList<>();
                                if (giftInfo2s.size() > 8) {
                                    tabLayoutChild.setVisibility(VISIBLE);
                                    lis = Partition.ofSize(giftInfo2s, 8);
                                } else {
                                    Log.d("ok",lis.toString()) ;
                                    tabLayoutChild.setVisibility(GONE);
                                    lis.add(giftInfo2s);
                                }
                                //childPageAdapter.updateItems(lis);
                                dotView(lis.size());
                            }
                            //pagerInit(giftInfo2s.get(0).gift_category, giftInfo2s);
                            //else pagerInit(page, new ArrayList<GiftInfo2>());
                        });
            }

            void dotView(int count){
                dotscount = count;
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    ImageView img = new ImageView(getContext());
                    dots[i] = img;
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_activ_dots));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    tabLayoutChild.addView(dots[i], params);

                    img.setOnClickListener(v->{
                        int pos1 = getCurrentItem(recyclerViewChild);
                        scrollToDot(pos1);
                    });

                }
                dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));

                recyclerViewChild.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        int pos1 = getCurrentItem(recyclerViewChild);
                        //tabLayoutChild.selectTab(tabLayoutChild.getTabAt(pos1), true);

                        scrollToDot(pos1);

                    }
                });
            }

            void scrollToDot(int pos1){
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_activ_dots));
                }

                dots[pos1].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));
            }


        }
    }




}
