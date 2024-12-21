package com.deificindia.indifun.Model.abs_plugs;
/*[
        {
            "id": "1",
            "mall_product_id": "1",
            "duration": "1",
            "entry_date": "2020-10-06",
            "expiry_date": "2020-11-06",
            "user_id": "12"
        },*/
public class BackUpMall {
    int id;
    String mall_product_id;
    String duration;
    String entry_date;
    String expiry_date;
    String user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMall_product_id() {
        return mall_product_id;
    }

    public void setMall_product_id(String mall_product_id) {
        this.mall_product_id = mall_product_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
