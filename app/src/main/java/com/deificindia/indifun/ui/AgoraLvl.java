package com.deificindia.indifun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

public class AgoraLvl  extends RelativeLayout {

    RelativeLayout parentLay;
    ImageView img;
    TextView txt;

    public AgoraLvl(Context context) {
        super(context);
        init(context);
    }

    public AgoraLvl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AgoraLvl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_agora_lvl, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        parentLay = findViewById(R.id.parent);
        img = findViewById(R.id.img);
        txt = findViewById(R.id.tv);

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

    public void tooltips(String msg){
        ToolTipsManager mToolTipsManager = new ToolTipsManager();
        ToolTip.Builder builder = new ToolTip.Builder(getContext(), txt, parentLay, msg, ToolTip.POSITION_BELOW);
        mToolTipsManager.show(builder.build());
    }

}
