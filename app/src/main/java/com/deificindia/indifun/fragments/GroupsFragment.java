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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.deificindia.indifun.pojo.getgrouplistbycategory.ResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupsFragment extends Fragment {


    private String groupid;
    private int id;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    private RecyclerView grouplist;
    private TextView nogroups;
    private Grouplistadapter grouplistadapter;

    public GroupsFragment(String groupid) {
        this.groupid = groupid;
    }

    public GroupsFragment() {

    }


    // newInstance constructor for creating fragment with arguments
    public static GroupsFragment newInstance(int id) {
        GroupsFragment fragmentFirst = new GroupsFragment();
        Bundle args = new Bundle();
        //   args.putInt("someInt", page);
        args.putInt("id", id);
        //   args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id", 0);
        // tabpageno = getArguments().getInt("someInt", 0);
        //  title = getArguments().getString("someTitle");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.grouplist_frgament, container, false);
        grouplist=v.findViewById(R.id.grouplist);
        nogroups=v.findViewById(R.id.nogroups);
        getgrouplistbycat(id);
        return v;
    }




    private void getgrouplistbycat(int groupid1) {

        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETGROUPLISTBYCAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                grouplist.setVisibility(View.VISIBLE);
                                nogroups.setVisibility(View.GONE);
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setGroupName(qstnArray.getString("group_name"));
                                    resultItem.setMadeBy(qstnArray.getString("made_by"));
                                    resultItem.setGroupCategory(qstnArray.getString("group_category"));
                                    resultItem.setDescription(qstnArray.getString("description"));
                                    resultItem.setEntryDate(qstnArray.getString("entry_date"));
                                    resultItems.add(resultItem);
                                }

                                grouplistadapter = new Grouplistadapter(getActivity());
                                grouplist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                grouplist.setAdapter(grouplistadapter);

                                grouplistadapter.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems.get(i));

                                    grouplistadapter.addItem(channelBeans);
                                }


                            } else {
                                myDialog.dismiss();
                                grouplist.setVisibility(View.GONE);
                                nogroups.setVisibility(View.VISIBLE);
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                grouplist.setVisibility(View.VISIBLE);
                                grouplistadapter = new Grouplistadapter(getActivity());
                                grouplist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                grouplist.setAdapter(grouplistadapter);
                                grouplistadapter.notifyDataSetChanged();
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
                params.put("group_category", String.valueOf(groupid1));
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private class Grouplistadapter extends RecyclerView.Adapter<Grouplistadapter.ViewHolder> {

        private ArrayList<ResultItem> resultItems1 = new ArrayList<>();
        private Context context;


        public Grouplistadapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grouplist_row, parent, false);


            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


            holder.groupname.setText(resultItems1.get(position).getGroupName());
            holder.groupdesc.setText(resultItems1.get(position).getDescription());
            holder.creationdate.setText(resultItems1.get(position).getEntryDate());


            holder.joingroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    joingroup(resultItems1.get(position).getId());
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

        public void addItem(ArrayList<ResultItem> channelBeans) {

            resultItems1.addAll(channelBeans);
            notifyDataSetChanged();
        }
        private void joingroup(final String groupid1) {
            Dialog myDialog;
            Progress_Dialogue DialogUtils = null;
            myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_JOINGROUP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.optInt("status") == 1) {
                                    myDialog.dismiss();
                                   getActivity().getSupportFragmentManager().popBackStackImmediate();
                                } else {
                                    myDialog.dismiss();
                                    Toast.makeText(getActivity(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                myDialog.dismiss();
                                Toast.makeText(getActivity(), "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            myDialog.dismiss();
                            Toast.makeText(getActivity(), "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                    params.put("user_group_id", groupid1);

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
        public void clearData() {
            resultItems1.clear(); // clear list
            notifyDataSetChanged(); // let your adapter know about the changes and reload view.
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView creationdate,groupname,groupdesc,joingroup;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.creationdate = itemView.findViewById(R.id.creationdate);
                this.groupname = itemView.findViewById(R.id.groupname);
                this.groupdesc = itemView.findViewById(R.id.groupdesc);
                this.joingroup = itemView.findViewById(R.id.joingroup);

            }
        }
    }
}
