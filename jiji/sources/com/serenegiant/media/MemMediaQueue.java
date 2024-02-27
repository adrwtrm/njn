package com.serenegiant.media;

import com.serenegiant.media.IRecycleBuffer;
import com.serenegiant.utils.Pool;
import java.nio.ByteOrder;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class MemMediaQueue implements IMediaQueue<RecycleMediaData> {
    private final IRecycleBuffer.Factory<RecycleMediaData> mFactory;
    private final Pool<RecycleMediaData> mPool;
    private final LinkedBlockingQueue<RecycleMediaData> mQueue;

    /* loaded from: classes2.dex */
    public static class DefaultFactory implements IRecycleBuffer.Factory<RecycleMediaData> {
        @Override // com.serenegiant.media.IRecycleBuffer.Factory
        public RecycleMediaData create(IRecycleParent<RecycleMediaData> iRecycleParent, Object... objArr) {
            int i = 0;
            ByteOrder byteOrder = null;
            if (objArr != null && objArr.length > 0) {
                int length = objArr.length;
                ByteOrder byteOrder2 = null;
                int i2 = 0;
                while (i < length) {
                    Object obj = objArr[i];
                    if (obj instanceof Integer) {
                        i2 = ((Integer) obj).intValue();
                    } else if (obj instanceof ByteOrder) {
                        byteOrder2 = (ByteOrder) obj;
                    }
                    i++;
                }
                i = i2;
                byteOrder = byteOrder2;
            }
            if (i <= 0 || byteOrder == null) {
                if (i > 0) {
                    return new RecycleMediaData(iRecycleParent, i);
                }
                if (byteOrder != null) {
                    return new RecycleMediaData(iRecycleParent, byteOrder);
                }
                return new RecycleMediaData(iRecycleParent);
            }
            return new RecycleMediaData(iRecycleParent, i, byteOrder);
        }
    }

    public MemMediaQueue(int i, int i2) {
        this(i, i2, i2, null);
    }

    public MemMediaQueue(int i, int i2, int i3) {
        this(i, i2, i3, null);
    }

    public MemMediaQueue(int i, int i2, int i3, IRecycleBuffer.Factory<RecycleMediaData> factory) {
        this.mQueue = new LinkedBlockingQueue<>(i3);
        this.mFactory = factory == null ? new DefaultFactory() : factory;
        this.mPool = new Pool<RecycleMediaData>(i, i2, new Object[0]) { // from class: com.serenegiant.media.MemMediaQueue.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.serenegiant.utils.Pool
            public RecycleMediaData createObject(Object... objArr) {
                return (RecycleMediaData) MemMediaQueue.this.mFactory.create(MemMediaQueue.this, objArr);
            }
        };
    }

    @Override // com.serenegiant.media.IMediaQueue
    public void init(Object... objArr) {
        clear();
        this.mPool.init(objArr);
    }

    @Override // com.serenegiant.media.IMediaQueue
    public void clear() {
        this.mQueue.clear();
        this.mPool.clear();
    }

    @Override // com.serenegiant.media.IMediaQueue
    public RecycleMediaData obtain(Object... objArr) {
        return this.mPool.obtain(objArr);
    }

    @Override // com.serenegiant.media.IMediaQueue
    public boolean queueFrame(RecycleMediaData recycleMediaData) {
        return this.mQueue.offer(recycleMediaData);
    }

    @Override // com.serenegiant.media.IMediaQueue
    public RecycleMediaData peek() {
        return this.mQueue.peek();
    }

    @Override // com.serenegiant.media.IMediaQueue
    public RecycleMediaData poll() {
        return this.mQueue.poll();
    }

    @Override // com.serenegiant.media.IMediaQueue
    public RecycleMediaData poll(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.mQueue.poll(j, timeUnit);
    }

    @Override // com.serenegiant.media.IMediaQueue
    public int count() {
        return this.mQueue.size();
    }

    @Override // com.serenegiant.media.IRecycleParent
    public boolean recycle(RecycleMediaData recycleMediaData) {
        return this.mPool.recycle((Pool<RecycleMediaData>) recycleMediaData);
    }
}
