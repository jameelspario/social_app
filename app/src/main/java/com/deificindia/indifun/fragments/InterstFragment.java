package com.deificindia.indifun.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.deificindia.indifun.pojo.getinterstsubcatlist.ResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterstFragment extends Fragment {


    private int id;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private ArrayList<ResultItem> resultItems = new ArrayList<>();
    private RecyclerView interstsubcatlist;
    private TextView nointerstsubcatlist;
    private INterstSubCatlistadapter iNterstSubCatlistadapter;

    public InterstFragment() {
        // Required empty public constructor
    }


    // newInstance constructor for creating fragment with arguments
    public static InterstFragment newInstance(int id) {
        InterstFragment fragmentFirst = new InterstFragment();
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
        View v = inflater.inflate(R.layout.fragment_interst, container, false);
        interstsubcatlist=v.findViewById(R.id.interstsubcatlist);
        nointerstsubcatlist=v.findViewById(R.id.nointerstsubcatlist);
        getallinterstlist(id);
        return v;
    }

    private void getallinterstlist(int id1) {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETINTERSTSUBCATLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                interstsubcatlist.setVisibility(View.VISIBLE);
                                nointerstsubcatlist.setVisibility(View.GONE);
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setStatus(qstnArray.getString("status"));
                                    resultItem.setIntrestSubcategory(qstnArray.getString("intrest_subcategory"));
                                    resultItem.setIntrest_id(qstnArray.getString("intrest_id"));
                                    resultItems.add(resultItem);
                                }

                                iNterstSubCatlistadapter = new INterstSubCatlistadapter(getActivity());
                                interstsubcatlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                interstsubcatlist.setAdapter(iNterstSubCatlistadapter);

                                iNterstSubCatlistadapter.notifyDataSetChanged();
                                ArrayList<ResultItem> channelBeans = new ArrayList<>();
                                for (int i = 0; i < resultItems.size(); i++) {

                                    channelBeans.clear();
                                    channelBeans.add(resultItems.get(i));

                                    iNterstSubCatlistadapter.addItem(channelBeans);
                                }


                            } else {
                                myDialog.dismiss();
                                interstsubcatlist.setVisibility(View.GONE);
                                nointerstsubcatlist.setVisibility(View.VISIBLE);
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                iNterstSubCatlistadapter = new INterstSubCatlistadapter(getActivity());
                                interstsubcatlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                interstsubcatlist.setAdapter(iNterstSubCatlistadapter);
                                iNterstSubCatlistadapter.notifyDataSetChanged();
                                //    Toast.makeText(LanguageList.this, obj.optString("result"), Toast.LENGTH_SHORT).show();
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
                params.put("intrest_category_id", String.valueOf(id1));
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                return params;
            }
        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private class INterstSubCatlistadapter extends RecyclerView.Adapter<INterstSubCatlistadapter.ViewHolder> {

        private ArrayList<ResultItem> resultItems1 = new ArrayList<>();
        private Context context;


        public INterstSubCatlistadapter(Context context) {
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


            holder.languagename.setText(resultItems1.get(position).getIntrestSubcategory());

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
                        uncheckinterest(holder, position, resultItems1.get(position).getIntrest_id());

                    }else{
                        updateinterest(holder, position, resultItems1.get(position).getId());
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


        private void updateinterest(ViewHolder holder, final int position1, final String langid) {
            Dialog myDialog;
            Progress_Dialogue DialogUtils = null;
            myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATEINTEREST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.optInt("status") == 1) {
                                    myDialog.dismiss();
                                    holder.catcircleselect1.setVisibility(View.VISIBLE);
                                    holder.catcircleselect.setVisibility(View.GONE);
                                    getallinterstlist(id);
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
                    params.put("intrest_id",langid );
                    params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                    return params;
                }
            };
            IndifunApplication.getInstance().addToRequestQueue(stringRequest);
        }


        private void uncheckinterest(ViewHolder holder, final int position1, final String langid) {
            Dialog myDialog;
            Progress_Dialogue DialogUtils = null;
            myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UNCHECKINTEREST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.optInt("status") == 1) {
                                    myDialog.dismiss();
                                    holder.catcircleselect1.setVisibility(View.GONE);
                                    holder.catcircleselect.setVisibility(View.VISIBLE);
                                   getallinterstlist(id);

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
                    params.put("id",langid );
                    params.put("user_id",IndifunApplication.getInstance().getCredential().getId() );
                    return params;
                }
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
}
