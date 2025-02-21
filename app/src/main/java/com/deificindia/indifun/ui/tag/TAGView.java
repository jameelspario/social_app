package com.deificindia.indifun.ui.tag;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;

/*
*  <com.jude.tagview.TAGView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:tag_stroke_width="2dp"          //0 for background mode.other for border mode.
        app:tag_radius="4dp"                //radius for the background.default 4dp.
        app:tag_as_circle="true"            //show as circle
        app:tag_icon="@drawable/ic_doctor"  //If you don't set,it just like a label
        app:tag_text="TAG"                  //If you don't set,it just like a icon with background.
        app:tag_color="#9d55b8"             //background color of the TAGView.
        app:tag_text_color="#ffffff"        //text color of the label.default white.
        app:tag_text_size="13sp"            //text size of the label.default 13sp.
        app:tag_divider="2dp"               //distance between icon and text.default 4dp.
        app:tag_image_width="16dp"          //control the icon's width
        app:tag_padding="4dp"               //set padding by this
        app:tag_padding_top="4dp"
        app:tag_padding_bottom="4dp"
        app:tag_padding_left="4dp"
        app:tag_padding_right="4dp"
        />
* */
public class TAGView extends ViewGroup {

    private ImageView mImageView;
    private TextView mTextView;


    private float radius;

    private Paint mPaint;
    private int color;
    private int imageWidth;
    private int dividerWidth;
    private int paddingLeft;
    private int paddingTop;
    private int paddingBottom;
    private int paddingRight;
    private int strokeWidth;
    private boolean asCircle;

    public TAGView(Context context) {
        this(context,null);
    }

    public TAGView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TAGView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs){
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        LayoutInflater.from(getContext()).inflate(R.layout.tag_view,this,true);
        mImageView = (ImageView) findViewById(R.id.icon);
        mTextView = (TextView) findViewById(R.id.text);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TAGView);
        try {
            setIcon(a.getResourceId(R.styleable.TAGView_tag_icon, 0));
            setText(a.getString(R.styleable.TAGView_tag_text));
            setTextColor(a.getColor(R.styleable.TAGView_tag_text_color, Color.WHITE));
            color = a.getColor(R.styleable.TAGView_tag_color, Color.TRANSPARENT);
            mPaint.setColor(color);
            radius = a.getDimension(R.styleable.TAGView_tag_radius, dip2px(2));
            imageWidth = (int) a.getDimension(R.styleable.TAGView_tag_image_width, dip2px(16));
            dividerWidth = (int) a.getDimension(R.styleable.TAGView_tag_divider, dip2px(2));
            paddingLeft=paddingRight=paddingTop=paddingBottom
                    =(int) a.getDimension(R.styleable.TAGView_tag_padding, dip2px(2));
            paddingLeft = (int) a.getDimension(R.styleable.TAGView_tag_padding_left, dip2px(0));
            paddingRight = (int) a.getDimension(R.styleable.TAGView_tag_padding_right, dip2px(0));
            paddingTop = (int) a.getDimension(R.styleable.TAGView_tag_padding_top, dip2px(0));
            paddingBottom = (int) a.getDimension(R.styleable.TAGView_tag_padding_bottom, dip2px(0));
            asCircle = a.getBoolean(R.styleable.TAGView_tag_as_circle,false);
            strokeWidth = (int) a.getDimension(R.styleable.TAGView_tag_stroke_width, dip2px(0));
            setStrokeWidth(strokeWidth);

            int textSize = a.getDimensionPixelSize(R.styleable.TAGView_tag_text_size, (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }finally {
            a.recycle();
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        paddingLeft = dip2px(left);
        paddingRight = dip2px(right);
        paddingTop = dip2px(top);
        paddingBottom = dip2px(bottom);
        requestLayout();
    }


    public void setDividerWidth(int width){
        dividerWidth = dip2px(width);
        requestLayout();
    }

    public void setImageWidth(int width){
        imageWidth = dip2px(width);
        invalidate();
    }

    public void setAsCircle(){
        asCircle = true;
        invalidate();
    }

    public void setRadius(int radius){
        this.radius = dip2px(radius);
        invalidate();
    }

    public void setStrokeWidth(int width){
        strokeWidth = width;
        if (strokeWidth != 0){
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
        }else{
            mPaint.setStyle(Paint.Style.FILL);
        }
        invalidate();
    }

    public void setTextSize(int sp){
        mTextView.setTextSize(sp);
    }

    public void setText(String txt, int color, int dimen /*R.dimen._18dp*/){
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen);
        setTextColor(color);
        setText(txt);
    }

    public void setText(String text){
        if (text==null||text.isEmpty()){
            mTextView.setVisibility(GONE);
        }
        else{
            mTextView.setVisibility(VISIBLE);
            mTextView.setText(text);
        }
    }
    public void setTextColor(int color){
        mTextView.setTextColor(color);
    }
    public void setIcon(int res){
        if (res == 0)removeIcon();
        else {
            mImageView.setVisibility(VISIBLE);
            mImageView.setImageResource(res);
        }
    }

    public void iconTint(int colo){
       // mImageView.setColorFilter(ContextCompat.getColor(getContext(), colo
        //        /* R.color.COLOR_YOUR_COLOR*/), android.graphics.PorterDuff.Mode.MULTIPLY);
        mImageView.setColorFilter(colo, PorterDuff.Mode.SRC_IN);
    }

    public void removeIcon(){
        mImageView.setVisibility(GONE);
    }

    public void setBackgroundColor(int color){
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);


        int inWidth = width-paddingLeft-paddingRight;
        int inHeight = height-paddingTop-paddingBottom;

//        switch (widthModel){
//            case MeasureSpec.EXACTLY:
//                Log.i("TAGView",mTextView.getText()+" widthModel"+" EXACTLY"+"  width"+width);
//                break;
//            case MeasureSpec.UNSPECIFIED:
//                Log.i("TAGView",mTextView.getText()+" widthModel"+" UNSPECIFIED"+"  width"+width);
//                break;
//            case MeasureSpec.AT_MOST:
//                Log.i("TAGView",mTextView.getText()+" widthModel"+" AT_MOST"+"  width"+width);
//                break;
//        }
//        switch (heightModel){
//            case MeasureSpec.EXACTLY:
//                Log.i("TAGView",mTextView.getText()+" heightModel"+" EXACTLY"+"  height"+height);
//                break;
//            case MeasureSpec.UNSPECIFIED:
//                Log.i("TAGView",mTextView.getText()+" heightModel"+" UNSPECIFIED"+"  height"+height);
//                break;
//            case MeasureSpec.AT_MOST:
//                Log.i("TAGView",mTextView.getText()+" heightModel"+" AT_MOST"+"  height"+height);
//                break;
//        }

        if (mImageView.getVisibility()==VISIBLE)
        mImageView.measure(
                MeasureSpec.makeMeasureSpec(imageWidth,MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(inHeight, MeasureSpec.UNSPECIFIED)
        );
        if (mTextView.getVisibility()==VISIBLE)
        mTextView.measure(
                MeasureSpec.makeMeasureSpec(inWidth,MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(inHeight,MeasureSpec.UNSPECIFIED)
        );

//        Log.i("TAGView",mTextView.getText()+" I width"+mImageView.getMeasuredWidth()+" I height"+mImageView.getMeasuredHeight()+" T width"+mTextView.getMeasuredWidth()+" T height"+mTextView.getMeasuredHeight());

        int widthTotal = mImageView.getMeasuredWidth()+
                mTextView.getMeasuredWidth()+
                paddingLeft+
                paddingRight+
                ((mTextView.getVisibility()==VISIBLE && mImageView.getVisibility()==VISIBLE)?dividerWidth:0);
        int heightTotal = Math.max(mImageView.getMeasuredHeight(), mTextView.getMeasuredHeight())+paddingTop+paddingBottom;



        int reheight = height;
        int rewidth = width;
        switch (heightModel){
            case MeasureSpec.EXACTLY:
                reheight = height;
                break;
            case MeasureSpec.AT_MOST:
                reheight = Math.min(heightTotal, height);
                break;
            case MeasureSpec.UNSPECIFIED:
                reheight = heightTotal;
                break;
        }
        switch (widthModel){
            case MeasureSpec.EXACTLY:
                rewidth = width;
                break;
            case MeasureSpec.AT_MOST:
                rewidth = Math.min(widthTotal, width);
                break;
            case MeasureSpec.UNSPECIFIED:
                rewidth = heightTotal;
                break;
        }
        setMeasuredDimension(rewidth, reheight);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left,height = bottom - top;
//        Log.i("TAGView", mTextView.getText() + "  Layout W" + width);
        int l = paddingLeft, r = width-paddingRight,t = paddingTop,b = height-paddingBottom;
        int centerVertical = (b+t)/2 , centerHorizontal = (l+r)/2;



        if (mTextView.getVisibility()==VISIBLE&&mImageView.getVisibility()==VISIBLE){
            int divider = Math.max(r - l - mImageView.getMeasuredWidth() - mTextView.getMeasuredWidth() - dividerWidth, 0)/3;

            mImageView.layout(
                    l+divider,
                    centerVertical-mImageView.getMeasuredHeight()/2,
                    l+divider+mImageView.getMeasuredWidth(),
                    centerVertical+mImageView.getMeasuredHeight()/2);

            mTextView.layout(
                    l+divider*2+mImageView.getMeasuredWidth()+dividerWidth,
                    centerVertical-mTextView.getMeasuredHeight()/2,
                    r-divider,
                    centerVertical+mTextView.getMeasuredHeight()/2);
        }else {

            mImageView.layout(
                    centerHorizontal-mImageView.getMeasuredWidth()/2,
                    centerVertical-mImageView.getMeasuredHeight()/2,
                    centerHorizontal+mImageView.getMeasuredWidth() / 2,
                    centerVertical + mImageView.getMeasuredHeight()/2);

            mTextView.layout(
                    centerHorizontal-mTextView.getMeasuredWidth()/2,
                    centerVertical-mTextView.getMeasuredHeight()/2,
                    centerHorizontal+mTextView.getMeasuredWidth()/2,
                    centerVertical+mTextView.getMeasuredHeight()/2);
        }

    }


    @Override
    public void draw(Canvas canvas) {
        if (asCircle){
            canvas.drawArc(new RectF(strokeWidth/2, strokeWidth/2, getWidth()-strokeWidth/2, getHeight()-strokeWidth/2),0,360,true,mPaint);
        }else {
            canvas.drawRoundRect(new RectF(strokeWidth/2, strokeWidth/2, getWidth()-strokeWidth/2, getHeight()-strokeWidth/2), radius, radius, mPaint);
        }
        super.draw(canvas);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
