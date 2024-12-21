package com.deificindia.indifun.Model.abs_plugs;
/* [
        {
            "id": "1",
            "title": "porsche",
            "image": "11.jpeg",
            "coin": "2999",
            "entry_date": "2020-10-06",
            "duration": "1"
        },*/
public class MallProduct {
    int id;
    String title;
    String image;
    String coin;
    String entry_date;
    String duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
