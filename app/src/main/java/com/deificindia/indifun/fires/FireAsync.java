package com.deificindia.indifun.fires;

import static com.deificindia.indifun.Utility.Logger.loge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.deificindia.indifun.db.PKInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/*
*
DatabaseReference ref = ...: // Your ref goes here
boolean throwError = false; // if true, the code will throw the error occurred during data fetching, else if false, it will just skip it and return null
DataSnapshot result = FireAsync.getRealtimeValue(ref, throwError);
...
* */

public class FireAsync {

    private List<PKInfo> result = new ArrayList<>();
    private DatabaseReference dbr;
    private DatabaseException err;

    CountDownLatch done;

    private FireAsync(DatabaseReference dbr){
        this.dbr=dbr;
    }

    private List<PKInfo> fetch(boolean error){
        done = new CountDownLatch(1);

        result= new ArrayList<>();

        if(dbr!=null){
            dbr.addListenerForSingleValueEvent(new ValueEventListener(){

                @Override
                public void onDataChange(DataSnapshot data) {

                    for(DataSnapshot ds:data.getChildren()){
                        PKInfo info = ds.getValue(PKInfo.class);
                        result.add(info);
                    }

                    done.countDown();
                }

                @Override
                public void onCancelled(DatabaseError e) {
                    err=e.toException();
                    done.countDown();
                }
            });
        }else{
            done.countDown();
        }

        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if(error && err!=null){
            throw err;
        }

        return result;
    }

    @WorkerThread
    public static @Nullable
    List<PKInfo> getRealtimeValue(@NonNull DatabaseReference ref) {
        return getRealtimeValue(ref,false);
    }

    @WorkerThread
    public static @Nullable List<PKInfo> getRealtimeValue(@NonNull DatabaseReference ref, boolean throwError) throws DatabaseException
    {
        return new FireAsync(ref).fetch(throwError);
    }

}
