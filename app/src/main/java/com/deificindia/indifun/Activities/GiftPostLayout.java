package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.deificindia.indifun.Adapter.GiftPostAdapter;
import com.deificindia.indifun.R;
import com.deificindia.indifun.bindingmodals.otheruserprofile.Gift;
import com.deificindia.indifun.rest.AppConfig;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GiftPostLayout extends AppCompatActivity {
private GiftPostAdapter adapter;
private RecyclerView recyclerView;
private ImageView Back;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_post_layout);
        recyclerView=findViewById(R.id.recyclerview);
        Back=findViewById(R.id.back);
        Back.setOnClickListener(v->{
            Intent back=new Intent(getApplicationContext(),UserDetailsActivityActivity.class);
            startActivity(back);
        });
//        String [] data={};

        ArrayList<Gift> gifts=getIntent().getParcelableArrayListExtra("gift");
         System.out.println("sdfghj "   + new Gson().toJson(gifts));

        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new GiftPostAdapter(GiftPostLayout.this,gifts );
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

}