package com.deificindia.indifun.fires;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ItemKeyedDataSource;

import com.deificindia.indifun.deificpk.modals.BroadList;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.fires.FireFun.BROADCASTERS;

public class FirebaseRepository {

    private FirebaseFirestore firestoreDB;

    public FirebaseRepository(){
        firestoreDB = FirebaseFirestore.getInstance();
    }

    public void getAtpRanks(final int startRank, final int size,
                            @NonNull final ItemKeyedDataSource.LoadCallback<BroadList> callback){

        firestoreDB.collection(BROADCASTERS)
                .whereGreaterThan("rank", startRank)
                .limit(size)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.w("", "exception in fetching from firestore", e);
                    return;
                }
                List<BroadList> ranksList = new ArrayList<>();
                for(DocumentSnapshot doc : snapshots.getDocuments()){
                    ranksList.add(doc.toObject(BroadList.class));
                }

                if(ranksList.size() == 0){
                    return;
                }
                if(callback instanceof ItemKeyedDataSource.LoadInitialCallback){
                    //initial load
                    ((ItemKeyedDataSource.LoadInitialCallback)callback)
                            .onResult(ranksList, 0, ranksList.size());
                }else{
                    //next pages load
                    callback.onResult(ranksList);
                }
            }
        });
    }
}
