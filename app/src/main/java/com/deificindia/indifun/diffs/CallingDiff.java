package com.deificindia.indifun.diffs;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.deificpk.frags.RemoteTrack;

import java.util.List;
import java.util.Objects;

public class CallingDiff  extends DiffUtil.Callback{

    List<EntityCall> oldlist;
    List<EntityCall> newlist;

    public CallingDiff(List<EntityCall> oldlist, List<EntityCall> newlist) {
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
        return  Objects.equals(oldlist.get(oldItemPosition).jfuid, newlist.get(newItemPosition).jfuid);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return  oldlist.get(oldItemPosition).equals(newlist.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
