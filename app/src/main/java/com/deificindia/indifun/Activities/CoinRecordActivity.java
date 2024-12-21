package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.indifun.Adapter.CoinRecordAdapter;
import com.deificindia.indifun.Model.abs_plugs.CoinRecordResult;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.Utility.ListSorting;
import com.deificindia.indifun.dialogs.CoinRecordFilterDialog;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.LoadingAnimView;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.deificindia.indifun.rest.RetroCallListener.COIN_RECORD;

public class CoinRecordActivity extends AppCompatActivity {


    private ImageView img_back, imgFilter;
    private TextView txt_header_title, tvMessage;
    RecyclerView recyclerView;
    CoinRecordAdapter adapter;
    LoadingAnimView loadingAnimView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_record);
        img_back = findViewById(R.id.img_back);
        imgFilter = findViewById(R.id.imgFilter);
        txt_header_title = findViewById(R.id.txt_header_title);
        txt_header_title.setText("Coin Records");
        tvMessage = findViewById(R.id.tvMessage);
        recyclerView = findViewById(R.id.recyclerView);
        loadingAnimView = findViewById(R.id.lottieanim);

        img_back.setOnClickListener(v -> onBackPressed());

        imgFilter.setOnClickListener(v->{
            CoinRecordFilterDialog dialog = new CoinRecordFilterDialog();
            dialog.show(getSupportFragmentManager(), "CoinRecordFilterDialog");
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(2));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
//        //or
       DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
       dividerItemDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.divider,null)));

        recyclerView.addItemDecoration(dividerItemDecoration);

        loadingAnimView.startloading();
        callApi();
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

    void callApi(){
        RetroCalls.instance()
                .callType(COIN_RECORD)
                .withUID()
                .listeners((type_result, code, message) -> {
                    loadingAnimView.showError();
                    loadingAnimView.setErrText("No data");
        }).coin_record((type_result, obj2) -> {
            //Collections.sort(obj2.getResult(), new ListSorting.shortCoinsResult());
            if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null && type_result==COIN_RECORD){
                initAdapter(obj2.getResult());
                loadingAnimView.stopAnim();
            }else {
                loadingAnimView.showError();
                loadingAnimView.setErrText("No data");
            }
        });
    }

    void initAdapter(List<CoinRecordResult> lits){
        adapter = new CoinRecordAdapter(lits, CoinRecordActivity.this);
        recyclerView.setAdapter(adapter);
    }


}
