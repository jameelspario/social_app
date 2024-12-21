package com.deificindia.indifun.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

/*
state== 1=>request call
        2=>accept call
        3=>reject call

* */
@Entity
public class EntityCall {


    @Exclude
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String owner;
    public String room;
    public long index;

    public String jfuid;
    public String jwuid;
    public String jname;
    public String javtar;
    public long jtime;
    public long jxp;
    public long state;

    public boolean isaudiomute;
    public boolean iscameramute;
    public boolean isbackcamera;

    public EntityCall() { }


    public EntityCall(long index, long state) {
        this.index = index;
        this.state = state;
    }
}
