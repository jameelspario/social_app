package com.deificindia.indifun.anim;

import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.deificindia.chat.Model.Chat;
import com.deificindia.indifun.R;
import com.deificindia.indifun.anim.interpolators.BounceInterpolator;
import com.deificindia.indifun.deificpk.animutils.GiftHeartAnimView;
import com.deificindia.indifun.deificpk.modals.ChatModal;
import com.deificindia.indifun.deificpk.utils.LevelControll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HearAnimatonInfoControll {

    LinearLayout layout_gift_2_animation;


    Map<String, GiftHeartAnimView> heartAnimViewMap = new HashMap<>();
    LinkedList<String> users = new LinkedList<>();
    LinkedHashMap<String, List<ChatModal>> linkedHashMap = new LinkedHashMap<>();

    List<String> playingfor = new ArrayList<>();

    public HearAnimatonInfoControll(LinearLayout layout_gift_2_animation) {
        this.layout_gift_2_animation = layout_gift_2_animation;
    }


    public void updateHeart(ChatModal modal){
        if(linkedHashMap.containsKey(modal.senderuid)){
            if(playingfor.contains(modal.senderuid)){

            }else{
                Objects.requireNonNull(linkedHashMap.get(modal.senderuid)).add(modal);
            }
        }else {
            List<ChatModal> chat = new ArrayList<>();
            chat.add(modal);
            linkedHashMap.put(modal.senderuid, chat);

            playHeartGiftInfo(modal.senderuid, modal.senderavtar, modal.sendername, modal.senderxp);
        }
    }


    private void getDataBeforePlaying(){
        List<ChatModal> list = linkedHashMap.remove("");
    }
    public void playHeartGiftInfo(String fuid, String avtar, String username, long xp) {
        GiftHeartAnimView animView = new GiftHeartAnimView(layout_gift_2_animation.getContext());

        layout_gift_2_animation.addView(animView);
        heartAnimViewMap.put(fuid, animView);

        animView.setX(-animView.getWidth());

        animView.init(UserImageBaseUrl+avtar, username, "send heart", R.drawable.ic_heart_red_1,
                LevelControll.getUsernameColor(LevelControll.getLevel(xp)));

        sideGiftAnim_slideIn(animView, fuid);

    }

    public void sideGiftAnim_slideIn(GiftHeartAnimView imageView, String fuid) {
        
        imageView.animate()
                .x(0)
                .setDuration(100)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        bounceAnim(imageView.getImg_heart());
                        imageView.updateGiftCount(1);
                        bounceAnim(imageView.getTv_no_of_heart());

                    }
                })
                .start();

    }

    private void bounceAnim(View v){
        final Animation myAnim = AnimationUtils.loadAnimation(layout_gift_2_animation.getContext(), R.anim.bounce);

        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        v.startAnimation(myAnim);
    }



}
