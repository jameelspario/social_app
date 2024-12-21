package com.deificindia.indifun.Model;

import com.deificindia.indifun.bindingmodals.otheruserprofile.OtherUserProfileResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamilyDetailsRes {
    @SerializedName("result")
    @Expose
    private FamilyDetails result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public FamilyDetails getResult() {
        return result;
    }

    public void setResult(FamilyDetails result) {
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
