package com.deificindia.indifun.ui.imagelistview;


import com.deificindia.indifun.Utility.URLs;


public class MyImageModal {

    public static final int CIRCULAR = 1;
    public static final int RECTANGULAR = 2;

    public String image_link;

    public MyImageModal(String image_link) {
        this.image_link = image_link;
    }

    public String imageUrl(){
        return URLs.UserImageBaseUrl + image_link;
    }
}
