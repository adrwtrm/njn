package com.serenegiant.media;

import com.serenegiant.media.IRecycleBuffer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public interface IMediaQueue<T extends IRecycleBuffer> extends IRecycleParent<T> {
    void clear();

    int count();

    void init(Object... objArr);

    T obtain(Object... objArr);

    T peek();

    T poll();

    T poll(long j, TimeUnit timeUnit) throws InterruptedException;

    boolean queueFrame(T t);
}
