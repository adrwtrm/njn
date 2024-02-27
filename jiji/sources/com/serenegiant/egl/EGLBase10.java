package com.serenegiant.egl;

import android.opengl.GLES10;
import android.opengl.GLES20;
import android.util.Log;
import android.view.Surface;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.WrappedSurfaceHolder;
import com.serenegiant.system.BuildCheck;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class EGLBase10 extends EGLBase {
    private static final boolean DEBUG = false;
    private static final Context EGL_NO_CONTEXT = wrap(EGL10.EGL_NO_CONTEXT);
    private static final String TAG = "EGLBase10";
    private Context mContext = EGL_NO_CONTEXT;
    private EGL10 mEgl = null;
    private EGLDisplay mEglDisplay = EGL10.EGL_NO_DISPLAY;
    private Config mEglConfig = null;
    private int mGlVersion = 2;

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
        @Override // com.serenegiant.egl.EGLBase.IContext
        public long getNativeHandle() {
            return 0L;
        }

        private Context(EGLContext eGLContext) {
            super(eGLContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Config extends EGLBase.IConfig<EGLConfig> {
        private Config(EGLConfig eGLConfig) {
            super(eGLConfig);
        }
    }

    /* loaded from: classes2.dex */
    private static class EglSurface implements EGLBase.IEglSurface {
        private final EGLBase10 mEglBase;
        private EGLSurface mEglSurface;
        private final int mGLVersion;
        private final boolean mOwnSurface;
        private int viewPortHeight;
        private int viewPortWidth;
        private int viewPortX;
        private int viewPortY;

        public void setPresentationTime(long j) {
        }

        private EglSurface(EGLBase10 eGLBase10, Object obj) throws IllegalArgumentException {
            this.mEglBase = eGLBase10;
            this.mGLVersion = eGLBase10.getGlVersion();
            if ((obj instanceof Surface) && !BuildCheck.isAndroid4_2()) {
                obj = new WrappedSurfaceHolder((Surface) obj);
            }
            if (GLUtils.isSupportedSurface(obj)) {
                this.mEglSurface = eGLBase10.createWindowSurface(obj);
                this.mOwnSurface = true;
                setViewPort(0, 0, getWidth(), getHeight());
                return;
            }
            throw new IllegalArgumentException("unsupported surface");
        }

        private EglSurface(EGLBase10 eGLBase10, int i, int i2) {
            this.mEglBase = eGLBase10;
            this.mGLVersion = eGLBase10.getGlVersion();
            if (i <= 0 || i2 <= 0) {
                this.mEglSurface = eGLBase10.createOffscreenSurface(1, 1);
            } else {
                this.mEglSurface = eGLBase10.createOffscreenSurface(i, i2);
            }
            this.mOwnSurface = true;
            setViewPort(0, 0, getWidth(), getHeight());
        }

        private EglSurface(EGLBase10 eGLBase10) {
            this(eGLBase10, 12377);
        }

        private EglSurface(EGLBase10 eGLBase10, int i) {
            this.mEglBase = eGLBase10;
            this.mGLVersion = eGLBase10.getGlVersion();
            this.mEglSurface = eGLBase10.mEgl.eglGetCurrentSurface(i);
            this.mOwnSurface = false;
            setViewPort(0, 0, getWidth(), getHeight());
        }

        @Override // com.serenegiant.gl.ISurface
        public void release() {
            this.mEglBase.makeDefault();
            if (this.mOwnSurface) {
                this.mEglBase.destroySurface(this.mEglSurface);
            }
            this.mEglSurface = EGL10.EGL_NO_SURFACE;
        }

        @Override // com.serenegiant.gl.ISurface
        public boolean isValid() {
            return this.mEglSurface != EGL10.EGL_NO_SURFACE && this.mEglBase.getSurfaceWidth(this.mEglSurface) > 0 && this.mEglBase.getSurfaceHeight(this.mEglSurface) > 0;
        }

        @Override // com.serenegiant.gl.ISurface
        public int getWidth() {
            return this.mEglBase.getSurfaceWidth(this.mEglSurface);
        }

        @Override // com.serenegiant.gl.ISurface
        public int getHeight() {
            return this.mEglBase.getSurfaceHeight(this.mEglSurface);
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

        public String toString() {
            return "EglSurface{mEglBase=" + this.mEglBase + ", mEglSurface=" + this.mEglSurface + ", mOwnSurface=" + this.mOwnSurface + ", viewPortX=" + this.viewPortX + ", viewPortY=" + this.viewPortY + ", viewPortWidth=" + this.viewPortWidth + ", viewPortHeight=" + this.viewPortHeight + '}';
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static EGLBase createFromCurrentImpl(int i, boolean z, int i2, boolean z2) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLContext eglGetCurrentContext = egl10.eglGetCurrentContext();
        return new EGLBase10(i, (eglGetCurrentContext == null || egl10.eglGetCurrentSurface(12377) == null) ? null : wrap(eglGetCurrentContext), z, i2, z2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean hasGLThreadImpl() {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        return (egl10.eglGetCurrentContext() == null || egl10.eglGetCurrentSurface(12377) == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static EGLBase.IContext<?> wrapCurrentContextImpl() {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLContext eglGetCurrentContext = egl10.eglGetCurrentContext();
        EGLSurface eglGetCurrentSurface = egl10.eglGetCurrentSurface(12377);
        if (eglGetCurrentContext == null || eglGetCurrentSurface == null) {
            return null;
        }
        return wrapContext(eglGetCurrentContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EGLBase10(int i, Context context, boolean z, int i2, boolean z2) throws IllegalArgumentException {
        init(i, context, z, i2, z2);
    }

    EGLBase10(int i, boolean z, int i2, boolean z2) throws IllegalArgumentException {
        init(i, wrap(((EGL10) EGLContext.getEGL()).eglGetCurrentContext()), z, i2, z2);
    }

    @Override // com.serenegiant.egl.EGLBase
    public void release() {
        destroyContext();
        this.mContext = EGL_NO_CONTEXT;
        EGL10 egl10 = this.mEgl;
        if (egl10 == null) {
            return;
        }
        egl10.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        this.mEgl.eglTerminate(this.mEglDisplay);
        this.mEglDisplay = EGL10.EGL_NO_DISPLAY;
        this.mEglConfig = null;
        this.mEgl = null;
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
    public boolean isValidContext() {
        return this.mContext.eglContext != EGL10.EGL_NO_CONTEXT;
    }

    @Override // com.serenegiant.egl.EGLBase
    public Context getContext() throws IllegalStateException {
        if (!isValidContext()) {
            throw new IllegalStateException();
        }
        return this.mContext;
    }

    @Override // com.serenegiant.egl.EGLBase
    public Config getConfig() throws IllegalStateException {
        if (!isValidContext()) {
            throw new IllegalStateException();
        }
        return this.mEglConfig;
    }

    @Override // com.serenegiant.egl.EGLBase
    public void makeDefault() {
        if (this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT)) {
            return;
        }
        Log.w(TAG, "makeDefault:eglMakeCurrent:err=" + this.mEgl.eglGetError());
    }

    @Override // com.serenegiant.egl.EGLBase
    public void sync() {
        this.mEgl.eglWaitGL();
        this.mEgl.eglWaitNative(12379, null);
    }

    @Override // com.serenegiant.egl.EGLBase
    public void waitGL() {
        this.mEgl.eglWaitGL();
    }

    @Override // com.serenegiant.egl.EGLBase
    public void waitNative() {
        this.mEgl.eglWaitNative(12379, null);
    }

    @Override // com.serenegiant.egl.EGLBase
    public String queryString(int i) {
        return this.mEgl.eglQueryString(this.mEglDisplay, i);
    }

    @Override // com.serenegiant.egl.EGLBase
    public int getGlVersion() {
        return this.mGlVersion;
    }

    private final void init(int i, Context context, boolean z, int i2, boolean z2) throws IllegalArgumentException {
        EGLConfig config;
        if (context == null) {
            context = EGL_NO_CONTEXT;
        }
        if (this.mEgl == null) {
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            this.mEgl = egl10;
            EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            this.mEglDisplay = eglGetDisplay;
            if (eglGetDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new IllegalArgumentException("eglGetDisplay failed");
            }
            if (!this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                this.mEglDisplay = EGL10.EGL_NO_DISPLAY;
                throw new IllegalArgumentException("eglInitialize failed");
            }
        }
        if (i >= 3 && (config = getConfig(3, z, i2, z2)) != null) {
            EGLContext createContext = createContext(context, config, 3);
            if (this.mEgl.eglGetError() == 12288) {
                this.mEglConfig = wrap(config);
                this.mContext = wrap(createContext);
                this.mGlVersion = 3;
            }
        }
        if (i >= 2 && !isValidContext()) {
            EGLConfig config2 = getConfig(2, z, i2, z2);
            if (config2 == null) {
                throw new IllegalArgumentException("chooseConfig failed");
            }
            try {
                EGLContext createContext2 = createContext(context, config2, 2);
                checkEglError("eglCreateContext");
                this.mEglConfig = wrap(config2);
                this.mContext = wrap(createContext2);
                this.mGlVersion = 2;
            } catch (Exception e) {
                if (z2) {
                    EGLConfig config3 = getConfig(2, z, i2, false);
                    if (config3 == null) {
                        throw new IllegalArgumentException("chooseConfig failed");
                    }
                    EGLContext createContext3 = createContext(context, config3, 2);
                    checkEglError("eglCreateContext");
                    this.mEglConfig = wrap(config3);
                    this.mContext = wrap(createContext3);
                    this.mGlVersion = 2;
                } else {
                    throw e;
                }
            }
        }
        if (!isValidContext()) {
            EGLConfig config4 = getConfig(1, z, i2, z2);
            if (config4 == null) {
                throw new IllegalArgumentException("chooseConfig failed");
            }
            EGLContext createContext4 = createContext(context, config4, 1);
            checkEglError("eglCreateContext");
            this.mEglConfig = wrap(config4);
            this.mContext = wrap(createContext4);
            this.mGlVersion = 1;
        }
        int[] iArr = new int[1];
        this.mEgl.eglQueryContext(this.mEglDisplay, (EGLContext) this.mContext.eglContext, EGLConst.EGL_CONTEXT_CLIENT_VERSION, iArr);
        if (this.mEgl.eglGetError() == 12288) {
            Log.d(TAG, String.format("EGLContext created, client version %d(request %d) ", Integer.valueOf(iArr[0]), Integer.valueOf(i)));
        }
        makeDefault();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean makeCurrent(EGLSurface eGLSurface) {
        if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
            if (this.mEgl.eglGetError() == 12299) {
                Log.e(TAG, "makeCurrent:EGL_BAD_NATIVE_WINDOW");
            }
            return false;
        } else if (this.mEgl.eglMakeCurrent(this.mEglDisplay, eGLSurface, eGLSurface, (EGLContext) this.mContext.eglContext)) {
            return true;
        } else {
            Log.w(TAG, "eglMakeCurrent" + this.mEgl.eglGetError());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int swap(EGLSurface eGLSurface) {
        if (this.mEgl.eglSwapBuffers(this.mEglDisplay, eGLSurface)) {
            return 12288;
        }
        return this.mEgl.eglGetError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int swap(EGLSurface eGLSurface, long j) {
        if (this.mEgl.eglSwapBuffers(this.mEglDisplay, eGLSurface)) {
            return 12288;
        }
        return this.mEgl.eglGetError();
    }

    private final EGLContext createContext(Context context, EGLConfig eGLConfig, int i) {
        return this.mEgl.eglCreateContext(this.mEglDisplay, eGLConfig, (EGLContext) context.eglContext, new int[]{EGLConst.EGL_CONTEXT_CLIENT_VERSION, i, 12344});
    }

    private final void destroyContext() {
        EGLContext eGLContext = (EGLContext) this.mContext.eglContext;
        this.mContext = EGL_NO_CONTEXT;
        if (eGLContext == EGL10.EGL_NO_CONTEXT || this.mEgl.eglDestroyContext(this.mEglDisplay, eGLContext)) {
            return;
        }
        Log.e("destroyContext", "display:" + this.mEglDisplay + " context: " + eGLContext);
        Log.e(TAG, "eglDestroyContext:" + this.mEgl.eglGetError());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getSurfaceWidth(EGLSurface eGLSurface) {
        int[] iArr = new int[1];
        if (!this.mEgl.eglQuerySurface(this.mEglDisplay, eGLSurface, 12375, iArr)) {
            iArr[0] = 0;
        }
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getSurfaceHeight(EGLSurface eGLSurface) {
        int[] iArr = new int[1];
        if (!this.mEgl.eglQuerySurface(this.mEglDisplay, eGLSurface, 12374, iArr)) {
            iArr[0] = 0;
        }
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final EGLSurface createWindowSurface(Object obj) throws IllegalArgumentException {
        try {
            EGLSurface eglCreateWindowSurface = this.mEgl.eglCreateWindowSurface(this.mEglDisplay, (EGLConfig) this.mEglConfig.eglConfig, obj, new int[]{12344});
            if (eglCreateWindowSurface != null && eglCreateWindowSurface != EGL10.EGL_NO_SURFACE) {
                makeCurrent(eglCreateWindowSurface);
                return eglCreateWindowSurface;
            }
            int eglGetError = this.mEgl.eglGetError();
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
        int[] iArr = {12375, i, 12374, i2, 12344};
        this.mEgl.eglWaitGL();
        try {
            EGLSurface eglCreatePbufferSurface = this.mEgl.eglCreatePbufferSurface(this.mEglDisplay, (EGLConfig) this.mEglConfig.eglConfig, iArr);
            checkEglError("eglCreatePbufferSurface");
            if (eglCreatePbufferSurface == null || eglCreatePbufferSurface == EGL10.EGL_NO_SURFACE) {
                throw new RuntimeException("createOffscreenSurface failed error=" + this.mEgl.eglGetError());
            }
            return eglCreatePbufferSurface;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (RuntimeException e2) {
            Log.e(TAG, "createOffscreenSurface", e2);
            throw new IllegalArgumentException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void destroySurface(EGLSurface eGLSurface) {
        if (eGLSurface != EGL10.EGL_NO_SURFACE) {
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.mEgl.eglDestroySurface(this.mEglDisplay, eGLSurface);
        }
    }

    private final void checkEglError(String str) {
        int eglGetError = this.mEgl.eglGetError();
        if (eglGetError != 12288) {
            throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }

    private final EGLConfig getConfig(int i, boolean z, int i2, boolean z2) {
        int i3 = 10;
        int i4 = 12;
        int[] iArr = {12352, i >= 3 ? 68 : 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344, 12344, 12344, 12344, 12344, 12344, 12344};
        if (i2 > 0) {
            iArr[10] = 12326;
            iArr[11] = 8;
        } else {
            i4 = 10;
        }
        if (z) {
            int i5 = i4 + 1;
            iArr[i4] = 12325;
            i4 = i5 + 1;
            iArr[i5] = 16;
        }
        if (z2 && BuildCheck.isAndroid4_3()) {
            int i6 = i4 + 1;
            iArr[i4] = 12610;
            i4 = i6 + 1;
            iArr[i6] = 1;
        }
        int i7 = i4;
        for (int i8 = 16; i8 >= i7; i8--) {
            iArr[i8] = 12344;
        }
        EGLConfig internalGetConfig = internalGetConfig(iArr);
        if (internalGetConfig == null && i == 2 && z2) {
            while (true) {
                if (i3 >= 16) {
                    break;
                } else if (iArr[i3] == 12610) {
                    while (i3 < 17) {
                        iArr[i3] = 12344;
                        i3++;
                    }
                } else {
                    i3 += 2;
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
        if (this.mEgl.eglChooseConfig(this.mEglDisplay, iArr, eGLConfigArr, 1, new int[1])) {
            return eGLConfigArr[0];
        }
        return null;
    }
}
