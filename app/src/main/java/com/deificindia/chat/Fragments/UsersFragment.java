package com.deificindia.chat.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.chat.Adapter.UserAdapter;
import com.deificindia.chat.Model.User;
import com.deificindia.indifun.Activities.ProfileActivity;
import com.deificindia.indifun.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UsersFragment extends Fragment {
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    EditText search_users;
ImageView back;
TextView userf,textnotfind;
    private List<String> followingList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        mUsers = new ArrayList<>();
        userAdapter=new UserAdapter(getContext(),mUsers,true);
        recyclerView.setAdapter(userAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
back=view.findViewById(R.id.back);
userf=view.findViewById(R.id.text);
userf.setText("Select User");
        textnotfind=view.findViewById(R.id.textnotfind);
        textnotfind.setVisibility(View.GONE);
//back.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        ChatsFragment fragment3=new ChatsFragment();
//        FragmentTransaction tra=getFragmentManager().beginTransaction();
//        tra.replace(R.id.cont,fragment3);
//        tra.commit();
//
//    }
//});
        checkFollowing();

        search_users = view.findViewById(R.id.search_users);
//        search_users.setVisibility(View.VISIBLE);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void searchUsers(String s) {

        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("search")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    assert user != null;
                    assert fuser != null;
                    if (!user.getId().equals(fuser.getUid())){
                        mUsers.add(user);
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers,true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkFollowing() {
        final ProgressDialog pg = new ProgressDialog(getContext());

        pg.setMessage("Please wait...");
        pg.setCancelable(true);
        pg.setIcon(R.mipmap.ic_launcher);
        pg.show();
        followingList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("UnFollow");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                followingList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }
                if (dataSnapshot.exists()){
                    readUsers();
                    pg.dismiss();
                }else{
                    final Dialog dialog=new Dialog(getActivity(),R.style.Theme_Design_NoActionBar);
//                    dialog.setContentView(R.layout.checkfollow);
//                    dialog.show();
//                    LinearLayout checkinternet=dialog.findViewById(R.id.checkinternet);
                    textnotfind.setVisibility(View.VISIBLE);
                    ImageView back=dialog.findViewById(R.id.back);
//                    checkinternet.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent bac=new Intent(getContext(), FollowerActivity.class);
//                            startActivity(bac);
//                        }
//                    });
                    recyclerView.setVisibility(View.GONE);
//                    back.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                   // startActivity(getContext(),DashBoard.class).;
//                            Intent bac=new Intent(getContext(), ProfileFragment.class);
//                            startActivity(bac);
//
//                        }
//                    });

                    //Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   mUsers.clear();
                   if(dataSnapshot.exists()) {

                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           User user = snapshot.getValue(User.class);

                           assert user != null;
                           if (user.getId().equals(firebaseUser.getUid())) {
//                               mUsers.add(user);
                               Log.d("find", firebaseUser.getUid());
                           } else {
                               for (String id : followingList) {
//                        assert post != null;
                                   if (user.getId().equals(id)) {
                                       mUsers.add(user);
                                   }
                               }
                           }
                       }
                   }else{
                       final Dialog dialog=new Dialog(getActivity(),R.style.Theme_Design_NoActionBar);
                     //  dialog.setContentView(R.layout.checkinternet);
                       dialog.show();
                       Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
                   }
                userAdapter.notifyDataSetChanged();
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
