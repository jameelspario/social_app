package com.deificindia.indifun.Model.abs_plugs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinRecordResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("paid_for")
    @Expose
    private String paidFor;
    @SerializedName("paid_id")
    @Expose
    private String paidId;
    @SerializedName("via")
    @Expose
    private String via;
    @SerializedName("transection_id")
    @Expose
    private String transectionId;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("time")
    @Expose
    private String time;
    private String coin_count;

    public String getCoin_count() {
        return coin_count;
    }

    public void setCoin_count(String coin_count) {
        this.coin_count = coin_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaidFor() {
        return paidFor;
    }

    public void setPaidFor(String paidFor) {
        this.paidFor = paidFor;
    }

    public String getPaidId() {
        return paidId;
    }

    public void setPaidId(String paidId) {
        this.paidId = paidId;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getTransectionId() {
        return transectionId;
    }

    public void setTransectionId(String transectionId) {
        this.transectionId = transectionId;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
