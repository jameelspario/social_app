package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deificindia.indifun.Adapter.ChatAdapter;
import com.deificindia.indifun.Adapter.MassegeAdapter;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs.StringModal;
import com.deificindia.indifun.Model.retro.MsgModel_Result;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.RetroCalls;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.ActivitiesUtils.FROMWUID;
import static com.deificindia.indifun.Utility.ActivitiesUtils.MESSENGER_ID;
import static com.deificindia.indifun.Utility.ActivitiesUtils.USERNAME;

public class ChatActivity extends AppCompatActivity {
    TextView chatUsername, senderDistance, senderTime;
    ImageView chatBackarrow, chatFollow, chatSetting, sendButton, micIcon,gifIcon, imageIcon, giftIcon, smileIcon, optionIcon, videoIcon, locationIcon;
    LinearLayout optionLayout;
    public LinearLayout typingLayout;
    TextInputEditText msgType;
    EmojiconEditText emojiconEditText, emojiconEditText2;
    EmojIconActions emojIcon;
    View rootView;
    String messengerid, user_wuid;
    RecyclerView chatrecycler;
    LinearLayoutManager linearLayoutManager;
    MassegeAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatUsername = findViewById(R.id.chatUsername);
        senderDistance = findViewById(R.id.senderDistance);
        senderTime = findViewById(R.id.senderTime);
        chatBackarrow = findViewById(R.id.chatBackarrow);
        chatFollow = findViewById(R.id.chatFollow);
        chatSetting =findViewById(R.id.chatSetting);
        sendButton = findViewById(R.id.sendButton);
        micIcon = findViewById(R.id.micIcon);
        gifIcon = findViewById(R.id.gifIcon);
        imageIcon = findViewById(R.id.imageIcon);
        giftIcon = findViewById(R.id.giftIcon);
        smileIcon = findViewById(R.id.smileIcon);
        optionIcon = findViewById(R.id.optionIcon);
        videoIcon = findViewById(R.id.videoIcon);
        locationIcon = findViewById(R.id.locationIcon);
        emojiconEditText = findViewById(R.id.emojicon_edit_text2);
        rootView = findViewById(R.id.parent_view);
        typingLayout = findViewById(R.id.linearLayout2);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        chatrecycler = findViewById(R.id.chatRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        chatrecycler.setLayoutManager(linearLayoutManager);
        chatrecycler.addItemDecoration(new EqualSpacingItemDecoration(5));

        Intent i = getIntent();
        messengerid = i.getStringExtra(MESSENGER_ID);
        user_wuid = i.getStringExtra(FROMWUID);
        String name  = i.getStringExtra(USERNAME);

        chatUsername.setText(name);

        String uid = MySharePref.getUserId();
        adapter =  new MassegeAdapter(new ArrayList<>(),ChatActivity.this, uid);
        chatrecycler.setAdapter(adapter);
        swipeRefreshLayout.post(this::getmessages);
        swipeRefreshLayout.setOnRefreshListener(()->{
            swipeRefreshLayout.setRefreshing(true);
            getmessages();
        });
        
        sendButton.setOnClickListener(v -> sendMsg());

        emojiconEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                disableBtn();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        chatBackarrow.setOnClickListener(v->{
            onBackPressed();
        });

        disableBtn();
    }

    void disableBtn(){
        sendButton.setEnabled(!emojiconEditText.getText().toString().trim().isEmpty());
    }

    private void sendMsg() {
        String msg = emojiconEditText.getText().toString();
        if(msg.isEmpty()) {
            Toast.makeText(this, "Type something", Toast.LENGTH_SHORT).show();
            return;
        }

        sendButton.setEnabled(false);
        swipeRefreshLayout.setRefreshing(true);
        RetroCalls.instance().withUID().stringParam(user_wuid, msg)
                .setOnFail((a,b,c)->{
                    //emojiconEditText.setText("");
                    Toast.makeText(this, "something went wrong, could not send message.", Toast.LENGTH_SHORT).show();
                    sendButton.setEnabled(true);
                    swipeRefreshLayout.setRefreshing(false);
                }).user_new_message((a,b)->{
                    if(b!=null && b.getStatus()==1){
                        MsgModel_Result modal = b.getResult();
                        modal.sender = 1;

                        adapter.update(modal);
                        emojiconEditText.setText("");
                        sendButton.setEnabled(true);
                        swipeRefreshLayout.setRefreshing(false);
                        linearLayoutManager.scrollToPosition(adapter.getItemCount() - 1);
                    }else {
                        sendButton.setEnabled(true);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    //int index=0;
    private void getmessages() {

        RetroCalls.instance().stringParam(messengerid)
                .setOnFail((a,b,c)->{
                    swipeRefreshLayout.setRefreshing(false);
                })
                .user_messages((a, b)->{
                    swipeRefreshLayout.setRefreshing(false);
                    if(b!=null && b.getStatus()==1 && b.getResult()!=null){
                        adapter.updateMessage(b.getResult());
                        linearLayoutManager.scrollToPosition(adapter.getItemCount() - 1);
                    }
        });
    }

    public void optionClick(){
        if ((optionLayout.getVisibility() == View.GONE)) {
            optionLayout.setVisibility(View.VISIBLE);
        } else {
            optionLayout.setVisibility(View.GONE);
        }
    }

    public void emojiClick(){

    }

    public void optionClick(View view) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

}