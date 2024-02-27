package com.epson.iprojection.common.utils;

import android.media.Image;
import com.epson.iprojection.common.Lg;
import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageReaderUtils.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J \u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0002J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0018\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/common/utils/ImageReaderUtils;", "", "()V", "getBitmapArray", "", "image", "Landroid/media/Image;", "bytePerPixel", "", "bmpArray", "getBitmapArrayWithRemovedPixelMargin", "getBitmapArrayWithRemovedRowMargin", "getBitmapBuffer", "Ljava/nio/ByteBuffer;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ImageReaderUtils {
    public static final ImageReaderUtils INSTANCE = new ImageReaderUtils();

    private ImageReaderUtils() {
    }

    public final ByteBuffer getBitmapBuffer(Image image, int i) {
        Intrinsics.checkNotNullParameter(image, "image");
        Image.Plane plane = image.getPlanes()[0];
        if (image.getWidth() == plane.getRowStride() / plane.getPixelStride()) {
            return plane.getBuffer();
        }
        return null;
    }

    public final byte[] getBitmapArray(Image image, int i, byte[] bmpArray) {
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(bmpArray, "bmpArray");
        Image.Plane plane = image.getPlanes()[0];
        if (plane.getPixelStride() == i) {
            Lg.d("plane.pixelStride == bytePerPixel");
            return getBitmapArrayWithRemovedRowMargin(image, i, bmpArray);
        } else if (plane.getPixelStride() > i) {
            Lg.e("plane.pixelStride > bytePerPixel");
            return getBitmapArrayWithRemovedPixelMargin(image, i, bmpArray);
        } else {
            Lg.e("error!!");
            return null;
        }
    }

    private final byte[] getBitmapArrayWithRemovedRowMargin(Image image, int i, byte[] bArr) {
        Image.Plane plane = image.getPlanes()[0];
        ByteBuffer buffer = plane.getBuffer();
        int width = image.getWidth() * i;
        int height = image.getHeight();
        for (int i2 = 0; i2 < height; i2++) {
            buffer.position(plane.getRowStride() * i2);
            buffer.get(bArr, width * i2, width);
        }
        return bArr;
    }

    private final byte[] getBitmapArrayWithRemovedPixelMargin(Image image, int i, byte[] bArr) {
        Image.Plane plane = image.getPlanes()[0];
        ByteBuffer buffer = plane.getBuffer();
        int width = image.getWidth() * i;
        int height = image.getHeight();
        for (int i2 = 0; i2 < height; i2++) {
            int width2 = image.getWidth();
            for (int i3 = 0; i3 < width2; i3++) {
                buffer.position((plane.getRowStride() * i2) + (plane.getPixelStride() * i3));
                buffer.get(bArr, (width * i2) + (i * i3), i);
            }
        }
        return bArr;
    }
}
