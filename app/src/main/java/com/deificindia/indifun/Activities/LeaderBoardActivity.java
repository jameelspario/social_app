package com.deificindia.indifun.Activities;

import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.COINSENDER;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.FOLLOWERS;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.GLOBAL;
import static com.deificindia.indifun.fragments.LeaderBoardDcfFragment.LOCAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.indifun.Utility.MenuUtils;
import com.deificindia.indifun.anim.CircleReavealAnim;
import com.deificindia.indifun.deificpk.utils.BroadApiCall;
import com.deificindia.indifun.dialogs.DialogUtils;
import com.deificindia.indifun.fragments.LeaderBoardFragment;
import com.deificindia.indifun.interfaces.Event;
import com.google.android.material.tabs.TabLayout;

import com.deificindia.indifun.R;
import com.skydoves.powermenu.CustomPowerMenu;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity/* implements OnMenuItemClickListener<PowerMenuItem>*/ {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    private ImageView img_back;
    private TextView txt_header_title, txtMenu;
    private TabLayout tabLayout;

    View rootLayout;

    private int revealX;
    private int revealY;

    private  int isglobal_local = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.rootLayout);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {

            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        CircleReavealAnim.activityReaveal(rootLayout, revealX, revealY, true, null);
                        initView();
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }

        } else {
            rootLayout.setVisibility(View.VISIBLE);
            initView();
        }

    }

    void initView(){
        img_back=findViewById(R.id.img_back);
        txt_header_title=findViewById(R.id.txt_header_title);
        txtMenu=findViewById(R.id.txtmenu);
        img_back.setOnClickListener(v -> onBackPressed());
        //ActivityUtils.onPowerMenu(LeaderBoardActivity.this, isglobal_local == 0, this).showAsAnchorCenter(v);
        txtMenu.setOnClickListener(this::openMenu);
        tabLayout = findViewById(R.id.tabs);
        replaceFragment(new LeaderBoardFragment(COINSENDER, isglobal_local));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*if (tab.getPosition() == 0) {
                    replaceFragment(new LeaderBoardFragment(0, isglobal_local));
                } else*/
                if (tab.getPosition() == 0) {
                    replaceFragment(new LeaderBoardFragment(COINSENDER, isglobal_local));
                } else {
                    replaceFragment(new LeaderBoardFragment(FOLLOWERS, isglobal_local));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void replaceFragment(Fragment fragment) {
       getSupportFragmentManager()
       .beginTransaction()
        .replace(R.id.fragment_container, fragment)
        .commit();
    }

   /* @Override
    public void onItemClick(int position, PowerMenuItem item) {
        if ("Global".equals(item.getTitle())) {
            isglobal_local = 0;
        }else {
            isglobal_local = 1;
        }
        Event.trigger(isglobal_local);
    }*/

    //////////////////////
    CustomPowerMenu customPowerMenu;
    void openMenu(View view){
        /*registerForContextMenu(view);
        openContextMenu(view);*/

      /*  DialogUtils.showSelectLeaderBoardOptionDialog(this, isglobal_local, (dialog, selection) -> {
            isglobal_local = selection;
            Event.trigger(isglobal_local);

            dialog.dismiss();
        });*/

        List<MenuUtils.IconPowerMenuItem> menuitem = new ArrayList<>();

        menuitem.add(new MenuUtils.IconPowerMenuItem(
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_location_on_24), "Local"));

        menuitem.add(new MenuUtils.IconPowerMenuItem(
                ContextCompat.getDrawable(this, R.drawable.ic_globe_309064), "Global"));

        customPowerMenu = MenuUtils.createMenu(txtMenu, menuitem, (position, item) -> {
            if ("Local".contentEquals(item.title)) {
                isglobal_local = LOCAL;
                Event.trigger(isglobal_local);
            } else if ("Global".contentEquals(item.title)) {
                isglobal_local = GLOBAL;
                Event.trigger(isglobal_local);
            }

            customPowerMenu.dismiss();

        });

    }

    @Override
    public void onBackPressed() {
        CircleReavealAnim.activityReaveal(rootLayout, revealX, revealY, false, this::finish);
    }
}