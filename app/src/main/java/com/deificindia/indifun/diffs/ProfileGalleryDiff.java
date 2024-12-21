package com.deificindia.indifun.diffs;

import androidx.recyclerview.widget.DiffUtil;

import com.deificindia.indifun.Model.abs_plugs.ProfileGalleryModal;

import java.util.List;

public class ProfileGalleryDiff extends DiffUtil.Callback{

    List<ProfileGalleryModal> oldlist;
    List<ProfileGalleryModal> newlist;

    public ProfileGalleryDiff(List<ProfileGalleryModal> oldlist, List<ProfileGalleryModal> newlist) {
        this.oldlist = oldlist;
        this.newlist = newlist;
    }

    @Override
    public int getOldListSize() {
        return oldlist.size();
    }

    @Override
    public int getNewListSize() {
        return newlist.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldlist.get(oldItemPosition).user_id!=null &&
                newlist.get(newItemPosition).user_id!=null &&
                oldlist.get(oldItemPosition).user_id.equals(newlist.get(newItemPosition).user_id);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldlist.get(oldItemPosition).id == (newlist.get(newItemPosition).id) ||
                oldlist.get(oldItemPosition).image.equals(newlist.get(newItemPosition).image);
    }


}
