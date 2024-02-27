package com.serenegiant.glpipeline;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLContext;
import com.serenegiant.gl.GLManager;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.glpipeline.GLPipelineSource;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.utils.ThreadUtils;

/* loaded from: classes2.dex */
public class VideoSourcePipeline extends ProxyPipeline implements GLPipelineSource {
    private static final boolean DEBUG = false;
    private static final int REQUEST_RECREATE_MASTER_SURFACE = 3;
    private static final int REQUEST_UPDATE_SIZE = 2;
    private static final int REQUEST_UPDATE_TEXTURE = 1;
    private static final String TAG = "VideoSourcePipeline";
    private final GLPipelineSource.PipelineSourceCallback mCallback;
    private final GLContext mGLContext;
    private final Handler mGLHandler;
    private Surface mInputSurface;
    private SurfaceTexture mInputTexture;
    private final GLManager mManager;
    private final SurfaceTexture.OnFrameAvailableListener mOnFrameAvailableListener;
    private final boolean mOwnManager;
    private final Object mSync;
    private int mTexId;
    private final float[] mTexMatrix;

    public VideoSourcePipeline(GLManager gLManager, int i, int i2, GLPipelineSource.PipelineSourceCallback pipelineSourceCallback) {
        this(gLManager, i, i2, pipelineSourceCallback, false);
    }

    public VideoSourcePipeline(GLManager gLManager, int i, int i2, GLPipelineSource.PipelineSourceCallback pipelineSourceCallback, boolean z) {
        super(i, i2);
        this.mSync = new Object();
        this.mTexMatrix = new float[16];
        this.mOnFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.serenegiant.glpipeline.VideoSourcePipeline.3
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                if (VideoSourcePipeline.this.isValid()) {
                    VideoSourcePipeline.this.mGLHandler.sendEmptyMessage(1);
                }
            }
        };
        this.mOwnManager = z;
        Handler.Callback callback = new Handler.Callback() { // from class: com.serenegiant.glpipeline.VideoSourcePipeline.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                return VideoSourcePipeline.this.handleMessage(message);
            }
        };
        if (z) {
            GLManager createShared = gLManager.createShared(callback);
            this.mManager = createShared;
            this.mGLHandler = createShared.getGLHandler();
        } else {
            this.mManager = gLManager;
            this.mGLHandler = gLManager.createGLHandler(callback);
        }
        this.mGLContext = this.mManager.getGLContext();
        this.mCallback = pipelineSourceCallback;
        this.mGLHandler.sendEmptyMessage(3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.glpipeline.ProxyPipeline
    public void internalRelease() {
        if (isValid()) {
            this.mGLHandler.post(new Runnable() { // from class: com.serenegiant.glpipeline.VideoSourcePipeline.2
                @Override // java.lang.Runnable
                public void run() {
                    VideoSourcePipeline.this.handleReleaseInputSurface();
                    if (VideoSourcePipeline.this.mOwnManager) {
                        VideoSourcePipeline.this.mManager.release();
                    }
                }
            });
        }
        super.internalRelease();
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public GLManager getGLManager() throws IllegalStateException {
        checkValid();
        return this.mManager;
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void resize(int i, int i2) throws IllegalStateException {
        checkValid();
        boolean z = i > 0 && i2 > 0 && !(i == getWidth() && i2 == getHeight());
        super.resize(i, i2);
        if (z) {
            Handler handler = this.mGLHandler;
            handler.sendMessage(handler.obtainMessage(2, i, i2));
        }
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public boolean isValid() {
        Surface surface;
        return this.mManager.isValid() && (surface = this.mInputSurface) != null && surface.isValid();
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public SurfaceTexture getInputSurfaceTexture() throws IllegalStateException {
        checkValid();
        SurfaceTexture surfaceTexture = this.mInputTexture;
        if (surfaceTexture != null) {
            return surfaceTexture;
        }
        throw new IllegalStateException("has no master surface");
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public Surface getInputSurface() throws IllegalStateException {
        checkValid();
        Surface surface = this.mInputSurface;
        if (surface != null) {
            return surface;
        }
        throw new IllegalStateException("has no master surface");
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public int getTexId() {
        return this.mTexId;
    }

    @Override // com.serenegiant.glpipeline.GLPipelineSource
    public float[] getTexMatrix() {
        return this.mTexMatrix;
    }

    protected void checkValid() throws IllegalStateException {
        if (!this.mManager.isValid()) {
            throw new IllegalStateException("Already released");
        }
    }

    protected boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            handleUpdateTex();
            return true;
        } else if (i == 2) {
            handleResize(message.arg1, message.arg2);
            return true;
        } else if (i != 3) {
            return false;
        } else {
            handleReCreateInputSurface();
            return true;
        }
    }

    protected void handleUpdateTex() {
        makeDefault();
        GLES20.glClear(16384);
        GLES20.glFlush();
        SurfaceTexture surfaceTexture = this.mInputTexture;
        if (surfaceTexture != null) {
            surfaceTexture.updateTexImage();
            this.mInputTexture.getTransformMatrix(this.mTexMatrix);
            GLES20.glFlush();
            ThreadUtils.NoThrowSleep(0L, 0);
            onFrameAvailable(true, this.mTexId, this.mTexMatrix);
        }
    }

    protected void handleReCreateInputSurface() {
        makeDefault();
        handleReleaseInputSurface();
        makeDefault();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.mTexId = GLUtils.initTex(GLConst.GL_TEXTURE_EXTERNAL_OES, 33984, 9728);
        this.mInputTexture = new SurfaceTexture(this.mTexId);
        this.mInputSurface = new Surface(this.mInputTexture);
        if (BuildCheck.isAndroid4_1()) {
            this.mInputTexture.setDefaultBufferSize(getWidth(), getHeight());
        }
        if (BuildCheck.isLollipop()) {
            this.mInputTexture.setOnFrameAvailableListener(this.mOnFrameAvailableListener, this.mGLHandler);
        } else {
            this.mInputTexture.setOnFrameAvailableListener(this.mOnFrameAvailableListener);
        }
        this.mCallback.onCreate(this.mInputSurface);
    }

    protected void handleReleaseInputSurface() {
        if (this.mInputSurface != null) {
            this.mCallback.onDestroy();
            try {
                this.mInputSurface.release();
            } catch (Exception e) {
                Log.w(TAG, e);
            }
            this.mInputSurface = null;
        }
        SurfaceTexture surfaceTexture = this.mInputTexture;
        if (surfaceTexture != null) {
            try {
                surfaceTexture.release();
            } catch (Exception e2) {
                Log.w(TAG, e2);
            }
            this.mInputTexture = null;
        }
        int i = this.mTexId;
        if (i != 0) {
            GLUtils.deleteTex(i);
            this.mTexId = 0;
        }
    }

    protected void handleResize(int i, int i2) {
        Surface surface = this.mInputSurface;
        if (surface == null || !surface.isValid()) {
            handleReCreateInputSurface();
        }
        if (BuildCheck.isAndroid4_1()) {
            this.mInputTexture.setDefaultBufferSize(i, i2);
        }
    }

    protected void makeDefault() {
        this.mGLContext.makeDefault();
    }
}
