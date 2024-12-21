package com.deificindia.indifun.Model.abs2Modals;

import com.deificindia.indifun.Model.abs.Abs;

public class CheckinResult extends Abs {
    private long checkin_time;
    private long timecurrent;
    private int heart_daily;

    public long getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(long checkin_time) {
        this.checkin_time = checkin_time;
    }

    public long getTimecurrent() {
        return timecurrent;
    }

    public void setTimecurrent(long timecurrent) {
        this.timecurrent = timecurrent;
    }

    public int getHeart_daily() {
        return heart_daily;
    }

    public void setHeart_daily(int heart_daily) {
        this.heart_daily = heart_daily;
    }
}
