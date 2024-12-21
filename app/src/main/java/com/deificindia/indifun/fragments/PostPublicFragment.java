package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deificindia.indifun.Adapter.PostPublicAdapter;
import com.deificindia.indifun.Model.abs_plugs.HotpostResult;
import com.deificindia.indifun.Model.posts.Hotpostmodel;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.ListSorting;
import com.deificindia.indifun.interfaces.OnCommentUserClickListener;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.dialogs.CommentBottomSheetDialog;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.ui.PaginationScrollListener;
import com.deificindia.indifun.ui.swipe.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.deificindia.indifun.Utility.UiUtils.setSwipeRefreshColor;
import static com.deificindia.indifun.rest.RetroCallListener.FOLLOW_POST;
import static com.deificindia.indifun.rest.RetroCallListener.POST_PUBLIC;

public class PostPublicFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView hotlist;
    private PostPublicAdapter adapter;
    private List<Hotpostmodel.MyResult> hotpostmodelList = new ArrayList<>();
    CommentBottomSheetDialog bottomSheet;

    SwipeRefreshLayout swipeRefreshLayout;
    LoadingAnimView loadingAnimView;

    private PaginationScrollListener scrollListener;
    int index = 0; //offset
    int size = 5;

    public PostPublicFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_public, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hotlist = view.findViewById(R.id.hotlist);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        loadingAnimView = view.findViewById(R.id.lottieanim);

        swipeRefreshLayout.setOnRefreshListener(this);
        setSwipeRefreshColor(swipeRefreshLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        hotlist.setHasFixedSize(true);
        hotlist.setLayoutManager(layoutManager);
        hotlist.addItemDecoration(new EqualSpacingItemDecoration(5));

        initAdapter();

        scrollListener=new PaginationScrollListener(layoutManager);
        scrollListener.setOnLoadMoreListener(() -> {
            if(!adapter.isLoading() && !adapter.reachedEnd()){
                loadMore();
            }
        });

        hotlist.addOnScrollListener(scrollListener);

        firstLoad();
    }

    void initAdapter(){
        adapter = new PostPublicAdapter(getActivity());
        adapter.setOnCommentUserClickListener(new OnCommentUserClickListener() {
            @Override
            public boolean onComment(int pos, String id, String postby, View v) {
                bottomSheet = new CommentBottomSheetDialog();
                bottomSheet.setOnMessageSent(message -> on_comment(pos, id, message, v));
                bottomSheet.show(getChildFragmentManager(), "ModalBottomSheet");
                return false;
            }

            @Override
            public void onLikeClick() {
                loadingAnimView.playLikeAnim();
            }
        });
        hotlist.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        firstLoad();
    }

    void firstLoad(){
        index = 0;
        adapter.clearData();

        loadData();

    }

    void loadMore(){
        index++;
        adapter.addLoadingView();
        loadData();

    }

    void reachedEnd(){
        adapter.removeLoadingView();
        scrollListener.setLoaded();
    }

    void loadData(){

        swipeRefreshLayout.setRefreshing(true);
        loadingAnimView.startloading();

        RetroCalls.instance().callType(POST_PUBLIC)
                .withUID()
                .intParam(index, size)
                .post_public((a,b)->{
                    if (b != null && b.getStatus() == 1 && b.getResult() != null) {
                        reachedEnd();
                        List<HotpostResult> hotpostmodelList = b.getResult();
                        Collections.sort(hotpostmodelList, new ListSorting.ShortHotpostmodelByTime());
                        adapter.addData(hotpostmodelList);
                        loadingAnimView.stopAnim();
                    }else{
                        if(index>0){
                            reachedEnd();
                        } else {
                            loadingAnimView.showError();
                            loadingAnimView.setErrText("");
                        }
                    }

                    swipeRefreshLayout.setRefreshing(false);
                });

    }

    void on_comment(int pos,String post_id, String comment, View v){
        RetroCalls.instance().callType(FOLLOW_POST)
                .withUID()
                .stringParam(post_id, comment)
                .intParam(0)
                .setOnFail((a,b,c)->{
                    if(bottomSheet!=null) bottomSheet.dismiss();
                })
                .follow_post((a,obj)->{
                    if(obj!=null){
                        PostModal ob = (PostModal) obj;
                        if(ob.getStatus()==1){
                            adapter.onPostComment(pos);
                            //commentll.setVisibility(View.GONE);
                            if(bottomSheet!=null) bottomSheet.dismiss();
                        }
                    }
                    if(bottomSheet!=null) bottomSheet.dismiss();
                });
               /* .listeners((call_type, obj) -> {

                }, (type, code, message) -> {

                });*/
    }


}