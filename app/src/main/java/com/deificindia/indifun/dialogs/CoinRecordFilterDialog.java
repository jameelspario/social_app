package com.deificindia.indifun.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CoinRecordFilterDialog  extends BottomSheetDialogFragment {

    TextView heading;
    RecyclerView recyclerView;
    Button btndone;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.coin_record_bottom_sheet_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        heading = view.findViewById(R.id.tvheader);
        btndone = view.findViewById(R.id.btndone);
        recyclerView = view.findViewById(R.id.recycler);

        btndone.setOnClickListener(v->{
            dismiss();
        });
    }
}
