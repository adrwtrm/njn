package com.serenegiant.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/* loaded from: classes2.dex */
public class TypedArrayUtils {
    private TypedArrayUtils() {
    }

    public static float[] readArray(Resources resources, int i, float f) {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            float[] fArr = new float[length];
            for (int i2 = 0; i2 < length; i2++) {
                fArr[i2] = obtainTypedArray.getFloat(i2, f);
            }
            return fArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static boolean[] readArray(Resources resources, int i, boolean z) {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            boolean[] zArr = new boolean[length];
            for (int i2 = 0; i2 < length; i2++) {
                zArr[i2] = obtainTypedArray.getBoolean(i2, z);
            }
            return zArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static String[] readArray(Resources resources, int i, String str) {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            String[] strArr = new String[length];
            for (int i2 = 0; i2 < length; i2++) {
                String string = obtainTypedArray.getString(i2);
                strArr[i2] = string;
                if (string == null) {
                    strArr[i2] = str;
                }
            }
            return strArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static CharSequence[] readArray(Resources resources, int i, CharSequence charSequence) {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            CharSequence[] charSequenceArr = new CharSequence[length];
            for (int i2 = 0; i2 < length; i2++) {
                CharSequence text = obtainTypedArray.getText(i2);
                charSequenceArr[i2] = text;
                if (text == null) {
                    charSequenceArr[i2] = charSequence;
                }
            }
            return charSequenceArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static int[] readArray(Resources resources, int i, int i2) {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            int[] iArr = new int[length];
            for (int i3 = 0; i3 < length; i3++) {
                iArr[i3] = obtainTypedArray.getInt(i3, i2);
            }
            return iArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static int[] readArrayWithException(Resources resources, int i, int i2) throws UnsupportedOperationException {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            int[] iArr = new int[length];
            for (int i3 = 0; i3 < length; i3++) {
                iArr[i3] = obtainTypedArray.getInteger(i3, i2);
            }
            return iArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static int[] readColorArray(Resources resources, int i, int i2) {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            int[] iArr = new int[length];
            for (int i3 = 0; i3 < length; i3++) {
                iArr[i3] = obtainTypedArray.getColor(i3, i2);
            }
            return iArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static float[] readDimensionArray(Resources resources, int i, float f) {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            float[] fArr = new float[length];
            for (int i2 = 0; i2 < length; i2++) {
                fArr[i2] = obtainTypedArray.getDimension(i2, f);
            }
            return fArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static Drawable[] readDrawableArray(Resources resources, int i) {
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            Drawable[] drawableArr = new Drawable[length];
            for (int i2 = 0; i2 < length; i2++) {
                drawableArr[i2] = obtainTypedArray.getDrawable(i2);
            }
            return drawableArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static int getAttr(Context context, int i, int i2) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId != 0 ? i : i2;
    }
}
