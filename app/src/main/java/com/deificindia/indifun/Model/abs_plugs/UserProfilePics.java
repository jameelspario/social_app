package com.deificindia.indifun.Model.abs_plugs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfilePics {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
}
