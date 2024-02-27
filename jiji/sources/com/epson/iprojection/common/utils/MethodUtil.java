package com.epson.iprojection.common.utils;

import android.app.Notification;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.webkit.WebView;
import com.epson.iprojection.common.Lg;

/* loaded from: classes.dex */
public class MethodUtil {
    public static void compatStartScan(WifiManager wifiManager) {
    }

    public static void compatStartDragAndDrop(ClipData clipData, View.DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        dragShadowBuilder.getView().startDragAndDrop(ClipData.newPlainText("", ""), dragShadowBuilder, obj, i);
    }

    public static void compatVibrate(Vibrator vibrator, long j) {
        compatVibrate(vibrator, j, -1);
    }

    public static void compatVibrate(Vibrator vibrator, long j, int i) {
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(j, i));
            vibrator.vibrate(VibrationEffect.createOneShot(j, -1));
        }
    }

    public static int compatGetColor(Context context, int i) {
        return context.getColor(i);
    }

    public static Notification.Builder compatNotificationBuilder(Context context) {
        return new Notification.Builder(context, context.getPackageName());
    }

    public static Bitmap compatGetDrawingCache(WebView webView) {
        return webView.getDrawingCache();
    }

    public static void compatSetDrawingCacheEnabled(WebView webView, boolean z) {
        webView.setDrawingCacheEnabled(z);
    }

    public static int compatGetLocationMode(Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "location_mode");
        } catch (Settings.SettingNotFoundException e) {
            Lg.e("Location mode setting was not found. :" + e);
            return 0;
        }
    }
}
