package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.fragments.InterstFragment;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.pojo.interstcatlits.ResultItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class InterstList extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView img_back;
    private TextView txt_header_title;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    private MyPagerAdapter adapterViewPager;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interst_list);
        img_back = findViewById(R.id.img_back);
        txt_header_title = findViewById(R.id.txt_header_title);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager1);
        txt_header_title.setText("Interests");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getinterstcatlots();
    }

    private void getinterstcatlots() {

        myDialog = DialogUtils.showProgressDialog(InterstList.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.INTERESTCATLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setIntrestCategory(qstnArray.getString("intrest_category"));
                                    resultItems.add(resultItem);


                                }
                                if (resultItems != null && resultItems.size() > 0) {
                                    tabLayout.setVisibility(View.VISIBLE);
                                    viewPager.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < resultItems.size(); i++) {

                                        tabLayout.addTab(tabLayout.newTab().setText(resultItems.get(i).getIntrestCategory()));
                                    }

                                    adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), resultItems);
                                    viewPager.setAdapter(adapterViewPager);
                                    tabLayout.setupWithViewPager(viewPager);
                                    viewPager.setCurrentItem(0/*pos*/);
                                    // Attach the page change listener inside the activity
                                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                        // This method will be invoked when a new page becomes selected.
                                        @Override
                                        public void onPageSelected(int position) {


                  /*  Toast.makeText(MainActivity.this,
                            "Selected page position: " + position, Toast.LENGTH_SHORT).show();*/
                                        }

                                        // This method will be invoked when the current page is scrolled
                                        @Override
                                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                            // Code goes here
                                        }

                                        // Called when the scroll state changes:
                                        // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
                                        @Override
                                        public void onPageScrollStateChanged(int state) {
                                            // Code goes here
                                        }
                                    });
                                    // setupViewPager(viewPager);
                                    //viewPager.setOffscreenPageLimit(0);
                                    //  viewPager.setCurrentItem(pos);
                                    tabLayout.setupWithViewPager(viewPager);
                                } else {
                                    tabLayout.setVisibility(View.GONE);
                                    viewPager.setVisibility(View.GONE);
                                }


                            } else {
                                myDialog.dismiss();

                                Toast.makeText(InterstList.this, obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(InterstList.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(InterstList.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS;
        private ArrayList<ResultItem> resultItems1;

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<ResultItem> resultItems1) {
            super(fragmentManager);
            this.resultItems1 = resultItems1;
            NUM_ITEMS = resultItems1.size();
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            //Un Comment this one when you are making it dynamic
            //Send id and rest data in this
            //return FirstFragment.newInstance(position, "Page # 1");

            // switch (position) {
            //   case 0: // Fragment # 0 - This will show FirstFragment
            return InterstFragment.newInstance(Integer.parseInt(resultItems1.get(position).getId()));
            //   default:
            //    return null;
            //  }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence charSequence = null;
            charSequence = resultItems1.get(position).getIntrestCategory();

            return charSequence;
            //  return super.getPageTitle(position);

        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStackImmediate();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}
