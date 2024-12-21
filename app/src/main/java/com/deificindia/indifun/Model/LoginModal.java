package com.deificindia.indifun.Model;


import com.deificindia.indifun.modals.Result;

public class LoginModal {

    public String id;
    public String fb_id;
    public String uid;
    public String full_name;
    public String email;
    public String contact;
    public String firebase_uid;
    public String point;
    public String broadcast_point;
    public String golden_coins;
    public String silver_coins;
    public String country;
    public String country_id;
    public String cover;
    public String state;
    public String city;
    public String latitude;
    public String longitude;
    public String credits;
    public String age;
    public String dob;
    public String whatsup;
    public String gender;
    public String sexuality;
    public String profile_picture;
    public String ip;
    public String registered;
    public String register_time;
    public String registration_time;
    public String last_login_date;
    public String last_login;
    public String checkin_time;
    public String last_active;
    public String otp_verified;
    public String otp;
    public String is_verified;
    public String is_online;
    public String is_broadcasting;
    public String height;
    public String weight;
    public String last_encounter;
    public String relationship;
    public String user_token;
    public String status;
    public String log_status;


    public Result clone() throws CloneNotSupportedException {
        Result res = new Result();
        res.setId(id);
        res.setFbId(fb_id);
        res.setUid(uid);
        res.setFullName(full_name);
        res.setEmail(email);
        res.setContact(contact);
        res.setFirebaseUid(firebase_uid);
        res.setPoint(point);
        res.setBroadcastPoint(broadcast_point);
        res.setGoldenCoins(golden_coins);
        res.setSilverCoins(silver_coins);
        res.setCountry(country);
        res.setCountry(country);
        res.setCountryId(country_id);
        res.setCover(cover);
        res.setState(state);
        res.setCity(city);
        res.setLatitude(latitude);
        res.setLongitude(longitude);
        res.setCredits(credits);
        res.setAge(age);
        res.setDob(dob);
        res.setWhatsup(whatsup);
        res.setGender(gender);
        res.setSexuality(sexuality);
        res.setProfilePicture(profile_picture);
        res.setIp(ip);
        res.setRegistered(registered);
        res.setRegisterTime(register_time);
        res.setLastLogin(last_login);
//        res.setChinTime();
        res.setLastActive(last_active);


        res.setIsVerified(is_verified);
        res.setIsOnline(is_online);
        res.setIsBroadcasting(is_broadcasting);
        res.setHeight(height);
        res.setWeight(weight);
//        res.setLastEncounter()
        res.setRelationship(relationship);
        res.user_token = user_token;
        res.setStatus(status);

        return res;
    }


}
