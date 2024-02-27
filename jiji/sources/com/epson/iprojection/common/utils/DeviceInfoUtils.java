package com.epson.iprojection.common.utils;

import android.os.Build;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: DeviceInfoUtils.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/common/utils/DeviceInfoUtils;", "", "()V", "isDeviceHuawei", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class DeviceInfoUtils {
    public static final DeviceInfoUtils INSTANCE = new DeviceInfoUtils();

    private DeviceInfoUtils() {
    }

    public final boolean isDeviceHuawei() {
        String manufacturer = Build.MANUFACTURER;
        String brand = Build.BRAND;
        Intrinsics.checkNotNullExpressionValue(manufacturer, "manufacturer");
        String lowerCase = manufacturer.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (!StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) "huawei", false, 2, (Object) null)) {
            Intrinsics.checkNotNullExpressionValue(brand, "brand");
            String lowerCase2 = brand.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (!StringsKt.contains$default((CharSequence) lowerCase2, (CharSequence) "huawei", false, 2, (Object) null)) {
                return false;
            }
        }
        return true;
    }
}
