package com.serenegiant.media;

import android.os.Parcel;
import android.os.Parcelable;
import com.epson.iprojection.common.CommonDefine;
import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public class VideoConfig implements Parcelable, Cloneable {
    public static float BPP_MAX = 0.3f;
    public static final float BPP_MIN = 0.01f;
    private static final boolean DEBUG = false;
    public static final int FPS_MAX = 121;
    public static final int FPS_MIN = 2;
    private static final int IFRAME_MAX = 30;
    private static final int IFRAME_MIN = 1;
    private static final String TAG = "VideoConfig";
    private float BPP;
    private int mCaptureFps;
    private float mIframeIntervalsS;
    private long mMaxDuration;
    private float mNumFramesBetweenIframeOn30fps;
    private boolean mUseMediaMuxer;
    private boolean mUseSurfaceCapture;
    public static final VideoConfig DEFAULT_CONFIG = new VideoConfig(0.25f, 10.0f, 300.0f, 30, 30000, true, true);
    public static final Parcelable.Creator<VideoConfig> CREATOR = new Parcelable.Creator<VideoConfig>() { // from class: com.serenegiant.media.VideoConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoConfig createFromParcel(Parcel parcel) {
            return new VideoConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoConfig[] newArray(int i) {
            return new VideoConfig[i];
        }
    };

    private static float calcBPP(int i, int i2, int i3, int i4) {
        return i4 / ((i3 * i) * i2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VideoConfig() {
        this(DEFAULT_CONFIG);
    }

    public VideoConfig(VideoConfig videoConfig) {
        this.BPP = videoConfig.BPP;
        this.mIframeIntervalsS = videoConfig.mIframeIntervalsS;
        this.mNumFramesBetweenIframeOn30fps = videoConfig.mNumFramesBetweenIframeOn30fps;
        this.mCaptureFps = videoConfig.mCaptureFps;
        this.mMaxDuration = videoConfig.mMaxDuration;
        this.mUseMediaMuxer = videoConfig.mUseMediaMuxer;
        this.mUseSurfaceCapture = videoConfig.mUseSurfaceCapture;
    }

    private VideoConfig(float f, float f2, float f3, int i, long j, boolean z, boolean z2) {
        this.BPP = f;
        this.mIframeIntervalsS = f2;
        this.mNumFramesBetweenIframeOn30fps = f3;
        this.mCaptureFps = i;
        this.mMaxDuration = j;
        this.mUseMediaMuxer = z;
        this.mUseSurfaceCapture = z2;
    }

    /* renamed from: clone */
    public VideoConfig m274clone() throws CloneNotSupportedException {
        return (VideoConfig) super.clone();
    }

    public VideoConfig set(VideoConfig videoConfig) {
        this.BPP = videoConfig.BPP;
        this.mIframeIntervalsS = videoConfig.mIframeIntervalsS;
        this.mNumFramesBetweenIframeOn30fps = videoConfig.mNumFramesBetweenIframeOn30fps;
        this.mCaptureFps = videoConfig.mCaptureFps;
        this.mMaxDuration = videoConfig.mMaxDuration;
        this.mUseMediaMuxer = videoConfig.mUseMediaMuxer;
        this.mUseSurfaceCapture = videoConfig.mUseSurfaceCapture;
        return this;
    }

    public VideoConfig reset() {
        return set(DEFAULT_CONFIG);
    }

    public long maxDuration() {
        return this.mMaxDuration;
    }

    public VideoConfig setMaxDuration(long j) {
        this.mMaxDuration = j;
        return this;
    }

    public boolean useMediaMuxer() {
        return this.mUseMediaMuxer;
    }

    public VideoConfig setUseMediaMuxer(boolean z) {
        this.mUseMediaMuxer = z & true;
        return this;
    }

    public boolean useSurfaceCapture() {
        return this.mUseSurfaceCapture;
    }

    public VideoConfig setUseSurfaceCapture(boolean z) {
        this.mUseSurfaceCapture = z & true;
        return this;
    }

    public VideoConfig setCaptureFps(int i) {
        this.mCaptureFps = i <= 121 ? Math.max(i, 2) : 121;
        return this;
    }

    public int captureFps() {
        int i = this.mCaptureFps;
        if (i > 121) {
            return 121;
        }
        return Math.max(i, 2);
    }

    public Fraction getCaptureFps() {
        return new Fraction(captureFps(), 1);
    }

    public VideoConfig setIFrameIntervals(float f) {
        this.mIframeIntervalsS = f;
        this.mNumFramesBetweenIframeOn30fps = f * 30.0f;
        return this;
    }

    public int iFrameIntervals() {
        return (int) this.mIframeIntervalsS;
    }

    public Fraction getIFrameIntervals() {
        return new Fraction(this.mIframeIntervalsS);
    }

    public final int calcIFrameIntervals() {
        int captureFps;
        float f;
        if (captureFps() < 2) {
            f = 1.0f;
        } else {
            try {
                f = (float) Math.ceil(this.mNumFramesBetweenIframeOn30fps / captureFps);
            } catch (Exception unused) {
                f = this.mIframeIntervalsS;
            }
        }
        int i = (int) f;
        return (int) (i >= 1 ? i > 30 ? 30.0f : f : 1.0f);
    }

    private static int calcBitrate(int i, int i2, int i3, float f) {
        int floor = ((int) (Math.floor(((((f * i3) * i) * i2) / 1000.0f) / 100.0f) * 100.0d)) * 1000;
        int i4 = CommonDefine.EXPIRE_NFC_CONNECT;
        if (floor >= 200000) {
            i4 = 20000000;
            if (floor <= 20000000) {
                return floor;
            }
        }
        return i4;
    }

    public int getBitrate(int i, int i2) {
        return calcBitrate(i, i2, captureFps(), this.BPP);
    }

    public int getBitrate(int i, int i2, int i3) {
        return calcBitrate(i, i2, i3, this.BPP);
    }

    public int getBitrate(int i, int i2, int i3, float f) {
        return calcBitrate(i, i2, i3, f);
    }

    public float calcBPP(int i, int i2, int i3) {
        return calcBPP(i, i2, captureFps(), i3);
    }

    public VideoConfig setBPP(int i, int i2, int i3) throws IllegalArgumentException {
        setBPP(calcBPP(i, i2, i3));
        return this;
    }

    public VideoConfig setBPP(float f) throws IllegalArgumentException {
        if (f < 0.01f || f > BPP_MAX) {
            throw new IllegalArgumentException("bpp should be within [BPP_MIN, BPP_MAX]");
        }
        this.BPP = f;
        return this;
    }

    public float bpp() {
        return this.BPP;
    }

    public int getSizeRate(int i, int i2) {
        return (getBitrate(i, i2) * 60) / 8;
    }

    public String toString() {
        return "VideoConfig{BPP=" + this.BPP + ", mIframeIntervalsS=" + this.mIframeIntervalsS + ", mNumFramesBetweenIframeOn30fps=" + this.mNumFramesBetweenIframeOn30fps + ", mCaptureFps=" + this.mCaptureFps + ", mMaxDuration=" + this.mMaxDuration + ", mUseMediaMuxer=" + this.mUseMediaMuxer + ", mUseSurfaceCapture=" + this.mUseSurfaceCapture + '}';
    }

    protected VideoConfig(Parcel parcel) {
        this.BPP = parcel.readFloat();
        this.mIframeIntervalsS = parcel.readFloat();
        this.mNumFramesBetweenIframeOn30fps = parcel.readFloat();
        this.mCaptureFps = parcel.readInt();
        this.mMaxDuration = parcel.readLong();
        this.mUseMediaMuxer = parcel.readByte() != 0;
        this.mUseSurfaceCapture = parcel.readByte() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.BPP);
        parcel.writeFloat(this.mIframeIntervalsS);
        parcel.writeFloat(this.mNumFramesBetweenIframeOn30fps);
        parcel.writeInt(this.mCaptureFps);
        parcel.writeLong(this.mMaxDuration);
        parcel.writeByte(this.mUseMediaMuxer ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mUseSurfaceCapture ? (byte) 1 : (byte) 0);
    }
}
