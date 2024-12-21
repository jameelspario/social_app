package com.deificindia.indifun.deificpk.modals;

import android.os.Parcel;
import android.os.Parcelable;

import com.deificindia.indifun.db.LiveEntity2;

public class Clip  implements Parcelable {
    public int id;
    public int colorbg;
    public int n;
    public boolean isowner;
    public String ownerfuid;
    public String ownerwuid;
    public String ownername;
    public String roomid;
    public String roomname;
    public long broadcastid;
    public String owneravtar;
    public long avtartype;

    public String self_fuid;
    public String self_wuid;

    public String broad_join_identity;

    public int is_friend;
    public int is_blocked;
    public int is_following;
    public int is_mute;
    public int is_kick;

    public Clip() { }

    public Clip(int id) {
        this.id = id;
    }

    protected Clip(Parcel in) {
        id = in.readInt();
        colorbg = in.readInt();
        n = in.readInt();
        isowner = in.readByte() != 0;
        ownerfuid = in.readString();
        ownerwuid = in.readString();
        ownername = in.readString();
        roomid = in.readString();
        roomname = in.readString();
        broadcastid = in.readLong();
        owneravtar = in.readString();
        avtartype = in.readLong();
        self_fuid = in.readString();
        self_wuid = in.readString();
       // broad_room_name = in.readString();
       // broadcast_id = in.readString();
        broad_join_identity = in.readString();
        is_friend = in.readInt();
        is_blocked = in.readInt();
        is_following = in.readInt();
        is_mute = in.readInt();
        is_kick = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(colorbg);
        dest.writeInt(n);
        dest.writeByte((byte) (isowner ? 1 : 0));
        dest.writeString(ownerfuid);
        dest.writeString(ownerwuid);
        dest.writeString(ownername);
        dest.writeString(roomid);
        dest.writeString(roomname);
        dest.writeLong(broadcastid);
        dest.writeString(owneravtar);
        dest.writeLong(avtartype);
        dest.writeString(self_fuid);
        dest.writeString(self_wuid);
       // dest.writeString(broad_room_name);
       // dest.writeString(broadcast_id);
       dest.writeString(broad_join_identity);
       dest.writeInt(is_friend);
       dest.writeInt(is_blocked);
       dest.writeInt(is_following);
       dest.writeInt(is_mute);
       dest.writeInt(is_kick);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Clip> CREATOR = new Creator<Clip>() {
        @Override
        public Clip createFromParcel(Parcel in) {
            return new Clip(in);
        }

        @Override
        public Clip[] newArray(int size) {
            return new Clip[size];
        }
    };

    public LiveEntity2 getOwnerInfo(){
        LiveEntity2 entity2 = new LiveEntity2();
        entity2.id = id;
        entity2.isOwner = isowner;
        entity2.roomid = roomid;
        entity2.roomname = roomname;
        entity2.owneruid = ownerfuid;
        entity2.broadid = broadcastid;
        entity2.owneravtar = owneravtar;
        entity2.owneravtartype = avtartype;
        entity2.broad_join_identity = broad_join_identity;

        return entity2;
    }
}
