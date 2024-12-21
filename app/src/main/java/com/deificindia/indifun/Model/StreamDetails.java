package com.deificindia.indifun.Model;

import com.deificindia.indifun.Utility.api;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StreamDetails {
    @SerializedName(api.TAG_STATUS)
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("streamid")
    public String streamid;

    @SerializedName("Posted_by")
    public String Posted_by;

    @SerializedName("posted_user_id")
    public String posted_user_id;

    @SerializedName("title")
    public String title;

    @SerializedName("watch_count")
    public String watch_count;

    @SerializedName("user_image")
    public String user_image;

    @SerializedName("stream_thumbnail")
    public String stream_thumbnail;

    @SerializedName("userid")
    public String userid;

    @SerializedName("following")
    public String following;
    @SerializedName("reported")
    public String reported;

    @SerializedName("live_viewers")
    public ArrayList<viewers> viewers = new ArrayList();

    public static class viewers {

        @SerializedName("user_id")
        public String user_id;

        @SerializedName("user_name")
        public String user_name;

        @SerializedName("user_image")
        public String user_image;

        @SerializedName("like_count")
        public String like_count;
    }
}
