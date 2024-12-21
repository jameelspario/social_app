package com.deificindia.indifun.vm;

public class DataWrapper <T>{
    public T data;
    public String error;

    public DataWrapper(T data, String error) {
        this.data = data;
        this.error = error;
    }
}
