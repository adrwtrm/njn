package com.serenegiant.media;

import android.graphics.SurfaceTexture;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.opengl.GLES20;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.egl.EglTask;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.media.MediaReaper;
import com.serenegiant.utils.HandlerThreadHandler;

/* loaded from: classes2.dex */
public class MediaScreenEncoder extends AbstractVideoEncoder {
    private static final boolean DEBUG = false;
    private static final String MIME = "video/avc";
    private static final String TAG = "MediaScreenEncoder";
    private final VirtualDisplay.Callback mCallback;
    private final int mDensity;
    private final DrawTask mDrawTask;
    private final Handler mHandler;
    private Surface mInputSurface;
    private MediaProjection mMediaProjection;
    private volatile boolean requestDraw;

    public MediaScreenEncoder(IRecorder iRecorder, EncoderListener encoderListener, MediaProjection mediaProjection, int i) {
        super("video/avc", iRecorder, encoderListener);
        this.mDrawTask = new DrawTask(null, 0);
        this.mCallback = new VirtualDisplay.Callback() { // from class: com.serenegiant.media.MediaScreenEncoder.1
            @Override // android.hardware.display.VirtualDisplay.Callback
            public void onPaused() {
            }

            @Override // android.hardware.display.VirtualDisplay.Callback
            public void onResumed() {
            }

            @Override // android.hardware.display.VirtualDisplay.Callback
            public void onStopped() {
                MediaScreenEncoder.this.mRequestStop = true;
            }
        };
        this.mMediaProjection = mediaProjection;
        this.mDensity = i;
        this.mHandler = HandlerThreadHandler.createHandler(TAG);
    }

    @Override // com.serenegiant.media.AbstractEncoder
    protected boolean internalPrepare(MediaReaper.ReaperListener reaperListener) throws Exception {
        this.mTrackIndex = -1;
        this.mIsCapturing = true;
        if (MediaCodecUtils.selectVideoEncoder("video/avc") == null) {
            Log.e(TAG, "Unable to find an appropriate codec for video/avc");
            return true;
        }
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", this.mWidth, this.mHeight);
        createVideoFormat.setInteger("color-format", MediaCodecUtils.OMX_COLOR_FormatAndroidOpaque);
        createVideoFormat.setInteger("bitrate", this.mBitRate > 0 ? this.mBitRate : getConfig().getBitrate(this.mWidth, this.mHeight));
        createVideoFormat.setInteger("frame-rate", this.mFramerate > 0 ? this.mFramerate : getConfig().captureFps());
        createVideoFormat.setInteger("i-frame-interval", this.mIFrameIntervals > 0 ? this.mIFrameIntervals : getConfig().calcIFrameIntervals());
        this.mMediaCodec = MediaCodec.createEncoderByType("video/avc");
        this.mMediaCodec.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mInputSurface = this.mMediaCodec.createInputSurface();
        this.mMediaCodec.start();
        this.mReaper = new MediaReaper.VideoReaper(this.mMediaCodec, reaperListener, this.mWidth, this.mHeight);
        new Thread(this.mDrawTask, DrawTask.class.getSimpleName()).start();
        return false;
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder, com.serenegiant.glpipeline.GLPipeline
    public void release() {
        super.release();
        this.mInputSurface = null;
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void start() {
        super.start();
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void stop() {
        this.mDrawTask.release();
        super.stop();
    }

    @Override // com.serenegiant.media.AbstractEncoder, com.serenegiant.media.Encoder
    public void signalEndOfInputStream() {
        if (this.mMediaCodec != null) {
            this.mMediaCodec.signalEndOfInputStream();
        }
    }

    /* loaded from: classes2.dex */
    private final class DrawTask extends EglTask {
        private VirtualDisplay display;
        private long intervals;
        private final Runnable mDrawTask;
        private GLDrawer2D mDrawer;
        private EGLBase.IEglSurface mEncoderSurface;
        private final SurfaceTexture.OnFrameAvailableListener mOnFrameAvailableListener;
        private Surface mSourceSurface;
        private SurfaceTexture mSourceTexture;
        private int mTexId;
        private final float[] mTexMatrix;

        @Override // com.serenegiant.utils.MessageTask
        protected boolean onError(Throwable th) {
            return false;
        }

        @Override // com.serenegiant.utils.MessageTask
        protected Object processRequest(int i, int i2, int i3, Object obj) {
            return null;
        }

        public DrawTask(EGLBase.IContext<?> iContext, int i) {
            super(iContext, i);
            this.mTexMatrix = new float[16];
            this.mOnFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.serenegiant.media.MediaScreenEncoder.DrawTask.1
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    if (MediaScreenEncoder.this.mIsCapturing) {
                        synchronized (MediaScreenEncoder.this.mSync) {
                            MediaScreenEncoder.this.requestDraw = true;
                            MediaScreenEncoder.this.mSync.notify();
                        }
                    }
                }
            };
            this.mDrawTask = new Runnable() { // from class: com.serenegiant.media.MediaScreenEncoder.DrawTask.2
                @Override // java.lang.Runnable
                public void run() {
                    boolean z;
                    synchronized (MediaScreenEncoder.this.mSync) {
                        z = MediaScreenEncoder.this.requestDraw;
                        if (!z) {
                            try {
                                MediaScreenEncoder.this.mSync.wait(DrawTask.this.intervals);
                                z = MediaScreenEncoder.this.requestDraw;
                                MediaScreenEncoder.this.requestDraw = false;
                            } catch (InterruptedException unused) {
                                return;
                            }
                        }
                    }
                    if (MediaScreenEncoder.this.mIsCapturing && !MediaScreenEncoder.this.mRequestStop) {
                        if (z) {
                            DrawTask.this.mSourceTexture.updateTexImage();
                            DrawTask.this.mSourceTexture.getTransformMatrix(DrawTask.this.mTexMatrix);
                        }
                        DrawTask.this.mEncoderSurface.makeCurrent();
                        DrawTask.this.mDrawer.draw(33984, DrawTask.this.mTexId, DrawTask.this.mTexMatrix, 0);
                        DrawTask.this.mEncoderSurface.swap();
                        DrawTask.this.makeCurrent();
                        GLES20.glClear(16384);
                        GLES20.glFlush();
                        MediaScreenEncoder.this.frameAvailableSoon();
                        DrawTask.this.queueEvent(this);
                        return;
                    }
                    DrawTask.this.releaseSelf();
                }
            };
        }

        @Override // com.serenegiant.utils.MessageTask
        protected void onStart() {
            GLDrawer2D create = GLDrawer2D.create(isGLES3(), true);
            this.mDrawer = create;
            this.mTexId = create.initTex(33984);
            SurfaceTexture surfaceTexture = new SurfaceTexture(this.mTexId);
            this.mSourceTexture = surfaceTexture;
            surfaceTexture.setDefaultBufferSize(MediaScreenEncoder.this.mWidth, MediaScreenEncoder.this.mHeight);
            this.mSourceSurface = new Surface(this.mSourceTexture);
            this.mSourceTexture.setOnFrameAvailableListener(this.mOnFrameAvailableListener, MediaScreenEncoder.this.mHandler);
            this.mEncoderSurface = getEgl().createFromSurface(MediaScreenEncoder.this.mInputSurface);
            this.intervals = 1000.0f / MediaScreenEncoder.this.mFramerate;
            this.display = MediaScreenEncoder.this.mMediaProjection.createVirtualDisplay("Capturing Display", MediaScreenEncoder.this.mWidth, MediaScreenEncoder.this.mHeight, MediaScreenEncoder.this.mDensity, 16, this.mSourceSurface, MediaScreenEncoder.this.mCallback, MediaScreenEncoder.this.mHandler);
            queueEvent(this.mDrawTask);
        }

        @Override // com.serenegiant.utils.MessageTask
        protected void onStop() {
            GLDrawer2D gLDrawer2D = this.mDrawer;
            if (gLDrawer2D != null) {
                gLDrawer2D.release();
                this.mDrawer = null;
            }
            Surface surface = this.mSourceSurface;
            if (surface != null) {
                surface.release();
                this.mSourceSurface = null;
            }
            SurfaceTexture surfaceTexture = this.mSourceTexture;
            if (surfaceTexture != null) {
                surfaceTexture.release();
                this.mSourceTexture = null;
            }
            EGLBase.IEglSurface iEglSurface = this.mEncoderSurface;
            if (iEglSurface != null) {
                iEglSurface.release();
                this.mEncoderSurface = null;
            }
            makeCurrent();
            if (this.display != null) {
                this.display.release();
            }
            if (MediaScreenEncoder.this.mMediaProjection != null) {
                MediaScreenEncoder.this.mMediaProjection.stop();
                MediaScreenEncoder.this.mMediaProjection = null;
            }
        }
    }
}
