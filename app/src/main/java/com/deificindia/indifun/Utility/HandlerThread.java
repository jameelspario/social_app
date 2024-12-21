package com.deificindia.indifun.Utility;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlerThread {

    private static final int cores = Runtime.getRuntime().availableProcessors();
    public static ExecutorService executor = Executors.newFixedThreadPool(cores + 1);
    public static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void stop() {
        executor.shutdown();
    }



}
