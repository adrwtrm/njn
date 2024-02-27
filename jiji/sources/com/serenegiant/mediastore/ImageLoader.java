package com.serenegiant.mediastore;

import android.content.Context;
import android.graphics.Bitmap;
import com.serenegiant.graphics.BitmapHelper;
import com.serenegiant.utils.ThreadPool;
import java.util.concurrent.FutureTask;

/* loaded from: classes2.dex */
public abstract class ImageLoader implements Runnable {
    private Bitmap mBitmap;
    protected final LoaderDrawable mParent;
    private final MediaInfo mInfo = new MediaInfo();
    private final FutureTask<Bitmap> mTask = new FutureTask<>(this, null);

    protected abstract Bitmap loadBitmap(Context context, MediaInfo mediaInfo, int i, int i2);

    public ImageLoader(LoaderDrawable loaderDrawable) {
        this.mParent = loaderDrawable;
    }

    public long id() {
        return this.mInfo.id;
    }

    public synchronized void startLoad(MediaInfo mediaInfo) {
        this.mInfo.set(mediaInfo);
        this.mBitmap = null;
        ThreadPool.queueEvent(this.mTask);
    }

    public void cancelLoad() {
        this.mTask.cancel(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Bitmap loadDefaultBitmap(Context context, int i) {
        return BitmapHelper.fromDrawable(context, i);
    }

    @Override // java.lang.Runnable
    public void run() {
        MediaInfo mediaInfo;
        synchronized (this) {
            mediaInfo = new MediaInfo(this.mInfo);
        }
        if (!this.mTask.isCancelled()) {
            this.mBitmap = loadBitmap(this.mParent.getContext(), mediaInfo, this.mParent.getIntrinsicWidth(), this.mParent.getIntrinsicHeight());
        }
        if (this.mTask.isCancelled() || !mediaInfo.equals(this.mInfo) || this.mBitmap == null) {
            return;
        }
        LoaderDrawable loaderDrawable = this.mParent;
        loaderDrawable.scheduleSelf(loaderDrawable, 0L);
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }
}
