package com.deificindia.indifun.Model.abs_plugs;

import java.util.Objects;

public class BlockList {

    /*
{
"id": "555",
"full_name": "qee",
"profile_picture": "",
"blocked_time": "1611230398645"
}
    * */

    private String id;
    private String full_name;
    private String profile_picture;
    private String blocked_time;

    public int is_blocked = 1;

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

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getBlocked_time() {
        return blocked_time;
    }

    public void setBlocked_time(String blocked_time) {
        this.blocked_time = blocked_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockList blockList = (BlockList) o;
        return Objects.equals(id, blockList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
