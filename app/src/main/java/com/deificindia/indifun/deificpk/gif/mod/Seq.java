package com.deificindia.indifun.deificpk.gif.mod;

import android.graphics.Point;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

public class Seq {
    public String name;
    public int on;
    public int off;
    public boolean loop;
    public boolean fade;
    public boolean zoomin;
    public boolean zoomout;
    public int zoomin_duration;
    public int zoomout_duration;
    public List<Float> from;
    public List<Float> to;

    public Point pointFrom;
    public Point pointTo;
    public Point pointCenter;

    public void point(FrameLayout frame, View view){
        if(this.from!=null && !this.from.isEmpty()){
            int x = (int) ((frame.getWidth()*from.get(0))-(view.getWidth()/2));
            int y = (int) ((frame.getHeight()*from.get(1))-(view.getHeight()/2));
            pointFrom = new Point(x, y);
        }

        if(this.to!=null && !this.to.isEmpty()){
            int x = (int) ((frame.getWidth()*to.get(0))-(view.getWidth()/2));
            int y = (int) ((frame.getHeight()*to.get(1))-(view.getHeight()/2));
            pointTo = new Point(x, y);
        }

        int centerx = frame.getWidth()/2 - view.getWidth()/2;
        int centery = frame.getHeight()/2 - view.getHeight()/2;
        pointCenter = new Point(centerx, centery);
    }
}
