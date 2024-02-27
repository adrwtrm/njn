package com.epson.iprojection.ui.activities.fileselect;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.fileselect.FileSelectContract;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FileSelectUseCase.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J \u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u0016J*\u0010\u0017\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\bH\u0002J\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001a\u0010\u001d\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\u0010\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010!\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001a\u0010\"\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001a\u0010#\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001a\u0010$\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectUseCase;", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$UseCase;", "mContext", "Landroid/content/Context;", "mListener", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$ReceiveDataProgressListener;", "(Landroid/content/Context;Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$ReceiveDataProgressListener;)V", "NODATA", "", "mIsCanceled", "", "cancelReceiving", "", "cleanDirectory", "directoryPath", "", "copy", "input", "Ljava/io/InputStream;", "output", "Ljava/io/OutputStream;", "bufferSize", "", "copyFileFromUri", "context", "uri", "Landroid/net/Uri;", "lastModified", "getFileNameFromUri", "getLastModified", "isSingleFileSelected", "intent", "Landroid/content/Intent;", "prepareCleanDirectory", "receiveFiles", "receiveMultipleFiles", "receiveSingleFile", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class FileSelectUseCase implements FileSelectContract.UseCase {
    private final long NODATA;
    private final Context mContext;
    private boolean mIsCanceled;
    private final FileSelectContract.ReceiveDataProgressListener mListener;

    public FileSelectUseCase(Context mContext, FileSelectContract.ReceiveDataProgressListener mListener) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mListener, "mListener");
        this.mContext = mContext;
        this.mListener = mListener;
        this.NODATA = -1L;
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.UseCase
    public String receiveFiles(Intent intent, String directoryPath) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(directoryPath, "directoryPath");
        if (isSingleFileSelected(intent)) {
            return receiveSingleFile(intent, directoryPath);
        }
        return receiveMultipleFiles(intent, directoryPath);
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.UseCase
    public void cancelReceiving() {
        this.mIsCanceled = true;
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.UseCase
    public void cleanDirectory(String directoryPath) {
        File[] listFiles;
        Intrinsics.checkNotNullParameter(directoryPath, "directoryPath");
        File file = new File(directoryPath);
        if (file.exists() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                file2.delete();
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.UseCase
    public void prepareCleanDirectory(String directoryPath) {
        Intrinsics.checkNotNullParameter(directoryPath, "directoryPath");
        File file = new File(directoryPath);
        if (!file.exists()) {
            file.mkdir();
        }
        cleanDirectory(directoryPath);
    }

    private final boolean isSingleFileSelected(Intent intent) {
        return intent.getClipData() == null;
    }

    private final String receiveSingleFile(Intent intent, String str) {
        Uri data = intent.getData();
        if (data != null) {
            return copyFileFromUri(this.mContext, data, str, this.NODATA);
        }
        return null;
    }

    private final String receiveMultipleFiles(Intent intent, String str) {
        ClipData.Item itemAt;
        ClipData clipData = intent.getClipData();
        Integer valueOf = clipData != null ? Integer.valueOf(clipData.getItemCount()) : null;
        if (valueOf != null) {
            int i = 0;
            this.mListener.onUpdatedReceiveDataProgress(0, valueOf.intValue());
            int intValue = valueOf.intValue();
            String str2 = null;
            while (i < intValue) {
                ClipData clipData2 = intent.getClipData();
                Uri uri = (clipData2 == null || (itemAt = clipData2.getItemAt(i)) == null) ? null : itemAt.getUri();
                String copyFileFromUri = uri != null ? copyFileFromUri(this.mContext, uri, str, getLastModified(this.mContext, uri)) : null;
                if (copyFileFromUri != null) {
                    str2 = copyFileFromUri;
                }
                i++;
                this.mListener.onUpdatedReceiveDataProgress(i, valueOf.intValue());
                if (this.mIsCanceled) {
                    return null;
                }
            }
            return str2;
        }
        return null;
    }

    private final long getLastModified(Context context, Uri uri) {
        long j;
        if (uri == null) {
            return this.NODATA;
        }
        Cursor query = context.getContentResolver().query(uri, null, null, null, null);
        if (query != null) {
            Cursor cursor = query;
            try {
                Cursor cursor2 = cursor;
                try {
                    int columnIndexOrThrow = cursor2.getColumnIndexOrThrow("last_modified");
                    cursor2.moveToFirst();
                    j = cursor2.getLong(columnIndexOrThrow);
                } catch (Exception unused) {
                    j = this.NODATA;
                }
                CloseableKt.closeFinally(cursor, null);
                return j;
            } finally {
            }
        } else {
            return this.NODATA;
        }
    }

    private final String copyFileFromUri(Context context, Uri uri, String str, long j) {
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            String str2 = str + getFileNameFromUri(context, uri);
            File file = new File(str2);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            if (openInputStream != null) {
                copy$default(this, openInputStream, fileOutputStream, 0, 4, null);
            }
            if (openInputStream != null) {
                openInputStream.close();
            }
            fileOutputStream.close();
            if (j != this.NODATA) {
                file.setLastModified(j);
            }
            if (this.mIsCanceled) {
                file.delete();
                String str3 = null;
                return null;
            }
            return str2;
        } catch (Exception unused) {
            Lg.e("stream file でエラー発生");
            return null;
        }
    }

    public static /* synthetic */ long copy$default(FileSelectUseCase fileSelectUseCase, InputStream inputStream, OutputStream outputStream, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 8192;
        }
        return fileSelectUseCase.copy(inputStream, outputStream, i);
    }

    public final long copy(InputStream input, OutputStream output, int i) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(output, "output");
        byte[] bArr = new byte[i];
        int read = input.read(bArr);
        long j = 0;
        while (read >= 0) {
            output.write(bArr, 0, read);
            j += read;
            read = input.read(bArr);
            if (this.mIsCanceled) {
                return -1L;
            }
        }
        return j;
    }

    private final String getFileNameFromUri(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
        String string = (query == null || !query.moveToFirst()) ? null : query.getString(query.getColumnIndexOrThrow("_display_name"));
        if (query != null) {
            query.close();
        }
        return string;
    }
}
