package com.serenegiant.mediastore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import com.serenegiant.graphics.MatrixUtils;

/* loaded from: classes2.dex */
public abstract class ThumbnailLoaderDrawable extends Drawable implements Runnable {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_PAINT_FLAGS = 6;
    private static final String TAG = "ThumbnailLoaderDrawable";
    private Bitmap mBitmap;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private final Context mContext;
    private final Paint mDebugPaint;
    private final Matrix mDrawMatrix;
    private ThumbnailLoader mLoader;
    private final Paint mPaint = new Paint(6);
    private int mRotationDegree;
    private int mTargetDensity;

    protected abstract Bitmap checkCache(long j);

    protected abstract ThumbnailLoader createLoader();

    public ThumbnailLoaderDrawable(Context context, int i, int i2) {
        Paint paint = new Paint(6);
        this.mDebugPaint = paint;
        this.mDrawMatrix = new Matrix();
        this.mRotationDegree = 0;
        this.mTargetDensity = 160;
        this.mContext = context;
        paint.setColor(-65536);
        paint.setTextSize(18.0f);
        this.mBitmapWidth = i;
        this.mBitmapHeight = i2;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateDrawMatrix(getBounds());
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (this.mBitmap != null) {
            canvas.save();
            try {
                canvas.clipRect(bounds);
                canvas.concat(this.mDrawMatrix);
                canvas.rotate(this.mRotationDegree, bounds.centerX(), bounds.centerY());
                canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.mPaint);
                return;
            } finally {
                canvas.restore();
            }
        }
        this.mPaint.setColor(-3355444);
        canvas.drawRect(bounds, this.mPaint);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Context getContext() {
        return this.mContext;
    }

    private void updateDrawMatrix(Rect rect) {
        if (this.mBitmap == null || rect.isEmpty()) {
            this.mDrawMatrix.reset();
            return;
        }
        MatrixUtils.updateDrawMatrix(MatrixUtils.ScaleType.CENTER_CROP, this.mDrawMatrix, rect.width(), rect.height(), this.mBitmap.getWidth(), this.mBitmap.getHeight());
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Bitmap bitmap = this.mBitmap;
        return (bitmap == null || bitmap.hasAlpha() || this.mPaint.getAlpha() < 255) ? -3 : -1;
    }

    public void setTargetDensity(Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    public void setTargetDensity(int i) {
        if (this.mTargetDensity != i) {
            if (i == 0) {
                i = 160;
            }
            this.mTargetDensity = i;
            if (this.mBitmap != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }

    public void setRotation(int i) {
        if (this.mRotationDegree != i) {
            this.mRotationDegree = i;
            invalidateSelf();
        }
    }

    public int getRotation() {
        return this.mRotationDegree;
    }

    @Override // java.lang.Runnable
    public void run() {
        setBitmap(this.mLoader.getBitmap());
    }

    public void startLoad(MediaInfo mediaInfo) {
        ThumbnailLoader thumbnailLoader = this.mLoader;
        if (thumbnailLoader != null) {
            thumbnailLoader.cancelLoad();
        }
        Bitmap checkCache = checkCache(mediaInfo.id);
        if (checkCache == null) {
            this.mBitmap = null;
            ThumbnailLoader createLoader = createLoader();
            this.mLoader = createLoader;
            createLoader.startLoad(mediaInfo);
        } else {
            setBitmap(checkCache);
        }
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBitmap(Bitmap bitmap) {
        if (bitmap != this.mBitmap) {
            this.mBitmap = bitmap;
            computeBitmapSize();
            updateDrawMatrix(getBounds());
        }
    }

    private void computeBitmapSize() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            this.mBitmapWidth = bitmap.getScaledWidth(this.mTargetDensity);
            this.mBitmapHeight = this.mBitmap.getScaledHeight(this.mTargetDensity);
            return;
        }
        this.mBitmapHeight = -1;
        this.mBitmapWidth = -1;
    }
}
