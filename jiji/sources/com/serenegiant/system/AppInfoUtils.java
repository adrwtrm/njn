package com.serenegiant.system;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.StringBuilderPrinter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class AppInfoUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "AppInfoUtils";

    /* loaded from: classes2.dex */
    public interface AppInfoFilterCallback {
        boolean onFilter(ApplicationInfo applicationInfo);
    }

    private AppInfoUtils() {
    }

    public static boolean isSystemAppOrUpdatedSystemApp(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & 1) == 0 && (applicationInfo.flags & 128) == 0) ? false : true;
    }

    public static boolean isSystemApp(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 1) != 0;
    }

    public static boolean isUpdatedSystemApp(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 128) != 0;
    }

    public static boolean hasFeature(Context context, ApplicationInfo applicationInfo, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(applicationInfo.packageName, 16384);
            if (packageInfo.reqFeatures != null && packageInfo.reqFeatures.length > 0) {
                for (FeatureInfo featureInfo : packageInfo.reqFeatures) {
                    if (str.equals(featureInfo.name)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public static List<ApplicationInfo> getInstalledApplications(Context context, AppInfoFilterCallback appInfoFilterCallback) {
        ArrayList arrayList = new ArrayList();
        for (ApplicationInfo applicationInfo : context.getPackageManager().getInstalledApplications(128)) {
            if (appInfoFilterCallback == null || appInfoFilterCallback.onFilter(applicationInfo)) {
                arrayList.add(applicationInfo);
            }
        }
        return arrayList;
    }

    public static Drawable getApplicationIcon(Context context, ApplicationInfo applicationInfo) {
        return applicationInfo.loadIcon(context.getPackageManager());
    }

    public static CharSequence getDisplayName(Context context, ApplicationInfo applicationInfo) {
        return applicationInfo.loadLabel(context.getPackageManager());
    }

    public static String toString(ApplicationInfo applicationInfo) {
        StringBuilder sb = new StringBuilder();
        applicationInfo.dump(new StringBuilderPrinter(sb), "");
        return sb.toString();
    }

    public static String getDefaultActivity(Context context, String str) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        ComponentName component = launchIntentForPackage != null ? launchIntentForPackage.getComponent() : null;
        if (component != null) {
            return component.getClassName();
        }
        return null;
    }

    public static ActivityInfo findLauncherActivity(Context context, AppInfoFilterCallback appInfoFilterCallback) {
        return findActivity(context, new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER"), appInfoFilterCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.ActivityInfo findActivity(android.content.Context r1, android.content.Intent r2, com.serenegiant.system.AppInfoUtils.AppInfoFilterCallback r3) {
        /*
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            r0 = 131200(0x20080, float:1.8385E-40)
            java.util.List r1 = r1.queryIntentActivities(r2, r0)
            java.util.Iterator r1 = r1.iterator()
        Lf:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L64
            java.lang.Object r2 = r1.next()
            android.content.pm.ResolveInfo r2 = (android.content.pm.ResolveInfo) r2
            android.content.pm.ActivityInfo r2 = r2.activityInfo
            if (r3 == 0) goto L27
            android.content.pm.ApplicationInfo r0 = r2.applicationInfo
            boolean r0 = r3.onFilter(r0)
            if (r0 == 0) goto Lf
        L27:
            java.lang.String r1 = r2.packageName
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L35
            android.content.pm.ApplicationInfo r1 = r2.applicationInfo     // Catch: java.lang.Exception -> L35
            java.lang.String r1 = r1.packageName     // Catch: java.lang.Exception -> L35
            r2.packageName = r1     // Catch: java.lang.Exception -> L35
        L35:
            java.lang.String r1 = r2.targetActivity
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L65
            java.lang.String r1 = r2.name
            java.lang.String r3 = "."
            boolean r1 = r1.startsWith(r3)
            if (r1 == 0) goto L5f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = r2.packageName
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = r2.name
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            r2.targetActivity = r1
            goto L65
        L5f:
            java.lang.String r1 = r2.name
            r2.targetActivity = r1
            goto L65
        L64:
            r2 = 0
        L65:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.system.AppInfoUtils.findActivity(android.content.Context, android.content.Intent, com.serenegiant.system.AppInfoUtils$AppInfoFilterCallback):android.content.pm.ActivityInfo");
    }

    public static List<ActivityInfo> getLauncherActivities(Context context, AppInfoFilterCallback appInfoFilterCallback) {
        return getActivities(context, new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER"), appInfoFilterCallback);
    }

    public static List<ActivityInfo> getActivities(Context context, Intent intent, AppInfoFilterCallback appInfoFilterCallback) {
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 131200)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (appInfoFilterCallback == null || appInfoFilterCallback.onFilter(activityInfo.applicationInfo)) {
                if (TextUtils.isEmpty(activityInfo.packageName)) {
                    try {
                        activityInfo.packageName = activityInfo.applicationInfo.packageName;
                    } catch (Exception unused) {
                    }
                }
                if (TextUtils.isEmpty(activityInfo.targetActivity)) {
                    if (activityInfo.name.startsWith(".")) {
                        activityInfo.targetActivity = activityInfo.packageName + activityInfo.name;
                    } else {
                        activityInfo.targetActivity = activityInfo.name;
                    }
                }
                arrayList.add(activityInfo);
            }
        }
        return arrayList;
    }

    public static List<String> getInstalledPackages(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            Process exec = Runtime.getRuntime().exec("pm list packages -u");
            exec.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                arrayList.add(readLine.replace("package:", ""));
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public static List<String> getLauncherPackages(Context context) {
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER"), 131072)) {
            arrayList.add(resolveInfo.activityInfo.applicationInfo.packageName);
        }
        return arrayList;
    }
}
