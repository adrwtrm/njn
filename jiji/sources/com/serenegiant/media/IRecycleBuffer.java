package com.serenegiant.media;

/* loaded from: classes2.dex */
public interface IRecycleBuffer {

    /* loaded from: classes2.dex */
    public interface Factory<T extends IRecycleBuffer> {
        T create(IRecycleParent<T> iRecycleParent, Object... objArr);
    }

    void recycle();
}
