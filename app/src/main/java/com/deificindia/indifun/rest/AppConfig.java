package com.deificindia.indifun.rest;

import com.deificindia.indifun.Utility.api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {
    private static Retrofit retrofit = null;
    private static final String BASEURL = api.API_URL;
    private static final int MAX_RESPONSE_THREAD = 10;
    private static final int DEFAULT_TIMEOUT_IN_SECONDS = 30;
    public static final String BASEURL_TOKEN = "https://spario.000webhostapp.com/tw/";

    private static Retrofit __getClient(String burl) {
        //if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(burl)
                    .client(okHttpClient)
                    //.callbackExecutor(Executors.newFixedThreadPool(MAX_RESPONSE_THREAD))
                    .addConverterFactory(GsonConverterFactory.create(gson));

           /* if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(XLog::d);
                interceptor.level(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                builder.client(client);
            }*/

            retrofit = builder.build();

        //}
        return retrofit;
    }

    public static LoadInterface loadInterface() {
        return AppConfig.__getClient(BASEURL).create(LoadInterface.class);
    }

    public static LoadInterface loadInterface(String baseurl) {
        return AppConfig.__getClient(baseurl).create(LoadInterface.class);
    }

    //////////////////////////////////////////////////////////////////////////////////


}
