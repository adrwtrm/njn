package com.epson.iprojection.service.mirroring;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.HandlerThread;
import com.epson.iprojection.common.utils.ImageReaderUtils;
import com.epson.iprojection.service.webrtc.WebRTCEntrance;
import dagger.hilt.android.plugin.AndroidEntryPointClassVisitor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ThumbnailCapturer.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016J\u0010\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\tH\u0016J\u0018\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0002J\u0010\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020\u000bH\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/epson/iprojection/service/mirroring/ThumbnailCapturer;", "Landroid/media/ImageReader$OnImageAvailableListener;", "Landroid/media/projection/MediaProjection$Callback;", "context", "Landroid/content/Context;", "captureIntent", "Landroid/content/Intent;", AndroidEntryPointClassVisitor.ON_RECEIVE_METHOD_DESCRIPTOR, "_imageReader", "Landroid/media/ImageReader;", "_isCaptured", "", "_mediaProjection", "Landroid/media/projection/MediaProjection;", "_pixelArray", "", "_thread", "Landroid/os/HandlerThread;", "_virtualDisplay", "Landroid/hardware/display/VirtualDisplay;", "createThumbnail", "width", "", "height", "getBitmap", "image", "Landroid/media/Image;", "onImageAvailable", "", "reader", "startMediaProjection", "stopMediaProjection", "isMediaProjectionGotFromService", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ThumbnailCapturer extends MediaProjection.Callback implements ImageReader.OnImageAvailableListener {
    private ImageReader _imageReader;
    private boolean _isCaptured;
    private MediaProjection _mediaProjection;
    private byte[] _pixelArray;
    private HandlerThread _thread;
    private VirtualDisplay _virtualDisplay;
    private final Intent captureIntent;
    private final Context context;

    public static /* synthetic */ void $r8$lambda$ab6yHUGyxNwn_hXTdrvu2OmFs54(ThumbnailCapturer thumbnailCapturer, int i, int i2) {
        createThumbnail$lambda$0(thumbnailCapturer, i, i2);
    }

    public ThumbnailCapturer(Context context, Intent captureIntent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(captureIntent, "captureIntent");
        this.context = context;
        this.captureIntent = captureIntent;
    }

    public final synchronized byte[] createThumbnail(final int i, final int i2) {
        boolean z;
        this._isCaptured = false;
        this._pixelArray = null;
        MediaProjection mediaProjection = WebRTCEntrance.INSTANCE.getMediaProjection();
        this._mediaProjection = mediaProjection;
        if (mediaProjection == null) {
            Object systemService = this.context.getSystemService("media_projection");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
            MediaProjection mediaProjection2 = ((MediaProjectionManager) systemService).getMediaProjection(-1, this.captureIntent);
            this._mediaProjection = mediaProjection2;
            if (mediaProjection2 == null) {
                return null;
            }
            z = true;
        } else {
            z = false;
        }
        new Thread(new Runnable() { // from class: com.epson.iprojection.service.mirroring.ThumbnailCapturer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ThumbnailCapturer.$r8$lambda$ab6yHUGyxNwn_hXTdrvu2OmFs54(ThumbnailCapturer.this, i, i2);
            }
        }).start();
        for (int i3 = 0; i3 < 5001; i3++) {
            Thread.sleep(1L);
            if (this._isCaptured) {
                break;
            }
        }
        stopMediaProjection(z);
        return this._pixelArray;
    }

    public static final void createThumbnail$lambda$0(ThumbnailCapturer this$0, int i, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startMediaProjection(i, i2);
    }

    private final void startMediaProjection(int i, int i2) {
        HandlerThread handlerThread = new HandlerThread("thumbnail image receive thread");
        this._thread = handlerThread;
        Intrinsics.checkNotNull(handlerThread);
        handlerThread.start();
        ImageReader newInstance = ImageReader.newInstance(i, i2, 1, 1);
        this._imageReader = newInstance;
        Intrinsics.checkNotNull(newInstance);
        HandlerThread handlerThread2 = this._thread;
        Intrinsics.checkNotNull(handlerThread2);
        newInstance.setOnImageAvailableListener(this, new Handler(handlerThread2.getLooper()));
        HandlerThread handlerThread3 = new HandlerThread("thumbnail image onAvailable thread");
        handlerThread3.start();
        MediaProjection mediaProjection = this._mediaProjection;
        Intrinsics.checkNotNull(mediaProjection);
        mediaProjection.registerCallback(this, new Handler(handlerThread3.getLooper()));
        int i3 = this.context.getResources().getDisplayMetrics().densityDpi;
        MediaProjection mediaProjection2 = this._mediaProjection;
        Intrinsics.checkNotNull(mediaProjection2);
        ImageReader imageReader = this._imageReader;
        Intrinsics.checkNotNull(imageReader);
        this._virtualDisplay = mediaProjection2.createVirtualDisplay("Capturing Thumbnail Display", i, i2, i3, 16, imageReader.getSurface(), null, null);
    }

    private final void stopMediaProjection(boolean z) {
        ImageReader imageReader = this._imageReader;
        if (imageReader != null) {
            Intrinsics.checkNotNull(imageReader);
            imageReader.setOnImageAvailableListener(null, null);
            this._imageReader = null;
        }
        MediaProjection mediaProjection = this._mediaProjection;
        if (mediaProjection != null) {
            Intrinsics.checkNotNull(mediaProjection);
            mediaProjection.unregisterCallback(this);
            if (z) {
                MediaProjection mediaProjection2 = this._mediaProjection;
                Intrinsics.checkNotNull(mediaProjection2);
                mediaProjection2.stop();
            }
            this._mediaProjection = null;
        }
        VirtualDisplay virtualDisplay = this._virtualDisplay;
        if (virtualDisplay != null) {
            Intrinsics.checkNotNull(virtualDisplay);
            virtualDisplay.release();
            this._virtualDisplay = null;
        }
        HandlerThread handlerThread = this._thread;
        if (handlerThread != null) {
            Intrinsics.checkNotNull(handlerThread);
            handlerThread.quitSafely();
            this._thread = null;
        }
    }

    @Override // android.media.ImageReader.OnImageAvailableListener
    public void onImageAvailable(ImageReader reader) {
        Intrinsics.checkNotNullParameter(reader, "reader");
        if (this._isCaptured) {
            return;
        }
        Image acquireLatestImage = reader.acquireLatestImage();
        Intrinsics.checkNotNullExpressionValue(acquireLatestImage, "reader.acquireLatestImage()");
        this._pixelArray = getBitmap(acquireLatestImage);
        acquireLatestImage.close();
        this._isCaptured = true;
    }

    private final byte[] getBitmap(Image image) {
        byte[] bArr = new byte[image.getWidth() * image.getHeight() * 4];
        ImageReaderUtils.INSTANCE.getBitmapArray(image, 4, bArr);
        return bArr;
    }
}
