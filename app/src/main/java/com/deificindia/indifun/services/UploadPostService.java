package com.deificindia.indifun.services;

import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.chat.Fragments.APIService;
import com.deificindia.indifun.Model.ImagesSend;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs.PostModal;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.LoadInterface;
import com.deificindia.indifun.rest.RetroConfig2;
import com.google.gson.Gson;
import com.nguyenhoanglam.imagepicker.model.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.deificindia.indifun.Utility.Logger.logd;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.Utility.URLs.ADDPOST;
import static com.deificindia.indifun.Utility.URLs.BaseUrl;

public class UploadPostService extends JobIntentService {

    static ImageUploadListener _listener;
    private static final String ACTION_FOO = "com.deificindia.indifun.services.action.FOO";

    private static final String EXTRA_PARAM1 = "com.deificindia.indifun.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.deificindia.indifun.services.extra.PARAM2";




    private String Content;
    private String Error = null;
    String data = "";
    private BufferedReader reader;

    public static void startAction(Context context,  String param1, ArrayList<Image> param2,  ImageUploadListener listener){
        _listener = listener;
        Intent mIntent = new Intent(context, UploadPostService.class);
        mIntent.setAction(ACTION_FOO);
        mIntent.putExtra(EXTRA_PARAM1, param1);
        mIntent.putParcelableArrayListExtra(EXTRA_PARAM2, param2);
        UploadPostService.enqueueWork(context, mIntent);
    }

    static void enqueueWork(Context context,  Intent intent) {
        enqueueWork(context, UploadPostService.class, 1, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        onHandleIntent(intent);
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final ArrayList<Image> param2 = intent.getParcelableArrayListExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            }
        }
    }

    private void handleActionFoo(String param1, ArrayList<Image> pictures) {
        Result credential = IndifunApplication.getInstance().getCredential();
        //String res = prepost(pictures);
       // postMyPost(param1/*enterpostcontent.getText().toString().trim()*/, res);
        //logd("Intent", res);
        //upMy(IndifunApplication.getInstance().getCredential().getId(), param1, res);
       /* MyUp2(IndifunApplication.getInstance().getCredential().getId(),
                param1,
                "https://deificindia.com/indifun/panel/api/add_post",
                res);*/

       /* MyUp3(
                IndifunApplication.getInstance().getCredential().getId(),
                param1,
                res
        );*/
        //-------------------------
        ArrayList<String> imagesLink = new ArrayList<>();
        String token = credential.user_token;
        for (Image img:pictures){
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), img.getUri());
                String imgEncoded = encodeTobase64(bitmap);


                Call<ObjectModal<String>> call = RetroConfig2.createService(LoadInterface.class, token).uploadImage(imgEncoded);
                Response<ObjectModal<String>> resp = call.execute();

                if(resp.isSuccessful() && resp.body()!=null && resp.body().getStatus()==1){
                    imagesLink.add(resp.body().getResult());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        loge("Upload", param1);
        loge("Upload", new Gson().toJson(imagesLink));

        trigger(imagesLink);
    }


    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    private static Retrofit retrofit;
    public static Retrofit getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    private void MyUp3(String uid, String content, String image) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADDPOST,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                JSONObject jsonObject = obj.optJSONObject("result");
                                //trigger("Success");
                            } else {
                                //trigger("Not success");
                            }
                        } catch (JSONException e) {
                            //trigger(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        //trigger(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("post_by", uid);
                params.put("content", content);
                params.put("image", image);
                return params;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    public interface ImageUploadListener{
        void uploadStatus(ArrayList<String> imagesLink);
    }

    void trigger(ArrayList<String> imagesLink){
        if(_listener!=null) _listener.uploadStatus(imagesLink);
    }

}