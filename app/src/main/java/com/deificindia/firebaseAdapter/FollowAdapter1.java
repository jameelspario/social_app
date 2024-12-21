package com.deificindia.firebaseAdapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Adapter.AbstractAdapter;

public class FollowAdapter1 extends RecyclerView.Adapter<FollowAdapter1.ImageViewModel> {

    @NonNull
    @Override
    public ImageViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewModel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ImageViewModel extends RecyclerView.ViewHolder {

        public ImageViewModel(@NonNull View itemView) {
            super(itemView);
        }
    }
}
