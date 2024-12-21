package com.deificindia.indifun.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deificindia.indifun.R;

import java.text.MessageFormat;

public class PieChart extends RelativeLayout {

    TextView txtProgress, txtTitle;
    ProgressBar progress_full, progress;

    String title;
    int max, progr;

    public PieChart(Context context) {
        super(context);
        init(null);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    void init(AttributeSet attrs){
        inflate(getContext(), R.layout.layout_pie_chart, this);

        if(attrs!=null){
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PieChart, 0, 0);

            title = a.getString(R.styleable.PieChart_pie_title);
            max = a.getInt(R.styleable.PieChart_pie_max, 0);
            progr = a.getInt(R.styleable.PieChart_pie_progress, 0);
            //mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, DEFAULT_BORDER_WIDTH);

            a.recycle();
        }

        txtTitle = findViewById(R.id.textView);
        txtProgress = findViewById(R.id.number);
        progress = findViewById(R.id.stats_progressbar);
        progress_full = findViewById(R.id.background_progressbar);

        txtTitle.setText(title);
        txtProgress.setText(MessageFormat.format("{0}/{1}", progr, max));
        //progress.setProgress((progr/max) *100);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    public void setProgress(int pro, int max){

        txtProgress.setText(MessageFormat.format("{0}/{1}", pro, max));
        int pro1 = (pro/max) *100;
        progress.setProgress(pro1);
    }

}
