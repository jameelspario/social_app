package com.deificindia.chat.Fragments;


import com.deificindia.chat.Notifications.MyResponse;
import com.deificindia.chat.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAVxBtAO0:APA91bHFQXCYI5zdp7zAWNHP9h0szE2r0EvSAqtn76kGmioXSOBM3BMbArFK6lj0LPAx5CzgZLFvXq8tHTKKEngXDb85D8TLcRwzIN9SsDTlBT1r29rHT4DM7PlyEYVseiW18nbeQEaI"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
