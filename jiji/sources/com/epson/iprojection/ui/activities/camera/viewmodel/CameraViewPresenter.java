package com.epson.iprojection.ui.activities.camera.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.CameraUtils;
import com.epson.iprojection.common.utils.PermissionUtilsKt;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.databinding.MainCameraBinding;
import com.epson.iprojection.ui.activities.camera.models.CameraContract;
import com.epson.iprojection.ui.activities.marker.Activity_Marker;
import com.epson.iprojection.ui.activities.marker.BmpHolder;
import com.epson.iprojection.ui.activities.marker.ImageSaver;
import com.epson.iprojection.ui.activities.marker.ImageSaver10orMore;
import com.epson.iprojection.ui.activities.marker.ImageSaver9orLess;
import com.epson.iprojection.ui.common.RenderedImageFile;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.engine_wrapper.ContentRectHolder;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.serenegiant.view.MessagePanelUtils;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class CameraViewPresenter implements CameraContract.IPresenter {
    private static final String CAMERA_BACK = "0";
    private static final String CAMERA_FRONT = "1";
    private static final int CAPTURE_IMAGE_FORMAT = 35;
    private static final int INTENT_MARKER = 0;
    private static final int STATE_CLOSED = 0;
    private static final int STATE_OPENED = 1;
    private static final int TEXTURE_VIEW_MAX_HEIGHT = 768;
    private static final int TEXTURE_VIEW_MAX_WIDTH = 1024;
    private Activity _activity;
    private Handler _backgroundHandler;
    private HandlerThread _backgroundThread;
    private MainCameraBinding _binding;
    private CameraCharacteristics _cameraCharacteristics;
    private CameraDevice _cameraDevice;
    private String _cameraId;
    private CameraManager _cameraManager;
    private final CameraContract.IView _contractView;
    private DisplayManager.DisplayListener _displayListener;
    private DisplayManager _displayManager;
    private ImageReader _frameImageReader;
    private final OrientationEventListener _orientationListener;
    private Bitmap _pausedBitmap;
    private CaptureRequest.Builder _previewBuilder;
    private CameraCaptureSession _previewSession;
    private Size _previewSize;
    private final ImageSaver _saver;
    private Image capturedImage;
    private final Object _cameraStateLock = new Object();
    private float mCurrentZoomLevel = 1.0f;
    private Rect _cropRegion = null;
    private int _lastOrientationNum = 0;
    private boolean _isSendable = false;
    private boolean _isPlaying = false;
    private boolean _isLightingOn = false;
    private boolean _isNowCameraFacingBack = true;
    private final Semaphore _cameraOpenCloseLock = new Semaphore(1);
    private int _cameraState = 0;
    private final CameraDevice.StateCallback _stateCallback = new CameraDevice.StateCallback() { // from class: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewPresenter.1
        {
            CameraViewPresenter.this = this;
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(CameraDevice cameraDevice) {
            synchronized (CameraViewPresenter.this._cameraStateLock) {
                CameraViewPresenter.this._cameraState = 1;
                CameraViewPresenter.this._cameraOpenCloseLock.release();
                CameraViewPresenter.this._cameraDevice = cameraDevice;
                if (CameraViewPresenter.this._previewSize != null && CameraViewPresenter.this._binding.camSurfaceview.isAvailable()) {
                    CameraViewPresenter.this.createCameraPreviewSession();
                }
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(CameraDevice cameraDevice) {
            synchronized (CameraViewPresenter.this._cameraStateLock) {
                CameraViewPresenter.this._cameraState = 0;
                CameraViewPresenter.this._cameraOpenCloseLock.release();
                cameraDevice.close();
                CameraViewPresenter.this._cameraDevice = null;
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(CameraDevice cameraDevice, int i) {
            synchronized (CameraViewPresenter.this._cameraStateLock) {
                CameraViewPresenter.this._cameraState = 0;
                CameraViewPresenter.this._cameraOpenCloseLock.release();
                cameraDevice.close();
                CameraViewPresenter.this._cameraDevice = null;
            }
            if (CameraViewPresenter.this._contractView != null) {
                CameraViewPresenter.this._contractView.destroy();
            }
        }
    };
    private final TextureView.SurfaceTextureListener _surfaceTextureListener = new TextureView.SurfaceTextureListener() { // from class: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewPresenter.2
        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        {
            CameraViewPresenter.this = this;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            CameraViewPresenter.this.configureTransform(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            CameraViewPresenter.this.configureTransform(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            synchronized (CameraViewPresenter.this._cameraStateLock) {
                CameraViewPresenter.this._previewSize = null;
            }
            return true;
        }
    };
    private final ImageReader.OnImageAvailableListener mOnJpegImageAvailableListener = new ImageReader.OnImageAvailableListener() { // from class: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewPresenter.3
        {
            CameraViewPresenter.this = this;
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader imageReader) {
            if (imageReader == null) {
                return;
            }
            CameraViewPresenter.this.capturedImage = imageReader.acquireLatestImage();
            CameraViewPresenter.this.prepareSavingImage();
        }
    };
    private final Runnable _sendRun = new Runnable() { // from class: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewPresenter$$ExternalSyntheticLambda1
        {
            CameraViewPresenter.this = this;
        }

        @Override // java.lang.Runnable
        public final void run() {
            CameraViewPresenter.this.m80x8b32a350();
        }
    };

    /* renamed from: lambda$new$0$com-epson-iprojection-ui-activities-camera-viewmodel-CameraViewPresenter */
    public /* synthetic */ void m80x8b32a350() {
        long j;
        while (!this._binding.camSurfaceview.isActivated()) {
            Sleeper.sleep(100L);
        }
        Bitmap bitmap = null;
        while (this._isSendable) {
            long currentTimeMillis = System.currentTimeMillis();
            if (Pj.getIns().isConnected()) {
                Bitmap sendCameraImage = sendCameraImage();
                if (sendCameraImage != null) {
                    bitmap = sendCameraImage;
                }
                j = 100;
            } else {
                j = 1000;
            }
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            Sleeper.sleep(currentTimeMillis2 > j ? 10L : j - currentTimeMillis2);
        }
        Bitmap sendCameraImage2 = sendCameraImage();
        if (sendCameraImage2 != null) {
            bitmap = sendCameraImage2;
        }
        new RenderedImageFile().save(this._activity, bitmap);
    }

    public CameraViewPresenter(CameraContract.IView iView, MainCameraBinding mainCameraBinding, String str) {
        this._activity = iView.getActivityForIntent();
        this._contractView = iView;
        this._binding = mainCameraBinding;
        this._cameraId = str;
        this._orientationListener = new OrientationEventListener(this._activity, 3) { // from class: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewPresenter.4
            {
                CameraViewPresenter.this = this;
            }

            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int i) {
                if (CameraViewPresenter.this._binding.camSurfaceview != null && CameraViewPresenter.this._binding.camSurfaceview.isAvailable() && CameraViewPresenter.this.isUpdateOrientation()) {
                    CameraViewPresenter cameraViewPresenter = CameraViewPresenter.this;
                    cameraViewPresenter.configureTransform(cameraViewPresenter._binding.camSurfaceview.getWidth(), CameraViewPresenter.this._binding.camSurfaceview.getHeight());
                }
            }
        };
        if (Build.VERSION.SDK_INT >= 29) {
            this._saver = new ImageSaver10orMore(this._activity);
        } else {
            this._saver = new ImageSaver9orLess(this._activity);
        }
        this._saver.registerCallback((ImageSaver.ISaveFinishCallback) this._activity);
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0041 A[RETURN] */
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
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewPresenter.isUpdateOrientation():boolean");
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public void onPause() {
        OrientationEventListener orientationEventListener = this._orientationListener;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
        }
        switchFlash(false);
        this._isLightingOn = false;
        closeCamera();
        stopBackgroundThread();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public void onResume() {
        startBackgroundThread();
        if (openCamera()) {
            this._lastOrientationNum = this._activity.getWindowManager().getDefaultDisplay().getRotation();
            this._binding.camSurfaceview.onActivityCreated(this._activity, this._lastOrientationNum);
            if (this._binding.camSurfaceview.isAvailable()) {
                configureTransform(this._binding.camSurfaceview.getWidth(), this._binding.camSurfaceview.getHeight());
                if (!this._isPlaying) {
                    try {
                        Bitmap load = new RenderedImageFile().load(this._activity);
                        if (load != null) {
                            Pj.getIns().sendImage(load, null);
                        }
                    } catch (BitmapMemoryException e) {
                        Lg.e("BitmapMemoryException e = " + e);
                    }
                }
            } else {
                this._binding.camSurfaceview.setSurfaceTextureListener(this._surfaceTextureListener);
            }
            OrientationEventListener orientationEventListener = this._orientationListener;
            if (orientationEventListener != null && orientationEventListener.canDetectOrientation()) {
                this._orientationListener.enable();
            }
            if (Pj.getIns().isConnected()) {
                this._isSendable = true;
            }
            new Thread(this._sendRun).start();
            this._isPlaying = true;
        }
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public void onDestroy() {
        this._saver.unregisterCallback();
    }

    private boolean openCamera() {
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
                return true;
            } catch (CameraAccessException | InterruptedException | SecurityException unused) {
                cameraDeviceFailure();
                return true;
            }
        }
        return false;
    }

    private boolean setUpCameraOutputs() {
        try {
            CameraManager cameraManager = (CameraManager) this._activity.getSystemService("camera");
            this._cameraManager = cameraManager;
            if (cameraManager == null) {
                throw new CameraAccessException(3, "CameraManager is null.");
            }
            if (this._cameraId == null) {
                String backCameraId = CameraUtils.getBackCameraId(cameraManager);
                this._cameraId = backCameraId;
                if (backCameraId != null) {
                    this._isNowCameraFacingBack = true;
                }
            }
            if (this._cameraId == null) {
                String frontFacingCameraId = CameraUtils.getFrontFacingCameraId(this._cameraManager);
                this._cameraId = frontFacingCameraId;
                if (frontFacingCameraId != null) {
                    this._isNowCameraFacingBack = false;
                }
            }
            String str = this._cameraId;
            if (str == null) {
                throw new CameraAccessException(3, "Nothing camera devices.");
            }
            this._cameraCharacteristics = this._cameraManager.getCameraCharacteristics(str);
            synchronized (this._cameraStateLock) {
                if (this._frameImageReader == null) {
                    Size chooseOptimalSize = CameraUtils.chooseOptimalSize(35, 1024, TEXTURE_VIEW_MAX_HEIGHT, this._cameraManager, this._cameraId);
                    if (!MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
                        ContentRectHolder.INSTANCE.setContentRect(chooseOptimalSize.getWidth(), chooseOptimalSize.getHeight());
                    }
                    ImageReader newInstance = ImageReader.newInstance(chooseOptimalSize.getWidth(), chooseOptimalSize.getHeight(), 35, 5);
                    this._frameImageReader = newInstance;
                    newInstance.setOnImageAvailableListener(this.mOnJpegImageAvailableListener, this._backgroundHandler);
                }
            }
            return true;
        } catch (CameraAccessException unused) {
            closeCamera();
            this._contractView.destroy();
            return false;
        }
    }

    private void closeCamera() {
        DisplayManager.DisplayListener displayListener;
        try {
            try {
                this._cameraOpenCloseLock.acquire();
                synchronized (this._cameraOpenCloseLock) {
                    this._isSendable = false;
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
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
            }
        } finally {
            this._cameraOpenCloseLock.release();
        }
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public void onClickEvent(View view) {
        int id = view.getId();
        if (id == R.id.btn_play_pause) {
            pauseAndPlay();
        } else if (id != R.id.btn_projection) {
            switch (id) {
                case R.id.camera_btn_exchange /* 2131230927 */:
                    switchCamera();
                    return;
                case R.id.camera_btn_light /* 2131230928 */:
                    toggleFlash();
                    return;
                case R.id.camera_btn_paint /* 2131230929 */:
                    launchActivityMarker();
                    return;
                case R.id.camera_btn_save /* 2131230930 */:
                    if (Build.VERSION.SDK_INT >= 29 || PermissionUtilsKt.isUsablePermission(this._activity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                        save();
                        return;
                    } else {
                        this._contractView.goStragePermissionActivity();
                        return;
                    }
                default:
                    return;
            }
        } else {
            this._contractView.onClick(view);
        }
    }

    private void pauseAndPlay() {
        if (this._isPlaying) {
            pauseCamera();
            this._isPlaying = false;
            this._isSendable = false;
            this._pausedBitmap = this._binding.camSurfaceview.getPauseBitmap();
            return;
        }
        createCameraPreviewSession();
        this._isPlaying = true;
        this._isSendable = true;
        new Thread(this._sendRun).start();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public int getLastOrientationNum() {
        return this._lastOrientationNum;
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public String getLastCameraId() {
        return this._cameraId;
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public boolean getPlayingState() {
        return this._isPlaying;
    }

    private void pauseCamera() {
        CameraCaptureSession cameraCaptureSession = this._previewSession;
        if (cameraCaptureSession == null || this._cameraDevice == null) {
            return;
        }
        try {
            cameraCaptureSession.abortCaptures();
        } catch (CameraAccessException | IllegalStateException unused) {
            cameraDeviceFailure();
        }
    }

    public void prepareSavingImage() {
        this._backgroundHandler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewPresenter$$ExternalSyntheticLambda2
            {
                CameraViewPresenter.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                CameraViewPresenter.this.m81xf5ea803b();
            }
        });
    }

    /* renamed from: lambda$prepareSavingImage$1$com-epson-iprojection-ui-activities-camera-viewmodel-CameraViewPresenter */
    public /* synthetic */ void m81xf5ea803b() {
        Image image;
        try {
            try {
                this._isSendable = false;
                BmpHolder.ins().set(this._binding.camSurfaceview.getPauseBitmap());
                Intent intent = new Intent(this._contractView.getActivityForIntent(), Activity_Marker.class);
                intent.putExtra(Activity_Marker.IntentMsg_CameraViewMode, true);
                this._contractView.goNextActivity(intent, 0);
                this._isPlaying = false;
                image = this.capturedImage;
                if (image == null) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                image = this.capturedImage;
                if (image == null) {
                    return;
                }
            }
            image.close();
        } catch (Throwable th) {
            Image image2 = this.capturedImage;
            if (image2 != null) {
                image2.close();
            }
            throw th;
        }
    }

    public void createCameraPreviewSession() {
        SurfaceTexture surfaceTexture;
        if (this._cameraDevice == null || !this._binding.camSurfaceview.isAvailable() || this._cameraState == 0 || this._previewSize == null || (surfaceTexture = this._binding.camSurfaceview.getSurfaceTexture()) == null) {
            return;
        }
        surfaceTexture.setDefaultBufferSize(this._previewSize.getWidth(), this._previewSize.getHeight());
        Surface surface = new Surface(surfaceTexture);
        try {
            CaptureRequest.Builder createCaptureRequest = this._cameraDevice.createCaptureRequest(1);
            this._previewBuilder = createCaptureRequest;
            createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, 4);
            if (this._cropRegion != null) {
                this._previewBuilder.set(CaptureRequest.SCALER_CROP_REGION, this._cropRegion);
            }
            this._previewBuilder.addTarget(surface);
            setFpsRange();
        } catch (CameraAccessException unused) {
            cameraDeviceFailure();
        }
        try {
            this._cameraDevice.createCaptureSession(Arrays.asList(surface, this._frameImageReader.getSurface()), new CameraCaptureSession.StateCallback() { // from class: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewPresenter.5
                {
                    CameraViewPresenter.this = this;
                }

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    CameraViewPresenter.this._previewSession = cameraCaptureSession;
                    if (CameraViewPresenter.this._cameraDevice == null) {
                        return;
                    }
                    try {
                        CameraViewPresenter.this._previewSession.setRepeatingRequest(CameraViewPresenter.this._previewBuilder.build(), null, CameraViewPresenter.this._backgroundHandler);
                    } catch (CameraAccessException unused2) {
                        CameraViewPresenter.this.cameraDeviceFailure();
                    }
                }

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    CameraViewPresenter.this.cameraDeviceFailure();
                }
            }, null);
        } catch (CameraAccessException unused2) {
            cameraDeviceFailure();
        }
    }

    public void configureTransform(final int i, final int i2) {
        if (this._binding.camSurfaceview == null) {
            return;
        }
        this._activity.runOnUiThread(new Runnable() { // from class: com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewPresenter$$ExternalSyntheticLambda0
            {
                CameraViewPresenter.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                CameraViewPresenter.this.m79x3d834dae(i, i2);
            }
        });
    }

    /* renamed from: lambda$configureTransform$2$com-epson-iprojection-ui-activities-camera-viewmodel-CameraViewPresenter */
    public /* synthetic */ void m79x3d834dae(int i, int i2) {
        Size size;
        int i3;
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
        int i4 = i;
        int i5 = i2;
        RectF rectF = new RectF(0.0f, 0.0f, i4, i5);
        float f = height;
        float f2 = width;
        RectF rectF2 = new RectF(0.0f, 0.0f, f, f2);
        if (z2 && z) {
            rectF2 = new RectF(0.0f, 0.0f, f2, f);
        } else {
            i5 = i4;
            i4 = i5;
        }
        Matrix matrix = new Matrix();
        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        if (z) {
            size = chooseOptimalSize;
            rectF2.offset(centerX - rectF2.centerX(), centerY - rectF2.centerY());
            matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.FILL);
            float max = Math.max(i4 / f, i5 / f2);
            matrix.postScale(max, max, centerX, centerY);
            i3 = (rotation - 2) * 90;
        } else {
            size = chooseOptimalSize;
            i3 = rotation * 90;
        }
        matrix.postRotate(i3, centerX, centerY);
        synchronized (this._cameraStateLock) {
            if (z2) {
                this._binding.camSurfaceview.setAspectRatio(height, width);
            } else {
                this._binding.camSurfaceview.setAspectRatio(width, height);
            }
            this._binding.camSurfaceview.setTransform(matrix);
            this._binding.camSurfaceview.setDefaultOrientation(rotation);
        }
        Size size2 = this._previewSize;
        Size size3 = size;
        if (size2 == null || !CameraUtils.euqalsAspects(size3, size2)) {
            this._previewSize = size3;
            createCameraPreviewSession();
        }
    }

    private void switchCamera() {
        this._isNowCameraFacingBack = !this._isNowCameraFacingBack;
        switchFlash(false);
        this._isLightingOn = false;
        this._cropRegion = null;
        this.mCurrentZoomLevel = 1.0f;
        this._frameImageReader = null;
        if (this._cameraId.equals(CAMERA_FRONT)) {
            this._cameraDevice.close();
            this._cameraDevice = null;
            this._cameraId = CameraUtils.getBackCameraId(this._cameraManager);
            openCamera();
        } else if (this._cameraId.equals(CAMERA_BACK)) {
            this._cameraDevice.close();
            this._cameraDevice = null;
            this._cameraId = CameraUtils.getFrontFacingCameraId(this._cameraManager);
            openCamera();
        }
        changeZoomLevel(0);
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public boolean isNowCameraFacingBack() {
        return this._isNowCameraFacingBack;
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public boolean hasFlash() {
        return this._activity.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }

    private void toggleFlash() {
        if (this._cameraId.equals(CAMERA_BACK)) {
            switchFlash(!this._isLightingOn);
            this._isLightingOn = !this._isLightingOn;
        }
    }

    private void switchFlash(boolean z) {
        CaptureRequest.Builder builder = this._previewBuilder;
        if (builder == null || this._previewSession == null) {
            return;
        }
        try {
            if (z) {
                builder.set(CaptureRequest.FLASH_MODE, 2);
            } else {
                builder.set(CaptureRequest.FLASH_MODE, 0);
            }
            this._previewSession.setRepeatingRequest(this._previewBuilder.build(), null, null);
        } catch (CameraAccessException | IllegalStateException unused) {
            cameraDeviceFailure();
        }
    }

    private void launchActivityMarker() {
        CameraDevice cameraDevice = this._cameraDevice;
        if (cameraDevice == null || this._previewSession == null) {
            return;
        }
        try {
            CaptureRequest.Builder createCaptureRequest = cameraDevice.createCaptureRequest(2);
            createCaptureRequest.addTarget(this._frameImageReader.getSurface());
            setCameraMode(createCaptureRequest);
            if (this._isPlaying) {
                this._previewSession.stopRepeating();
            }
            this._previewSession.capture(createCaptureRequest.build(), null, null);
        } catch (CameraAccessException | IllegalStateException unused) {
            cameraDeviceFailure();
        }
    }

    private void setCameraMode(CaptureRequest.Builder builder) {
        builder.set(CaptureRequest.CONTROL_AF_MODE, 3);
        builder.set(CaptureRequest.CONTROL_EFFECT_MODE, 0);
    }

    private void setFpsRange() {
        if (this._cameraDevice == null) {
            return;
        }
        try {
            Range[] rangeArr = (Range[]) this._cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            if (rangeArr == null || rangeArr.length <= 1) {
                return;
            }
            boolean z = false;
            int i = 0;
            int i2 = 0;
            for (Range range : rangeArr) {
                if (((Integer) range.getLower()).intValue() > 10) {
                    if (!z) {
                        int intValue = ((Integer) range.getLower()).intValue();
                        i2 = ((Integer) range.getUpper()).intValue();
                        i = intValue;
                        z = true;
                    } else if (i2 > ((Integer) range.getUpper()).intValue()) {
                        i = ((Integer) range.getLower()).intValue();
                        i2 = ((Integer) range.getUpper()).intValue();
                    } else if (i2 == ((Integer) range.getUpper()).intValue() && i > ((Integer) range.getLower()).intValue()) {
                        i = ((Integer) range.getLower()).intValue();
                        i2 = ((Integer) range.getUpper()).intValue();
                    }
                }
            }
            if (z) {
                this._cameraDevice.createCaptureRequest(1).set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, Range.create(Integer.valueOf(i), Integer.valueOf(i2)));
            }
        } catch (CameraAccessException | NullPointerException unused) {
            cameraDeviceFailure();
        }
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public boolean isAvailableZoom() {
        CameraCharacteristics cameraCharacteristics = this._cameraCharacteristics;
        return cameraCharacteristics != null && ((Float) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue() > 1.0f;
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public int hasSomeCameras() {
        CameraManager cameraManager = this._cameraManager;
        if (cameraManager != null) {
            return CameraUtils.getAvailableCameras(cameraManager);
        }
        return 0;
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
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

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public void save() {
        this._saver.save(this._pausedBitmap, false);
        this._binding.toolbarCamera.cameraBtnSave.setEnabled(false);
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IPresenter
    public void showSaveFailed() {
        this._saver.showSaveFailed();
    }

    private Bitmap sendCameraImage() {
        if (this._activity.isFinishing()) {
            return null;
        }
        Bitmap pauseBitmap = this._binding.camSurfaceview.getPauseBitmap();
        if (Pj.getIns().isConnected() && pauseBitmap != null) {
            try {
                Pj.getIns().sendImage(pauseBitmap, null);
            } catch (BitmapMemoryException unused) {
                Lg.e("catch BitmapMemoryException.");
            }
        }
        return pauseBitmap;
    }

    private void startBackgroundThread() {
        HandlerThread handlerThread = new HandlerThread("CameraPreview");
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

    public void cameraDeviceFailure() {
        this._cameraOpenCloseLock.release();
        this._contractView.showActivityFinishDialog(R.string._CannotUseCamera_);
    }
}
