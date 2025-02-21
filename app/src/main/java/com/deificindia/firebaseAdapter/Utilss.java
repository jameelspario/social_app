package com.deificindia.firebaseAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.chat.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Utilss {


    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static List<User> DataCache =new ArrayList<>();

    public static String searchString = "";



    public static void show(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static void openActivity(Context c,Class clazz){
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }

    /**
     * This method will allow us send a serialized scientist objec  to a specified
     *  activity
     */
    public static void sendScientistToActivity(Context c, User user,
                                               Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("SCIENTIST_KEY",user);
        c.startActivity(i);
    }

    /**
     * This method will allow us receive a serialized scientist, deserialize it and return it,.
     */
    public  static User receiveScientist(Intent intent, Context c){
        try {
            return (User) intent.getSerializableExtra("SCIENTIST_KEY");
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-SCIENTIST ERROR: "+e.getMessage());
        }
        return null;
    }

    public static void showProgressBar(ProgressBar pb){
        pb.setVisibility(View.VISIBLE);
    }
    public static void hideProgressBar(ProgressBar pb){
        pb.setVisibility(View.GONE);
    }

    public static DatabaseReference getDatabaseRefence() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static void search(final AppCompatActivity a, DatabaseReference db,
                              final ProgressBar pb,
                              NewStarAdapter adapter, String searchTerm) {
        if(searchTerm != null && searchTerm.length()>0){
            char[] letters=searchTerm.toCharArray();
            String firstLetter = String.valueOf(letters[0]).toUpperCase();
            String remainingLetters = searchTerm.substring(1);
            searchTerm=firstLetter+remainingLetters;
        }

        Utilss.showProgressBar(pb);

        Query firebaseSearchQuery = db.child("UserDetails").orderByChild("full_name").startAt(searchTerm)
                .endAt(searchTerm + "\uf8ff");


        firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataCache.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Scientist Objects and populate our arraylist.
                        User scientist = ds.getValue(User.class);
                        scientist.setKey(ds.getKey());
                        DataCache.add(scientist);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    Utilss.show(a,"No item found");
                }
                Utilss.hideProgressBar(pb);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE CRUD", databaseError.getMessage());
                Utilss.hideProgressBar(pb);
                Utilss.show(a,databaseError.getMessage());
            }
        });
    }

    public static void select(final AppCompatActivity a, DatabaseReference db,
                              final ProgressBar pb,
                              final RecyclerView recyclerView, NewStarAdapter adapter) {
        Utilss.showProgressBar(pb);

        db.child("UserDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataCache.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Scientist Objects and populate our arraylist.
                        User scientist = ds.getValue(User.class);
                        scientist.setKey(ds.getKey());
                        DataCache.add(scientist);
                    }

                    adapter.notifyDataSetChanged();
                }else {
                    Utilss.show(a,"No more item found");
                }
                Utilss.hideProgressBar(pb);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE CRUD", databaseError.getMessage());
                Utilss.hideProgressBar(pb);
                Utilss.show(a,databaseError.getMessage());
            }
        });
    }
    public static Boolean scientistExists(String key){
        for (User scientist: DataCache){
            if (scientist.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }


}
