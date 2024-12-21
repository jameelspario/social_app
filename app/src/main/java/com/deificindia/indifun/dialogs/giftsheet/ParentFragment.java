package com.deificindia.indifun.dialogs.giftsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.MyDatasKt;
import com.deificindia.indifun.Utility.Partition;
import com.deificindia.indifun.db.TempDb;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.modals.GiftListResponse2;
import com.deificindia.indifun.rest.AppConfig;
//import com.deificindia.indifun.agorlive.proxy.struts.model.GiftInfo;
//import com.deificindia.indifun.agorlive.proxy.struts.response.GiftListResponse;
import com.deificindia.indifun.Utility.Logger;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage=1;

    FragmentManager childFragMang;
    ChildFragmentPagerAdapter adapter;
    ViewPager2 pager;
    //TabLayout tab1;
    LinearLayout tab1;
    Disposable disposable;

    private int dotscount;
    ImageView[] dots;

    public static ParentFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ParentFragment fragment = new ParentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift_page, container, false);
        childFragMang= getChildFragmentManager();
        tab1 =  view.findViewById(R.id.tab_layout);
        pager =  view.findViewById(R.id.photos_viewpager);

        pager.setSaveEnabled(false);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //changeTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        mPage = getArguments().getInt(ARG_PAGE);
        switch (mPage){
            case 0:callApi(3); break;
            case 1:callApi(2); break;
            case 2:callApi(1); break;
        }

        return view;
    }

    @Override
    public void onDetach() {
        if(disposable!=null && !disposable.isDisposed()) disposable.dispose();
        super.onDetach();
    }

    void callApi(int page){

        disposable = Observable.fromCallable(() ->
                MyDatasKt.__tempDao().getGiftByCat(page))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(giftInfo2s -> {
                    if(giftInfo2s!=null && giftInfo2s.size()>0)
                        pagerInit(giftInfo2s.get(0).gift_category, giftInfo2s);
                    else pagerInit(page, new ArrayList<GiftInfo2>());
                });
    }

    void pagerInit(int gift_category, List<GiftInfo2> infoList){
        List<List<GiftInfo2>> listinlist = new ArrayList<>();
        if(infoList.size()> 8) {
           listinlist = Partition.ofSize(infoList, 8);
        }else {
            listinlist.add(infoList);
        }
        adapter = new ChildFragmentPagerAdapter(childFragMang, getLifecycle(), gift_category, listinlist);

        pager.setAdapter(adapter);
        /*new TabLayoutMediator(tab1, pager, (tab, position) -> {

        }).attach();*/

        if(listinlist.size()>1) dotView(listinlist.size());
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                scrollToDot(position);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
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

            tab1.addView(dots[i], params);

            /*img.setOnClickListener(v->{
                int pos1 = getCurrentItem(recyclerViewChild);
                scrollToDot(pos1);
            });*/


        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));

       /* recyclerViewChild.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int pos1 = getCurrentItem(recyclerViewChild);
                //tabLayoutChild.selectTab(tabLayoutChild.getTabAt(pos1), true);
                scrollToDot(pos1);

            }
        });*/

    }

    void scrollToDot(int pos1){
        if(dots== null) return;

        for(int i = 0; i< dotscount; i++){
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_activ_dots));
        }

        dots[pos1].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));
    }


    void callApi1(int page){
        Logger.loge("ParentFrag", ""+page);

        Call<GiftListResponse2> call = AppConfig.loadInterface().getGiftList(page);
        call.enqueue(new Callback<GiftListResponse2>() {
            @Override
            public void onResponse(Call<GiftListResponse2> call, Response<GiftListResponse2> response) {
                GiftListResponse2 data = response.body();
                if(data!=null && data.getStatus()==1 && data.getResult()!=null){
                    //chatlist.setAdapter(new ChatAdapter(list.getResult(), getContext()));
                    List<GiftInfo2> infoList1 = data.getResult();
                    pagerInit(page, infoList1);
                }
            }

            @Override
            public void onFailure(Call<GiftListResponse2> call, Throwable t) { }
        });
    }

}
