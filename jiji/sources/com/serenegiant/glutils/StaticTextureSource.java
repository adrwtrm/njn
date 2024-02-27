package com.serenegiant.glutils;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.util.Log;
import android.util.SparseArray;
import com.serenegiant.egl.EGLBase;
import com.serenegiant.egl.EglTask;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.gl.GLTexture;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.gl.RendererTarget;
import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public class StaticTextureSource implements GLConst, IMirror {
    private static final boolean DEBUG = false;
    private static final int REQUEST_ADD_SURFACE = 3;
    private static final int REQUEST_DRAW = 1;
    private static final int REQUEST_MIRROR = 6;
    private static final int REQUEST_REMOVE_SURFACE = 4;
    private static final int REQUEST_SET_BITMAP = 7;
    private static final String TAG = "StaticTextureSource";
    private final Runnable mOnFrameTask;
    private RendererTask mRendererTask;
    private final Object mSync;

    public StaticTextureSource(Bitmap bitmap) {
        this(bitmap, new Fraction(10));
    }

    public StaticTextureSource(Fraction fraction) {
        this(null, fraction);
    }

    public StaticTextureSource(Bitmap bitmap, Fraction fraction) {
        this.mSync = new Object();
        this.mOnFrameTask = new Runnable() { // from class: com.serenegiant.glutils.StaticTextureSource.1
            @Override // java.lang.Runnable
            public void run() {
                RendererTask rendererTask;
                long j = StaticTextureSource.this.mRendererTask.mIntervalsNs / 1000000;
                int i = (int) (StaticTextureSource.this.mRendererTask.mIntervalsNs % 1000000);
                while (StaticTextureSource.this.isRunning()) {
                    try {
                        synchronized (StaticTextureSource.this.mSync) {
                            StaticTextureSource.this.mSync.wait(j, i);
                            rendererTask = StaticTextureSource.this.mRendererTask;
                        }
                        if (rendererTask != null) {
                            if (rendererTask.mImageSource != null) {
                                rendererTask.removeRequest(1);
                                rendererTask.offer(1);
                            }
                        }
                    } catch (Exception e) {
                        Log.w(StaticTextureSource.TAG, e);
                    }
                }
            }
        };
        this.mRendererTask = new RendererTask(this, bitmap != null ? bitmap.getWidth() : 1, bitmap != null ? bitmap.getHeight() : 1, fraction);
        new Thread(this.mRendererTask, TAG).start();
        if (!this.mRendererTask.waitReady()) {
            throw new RuntimeException("failed to start renderer thread");
        }
        setBitmap(bitmap);
    }

    public boolean isRunning() {
        boolean z;
        synchronized (this.mSync) {
            RendererTask rendererTask = this.mRendererTask;
            z = rendererTask != null && rendererTask.isRunning();
        }
        return z;
    }

    public void release() {
        RendererTask rendererTask;
        synchronized (this.mSync) {
            rendererTask = this.mRendererTask;
            this.mRendererTask = null;
            this.mSync.notifyAll();
        }
        if (rendererTask != null) {
            rendererTask.release();
        }
    }

    public void addSurface(int i, Object obj, boolean z) {
        addSurface(i, obj, z, -1);
    }

    public void addSurface(int i, Object obj, boolean z, int i2) {
        RendererTask rendererTask;
        synchronized (this.mSync) {
            rendererTask = this.mRendererTask;
        }
        if (rendererTask != null) {
            rendererTask.addSurface(i, obj, i2);
        }
    }

    public void removeSurface(int i) {
        RendererTask rendererTask;
        synchronized (this.mSync) {
            rendererTask = this.mRendererTask;
        }
        if (rendererTask != null) {
            rendererTask.removeSurface(i);
        }
    }

    public void requestFrame() {
        RendererTask rendererTask;
        synchronized (this.mSync) {
            rendererTask = this.mRendererTask;
        }
        if (rendererTask != null) {
            rendererTask.removeRequest(1);
            rendererTask.offer(1);
        }
    }

    public int getCount() {
        return this.mRendererTask.getCount();
    }

    public void setBitmap(Bitmap bitmap) {
        RendererTask rendererTask;
        if (bitmap != null) {
            synchronized (this.mSync) {
                rendererTask = this.mRendererTask;
            }
            if (rendererTask != null) {
                rendererTask.setBitmap(bitmap);
            }
        }
    }

    public int getWidth() {
        int i;
        synchronized (this.mSync) {
            RendererTask rendererTask = this.mRendererTask;
            i = rendererTask != null ? rendererTask.mVideoWidth : 0;
        }
        return i;
    }

    public int getHeight() {
        int i;
        synchronized (this.mSync) {
            RendererTask rendererTask = this.mRendererTask;
            i = rendererTask != null ? rendererTask.mVideoHeight : 0;
        }
        return i;
    }

    @Override // com.serenegiant.glutils.IMirror
    public void setMirror(int i) throws IllegalStateException {
        RendererTask rendererTask;
        synchronized (this.mSync) {
            rendererTask = this.mRendererTask;
        }
        if (rendererTask == null || rendererTask.mMirror == i) {
            return;
        }
        rendererTask.offer(6, i);
    }

    @Override // com.serenegiant.glutils.IMirror
    public int getMirror() {
        int i;
        synchronized (this.mSync) {
            RendererTask rendererTask = this.mRendererTask;
            i = rendererTask != null ? rendererTask.mMirror : 0;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class RendererTask extends EglTask {
        private GLDrawer2D mDrawer;
        private GLTexture mImageSource;
        private final long mIntervalsNs;
        private int mMirror;
        private final StaticTextureSource mParent;
        private final SparseArray<RendererTarget> mTargets;
        private int mVideoHeight;
        private int mVideoWidth;

        @Override // com.serenegiant.utils.MessageTask
        protected boolean onError(Throwable th) {
            return false;
        }

        public RendererTask(StaticTextureSource staticTextureSource, int i, int i2, Fraction fraction) {
            super(GLUtils.getSupportedGLVersion(), (EGLBase.IContext<?>) null, 0);
            this.mTargets = new SparseArray<>();
            this.mMirror = 0;
            this.mParent = staticTextureSource;
            this.mVideoWidth = i;
            this.mVideoHeight = i2;
            float asFloat = fraction != null ? fraction.asFloat() : 0.0f;
            this.mIntervalsNs = asFloat <= 0.0f ? 100000000L : 1.0E9f / asFloat;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.egl.EglTask, com.serenegiant.utils.MessageTask
        public void onInit(int i, int i2, Object obj) {
            super.onInit(i, i2, obj);
        }

        @Override // com.serenegiant.utils.MessageTask
        protected void onStart() {
            this.mDrawer = GLDrawer2D.create(false, false);
            new Thread(this.mParent.mOnFrameTask, StaticTextureSource.TAG).start();
        }

        @Override // com.serenegiant.utils.MessageTask
        protected void onStop() {
            makeCurrent();
            GLDrawer2D gLDrawer2D = this.mDrawer;
            if (gLDrawer2D != null) {
                gLDrawer2D.release();
                this.mDrawer = null;
            }
            GLTexture gLTexture = this.mImageSource;
            if (gLTexture != null) {
                gLTexture.release();
                this.mImageSource = null;
            }
            handleRemoveAll();
        }

        @Override // com.serenegiant.utils.MessageTask
        protected Object processRequest(int i, int i2, int i3, Object obj) {
            if (i == 1) {
                handleDraw();
                return null;
            } else if (i == 3) {
                handleAddSurface(i2, obj, i3);
                return null;
            } else if (i == 4) {
                handleRemoveSurface(i2);
                return null;
            } else if (i == 6) {
                handleMirror(i2);
                return null;
            } else if (i != 7) {
                return null;
            } else {
                handleSetBitmap((Bitmap) obj);
                return null;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x001b, code lost:
            r4.mTargets.wait(1000);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void addSurface(int r5, java.lang.Object r6, int r7) {
            /*
                r4 = this;
                r4.checkFinished()
                boolean r0 = com.serenegiant.gl.GLUtils.isSupportedSurface(r6)
                if (r0 == 0) goto L30
                android.util.SparseArray<com.serenegiant.gl.RendererTarget> r0 = r4.mTargets
                monitor-enter(r0)
            Lc:
                android.util.SparseArray<com.serenegiant.gl.RendererTarget> r1 = r4.mTargets     // Catch: java.lang.Throwable -> L2d
                java.lang.Object r1 = r1.get(r5)     // Catch: java.lang.Throwable -> L2d
                if (r1 != 0) goto L2b
                r1 = 3
                boolean r1 = r4.offer(r1, r5, r7, r6)     // Catch: java.lang.Throwable -> L2d
                if (r1 == 0) goto L23
                android.util.SparseArray<com.serenegiant.gl.RendererTarget> r5 = r4.mTargets     // Catch: java.lang.InterruptedException -> L2b java.lang.Throwable -> L2d
                r6 = 1000(0x3e8, double:4.94E-321)
                r5.wait(r6)     // Catch: java.lang.InterruptedException -> L2b java.lang.Throwable -> L2d
                goto L2b
            L23:
                android.util.SparseArray<com.serenegiant.gl.RendererTarget> r1 = r4.mTargets     // Catch: java.lang.InterruptedException -> L2b java.lang.Throwable -> L2d
                r2 = 10
                r1.wait(r2)     // Catch: java.lang.InterruptedException -> L2b java.lang.Throwable -> L2d
                goto Lc
            L2b:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L2d
                return
            L2d:
                r5 = move-exception
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L2d
                throw r5
            L30:
                java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
                java.lang.String r6 = "Surface should be one of Surface, SurfaceTexture or SurfaceHolder"
                r5.<init>(r6)
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.glutils.StaticTextureSource.RendererTask.addSurface(int, java.lang.Object, int):void");
        }

        public void removeSurface(int i) {
            synchronized (this.mTargets) {
                if (this.mTargets.get(i) != null) {
                    while (!offer(4, i)) {
                        try {
                            this.mTargets.wait(10L);
                        } catch (InterruptedException unused) {
                        }
                    }
                    this.mTargets.wait();
                }
            }
        }

        public void setBitmap(Bitmap bitmap) {
            offer(7, bitmap);
        }

        public int getCount() {
            int size;
            synchronized (this.mTargets) {
                size = this.mTargets.size();
            }
            return size;
        }

        private void checkFinished() {
            if (isFinished()) {
                throw new RuntimeException("already finished");
            }
        }

        private void handleDraw() {
            makeCurrent();
            GLTexture gLTexture = this.mImageSource;
            if (gLTexture == null) {
                Log.w(StaticTextureSource.TAG, "mImageSource is not ready");
            } else {
                int texId = gLTexture.getTexId();
                synchronized (this.mTargets) {
                    for (int size = this.mTargets.size() - 1; size >= 0; size--) {
                        RendererTarget valueAt = this.mTargets.valueAt(size);
                        if (valueAt != null && valueAt.canDraw()) {
                            try {
                                valueAt.draw(this.mDrawer, 33984, texId, null);
                                GLUtils.checkGlError("handleDraw");
                            } catch (Exception unused) {
                                this.mTargets.removeAt(size);
                                valueAt.release();
                            }
                        }
                    }
                }
            }
            GLES20.glClear(16384);
            GLES20.glFlush();
        }

        private void handleAddSurface(int i, Object obj, int i2) {
            checkTarget();
            synchronized (this.mTargets) {
                if (this.mTargets.get(i) != null) {
                    Log.w(StaticTextureSource.TAG, "surface is already added: id=" + i);
                } else {
                    try {
                        RendererTarget createRendererTarget = createRendererTarget(i, getEgl(), obj, i2);
                        createRendererTarget.setMirror(this.mMirror);
                        this.mTargets.append(i, createRendererTarget);
                    } catch (Exception e) {
                        Log.w(StaticTextureSource.TAG, "invalid surface: surface=" + obj, e);
                    }
                }
                this.mTargets.notifyAll();
            }
        }

        private RendererTarget createRendererTarget(int i, EGLBase eGLBase, Object obj, float f) {
            if (f > 1000.0f) {
                f /= 1000.0f;
            }
            return RendererTarget.newInstance(eGLBase, obj, f);
        }

        private void handleRemoveSurface(int i) {
            synchronized (this.mTargets) {
                RendererTarget rendererTarget = this.mTargets.get(i);
                if (rendererTarget != null) {
                    this.mTargets.remove(i);
                    rendererTarget.release();
                }
                checkTarget();
                this.mTargets.notifyAll();
            }
        }

        private void handleRemoveAll() {
            synchronized (this.mTargets) {
                int size = this.mTargets.size();
                for (int i = 0; i < size; i++) {
                    RendererTarget valueAt = this.mTargets.valueAt(i);
                    if (valueAt != null) {
                        makeCurrent();
                        valueAt.release();
                    }
                }
                this.mTargets.clear();
            }
        }

        private void checkTarget() {
            synchronized (this.mTargets) {
                int size = this.mTargets.size();
                for (int i = 0; i < size; i++) {
                    RendererTarget valueAt = this.mTargets.valueAt(i);
                    if (valueAt != null && !valueAt.isValid()) {
                        int keyAt = this.mTargets.keyAt(i);
                        this.mTargets.valueAt(i).release();
                        this.mTargets.remove(keyAt);
                    }
                }
            }
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

        private void handleSetBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (this.mImageSource == null) {
                this.mImageSource = GLTexture.newInstance(33984, width, height);
                GLUtils.checkGlError("handleSetBitmap");
            }
            this.mImageSource.loadBitmap(bitmap);
            this.mVideoWidth = width;
            this.mVideoHeight = height;
        }
    }
}
