package com.deificindia.indifun.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.Model.abs_plugs.BroadWatched;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.pojo.broadcastwatchedpojo.ResultItem;
import com.deificindia.indifun.rest.RetroCalls;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BroadcastsWatchActivity extends AppCompatActivity {

    private ImageView img_back, applogo1, deleltbroadcasts;
    private TextView txt_header_title;
    private Dialog myDialog;
    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    private RecyclerView broadcastlist;
    private TextView nobroadcast;
    private BroadcastsWatchAdapter broadcastsWatchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcasts_watch);
        img_back = findViewById(R.id.img_back);
        nobroadcast = findViewById(R.id.nobroadcast);
        broadcastlist = findViewById(R.id.broadcastlist);
        txt_header_title = findViewById(R.id.txt_header_title);
        deleltbroadcasts = findViewById(R.id.deleltbroadcasts);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        deleltbroadcasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(BroadcastsWatchActivity.this);
                alert.setTitle(getString(R.string.alert));
                alert.setMessage(getString(R.string.deletemsg));
                alert.setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                deletebroadcast();

                                dialog.dismiss();

                            }
                        }).setNegativeButton(getString(R.string.cancel1), null);

                AlertDialog alert1 = alert.create();
                alert1.show();
            }
        });

        broadcastsWatchAdapter = new BroadcastsWatchAdapter(BroadcastsWatchActivity.this);
        broadcastlist.setLayoutManager(new LinearLayoutManager(BroadcastsWatchActivity.this));
        broadcastlist.setAdapter(broadcastsWatchAdapter);

        getBroadInfo();
    }

    private void deletebroadcast() {

       myDialog = Progress_Dialogue.showProgressDialog(BroadcastsWatchActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DELETEBROADCASTWATCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                getBroadInfo();
                                deleltbroadcasts.setVisibility(View.GONE);

                            } else {
                                myDialog.dismiss();
                                //    Toast.makeText(BroadcastsWatchActivity.this, obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(BroadcastsWatchActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(BroadcastsWatchActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                params.put("broadcast_id", "all");
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void getBroadInfo(){
        RetroCalls.instance().callType(1)
                .withUID()
        .broadast_by_userid((a,b)->{
            if(b!=null && b.getStatus()==1 && b.getResult()!=null){

                //broadcastsWatchAdapter.notifyDataSetChanged();

                broadcastsWatchAdapter.addItem(b.getResult());

            }else {
                broadcastlist.setVisibility(View.GONE);
                nobroadcast.setVisibility(View.VISIBLE);
                broadcastlist.setAdapter(null);
            }
        });
    }
    /*private void getbroadcastwatched() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETBROADCASTWATCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                //myDialog.dismiss();
                                broadcastlist.setVisibility(View.VISIBLE);
                                nobroadcast.setVisibility(View.GONE);
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setBroadcastId(qstnArray.getString("broadcast_id"));
                                    resultItem.setUserId(qstnArray.getString("user_id"));
                                    resultItem.setAction(qstnArray.getString("action"));
                                    resultItem.setEntryDate(qstnArray.getString("entry_date"));
                                    resultItem.setEntryTime(qstnArray.getString("entry_time"));
                                    resultItem.setIsLive(qstnArray.getString("is_live"));
                                    resultItem.setUserName(qstnArray.getString("user_name"));
                                    resultItem.setTitle(qstnArray.getString("title"));
                                    resultItem.setUserImage(qstnArray.getString("user_image"));
                                    resultItems.add(resultItem);
                                }

                                broadcastsWatchAdapter = new BroadcastsWatchAdapter(BroadcastsWatchActivity.this);
                                broadcastlist.setLayoutManager(new LinearLayoutManager(BroadcastsWatchActivity.this));
                                broadcastlist.setAdapter(broadcastsWatchAdapter);

                                broadcastsWatchAdapter.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems.get(i));

                                    broadcastsWatchAdapter.addItem(channelBeans);
                                }

                            } else {
                                //myDialog.dismiss();
                                broadcastlist.setVisibility(View.GONE);
                                nobroadcast.setVisibility(View.VISIBLE);
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                broadcastsWatchAdapter = new BroadcastsWatchAdapter(BroadcastsWatchActivity.this);
                                broadcastlist.setLayoutManager(new LinearLayoutManager(BroadcastsWatchActivity.this));
                                broadcastlist.setAdapter(broadcastsWatchAdapter);
                                broadcastsWatchAdapter.notifyDataSetChanged();
                                //    Toast.makeText(BroadcastsWatchActivity.this, obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                           // myDialog.dismiss();
                            Toast.makeText(BroadcastsWatchActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime)+ " " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //myDialog.dismiss();
                        Toast.makeText(BroadcastsWatchActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime) + " " + error.getMessage(), Toast.LENGTH_SHORT).show();

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
*/
    private class BroadcastsWatchAdapter extends RecyclerView.Adapter<BroadcastsWatchAdapter.ViewHolder> {

        private ArrayList<BroadWatched> resultItems1 = new ArrayList<>();
        private Context context;

        public BroadcastsWatchAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.broadcastlist_row, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            holder.username.setText(resultItems1.get(position).getUser_name());
            holder.broadcastdesc.setText(resultItems1.get(position).getTitle());
            //holder.broadcastaction.setText(resultItems1.get(position).getAction());

            try {
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                Date d = f.parse(resultItems1.get(position).getEntry_time());
                DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                DateFormat time = new SimpleDateFormat("hh:mm a");
                System.out.println("Date: " + date.format(d));
                System.out.println("Time: " + time.format(d));

                holder.broadcasttime.setText(time.format(d));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (resultItems1.get(position).getUser_image() != null && !resultItems1.get(position).getUser_image().isEmpty()) {

                Picasso.get()
                        .load(URLs.UserImageBaseUrl + resultItems1.get(position).getUser_image())
                        .error(R.drawable.no_image)
                        .into(holder.userimage);
            } else {
                holder.userimage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            }

            holder.deleltbroadcasts1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(BroadcastsWatchActivity.this);
                    alert.setTitle(getString(R.string.alert));
                    alert.setMessage(getString(R.string.deletemsg1));
                    alert.setCancelable(false)
                            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    deletebroadcastindividual(resultItems1.get(position).getId());
                                    dialog.dismiss();

                                }
                            }).setNegativeButton(getString(R.string.cancel1), null);

                    AlertDialog alert1 = alert.create();
                    alert1.show();

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

        @Override
        public int getItemCount() {
            return resultItems1.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void addItem(List<BroadWatched> channelBeans) {

            resultItems1.addAll(channelBeans);
            notifyDataSetChanged();
        }

        public void clearData() {
            resultItems1.clear(); // clear list
            notifyDataSetChanged(); // let your adapter know about the changes and reload view.
        }

        private void deletebroadcastindividual(int id1) {

            myDialog = Progress_Dialogue.showProgressDialog(BroadcastsWatchActivity.this, "Loading Please Wait...");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DELETEBROADCASTWATCH,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.optInt("status") == 1) {
                                    myDialog.dismiss();
                                    getBroadInfo();

                                } else {
                                    myDialog.dismiss();
                                    //    Toast.makeText(BroadcastsWatchActivity.this, obj.optString("result"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                myDialog.dismiss();

                                Toast.makeText(BroadcastsWatchActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            myDialog.dismiss();
                            Toast.makeText(BroadcastsWatchActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                    params.put("broadcast_id", String.valueOf(id1));
                    return params;
                }


            };
            IndifunApplication.getInstance().addToRequestQueue(stringRequest);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView username, broadcastdesc, broadcasttime, broadcastaction;
            private ImageView userimage,deleltbroadcasts1;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.userimage = itemView.findViewById(R.id.userimage);
                this.deleltbroadcasts1 = itemView.findViewById(R.id.deleltbroadcasts1);
                this.username = itemView.findViewById(R.id.username);
                this.broadcastdesc = itemView.findViewById(R.id.broadcastdesc);
                this.broadcasttime = itemView.findViewById(R.id.broadcasttime);
                this.broadcastaction = itemView.findViewById(R.id.broadcastaction);
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
        supportFinishAfterTransition();
    }
}