package com.serenegiant.egl;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.util.Log;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.system.BuildCheck;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class EGLBase14 extends EGLBase {
    private static final boolean DEBUG = false;
    private static final Context EGL_NO_CONTEXT = wrap(EGL14.EGL_NO_CONTEXT);
    private static final String TAG = "EGLBase14";
    private Context mContext;
    private EGLContext mDefaultContext;
    private Config mEglConfig;
    private EGLDisplay mEglDisplay;
    private int mGlVersion;
    private final int[] mSurfaceDimension;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Context wrap(EGLContext eGLContext) {
        return new Context(eGLContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Config wrap(EGLConfig eGLConfig) {
        return new Config(eGLConfig);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Context extends EGLBase.IContext<EGLContext> {
        private Context(EGLContext eGLContext) {
            super(eGLContext);
        }

        @Override // com.serenegiant.egl.EGLBase.IContext
        public long getNativeHandle() {
            if (this.eglContext != 0) {
                return BuildCheck.isLollipop() ? ((EGLContext) this.eglContext).getNativeHandle() : ((EGLContext) this.eglContext).getHandle();
            }
            return 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Config extends EGLBase.IConfig<EGLConfig> {
        private Config(EGLConfig eGLConfig) {
            super(eGLConfig);
        }
    }

    /* loaded from: classes2.dex */
    private static class EglSurface implements EGLBase.IEglSurface {
        private final EGLBase14 mEglBase;
        private EGLSurface mEglSurface;
        private final int mGLVersion;
        private final boolean mOwnSurface;
        private int viewPortHeight;
        private int viewPortWidth;
        private int viewPortX;
        private int viewPortY;

        private EglSurface(EGLBase14 eGLBase14, Object obj) throws IllegalArgumentException {
            this.mEglBase = eGLBase14;
            this.mGLVersion = eGLBase14.getGlVersion();
            if (GLUtils.isSupportedSurface(obj)) {
                this.mEglSurface = eGLBase14.createWindowSurface(obj);
                this.mOwnSurface = true;
                setViewPort(0, 0, getWidth(), getHeight());
                return;
            }
            throw new IllegalArgumentException("unsupported surface");
        }

        private EglSurface(EGLBase14 eGLBase14, int i, int i2) {
            this.mEglBase = eGLBase14;
            this.mGLVersion = eGLBase14.getGlVersion();
            if (i <= 0 || i2 <= 0) {
                this.mEglSurface = eGLBase14.createOffscreenSurface(1, 1);
            } else {
                this.mEglSurface = eGLBase14.createOffscreenSurface(i, i2);
            }
            this.mOwnSurface = true;
            setViewPort(0, 0, getWidth(), getHeight());
        }

        private EglSurface(EGLBase14 eGLBase14) {
            this(eGLBase14, 12377);
        }

        private EglSurface(EGLBase14 eGLBase14, int i) {
            this.mEglBase = eGLBase14;
            this.mGLVersion = eGLBase14.getGlVersion();
            this.mEglSurface = EGL14.eglGetCurrentSurface(i);
            this.mOwnSurface = false;
            setViewPort(0, 0, getWidth(), getHeight());
        }

        @Override // com.serenegiant.gl.ISurface
        public void release() {
            this.mEglBase.makeDefault();
            if (this.mOwnSurface) {
                this.mEglBase.destroySurface(this.mEglSurface);
            }
            this.mEglSurface = EGL14.EGL_NO_SURFACE;
        }

        @Override // com.serenegiant.gl.ISurface
        public void makeCurrent() {
            this.mEglBase.makeCurrent(this.mEglSurface);
            setViewPort(this.viewPortX, this.viewPortY, this.viewPortWidth, this.viewPortHeight);
        }

        @Override // com.serenegiant.gl.ISurface
        public void setViewPort(int i, int i2, int i3, int i4) {
            this.viewPortX = i;
            this.viewPortY = i2;
            this.viewPortWidth = i3;
            this.viewPortHeight = i4;
            if (this.mGLVersion >= 2) {
                GLES20.glViewport(i, i2, i3, i4);
            } else {
                GLES10.glViewport(i, i2, i3, i4);
            }
        }

        @Override // com.serenegiant.gl.ISurface
        public void swap() {
            this.mEglBase.swap(this.mEglSurface);
        }

        @Override // com.serenegiant.egl.EGLBase.IEglSurface
        public void swap(long j) {
            this.mEglBase.swap(this.mEglSurface, j);
        }

        public void setPresentationTime(long j) {
            EGLExt.eglPresentationTimeANDROID(this.mEglBase.mEglDisplay, this.mEglSurface, j);
        }

        @Override // com.serenegiant.gl.ISurface
        public boolean isValid() {
            return this.mEglSurface != EGL14.EGL_NO_SURFACE && this.mEglBase.getSurfaceWidth(this.mEglSurface) > 0 && this.mEglBase.getSurfaceHeight(this.mEglSurface) > 0;
        }

        @Override // com.serenegiant.gl.ISurface
        public int getWidth() {
            return this.mEglBase.getSurfaceWidth(this.mEglSurface);
        }

        @Override // com.serenegiant.gl.ISurface
        public int getHeight() {
            return this.mEglBase.getSurfaceHeight(this.mEglSurface);
        }

        public String toString() {
            return "EglSurface{mEglBase=" + this.mEglBase + ", mEglSurface=" + this.mEglSurface + ", mOwnSurface=" + this.mOwnSurface + ", viewPortX=" + this.viewPortX + ", viewPortY=" + this.viewPortY + ", viewPortWidth=" + this.viewPortWidth + ", viewPortHeight=" + this.viewPortHeight + '}';
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static EGLBase createFromCurrentImpl(int i, boolean z, int i2, boolean z2) {
        EGLContext eglGetCurrentContext = EGL14.eglGetCurrentContext();
        return new EGLBase14(i, (eglGetCurrentContext == null || EGL14.eglGetCurrentSurface(12377) == null) ? null : wrap(eglGetCurrentContext), z, i2, z2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean hasGLThreadImpl() {
        return (EGL14.eglGetCurrentContext() == null || EGL14.eglGetCurrentSurface(12377) == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static EGLBase.IContext<?> wrapCurrentContextImpl() {
        EGLContext eglGetCurrentContext = EGL14.eglGetCurrentContext();
        EGLSurface eglGetCurrentSurface = EGL14.eglGetCurrentSurface(12377);
        if (eglGetCurrentContext == null || eglGetCurrentSurface == null) {
            return null;
        }
        return wrapContext(eglGetCurrentContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EGLBase14(int i, Context context, boolean z, int i2, boolean z2) {
        this.mContext = EGL_NO_CONTEXT;
        this.mEglDisplay = EGL14.EGL_NO_DISPLAY;
        this.mEglConfig = null;
        this.mGlVersion = 2;
        this.mDefaultContext = EGL14.EGL_NO_CONTEXT;
        this.mSurfaceDimension = new int[2];
        init(i, context, z, i2, z2);
    }

    EGLBase14(int i, boolean z, int i2, boolean z2) {
        this.mContext = EGL_NO_CONTEXT;
        this.mEglDisplay = EGL14.EGL_NO_DISPLAY;
        this.mEglConfig = null;
        this.mGlVersion = 2;
        this.mDefaultContext = EGL14.EGL_NO_CONTEXT;
        this.mSurfaceDimension = new int[2];
        init(i, wrap(EGL14.eglGetCurrentContext()), z, i2, z2);
    }

    @Override // com.serenegiant.egl.EGLBase
    public void release() {
        if (this.mEglDisplay != EGL14.EGL_NO_DISPLAY) {
            destroyContext();
            EGL14.eglTerminate(this.mEglDisplay);
            EGL14.eglReleaseThread();
        }
        this.mEglDisplay = EGL14.EGL_NO_DISPLAY;
        this.mContext = EGL_NO_CONTEXT;
    }

    @Override // com.serenegiant.egl.EGLBase
    public EGLBase.IEglSurface createFromSurface(Object obj) {
        EglSurface eglSurface = new EglSurface(obj);
        eglSurface.makeCurrent();
        return eglSurface;
    }

    @Override // com.serenegiant.egl.EGLBase
    public EGLBase.IEglSurface createOffscreen(int i, int i2) {
        EglSurface eglSurface = new EglSurface(i, i2);
        eglSurface.makeCurrent();
        return eglSurface;
    }

    @Override // com.serenegiant.egl.EGLBase
    public EGLBase.IEglSurface wrapCurrent() {
        EglSurface eglSurface = new EglSurface();
        eglSurface.makeCurrent();
        return eglSurface;
    }

    @Override // com.serenegiant.egl.EGLBase
    public String queryString(int i) {
        return EGL14.eglQueryString(this.mEglDisplay, i);
    }

    @Override // com.serenegiant.egl.EGLBase
    public int getGlVersion() {
        return this.mGlVersion;
    }

    @Override // com.serenegiant.egl.EGLBase
    public boolean isValidContext() {
        return this.mContext.eglContext != EGL14.EGL_NO_CONTEXT;
    }

    @Override // com.serenegiant.egl.EGLBase
    public Context getContext() throws IllegalStateException {
        if (!isValidContext()) {
            throw new IllegalStateException();
        }
        return this.mContext;
    }

    @Override // com.serenegiant.egl.EGLBase
    public Config getConfig() {
        if (!isValidContext()) {
            throw new IllegalStateException();
        }
        return this.mEglConfig;
    }

    @Override // com.serenegiant.egl.EGLBase
    public void makeDefault() {
        if (EGL14.eglMakeCurrent(this.mEglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)) {
            return;
        }
        Log.w("TAG", "makeDefault" + EGL14.eglGetError());
    }

    @Override // com.serenegiant.egl.EGLBase
    public void sync() {
        EGL14.eglWaitGL();
        EGL14.eglWaitNative(12379);
    }

    @Override // com.serenegiant.egl.EGLBase
    public void waitGL() {
        EGL14.eglWaitGL();
    }

    @Override // com.serenegiant.egl.EGLBase
    public void waitNative() {
        EGL14.eglWaitNative(12379);
    }

    private void init(int i, Context context, boolean z, int i2, boolean z2) {
        EGLConfig config;
        if (this.mEglDisplay != EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("EGL already set up");
        }
        EGLDisplay eglGetDisplay = EGL14.eglGetDisplay(0);
        this.mEglDisplay = eglGetDisplay;
        if (eglGetDisplay == EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglGetDisplay failed");
        }
        int[] iArr = new int[2];
        if (!EGL14.eglInitialize(this.mEglDisplay, iArr, 0, iArr, 1)) {
            this.mEglDisplay = EGL14.EGL_NO_DISPLAY;
            throw new RuntimeException("eglInitialize failed");
        }
        if (context == null) {
            context = EGL_NO_CONTEXT;
        }
        if (i >= 3 && (config = getConfig(3, z, i2, z2)) != null) {
            EGLContext createContext = createContext(context, config, 3);
            if (EGL14.eglGetError() == 12288) {
                this.mEglConfig = wrap(config);
                this.mContext = wrap(createContext);
                this.mGlVersion = 3;
            }
        }
        if (i >= 2 && !isValidContext()) {
            EGLConfig config2 = getConfig(2, z, i2, z2);
            if (config2 == null) {
                throw new RuntimeException("chooseConfig failed");
            }
            try {
                EGLContext createContext2 = createContext(context, config2, 2);
                checkEglError("eglCreateContext");
                this.mEglConfig = wrap(config2);
                this.mContext = wrap(createContext2);
                this.mGlVersion = 2;
            } catch (Exception unused) {
                if (z2) {
                    EGLConfig config3 = getConfig(2, z, i2, false);
                    if (config3 == null) {
                        throw new RuntimeException("chooseConfig failed");
                    }
                    EGLContext createContext3 = createContext(context, config3, 2);
                    checkEglError("eglCreateContext");
                    this.mEglConfig = wrap(config3);
                    this.mContext = wrap(createContext3);
                    this.mGlVersion = 2;
                }
            }
        }
        if (!isValidContext()) {
            EGLConfig config4 = getConfig(1, z, i2, z2);
            if (config4 == null) {
                throw new RuntimeException("chooseConfig failed");
            }
            EGLContext createContext4 = createContext(context, config4, 1);
            checkEglError("eglCreateContext");
            this.mEglConfig = wrap(config4);
            this.mContext = wrap(createContext4);
            this.mGlVersion = 1;
        }
        int[] iArr2 = new int[1];
        EGL14.eglQueryContext(this.mEglDisplay, (EGLContext) this.mContext.eglContext, EGLConst.EGL_CONTEXT_CLIENT_VERSION, iArr2, 0);
        if (EGL14.eglGetError() == 12288) {
            Log.d(TAG, String.format("EGLContext created, client version %d(request %d) ", Integer.valueOf(iArr2[0]), Integer.valueOf(i)));
        }
        makeDefault();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean makeCurrent(EGLSurface eGLSurface) {
        if (eGLSurface == null || eGLSurface == EGL14.EGL_NO_SURFACE) {
            if (EGL14.eglGetError() == 12299) {
                Log.e(TAG, "makeCurrent:returned EGL_BAD_NATIVE_WINDOW.");
            }
            return false;
        } else if (EGL14.eglMakeCurrent(this.mEglDisplay, eGLSurface, eGLSurface, (EGLContext) this.mContext.eglContext)) {
            return true;
        } else {
            Log.w("TAG", "eglMakeCurrent" + EGL14.eglGetError());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int swap(EGLSurface eGLSurface) {
        if (EGL14.eglSwapBuffers(this.mEglDisplay, eGLSurface)) {
            return 12288;
        }
        return EGL14.eglGetError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int swap(EGLSurface eGLSurface, long j) {
        EGLExt.eglPresentationTimeANDROID(this.mEglDisplay, eGLSurface, j);
        if (EGL14.eglSwapBuffers(this.mEglDisplay, eGLSurface)) {
            return 12288;
        }
        return EGL14.eglGetError();
    }

    private EGLContext createContext(Context context, EGLConfig eGLConfig, int i) {
        return EGL14.eglCreateContext(this.mEglDisplay, eGLConfig, (EGLContext) context.eglContext, new int[]{EGLConst.EGL_CONTEXT_CLIENT_VERSION, i, 12344}, 0);
    }

    private void destroyContext() {
        EGLContext eGLContext = (EGLContext) this.mContext.eglContext;
        this.mContext = EGL_NO_CONTEXT;
        if (eGLContext != EGL14.EGL_NO_CONTEXT && !EGL14.eglDestroyContext(this.mEglDisplay, eGLContext)) {
            Log.e("destroyContext", "display:" + this.mEglDisplay + " context: " + eGLContext);
            Log.e(TAG, "eglDestroyContext:" + EGL14.eglGetError());
        }
        if (this.mDefaultContext != EGL14.EGL_NO_CONTEXT) {
            if (!EGL14.eglDestroyContext(this.mEglDisplay, this.mDefaultContext)) {
                Log.e("destroyContext", "display:" + this.mEglDisplay + " context: " + this.mDefaultContext);
                Log.e(TAG, "eglDestroyContext:" + EGL14.eglGetError());
            }
            this.mDefaultContext = EGL14.EGL_NO_CONTEXT;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getSurfaceWidth(EGLSurface eGLSurface) {
        if (!EGL14.eglQuerySurface(this.mEglDisplay, eGLSurface, 12375, this.mSurfaceDimension, 0)) {
            this.mSurfaceDimension[0] = 0;
        }
        return this.mSurfaceDimension[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getSurfaceHeight(EGLSurface eGLSurface) {
        if (!EGL14.eglQuerySurface(this.mEglDisplay, eGLSurface, 12374, this.mSurfaceDimension, 1)) {
            this.mSurfaceDimension[1] = 0;
        }
        return this.mSurfaceDimension[1];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final EGLSurface createWindowSurface(Object obj) throws IllegalArgumentException {
        try {
            EGLSurface eglCreateWindowSurface = EGL14.eglCreateWindowSurface(this.mEglDisplay, (EGLConfig) this.mEglConfig.eglConfig, obj, new int[]{12344}, 0);
            if (eglCreateWindowSurface != null && eglCreateWindowSurface != EGL14.EGL_NO_SURFACE) {
                makeCurrent(eglCreateWindowSurface);
                return eglCreateWindowSurface;
            }
            int eglGetError = EGL14.eglGetError();
            if (eglGetError == 12299) {
                Log.e(TAG, "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
            }
            throw new RuntimeException("createWindowSurface failed error=" + eglGetError);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e2) {
            Log.e(TAG, "eglCreateWindowSurface", e2);
            throw new IllegalArgumentException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final EGLSurface createOffscreenSurface(int i, int i2) throws IllegalArgumentException {
        try {
            EGLSurface eglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(this.mEglDisplay, (EGLConfig) this.mEglConfig.eglConfig, new int[]{12375, i, 12374, i2, 12344}, 0);
            checkEglError("eglCreatePbufferSurface");
            if (eglCreatePbufferSurface == null || eglCreatePbufferSurface == EGL14.EGL_NO_SURFACE) {
                throw new RuntimeException("createOffscreenSurface failed error=" + EGL14.eglGetError());
            }
            return eglCreatePbufferSurface;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e2) {
            Log.e(TAG, "createOffscreenSurface", e2);
            throw new IllegalArgumentException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroySurface(EGLSurface eGLSurface) {
        if (eGLSurface != EGL14.EGL_NO_SURFACE) {
            EGL14.eglMakeCurrent(this.mEglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(this.mEglDisplay, eGLSurface);
        }
    }

    private void checkEglError(String str) {
        int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }

    private EGLConfig getConfig(int i, boolean z, int i2, boolean z2) {
        int i3;
        int[] iArr = {12352, i >= 3 ? 68 : 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344, 12344, 12344, 12344, 12344, 12344, 12344};
        if (i2 > 0) {
            iArr[10] = 12326;
            iArr[11] = i2;
            i3 = 12;
        } else {
            i3 = 10;
        }
        if (z) {
            int i4 = i3 + 1;
            iArr[i3] = 12325;
            i3 = i4 + 1;
            iArr[i4] = 16;
        }
        if (z2 && BuildCheck.isAndroid4_3()) {
            int i5 = i3 + 1;
            iArr[i3] = 12610;
            i3 = i5 + 1;
            iArr[i5] = 1;
        }
        int i6 = i3;
        for (int i7 = 16; i7 >= i6; i7--) {
            iArr[i7] = 12344;
        }
        EGLConfig internalGetConfig = internalGetConfig(iArr);
        if (internalGetConfig == null && i == 2 && z2) {
            int i8 = 10;
            while (true) {
                if (i8 >= 16) {
                    break;
                } else if (iArr[i8] == 12610) {
                    while (i8 < 17) {
                        iArr[i8] = 12344;
                        i8++;
                    }
                } else {
                    i8 += 2;
                }
            }
            internalGetConfig = internalGetConfig(iArr);
        }
        if (internalGetConfig == null) {
            Log.w(TAG, "try to fallback to RGB565");
            iArr[3] = 5;
            iArr[5] = 6;
            iArr[7] = 5;
            iArr[9] = 0;
            return internalGetConfig(iArr);
        }
        return internalGetConfig;
    }

    private EGLConfig internalGetConfig(int[] iArr) {
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (EGL14.eglChooseConfig(this.mEglDisplay, iArr, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
            return eGLConfigArr[0];
        }
        return null;
    }
}
