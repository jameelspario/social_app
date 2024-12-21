package com.deificindia.indifun.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.Model.GetFollow_Result;
import com.deificindia.indifun.Model.retro.TrendingModal;
import com.deificindia.indifun.rest.AppConfig;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveViewModel extends ViewModel {



    private MutableLiveData<TrendingModal> mutableLiveData_trending;
    private MutableLiveData<CountryNamesResult> mutableLiveData_country;

    private LiveRepository newsRepository;

    public LiveViewModel() { }

    public void init(){
        newsRepository = LiveRepository.getInstance();
    }

    /*friends*/
    public  MutableLiveData<DataWrapper<GetFollow_Result>> get_follow_homepage(String userId) {
        MutableLiveData<DataWrapper<GetFollow_Result>> data = new MutableLiveData<>();

        Observable.fromCallable((Callable<String>) () -> {
            Call<GetFollow_Result> call = AppConfig.loadInterface().follow_homepage();
            call.enqueue(new Callback<GetFollow_Result>() {
                @Override
                public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {
                    if(response.isSuccessful()) {
                        GetFollow_Result result = response.body();
                        data.setValue(new DataWrapper<>(result, ""));

                    }else data.setValue(new DataWrapper<>(null, ""));
                }

                @Override
                public void onFailure(Call<GetFollow_Result> call, Throwable t) {
                    data.setValue(new DataWrapper<>(null, ""));
                }
            });
            return "";
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });

        return data;
    }

//--------------------------------------------------------------------------------------------------
    /*recomended setion of view model*/
    public MutableLiveData<DataWrapper<GetFollow_Result>> follow_homepage_recommended(String userId) {
        MutableLiveData<DataWrapper<GetFollow_Result>> data = new MutableLiveData<>();

        Call<GetFollow_Result> call = AppConfig.loadInterface().follow_homepage_recommended();
        call.enqueue(new Callback<GetFollow_Result>() {
            @Override
            public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {

                if(response.isSuccessful()){
                    GetFollow_Result resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else {
                    data.setValue(new DataWrapper<>(null, ""));
                }
            }

            @Override
            public void onFailure(Call<GetFollow_Result> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, null));
            }
        });

        return data;
    }
//===================================================================================================





}
