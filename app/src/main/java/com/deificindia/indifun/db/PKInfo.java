package com.deificindia.indifun.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

@Entity
public class PKInfo {

    @Exclude
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String pk_user_fuid;
    public String pk_user_wuid;
    public String pk_user_avtar;
    public long pk_user_avtar_type;
    public String pk_user_name;
    public long pk_broad_id;
    public long pkid;
    public long pk_dusration;

    public String pk_room_name;
    public String pk_room_id;

    @Ignore
    public long current_time;

    public PKInfo() { }

    public PKInfo(String pk_user_fuid, String pk_user_wuid,
                  String pk_user_avtar, long pk_user_avtar_type,
                  String pk_user_name, long pk_broad_id,
                  long pk_dusration, String pk_room_id,
                  String pk_room_name) {
        this.pk_user_fuid = pk_user_fuid;
        this.pk_user_wuid = pk_user_wuid;
        this.pk_user_avtar = pk_user_avtar;
        this.pk_user_avtar_type = pk_user_avtar_type;
        this.pk_user_name = pk_user_name;
        this.pk_broad_id = pk_broad_id;
        this.pk_dusration = pk_dusration;
        this.pk_room_name = pk_room_name;
        this.pk_room_id = pk_room_id;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pk_user_fuid", pk_user_fuid);
        result.put("pk_user_wuid", pk_user_wuid);
        result.put("pk_user_avtar", pk_user_avtar);
        result.put("pk_user_avtar_type", pk_user_avtar_type);
        result.put("pk_user_name", pk_user_name);
        result.put("pk_broad_id" ,pk_broad_id);
        result.put("pk_dusration" ,pk_dusration);
        result.put("pk_room_id" ,pk_room_id);
        result.put("pk_room_name" ,pk_room_name);

        return result;
    }




}
