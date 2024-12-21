package com.deificindia.indifun.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.pojo.searchuserpojo.ResultItem;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchUserActivity extends AppCompatActivity {


    private ImageView img_back,applogo1;
    private TextView txt_header_title;
    private EditText seachtextet;
    private String edittext;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    private RecyclerView searchlist;
    private TextView nousers;
    private SearchUserAdpater searchUserAdpater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        img_back=findViewById(R.id.img_back);
        searchlist=findViewById(R.id.searchlist);
        nousers=findViewById(R.id.nousers);
        seachtextet=findViewById(R.id.seachtextet);
        txt_header_title=findViewById(R.id.txt_header_title);
        txt_header_title.setText("Search / Add User");

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        seachtextet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    seachtextet.clearFocus();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(seachtextet.getWindowToken(), 0);

                    edittext = seachtextet.getText().toString().trim();
                    searchuser(edittext);
                    return true;
                }
                return false;
            }
        });
    }

    private void searchuser(final String edittext1) {

        myDialog = DialogUtils.showProgressDialog(SearchUserActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEARCHUSER,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                searchlist.setVisibility(View.VISIBLE);
                                nousers.setVisibility(View.GONE);
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem result = new ResultItem();
                                    result.setId(qstnArray.getString("id").toString());
                                    result.setUid(qstnArray.getString("uid").toString());
                                    result.setFullName(qstnArray.getString("full_name").toString());
                                    result.setEmail(qstnArray.getString("email").toString());
                                    result.setContact(qstnArray.getString("contact").toString());
                                   // result.setLatitude(qstnArray.getString("latitude"));
                                  //  result.setLongitude(qstnArray.getString("longitude"));
                                    result.setProfilePicture(qstnArray.getString("profile_picture").toString());
                                  //  result.setAge(qstnArray.getString("age"));
                                    result.setDob(qstnArray.getString("dob").toString());
                                    result.setGender(qstnArray.getString("gender").toString());
                                    result.setIsVerified(qstnArray.getString("is_verified").toString());
                                    resultItems.add(result);
                                }

                                searchUserAdpater = new SearchUserAdpater(SearchUserActivity.this);
                                searchlist.setLayoutManager(new LinearLayoutManager(SearchUserActivity.this));
                                searchlist.setAdapter(searchUserAdpater);

                                searchUserAdpater.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems.get(i));

                                    searchUserAdpater.addItem(channelBeans);
                                }


                            } else {
                                myDialog.dismiss();
                                searchlist.setVisibility(View.GONE);
                                nousers.setVisibility(View.VISIBLE);
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                searchUserAdpater = new SearchUserAdpater(SearchUserActivity.this);
                                searchlist.setLayoutManager(new LinearLayoutManager(SearchUserActivity.this));
                                searchlist.setAdapter(searchUserAdpater);
                                searchUserAdpater.notifyDataSetChanged();
                                //    Toast.makeText(BroadcastsWatchActivity.this, obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Toast.makeText(SearchUserActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(SearchUserActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("full_name", edittext1);
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

    private class SearchUserAdpater extends RecyclerView.Adapter<SearchUserAdpater.ViewHolder> {

        private ArrayList<ResultItem> resultItems1 = new ArrayList<>();
        private Context context;


        public SearchUserAdpater(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.searchlist_row, parent, false);


            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            holder.username.setText(resultItems1.get(position).getFullName());
            holder.gender.setText(resultItems1.get(position).getGender());
            if (resultItems1.get(position).getProfilePicture() != null && !resultItems1.get(position).getProfilePicture().isEmpty()) {

                Picasso.get().load(URLs.UserImageBaseUrl + resultItems1.get(position)
                        .getProfilePicture())
                        .error(R.drawable.no_image)
                        .into(holder.userimage);
            } else {
                holder.userimage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            }
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

            private TextView gender, username;
            private ImageView userimage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.userimage = itemView.findViewById(R.id.userimage);
                this.gender = itemView.findViewById(R.id.gender);
                this.username = itemView.findViewById(R.id.username);
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
