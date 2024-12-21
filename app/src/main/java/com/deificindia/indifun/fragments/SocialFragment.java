package com.deificindia.indifun.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.pojo.checkinmedalpojo.ResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SocialFragment extends Fragment {

    private RecyclerView checkmedalist, sharelivelist, rechargelist;
    private LinearLayout checkmedalll, sharelivell, rechargell;
    private ArrayList<ResultItem> resultItems1 = new ArrayList<>();
    private ArrayList<com.deificindia.indifun.pojo.sharelivemedal.ResultItem> resultItems2 = new ArrayList<>();
    private ArrayList<com.deificindia.indifun.pojo.rechargemedal.ResultItem> resultItems3 = new ArrayList<>();
    private CheckinMedalAdapter checkinMedalAdapter;
    private ShareLiveAdapter shareLiveAdapter;
    private RechargeMedalAdapter rechargeMedalAdapter;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private TextView checkmedals,sharelive,recharge;

    public SocialFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_social_hourly, container, false);
        rechargelist = v.findViewById(R.id.rechargelist);
        checkmedalist = v.findViewById(R.id.checkmedalist);
        sharelivelist = v.findViewById(R.id.sharelivelist);
        checkmedalll = v.findViewById(R.id.checkmedalll);
        sharelivell = v.findViewById(R.id.sharelivell);
        rechargell = v.findViewById(R.id.rechargell);
        checkmedals = v.findViewById(R.id.checkmedals);
        sharelive = v.findViewById(R.id.sharelive);
        recharge = v.findViewById(R.id.recharge);
        
        getcheckinmedals();
        getsharelivemedals();
        getrechargeedals();

        return v;
    }


    private void getcheckinmedals() {
         Dialog myDialog;
         Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETCHECKINMEDAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                if (resultItems1 != null) {
                                    resultItems1.clear();
                                }
                                myDialog.dismiss();
                                checkmedalll.setVisibility(View.VISIBLE);
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setMedalCategory(qstnArray.getString("medal_category"));
                                    resultItem.setMedalSubcategory(qstnArray.getString("medal_subcategory"));
                                    resultItem.setProgressLimit(qstnArray.getString("progress_limit"));
                                    resultItem.setAchievement(qstnArray.getString("achievement"));
                                    resultItem.setEntryDate(qstnArray.getString("entry_date"));
                                    resultItems1.add(resultItem);
                                }

                                checkinMedalAdapter = new CheckinMedalAdapter(getActivity());
                                checkmedalist.setLayoutManager(new GridLayoutManager(getActivity(),3));
                                checkmedalist.setAdapter(checkinMedalAdapter);

                                checkinMedalAdapter.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems1.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems1.get(i));

                                    checkinMedalAdapter.addItem(channelBeans);
                                }


                            } else {
                                myDialog.dismiss();
                                checkmedalll.setVisibility(View.GONE);
                                if (resultItems1 != null) {
                                    resultItems1.clear();
                                }
                                checkinMedalAdapter = new CheckinMedalAdapter(getActivity());
                                checkmedalist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                checkmedalist.setAdapter(checkinMedalAdapter);
                                checkinMedalAdapter.notifyDataSetChanged();
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
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

                return null;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private class CheckinMedalAdapter extends RecyclerView.Adapter<CheckinMedalAdapter.ViewHolder> {

        private ArrayList<ResultItem> checkinitems = new ArrayList<>();
        private Context context;


        public CheckinMedalAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checkinmedallist_row, parent, false);


            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            holder.progress.setText(checkinitems.get(position).getAchievement()+"/"+checkinitems.get(position).getProgressLimit());

           /* if (resultItems1.get(position).getUserImage() != null && !resultItems1.get(position).getUserImage().isEmpty()) {

                Picasso.with(getActivity()).load(URLs.UserImageBaseUrl + resultItems1.get(position).getUserImage()).error(R.drawable.no_image).into(holder.userimage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("TAG", "onSuccess");
                    }

                    @Override
                    public void onError() {
                        //  Toast.makeText(DocumnetManagementActivity.this, "An error occurred in loading image", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                holder.userimage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            }*/
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
            return checkinitems.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void addItem(ArrayList<ResultItem> channelBeans) {

            checkinitems.addAll(channelBeans);
            notifyDataSetChanged();
        }

        public void clearData() {
            checkinitems.clear(); // clear list
            notifyDataSetChanged(); // let your adapter know about the changes and reload view.
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView progress;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.progress = itemView.findViewById(R.id.progress);
            }
        }
    }




    //sharelive medal

    private void getsharelivemedals() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETSHARELIVEMEDAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                if (resultItems2 != null) {
                                    resultItems2.clear();
                                }
                                myDialog.dismiss();
                                sharelivell.setVisibility(View.VISIBLE);
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    com.deificindia.indifun.pojo.sharelivemedal.ResultItem resultItem = new com.deificindia.indifun.pojo.sharelivemedal.ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setMedalCategory(qstnArray.getString("medal_category"));
                                    resultItem.setMedalSubcategory(qstnArray.getString("medal_subcategory"));
                                    resultItem.setProgressLimit(qstnArray.getString("progress_limit"));
                                    resultItem.setAchievement(qstnArray.getString("achievement"));
                                    resultItem.setEntryDate(qstnArray.getString("entry_date"));
                                    resultItems2.add(resultItem);
                                }

                                shareLiveAdapter = new ShareLiveAdapter(getActivity());
                                sharelivelist.setLayoutManager(new GridLayoutManager(getActivity(),3));
                                sharelivelist.setAdapter(shareLiveAdapter);

                                shareLiveAdapter.notifyDataSetChanged();
                                ArrayList<com.deificindia.indifun.pojo.sharelivemedal.ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems2.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems2.get(i));

                                    shareLiveAdapter.addItem(channelBeans);
                                }


                            } else {
                                myDialog.dismiss();
                                sharelivell.setVisibility(View.GONE);
                                if (resultItems2 != null) {
                                    resultItems2.clear();
                                }
                                shareLiveAdapter = new ShareLiveAdapter(getActivity());
                                sharelivelist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                sharelivelist.setAdapter(shareLiveAdapter);
                                shareLiveAdapter.notifyDataSetChanged();
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
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
                params.put("medal_category", "social");
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private class ShareLiveAdapter extends RecyclerView.Adapter<ShareLiveAdapter.ViewHolder> {

        private ArrayList<com.deificindia.indifun.pojo.sharelivemedal.ResultItem> shareitems = new ArrayList<>();
        private Context context;


        public ShareLiveAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checkinmedallist_row, parent, false);


            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            holder.progress.setText(shareitems.get(position).getAchievement()+"/"+shareitems.get(position).getProgressLimit());

           /* if (resultItems1.get(position).getUserImage() != null && !resultItems1.get(position).getUserImage().isEmpty()) {

                Picasso.with(getActivity()).load(URLs.UserImageBaseUrl + resultItems1.get(position).getUserImage()).error(R.drawable.no_image).into(holder.userimage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("TAG", "onSuccess");
                    }

                    @Override
                    public void onError() {
                        //  Toast.makeText(DocumnetManagementActivity.this, "An error occurred in loading image", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                holder.userimage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            }*/
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
            return shareitems.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void addItem(ArrayList<com.deificindia.indifun.pojo.sharelivemedal.ResultItem> channelBeans) {

            shareitems.addAll(channelBeans);
            notifyDataSetChanged();
        }

        public void clearData() {
            shareitems.clear(); // clear list
            notifyDataSetChanged(); // let your adapter know about the changes and reload view.
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView progress;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.progress = itemView.findViewById(R.id.progress);
            }
        }
    }



    //recharge medals

    private void getrechargeedals() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETRCHARGEMEDAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                if (resultItems3 != null) {
                                    resultItems3.clear();
                                }
                                myDialog.dismiss();
                                rechargell.setVisibility(View.VISIBLE);
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    com.deificindia.indifun.pojo.rechargemedal.ResultItem resultItem = new com.deificindia.indifun.pojo.rechargemedal.ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setMedalCategory(qstnArray.getString("medal_category"));
                                    resultItem.setMedalSubcategory(qstnArray.getString("medal_subcategory"));
                                    resultItem.setProgressLimit(qstnArray.getString("progress_limit"));
                                    resultItem.setAchievement(qstnArray.getString("achievement"));
                                    resultItem.setEntryDate(qstnArray.getString("entry_date"));
                                    resultItems3.add(resultItem);
                                }

                                rechargeMedalAdapter = new RechargeMedalAdapter(getActivity());
                                rechargelist.setLayoutManager(new GridLayoutManager(getActivity(),3));
                                rechargelist.setAdapter(rechargeMedalAdapter);

                                rechargeMedalAdapter.notifyDataSetChanged();
                                ArrayList<com.deificindia.indifun.pojo.rechargemedal.ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems3.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems3.get(i));

                                    rechargeMedalAdapter.addItem(channelBeans);
                                }


                            } else {
                                myDialog.dismiss();
                                rechargell.setVisibility(View.GONE);
                                if (resultItems3 != null) {
                                    resultItems3.clear();
                                }
                                rechargeMedalAdapter = new RechargeMedalAdapter(getActivity());
                                rechargelist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                rechargelist.setAdapter(rechargeMedalAdapter);
                                rechargeMedalAdapter.notifyDataSetChanged();
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
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
                params.put("medal_category", "social");
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private class RechargeMedalAdapter extends RecyclerView.Adapter<RechargeMedalAdapter.ViewHolder> {

        private ArrayList<com.deificindia.indifun.pojo.rechargemedal.ResultItem> rechargeitems = new ArrayList<>();
        private Context context;


        public RechargeMedalAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checkinmedallist_row, parent, false);


            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            holder.progress.setText(rechargeitems.get(position).getAchievement()+"/"+rechargeitems.get(position).getProgressLimit());

           /* if (resultItems1.get(position).getUserImage() != null && !resultItems1.get(position).getUserImage().isEmpty()) {

                Picasso.with(getActivity()).load(URLs.UserImageBaseUrl + resultItems1.get(position).getUserImage()).error(R.drawable.no_image).into(holder.userimage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("TAG", "onSuccess");
                    }

                    @Override
                    public void onError() {
                        //  Toast.makeText(DocumnetManagementActivity.this, "An error occurred in loading image", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                holder.userimage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            }*/
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
            return rechargeitems.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void addItem(ArrayList<com.deificindia.indifun.pojo.rechargemedal.ResultItem> channelBeans) {

            rechargeitems.addAll(channelBeans);
            notifyDataSetChanged();
        }

        public void clearData() {
            rechargeitems.clear(); // clear list
            notifyDataSetChanged(); // let your adapter know about the changes and reload view.
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView progress;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.progress = itemView.findViewById(R.id.progress);
            }
        }
    }
}
