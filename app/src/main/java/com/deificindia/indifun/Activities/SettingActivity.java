package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {


    private ImageView img_back;
    private TextView txt_header_title;
    //private RelativeLayout rl_account, rl_terms,rl_general,rl_strikergallery, rl_privacy, rl_helpcenter, rl_about, rl_clearthecatch;
    private String terms,privacy,about;

    private RecyclerView recyclerViewSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        img_back = findViewById(R.id.img_back);
        txt_header_title = findViewById(R.id.txt_header_title);

        recyclerViewSettings = findViewById(R.id.recyclerViewSettings);

        txt_header_title.setText("Settings");
        img_back.setOnClickListener(v -> onBackPressed());

        getterms();
        getabout();
        getprivacy();

        adapterView();
    }

    String[] strs = new String[]{"Account", "Notification", "Terms & Conditions", "Privacy Policy",
    "Sticker Gallery", "General", "Clear the cache", "About Indifun"};

    void adapterView(){
        SettingAdapter adapter = new SettingAdapter(Arrays.asList(strs));
        recyclerViewSettings.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerViewSettings.setAdapter(adapter);
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void getterms() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(SettingActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETTERMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                terms = jsonObject.optString("terms");


                            } else {
                                myDialog.dismiss();

                                // Toast.makeText(SettingActivity.this, obj.optString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();

                        Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void getabout() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(SettingActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETABOUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");

                                about=jsonObject.optString("about");

                            } else {
                                myDialog.dismiss();

                                Toast.makeText(SettingActivity.this, obj.optString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();

                        Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void getprivacy() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(SettingActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETPRIVACY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                privacy = jsonObject.optString("privacy_policy");


                            } else {
                                myDialog.dismiss();

                                // Toast.makeText(SettingActivity.this, obj.optString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();

                        Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

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
      supportFinishAfterTransition();
    }

    class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingHolder>{

        List<String> items = new ArrayList<>();

        public SettingAdapter(List<String> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public SettingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SettingHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting_view, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull SettingHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() { return items.size(); }

        class SettingHolder extends RecyclerView.ViewHolder{

            View parentView;
            TextView title;
            public SettingHolder(@NonNull View itemView) {
                super(itemView);
                parentView = itemView;
                title = itemView.findViewById(R.id.tv_text);

//trans_item_setting_view

            }

            void bind(String s){
                title.setText(s);

                parentView.setOnClickListener(v->{
                    settingHandler(s, title);
                });
            }
        }
    }

    void settingHandler(String txt, TextView tv){
      if(txt.equals(strs[0])){ transitionanimation(new Intent(SettingActivity.this, AccountActivity.class), tv); }
      if(txt.equals(strs[1])){ /*startActivity(new Intent(SettingActivity.this, AccountActivity.class));*/ }
      if(txt.equals(strs[2])){ webView("Terms & Conditions", terms, tv); }
      if(txt.equals(strs[3])){ webView("Privacy Policy", privacy, tv); }
      if(txt.equals(strs[4])){ transitionanimation(new Intent(SettingActivity.this, StickerActivity.class), tv); }
      if(txt.equals(strs[5])){ transitionanimation(new Intent(SettingActivity.this, GeneralActivity.class), tv); }
      if(txt.equals(strs[6])){
          try {
              File dir = getCacheDir();
              deleteDir(dir);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      if(txt.equals(strs[7])){ webView("About Indifun", about, tv); }

    }

    void webView(String title, String value, View tv){
        Intent intent = new Intent(SettingActivity.this, WebViewActivity.class)
                .putExtra(AppConstants.VALUE, title)
                .putExtra(AppConstants.HTMLCONTENT, value);

        transitionanimation(intent, tv);
    }

    void transitionanimation(Intent intent, View tv){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                tv,
                ViewCompat.getTransitionName(tv));
        startActivity(intent, options.toBundle());
    }

}