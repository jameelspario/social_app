package com.deificindia.indifun.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.deificindia.indifun.R;

import com.deificindia.indifun.Utility.DownloadGift;
import com.deificindia.indifun.Utility.DownloadGiftCallbacks;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.deificpk.gif.GifParser;
import com.deificindia.indifun.deificpk.widgets.PkLayout;
import com.deificindia.indifun.ui.BubbleEmitterView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.Random;

import static com.deificindia.indifun.Utility.URLs.GifgBaseUrl;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;

public class Test1Activity extends AppCompatActivity {

    BubbleEmitterView bubbleEmitterView;
//    ImageFloatView imageFloatView;
//    GiftAnim giftanim;

    SimpleDraweeView draweeView;
    FrameLayout frameLayout;

    PkLayout pkLayout;
    EditText t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        //bubbleEmitterView = findViewById(R.id.bubble);
        draweeView = findViewById(R.id.simpledrawee);
        frameLayout = findViewById(R.id.frame);
//        imageFloatView = findViewById(R.id.bloatview);
//        giftanim = findViewById(R.id.giftanim);

        //emittbubble();

        t1 = findViewById(R.id.txt1);
        t2 = findViewById(R.id.txt22);
        //pkLayout = findViewById(R.id.pk);
    }


    void emittbubble(){
        Random random1 = new Random();
        int timne = random1.nextInt(500-100) + 100;

        new Handler().postDelayed(()->{
            Random random = new Random();
            int size = random.nextInt(80-20) + 20;
            bubbleEmitterView.emitBubble(size);

        }, timne);
    }





    GifParser gifParser;
    boolean isPlaying;
    public void onGift(){
//        GiftAnimView gav=new GiftAnimView(this);
//        gav.setData("", "Indifun", "5", "gave gift", R.drawable.bg_red);
//      giftanim.generateView(gav);
//       giftanim.animateMe(gav,true);

        if(!isPlaying) {
            if (frameLayout.getChildCount() > 0) frameLayout.removeAllViews();
            gifParser = GifParser.instance(this).container(frameLayout).parse("gif");

        }else {
            gifParser.finish();
        }
        isPlaying = !isPlaying;
    }

    private void giftAnim(String url){
        DownloadGift.instance()
                //.with(this, GifgBaseUrl + url, url /*"diya4.zip"*/)
                .with(this, GifgBaseUrl+url, url)
                .listener(new DownloadGiftCallbacks() {
                    @Override
                    public void onPostExecute(AnimationDrawable msg) {
                        //super.play2(lottieAnimationView, msg);

                    }

                    @Override
                    public void onGif(Bundle bundle) {

                    }



                    @Override
                    public void onEnd() {
                        //if (topFrame.getChildCount() > 0) topFrame.removeAllViews();
                        //lottieAnimationView.setBackgroundResource(0);
                    }
                })
                .init();
    }

    public void update(View view) {

        String t = t1.getText().toString();
        String s = t2.getText().toString();

       // pkLayout.setPoints(Long.parseLong(t), Long.parseLong(s));

    }

    public void onBubble(View view) {
        //onGift();
        pkLayout.setResult(1);
    }

    public void onBubble2(View view) {
        pkLayout.setResult(2);
    }
    public void onBubble3(View view) {
        pkLayout.setResult(0);
    }
    public void onBubble4(View view) {
        pkLayout.removeResult();
    }
}