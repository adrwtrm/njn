package com.serenegiant.mediastore;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import androidx.collection.LruCache;
import com.serenegiant.common.BuildConfig;
import com.serenegiant.graphics.BitmapHelper;
import com.serenegiant.io.DiskLruCache;
import com.serenegiant.system.ContextUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

/* loaded from: classes2.dex */
public class ThumbnailCache {
    private static final int CACHE_RATE = 8;
    private static final boolean DEBUG = false;
    private static final int DISK_CACHE_INDEX = 0;
    private static final int DISK_CACHE_SIZE = 10485760;
    private static final String DISK_CACHE_SUBDIR = ".thumbnailCache";
    private static final String TAG = "ThumbnailCache";
    private static int sCacheSize = 0;
    private static DiskLruCache sDiskLruCache = null;
    private static int sMaxDiskCacheBytes = 10485760;
    private static final Object sSync = new Object();
    private static LruCache<String, Bitmap> sThumbnailCache;

    private static void prepareThumbnailCache(Context context, int i) {
        synchronized (sSync) {
            LruCache<String, Bitmap> lruCache = sThumbnailCache;
            if (lruCache == null || sMaxDiskCacheBytes != i) {
                sMaxDiskCacheBytes = i;
                if (i <= 0) {
                    sMaxDiskCacheBytes = DISK_CACHE_SIZE;
                }
                if (lruCache != null) {
                    lruCache.evictAll();
                }
                DiskLruCache diskLruCache = sDiskLruCache;
                if (diskLruCache != null && !diskLruCache.isClosed()) {
                    try {
                        sDiskLruCache.close();
                    } catch (IOException unused) {
                    }
                }
                sCacheSize = (((ActivityManager) ContextUtils.requireSystemService(context, ActivityManager.class)).getMemoryClass() * 1048576) / 8;
                sThumbnailCache = new LruCache<String, Bitmap>(sCacheSize) { // from class: com.serenegiant.mediastore.ThumbnailCache.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // androidx.collection.LruCache
                    public int sizeOf(String str, Bitmap bitmap) {
                        return bitmap.getRowBytes() * bitmap.getHeight();
                    }
                };
                try {
                    File diskCacheDir = getDiskCacheDir(context);
                    if (!diskCacheDir.exists()) {
                        diskCacheDir.mkdirs();
                    }
                    if (!diskCacheDir.canWrite()) {
                        Log.w(TAG, "unable to write to cache dir!!");
                    }
                    sDiskLruCache = DiskLruCache.open(diskCacheDir, BuildConfig.VERSION_CODE, 1, sMaxDiskCacheBytes);
                } catch (IOException e) {
                    sDiskLruCache = null;
                    Log.w(TAG, e);
                }
            }
        }
    }

    private static File getDiskCacheDir(Context context) throws IOException {
        File externalCacheDir = context.getExternalCacheDir();
        externalCacheDir.mkdirs();
        if (externalCacheDir == null || !externalCacheDir.canWrite()) {
            externalCacheDir = context.getCacheDir();
            externalCacheDir.mkdirs();
        }
        if (externalCacheDir == null || !externalCacheDir.canWrite()) {
            throw new IOException("can't write cache dir");
        }
        File file = new File(externalCacheDir, DISK_CACHE_SUBDIR);
        file.mkdirs();
        return file;
    }

    public ThumbnailCache(Context context) {
        prepareThumbnailCache(context, DISK_CACHE_SIZE);
    }

    public ThumbnailCache(Context context, int i) {
        prepareThumbnailCache(context, i);
    }

    protected void finalize() throws Throwable {
        try {
            trim();
        } finally {
            super.finalize();
        }
    }

    public Bitmap get(long j) {
        return get(getKey(j));
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
        if (r3 != null) goto L19;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0046 A[Catch: all -> 0x004d, TryCatch #5 {, blocks: (B:4:0x0003, B:6:0x000d, B:15:0x002f, B:27:0x0046, B:28:0x004b, B:9:0x0012, B:11:0x0018, B:13:0x001f, B:19:0x0035), top: B:41:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Bitmap get(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.Object r0 = com.serenegiant.mediastore.ThumbnailCache.sSync
            monitor-enter(r0)
            androidx.collection.LruCache<java.lang.String, android.graphics.Bitmap> r1 = com.serenegiant.mediastore.ThumbnailCache.sThumbnailCache     // Catch: java.lang.Throwable -> L4d
            java.lang.Object r1 = r1.get(r6)     // Catch: java.lang.Throwable -> L4d
            android.graphics.Bitmap r1 = (android.graphics.Bitmap) r1     // Catch: java.lang.Throwable -> L4d
            if (r1 != 0) goto L4b
            com.serenegiant.io.DiskLruCache r2 = com.serenegiant.mediastore.ThumbnailCache.sDiskLruCache     // Catch: java.lang.Throwable -> L4d
            if (r2 == 0) goto L4b
            r3 = 0
            com.serenegiant.io.DiskLruCache$Snapshot r2 = r2.get(r6)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35
            if (r2 == 0) goto L2d
            r4 = 0
            java.io.InputStream r3 = r2.getInputStream(r4)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35
            if (r3 == 0) goto L2d
            r2 = r3
            java.io.FileInputStream r2 = (java.io.FileInputStream) r2     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35
            java.io.FileDescriptor r2 = r2.getFD()     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35
            r4 = 2147483647(0x7fffffff, float:NaN)
            android.graphics.Bitmap r1 = com.serenegiant.graphics.BitmapHelper.asBitmap(r2, r4, r4)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35
        L2d:
            if (r3 == 0) goto L44
        L2f:
            r3.close()     // Catch: java.io.IOException -> L44 java.lang.Throwable -> L4d
            goto L44
        L33:
            r6 = move-exception
            goto L3b
        L35:
            com.serenegiant.io.DiskLruCache r2 = com.serenegiant.mediastore.ThumbnailCache.sDiskLruCache     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L41
            r2.remove(r6)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L41
            goto L41
        L3b:
            if (r3 == 0) goto L40
            r3.close()     // Catch: java.io.IOException -> L40 java.lang.Throwable -> L4d
        L40:
            throw r6     // Catch: java.lang.Throwable -> L4d
        L41:
            if (r3 == 0) goto L44
            goto L2f
        L44:
            if (r1 == 0) goto L4b
            androidx.collection.LruCache<java.lang.String, android.graphics.Bitmap> r2 = com.serenegiant.mediastore.ThumbnailCache.sThumbnailCache     // Catch: java.lang.Throwable -> L4d
            r2.put(r6, r1)     // Catch: java.lang.Throwable -> L4d
        L4b:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4d
            return r1
        L4d:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4d
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.mediastore.ThumbnailCache.get(java.lang.String):android.graphics.Bitmap");
    }

    public void put(long j, Bitmap bitmap, boolean z) {
        put(getKey(j), bitmap, z);
    }

    @Deprecated
    public void put(String str, Bitmap bitmap) {
        put(str, bitmap, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003b, code lost:
        if (r2 != null) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void put(java.lang.String r4, android.graphics.Bitmap r5, boolean r6) {
        /*
            r3 = this;
            java.lang.Object r0 = com.serenegiant.mediastore.ThumbnailCache.sSync
            monitor-enter(r0)
            androidx.collection.LruCache<java.lang.String, android.graphics.Bitmap> r1 = com.serenegiant.mediastore.ThumbnailCache.sThumbnailCache     // Catch: java.lang.Throwable -> L50
            java.lang.Object r1 = r1.get(r4)     // Catch: java.lang.Throwable -> L50
            android.graphics.Bitmap r1 = (android.graphics.Bitmap) r1     // Catch: java.lang.Throwable -> L50
            if (r1 == 0) goto Lf
            if (r6 == 0) goto L14
        Lf:
            androidx.collection.LruCache<java.lang.String, android.graphics.Bitmap> r1 = com.serenegiant.mediastore.ThumbnailCache.sThumbnailCache     // Catch: java.lang.Throwable -> L50
            r1.put(r4, r5)     // Catch: java.lang.Throwable -> L50
        L14:
            com.serenegiant.io.DiskLruCache r1 = com.serenegiant.mediastore.ThumbnailCache.sDiskLruCache     // Catch: java.lang.Throwable -> L50
            if (r1 == 0) goto L4e
            r2 = 0
            boolean r1 = r1.contains(r4)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L48 java.io.IOException -> L4b
            if (r1 == 0) goto L21
            if (r6 == 0) goto L3b
        L21:
            com.serenegiant.io.DiskLruCache r6 = com.serenegiant.mediastore.ThumbnailCache.sDiskLruCache     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L48 java.io.IOException -> L4b
            com.serenegiant.io.DiskLruCache$Editor r4 = r6.edit(r4)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L48 java.io.IOException -> L4b
            if (r4 == 0) goto L3b
            r6 = 0
            java.io.OutputStream r2 = r4.newOutputStream(r6)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L48 java.io.IOException -> L4b
            android.graphics.Bitmap$CompressFormat r6 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L48 java.io.IOException -> L4b
            r1 = 90
            r5.compress(r6, r1, r2)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L48 java.io.IOException -> L4b
            r4.commit()     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L48 java.io.IOException -> L4b
            r2.close()     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L48 java.io.IOException -> L4b
        L3b:
            if (r2 == 0) goto L4e
        L3d:
            r2.close()     // Catch: java.io.IOException -> L4e java.lang.Throwable -> L50
            goto L4e
        L41:
            r4 = move-exception
            if (r2 == 0) goto L47
            r2.close()     // Catch: java.io.IOException -> L47 java.lang.Throwable -> L50
        L47:
            throw r4     // Catch: java.lang.Throwable -> L50
        L48:
            if (r2 == 0) goto L4e
            goto L3d
        L4b:
            if (r2 == 0) goto L4e
            goto L3d
        L4e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L50
            return
        L50:
            r4 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L50
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.mediastore.ThumbnailCache.put(java.lang.String, android.graphics.Bitmap, boolean):void");
    }

    public void clear() {
        synchronized (sSync) {
            sThumbnailCache.evictAll();
            DiskLruCache diskLruCache = sDiskLruCache;
            if (diskLruCache != null) {
                try {
                    diskLruCache.delete();
                } catch (IOException unused) {
                }
            }
        }
    }

    public void trim() {
        synchronized (sSync) {
            sThumbnailCache.trimToSize(sCacheSize);
            DiskLruCache diskLruCache = sDiskLruCache;
            if (diskLruCache != null) {
                try {
                    diskLruCache.flush();
                } catch (IOException unused) {
                }
            }
        }
    }

    public void remove(String str) {
        synchronized (sSync) {
            sThumbnailCache.remove(str);
            DiskLruCache diskLruCache = sDiskLruCache;
            if (diskLruCache != null) {
                try {
                    diskLruCache.remove(str);
                } catch (IOException unused) {
                }
            }
        }
    }

    public Bitmap getThumbnail(ContentResolver contentResolver, MediaInfo mediaInfo, int i, int i2) throws FileNotFoundException, IOException {
        Bitmap videoThumbnail;
        if (Build.VERSION.SDK_INT >= 29) {
            return contentResolver.loadThumbnail(mediaInfo.getUri(), new Size(i, i2), null);
        }
        if (mediaInfo.mediaType == 1) {
            if (i <= 0 || i2 <= 0) {
                videoThumbnail = BitmapHelper.asBitmap(contentResolver, mediaInfo.getUri(), i, i2);
            } else {
                videoThumbnail = getImageThumbnail(contentResolver, mediaInfo.id, i, i2);
            }
        } else if (mediaInfo.mediaType == 3) {
            videoThumbnail = getVideoThumbnail(contentResolver, mediaInfo.id, i, i2);
        } else {
            throw new UnsupportedOperationException("unexpected mediaType");
        }
        if (videoThumbnail != null) {
            return videoThumbnail;
        }
        throw new IOException("failed to load thumbnail," + mediaInfo);
    }

    public Bitmap getImageThumbnail(ContentResolver contentResolver, long j, int i, int i2) throws FileNotFoundException, IOException {
        Bitmap bitmap;
        Bitmap asBitmap;
        String key = getKey(j);
        synchronized (sSync) {
            bitmap = get(key);
            if (bitmap == null) {
                if (i <= 0 || i2 <= 0) {
                    asBitmap = BitmapHelper.asBitmap(contentResolver, j, i, i2);
                } else {
                    try {
                        asBitmap = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, j, (i > 96 || i2 > 96 || i * i2 > 16384) ? 1 : 3, null);
                    } catch (Exception e) {
                        remove(key);
                        if (e instanceof IOException) {
                            throw ((IOException) e);
                        }
                        throw new IOException(e);
                    }
                }
                if (asBitmap != null) {
                    int orientation = BitmapHelper.getOrientation(contentResolver, j);
                    if (orientation != 0) {
                        Bitmap rotateBitmap = BitmapHelper.rotateBitmap(asBitmap, orientation);
                        asBitmap.recycle();
                        bitmap = rotateBitmap;
                    } else {
                        bitmap = asBitmap;
                    }
                    put(key, bitmap, false);
                } else {
                    throw new IOException("failed to get thumbnail,key=" + key + "/id=" + j);
                }
            }
        }
        return bitmap;
    }

    public Bitmap getVideoThumbnail(ContentResolver contentResolver, long j, int i, int i2) throws FileNotFoundException, IOException {
        Bitmap bitmap;
        String key = getKey(j);
        synchronized (sSync) {
            bitmap = get(key);
            if (bitmap == null) {
                try {
                    Bitmap thumbnail = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, j, (i > 96 || i2 > 96 || i * i2 > 16384) ? 1 : 3, null);
                    if (thumbnail != null) {
                        int orientation = BitmapHelper.getOrientation(contentResolver, j);
                        if (orientation != 0) {
                            Bitmap rotateBitmap = BitmapHelper.rotateBitmap(thumbnail, orientation);
                            thumbnail.recycle();
                            bitmap = rotateBitmap;
                        } else {
                            bitmap = thumbnail;
                        }
                        put(key, bitmap, false);
                    } else {
                        throw new IOException("failed to get thumbnail,key=" + key + "/id=" + j);
                    }
                } catch (Exception e) {
                    remove(key);
                    if (e instanceof IOException) {
                        throw ((IOException) e);
                    }
                    throw new IOException(e);
                }
            }
        }
        return bitmap;
    }

    private static String getKey(long j) {
        return String.format(Locale.US, "%x", Long.valueOf(j));
    }
}
