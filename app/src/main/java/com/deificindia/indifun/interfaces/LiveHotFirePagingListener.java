package com.deificindia.indifun.interfaces;

import com.google.firebase.firestore.FirebaseFirestoreException;

public interface LiveHotFirePagingListener {

    void onStateChange(FirebaseFirestoreException state);
}
