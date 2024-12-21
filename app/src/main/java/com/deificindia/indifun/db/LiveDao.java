package com.deificindia.indifun.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.deificindia.indifun.deificpk.modals.RoomUserInfo;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LiveDao {

    @Query("SELECT * FROM LiveEntity")
    List<LiveEntity> getAll();
    @Query("SELECT COUNT(id) FROM EntityCall WHERE room=:room1 AND state=:state")
    int countOngoingCalls(String room1, long state);
    @Query("SELECT * FROM LiveEntity WHERE uid = :id")
    List<LiveEntity> loadAllById(int id);

    @Query("SELECT * FROM LiveEntity") // WHERE is_online=:i AND fuid!=:fuid
    LiveData<List<LiveEntity>> loadHot(/*int i, String fuid*/);

    @Query("SELECT * FROM LiveEntity WHERE is_online=:i AND is_friend=:f AND fuid!=:fuid")
    LiveData<List<LiveEntity>> loadFriend(int i, int f, String fuid);

    @Query("SELECT * FROM LiveEntity WHERE is_online=:i AND (matched=:f OR is_following=:isfollowing)  AND fuid!=:fuid")
    LiveData<List<LiveEntity>> loadRecommended(int i, int f, int isfollowing, String fuid);

    @Query("SELECT * FROM LiveEntity2 WHERE owneruid=:fuid AND is_online=:is_online")
    LiveData<LiveEntity2> getLiveUser(String fuid, int is_online);


    @Query("SELECT * FROM LiveEntity WHERE fuid = :fuid")
    LiveEntity exist(String fuid);

    @Query("SELECT * FROM LiveEntity")
    List<LiveEntity> getByIndex();

    @Query("SELECT * FROM LiveEntity WHERE is_online=:on AND is_blocked=:blocked AND is_kick=:kick AND is_mute=:mute AND fuid!=:fuid")
    List<LiveEntity> universal1(int on,int blocked, int kick, int mute, String fuid);

    @Query("SELECT * FROM LiveEntity WHERE is_friend=:f AND  is_online=:on AND is_blocked=:blocked AND is_kick=:kick AND is_mute=:mute  AND fuid!=:fuid")
    List<LiveEntity> loadFriend1(int f, int on, int blocked, int kick, int mute, String fuid);

    @Query("SELECT * FROM LiveEntity WHERE is_online=:on AND is_blocked=:blocked AND is_kick=:kick AND is_mute=:mute AND (matched=:f OR is_following=:isfollowing) AND fuid!=:fuid")
    List<LiveEntity> loadRecommended1(int f, int isfollowing, int on,  int blocked, int kick, int mute, String fuid);

    @Insert
    void insertAll(LiveEntity... users);

    @Insert
    Long insert(LiveEntity obj);


    @Update(onConflict = REPLACE)
    void update(LiveEntity obj);

    @Query("UPDATE LiveEntity SET roomname = :rname, " +
            "roomid=:roomid, " +
            "wuid=:wuid, " +
            "full_name=:full_name, " +
            "image=:image, " +
            "owneravtartype=:owneravtartype, " +
            "broadid=:broadid, " +
            "is_online=:is_online, " +
            "ispk=:ispk, " +
            "pksender=:pksender, " +
            "pkduration=:pkduration, " +
            "pkstarttime=:pkstarttime, " +
            "state=:state, " +
            "callcount=:callcount, " +
            "calltype=:calltype, " +
            "pkroomid=:pkroomid, " +
            "pkuseruid=:pkuseruid, " +
            "pkuseruidweb=:pkuseruidweb, " +
            "pkusername=:pkusername, " +
            "pkavtar=:pkavtar, " +
            "pkavtartype=:pkavtartype, " +
            "pkbroadid=:pkbroadid, " +
            "pkid=:pkid, " +
            "pkpkid=:pkpkid, " +
            "gender=:gender, " +
            "age=:age " +
            "WHERE fuid=:fuid")
    void update(String fuid,
                String rname,
                String roomid,
                String wuid,
                String full_name,
                String image,
                long owneravtartype,
                long broadid,
                String age,
                String gender,
                int is_online,
                int ispk,
                int pksender,
                long pkduration,
                long pkstarttime,
                long state,
                long callcount,
                String calltype,
                String pkroomid,
                String pkuseruid,
                String pkuseruidweb,
                String pkusername,
                String pkavtar,
                long pkavtartype,
                long pkbroadid,
                long pkid,
                long pkpkid
                );

    @Query("UPDATE LiveEntity SET is_friend = :is_friend, is_blocked=:is_blocked, is_mute=:is_mute, is_kick=:is_kick, is_following=:is_following, matched=:matched, is_online=:isOnline, " +
            "whatsapp=:whatsapp, diamond=:diamond, heart=:heart, level=:level, to_next_level=:to_next_level WHERE fuid=:uid")
    void updateApiData(String uid,
                int is_friend,
                int is_blocked,
                int is_mute,
                int is_kick,
                int is_following,
                int matched,
                int isOnline,
                String whatsapp,
                String diamond,
                String heart,
                String level,
                String to_next_level

    );

    @Query("UPDATE LiveEntity SET "+
            "ispk=:ispk, " +
            "pksender=:pksender, " +
            "pkduration=:pkduration, " +
            "pkstarttime=:pkstarttime, " +
            "state=:state, " +
            "callcount=:callcount, " +
            "calltype=:calltype, " +
            "pkroomid=:pkroomid, " +
            "pkuseruid=:pkuseruid, " +
            "pkuseruidweb=:pkuseruidweb, " +
            "pkusername=:pkusername, " +
            "pkavtar=:pkavtar, " +
            "pkavtartype=:pkavtartype, " +
            "pkbroadid=:pkbroadid, localpoint=:localp, remotepoint=:remotep, " +
            "pkid=:pkid, " +
            "pkpkid=:pkpkid " +
            "WHERE fuid=:fuid")
    void updateFire(String fuid,
                int ispk,
                int pksender,
                long pkduration,
                long pkstarttime,
                long state,
                long callcount,
                String calltype,
                String pkroomid,
                String pkuseruid,
                String pkuseruidweb,
                String pkusername,
                String pkavtar,
                long pkavtartype,
                long pkbroadid,
                long localp,
                long remotep,
                long pkid,
                long pkpkid
    );

    @Query("UPDATE LiveEntity SET is_online = :isonline WHERE fuid=:fuid")
    void updateOnline(String fuid, int isonline);

    @Query("UPDATE LiveEntity SET is_following = :isfollowing WHERE wuid=:wuid")
    void updateFollowing(String wuid, int isfollowing);


    @Query("UPDATE LiveEntity SET is_blocked =:state WHERE wuid=:wuid")
    void updateIsBlocked(String wuid, int state);

    @Query("UPDATE LiveEntity SET is_kick =:state WHERE wuid=:wuid")
    void updateIsKicked(String wuid, int state);

    @Query("UPDATE LiveEntity SET is_mute =:state WHERE wuid=:wuid")
    void updateIsMuted(String wuid, int state);

    @Query("UPDATE LiveEntity SET isconnected =:state WHERE wuid=:wuid")
    void connectionState(String wuid, int state);


    @Delete
    void delete(LiveEntity user);

    @Query("DELETE FROM LiveEntity")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM LiveEntity")
    int getCount();

    /*=======live entity 2======LiveEntity2===========*/
    @Insert
    Long insert_live(LiveEntity2 obj);

    @Update(onConflict = REPLACE)
    void update_live(LiveEntity2 obj);

    @Query("SELECT * FROM LiveEntity2 WHERE owneruid = :ownerfuid")
    LiveEntity2 exist_live(String ownerfuid);

    @Query("SELECT * FROM LiveEntity2") // WHERE is_online=:i AND fuid!=:fuid
    LiveData<List<LiveEntity2>> read_live();

    @Query("SELECT * FROM LiveEntity2 WHERE is_online=:i AND owneruid!=:fuid AND roomid IS NOT NULL") //
    LiveData<List<LiveEntity2>> loadHot_live(int i, String fuid);

    @Query("SELECT * FROM LiveEntity2 WHERE is_online=:i AND is_friend=:f AND owneruid!=:fuid")
    LiveData<List<LiveEntity2>> loadFriend_live(int i, int f, String fuid);

    //recomended broadcasters agency user - other user
    @Query("SELECT * FROM LiveEntity2 WHERE is_online=:i AND (matched=:f OR is_following=:isfollowing)  AND owneruid!=:fuid")
    LiveData<List<LiveEntity2>> loadRecommended_live(int i, int f, int isfollowing, String fuid);

    @Query("SELECT * FROM LiveEntity2 WHERE is_online=:on AND id=:id")
    LiveEntity2 universal2(int on,int id);

    @Query("SELECT * FROM LiveEntity2 WHERE is_online=:on AND owneruid=:uid")
    LiveEntity2 liveById(int on, String uid);

    @Query("SELECT * FROM LiveEntity2 WHERE state=:state AND owneruid!=:loacalfuid")
    List<LiveEntity2> pkusersResult(int state, String loacalfuid);

    @Query("SELECT COUNT(*) FROM LiveEntity2")
    int count_live();

    @Delete
    void delete_live(LiveEntity2 user);

    @Query("DELETE FROM LiveEntity2")
    void clear_live();

    @Query("DELETE FROM LiveEntity2 WHERE owneruid=:uid")
    void delete_single(String uid);

    @Query("UPDATE LiveEntity2 SET is_online =:n")
    void gooffline(int n);


   /* ==============Entity Calls==============*/
   @Query("SELECT * FROM EntityCall WHERE room=:room1")
   LiveData<List<EntityCall>> getAllCalls(String room1);

    @Query("SELECT * FROM EntityCall WHERE jfuid = :fuid")
    EntityCall call_exist(String fuid);

    @Query("SELECT * FROM EntityCall WHERE room=:room1 AND `index`=:indx")
    List<EntityCall> getSelectedCalls(String room1, long indx);

    @Insert
    Long call_insert(EntityCall obj);

    @Query("UPDATE EntityCall SET jwuid=:wuid, `index`=:index1, jname=:name, javtar=:avtar, jtime=:time, jxp=:xp, state=:state, owner=:owner, room=:room  WHERE jfuid=:fuid")
    void call_update(String fuid, String wuid, long index1, String name, String avtar, long time, long xp, long state, String owner, String room);
@Update
void call_update(EntityCall obj);
//    @Query("UPDATE EntityCall SET state = :isc WHERE jfuid=:fuid")
//    void call_state(String fuid, int isc);
    @Query("UPDATE EntityCall SET state = :isc WHERE jfuid=:fuid")
    void call_state(String fuid, long isc);

    @Query("UPDATE EntityCall SET isaudiomute = :isc WHERE jfuid=:fuid")
    void call_update_audio(String fuid, boolean isc);

    @Query("UPDATE EntityCall SET iscameramute = :isc WHERE jfuid=:fuid")
    void call_update_camera(String fuid, boolean isc);

    @Query("UPDATE EntityCall SET isbackcamera = :isc WHERE jfuid=:fuid")
    void call_update_camera_view(String fuid, boolean isc);

    @Query("DELETE FROM EntityCall")
    void clear_call();

    @Query("DELETE FROM EntityCall WHERE jfuid=:fuid")
    void delete_call(String fuid);
//
//    @Query("UPDATE EntityCall SET state = :isc WHERE jfuid=:fuid")
//    void call_state(String fuid, long isc);


    /*pk info => PKInfo*/

    @Update
    void pk_update(PKInfo obj);

    @Insert
    Long pk_insert(PKInfo obj);

    @Query("SELECT * FROM PKInfo WHERE pk_room_id = :pk_room_id")
    PKInfo pk_exist(String pk_room_id);

    @Query("DELETE FROM PKInfo")
    void pk_delete();

    @Query("DELETE FROM PKInfo WHERE pk_room_id=:pk_room_id")
    void pk_delete2(String pk_room_id);





}
