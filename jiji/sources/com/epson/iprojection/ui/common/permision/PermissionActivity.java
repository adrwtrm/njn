package com.epson.iprojection.ui.common.permision;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePermissionChangeDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.permision.PermissionContract;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PermissionActivity.kt */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u0000 12\u00020\u00012\u00020\u0002:\u00011B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\rH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0018\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u0016H\u0016J\"\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u001b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\u0012\u0010 \u001a\u00020\r2\b\u0010!\u001a\u0004\u0018\u00010\u0014H\u0014J+\u0010\"\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00160$2\u0006\u0010%\u001a\u00020&H\u0016¢\u0006\u0002\u0010'J\b\u0010(\u001a\u00020\rH\u0014J\b\u0010)\u001a\u00020\rH\u0014J#\u0010*\u001a\u00020\r2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00160$2\u0006\u0010+\u001a\u00020,H\u0002¢\u0006\u0002\u0010-J\u0010\u0010.\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u0016H\u0016J\u0012\u0010/\u001a\u00020\r2\b\b\u0001\u00100\u001a\u00020\u001bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u00062"}, d2 = {"Lcom/epson/iprojection/ui/common/permision/PermissionActivity;", "Lcom/epson/iprojection/ui/common/activity/base/PjConnectableActivity;", "Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;", "()V", "mIsAlreadyCalledOnRestart", "", "mPresenter", "Lcom/epson/iprojection/ui/common/permision/PermissionContract$Presenter;", "getMPresenter", "()Lcom/epson/iprojection/ui/common/permision/PermissionContract$Presenter;", "setMPresenter", "(Lcom/epson/iprojection/ui/common/permision/PermissionContract$Presenter;)V", "createPresenter", "", "destroy", "getActivityForIntent", "Landroid/app/Activity;", "getContext", "Landroid/content/Context;", "getIntentExtra", "Landroid/os/Bundle;", "getPackageNameForPresenter", "", "goNextActivity", "intent", "Landroid/content/Intent;", "requestCode", "", "hasPermission", "permission", "onActivityResult", "resultCode", "onCreate", "b", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onRestart", "onStart", "setGoogleAnalytics", NotificationCompat.CATEGORY_EVENT, "Lcom/epson/iprojection/ui/common/analytics/customdimension/enums/ePermissionChangeDimension;", "([Ljava/lang/String;Lcom/epson/iprojection/ui/common/analytics/customdimension/enums/ePermissionChangeDimension;)V", "showRequestPermission", "showToast", "message", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class PermissionActivity extends PjConnectableActivity implements PermissionContract.View {
    public static final Companion Companion = new Companion(null);
    public static final int REQEST_CODE = 100;
    private boolean mIsAlreadyCalledOnRestart;
    protected PermissionContract.Presenter mPresenter;

    public abstract void createPresenter();

    protected final PermissionContract.Presenter getMPresenter() {
        PermissionContract.Presenter presenter = this.mPresenter;
        if (presenter != null) {
            return presenter;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mPresenter");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setMPresenter(PermissionContract.Presenter presenter) {
        Intrinsics.checkNotNullParameter(presenter, "<set-?>");
        this.mPresenter = presenter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        createPresenter();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, android.app.Activity
    public void onRestart() {
        super.onRestart();
        if (isFinishing() || this.mIsAlreadyCalledOnRestart) {
            return;
        }
        getMPresenter().restart();
        this.mIsAlreadyCalledOnRestart = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        if (isFinishing()) {
            return;
        }
        getMPresenter().start();
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.View
    public Context getContext() {
        return this;
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.View
    public void destroy() {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        getMPresenter().onActivityResult(i, i2, intent);
        setResult(i2, intent);
        getMPresenter().restart();
        this.mIsAlreadyCalledOnRestart = true;
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.View
    public Bundle getIntentExtra() {
        return getIntent().getExtras();
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.View
    public Activity getActivityForIntent() {
        return this;
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.View
    public void showToast(int i) {
        Toast.makeText(this, getString(i), 1).show();
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.View
    public void goNextActivity(Intent intent, int i) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        startActivityForResult(intent, i);
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.View
    public void showRequestPermission(String permission) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        getMPresenter().onStartedRequestPermission();
        ActivityCompat.requestPermissions(this, new String[]{permission}, 100);
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.View
    public boolean hasPermission(String permission) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        return ContextCompat.checkSelfPermission(this, permission) == 0;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(i, permissions, grantResults);
        getMPresenter().onReturnedRequestPermission();
        if (grantResults.length == 0) {
            getMPresenter().onCanceled();
            return;
        }
        int i2 = grantResults[0];
        if (i2 != -1) {
            if (i2 != 0) {
                return;
            }
            getMPresenter().onGranted();
            setGoogleAnalytics(permissions, ePermissionChangeDimension.Allow);
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
            getMPresenter().onDenied(permissions);
        } else {
            getMPresenter().onDeniedWithNeverAsk(permissions);
        }
        setGoogleAnalytics(permissions, ePermissionChangeDimension.Deny);
    }

    private final void setGoogleAnalytics(String[] strArr, ePermissionChangeDimension epermissionchangedimension) {
        for (String str : strArr) {
            if (Intrinsics.areEqual(str, "android.permission.CAMERA")) {
                Analytics.getIns().setCameraPermissionEvent(epermissionchangedimension);
                Analytics.getIns().sendEvent(eCustomEvent.CAMERA_PERMISSION, (String) Objects.requireNonNull(getClass().getCanonicalName()));
            }
            if (Intrinsics.areEqual(str, "android.permission.ACCESS_FINE_LOCATION")) {
                Analytics.getIns().setLocationPermissionEvent(epermissionchangedimension);
                Analytics.getIns().sendEvent(eCustomEvent.LOCATION_PERMISSION, (String) Objects.requireNonNull(getClass().getCanonicalName()));
            }
        }
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.View
    public String getPackageNameForPresenter() {
        String packageName = getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
        return packageName;
    }

    /* compiled from: PermissionActivity.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/common/permision/PermissionActivity$Companion;", "", "()V", "REQEST_CODE", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
