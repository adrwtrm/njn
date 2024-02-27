package com.serenegiant.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes2.dex */
public class HandlerThreadHandler extends Handler {
    private static final String TAG = "HandlerThreadHandler";
    private final boolean mAsynchronous;
    private final long mId;

    public static final HandlerThreadHandler createHandler() {
        return createHandler(TAG, false);
    }

    public static final HandlerThreadHandler createHandler(boolean z) {
        return createHandler(TAG, z);
    }

    public static final HandlerThreadHandler createHandler(String str) {
        return createHandler(str, false);
    }

    public static final HandlerThreadHandler createHandler(String str, boolean z) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return new HandlerThreadHandler(handlerThread.getLooper(), z);
    }

    public static final HandlerThreadHandler createHandler(Handler.Callback callback) {
        return createHandler(TAG, callback);
    }

    public static final HandlerThreadHandler createHandler(Handler.Callback callback, boolean z) {
        return createHandler(TAG, callback, z);
    }

    public static final HandlerThreadHandler createHandler(String str, Handler.Callback callback) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return new HandlerThreadHandler(handlerThread.getLooper(), callback, false);
    }

    public static final HandlerThreadHandler createHandler(String str, Handler.Callback callback, boolean z) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return new HandlerThreadHandler(handlerThread.getLooper(), callback, z);
    }

    private HandlerThreadHandler(Looper looper, boolean z) {
        super(looper);
        this.mId = looper.getThread().getId();
        this.mAsynchronous = z;
    }

    private HandlerThreadHandler(Looper looper, Handler.Callback callback, boolean z) {
        super(looper, callback);
        this.mId = looper.getThread().getId();
        this.mAsynchronous = z;
    }

    @Override // android.os.Handler
    public boolean sendMessageAtTime(Message message, long j) {
        if (this.mAsynchronous) {
            message.setAsynchronous(true);
        }
        return super.sendMessageAtTime(message, j);
    }

    public long getId() {
        return this.mId;
    }

    public void quitSafely() throws IllegalStateException {
        getLooper().quitSafely();
    }

    public void quit() throws IllegalStateException {
        getLooper().quit();
    }

    public boolean isCurrentThread() throws IllegalStateException {
        return this.mId == Thread.currentThread().getId();
    }
}
