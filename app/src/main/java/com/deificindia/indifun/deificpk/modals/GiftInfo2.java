package com.deificindia.indifun.deificpk.modals;

/*
{
"id": "75",
"gift_category": "2",
"name": "rocket",
"cover": "roc-removebg-preview.png",
"json_animation": "",
"image_sequence": "Rocket.svga",
"image_sequence2": "",
"type": "2",
"point": "30",
"entry_date": "2020-12-30"
}
* */

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GiftInfo2 {
    @PrimaryKey(autoGenerate = true)
    public int pid;

    public int id;
    public int gift_category;
    public String name;
    public String cover;
    public String animation;

    public int type;
    public int point;
    public String entry_date;
    public boolean isSelected=false;


}
