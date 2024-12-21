package com.deificindia.indifun.fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Activities.GiftPostLayout;
import com.deificindia.indifun.Adapter.GiftPostAdapter;
import com.deificindia.indifun.Adapter.TopFansAdapater;
import com.deificindia.indifun.Model.GoldenCoinModal;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.bindingmodals.otheruserprofile.Gift;
import com.deificindia.indifun.bindingmodals.otheruserprofile.TopFans;
import com.deificindia.indifun.rest.LoadInterface;
import com.deificindia.indifun.rest.RetroCallListener;
import com.deificindia.indifun.rest.RetroConfig2;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
     private RecyclerView recyclerView;
     private TopFansAdapater adapter;
    private TextView titleTxtView;
    private List<TopFans> topFansList = new ArrayList<>();


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentFragment newInstance(String param1) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_content, container, false);
//        titleTxtView = (TextView)myView.findViewById(R.id.title_txtView);
//        titleTxtView.setText(mParam1);
        recyclerView=myView.findViewById(R.id.recyclerView12);
     //   List<TopFans> topFansList=top();
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        adapter = new TopFansAdapater(getContext(),topFansList);
       // adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        top_fans();
        return myView;
    }
//    List<TopFans> top(){
//        return Arrays.asList(
//                new TopFans("16", 12, ""),
//                new TopFans("15", 10, ""),
//                new TopFans("14", 9, ""),
//                new TopFans("13", 18, ""),
//                new TopFans("12", 115, ""),
//                new TopFans("11", 18, "")
//        );
//    }
    public void top_fans(){

       RetroConfig2.createService(LoadInterface.class)
             .top_fans(IndifunApplication.getInstance().getCredential().getId())
               .enqueue(new Callback<ObjectModal<List<TopFans>>>() {
                   @Override
                   public void onResponse(Call<ObjectModal<List<TopFans>>> call, Response<ObjectModal<List<TopFans>>> response) {
                       if(response.body()!=null && response.body().status==1 && response.body().getResult()!=null){
                           adapter.updatetopfans(response.body().getResult());
                       }
                   }

                   @Override
                   public void onFailure(Call<ObjectModal<List<TopFans>>> call, Throwable t) {

                   }
               });
    }


}
