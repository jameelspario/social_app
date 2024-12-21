package com.deificindia.indifun.deificpk.widgets.messeging;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.chat.Model.User;
import com.deificindia.indifun.R;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.logpk;

public class LiveRoomMessageAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int MAX_SAVED_MESSAGE = 50;
    private ArrayList<LiveRoomMesgModal> mMessageList = new ArrayList<>();
    private String sdfuid;
    private Context context;

    public LiveRoomMessageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new GiftMessageHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.message_item_gift_layout, parent, false));
            case 1:
                return new ChatMessageHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.message_item_layout, parent, false));

            case 2:
                return new FirstMessageHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.blue_message_item_layout, parent, false));
            case 5:
                return new SytemMessageHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_system_message_layout, parent, false));
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LiveRoomMesgModal item = mMessageList.get(position);
loge("",item.wuid);
        sdfuid=item.fuid;
        if(getItemViewType(position)==0){
            ((GiftMessageHolder)holder).bing(item);
        }else if(getItemViewType(position)==1){
            ((ChatMessageHolder)holder).bing(item);
        }else if(getItemViewType(position)==2){
            ((FirstMessageHolder)holder).bing(item);
        }
        else if(getItemViewType(position)==5){
            ((SytemMessageHolder)holder).bing(item);
        }
    }


    @Override
    public int getItemCount() { return mMessageList.size(); }

    @Override
    public int getItemViewType(int position) {
        return mMessageList.get(position).type;
    }

    public void addMessage(LiveRoomMesgModal item) {
        if (mMessageList.size() == MAX_SAVED_MESSAGE) {
            mMessageList.remove(mMessageList.size() - 1);
        }

        mMessageList.add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        mMessageList.clear();
        notifyDataSetChanged();
    }

    /////////////////holders\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    class GiftMessageHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView messageText;
        private AppCompatImageView giftIcon;

        public GiftMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.live_message_item_text);
            giftIcon = itemView.findViewById(R.id.live_message_gift_icon);

        }

        void bing(LiveRoomMesgModal item){ }
    }

    class ChatMessageHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView nameText;
        private AppCompatTextView messageText;

        private View layout1, background_layout;
        private LinearLayout linearLayout;
        UserTags userTags;



        public ChatMessageHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.tvName);
            messageText = itemView.findViewById(R.id.live_message_item_text);
            layout1 = itemView.findViewById(R.id.live_message_item_layout);
            background_layout = itemView.findViewById(R.id.background_layout);
            linearLayout = itemView.findViewById(R.id.tags);

            userTags = UserTags.instance().container(linearLayout);

        }

        void bing(LiveRoomMesgModal item){
            int background = item.backgroud==-1 ? R.drawable.round_scalable_gray_transparent_bg : item.backgroud;
            background_layout.setBackgroundResource(background);

            LiveRoomMesgModal.Item user = item.user;
            LiveRoomMesgModal.Item message = item.message;

            nameText.setText(user.text==null?"":user.text);
            if(item.fuid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                textGradient(nameText, user.text);
            else nameText.setTextColor(colorRes(user.color));


            messageText.setTextColor(colorRes(message.color));
            messageText.setText(message.text==null?"":message.text);

            itemView.setOnClickListener(v->{
                loge("MSG", item.fuid, item.wuid);
                if(_add_Listener!=null) _add_Listener.onUserClick(item.fuid, item.wuid, item.user.text);
            });


            userTags.addTo(UserTags.LEVEL)
                    .updateLevel(item.lvl + " Lvl.");

        }


        void textGradient(TextView textView, String txt){
            TextPaint paint = textView.getPaint();
            float width = paint.measureText(txt);

            Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                    new int[]{
                            Color.parseColor("#F97C3C"),
                            Color.parseColor("#FDB54E"),
                            Color.parseColor("#64B678"),
                            Color.parseColor("#478AEA"),
                            Color.parseColor("#8446CC"),
                    }, null, Shader.TileMode.CLAMP);
            textView.getPaint().setShader(textShader);
        }
    }

    private int colorRes(int col){
        return context.getResources().getColor(col);
    }

    class FirstMessageHolder extends RecyclerView.ViewHolder{
        private AppCompatImageView imageView;
        private AppCompatTextView messageText;
        private View layout;

        public FirstMessageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.live_icon);
            messageText = itemView.findViewById(R.id.live_message_item_text);
            layout = itemView.findViewById(R.id.background);

        }

        void bing(LiveRoomMesgModal item){
            int background = item.backgroud==-1 ? R.drawable.round_scalable_gray_transparent_bg : item.backgroud;
            layout.setBackgroundResource(background);

            messageText.setText(item.message.text);
            int messageColor = item.message.color==-1 ? R.color.white : item.message.color;
            messageText.setTextColor(context.getResources().getColor(messageColor));

            if(item.image!=-1) imageView.setImageResource(item.image);
            else imageView.setVisibility(View.GONE);
        }
    }

    class SytemMessageHolder extends RecyclerView.ViewHolder{
        View background_layout;
        AppCompatTextView vtext;
        public SytemMessageHolder(@NonNull View itemView) {
            super(itemView);

            background_layout = itemView.findViewById(R.id.background_layout);
            vtext = itemView.findViewById(R.id.tvMessage);
        }

        public void bing(LiveRoomMesgModal item) {

            int background = item.backgroud==-1 ? R.drawable.round_scalable_blue_transparent_bg : item.backgroud;
            background_layout.setBackgroundResource(background);

            String text = item.user.text + ":  " + item.message.text;
            SpannableString messageSpan = new SpannableString(text);

            messageSpan.setSpan(new StyleSpan(Typeface.BOLD),
                    0, item.user.text.length() + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            messageSpan.setSpan(new ForegroundColorSpan(Color.WHITE),
                    0, item.user.text.length() + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            messageSpan.setSpan(new ForegroundColorSpan(Color.YELLOW),
                    item.user.text.length() + 2, messageSpan.length(),
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            vtext.setText(messageSpan);
        }
    }

    OnAddClickListener _add_Listener;

    public interface OnAddClickListener{
        void onUserClick(String fuid, String wuid, String name);
    }

    public void setListener(OnAddClickListener listener){ _add_Listener = listener; }

}
