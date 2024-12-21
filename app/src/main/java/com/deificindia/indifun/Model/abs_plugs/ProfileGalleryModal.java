package com.deificindia.indifun.Model.abs_plugs;

public class ProfileGalleryModal {
    //{"id":"1","user_id":"12","image":"f336965acffd1e9c95e392b9b7b716e4.png","entry_date":"2020-10-03"}

    public int id = -1;
    public String user_id;
    public String image;
    public String entry_date;

    public ProfileGalleryModal() {
    }

    public ProfileGalleryModal(int id, String user_id) {
        this.id = id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }
}
