package com.serenegiant.glutils;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.math.Fraction;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public interface IRendererHolder extends IMirror {
    public static final int DEFAULT_CAPTURE_COMPRESSION = 80;
    public static final int OUTPUT_FORMAT_JPEG = 0;
    public static final int OUTPUT_FORMAT_PNG = 1;
    public static final int OUTPUT_FORMAT_WEBP = 2;

    /* loaded from: classes2.dex */
    public interface OnCapturedListener {
        void onCaptured(IRendererHolder iRendererHolder, boolean z);
    }

    /* loaded from: classes2.dex */
    public interface RenderHolderCallback {
        void onCreate(Surface surface);

        void onDestroy();

        void onFrameAvailable();
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface StillCaptureFormat {
    }

    void addSurface(int i, Object obj, boolean z) throws IllegalStateException, IllegalArgumentException;

    void addSurface(int i, Object obj, boolean z, Fraction fraction) throws IllegalStateException, IllegalArgumentException;

    void captureStill(OutputStream outputStream, int i, int i2, OnCapturedListener onCapturedListener) throws IllegalStateException;

    void captureStill(String str, int i, OnCapturedListener onCapturedListener) throws FileNotFoundException, IllegalStateException;

    void captureStill(String str, OnCapturedListener onCapturedListener) throws FileNotFoundException, IllegalStateException;

    void clearSurface(int i, int i2);

    void clearSurfaceAll(int i);

    EGLBase.IContext<?> getContext();

    int getCount();

    Surface getSurface();

    SurfaceTexture getSurfaceTexture();

    boolean isEnabled(int i);

    boolean isRunning();

    void queueEvent(Runnable runnable);

    void release();

    void removeSurface(int i);

    void removeSurfaceAll();

    void requestFrame();

    void reset();

    void resize(int i, int i2) throws IllegalStateException;

    void setEnabled(int i, boolean z);

    void setMvpMatrix(int i, int i2, float[] fArr);
}
