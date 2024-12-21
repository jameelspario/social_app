package com.deificindia.indifun.deificpk.widgets.messeging;


import com.deificindia.indifun.R;

public class LiveRoomMesgModal {

    public int type;
    public String fuid;
    public String wuid;
    public String lvl;
    public int image = -1;
    public int backgroud = -1; //R.drawable.bg
    public int giftIndex;
    public Item message;
    public Item user;

    public static class Item{
        String text;
        int color = R.color.white; //user //type ==2 >> R.color.black || type==1 >> Color.WHITE

        public Item() { }

        public Item(String text, int color) {
            this.text = text;
            this.color = color;
        }
    }


}
