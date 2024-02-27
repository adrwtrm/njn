package com.serenegiant.media;

import android.media.MediaCodec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes2.dex */
public class MediaData {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private ByteBuffer mBuffer;
    private ByteOrder mByteOrder;
    private int mFlags;
    private long mPresentationTimeUs;
    private int mSize;
    private int mTrackIx;

    public MediaData() {
        this.mByteOrder = ByteOrder.nativeOrder();
        this.mSize = 1024;
    }

    public MediaData(ByteOrder byteOrder) {
        ByteOrder.nativeOrder();
        this.mSize = 1024;
        this.mByteOrder = byteOrder;
    }

    public MediaData(int i) {
        this.mByteOrder = ByteOrder.nativeOrder();
        this.mSize = 1024;
        resize(i);
    }

    public MediaData(int i, ByteOrder byteOrder) {
        ByteOrder.nativeOrder();
        this.mSize = 1024;
        this.mByteOrder = byteOrder;
        resize(i);
    }

    public MediaData(MediaData mediaData) {
        this.mByteOrder = ByteOrder.nativeOrder();
        this.mSize = 1024;
        this.mByteOrder = mediaData.mByteOrder;
        set(mediaData.mTrackIx, mediaData.mBuffer, 0, mediaData.mSize, mediaData.mPresentationTimeUs, mediaData.mFlags);
    }

    public void set(MediaData mediaData) {
        set(0, mediaData.mBuffer, 0, mediaData.mSize, mediaData.mPresentationTimeUs, mediaData.mFlags);
    }

    public void set(ByteBuffer byteBuffer, int i, long j) {
        set(0, byteBuffer, 0, i, j, 0);
    }

    public void set(ByteBuffer byteBuffer, int i, int i2, long j, int i3) {
        set(0, byteBuffer, i, i2, j, i3);
    }

    public void set(int i, ByteBuffer byteBuffer, int i2, int i3, long j, int i4) {
        this.mTrackIx = i;
        this.mPresentationTimeUs = j;
        this.mSize = i3;
        this.mFlags = i4;
        resize(i3);
        if (byteBuffer == null || i3 <= i2) {
            return;
        }
        byteBuffer.clear();
        byteBuffer.position(i3 + i2);
        byteBuffer.flip();
        byteBuffer.position(i2);
        this.mBuffer.put(byteBuffer);
        this.mBuffer.flip();
    }

    public void set(byte[] bArr, long j) {
        set(0, bArr, 0, bArr != null ? bArr.length : 0, j, 0);
    }

    public void set(int i, byte[] bArr, int i2, int i3, long j, int i4) {
        int min = Math.min(bArr != null ? bArr.length : 0, i3);
        this.mTrackIx = i;
        this.mPresentationTimeUs = j;
        this.mSize = min;
        this.mFlags = i4;
        resize(min);
        if (bArr == null || min <= i2) {
            return;
        }
        this.mBuffer.put(bArr, i2, min);
        this.mBuffer.flip();
    }

    public void set(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        set(0, byteBuffer, bufferInfo);
    }

    public void set(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        this.mTrackIx = i;
        this.mPresentationTimeUs = bufferInfo.presentationTimeUs;
        this.mSize = byteBuffer != null ? bufferInfo.size : 0;
        this.mFlags = bufferInfo.flags;
        int i2 = bufferInfo.offset;
        resize(this.mSize);
        if (byteBuffer == null || this.mSize <= i2) {
            return;
        }
        byteBuffer.clear();
        byteBuffer.position(this.mSize + i2);
        byteBuffer.flip();
        byteBuffer.position(i2);
        this.mBuffer.put(byteBuffer);
        this.mBuffer.flip();
    }

    public MediaData resize(int i) {
        ByteBuffer byteBuffer = this.mBuffer;
        if (byteBuffer == null || byteBuffer.capacity() < i) {
            this.mBuffer = ByteBuffer.allocateDirect(i).order(this.mByteOrder);
        }
        this.mBuffer.clear();
        return this;
    }

    public void clear() {
        this.mFlags = 0;
        this.mSize = 0;
        ByteBuffer byteBuffer = this.mBuffer;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
    }

    public int size() {
        return this.mSize;
    }

    public MediaData size(int i) {
        this.mSize = i;
        return this;
    }

    public int trackIx() {
        return this.mTrackIx;
    }

    public int flags() {
        return this.mFlags;
    }

    public long presentationTimeUs() {
        return this.mPresentationTimeUs;
    }

    public MediaData presentationTimeUs(long j) {
        this.mPresentationTimeUs = j;
        return this;
    }

    public void get(byte[] bArr) throws ArrayIndexOutOfBoundsException {
        int i;
        ByteBuffer byteBuffer = this.mBuffer;
        if (byteBuffer == null || (i = this.mSize) <= 0 || bArr == null || bArr.length < i) {
            throw new ArrayIndexOutOfBoundsException("");
        }
        byteBuffer.clear();
        this.mBuffer.position(this.mSize);
        this.mBuffer.flip();
        this.mBuffer.get(bArr);
    }

    public void get(ByteBuffer byteBuffer) throws ArrayIndexOutOfBoundsException {
        if (this.mBuffer == null || this.mSize <= 0 || byteBuffer == null || byteBuffer.remaining() < this.mSize) {
            throw new ArrayIndexOutOfBoundsException("");
        }
        this.mBuffer.clear();
        this.mBuffer.position(this.mSize);
        this.mBuffer.flip();
        byteBuffer.put(this.mBuffer);
    }

    public void get(MediaCodec.BufferInfo bufferInfo) {
        bufferInfo.set(0, this.mSize, this.mPresentationTimeUs, this.mFlags);
    }

    public ByteBuffer get() {
        ByteBuffer byteBuffer = this.mBuffer;
        if (byteBuffer != null) {
            byteBuffer.clear();
            this.mBuffer.position(this.mSize);
            this.mBuffer.flip();
        } else {
            int i = this.mSize;
            if (i <= 0) {
                i = 1024;
            }
            resize(i);
        }
        return this.mBuffer;
    }

    public ByteBuffer get(int i) {
        resize(i);
        return get();
    }

    public int capacity() {
        ByteBuffer byteBuffer = this.mBuffer;
        if (byteBuffer != null) {
            return byteBuffer.capacity();
        }
        return 0;
    }

    public ByteOrder order() {
        return this.mByteOrder;
    }

    public MediaData order(ByteOrder byteOrder) {
        this.mByteOrder = byteOrder;
        ByteBuffer byteBuffer = this.mBuffer;
        if (byteBuffer != null) {
            byteBuffer.order(byteOrder);
        }
        return this;
    }
}
