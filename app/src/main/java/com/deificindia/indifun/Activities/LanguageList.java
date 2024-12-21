package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.deificindia.indifun.pojo.alllangaugespojo.ResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LanguageList extends AppCompatActivity {


    private ImageView img_back;
    private TextView txt_header_title;
    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    private ArrayList<com.deificindia.indifun.pojo.userlangpojo.ResultItem> userresultItems = new ArrayList<>();
    private RecyclerView langaugelist;
    private TextView nolanguages;
    private LanguagesAdapter languagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_list);
        img_back = findViewById(R.id.img_back);
        txt_header_title = findViewById(R.id.txt_header_title);
        langaugelist = findViewById(R.id.langaugelist);
        nolanguages = findViewById(R.id.nolanguages);
        txt_header_title.setText("Languages");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


      //  getuserlangauges();
        getalllangauges();
    }

    private void getalllangauges() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(LanguageList.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETLANGUAGELIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                langaugelist.setVisibility(View.VISIBLE);
                                nolanguages.setVisibility(View.GONE);
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setLanguage(qstnArray.getString("language"));
                                    resultItem.setStatus(qstnArray.getString("status"));
                                    resultItem.setLanguage_id(qstnArray.getString("language_id"));
                                    resultItems.add(resultItem);
                                }

                                /*if(resultItems!=null && !resultItems.isEmpty()){
                                    for(int i=0;i<resultItems.size();i++){
                                        for(int j=0;j<userresultItems.size();j++){
                                        if(userresultItems.get(j).getLanguageName().equalsIgnoreCase(resultItems.get(i).getLanguage())){
                                            resultItems.get(i).setSelected(true);
                                        }else{
                                            resultItems.get(i).setSelected(false);
                                        }
                                        }
                                    }
                                }*/


                                languagesAdapter = new LanguagesAdapter(LanguageList.this);
                                langaugelist.setLayoutManager(new LinearLayoutManager(LanguageList.this));
                                langaugelist.setAdapter(languagesAdapter);

                                languagesAdapter.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems.get(i));

                                    languagesAdapter.addItem(channelBeans);
                                }


                            } else {
                                myDialog.dismiss();
                                langaugelist.setVisibility(View.GONE);
                                nolanguages.setVisibility(View.VISIBLE);
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                languagesAdapter = new LanguagesAdapter(LanguageList.this);
                                langaugelist.setLayoutManager(new LinearLayoutManager(LanguageList.this));
                                langaugelist.setAdapter(languagesAdapter);
                                languagesAdapter.notifyDataSetChanged();
                                //    Toast.makeText(LanguageList.this, obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(LanguageList.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(LanguageList.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private void getuserlangauges() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(LanguageList.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETUSERLANGUAGELIST,
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
                                    com.deificindia.indifun.pojo.userlangpojo.ResultItem resultItem = new com.deificindia.indifun.pojo.userlangpojo.ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setLanguageName(qstnArray.getString("language_name"));
                                    resultItem.setLanguageId(qstnArray.getString("language_id"));
                                    resultItem.setUserId(qstnArray.getString("user_id"));
                                    userresultItems.add(resultItem);
                                }

                            } else {
                                myDialog.dismiss();

                                //    Toast.makeText(LanguageList.this, obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(LanguageList.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(LanguageList.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

        private ArrayList<ResultItem> resultItems1 = new ArrayList<>();
        private Context context;


        public LanguagesAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.langaueslist_row, parent, false);


            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            holder.languagename.setText(resultItems1.get(position).getLanguage());

            if(resultItems1.get(position).getStatus().equalsIgnoreCase("1")){
                holder.catcircleselect1.setVisibility(View.VISIBLE);
                holder.catcircleselect.setVisibility(View.GONE);
            }else{
                holder.catcircleselect1.setVisibility(View.GONE);
                holder.catcircleselect.setVisibility(View.VISIBLE);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resultItems1.get(position).getStatus().equalsIgnoreCase("1")) {
                        unchecklanguage(holder, position, resultItems1.get(position).getLanguage_id());

                    }else{
                        updatelanguage(holder, position, resultItems1.get(position).getId());
                    }
                }
            });


            /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), HistoryDetailActivity1.class)
                            .putExtra(AppConstants.CUSTOMERNAME, deliveryOrderDataItems1.get(position).getCustomerName())
                            .putExtra(AppConstants.ORDERID, deliveryOrderDataItems1.get(position).getDeliveryOrderRand())
                            .putExtra(AppConstants.SUBTOTAL, deliveryOrderDataItems1.get(position).getProductPayment())
                            .putExtra(AppConstants.EPLADDRESS, deliveryOrderDataItems1.get(position).getPlAddress() + " , " + deliveryOrderDataItems1.get(position).getEPlCityName() + " , " + deliveryOrderDataItems1.get(position).getEPlCountryName())
                            .putExtra(AppConstants.APLADDRESS, deliveryOrderDataItems1.get(position).getPlAddress() + " , " + deliveryOrderDataItems1.get(position).getAPlCityName() + " , " + deliveryOrderDataItems1.get(position).getAPlCountryName())
                            .putExtra(AppConstants.EDLADDRESS, deliveryOrderDataItems1.get(position).getDlAddress() + " , " + deliveryOrderDataItems1.get(position).getEDlCityName() + " , " + deliveryOrderDataItems1.get(position).getEDlCountryName())
                            .putExtra(AppConstants.ADLADDRESS, deliveryOrderDataItems1.get(position).getDlAddress() + " , " + deliveryOrderDataItems1.get(position).getADlCityName() + " , " + deliveryOrderDataItems1.get(position).getADlCountryName())
                            .putExtra(AppConstants.DELNOTE, deliveryOrderDataItems1.get(position).getDlNote())
                            .putExtra(AppConstants.DISTANCE, holder.distancetv.getText().toString().trim())
                            .putExtra(AppConstants.DELORDERID, deliveryOrderDataItems1.get(position).getDeliveryOrderId() )
                            .putExtra(AppConstants.DELORDERSTATUS, deliveryOrderDataItems1.get(position).getDeliveryOrderStatus() )
                            .putExtra(AppConstants.PAYMENTTYPE, deliveryOrderDataItems1.get(position).getProductPaymentMethod()));
                    ;

                }
            });*/
        }


        private void updatelanguage(ViewHolder holder, final int position1, final String langid) {
            Dialog myDialog;
            Progress_Dialogue DialogUtils = null;
            myDialog = DialogUtils.showProgressDialog(LanguageList.this, "Loading Please Wait...");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATELANG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.optInt("status") == 1) {
                                    myDialog.dismiss();
                                    holder.catcircleselect1.setVisibility(View.VISIBLE);
                                    holder.catcircleselect.setVisibility(View.GONE);
                                    getalllangauges();
                                } else {
                                    myDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                myDialog.dismiss();
                                Toast.makeText(LanguageList.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            myDialog.dismiss();
                            Toast.makeText(LanguageList.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("language_id",langid );
                    params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
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


        private void unchecklanguage(ViewHolder holder, final int position1, final String langid) {
            Dialog myDialog;
            Progress_Dialogue DialogUtils = null;
            myDialog = DialogUtils.showProgressDialog(LanguageList.this, "Loading Please Wait...");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UNCHECKLANG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.optInt("status") == 1) {
                                    myDialog.dismiss();
                                    holder.catcircleselect1.setVisibility(View.GONE);
                                    holder.catcircleselect.setVisibility(View.VISIBLE);
                                    notifyItemChanged(position1);
                                    getalllangauges();

                                } else {
                                    myDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                myDialog.dismiss();
                                Toast.makeText(LanguageList.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            myDialog.dismiss();
                            Toast.makeText(LanguageList.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id",langid );
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
        @Override
        public int getItemCount() {
            return resultItems1.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void addItem(ArrayList<ResultItem> channelBeans) {

            resultItems1.addAll(channelBeans);
            notifyDataSetChanged();
        }

        public void clearData() {
            resultItems1.clear(); // clear list
            notifyDataSetChanged(); // let your adapter know about the changes and reload view.
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView languagename;
            private ImageView catcircleselect1, catcircleselect;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.languagename = itemView.findViewById(R.id.languagename);
                this.catcircleselect1 = itemView.findViewById(R.id.catcircleselect1);
                this.catcircleselect = itemView.findViewById(R.id.catcircleselect);
            }
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
