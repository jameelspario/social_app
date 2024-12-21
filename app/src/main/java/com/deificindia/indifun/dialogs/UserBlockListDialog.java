package com.deificindia.indifun.dialogs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.Adapter.BlockListAdapter;
import com.deificindia.indifun.Model.abs_plugs.BlockList;
import com.deificindia.indifun.Model.retro.UserFriendsResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.anim.CircleReavealAnim;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.LoadingAnimView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserBlockListDialog extends DialogFragment {

    View parentView;
    RecyclerView recyclerView;
    BlockListAdapter adapter;
    LoadingAnimView loadingAnimView;

    int cx, cy;

    public UserBlockListDialog() {}

    public UserBlockListDialog(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_user_block_list, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setOnShowListener(dialogInterface->{
            CircleReavealAnim.circleRevealDialog(parentView, cx, cy, true, null);
        });

        view.findViewById(R.id.imgClose).setOnClickListener(v->{
            CircleReavealAnim.circleRevealDialog(parentView, cx, cy, false, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dismissAllowingStateLoss();
                }
            });

        });

        getDialog().setOnKeyListener((dialogInterface, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK){
                CircleReavealAnim.circleRevealDialog(parentView, cx, cy, false, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        dismissAllowingStateLoss();
                    }
                });
                return true;
            }

            return false;
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new BlockListAdapter();

        recyclerView.setAdapter(adapter);

        loadingAnimView = view.findViewById(R.id.lottieanim);

        blocklist();
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {

            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();

        } catch (IllegalStateException e) {
            Log.d("UserBlockListDialog", "Exception", e);
        }
    }

    public static void opeDialog(FragmentManager manager, int x, int y){
        UserBlockListDialog dialog = new UserBlockListDialog(x,y);

        manager.beginTransaction()
                .add(dialog, "UserBlockListDialog")
                .commitAllowingStateLoss();
    }

    void blocklist(){
        loadingAnimView.startloading();
        RetroCalls.instance().withUID().callType(24)
                .setOnFail((a,b,c)->{
                    loadingAnimView.stopAnim();
                    adapter.updateData(new ArrayList<>());
                })
                .blocklist((a,b)->{
                    loadingAnimView.stopAnim();
                    if(b!=null && b.getStatus()==1 && b.getResult()!=null){
                        Set<BlockList> set = new HashSet<>(b.getResult());
                        adapter.updateData(new ArrayList<BlockList>(set));
                    }else {
                        loadingAnimView.showError();
                        loadingAnimView.setErrText("No Blocked Users");
                        adapter.updateData(new ArrayList<>());
                    }
                });
    }


}
