package com.serenegiant.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.net.Uri;
import android.util.Log;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.epson.iprojection.ui.activities.pjselect.permission.audio.PermissionAudioPresenter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class PermissionUtils {
    private static final boolean DEBUG = false;
    private final PermissionCallback mCallback;
    private final Map<String, ActivityResultLauncher<String>> mLaunchers;
    private final Map<String[], ActivityResultLauncher<String[]>> mMultiLaunchers;
    private final WeakReference<ComponentActivity> mWeakActivity;
    private static final String TAG = "PermissionUtils";
    private static final String PREF_NAME_NOT_ASK_AGAIN = TAG + "_NOT_ASK_AGAIN";

    /* loaded from: classes2.dex */
    public interface PermissionCallback {
        void onPermission(String str);

        void onPermissionDenied(String str);

        void onPermissionNeverAskAgain(String str);

        void onPermissionNeverAskAgain(String[] strArr);

        void onPermissionShowRational(String str);

        void onPermissionShowRational(String[] strArr);
    }

    /* loaded from: classes2.dex */
    public static abstract class SinglePermissionCallback implements PermissionCallback {
        @Override // com.serenegiant.system.PermissionUtils.PermissionCallback
        public void onPermissionNeverAskAgain(String[] strArr) {
        }

        @Override // com.serenegiant.system.PermissionUtils.PermissionCallback
        public void onPermissionShowRational(String[] strArr) {
        }
    }

    public PermissionUtils(ComponentActivity componentActivity, PermissionCallback permissionCallback) {
        HashMap hashMap = new HashMap();
        this.mLaunchers = hashMap;
        this.mMultiLaunchers = new HashMap();
        this.mWeakActivity = new WeakReference<>(componentActivity);
        this.mCallback = permissionCallback;
        hashMap.putAll(prepare(componentActivity, permissionCallback));
    }

    public PermissionUtils(Fragment fragment, PermissionCallback permissionCallback) {
        HashMap hashMap = new HashMap();
        this.mLaunchers = hashMap;
        this.mMultiLaunchers = new HashMap();
        this.mWeakActivity = new WeakReference<>(fragment.requireActivity());
        this.mCallback = permissionCallback;
        hashMap.putAll(prepare(fragment, permissionCallback));
    }

    public PermissionUtils prepare(ComponentActivity componentActivity, String[] strArr) {
        this.mMultiLaunchers.put(strArr, prepare(componentActivity, strArr, this.mCallback));
        return this;
    }

    public PermissionUtils prepare(Fragment fragment, String[] strArr) {
        this.mMultiLaunchers.put(strArr, prepare(fragment, strArr, this.mCallback));
        return this;
    }

    public boolean requestPermission(String str, boolean z) throws IllegalArgumentException {
        ComponentActivity componentActivity = this.mWeakActivity.get();
        if (componentActivity == null || componentActivity.isFinishing()) {
            return false;
        }
        boolean hasPermission = hasPermission(componentActivity, str);
        if (hasPermission) {
            setNeverAskAgain(componentActivity, str, false);
            return true;
        }
        if (z && ActivityCompat.shouldShowRequestPermissionRationale(componentActivity, str)) {
            this.mCallback.onPermissionShowRational(str);
        } else if (!isNeverAskAgain(componentActivity, str)) {
            ActivityResultLauncher<String> activityResultLauncher = this.mLaunchers.containsKey(str) ? this.mLaunchers.get(str) : null;
            if (activityResultLauncher != null) {
                activityResultLauncher.launch(str);
            } else {
                throw new IllegalArgumentException("has no ActivityResultLauncher for " + str);
            }
        } else {
            this.mCallback.onPermissionNeverAskAgain(str);
        }
        return hasPermission;
    }

    public boolean requestPermission(String[] strArr, boolean z) throws IllegalArgumentException {
        if (strArr.length == 1) {
            return requestPermission(strArr[0], z);
        }
        ComponentActivity componentActivity = this.mWeakActivity.get();
        if (componentActivity == null || componentActivity.isFinishing()) {
            return false;
        }
        boolean hasPermissionAll = hasPermissionAll(componentActivity, strArr);
        if (hasPermissionAll) {
            for (String str : strArr) {
                setNeverAskAgain(componentActivity, str, false);
            }
            return true;
        }
        if (z && shouldShowRequestPermissionRationale(componentActivity, strArr)) {
            this.mCallback.onPermissionShowRational(strArr);
        } else if (!isNeverAskAgain(componentActivity, strArr)) {
            ActivityResultLauncher<String[]> activityResultLauncher = this.mMultiLaunchers.containsKey(strArr) ? this.mMultiLaunchers.get(strArr) : null;
            if (activityResultLauncher != null) {
                activityResultLauncher.launch(strArr);
            } else {
                throw new IllegalArgumentException("has no ActivityResultLauncher for " + Arrays.toString(strArr));
            }
        } else {
            this.mCallback.onPermissionNeverAskAgain(strArr);
        }
        return hasPermissionAll;
    }

    public static Map<String, ActivityResultLauncher<String>> prepare(final ComponentActivity componentActivity, final PermissionCallback permissionCallback) {
        String[] requestedPermissions;
        HashMap hashMap = new HashMap();
        for (final String str : requestedPermissions(componentActivity)) {
            hashMap.put(str, componentActivity.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback() { // from class: com.serenegiant.system.PermissionUtils$$ExternalSyntheticLambda3
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(Object obj) {
                    PermissionUtils.lambda$prepare$0(str, componentActivity, permissionCallback, componentActivity, (Boolean) obj);
                }
            }));
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$prepare$0(String str, Context context, PermissionCallback permissionCallback, ComponentActivity componentActivity, Boolean bool) {
        if (bool.booleanValue()) {
            setNeverAskAgain(context, str, false);
            permissionCallback.onPermission(str);
            return;
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(componentActivity, str)) {
            setNeverAskAgain(context, str, true);
        }
        permissionCallback.onPermissionDenied(str);
    }

    public static Map<String, ActivityResultLauncher<String>> prepare(Fragment fragment, final PermissionCallback permissionCallback) {
        String[] requestedPermissions;
        HashMap hashMap = new HashMap();
        final FragmentActivity requireActivity = fragment.requireActivity();
        for (final String str : requestedPermissions(requireActivity)) {
            hashMap.put(str, fragment.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback() { // from class: com.serenegiant.system.PermissionUtils$$ExternalSyntheticLambda0
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(Object obj) {
                    PermissionUtils.lambda$prepare$1(str, requireActivity, permissionCallback, (Boolean) obj);
                }
            }));
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$prepare$1(String str, ComponentActivity componentActivity, PermissionCallback permissionCallback, Boolean bool) {
        if (bool.booleanValue()) {
            setNeverAskAgain(componentActivity, str, false);
            permissionCallback.onPermission(str);
            return;
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(componentActivity, str)) {
            setNeverAskAgain(componentActivity, str, true);
        }
        permissionCallback.onPermissionDenied(str);
    }

    public static ActivityResultLauncher<String[]> prepare(final ComponentActivity componentActivity, String[] strArr, final PermissionCallback permissionCallback) {
        return componentActivity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.serenegiant.system.PermissionUtils$$ExternalSyntheticLambda2
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                PermissionUtils.lambda$prepare$2(ComponentActivity.this, permissionCallback, (Map) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$prepare$2(ComponentActivity componentActivity, PermissionCallback permissionCallback, Map map) {
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            if (((Boolean) entry.getValue()).booleanValue()) {
                setNeverAskAgain(componentActivity, str, false);
                permissionCallback.onPermission(str);
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(componentActivity, str)) {
                    setNeverAskAgain(componentActivity, str, true);
                }
                permissionCallback.onPermissionDenied(str);
            }
        }
    }

    public static ActivityResultLauncher<String[]> prepare(Fragment fragment, String[] strArr, final PermissionCallback permissionCallback) {
        final FragmentActivity requireActivity = fragment.requireActivity();
        return fragment.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.serenegiant.system.PermissionUtils$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                PermissionUtils.lambda$prepare$3(ComponentActivity.this, permissionCallback, (Map) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$prepare$3(ComponentActivity componentActivity, PermissionCallback permissionCallback, Map map) {
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            if (((Boolean) entry.getValue()).booleanValue()) {
                setNeverAskAgain(componentActivity, str, false);
                permissionCallback.onPermission(str);
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(componentActivity, str)) {
                    setNeverAskAgain(componentActivity, str, true);
                }
                permissionCallback.onPermissionDenied(str);
            }
        }
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String[] strArr) {
        for (String str : strArr) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNeverAskAgain(Context context, String[] strArr) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME_NOT_ASK_AGAIN, 0);
        for (String str : strArr) {
            if (sharedPreferences.getBoolean(str, false)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNeverAskAgain(Context context, String str) {
        return context.getSharedPreferences(PREF_NAME_NOT_ASK_AGAIN, 0).getBoolean(str, false);
    }

    private static void setNeverAskAgain(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_NAME_NOT_ASK_AGAIN, 0).edit();
        if (z) {
            edit.putBoolean(str, true).apply();
        } else {
            edit.remove(str).apply();
        }
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
