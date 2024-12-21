package com.deificindia.indifun.vm;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deificindia.indifun.deificpk.modals.ChatModal;
import com.deificindia.indifun.deificpk.modals.PkInfo;
import com.deificindia.indifun.fires.FireConst;
import com.deificindia.indifun.modals.Post;
import com.deificindia.indifun.vm.modal.LoginVmModal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.deificpk.utils.Const.loge;
import static com.deificindia.indifun.fires.FireFun.BROADCASTERS;
import static com.deificindia.indifun.fires.FireFun.CHATS;

public class BroadVm  extends ViewModel {

    public static final String TAG = "BroadVm";
    public ListenerRegistration listenerRegistration;
    public ListenerRegistration chatListenerRegistration;


    public boolean userPkMode, userisPkSender;
    public String user_broad_room_id, user_pk_room_id;

    public boolean should_change_to_broad;
    public boolean is_pk_sender;
    public boolean  isOwner, isPkMode, isPkSender, onCall;
    public boolean ischangingroomforpkandviseversa;
    public String connectedToRoom = "";

    public String local_fuid;
    public String local_wuid;
    public int local_level;
    public long local_xp;

    public int owner_level;
    public long owner_xp;

    /*==intent data===*/
    public String broad_usr_fuid;
    public String broad_usr_wuid;
    public String broad_usr_name;
    public String broad_usr_avtar;
    public long is_following;
    public long broad_usr_avtar_type=0;
    public int broad_room_type = 1;
    public long broadcast_id;
    public long broad_pkid;

    public String pk_usr_uid;
    public String pk_usr_uid_web;
    public String pk_usr_name;
    public String pk_usr_avtar;
    public long pk_usr_avtar_type;
    public long pkbroadid;
    public long pkduration;

    public String broad_room_name;
    public String broad_room_id;
    public String broad_join_identity;

    public String pk_room_id;  //remote room id

    public int user_diamond=0;
    public long user_heart= 1;
    public long broadcast_level=0 ;
    public long broadcast_xp =0;
    public long golden_coins=0;

    public boolean is_muted;

    private final MutableLiveData<ChatModal> chatModal = new MutableLiveData<ChatModal>();
    private final MutableLiveData<DocumentChange> braodModal = new MutableLiveData<DocumentChange>();

    public int tabId = 3;
    public boolean playingRoomEnterAnim;
    public long state;

    private void sendChat(ChatModal item) { chatModal.setValue(item); }

    public LiveData<ChatModal> liveChatListener() { return chatModal; }

    DatabaseReference ref_chat;
    ChildEventListener chat_listentner;
    public void liveListenChat(String broad_usr_uid, String broad_room_id){
        loge(TAG, broad_room_id, broad_usr_uid);

        ref_chat = FirebaseDatabase.getInstance()
                .getReference(FireConst.COLL_GO)
                .child(broad_usr_uid)
                .child(FireConst.CHATS);

        //Query query = ref
                //.orderByChild("room").equalTo(broad_room_id)
                //.orderByChild("time");


        chat_listentner = ref_chat.addChildEventListener(new ChildEventListener() {
                  @Override
                  public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                      ChatModal chat = snapshot.getValue(ChatModal.class);
                      sendChat(chat);
                  }

                  @Override
                  public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                  }

                  @Override
                  public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                  }

                  @Override
                  public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });
    }

    public void removeListeners(){
        if(ref_chat!=null && chat_listentner!=null) {
            ref_chat.removeEventListener(chat_listentner);
            ref_chat = null;
            chat_listentner = null;
        }
    }




}
