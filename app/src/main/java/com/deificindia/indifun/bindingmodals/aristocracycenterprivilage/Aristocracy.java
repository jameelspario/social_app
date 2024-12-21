package com.deificindia.indifun.bindingmodals.aristocracycenterprivilage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Aristocracy {
    @SerializedName("result")
    @Expose
    private List<AristoPrivileges> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<AristoPrivileges> getResult() {
        return result;
    }

    public void setResult(List<AristoPrivileges> result) {
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
