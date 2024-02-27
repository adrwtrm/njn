package com.serenegiant.preference;

import android.content.SharedPreferences;
import android.util.Log;
import com.serenegiant.utils.ObjectHelper;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class PrefHelper {
    private PrefHelper() {
    }

    public static short get(SharedPreferences sharedPreferences, String str, short s) {
        if (sharedPreferences == null || !sharedPreferences.contains(str)) {
            return s;
        }
        try {
            return (short) sharedPreferences.getInt(str, s);
        } catch (Exception unused) {
            return ObjectHelper.asShort(getObject(sharedPreferences, str, Short.valueOf(s)), s);
        }
    }

    public static int get(SharedPreferences sharedPreferences, String str, int i) {
        if (sharedPreferences == null || !sharedPreferences.contains(str)) {
            return i;
        }
        try {
            return sharedPreferences.getInt(str, i);
        } catch (Exception unused) {
            return ObjectHelper.asInt(getObject(sharedPreferences, str, Integer.valueOf(i)), i);
        }
    }

    public static long get(SharedPreferences sharedPreferences, String str, long j) {
        if (sharedPreferences == null || !sharedPreferences.contains(str)) {
            return j;
        }
        try {
            return sharedPreferences.getLong(str, j);
        } catch (Exception unused) {
            return ObjectHelper.asLong(getObject(sharedPreferences, str, Long.valueOf(j)), j);
        }
    }

    public static float get(SharedPreferences sharedPreferences, String str, float f) {
        if (sharedPreferences == null || !sharedPreferences.contains(str)) {
            return f;
        }
        try {
            return sharedPreferences.getFloat(str, f);
        } catch (Exception unused) {
            return ObjectHelper.asFloat(getObject(sharedPreferences, str, Float.valueOf(f)), f);
        }
    }

    public static double get(SharedPreferences sharedPreferences, String str, double d) {
        if (sharedPreferences == null || !sharedPreferences.contains(str)) {
            return d;
        }
        try {
            return Double.parseDouble(sharedPreferences.getString(str, Double.toString(d)));
        } catch (Exception unused) {
            return ObjectHelper.asDouble(getObject(sharedPreferences, str, Double.valueOf(d)), d);
        }
    }

    public static boolean get(SharedPreferences sharedPreferences, String str, boolean z) {
        if (sharedPreferences == null || !sharedPreferences.contains(str)) {
            return z;
        }
        try {
            return sharedPreferences.getBoolean(str, z);
        } catch (Exception unused) {
            return ObjectHelper.asBoolean(Boolean.valueOf(get(sharedPreferences, str, z)), z);
        }
    }

    public <T> T get(SharedPreferences sharedPreferences, String str, T t) {
        Class<?> cls = t.getClass();
        T t2 = (T) getObject(sharedPreferences, str, t);
        return cls.isInstance(t2) ? t2 : t;
    }

    public static final Object getObject(SharedPreferences sharedPreferences, String str) {
        return getObject(sharedPreferences, str, null);
    }

    public static final Object getObject(SharedPreferences sharedPreferences, String str, Object obj) {
        return (sharedPreferences == null || !sharedPreferences.contains(str)) ? obj : sharedPreferences.getAll().get(str);
    }

    public static void copy(SharedPreferences sharedPreferences, SharedPreferences sharedPreferences2) {
        Map<String, ?> all = sharedPreferences.getAll();
        if (all == null || all.isEmpty()) {
            return;
        }
        SharedPreferences.Editor edit = sharedPreferences2.edit();
        try {
            for (Map.Entry<String, ?> entry : all.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    edit.putString(key, (String) value);
                } else if (value instanceof Set) {
                    edit.putStringSet(key, (Set) value);
                } else if (value instanceof Integer) {
                    edit.putInt(key, ((Integer) value).intValue());
                } else if (value instanceof Long) {
                    edit.putLong(key, ((Long) value).longValue());
                } else if (value instanceof Float) {
                    edit.putFloat(key, ((Float) value).floatValue());
                } else if (value instanceof Boolean) {
                    edit.putBoolean(key, ((Boolean) value).booleanValue());
                }
            }
        } finally {
            edit.apply();
        }
    }

    public static void dump(String str, SharedPreferences sharedPreferences) {
        Map<String, ?> all = sharedPreferences.getAll();
        if (all != null && !all.isEmpty()) {
            for (Map.Entry<String, ?> entry : all.entrySet()) {
                Log.d(str, "dump:" + entry.getKey() + "=" + entry.getValue());
            }
            return;
        }
        Log.d(str, "dump:empty");
    }
}
