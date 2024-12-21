package com.deificindia.indifun.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ImagesParsble implements Parcelable {
    List<String> images;

    public ImagesParsble() {

    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ImagesParsble(Parcel in) {
        images = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImagesParsble> CREATOR = new Creator<ImagesParsble>() {
        @Override
        public ImagesParsble createFromParcel(Parcel in) {
            return new ImagesParsble(in);
        }

        @Override
        public ImagesParsble[] newArray(int size) {
            return new ImagesParsble[size];
        }
    };
}
