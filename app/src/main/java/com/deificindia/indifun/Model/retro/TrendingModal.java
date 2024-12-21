package com.deificindia.indifun.Model.retro;

import com.deificindia.indifun.Model.CountryUserResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrendingModal {

    public List<TrendingResult> result;
    private String message;
    private int status;

    public List<TrendingResult> getResult() {
        return result;
    }

    public void setResult(List<TrendingResult> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
