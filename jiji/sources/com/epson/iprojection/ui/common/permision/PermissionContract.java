package com.epson.iprojection.ui.common.permision;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import kotlin.Metadata;

/* compiled from: PermissionContract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u0002\u0003¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/ui/common/permision/PermissionContract;", "", "Presenter", "View", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface PermissionContract {

    /* compiled from: PermissionContract.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\b\u0010\t\u001a\u00020\u0003H&J\u001b\u0010\n\u001a\u00020\u00032\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH&¢\u0006\u0002\u0010\u000eJ\u001b\u0010\u000f\u001a\u00020\u00032\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH&¢\u0006\u0002\u0010\u000eJ\b\u0010\u0010\u001a\u00020\u0003H&J\b\u0010\u0011\u001a\u00020\u0003H&J\b\u0010\u0012\u001a\u00020\u0003H&J\b\u0010\u0013\u001a\u00020\u0003H&J\b\u0010\u0014\u001a\u00020\u0003H&¨\u0006\u0015"}, d2 = {"Lcom/epson/iprojection/ui/common/permision/PermissionContract$Presenter;", "", "onActivityResult", "", "requestCode", "", "resultCode", "intent", "Landroid/content/Intent;", "onCanceled", "onDenied", "permissions", "", "", "([Ljava/lang/String;)V", "onDeniedWithNeverAsk", "onGranted", "onReturnedRequestPermission", "onStartedRequestPermission", "restart", "start", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface Presenter {
        void onActivityResult(int i, int i2, Intent intent);

        void onCanceled();

        void onDenied(String[] strArr);

        void onDeniedWithNeverAsk(String[] strArr);

        void onGranted();

        void onReturnedRequestPermission();

        void onStartedRequestPermission();

        void restart();

        void start();
    }

    /* compiled from: PermissionContract.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\n\u0010\b\u001a\u0004\u0018\u00010\tH&J\b\u0010\n\u001a\u00020\u000bH&J\u001a\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H&J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bH&J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u000bH&J\u0012\u0010\u0015\u001a\u00020\u00032\b\b\u0001\u0010\u0016\u001a\u00020\u0010H&¨\u0006\u0017"}, d2 = {"Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;", "", "destroy", "", "getActivityForIntent", "Landroid/app/Activity;", "getContext", "Landroid/content/Context;", "getIntentExtra", "Landroid/os/Bundle;", "getPackageNameForPresenter", "", "goNextActivity", "intent", "Landroid/content/Intent;", "requestCode", "", "hasPermission", "", "permission", "showRequestPermission", "showToast", "message", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface View {
        void destroy();

        Activity getActivityForIntent();

        Context getContext();

        Bundle getIntentExtra();

        String getPackageNameForPresenter();

        void goNextActivity(Intent intent, int i);

        boolean hasPermission(String str);

        void showRequestPermission(String str);

        void showToast(int i);

        /* compiled from: PermissionContract.kt */
        @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class DefaultImpls {
            public static /* synthetic */ void goNextActivity$default(View view, Intent intent, int i, int i2, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: goNextActivity");
                }
                if ((i2 & 2) != 0) {
                    i = 0;
                }
                view.goNextActivity(intent, i);
            }
        }
    }
}
