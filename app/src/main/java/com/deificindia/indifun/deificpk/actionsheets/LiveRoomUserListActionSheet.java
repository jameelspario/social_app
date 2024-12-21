package com.deificindia.indifun.deificpk.actionsheets;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.chat.Notifications.Data;
import com.deificindia.firebaseAdapter.ListJoinAdapter;
import com.deificindia.firebaseAdapter.RoomListAdapterFirebase;
import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.db.TempRepo;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;
import com.deificindia.indifun.fires.FireConst;
import com.deificindia.indifun.fires.m.UserJoinedInfo;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.deificindia.indifun.Utility.Logger.loge;

public class LiveRoomUserListActionSheet extends AbstractActionSheet {
    private static final String TAG = LiveRoomUserListActionSheet.class.getSimpleName();

    private List<UserJoinedInfo> modlwlist;

    private OnUserSelectedListener mOnUserSelectedListener;
    //private RoomUserAdapter mAdapter;
    private ListJoinAdapter madapter;

    //    private JoinerAdapterFir mAdapter;
    private WeakReference<LoadingAnimView> loadingAnimView;
    private String mRoomId;
    private String owner_uid;
    private boolean misOwner;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.action_room_all_user_list, this, true);
        LoadingAnimView loading = findViewById(R.id.loadinganim);
        loadingAnimView = new WeakReference<>(loading);
        AppCompatTextView heading = findViewById(R.id.live_room_action_sheet_bg_music_title);
        heading.setText(getResources().getString(R.string.live_room_all_online_user_action_sheet_title));

        RecyclerView recyclerView = findViewById(R.id.live_room_action_sheet_all_user_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(
//                getContext(), LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        modlwlist=new ArrayList<>();
        madapter = new ListJoinAdapter(getContext(),modlwlist);

        recyclerView.setAdapter(madapter);
        //showUsers();
        joineUserList();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastItemPosition = recyclerView.getChildAdapterPosition(
                            recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                    if (lastItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
//                        requestMoreAudience();
                    }
                }
            }
        });

        loadingAnimView.get().startloading();
    }


    public LiveRoomUserListActionSheet(Context context, Map<String, Object> map) {
        super(context);

        try {
            mRoomId = (String) map.get("room_id");
            owner_uid = (String) map.get("owner_uid");
            misOwner = map.get("is_owner") != null && (boolean) map.get("is_owner");
        }catch (Exception e){
            e.printStackTrace();
        }

        init();

    }

    private void joineUserList(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FireConst.USERS_JOINED);
        Query query = ref.orderByChild("room_id").equalTo(mRoomId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    modlwlist.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserJoinedInfo addLiveModel=snapshot.getValue(UserJoinedInfo.class);
                        modlwlist.add(addLiveModel);
                    }
                    loadingAnimView.get().stopAnim();
                    madapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public interface OnUserSelectedListener extends AbsActionSheetListener {
        void onActionSheetUserListItemSelected(UserJoinedInfo broad_id);
    }

    @Override
    public void setActionSheetListener(AbsActionSheetListener listener) {
        if (listener instanceof OnUserSelectedListener) {
            mOnUserSelectedListener = (OnUserSelectedListener) listener;
        }
    }


}
