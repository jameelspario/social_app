package com.deificindia.indifun.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static java.lang.Math.abs;

/*
<BubbleEmitterView
    android:id="@+id/bubbleEmitter"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>

Handler().postDelayed({
            val size = Random.nextInt(20, 80)
            bubbleEmitter.emitBubble(size)
            emitBubbles()
        }, Random.nextLong(100, 500))
* */
public class BubbleEmitterView extends View {

    public static final int BUBBLE_LIMIT = 25;
    public static final int BASE_ALPHA = 255;
    public static final float NO_VALUE = -1f;


    private Handler pushHandler = new Handler();
    private ArrayList<Bubble> bubbles = new ArrayList<>();

    private Long emissionDelayMillis = 10L * bubbles.size();
    private Boolean canExplode = true;


    public BubbleEmitterView(Context context) {
        super(context);
    }

    public BubbleEmitterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleEmitterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paintStroke()  {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        paint.setStrokeWidth(2f);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    private Paint paintFill(){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    private Paint paintGloss(){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        paint.setStyle(Paint.Style.FILL);
        return paint;

    }

    static class Bubble{
        UUID uuid;
        Float radius;
        Float x = NO_VALUE;
        Float y = NO_VALUE;
        int alpha = BASE_ALPHA;
        Boolean alive = true;
        Boolean animating = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ArrayList<Bubble> bubbles2 = new ArrayList<>();

        for(Bubble bubble:bubbles){
            if(bubble.alive){
                bubbles2.add(bubble);
            }
        }
        bubbles = bubbles2;

        for (Bubble bubble:bubbles){
            int diameter = (int) (bubble.radius * 2);
            if(bubble.x==-1F){
                bubble.x = Float.valueOf(new Random().nextInt(getWidth() - diameter));
            }

            paintStroke().setAlpha(bubble.alpha);
            paintFill().setAlpha(bubble.alpha);
            paintGloss().setAlpha(bubble.alpha);

            canvas.drawCircle(bubble.x, bubble.y, bubble.radius, paintStroke());
            canvas.drawCircle(bubble.x, bubble.y, bubble.radius, paintFill());
            canvas.drawCircle(bubble.x + bubble.radius / 2.5F, bubble.y - bubble.radius / 2.5F, bubble.radius / 4, paintGloss());


            if (!bubble.animating) {
                bubble.animating = true;
                moveAnimation(bubble.uuid, bubble.radius).start();
                if (canExplode) {
                    explodeAnimation(bubble.uuid, bubble.radius).start();
                }
                fadeOutAnimation(bubble.uuid, bubble.radius).start();
            }

        }

    }


    public void emitBubble(int strength) {
        if (bubbles.size() >= BUBBLE_LIMIT) {
            return;
        }

        UUID uuid = UUID.randomUUID();
        Float radius = abs(strength) / 4F;
        Bubble bubble = new Bubble();
        bubble.uuid = uuid;
        bubble.radius = radius;

        pushHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bubbles.add(bubble);
            }
        },emissionDelayMillis);
        invalidate();
    }

     void setColors(@ColorInt int stroke/*: Int = rgb(249,249,249)*/,
                  @ColorInt int fill/*: Int = rgb(236,236,236)*/,
                  @ColorInt int gloss/*: Int = rgb(255,255,255)*/) {
         paintStroke().setColor(stroke);
         paintFill().setColor(fill);
         paintGloss().setColor(gloss);
    }

    void setColorResources(@ColorRes int stroke /* = R.color.ghostWhite*/,
                           @ColorRes int fill/*: Int = R.color.whiteSmoke*/,
                           @ColorRes int gloss/*: Int = android.R.color.white*/) {
        paintStroke().setColor(ContextCompat.getColor(getContext(), stroke));
        paintFill().setColor(ContextCompat.getColor(getContext(), fill));
        paintGloss().setColor(ContextCompat.getColor(getContext(), gloss));
    }

    void setEmissionDelay(long delayMillis){
        emissionDelayMillis = delayMillis;
    }
    void canExplode(boolean b){
        canExplode = b;
    }

    ////////////////////////////////////////////////////////////////////
    private ValueAnimator moveAnimation(UUID uuid, float radius){
        ValueAnimator animator = ValueAnimator.ofFloat(getHeight(), getHeight()/2f - radius* 10);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(!bubbles.isEmpty() && bubbles.get(0)!=null){
                    if(bubbles.get(0).uuid==uuid){
                        bubbles.get(0).y = (Float) animation.getAnimatedValue();
                        invalidate();
                    }
                }
            }
        });
        animator.setDuration((long) (2000L + 100L * radius));
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    private ValueAnimator fadeOutAnimation(UUID uuid, float radius){
        ValueAnimator animator = ValueAnimator.ofInt(BASE_ALPHA, 0);
        animator.addUpdateListener(animation -> bubbles.get(0).alpha = (int) animation.getAnimatedValue());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if(!bubbles.isEmpty() && bubbles.get(0)!=null){
                    if(bubbles.get(0).uuid == uuid){
                        bubbles.get(0).alive = false;
                        invalidate();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        animator.setDuration(200L);
        animator.setStartDelay((long) (1000L + 100L * radius));
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    private ValueAnimator explodeAnimation(UUID uuid, Float radius){
        ValueAnimator animator = ValueAnimator.ofFloat(radius, radius * 2);
        animator.addUpdateListener(animation -> {
            if(!bubbles.isEmpty() && bubbles.get(0)!=null){
                if(bubbles.get(0).uuid==uuid){
                    bubbles.get(0).radius = (Float) animation.getAnimatedValue();
                }
            }
        });
        animator.setDuration(300L);
        animator.setStartDelay((long) (1000L + 100L * radius));
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
