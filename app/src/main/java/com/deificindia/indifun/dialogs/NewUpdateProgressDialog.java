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

import com.deificindia.indifun.R;
import com.deificindia.indifun.anim.CircleReavealAnim;
import com.deificindia.indifun.anim.TranslateView;

public class NewUpdateProgressDialog  extends DialogFragment {

    View parentView;

    int cx, cy;

    public NewUpdateProgressDialog() {
        this.cx = TranslateView.getScreenWidth()/2;
        this.cy = TranslateView.getScreenHeight()/2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_update_progress, container, false);
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

    public static void opeDialog(FragmentManager manager){
        NewUpdateProgressDialog dialog = new NewUpdateProgressDialog();

        manager.beginTransaction()
                .add(dialog, "UserBlockListDialog")
                .commitAllowingStateLoss();
    }


}
