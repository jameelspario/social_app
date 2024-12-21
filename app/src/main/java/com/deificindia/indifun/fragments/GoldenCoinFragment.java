package com.deificindia.indifun.fragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.Payment.Payment;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.pojo.goldencoins.ResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GoldenCoinFragment extends Fragment {

    private Dialog myDialog;

    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    private RecyclerView goldencoinlist;
    private TextView nocoins,totalcoinstv;
    private GoldenCOinAdapter goldenCOinAdapter;
    private int   totalcoinstv1;

    private Button button_Click;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goldencoins, container, false);
        nocoins = v.findViewById(R.id.nocoins);
        totalcoinstv = v.findViewById(R.id.totalcoinstv);
        button_Click = v.findViewById(R.id.button_Click);
        button_Click.setOnClickListener(v1->{
            Intent razor=new Intent(getContext(),Payment.class);
            startActivity(razor);
        });
        goldencoinlist = v.findViewById(R.id.goldencoinlist);
        getgoldencoins();
        return v;
    }


    private void getgoldencoins() {

        myDialog = Progress_Dialogue.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETGOLDENCOINS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            myDialog.dismiss();
                            if (obj.optInt("status") == 1) {
                                goldencoinlist.setVisibility(View.VISIBLE);
                                nocoins.setVisibility(View.GONE);

                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getInt("id"));
                                    resultItem.setCoinAmount(qstnArray.getInt("coin_amount"));
                                    resultItem.setCoin_point(qstnArray.getInt("coin_point"));
                                    resultItem.setEntrydate(qstnArray.getString("entrydate"));
                                    resultItem.setCoinType(qstnArray.getString("coin_type"));

                                    try {
                                        if (qstnArray.has("golden_coins")) {
                                            resultItem.setGolden_coins(qstnArray.getInt("golden_coins"));
                                        }
                                    }catch (Exception e){

                                    }
                                    resultItems.add(resultItem);
                                }

                                if(resultItems.size()>0) {
                                    totalcoinstv1 = resultItems.get(0).getGolden_coins();
                                    totalcoinstv.setText("" + totalcoinstv1);
                                }
                                goldenCOinAdapter = new GoldenCOinAdapter(getActivity());
                                goldencoinlist.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                                goldencoinlist.setAdapter(goldenCOinAdapter);

                                goldenCOinAdapter.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems.get(i));

                                    goldenCOinAdapter.addItem(channelBeans);
                                }


                            } else {
                                goldencoinlist.setVisibility(View.GONE);
                                nocoins.setVisibility(View.VISIBLE);
                                goldencoinlist.setVisibility(View.GONE);
                                nocoins.setVisibility(View.VISIBLE);
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                goldencoinlist.setVisibility(View.VISIBLE);
                                goldenCOinAdapter = new GoldenCOinAdapter(getActivity());
                                goldencoinlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                goldencoinlist.addItemDecoration(new EqualSpacingItemDecoration(8));
                                goldencoinlist.setAdapter(goldenCOinAdapter);
                                goldenCOinAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), obj.optString("msg_en"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(getActivity(), getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(getActivity(), getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", MySharePref.getUserId());
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private class GoldenCOinAdapter extends RecyclerView.Adapter<GoldenCOinAdapter.ViewHolder> {

        private ArrayList<ResultItem> resultItems1 = new ArrayList<>();
        private Context context;

        public GoldenCOinAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v  = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.goldenlost_row, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            holder.coinamount.setText("INR " + resultItems1.get(position).getCoinAmount());
            holder.coinpoint.setText( ""+resultItems1.get(position).getCoin_point());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // purchasecoins(holder,position,String.valueOf(resultItems1.get(position).getCoinAmount()));
                    //Toast.makeText(context, "You are about to pay"+resultItems1.get(position).getCoinAmount() +"INR", Toast.LENGTH_SHORT).show();
                    Intent myinte = new Intent(getActivity(), Payment.class) ;
                    myinte.putExtra("mycoin_amount",resultItems1.get(position).getCoinAmount());
                    myinte.putExtra("mycoin",resultItems1.get(position).getCoin_point());
                    myinte.putExtra("myid",resultItems1.get(position).getId());
                    System.out.println("coincount12"+resultItems1.get(position).getCoin_point());
                    startActivity(myinte);


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

        private void purchasecoins(final ViewHolder holder,final int position, final String coinamount) {
            myDialog = Progress_Dialogue.showProgressDialog(getActivity(), "Loading Please Wait...");
if (position   ==0)
{
    Intent myinte = new Intent(getActivity(), Payment.class) ;
    myinte.putExtra("mycoin_amount",resultItems1.get(position).getCoinAmount());
    myinte.putExtra("mycoin",resultItems1.get(position).getCoin_point());
    myinte.putExtra("myid",resultItems1.get(position).getCoin_point());
    startActivity(myinte);

}
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_PURCHASECOINS,
                    response -> {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();

                                new AlertDialog.Builder(getActivity())
                                        .setTitle(getString(R.string.coinspurchased))
                                        .setMessage(getString(R.string.succespay))
                                        .setIcon(R.drawable.ic_check_circle_black_24dp)
                                        .setCancelable(false)
                                        .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel()).show();
                            } else {
                                myDialog.dismiss();
                                Toast.makeText(getActivity(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Toast.makeText(getActivity(), " "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        myDialog.dismiss();
                       // Toast.makeText(getActivity(), "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String currentdate = formatter.format(date);
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                    params.put("paid_for", "coins");
                    params.put("transection_id", "665656565");
                    params.put("paid_amount", coinamount);
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

            private TextView coinamount,coinpoint;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.coinamount = itemView.findViewById(R.id.coinamount);
                this.coinpoint = itemView.findViewById(R.id.coinpoint);

            }
        }
    }
}
