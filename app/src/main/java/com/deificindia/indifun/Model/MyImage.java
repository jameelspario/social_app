package com.deificindia.indifun.Model;

import android.os.Parcel;
import android.os.Parcelable;

import static com.deificindia.indifun.Utility.URLs.UserPostImagesBaseUrl;

public class MyImage implements Parcelable {
    String id;
    String fuid;
    String image;
    String description;

    protected MyImage(Parcel in) {
        id = in.readString();
        fuid = in.readString();
        image = in.readString();
        description = in.readString();
    }

    public static final Creator<MyImage> CREATOR = new Creator<MyImage>() {
        @Override
        public MyImage createFromParcel(Parcel in) {
            return new MyImage(in);
        }

        @Override
        public MyImage[] newArray(int size) {
            return new MyImage[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFuid() {
        return fuid;
    }

    public void setFuid(String fuid) {
        this.fuid = fuid;
    }

    public String getImage() {
        return image;
    }

    public String getPostImage(){
        return UserPostImagesBaseUrl+image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fuid);
        dest.writeString(image);
        dest.writeString(description);
    }
}
