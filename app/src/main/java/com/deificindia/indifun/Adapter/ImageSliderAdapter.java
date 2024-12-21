package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.deificindia.indifun.Model.ImagesParsble;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {

    private Context cnx;
    private LayoutInflater layoutInflater;
    private List<String> ls;

    public ImageSliderAdapter(Context cnx, List<String> ls) {
        this.cnx = cnx;
        this.ls = ls;
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) cnx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout,null, false);
        ImageView cstm_imageView = (ImageView) view.findViewById(R.id.cstm_imageView);
        Picasso.get().load(URLs.UserImageBaseUrl+ls.get(position)).into(cstm_imageView);


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

}
