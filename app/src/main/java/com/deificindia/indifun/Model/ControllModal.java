package com.deificindia.indifun.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ControllModal implements Parcelable {
    public int TYPE;
    public String data;
    public String dataone;
    public String datato;
    public String datath;
    public String[] strs;
    public int[] ints;
    public long[] longs;
    public int bool;

    public ControllModal(int TYPE, String data) {
        this.TYPE = TYPE;
        this.data = data;
    }

    public ControllModal(int TYPE, int bool) {
        this.TYPE = TYPE;
        this.bool = bool;
    }

    public ControllModal(int TYPE, String data, String dataone, int b) {
        this.TYPE = TYPE;
        this.data = data;
        this.dataone = dataone;
        this.bool = b;
    }


    public ControllModal(int TYPE, String data, String dataone, String datato) {
        this.TYPE = TYPE;
        this.data = data;
        this.dataone = dataone;
        this.datato = datato;
    }


    public ControllModal(int TYPE, String[] strs, long[] longs) {
        this.TYPE = TYPE;
        this.strs = strs;
        this.longs = longs;
    }

    public ControllModal(int TYPE, String[] strs, long[] longs, int bool) {
        this.TYPE = TYPE;
        this.strs = strs;
        this.longs = longs;
        this.bool = bool;
    }

    public ControllModal(int TYPE, String[] strs, int[] ints) {
        this.TYPE = TYPE;
        this.strs = strs;
        this.ints = ints;
    }

    protected ControllModal(Parcel in) {
        TYPE = in.readInt();
        data = in.readString();
        dataone = in.readString();
        datato = in.readString();
        datath = in.readString();
        strs = in.createStringArray();
        ints = in.createIntArray();
        bool = in.readInt();
        longs = in.createLongArray();
    }

    public static final Creator<ControllModal> CREATOR = new Creator<ControllModal>() {
        @Override
        public ControllModal createFromParcel(Parcel in) {
            return new ControllModal(in);
        }

        @Override
        public ControllModal[] newArray(int size) {
            return new ControllModal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(TYPE);
        parcel.writeString(data);
        parcel.writeString(dataone);
        parcel.writeString(datato);
        parcel.writeString(datath);
        parcel.writeIntArray(ints);
        parcel.writeStringArray(strs);
        parcel.writeInt(bool);
        parcel.writeLongArray(longs);
    }
}
