package com.epson.iprojection.ui.common.uiparts;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.common.utils.NotificationUtils;
import com.epson.iprojection.ui.activities.restorewifi.Activity_RestoreWifi;

/* loaded from: classes.dex */
public class RestoreWifiNotification {
    private static final RestoreWifiNotification _inst = new RestoreWifiNotification();
    private Context _appContext = null;

    public void show(Context context) {
        hide();
        if (context == null) {
            Lg.e("Context is null.");
            return;
        }
        this._appContext = context;
        Notification.Builder autoCancel = MethodUtil.compatNotificationBuilder(this._appContext).setSmallIcon(R.drawable.statusbar_icon).setContentTitle(this._appContext.getString(R.string._ChangedWiFiSettings_)).setContentText(this._appContext.getString(R.string._ReturnPreviousWiFiSettingsManually_)).setContentIntent(PendingIntent.getActivity(this._appContext, CommonDefine.REQ_CODE_NOTIFICATION_RESTORE_WIFI, new Intent(this._appContext, Activity_RestoreWifi.class), 1073741824)).setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager == null) {
            Lg.e("NotificationManager is null !!");
            return;
        }
        notificationManager.createNotificationChannel(NotificationUtils.Companion.createChannel(context, 3));
        notificationManager.notify(1002, autoCancel.build());
    }

    public void hide() {
        Context context = this._appContext;
        if (context != null) {
            NotificationManagerCompat.from(context).cancel(1002);
            this._appContext = null;
        }
    }

    private RestoreWifiNotification() {
    }

    public static RestoreWifiNotification getIns() {
        return _inst;
    }
}
