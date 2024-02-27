package com.serenegiant.media;

import com.serenegiant.media.IRecycleBuffer;

/* loaded from: classes2.dex */
public interface IRecycleParent<T extends IRecycleBuffer> {
    boolean recycle(T t);
}
