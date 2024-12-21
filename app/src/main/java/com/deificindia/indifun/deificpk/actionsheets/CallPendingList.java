package com.deificindia.indifun.deificpk.actionsheets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.HandlerThread;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.db.LiveRepo;
import com.deificindia.indifun.deificpk.frags.RemoteTrack;
import com.deificindia.indifun.deificpk.utils.Const;
import com.deificindia.indifun.fires.FireConst;
import com.deificindia.indifun.fires.LiveFireFun;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.deificpk.frags.RemoteTrack.CALLACCEPTED;
import static com.deificindia.indifun.deificpk.frags.RemoteTrack.CALLING;
import static com.deificindia.indifun.deificpk.frags.RemoteTrack.CALLREJECTED;
import static com.deificindia.indifun.deificpk.utils.Const.loge;

public class CallPendingList  extends AbstractActionSheet {

    boolean isowner;
    String ownerfuid;
    String roomname;
    RecyclerView recyclerView;
    TextView button1;
    CallPendingAdapter mAdapter;

    ChildEventListener valueEventListener;
    DatabaseReference databaseReference;

    EntityCall track;
    boolean hasongoingCall;

    private static final String TAG = CallPendingList.class.getSimpleName();

    private WeakReference<LoadingAnimView> loadingAnimView;
    OnCallPendingListListener _listener;

    public CallPendingList(Context context, boolean isowner, String ownerfuid, EntityCall track, String roomname) {
        super(context);
        this.isowner = isowner;
        this.ownerfuid = ownerfuid;
        this.track = track;
        this.roomname = roomname;
        init();

    }

    @Override
    public void setActionSheetListener(AbsActionSheetListener listener) {
        if (listener instanceof OnCallPendingListListener) {
            _listener = (OnCallPendingListListener) listener;
        }
    }

    public interface OnCallPendingListListener extends AbsActionSheetListener {
        void onActionSheetButtonClicked(int what, EntityCall track);
    }

    private void trigger(int what){
        if (_listener != null) _listener.onActionSheetButtonClicked(what, track);
    }

    List<EntityCall> calleeList = new ArrayList<>();

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.action_room_all_user_list, this, true);
        LoadingAnimView loading = findViewById(R.id.loadinganim);
        loadingAnimView = new WeakReference<>(loading);
        button1 = findViewById(R.id.button1);
        AppCompatTextView heading = findViewById(R.id.live_room_action_sheet_bg_music_title);
        heading.setText(getResources().getString(R.string.live_room_call_pending_list_action_sheet_title));

        if(!isowner /*&& call-not-sent*/) {

            FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                    .child(ownerfuid)
                    .child(FireConst.BROAD_CALLS)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("state").get().addOnCompleteListener(task -> {
                        loge("CAllPending", ""+task.getResult());

                        try {
                            if (task.getResult().getValue()!=null && task.getResult().getValue(Long.class) > 1) {
                                button1.setVisibility(GONE);
                            }else {
                                callSending();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            callSending();
                        }
                    });

                    /*.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            loge("CAllPending", new Gson().toJson(snapshot.getValue()));
                            if (task.getResult().getValue(Long.class) == 1) {

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/
        }

        recyclerView = findViewById(R.id.live_room_action_sheet_all_user_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new CallPendingAdapter();

        RecyclerView recyclerView = findViewById(R.id.live_room_action_sheet_all_user_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(5, EqualSpacingItemDecoration.VERTICAL));
        //mAdapter = new LiveRoomUserListActionSheet.RoomUserAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastItemPosition = recyclerView.getChildAdapterPosition(
                            recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                    if (lastItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                        //requestMoreAudience();
                    }
                }
            }
        });

        loadingAnimView.get().startloading();
       /* HandlerThread.executor.execute(()->{
            List<EntityCall> list = LiveRepo.getFilteredCall(getContext(), roomname, track.index);
            for(EntityCall ent:list){
                if(ent.jfuid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                }
            }
            HandlerThread.mHandler.post(()->{
                loadingAnimView.get().stopAnim();
                mAdapter.updateAdapter(list);
            });
        });*/

        databaseReference =  FirebaseDatabase.getInstance()
                .getReference(FireConst.COLL_GO)
                .child(ownerfuid)
                .child(FireConst.BROAD_CALLS);

        Query query = databaseReference.orderByChild("index").equalTo(track.index);

        valueEventListener = query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingAnimView.get().stopAnim();
                EntityCall call = snapshot.getValue(EntityCall.class);
                calleeList.add(call);

                mAdapter.insertAdapter(calleeList.size()-1, call);
                ifHasOngoingCall();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingAnimView.get().stopAnim();
                EntityCall call = snapshot.getValue(EntityCall.class);
                for(int i=0; i<calleeList.size(); i++){
                    if(calleeList.get(i).jfuid.equals(call.jfuid)){

                        mAdapter.notifyItemChanged(i, calleeList.get(i));
                        ifHasOngoingCall();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                EntityCall call = snapshot.getValue(EntityCall.class);
                for(int i=0; i<calleeList.size(); i++){
                    if(calleeList.get(i).jfuid.equals(call.jfuid)){
                        calleeList.remove(call);

                        mAdapter.notifyItemRemoved(i);
                        ifHasOngoingCall();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void ifHasOngoingCall(){
        boolean hascall = false;
        for(int i=0; i<calleeList.size(); i++){
            if(calleeList.get(i).state==CALLACCEPTED){
                hascall = true;
                break;
            }
        }

        if(hasongoingCall!=hascall){
            hasongoingCall = hascall;
            mAdapter.notifyDataSetChanged();
        }

    }

    void callSending(){
        button1.setVisibility(VISIBLE);
        button1.setOnClickListener(v -> {
            trigger(1);
        });
    }


    class CallPendingAdapter extends RecyclerView.Adapter<CallPendingHolder>{
        List<EntityCall> callList = new ArrayList<>();

        @NonNull
        @Override
        public CallPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CallPendingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_pending_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CallPendingHolder holder, int position) {
            holder.bind(callList.get(position));
        }

        @Override
        public int getItemCount() {
            return callList.size();
        }

        public void insertAdapter(int at, EntityCall item) {
            this.callList.add(item);
            notifyItemInserted(at);
        }

    }

    class CallPendingHolder extends RecyclerView.ViewHolder{

        ImageView avtar;
        TextView name, txt_message;
        LinearLayout tags;
        Button acceptButton, rejectButton;
        LottieAnimationView animationView;

        public CallPendingHolder(@NonNull View itemView) {
            super(itemView);

            avtar = itemView.findViewById(R.id.user_avtar);
            name = itemView.findViewById(R.id.user_name);
            tags = itemView.findViewById(R.id.user_tags);
            txt_message = itemView.findViewById(R.id.txt_message);
            acceptButton = itemView.findViewById(R.id.accept_button);
            rejectButton = itemView.findViewById(R.id.reject_button);
            animationView = itemView.findViewById(R.id.animation);
        }

        void bind(EntityCall entityCall){

            if(isowner) {
                acceptButton.setVisibility(VISIBLE);
                acceptButton.setText("Accept");
                rejectButton.setVisibility(VISIBLE);

                /*HandlerThread.executor.execute(()->{
                    int c = LiveRepo.countOngoingCalls(getContext(), roomname);
                    HandlerThread.mHandler.post(()->{
                        acceptButton.setEnabled(c < Const.callCount);
                    });
                });*/

                acceptButton.setEnabled(!hasongoingCall);

                acceptButton.setOnClickListener(v -> {

                    LiveFireFun.instance().acceptCall_or_rejectCall(entityCall.owner, entityCall.jfuid, CALLACCEPTED);

                    trigger(0);

                });

                rejectButton.setOnClickListener(v->{

                    LiveFireFun.instance().acceptCall_or_rejectCall(entityCall.owner, entityCall.jfuid, CALLREJECTED);

                    trigger(0);
                });
            } else {
                acceptButton.setVisibility(GONE);
                rejectButton.setVisibility(GONE);
            }

            if(!isowner && entityCall.jfuid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                //acceptButton.setVisibility(GONE);
                acceptButton.setText(R.string.end_call);
                acceptButton.setOnClickListener(v->{

                });
                rejectButton.setVisibility(GONE);
            }

            UiUtils.setAvtarRounded(entityCall.javtar, avtar);
            name.setText(entityCall.jname);

            if(entityCall.state == CALLACCEPTED || entityCall.state == CALLING){
                animationView.cancelAnimation();
                animationView.setVisibility(GONE);
            }else {
                animationView.cancelAnimation();
                animationView.setVisibility(GONE);
            }

            String status="";
            if(entityCall.state==1){
                status = "Calling...";
            }else if(entityCall.state==2){
                status  = "On call.";
            }else if(entityCall.state==3){
                status = "";
            }
            txt_message.setText(status);

        }
    }

}
