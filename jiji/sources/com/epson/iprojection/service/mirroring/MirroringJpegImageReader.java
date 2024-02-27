package com.epson.iprojection.service.mirroring;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringUtils;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringJpegImageReader.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00192\u00020\u00012\u00020\u0002:\u0001\u0019B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0012\u001a\u00020\tH\u0002J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\tH\u0016J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\rH\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringJpegImageReader;", "Lcom/epson/iprojection/service/mirroring/MirroringJpeg;", "Landroid/media/ImageReader$OnImageAvailableListener;", "()V", "_bitmapForCapture", "Landroid/graphics/Bitmap;", "_bmpByteArray", "", "_imageReader", "Landroid/media/ImageReader;", "_imageReaderThread", "Landroid/os/HandlerThread;", "createBitmap", "", "width", "", "height", "getBitmapFromImageReader", "imageReader", "onImageAvailable", "reader", "startImage", "context", "Landroid/content/Context;", "stopImage", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringJpegImageReader extends MirroringJpeg implements ImageReader.OnImageAvailableListener {
    public static final int BYTE_PER_PIXEL = 4;
    public static final Companion Companion = new Companion(null);
    private Bitmap _bitmapForCapture;
    private byte[] _bmpByteArray;
    private ImageReader _imageReader;
    private HandlerThread _imageReaderThread;

    @Override // com.epson.iprojection.service.mirroring.MirroringJpeg
    public void startImage(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        notifyContentRect();
        Size virtualDisplayResolution = MirroringUtils.Companion.getVirtualDisplayResolution(context, new Size(Pj.getIns().getPjResWidth(), Pj.getIns().getPjResHeight()));
        createBitmap(virtualDisplayResolution.getWidth(), virtualDisplayResolution.getHeight());
        HandlerThread handlerThread = new HandlerThread("image receive thread");
        this._imageReaderThread = handlerThread;
        Intrinsics.checkNotNull(handlerThread);
        handlerThread.start();
        ImageReader newInstance = ImageReader.newInstance(virtualDisplayResolution.getWidth(), virtualDisplayResolution.getHeight(), 1, 5);
        this._imageReader = newInstance;
        Intrinsics.checkNotNull(newInstance);
        HandlerThread handlerThread2 = this._imageReaderThread;
        Intrinsics.checkNotNull(handlerThread2);
        newInstance.setOnImageAvailableListener(this, new Handler(handlerThread2.getLooper()));
        MediaProjection mediaProjection = get_mediaProjection();
        Intrinsics.checkNotNull(mediaProjection);
        int width = virtualDisplayResolution.getWidth();
        int height = virtualDisplayResolution.getHeight();
        int i = context.getResources().getDisplayMetrics().densityDpi;
        ImageReader imageReader = this._imageReader;
        Intrinsics.checkNotNull(imageReader);
        set_virtualDisplay(mediaProjection.createVirtualDisplay("Capturing Display", width, height, i, 16, imageReader.getSurface(), null, null));
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringJpeg
    public void stopImage() {
        ImageReader imageReader = this._imageReader;
        if (imageReader != null) {
            Intrinsics.checkNotNull(imageReader);
            imageReader.setOnImageAvailableListener(null, null);
            this._imageReader = null;
        }
        HandlerThread handlerThread = this._imageReaderThread;
        if (handlerThread != null) {
            Intrinsics.checkNotNull(handlerThread);
            handlerThread.quitSafely();
            this._imageReaderThread = null;
        }
        if (get_virtualDisplay() != null) {
            VirtualDisplay virtualDisplay = get_virtualDisplay();
            Intrinsics.checkNotNull(virtualDisplay);
            virtualDisplay.release();
            set_virtualDisplay(null);
        }
    }

    @Override // android.media.ImageReader.OnImageAvailableListener
    public void onImageAvailable(ImageReader reader) {
        Intrinsics.checkNotNullParameter(reader, "reader");
        if (get_isAVMuting() || get_isStopped() || get_isRotating()) {
            Lg.e("return isPause:" + get_isAVMuting() + " isStopped:" + get_isStopped() + " isRotating:" + get_isRotating());
            reader.acquireNextImage().close();
            return;
        }
        try {
            Bitmap bitmapFromImageReader = getBitmapFromImageReader(reader);
            if (bitmapFromImageReader == null || get_isAVMuting() || get_isStopped()) {
                return;
            }
            Pj.getIns().sendMirroringImage(bitmapFromImageReader);
        } catch (BitmapMemoryException unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0024, code lost:
        if (r0 != r1.getHeight()) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final android.graphics.Bitmap getBitmapFromImageReader(android.media.ImageReader r4) {
        /*
            r3 = this;
            android.media.Image r4 = r4.acquireNextImage()
            if (r4 != 0) goto L8
            r4 = 0
            return r4
        L8:
            int r0 = r4.getWidth()
            android.graphics.Bitmap r1 = r3._bitmapForCapture
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            int r1 = r1.getWidth()
            if (r0 != r1) goto L26
            int r0 = r4.getHeight()
            android.graphics.Bitmap r1 = r3._bitmapForCapture
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            int r1 = r1.getHeight()
            if (r0 == r1) goto L39
        L26:
            java.lang.String r0 = "サイズが合わないのでBitmapを生成しなおす"
            com.epson.iprojection.common.Lg.e(r0)
            int r0 = r4.getWidth()
            int r1 = r4.getHeight()
            r3.createBitmap(r0, r1)
            r3.notifyContentRect()
        L39:
            com.epson.iprojection.common.utils.ImageReaderUtils r0 = com.epson.iprojection.common.utils.ImageReaderUtils.INSTANCE     // Catch: java.lang.Exception -> L66
            r1 = 4
            java.nio.ByteBuffer r0 = r0.getBitmapBuffer(r4, r1)     // Catch: java.lang.Exception -> L66
            if (r0 == 0) goto L4d
            android.graphics.Bitmap r1 = r3._bitmapForCapture     // Catch: java.lang.Exception -> L66
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)     // Catch: java.lang.Exception -> L66
            java.nio.Buffer r0 = (java.nio.Buffer) r0     // Catch: java.lang.Exception -> L66
            r1.copyPixelsFromBuffer(r0)     // Catch: java.lang.Exception -> L66
            goto L66
        L4d:
            com.epson.iprojection.common.utils.ImageReaderUtils r0 = com.epson.iprojection.common.utils.ImageReaderUtils.INSTANCE     // Catch: java.lang.Exception -> L66
            byte[] r2 = r3._bmpByteArray     // Catch: java.lang.Exception -> L66
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)     // Catch: java.lang.Exception -> L66
            byte[] r0 = r0.getBitmapArray(r4, r1, r2)     // Catch: java.lang.Exception -> L66
            android.graphics.Bitmap r1 = r3._bitmapForCapture     // Catch: java.lang.Exception -> L66
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)     // Catch: java.lang.Exception -> L66
            java.nio.ByteBuffer r0 = java.nio.ByteBuffer.wrap(r0)     // Catch: java.lang.Exception -> L66
            java.nio.Buffer r0 = (java.nio.Buffer) r0     // Catch: java.lang.Exception -> L66
            r1.copyPixelsFromBuffer(r0)     // Catch: java.lang.Exception -> L66
        L66:
            r4.close()
            android.graphics.Bitmap r4 = r3._bitmapForCapture
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.service.mirroring.MirroringJpegImageReader.getBitmapFromImageReader(android.media.ImageReader):android.graphics.Bitmap");
    }

    private final void createBitmap(int i, int i2) {
        Lg.d("[" + i + ',' + i2 + "]でBitmapを生成");
        this._bmpByteArray = new byte[i * i2 * 4];
        this._bitmapForCapture = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
    }

    /* compiled from: MirroringJpegImageReader.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringJpegImageReader$Companion;", "", "()V", "BYTE_PER_PIXEL", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
