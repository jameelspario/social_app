package com.deificindia.indifun.Model;


import com.deificindia.indifun.Model.retro.LiveResult;

import java.util.List;

public class GetFollow_Result {

    public List<LiveResult> result;
    private String message;
    private int status;

    public List<LiveResult> getResult() {
        return result;
    }

    public void setResult(List<LiveResult> result) {
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
