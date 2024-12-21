package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.fragments.GroupsFragment;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.pojo.discovergroupspojo.ResultItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiscoverGroups extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView img_back;
    private TextView txt_header_title;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    MyPagerAdapter adapterViewPager;
    private int pos;
    private ImageView addgroup;
    private ListPopupWindow listPopupWindow;
    private RelativeLayout btn_continue;
    private EditText entergroupname, entergroupdesc, entergroupcat;
    private AlertDialog alertDialog;
    private ArrayList<String> groupcatlist = new ArrayList<>();
    private ArrayList<String> groupcatlist_id = new ArrayList<>();
    private String groupcatid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_groups);
        img_back = findViewById(R.id.img_back);
        txt_header_title = findViewById(R.id.txt_header_title);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager1);

        addgroup = findViewById(R.id.addgroup);

        txt_header_title.setText("Discover Groups");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DiscoverGroups.this);
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_addgroup, viewGroup, false);

                entergroupname = dialogView.findViewById(R.id.entergroupname);
                entergroupdesc = dialogView.findViewById(R.id.entergroupdesc);
                entergroupcat = dialogView.findViewById(R.id.entergroupcat);
                btn_continue = dialogView.findViewById(R.id.btn_continue);

                builder.setView(dialogView);
                alertDialog = builder.create();

                entergroupcat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (resultItems != null && resultItems.size() > 0) {
                            show_drop_down_groupcatid(entergroupcat);
                        } else {
                            Toast.makeText(DiscoverGroups.this, "There Is No Group List Available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*if (!enterdropffotp.getText().toString().isEmpty()) {
                            alertDialog.cancel();
                            dropofftv.setVisibility(View.GONE);
                            markpicked.setVisibility(View.GONE);
                            markerddeliver.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(MapTrackingActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();

                        }*/
                        //  if (!enterdropffotp.getText().toString().isEmpty()) {
                        if (entergroupname.getText().toString().trim().isEmpty()) {
                            Toast.makeText(DiscoverGroups.this, getString(R.string.entergroupname), Toast.LENGTH_SHORT).show();
                        } else if (entergroupdesc.getText().toString().trim().isEmpty()) {
                            Toast.makeText(DiscoverGroups.this, getString(R.string.entergroupdesc), Toast.LENGTH_SHORT).show();
                        } else if (entergroupcat.getText().toString().trim().isEmpty()) {
                            Toast.makeText(DiscoverGroups.this, getString(R.string.entergroupcat), Toast.LENGTH_SHORT).show();
                        } else {
                            myDialog = DialogUtils.showProgressDialog(DiscoverGroups.this, "Loading Please Wait...");
                            creategroup(entergroupname.getText().toString().trim(), entergroupdesc.getText().toString().trim(), entergroupcat.getText().toString().trim(), groupcatid);
                        }


                        // } else {
                        //      Toast.makeText(MapTrackingActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();

                        //  }
                    }
                });
                alertDialog.show();
            }
        });

        getdicoverablegroups();


    }

    private void show_drop_down_groupcatid(final EditText entergroupcat1) {

        listPopupWindow = new ListPopupWindow(DiscoverGroups.this);
        if (groupcatlist != null && groupcatlist_id != null) {
            groupcatlist.clear();
            groupcatlist_id.clear();
        }


        for (ResultItem beans : resultItems) {
            if (!groupcatlist.contains(beans.getCategoryName())) {
                groupcatlist.add(beans.getCategoryName());
                groupcatlist_id.add(beans.getId());
            }
        }


        listPopupWindow.setAdapter(new ArrayAdapter(DiscoverGroups.this, R.layout.spinnerdownward, groupcatlist));

        listPopupWindow.setAnchorView(entergroupcat1);
        listPopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);

        listPopupWindow.setModal(true);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                entergroupcat.setText(groupcatlist.get(i));
                groupcatid = groupcatlist_id.get(i);
                listPopupWindow.dismiss();

            }
        });
        listPopupWindow.show();
    }

    private void creategroup(final String gorupname, final String gorupdesc, final String groupcat, final String groupcatid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CREATEGROUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                alertDialog.cancel();
                                onBackPressed();
                            } else {
                                myDialog.dismiss();
                                Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Toast.makeText(DiscoverGroups.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(DiscoverGroups.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String currentdate = formatter.format(date);
                Map<String, String> params = new HashMap<>();
                params.put("made_by", IndifunApplication.getInstance().getCredential().getId());
                params.put("group_name", gorupname);
                params.put("group_category", groupcatid);
                params.put("description", gorupdesc);
                params.put("entry_date", currentdate);

                return params;
            }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
               *//* Map<String, String> params = new HashMap<String, String>();
                params.put(AppConstants.TastyTiffinKEY, AppConstants.TastyTiffinKEYVALUE);
                return params;*//*
               return null;
            }*/
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
            return GroupsFragment.newInstance(Integer.parseInt(resultItems1.get(position).getId()));
            //   default:
            //    return null;
            //  }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence charSequence = null;
            charSequence = resultItems1.get(position).getCategoryName();

            return charSequence;
            //  return super.getPageTitle(position);

        }

    }

    private void getdicoverablegroups() {

        myDialog = DialogUtils.showProgressDialog(DiscoverGroups.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DICOVERGROUPS,
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
                                    resultItem.setCategoryName(qstnArray.getString("category_name"));
                                    resultItem.setEntryDate(qstnArray.getString("entry_date"));
                                    resultItems.add(resultItem);


                                }
                                if (resultItems != null && resultItems.size() > 0) {
                                    tabLayout.setVisibility(View.VISIBLE);
                                    viewPager.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < resultItems.size(); i++) {

                                        tabLayout.addTab(tabLayout.newTab().setText(resultItems.get(i).getCategoryName()));
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

                                Toast.makeText(DiscoverGroups.this, obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(DiscoverGroups.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(DiscoverGroups.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
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
