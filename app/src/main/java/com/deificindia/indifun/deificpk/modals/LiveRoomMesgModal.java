package com.deificindia.indifun.deificpk.modals;


import java.util.List;

public class LiveRoomMesgModal {

    public int type;
    public int image = -1;
    public int backgroud = -1; //R.drawable.bg
    public int giftIndex;
    public Item message;
    public Item user;
    public List<Tags> tagsList;

    public static class Item{
        public String text;
        public int color = -1; //user //type ==2 >> R.color.black || type==1 >> Color.WHITE

        public Item() { }

        public Item(String text, int color) {
            this.text = text;
            this.color = color;
        }
    }

    class Tags{
        public String tagicon;
        public String tagtext;
    }
}
