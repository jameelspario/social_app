package com.deificindia.indifun.db;

import androidx.room.TypeConverter;

import com.google.firebase.Timestamp;
import com.google.gson.Gson;

import static com.deificindia.indifun.Utility.Logger.toGson;

public class Converters {

    @TypeConverter
    public String fromTime(Timestamp t){
        return toGson(t);
    }

    @TypeConverter
    public Timestamp toTime(String l){
        return new Gson().fromJson(l, Timestamp.class);
    }


}
