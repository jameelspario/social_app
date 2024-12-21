package com.deificindia.indifun.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.deificindia.indifun.Model.abs_plugs.UserBroadLevel;
import com.deificindia.indifun.Model.abs_plugs.UserLevelModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;

import static com.deificindia.indifun.rest.RetroCallListener.USERLEVEL;

public class BroadcasterLvFragment extends Fragment {

    TextView mainLevel,tonextlvl, MyEp, textView9, textView10;
    CircleImageView circleImageView;
    ProgressBar progressBar;

    public  static BroadcasterLvFragment getInstance(){
        BroadcasterLvFragment broadcasterLvFragment = new BroadcasterLvFragment();
        return broadcasterLvFragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saved){

        return inflater.inflate(R.layout.broadcasterlevel_layout, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainLevel = view.findViewById(R.id.mainLevel);
        tonextlvl = view.findViewById(R.id.tonextlvl);
        MyEp = view.findViewById(R.id.MyEp);

        textView9 = view.findViewById(R.id.textView9);
        textView10 = view.findViewById(R.id.textView10);

        circleImageView = view.findViewById(R.id.mainDp);
        progressBar = view.findViewById(R.id.progressBar2);

        UiUtils.setAvtar(IndifunApplication.getInstance().getCredential().getProfilePicture(), getContext(), circleImageView);


        getdata();
    }

    private void getdata() {
        RetroCalls.instance().callType(USERLEVEL)
                .withUID()
                .setOnFail((type_result, code, message) -> { })
                .get_user_broadcast_level((type_result, obj2) -> {
                    if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null) {
                        setData(obj2.getResult());
                    }
                });
    }

    void setData(UserBroadLevel modal){
        mainLevel.setText("Lv. "+modal.getLevel());
        tonextlvl.setText(""+modal.getTo_next_level());
        MyEp.setText(""+modal.getMy_broadcasting_xp());

        //if(modal.getLevel()!=null){
            textView9.setText("Lv. "+modal.getLevel());

            int nextLevel = 0;
            if(modal.getLevel()==10) nextLevel = modal.getLevel();
            else nextLevel = modal.getLevel()+1;

            textView10.setText("Lv. "+nextLevel);
        //}


        progressBar.setMax(modal.getMy_broadcasting_xp()+modal.getTo_next_level());

        progressBar.setProgress(modal.getMy_broadcasting_xp());

    }
}
