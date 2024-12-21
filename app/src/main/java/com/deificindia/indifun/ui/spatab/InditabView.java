package com.deificindia.indifun.ui.spatab;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.deificindia.indifun.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
InditabView tab = findViewById(R.id.tabs);
tab.setuptab(this, "tab1dfsdfgdfgdh", "tab2hfghfgh", "tafsdfgsdgb3hfghf", "tab4rwerwrtertyrty");
* */
//8563932079
public class InditabView extends LinearLayout {

    Map<String, CheckedTextView> map = new HashMap<>();
    List<String> titleset = new LinkedList<>();
    ViewPager2 viewPager2;

    TabSelectionChange listener;

    public void setListener(TabSelectionChange listener) {
        this.listener = listener;
    }


    public InditabView(Context context) {
        super(context);
        init();
    }

    public InditabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InditabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        setBackgroundResource(R.drawable.bg_tab_parent);
        //View v = LayoutInflater.from(getContext()).inflate(R.layout.indi_tab_layout, this, false);
        //parent = v.findViewById(R.id.parent);
    }


    private CheckedTextView textView1(Context context, String txt, int typ){

        CheckedTextView textView = new CheckedTextView(context);
        addView(textView);
        textView.setText(txt);

        LayoutParams param = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        param.gravity = Gravity.CENTER;
        textView.setLayoutParams(param);
        textView.setId(View.generateViewId());
        textView.setGravity(Gravity.CENTER);
        textView.setTextAlignment(TEXT_ALIGNMENT_GRAVITY); //android:textAlignment="gravity"
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setMaxLines(1);

        textView.setPadding(10, 0, 10, 0);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        return textView;

    }


    private View devider(Context context){
        View v = new View(context);
        LayoutParams param = new LayoutParams(1, LayoutParams.MATCH_PARENT);
        v.setLayoutParams(param);
        v.setBackgroundColor(getResources().getColor(R.color.gray));

        return v;
    }

    void initView(Context context, String tx, int n, int index){
        CheckedTextView textView = textView1(context, tx, n);

        switch (n){
            case 1:
                textView.setBackgroundResource(R.drawable.click_tab_start);
                break;
            case 2:
                textView.setBackgroundResource(R.drawable.click_tab_end);
                break;
            case 3:
                textView.setBackgroundResource(R.drawable.click_tab_single);
                break;
            default:
                textView.setBackgroundResource(R.drawable.click_tab_center);
        }

        textView.setOnClickListener(v->{
            //Log.e("TAG", "initView: "+index);
            //uncheckAll();
            //textView.setChecked(true);
           triggerChange(index);
        });
        map.put(tx, textView);
    }

    public void setuptab(Context context, String... tabtitle){
        removeAllViews();
        map.clear();
        titleset.clear();

        if(tabtitle.length==1){
            initView(context, tabtitle[0], 3, 0);
            return;
        }
        for (int i =0; i < tabtitle.length; i++){
           // Log.e("t", ""+i);
            if(i==0){
                initView(context, tabtitle[i], 1, i);
            }

            else if(i==tabtitle.length-1){
                initView(context, tabtitle[i], 2, i);
            }
            else {
                initView(context, tabtitle[i], 4, i);
            }

            if(i !=tabtitle.length-1){
                View v = devider(context);
                addView(v);
            }

            titleset.add(tabtitle[i]);

        }
    }

    public void changeSelection(int index){
        uncheckAll();
       // List<String> keys = new ArrayList(map.keySet());
       // String k = keys.get(index);
        String tab = titleset.get(index);
        CheckedTextView ctv = map.get(tab);
        if(ctv!=null){
            ctv.setChecked(true);
            ctv.setTextColor(getResources().getColor(R.color.black));
            ctv.setTypeface(null, Typeface.BOLD);
        }
        /*int pro = 0;
        for(String k1:map.keySet()){
            if(pro==index){
                CheckedTextView ctv = map.get(k1);
                if(ctv!=null){
                    ctv.setChecked(true);
                    ctv.setTextColor(getResources().getColor(R.color.black));
                }
                return;
            }
            pro++;
        }
*/

    }

    private void uncheckAll(){
        for (String ctv:map.keySet()){
            CheckedTextView ct = map.get(ctv);
            if(ct!=null){
                ct.setChecked(false);
                ct.setTextColor(getResources().getColor(R.color.white));
                ct.setTypeface(null, Typeface.NORMAL);
            }
        }
    }

    public interface TabSelectionChange{
        void onSelected(int index);
        void onPagerScrolled(int pos);
    }

    private void triggerChange(int index){

        if (listener != null) {
            listener.onSelected(index);
        }

        if(viewPager2!=null){
            viewPager2.setCurrentItem(index);
        }
    }

    public void setViewPager(ViewPager2 viewPager2) {
        this.viewPager2 = viewPager2;
        pagerListener();
    }

    void pagerListener(){
        if(viewPager2==null) return;

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //loge("Explore 2", ""+position);
                if (listener != null) {
                    listener.onPagerScrolled(position);
                }
                changeSelection(position);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //loge("Explore 3", ""+position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }


}
