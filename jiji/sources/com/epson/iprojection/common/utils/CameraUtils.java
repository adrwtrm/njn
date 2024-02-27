package com.epson.iprojection.common.utils;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Size;
import android.util.SparseIntArray;
import com.epson.iprojection.common.Lg;
import com.serenegiant.widget.ProgressView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javassist.compiler.TokenId;

/* loaded from: classes.dex */
public class CameraUtils {
    private static final double ASPECT_RATIO_TOLERANCE = 0.005d;
    private static final SparseIntArray ORIENTATIONS;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        ORIENTATIONS = sparseIntArray;
        sparseIntArray.append(0, 0);
        sparseIntArray.append(1, 90);
        sparseIntArray.append(2, 180);
        sparseIntArray.append(3, ProgressView.DIRECTION_TOP_TO_BOTTOM);
    }

    public static int getAvailableCameras(CameraManager cameraManager) {
        try {
            return cameraManager.getCameraIdList().length;
        } catch (CameraAccessException e) {
            Lg.e("characteristics error : " + e.getMessage());
            return 0;
        }
    }

    public static String getBackCameraId(CameraManager cameraManager) {
        String[] cameraIdList;
        try {
            for (String str : cameraManager.getCameraIdList()) {
                if (((Integer) cameraManager.getCameraCharacteristics(str).get(CameraCharacteristics.LENS_FACING)).intValue() == 1) {
                    return str;
                }
            }
            return null;
        } catch (CameraAccessException e) {
            Lg.e("characteristics error : " + e.getMessage());
            return null;
        }
    }

    public static String getFrontFacingCameraId(CameraManager cameraManager) {
        String[] cameraIdList;
        try {
            for (String str : cameraManager.getCameraIdList()) {
                if (((Integer) cameraManager.getCameraCharacteristics(str).get(CameraCharacteristics.LENS_FACING)).intValue() == 0) {
                    return str;
                }
            }
            return null;
        } catch (CameraAccessException e) {
            Lg.e("characteristics error : " + e.getMessage());
            return null;
        }
    }

    public static Size chooseOptimalSize(int i, int i2, int i3, CameraManager cameraManager, String str) {
        List<Size> supportedPreviewSizes = getSupportedPreviewSizes(i, cameraManager, str);
        Size size = new Size(i2, i3);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int width = size.getWidth();
        int height = size.getHeight();
        for (Size size2 : supportedPreviewSizes) {
            if (size2.getWidth() <= i2 && size2.getHeight() <= i3 && size2.getHeight() == (size2.getWidth() * height) / width) {
                if (size2.getWidth() >= i2 && size2.getHeight() >= i3) {
                    arrayList.add(size2);
                } else {
                    arrayList2.add(size2);
                }
            }
        }
        if (arrayList.size() > 0) {
            return (Size) Collections.min(arrayList, new CompareSizesByArea());
        }
        if (arrayList2.size() > 0) {
            return (Size) Collections.max(arrayList2, new CompareSizesByArea());
        }
        return supportedPreviewSizes.get(0);
    }

    public static List<Size> getSupportedPreviewSizes(int i, CameraManager cameraManager, String str) {
        List<Size> arrayList = new ArrayList<>();
        try {
            StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraManager.getCameraCharacteristics(str).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (streamConfigurationMap == null) {
                return arrayList;
            }
            arrayList = Arrays.asList(streamConfigurationMap.getOutputSizes(i));
            Collections.sort(arrayList, new Comparator() { // from class: com.epson.iprojection.common.utils.CameraUtils$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return CameraUtils.lambda$getSupportedPreviewSizes$0((Size) obj, (Size) obj2);
                }
            });
            return arrayList;
        } catch (CameraAccessException e) {
            e.printStackTrace();
            return arrayList;
        }
    }

    public static /* synthetic */ int lambda$getSupportedPreviewSizes$0(Size size, Size size2) {
        return (size2.getWidth() * size2.getHeight()) - (size.getWidth() * size.getHeight());
    }

    public static int getSensorToDeviceRotation(CameraCharacteristics cameraCharacteristics, int i) {
        if (cameraCharacteristics == null) {
            return 0;
        }
        int intValue = ((Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        int i2 = ORIENTATIONS.get(i);
        if (((Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)).intValue() == 0) {
            i2 = -i2;
        }
        return ((intValue - i2) + TokenId.EXOR_E) % TokenId.EXOR_E;
    }

    public static boolean euqalsAspects(Size size, Size size2) {
        return Math.abs((((double) size.getWidth()) / ((double) size.getHeight())) - (((double) size2.getWidth()) / ((double) size2.getHeight()))) <= ASPECT_RATIO_TOLERANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class CompareSizesByArea implements Comparator<Size> {
        CompareSizesByArea() {
        }

        @Override // java.util.Comparator
        public int compare(Size size, Size size2) {
            return Long.signum((size.getWidth() * size.getHeight()) - (size2.getWidth() * size2.getHeight()));
        }
    }
}
