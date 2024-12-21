
package com.deificindia.indifun.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Userlevel_Result {

    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("my_xp")
    @Expose
    private String myXp;
    @SerializedName("to_next_level")
    @Expose
    private Integer toNextLevel;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMyXp() {
        return myXp;
    }

    public void setMyXp(String myXp) {
        this.myXp = myXp;
    }

    public Integer getToNextLevel() {
        return toNextLevel;
    }

    public void setToNextLevel(Integer toNextLevel) {
        this.toNextLevel = toNextLevel;
    }

}
