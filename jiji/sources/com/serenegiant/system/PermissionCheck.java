package com.serenegiant.system;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.net.Uri;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.epson.iprojection.ui.activities.pjselect.permission.audio.PermissionAudioPresenter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Deprecated
/* loaded from: classes2.dex */
public final class PermissionCheck {
    private static final boolean DEBUG = false;
    private static final String TAG = "PermissionCheck";

    private PermissionCheck() {
    }

    public static final void dumpPermissions(Context context) {
        if (context == null) {
            return;
        }
        try {
            for (PermissionGroupInfo permissionGroupInfo : context.getPackageManager().getAllPermissionGroups(128)) {
                Log.d(TAG, "dumpPermissions:" + permissionGroupInfo.name + "=" + permissionGroupInfo);
            }
        } catch (Exception unused) {
        }
    }

    public static String[] requestedPermissions(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        return (packageInfo == null || packageInfo.requestedPermissions == null) ? new String[0] : packageInfo.requestedPermissions;
    }

    public static int checkSelfPermission(Context context, String str) {
        if (context == null) {
            return -1;
        }
        try {
            return ContextCompat.checkSelfPermission(context, str);
        } catch (Exception unused) {
            return -1;
        }
    }

    public static boolean hasPermission(Context context, String str) {
        if (context == null) {
            return false;
        }
        try {
            return ContextCompat.checkSelfPermission(context, str) == 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String[] hasPermission(Context context, String[] strArr) {
        ArrayList arrayList = new ArrayList();
        if (context != null) {
            for (String str : strArr) {
                if (ContextCompat.checkSelfPermission(context, str) == 0) {
                    arrayList.add(str);
                }
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static boolean hasPermissionAll(Context context, String[] strArr) {
        if (context != null) {
            for (String str : strArr) {
                if (ContextCompat.checkSelfPermission(context, str) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean hasPermissionAll(Context context) {
        if (context != null) {
            return hasPermissionAll(context, requestedPermissions(context));
        }
        return false;
    }

    public static boolean hasAudio(Context context) {
        return hasPermission(context, PermissionAudioPresenter.permission);
    }

    public static boolean hasNetwork(Context context) {
        return hasPermission(context, "android.permission.INTERNET");
    }

    public static boolean hasNetworkState(Context context) {
        return hasPermission(context, "android.permission.ACCESS_NETWORK_STATE");
    }

    public static boolean hasWriteExternalStorage(Context context) {
        return hasPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static boolean hasReadExternalStorage(Context context) {
        return hasPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static boolean hasAccessLocation(Context context) {
        return hasPermission(context, "android.permission.ACCESS_COARSE_LOCATION") && hasPermission(context, "android.permission.ACCESS_FINE_LOCATION");
    }

    public static boolean hasAccessCoarseLocation(Context context) {
        return hasPermission(context, "android.permission.ACCESS_COARSE_LOCATION");
    }

    public static boolean hasAccessFineLocation(Context context) {
        return hasPermission(context, "android.permission.ACCESS_FINE_LOCATION");
    }

    public static boolean hasCamera(Context context) {
        return hasPermission(context, "android.permission.CAMERA");
    }

    public static void openSettings(Context context) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

    public static List<String> missingPermissions(Context context) throws IllegalArgumentException, PackageManager.NameNotFoundException {
        return missingPermissions(context, requestedPermissions(context));
    }

    public static List<String> missingPermissions(Context context, String[] strArr) throws IllegalArgumentException, PackageManager.NameNotFoundException {
        return missingPermissions(context, Arrays.asList(strArr));
    }

    public static List<String> missingPermissions(Context context, Collection<String> collection) throws IllegalArgumentException, PackageManager.NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096);
        ArrayList arrayList = new ArrayList(collection);
        String[] strArr = packageInfo.requestedPermissions;
        if (strArr != null && strArr.length > 0) {
            for (String str : strArr) {
                if (hasPermission(context, str)) {
                    arrayList.remove(str);
                }
            }
        }
        return arrayList;
    }
}
