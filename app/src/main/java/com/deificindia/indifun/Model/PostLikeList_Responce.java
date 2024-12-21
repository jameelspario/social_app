package com.deificindia.indifun.Model;

import java.util.List;

public class PostLikeList_Responce {

    List<PostLikeList_Result> result;
    String message;
    String status;

    public List<PostLikeList_Result> getResult() {
        return result;
    }

    public void setResult(List<PostLikeList_Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
