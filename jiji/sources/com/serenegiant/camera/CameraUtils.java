package com.serenegiant.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.serenegiant.gl.WrappedSurfaceHolder;
import com.serenegiant.view.ViewUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import javassist.compiler.TokenId;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes2.dex */
public class CameraUtils implements CameraConst {
    private static final boolean DEBUG = false;
    private static final String TAG = "CameraUtils";

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface PreviewFormat {
    }

    public static Camera setupCamera(Context context, int i, int i2, int i3) throws IOException {
        int findCamera = findCamera(0);
        Camera open = Camera.open(findCamera);
        Camera.Parameters parameters = open.getParameters();
        if (parameters != null) {
            List<String> supportedFocusModes = parameters.getSupportedFocusModes();
            if (supportedFocusModes.contains("continuous-video")) {
                parameters.setFocusMode("continuous-video");
            } else if (supportedFocusModes.contains(DebugKt.DEBUG_PROPERTY_VALUE_AUTO)) {
                parameters.setFocusMode(DebugKt.DEBUG_PROPERTY_VALUE_AUTO);
            }
            parameters.setRecordingHint(true);
            chooseVideoSize(parameters, i2, i3);
            int[] chooseFps = chooseFps(parameters, 1.0f, 120.0f);
            setupRotation(context, findCamera, open, parameters);
            open.setParameters(parameters);
            Camera.Size previewSize = open.getParameters().getPreviewSize();
            Log.d(TAG, String.format("handleStartPreview(%d, %d),fps(%d-%d)", Integer.valueOf(previewSize.width), Integer.valueOf(previewSize.height), Integer.valueOf(chooseFps[0]), Integer.valueOf(chooseFps[1])));
        }
        return open;
    }

    public static final int findCamera(int i) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        int i2 = i;
        boolean z = false;
        while (!z) {
            for (int i3 = 0; i3 < numberOfCameras; i3++) {
                Camera.getCameraInfo(i3, cameraInfo);
                if (cameraInfo.facing == i2) {
                    return i3;
                }
            }
            if (i2 == i) {
                i2 = i == 0 ? 1 : 0;
            } else {
                z = true;
            }
        }
        return -1;
    }

    public static Camera.Size chooseVideoSize(Camera.Parameters parameters, int i, int i2) throws IllegalArgumentException {
        Camera.Size preferredPreviewSizeForVideo = parameters.getPreferredPreviewSizeForVideo();
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (i <= 0 || i2 <= 0 || (size.width == i && size.height == i2)) {
                Log.d(TAG, String.format("match supported preview size:%dx%d", Integer.valueOf(size.width), Integer.valueOf(size.height)));
                parameters.setPreviewSize(size.width, size.height);
                parameters.setPictureSize(size.width, size.height);
                return size;
            }
        }
        getClosestSupportedSize(parameters.getSupportedPreviewSizes(), i, i2);
        if (preferredPreviewSizeForVideo != null) {
            Log.d(TAG, String.format("use ppsfv: %dx%d", Integer.valueOf(preferredPreviewSizeForVideo.width), Integer.valueOf(preferredPreviewSizeForVideo.height)));
            parameters.setPreviewSize(preferredPreviewSizeForVideo.width, preferredPreviewSizeForVideo.height);
            parameters.setPictureSize(preferredPreviewSizeForVideo.width, preferredPreviewSizeForVideo.height);
            return preferredPreviewSizeForVideo;
        }
        throw new IllegalArgumentException(String.format("Unable to set preview size to %dx%d)", Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public static Camera.Size getClosestSupportedSize(List<Camera.Size> list, int i, int i2) {
        double d = i / i2;
        double d2 = Double.MAX_VALUE;
        Camera.Size size = null;
        double d3 = Double.MAX_VALUE;
        Camera.Size size2 = null;
        for (Camera.Size size3 : list) {
            if (size3.width == i) {
                double abs = Math.abs((size3.width / size3.height) - d) / d;
                if (abs < d3) {
                    size2 = size3;
                    d3 = abs;
                }
            }
        }
        if (size2 == null || d3 < 0.05d) {
            for (Camera.Size size4 : list) {
                if (size4.width == i) {
                    double abs2 = Math.abs((size4.width / size4.height) - d) / d;
                    if (abs2 < d2) {
                        size = size4;
                        d2 = abs2;
                    }
                }
            }
            d3 = d2;
            size2 = size;
        }
        if (size2 != null && d3 < 0.05d) {
            Log.w(TAG, String.format("Set preview size to (%dx%d) instead of (%d,%d)", Integer.valueOf(size2.width), Integer.valueOf(size2.height), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        return size2;
    }

    public static int[] chooseFps(Camera.Parameters parameters, float f, float f2) {
        List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
        int[] iArr = null;
        if (supportedPreviewFpsRange != null && !supportedPreviewFpsRange.isEmpty()) {
            int size = supportedPreviewFpsRange.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                int[] iArr2 = supportedPreviewFpsRange.get(size);
                if (iArr2[0] / 1000.0f >= f && iArr2[1] / 1000.0f <= f2) {
                    iArr = iArr2;
                    break;
                }
                size--;
            }
            if (iArr == null) {
                int size2 = supportedPreviewFpsRange.size() - 1;
                while (true) {
                    if (size2 < 0) {
                        break;
                    }
                    int[] iArr3 = supportedPreviewFpsRange.get(size2);
                    if (iArr3[1] / 1000.0f <= f2) {
                        iArr = iArr3;
                        break;
                    }
                    size2--;
                }
            }
            if (iArr == null) {
                Log.w(TAG, String.format("chooseFps:specific fps range(%f-%f) not found,use fastest one", Float.valueOf(f), Float.valueOf(f2)));
            }
        }
        if (iArr != null) {
            parameters.setPreviewFpsRange(iArr[0], iArr[1]);
            Camera.Size previewSize = parameters.getPreviewSize();
            Log.d(TAG, String.format("chooseFps:(%dx%d),fps=%d-%d", Integer.valueOf(previewSize.width), Integer.valueOf(previewSize.height), Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1])));
        }
        return iArr;
    }

    public static int setupRotation(int i, View view, Camera camera, Camera.Parameters parameters) {
        int i2;
        int rotationDegrees = ViewUtils.getRotationDegrees(view);
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        if (cameraInfo.facing == 1) {
            i2 = (360 - ((cameraInfo.orientation + rotationDegrees) % TokenId.EXOR_E)) % TokenId.EXOR_E;
        } else {
            i2 = ((cameraInfo.orientation - rotationDegrees) + TokenId.EXOR_E) % TokenId.EXOR_E;
        }
        camera.setDisplayOrientation(i2);
        return i2;
    }

    public static int setupRotation(Context context, int i, Camera camera, Camera.Parameters parameters) {
        int i2;
        int rotationDegrees = ViewUtils.getRotationDegrees(context);
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        if (cameraInfo.facing == 1) {
            i2 = (360 - ((cameraInfo.orientation + rotationDegrees) % TokenId.EXOR_E)) % TokenId.EXOR_E;
        } else {
            i2 = ((cameraInfo.orientation - rotationDegrees) + TokenId.EXOR_E) % TokenId.EXOR_E;
        }
        camera.setDisplayOrientation(i2);
        return i2;
    }

    public static void setPreviewSurface(Camera camera, Object obj) throws IllegalArgumentException, IOException {
        if (obj instanceof SurfaceTexture) {
            camera.setPreviewTexture((SurfaceTexture) obj);
        } else if (obj instanceof SurfaceHolder) {
            camera.setPreviewDisplay((SurfaceHolder) obj);
        } else if (obj instanceof Surface) {
            camera.setPreviewDisplay(new WrappedSurfaceHolder((Surface) obj));
        } else if (obj instanceof SurfaceView) {
            camera.setPreviewDisplay(((SurfaceView) obj).getHolder());
        } else {
            throw new IllegalArgumentException("Unknown surface type," + obj);
        }
    }

    public static void dumpSupportedPictureFormats(Camera.Parameters parameters) {
        for (Integer num : parameters.getSupportedPictureFormats()) {
            int intValue = num.intValue();
            if (intValue == 0) {
                Log.i(TAG, "supported: UNKNOWN");
            } else if (intValue == 4) {
                Log.i(TAG, "supported: RGB_565");
            } else if (intValue == 20) {
                Log.i(TAG, "supported: YUY2");
            } else if (intValue == 32) {
                Log.i(TAG, "supported: RAW_SENSOR");
            } else if (intValue == 842094169) {
                Log.i(TAG, "supported: YV12");
            } else if (intValue == 1144402265) {
                Log.i(TAG, "supported: DEPTH16");
            } else if (intValue == 16) {
                Log.i(TAG, "supported: NV16");
            } else if (intValue == 17) {
                Log.i(TAG, "supported: NV21");
            } else if (intValue == 256) {
                Log.i(TAG, "supported: JPEG");
            } else if (intValue == 257) {
                Log.i(TAG, "supported: DEPTH_POINT_CLOUD");
            } else {
                switch (intValue) {
                    case 34:
                        Log.i(TAG, "supported: PRIVATE");
                        continue;
                    case 35:
                        Log.i(TAG, "supported: YUV_420_888");
                        continue;
                    case 36:
                        Log.i(TAG, "supported: RAW_PRIVATE");
                        continue;
                    case 37:
                        Log.i(TAG, "supported: RAW10");
                        continue;
                    case 38:
                        Log.i(TAG, "supported: RAW12");
                        continue;
                    case 39:
                        Log.i(TAG, "supported: YUV_422_888");
                        continue;
                    case 40:
                        Log.i(TAG, "supported: YUV_444_888");
                        continue;
                    case 41:
                        Log.i(TAG, "supported: FLEX_RGB_888");
                        continue;
                    case 42:
                        Log.i(TAG, "supported: FLEX_RGBA_8888");
                        continue;
                    default:
                        Log.i(TAG, String.format("supported: unknown, %08x", Integer.valueOf(intValue)));
                        continue;
                }
            }
        }
    }

    public static void dumpSupportedPreviewFormats(Camera.Parameters parameters) {
        for (Integer num : parameters.getSupportedPreviewFormats()) {
            int intValue = num.intValue();
            if (intValue == 0) {
                Log.i(TAG, "supported: UNKNOWN");
            } else if (intValue == 4) {
                Log.i(TAG, "supported: RGB_565");
            } else if (intValue == 20) {
                Log.i(TAG, "supported: YUY2");
            } else if (intValue == 32) {
                Log.i(TAG, "supported: RAW_SENSOR");
            } else if (intValue == 842094169) {
                Log.i(TAG, "supported: YV12");
            } else if (intValue == 1144402265) {
                Log.i(TAG, "supported: DEPTH16");
            } else if (intValue == 16) {
                Log.i(TAG, "supported: NV16");
            } else if (intValue == 17) {
                Log.i(TAG, "supported: NV21");
            } else if (intValue == 256) {
                Log.i(TAG, "supported: JPEG");
            } else if (intValue == 257) {
                Log.i(TAG, "supported: DEPTH_POINT_CLOUD");
            } else {
                switch (intValue) {
                    case 34:
                        Log.i(TAG, "supported: PRIVATE");
                        continue;
                    case 35:
                        Log.i(TAG, "supported: YUV_420_888");
                        continue;
                    case 36:
                        Log.i(TAG, "supported: RAW_PRIVATE");
                        continue;
                    case 37:
                        Log.i(TAG, "supported: RAW10");
                        continue;
                    case 38:
                        Log.i(TAG, "supported: RAW12");
                        continue;
                    case 39:
                        Log.i(TAG, "supported: YUV_422_888");
                        continue;
                    case 40:
                        Log.i(TAG, "supported: YUV_444_888");
                        continue;
                    case 41:
                        Log.i(TAG, "supported: FLEX_RGB_888");
                        continue;
                    case 42:
                        Log.i(TAG, "supported: FLEX_RGBA_8888");
                        continue;
                    default:
                        Log.i(TAG, String.format("supported: unknown, %08x", Integer.valueOf(intValue)));
                        continue;
                }
            }
        }
    }
}
