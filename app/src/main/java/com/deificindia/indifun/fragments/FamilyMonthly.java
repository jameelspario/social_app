package com.deificindia.indifun.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deificindia.indifun.Adapter.FamilyTotalAdapter;
import com.deificindia.indifun.Model.TotalFamily;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.rest.LoadInterface;
import com.deificindia.indifun.rest.RetroConfig2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FamilyMonthly#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyMonthly extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private FamilyTotalAdapter adapter;
    private TextView titleTxtView;
    private List<TotalFamily> topFansList = new ArrayList<>();
    private static final int VERTICAL_ITEM_SPACE = 48;
    public FamilyMonthly() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FamilyMonthly.
     */
    // TODO: Rename and change types and number of parameters
    public static FamilyMonthly newInstance(String param1, String param2) {
        FamilyMonthly fragment = new FamilyMonthly();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView= inflater.inflate(R.layout.fragment_family_monthly, container, false);
        recyclerView=myView.findViewById(R.id.recyclerView12);
        //   List<TopFans> topFansList=top();
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        adapter = new FamilyTotalAdapter(getContext(),topFansList);
        //add ItemDecoration
        //  recyclerView.addItemDecoration(new Vertica(VERTICAL_ITEM_SPACE));
        //or

        // adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        top_fans();
        return myView;
    }
    public void top_fans(){

        RetroConfig2.createService(LoadInterface.class)
                .total_monthly_family()
                .enqueue(new Callback<ObjectModal<List<TotalFamily>>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<List<TotalFamily>>> call, Response<ObjectModal<List<TotalFamily>>> response) {
                        if(response.body()!=null && response.body().status==1 && response.body().getResult()!=null){
                            adapter.updatetopfans(response.body().getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<List<TotalFamily>>> call, Throwable t) {

                    }
                });
    }

}