package com.deificindia.indifun.dialogs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.deificindia.firebaseModel.firebaseUserModel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.interfaces.Event;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.deificindia.indifun.Utility.KeyClass.SAVE_AGE1;
import static com.deificindia.indifun.Utility.KeyClass.SAVE_GENDER;
import static com.deificindia.indifun.Utility.MySharePref.getData;
import static com.deificindia.indifun.Utility.MySharePref.getIntData;
import static com.deificindia.indifun.Utility.MySharePref.saveData;
import static com.deificindia.indifun.Utility.MySharePref.saveIntData;

public class FilterBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    public static  List<String> dataset = new LinkedList<>(Arrays.asList("18-22", "23-26", "27-34", "All"));

    Spinner spinner;

    AppCompatButton img1, img2, img3;
    Button btnSubmit;

    String gender_old, gender_selected;
    int age_old, age_selected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_bottom_sheet_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.ageApinner);
        img1 = view.findViewById(R.id.imgAll);
        img2 = view.findViewById(R.id.imgMale);
        img3 = view.findViewById(R.id.imgFeMale);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
//        img2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // readUsers();
//            }
//        });

        img3.setOnClickListener(this);
        initspinner();

        gender_old = gender_selected = getData(getContext(), SAVE_GENDER,"All");
        age_old = age_selected = getIntData(getContext(), SAVE_AGE1, dataset.indexOf("All"));

        imgSelect(gender_selected);
        spinner.setSelection(age_selected);

    }

    void initspinner(){

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, dataset);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age_selected = position;
                saveIntData(getContext(), SAVE_AGE1, position);
                ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void imgSelect(String n){
        Drawable drawable1 =  ContextCompat.getDrawable(getContext(),R.drawable.bg_button_black_state_unselected);
        Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.bg_radio_button_state_pressed_2);
        switch (n){
            case "All":
                img1.setBackground(drawable1);
                img2.setBackground(drawable2);
                img3.setBackground(drawable2);
                saveData(getContext(), SAVE_GENDER, "All");
//                readUsers();
                break;
            case "Male":
                img1.setBackground(drawable2);
                img2.setBackground(drawable1);
                img3.setBackground(drawable2);
                saveData(getContext(), SAVE_GENDER, "Male");
                break;
            case "Female":
                img1.setBackground(drawable2);
                img2.setBackground(drawable2);
                img3.setBackground(drawable1);
                saveData(getContext(), SAVE_GENDER, "Female");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgAll:
                imgSelect("All");
                break;
            case R.id.imgMale:
                imgSelect("Male");
                break;
            case R.id.imgFeMale:
                imgSelect("Female");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        Event.trigger(!gender_old.equals(gender_selected) || age_old!=age_selected);
        super.onDestroyView();
    }
}
