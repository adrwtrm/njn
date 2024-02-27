package com.serenegiant.app;

import android.app.ActivityManager;
import android.content.Context;
import com.serenegiant.system.ContextUtils;
import java.util.List;

/* loaded from: classes2.dex */
public class ActivityUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "ActivityUtils";

    private ActivityUtils() {
    }

    public static int getAppId(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) ContextUtils.requireSystemService(context, ActivityManager.class)).getRunningTasks(Integer.MAX_VALUE);
        for (int i = 0; i < runningTasks.size(); i++) {
            if (runningTasks.get(i).baseActivity.getPackageName().equals(context.getPackageName())) {
                return runningTasks.get(i).id;
            }
        }
        return -1;
    }

    public static void moveTaskToFront(Context context) {
        int appId = getAppId(context);
        if (appId > 0) {
            ((ActivityManager) ContextUtils.requireSystemService(context, ActivityManager.class)).moveTaskToFront(appId, 1);
        }
    }
}
