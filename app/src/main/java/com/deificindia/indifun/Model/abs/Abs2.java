package com.deificindia.indifun.Model.abs;

public abstract class Abs2 {
    public String msg;
    public int code;

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }
}

