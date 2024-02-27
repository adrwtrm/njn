package com.epson.iprojection.ui.common.permision;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.ui.common.permision.PermissionContract;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PermissionPresenter.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0014\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0004J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J\u000e\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0014H&J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u0013\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018H&¢\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u0010H\u0002J\"\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010 \u001a\u00020\u0010H\u0016J\u001b\u0010!\u001a\u00020\u00102\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018H\u0016¢\u0006\u0002\u0010#J\u001b\u0010$\u001a\u00020\u00102\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018H\u0016¢\u0006\u0002\u0010#J\b\u0010%\u001a\u00020\u0010H\u0016J\b\u0010&\u001a\u00020\u0010H\u0016J\b\u0010'\u001a\u00020\u0010H\u0016J\b\u0010(\u001a\u00020\u0010H\u0016J\b\u0010)\u001a\u00020\u0006H\u0016J\b\u0010*\u001a\u00020\u0006H\u0002J\u0010\u0010+\u001a\u00020\u00102\u0006\u0010,\u001a\u00020\u0016H\u0002J\u001b\u0010-\u001a\u00020\u00102\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018H\u0002¢\u0006\u0002\u0010#J\u001b\u0010.\u001a\u00020\u00102\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018H\u0002¢\u0006\u0002\u0010#J\b\u0010/\u001a\u00020\u0010H\u0016J\b\u00100\u001a\u00020\u0010H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u00061"}, d2 = {"Lcom/epson/iprojection/ui/common/permision/PermissionPresenter;", "Lcom/epson/iprojection/ui/common/permision/PermissionContract$Presenter;", "mView", "Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;", "(Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;)V", "mIsCallingSettings", "", "mIsDestroyed", "mIsShowing", "getMIsShowing", "()Z", "setMIsShowing", "(Z)V", "getMView", "()Lcom/epson/iprojection/ui/common/permision/PermissionContract$View;", "callDestroy", "", "getIntentForNextActivity", "Landroid/content/Intent;", "getNextActivityClass", "Ljava/lang/Class;", "getPermissionShouldGet", "", "getPermissions", "", "()[Ljava/lang/String;", "goNextActivity", "onActivityResult", "requestCode", "", "resultCode", "intent", "onCanceled", "onDenied", "permissions", "([Ljava/lang/String;)V", "onDeniedWithNeverAsk", "onGranted", "onReturnedRequestPermission", "onStartedRequestPermission", "restart", "shouldStartSettingsActivityOnRefusedNeverAsk", "showPermissionDialog", "showPermissionDialogCore", "permission", "showToastOnRefused", "showToastOnRefusedNeverAsk", "start", "startSettingsActivity", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class PermissionPresenter implements PermissionContract.Presenter {
    private boolean mIsCallingSettings;
    private boolean mIsDestroyed;
    private boolean mIsShowing;
    private final PermissionContract.View mView;

    public static /* synthetic */ void $r8$lambda$79dQhlMT84Y6rWyaOoEIZTf76ZQ(PermissionPresenter permissionPresenter) {
        onCanceled$lambda$1(permissionPresenter);
    }

    public abstract Class<?> getNextActivityClass();

    public abstract String[] getPermissions();

    public boolean shouldStartSettingsActivityOnRefusedNeverAsk() {
        return true;
    }

    public PermissionPresenter(PermissionContract.View mView) {
        Intrinsics.checkNotNullParameter(mView, "mView");
        this.mView = mView;
    }

    public final PermissionContract.View getMView() {
        return this.mView;
    }

    protected final boolean getMIsShowing() {
        return this.mIsShowing;
    }

    public final void setMIsShowing(boolean z) {
        this.mIsShowing = z;
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void restart() {
        if (this.mIsCallingSettings) {
            return;
        }
        if (!this.mIsShowing && !SystemPermissionCallingStatus.INSTANCE.isCalling()) {
            callDestroy();
        }
        this.mIsShowing = false;
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void start() {
        if (this.mIsDestroyed || SystemPermissionCallingStatus.INSTANCE.isCalling()) {
            return;
        }
        if (this.mIsCallingSettings) {
            this.mIsCallingSettings = false;
            for (String str : getPermissions()) {
                if (!this.mView.hasPermission(str)) {
                    this.mView.destroy();
                    return;
                }
            }
        }
        if (showPermissionDialog() && !this.mIsCallingSettings) {
            goNextActivity();
        }
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1050) {
            SystemPermissionCallingStatus.INSTANCE.onReturned();
        }
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void onGranted() {
        this.mIsShowing = false;
        if (showPermissionDialog()) {
            goNextActivity();
        }
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void onDenied(String[] permissions) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        this.mIsShowing = false;
        showToastOnRefused(permissions);
        callDestroy();
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void onDeniedWithNeverAsk(String[] permissions) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        this.mIsShowing = false;
        if (shouldStartSettingsActivityOnRefusedNeverAsk()) {
            showToastOnRefusedNeverAsk(permissions);
            startSettingsActivity();
            return;
        }
        this.mView.destroy();
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void onStartedRequestPermission() {
        SystemPermissionCallingStatus.INSTANCE.onStarted();
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void onReturnedRequestPermission() {
        SystemPermissionCallingStatus.INSTANCE.onReturned();
    }

    @Override // com.epson.iprojection.ui.common.permision.PermissionContract.Presenter
    public void onCanceled() {
        new Handler().post(new Runnable() { // from class: com.epson.iprojection.ui.common.permision.PermissionPresenter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PermissionPresenter.$r8$lambda$79dQhlMT84Y6rWyaOoEIZTf76ZQ(PermissionPresenter.this);
            }
        });
    }

    public static final void onCanceled$lambda$1(PermissionPresenter this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mIsShowing = false;
        this$0.showPermissionDialog();
    }

    private final void goNextActivity() {
        Intent intentForNextActivity = getIntentForNextActivity();
        if (intentForNextActivity == null) {
            this.mView.destroy();
        } else {
            PermissionContract.View.DefaultImpls.goNextActivity$default(this.mView, intentForNextActivity, 0, 2, null);
        }
    }

    private final boolean showPermissionDialog() {
        String permissionShouldGet = getPermissionShouldGet();
        if (permissionShouldGet != null) {
            showPermissionDialogCore(permissionShouldGet);
            return false;
        }
        return true;
    }

    private final void startSettingsActivity() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", this.mView.getPackageNameForPresenter(), null));
        this.mIsCallingSettings = true;
        SystemPermissionCallingStatus.INSTANCE.onStarted();
        this.mView.goNextActivity(intent, CommonDefine.REQUEST_CODE_PERMISSION_SETTING);
    }

    private final String getPermissionShouldGet() {
        String[] permissions;
        for (String str : getPermissions()) {
            if (!this.mView.hasPermission(str)) {
                return str;
            }
        }
        return null;
    }

    private final void showPermissionDialogCore(String str) {
        this.mIsShowing = true;
        this.mView.showRequestPermission(str);
    }

    public final void callDestroy() {
        this.mIsDestroyed = true;
        this.mView.destroy();
    }

    private final Intent getIntentForNextActivity() {
        Class<?> nextActivityClass = getNextActivityClass();
        if (nextActivityClass == null) {
            return null;
        }
        Object clone = this.mView.getActivityForIntent().getIntent().clone();
        Intrinsics.checkNotNull(clone, "null cannot be cast to non-null type android.content.Intent");
        Intent intent = (Intent) clone;
        intent.setClass(this.mView.getActivityForIntent(), nextActivityClass);
        return intent;
    }

    private final void showToastOnRefused(String[] strArr) {
        for (String str : strArr) {
            Intrinsics.areEqual(str, "android.permission.CAMERA");
            if (Intrinsics.areEqual(str, "android.permission.ACCESS_FINE_LOCATION")) {
                this.mView.showToast(R.string._RequiredWiFiConnectionLocationPermission_);
            }
        }
    }

    private final void showToastOnRefusedNeverAsk(String[] strArr) {
        for (String str : strArr) {
            if (Intrinsics.areEqual(str, "android.permission.CAMERA")) {
                this.mView.showToast(R.string._AllowToCameraPermission_);
            }
            if (Intrinsics.areEqual(str, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                this.mView.showToast(R.string._AllowToStoragePermission_);
            }
            if (Intrinsics.areEqual(str, "android.permission.ACCESS_FINE_LOCATION")) {
                this.mView.showToast(R.string._AllowToWiFiConnectionLocationPermission_);
            }
        }
    }
}
