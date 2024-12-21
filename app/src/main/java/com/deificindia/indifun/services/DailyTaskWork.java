package com.deificindia.indifun.services;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.concurrent.callback.CallbackToFutureAdapter;
import androidx.concurrent.callback.ResolvableFuture;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class DailyTaskWork extends Worker {

    public DailyTaskWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        return Result.success();
    }
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */

    /*@NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver<Result>() {
            @Nullable
            @Override
            public Object attachCompleter(@NonNull CallbackToFutureAdapter.Completer<Result> completer) throws Exception {
                return null;
            }
        });
    }*/



}
