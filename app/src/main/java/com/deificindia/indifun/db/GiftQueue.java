package com.deificindia.indifun.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GiftQueue {
    @PrimaryKey(autoGenerate = true)
    public int pid;

    public int isused;
    public int isreceived;

    public long giftid;
    public int gifttype;
    public String animation;
    public String name;

    public GiftQueue() {}

    public GiftQueue(int isused, int isreceived, long giftid, int gifttype, String animation, String name) {
        this.isused = isused;
        this.isreceived = isreceived;
        this.giftid = giftid;
        this.gifttype = gifttype;
        this.animation = animation;
        this.name = name;
    }

}
