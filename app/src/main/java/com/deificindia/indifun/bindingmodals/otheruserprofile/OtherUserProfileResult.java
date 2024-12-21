package com.deificindia.indifun.bindingmodals.otheruserprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtherUserProfileResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("whatsup")
    @Expose
    private String whatsup;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("registered")
    @Expose
    private String registered;
    @SerializedName("relationship")
    @Expose
    private String relationship;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("cntry_flag")
    @Expose
    private String cntryFlag;
    @SerializedName("moment_pic")
    @Expose
    private List<MomentPic> momentPic = null;
    @SerializedName("moment_pic_count")
    @Expose
    private int momentPicCount;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("language")
    @Expose
    private List<Language> language = null;
    @SerializedName("intrest")
    @Expose
    private List<Intrest> intrest = null;
    @SerializedName("count_intrest")
    @Expose
    private int countIntrest;
    @SerializedName("medals")
    @Expose
    private List<Medal> medals = null;
    @SerializedName("medals_count")
    @Expose
    private int medalsCount;
    @SerializedName("gift")
    @Expose
    private List<Gift> gift = null;
    @SerializedName("gift_count")
    @Expose
    private int giftCount;
    @SerializedName("group")
    @Expose
    private List<Group> group = null;
    @SerializedName("group_count")
    @Expose
    private int groupCount;
    @SerializedName("is_following")
    @Expose
    private int is_following;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("join_date")
    @Expose
    private String join_date;

    @SerializedName("top_fan")
    @Expose
    private List<TopFans> topFans=null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWhatsup() {
        return whatsup;
    }

    public void setWhatsup(String whatsup) {
        this.whatsup = whatsup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCntryFlag() {
        return cntryFlag;
    }

    public void setCntryFlag(String cntryFlag) {
        this.cntryFlag = cntryFlag;
    }

    public List<MomentPic> getMomentPic() {
        return momentPic;
    }

    public void setMomentPic(List<MomentPic> momentPic) {
        this.momentPic = momentPic;
    }

    public int getMomentPicCount() {
        return momentPicCount;
    }

    public void setMomentPicCount(int momentPicCount) {
        this.momentPicCount = momentPicCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Language> getLanguage() {
        return language;
    }

    public void setLanguage(List<Language> language) {
        this.language = language;
    }

    public List<Intrest> getIntrest() {
        return intrest;
    }

    public void setIntrest(List<Intrest> intrest) {
        this.intrest = intrest;
    }

    public int getCountIntrest() {
        return countIntrest;
    }

    public void setCountIntrest(int countIntrest) {
        this.countIntrest = countIntrest;
    }

    public List<Medal> getMedals() {
        return medals;
    }

    public void setMedals(List<Medal> medals) {
        this.medals = medals;
    }

    public int getMedalsCount() {
        return medalsCount;
    }

    public void setMedalsCount(int medalsCount) {
        this.medalsCount = medalsCount;
    }

    public List<Gift> getGift() {
        return gift;
    }

    public void setGift(List<Gift> gift) {
        this.gift = gift;
    }

    public int getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }

    public List<TopFans> getTopFans() {
        return topFans;
    }

    public void setTopFans(List<TopFans> topFans) {
        this.topFans = topFans;
    }

    public List<Group> getGroup() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public int getIs_following() {
        return is_following;
    }

    public void setIs_following(int is_following) {
        this.is_following = is_following;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }
}
