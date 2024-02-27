package com.serenegiant.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationChannelGroupCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.serenegiant.common.R;
import com.serenegiant.system.BuildCheck;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class NotificationFactoryCompat {
    private static final boolean DEBUG = false;
    private static final String TAG = "NotificationFactoryCompat";
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

    protected NotificationChannelCompat setupNotificationChannel(NotificationChannelCompat notificationChannelCompat) {
        return notificationChannelCompat;
    }

    protected NotificationChannelGroupCompat setupNotificationChannelGroup(NotificationChannelGroupCompat notificationChannelGroupCompat) {
        return notificationChannelGroupCompat;
    }

    public NotificationFactoryCompat(Context context, String str, String str2, int i) {
        this(context, str, str, 0, null, null, i, R.drawable.ic_notification);
    }

    public NotificationFactoryCompat(Context context, String str, String str2, int i, int i2) {
        this(context, str, str, 0, null, null, i, i2);
    }

    public NotificationFactoryCompat(Context context, String str, String str2, int i, String str3, String str4, int i2, int i3) throws IllegalArgumentException {
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
        createNotificationChannel(this.context);
        return createNotificationBuilder(this.context, charSequence, charSequence2).build();
    }

    protected void createNotificationChannel(Context context) {
        NotificationManagerCompat from = NotificationManagerCompat.from(context);
        if (from.getNotificationChannel(this.channelId) == null) {
            NotificationChannelCompat.Builder builder = new NotificationChannelCompat.Builder(this.channelId, this.importance);
            builder.setName(this.channelTitle);
            if (!TextUtils.isEmpty(this.groupId)) {
                createNotificationChannelGroup(context, this.groupId, this.groupName);
                builder.setGroup(this.groupId);
            }
            from.createNotificationChannel(setupNotificationChannel(builder.build()));
        }
    }

    protected void createNotificationChannelGroup(Context context, String str, String str2) {
        NotificationChannelGroupCompat notificationChannelGroupCompat;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        NotificationManagerCompat from = NotificationManagerCompat.from(context);
        Iterator<NotificationChannelGroupCompat> it = from.getNotificationChannelGroupsCompat().iterator();
        while (true) {
            if (!it.hasNext()) {
                notificationChannelGroupCompat = null;
                break;
            }
            notificationChannelGroupCompat = it.next();
            if (str.equals(notificationChannelGroupCompat.getId())) {
                break;
            }
        }
        if (notificationChannelGroupCompat == null) {
            NotificationChannelGroupCompat.Builder builder = new NotificationChannelGroupCompat.Builder(str);
            if (!TextUtils.isEmpty(str2)) {
                str = str2;
            }
            builder.setName(str);
            from.createNotificationChannelGroup(setupNotificationChannelGroup(builder.build()));
        }
    }

    protected NotificationCompat.Builder createNotificationBuilder(Context context, CharSequence charSequence, CharSequence charSequence2) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, this.channelId);
        builder.setContentTitle(charSequence).setContentText(charSequence2).setContentIntent(createContentIntent()).setDeleteIntent(createDeleteIntent()).setSmallIcon(this.smallIconId).setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle(charSequence).bigText(charSequence2).setSummaryText(charSequence2));
        if (!TextUtils.isEmpty(this.groupId)) {
            builder.setGroup(this.groupId);
        }
        if (this.largeIconId != 0) {
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), this.largeIconId));
        }
        return builder;
    }
}
