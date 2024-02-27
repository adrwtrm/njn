package org.webrtc;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import com.serenegiant.gl.GLConst;
import java.util.concurrent.Callable;
import org.webrtc.EglBase;
import org.webrtc.TextureBufferImpl;
import org.webrtc.VideoFrame;

/* loaded from: classes.dex */
public class SurfaceTextureHelper {
    private static final int RESEND_INTERVAL = 100;
    private static final long RESEND_INTERVAL_NS = 100000000;
    private static final String TAG = "SurfaceTextureHelper";
    private Runnable _periodicSender;
    private long _timestampNs;
    private final EglBase eglBase;
    private final FrameRefMonitor frameRefMonitor;
    private int frameRotation;
    private final Handler handler;
    private boolean hasPendingTexture;
    private boolean isQuitting;
    private volatile boolean isTextureInUse;
    private VideoSink listener;
    private final int oesTextureId;
    private VideoSink pendingListener;
    final Runnable setListenerRunnable;
    private final SurfaceTexture surfaceTexture;
    private int textureHeight;
    private final TextureBufferImpl.RefCountMonitor textureRefCountMonitor;
    private int textureWidth;
    private final TimestampAligner timestampAligner;
    private final YuvConverter yuvConverter;

    /* loaded from: classes.dex */
    public interface FrameRefMonitor {
        void onDestroyBuffer(VideoFrame.TextureBuffer textureBuffer);

        void onNewBuffer(VideoFrame.TextureBuffer textureBuffer);

        void onReleaseBuffer(VideoFrame.TextureBuffer textureBuffer);

        void onRetainBuffer(VideoFrame.TextureBuffer textureBuffer);
    }

    public static SurfaceTextureHelper create(final String str, final EglBase.Context context, final boolean z, final YuvConverter yuvConverter, final FrameRefMonitor frameRefMonitor) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper());
        return (SurfaceTextureHelper) ThreadUtils.invokeAtFrontUninterruptibly(handler, new Callable<SurfaceTextureHelper>() { // from class: org.webrtc.SurfaceTextureHelper.1
            @Override // java.util.concurrent.Callable
            public SurfaceTextureHelper call() {
                try {
                    return new SurfaceTextureHelper(EglBase.Context.this, handler, z, yuvConverter, frameRefMonitor);
                } catch (RuntimeException e) {
                    Logging.e(SurfaceTextureHelper.TAG, str + " create failure", e);
                    return null;
                }
            }
        });
    }

    public static SurfaceTextureHelper create(String str, EglBase.Context context) {
        return create(str, context, false, new YuvConverter(), null);
    }

    public static SurfaceTextureHelper create(String str, EglBase.Context context, boolean z) {
        return create(str, context, z, new YuvConverter(), null);
    }

    public static SurfaceTextureHelper create(String str, EglBase.Context context, boolean z, YuvConverter yuvConverter) {
        return create(str, context, z, yuvConverter, null);
    }

    private SurfaceTextureHelper(EglBase.Context context, final Handler handler, boolean z, YuvConverter yuvConverter, FrameRefMonitor frameRefMonitor) {
        this.textureRefCountMonitor = new TextureBufferImpl.RefCountMonitor() { // from class: org.webrtc.SurfaceTextureHelper.2
            @Override // org.webrtc.TextureBufferImpl.RefCountMonitor
            public void onRetain(TextureBufferImpl textureBufferImpl) {
                if (SurfaceTextureHelper.this.frameRefMonitor != null) {
                    SurfaceTextureHelper.this.frameRefMonitor.onRetainBuffer(textureBufferImpl);
                }
            }

            @Override // org.webrtc.TextureBufferImpl.RefCountMonitor
            public void onRelease(TextureBufferImpl textureBufferImpl) {
                if (SurfaceTextureHelper.this.frameRefMonitor != null) {
                    SurfaceTextureHelper.this.frameRefMonitor.onReleaseBuffer(textureBufferImpl);
                }
            }

            @Override // org.webrtc.TextureBufferImpl.RefCountMonitor
            public void onDestroy(TextureBufferImpl textureBufferImpl) {
                SurfaceTextureHelper.this.returnTextureFrame();
                if (SurfaceTextureHelper.this.frameRefMonitor != null) {
                    SurfaceTextureHelper.this.frameRefMonitor.onDestroyBuffer(textureBufferImpl);
                }
            }
        };
        this.setListenerRunnable = new Runnable() { // from class: org.webrtc.SurfaceTextureHelper.3
            @Override // java.lang.Runnable
            public void run() {
                Logging.d(SurfaceTextureHelper.TAG, "Setting listener to " + SurfaceTextureHelper.this.pendingListener);
                SurfaceTextureHelper surfaceTextureHelper = SurfaceTextureHelper.this;
                surfaceTextureHelper.listener = surfaceTextureHelper.pendingListener;
                SurfaceTextureHelper.this.pendingListener = null;
                if (SurfaceTextureHelper.this.hasPendingTexture) {
                    SurfaceTextureHelper.this.updateTexImage();
                    SurfaceTextureHelper.this.hasPendingTexture = false;
                }
            }
        };
        this._periodicSender = new Runnable() { // from class: org.webrtc.SurfaceTextureHelper.4
            @Override // java.lang.Runnable
            public void run() {
                SurfaceTextureHelper.this.onFrameAvailableCore(true);
                if (SurfaceTextureHelper.this.isQuitting) {
                    return;
                }
                SurfaceTextureHelper.this.handler.postDelayed(this, 100L);
            }
        };
        if (handler.getLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("SurfaceTextureHelper must be created on the handler thread");
        }
        this.handler = handler;
        this.timestampAligner = z ? new TimestampAligner() : null;
        this.yuvConverter = yuvConverter;
        this.frameRefMonitor = frameRefMonitor;
        EglBase create = EglBase.create(context, EglBase.CONFIG_PIXEL_BUFFER);
        this.eglBase = create;
        try {
            create.createDummyPbufferSurface();
            create.makeCurrent();
            int generateTexture = GlUtil.generateTexture(GLConst.GL_TEXTURE_EXTERNAL_OES);
            this.oesTextureId = generateTexture;
            SurfaceTexture surfaceTexture = new SurfaceTexture(generateTexture);
            this.surfaceTexture = surfaceTexture;
            setOnFrameAvailableListener(surfaceTexture, new SurfaceTexture.OnFrameAvailableListener() { // from class: org.webrtc.SurfaceTextureHelper$$ExternalSyntheticLambda5
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public final void onFrameAvailable(SurfaceTexture surfaceTexture2) {
                    SurfaceTextureHelper.this.m1920lambda$new$0$orgwebrtcSurfaceTextureHelper(handler, surfaceTexture2);
                }
            }, handler);
        } catch (RuntimeException e) {
            this.eglBase.release();
            handler.getLooper().quit();
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$org-webrtc-SurfaceTextureHelper  reason: not valid java name */
    public /* synthetic */ void m1920lambda$new$0$orgwebrtcSurfaceTextureHelper(Handler handler, SurfaceTexture surfaceTexture) {
        handler.removeCallbacks(this._periodicSender);
        handler.postDelayed(this._periodicSender, 1000L);
        onFrameAvailableCore(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFrameAvailableCore(boolean z) {
        if (this.hasPendingTexture) {
            Logging.d(TAG, "A frame is already pending, dropping frame.");
        }
        this.hasPendingTexture = true;
        tryDeliverTextureFrame(z);
    }

    private static void setOnFrameAvailableListener(SurfaceTexture surfaceTexture, SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener, Handler handler) {
        surfaceTexture.setOnFrameAvailableListener(onFrameAvailableListener, handler);
    }

    public void startListening(VideoSink videoSink) {
        if (this.listener != null || this.pendingListener != null) {
            throw new IllegalStateException("SurfaceTextureHelper listener has already been set.");
        }
        this.pendingListener = videoSink;
        this.handler.post(this.setListenerRunnable);
    }

    public void stopListening() {
        Logging.d(TAG, "stopListening()");
        this.handler.removeCallbacks(this.setListenerRunnable);
        ThreadUtils.invokeAtFrontUninterruptibly(this.handler, new Runnable() { // from class: org.webrtc.SurfaceTextureHelper$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceTextureHelper.this.m1924lambda$stopListening$1$orgwebrtcSurfaceTextureHelper();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$stopListening$1$org-webrtc-SurfaceTextureHelper  reason: not valid java name */
    public /* synthetic */ void m1924lambda$stopListening$1$orgwebrtcSurfaceTextureHelper() {
        this.listener = null;
        this.pendingListener = null;
    }

    public void setTextureSize(final int i, final int i2) {
        if (i <= 0) {
            throw new IllegalArgumentException("Texture width must be positive, but was " + i);
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("Texture height must be positive, but was " + i2);
        }
        this.surfaceTexture.setDefaultBufferSize(i, i2);
        this.handler.post(new Runnable() { // from class: org.webrtc.SurfaceTextureHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceTextureHelper.this.m1923lambda$setTextureSize$2$orgwebrtcSurfaceTextureHelper(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setTextureSize$2$org-webrtc-SurfaceTextureHelper  reason: not valid java name */
    public /* synthetic */ void m1923lambda$setTextureSize$2$orgwebrtcSurfaceTextureHelper(int i, int i2) {
        this.textureWidth = i;
        this.textureHeight = i2;
        tryDeliverTextureFrame(false);
    }

    public void forceFrame() {
        this.handler.post(new Runnable() { // from class: org.webrtc.SurfaceTextureHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceTextureHelper.this.m1919lambda$forceFrame$3$orgwebrtcSurfaceTextureHelper();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$forceFrame$3$org-webrtc-SurfaceTextureHelper  reason: not valid java name */
    public /* synthetic */ void m1919lambda$forceFrame$3$orgwebrtcSurfaceTextureHelper() {
        this.hasPendingTexture = true;
        tryDeliverTextureFrame(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setFrameRotation$4$org-webrtc-SurfaceTextureHelper  reason: not valid java name */
    public /* synthetic */ void m1922lambda$setFrameRotation$4$orgwebrtcSurfaceTextureHelper(int i) {
        this.frameRotation = i;
    }

    public void setFrameRotation(final int i) {
        this.handler.post(new Runnable() { // from class: org.webrtc.SurfaceTextureHelper$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceTextureHelper.this.m1922lambda$setFrameRotation$4$orgwebrtcSurfaceTextureHelper(i);
            }
        });
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.surfaceTexture;
    }

    public Handler getHandler() {
        return this.handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void returnTextureFrame() {
        this.handler.post(new Runnable() { // from class: org.webrtc.SurfaceTextureHelper$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceTextureHelper.this.m1921lambda$returnTextureFrame$5$orgwebrtcSurfaceTextureHelper();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$returnTextureFrame$5$org-webrtc-SurfaceTextureHelper  reason: not valid java name */
    public /* synthetic */ void m1921lambda$returnTextureFrame$5$orgwebrtcSurfaceTextureHelper() {
        this.isTextureInUse = false;
        if (this.isQuitting) {
            release();
        } else {
            tryDeliverTextureFrame(false);
        }
    }

    public boolean isTextureInUse() {
        return this.isTextureInUse;
    }

    public void dispose() {
        Logging.d(TAG, "dispose()");
        ThreadUtils.invokeAtFrontUninterruptibly(this.handler, new Runnable() { // from class: org.webrtc.SurfaceTextureHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceTextureHelper.this.m1918lambda$dispose$6$orgwebrtcSurfaceTextureHelper();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$dispose$6$org-webrtc-SurfaceTextureHelper  reason: not valid java name */
    public /* synthetic */ void m1918lambda$dispose$6$orgwebrtcSurfaceTextureHelper() {
        this.isQuitting = true;
        if (this.isTextureInUse) {
            return;
        }
        release();
    }

    @Deprecated
    public VideoFrame.I420Buffer textureToYuv(VideoFrame.TextureBuffer textureBuffer) {
        return textureBuffer.toI420();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTexImage() {
        synchronized (EglBase.lock) {
            this.surfaceTexture.updateTexImage();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void tryDeliverTextureFrame(boolean r15) {
        /*
            r14 = this;
            android.os.Handler r0 = r14.handler
            android.os.Looper r0 = r0.getLooper()
            java.lang.Thread r0 = r0.getThread()
            java.lang.Thread r1 = java.lang.Thread.currentThread()
            if (r0 != r1) goto L98
            boolean r0 = r14.isQuitting
            if (r0 != 0) goto L97
            boolean r0 = r14.hasPendingTexture
            if (r0 == 0) goto L97
            boolean r0 = r14.isTextureInUse
            if (r0 != 0) goto L97
            org.webrtc.VideoSink r0 = r14.listener
            if (r0 != 0) goto L22
            goto L97
        L22:
            int r0 = r14.textureWidth
            if (r0 == 0) goto L90
            int r0 = r14.textureHeight
            if (r0 != 0) goto L2b
            goto L90
        L2b:
            r0 = 1
            r14.isTextureInUse = r0
            r0 = 0
            r14.hasPendingTexture = r0
            r14.updateTexImage()
            r0 = 16
            float[] r0 = new float[r0]
            android.graphics.SurfaceTexture r1 = r14.surfaceTexture
            r1.getTransformMatrix(r0)
            android.graphics.SurfaceTexture r1 = r14.surfaceTexture
            long r1 = r1.getTimestamp()
            org.webrtc.TimestampAligner r3 = r14.timestampAligner
            if (r3 == 0) goto L4b
            long r1 = r3.translateTimestamp(r1)
        L4b:
            if (r15 == 0) goto L5d
            long r3 = r14._timestampNs
            r5 = 0
            int r15 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r15 != 0) goto L56
            goto L5d
        L56:
            r1 = 100000000(0x5f5e100, double:4.94065646E-316)
            long r3 = r3 + r1
            r14._timestampNs = r3
            goto L5f
        L5d:
            r14._timestampNs = r1
        L5f:
            org.webrtc.TextureBufferImpl r15 = new org.webrtc.TextureBufferImpl
            int r6 = r14.textureWidth
            int r7 = r14.textureHeight
            org.webrtc.VideoFrame$TextureBuffer$Type r8 = org.webrtc.VideoFrame.TextureBuffer.Type.OES
            int r9 = r14.oesTextureId
            android.graphics.Matrix r10 = org.webrtc.RendererCommon.convertMatrixToAndroidGraphicsMatrix(r0)
            android.os.Handler r11 = r14.handler
            org.webrtc.YuvConverter r12 = r14.yuvConverter
            org.webrtc.TextureBufferImpl$RefCountMonitor r13 = r14.textureRefCountMonitor
            r5 = r15
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13)
            org.webrtc.SurfaceTextureHelper$FrameRefMonitor r0 = r14.frameRefMonitor
            if (r0 == 0) goto L7e
            r0.onNewBuffer(r15)
        L7e:
            org.webrtc.VideoFrame r0 = new org.webrtc.VideoFrame
            int r1 = r14.frameRotation
            long r2 = r14._timestampNs
            r0.<init>(r15, r1, r2)
            org.webrtc.VideoSink r15 = r14.listener
            r15.onFrame(r0)
            r0.release()
            return
        L90:
            java.lang.String r15 = "SurfaceTextureHelper"
            java.lang.String r0 = "Texture size has not been set."
            org.webrtc.Logging.w(r15, r0)
        L97:
            return
        L98:
            java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
            java.lang.String r0 = "Wrong thread."
            r15.<init>(r0)
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.SurfaceTextureHelper.tryDeliverTextureFrame(boolean):void");
    }

    private void release() {
        if (this.handler.getLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Wrong thread.");
        }
        if (this.isTextureInUse || !this.isQuitting) {
            throw new IllegalStateException("Unexpected release.");
        }
        this.yuvConverter.release();
        GLES20.glDeleteTextures(1, new int[]{this.oesTextureId}, 0);
        this.surfaceTexture.release();
        this.eglBase.release();
        this.handler.getLooper().quit();
        TimestampAligner timestampAligner = this.timestampAligner;
        if (timestampAligner != null) {
            timestampAligner.dispose();
        }
    }
}
