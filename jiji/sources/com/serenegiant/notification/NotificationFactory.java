package com.serenegiant.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.serenegiant.common.R;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.ContextUtils;
import java.util.Iterator;

@Deprecated
/* loaded from: classes2.dex */
public class NotificationFactory {
    private static final boolean DEBUG = false;
    private static final String TAG = "NotificationFactory";
    protected final String channelId;
    protected final String channelTitle;
    protected final Context context;
    protected final String groupId;
    protected final String groupName;
    protected final int importance;
    protected final int largeIconId;
    protected final int smallIconId;

    protected PendingIntent createContentIntent() {
        return null;
    }

    protected PendingIntent createDeleteIntent() {
        return null;
    }

    public boolean isForegroundService() {
        return true;
    }

    protected NotificationChannel setupNotificationChannel(NotificationChannel notificationChannel) {
        return notificationChannel;
    }

    protected NotificationChannelGroup setupNotificationChannelGroup(NotificationChannelGroup notificationChannelGroup) {
        return notificationChannelGroup;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NotificationFactory(Context context, String str, String str2, int i) {
        this(context, str, str, 0, null, null, i, R.drawable.ic_notification);
        BuildCheck.isAndroid7();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NotificationFactory(Context context, String str, String str2, int i, int i2) {
        this(context, str, str, 0, null, null, i, i2);
        BuildCheck.isAndroid7();
    }

    public NotificationFactory(Context context, String str, String str2, int i, String str3, String str4, int i2, int i3) throws IllegalArgumentException {
        Object create;
        this.context = context;
        this.channelId = str;
        this.channelTitle = TextUtils.isEmpty(str2) ? str : str2;
        this.importance = i;
        this.groupId = str3;
        this.groupName = TextUtils.isEmpty(str4) ? str3 : str4;
        this.smallIconId = i2;
        this.largeIconId = i3;
        if (BuildCheck.isAPI21()) {
            return;
        }
        try {
            create = ContextCompat.getDrawable(context, i2);
        } catch (Exception unused) {
            create = VectorDrawableCompat.create(context.getResources(), i2, null);
        }
        if (create instanceof VectorDrawableCompat) {
            throw new IllegalArgumentException("Can't use vector drawable as small icon before API21!");
        }
    }

    public Notification createNotification(CharSequence charSequence, CharSequence charSequence2) {
        if (BuildCheck.isOreo()) {
            createNotificationChannel(this.context);
        }
        return createNotificationBuilder(this.context, charSequence, charSequence2).build();
    }

    protected void createNotificationChannel(Context context) {
        NotificationManager notificationManager = (NotificationManager) ContextUtils.requireSystemService(context, NotificationManager.class);
        if (notificationManager.getNotificationChannel(this.channelId) == null) {
            NotificationChannel notificationChannel = new NotificationChannel(this.channelId, this.channelTitle, this.importance);
            if (!TextUtils.isEmpty(this.groupId)) {
                createNotificationChannelGroup(context, this.groupId, this.groupName);
                notificationChannel.setGroup(this.groupId);
            }
            notificationChannel.setLockscreenVisibility(0);
            notificationManager.createNotificationChannel(setupNotificationChannel(notificationChannel));
        }
    }

    protected void createNotificationChannelGroup(Context context, String str, String str2) {
        NotificationChannelGroup notificationChannelGroup;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) ContextUtils.requireSystemService(context, NotificationManager.class);
        Iterator<NotificationChannelGroup> it = notificationManager.getNotificationChannelGroups().iterator();
        while (true) {
            if (!it.hasNext()) {
                notificationChannelGroup = null;
                break;
            }
            notificationChannelGroup = it.next();
            if (str.equals(notificationChannelGroup.getId())) {
                break;
            }
        }
        if (notificationChannelGroup == null) {
            if (TextUtils.isEmpty(str2)) {
                str2 = str;
            }
            notificationManager.createNotificationChannelGroup(setupNotificationChannelGroup(new NotificationChannelGroup(str, str2)));
        }
    }

    protected NotificationBuilder createNotificationBuilder(Context context, CharSequence charSequence, CharSequence charSequence2) {
        NotificationBuilder notificationBuilder = new NotificationBuilder(context, this.channelId, this.smallIconId) { // from class: com.serenegiant.notification.NotificationFactory.1
            @Override // com.serenegiant.notification.NotificationBuilder
            protected PendingIntent createContentIntent() {
                return NotificationFactory.this.createContentIntent();
            }
        };
        notificationBuilder.setContentTitle(charSequence).setContentText(charSequence2).setSmallIcon(this.smallIconId).setStyle((NotificationCompat.Style) new NotificationCompat.BigTextStyle().setBigContentTitle(charSequence).bigText(charSequence2).setSummaryText(charSequence2));
        PendingIntent createContentIntent = createContentIntent();
        if (createContentIntent != null) {
            notificationBuilder.setContentIntent(createContentIntent);
        }
        PendingIntent createDeleteIntent = createDeleteIntent();
        if (createDeleteIntent != null) {
            notificationBuilder.setDeleteIntent(createDeleteIntent);
        }
        if (!TextUtils.isEmpty(this.groupId)) {
            notificationBuilder.setGroup(this.groupId);
        }
        if (this.largeIconId != 0) {
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), this.largeIconId));
        }
        return notificationBuilder;
    }
}
