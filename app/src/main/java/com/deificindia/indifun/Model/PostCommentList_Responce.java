package com.deificindia.indifun.Model;

import java.util.List;

public class PostCommentList_Responce {

    List<PostCommentList_Result> result;
    String message;
    String status;

    public List<PostCommentList_Result> getResult() {
        return result;
    }

    public void setResult(List<PostCommentList_Result> result) {
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
