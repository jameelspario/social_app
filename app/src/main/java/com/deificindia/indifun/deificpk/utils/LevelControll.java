package com.deificindia.indifun.deificpk.utils;

import com.deificindia.indifun.R;

import java.util.ArrayList;
import java.util.List;

public class LevelControll {
    public static final int C1 = 300;
    public static final int C2 = 600;
    public static final int C3 = 1200;
    public static final int C4 = 2400;
    public static final int C5 = 4800;
    public static final int C6 = 9600;
    public static final int C7 = 19200;
    public static final int C8 = 28400;
    public static final int C9 = 76800;

    public static String getLevel(long xp){
        if(xp <= C1) return "1";
        else if(xp > C1 && xp <= C2 ) return "2";
        else if(xp > C2 && xp <= C3 ) return "3";
        else if(xp > C3 && xp <= C4 ) return "4";
        else if(xp > C4 && xp <= C5 ) return "5";
        else if(xp > C5 && xp <= C6 ) return "6";
        else if(xp > C6 && xp <= C7 ) return "7";
        else if(xp > C7 && xp <= C8 ) return "8";
        else if(xp > C8 && xp <= C9 ) return "9";
        else if(xp > C9) return "10";
        else return "0";
    }

    public static int getLevelFrame(String level){
        switch (level){
            case "1": return R.drawable.frame_1;
            case "2": return R.drawable.frame_2;
            case "3": return R.drawable.frame_3;
            case "4": return R.drawable.frame_4;
            case "5": return R.drawable.frame_5;
            case "6": return R.drawable.frame_5;
            case "7": return R.drawable.frame_5;
            case "8": return R.drawable.frame_5;
            case "9": return R.drawable.frame_09;
            case "10": return R.drawable.frame_5;

            default: return R.drawable.frame_1;
        }
    }

    public static int getFrameByXp(String xp){
        return getFrameByXp(!xp.isEmpty()?Long.parseLong(xp):1);
    }

    public static int getFrameByXp(long xp){
        return getLevelFrame(getLevel(xp));
    }

    public static int getUsernameColor(String level){
        switch (level){
            case "1": return R.color.name_color_1;
            case "2": return R.color.name_color_2;
            case "3": return R.color.name_color_3;
            case "4": return R.color.name_color_4;
            case "5": return R.color.name_color_5;
            case "6": return R.color.name_color_6;
            case "7": return R.color.name_color_7;
            case "8": return R.color.name_color_8;
            case "9": return R.color.name_color_9;
            case "10": return R.color.name_color_10;

            default: return R.color.name_color_1;
        }
    }

    public static int getUserMessageColor(String level){
        switch (level){
            case "1": return R.color.msg_color_1;
            case "2": return R.color.msg_color_2;
            case "3": return R.color.msg_color_3;
            case "4": return R.color.msg_color_4;
            case "5": return R.color.msg_color_5;
            case "6": return R.color.msg_color_6;
            case "7": return R.color.msg_color_7;
            case "8": return R.color.msg_color_8;
            case "9": return R.color.msg_color_9;
            case "10": return R.color.msg_color_10;

            default: return R.color.msg_color_1;
        }
    }

    public static int getLevelGiftBg(String level){
        switch (level){
            case "1": return R.drawable.frame_1;
            case "2": return R.drawable.frame_2;
            case "3": return R.drawable.frame_3;
            case "4": return R.drawable.frame_4;
            case "5": return R.drawable.frame_5;
            case "6": return R.drawable.frame_5;
            case "7": return R.drawable.frame_5;
            case "8": return R.drawable.frame_5;
            case "9": return R.drawable.frame_5;
            case "10": return R.drawable.frame_5;

            default: return R.drawable.frame_1;
        }
    }

    public static int getLevelHeartGiftBg(String level){
        switch (level){
            case "1": return R.drawable.frame_1;
            case "2": return R.drawable.frame_2;
            case "3": return R.drawable.frame_3;
            case "4": return R.drawable.frame_4;
            case "5": return R.drawable.frame_5;
            case "6": return R.drawable.frame_5;
            case "7": return R.drawable.frame_5;
            case "8": return R.drawable.frame_5;
            case "9": return R.drawable.frame_5;
            case "10": return R.drawable.frame_5;

            default: return R.drawable.frame_1;
        }
    }

   static ArrayList<Integer> samples = new ArrayList();

    public static int rndHeart(){
        if (samples.size() == 0) {
            samples.add(R.drawable.ic_heart_red_1);
            samples.add(R.drawable.ic_heart_red_2);
            samples.add(R.drawable.ic_heart_blue_1);
            samples.add(R.drawable.ic_heart_voilet_2);
        }
        return samples.get((int) Math.floor(Math.random() * samples.size()));
    }


    public static int getMinLevelLimit(int totallvl){
        if(totallvl>C1 && totallvl<C2) return C1;
        if(totallvl>C2 && totallvl<C3) return C2;
        if(totallvl>C3 && totallvl<C4) return C3;
        if(totallvl>C4 && totallvl<C5) return C4;
        if(totallvl>C5 && totallvl<C6) return C5;
        if(totallvl>C6 && totallvl<C7) return C6;
        if(totallvl>C7 && totallvl<C8) return C7;
        if(totallvl>C8 && totallvl<C9) return C8;
        else return C9;
    }

    private static final List<Integer> background = new ArrayList<>();
    public static int background(){
        if(background.isEmpty()){
            background.add(R.drawable.profile_image_1);
            background.add(R.drawable.profile_image_2);
            background.add(R.drawable.profile_image_3);
            background.add(R.drawable.profile_image_4);
            background.add(R.drawable.profile_image_5);
            background.add(R.drawable.profile_image_6);
            background.add(R.drawable.profile_image_7);
            background.add(R.drawable.profile_image_8);
            background.add(R.drawable.profile_image_9);
            background.add(R.drawable.profile_image_10);
            background.add(R.drawable.profile_image_11);
            background.add(R.drawable.profile_image_12);
        }

        return background.get((int) Math.floor(Math.random() * background.size()));
    }



}
