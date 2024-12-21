package com.deificindia.indifun.Model.abs;

//DataMod<List<Musics>>
public class DataMod<T> extends Abs2 {

    private T data;

    public T getData() { return data; }

    public void setData(T data) { this.data = data; }
}
