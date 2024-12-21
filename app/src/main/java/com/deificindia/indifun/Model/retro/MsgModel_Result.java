package com.deificindia.indifun.Model.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsgModel_Result {

    /*
    * {
"id": "11",
"messanger_id": "2",
"message": "testing",
"add_broadcast_id": null,
"broadcast_link": "0",
"userto": "555",
"userfrom": "12",
"imageto": "",
"imagefrom": "",
"sticker_id": null,
"date": "2021-01-15",
"time": "05:11:53 PM",
"mili_time": "1610710913611",
"is_seen_1": "1",
"is_seen_2": "0"
},
* {"userto":"12",
* "userfrom":"555","imageto":1,"imagefrom":1,"messanger_id":"2",
* "message":"dxc",
* "date":"2021-02-16","time":"02:53:04 PM","mili_time":1613467384237,"is_seen_1":1,"is_seen_2":0}
    * */
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("messanger_id")
    @Expose
    public String messanger_id;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("add_broadcast_id")
    @Expose
    public String add_broadcast_id;
    @SerializedName("broadcast_link")
    @Expose
    public String broadcast_link;
    @SerializedName("userto")
    @Expose
    public String userto;
    @SerializedName("userfrom")
    @Expose
    public String userfrom;
    @SerializedName("imageto")
    @Expose
    public String imageto;
    public String imagefrom;
    @SerializedName("sticker_id")
    @Expose
    public String sticker_id;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("time")
    @Expose
    public String time;
    public long mili_time;
    @SerializedName("is_seen_1")
    @Expose
    public String is_seen_1;
    @SerializedName("is_seen_2")
    @Expose
    public String is_seen_2;

    public int sender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessanger_id() {
        return messanger_id;
    }

    public void setMessanger_id(String messanger_id) {
        this.messanger_id = messanger_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdd_broadcast_id() {
        return add_broadcast_id;
    }

    public void setAdd_broadcast_id(String add_broadcast_id) {
        this.add_broadcast_id = add_broadcast_id;
    }

    public String getBroadcast_link() {
        return broadcast_link;
    }

    public void setBroadcast_link(String broadcast_link) {
        this.broadcast_link = broadcast_link;
    }

    public String getUserto() {
        return userto;
    }

    public void setUserto(String userto) {
        this.userto = userto;
    }

    public String getUserfrom() {
        return userfrom;
    }

    public void setUserfrom(String userfrom) {
        this.userfrom = userfrom;
    }

    public String getImageto() {
        return imageto;
    }

    public void setImageto(String imageto) {
        this.imageto = imageto;
    }

    public String getImagefrom() {
        return imagefrom;
    }

    public void setImagefrom(String imagefrom) {
        this.imagefrom = imagefrom;
    }

    public String getSticker_id() {
        return sticker_id;
    }

    public void setSticker_id(String sticker_id) {
        this.sticker_id = sticker_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getMili_time() {
        return mili_time;
    }

    public void setMili_time(long mili_time) {
        this.mili_time = mili_time;
    }

    public String getIs_seen_1() {
        return is_seen_1;
    }

    public void setIs_seen_1(String is_seen_1) {
        this.is_seen_1 = is_seen_1;
    }

    public String getIs_seen_2() {
        return is_seen_2;
    }

    public void setIs_seen_2(String is_seen_2) {
        this.is_seen_2 = is_seen_2;
    }
}
