package com.deificindia.indifun.fires;


import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.deificpk.modals.ChatModal;
import com.deificindia.indifun.interfaces.Firelistener;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.RetroCalls;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;

import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.deificindia.indifun.Utility.Logger.loge;

public class FireFun {

    public static final String BROADCASTERS = "broadcasters";
    public static final String RANDOMPKCOLL = "randompkcoll";
    public static final String CHATS = "chats";
    public static final String TAG = "broadcasters";

    public static void getBroacasterList(Firelistener.OnLiveBroad listener){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query colRef = FirebaseFirestore.getInstance()
                .collection(BROADCASTERS)
                //.whereNotEqualTo("owneruid", uid)
                .whereLessThanOrEqualTo("state",1)
                .whereEqualTo("ispk", false);

        colRef.get().addOnCompleteListener(task -> { //Task<QuerySnapshot>
            List<DocumentChange> qu = Objects.requireNonNull(task.getResult()).getDocumentChanges();
            if(qu!=null) {
                List<BroadList> broadlist = new ArrayList<>();
                for (DocumentChange doc : qu) {
                    BroadList obj = doc.getDocument().toObject(BroadList.class);
                    if(!obj.owneruid.equals(uid))
                        broadlist.add(obj);
                }

                if(listener!=null) listener.onLive(broadlist);
            }
        }).addOnFailureListener(e -> {

        });
    }



    //secondry owner setting ==> on invite send
    public static void inviteUserForPk(String remote_uid, String pkroom,
                                       String my_uid,String my_uid_web,
                                       String my_name, String my_avtar, long my_avtar_type, long pkbroadid,
                                       long pktime, int stat){

        Map<String, Object> map= new HashMap<>();
        map.put("pkroomid", pkroom);
        map.put("pkuseruid", my_uid);
        map.put("pkuseruidweb", my_uid_web);
        map.put("pkusername", my_name);
        map.put("pkavtar", my_avtar);
        map.put("pkavtartype", my_avtar_type);
        map.put("pkduration", pktime);
        //map.put("pkstarttime", pkstarttime);
        map.put("pkbroadid", pkbroadid);
        map.put("state", stat);

        FirebaseFirestore.getInstance().collection(BROADCASTERS)
                .document(remote_uid)
                .set(map, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {

                }).addOnFailureListener(e -> {

                });

    }

    //primary owner setting ==> on invite send
    public static void change_sender_state(String uid, String pkroom, boolean issender, long pktime, int stat){
        Map<String, Object> map= new HashMap<>();
        map.put("pksender", issender);
        map.put("pkroomid", pkroom);
        map.put("pkduration", pktime);
        //map.put("pkstarttime", pkstarttime);
        map.put("state", stat);

        FirebaseFirestore.getInstance().collection(BROADCASTERS)
                .document(uid)
                .set(map, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {

                }).addOnFailureListener(e -> {

        });
    }

    public static void resumetobroadstate(String loca_fuid, String remote_fuid, boolean ispk, boolean issender, int stat){
        loge(TAG, "", loca_fuid, remote_fuid, ""+ispk, ""+issender, ""+stat);
        Map<String, Object> map= new HashMap<>();
        map.put("pksender", issender);
        map.put("state", stat);
        map.put("ispk", ispk);

        updateMap2(loca_fuid, map);
        updateMap2(remote_fuid, map);
    }

    ///accepting pk request///
    public static void acceptInvitationSecOwner(String remote_uid, String pkroom,
                                                String my_uid, String my_uid_web, String my_name,
                                                String my_avtar, long my_avtar_type, long pkbroadid,
                                                boolean pksender, boolean ispk, int state){

        Map<String, Object> map= new HashMap<>();
        map.put("pkroomid", pkroom);
        map.put("pkuseruid", my_uid);
        map.put("pkuseruidweb", my_uid_web);
        map.put("pkusername", my_name);
        map.put("pkavtar", my_avtar);
        map.put("pkavtartype", my_avtar_type);
        map.put("pkbroadid", pkbroadid);

       /* Map<String, Object> map2 = new HashMap<>();
        map2.put("pkinfo", map);*/
        //map2.put("pksender", pksender);
        map.put("ispk", ispk);
        map.put("state", state);

        if(remote_uid==null) return;
        FirebaseFirestore.getInstance()
                .collection(BROADCASTERS)
                .document(remote_uid)
                .set(map, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    loge(TAG, "inviteUserForPk2",  "success");
                }).addOnFailureListener(e -> {
            loge(TAG, "inviteUserForPk2", "fail", e.getMessage());
        });

    }

    public static void rejectInvitationNotifyToPrimOwner(
            String remote_uid, String pkroom,
            String my_uid, String my_name,
            String my_avtar,
            long my_avtar_type,
            boolean pksender, boolean ispk, int stat){

        Map<String, Object> map= new HashMap<>();
        map.put("pkroomid", pkroom);
        map.put("pkuseruid", my_uid);
        map.put("pkusername", my_name);
        map.put("pkavtar", my_avtar);
        map.put("pkavtartype", my_avtar_type);

       /* Map<String, Object> map2 = new HashMap<>();
        map2.put("pkinfo", map);*/
        //map2.put("pksender", pksender);
        map.put("ispk", ispk);
        map.put("state", stat);
        if(remote_uid==null) return;
        FirebaseFirestore.getInstance()
                .collection(BROADCASTERS)
                .document(remote_uid)
                .set(map, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    loge(TAG, "inviteUserForPk2",  "success");
                }).addOnFailureListener(e -> {
            loge(TAG, "inviteUserForPk2", "fail", e.getMessage());
        });

    }

    public static void onRejectedBySecOwner(String uid, int state){
        Map<String, Object> map= new HashMap<>();
        map.put("pksender", false);
        map.put("ispk", false);
        map.put("state", state);

        updateMap2(uid, map);
    }
    //=>2 owner setting ==> act ownself
    //resume to broad mode
    public static void change_ispk(String uid, boolean ispk, int state){
        Map<String, Object> map= new HashMap<>();
        //map.put("pksender", false);
        map.put("ispk", ispk);
        map.put("state", state);

        FirebaseFirestore.getInstance()
                .collection(BROADCASTERS)
                .document(uid)
                .set(map, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {

                }).addOnFailureListener(e -> {

        });
    }

    public static void updateStartTime(String owner1, String owner2, long currTime){
        Map<String, Object> map= new HashMap<>();
        map.put("pkstarttime", currTime);
        updateMap2(owner1, map);
        updateMap2(owner2, map);
    }


    public static void insertMap(String coll, String doc, Map<String, Object> map){
        loge(TAG, "updateMap", doc);
        if(doc==null) return;
        new Thread(()->{
            FirebaseFirestore.getInstance()
                    .collection(coll)
                    .document(doc)
                    .set(map)
                    .addOnSuccessListener(aVoid -> {})
                    .addOnFailureListener(e -> {});
        }).start();
    }

    public static void updateMap2(String doc, Map<String, Object> map){
        if(doc==null) return;
        new Thread(()->
            FirebaseFirestore.getInstance()
                    .collection(BROADCASTERS)
                    .document(doc)
                    .set(map, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {})
                    .addOnFailureListener(e -> {}))
                .start();
    }
////////////////////////////////////////////////////////////////////////////

    public static void sendMessage(ChatModal chat
            /*
            String room,
            String ownerUid,
            long typ,
            String msg,
                       String senderuid,
                       String senderwuid,
                       String sendername,
                       String senderavtar,
                       long senderxp,
                       long giftid,
                       String giftcat,
                       String giftanim,
                        String giftname,
            long gifttyp,
            long giftpoint,
            long system,
            String tofuid,
            String towuid,
            String toname
            */){

        /*Map<String, Object> map= new HashMap<>();
        map.put("room", room);
        map.put("owner", ownerUid);
        map.put("time", System.currentTimeMillis());
        map.put("type", typ);
        map.put("message", msg);

        map.put("senderuid", senderuid);
        map.put("senderuidweb", senderwuid);
        map.put("sendername", sendername);
        map.put("senderavtar", senderavtar);
        map.put("senderxp", senderxp);

        map.put("giftid", giftid);
        map.put("giftcat", giftcat);
        map.put("giftanim", giftanim);
        map.put("giftname", giftname);
        map.put("gifttype", gifttyp);
        map.put("giftpoing", giftpoint);

        map.put("system", system);
        map.put("tofuid", tofuid);
        map.put("towuid", towuid);
        map.put("toname", toname);
*/
       /* FirebaseFirestore.getInstance()
                .collection(BROADCASTERS)
                .document(ownerUid)
                .collection(CHATS)
                .document()
                .set(map)
                .addOnSuccessListener(aVoid -> {
                    loge(TAG, "inviteUserForPk2",  "success");
                }).addOnFailureListener(e -> {
            loge(TAG, "inviteUserForPk2", "fail", e.getMessage());
        });
*/

       DatabaseReference ref = FirebaseDatabase.getInstance()
               .getReference(FireConst.COLL_GO)
                .child(chat.owner);

        String key = ref.child(FireConst.CHATS).push().getKey();
        Map<String, Object> map = LiveFireFun.getMap(FireConst.CHANGE_CHAT);
        map.put(FireConst.CHATS+"/"+key, chat);

        ref.updateChildren(map);

    }


    //acceptcall by user
    public static void acceptCall(String owneruid, String selfuid, long state){
        //Map<String, Object> update = new HashMap<>();
        //update.put("calls."+selfuid+".state", state);
        loge(TAG, ""+owneruid, ""+selfuid, ""+state);
        FieldPath path = FieldPath.of("calls", selfuid, "state");
        updateCall(owneruid, path, state);
    }

    public static void deleteCall(String owneruid, String selfuid){
        //Map<String, Object> update = new HashMap<>();
        //update.put("calls."+selfuid, FieldValue.delete());
        if(owneruid==null || selfuid==null) return;
        FieldPath path = FieldPath.of("calls", selfuid);

        FirebaseFirestore.getInstance()
                .collection(BROADCASTERS)
                .document(owneruid)
                .update(path, FieldValue.delete());
    }

    private static void updateCall(String todocs, FieldPath path, long val){
        if(todocs==null) return;
        FirebaseFirestore.getInstance()
                .collection(BROADCASTERS)
                .document(todocs)
                .update(path, val)
                .addOnSuccessListener(aVoid -> {}).addOnFailureListener(e -> {});
    }



}
