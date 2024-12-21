package com.deificindia.indifun.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.deificindia.indifun.Adapter.CommentAdapter;
import com.deificindia.indifun.Model.retro.Commentmodel_Response;
import com.deificindia.indifun.R;
import com.deificindia.indifun.rest.AppConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.Sharedpref.getData;

public class CommentFragment extends Fragment {
    private RecyclerView commentlist;
    private CommentAdapter commentAdapter;
    private SwipeRefreshLayout commentswipe;
    private List<Commentmodel_Response> commentmodelList = new ArrayList<>();
    private static final  String URL = "https://deificindia.com/indifun/api/notification_comments";

    public  static CommentFragment getInstance(){
        CommentFragment commentFragment = new CommentFragment();
        return commentFragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saved){
        View view = inflater.inflate(R.layout.comment_fragment_layout, container,false);
        commentswipe = view.findViewById(R.id.commentswipe);
        commentlist = view.findViewById(R.id.commentlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        commentlist.setLayoutManager(linearLayoutManager);
        getdata();

        commentswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
//                getdata();
//                likeAdapter.notifyDataSetChanged();
                commentswipe.setRefreshing(false);
            }
        });
        return  view;
    }

    private void getdata()
    {
        String uid = getData(getContext(),"userId",null);
        Call<Commentmodel_Response> commentmodelList = AppConfig.loadInterface().getComments(uid);
        commentmodelList.enqueue(new Callback<Commentmodel_Response>() {
            @Override
            public void onResponse(Call<Commentmodel_Response> call, Response<Commentmodel_Response> response) {
                Commentmodel_Response List = response.body();
                //commentlist.setAdapter(new CommentAdapter(List.getResult(), getContext()));
                Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Commentmodel_Response> call, Throwable t) {
                Toast.makeText(getContext(), "error occcured", Toast.LENGTH_LONG).show();

            }
        });
    }
}
