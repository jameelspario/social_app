package com.deificindia.indifun.Utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.MenuBaseAdapter;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.List;

public class MenuUtils {

    public static class IconPowerMenuItem {
        public Drawable icon;
        public String title;

        public IconPowerMenuItem(Drawable icon, String title) {
            this.icon = icon;
            this.title = title;
        }
    }

    public static class IconMenuAdapter extends MenuBaseAdapter<IconPowerMenuItem> {

        @Override
        public View getView(int index, View view, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();

            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_icon_menu, viewGroup, false);
            }

            IconPowerMenuItem item = (IconPowerMenuItem) getItem(index);
            final ImageView icon = view.findViewById(R.id.item_icon);
            icon.setImageDrawable(item.icon);
            final TextView title = view.findViewById(R.id.item_title);
            title.setText(item.title);
            return super.getView(index, view, viewGroup);
        }
    }

    /*
    private OnMenuItemClickListener<IconPowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
    @Override
    public void onItemClick(int position, IconPowerMenuItem item) {
        Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        iconMenu.dismiss();
    }
};
    * */

    public static CustomPowerMenu createMenu(View img, List<IconPowerMenuItem> menus, OnMenuItemClickListener<IconPowerMenuItem> onIconMenuItemClickListener){
        CustomPowerMenu customPowerMenu = new CustomPowerMenu.Builder<>(img.getContext(), new IconMenuAdapter())
                .addItemList(menus)
                .setOnMenuItemClickListener(onIconMenuItemClickListener)
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .build();

        customPowerMenu.showAsAnchorCenter(img);

        return customPowerMenu;
    }

    public static PowerMenu onPowerMenu(Context context, boolean isGlobal, OnMenuItemClickListener<PowerMenuItem> listener){
        return new PowerMenu.Builder(context)
                //.addItemList(list) // list has "Novel", "Poerty", "Art"
                .addItem(new PowerMenuItem("Global", isGlobal)) // add an item.
                .addItem(new PowerMenuItem("Local", !isGlobal)) // aad an item list.
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT) // Animation start point (TOP | LEFT).
                .setMenuRadius(10f) // sets the corner radius.
                .setMenuShadow(10f) // sets the shadow.
                .setTextColor(ContextCompat.getColor(context, R.color.black))
                .setTextGravity(Gravity.CENTER)
                //.setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                .setSelectedTextColor(Color.WHITE)
                .setMenuColor(Color.GRAY)
                .setSelectedMenuColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setOnMenuItemClickListener(listener)
                .build();
    }

}
