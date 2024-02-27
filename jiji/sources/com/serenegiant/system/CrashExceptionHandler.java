package com.serenegiant.system;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.serenegiant.utils.LogUtils;
import java.lang.Thread;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class CrashExceptionHandler implements Thread.UncaughtExceptionHandler {
    static final String LOG_NAME = "crashrepo.txt";
    static final String MAIL_TO = "t_saki@serenegiant.com";
    private static final int REQUEST_RESTART_ACTIVITY = 2039;
    private static final String TAG = "CrashExceptionHandler";
    private final Thread.UncaughtExceptionHandler mHandler = Thread.getDefaultUncaughtExceptionHandler();
    private final WeakReference<Context> mWeakContext;

    public static void registerCrashHandler(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(context));
    }

    @Deprecated
    public static void setAutoRestart(final Context context, final PendingIntent pendingIntent, final long j) {
        final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.serenegiant.system.CrashExceptionHandler.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
                try {
                    ((AlarmManager) ContextUtils.requireSystemService(context, AlarmManager.class)).set(0, System.currentTimeMillis() + j, pendingIntent);
                } finally {
                    defaultUncaughtExceptionHandler.uncaughtException(thread, th);
                }
            }
        });
    }

    @Deprecated
    public static void setAutoRestart(Context context, Class<? extends Activity> cls, long j) {
        setAutoRestart(context, PendingIntent.getActivity(context.getApplicationContext(), REQUEST_RESTART_ACTIVITY, Intent.makeMainActivity(new ComponentName(context, cls)), BuildCheck.isAPI31() ? 335544320 : 268435456), j);
    }

    private CrashExceptionHandler(Context context) {
        this.mWeakContext = new WeakReference<>(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0064, code lost:
        if (r1 == null) goto L10;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0074 A[Catch: Exception -> 0x0078, TRY_LEAVE, TryCatch #4 {Exception -> 0x0078, blocks: (B:27:0x0070, B:29:0x0074), top: B:37:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    @Override // java.lang.Thread.UncaughtExceptionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void uncaughtException(java.lang.Thread r6, java.lang.Throwable r7) {
        /*
            r5 = this;
            java.lang.ref.WeakReference<android.content.Context> r0 = r5.mWeakContext
            java.lang.Object r0 = r0.get()
            android.content.Context r0 = (android.content.Context) r0
            if (r0 == 0) goto L70
            r1 = 0
            java.lang.String r2 = "crashrepo.txt"
            r3 = 0
            java.io.FileOutputStream r2 = r0.openFileOutput(r2, r3)     // Catch: java.lang.Throwable -> L57 org.json.JSONException -> L59 java.io.FileNotFoundException -> L60
            java.io.PrintWriter r3 = new java.io.PrintWriter     // Catch: java.lang.Throwable -> L57 org.json.JSONException -> L59 java.io.FileNotFoundException -> L60
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L57 org.json.JSONException -> L59 java.io.FileNotFoundException -> L60
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            r1.<init>()     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            java.lang.String r2 = "Build"
            org.json.JSONObject r4 = getBuildInfo()     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            r1.put(r2, r4)     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            java.lang.String r2 = "PackageInfo"
            org.json.JSONObject r4 = getPackageInfo(r0)     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            r1.put(r2, r4)     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            java.lang.String r2 = "Exception"
            org.json.JSONObject r4 = getExceptionInfo(r7)     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            r1.put(r2, r4)     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            java.lang.String r2 = "SharedPreferences"
            org.json.JSONObject r0 = getPreferencesInfo(r0)     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            r1.put(r2, r0)     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            r3.print(r0)     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            r3.flush()     // Catch: java.lang.Throwable -> L4e org.json.JSONException -> L51 java.io.FileNotFoundException -> L54
            r3.close()
            goto L70
        L4e:
            r6 = move-exception
            r1 = r3
            goto L6a
        L51:
            r0 = move-exception
            r1 = r3
            goto L5a
        L54:
            r0 = move-exception
            r1 = r3
            goto L61
        L57:
            r6 = move-exception
            goto L6a
        L59:
            r0 = move-exception
        L5a:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L57
            if (r1 == 0) goto L70
            goto L66
        L60:
            r0 = move-exception
        L61:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L57
            if (r1 == 0) goto L70
        L66:
            r1.close()
            goto L70
        L6a:
            if (r1 == 0) goto L6f
            r1.close()
        L6f:
            throw r6
        L70:
            java.lang.Thread$UncaughtExceptionHandler r0 = r5.mHandler     // Catch: java.lang.Exception -> L78
            if (r0 == 0) goto L7e
            r0.uncaughtException(r6, r7)     // Catch: java.lang.Exception -> L78
            goto L7e
        L78:
            r6 = move-exception
            java.lang.String r7 = com.serenegiant.system.CrashExceptionHandler.TAG
            android.util.Log.w(r7, r6)
        L7e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.system.CrashExceptionHandler.uncaughtException(java.lang.Thread, java.lang.Throwable):void");
    }

    private static JSONObject getBuildInfo() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("BRAND", Build.BRAND);
        jSONObject.put("MODEL", Build.MODEL);
        jSONObject.put("DEVICE", Build.DEVICE);
        jSONObject.put("MANUFACTURER", Build.MANUFACTURER);
        jSONObject.put("VERSION.SDK_INT", Build.VERSION.SDK_INT);
        jSONObject.put("VERSION.RELEASE", Build.VERSION.RELEASE);
        return jSONObject;
    }

    private static JSONObject getPackageInfo(Context context) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            jSONObject.put("packageName", packageInfo.packageName);
            jSONObject.put("versionCode", packageInfo.versionCode);
            jSONObject.put("versionName", packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            jSONObject.put("error", e);
        }
        return jSONObject;
    }

    private static JSONObject getExceptionInfo(Throwable th) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(AppMeasurementSdk.ConditionalUserProperty.NAME, th.getClass().getName());
        jSONObject.put("cause", th.getCause());
        jSONObject.put("message", th.getMessage());
        JSONArray jSONArray = new JSONArray();
        StackTraceElement[] stackTrace = th.getStackTrace();
        int length = stackTrace.length;
        for (int i = 0; i < length; i++) {
            jSONArray.put("at " + LogUtils.getMetaInfo(stackTrace[i]));
        }
        jSONObject.put("stacktrace", jSONArray);
        return jSONObject;
    }

    private static JSONObject getPreferencesInfo(Context context) throws JSONException {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, ?> entry : defaultSharedPreferences.getAll().entrySet()) {
            jSONObject.put(entry.getKey(), entry.getValue());
        }
        return jSONObject;
    }
}
