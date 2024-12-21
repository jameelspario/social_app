package com.deificindia.chat.Model;


import java.io.Serializable;

public class User implements Serializable {

    private String id,key;
    private String full_name;
    private String image;
    private String status;
    private String search;
    private String fuid;
    private String id1;
    private String uid;
    private String age;
    private String gender;
    private String level;
    private String gold_coin="0";
    private String city;
    private String email;
    private String contact;
    private String country;
    public String like;
    private String whatsup;
    private String relationship;
    private String state;
    private String user_id;
    private String dob;
    private String my_xp;
    public long my_xp_collect;

    public User(String like,String id,String level,String gold_coin,String age,String gender, String uid,String fuid,String full_name, String image, String status, String search,String id1) {
        this.id = id;
        this.full_name = full_name;
        this.image = image;
        this.status = status;
        this.like=like;
        this.search = search;
        this.fuid=fuid;
        this.id1=id1;
        this.uid=uid;
        this.age=age;
        this.level=level;
        this.gold_coin=gold_coin;
        this.gender=gender;
    }

    public User() {

    }

    public String getLike() {
        return like;
    }


    public String getUser_id() {
        return user_id;
    }

    public String getMy_xp() {
        return my_xp;
    }

    public void setMy_xp(String my_xp) {
        this.my_xp = my_xp;
    }

    public String getCity() {
        return city;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public String getCountry() {
        return country;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getWhatsup() {
        return whatsup;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setWhatsup(String whatsup) {
        this.whatsup = whatsup;
    }


    public String getGold_coin() {
        return gold_coin;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setGold_coin(String gold_coin) {
        this.gold_coin = gold_coin;
    }

    public String getLevel() {
        return level;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUid() {
        return uid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getFuid() {
        return fuid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }


    public void setFuid(String fuid) {
        this.fuid = fuid;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return full_name;
    }

    public void setUsername(String username) {
        this.full_name = username;
    }

    public String getImageURL() {
        return image;
    }

    public void setImageURL(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setKey(String key) {
        this.key=key;
    }

    public Object getKey() {
        return key;
    }
}
