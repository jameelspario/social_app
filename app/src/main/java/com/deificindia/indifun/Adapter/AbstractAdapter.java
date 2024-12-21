package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.holders.HorizontalLoadingHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.holders.HorizontalLoadingHolder.VIEW_TYPE_LOADING;
/*
 scrollListener=new PaginationScrollListener(layoutManager);
        scrollListener.setOnLoadMoreListener(() -> {
            if(!isLoadingMore && !reachedEnd){
                loadMore();
            }
        });

        rv_followe.addOnScrollListener(scrollListener);

        -------------------
void firstLoad(){
        index = 0;
        adapter.clearData();
        isLoadingMore = true;

        getFollow();

    }

    void loadMore(){
        index++;
        adapter.addLoadingView();
        isLoadingMore = true;
        getFollow();

    }

    void reachedEnd(){
        adapter.removeLoadingView();
        scrollListener.setLoaded();
        isLoadingMore = false;
    }
* */
public abstract class AbstractAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final List<T> items_list = new ArrayList<>();

    public static final int ITEM_TYPE_VIEW = 1;

    private boolean isLoading;
    private boolean reachedEnd;

    private int index = 0; //offset
    private int size = 5;

    private WeakReference<Context> _context;
    protected Context context(){
        return this._context.get();
    }

    public AbstractAdapter(Context _context) {
        this._context = new WeakReference<Context>(_context);
    }

    public void clearData(){
        items_list.clear();
        //notifyDataSetChanged();
    }

    public void addData(List<T> dataViews) {
        items_list.addAll(dataViews);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items_list.get(position) == null ? VIEW_TYPE_LOADING : ITEM_TYPE_VIEW;
    }

    @Override
    public int getItemCount() { return items_list == null ? 0 : items_list.size(); }

    public void addLoadingView() {
        //add loading item
        isLoading = true;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                items_list.add(null);
                notifyItemInserted(items_list.size() - 1);
            }
        });
    }

    public void removeLoadingView() {
        //Remove loading item
        try {
            if(isLoading) {
                items_list.remove(items_list.size() - 1);
                notifyItemRemoved(items_list.size());
                isLoading = false;
            }
        }catch (ArrayIndexOutOfBoundsException e){}
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean reachedEnd() {
        return reachedEnd;
    }

    public void reachedEnd(boolean reachedEnd) {
        this.reachedEnd = reachedEnd;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_VIEW:
                return createViewHolder(parent);
            case VIEW_TYPE_LOADING:
                return new HorizontalLoadingHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_loading, parent, false)
                );
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_VIEW:
                bindViewHolder2(holder, position);
                break;
            case VIEW_TYPE_LOADING:
                ((HorizontalLoadingHolder)holder).bind();
                break;
        }
    }

    protected abstract RecyclerView.ViewHolder createViewHolder(ViewGroup parent);
    protected abstract void bindViewHolder2(RecyclerView.ViewHolder holder, int position);



}
