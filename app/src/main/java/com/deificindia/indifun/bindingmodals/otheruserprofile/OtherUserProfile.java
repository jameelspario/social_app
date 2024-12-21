
package com.deificindia.indifun.bindingmodals.otheruserprofile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherUserProfile {

    @SerializedName("result")
    @Expose
    private OtherUserProfileResult result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public OtherUserProfileResult getResult() {
        return result;
    }

    public void setResult(OtherUserProfileResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
