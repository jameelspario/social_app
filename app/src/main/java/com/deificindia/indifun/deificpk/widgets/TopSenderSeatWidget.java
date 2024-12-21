package com.deificindia.indifun.deificpk.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.deificindia.firebaseModel.GiftSenderModel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.anim.TranslateView;
import com.google.android.gms.vision.Frame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopSenderSeatWidget extends LinearLayout {

    int seat1Id = 1<<3;
    int seat2Id = 2<<4;
    int seat3Id = 3<<5;
    int parent = 4<<6;

    public static final int PKOWNER = 1;
    int ispkowner = PKOWNER;

    Map<Integer, View> map = new HashMap<>();
    Map<Integer, ImageView> seat_profile = new HashMap<>();
    Map<Integer, FrameLayout> seat_frame = new HashMap<>();
    Map<Integer, GiftSenderModel> seat_data = new HashMap<>();


    public TopSenderSeatWidget(Context context) {
        super(context);
        init( null);
    }

    public TopSenderSeatWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TopSenderSeatWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    int mHeight, margins;
    View seat1, seat2, seat3;
    void init(AttributeSet attrs){
        mHeight = getResources().getDimensionPixelSize(R.dimen.live_name_pad_height_4);
        margins = dip2px(4);

        if(attrs!=null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TopSenderSeatWidget);
            ispkowner = typedArray.getInt(R.styleable.TopSenderSeatWidget_tssw_type, PKOWNER);
        }

        //setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //inflate(getContext(), R.layout.layout_top_sender_seat_widget, this);
        setId(parent);

        seat1 = profileWithFrame(getContext(), seat1Id);
        map.put(1, seat1);
        seat2 = profileWithFrame(getContext(), seat2Id);
        map.put(2, seat2);
        seat3 = profileWithFrame(getContext(), seat3Id);
        map.put(3, seat3);

        if(ispkowner==PKOWNER) {
            addView(seat1);
            addView(seat2);
            addView(seat3);
        }else {
            addView(seat3);
            addView(seat2);
            addView(seat1);
        }

        seatClick();

        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) seat1.getLayoutParams();
        param.rightMargin = margins;
        seat1.setLayoutParams(param);

        LinearLayout.LayoutParams param2 = (LinearLayout.LayoutParams) seat2.getLayoutParams();
        param2.rightMargin = margins;
        seat2.setLayoutParams(param2);

        /*LinearLayout.LayoutParams param3 = (LinearLayout.LayoutParams) seat3.getLayoutParams();
        param3.rightMargin = dip2px(4);
        seat3.setLayoutParams(param3);*/

    }


    void seatClick(){
        seat1.setOnClickListener(v-> seatClick(1));
        seat2.setOnClickListener(v-> seatClick(2));
        seat3.setOnClickListener(v-> seatClick(3));
    }

    void seatClick(int i){
        if(seatClickListener!=null)
            seatClickListener.seatClick(i, seat_data.get(i));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // int width = MeasureSpec.getSize(widthMeasureSpec);

        int width = mHeight*3 + margins*2;
        setMeasuredDimension(width, mHeight);
        int heightSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
        super.onMeasure(width, heightSpec);
    }


    View profileWithFrame(Context context, int id){
        FrameLayout frameLayout = new FrameLayout(context);
        FrameLayout.LayoutParams params0 = new FrameLayout.LayoutParams(mHeight, mHeight);
        frameLayout.setLayoutParams(params0);

        frameLayout.setId(id);

        ImageView imageView = new ImageView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin = dip2px( 2);
        imageView.setImageResource(R.drawable.img_user_default);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(params);
        int p = dip2px( 4);
        imageView.setPadding(p,p,p,p);

        frameLayout.addView(imageView);


        FrameLayout frame = new FrameLayout(context);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frame.setLayoutParams(params2);

        if(id==seat1Id) {
            frame.setBackgroundResource(R.drawable.crown_gold);
            saveView(1, imageView, frame);
        }else if(id==seat2Id) {
            frame.setBackgroundResource(R.drawable.crown_silver);
            saveView(2, imageView, frame);
        }else {
            frame.setBackgroundResource(R.drawable.crown_bronze);
            saveView(3, imageView, frame);
        }

        frameLayout.addView(frame);

        return frameLayout;
    }

    void saveView(int seat, ImageView imageView, FrameLayout frame){
        seat_profile.put(seat, imageView);
        seat_frame.put(seat, frame);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    onSeatClickListener seatClickListener;
    public interface onSeatClickListener{
        void seatClick(int seat, GiftSenderModel gift);
    }

    public void updateSeat(int at, GiftSenderModel gift){
        ImageView img = seat_profile.get(at);
        GiftSenderModel data = seat_data.get(at);
        if(img!=null){
            UiUtils.setAvtarRounded(gift.avtar, img);
            seat_data.put(at, gift);
        }
    }


}
