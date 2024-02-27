package com.serenegiant.mediastore;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.utils.UriHelper;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class MediaStoreOutputStream extends OutputStream {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaStoreOutputStream";
    private final Object closeLock;
    private volatile boolean closed;
    private final ContentResolver mCr;
    private final String mOutputPath;
    private final FileOutputStream mOutputStream;
    private final Uri mUri;

    @Deprecated
    public MediaStoreOutputStream(Context context, String str, String str2) throws FileNotFoundException {
        this(context, str, null, str2);
    }

    @Deprecated
    public MediaStoreOutputStream(Context context, String str, String str2, String str3) throws FileNotFoundException {
        this.closeLock = new Object();
        this.closed = false;
        ContentResolver contentResolver = context.getContentResolver();
        this.mCr = contentResolver;
        Uri contentUri = MediaStoreUtils.getContentUri(contentResolver, str, str2, str3, (String) null);
        this.mUri = contentUri;
        this.mOutputStream = new FileOutputStream(contentResolver.openFileDescriptor(contentUri, "w").getFileDescriptor());
        this.mOutputPath = UriHelper.getPath(context, contentUri);
    }

    @Deprecated
    public MediaStoreOutputStream(Context context, String str, String str2, String str3, String str4) throws FileNotFoundException {
        this.closeLock = new Object();
        this.closed = false;
        ContentResolver contentResolver = context.getContentResolver();
        this.mCr = contentResolver;
        Uri contentUri = MediaStoreUtils.getContentUri(contentResolver, str, str2, str3, str4);
        this.mUri = contentUri;
        this.mOutputStream = new FileOutputStream(contentResolver.openFileDescriptor(contentUri, "rw").getFileDescriptor());
        this.mOutputPath = UriHelper.getPath(context, contentUri);
    }

    public MediaStoreOutputStream(Context context, DocumentFile documentFile) throws FileNotFoundException {
        this.closeLock = new Object();
        this.closed = false;
        ContentResolver contentResolver = context.getContentResolver();
        this.mCr = contentResolver;
        Uri uri = documentFile.getUri();
        this.mUri = uri;
        this.mOutputStream = new FileOutputStream(contentResolver.openFileDescriptor(uri, "rw").getFileDescriptor());
        this.mOutputPath = UriHelper.getPath(context, uri);
    }

    public Uri getUri() {
        return this.mUri;
    }

    @Deprecated
    public String getOutputPath() {
        return this.mOutputPath;
    }

    public FileDescriptor getFd() throws IOException {
        return this.mOutputStream.getFD();
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        this.mOutputStream.write(i);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        this.mOutputStream.write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (this.closed && i2 > 0) {
            throw new IOException("Stream Closed");
        }
        this.mOutputStream.write(bArr, i, i2);
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.mOutputStream.flush();
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.closeLock) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            try {
                this.mOutputStream.close();
            } finally {
                if (UriHelper.isContentUri(this.mUri)) {
                    MediaStoreUtils.updateContentUri(this.mCr, this.mUri);
                }
            }
        }
    }
}
