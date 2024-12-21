package com.deificindia.indifun.Utility;

//import com.deificindia.indifun1.agorlive.ui.comp.LiveRoomUsers;
import com.deificindia.firebaseModel.GiftSenderModel;
import com.deificindia.indifun.Model.CountryNamesResult;
import com.deificindia.indifun.Model.abs2Modals.LeaderBoardModel;
import com.deificindia.indifun.Model.abs_plugs.HotpostResult;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;

import java.util.Comparator;

//import static com.deificindia.indifun1.agorlive.ui.comp.LiveRoomUsers.*;

/*
use
Collections.sort(arraylist, new DateComparator());
---------------------------------------------------
Collections.sort(list, new FirstNameSorter()
                                .thenComparing(new LastNameSorter())
                                .thenComparing(new AgeSorter()));
    -------------------
    Comparator c = Collections.reverseOrder();
      Collections.sort(list,c)
 */
public class ListSorting {

   /* public static class UserListSort implements Comparator<UserModal> {

        @Override
        public int compare(UserModal o1, UserModal o2) {
            if(o1==null && o2==null){
                return -1;
            }
            return o1.userLevel - o2.userLevel;
        }
    }*/

    public static class shortDiamondsResult implements Comparator<LeaderBoardModel> {

        @Override
        public int compare(LeaderBoardModel o1, LeaderBoardModel o2) {
            if(o1.getDiamondCount() > o2.getDiamondCount()){
                return 1;
            }else if(o1.getDiamondCount() < o2.getDiamondCount()){
                return -1;
            }

            return 0;
        }
    }
    public static class shortCoinsResult implements Comparator<LeaderBoardModel> {

        @Override
        public int compare(LeaderBoardModel o1, LeaderBoardModel o2) {
            if(o1.getCoinCount() > o2.getCoinCount()){
                return 1;
            }else if(o1.getCoinCount() < o2.getCoinCount()){
                return -1;
            }

            return 0;
        }
    }
    public static class shortFollowerResult implements Comparator<LeaderBoardModel> {

        @Override
        public int compare(LeaderBoardModel o1, LeaderBoardModel o2) {
            if(o1.getFollowersCount() > o2.getFollowersCount()){
                return 1;
            }else if(o1.getFollowersCount() < o2.getFollowersCount()){
                return -1;
            }

            return 0;
        }
    }

    public static class ShortHotpostmodelByTime implements Comparator<HotpostResult> {

        @Override
        public int compare(HotpostResult o1, HotpostResult o2) {
            if(o1 ==null || o2==null) return -1;
            if(o1.getTime_milli() > o2.getTime_milli()){
                return -1;
            }

            return 0;
        }
    }


    public static class ShortRoomUserInfoByPoints implements Comparator<RoomUserInfo> {

        @Override
        public int compare(RoomUserInfo o1, RoomUserInfo o2) {
            if(o1 ==null || o2==null) return -1;
            if(o1.points > o2.points){
                return -1;
            }/*else if(o1.getTime_milli() < o2.getTime_milli()){
                return -1;
            }*/

            return 0;
        }
    }

    public static class ShortGiftSenderModel implements Comparator<GiftSenderModel> {

        @Override
        public int compare(GiftSenderModel o1, GiftSenderModel o2) {
            if(o1 ==null || o2==null) return -1;
            if(o1.points > o2.points){
                return -1;
            }/*else if(o1.getTime_milli() < o2.getTime_milli()){
                return -1;
            }*/

            return 0;
        }
    }
    public static class ShortCountry implements Comparator<CountryNamesResult.MyCountry> {

        @Override
        public int compare(CountryNamesResult.MyCountry o1, CountryNamesResult.MyCountry o2) {
            if(o1 ==null || o2==null) return -1;
            if(o1.getId() < o2.getId()){
                return -1;
            }/*else if(o1.getTime_milli() < o2.getTime_milli()){
                return -1;
            }*/

            return 0;
        }
    }


}
