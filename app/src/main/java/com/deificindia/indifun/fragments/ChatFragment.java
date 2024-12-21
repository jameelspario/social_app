package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deificindia.indifun.Adapter.ChatAdapter;

import com.deificindia.indifun.R;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {
    private RecyclerView chatlist;
    private ChatAdapter likeAdapter;
    private SwipeRefreshLayout Chatswipe;
    private static final  String URL = "https://deificindia.com/indifun/api/user_chatbox";
    private ImageView searchimage;
    private TextView txt_header_title, notitext, momenttext, recenttext;
    private LinearLayout nLayout, mLayout, rLayout, mrLayout;

//    public ChatFragment() {
//    }

    public  static ChatFragment getInstance(){
        ChatFragment chatFragment = new ChatFragment();
        return chatFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_chat, container, false);

        return v;
    }


}