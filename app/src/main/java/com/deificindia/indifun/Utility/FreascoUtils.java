package com.deificindia.indifun.Utility;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.concurrent.Executor;

public class FreascoUtils {

    public FreascoUtils withUrl(String link){
        Uri imageUri = Uri.parse(link);
        return this;
    }

    public FreascoUtils local(int imgResId){
        Uri imageUri = com.facebook.common.util.UriUtil.getUriForResourceId(imgResId);
        return this;
    }

    public void fres(Uri imageUri){


        ImageRequestBuilder builder = ImageRequestBuilder
                .newBuilderWithSource(imageUri)
                .setRequestPriority(Priority.HIGH)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH);

        final DataSource<CloseableReference<CloseableImage>> dataSource =
                Fresco.getImagePipeline().fetchDecodedImage(builder.build(), imageUri);

        try {
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                public void onNewResultImpl(@Nullable Bitmap bitmap) {
                    if (null != bitmap) {
                        //TODO use bitmap
                    }
                }

                @Override
                public void onFailureImpl(DataSource dataSource) {
                    if (dataSource != null) {
                        dataSource.close();
                    }
                }
            }, new MainThreadExecutor1());
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
        }

    }

    private class MainThreadExecutor1 implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {

                handler.post(runnable);
        }
    }
}
