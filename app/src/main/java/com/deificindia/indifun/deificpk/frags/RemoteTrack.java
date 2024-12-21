package com.deificindia.indifun.deificpk.frags;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.deificpk.utils.CameraCapturerCompat;
import com.twilio.video.AudioSink;
import com.twilio.video.AudioTrack;
import com.twilio.video.CameraCapturer;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.RemoteAudioTrack;
import com.twilio.video.RemoteVideoTrack;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;

import java.nio.ByteBuffer;

public class RemoteTrack implements Cloneable {

    public static final int CALLWATING = 0;
    public static final int CALLING = 1; //JOINCALL
    public static final int CALLACCEPTED = 2;
    public static final int CALLREJECTED = 3;
    public static final int CALLGOTLOCALVIDAUD = 5;
    //public static final int CALL_COUNTS = 2;
    public static final int localstate_0_OFF = 0;
    public static final int localstate_0_ON = 1;
public boolean indexBzy;

    public static final String vcall = "video";
    public static final String acall = "audio";

    public String calltype;
    public int index;
    public int localstate;
    public String tofuid;
    public EntityCall entityCall;

    public CameraCapturerCompat cameraCapturerCompat;
    public LocalVideoTrack localVideoTrack;
    public LocalAudioTrack localAudioTrack;
    public LocalParticipant localParticipant;

    public String remoteParticipantIdentity;
    public VideoTrack remoteVideoTrack;
    public AudioTrack remoteAudioTrack;

    public boolean isaudiomute;
    public boolean isvideomute;
    public boolean iscameramute;
    public boolean isbackcamera;


    public boolean audio_published, video_published;

    public RemoteTrack() { }

    public RemoteTrack(String calltype, int index) {
        this.calltype = calltype;
        this.index = index;
    }

    public RemoteTrack(VideoTrack remoteVideoTrack) {
        this.remoteVideoTrack = remoteVideoTrack;
    }

    public RemoteTrack(AudioTrack remoteAudioTrack) {
        this.remoteAudioTrack = remoteAudioTrack;
    }

    public int getCallState() {
        return entityCall==null?0:(int)entityCall.state;
    }

    public void resetTrack(){
        localstate = 0;
        isaudiomute = false;
        isvideomute = false;
        iscameramute = false;
        isbackcamera = false;

        audio_published = false;
        video_published = false;

        remoteVideoTrack = null;
        remoteAudioTrack = null;

        entityCall = null;

        Log.e("RemoteTrack", "resetTrack: " );
    }

    public int getlocalstate(){
        return localstate;
    }

    public AudioSink audioSink = new AudioSink() {
        @Override
        public void renderSample(@NonNull ByteBuffer audioSample, int encoding, int sampleRate, int channels) {
           /* try {
                //wavFileHelper.writeBytesToFile(audioSample, encoding, sampleRate, channels);
            } catch (IOException e) {
                Log.e(TAG, String.format("A fatal error has occurred. Stacktrace %s", e.getLocalizedMessage()));
            }*/
        }
    };

    @NonNull
    @Override
    public RemoteTrack clone() {
        //return super.clone();
        RemoteTrack clone;
        try {
            clone = (RemoteTrack) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); //should not happen
        }

        return clone;
    }

    public void switchCamera(){
        if (cameraCapturerCompat != null) {
            CameraCapturer.CameraSource cameraSource = cameraCapturerCompat.getCameraSource();
            cameraCapturerCompat.switchCamera();

            this.isbackcamera = cameraSource == CameraCapturer.CameraSource.BACK_CAMERA;
            //local_or_broadcast_video_view.setMirror(cameraSource == CameraCapturer.CameraSource.BACK_CAMERA);

        }
    }

    public void muteLocalAudioTrack(boolean b){
        if(localAudioTrack==null || localParticipant==null) return;
        if(b) {
            // Disable audio track. Audio still flows to participants but the audio is silent.
            localAudioTrack.enable(false);
            // Release and unpublish audio track. Audio no longer flows to participants.
            //localAudioTrack.release();
            //localParticipant.unpublish(localAudioTrack);
            localParticipant.unpublishTrack(localAudioTrack);

        }else {
            // Disable audio track. Audio still flows to participants but the audio is silent.
            localAudioTrack.enable(true);
            // Release and unpublish audio track. Audio no longer flows to participants.
            localParticipant.publishTrack(localAudioTrack);
        }

        this.isaudiomute = b;
    }


    public void muteLocalVideoTrack(boolean b){
        if (localVideoTrack == null || localParticipant == null) return;
        if(b) {
            try {
                // Disable audio track. Audio still flows to participants but the audio is silent.
                localVideoTrack.enable(false);
                // Release and unpublish audio track. Audio no longer flows to participants.
                //localVideoTrack.release();
                //localParticipant.unpublish(localAudioTrack);
                localParticipant.unpublishTrack(localVideoTrack);
            }catch (IllegalArgumentException e){}
        }else{
            localVideoTrack.enable(true);
            localParticipant.publishTrack(localVideoTrack);
        }

        this.iscameramute = b;
    }


    public void removeRemoteVideo(VideoView videoView){
        if(remoteVideoTrack!=null) remoteVideoTrack.removeRenderer(videoView);
    }

    public void removeRemoteAudio(){
        if(localAudioTrack!=null) localAudioTrack.removeSink(audioSink);
    }




}
