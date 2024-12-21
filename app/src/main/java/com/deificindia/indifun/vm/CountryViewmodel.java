package com.deificindia.indifun.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.Model.CountryUsers;
import com.deificindia.indifun.Model.retro.TrendingModal;
import com.deificindia.indifun.rest.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryViewmodel extends ViewModel {

    /*Country result*/
    public LiveData<DataWrapper<CountryNamesResult>> getCountry() {
        MutableLiveData<DataWrapper<CountryNamesResult>> data = new MutableLiveData<>();
        Call<CountryNamesResult> call = AppConfig.loadInterface().getCountry();
        call.enqueue(new Callback<CountryNamesResult>() {
            @Override
            public void onResponse(Call<CountryNamesResult> call, Response<CountryNamesResult> response) {

                if(response.isSuccessful()){
                    CountryNamesResult resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else {
                    data.setValue(new DataWrapper<>(null, "no response"));

                }
            }

            @Override
            public void onFailure(Call<CountryNamesResult> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, "no response"));
            }
        });
        return data;
    }

    public LiveData<DataWrapper<TrendingModal>> getCountryUsers(int countryid,int i, int s) {
        MutableLiveData<DataWrapper<TrendingModal>> data = new MutableLiveData<>();
        Call<TrendingModal> call = AppConfig.loadInterface().getCountryUsers(countryid, i, s);
        call.enqueue(new Callback<TrendingModal>() {
            @Override
            public void onResponse(Call<TrendingModal> call, Response<TrendingModal> response) {

                if(response.isSuccessful()){
                    TrendingModal resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else {
                    data.setValue(new DataWrapper<>(null, "no response"));

                }
            }

            @Override
            public void onFailure(Call<TrendingModal> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, "no response"));
            }
        });
        return data;
    }

}
