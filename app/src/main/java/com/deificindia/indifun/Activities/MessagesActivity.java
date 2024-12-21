package com.deificindia.indifun.Activities;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deificindia.indifun.Adapter.ChatAdapter;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.anim.TranslateView;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.RetroCalls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesActivity extends FragmentActivity {

    private RecyclerView chatlist;
    private ChatAdapter likeAdapter;
    private SwipeRefreshLayout Chatswipe;
    private ImageView searchimage;
    private TextView txt_header_title, notitext, momenttext, recenttext, tv_message;
    private LinearLayout nLayout, mLayout, rLayout, mrLayout;

    private boolean showing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        searchimage=findViewById(R.id.searchimage);
        txt_header_title=findViewById(R.id.txt_header_title);
        nLayout = findViewById(R.id.nLayout);
        mLayout = findViewById(R.id.mLayout);
        notitext = findViewById(R.id.notitext);
        recenttext = findViewById(R.id.recenttext);
        momenttext = findViewById(R.id.momenttext);
        rLayout = findViewById(R.id.rLayout);
        mrLayout = findViewById(R.id.mrLayout);
        txt_header_title.setText("Messages");
        Chatswipe = findViewById(R.id.swiprefresh);
        chatlist = findViewById(R.id.recylerview);
        tv_message = findViewById(R.id.tv_message);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatlist.setLayoutManager(linearLayoutManager);
        chatlist.addItemDecoration(new EqualSpacingItemDecoration(5));
        getdata();

        Chatswipe.setOnRefreshListener(() -> {
            getdata();
            Chatswipe.setRefreshing(false);
        });

        nLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (mrLayout.getVisibility() != View.VISIBLE  ){
                    mrLayout.setVisibility(View.VISIBLE);
                }
                else{
                    mrLayout.setVisibility(View.GONE);
                }*/
                toggle();
            }
        });

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, MomentNotification.class));

            }
        });

        rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////
            }
        });

        notitext.setOnClickListener(v -> {
            /*if (mrLayout.getVisibility() != View.VISIBLE  ){
                mrLayout.setVisibility(View.VISIBLE);
            }
            else{
                mrLayout.setVisibility(View.GONE);
            }*/
            toggle();

        });

        momenttext.setOnClickListener(v -> startActivity(new Intent(MessagesActivity.this, MomentNotification.class)));

        searchimage.setOnClickListener(v -> startActivity(new Intent(MessagesActivity.this, SearchUserActivity.class)));

        findViewById(R.id.imgBack).setOnClickListener(v->{
            onBackPressed();
        });

        toggle();

    }

    private void getdata() {
        tv_message.setVisibility(View.GONE);
        RetroCalls.instance().withUID().getChats((a,b)->{
            if(b!=null && b.getStatus()==1 && b.getResult()!=null){
                chatlist.setAdapter(new ChatAdapter(b.getResult(), MessagesActivity.this));
            }else {
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText("No message...");
            }
        });
    }

    private void toggle() {
       /* Transition transition = new Fade();
        transition.setDuration(600);
        transition.addTarget(R.id.image);*/
        //TransitionManager.beginDelayedTransition(parent, transition);
        //image.setVisibility(show ? View.VISIBLE : View.GONE);

        nLayout.setEnabled(false);
        if(!showing) {
            mrLayout.setVisibility(View.VISIBLE);
            mrLayout.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            nLayout.setEnabled(true);
                            showing=true;
                        }
                    });
        }else {
            mrLayout.animate()
                    .translationY(-mrLayout.getHeight())
                    .alpha(0.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mrLayout.setVisibility(View.GONE);
                            nLayout.setEnabled(true);
                            showing=false;
                        }
                    });
        }

        //showing=!showing;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}