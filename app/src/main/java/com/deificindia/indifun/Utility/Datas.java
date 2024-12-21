package com.deificindia.indifun.Utility;

import com.deificindia.indifun.Model.abs_plugs.ProfileGalleryModal;

import java.util.ArrayList;
import java.util.List;

public class Datas {

    public static List<ProfileGalleryModal> profileGalleryItems(String uid){
        List<ProfileGalleryModal> list = new ArrayList<>();

        for (int i=0; i<9; i++){
            list.add(new ProfileGalleryModal(-1, uid));
        }

        return list;
    }
}
