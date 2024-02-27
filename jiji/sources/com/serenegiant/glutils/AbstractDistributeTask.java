package com.serenegiant.glutils;

import android.opengl.GLES20;
import android.util.Log;
import android.util.SparseArray;
import com.serenegiant.camera.CameraConst;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.GLContext;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.math.Fraction;
import com.serenegiant.utils.ThreadUtils;

/* loaded from: classes2.dex */
public abstract class AbstractDistributeTask implements IMirror {
    private static final boolean DEBUG = false;
    private static final int REQUEST_ADD_SURFACE = 3;
    private static final int REQUEST_CLEAR = 8;
    private static final int REQUEST_CLEAR_ALL = 9;
    private static final int REQUEST_DRAW = 1;
    private static final int REQUEST_MIRROR = 6;
    private static final int REQUEST_RECREATE_MASTER_SURFACE = 5;
    private static final int REQUEST_REMOVE_SURFACE = 4;
    private static final int REQUEST_REMOVE_SURFACE_ALL = 12;
    private static final int REQUEST_ROTATE = 7;
    private static final int REQUEST_SET_MVP = 10;
    private static final int REQUEST_UPDATE_SIZE = 2;
    private static final String TAG = "AbstractDistributeTask";
    private GLDrawer2D mDrawer;
    private final GLDrawer2D.DrawerFactory mDrawerFactory;
    private volatile boolean mHasNewFrame;
    private volatile boolean mIsFirstFrameRendered;
    private volatile boolean mReleased;
    private int mVideoHeight;
    private int mVideoWidth;
    private final Object mSync = new Object();
    private final SparseArray<RendererTarget> mTargets = new SparseArray<>();
    private int mMirror = 0;
    private int mRotation = 0;

    public abstract void callOnFrameAvailable();

    public abstract EGLBase.IContext<?> getContext();

    public abstract EGLBase getEgl();

    public abstract GLContext getGLContext();

    public abstract int getGlVersion();

    protected boolean handleOnError(Exception exc) {
        return false;
    }

    protected abstract void handleReCreateInputSurface();

    protected abstract void handleReleaseInputSurface();

    protected abstract void handleUpdateTexture();

    public abstract boolean isFinished();

    public abstract boolean isGLES3();

    public abstract boolean isMasterSurfaceValid();

    public abstract boolean isOES3Supported();

    public abstract boolean isRunning();

    public abstract void makeCurrent();

    public abstract void notifyParent(boolean z);

    public abstract boolean offer(int i);

    public abstract boolean offer(int i, int i2);

    public abstract boolean offer(int i, int i2, int i3);

    public abstract boolean offer(int i, int i2, int i3, Object obj);

    public abstract boolean offer(int i, Object obj);

    public abstract void removeRequest(int i);

    public abstract void start(String str);

    public abstract boolean waitReady();

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractDistributeTask(int i, int i2, GLDrawer2D.DrawerFactory drawerFactory) {
        this.mVideoWidth = i <= 0 ? CameraConst.DEFAULT_WIDTH : i;
        this.mVideoHeight = i2 <= 0 ? CameraConst.DEFAULT_HEIGHT : i2;
        this.mDrawerFactory = drawerFactory == null ? GLDrawer2D.DEFAULT_FACTORY : drawerFactory;
        this.mReleased = false;
    }

    public synchronized void release() {
        if (!this.mReleased) {
            this.mReleased = true;
        }
    }

    public boolean isFirstFrameRendered() {
        return this.mIsFirstFrameRendered;
    }

    public void requestFrame(boolean z, int i, float[] fArr) {
        this.mIsFirstFrameRendered = true;
        this.mHasNewFrame = true;
        offer(1, i, z ? 1 : 0, fArr);
    }

    public void requestRecreateMasterSurface() {
        offer(5);
    }

    public void addSurface(int i, Object obj) throws IllegalStateException, IllegalArgumentException {
        addSurface(i, obj, null);
    }

    public void addSurface(int i, Object obj, Fraction fraction) throws IllegalStateException, IllegalArgumentException {
        RendererTarget rendererTarget;
        checkFinished();
        if (!GLUtils.isSupportedSurface(obj)) {
            throw new IllegalArgumentException("Surface should be one of Surface, SurfaceTexture or SurfaceHolder");
        }
        synchronized (this.mTargets) {
            rendererTarget = this.mTargets.get(i);
        }
        if (rendererTarget == null) {
            TargetSurface targetSurface = new TargetSurface(i, obj, fraction);
            while (isRunning() && !isFinished() && !offer(3, targetSurface)) {
                synchronized (this.mSync) {
                    try {
                        this.mSync.wait(5L);
                    } catch (InterruptedException unused) {
                        return;
                    } finally {
                    }
                }
            }
        }
    }

    public void removeSurface(int i) {
        while (isRunning()) {
            synchronized (this.mSync) {
                if (offer(4, i)) {
                    try {
                        this.mSync.wait();
                    } catch (InterruptedException unused) {
                    }
                    return;
                } else {
                    try {
                        this.mSync.wait(5L);
                    } catch (InterruptedException unused2) {
                        return;
                    }
                }
            }
        }
    }

    public void removeSurfaceAll() {
        while (isRunning() && !isFinished()) {
            synchronized (this.mSync) {
                if (offer(12)) {
                    try {
                        this.mSync.wait();
                    } catch (InterruptedException unused) {
                    }
                    return;
                } else {
                    try {
                        this.mSync.wait(5L);
                    } catch (InterruptedException unused2) {
                        return;
                    }
                }
            }
        }
    }

    public void clearSurface(int i, int i2) throws IllegalStateException {
        checkFinished();
        offer(8, i, i2);
    }

    public void clearSurfaceAll(int i) throws IllegalStateException {
        checkFinished();
        offer(9, i);
    }

    public void setMvpMatrix(int i, int i2, float[] fArr) throws IllegalStateException, IllegalArgumentException {
        checkFinished();
        if (fArr.length >= i2 + 16) {
            offer(10, i, i2, fArr);
            return;
        }
        throw new IllegalArgumentException("matrix is too small, should be longer than offset + 16");
    }

    public boolean isEnabled(int i) {
        boolean z;
        synchronized (this.mTargets) {
            RendererTarget rendererTarget = this.mTargets.get(i);
            z = rendererTarget != null && rendererTarget.isEnabled();
        }
        return z;
    }

    public void setEnabled(int i, boolean z) {
        synchronized (this.mTargets) {
            RendererTarget rendererTarget = this.mTargets.get(i);
            if (rendererTarget != null) {
                rendererTarget.setEnabled(z);
            }
        }
    }

    public int getCount() {
        int size;
        synchronized (this.mTargets) {
            size = this.mTargets.size();
        }
        return size;
    }

    public void resize(int i, int i2) throws IllegalStateException {
        checkFinished();
        if (i <= 0 || i2 <= 0) {
            return;
        }
        if (this.mVideoWidth == i && this.mVideoHeight == i2) {
            return;
        }
        offer(2, i, i2);
    }

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(int i) throws IllegalStateException {
        checkFinished();
        if (this.mMirror != i) {
            offer(6, i);
        }
    }

    @Override // com.serenegiant.glutils.IMirror
    public int getMirror() {
        return this.mMirror;
    }

    public int width() {
        return this.mVideoWidth;
    }

    public int height() {
        return this.mVideoHeight;
    }

    public void rotation(int i) {
        checkFinished();
        if (this.mRotation != i) {
            offer(7, i);
        }
    }

    public int rotation() {
        return this.mRotation;
    }

    public GLDrawer2D getDrawer() {
        GLDrawer2D gLDrawer2D;
        synchronized (this.mSync) {
            gLDrawer2D = this.mDrawer;
        }
        return gLDrawer2D;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void handleOnStart() {
        internalOnStart();
        notifyParent(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void internalOnStart() {
        synchronized (this.mSync) {
            this.mDrawer = this.mDrawerFactory.create(isGLES3(), true);
        }
        handleReCreateInputSurface();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void handleOnStop() {
        notifyParent(false);
        makeCurrent();
        internalOnStop();
        handleRemoveAll();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void internalOnStop() {
        handleReleaseInputSurface();
        handleRemoveAll();
        GLDrawer2D gLDrawer2D = this.mDrawer;
        if (gLDrawer2D != null) {
            gLDrawer2D.release();
            this.mDrawer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object handleRequest(int i, int i2, int i3, Object obj) {
        switch (i) {
            case 1:
                handleDraw(i3 != 0, i2, (float[]) obj);
                return null;
            case 2:
                handleResize(i2, i3);
                return null;
            case 3:
                if (obj instanceof TargetSurface) {
                    handleAddSurface((TargetSurface) obj);
                    return null;
                }
                return null;
            case 4:
                handleRemoveSurface(i2);
                return null;
            case 5:
                handleReCreateInputSurface();
                return null;
            case 6:
                handleMirror(i2);
                return null;
            case 7:
                handleRotate(i2, i3);
                return null;
            case 8:
                handleClear(i2, i3);
                return null;
            case 9:
                handleClearAll(i2);
                return null;
            case 10:
                handleSetMvp(i2, i3, (float[]) obj);
                return null;
            case 11:
            default:
                return null;
            case 12:
                handleRemoveAll();
                return null;
        }
    }

    private void handleDraw(boolean z, int i, float[] fArr) {
        removeRequest(1);
        if (!isMasterSurfaceValid()) {
            Log.e(TAG, "handleDraw:invalid master surface");
            offer(5);
            return;
        }
        if (this.mIsFirstFrameRendered) {
            try {
                makeCurrent();
                if (this.mHasNewFrame) {
                    this.mHasNewFrame = false;
                    handleUpdateTexture();
                    GLES20.glFlush();
                    ThreadUtils.NoThrowSleep(0L, 0);
                }
                handleDrawTargets(z, i, fArr);
            } catch (Exception e) {
                Log.e(TAG, "handleDraw:thread id =" + Thread.currentThread().getId(), e);
                offer(5);
                return;
            }
        }
        makeCurrent();
        GLES20.glClear(16384);
        GLES20.glFlush();
        if (this.mIsFirstFrameRendered) {
            callOnFrameAvailable();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleDrawTargets(boolean z, int i, float[] fArr) {
        GLDrawer2D gLDrawer2D;
        int size = this.mTargets.size();
        synchronized (this.mSync) {
            GLDrawer2D gLDrawer2D2 = this.mDrawer;
            if (gLDrawer2D2 == null || gLDrawer2D2.isOES() != z) {
                GLDrawer2D gLDrawer2D3 = this.mDrawer;
                if (gLDrawer2D3 != null) {
                    gLDrawer2D3.release();
                }
                this.mDrawer = this.mDrawerFactory.create(isGLES3(), z);
            }
            gLDrawer2D = this.mDrawer;
        }
        for (int i2 = size - 1; i2 >= 0; i2--) {
            RendererTarget valueAt = this.mTargets.valueAt(i2);
            if (valueAt != null && valueAt.canDraw()) {
                try {
                    valueAt.draw(gLDrawer2D, 33984, i, fArr);
                } catch (Exception unused) {
                    synchronized (this.mTargets) {
                        this.mTargets.removeAt(i2);
                        valueAt.release();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleResize(int i, int i2) {
        this.mVideoWidth = i;
        this.mVideoHeight = i2;
    }

    private void handleAddSurface(TargetSurface targetSurface) {
        checkTarget();
        if (this.mTargets.get(targetSurface.id) == null) {
            try {
                RendererTarget createRendererTarget = createRendererTarget(getEgl(), targetSurface.id, targetSurface.surface, targetSurface.maxFps);
                GLUtils.setMirror(createRendererTarget.getMvpMatrix(), this.mMirror);
                synchronized (this.mTargets) {
                    this.mTargets.append(targetSurface.id, createRendererTarget);
                }
            } catch (Exception e) {
                Log.w(TAG, "invalid surface: surface=" + targetSurface, e);
            }
        } else {
            Log.w(TAG, "surface is already added: id=" + targetSurface.id);
        }
        synchronized (this.mSync) {
            this.mSync.notify();
        }
    }

    private RendererTarget createRendererTarget(EGLBase eGLBase, int i, Object obj, Fraction fraction) {
        return RendererTarget.newInstance(eGLBase, obj, fraction != null ? fraction.asFloat() : -1.0f);
    }

    private void handleRemoveSurface(int i) {
        RendererTarget rendererTarget = this.mTargets.get(i);
        if (rendererTarget != null) {
            this.mTargets.remove(i);
            if (rendererTarget.isValid()) {
                rendererTarget.clear(0);
            }
            rendererTarget.release();
        }
        checkTarget();
        synchronized (this.mSync) {
            this.mSync.notify();
        }
    }

    private void handleRemoveAll() {
        synchronized (this.mTargets) {
            int size = this.mTargets.size();
            Log.i(TAG, "handleRemoveAll:n=" + size);
            for (int i = 0; i < size; i++) {
                RendererTarget valueAt = this.mTargets.valueAt(i);
                if (valueAt != null) {
                    if (valueAt.isValid()) {
                        valueAt.clear(0);
                    }
                    valueAt.release();
                }
            }
            this.mTargets.clear();
        }
        synchronized (this.mSync) {
            this.mSync.notify();
        }
    }

    private void checkTarget() {
        int size = this.mTargets.size();
        for (int i = 0; i < size; i++) {
            RendererTarget valueAt = this.mTargets.valueAt(i);
            if (valueAt != null && !valueAt.isValid()) {
                this.mTargets.remove(this.mTargets.keyAt(i));
                valueAt.release();
            }
        }
    }

    private void handleClear(int i, int i2) {
        RendererTarget rendererTarget = this.mTargets.get(i);
        if (rendererTarget == null || !rendererTarget.isValid()) {
            return;
        }
        rendererTarget.clear(i2);
    }

    private void handleClearAll(int i) {
        int size = this.mTargets.size();
        for (int i2 = 0; i2 < size; i2++) {
            RendererTarget valueAt = this.mTargets.valueAt(i2);
            if (valueAt != null && valueAt.isValid()) {
                valueAt.clear(i);
            }
        }
    }

    private void handleSetMvp(int i, int i2, float[] fArr) {
        RendererTarget rendererTarget = this.mTargets.get(i);
        if (rendererTarget == null || !rendererTarget.isValid()) {
            return;
        }
        System.arraycopy(fArr, i2, rendererTarget.getMvpMatrix(), 0, 16);
    }

    private void handleMirror(int i) {
        this.mMirror = i;
        int size = this.mTargets.size();
        for (int i2 = 0; i2 < size; i2++) {
            RendererTarget valueAt = this.mTargets.valueAt(i2);
            if (valueAt != null) {
                GLUtils.setMirror(valueAt.getMvpMatrix(), i);
            }
        }
    }

    private void handleRotate(int i, int i2) {
        this.mRotation = i2;
        RendererTarget rendererTarget = this.mTargets.get(i);
        if (rendererTarget != null) {
            GLUtils.setRotation(rendererTarget.getMvpMatrix(), i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void checkFinished() throws IllegalStateException {
        if (isFinished()) {
            throw new IllegalStateException("already finished");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class TargetSurface {
        private final int id;
        private final Fraction maxFps;
        private final Object surface;

        private TargetSurface(int i, Object obj, int i2) {
            this(i, obj, makeFraction(i2));
        }

        private TargetSurface(int i, Object obj, Fraction fraction) {
            this.id = i;
            this.surface = obj;
            this.maxFps = fraction;
        }

        public String toString() {
            return "TargetSurface{id=" + this.id + ", surface=" + this.surface + ", maxFps=" + this.maxFps + '}';
        }

        private static Fraction makeFraction(int i) {
            if (i < 0) {
                return null;
            }
            if (i > 1000) {
                return new Fraction(i, 1000);
            }
            return new Fraction(i, 1);
        }
    }
}
