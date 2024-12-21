package com.deificindia.indifun;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.deificindia.indifun.Model.abs_plugs.LevelData;
import com.deificindia.indifun.db.TempDao;
import com.deificindia.indifun.db.TempDb;
import com.deificindia.indifun.db.TempRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.deificindia.indifun", appContext.getPackageName());
    }

    private TempDao userDao;
    private TempDb db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TempDb.class).build();
        userDao = db.dao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        List<LevelData> obj = new ArrayList<>();
        obj.add(new LevelData(300, 0, 1));
        obj.add(new LevelData(600, 3, 2));
        obj.add(new LevelData(1200, 600, 3));

        userDao.insertLevelData(obj);

        Integer lvl = userDao.live_level(200);
        assertEquals(java.util.Optional.ofNullable(lvl), 1);
    }

    @Test
    public void testDb(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        TempRepo.calculate_level(appContext, 1000000, p->{
            Log.e("TAG", ""+p);
        });
    }



}