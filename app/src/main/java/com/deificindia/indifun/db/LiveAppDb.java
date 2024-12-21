package com.deificindia.indifun.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(Converters.class)
@Database(entities = {LiveEntity.class, EntityCall.class,
        LiveEntity2.class, PKInfo.class},
        version = 18)
public abstract class LiveAppDb extends RoomDatabase {
    public abstract LiveDao userDao();

    private static volatile LiveAppDb INSTANCE;
    private static final int NUMBER_OF_THREADS = 1;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final Handler handler = new Handler(Looper.getMainLooper());

    public static LiveAppDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LiveAppDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LiveAppDb.class, "user_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}