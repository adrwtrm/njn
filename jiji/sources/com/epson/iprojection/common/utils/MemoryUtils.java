package com.epson.iprojection.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Process;
import com.epson.iprojection.common.Lg;

/* loaded from: classes.dex */
public final class MemoryUtils {
    public static void showExtendedHeap(Context context) {
    }

    public static void show(Context context) {
        Lg.d("** memory ** pid : " + Process.myPid());
        showNativeHeap(context);
        showJavaHeap(context);
    }

    public static void showNativeHeap(Context context) {
        String format = String.format("%1$,6d", Long.valueOf((Debug.getNativeHeapAllocatedSize() / 1024) - (Debug.getNativeHeapFreeSize() / 1024)));
        String format2 = String.format("%1$,6d", Long.valueOf(Debug.getNativeHeapAllocatedSize() / 1024));
        Lg.d("** memory ** nativeヒープ = " + format + "/" + format2 + " | size = " + String.format("%1$,6d", Long.valueOf(Debug.getNativeHeapSize() / 1024)) + "[KB]");
    }

    public static void showJavaHeap(Context context) {
        Runtime runtime = Runtime.getRuntime();
        String format = String.format("%1$,6d", Long.valueOf((runtime.totalMemory() - runtime.freeMemory()) / 1024));
        String format2 = String.format("%1$,6d", Long.valueOf(runtime.totalMemory() / 1024));
        Lg.d("** memory ** java  ヒープ = " + format + "/" + format2 + " | max  = " + String.format("%1$,6d", Long.valueOf(runtime.maxMemory() / 1024)) + "[KB]");
    }

    public static void showLinuxHeap(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        Lg.d("** memory ** linux ヒープ free = " + String.format("%1$,3d", Long.valueOf(memoryInfo.availMem / 1024)) + "[KB]");
    }

    public static void showProcessIndex(Context context) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            Lg.d("pid:" + String.format("%05d", Integer.valueOf(runningAppProcessInfo.pid)) + " name:[" + runningAppProcessInfo.processName + "]");
        }
    }

    private MemoryUtils() {
    }
}
