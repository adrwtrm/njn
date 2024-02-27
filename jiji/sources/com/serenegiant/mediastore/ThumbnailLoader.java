package com.serenegiant.mediastore;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.serenegiant.utils.ThreadPool;
import java.util.concurrent.FutureTask;

/* loaded from: classes2.dex */
public abstract class ThumbnailLoader implements Runnable {
    private static final boolean DEBUG = false;
    private static final String TAG = "ThumbnailLoader";
    private Bitmap mBitmap;
    protected final ThumbnailLoaderDrawable mParent;
    private final MediaInfo mInfo = new MediaInfo();
    private final FutureTask<Bitmap> mTask = new FutureTask<>(this, null);

    protected abstract Bitmap loadThumbnail(Context context, MediaInfo mediaInfo, int i, int i2);

    public ThumbnailLoader(ThumbnailLoaderDrawable thumbnailLoaderDrawable) {
        this.mParent = thumbnailLoaderDrawable;
    }

    public long id() {
        return this.mInfo.id;
    }

    public Uri getUri() {
        return this.mInfo.getUri();
    }

    public synchronized void startLoad(MediaInfo mediaInfo) {
        this.mInfo.set(mediaInfo);
        this.mBitmap = null;
        ThreadPool.queueEvent(this.mTask);
    }

    public void cancelLoad() {
        this.mTask.cancel(true);
    }

    @Override // java.lang.Runnable
    public void run() {
        MediaInfo mediaInfo;
        synchronized (this) {
            mediaInfo = new MediaInfo(this.mInfo);
        }
        if (!this.mTask.isCancelled()) {
            this.mBitmap = loadThumbnail(this.mParent.getContext(), mediaInfo, this.mParent.getIntrinsicWidth(), this.mParent.getIntrinsicHeight());
        }
        if (this.mTask.isCancelled() || !mediaInfo.equals(this.mInfo)) {
            return;
        }
        ThumbnailLoaderDrawable thumbnailLoaderDrawable = this.mParent;
        thumbnailLoaderDrawable.scheduleSelf(thumbnailLoaderDrawable, 0L);
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }
}
