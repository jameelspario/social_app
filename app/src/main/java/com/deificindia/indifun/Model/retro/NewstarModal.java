package com.deificindia.indifun.Model.retro;


import java.util.List;

public class NewstarModal {

    public List<MyResult> result;
    private String message;
    private int status;

    public class MyResult{
        String id; //"id": "12",
        String full_name; //"full_name": "priya sahu",
        String age;///"age": "25",
        String gender;//"gender": "female",
        String profile_picture; //"profile_picture": "77d137687e4a45ef0efed4d2899b62d2.png",
        String registered;//"registered": "2020-10-26",
        String register_time;//"register_time": "01:49:00 PM"

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getProfile_picture() {
            return profile_picture;
        }

        public void setProfile_picture(String profile_picture) {
            this.profile_picture = profile_picture;
        }

        public String getRegistered() {
            return registered;
        }

        public void setRegistered(String registered) {
            this.registered = registered;
        }

        public String getRegister_time() {
            return register_time;
        }

        public void setRegister_time(String register_time) {
            this.register_time = register_time;
        }
    }

    public List<MyResult> getResult() {
        return result;
    }

    public void setResult(List<MyResult> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
