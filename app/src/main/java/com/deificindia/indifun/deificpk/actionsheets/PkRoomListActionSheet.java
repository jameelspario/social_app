package com.deificindia.indifun.deificpk.actionsheets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.Utility.api;
import com.deificindia.indifun.db.LiveAppDb;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.db.LiveRepo2;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.deificpk.modals.RoomInfo;
import com.deificindia.indifun.deificpk.utils.UserUtil;
import com.deificindia.indifun.fires.FireFun;
import com.deificindia.indifun.interfaces.Firelistener;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import timber.log.Timber;


public class PkRoomListActionSheet extends AbstractActionSheet {
    private static final int ROOM_REQUEST_COUNT = 10;

    public interface OnPkRoomSelectedListener extends AbsActionSheetListener {
        void onPkRoomListActionSheetRoomSelected(int position, String roomId, String uid, long pkTime1);
        void onRandomPkClickListener(long pkTime1);
    }

    private OnPkRoomSelectedListener mListener;
    private PkRoomListAdapter mAdapter;
    private String mToken;
    private int mRoomType;
    private AppCompatTextView mTitle;
    private String mTitleFormat;

    private long pktime;

    @Override
    public void setActionSheetListener(AbsActionSheetListener listener) {
        if (listener instanceof OnPkRoomSelectedListener) {
            mListener = (OnPkRoomSelectedListener) listener;
        }
    }

    public PkRoomListActionSheet(Context context, Map<String, Object> map) {
        super(context);
        try {
            pktime = (map!=null && map.get("pktime")!=null)?(long) map.get("pktime"):0;
        }catch (Exception e){
            e.printStackTrace();
        }

        init();
    }

    private void init() {
        mTitleFormat = getResources().getString(R.string.live_room_pk_room_list_title_format);
        LayoutInflater.from(getContext()).inflate(R.layout.action_room_all_pk_room_list, this, true);
        mTitle = findViewById(R.id.live_room_action_sheet_pk_room_list_title);
        RecyclerView recyclerView = findViewById(R.id.live_room_action_sheet_pk_room_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new PkRoomListAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastItemPosition = recyclerView.getChildAdapterPosition(
                            recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                    if (lastItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                        //requestMorePkRoom();
                    }
                }
            }
        });

        findViewById(R.id.btnRnd).setOnClickListener(v->{
            if (mListener != null) {
                mListener.onRandomPkClickListener(pktime);
            }
        });

        requestMorePkRoom();
    }

    public void requestMorePkRoom() {

        LiveAppDb.databaseWriteExecutor.execute(()->{
            List<LiveEntity2> list = new LiveRepo2(getContext()).pkUsersResult();

            LiveAppDb.handler.post(()->{
                if(list.size()>0){
                    appendUsers(list);
                }
            });
        });
    }

    public void appendUsers(List<LiveEntity2> list) {
        mAdapter.append(list);
    }

    private class PkRoomListAdapter extends RecyclerView.Adapter {
        private List<LiveEntity2> mRoomList;

       void append(@NonNull List<LiveEntity2> list) {
            if (mRoomList == null) {
                mRoomList = list;
            }

            notifyDataSetChanged();
            String title = String.format(mTitleFormat, mRoomList.size());
            mTitle.setText(title);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PkRoomListViewHolder(LayoutInflater.
                    from(getContext()).inflate(R.layout.action_room_all_pk_room_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            if (mRoomList == null) {
                return;
            }

            LiveEntity2 info = mRoomList.get(position);
            PkRoomListViewHolder viewHolder = (PkRoomListViewHolder) holder;
            viewHolder.name.setText(info.ownername);
            Picasso.get().load(info.owneravtar).placeholder(R.drawable.ic_placeholder).into(viewHolder.icon);
            Timber.tag("findpk name").d(info.owneravtar);
//
//            if(info.owneravtar==null){
//                viewHolder.icon.setImageDrawable(UserUtil.getUserRoundIcon(getResources()));
//            }else {
//                UiUtils.setAvtarRounded(info.owneravtar, getContext(), viewHolder.icon);
//                Log.d("findpk image",info.owneravtar);
//            }

            viewHolder.pkButton.setOnClickListener(view -> {
                if (mListener != null) {
                    mListener.onPkRoomListActionSheetRoomSelected(position, info.roomid, info.owneruid, pktime);
                }
            });
        }

        @Override
        public int getItemCount() {
            return (mRoomList == null) ? 0 : mRoomList.size();
        }

        String getLastRoomId() {
            if (mRoomList == null) {
                return null;
            } else if (mRoomList.size() == 0) {
                return null;
            } else {
                int size = mRoomList.size();
                return mRoomList.get(size - 1).roomid;
            }
        }
    }

    private class PkRoomListViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView icon;
        AppCompatTextView name;
        AppCompatTextView pkButton;

        PkRoomListViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.live_room_action_sheet_pk_room_list_item_icon);
            name = itemView.findViewById(R.id.live_room_action_sheet_pk_room_list_item_name);
            pkButton = itemView.findViewById(R.id.live_room_action_sheet_pk_room_list_item_invite_btn);
        }
    }
}
