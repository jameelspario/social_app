package com.deificindia.indifun.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.DialogFragment;

import com.deificindia.chat.Model.User;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.deificpk.utils.WidgetUtils;
import com.deificindia.indifun.rest.RetroCallListener;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.EasyFlipView;
import com.deificindia.indifun.ui.luck.LuckyItem;
import com.deificindia.indifun.ui.luck.LuckyWheelView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.deificindia.indifun.Utility.Logger.loge;
import static tvi.webrtc.ContextUtils.getApplicationContext;

public class DailyCheckinDialog extends DialogFragment implements View.OnClickListener {

    int style = DialogFragment.STYLE_NO_TITLE;
    int theme = R.style.TransparentDialog;

    EasyFlipView easyFlipView;
    LuckyWheelView luckyWheelView;
    Button checkinbutton;
    LinearLayout linearLayout;
    String pointcollect, addpoint, localpointadd;
    int localpointaddl;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;

    List<LuckyItem> data = new ArrayList<>();

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.dialog_daily_checkin_flip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        easyFlipView = view.findViewById(R.id.flipView);
        luckyWheelView = easyFlipView.findViewById(R.id.luckyWheel1);
        setuserdetails();
        easyFlipView.mCardBackLayout.findViewById(R.id.imgClose).setOnClickListener(this);
        easyFlipView.mCardFrontLayout.findViewById(R.id.imgClose).setOnClickListener(this);
        checkinbutton = easyFlipView.mCardFrontLayout.findViewById(R.id.btnchecking);
        checkinbutton.setOnClickListener(v -> {
            startCheck();
        });

        linearLayout = easyFlipView.mCardBackLayout.findViewById(R.id.layouttags);

        easyFlipView.mCardBackLayout.findViewById(R.id.btnchecking)
                .setOnClickListener(v ->
                                updateProfile()
//                        dismiss()


                );
//        easyFlipView.mCardBackLayout.setOnClickListener(v -> {
//            dismiss();
//                    updateProfile();
//        });
        dailycheck();
    }

    void dailycheck() {
        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.topText = "50";
        luckyItem1.icon = R.drawable.d_diamond;
        luckyItem1.color = 0xffD6EAF8;//#D6EAF8
        data.add(luckyItem1);

        LuckyItem luckyItem21 = new LuckyItem();
        luckyItem21.topText = "2";
        luckyItem21.icon = R.drawable.d_bean;
        luckyItem21.color = 0xffFCF3CF;//#FCF3CF
        data.add(luckyItem21);

        LuckyItem luckyItem51 = new LuckyItem();
        luckyItem51.topText = "5";
        luckyItem51.icon = R.drawable.d_bronze;
        luckyItem51.color = 0xffFADBD8;//#FADBD8
        data.add(luckyItem51);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.topText = "2";
        luckyItem2.icon = R.drawable.d_bean;
        luckyItem2.color = 0xffD6EAF8;
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.topText = "20";
        luckyItem3.icon = R.drawable.d_silver;
        luckyItem3.color = 0xffFCF3CF;
        data.add(luckyItem3);

        //////////////////
        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.topText = "2";
        luckyItem4.icon = R.drawable.d_bean;
        luckyItem4.color = 0xffFADBD8;
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.topText = "5";
        luckyItem5.icon = R.drawable.d_bronze;
        luckyItem5.color = 0xffD6EAF8;
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.topText = "2";
        luckyItem6.icon = R.drawable.d_bean;
        luckyItem6.color = 0xffFCF3CF;
        data.add(luckyItem6);

        //////////////////////
        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.topText = "35";
        luckyItem7.icon = R.drawable.d_gold;
        luckyItem7.color = 0xffFADBD8;
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.topText = "2";
        luckyItem8.icon = R.drawable.d_bean;
        luckyItem8.color = 0xffD6EAF8;
        data.add(luckyItem8);

        LuckyItem luckyItem31 = new LuckyItem();
        luckyItem31.topText = "20";
        luckyItem31.icon = R.drawable.d_silver;
        luckyItem31.color = 0xffFCF3CF;
        data.add(luckyItem3);

        LuckyItem luckyItem81 = new LuckyItem();
        luckyItem81.topText = "2";
        luckyItem81.icon = R.drawable.d_bean;
        luckyItem81.color = 0xffFADBD8;
        data.add(luckyItem81);

        /////////////////////                                                                                                                   

        luckyWheelView.setData(data);
        luckyWheelView.setRound(5);

        luckyWheelView.setLuckyRoundItemSelectedListener(index -> {
            //  Toast.makeText(getContext(), data.get(index).topText, Toast.LENGTH_SHORT).show();
            checkinbutton.setEnabled(false);
            easyFlipView.flipTheView();

            setData(data.get(index).topText);
        });
    }


    void setData(String points){
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Updating...");

        RetroCalls.instance()
                .withUID()
                .intParam(Integer.parseInt(points))
                .data_after_checkin2((type_result, obj2) -> {
                    if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null){
                        myDialog.dismiss();
                        show_point(points.isEmpty()?0:Integer.parseInt(points));
                        return;
                    }
                    Toast.makeText(getContext(), "Failed to update Check yout internet.", Toast.LENGTH_SHORT).show();
                    myDialog.dismiss();
                });
    }

    void show_point(int point){
        WidgetUtils.setChip(getContext(), linearLayout,
                R.color.name_color_2,
                R.color.white,
                WidgetUtils.countToString(point),
                WidgetUtils.getUriToResource(getContext(), R.drawable.ic_xp));
        loge("point", String.valueOf(point));
        pointcollect=String.valueOf(point);

       localpointaddl= Integer.parseInt(addpoint)+Integer.parseInt(pointcollect);
        loge("point1", String.valueOf(localpointaddl));
    }

    private void updateProfile(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(firebaseUser.getUid());

        HashMap<String, Object> params = new HashMap<>();
        params.put("my_xp_collect",localpointaddl);
        reference.updateChildren(params);
        Toast.makeText(getContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();
        dismiss();
    }



    public void setuserdetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                addpoint= String.valueOf(user.my_xp_collect);
                loge("point", addpoint);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgClose:
                dismiss();
                break;
        }
    }

    void startCheck(){
        int index = getRandomIndex();
        luckyWheelView.startLuckyWheelWithTargetIndex(index);
    }

    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(data.size() - 1) + 0;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }

}
