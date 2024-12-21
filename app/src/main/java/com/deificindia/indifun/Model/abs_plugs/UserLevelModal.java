package com.deificindia.indifun.Model.abs_plugs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
{
    "result": {
        "level": "3",
        "my_xp": "871",
        "to_next_level": 329,
        "diamond": "11",
        "heart": "2"
    },
    "message": "success",
    "status": 1
}
* */
public class UserLevelModal {

    @SerializedName("level")
    @Expose
    private int level;
    @SerializedName("my_xp")
    @Expose
    private int myXp;
    @SerializedName("to_next_level")
    @Expose
    private int toNextLevel;
    @SerializedName("diamond")
    @Expose
    private String diamond;
    @SerializedName("heart")
    @Expose
    private String heart;

    public int getLevel1() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMyXp() {
        return myXp;
    }

    public void setMyXp(int myXp) {
        this.myXp = myXp;
    }

    public int getToNextLevel() {
        return toNextLevel;
    }

    public void setToNextLevel(int toNextLevel) {
        this.toNextLevel = toNextLevel;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

}
