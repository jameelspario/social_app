package com.deificindia.indifun.Model.abs_plugs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LevelData {
    /*
"id": "1",
"max_point": "300",
"min_point": "0",
"level": "1",
"frame": null,
"entry_effect": null,
"msg_user_text": null,
"entry_info": null,
"msg_text": null,
"gift_effect": null
    * */

    @PrimaryKey(autoGenerate = true)
    public int table_id;
    public int id;
    public int max_point;
    public int min_point;
    public int level;
    public String frame;
    public String entry_effect;
    public String msg_user_text;
    public String entry_info;
    public String msg_text;
    public String gift_effect;


    public LevelData(int max_point, int min_point, int level) {
        this.max_point = max_point;
        this.min_point = min_point;
        this.level = level;
    }
}
