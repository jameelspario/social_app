package com.deificindia.indifun.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
{
    "result": {
        "heart_count": "29",
"to": "12",
"from": "590",
"point": "1",
"entry_date": "2021-04-03",
"broadcast_id": "0",
"heart_daily": "7"
    },
    "message": "success",
    "status": 1
}
* */
public class HeartPoint {

    @SerializedName("result")
    @Expose
    private MyResult result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public class MyResult{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("point")
        @Expose
        private String point;
        @SerializedName("entry_date")
        @Expose
        private String entryDate;
        @SerializedName("broadcast_id")
        @Expose
        private String broadcastId;
        @SerializedName("heart_daily")
        @Expose
        private int heartDaily;


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

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getEntryDate() {
            return entryDate;
        }

        public void setEntryDate(String entryDate) {
            this.entryDate = entryDate;
        }

        public String getBroadcastId() {
            return broadcastId;
        }

        public void setBroadcastId(String broadcastId) {
            this.broadcastId = broadcastId;
        }

        public int getHeartDaily() {
            return heartDaily;
        }

        public void setHeartDaily(int heartDaily) {
            this.heartDaily = heartDaily;
        }
    }

    public MyResult getResult() {
        return result;
    }

    public void setResult(MyResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
