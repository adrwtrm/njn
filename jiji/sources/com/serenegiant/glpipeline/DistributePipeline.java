package com.serenegiant.glpipeline;

import android.util.Log;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes2.dex */
public class DistributePipeline implements GLPipeline {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_HEIGHT = 480;
    private static final int DEFAULT_WIDTH = 640;
    private static final String TAG = "DistributePipeline";
    private int mHeight;
    private GLPipeline mParent;
    private final Set<GLPipeline> mPipelines;
    private volatile boolean mReleased;
    private final Object mSync;
    private int mWidth;

    public DistributePipeline() {
        this(640, 480);
    }

    protected DistributePipeline(int i, int i2) {
        this.mSync = new Object();
        this.mPipelines = new CopyOnWriteArraySet();
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

    protected void internalRelease() {
        this.mReleased = true;
        synchronized (this.mSync) {
            this.mParent = null;
        }
        for (GLPipeline gLPipeline : this.mPipelines) {
            gLPipeline.release();
        }
        this.mPipelines.clear();
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void resize(int i, int i2) throws IllegalStateException {
        if (!this.mReleased) {
            this.mWidth = i;
            this.mHeight = i2;
            for (GLPipeline gLPipeline : this.mPipelines) {
                gLPipeline.resize(i, i2);
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
            z = (this.mReleased || this.mPipelines.isEmpty() || this.mParent == null) ? false : true;
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

    public void addPipeline(GLPipeline gLPipeline) {
        if (!this.mReleased) {
            this.mPipelines.add(gLPipeline);
            gLPipeline.setParent(this);
            gLPipeline.resize(this.mWidth, this.mHeight);
            return;
        }
        throw new IllegalStateException("already released!");
    }

    public void removePipeline(GLPipeline gLPipeline) {
        if (!this.mReleased) {
            this.mPipelines.remove(gLPipeline);
            gLPipeline.setParent(null);
            return;
        }
        throw new IllegalStateException("already released!");
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void setPipeline(GLPipeline gLPipeline) {
        if (gLPipeline == null) {
            throw new IllegalArgumentException("DistributePipeline#setPipeline can't accept null!");
        }
        if (!this.mReleased) {
            this.mPipelines.add(gLPipeline);
            gLPipeline.setParent(this);
            gLPipeline.resize(this.mWidth, this.mHeight);
            return;
        }
        throw new IllegalStateException("already released!");
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public GLPipeline getPipeline() {
        Iterator<GLPipeline> it = this.mPipelines.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void remove() {
        GLPipeline gLPipeline;
        GLPipeline findFirst = GLPipeline.findFirst(this);
        synchronized (this.mSync) {
            gLPipeline = this.mParent;
            if (gLPipeline != null) {
                if (this.mPipelines.size() == 1) {
                    this.mParent.setPipeline(getPipeline());
                } else {
                    this.mParent.setPipeline(null);
                    Log.d(TAG, "#remove can't rebuild pipeline chain!");
                }
            }
            this.mParent = null;
        }
        if (findFirst != this) {
            GLPipeline.validatePipelineChain(findFirst);
            gLPipeline.refresh();
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void onFrameAvailable(boolean z, int i, float[] fArr) {
        if (this.mReleased) {
            return;
        }
        for (GLPipeline gLPipeline : this.mPipelines) {
            if (gLPipeline != null) {
                gLPipeline.onFrameAvailable(z, i, fArr);
            }
        }
    }

    @Override // com.serenegiant.glpipeline.GLPipeline
    public void refresh() {
        if (this.mReleased) {
            return;
        }
        for (GLPipeline gLPipeline : this.mPipelines) {
            gLPipeline.refresh();
        }
    }
}
