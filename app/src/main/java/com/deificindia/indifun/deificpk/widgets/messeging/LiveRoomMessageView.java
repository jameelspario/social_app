package com.deificindia.indifun.deificpk.widgets.messeging;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.deificpk.utils.LevelControll;

import static com.deificindia.indifun.deificpk.utils.Const.TYPE_SYSTEM;


public class LiveRoomMessageView  extends RelativeLayout {
    public static final int MSG_TYPE_SYSTEM = 0;
    public static final int MSG_TYPE_CHAT = 1;
    public static final int MSG_TYPE_GIFT = 2;

    public static final int MSG_SYSTEM_STATE_JOIN = 1;
    public static final int MSG_SYSTEM_STATE_LEAVE = 0;

    public static final int MSG_SYSTEM_ROLE_OWNER = 1;
    public static final int MSG_SYSTEM_ROLE_HOST = 2;
    public static final int MSG_SYSTEM_ROLE_AUDIENCE = 3;

    private static final int MESSAGE_TEXT_COLOR = Color.rgb(196, 196, 196);
    private static final int MESSAGE_TEXT_COLOR_LIGHT = Color.argb(101, 35, 35, 35);
    public static final int MAX_SAVED_MESSAGE = 50;
    private static final int MESSAGE_ITEM_MARGIN = 8;

    //public LiveMessageEditLayout liveMessageEditLayout;
    private LiveRoomMessageAdapter mAdapter;
    private LayoutInflater mInflater;
    private LinearLayoutManager mLayoutManager;
    private boolean mLightMode;

    private String mJoinNotificationText;
    private String mLeaveNotificationText;

    RecyclerView message_recyclerview;
    TextView txt_user_enter_message;
    View room_enter_layout;

    private Handler mHandler;

    int screen_width = 480;

    public LiveRoomMessageView(@NonNull Context context) {
        super(context);

    }

    public LiveRoomMessageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveRoomMessageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int sw) {
        this.screen_width = sw;
        init(false);
    }

    public void init(boolean lightMode) {
        mLightMode = lightMode;
        mHandler = new Handler(Looper.myLooper());
        mInflater = LayoutInflater.from(getContext());
        View v = mInflater.inflate(R.layout.room_message_layout, this, true);

        message_recyclerview = v.findViewById(R.id.message_recyclerview);

        room_enter_layout = v.findViewById(R.id.room_enter_layout);
        txt_user_enter_message = v.findViewById(R.id.txt_user_enter_message);
        txt_user_enter_message.setText("Hi");
        initUi();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mHeight = (screen_width/8)*5;

        if(getLayoutParams().height== ViewGroup.LayoutParams.WRAP_CONTENT){
            int width = getContext().getResources().getDisplayMetrics().widthPixels;
            int mMaxWidth = MeasureSpec.getSize(widthMeasureSpec);
            //setMeasuredDimension(mMaxWidth, mHeight);
            int widthSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY);
            int heightSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);

            super.onMeasure(widthSpec, heightSpec);
            //super.onMeasure(widthMeasureSpec, height);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    void initUi(){
        mAdapter = new LiveRoomMessageAdapter(getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setStackFromEnd(true);
        message_recyclerview.setLayoutManager(mLayoutManager);
        message_recyclerview.setAdapter(mAdapter);
        message_recyclerview.addItemDecoration(new MessageItemDecorator());

        mJoinNotificationText = getResources().getString(R.string.live_system_notification_member_joined);
        mLeaveNotificationText = getResources().getString(R.string.live_system_notification_member_left);
    }

    public void scroll_to_end(){
        try {
            mLayoutManager.scrollToPosition(mAdapter.getItemCount() - 1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void joinMessage(int type, String user, String message, int... index) {
        if(type==MSG_TYPE_SYSTEM){
            room_enter_layout.setVisibility(VISIBLE);
            txt_user_enter_message.setText(onUserJoinRoomMessage(user, message));
            removeEnter();
            return;
        }
    }

    public void addMessage(int type,  String user, String message, String fuid, String wuid, long senderxpOrSystemType) {
        LiveRoomMesgModal item = new LiveRoomMesgModal();
        item.type = type;
        item.fuid = fuid;
        item.wuid = wuid;

        if(type==TYPE_SYSTEM){
            switch ((int) senderxpOrSystemType){
                case 1: message = "is blocked."; break;
                case 2: message = "is muted in broad."; break;
                case 3: message = "User is kicked out from broad."; break;
                default: message = "";
            }
            item.user = new LiveRoomMesgModal.Item(user, 0);
            item.message = new LiveRoomMesgModal.Item(message, 0);
        }else{
            String lvl = LevelControll.getLevel(senderxpOrSystemType);
            item.lvl = lvl;
            item.user = new LiveRoomMesgModal.Item(user, LevelControll.getUsernameColor(lvl));
            item.message = new LiveRoomMesgModal.Item(message, LevelControll.getUserMessageColor(lvl));
        }

        notifyChange(item);
    }

    public void onJoinRoomFirsTime(){
        String message = getResources().getString(R.string.first_mesage);
        LiveRoomMesgModal item = new LiveRoomMesgModal(
               /* 2, -1, new LiveRoomMesgModal.Item(message, R.color.welcome_message_color)*/);
        item.type = 2;
        item.message =  new LiveRoomMesgModal.Item(message, R.color.welcome_message_color);
        notifyChange(item);
    }

    public SpannableString onUserJoinRoomMessage(String user, String message){
        String text = user + ": " +message;
        SpannableString messageSpan = new SpannableString(text);
        messageSpan.setSpan(new StyleSpan(Typeface.BOLD),
                0, user.length() + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        messageSpan.setSpan(new ForegroundColorSpan(Color.WHITE),
                0, user.length() + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return messageSpan;
    }

    void notifyChange(LiveRoomMesgModal item){
        mAdapter.addMessage(item);
        mLayoutManager.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    public void clear(){
        mAdapter.clear();
    }

    private static class MessageItemDecorator extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = MESSAGE_ITEM_MARGIN;
            outRect.bottom = MESSAGE_ITEM_MARGIN;

        }
    }


    void removeEnter(){
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 3000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            room_enter_layout.setVisibility(GONE);
        }
    };


    public void setListener(LiveRoomMessageAdapter.OnAddClickListener listener){ mAdapter.setListener(listener); }

}
