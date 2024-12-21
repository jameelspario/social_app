
package com.deificindia.indifun.Model.posts;


import com.deificindia.indifun.Model.MyImage;

import java.util.List;

import static com.deificindia.indifun.Utility.URLs.UserPostImagesBaseUrl;

public class Hotpostmodel {

    /*
"id": "34",
"image": [],
"content": "er",
"entry_date": "2021-02-15",
"time": "01:32:25 PM",
"time_milli": "",
"age": null,
"gender": null,
"profile_picture": null,
"total_likes": "0",
"total_comments": "0",
"post_by": "admin",
"user_name": "Indifun Team",
"is_likes": 0,
"whatsup": null,
"is_online": null,
"is_broadcasting": null,
"is_blocked": null,
"is_mute": null,
"is_kick": null,
"blocked_time": null,
"mute_time": null,
"kick_time": null,
"is_following": 0
    * */

    List<MyResult> result;
    String message;
    int status;

    public class MyResult{
        String id;
        List<MyImage> image;
        String content;
        String entry_date;
        String time;
        long time_milli;
        int total_likes;
        int total_comments;
        String post_by;
        String user_name;
        int is_likes;
        String age;
        String gender;
        String profile_picture;
        int is_online;
        int is_broadcasting;
        int broadcasting_type;
        int is_following;
        String watsapp;
        String distance;
        int level;

        public void updateTotal_Likes() {
            is_likes = 1;
            total_likes++;
        }

        public void updateFollow(int islike){
            this.is_following = islike;
        }

        public void updateTotal_comments() {
            total_comments++;
        }

        public int getLevel(){
            return level;
        }

        /*public class MyImages{
            String id;
            String post_id;
            String image;
            String post_by;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPost_id() {
                return post_id;
            }

            public void setPost_id(String post_id) {
                this.post_id = post_id;
            }

            public String getImage() {
                return image;
            }

            public String getPostImage(){
                return UserPostImagesBaseUrl+image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getPost_by() {
                return post_by;
            }

            public void setPost_by(String post_by) {
                this.post_by = post_by;
            }
        }*/

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<MyImage> getImage() {
            return image;
        }

        public void setImage(List<MyImage> image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEntry_date() {
            return entry_date;
        }

        public void setEntry_date(String entry_date) {
            this.entry_date = entry_date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public long getTime_milli() {
            return time_milli;
        }

        public void setTime_milli(long time_milli) {
            this.time_milli = time_milli;
        }

        public int getTotal_likes() {
            return total_likes;
        }

        public void setTotal_likes(int total_likes) {
            this.total_likes = total_likes;
        }

        public int getTotal_comments() {
            return total_comments;
        }

        public void setTotal_comments(int total_comments) {
            this.total_comments = total_comments;
        }

        public String getPost_by() {
            return post_by;
        }

        public void setPost_by(String post_by) {
            this.post_by = post_by;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getIs_likes() {
            return is_likes;
        }

        public void setIs_likes(int is_likes) {
            this.is_likes = is_likes;
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

        public int getIs_online() {
            return is_online;
        }

        public void setIs_online(int is_online) {
            this.is_online = is_online;
        }

        public int getIs_broadcasting() {
            return is_broadcasting;
        }

        public void setIs_broadcasting(int is_broadcasting) {
            this.is_broadcasting = is_broadcasting;
        }

        public int getBroadcasting_type() {
            return broadcasting_type;
        }

        public void setBroadcasting_type(int broadcasting_type) {
            this.broadcasting_type = broadcasting_type;
        }

        public int getIs_following() {
            return is_following;
        }

        public void setIs_following(int is_following) {
            this.is_following = is_following;
        }

        public String getWatsapp() {
            return watsapp;
        }

        public void setWatsapp(String watsapp) {
            this.watsapp = watsapp;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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
