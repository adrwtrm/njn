package com.epson.iprojection.service.mirroring;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.common.utils.NotificationUtils;
import com.epson.iprojection.ui.activities.mirroring.MirroringNotificationActivity;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringNotification.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0006\u0010\u0019\u001a\u00020\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001a"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringNotification;", "", "()V", "REQUEST_CODE_AVMUTE", "", "REQUEST_CODE_DISCONNECT", "REQUEST_CODE_PROJECTION_MYSELF", "<set-?>", "", "_isPaused", "get_isPaused", "()Z", "create", "Landroid/app/Notification$Builder;", "context", "Landroid/content/Context;", "isPaused", "delete", "", "getIntentAVMuteActon", "Landroid/app/Notification$Action;", "getIntentDefaultAction", "Landroid/app/PendingIntent;", "getIntentDisconnectAction", "getIntentProjectionMySelf", "initialize", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringNotification {
    public static final MirroringNotification INSTANCE = new MirroringNotification();
    private static final int REQUEST_CODE_AVMUTE = 0;
    private static final int REQUEST_CODE_DISCONNECT = 1;
    private static final int REQUEST_CODE_PROJECTION_MYSELF = 2;
    private static boolean _isPaused;

    private MirroringNotification() {
    }

    public final boolean get_isPaused() {
        return _isPaused;
    }

    public final void initialize() {
        _isPaused = false;
    }

    public final Notification.Builder create(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return create(context, _isPaused);
    }

    public final Notification.Builder create(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        _isPaused = z;
        Notification.Builder compatNotificationBuilder = MethodUtil.compatNotificationBuilder(context);
        Intrinsics.checkNotNullExpressionValue(compatNotificationBuilder, "compatNotificationBuilder(context)");
        compatNotificationBuilder.setColor(MethodUtil.compatGetColor(context, R.color.EpsonBlue)).setSmallIcon(R.drawable.statusbar_icon).setWhen(System.currentTimeMillis()).setShowWhen(false).setVisibility(0).setNumber(0).setOngoing(true).setContentIntent(getIntentDefaultAction(context)).setContentTitle(context.getString(R.string._NowProjecting_)).setColorized(true);
        if (Pj.getIns().isMppClient()) {
            compatNotificationBuilder.setStyle(new Notification.MediaStyle().setShowActionsInCompactView(0)).addAction(getIntentDisconnectAction(context));
        } else {
            compatNotificationBuilder.setStyle(new Notification.MediaStyle().setShowActionsInCompactView(0, 1, 2)).addAction(getIntentProjectionMySelf(context)).addAction(getIntentAVMuteActon(context, z)).addAction(getIntentDisconnectAction(context));
        }
        Object systemService = context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        notificationManager.createNotificationChannel(NotificationUtils.Companion.createChannel(context, 4));
        notificationManager.notify(1001, compatNotificationBuilder.build());
        return compatNotificationBuilder;
    }

    private final PendingIntent getIntentDefaultAction(Context context) {
        Intent intent = new Intent(context, MirroringNotificationActivity.class);
        intent.putExtra(CommonDefine.INTENT_EXTRA_MIRRORING, true);
        PendingIntent activity = PendingIntent.getActivity(context, CommonDefine.REQ_CODE_NOTIFICATION_MIRRORING, intent, 201326592);
        Intrinsics.checkNotNullExpressionValue(activity, "getActivity(\n           ….FLAG_IMMUTABLE\n        )");
        return activity;
    }

    private final Notification.Action getIntentDisconnectAction(Context context) {
        Intent intent = new Intent(context, MirroringService.class);
        intent.putExtra(CommonDefine.INTENT_EXTRA_DISCONNECT, CommonDefine.TRUE);
        Notification.Action build = new Notification.Action.Builder((int) R.drawable.notification_disconnect, context.getString(R.string._Disconnect_), PendingIntent.getService(context, 1, intent, 201326592)).build();
        Intrinsics.checkNotNullExpressionValue(build, "Builder(\n            R.d…ayPause\n        ).build()");
        return build;
    }

    private final Notification.Action getIntentAVMuteActon(Context context, boolean z) {
        int i;
        Intent intent = new Intent(context, MirroringService.class);
        if (z) {
            intent.putExtra(CommonDefine.INTENT_EXTRA_PAUSE, CommonDefine.FALSE);
            i = R.drawable.notification_play;
        } else {
            intent.putExtra(CommonDefine.INTENT_EXTRA_PAUSE, CommonDefine.TRUE);
            i = R.drawable.notification_pause;
        }
        Notification.Action build = new Notification.Action.Builder(i, context.getString(R.string._MuteOn_), PendingIntent.getService(context, 0, intent, 201326592)).build();
        Intrinsics.checkNotNullExpressionValue(build, "Builder(\n            ico…gIntent\n        ).build()");
        return build;
    }

    private final Notification.Action getIntentProjectionMySelf(Context context) {
        Intent intent = new Intent(context, MirroringService.class);
        intent.putExtra(CommonDefine.INTENT_EXTRA_MYSELF, CommonDefine.TRUE);
        Notification.Action build = new Notification.Action.Builder((int) R.drawable.projection, context.getString(R.string._ProjectNow_), PendingIntent.getService(context, 2, intent, 201326592)).build();
        Intrinsics.checkNotNullExpressionValue(build, "Builder(\n            R.d…gIntent\n        ).build()");
        return build;
    }

    public final void delete(Context context) {
        Intrinsics.checkNotNull(context);
        NotificationManagerCompat from = NotificationManagerCompat.from(context);
        Intrinsics.checkNotNullExpressionValue(from, "from(context!!)");
        from.cancel(1001);
    }
}
