package com.deificindia.indifun.bindingmodals.aristocracycenterprivilage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*[
       {
           "id": null,
           "aristocracy_center_id": "2",
           "privileges": "13",
           "title": null,
           "cover": null
       },*/
public class AristoPrivileges {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("aristocracy_center_id")
    @Expose
    private String aristocracyCenterId;
    @SerializedName("privileges")
    @Expose
    private String privileges;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cover")
    @Expose
    private String cover;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAristocracyCenterId() {
        return aristocracyCenterId;
    }

    public void setAristocracyCenterId(String aristocracyCenterId) {
        this.aristocracyCenterId = aristocracyCenterId;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
