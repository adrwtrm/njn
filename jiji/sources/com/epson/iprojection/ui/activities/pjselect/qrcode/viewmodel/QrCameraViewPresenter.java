package com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.CameraUtils;
import com.epson.iprojection.databinding.MainQrcodeCameraBinding;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.linkagedata.decoder.LinkageDataCommonDecoder;
import com.epson.iprojection.linkagedata.exception.LinkageDataFormatException;
import com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract;
import com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.serenegiant.view.MessagePanelUtils;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class QrCameraViewPresenter implements IOnFoundQRCode, QrcodeContract.IPresenter {
    private static final String CAMERA_BACK = "0";
    private static final int CAPTURE_DELAY_TIME = 100;
    private static final int CAPTURE_IMAGE_FORMAT = 35;
    private static final int STATE_CLOSED = 0;
    private static final int STATE_OPENED = 1;
    private static final int TEXTURE_VIEW_MAX_HEIGHT = 768;
    private static final int TEXTURE_VIEW_MAX_WIDTH = 1024;
    private final Activity _activity;
    private Handler _backgroundHandler;
    private HandlerThread _backgroundThread;
    private final MainQrcodeCameraBinding _binding;
    private CameraCharacteristics _cameraCharacteristics;
    private CameraDevice _cameraDevice;
    private CameraManager _cameraManager;
    private final QrcodeContract.IView _contractView;
    private DisplayManager.DisplayListener _displayListener;
    private DisplayManager _displayManager;
    private ImageReader _frameImageReader;
    private final OrientationEventListener _orientationListener;
    private CaptureRequest.Builder _previewBuilder;
    private CameraCaptureSession _previewSession;
    private Size _previewSize;
    private final ExecutorService _threadExecutor;
    private final Object _cameraStateLock = new Object();
    private float mCurrentZoomLevel = 1.0f;
    private Rect _cropRegion = null;
    private String _cameraId = CAMERA_BACK;
    private int _lastOrientationNum = 0;
    private boolean _isPlaying = true;
    private int _cameraState = 0;
    private final TextureView.SurfaceTextureListener _surfaceTextureListener = new TextureView.SurfaceTextureListener() { // from class: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter.1
        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            QrCameraViewPresenter.this.configureTransform(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            QrCameraViewPresenter.this.configureTransform(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            synchronized (QrCameraViewPresenter.this._cameraStateLock) {
                QrCameraViewPresenter.this._previewSize = null;
            }
            return true;
        }
    };
    private final Semaphore _cameraOpenCloseLock = new Semaphore(1);
    private final CameraDevice.StateCallback _stateCallback = new AnonymousClass2();
    private final ImageReader.OnImageAvailableListener mOnJpegImageAvailableListener = new AnonymousClass3();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 extends CameraDevice.StateCallback {
        AnonymousClass2() {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(CameraDevice cameraDevice) {
            synchronized (QrCameraViewPresenter.this._cameraStateLock) {
                QrCameraViewPresenter.this._cameraState = 1;
                QrCameraViewPresenter.this._cameraOpenCloseLock.release();
                QrCameraViewPresenter.this._cameraDevice = cameraDevice;
                QrCameraViewPresenter.this._activity.runOnUiThread(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        QrCameraViewPresenter.AnonymousClass2.this.m152x3492fc19();
                    }
                });
                if (QrCameraViewPresenter.this._previewSize != null && QrCameraViewPresenter.this._binding.qrSurfaceview.isAvailable()) {
                    QrCameraViewPresenter.this.createCameraPreviewSession();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$onOpened$0$com-epson-iprojection-ui-activities-pjselect-qrcode-viewmodel-QrCameraViewPresenter$2  reason: not valid java name */
        public /* synthetic */ void m152x3492fc19() {
            QrCameraViewPresenter.this._activity.getWindow().addFlags(128);
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(CameraDevice cameraDevice) {
            synchronized (QrCameraViewPresenter.this._cameraStateLock) {
                QrCameraViewPresenter.this._cameraState = 0;
                QrCameraViewPresenter.this._cameraOpenCloseLock.release();
                cameraDevice.close();
                QrCameraViewPresenter.this._cameraDevice = null;
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(CameraDevice cameraDevice, int i) {
            synchronized (QrCameraViewPresenter.this._cameraStateLock) {
                QrCameraViewPresenter.this._cameraState = 0;
                QrCameraViewPresenter.this._cameraOpenCloseLock.release();
                cameraDevice.close();
                QrCameraViewPresenter.this._cameraDevice = null;
            }
            if (QrCameraViewPresenter.this._contractView != null) {
                QrCameraViewPresenter.this._contractView.destroy();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass3 implements ImageReader.OnImageAvailableListener {
        AnonymousClass3() {
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(final ImageReader imageReader) {
            if (imageReader == null || QrCameraViewPresenter.this._threadExecutor == null) {
                return;
            }
            try {
                QrCameraViewPresenter.this._threadExecutor.submit(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        QrCameraViewPresenter.AnonymousClass3.this.m153xe117e85f(imageReader);
                    }
                });
            } catch (RejectedExecutionException unused) {
                Lg.e("ExecutorService aborted and shutdown.");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$onImageAvailable$0$com-epson-iprojection-ui-activities-pjselect-qrcode-viewmodel-QrCameraViewPresenter$3  reason: not valid java name */
        public /* synthetic */ void m153xe117e85f(ImageReader imageReader) {
            try {
                Image acquireLatestImage = imageReader.acquireLatestImage();
                if (acquireLatestImage == null) {
                    return;
                }
                if (!QrCameraViewPresenter.this._isPlaying) {
                    acquireLatestImage.close();
                    return;
                }
                ByteBuffer buffer = acquireLatestImage.getPlanes()[0].getBuffer();
                byte[] bArr = new byte[buffer.limit()];
                int pixelStride = acquireLatestImage.getPlanes()[0].getPixelStride();
                buffer.duplicate().get(bArr);
                acquireLatestImage.close();
                int width = QrCameraViewPresenter.this._previewSize.getWidth() + ((acquireLatestImage.getPlanes()[0].getRowStride() - (QrCameraViewPresenter.this._previewSize.getWidth() * pixelStride)) / pixelStride);
                byte[] readQRCode = QrCameraViewPresenter.this.readQRCode(bArr, width, (QrCameraViewPresenter.this._previewSize.getHeight() * QrCameraViewPresenter.this._previewSize.getWidth()) / width);
                if (readQRCode != null) {
                    if (QrCameraViewPresenter.this._isPlaying) {
                        try {
                            QrCameraViewPresenter.this._isPlaying = false;
                            QrCameraViewPresenter.this._previewSession.stopRepeating();
                            QrCameraViewPresenter.this._previewSession.abortCaptures();
                        } catch (CameraAccessException unused) {
                            QrCameraViewPresenter.this.cameraDeviceFailure();
                        }
                        QrCameraViewPresenter.this.onFoundQRCode(readQRCode);
                        return;
                    }
                    return;
                }
                Thread.sleep(100L);
            } catch (LinkageDataFormatException | InterruptedException unused2) {
                QrCameraViewPresenter.this.onFoundQRCode(null);
            }
        }
    }

    public QrCameraViewPresenter(QrcodeContract.IView iView, MainQrcodeCameraBinding mainQrcodeCameraBinding) {
        Activity activityForIntent = iView.getActivityForIntent();
        this._activity = activityForIntent;
        this._contractView = iView;
        this._binding = mainQrcodeCameraBinding;
        this._threadExecutor = Executors.newSingleThreadExecutor();
        this._orientationListener = new OrientationEventListener(activityForIntent, 3) { // from class: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter.4
            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int i) {
                if (QrCameraViewPresenter.this._binding.qrSurfaceview != null && QrCameraViewPresenter.this._binding.qrSurfaceview.isAvailable() && QrCameraViewPresenter.this.isUpdateOrientation()) {
                    QrCameraViewPresenter qrCameraViewPresenter = QrCameraViewPresenter.this;
                    qrCameraViewPresenter.configureTransform(qrCameraViewPresenter._binding.qrSurfaceview.getWidth(), QrCameraViewPresenter.this._binding.qrSurfaceview.getHeight());
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0041 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isUpdateOrientation() {
        /*
            r5 = this;
            android.hardware.camera2.CameraManager r0 = r5._cameraManager
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            android.app.Activity r0 = r5._activity
            android.view.WindowManager r0 = r0.getWindowManager()
            android.view.Display r0 = r0.getDefaultDisplay()
            int r0 = r0.getRotation()
            android.hardware.camera2.CameraCharacteristics r2 = r5._cameraCharacteristics
            int r0 = com.epson.iprojection.common.utils.CameraUtils.getSensorToDeviceRotation(r2, r0)
            r2 = 1
            r3 = 45
            if (r0 <= 0) goto L23
            if (r0 > r3) goto L23
        L21:
            r0 = r1
            goto L3a
        L23:
            r4 = 135(0x87, float:1.89E-43)
            if (r0 <= r3) goto L2b
            if (r0 > r4) goto L2b
            r0 = r2
            goto L3a
        L2b:
            r3 = 225(0xe1, float:3.15E-43)
            if (r0 <= r4) goto L33
            if (r0 > r3) goto L33
            r0 = 2
            goto L3a
        L33:
            if (r0 <= r3) goto L21
            r3 = 315(0x13b, float:4.41E-43)
            if (r0 > r3) goto L21
            r0 = 3
        L3a:
            int r3 = r5._lastOrientationNum
            if (r0 == r3) goto L41
            r5._lastOrientationNum = r0
            return r2
        L41:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter.isUpdateOrientation():boolean");
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IPresenter
    public void onPause() {
        DisplayManager.DisplayListener displayListener;
        this._isPlaying = false;
        this._cameraState = 0;
        CameraCaptureSession cameraCaptureSession = this._previewSession;
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            this._previewSession = null;
        }
        CameraDevice cameraDevice = this._cameraDevice;
        if (cameraDevice != null) {
            cameraDevice.close();
            this._cameraDevice = null;
        }
        ImageReader imageReader = this._frameImageReader;
        if (imageReader != null) {
            imageReader.close();
            this._frameImageReader = null;
        }
        DisplayManager displayManager = this._displayManager;
        if (displayManager != null && (displayListener = this._displayListener) != null) {
            displayManager.unregisterDisplayListener(displayListener);
        }
        this._displayManager = null;
        this._displayListener = null;
        stopBackgroundThread();
        OrientationEventListener orientationEventListener = this._orientationListener;
        if (orientationEventListener != null && orientationEventListener.canDetectOrientation()) {
            this._orientationListener.disable();
        }
        this._cameraOpenCloseLock.release();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IPresenter
    public void onResume() {
        startBackgroundThread();
        openCamera();
        if (this._binding.qrSurfaceview.isAvailable()) {
            configureTransform(this._binding.qrSurfaceview.getWidth(), this._binding.qrSurfaceview.getHeight());
        } else {
            this._binding.qrSurfaceview.setSurfaceTextureListener(this._surfaceTextureListener);
        }
        OrientationEventListener orientationEventListener = this._orientationListener;
        if (orientationEventListener != null && orientationEventListener.canDetectOrientation()) {
            this._orientationListener.enable();
        }
        this._isPlaying = true;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IPresenter
    public boolean getPlayingState() {
        return this._isPlaying;
    }

    private void openCamera() {
        Handler handler;
        if (setUpCameraOutputs()) {
            try {
                if (!this._cameraOpenCloseLock.tryAcquire(MessagePanelUtils.MESSAGE_DURATION_SHORT, TimeUnit.MILLISECONDS)) {
                    throw new RuntimeException("Time out waiting to lock camera opening.");
                }
                synchronized (this._cameraStateLock) {
                    handler = this._backgroundHandler;
                }
                this._cameraManager.openCamera(this._cameraId, this._stateCallback, handler);
            } catch (CameraAccessException | InterruptedException | SecurityException unused) {
                cameraDeviceFailure();
            }
        }
    }

    private boolean setUpCameraOutputs() {
        CameraManager cameraManager;
        try {
            cameraManager = (CameraManager) this._activity.getSystemService("camera");
            this._cameraManager = cameraManager;
        } catch (CameraAccessException | NullPointerException unused) {
            cameraDeviceFailure();
        }
        if (cameraManager == null) {
            throw new CameraAccessException(3, "CameraManager is null.");
        }
        String backCameraId = CameraUtils.getBackCameraId(cameraManager);
        this._cameraId = backCameraId;
        if (backCameraId != null) {
            this._cameraCharacteristics = this._cameraManager.getCameraCharacteristics(backCameraId);
        }
        synchronized (this._cameraStateLock) {
            if (this._frameImageReader == null) {
                Size chooseOptimalSize = CameraUtils.chooseOptimalSize(35, 1024, TEXTURE_VIEW_MAX_HEIGHT, this._cameraManager, this._cameraId);
                ImageReader newInstance = ImageReader.newInstance(chooseOptimalSize.getWidth(), chooseOptimalSize.getHeight(), 35, 5);
                this._frameImageReader = newInstance;
                newInstance.setOnImageAvailableListener(this.mOnJpegImageAvailableListener, this._backgroundHandler);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createCameraPreviewSession() {
        SurfaceTexture surfaceTexture;
        if (this._cameraDevice == null || !this._binding.qrSurfaceview.isAvailable() || this._previewSize == null || (surfaceTexture = this._binding.qrSurfaceview.getSurfaceTexture()) == null) {
            return;
        }
        surfaceTexture.setDefaultBufferSize(this._previewSize.getWidth(), this._previewSize.getHeight());
        Surface surface = new Surface(surfaceTexture);
        try {
            CaptureRequest.Builder createCaptureRequest = this._cameraDevice.createCaptureRequest(1);
            this._previewBuilder = createCaptureRequest;
            createCaptureRequest.addTarget(this._frameImageReader.getSurface());
            this._previewBuilder.addTarget(surface);
            this._previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, 4);
            if (this._cropRegion != null) {
                this._previewBuilder.set(CaptureRequest.SCALER_CROP_REGION, this._cropRegion);
            }
        } catch (CameraAccessException unused) {
            cameraDeviceFailure();
        }
        try {
            this._cameraDevice.createCaptureSession(Arrays.asList(surface, this._frameImageReader.getSurface()), new CameraCaptureSession.StateCallback() { // from class: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter.5
                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    QrCameraViewPresenter.this._previewSession = cameraCaptureSession;
                    if (QrCameraViewPresenter.this._cameraDevice == null) {
                        return;
                    }
                    try {
                        QrCameraViewPresenter.this._previewSession.setRepeatingRequest(QrCameraViewPresenter.this._previewBuilder.build(), null, QrCameraViewPresenter.this._backgroundHandler);
                    } catch (CameraAccessException unused2) {
                        QrCameraViewPresenter.this.cameraDeviceFailure();
                    }
                }

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    QrCameraViewPresenter.this.cameraDeviceFailure();
                }
            }, null);
        } catch (CameraAccessException unused2) {
            cameraDeviceFailure();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configureTransform(final int i, final int i2) {
        if (this._binding.qrSurfaceview == null) {
            return;
        }
        this._activity.runOnUiThread(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewPresenter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QrCameraViewPresenter.this.m151x1798ef89(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$configureTransform$0$com-epson-iprojection-ui-activities-pjselect-qrcode-viewmodel-QrCameraViewPresenter  reason: not valid java name */
    public /* synthetic */ void m151x1798ef89(int i, int i2) {
        Size size;
        Size chooseOptimalSize = CameraUtils.chooseOptimalSize(35, 1024, TEXTURE_VIEW_MAX_HEIGHT, this._cameraManager, this._cameraId);
        int rotation = this._activity.getWindowManager().getDefaultDisplay().getRotation();
        int sensorToDeviceRotation = CameraUtils.getSensorToDeviceRotation(this._cameraCharacteristics, rotation);
        boolean z = false;
        boolean z2 = sensorToDeviceRotation == 90 || sensorToDeviceRotation == 270;
        if (rotation == 1 || rotation == 3) {
            z = true;
        }
        int width = chooseOptimalSize.getWidth();
        int height = chooseOptimalSize.getHeight();
        int i3 = i;
        int i4 = i2;
        RectF rectF = new RectF(0.0f, 0.0f, i3, i4);
        float f = height;
        float f2 = width;
        RectF rectF2 = new RectF(0.0f, 0.0f, f, f2);
        if (z2 && z) {
            rectF2 = new RectF(0.0f, 0.0f, f2, f);
        } else {
            i4 = i3;
            i3 = i4;
        }
        Matrix matrix = new Matrix();
        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        if (z) {
            size = chooseOptimalSize;
            rectF2.offset(centerX - rectF2.centerX(), centerY - rectF2.centerY());
            matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.FILL);
            float max = Math.max(i3 / f, i4 / f2);
            matrix.postScale(max, max, centerX, centerY);
            rotation -= 2;
        } else {
            size = chooseOptimalSize;
        }
        matrix.postRotate(rotation * 90, centerX, centerY);
        synchronized (this._cameraStateLock) {
            if (z2) {
                this._binding.qrSurfaceview.setAspectRatio(height, width);
            } else {
                this._binding.qrSurfaceview.setAspectRatio(width, height);
            }
            this._binding.qrSurfaceview.setTransform(matrix);
        }
        if (this._previewSize == null) {
            this._previewSize = size;
            if (this._cameraState != 0) {
                createCameraPreviewSession();
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IPresenter
    public boolean isAvailableZoom() {
        CameraCharacteristics cameraCharacteristics = this._cameraCharacteristics;
        return cameraCharacteristics != null && ((Float) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue() > 1.0f;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IPresenter
    public void changeZoomLevel(int i) {
        try {
            float floatValue = ((Float) this._cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue();
            float max = ((i * (floatValue - 1.0f)) / this._binding.seekZoom.getMax()) + 1.0f;
            if (floatValue >= max && this.mCurrentZoomLevel != max) {
                this.mCurrentZoomLevel = max;
                Rect rect = (Rect) this._cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
                if (this.mCurrentZoomLevel == 1.0f) {
                    this._cropRegion.set(rect);
                } else {
                    int floor = (int) Math.floor(rect.width() / this.mCurrentZoomLevel);
                    int floor2 = (int) Math.floor(rect.height() / this.mCurrentZoomLevel);
                    int width = (rect.width() - floor) / 2;
                    int height = (rect.height() - floor2) / 2;
                    int i2 = floor + width;
                    if (rect.right >= width + i2) {
                        int i3 = floor2 + height;
                        if (rect.bottom >= height + i3) {
                            this._cropRegion = new Rect(width, height, i2, i3);
                            this._previewBuilder.set(CaptureRequest.SCALER_CROP_REGION, this._cropRegion);
                        }
                    }
                    Lg.e("CropSize overflow error.");
                    return;
                }
                this._previewSession.setRepeatingRequest(this._previewBuilder.build(), null, null);
            }
        } catch (CameraAccessException | NullPointerException unused) {
            cameraDeviceFailure();
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.IOnFoundQRCode
    public void onFoundQRCode(byte[] bArr) {
        if (bArr != null) {
            this._contractView.onQREvent(bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] readQRCode(byte[] bArr, int i, int i2) throws LinkageDataFormatException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new PlanarYUVLuminanceSource(bArr, i, i2, 0, 0, i, i2, false)));
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        try {
            byte[] contentData = LinkageDataCommonDecoder.getContentData(multiFormatReader.decode(binaryBitmap).getRawBytes(), D_LinkageData.Mode.MODE_QR);
            multiFormatReader.reset();
            return contentData;
        } catch (Exception unused) {
            multiFormatReader.reset();
            return null;
        } catch (Throwable th) {
            multiFormatReader.reset();
            throw th;
        }
    }

    private void startBackgroundThread() {
        HandlerThread handlerThread = new HandlerThread("QRCodePreview");
        this._backgroundThread = handlerThread;
        handlerThread.start();
        this._backgroundHandler = new Handler(this._backgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        HandlerThread handlerThread = this._backgroundThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        try {
            if (this._activity.isFinishing()) {
                this._threadExecutor.shutdown();
                if (!this._threadExecutor.awaitTermination(60L, TimeUnit.SECONDS)) {
                    this._threadExecutor.shutdownNow();
                }
            }
            HandlerThread handlerThread2 = this._backgroundThread;
            if (handlerThread2 != null) {
                handlerThread2.join();
            }
            this._backgroundThread = null;
            this._backgroundHandler = null;
        } catch (InterruptedException e) {
            Lg.e("thread interrupt error:" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cameraDeviceFailure() {
        this._cameraOpenCloseLock.release();
        this._contractView.showActivityFinishDialog(R.string._CannotUseCamera_);
    }
}
