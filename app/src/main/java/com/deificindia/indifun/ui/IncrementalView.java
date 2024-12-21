package com.deificindia.indifun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deificindia.indifun.R;

public class IncrementalView extends RelativeLayout {


    ImageView plus, minus;
    TextView text;

    OnValueChanged _listener;
    OnClickListener _click_listener;

    boolean custom_icrement;
    int current_value = 0;
    int maxValue = Integer.MAX_VALUE, minValue = Integer.MIN_VALUE;

    public IncrementalView(Context context) {
        super(context);
        initView();
    }

    public IncrementalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public IncrementalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    void initView(){
        inflate(getContext(), R.layout.layout_increamental_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        plus = findViewById(R.id.img_plus);
        minus = findViewById(R.id.img_minu);
        text = findViewById(R.id.txt);

        plus.setOnClickListener(v->{

            if(custom_icrement){
                if(_click_listener!=null){
                    Integer tempVal = _click_listener.onPlusClick(current_value);
                    if (tempVal!=null){
                        current_value = tempVal;
                    }
                }
            }else {
                if(current_value < maxValue){
                    current_value++;
                }
            }

            setVal(current_value);
            if(_listener!=null) _listener.onValueChanged(current_value);
        });

        minus.setOnClickListener(v->{

            if(custom_icrement){
                if(_click_listener!=null){

                    Integer tempVal = _click_listener.onMinusClick(current_value);
                    if (tempVal!=null){
                        current_value = tempVal;
                    }
                }
            }else {
                if(current_value > minValue){
                    current_value--;
                }
            }

            setVal(current_value);

            if(_listener!=null) _listener.onValueChanged(current_value);

        });

    }

    private void setVal(int val){
        setText(val+"");
    }

    public void setText(String product_variant_min_qty) {
        text.setText(product_variant_min_qty);
    }


    public interface OnValueChanged{
        void onValueChanged(int currvalue);
    }

    public interface OnClickListener{
        Integer onPlusClick(int curr);
        Integer onMinusClick(int curr);
    }

    public void setListener(OnValueChanged listener) {
        this._listener = listener;
    }

    public void setClickListener(OnClickListener _click_listener) {
        this._click_listener = _click_listener;
    }

    public void setCurrentValue(int val){
        this.current_value = val;
        setVal(val);
    }

    public int getCurrentValue(){
        current_value = text.getText().toString().isEmpty()?0:Integer.parseInt(text.getText().toString());
        return current_value;
    }

    public void setHint(String hint){
        text.setHint(hint);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setCustomIcrement(boolean custom_icrement) {
        this.custom_icrement = custom_icrement;
    }
}
