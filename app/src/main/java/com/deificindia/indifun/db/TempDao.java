package com.deificindia.indifun.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.indifun.Model.abs_plugs.JoinerName;
import com.deificindia.indifun.Model.abs_plugs.LevelData;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;

import java.util.List;

@Dao
public interface TempDao {

    @Insert
    Long insert(RoomUserInfo obj);

    @Query("SELECT * FROM RoomUserInfo")
    List<RoomUserInfo> getAll();

    @Query("SELECT * FROM RoomUserInfo WHERE wuid = :wuid AND broadid=:broadid")
    List<RoomUserInfo> loadAllById(String wuid, int broadid);

    @Query("SELECT * FROM RoomUserInfo WHERE fuid = :fuid")
    RoomUserInfo exist(String fuid);

    @Query("SELECT * FROM RoomUserInfo WHERE fuid = :fuid AND online=:isonline AND enterplayed=:isplayedenter")
    RoomUserInfo getSingleUserInfo(String fuid, boolean isonline, boolean isplayedenter);

    @Query("SELECT * FROM RoomUserInfo WHERE online=:isonline")
    List<RoomUserInfo> getAllUserInfo(boolean isonline);


    @Query("SELECT * FROM RoomUserInfo WHERE wuid!=:wuid AND broadid=:broadid")
    LiveData<List<RoomUserInfo>> getTopUsers(String wuid, int broadid);

    @Query("UPDATE RoomUserInfo SET identity = :identity, roomname=:roomname, ownerfuid=:ownerfuid, online=:ison WHERE fuid=:uid")
    void updateFirstData(String uid, String identity,
                         String roomname, String ownerfuid,
                         boolean ison);

    @Query("UPDATE RoomUserInfo SET wuid=:wuid,avtar=:avtar,name=:name,points=:points,level=:level, broadid=:broadid WHERE fuid=:fuid")
    void updateApiData(String fuid, String wuid, String avtar, String name,long points,String level, String broadid);

    @Query("UPDATE RoomUserInfo SET online=:ison WHERE fuid=:fuid")
    void updateOnline(String fuid, boolean ison);

    @Query("UPDATE RoomUserInfo SET enterplayed=:ison WHERE fuid=:fuid")
    void updateEnterPlayed(String fuid, boolean ison);

    @Query("UPDATE RoomUserInfo SET points=:ison WHERE wuid=:wuid")
    void updatePoint(String wuid, long ison);

    @Query("SELECT COUNT(*) FROM RoomUserInfo WHERE ownerfuid=:owneruid AND roomname=:roomname AND online=:ison")
    int getCount(String owneruid, String roomname, boolean ison);

    @Query("SELECT * FROM RoomUserInfo WHERE ownerfuid=:owneruid AND roomname=:roomname AND online=:ison")
    LiveData<List<RoomUserInfo>> temp_join_user_only(String owneruid, String roomname, boolean ison);

    @Query("DELETE FROM RoomUserInfo")
    void clear();

    ///////////////////gift list////////////////////////////////////////////////////////////////
    @Insert
    void insertGift(GiftInfo2 obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllOrders(List<GiftInfo2> order);

    @Query("SELECT * FROM GiftInfo2 WHERE gift_category=:cat")
    List<GiftInfo2> getGiftByCat(int cat);

    @Query("DELETE FROM GiftInfo2")
    void clearGift();
    ///////////////gift sending queue///////////////////////
    @Insert
    Long insertGiftQueue(GiftQueue obj);

    //SELECT * FROM tablename ORDER BY column DESC LIMIT 1;
    @Query("SELECT * FROM GiftQueue WHERE isused = :identity ORDER BY pid DESC LIMIT 1")
    GiftQueue exist_gift_queue(int identity);

    @Query("UPDATE GiftQueue SET isused=:isused WHERE pid=:pid")
    void updateQueue_isused(int isused, int pid);

    @Query("DELETE FROM GiftQueue")
    void clear_gift_queue();

    //////////////joiner////name/////////
    @Query("SELECT * FROM JoinerName WHERE firebase_uid = :fuid")
    JoinerName joinerExist(String fuid);

    @Insert
    Long insertJoinerName(JoinerName obj);

    @Query("UPDATE JoinerName SET level =:level, diamond=:diamond,heart=:heart,broadcast_level=:broadcast_level, broadcast_point=:broadcast_point, my_xp=:my_xp, total_xp=:total_xp, to_next_level=:to_next_level, friends=:friends, add_broadcast_id=:add_broadcast_id, add_broadcast_title=:add_broadcast_title, broadcasting_type=:broadcasting_type,is_broadcasting=:is_broadcasting WHERE firebase_uid=:fuid")
    void updateJoinerName(String fuid, String level, String diamond, String heart, String broadcast_level, long broadcast_point, String my_xp, String total_xp,                      String to_next_level,         String friends, String add_broadcast_id,              String add_broadcast_title,              String broadcasting_type,        String is_broadcasting);

    @Query("UPDATE JoinerName SET diamond =:ll WHERE firebase_uid=:fuid")
    void joinerName_diamond(String fuid, String ll);

    @Query("UPDATE JoinerName SET heart =:ll WHERE firebase_uid=:fuid")
    void joinerName_heart(String fuid, String ll);

    @Query("UPDATE JoinerName SET broadcast_point=broadcast_point+:ll WHERE firebase_uid=:fuid")
    void joinerName_broadcaste_point(String fuid, long ll);

    @Query("UPDATE JoinerName SET my_xp =:ll WHERE firebase_uid=:fuid")
    void joinerName_xp(String fuid, String ll);

    @Query("SELECT * FROM JoinerName WHERE firebase_uid = :fuid")
    JoinerName get_JoinerName(String fuid);

    @Query("SELECT * FROM JoinerName WHERE firebase_uid=:owner_fuid")
    LiveData<JoinerName> get_JoinerName_live(String owner_fuid);

    ///////////////LevelData///////////////////////////
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLevelData(List<LevelData> obj);

    @Query("DELETE FROM LEVELDATA")
    void clearLevelData();

    //@Query("SELECT level FROM LevelData WHERE (max_point<:points) OR (min_point<:points AND max_point>=:points) LIMIT 1")
    @Query("SELECT level FROM LevelData WHERE (min_point<:points AND max_point>=:points) LIMIT 1")
    Integer live_level(long points);

    @Query("SELECT max(max_point) FROM LevelData LIMIT 1")
    long live_max();

    @Query("SELECT max(level) FROM LevelData LIMIT 1")
    long live_max_level();



}
