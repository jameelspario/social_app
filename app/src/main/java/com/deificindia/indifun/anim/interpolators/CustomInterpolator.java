package com.deificindia.indifun.anim.interpolators;

import android.graphics.PointF;
import android.view.animation.Interpolator;

/*
* ObjectAnimator.setInterpolator(new com.mico.live.ui.d.b(0.9f, 0.0f, 1.0f, 1.0f));
*
* */
public class CustomInterpolator implements Interpolator {
    private int a = 0;
    private final PointF b = new PointF();
    private final PointF c;

    public CustomInterpolator(float f2, float f3, float f4, float f5) {
        PointF pointF = new PointF();
        this.c = pointF;
        PointF pointF2 = this.b;
        pointF2.x = f2;
        pointF2.y = f3;
        pointF.x = f4;
        pointF.y = f5;
    }

    public static double a(double d, double d2, double d3, double d4, double d5) {
        double d6 = 1.0d - d;
        double d7 = d * d;
        double d8 = d6 * d6;
        return (d8 * d6 * d2) + (d8 * 3.0d * d * d3) + (d6 * 3.0d * d7 * d4) + (d7 * d * d5);
    }

    @Override
    public float getInterpolation(float f2) {
        int i2 = this.a;
        float f3 = f2;
        while (true) {
            if (i2 >= 4096) {
                break;
            }
            f3 = (((float) i2) * 1.0f) / 4096.0f;
            if (a((double) f3, 0.0d, (double) this.b.x, (double) this.c.x, 1.0d) >= ((double) f2)) {
                this.a = i2;
                break;
            }
            i2++;
        }
        double a2 = a((double) f3, 0.0d, (double) this.b.y, (double) this.c.y, 1.0d);
        if (a2 > 0.999d) {
            a2 = 1.0d;
            this.a = 0;
        }
        return (float) a2;
    }
}
