package com.deificindia.indifun.dialogs.giftsheet;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.deificindia.indifun.deificpk.modals.GiftInfo2;

import java.util.List;

public class GiftDiffCall extends DiffUtil.Callback {

    List<GiftInfo2> oldlist;
    List<GiftInfo2> newlist;

    public GiftDiffCall(List<GiftInfo2> oldlist, List<GiftInfo2> newlist) {
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
    public boolean areItemsTheSame(int oldItem, int newItem) {
        return oldlist.get(oldItem).id==(newlist.get(newItem).id);
    }

    @Override
    public boolean areContentsTheSame(int oldItem, int newItem) {
        return oldlist.get(oldItem).equals(newlist.get(newItem));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Log.e("TAG-LSDiff", "payload called");
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
