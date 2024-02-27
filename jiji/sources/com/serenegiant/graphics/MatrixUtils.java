package com.serenegiant.graphics;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class MatrixUtils {
    private static final Map<ImageView.ScaleType, ScaleType> sScaleTypeMap;

    private MatrixUtils() {
    }

    public static float getRotate(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return getRotate(fArr);
    }

    public static float getRotate(float[] fArr) {
        return (float) Math.round(Math.atan2(fArr[1], fArr[0]) * 57.29577951308232d);
    }

    public static float getScale(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return getScale(fArr);
    }

    public static float getScale(float[] fArr) {
        float f = fArr[0];
        float f2 = fArr[3];
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    public static float[] toGLMatrix(Matrix matrix, float[] fArr, float[] fArr2) {
        matrix.getValues(fArr2);
        fArr[0] = fArr2[0];
        fArr[1] = fArr2[3];
        fArr[2] = 0.0f;
        fArr[3] = fArr2[6];
        fArr[4] = fArr2[1];
        fArr[5] = fArr2[4];
        fArr[6] = 0.0f;
        fArr[7] = fArr2[7];
        fArr[8] = 0.0f;
        fArr[9] = 0.0f;
        fArr[10] = 1.0f;
        fArr[11] = 0.0f;
        fArr[12] = fArr2[2];
        fArr[13] = fArr2[5];
        fArr[14] = 0.0f;
        fArr[15] = fArr2[8];
        return fArr;
    }

    public static Matrix toAndroidMatrix(float[] fArr, Matrix matrix, float[] fArr2) {
        fArr2[0] = fArr[0];
        fArr2[3] = fArr[1];
        fArr2[6] = fArr[3];
        fArr2[1] = fArr[4];
        fArr2[4] = fArr[5];
        fArr2[7] = fArr[7];
        fArr2[2] = fArr[12];
        fArr2[5] = fArr[13];
        fArr2[8] = fArr[15];
        matrix.setValues(fArr2);
        return matrix;
    }

    public static String toGLMatrixString(float[] fArr) {
        return "GLMatrix[" + fArr[0] + ", " + fArr[1] + ", " + fArr[2] + ", " + fArr[3] + "][" + fArr[4] + ", " + fArr[5] + ", " + fArr[6] + ", " + fArr[7] + "][" + fArr[8] + ", " + fArr[9] + ", " + fArr[10] + ", " + fArr[11] + "][" + fArr[12] + ", " + fArr[13] + ", " + fArr[14] + ", " + fArr[15] + ']';
    }

    /* loaded from: classes2.dex */
    public enum ScaleType {
        MATRIX(0),
        FIT_XY(1),
        FIT_START(2),
        FIT_CENTER(3),
        FIT_END(4),
        CENTER(5),
        CENTER_CROP(6),
        CENTER_INSIDE(7);
        
        final int id;

        ScaleType(int i) {
            this.id = i;
        }
    }

    static {
        HashMap hashMap = new HashMap();
        sScaleTypeMap = hashMap;
        hashMap.put(ImageView.ScaleType.MATRIX, ScaleType.MATRIX);
        hashMap.put(ImageView.ScaleType.FIT_XY, ScaleType.FIT_XY);
        hashMap.put(ImageView.ScaleType.FIT_START, ScaleType.FIT_START);
        hashMap.put(ImageView.ScaleType.FIT_CENTER, ScaleType.FIT_CENTER);
        hashMap.put(ImageView.ScaleType.FIT_END, ScaleType.FIT_END);
        hashMap.put(ImageView.ScaleType.CENTER, ScaleType.CENTER);
        hashMap.put(ImageView.ScaleType.CENTER_CROP, ScaleType.CENTER_CROP);
        hashMap.put(ImageView.ScaleType.CENTER_INSIDE, ScaleType.CENTER_INSIDE);
    }

    public static ImageView.ScaleType toImageViewScaleType(ScaleType scaleType) {
        for (Map.Entry<ImageView.ScaleType, ScaleType> entry : sScaleTypeMap.entrySet()) {
            if (entry.getValue() == scaleType) {
                return entry.getKey();
            }
        }
        return ImageView.ScaleType.CENTER_CROP;
    }

    public static ScaleType fromImageViewScaleType(ImageView.ScaleType scaleType) {
        Map<ImageView.ScaleType, ScaleType> map = sScaleTypeMap;
        if (map.containsKey(scaleType)) {
            return map.get(scaleType);
        }
        return ScaleType.CENTER_CROP;
    }

    public static void updateDrawMatrix(ImageView.ScaleType scaleType, Matrix matrix, Rect rect, float f, float f2) {
        updateDrawMatrix(fromImageViewScaleType(scaleType), matrix, rect.width(), rect.height(), f, f2);
    }

    public static void updateDrawMatrix(ScaleType scaleType, Matrix matrix, float f, float f2, float f3, float f4) {
        float f5;
        float f6;
        float f7 = 0.0f;
        if (f3 <= 0.0f || f4 <= 0.0f || f <= 0.0f || f2 <= 0.0f) {
            matrix.reset();
        } else if (scaleType == ScaleType.CENTER_CROP) {
            if (f3 * f2 > f * f4) {
                f5 = f2 / f4;
                f6 = 0.0f;
                f7 = (f - (f3 * f5)) * 0.5f;
            } else {
                float f8 = f / f3;
                float f9 = (f2 - (f4 * f8)) * 0.5f;
                f5 = f8;
                f6 = f9;
            }
            matrix.setScale(f5, f5);
            matrix.postTranslate(Math.round(f7), Math.round(f6));
        } else if (scaleType == ScaleType.CENTER_INSIDE) {
            float min = (f3 > f || f4 > f2) ? Math.min(f / f3, f2 / f4) : 1.0f;
            matrix.setScale(min, min);
            matrix.postTranslate(Math.round((f - (f3 * min)) * 0.5f), Math.round((f2 - (f4 * min)) * 0.5f));
        } else if (scaleType == ScaleType.CENTER) {
            matrix.setTranslate(Math.round((f - f3) * 0.5f), Math.round((f2 - f4) * 0.5f));
        } else {
            RectF rectF = new RectF(0.0f, 0.0f, f, f2);
            RectF rectF2 = new RectF(0.0f, 0.0f, f3, f4);
            int i = AnonymousClass1.$SwitchMap$com$serenegiant$graphics$MatrixUtils$ScaleType[scaleType.ordinal()];
            if (i == 1) {
                matrix.setRectToRect(rectF2, rectF, Matrix.ScaleToFit.FILL);
            } else if (i == 2) {
                matrix.setRectToRect(rectF2, rectF, Matrix.ScaleToFit.START);
            } else if (i == 3) {
                matrix.setRectToRect(rectF2, rectF, Matrix.ScaleToFit.CENTER);
            } else if (i != 4) {
            } else {
                matrix.setRectToRect(rectF2, rectF, Matrix.ScaleToFit.END);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.serenegiant.graphics.MatrixUtils$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$serenegiant$graphics$MatrixUtils$ScaleType;

        static {
            int[] iArr = new int[ScaleType.values().length];
            $SwitchMap$com$serenegiant$graphics$MatrixUtils$ScaleType = iArr;
            try {
                iArr[ScaleType.FIT_XY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$serenegiant$graphics$MatrixUtils$ScaleType[ScaleType.FIT_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$serenegiant$graphics$MatrixUtils$ScaleType[ScaleType.FIT_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$serenegiant$graphics$MatrixUtils$ScaleType[ScaleType.FIT_END.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$serenegiant$graphics$MatrixUtils$ScaleType[ScaleType.MATRIX.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }
}
