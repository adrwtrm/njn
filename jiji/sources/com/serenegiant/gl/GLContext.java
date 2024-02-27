package com.serenegiant.gl;

import android.opengl.GLES20;
import android.text.TextUtils;
import android.util.Log;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.egl.EGLConst;

/* loaded from: classes2.dex */
public class GLContext implements EGLConst {
    private static final boolean DEBUG = false;
    private static final String TAG = "GLContext";
    private static boolean isOutputVersionInfo = false;
    private EGLBase mEgl;
    private EGLBase.IEglSurface mEglMasterSurface;
    private final int mFlags;
    private long mGLThreadId;
    private String mGlExtensions;
    private final int mMaxClientVersion;
    private final EGLBase.IContext<?> mSharedContext;
    private final Object mSync;

    public GLContext() {
        this(GLUtils.getSupportedGLVersion(), null, 0);
    }

    public GLContext(GLContext gLContext) {
        this(gLContext.getMaxClientVersion(), gLContext.getContext(), gLContext.getFlags());
    }

    public GLContext(int i, EGLBase.IContext<?> iContext, int i2) {
        this.mSync = new Object();
        this.mEgl = null;
        this.mMaxClientVersion = i;
        this.mSharedContext = iContext;
        this.mFlags = i2;
    }

    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            super.finalize();
        }
    }

    public void release() {
        synchronized (this.mSync) {
            this.mGLThreadId = 0L;
            EGLBase.IEglSurface iEglSurface = this.mEglMasterSurface;
            if (iEglSurface != null) {
                iEglSurface.release();
                this.mEglMasterSurface = null;
            }
            EGLBase eGLBase = this.mEgl;
            if (eGLBase != null) {
                eGLBase.release();
                this.mEgl = null;
            }
        }
    }

    public void initialize() throws IllegalArgumentException {
        initialize(null, 1, 1);
    }

    public void initialize(Object obj, int i, int i2) throws IllegalArgumentException {
        EGLBase.IEglSurface iEglSurface;
        EGLBase.IContext<?> iContext;
        if (this.mEgl == null && ((iContext = this.mSharedContext) == null || (iContext instanceof EGLBase.IContext))) {
            int i3 = this.mFlags;
            this.mEgl = EGLBase.createFrom(this.mMaxClientVersion, iContext, (i3 & 1) == 1, (i3 & 4) == 4 ? 1 : (i3 & 32) == 32 ? 8 : 0, (i3 & 2) == 2);
        }
        if (this.mEgl != null) {
            this.mGlExtensions = null;
            int max = Math.max(i, 1);
            int max2 = Math.max(i2, 1);
            if (i <= 0 && i2 <= 0 && (iEglSurface = this.mEglMasterSurface) != null) {
                max = Math.max(max, iEglSurface.getWidth());
                max2 = Math.max(max2, this.mEglMasterSurface.getHeight());
            }
            EGLBase.IEglSurface iEglSurface2 = this.mEglMasterSurface;
            if (iEglSurface2 != null) {
                iEglSurface2.release();
                this.mEglMasterSurface = null;
            }
            if (GLUtils.isSupportedSurface(obj)) {
                this.mEglMasterSurface = this.mEgl.createFromSurface(obj);
            } else {
                this.mEglMasterSurface = this.mEgl.createOffscreen(max, max2);
            }
            this.mGLThreadId = Thread.currentThread().getId();
            makeDefault();
            if (!isOutputVersionInfo) {
                isOutputVersionInfo = true;
                logVersionInfo();
            }
            isOES3Supported();
            return;
        }
        throw new IllegalArgumentException("failed to create EGLBase");
    }

    public EGLBase getEgl() throws IllegalStateException {
        EGLBase eGLBase;
        synchronized (this.mSync) {
            eGLBase = this.mEgl;
            if (eGLBase == null) {
                throw new IllegalStateException();
            }
        }
        return eGLBase;
    }

    public EGLBase.IConfig<?> getConfig() throws IllegalStateException {
        EGLBase.IConfig<?> config;
        synchronized (this.mSync) {
            EGLBase eGLBase = this.mEgl;
            if (eGLBase != null) {
                config = eGLBase.getConfig();
            } else {
                throw new IllegalStateException();
            }
        }
        return config;
    }

    public int getMaxClientVersion() {
        return this.mMaxClientVersion;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getMasterWidth() {
        int width;
        synchronized (this.mSync) {
            EGLBase.IEglSurface iEglSurface = this.mEglMasterSurface;
            width = iEglSurface != null ? iEglSurface.getWidth() : 1;
        }
        return width;
    }

    public int getMasterHeight() {
        int height;
        synchronized (this.mSync) {
            EGLBase.IEglSurface iEglSurface = this.mEglMasterSurface;
            height = iEglSurface != null ? iEglSurface.getHeight() : 1;
        }
        return height;
    }

    public EGLBase.IContext<?> getContext() throws IllegalStateException {
        EGLBase.IContext<?> context;
        synchronized (this.mSync) {
            EGLBase eGLBase = this.mEgl;
            context = eGLBase != null ? eGLBase.getContext() : null;
            if (context == null) {
                throw new IllegalStateException();
            }
        }
        return context;
    }

    public void makeDefault() throws IllegalStateException {
        EGLBase.IEglSurface iEglSurface;
        synchronized (this.mSync) {
            if (this.mEgl != null && (iEglSurface = this.mEglMasterSurface) != null) {
                iEglSurface.makeCurrent();
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void swap() throws IllegalStateException {
        EGLBase.IEglSurface iEglSurface;
        synchronized (this.mSync) {
            if (this.mEgl != null && (iEglSurface = this.mEglMasterSurface) != null) {
                iEglSurface.swap();
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void swap(long j) throws IllegalStateException {
        EGLBase.IEglSurface iEglSurface;
        synchronized (this.mSync) {
            if (this.mEgl != null && (iEglSurface = this.mEglMasterSurface) != null) {
                iEglSurface.swap(j);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void sync() throws IllegalStateException {
        synchronized (this.mSync) {
            EGLBase eGLBase = this.mEgl;
            if (eGLBase != null) {
                eGLBase.sync();
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void waitGL() throws IllegalStateException {
        synchronized (this.mSync) {
            EGLBase eGLBase = this.mEgl;
            if (eGLBase != null) {
                eGLBase.waitGL();
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void waitNative() throws IllegalStateException {
        synchronized (this.mSync) {
            EGLBase eGLBase = this.mEgl;
            if (eGLBase != null) {
                eGLBase.waitNative();
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public long getGLThreadId() {
        long j;
        synchronized (this.mSync) {
            j = this.mGLThreadId;
        }
        return j;
    }

    public boolean inGLThread() {
        boolean z;
        synchronized (this.mSync) {
            z = this.mGLThreadId == Thread.currentThread().getId();
        }
        return z;
    }

    public boolean isGLES2() {
        return getGlVersion() > 1;
    }

    public boolean isGLES3() {
        return getGlVersion() > 2;
    }

    public int getGlVersion() {
        int glVersion;
        synchronized (this.mSync) {
            EGLBase eGLBase = this.mEgl;
            glVersion = eGLBase != null ? eGLBase.getGlVersion() : 0;
        }
        return glVersion;
    }

    public boolean hasExtension(String str) {
        if (TextUtils.isEmpty(this.mGlExtensions)) {
            this.mGlExtensions = GLES20.glGetString(7939);
        }
        String str2 = this.mGlExtensions;
        return str2 != null && str2.contains(str);
    }

    public boolean isOES2Supported() {
        return isGLES2() && hasExtension("GL_OES_EGL_image_external");
    }

    public boolean isOES3Supported() {
        return isGLES3() && hasExtension("GL_OES_EGL_image_external_essl3");
    }

    private void checkGLThread() throws IllegalStateException {
        if (!inGLThread()) {
            throw new IllegalThreadStateException("Not a GL thread");
        }
    }

    public static void logVersionInfo() {
        String str = TAG;
        Log.i(str, "vendor:" + GLES20.glGetString(7936));
        Log.i(str, "renderer:" + GLES20.glGetString(7937));
        Log.i(str, "version:" + GLES20.glGetString(7938));
        Log.i(str, "supported version:" + supportedGLESVersion());
        Log.i(str, "extensions:" + GLES20.glGetString(7939));
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static float supportedGLESVersion() {
        /*
            boolean r0 = com.serenegiant.system.BuildCheck.isAndroid4_3()
            r1 = 1036831949(0x3dcccccd, float:0.1)
            r2 = 0
            if (r0 == 0) goto L29
            r0 = 1
            int[] r0 = new int[r0]
            r3 = 33307(0x821b, float:4.6673E-41)
            r4 = 0
            android.opengl.GLES30.glGetIntegerv(r3, r0, r4)
            r3 = r0[r4]
            r5 = 33308(0x821c, float:4.6674E-41)
            android.opengl.GLES30.glGetIntegerv(r5, r0, r4)
            r0 = r0[r4]
            int r4 = android.opengl.GLES30.glGetError()
            if (r4 != 0) goto L29
            float r3 = (float) r3
            float r0 = (float) r0
            float r0 = r0 * r1
            float r3 = r3 + r0
            goto L2a
        L29:
            r3 = r2
        L2a:
            int r0 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r0 > 0) goto L4c
            java.lang.String r0 = "ro.opengles.version"
            java.lang.String r0 = com.serenegiant.system.SysPropReader.read(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L4c
            int r0 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.NumberFormatException -> L4c
            r2 = -65536(0xffffffffffff0000, float:NaN)
            r2 = r2 & r0
            int r2 = r2 >> 16
            float r2 = (float) r2
            r3 = 65535(0xffff, float:9.1834E-41)
            r0 = r0 & r3
            float r0 = (float) r0
            float r0 = r0 * r1
            float r3 = r2 + r0
        L4c:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.gl.GLContext.supportedGLESVersion():float");
    }
}
