package com.serenegiant.camera;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaCodec;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import com.serenegiant.camera.CameraConst;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes2.dex */
public class Camera2Utils implements CameraConst {
    private static final boolean DEBUG = false;
    private static final String TAG = "Camera2Utils";

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface RequestTemplate {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class CompareSizesByArea implements Comparator<Size> {
        protected CompareSizesByArea() {
        }

        @Override // java.util.Comparator
        public int compare(Size size, Size size2) {
            return Long.signum((size.getWidth() * size.getHeight()) - (size2.getWidth() * size2.getHeight()));
        }
    }

    public static CameraConst.CameraInfo findCamera(CameraManager cameraManager, int i) throws CameraAccessException {
        String str;
        int i2;
        String[] cameraIdList = cameraManager.getCameraIdList();
        if (cameraIdList == null || cameraIdList.length <= 0) {
            return null;
        }
        int i3 = i == 0 ? 1 : 0;
        boolean z = false;
        int i4 = i3;
        loop0: while (true) {
            if (z) {
                str = null;
                i2 = 0;
                break;
            }
            for (String str2 : cameraIdList) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str2);
                if (((Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)).intValue() == i4) {
                    StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                    i2 = ((Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
                    str = str2;
                    break loop0;
                }
            }
            if (i4 == i3) {
                i4 = i3 == 1 ? 0 : 1;
            } else {
                z = true;
            }
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return new CameraConst.CameraInfo(str, i3, i2, CameraConst.DEFAULT_WIDTH, CameraConst.DEFAULT_HEIGHT);
    }

    public static CameraConst.CameraInfo findCamera(CameraManager cameraManager, int i, int i2, int i3, int i4) throws CameraAccessException {
        int i5;
        Size size;
        String str;
        int i6;
        String[] cameraIdList = cameraManager.getCameraIdList();
        int i7 = 0;
        if (cameraIdList == null || cameraIdList.length <= 0) {
            i5 = -1;
        } else {
            int i8 = i3 == 0 ? 1 : 0;
            i5 = i8;
            boolean z = false;
            loop0: while (!z) {
                for (String str2 : cameraIdList) {
                    CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str2);
                    if (((Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)).intValue() == i5) {
                        size = chooseOptimalSize(cameraCharacteristics, (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP), i, i2, i4);
                        i7 = ((Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
                        i6 = i5;
                        str = str2;
                        break loop0;
                    }
                }
                if (i5 == i8) {
                    i5 = i8 == 1 ? 0 : 1;
                } else {
                    z = true;
                }
            }
        }
        size = null;
        str = null;
        i6 = i5;
        int i9 = i7;
        if (TextUtils.isEmpty(str) || size == null) {
            return null;
        }
        return new CameraConst.CameraInfo(str, i6, i9, size.getWidth(), size.getHeight());
    }

    public static CameraConst.CameraInfo chooseOptimalSize(CameraManager cameraManager, String str, int i, int i2, int i3, int i4) throws CameraAccessException {
        CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str);
        Size chooseOptimalSize = chooseOptimalSize(cameraCharacteristics, (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP), i2, i3, i4);
        int intValue = ((Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        if (TextUtils.isEmpty(str) || chooseOptimalSize == null) {
            return null;
        }
        return new CameraConst.CameraInfo(str, i, intValue, chooseOptimalSize.getWidth(), chooseOptimalSize.getHeight());
    }

    public static Size chooseOptimalSize(CameraCharacteristics cameraCharacteristics, StreamConfigurationMap streamConfigurationMap, int i, int i2, int i3) {
        double width;
        double height;
        long width2;
        long height2;
        Size[] sizeArr;
        int i4;
        Rect rect = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        Size size = (Size) Collections.max(Arrays.asList(streamConfigurationMap.getOutputSizes(MediaCodec.class)), new CompareSizesByArea());
        if (i <= 0 || i2 <= 0) {
            Log.d(TAG, "chooseOptimalSize:select" + size);
            return size;
        }
        int max = Math.max(i, i2);
        int min = Math.min(i, i2);
        if (max <= 0 || min <= 0) {
            width = size.getWidth();
            height = size.getHeight();
        } else {
            width = max;
            height = min;
        }
        double d = width / height;
        if (max <= 0 || min <= 0) {
            width2 = size.getWidth();
            height2 = size.getHeight();
        } else {
            width2 = max;
            height2 = min;
        }
        long j = width2 * height2;
        Size[] outputSizes = streamConfigurationMap.getOutputSizes(SurfaceTexture.class);
        int length = outputSizes.length;
        int i5 = 0;
        while (true) {
            String str = ")";
            if (i5 < length) {
                Size size2 = outputSizes[i5];
                if (size2.getWidth() == max && size2.getHeight() == min) {
                    Log.v(TAG, "chooseOptimalSize:found(" + size2 + ")");
                    return size2;
                }
                i5++;
            } else {
                ArrayList arrayList = new ArrayList();
                int length2 = outputSizes.length;
                int i6 = 0;
                double d2 = Double.MAX_VALUE;
                Size size3 = null;
                while (i6 < length2) {
                    Size size4 = outputSizes[i6];
                    int i7 = length2;
                    String str2 = str;
                    Size[] sizeArr2 = outputSizes;
                    Size size5 = size;
                    long width3 = size4.getWidth() * size4.getHeight();
                    double abs = Math.abs((size4.getWidth() / size4.getHeight()) - d) / d;
                    if (abs < 0.2d && width3 <= j) {
                        arrayList.add(size4);
                    }
                    if (size4.getWidth() == max && abs < d2) {
                        d2 = abs;
                        size3 = size4;
                    }
                    i6++;
                    str = str2;
                    length2 = i7;
                    size = size5;
                    outputSizes = sizeArr2;
                }
                Size[] sizeArr3 = outputSizes;
                Size size6 = size;
                String str3 = str;
                if (size3 == null || d2 >= 0.05d) {
                    Size[] sizeArr4 = sizeArr3;
                    int length3 = sizeArr4.length;
                    int i8 = 0;
                    Size size7 = null;
                    double d3 = Double.MAX_VALUE;
                    while (i8 < length3) {
                        Size size8 = sizeArr4[i8];
                        if (size8.getWidth() == max) {
                            sizeArr = sizeArr4;
                            i4 = length3;
                            double abs2 = Math.abs((size8.getWidth() / size8.getHeight()) - d) / d;
                            if (abs2 < d3) {
                                d3 = abs2;
                                size7 = size8;
                            }
                        } else {
                            sizeArr = sizeArr4;
                            i4 = length3;
                        }
                        i8++;
                        length3 = i4;
                        sizeArr4 = sizeArr;
                    }
                    size3 = size7;
                    d2 = d3;
                }
                if (size3 != null && d2 < 0.05d) {
                    Log.d(TAG, String.format("chooseOptimalSize:select(%dx%d), request(%d,%d)", Integer.valueOf(size3.getWidth()), Integer.valueOf(size3.getHeight()), Integer.valueOf(max), Integer.valueOf(min)));
                    return size3;
                }
                try {
                    size3 = (Size) Collections.max(arrayList, new CompareSizesByArea());
                } catch (Exception unused) {
                }
                Size size9 = size3 == null ? size6 : size3;
                Log.d(TAG, "chooseOptimalSize:select(" + size9 + str3);
                return size9;
            }
        }
    }
}
