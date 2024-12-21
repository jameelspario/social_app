package com.deificindia.indifun.Model.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserGroupListResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("made_by")
    @Expose
    private String madeBy;
    @SerializedName("group_category")
    @Expose
    private String groupCategory;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    private String full_name;
    private String age;
    private String gender;
    private String distance;
    private String member;
    private String image;
    private String btn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getGroupCategory() {
        return groupCategory;
    }

    public void setGroupCategory(String groupCategory) {
        this.groupCategory = groupCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistance(){
        return distance;
    }

    public void setDistance(String distance){
        this.distance = distance;
    }

    public String getMember(){
        return member;
    }

    public void setMember(String member){
        this.member = member;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String Image) {
        this.image = image;
    }

    public String getBtn(){
        return btn;
    }

    public void setBtn(String btn){
        this.btn = btn;
    }
}
