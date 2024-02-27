package com.serenegiant.graphics;

import android.graphics.Matrix;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MatrixUtilsKtx.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002¨\u0006\u0005"}, d2 = {"getRotationDegree", "", "Landroid/graphics/Matrix;", "getScale", "", "common_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class MatrixUtilsKtxKt {
    public static final double getRotationDegree(Matrix matrix) {
        Intrinsics.checkNotNullParameter(matrix, "<this>");
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return -Math.rint(((float) Math.atan2(fArr[1], fArr[0])) * 57.29577951308232d);
    }

    public static final float getScale(Matrix matrix) {
        Intrinsics.checkNotNullParameter(matrix, "<this>");
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        float f = fArr[3];
        float f2 = fArr[0];
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }
}
