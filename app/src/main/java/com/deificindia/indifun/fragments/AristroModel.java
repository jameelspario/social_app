package com.deificindia.indifun.fragments;

public class AristroModel {

   private int id;
           private String aristocracy_center_id;
           private String privileges;
           private String title;
           private String cover;

    @Override
    public String toString() {
        return "AristroModel{" +
                "id=" + id +
                ", aristocracy_center_id='" + aristocracy_center_id + '\'' +
                ", privileges='" + privileges + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAristocracy_center_id() {
        return aristocracy_center_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public void setAristocracy_center_id(String aristocracy_center_id) {
        this.aristocracy_center_id = aristocracy_center_id;

    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

}
