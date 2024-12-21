package com.deificindia.indifun.fragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.Activities.RechargeCoins;
import com.deificindia.indifun.Adapter.AristoPrivillageAdapter;
import com.deificindia.indifun.Adapter.AristrocracyPlan;

import com.deificindia.indifun.Model.GoldenCoinModal;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.AristoPrivileges;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.Aristocracy;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.AristocracyPlan;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.AristocracyPlanResult;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.AristocracySubcription;
import com.deificindia.indifun.rest.AristocracySubcriptionResp;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.deificindia.indifun.Utility.Logger.loge;

/**
 * A simple {@link Fragment} subclass.
 */
public class AristocracyCenterFragment extends Fragment {

    private int id;
    private String title;
    private String image1;
    private String frame;
    private CircleImageView image;
    private TextView titile, buytv;
    RecyclerView recyclerView,recyclerview1;
    FrameLayout profileFrame;
    private AristoPrivillageAdapter adapter;

    private  String valu_of_card,value_id,value_month;

    public AristocracyCenterFragment() {
        // Required empty public constructor
    }

    // newInstance constructor for creating fragment with arguments
//    public static AristocracyCenterFragment newInstance(int id, String s, String title, String image,String frame) {
//        AristocracyCenterFragment fragmentFirst = new AristocracyCenterFragment();
//        Bundle args = new Bundle();
//        //   args.putInt("someInt", page);
//        args.putInt("id", id);
//        args.putString("title", title);
//        args.putString("image", image);
//        args.putString("frame", frame);
//
//        //   args.putString("someTitle", title);
//        fragmentFirst.setArguments(args);
//        return fragmentFirst;
//    }

    public static AristocracyCenterFragment newInstance(int id, String title, String image, String frame) {
        AristocracyCenterFragment fragmentFirst = new AristocracyCenterFragment();
        Bundle args = new Bundle();
        //   args.putInt("someInt", page);
        args.putInt("id", id);
        args.putString("title", title);
        args.putString("image", image);
        args.putString("frame", frame);

        //   args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;

    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getArguments().getInt("id", 0);
        title = getArguments().getString("title", "");
        image1 = getArguments().getString("image", "");
        frame = getArguments().getString("frame", "");

        // tabpageno = getArguments().getInt("someInt", 0);
        //  title = getArguments().getString("someTitle");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aristocracy_center, container, false);
        titile = v.findViewById(R.id.titile);
        image = v.findViewById(R.id.image);
        profileFrame = v.findViewById(R.id.profileFrame);
        profileFrame = v.findViewById(R.id.profileFrame);
        buytv = v.findViewById(R.id.buytv);
        recyclerView = v.findViewById(R.id.recycler);
        known_goldencoin();
        titile.setText(title);

        if (image1 != null && !image1.isEmpty()) {
            Picasso.get().load(URLs.AristocracyImageBaseUrl + image1).error(R.drawable.no_image).into(image);
        } else {
            image.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
        }
        if (frame != null && !frame.isEmpty()) {
            Picasso.get().load(URLs.AristocracyImageBaseUrl + frame).error(R.drawable.no_image).into((Target) profileFrame);
        } else {
//            Picasso.get().load(R.drawable.no_image).error(R.drawable.no_image).into((Target) profileFrame);
        }

        buytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bottomsheetopen();
                //buyaristocracycenter(id);
            }
        });

        aristoResult(id);
        return v;
    }


    /*aristocracy center plan*/
    public void Bottomsheetopen(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.CustomBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
        LinearLayout download = bottomSheetDialog.findViewById(R.id.download);
        FrameLayout frameLayout = bottomSheetDialog.findViewById(R.id.profileFrame);
        CircleImageView circleImageView = bottomSheetDialog.findViewById(R.id.profile_image);
        ImageView ImageView = bottomSheetDialog.findViewById(R.id.txt_to_next_xp);

        LinearLayout countinuie1 = bottomSheetDialog.findViewById(R.id.countinuie1);
        TextView text_note = bottomSheetDialog.findViewById(R.id.text_note);
        TextView text_note1 = bottomSheetDialog.findViewById(R.id.text_note1);
        TextView text_note2 = bottomSheetDialog.findViewById(R.id.text_note2);
        TextView tvusername = bottomSheetDialog.findViewById(R.id.tvusername);
         recyclerview1 = bottomSheetDialog.findViewById(R.id.recycler_price);
        ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });
        if (image1 != null && !image1.isEmpty()) {
            Picasso.get().load(URLs.AristocracyImageBaseUrl + image1).error(R.drawable.no_image).into(circleImageView);
        } else {
            image.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
        }
        if (frame != null && !frame.isEmpty()) {
            Picasso.get().load(URLs.AristocracyImageBaseUrl + frame).error(R.drawable.no_image).into((Target) (frameLayout));
        } else {
//            Picasso.get().load(R.drawable.no_image).error(R.drawable.no_image).into((Target) profileFrame);
        }
        tvusername.setText(title);

        countinuie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                known_goldencoin();

                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.aristocracycenter))
                        .setMessage(getString(R.string.aristocracycenterpay1))
                        .setIcon(R.drawable.ic_check_circle_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.confirm),
                                new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        known_goldencoin();
                                     //   dialogopen();
                                     //   buyaristocracycenter(id);


                                    }
                                })
                        .setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener(){
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        
                        
                    }
                })

                        .show();
            }
        });
        aristoResultPlan(id);
        bottomSheetDialog.show();
    }

    private void buyaristocracycenter(final int id1) {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_BUYARISTOCARCYCENTER,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(getString(R.string.aristocracycenter))
                                        .setMessage(getString(R.string.aristocracycenterpay))
                                        .setIcon(R.drawable.ic_check_circle_black_24dp)
                                        .setCancelable(false)
                                        .setPositiveButton(getString(R.string.ok),
                                                new DialogInterface.OnClickListener() {
                                                    @TargetApi(11)
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                        getChildFragmentManager().popBackStackImmediate();
                                                    }
                                                }).show();
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
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(getActivity(), "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("aristocracy_center_id", String.valueOf(id1));

                return params;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void aristoResult(int id){
        Call<Aristocracy> call = AppConfig.loadInterface()
                .aristocracy_center_privileges(id);
        call.enqueue(new Callback<Aristocracy>() {
            @Override
            public void onResponse(Call<Aristocracy> call, Response<Aristocracy> response) {
                Aristocracy resp = response.body();
                if(resp!=null && resp.getStatus()==1 && resp.getResult()!=null){
                    aristoPrivillageAdapterInit(resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<Aristocracy> call, Throwable t) {

            }
        });

    }
    private void known_goldencoin(){
        try {

            Result credencial = IndifunApplication.getInstance().getCredential();
            String token = credencial.user_token;
            RetroCalls.instance()
                    .stringParam(token)
                    .know_golden_coin((type_result, obj2) -> {
                        if(obj2!=null &&  obj2.getResult()!=null && obj2.getResult().golden_coin!=null) {
                            try {
                                int selected_cal = Integer.parseInt(valu_of_card);
                                int available_coin = Integer.parseInt(obj2.getResult().golden_coin);
                                if (available_coin >= selected_cal) {
                                    //subscription api
                                  suscription();

                                } else {
                                    ///buy coin
                                    Intent rechargecoin=new Intent(getContext(), RechargeCoins.class);
                                    startActivity(rechargecoin);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                FirebaseCrashlytics.getInstance().recordException(e);
                            }
                                    loge("gold", new Gson().toJson(obj2));
                        }
                    });
        } catch (Exception e){

        }
    }

    private void suscription() {
        try {

            Result credencial = IndifunApplication.getInstance().getCredential();
            String token = credencial.user_token;
            loge("tokenfind",token);
            RetroCalls.instance()
                    .stringParam(token,value_id,valu_of_card,value_month)
                    .subscription((type_result, obj2) -> {
                        System.out.println("messageprint"+obj2.getStatus());
                    });
        } catch (Exception e){

        }

    }

    private void aristoResultPlan(int id){
        Call<AristocracyPlanResult> call = AppConfig.loadInterface()
                .aristocracy_center_plan(id);
        call.enqueue(new Callback<AristocracyPlanResult>() {
            @Override
            public void onResponse(Call<AristocracyPlanResult> call, Response<AristocracyPlanResult> response) {
                AristocracyPlanResult resp = response.body();
                if(resp!=null && resp.getStatus()==1 && resp.getResult()!=null){
                    aristrocracyplan(resp.getResult());
//                    AristocracyPlan aristocracyPlan=resp
                }
            }

            @Override
            public void onFailure(Call<AristocracyPlanResult> call, Throwable t) {

            }
        });

    }

    private void aristoPrivillageAdapterInit(List<AristoPrivileges> result){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        AristoPrivillageAdapter adapter = new AristoPrivillageAdapter(result);
        recyclerView.setAdapter(adapter);
    }

    private void aristrocracyplan(List<AristocracyPlan> result){
        recyclerview1.setLayoutManager(new GridLayoutManager(getContext(), 3));
        AristrocracyPlan adapter = new AristrocracyPlan(result, new AristrocracyPlan.OnItemSelected() {
            @Override
            public void onItemSelect(String aristocracy_center_id,String val,String month) {

                value_id=aristocracy_center_id;
                valu_of_card = val;
                value_month=month;
            }
        });
        recyclerview1.setAdapter(adapter);
    }

}
