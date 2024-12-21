package com.deificindia.indifun.deificpk.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.ui.CircleImageView;


public class NewTag  extends RelativeLayout {
    private static final int IMAGE_VIEW_ID = 1 << 4;
    private static final int IMAGE_VIEW_ADD_ID = 5;
    private static final int TEXT_VIEW_ID = 6;

    private CircleImageView mIconImageView;
    private AppCompatTextView mNameTextView;

    String name;

    LayoutParams params;

    int iconPadding;

    private int mMaxWidth;
    private int mHeight;

    public NewTag(Context context) {
        super(context);
    }

    public NewTag(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewTag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int iconsize, int icon, int backgroundResource /*R.drawable.rou*/) {

        if (backgroundResource>-1) {
            setBackgroundResource(backgroundResource);
        } else {
            setBackgroundResource(R.drawable.round_scalable_gray_bg);
        }


        mIconImageView = new CircleImageView(getContext());
        mIconImageView.setId(IMAGE_VIEW_ID);
        addView(mIconImageView);
        iconPadding = getResources().getDimensionPixelSize(R.dimen._1dp);
        params = (LayoutParams) mIconImageView.getLayoutParams();
        int iconSize = iconsize - iconPadding * 2;
        params.width = iconSize;
        params.height = iconSize;
        params.leftMargin = iconPadding;
        params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mIconImageView.setLayoutParams(params);
        mIconImageView.setImageResource(icon);

        mNameTextView = new AppCompatTextView(getContext());
        mNameTextView.setId(TEXT_VIEW_ID);
        addView(mNameTextView);
        params = (LayoutParams) mNameTextView.getLayoutParams();
        params.addRule(RelativeLayout.END_OF, IMAGE_VIEW_ADD_ID);
        params.addRule(RelativeLayout.START_OF, IMAGE_VIEW_ID);
        params.leftMargin = iconPadding * 2;
        params.rightMargin = params.leftMargin;
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        mNameTextView.setLayoutParams(params);

        mNameTextView.setSingleLine(true);
        mNameTextView.setFocusable(true);
        mNameTextView.setFocusableInTouchMode(true);
        mNameTextView.setSelected(true);
        mNameTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mNameTextView.setMarqueeRepeatLimit(-1);
        mNameTextView.setTextAlignment(TextView.TEXT_ALIGNMENT_GRAVITY);
        mNameTextView.setGravity(Gravity.CENTER);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mMaxWidth, mHeight);
        int widthSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);
    }


    public void setLabel(String name, int color/*R.color.col*/, int textsize/*R.dimen.text_size_small*/) {
        this.name = name;
        if(mNameTextView!=null){
            mNameTextView.setText(name);

            if(color > 0) mNameTextView.setTextColor(getResources().getColor(color));

            if(textsize > 0) {
                int textSize = getResources().getDimensionPixelSize(textsize);
                mNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
        }

    }



}
