package com.deificindia.indifun.deificpk.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.deificindia.firebaseAdapter.GiftSenderAdapter;
import com.deificindia.firebaseAdapter.RoomListAdapterFirebase;
import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.firebaseModel.GiftSenderModel;
import com.deificindia.indifun.Adapter.LiveUserListAdapter;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;
import com.deificindia.indifun.fires.FireConst;
import com.deificindia.indifun.interfaces.Event;
import com.deificindia.indifun.interfaces.Firelistener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.deificindia.indifun.Utility.Logger.loge;

public class LiveRoomUserLayout extends RelativeLayout implements LifecycleOwner {
    private static final int MAX_ICON_COUNT = 4;

    private final LifecycleRegistry registry = new LifecycleRegistry(this);
    String room_id;

    public interface UserLayoutListener {
        void onTopGifterUserClick(GiftSenderModel info);
        void onUserLayoutShowUserList(View view);
    }

    DatabaseReference ref_gift_senders;
    ChildEventListener childEventListener;
    private LiveUserListAdapter adapt;
    private int mHeight;
    private int mIconSize;
    private int mIconMargin;
    private View live_participant_total_layout;
    private AppCompatTextView mCountText;
    private RecyclerView user_recycler;
    private RecyclerView userlistsend;
    private  GiftSenderAdapter adapter1;
//    List<RoomUserInfo> rankUsers;
    private RoomListAdapterFirebase adaptert;
   //private List<GiftSenderModel> post1;
    private UserLayoutListener mListener;
    private List<GiftSenderModel> modwllist;

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return registry;
    }

    public LiveRoomUserLayout(Context context) {
        super(context);
        //init("");
    }

    public LiveRoomUserLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //init("");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connectListener() {
        loge("LiveRoomUser", "resumed");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disconnectListener() {
        loge("LiveRoomUser", "paused");

        if(ref_gift_senders!=null && childEventListener!=null){
            ref_gift_senders.removeEventListener(childEventListener);
        }
    }

    public void setUserLayoutListener(UserLayoutListener listener) {
        mListener = listener;
     //   adapter1.setOnLiveUserClick(listener);
        adaptert.setOnLiveUserClick(listener);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, mHeight);
        int heightSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
//
    public void reset(long userCount) {
        Log.d("LiveRoomUserList", ""+userCount);
//        /setUserIcons(rankUsers);
        try {
            setUserCount((int) (userCount));
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }


    public void setUserCount(int total) {
        String value = countToString(total);
        mCountText.setText(value);
    }

    private String countToString(int number) {
        if (number <  1e3f) {
            return String.valueOf(number);
        } else if (number < 1e6f) {
            int quotient = (int) (number / 1e3f);
            return String.format(Locale.getDefault(), "%dK", quotient);
        } else if (number < 1e9f) {
            int quotient = (int) (number / 1e6f);
            return String.format(Locale.getDefault(), "%dM", quotient);
        } else {
            int quotient = (int) (number / 1e9f);
            return String.format(Locale.getDefault(), "%dB", quotient);
        }
    }

    private void setUserIcons(List<RoomUserInfo> rankUsers) {
//        if (mIconLayout.getChildCount() > 0) {
//            mIconLayout.removeAllViews();
//        }
//
//        if (rankUsers == null) return;
//
//        int id = 0;
//        for (int i = 0; i < rankUsers.size(); i++) {
//            if (i >= MAX_ICON_COUNT) break;
//            RoomUserInfo info = rankUsers.get(i);
//            setIconResource(info.wuid, id++);
//        }
    }

    private void setIconResource(String userId, int referenceId) {
       /* int resId = UserUtil.getUserProfileIcon(userId);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),
                BitmapFactory.decodeResource(getResources(), resId));
        drawable.setCircular(true);

        LayoutParams params = new LayoutParams(mIconSize, mIconSize);
        params.rightMargin = mIconMargin;
        if (referenceId > 0) {
            params.addRule(RelativeLayout.LEFT_OF, referenceId);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        }

        AppCompatImageView imageView = new AppCompatImageView(getContext());
        imageView.setId(referenceId + 1);
        imageView.setImageDrawable(drawable);
        mIconLayout.addView(imageView, params);*/
    }

    void setImageFromLink(String link, ImageView imageView){
        Picasso.get()
                .load(link)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap source = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), source);

                        drawable.setCircular(true);

                        drawable.setCornerRadius(Math.max(source.getWidth(), source.getHeight()) / 30.0f);

                        imageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    public void showNotification(boolean show) {
       /* if (mNotification != null) {
            int visibility = show ? VISIBLE : GONE;
            mNotification.setVisibility(visibility);
        }*/
    }

    public boolean notificationShown() {
        /*return mNotification != null &&
                mNotification.getVisibility() == VISIBLE;*/
        return false;

    }

    public void init(String room_id) {
        this.room_id = room_id;
        init(false);
    }

    public void init(boolean lightMode) {
        mHeight = getResources().getDimensionPixelSize(R.dimen.live_name_pad_height);
        mIconSize = getResources().getDimensionPixelSize(R.dimen.live_participant_layout_height);
        mIconMargin = getResources().getDimensionPixelSize(R.dimen.live_participant_margin_end);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(lightMode ?
                R.layout.live_participant_layout_light :
                R.layout.live_participant_layout, this, true);

        View parent = findViewById(R.id.parent);
        RelativeLayout.LayoutParams rl_lp = (LayoutParams) parent.getLayoutParams();
        rl_lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        parent.setLayoutParams(rl_lp);

        user_recycler = layout.findViewById(R.id.user_recycler);
      //  userlistsend=layout.findViewById(R.id.icon_layout);
        live_participant_total_layout = layout.findViewById(R.id.live_participant_total_layout);
        mCountText = layout.findViewById(R.id.live_participant_count_text);

        user_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        user_recycler.setHasFixedSize(true);
        user_recycler.addItemDecoration(new EqualSpacingItemDecoration(1, EqualSpacingItemDecoration.HORIZONTAL));
        //userlisdting();

        adaptert = new RoomListAdapterFirebase(getContext());
        user_recycler.setAdapter(adaptert);

        live_participant_total_layout.setOnClickListener(v->{
            if (mListener != null) mListener.onUserLayoutShowUserList(v);
        });
    }

    public void updateTopSender(List<GiftSenderModel> listinfo){
        adaptert.update(listinfo);
    }

}
