package com.serenegiant.egl;

import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLContext;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.utils.MessageTask;

/* loaded from: classes2.dex */
public abstract class EglTask extends MessageTask {
    private static final boolean DEBUG = false;
    private static final String TAG = "EglTask";
    private final GLContext mGLContext;
    private final int mMasterHeight;
    private final int mMasterWidth;

    public EglTask(EGLBase.IContext<?> iContext, int i) {
        this(GLUtils.getSupportedGLVersion(), iContext, i);
    }

    public EglTask(int i, EGLBase.IContext<?> iContext, int i2) {
        this(i, iContext, i2, 1, 1);
    }

    public EglTask(int i, EGLBase.IContext<?> iContext, int i2, int i3, int i4) {
        this(new GLContext(i, iContext, i2), i3, i4);
    }

    public EglTask(GLContext gLContext, int i, int i2) {
        this.mGLContext = gLContext;
        this.mMasterWidth = Math.max(i, 1);
        this.mMasterHeight = Math.max(i2, 1);
        init(0, 0, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.utils.MessageTask
    public void onInit(int i, int i2, Object obj) {
        this.mGLContext.initialize(null, this.mMasterWidth, this.mMasterHeight);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.utils.MessageTask
    public MessageTask.Request takeRequest() throws InterruptedException {
        MessageTask.Request takeRequest = super.takeRequest();
        this.mGLContext.makeDefault();
        return takeRequest;
    }

    @Override // com.serenegiant.utils.MessageTask
    protected void onBeforeStop() {
        this.mGLContext.makeDefault();
    }

    @Override // com.serenegiant.utils.MessageTask
    protected void onRelease() {
        this.mGLContext.release();
    }

    public GLContext getGLContext() {
        return this.mGLContext;
    }

    public EGLBase getEgl() {
        return this.mGLContext.getEgl();
    }

    public EGLBase.IConfig<?> getConfig() {
        return this.mGLContext.getConfig();
    }

    public EGLBase.IContext<?> getContext() {
        return this.mGLContext.getContext();
    }

    public void makeCurrent() {
        this.mGLContext.makeDefault();
    }

    public void swap() {
        this.mGLContext.swap();
    }

    public int getGlVersion() {
        return this.mGLContext.getGlVersion();
    }

    public boolean isGLES3() {
        return this.mGLContext.isGLES3();
    }

    public boolean hasExtension(String str) {
        return this.mGLContext.hasExtension(str);
    }

    public boolean isOES3Supported() {
        return this.mGLContext.isOES3Supported();
    }
}
