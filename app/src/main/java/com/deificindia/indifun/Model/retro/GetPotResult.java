
package com.deificindia.indifun.Model.retro;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPotResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("total_likes")
    @Expose
    private String totalLikes;
    @SerializedName("total_comments")
    @Expose
    private String totalComments;
    @SerializedName("post_by")
    @Expose
    private String postBy;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("is_likes")
    @Expose
    private Integer isLikes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(String totalLikes) {
        this.totalLikes = totalLikes;
    }

    public String getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(String totalComments) {
        this.totalComments = totalComments;
    }

    public String getPostBy() {
        return postBy;
    }

    public void setPostBy(String postBy) {
        this.postBy = postBy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsLikes() {
        return isLikes;
    }

    public void setIsLikes(Integer isLikes) {
        this.isLikes = isLikes;
    }

}
