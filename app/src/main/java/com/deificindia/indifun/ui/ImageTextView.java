package com.deificindia.indifun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deificindia.indifun.R;

public class ImageTextView extends RelativeLayout {
    ImageView img;
    TextView text;

    public ImageTextView(Context context) {
        super(context);

        init();
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    void init(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_image_text_view, this, false);

        img = v.findViewById(R.id.img);
        text = v.findViewById(R.id.text);
    }

    public void setImg(int res){
        img.setImageResource(res);
    }

    public void setText(String txt){
        text.setText(txt);
    }


}
