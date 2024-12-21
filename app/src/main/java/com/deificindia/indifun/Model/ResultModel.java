package com.deificindia.indifun.Model;

import java.util.List;

public class ResultModel {
    public List<MyResult> result;

    private String message;
    private int status;
    static class MyResult {

        String user_id;
        String friends;
        String user_name;
        String image;
        String is_online;
        String room_id;
        String add_broadcast_id;
        String add_broadcast_title;
        String broadcasting_type;
        String is_broadcasting;


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFriends() {
            return friends;
        }

        public void setFriends(String friends) {
            this.friends = friends;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIs_online() {
            return is_online;
        }

        public void setIs_online(String is_online) {
            this.is_online = is_online;
        }

        public String getAdd_broadcast_id() {
            return add_broadcast_id;
        }

        public void setAdd_broadcast_id(String add_broadcast_id) {
            this.add_broadcast_id = add_broadcast_id;
        }

        public String getAdd_broadcast_title() {
            return add_broadcast_title;
        }

        public void setAdd_broadcast_title(String add_broadcast_title) {
            this.add_broadcast_title = add_broadcast_title;
        }

        public String getBroadcasting_type() {
            return broadcasting_type;
        }

        public void setBroadcasting_type(String broadcasting_type) {
            this.broadcasting_type = broadcasting_type;
        }

        public String getIs_broadcasting() {
            return is_broadcasting;
        }

        public void setIs_broadcasting(String is_broadcasting) {
            this.is_broadcasting = is_broadcasting;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
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
