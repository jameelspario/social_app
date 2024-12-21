
package com.deificindia.indifun.Model.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInterestsResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("intrest_id")
    @Expose
    private String intrestId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIntrestId() {
        return intrestId;
    }

    public void setIntrestId(String intrestId) {
        this.intrestId = intrestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
