package com.epson.iprojection.ui.common.analytics.userproperty;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.epson.iprojection.ui.common.analytics.userproperty.enums.eUserProperty;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AppVersionProperty.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/userproperty/AppVersionProperty;", "", "()V", "set", "", "firebaseAnalytics", "Lcom/google/firebase/analytics/FirebaseAnalytics;", "context", "Landroid/content/Context;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class AppVersionProperty {
    public final void set(FirebaseAnalytics firebaseAnalytics, Context context) {
        Intrinsics.checkNotNullParameter(firebaseAnalytics, "firebaseAnalytics");
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 128);
            Intrinsics.checkNotNullExpressionValue(packageInfo, "context.packageManager.g…T_META_DATA\n            )");
            firebaseAnalytics.setUserProperty(eUserProperty.AppVersion.getUserPropertyName(), packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException unused) {
            firebaseAnalytics.setUserProperty(eUserProperty.AppVersion.getUserPropertyName(), "error");
        }
    }
}
