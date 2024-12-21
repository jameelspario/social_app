package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.deificindia.indifun.R;
import com.deificindia.indifun.vm.LoginVm;
import com.deificindia.indifun.vm.modal.LoginVmModal;
import com.hbb20.CountryCodePicker;

public class PhoneFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    LoginVm viewModel;

    private EditText edt_phone_number;
    private RelativeLayout btn_continue;
    private CountryCodePicker flaglayout;

    public PhoneFragment() {}


    public static PhoneFragment newInstance(String param1, String param2) {
        PhoneFragment fragment = new PhoneFragment();
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
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginVm.class);

        edt_phone_number =  view.findViewById(R.id.edt_phone_number);
        btn_continue =  view.findViewById(R.id.btn_continue);
        flaglayout =  view.findViewById(R.id.ccp1);
        btn_continue.setOnClickListener(this);
        flaglayout.registerCarrierNumberEditText(edt_phone_number);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                loginWithPhone();
                /*loge("Login",  flaglayout.getFullNumberWithPlus(), // +917859
                        flaglayout.getDefaultCountryNameCode(), // IN
                        flaglayout.getFullNumber()); // 917859 |*/
                break;
        }
    }

    public void loginWithPhone(){
        String mobile = flaglayout.getFullNumberWithPlus();
        String mobileraw = edt_phone_number.getText().toString().trim();
        if (!mobileraw.isEmpty()) {
            //loginperform(mobile);
            //startLogin(mobile);
//            Intent otvvarification= new Intent(LoginActivity.this,OtpActivity.class);
//            otvvarification.putExtra(KeyClass.PHONE_NUM,mobile);
//            startActivity(otvvarification);
            viewModel.sendData(new LoginVmModal(3, mobile));
        } else {
            edt_phone_number.setError("Enter a valid mobile number");
            edt_phone_number.setText("");
            edt_phone_number.requestFocus();
        }
    }


}