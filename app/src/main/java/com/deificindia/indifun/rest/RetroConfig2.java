package com.deificindia.indifun.rest;

import android.text.TextUtils;
import android.util.Log;

import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroConfig2 {

    private static final boolean LOG = true;
    private static final String BASEURL = api.API_URL;

    //private static BooleanSerializerDeserializer booleanSerializerDeserializer = new BooleanSerializerDeserializer();

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls()
            //.registerTypeAdapter(boolean.class, booleanSerializerDeserializer)
            //.registerTypeAdapter(boolean.class, booleanSerializerDeserializer)
            .setLenient()
            .create();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit.Builder getBuilder(String BASE){
        return new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create(gson));
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }
    public static <S> S createService(Class<S> serviceClass, boolean token) {
        return createService(serviceClass, token? IndifunApplication.getInstance().getCredential().user_token:null);
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password) {

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);
    }


    public static <S> S createService(Class<S> serviceClass, String token) {
        Log.e("Checkoutdataresponse", "token  = " + token);

        if (!httpClient.interceptors().isEmpty()) {
            httpClient.interceptors().clear();
        }

        if (!TextUtils.isEmpty(token)) {

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", token)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        // Logging HTTP Request & Response
        if (LOG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        OkHttpClient client = httpClient
                .retryOnConnectionFailure(true)
                .readTimeout(3000, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = getBuilder(BASEURL).client(client).build();
        return retrofit.create(serviceClass);
    }
}
