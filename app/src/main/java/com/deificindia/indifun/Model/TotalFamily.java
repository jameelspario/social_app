package com.deificindia.indifun.Model;

public class TotalFamily {
    private int family_id;
            private String family_name;
    private String  family_icon;
    private String   max_user;
    private String   total_member;
    private String   level;
    private String   total_point;
    private String color="#000000";

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFamily_id() {
        return family_id;
    }

    public void setFamily_id(int family_id) {
        this.family_id = family_id;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getFamily_icon() {
        return family_icon;
    }

    public void setFamily_icon(String family_icon) {
        this.family_icon = family_icon;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMax_user() {
        return max_user;
    }

    public String getTotal_member() {
        return total_member;
    }

    public String getTotal_point() {
        return total_point;
    }

    public void setMax_user(String max_user) {
        this.max_user = max_user;
    }

    public void setTotal_member(String total_member) {
        this.total_member = total_member;
    }

    public void setTotal_point(String total_point) {
        this.total_point = total_point;
    }
}
