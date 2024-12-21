package com.deificindia.indifun.interfaces;

import com.deificindia.indifun.db.EntityCall;
import com.twilio.video.VideoView;

public interface CallingControll {

    void callAdapterClickListener(int what, EntityCall item);

    boolean isBackCamera();
    boolean renderLocalCall(VideoView videoView, EntityCall item);

    void muteLocalAudioTrack(boolean b);
    void muteLocalVideoTrack(boolean b);
    void removeLocaLcall(VideoView videoView, EntityCall item);

    boolean renderRemoteCall(VideoView videoView, EntityCall item);

    void removeRemoteVideo(VideoView videoView, EntityCall item);
    void removeRemoteAudio(EntityCall item);


    void onRemovedAllCalls();
}
