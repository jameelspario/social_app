package com.deificindia.indifun.dialogs.giftsheet;

import com.deificindia.indifun.deificpk.modals.GiftInfo2;

public class GiftItemListener {
    private static OnGiftItemSelectedListener _listenr;

    public static void setOnGiftItemSelectedListener(OnGiftItemSelectedListener listenr){
        _listenr = listenr;
    }

    public static void trigger(int pos, GiftInfo2 gift){
            if (_listenr!=null) _listenr.onItemSelected(pos, gift);
    }
}
