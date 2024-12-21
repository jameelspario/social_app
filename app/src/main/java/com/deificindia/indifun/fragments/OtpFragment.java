package com.deificindia.indifun.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.KeyClass;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.vm.LoginVm;
import com.deificindia.indifun.vm.modal.LoginVmModal;

import java.util.Locale;


public class OtpFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText otp1_et, otp2_et, otp3_et, otp4_et, otp5_et, otp6_et;
    private TextView txt_show_otp, phone_number;

    private RelativeLayout btn_countinue;

    String otp1, otp2, otp3, otp4, otp5, otp6;
    String verify_otp = "";
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private TextView otptime,txt_resend;
    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;

    LoginVm viewModel;

    public OtpFragment() {}

    public static OtpFragment newInstance(String param1, String param2) {
        OtpFragment fragment = new OtpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(LoginVm.class);
        bindView(view);
        viewSetup();
    }

    private void bindView(View v) {
        txt_show_otp = v.findViewById(R.id.txt_show_otp);
        phone_number = v.findViewById(R.id.phone_number);
        btn_countinue=v.findViewById(R.id.btn_countinue);
        otptime=v.findViewById(R.id.otptime);
        txt_resend=v.findViewById(R.id.txt_resend);
        otp1_et = v.findViewById(R.id.otp1_et);
        otp2_et = v.findViewById(R.id.otp2_et);
        otp3_et = v.findViewById(R.id.otp3_et);
        otp4_et = v.findViewById(R.id.otp4_et);
        otp5_et = v.findViewById(R.id.otp5_et);
        otp6_et = v.findViewById(R.id.otp6_et);
        txt_resend.setVisibility(View.GONE);
        otptime.setVisibility(View.VISIBLE);
        setTimer();
        startTimer();

        txt_resend.setOnClickListener(vv -> {

            //     myDialog = DialogUtils.showProgressDialog(OtpActivity.this, "Loading Please Wait...");
            //sendVerificationCode(str_mob);
            txt_resend.setVisibility(View.GONE);
            otptime.setVisibility(View.VISIBLE);
            setTimer();
            startTimer();
            //  myDialog.dismiss();
            viewModel.sendData(new LoginVmModal(5, ""));

        });


    }

    private void viewSetup() {
//        firebaseAuth = FirebaseAuth.getInstance();
        //result = intent.getExtras().getParcelable(KeyClass.PUT_DATA);
//        sendVerificationCode(PhoneNum);
        phone_number.setText(mParam1);

        otp1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp1_et.getText().toString().length() == 1)
                    otp2_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp1 = otp1_et.getText().toString();
                System.out.println("first=====otp==================" + otp1);
            }
        });
        otp2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp2_et.getText().toString().length() == 1)
                    otp3_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp2 = otp2_et.getText().toString();
                System.out.println("first=====otp==================" + otp2);
            }
        });
        otp3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp3_et.getText().toString().length() == 1)
                    otp4_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp3 = otp3_et.getText().toString();
                System.out.println("first=====otp==================" + otp3);
            }
        });
        otp4_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp4_et.getText().toString().length() == 1)
                    otp5_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp4 = otp4_et.getText().toString();
                System.out.println("first=====otp==================" + otp4);
            }
        });
        otp5_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp5_et.getText().toString().length() == 1)
                    otp6_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp5 = otp5_et.getText().toString();
                System.out.println("first=====otp==================" + otp5);
            }
        });
        otp6_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp6_et.getText().toString().length() == 1)
                    otp6_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp6 = otp6_et.getText().toString();
                System.out.println("first=====otp==================" + otp6);
            }
        });
        btn_countinue.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_countinue:
                verify_otp = otp1 + otp2 + otp3 + otp4 + otp5 + otp6;
                System.out.println("sb wali=================" + verify_otp);
                if (verify_otp.equalsIgnoreCase("") || verify_otp == null) {
                    Toast.makeText(getContext(), "Please Enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    //OTPVERIFY(str_uid, verify_otp);
                    viewModel.sendData(new LoginVmModal(4, verify_otp));
                }
                break;
        }
    }

    private void setTimer() {
        int time = 1;
        totalTimeCountInMilliseconds = 30 * time * 1000;

    }

    private void startTimer() {
        countDownTimer = new OtpCounter(totalTimeCountInMilliseconds, 500).start();
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void resumeTimer() {
        otptime.setVisibility(View.VISIBLE);
        //textViewShowTime.setVisibility(View.GONE);
        countDownTimer = new OtpCounter(totalTimeCountInMilliseconds, 500).start();
    }


    public class OtpCounter extends CountDownTimer {

        OtpCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long leftTimeInMilliseconds) {
            long seconds = leftTimeInMilliseconds / 1000;
            otptime.setText(String.format(Locale.getDefault(), "%02d", seconds / 60) + ":" + String.format(Locale.getDefault(), "%02d", seconds % 60));
        }

        @Override
        public void onFinish() {
            otptime.setVisibility(View.GONE);
            txt_resend.setVisibility(View.VISIBLE);
        }
    }

    public void clearFocus() {
        otp1_et.clearFocus();
        otp2_et.clearFocus();
        otp3_et.clearFocus();
        otp4_et.clearFocus();
        otp5_et.clearFocus();
        otp6_et.clearFocus();
    }


}