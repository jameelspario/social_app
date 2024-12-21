package com.deificindia.indifun.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.indifun.Model.abs_plugs.JoinerName;
import com.deificindia.indifun.Model.abs_plugs.LevelData;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;
import com.google.common.base.Joiner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {RoomUserInfo.class, GiftInfo2.class, GiftQueue.class,
        JoinerName.class, LevelData.class}, version = 9)
public abstract class TempDb extends RoomDatabase {
    public abstract TempDao dao();

    private static volatile TempDb INSTANCE;
    private static final int NUMBER_OF_THREADS = 1;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TempDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TempDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TempDb.class, "temp_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}