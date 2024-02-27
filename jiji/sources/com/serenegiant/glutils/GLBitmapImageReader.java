package com.serenegiant.glutils;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.serenegiant.gl.GLConst;
import com.serenegiant.gl.GLSurface;
import com.serenegiant.gl.GLUtils;
import com.serenegiant.glutils.GLImageReceiver;
import com.serenegiant.glutils.ImageReader;
import com.serenegiant.graphics.BitmapHelper;
import com.serenegiant.utils.Pool;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingDeque;

/* loaded from: classes2.dex */
public class GLBitmapImageReader implements ImageReader<Bitmap>, GLImageReceiver.Callback {
    private static final boolean DEBUG = false;
    private static final String TAG = "GLBitmapImageReader";
    private final Bitmap.Config mConfig;
    private int mHeight;
    private ImageReader.OnImageAvailableListener<Bitmap> mListener;
    private Handler mListenerHandler;
    private final int mMaxImages;
    private final Pool<Bitmap> mPool;
    private GLSurface mReadSurface;
    private int mWidth;
    private ByteBuffer mWorkBuffer;
    private final Object mSync = new Object();
    private final LinkedBlockingDeque<Bitmap> mQueue = new LinkedBlockingDeque<>();
    private final Paint mPaint = new Paint();
    private volatile boolean mAllBitmapAcquired = false;
    private final Runnable mOnImageAvailableTask = new Runnable() { // from class: com.serenegiant.glutils.GLBitmapImageReader.2
        @Override // java.lang.Runnable
        public void run() {
            synchronized (GLBitmapImageReader.this.mSync) {
                if (GLBitmapImageReader.this.mListener != null) {
                    GLBitmapImageReader.this.mListener.onImageAvailable(GLBitmapImageReader.this);
                }
            }
        }
    };

    @Override // com.serenegiant.glutils.GLImageReceiver.Callback
    public void onCreateInputSurface(GLImageReceiver gLImageReceiver) {
    }

    @Override // com.serenegiant.glutils.GLImageReceiver.Callback
    public void onInitialize(GLImageReceiver gLImageReceiver) {
    }

    public GLBitmapImageReader(int i, int i2, final Bitmap.Config config, int i3) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mConfig = config;
        this.mMaxImages = i3;
        this.mPool = new Pool<Bitmap>(1, i3, new Object[0]) { // from class: com.serenegiant.glutils.GLBitmapImageReader.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.serenegiant.utils.Pool
            public Bitmap createObject(Object... objArr) {
                Bitmap createBitmap;
                synchronized (GLBitmapImageReader.this.mSync) {
                    createBitmap = Bitmap.createBitmap(GLBitmapImageReader.this.mWidth, GLBitmapImageReader.this.mHeight, config);
                }
                return createBitmap;
            }
        };
    }

    @Override // com.serenegiant.glutils.GLImageReceiver.Callback
    public void onRelease() {
        setOnImageAvailableListener(null, null);
        synchronized (this.mQueue) {
            this.mQueue.clear();
        }
        this.mWorkBuffer = null;
        this.mPool.clear();
    }

    @Override // com.serenegiant.glutils.GLImageReceiver.Callback
    public void onReleaseInputSurface(GLImageReceiver gLImageReceiver) {
        this.mWorkBuffer = null;
        GLSurface gLSurface = this.mReadSurface;
        if (gLSurface != null) {
            gLSurface.release();
            this.mReadSurface = null;
        }
    }

    @Override // com.serenegiant.glutils.GLImageReceiver.Callback
    public void onResize(int i, int i2) {
        synchronized (this.mSync) {
            this.mWidth = i;
            this.mHeight = i2;
        }
    }

    @Override // com.serenegiant.glutils.GLImageReceiver.Callback
    public void onFrameAvailable(GLImageReceiver gLImageReceiver, boolean z, int i, float[] fArr) {
        int width = gLImageReceiver.getWidth();
        int height = gLImageReceiver.getHeight();
        int pixelBytes = width * height * BitmapHelper.getPixelBytes(this.mConfig);
        ByteBuffer byteBuffer = this.mWorkBuffer;
        if (byteBuffer == null || byteBuffer.capacity() != pixelBytes) {
            synchronized (this.mSync) {
                this.mWidth = width;
                this.mHeight = height;
            }
            this.mWorkBuffer = ByteBuffer.allocateDirect(pixelBytes);
        }
        Bitmap obtainBitmap = obtainBitmap(width, height);
        if (obtainBitmap != null) {
            this.mAllBitmapAcquired = false;
            if (this.mReadSurface == null) {
                try {
                    this.mReadSurface = GLSurface.wrap(gLImageReceiver.isGLES3(), GLConst.GL_TEXTURE_EXTERNAL_OES, 33985, i, width, height, false);
                } catch (Exception e) {
                    Log.w(TAG, e);
                    return;
                }
            }
            this.mReadSurface.makeCurrent();
            this.mWorkBuffer.clear();
            GLUtils.glReadPixels(this.mWorkBuffer, width, height);
            this.mWorkBuffer.clear();
            obtainBitmap.copyPixelsFromBuffer(this.mWorkBuffer);
            synchronized (this.mQueue) {
                this.mQueue.addLast(obtainBitmap);
            }
        } else {
            this.mAllBitmapAcquired = true;
        }
        callOnFrameAvailable();
    }

    @Override // com.serenegiant.glutils.ImageReader
    public void setOnImageAvailableListener(ImageReader.OnImageAvailableListener<Bitmap> onImageAvailableListener, Handler handler) throws IllegalArgumentException {
        synchronized (this.mSync) {
            if (onImageAvailableListener != null) {
                Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
                if (looper == null) {
                    throw new IllegalArgumentException("handler is null but the current thread is not a looper");
                }
                Handler handler2 = this.mListenerHandler;
                if (handler2 == null || handler2.getLooper() != looper) {
                    this.mListenerHandler = new Handler(looper);
                }
                this.mListener = onImageAvailableListener;
            } else {
                this.mListener = null;
                this.mListenerHandler = null;
            }
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.serenegiant.glutils.ImageReader
    public Bitmap acquireLatestImage() throws IllegalStateException {
        Bitmap pollLast;
        synchronized (this.mQueue) {
            pollLast = this.mQueue.pollLast();
            while (!this.mQueue.isEmpty()) {
                recycle(this.mQueue.pollFirst());
            }
            if (this.mAllBitmapAcquired && pollLast == null) {
                throw new IllegalStateException("all bitmap is acquired!");
            }
        }
        return pollLast;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.serenegiant.glutils.ImageReader
    public Bitmap acquireNextImage() throws IllegalStateException {
        Bitmap pollFirst;
        synchronized (this.mQueue) {
            pollFirst = this.mQueue.pollFirst();
            if (this.mAllBitmapAcquired && pollFirst == null) {
                throw new IllegalStateException("all bitmap is acquired!");
            }
        }
        return pollFirst;
    }

    @Override // com.serenegiant.glutils.ImageReader
    public void recycle(Bitmap bitmap) {
        this.mAllBitmapAcquired = false;
        if (!bitmap.isRecycled()) {
            this.mPool.recycle((Pool<Bitmap>) bitmap);
        } else {
            this.mPool.release(bitmap);
        }
    }

    public int getMaxImages() {
        return this.mMaxImages;
    }

    public Bitmap.Config getConfig() {
        return this.mConfig;
    }

    private Bitmap obtainBitmap(int i, int i2) {
        Bitmap obtain = this.mPool.obtain(new Object[0]);
        if (obtain == null) {
            synchronized (this.mQueue) {
                obtain = this.mQueue.pollFirst();
            }
        }
        return obtain != null ? (obtain.getWidth() == i && obtain.getHeight() == i2) ? obtain : Bitmap.createBitmap(this.mWidth, this.mHeight, this.mConfig) : obtain;
    }

    private void callOnFrameAvailable() {
        synchronized (this.mSync) {
            Handler handler = this.mListenerHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mOnImageAvailableTask);
                this.mListenerHandler.post(this.mOnImageAvailableTask);
            }
        }
    }
}
