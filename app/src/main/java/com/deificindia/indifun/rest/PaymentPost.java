package com.deificindia.indifun.rest;

import com.google.gson.annotations.SerializedName;

public class PaymentPost {

    private int id;
//    pivrate String userid;
//    private String email;
//    private String username;
//    private String mobile;
    private int coin;
    private int amount;
    private String paymentid;
    private String orderid;


    public int getId() {
        return id;
    }

//    public String getUserid() {
//        return userid;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }

    public int getCoin() {
        return coin;
    }

    public int getAmount() {
        return amount;
    }

    public String getPaymentid() {
        return paymentid;
    }

//    public String getUsername() {
//        return username;
//    }

    public String getOrderid() {
        return orderid;
    }
}

