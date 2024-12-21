package com.deificindia.indifun.ui.multipost;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MultiPostImageView extends RecyclerView {

    List<String> items = new ArrayList<>();
    MultiPostAdapter multiPostAdapter;

    GridLayoutManager gridLayoutManager;

    public MultiPostImageView(Context context) {
        super(context);
    }

    public MultiPostImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiPostImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(List<String> items, OnItemClickListener<String> listener){
        this.items = items;

        multiPostAdapter = new MultiPostAdapter(items, listener);
        int span = 3;
        if(items.size()==1){
            span = 1;
        }else if(items.size()==2){
            span = 2;
        }

        gridLayoutManager = new GridLayoutManager(getContext(), span);
        setLayoutManager(gridLayoutManager);
        setHasFixedSize(true);

        setAdapter(multiPostAdapter);


    }




}
