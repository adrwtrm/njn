package com.serenegiant.system;

import android.content.Context;
import android.util.DisplayMetrics;

/* loaded from: classes2.dex */
public class DisplayMetricsUtils {
    private DisplayMetricsUtils() {
    }

    public static float dpToPixels(DisplayMetrics displayMetrics, float f) {
        return f * displayMetrics.density;
    }

    public static double dpToPixels(DisplayMetrics displayMetrics, double d) {
        return d * displayMetrics.density;
    }

    public static int dpToPixelsInt(DisplayMetrics displayMetrics, float f) {
        return Math.round(dpToPixels(displayMetrics, f));
    }

    public static int dpToPixelsInt(DisplayMetrics displayMetrics, double d) {
        return (int) Math.round(dpToPixels(displayMetrics, d));
    }

    public static float dpToPixels(Context context, float f) {
        return dpToPixels(context.getResources().getDisplayMetrics(), f);
    }

    public static double dpToPixels(Context context, double d) {
        return dpToPixels(context.getResources().getDisplayMetrics(), d);
    }

    public static int dpToPixelsInt(Context context, float f) {
        return dpToPixelsInt(context.getResources().getDisplayMetrics(), f);
    }

    public static float pixelsToDp(DisplayMetrics displayMetrics, float f) {
        return f / displayMetrics.density;
    }

    public static double pixelsToDp(DisplayMetrics displayMetrics, double d) {
        return d / displayMetrics.density;
    }

    public static float pixelsToDp(Context context, float f) {
        return pixelsToDp(context.getResources().getDisplayMetrics(), f);
    }

    public static double pixelsToDp(Context context, double d) {
        return pixelsToDp(context.getResources().getDisplayMetrics(), d);
    }

    public static float spToPixels(DisplayMetrics displayMetrics, float f) {
        return f * displayMetrics.scaledDensity;
    }

    public static double spToPixels(DisplayMetrics displayMetrics, double d) {
        return d * displayMetrics.scaledDensity;
    }

    public static int spToPixelsInt(DisplayMetrics displayMetrics, float f) {
        return Math.round(spToPixels(displayMetrics, f));
    }

    public static int spToPixelsInt(DisplayMetrics displayMetrics, double d) {
        return (int) Math.round(spToPixels(displayMetrics, d));
    }

    public static float spToPixels(Context context, float f) {
        return spToPixels(context.getResources().getDisplayMetrics(), f);
    }

    public static double spToPixels(Context context, double d) {
        return spToPixels(context.getResources().getDisplayMetrics(), d);
    }

    public static int spToPixelsInt(Context context, float f) {
        return spToPixelsInt(context.getResources().getDisplayMetrics(), f);
    }

    public static int spToPixelsInt(Context context, double d) {
        return spToPixelsInt(context.getResources().getDisplayMetrics(), d);
    }

    public static float pixelsToSp(DisplayMetrics displayMetrics, float f) {
        return f / displayMetrics.scaledDensity;
    }

    public static double pixelsToSp(DisplayMetrics displayMetrics, double d) {
        return d / displayMetrics.scaledDensity;
    }

    public static float pixelsToSp(Context context, float f) {
        return pixelsToSp(context.getResources().getDisplayMetrics(), f);
    }

    public static double pixelsToSp(Context context, double d) {
        return pixelsToSp(context.getResources().getDisplayMetrics(), d);
    }
}
