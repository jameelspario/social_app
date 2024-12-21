package com.deificindia.indifun.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.deificindia.indifun.Model.Userlevel_Response;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs_plugs.UserLevelModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Indifun;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.RetroCallListener;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.rest.RetroCallListener.USERLEVEL;


public class UserlevelFragment extends Fragment {
    TextView mainLevel,tonextlvl, MyEp, textView9, textView10;
    CircleImageView circleImageView;
    ProgressBar progressBar;
//    private List<Userlevel_Result> ulmodelList = new ArrayList<>();

    public  static UserlevelFragment getInstance(){
        UserlevelFragment userlevelFragment = new UserlevelFragment();
        return userlevelFragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saved){
        View view = inflater.inflate(R.layout.userlevel_layout, container,false);
        mainLevel = view.findViewById(R.id.mainLevel);
        tonextlvl = view.findViewById(R.id.tonextlvl);
        MyEp = view.findViewById(R.id.MyEp);

        textView9 = view.findViewById(R.id.textView9);
        textView10 = view.findViewById(R.id.textView10);

        circleImageView = view.findViewById(R.id.mainDp);
        progressBar = view.findViewById(R.id.progressBar2);

        UiUtils.setAvtar(IndifunApplication.getInstance().getCredential().getProfilePicture(), getContext(), circleImageView);


        getdata();
        return  view;
    }

    private void getdata() {
        /*String uid = MySharePref.getUserId();
        Call<Userlevel_Response> ulmodelList = AppConfig.loadInterface().getUserLevel(uid);
        ulmodelList.enqueue(new Callback<Userlevel_Response>() {
            @Override
            public void onResponse(Call<Userlevel_Response> call, Response<Userlevel_Response> response) {
                Userlevel_Response List = response.body();
                if(List!=null && List.getStatus()==1 && List.getResult()!=null) {
                    tonextlvl.setText(List.getResult().getToNextLevel());
                    MyEp.setText(List.getResult().getMyXp());
                }
//                likelist.setAdapter(new LikeAdapter(List.getResult(), getContext()));
                // Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Userlevel_Response> call, Throwable t) {
                // Toast.makeText(getContext(), "error occcured", Toast.LENGTH_LONG).show();

            }
        });
*/
        RetroCalls.instance().callType(USERLEVEL)
                .withUID()
                .setOnFail((type_result, code, message) -> { })
                .get_user_level((type_result, obj2) -> {
                    if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null) {
                        setData(obj2.getResult());
                    }
                });
    }

    void setData(UserLevelModal modal){
        mainLevel.setText("Lv. "+modal.getLevel1());
        tonextlvl.setText(""+modal.getToNextLevel());
        MyEp.setText(""+modal.getMyXp());

        //if(modal.getLevel1()!=null){
        textView9.setText("Lv. "+modal.getLevel1());

        int nextLevel = 0;
        if(modal.getLevel1()==10) nextLevel = modal.getLevel1();
        else nextLevel = modal.getLevel1()+1;

        textView10.setText("Lv. "+nextLevel);
            //textView10.setText("Lv. "+(!modal.getLevel1().isEmpty()? Integer.parseInt(modal.getLevel1())+1:0));
       // }


        progressBar.setMax(modal.getMyXp()+modal.getToNextLevel());
        progressBar.setProgress(modal.getMyXp());

    }


}
