package com.deificindia.indifun.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class OnlineCustomerService extends AppCompatActivity {

    private ImageView img_back,applogo1;
    private TextView txt_header_title,appname1,ownernametv,address1,addrss2;
    private LinearLayout onlinell;
    private RelativeLayout rlcs,rlcs1,rlcs2,rlcs3;
    private String appname,address_1,address_2,city,pin,state,mobile_number,mobile_number2,email1,email2,nationality,owner_name,logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_customer_service);
        img_back=findViewById(R.id.img_back);
        txt_header_title=findViewById(R.id.txt_header_title);
        appname1=findViewById(R.id.appname1);
        ownernametv=findViewById(R.id.ownernametv);
        address1=findViewById(R.id.address1);
        addrss2=findViewById(R.id.addrss2);
        rlcs=findViewById(R.id.rlcs);
        rlcs1=findViewById(R.id.rlcs1);
        rlcs2=findViewById(R.id.rlcs2);
        rlcs3=findViewById(R.id.rlcs3);
        applogo1=findViewById(R.id.applogo1);
        onlinell=findViewById(R.id.onlinell);

        txt_header_title.setText(getString(R.string.onlinecustomer));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        getonlinecontact();

        rlcs.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                if (mobile_number != null && !mobile_number.isEmpty()) {
                    Uri u = Uri.parse("tel:" + mobile_number/*"9876543210"*/);

                    // Create the intent and set the data for the
                    // intent as the phone number.
                    Intent i = new Intent(Intent.ACTION_DIAL, u);

                    try {
                        // Launch the Phone app's dialer with a phone
                        // number to dial a call.
                        startActivity(i);
                    } catch (SecurityException s) {
                        // show() method display the toast with
                        // exception message.
                    /*Toast.makeText(this, s.Message, Toast.LENGTH_LONG)
                            .show();*/
                    }
                }
            }
        });
        rlcs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email1 != null && !email1.isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email1});
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name));
                    intent.setPackage("com.google.android.gm");
                    if (intent.resolveActivity(getPackageManager()) != null)
                        startActivity(intent);
                    else
                        Toast.makeText(OnlineCustomerService.this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rlcs1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                if (mobile_number2 != null && !mobile_number2.isEmpty()) {
                    Uri u = Uri.parse("tel:" + mobile_number2/*"9876543210"*/);

                    // Create the intent and set the data for the
                    // intent as the phone number.
                    Intent i = new Intent(Intent.ACTION_DIAL, u);

                    try {
                        // Launch the Phone app's dialer with a phone
                        // number to dial a call.
                        startActivity(i);
                    } catch (SecurityException s) {
                        // show() method display the toast with
                        // exception message.
                    /*Toast.makeText(this, s.Message, Toast.LENGTH_LONG)
                            .show();*/
                    }
                }
            }
        });
        rlcs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email2 != null && !email2.isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email2});
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name));
                    intent.setPackage("com.google.android.gm");
                    if (intent.resolveActivity(getPackageManager()) != null)
                        startActivity(intent);
                    else
                        Toast.makeText(OnlineCustomerService.this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void getonlinecontact() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(OnlineCustomerService.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ONLINECONTACT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                onlinell.setVisibility(View.VISIBLE);
                                JSONObject jsonObject = obj.optJSONObject("result");

                                appname=jsonObject.optString("app_name");
                                address_1=jsonObject.optString("address_1");
                                address_2=jsonObject.optString("address_2");
                                city=jsonObject.optString("city");
                                pin=jsonObject.optString("pin");
                                state=jsonObject.optString("state");
                                nationality=jsonObject.optString("nationality");
                                mobile_number=jsonObject.optString("mobile_number");
                                mobile_number2=jsonObject.optString("mobile_number2");
                                email1=jsonObject.optString("email1");
                                email2=jsonObject.optString("email2");
                                owner_name=jsonObject.optString("owner_name");
                                logo=jsonObject.optString("logo");

                                appname1.setText(appname);
                                address1.setText(address_1+" , "+city+" , "+state+" , "+pin+" , "+nationality);
                                addrss2.setText(address_2+" , "+city+" , "+state+" , "+pin+" , "+nationality);
                                ownernametv.setText(owner_name);

                                if (logo != null && !logo.isEmpty()) {

                                    Picasso.get()
                                            .load(URLs.LogoImageBaseUrl+logo)
                                            .error(R.drawable.no_image)
                                            .into(applogo1);
                                } else {
                                    applogo1.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
                                }

                            } else {
                                onlinell.setVisibility(View.GONE);
                                myDialog.dismiss();
                                Toast.makeText(OnlineCustomerService.this, obj.optString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(OnlineCustomerService.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();

                        Toast.makeText(OnlineCustomerService.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {


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
}
