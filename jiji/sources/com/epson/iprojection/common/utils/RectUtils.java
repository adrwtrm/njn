package com.epson.iprojection.common.utils;

import android.graphics.Rect;
import kotlin.Metadata;

/* compiled from: RectUtils.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/common/utils/RectUtils;", "", "()V", "getCenteringRect", "Landroid/graphics/Rect;", "srcWidth", "", "srcHeight", "dstWidth", "dstHeight", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class RectUtils {
    public static final RectUtils INSTANCE = new RectUtils();

    private RectUtils() {
    }

    public final Rect getCenteringRect(int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int i6 = i4 - i2;
        Rect rect = new Rect();
        int i7 = i5 / 2;
        int i8 = i6 / 2;
        rect.left = i5 > 0 ? i7 : 0;
        rect.top = i6 > 0 ? i8 : 0;
        if (i5 > 0) {
            i3 -= i7;
        }
        rect.right = i3;
        if (i6 > 0) {
            i4 -= i8;
        }
        rect.bottom = i4;
        return rect;
    }
}
