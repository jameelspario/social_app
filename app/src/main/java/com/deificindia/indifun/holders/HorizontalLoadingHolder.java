package com.deificindia.indifun.holders;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;

public class HorizontalLoadingHolder extends RecyclerView.ViewHolder {

    public static final int VIEW_TYPE_LOADING = 10;


    ProgressBar progressBar;

    public HorizontalLoadingHolder(@NonNull View itemView) {
        super(itemView);

        progressBar = itemView.findViewById(R.id.progressbar);
    }

    public void bind(){

    }

}
