package com.serenegiant.glpipeline;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.math.Fraction;
import com.serenegiant.media.AbstractVideoEncoder;
import com.serenegiant.media.EncoderListener;
import com.serenegiant.media.IRecorder;
import com.serenegiant.media.MediaCodecUtils;
import com.serenegiant.media.MediaReaper;

/* loaded from: classes2.dex */
public class EncodePipeline extends AbstractVideoEncoder implements GLPipeline {
    private static final boolean DEBUG = false;
    private static final String TAG = "EncodePipeline";
    private int cnt;
    private GLDrawer2D mDrawer;
    private final GLManager mManager;
    private GLPipeline mParent;
    private GLPipeline mPipeline;
    private volatile boolean mReleased;
    private RendererTarget mRendererTarget;
    private final Object mSync;

    @Override // com.serenegiant.media.AbstractEncoder
    public int getCaptureFormat() {
        return 0;
    }

    public EncodePipeline(GLManager gLManager, IRecorder iRecorder, EncoderListener encoderListener) {
        super("video/avc", iRecorder, encoderListener);
        this.mSync = new Object();
        this.mManager = gLManager;
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder, com.serenegiant.glpipeline.GLPipeline
    public final void release() {
        if (!this.mReleased) {
            this.mReleased = true;
            internalRelease();
        }
        super.release();
    }

    protected void internalRelease() {
        GLPipeline gLPipeline;
        this.mReleased = true;
        releaseTarget();
        synchronized (this.mSync) {
            gLPipeline = this.mPipeline;
            this.mPipeline = null;
            this.mParent = null;
        }
        if (gLPipeline != null) {
            gLPipeline.release();
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void resize(int i, int i2) throws IllegalStateException {
        setVideoSize(i, i2);
        GLPipeline pipeline = getPipeline();
        if (pipeline != null) {
            pipeline.resize(i, i2);
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public boolean isValid() {
        return !this.mReleased && this.mManager.isValid();
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public boolean isActive() {
        boolean z;
        synchronized (this.mSync) {
            z = ((this.mReleased || this.mParent == null) && this.mPipeline == null) ? false : true;
        }
        return z;
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void setParent(GLPipeline gLPipeline) {
        synchronized (this.mSync) {
            this.mParent = gLPipeline;
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public GLPipeline getParent() {
        GLPipeline gLPipeline;
        synchronized (this.mSync) {
            gLPipeline = this.mParent;
        }
        return gLPipeline;
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void setPipeline(GLPipeline gLPipeline) {
        synchronized (this.mSync) {
            this.mPipeline = gLPipeline;
        }
        if (gLPipeline != null) {
            gLPipeline.setParent(this);
            gLPipeline.resize(this.mWidth, this.mHeight);
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public GLPipeline getPipeline() {
        GLPipeline gLPipeline;
        synchronized (this.mSync) {
            gLPipeline = this.mPipeline;
        }
        return gLPipeline;
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void remove() {
        GLPipeline gLPipeline;
        synchronized (this.mSync) {
            gLPipeline = this.mParent;
            if (gLPipeline instanceof DistributePipeline) {
                ((DistributePipeline) gLPipeline).removePipeline(this);
            } else if (gLPipeline != null) {
                gLPipeline.setPipeline(this.mPipeline);
            }
            this.mParent = null;
            this.mPipeline = null;
        }
        if (gLPipeline != null) {
            GLPipeline.findFirst(gLPipeline).refresh();
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void onFrameAvailable(boolean z, int i, float[] fArr) {
        GLPipeline gLPipeline;
        GLDrawer2D gLDrawer2D;
        RendererTarget rendererTarget;
        synchronized (this.mSync) {
            gLPipeline = this.mPipeline;
            GLDrawer2D gLDrawer2D2 = this.mDrawer;
            if (gLDrawer2D2 == null || z != gLDrawer2D2.isOES()) {
                GLDrawer2D gLDrawer2D3 = this.mDrawer;
                if (gLDrawer2D3 != null) {
                    gLDrawer2D3.release();
                }
                this.mDrawer = GLDrawer2D.create(this.mManager.isGLES3(), z);
            }
            gLDrawer2D = this.mDrawer;
            rendererTarget = this.mRendererTarget;
        }
        if (gLPipeline != null) {
            gLPipeline.onFrameAvailable(z, i, fArr);
        }
        if (!this.mReleased && !this.mRequestStop && rendererTarget != null && rendererTarget.canDraw()) {
            rendererTarget.draw(gLDrawer2D, 33984, i, fArr);
        }
        frameAvailableSoon();
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void refresh() {
        GLPipeline gLPipeline;
        synchronized (this.mSync) {
            gLPipeline = this.mPipeline;
        }
        if (gLPipeline != null) {
            gLPipeline.refresh();
        }
    }

    private void createTarget(final Surface surface, final Fraction fraction) {
        this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.EncodePipeline.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (EncodePipeline.this.mSync) {
                    if (EncodePipeline.this.mRendererTarget != null && EncodePipeline.this.mRendererTarget.getSurface() != surface) {
                        EncodePipeline.this.mRendererTarget.release();
                        EncodePipeline.this.mRendererTarget = null;
                    }
                    if (EncodePipeline.this.mRendererTarget == null && GLUtils.isSupportedSurface(surface)) {
                        EncodePipeline encodePipeline = EncodePipeline.this;
                        EGLBase egl = encodePipeline.mManager.getEgl();
                        Surface surface2 = surface;
                        Fraction fraction2 = fraction;
                        encodePipeline.mRendererTarget = RendererTarget.newInstance(egl, surface2, fraction2 != null ? fraction2.asFloat() : 0.0f);
                    }
                }
            }
        });
    }

    private void releaseTarget() {
        final GLDrawer2D gLDrawer2D;
        final RendererTarget rendererTarget;
        synchronized (this.mSync) {
            gLDrawer2D = this.mDrawer;
            this.mDrawer = null;
            rendererTarget = this.mRendererTarget;
            this.mRendererTarget = null;
        }
        if (!(gLDrawer2D == null && rendererTarget == null) && this.mManager.isValid()) {
            try {
                this.mManager.runOnGLThread(new Runnable() { // from class: com.serenegiant.glpipeline.EncodePipeline.2
                    @Override // java.lang.Runnable
                    public void run() {
                        GLDrawer2D gLDrawer2D2 = gLDrawer2D;
                        if (gLDrawer2D2 != null) {
                            gLDrawer2D2.release();
                        }
                        RendererTarget rendererTarget2 = rendererTarget;
                        if (rendererTarget2 != null) {
                            rendererTarget2.release();
                        }
                    }
                });
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void stop() {
        releaseTarget();
        super.stop();
    }

    @Override // com.serenegiant.media.AbstractEncoder
    protected boolean internalPrepare(MediaReaper.ReaperListener reaperListener) throws Exception {
        this.mTrackIndex = -1;
        this.mIsCapturing = true;
        if (MediaCodecUtils.selectVideoEncoder("video/avc") == null) {
            return true;
        }
        boolean z = this.mWidth >= 1000 || this.mHeight >= 1000;
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", this.mWidth, this.mHeight);
        createVideoFormat.setInteger("color-format", MediaCodecUtils.OMX_COLOR_FormatAndroidOpaque);
        createVideoFormat.setInteger("bitrate", this.mBitRate > 0 ? this.mBitRate : getConfig().getBitrate(this.mWidth, this.mHeight));
        createVideoFormat.setInteger("frame-rate", this.mFramerate > 0 ? this.mFramerate : getConfig().captureFps());
        createVideoFormat.setInteger("i-frame-interval", this.mIFrameIntervals > 0 ? this.mIFrameIntervals : getConfig().calcIFrameIntervals());
        this.mMediaCodec = MediaCodec.createEncoderByType("video/avc");
        this.mMediaCodec.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
        Surface createInputSurface = this.mMediaCodec.createInputSurface();
        this.mMediaCodec.start();
        this.mReaper = new MediaReaper.VideoReaper(this.mMediaCodec, reaperListener, this.mWidth, this.mHeight);
        createTarget(createInputSurface, getConfig().getCaptureFps());
        return z;
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void signalEndOfInputStream() {
        if (this.mMediaCodec != null) {
            this.mMediaCodec.signalEndOfInputStream();
        }
    }
}
