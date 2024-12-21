package com.deificindia.indifun.deificpk.modals;

import com.deificindia.indifun.Model.abs.Abs;

import java.util.List;

public class GiftListResponse2 extends Abs {
    public List<GiftInfo2> result;

    public List<GiftInfo2> getResult() {
        return result;
    }

    public void setResult(List<GiftInfo2> result) {
        this.result = result;
    }

}
