package com.deificindia.indifun.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deificindia.indifun.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommentBottomSheetDialog extends BottomSheetDialogFragment implements TextView.OnEditorActionListener {

    OnMessageSent _listener;

    ImageView btnSend;
    EditText commentBox;
    boolean shouldFocus;

    private InputMethodManager inputMethodManager;

    public CommentBottomSheetDialog() { }

    public CommentBottomSheetDialog(boolean shouldFocus) {
        this.shouldFocus = shouldFocus;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.comment_bottom_sheet_layout, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        btnSend = view.findViewById(R.id.imgsend);
        commentBox = view.findViewById(R.id.et_comment);

        btnSend.setOnClickListener(v->{
            String msg = commentBox.getText().toString();
            if(!msg.isEmpty()){
                if (_listener!=null){
                    _listener.onSend(msg);
                }
                inputMethodManager.hideSoftInputFromWindow(commentBox.getWindowToken(), 0);
                commentBox.setText("");
                dismiss();
            }else {
                Toast.makeText(getContext(), "Type some message", Toast.LENGTH_SHORT).show();
            }
        });

        commentBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    btnSend.setImageResource(R.drawable.ic_send_active);
                }else {
                    btnSend.setImageResource(R.drawable.ic_send_inactive);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(shouldFocus){
            commentBox.requestFocus();
            commentBox.setOnEditorActionListener(this);
        }


    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String msg = commentBox.getText().toString();
            if (_listener!=null){
                _listener.onSend(msg);
            }
            inputMethodManager.hideSoftInputFromWindow(commentBox.getWindowToken(), 0);
            commentBox.setText("");
            dismiss();
            return true;
        }
        return false;
    }

    public interface OnMessageSent{
        void onSend(String message);

    }

    public void setOnMessageSent(OnMessageSent listener){
        this._listener = listener;
    }




}
