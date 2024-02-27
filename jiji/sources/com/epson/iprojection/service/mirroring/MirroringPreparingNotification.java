package com.epson.iprojection.service.mirroring;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationManagerCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringPreparingNotification.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\t\u001a\u00020\n2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringPreparingNotification;", "", "()V", "create", "Landroid/app/Notification$Builder;", "context", "Landroid/content/Context;", "createChannel", "Landroid/app/NotificationChannel;", "delete", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringPreparingNotification {
    public static final MirroringPreparingNotification INSTANCE = new MirroringPreparingNotification();

    private MirroringPreparingNotification() {
    }

    public final Notification.Builder create(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Notification.Builder compatNotificationBuilder = MethodUtil.compatNotificationBuilder(context);
        Intrinsics.checkNotNullExpressionValue(compatNotificationBuilder, "compatNotificationBuilder(context)");
        compatNotificationBuilder.setColor(MethodUtil.compatGetColor(context, R.color.EpsonBlue)).setSmallIcon(R.drawable.statusbar_icon).setWhen(System.currentTimeMillis()).setShowWhen(false).setVisibility(0).setNumber(0).setOngoing(true).setContentTitle(context.getString(R.string._NowProjecting_)).setColorized(true);
        Object systemService = context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        NotificationChannel createChannel = createChannel(context);
        createChannel.setImportance(4);
        createChannel.setSound(null, null);
        notificationManager.createNotificationChannel(createChannel);
        notificationManager.notify(1001, compatNotificationBuilder.build());
        return compatNotificationBuilder;
    }

    public final void delete(Context context) {
        Intrinsics.checkNotNull(context);
        NotificationManagerCompat from = NotificationManagerCompat.from(context);
        Intrinsics.checkNotNullExpressionValue(from, "from(context!!)");
        from.cancel(1001);
    }

    private final NotificationChannel createChannel(Context context) {
        String packageName = context.getPackageName();
        return new NotificationChannel(packageName, packageName, 2);
    }
}
