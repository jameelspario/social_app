package com.deificindia.indifun.Model.abs_plugs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
{
        "level": "1",
        "my_broadcasting_xp": "2",
        "to_next_level": 298
    }
* */

public class UserBroadLevel {

    @SerializedName("level")
    @Expose
    private int level;

    @SerializedName("my_broadcasting_xp")
    @Expose
    private int my_broadcasting_xp;

    @SerializedName("to_next_level")
    @Expose
    private int to_next_level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMy_broadcasting_xp() {
        return my_broadcasting_xp;
    }

    public void setMy_broadcasting_xp(int my_broadcasting_xp) {
        this.my_broadcasting_xp = my_broadcasting_xp;
    }

    public int getTo_next_level() {
        return to_next_level;
    }

    public void setTo_next_level(int to_next_level) {
        this.to_next_level = to_next_level;
    }
}
