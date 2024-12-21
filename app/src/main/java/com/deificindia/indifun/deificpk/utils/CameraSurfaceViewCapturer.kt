package com.deificindia.indifun.deificpk.utils

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.PixelCopy
import android.view.SurfaceView
import com.twilio.video.*
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.math.roundToLong

class CameraSurfaceViewCapturer : VideoCapturer {
    private var surfaceView: SurfaceView? = null

    private lateinit var viewBitmap: Bitmap
    private lateinit var videoCapturerListener: VideoCapturer.Listener

    private val handler = Handler(Looper.getMainLooper())
    private val handlerPixelCopy = Handler(Looper.getMainLooper())
    private var started: Boolean = false

    // Twilio selects closest supported VideoFormat to 640x480 at 30 frames per second.
    // https://media.twiliocdn.com/sdk/android/video/releases/1.0.0-beta17/docs/com/twilio/video/LocalVideoTrack.html

    private val framesPerSecond: Int = 30
    private val streamWidth: Int = VideoDimensions.VGA_VIDEO_WIDTH
    private val streamHeight: Int = VideoDimensions.VGA_VIDEO_HEIGHT

    private val viewCapturerFrameRateMs: Long =
            (TimeUnit.SECONDS.toMillis(1).toFloat() / framesPerSecond.toFloat()).roundToLong()

    private val reentrantLock = ReentrantLock()

    fun changeSurfaceView(surfaceView: SurfaceView?) {
        reentrantLock.lock()
        this.surfaceView = surfaceView
        reentrantLock.unlock()
    }

    private val viewCapturer: Runnable = object : Runnable {
        override fun run() {
            reentrantLock.lock()

            val surfaceView = surfaceView

            if (started.not()) {
                reentrantLock.unlock()
                return
            }

            if (surfaceView == null ||
                    surfaceView.width == 0 ||
                    surfaceView.height == 0 ||
                    surfaceView.holder.surface.isValid.not()
            ) {
                handler.postDelayed(this, viewCapturerFrameRateMs)
                reentrantLock.unlock()
                return
            }

            // calculate frame width with fixed stream height while maintaining aspect ratio
            val frameWidthFixedHeight: Int = (surfaceView.width * streamHeight) / surfaceView.height

            // calculate frame height with fixed stream width while maintaining aspect ratio
            val frameHeightFixedWidth: Int = (surfaceView.height * streamWidth) / surfaceView.width

            // choose ratio that has more pixels
            val (frameWidth, frameHeight) =
                    if (frameWidthFixedHeight * streamHeight >= frameHeightFixedWidth * streamWidth) {
                        Pair(frameWidthFixedHeight, streamHeight)
                    } else {
                        Pair(streamWidth, frameHeightFixedWidth)
                    }

            viewBitmap = Bitmap.createBitmap(frameWidth, frameHeight, Bitmap.Config.ARGB_8888)

            // mutex.unlock() happens in callback
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                PixelCopy.request(
                        surfaceView,
                        viewBitmap,
                        {
                            val buffer = ByteBuffer.allocate(viewBitmap.byteCount)
                            viewBitmap.copyPixelsToBuffer(buffer)

                            // Create video frame
                            val dimensions = VideoDimensions(frameWidth, frameHeight)

                            val videoFrame =
                                    VideoFrame(
                                            buffer.array(),
                                            dimensions,
                                            VideoFrame.RotationAngle.ROTATION_0,
                                            TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime())
                                    )

                            // Notify the listener
                            videoCapturerListener.onFrameCaptured(videoFrame)
                            handler.postDelayed(this, viewCapturerFrameRateMs)
                            reentrantLock.unlock()
                        },
                        handlerPixelCopy
                )
            }
        }
    }

    override fun getSupportedFormats(): List<VideoFormat> =
            listOf(
                    VideoFormat(
                            VideoDimensions(streamWidth, streamHeight),
                            framesPerSecond,
                            VideoPixelFormat.RGBA_8888
                    )
            )

    override fun isScreencast(): Boolean {
        return true
    }

    override fun startCapture(
            captureFormat: VideoFormat,
            capturerListener: VideoCapturer.Listener
    ) {
        reentrantLock.lock()
        // Store the capturer listener
        videoCapturerListener = capturerListener
        started = true

        // Notify capturer API that the capturer has started
        val capturerStarted = handler.postDelayed(viewCapturer, viewCapturerFrameRateMs)
        videoCapturerListener.onCapturerStarted(capturerStarted)
        reentrantLock.unlock()
    }

    /**
     * Stop capturing frames. Note that the SDK cannot receive frames once this has been invoked.
     */
    override fun stopCapture() {
        reentrantLock.lock()
        started = false
        handler.removeCallbacks(viewCapturer)
        reentrantLock.unlock()
    }
}