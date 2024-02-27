package com.serenegiant.utils;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class JSONHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "JSONHelper";

    private JSONHelper() {
    }

    public static long getLong(JSONObject jSONObject, String str, long j) throws JSONException {
        if (jSONObject.has(str)) {
            try {
                try {
                    return jSONObject.getLong(str);
                } catch (Exception unused) {
                    return jSONObject.getBoolean(str) ? 1L : 0L;
                }
            } catch (JSONException unused2) {
                return Long.parseLong(jSONObject.getString(str));
            }
        }
        return j;
    }

    public static long optLong(JSONObject jSONObject, String str, long j) {
        if (jSONObject.has(str)) {
            try {
                try {
                    try {
                        return jSONObject.getLong(str);
                    } catch (Exception e) {
                        Log.w(TAG, e);
                        return j;
                    }
                } catch (Exception unused) {
                    return jSONObject.getBoolean(str) ? 1L : 0L;
                }
            } catch (JSONException unused2) {
                return Long.parseLong(jSONObject.getString(str));
            }
        }
        return j;
    }

    public static long optLong(JSONArray jSONArray, int i, long j) {
        if (jSONArray.length() > i) {
            try {
                try {
                    try {
                        return jSONArray.getLong(i);
                    } catch (Exception e) {
                        Log.w(TAG, e);
                        return j;
                    }
                } catch (Exception unused) {
                    return jSONArray.getBoolean(i) ? 1L : 0L;
                }
            } catch (JSONException unused2) {
                return Long.parseLong(jSONArray.getString(i));
            }
        }
        return j;
    }

    public static int getInt(JSONObject jSONObject, String str, int i) throws JSONException {
        if (jSONObject.has(str)) {
            try {
                try {
                    return jSONObject.getInt(str);
                } catch (Exception unused) {
                    return jSONObject.getBoolean(str) ? 1 : 0;
                }
            } catch (JSONException unused2) {
                return Integer.parseInt(jSONObject.getString(str));
            }
        }
        return i;
    }

    public static int optInt(JSONObject jSONObject, String str, int i) {
        if (jSONObject.has(str)) {
            try {
                try {
                    try {
                        return jSONObject.getInt(str);
                    } catch (Exception unused) {
                        return jSONObject.getBoolean(str) ? 1 : 0;
                    }
                } catch (JSONException unused2) {
                    return Integer.parseInt(jSONObject.getString(str));
                }
            } catch (Exception e) {
                Log.w(TAG, e);
                return i;
            }
        }
        return i;
    }

    public static int optInt(JSONArray jSONArray, int i, int i2) {
        if (jSONArray.length() > i) {
            try {
                try {
                    try {
                        return jSONArray.getInt(i);
                    } catch (Exception unused) {
                        return jSONArray.getBoolean(i) ? 1 : 0;
                    }
                } catch (JSONException unused2) {
                    return Integer.parseInt(jSONArray.getString(i));
                }
            } catch (Exception e) {
                Log.w(TAG, e);
                return i2;
            }
        }
        return i2;
    }

    public static boolean getBoolean(JSONObject jSONObject, String str, boolean z) throws JSONException {
        if (jSONObject.has(str)) {
            try {
                return jSONObject.getBoolean(str);
            } catch (Exception unused) {
                try {
                    if (jSONObject.getInt(str) != 0) {
                        return true;
                    }
                } catch (JSONException unused2) {
                    if (jSONObject.getDouble(str) != 0.0d) {
                        return true;
                    }
                }
                return false;
            }
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001f, code lost:
        if (r4.getDouble(r5) == 0.0d) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0011, code lost:
        if (r4.getInt(r5) == 0) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean optBoolean(org.json.JSONObject r4, java.lang.String r5, boolean r6) {
        /*
            boolean r0 = r4.has(r5)
            if (r0 == 0) goto L28
            boolean r6 = r4.getBoolean(r5)     // Catch: java.lang.Exception -> Lb
            goto L28
        Lb:
            r0 = 1
            r1 = 0
            int r4 = r4.getInt(r5)     // Catch: org.json.JSONException -> L17
            if (r4 == 0) goto L15
        L13:
            r6 = r0
            goto L28
        L15:
            r6 = r1
            goto L28
        L17:
            double r4 = r4.getDouble(r5)     // Catch: org.json.JSONException -> L22
            r2 = 0
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L15
            goto L13
        L22:
            r4 = move-exception
            java.lang.String r5 = com.serenegiant.utils.JSONHelper.TAG
            android.util.Log.w(r5, r4)
        L28:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.utils.JSONHelper.optBoolean(org.json.JSONObject, java.lang.String, boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001f, code lost:
        if (r4.getDouble(r5) == 0.0d) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0011, code lost:
        if (r4.getInt(r5) == 0) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean optBoolean(org.json.JSONArray r4, int r5, boolean r6) {
        /*
            int r0 = r4.length()
            if (r0 <= r5) goto L28
            boolean r6 = r4.getBoolean(r5)     // Catch: java.lang.Exception -> Lb
            goto L28
        Lb:
            r0 = 1
            r1 = 0
            int r4 = r4.getInt(r5)     // Catch: org.json.JSONException -> L17
            if (r4 == 0) goto L15
        L13:
            r6 = r0
            goto L28
        L15:
            r6 = r1
            goto L28
        L17:
            double r4 = r4.getDouble(r5)     // Catch: org.json.JSONException -> L22
            r2 = 0
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L15
            goto L13
        L22:
            r4 = move-exception
            java.lang.String r5 = com.serenegiant.utils.JSONHelper.TAG
            android.util.Log.w(r5, r4)
        L28:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.utils.JSONHelper.optBoolean(org.json.JSONArray, int, boolean):boolean");
    }
}
