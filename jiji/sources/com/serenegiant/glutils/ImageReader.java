package com.serenegiant.glutils;

import android.os.Handler;

/* loaded from: classes2.dex */
public interface ImageReader<T> {

    /* loaded from: classes2.dex */
    public interface OnImageAvailableListener<T> {
        void onImageAvailable(ImageReader<T> imageReader);
    }

    T acquireLatestImage() throws IllegalStateException;

    T acquireNextImage() throws IllegalStateException;

    void recycle(T t);

    void setOnImageAvailableListener(OnImageAvailableListener<T> onImageAvailableListener, Handler handler) throws IllegalArgumentException;
}
