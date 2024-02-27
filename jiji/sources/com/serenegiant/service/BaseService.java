package com.serenegiant.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.LifecycleService;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.serenegiant.common.R;
import com.serenegiant.notification.NotificationFactoryCompat;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.ContextUtils;
import com.serenegiant.utils.HandlerThreadHandler;
import com.serenegiant.utils.HandlerUtils;

/* loaded from: classes2.dex */
public abstract class BaseService extends LifecycleService {
    private static final boolean DEBUG = false;
    private static final int NOTIFICATION_ID = R.string.service_name;
    private static final String TAG = "BaseService";
    private Handler mAsyncHandler;
    private volatile boolean mDestroyed;
    private LocalBroadcastManager mLocalBroadcastManager;
    protected final Object mSync = new Object();
    private final Handler mUIHandler = new Handler(Looper.getMainLooper());
    private final BroadcastReceiver mLocalBroadcastReceiver = new BroadcastReceiver() { // from class: com.serenegiant.service.BaseService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                BaseService.this.onReceiveLocalBroadcast(context, intent);
            } catch (Exception e) {
                Log.w(BaseService.TAG, e);
            }
        }
    };

    protected abstract PendingIntent contextIntent();

    protected abstract IntentFilter createIntentFilter();

    protected abstract void onReceiveLocalBroadcast(Context context, Intent intent);

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public void onCreate() {
        super.onCreate();
        getApplicationContext();
        synchronized (this.mSync) {
            this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
            IntentFilter createIntentFilter = createIntentFilter();
            if (createIntentFilter != null && createIntentFilter.countActions() > 0) {
                this.mLocalBroadcastManager.registerReceiver(this.mLocalBroadcastReceiver, createIntentFilter);
            }
            if (this.mAsyncHandler == null) {
                this.mAsyncHandler = HandlerThreadHandler.createHandler(getClass().getSimpleName());
            }
        }
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public void onDestroy() {
        this.mDestroyed = true;
        synchronized (this.mSync) {
            this.mUIHandler.removeCallbacksAndMessages(null);
            Handler handler = this.mAsyncHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                HandlerUtils.NoThrowQuit(this.mAsyncHandler);
                this.mAsyncHandler = null;
            }
            LocalBroadcastManager localBroadcastManager = this.mLocalBroadcastManager;
            if (localBroadcastManager != null) {
                try {
                    localBroadcastManager.unregisterReceiver(this.mLocalBroadcastReceiver);
                } catch (Exception unused) {
                }
                this.mLocalBroadcastManager = null;
            }
        }
        super.onDestroy();
    }

    protected boolean isDestroyed() {
        return this.mDestroyed;
    }

    protected void sendLocalBroadcast(Intent intent) {
        synchronized (this.mSync) {
            LocalBroadcastManager localBroadcastManager = this.mLocalBroadcastManager;
            if (localBroadcastManager != null) {
                localBroadcastManager.sendBroadcast(intent);
            }
        }
    }

    protected void showNotification(int i, CharSequence charSequence, CharSequence charSequence2, PendingIntent pendingIntent) {
        showNotification(NOTIFICATION_ID, getString(R.string.service_name), null, null, i, R.drawable.ic_notification, charSequence, charSequence2, true, pendingIntent);
    }

    protected void showNotification(int i, CharSequence charSequence, CharSequence charSequence2, boolean z, PendingIntent pendingIntent) {
        showNotification(NOTIFICATION_ID, getString(R.string.service_name), null, null, i, R.drawable.ic_notification, charSequence, charSequence2, z, pendingIntent);
    }

    protected void showNotification(int i, String str, int i2, int i3, CharSequence charSequence, CharSequence charSequence2, PendingIntent pendingIntent) {
        showNotification(i, str, null, null, i2, i3, charSequence, charSequence2, true, pendingIntent);
    }

    protected void showNotification(int i, String str, String str2, String str3, int i2, int i3, CharSequence charSequence, CharSequence charSequence2, PendingIntent pendingIntent) {
        showNotification(i, str, str2, str3, i2, i3, charSequence, charSequence2, true, pendingIntent);
    }

    protected void showNotification(int i, String str, String str2, String str3, int i2, int i3, CharSequence charSequence, CharSequence charSequence2, final boolean z, final PendingIntent pendingIntent) {
        showNotification(i, charSequence, charSequence2, new NotificationFactoryCompat(this, str, str, 0, str2, str3, i2, i3) { // from class: com.serenegiant.service.BaseService.2
            @Override // com.serenegiant.notification.NotificationFactoryCompat
            public boolean isForegroundService() {
                return z;
            }

            @Override // com.serenegiant.notification.NotificationFactoryCompat
            protected PendingIntent createContentIntent() {
                return pendingIntent;
            }
        });
    }

    protected void showNotification(int i, CharSequence charSequence, CharSequence charSequence2, NotificationFactoryCompat notificationFactoryCompat) {
        NotificationManager notificationManager = (NotificationManager) ContextUtils.requireSystemService(this, NotificationManager.class);
        Notification createNotification = notificationFactoryCompat.createNotification(charSequence2, charSequence);
        if (notificationFactoryCompat.isForegroundService()) {
            startForeground(i, createNotification);
        }
        notificationManager.notify(i, createNotification);
    }

    protected void releaseNotification() {
        releaseNotification(NOTIFICATION_ID, getString(R.string.service_name), R.drawable.ic_notification, R.drawable.ic_notification, getString(R.string.service_name), getString(R.string.service_stop));
    }

    protected void releaseNotification(int i, String str, int i2, int i3, CharSequence charSequence, CharSequence charSequence2) {
        showNotification(i, str, i2, i3, charSequence, charSequence2, null);
        releaseNotification(i, str);
    }

    protected void releaseNotification(int i, String str) {
        stopForeground(true);
        cancelNotification(i, str);
    }

    protected void cancelNotification(int i, String str) {
        ((NotificationManager) ContextUtils.requireSystemService(this, NotificationManager.class)).cancel(i);
        releaseNotificationChannel(str);
    }

    protected void cancelNotification(int i) {
        cancelNotification(i, null);
    }

    protected void releaseNotificationChannel(String str) {
        if (TextUtils.isEmpty(str) || !BuildCheck.isOreo()) {
            return;
        }
        try {
            ((NotificationManager) ContextUtils.requireSystemService(this, NotificationManager.class)).deleteNotificationChannel(str);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    protected void releaseNotificationGroup(String str) {
        if (TextUtils.isEmpty(str) || !BuildCheck.isOreo()) {
            return;
        }
        try {
            ((NotificationManager) ContextUtils.requireSystemService(this, NotificationManager.class)).deleteNotificationChannelGroup(str);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    protected void runOnUiThread(Runnable runnable) throws IllegalStateException {
        if (runnable == null) {
            return;
        }
        if (this.mDestroyed) {
            throw new IllegalStateException("already destroyed");
        }
        this.mUIHandler.removeCallbacks(runnable);
        this.mUIHandler.post(runnable);
    }

    protected void runOnUiThread(Runnable runnable, long j) throws IllegalStateException {
        if (runnable == null) {
            return;
        }
        if (this.mDestroyed) {
            throw new IllegalStateException("already destroyed");
        }
        this.mUIHandler.removeCallbacks(runnable);
        if (j > 0) {
            this.mUIHandler.postDelayed(runnable, j);
        } else {
            this.mUIHandler.post(runnable);
        }
    }

    protected void removeFromUiThread(Runnable runnable) {
        this.mUIHandler.removeCallbacks(runnable);
    }

    protected void removeFromUiThreadAll(Object obj) {
        this.mUIHandler.removeCallbacksAndMessages(obj);
    }

    protected void queueEvent(Runnable runnable) throws IllegalStateException {
        if (runnable == null) {
            return;
        }
        if (this.mDestroyed) {
            throw new IllegalStateException("already destroyed");
        }
        queueEvent(runnable, 0L);
    }

    protected void queueEvent(Runnable runnable, long j) throws IllegalStateException {
        if (runnable == null) {
            return;
        }
        if (this.mDestroyed) {
            throw new IllegalStateException("already destroyed");
        }
        synchronized (this.mSync) {
            Handler handler = this.mAsyncHandler;
            if (handler != null) {
                handler.removeCallbacks(runnable);
                if (j > 0) {
                    this.mAsyncHandler.postDelayed(runnable, j);
                } else {
                    this.mAsyncHandler.post(runnable);
                }
            } else {
                throw new IllegalStateException("worker thread is not ready");
            }
        }
    }

    protected void removeEvent(Runnable runnable) {
        synchronized (this.mSync) {
            Handler handler = this.mAsyncHandler;
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
        }
    }

    protected void removeEventAll(Object obj) {
        synchronized (this.mSync) {
            Handler handler = this.mAsyncHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(obj);
            }
        }
    }

    protected Handler getAsyncHandler() throws IllegalStateException {
        Handler handler;
        if (this.mDestroyed) {
            throw new IllegalStateException("already destroyed");
        }
        synchronized (this.mSync) {
            handler = this.mAsyncHandler;
        }
        return handler;
    }
}
