package com.epson.iprojection.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import java.util.Map;

/* loaded from: classes.dex */
public class PrefUtils {
    public static final String CONSENTED_DAT = "Consented";
    public static final String CONSENTED_TAG = "iProjection Terms V300";
    public static final int ERR = Integer.MIN_VALUE;
    private static final int MAX = 10000;

    public static String read(Context context, String str) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, null);
    }

    public static String read(Context context, String str, int i) {
        return read(context, str + i);
    }

    public static int readInt(Context context, String str) {
        String read = read(context, str);
        if (read == null) {
            return Integer.MIN_VALUE;
        }
        try {
            return Integer.parseInt(read);
        } catch (NumberFormatException unused) {
            Lg.e("format error");
            return Integer.MIN_VALUE;
        }
    }

    public static boolean readBoolean(Context context, String str, boolean z) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(str, z);
    }

    public static int readInt(Context context, String str, int i) {
        return readInt(context, str + i);
    }

    public static synchronized void write(Context context, String str, String str2) {
        synchronized (PrefUtils.class) {
            write(context, str, str2, (SharedPreferences.Editor) null);
        }
    }

    public static synchronized void write(Context context, String str, boolean z) {
        synchronized (PrefUtils.class) {
            write(context, str, z, (SharedPreferences.Editor) null);
        }
    }

    public static synchronized void write(Context context, String str, String str2, SharedPreferences.Editor editor) {
        synchronized (PrefUtils.class) {
            if (editor != null) {
                editor.putString(str, str2);
            } else {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString(str, str2).commit();
            }
        }
    }

    public static synchronized void write(Context context, String str, boolean z, SharedPreferences.Editor editor) {
        synchronized (PrefUtils.class) {
            if (editor != null) {
                editor.putBoolean(str, z);
            } else {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(str, z).commit();
            }
        }
    }

    public static synchronized void write(Context context, String str, String str2, int i, SharedPreferences.Editor editor, boolean z) {
        synchronized (PrefUtils.class) {
            editor.putString(str + i, str2);
            if (z) {
                editor.commit();
            }
        }
    }

    public static synchronized void write(Context context, String str, String str2, int i, SharedPreferences.Editor editor) {
        synchronized (PrefUtils.class) {
            write(context, str + i, str2, editor);
        }
    }

    public static synchronized void writeInt(Context context, String str, int i, SharedPreferences.Editor editor) {
        synchronized (PrefUtils.class) {
            write(context, str, Integer.toString(i), editor);
        }
    }

    public static synchronized void writeInt(Context context, String str, int i, int i2) {
        synchronized (PrefUtils.class) {
            write(context, str, Integer.toString(i2), i, null);
        }
    }

    public static synchronized void writeInt(Context context, String str, int i) {
        synchronized (PrefUtils.class) {
            write(context, str, Integer.toString(i));
        }
    }

    public static synchronized void writeBoolean(Context context, String str, boolean z) {
        synchronized (PrefUtils.class) {
            write(context, str, z);
        }
    }

    public static void add(Context context, String str, String str2, SharedPreferences.Editor editor) {
        write(context, str, str2, getSize(context, str), editor);
    }

    public static synchronized void delete(Context context, String str, SharedPreferences.Editor editor) {
        synchronized (PrefUtils.class) {
            if (editor != null) {
                editor.remove(str);
            } else {
                PreferenceManager.getDefaultSharedPreferences(context).edit().remove(str).commit();
            }
        }
    }

    public static void delete(Context context, String str, int i) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        while (true) {
            if (i >= 10000) {
                break;
            }
            int i2 = i + 1;
            String read = read(context, str, i2);
            if (read == null) {
                delete(context, str + i, edit);
                break;
            } else {
                write(context, str, read, i, edit);
                i = i2;
            }
        }
        edit.commit();
    }

    public static void deleteAll(Context context, String str) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        for (int i = 0; i < 10000 && read(context, str, i) != null; i++) {
            delete(context, str + i, edit);
        }
        edit.commit();
    }

    public static int getSize(Context context, String str) {
        for (int i = 0; i < 10000; i++) {
            if (read(context, str, i) == null) {
                return i;
            }
        }
        return 0;
    }

    public static void insert(Context context, String str, String str2, int i) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        for (int size = getSize(context, str) - 1; size >= i; size--) {
            write(context, str, read(context, str, size), size + 1, edit);
        }
        write(context, str, str2, i, edit);
        edit.commit();
    }

    public static boolean isClientMode(Context context) {
        int readInt = readInt(context, PrefTagDefine.conPJ_CLIENT_MODE);
        return readInt != Integer.MIN_VALUE && readInt == 1;
    }

    public static boolean isConsented(Context context) {
        String read = read(context, CONSENTED_TAG);
        return read != null && read.compareTo(CONSENTED_DAT) == 0;
    }

    public static void deleteTags(Context context, String str) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Map<String, ?> all = PreferenceManager.getDefaultSharedPreferences(context).getAll();
        if (all.size() > 0) {
            for (String str2 : all.keySet()) {
                if (str2.startsWith(str)) {
                    delete(context, str2, edit);
                }
            }
            edit.commit();
        }
    }

    public static void recordConsented(Context context) {
        write(context, CONSENTED_TAG, CONSENTED_DAT, (SharedPreferences.Editor) null);
    }
}
