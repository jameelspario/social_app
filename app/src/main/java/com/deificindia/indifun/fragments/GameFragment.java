package com.deificindia.indifun.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.deificindia.indifun.pojo.getsilvrmedal.ResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class GameFragment extends Fragment {

    private RecyclerView silvermedallist;
    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    private SilverMedalAdapter silverMedalAdapter;
    private TextView nosilvermedals;

    public GameFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_games_hourly, container, false);
        silvermedallist=v.findViewById(R.id.silvermedallist);
        nosilvermedals=v.findViewById(R.id.nosilvermedals);
        getsilvermedal();

        return v;
    }


    private void getsilvermedal() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETSILVERMEDAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                myDialog.dismiss();
                                silvermedallist.setVisibility(View.VISIBLE);
                                nosilvermedals.setVisibility(View.GONE);
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
                                    resultItems.add(resultItem);
                                }

                                silverMedalAdapter = new SilverMedalAdapter(getActivity());
                                silvermedallist.setLayoutManager(new GridLayoutManager(getActivity(),3));
                                silvermedallist.setAdapter(silverMedalAdapter);

                                silverMedalAdapter.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems.get(i));

                                    silverMedalAdapter.addItem(channelBeans);
                                }


                            } else {
                                myDialog.dismiss();
                                silvermedallist.setVisibility(View.GONE);
                                nosilvermedals.setVisibility(View.VISIBLE);
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                silverMedalAdapter = new SilverMedalAdapter(getActivity());
                                silvermedallist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                silvermedallist.setAdapter(silverMedalAdapter);
                                silverMedalAdapter.notifyDataSetChanged();
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


    private class SilverMedalAdapter extends RecyclerView.Adapter<SilverMedalAdapter.ViewHolder> {

        private ArrayList<ResultItem> checkinitems = new ArrayList<>();
        private Context context;


        public SilverMedalAdapter(Context context) {
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
}
