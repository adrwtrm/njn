package com.epson.iprojection.common.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import com.epson.iprojection.ui.activities.marker.ImageSaver;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ImageIOUtils.kt */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u000e\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0003H\u0002\u001a(\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0007\u001a(\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0007¨\u0006\u0011"}, d2 = {"getCompressFormat", "Landroid/graphics/Bitmap$CompressFormat;", "filename", "", "getImageMimeType", "isEndWithIgnoreCase", "", "suffix", "save", "context", "Landroid/content/Context;", "bitmap", "Landroid/graphics/Bitmap;", "relativePath", "fileName", "buffer", "Ljava/nio/ByteBuffer;", "app_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ImageIOUtilsKt {
    public static final boolean save(Context context, ByteBuffer buffer, String relativePath, String fileName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        Intrinsics.checkNotNullParameter(relativePath, "relativePath");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "context.contentResolver");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", fileName);
        contentValues.put("mime_type", getImageMimeType(fileName));
        contentValues.put("relative_path", relativePath);
        contentValues.put("is_pending", (Integer) 1);
        Uri insert = contentResolver.insert(MediaStore.Images.Media.getContentUri("external_primary"), contentValues);
        if (insert == null) {
            return false;
        }
        OutputStream openOutputStream = contentResolver.openOutputStream(insert);
        try {
            WritableByteChannel newChannel = Channels.newChannel(openOutputStream);
            Intrinsics.checkNotNullExpressionValue(newChannel, "newChannel(out)");
            newChannel.write(buffer);
            CloseableKt.closeFinally(openOutputStream, null);
            contentValues.clear();
            contentValues.put("is_pending", (Integer) 0);
            contentResolver.update(insert, contentValues, null, null);
            return true;
        } finally {
        }
    }

    public static final boolean save(Context context, Bitmap bitmap, String relativePath, String fileName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(relativePath, "relativePath");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "context.contentResolver");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", fileName);
        contentValues.put("mime_type", getImageMimeType(fileName));
        contentValues.put("relative_path", relativePath);
        contentValues.put("is_pending", (Integer) 1);
        Uri insert = contentResolver.insert(MediaStore.Images.Media.getContentUri("external_primary"), contentValues);
        if (insert == null) {
            return false;
        }
        OutputStream openOutputStream = contentResolver.openOutputStream(insert);
        try {
            if (!bitmap.compress(getCompressFormat(fileName), 100, openOutputStream)) {
                CloseableKt.closeFinally(openOutputStream, null);
                return false;
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(openOutputStream, null);
            contentValues.clear();
            contentValues.put("is_pending", (Integer) 0);
            contentResolver.update(insert, contentValues, null, null);
            return true;
        } finally {
        }
    }

    public static final String getImageMimeType(String filename) {
        Intrinsics.checkNotNullParameter(filename, "filename");
        if (isEndWithIgnoreCase(filename, ImageSaver.FILE_TYPE)) {
            return "image/png";
        }
        if (isEndWithIgnoreCase(filename, ".jpg") || isEndWithIgnoreCase(filename, ".jpeg")) {
            return "image/jpeg";
        }
        throw new RuntimeException("保存する拡張子が間違っています(mimetype)");
    }

    private static final Bitmap.CompressFormat getCompressFormat(String str) {
        if (isEndWithIgnoreCase(str, ImageSaver.FILE_TYPE)) {
            return Bitmap.CompressFormat.PNG;
        }
        if (isEndWithIgnoreCase(str, ".jpg") || isEndWithIgnoreCase(str, ".jpeg")) {
            return Bitmap.CompressFormat.JPEG;
        }
        throw new RuntimeException("保存する拡張子が間違っています(compressformat)");
    }

    private static final boolean isEndWithIgnoreCase(String str, String str2) {
        String substring = str.substring(str.length() - str2.length(), str.length());
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return StringsKt.equals(substring, str2, true);
    }
}
