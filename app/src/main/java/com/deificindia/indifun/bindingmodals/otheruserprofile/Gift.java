
package com.deificindia.indifun.bindingmodals.otheruserprofile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gift implements Parcelable {

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
    private String giftId;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("broadcast_id")
    @Expose
    private String broadcastId;
    @SerializedName("cover")
    @Expose
    private String cover;
    public  int count;

    public Gift() {
    }

    protected Gift(Parcel in) {
        id = in.readString();
        to = in.readString();
        from = in.readString();
        giftId = in.readString();
        entryDate = in.readString();
        point = in.readString();
        broadcastId = in.readString();
        cover = in.readString();
        count=in.readInt();
    }

    public static final Creator<Gift> CREATOR = new Creator<Gift>() {
        @Override
        public Gift createFromParcel(Parcel in) {
            return new Gift(in);
        }

        @Override
        public Gift[] newArray(int size) {
            return new Gift[size];
        }
    };

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

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getBroadcastId() {
        return broadcastId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setBroadcastId(String broadcastId) {
        this.broadcastId = broadcastId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(to);
        dest.writeString(from);
        dest.writeString(giftId);
        dest.writeString(entryDate);
        dest.writeString(point);
        dest.writeString(broadcastId);
        dest.writeString(cover);
        dest.writeInt(count);
    }
}
