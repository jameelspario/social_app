package com.deificindia.indifun.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.ImagePickerActivity;


public class DialogUtils {

    public static AlertDialog createConnectDialog(EditText participantEditText,
                                                  DialogInterface.OnClickListener callParticipantsClickListener,
                                                  DialogInterface.OnClickListener cancelClickListener,
                                                  Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setIcon(R.drawable.ic_video_call_white_24dp);
        alertDialogBuilder.setTitle("Connect to a room");
        alertDialogBuilder.setPositiveButton("Connect", callParticipantsClickListener);
        alertDialogBuilder.setNegativeButton("Cancel", cancelClickListener);
        alertDialogBuilder.setCancelable(false);

        setRoomNameFieldInDialog(participantEditText, alertDialogBuilder);

        return alertDialogBuilder.create();
    }

    private static void setRoomNameFieldInDialog(EditText roomNameEditText,
                                                 AlertDialog.Builder alertDialogBuilder) {
        roomNameEditText.setHint("room name");
        alertDialogBuilder.setView(roomNameEditText);
    }

    public static Dialog showSelectLeaderBoardOptionDialog(Context context, int selected, final OnSelectionCahnge listener) {
        final int[] age_selected = {0};
        final Dialog dialog = new Dialog(context, R.style.live_room_dialog_center_in_window);
        dialog.setContentView(R.layout.dialog_select_leader_borad_option_dialog);

        AppCompatTextView negativeButton = dialog.findViewById(R.id.dialog_negative_button);
        negativeButton.setOnClickListener(v->{
            if(dialog!=null && dialog.isShowing()) dialog.dismiss();
        });

        AppCompatTextView positiveButton = dialog.findViewById(R.id.dialog_positive_button);
        positiveButton.setOnClickListener(v->{
            if(listener!=null) listener.onChange(dialog, pktime(age_selected[0]));
        });

        CheckedTextView btn3, btn5;
        btn3 = dialog.findViewById(R.id.btn3);
        btn5 = dialog.findViewById(R.id.btn5);

        changeSelectionOnLeaDER(context, selected, btn3, btn5);

        age_selected[0] = selected;

        btn3.setOnClickListener(v->{
            changeSelectionOnLeaDER(context, 0, btn3, btn5);

            age_selected[0] = 0;
        });

        btn5.setOnClickListener(v->{
            changeSelectionOnLeaDER(context, 1, btn3, btn5);

            age_selected[0] = 1;
        });

        dialog.show();
        return dialog;
    }

    public static Dialog showCallDialog(Context context, final OnPkTimeDialog listener) {
        final int[] selected = {0};

        final Dialog dialog = getDialog(R.layout.dialog_call_option, context);

        AppCompatTextView negativeButton = dialog.findViewById(R.id.dialog_negative_button);
        negativeButton.setOnClickListener(v->{
            if(dialog!=null && dialog.isShowing()) dialog.dismiss();
        });

        AppCompatTextView positiveButton = dialog.findViewById(R.id.dialog_positive_button);
        positiveButton.setOnClickListener(v->{
            if(listener!=null) listener.onSelected(dialog,selected[0]);
        });

        CheckedTextView btn3, btn5;
        btn3 = dialog.findViewById(R.id.btn3);
        btn5 = dialog.findViewById(R.id.btn5);

        btn3.setTextColor(context.getResources().getColor(R.color.golden_yellow));
        btn5.setTextColor(context.getResources().getColor(R.color.gray));

        btn3.setBackgroundTintList(context.getResources().getColorStateList(R.color.golden_yellow));
        btn5.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
        selected[0] = 0;

        btn3.setOnClickListener(v->{
            btn3.setTextColor(context.getResources().getColor(R.color.golden_yellow));
            btn5.setTextColor(context.getResources().getColor(R.color.gray));

            btn3.setTypeface(null, Typeface.BOLD);
            btn5.setTypeface(null, Typeface.NORMAL);

            btn3.setBackgroundTintList(context.getResources().getColorStateList(R.color.golden_yellow));
            btn5.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
            selected[0] = 0;
        });
        btn5.setOnClickListener(v->{
            btn3.setTextColor(context.getResources().getColor(R.color.gray));
            btn5.setTextColor(context.getResources().getColor(R.color.golden_yellow));
            btn3.setTypeface(null, Typeface.NORMAL);
            btn5.setTypeface(null, Typeface.BOLD);
            btn3.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
            btn5.setBackgroundTintList(context.getResources().getColorStateList(R.color.golden_yellow));
            selected[0] = 1;
        });


        dialog.show();
        return dialog;
    }


    private static int pktime(int n){
        switch (n){
            case 0:
                return 180;
            case 1:
                return 300;
            case 2:
                return 600;
        }

        return 180;
    }

    private static Dialog getDialog(int res, Context context){
        Dialog dialog = new Dialog(context, R.style.live_room_dialog_center_in_window);
        dialog.setContentView(res);
        return dialog;
    }
    public static Dialog showPkTimeDialog(Context context, final OnPkTimeDialog listener){
        final int[] age_selected = {0};

        final Dialog dialog = getDialog(R.layout.dialog_pk_time_chooser_dialog, context);

        AppCompatTextView negativeButton = dialog.findViewById(R.id.dialog_negative_button);
        negativeButton.setOnClickListener(v->{
            if(dialog.isShowing()) dialog.dismiss();
        });

        AppCompatTextView positiveButton = dialog.findViewById(R.id.dialog_positive_button);
        positiveButton.setOnClickListener(v->{
            if(listener!=null) listener.onSelected(dialog, pktime(age_selected[0]));
        });

        CheckedTextView btn3, btn5, btn10;
        btn3 = dialog.findViewById(R.id.btn3);
        btn5 = dialog.findViewById(R.id.btn5);
        btn10 = dialog.findViewById(R.id.btn10);

        btn3.setTextColor(context.getResources().getColor(R.color.golden_yellow));
        btn5.setTextColor(context.getResources().getColor(R.color.gray));
        btn10.setTextColor(context.getResources().getColor(R.color.gray));

        btn3.setBackgroundTintList(context.getResources().getColorStateList(R.color.golden_yellow));
        btn5.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
        btn10.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
        age_selected[0] = 0;

        btn3.setOnClickListener(v->{
            btn3.setTextColor(context.getResources().getColor(R.color.golden_yellow));
            btn5.setTextColor(context.getResources().getColor(R.color.gray));
            btn10.setTextColor(context.getResources().getColor(R.color.gray));

            btn3.setTypeface(null, Typeface.BOLD);
            btn5.setTypeface(null, Typeface.NORMAL);
            btn10.setTypeface(null, Typeface.NORMAL);

            btn3.setBackgroundTintList(context.getResources().getColorStateList(R.color.golden_yellow));
            btn5.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
            btn10.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
            age_selected[0] = 0;
        });
        btn5.setOnClickListener(v->{
            btn3.setTextColor(context.getResources().getColor(R.color.gray));
            btn5.setTextColor(context.getResources().getColor(R.color.golden_yellow));
            btn10.setTextColor(context.getResources().getColor(R.color.gray));

            btn3.setTypeface(null, Typeface.NORMAL);
            btn5.setTypeface(null, Typeface.BOLD);
            btn10.setTypeface(null, Typeface.NORMAL);

            btn3.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
            btn5.setBackgroundTintList(context.getResources().getColorStateList(R.color.golden_yellow));
            btn10.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
            age_selected[0] = 1;
        });
        btn10.setOnClickListener(v->{
            btn3.setTextColor(context.getResources().getColor(R.color.gray));
            btn5.setTextColor(context.getResources().getColor(R.color.gray));
            btn10.setTextColor(context.getResources().getColor(R.color.golden_yellow));

            btn3.setTypeface(null, Typeface.NORMAL);
            btn5.setTypeface(null, Typeface.NORMAL);
            btn10.setTypeface(null, Typeface.BOLD);

            btn3.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
            btn5.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
            btn10.setBackgroundTintList(context.getResources().getColorStateList(R.color.golden_yellow));
            age_selected[0] = 2;
        });
        /*Spinner spinner = dialog.findViewById(R.id.ageApinner);

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, pkTimers);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age_selected[0] = pktime(position);
                //saveIntData(context, SAVE_AGE1, position);
                ((TextView) spinner.getSelectedView()).setTextColor(context.getResources().getColor(R.color.black));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        dialog.show();
        return dialog;
    }

    public static Dialog infoDialog(Context context, String heading, String body, OnClickListener listener){
        final Dialog dialog = new Dialog(context, R.style.live_room_dialog_center_in_window);
        dialog.setContentView(R.layout.dialog_show_system_message);

        AppCompatTextView negativeButton = dialog.findViewById(R.id.dialog_gotit_button);
        negativeButton.setOnClickListener(v-> {
            if (listener != null) listener.onClick(dialog);
        });

        TextView tvHead = dialog.findViewById(R.id.txthead);
        TextView tvBody = dialog.findViewById(R.id.txtmessage);
        tvHead.setText(heading);
        tvBody.setText(body);

        dialog.show();
        return dialog;
    }

    private static void changeSelectionOnLeaDER(Context context, int i, CheckedTextView chk1, CheckedTextView chk2){
        if(i==1){
            chk1.setTextColor(context.getResources().getColor(R.color.gray));
            chk2.setTextColor(context.getResources().getColor(R.color.golden_yellow));
        }else {
            chk1.setTextColor(context.getResources().getColor(R.color.golden_yellow));
            chk2.setTextColor(context.getResources().getColor(R.color.gray));
        }
    }

    public interface OnPkTimeDialog{
        void onSelected(Dialog dialog, long time);
    }

    public interface OnSelectionCahnge{
        void onChange(Dialog dialog, int selection);
    }

    public interface OnClickListener{
        void onClick(Dialog dialog);
    }

    public static Dialog imageSelectorDialog(Context context, ImagePickerActivity.PickerOptionListener listener) {
        final Dialog dialog = new Dialog(context, R.style.live_room_dialog_center_in_window);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_selector_dialog);

        dialog.findViewById(R.id.btn1).setOnClickListener(v1->{
            if(listener!=null) listener.onTakeCameraSelected();
            dialog.dismiss();
        });
        dialog.findViewById(R.id.btn2).setOnClickListener(v2->{
            if(listener!=null) listener.onChooseGallerySelected();
            dialog.dismiss();
        });

       // dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();
        return dialog;
    }

}
