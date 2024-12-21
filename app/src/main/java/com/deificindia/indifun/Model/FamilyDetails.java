package com.deificindia.indifun.Model;

import java.util.ArrayList;

public class FamilyDetails {
    private String family_name;
    private String family_about;
    private String family_icon;
    private String family_background;
    private String family_level;
    private String family_next_level;
    private String family_point;
    private String family_to_next_point;
    private ArrayList<String> top_users_images;


    public FamilyDetails(String family_name, String family_about, String family_icon, String family_background, String family_level, String family_next_level, String family_point, String family_to_next_point, ArrayList<String> top_users_images) {
        this.family_name = family_name;
        this.family_about = family_about;
        this.family_icon = family_icon;
        this.family_background = family_background;
        this.family_level = family_level;
        this.family_next_level = family_next_level;
        this.family_point = family_point;
        this.family_to_next_point = family_to_next_point;
        this.top_users_images = top_users_images;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getFamily_about() {
        return family_about;
    }

    public void setFamily_about(String family_about) {
        this.family_about = family_about;
    }

    public String getFamily_icon() {
        return family_icon;
    }

    public void setFamily_icon(String family_icon) {
        this.family_icon = family_icon;
    }

    public String getFamily_background() {
        return family_background;
    }

    public void setFamily_background(String family_background) {
        this.family_background = family_background;
    }

    public String getFamily_level() {
        return family_level;
    }

    public void setFamily_level(String family_level) {
        this.family_level = family_level;
    }

    public String getFamily_next_level() {
        return family_next_level;
    }

    public void setFamily_next_level(String family_next_level) {
        this.family_next_level = family_next_level;
    }

    public String getFamily_point() {
        return family_point;
    }

    public void setFamily_point(String family_point) {
        this.family_point = family_point;
    }

    public String getFamily_to_next_point() {
        return family_to_next_point;
    }

    public void setFamily_to_next_point(String family_to_next_point) {
        this.family_to_next_point = family_to_next_point;
    }

    public ArrayList<String> getTop_users_images() {
        return top_users_images;
    }

    public void setTop_users_images(ArrayList<String> top_users_images) {
        this.top_users_images = top_users_images;
    }
}
