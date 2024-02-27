package com.serenegiant.glpipeline;

/* loaded from: classes2.dex */
public class ProxyPipeline implements GLPipeline {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_HEIGHT = 480;
    private static final int DEFAULT_WIDTH = 640;
    private static final String TAG = "ProxyPipeline";
    private int mHeight;
    private GLPipeline mParent;
    private GLPipeline mPipeline;
    private volatile boolean mReleased;
    private final Object mSync;
    private int mWidth;

    public ProxyPipeline() {
        this(640, 480);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ProxyPipeline(int i, int i2) {
        this.mSync = new Object();
        this.mReleased = false;
        if (i > 0 || i2 > 0) {
            this.mWidth = i;
            this.mHeight = i2;
            return;
        }
        this.mWidth = 640;
        this.mHeight = 480;
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public final void release() {
        if (this.mReleased) {
            return;
        }
        this.mReleased = true;
        internalRelease();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void internalRelease() {
        GLPipeline gLPipeline;
        this.mReleased = true;
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
        if (!this.mReleased) {
            this.mWidth = i;
            this.mHeight = i2;
            GLPipeline pipeline = getPipeline();
            if (pipeline != null) {
                pipeline.resize(i, i2);
                return;
            }
            return;
        }
        throw new IllegalStateException("already released!");
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public boolean isValid() {
        return !this.mReleased;
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public boolean isActive() {
        boolean z;
        synchronized (this.mSync) {
            z = (this.mReleased || this.mPipeline == null || this.mParent == null) ? false : true;
        }
        return z;
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public int getWidth() {
        return this.mWidth;
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public int getHeight() {
        return this.mHeight;
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void setParent(GLPipeline gLPipeline) {
        if (!this.mReleased) {
            synchronized (this.mSync) {
                this.mParent = gLPipeline;
            }
            return;
        }
        throw new IllegalStateException("already released!");
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
        if (!this.mReleased) {
            synchronized (this.mSync) {
                this.mPipeline = gLPipeline;
            }
            if (gLPipeline != null) {
                gLPipeline.setParent(this);
                gLPipeline.resize(this.mWidth, this.mHeight);
                return;
            }
            return;
        }
        throw new IllegalStateException("already released!");
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
        GLPipeline findFirst = GLPipeline.findFirst(this);
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
        if (findFirst != this) {
            GLPipeline.validatePipelineChain(findFirst);
            gLPipeline.refresh();
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void onFrameAvailable(boolean z, int i, float[] fArr) {
        GLPipeline gLPipeline;
        if (this.mReleased) {
            return;
        }
        synchronized (this.mSync) {
            gLPipeline = this.mPipeline;
        }
        if (gLPipeline != null) {
            gLPipeline.onFrameAvailable(z, i, fArr);
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void refresh() {
        GLPipeline gLPipeline;
        if (this.mReleased) {
            return;
        }
        synchronized (this.mSync) {
            gLPipeline = this.mPipeline;
        }
        if (gLPipeline != null) {
            gLPipeline.refresh();
        }
    }
}
