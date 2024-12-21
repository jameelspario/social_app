package com.deificindia.indifun.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;

public class TagView extends RelativeLayout {

    RelativeLayout parentLay;
    ImageView img;
    TextView txt;

    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_tag, this, true);

        parentLay = v.findViewById(R.id.parent);
        img = v.findViewById(R.id.img);
        txt = v.findViewById(R.id.tv);
    }

    public void init2(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_tag2, this, true);

        parentLay = v.findViewById(R.id.parent);
        img = v.findViewById(R.id.img);
        txt = v.findViewById(R.id.tv);
    }


    public View getParent1(){
        return parentLay;
    }
    public TextView getTagText(){
        return txt;
    }
    public ImageView getTagImage(){
        return img;
    }

    public void parentPadding(int l, int t, int r, int b){
        parentLay.setPadding(l,t,r,b);
    }
    public void setTagImage(int img1/*R.drawable.id*/){
        img.setImageResource(img1);
    }
    public void setTint(int col){
        //img.setBackgroundTintMode(Mode);
        img.setColorFilter(ContextCompat.getColor(getContext(), col),
                android.graphics.PorterDuff.Mode.SRC_IN);

    }


    public void setupViewData(int background/*R.drawable.id*/, int tag/*R.drawawble.id*/, String text){
        parentLay.setBackground(ContextCompat.getDrawable(getContext(), background));
        img.setImageDrawable(ContextCompat.getDrawable(getContext(), tag));
        txt.setText(text);
    }

    public void changeBg(int background/*R.drawable.id*/){
        parentLay.setBackground(ContextCompat.getDrawable(getContext(), background));
    }


}
