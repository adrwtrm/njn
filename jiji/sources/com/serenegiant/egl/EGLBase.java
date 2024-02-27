package com.serenegiant.egl;

import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import com.serenegiant.egl.EGLBase10;
import com.serenegiant.egl.EGLBase14;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.ISurface;
import com.serenegiant.system.BuildCheck;

/* loaded from: classes2.dex */
public abstract class EGLBase implements EGLConst {
    public static final int EGL_CONFIG_RGB565 = 1;
    public static final int EGL_CONFIG_RGBA = 0;

    /* loaded from: classes2.dex */
    public interface IEglSurface extends ISurface {
        void swap(long j);
    }

    public abstract IEglSurface createFromSurface(Object obj);

    public abstract IEglSurface createOffscreen(int i, int i2);

    public abstract IConfig<?> getConfig();

    public abstract IContext<?> getContext() throws IllegalStateException;

    public abstract int getGlVersion();

    public abstract boolean isValidContext();

    public abstract void makeDefault();

    public abstract String queryString(int i);

    public abstract void release();

    public abstract void sync();

    public abstract void waitGL();

    public abstract void waitNative();

    public abstract IEglSurface wrapCurrent();

    public static IContext<?> wrapCurrentContext() {
        if (isEGL14Supported()) {
            return EGLBase14.wrapCurrentContextImpl();
        }
        return EGLBase10.wrapCurrentContextImpl();
    }

    public static IContext<?> wrapContext(Object obj) {
        if (obj instanceof IContext) {
            return (IContext) obj;
        }
        if (obj instanceof EGLContext) {
            return EGLBase14.wrap((EGLContext) obj);
        }
        if (obj instanceof javax.microedition.khronos.egl.EGLContext) {
            return EGLBase10.wrap((javax.microedition.khronos.egl.EGLContext) obj);
        }
        if (obj == null) {
            return null;
        }
        throw new IllegalArgumentException("Unexpected shared context," + obj);
    }

    public static IConfig<?> wrapConfig(Object obj) {
        if (obj instanceof EGLConfig) {
            return EGLBase14.wrap((EGLConfig) obj);
        }
        if (obj instanceof javax.microedition.khronos.egl.EGLConfig) {
            return EGLBase10.wrap((javax.microedition.khronos.egl.EGLConfig) obj);
        }
        throw new IllegalArgumentException("Unexpected egl config," + obj);
    }

    public static EGLBase createFrom(IContext<?> iContext, boolean z, boolean z2) {
        return createFrom(GLUtils.getSupportedGLVersion(), iContext, z, 0, z2);
    }

    public static EGLBase createFrom(IContext<?> iContext, boolean z, int i, boolean z2) {
        return createFrom(GLUtils.getSupportedGLVersion(), iContext, z, i, z2);
    }

    public static EGLBase createFrom(int i, IContext<?> iContext, boolean z, int i2, boolean z2) {
        if (isEGL14Supported() && (iContext == null || (iContext instanceof EGLBase14.Context))) {
            return new EGLBase14(i, (EGLBase14.Context) iContext, z, i2, z2);
        }
        return new EGLBase10(i, (EGLBase10.Context) iContext, z, i2, z2);
    }

    public static EGLBase createFrom(int i, Object obj, boolean z, int i2, boolean z2) {
        return createFrom(i, wrapContext(obj), z, i2, z2);
    }

    public static EGLBase createFromCurrent(int i, boolean z, int i2, boolean z2) {
        if (isEGL14Supported()) {
            return EGLBase14.createFromCurrentImpl(i, z, i2, z2);
        }
        return EGLBase10.createFromCurrentImpl(i, z, i2, z2);
    }

    public static boolean hasGLThread() {
        if (isEGL14Supported()) {
            return EGLBase14.hasGLThreadImpl();
        }
        return EGLBase10.hasGLThreadImpl();
    }

    /* loaded from: classes2.dex */
    public static abstract class IContext<T> {
        public final T eglContext;

        public abstract long getNativeHandle();

        /* JADX INFO: Access modifiers changed from: protected */
        public IContext(T t) {
            this.eglContext = t;
        }

        public T getEGLContext() {
            return this.eglContext;
        }

        public String toString() {
            return "Context{eglContext=" + this.eglContext + '}';
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class IConfig<T> {
        public final T eglConfig;

        /* JADX INFO: Access modifiers changed from: protected */
        public IConfig(T t) {
            this.eglConfig = t;
        }

        public T getEGLConfig() {
            return this.eglConfig;
        }

        public String toString() {
            return "Config{eglConfig=" + this.eglConfig + '}';
        }
    }

    public static boolean isEGL14Supported() {
        return BuildCheck.isAPI21();
    }

    public boolean isGLES3() {
        return getGlVersion() >= 3;
    }

    public boolean isGLES2() {
        return getGlVersion() >= 2;
    }
}
