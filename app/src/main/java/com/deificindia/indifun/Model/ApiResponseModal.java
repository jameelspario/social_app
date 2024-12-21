package com.deificindia.indifun.Model;

public class ApiResponseModal {

    public static class IsBroadcasting_result{

        String id;
        String user_id;
        String user_name;
        String user_image;
        String duration;
        String title;
        String is_live;
        String entrydate;
        String entry_time;
        String completion_duration;
        String end_time;
        String broadcasting_type;
        String pk_with;
        String winner_user;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIs_live() {
            return is_live;
        }

        public void setIs_live(String is_live) {
            this.is_live = is_live;
        }

        public String getEntrydate() {
            return entrydate;
        }

        public void setEntrydate(String entrydate) {
            this.entrydate = entrydate;
        }

        public String getEntry_time() {
            return entry_time;
        }

        public void setEntry_time(String entry_time) {
            this.entry_time = entry_time;
        }

        public String getCompletion_duration() {
            return completion_duration;
        }

        public void setCompletion_duration(String completion_duration) {
            this.completion_duration = completion_duration;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getBroadcasting_type() {
            return broadcasting_type;
        }

        public void setBroadcasting_type(String broadcasting_type) {
            this.broadcasting_type = broadcasting_type;
        }

        public String getPk_with() {
            return pk_with;
        }

        public void setPk_with(String pk_with) {
            this.pk_with = pk_with;
        }

        public String getWinner_user() {
            return winner_user;
        }

        public void setWinner_user(String winner_user) {
            this.winner_user = winner_user;
        }
    }

    /*
    "user_id": "12",
    "user_name": "priya sahu",
    "friends": "1",
    "image": "0b0fa8cac4104b34921c47b91037469d.png",
    "is_online": "1",
    "add_broadcast_id": "2",
    "add_broadcast_title": "title 2",
    "broadcasting_type": "2",
    "is_broadcasting": "1"
    * */
    /*
    "user_id": "460",
            "friends": "0",
            "user_name": "xyz",
            "image": "19d80d5a198b17a667bdc051d6078d02.png",
            "is_online": "1",
            "add_broadcast_id": "3",
            "add_broadcast_title": "title 3",
            "broadcasting_type": "2",
            "is_broadcasting": "1"
    * */

    public static class follow_homepage_result{
        String user_id;
        String friends;
        String user_name;
        String image;
        String is_online;
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
    }

}
