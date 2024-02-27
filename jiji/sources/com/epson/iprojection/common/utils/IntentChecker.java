package com.epson.iprojection.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

/* loaded from: classes.dex */
public final class IntentChecker {
    public static boolean isIntentAvailable(Context context, String str) {
        return context.getPackageManager().queryIntentActivities(new Intent(str), 65536).size() > 0;
    }

    public static boolean isInstalledApp(Context context, String str) {
        for (ApplicationInfo applicationInfo : context.getPackageManager().getInstalledApplications(0)) {
            if (applicationInfo.packageName.compareTo(str) == 0) {
                return true;
            }
        }
        return false;
    }

    private IntentChecker() {
    }
}
