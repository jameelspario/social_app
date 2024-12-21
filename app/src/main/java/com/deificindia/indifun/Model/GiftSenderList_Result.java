package com.deificindia.indifun.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GiftSenderList_Result {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("gift_id")
    @Expose
    private String gift_id;
    @SerializedName("point")
    @Expose
    private String point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getGift_id() {
        return gift_id;
    }

    public void setGift_id(String gift_id) {
        this.gift_id = gift_id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
