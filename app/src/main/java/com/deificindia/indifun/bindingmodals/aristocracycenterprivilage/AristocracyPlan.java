package com.deificindia.indifun.bindingmodals.aristocracycenterprivilage;
/* [
        {
            "id": "1",
            "aristocracy_center_id": "1",
            "duration_month": "1",
            "save_discount": "1",
            "coin": "3000",
            "entry_date": "2020-10-06"
        },*/
public class AristocracyPlan {
    int id;
    String aristocracy_center_id;
    String duration_month;
    String save_discount;
    String coin;
    String entry_date;
    String best_offer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAristocracy_center_id() {
        return aristocracy_center_id;
    }

    public void setAristocracy_center_id(String aristocracy_center_id) {
        this.aristocracy_center_id = aristocracy_center_id;
    }

    public String getBest_offer() {
        return best_offer;
    }

    public void setBest_offer(String best_offer) {
        this.best_offer = best_offer;
    }

    public String getDurstion_month() {
        return duration_month;
    }

    public void setDurstion_month(String duration_month) {
        this.duration_month = duration_month;
    }

    public String getSave_discount() {
        return save_discount;
    }

    public void setSave_discount(String save_discount) {
        this.save_discount = save_discount;
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
}
