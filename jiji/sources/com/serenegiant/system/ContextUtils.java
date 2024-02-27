package com.serenegiant.system;

import android.content.Context;
import androidx.core.content.ContextCompat;

/* loaded from: classes2.dex */
public class ContextUtils {
    private ContextUtils() {
    }

    public static <T> T getSystemService(Context context, Class<T> cls) {
        return (T) ContextCompat.getSystemService(context, cls);
    }

    public static <T> T requireSystemService(Context context, Class<T> cls) throws IllegalArgumentException {
        T t = (T) ContextCompat.getSystemService(context, cls);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException();
    }
}
