package com.deificindia.indifun.Adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Activities.ChatActivity;
import com.deificindia.indifun.Model.retro.MsgModel_Result;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.UiUtils;

import java.util.List;


public class MassegeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MsgModel_Result> msgModel_result;
    private String localuser_id;
    private Context context;
    int index=0;

    public MassegeAdapter(List<MsgModel_Result> msgModel_result, Context context, String luid) {
        this.msgModel_result = msgModel_result;
        this.context = context;
        this.localuser_id = luid;
    }

    public void updateMessage(List<MsgModel_Result> result){
        this.msgModel_result=result;
        notifyDataSetChanged();
    }

    public void update(MsgModel_Result res){
        this.msgModel_result.add(res);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return msgModel_result.get(position).getUserfrom().equals(localuser_id)?1:0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View  view = null;
        switch (i) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.massage_adapter_layout, parent, false);
                return new LftMsgHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_massage_layout, parent, false);
                return new rghtMsgHolder(view);

        }
        return null;
        
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        final MsgModel_Result mlist = msgModel_result.get(pos);
        switch (getItemViewType(pos)){
            case 0:
                ((LftMsgHolder) holder).bindlftmsg(mlist);
                break;
            case 1:
                ((rghtMsgHolder) holder).bindrghtmsg(mlist);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return msgModel_result.size();
    }
    

    public class LftMsgHolder extends RecyclerView.ViewHolder {
        View parent;
        TextView tvDate, msg_text;
        ImageView done;

        public LftMsgHolder(View v2) {
            super(v2);
            parent = v2.findViewById(R.id.parent);
            msg_text = v2.findViewById(R.id.msg_text);
            tvDate = v2.findViewById(R.id.tvDate);
            done = v2.findViewById(R.id.done);
        }

        public void bindlftmsg(MsgModel_Result mlist) {
            msg_text.setText(mlist.getMessage());
            tvDate.setText(UiUtils.agotime(mlist.mili_time));
        }
    }

    public class rghtMsgHolder extends RecyclerView.ViewHolder {
        View parent;
        TextView tvDate, msg_text;
        ImageView done;

        public rghtMsgHolder(View v2) {
            super(v2);
            parent = v2.findViewById(R.id.parent);
            msg_text = v2.findViewById(R.id.msg_text);
            tvDate = v2.findViewById(R.id.tvDate);
            done = v2.findViewById(R.id.done);
        }

        public void bindrghtmsg(MsgModel_Result mlist) {
            msg_text.setText(mlist.getMessage());
            tvDate.setText(UiUtils.setAgoTime(mlist.date, mlist.time));

            if(mlist.sender > 0){
                addanim(parent);
            }

        }
    }


    void sendmessageanimation(View layout){
        GradientDrawable gd = new GradientDrawable();
        // Specify the shape of drawable
        gd.setShape(GradientDrawable.RECTANGLE);
        // Set the fill color of drawable
        gd.setColor(Color.TRANSPARENT); // make the background transparent
        // Create a 2 pixels width red colored border for drawable
        gd.setStroke(2, Color.RED); // border width and color
        // Make the border rounded
        gd.setCornerRadius(15.0f);

      /*  ((RelativeLayout) findViewById(R.id.rlvView)).setBackground(gd);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_from_bottom);
        ((RelativeLayout) findViewById(R.id.rlvView)).startAnimation(animation);*/

        // Make the border rounded
        gd.setCornerRadius(15.0f);
        //((RelativeLayout) findViewById(R.id.rlvView)).setBackground(gd);
        layout.setBackground(gd);

        ObjectAnimator animator = ObjectAnimator.ofFloat(gd, "cornerRadius", 0);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(800);
        animator.start();

      /*  ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY", 100,0);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(1000);
        animation.start();*/

    }

    void addanim(View view){

        if(context instanceof ChatActivity){
            ChatActivity act = (ChatActivity) context;
            LinearLayout ll = act.typingLayout;

            ObjectAnimator animationY = ObjectAnimator.ofFloat(view, "translationY", ll.getY(), 0);
            ObjectAnimator animationX = ObjectAnimator.ofFloat(view, "translationX", ll.getX(), 0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.setDuration(200);
            animatorSet.playTogether(animationX, animationY);
            animatorSet.start();
        }



    }
}
