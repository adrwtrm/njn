package com.serenegiant.media;

import java.lang.ref.WeakReference;
import java.nio.ByteOrder;

/* loaded from: classes2.dex */
public class RecycleMediaData extends MediaData implements IRecycleBuffer {
    private final WeakReference<IRecycleParent<RecycleMediaData>> mWeakParent;

    public RecycleMediaData(IRecycleParent<RecycleMediaData> iRecycleParent) {
        this.mWeakParent = new WeakReference<>(iRecycleParent);
    }

    public RecycleMediaData(IRecycleParent<RecycleMediaData> iRecycleParent, int i) {
        super(i);
        this.mWeakParent = new WeakReference<>(iRecycleParent);
    }

    public RecycleMediaData(IRecycleParent<RecycleMediaData> iRecycleParent, ByteOrder byteOrder) {
        super(byteOrder);
        this.mWeakParent = new WeakReference<>(iRecycleParent);
    }

    public RecycleMediaData(IRecycleParent<RecycleMediaData> iRecycleParent, int i, ByteOrder byteOrder) {
        super(i, byteOrder);
        this.mWeakParent = new WeakReference<>(iRecycleParent);
    }

    public RecycleMediaData(RecycleMediaData recycleMediaData) {
        super(recycleMediaData);
        this.mWeakParent = new WeakReference<>(recycleMediaData.mWeakParent.get());
    }

    @Override // com.serenegiant.media.IRecycleBuffer
    public void recycle() {
        IRecycleParent<RecycleMediaData> iRecycleParent = this.mWeakParent.get();
        if (iRecycleParent != null) {
            iRecycleParent.recycle(this);
        }
    }
}
