package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.indifun.R;

public class RedeemFragment extends Fragment implements View.OnClickListener {


    public RedeemFragment() {
        // Required empty public constructor
    }


    int wallet_amount = 0;
    int converter = 1;

    TextView wallet,
    cash_coin_val,
    tChoosenAmount,
    tSubmit;

    ImageView imgPlus, imgMinus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_redeem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wallet = view.findViewById(R.id.cash_coin);
        cash_coin_val = view.findViewById(R.id.cash_coin_val);
        tChoosenAmount = view.findViewById(R.id.txt);
        tSubmit = view.findViewById(R.id.btn_submit);
        imgPlus = view.findViewById(R.id.img_plus);
        imgMinus = view.findViewById(R.id.img_minu);

        view.findViewById(R.id.img_back).setOnClickListener(this);
        tSubmit.setOnClickListener(this);
        imgPlus.setOnClickListener(this);
        imgMinus.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                Navigation.findNavController(v).popBackStack();
                break;
            case R.id.btn_submit: submit(); break;
            case R.id.img_plus: increment(); break;
            case R.id.img_minu: decrement(); break;

        }
    }

    void increment(){
        int pay_amount = Integer.parseInt(tChoosenAmount.getText().toString());
        int wallet_current = Integer.parseInt(wallet.getText().toString());

        if(pay_amount<wallet_current){
            tChoosenAmount.setText(pay_amount+1);
            int coin = pay_amount*converter;
            wallet.setText(wallet_current - 1);
            cash_coin_val.setText(String.valueOf(coin));

            enableSubmit();
        }

    }

    void decrement(){
        int pay_amount = Integer.parseInt(tChoosenAmount.getText().toString());
        int wallet_current = Integer.parseInt(wallet.getText().toString());

        if(pay_amount > 0){
            tChoosenAmount.setText(pay_amount-1);
            int coin = pay_amount*converter;
            wallet.setText(wallet_current + 1);
            cash_coin_val.setText(String.valueOf(coin));

            enableSubmit();
        }

    }

    void enableSubmit(){
        int pay_amount = Integer.parseInt(tChoosenAmount.getText().toString());
        tSubmit.setEnabled(pay_amount>0);
    }

    void submit(){
        int pay_amount = Integer.parseInt(tChoosenAmount.getText().toString());

    }


}