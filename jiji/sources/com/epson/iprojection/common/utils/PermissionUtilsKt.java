package com.epson.iprojection.common.utils;

import android.content.Context;
import android.os.Build;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PermissionUtils.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0006\u0010\u0006\u001a\u00020\u0001¨\u0006\u0007"}, d2 = {"isUsablePermission", "", "context", "Landroid/content/Context;", "permission", "", "needGetLocationPermissionForWifi", "app_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PermissionUtilsKt {
    public static final boolean isUsablePermission(Context context, String permission) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(permission, "permission");
        return context.checkSelfPermission(permission) == 0;
    }

    public static final boolean needGetLocationPermissionForWifi() {
        return Build.VERSION.SDK_INT < 29;
    }
}
