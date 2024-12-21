package com.deificindia.indifun.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deificindia.indifun.Model.Countrymodel;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.FlagKit;

import java.util.List;

public class CountryAdapter extends ArrayAdapter<Countrymodel> {

    List<Countrymodel> list;
    Context cnx;

    public CountryAdapter(@NonNull Context context, int resource, List<Countrymodel> list) {
        super(context, R.layout.item_country_spinner, list);
        this.list = list;
        this.cnx = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)cnx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_country_spinner, parent, false);

        TextView textView =  row.findViewById(R.id.tvCode);
        textView.setText(list.get(position).getDial_code());

        ImageView imageView = row.findViewById(R.id.imgFlag);

        Drawable draw = FlagKit.drawableWithFlag(cnx,list.get(position).getCode().toLowerCase());
        imageView.setImageDrawable(draw);

        return row;
    }
}
