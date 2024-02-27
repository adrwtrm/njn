package com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring;

import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.provider.Settings;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.OverlayUtils;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.PermissionForMirroringStateView;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state.NotificationPermissionState;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state.State;
import com.epson.iprojection.ui.activities.pjselect.permission.audio.PermissionAudioPresenter;
import com.epson.iprojection.ui.common.singleton.mirroring.CaptureIntentHolder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PermissionForMirroringStateView.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\r\u000eB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00060\bR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/PermissionForMirroringStateView;", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/IPermissionForMirroringStateView;", "_activity", "Landroidx/appcompat/app/AppCompatActivity;", "_impl", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$IPermissionForMirroringCompletedCallback;", "(Landroidx/appcompat/app/AppCompatActivity;Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$IPermissionForMirroringCompletedCallback;)V", "_implView", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/PermissionForMirroringStateView$ImplView;", "_state", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/State;", "start", "", "ImplCallback", "ImplView", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PermissionForMirroringStateView implements IPermissionForMirroringStateView {
    private final AppCompatActivity _activity;
    private final Contract.IPermissionForMirroringCompletedCallback _impl;
    private final ImplView _implView;
    private State _state;

    public PermissionForMirroringStateView(AppCompatActivity _activity, Contract.IPermissionForMirroringCompletedCallback _impl) {
        Intrinsics.checkNotNullParameter(_activity, "_activity");
        Intrinsics.checkNotNullParameter(_impl, "_impl");
        this._activity = _activity;
        this._impl = _impl;
        this._implView = new ImplView();
    }

    @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.IPermissionForMirroringStateView
    public void start() {
        NotificationPermissionState notificationPermissionState = new NotificationPermissionState(new ContextData(new ImplCallback(), this._implView));
        this._state = notificationPermissionState;
        notificationPermissionState.run();
    }

    /* compiled from: PermissionForMirroringStateView.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/PermissionForMirroringStateView$ImplCallback;", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$ICallback;", "(Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/PermissionForMirroringStateView;)V", "changeState", "", "state", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/State;", "onFinished", "isStartingMirroringPossible", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    private final class ImplCallback implements Contract.ICallback {
        public ImplCallback() {
            PermissionForMirroringStateView.this = r1;
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.ICallback
        public void changeState(State state) {
            Intrinsics.checkNotNullParameter(state, "state");
            StringBuilder sb = new StringBuilder("[preMirroring] changeState ");
            State state2 = PermissionForMirroringStateView.this._state;
            State state3 = null;
            if (state2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_state");
                state2 = null;
            }
            Lg.d(sb.append(state2).append(" -> ").append(state).toString());
            PermissionForMirroringStateView.this._state = state;
            State state4 = PermissionForMirroringStateView.this._state;
            if (state4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_state");
            } else {
                state3 = state4;
            }
            state3.run();
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.ICallback
        public void onFinished(boolean z) {
            Lg.d("[preMirroring] onFinished : " + z);
            PermissionForMirroringStateView.this._impl.onFinished(z);
        }
    }

    /* compiled from: PermissionForMirroringStateView.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H\u0016J\b\u0010\u000e\u001a\u00020\bH\u0016J\u0014\u0010\u000f\u001a\u00020\b2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0005H\u0016J\b\u0010\u0013\u001a\u00020\nH\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\u0005H\u0016J\b\u0010\u0016\u001a\u00020\nH\u0016J\b\u0010\u0017\u001a\u00020\nH\u0016J\b\u0010\u0018\u001a\u00020\nH\u0016J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u0005H\u0016J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\bH\u0016R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\b0\b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/PermissionForMirroringStateView$ImplView;", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$IView;", "(Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/PermissionForMirroringStateView;)V", "_launcherRequestPermission", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "_launcherStartActivity", "Landroid/content/Intent;", "canDrawOverlays", "", "checkSelfPermission", "", "permission", "createCaptureIntent", "createIntent", "cls", "Ljava/lang/Class;", "getPackageName", "isCaptureIntentHolderEmpty", "requestPermission", "", "shouldGetAudioPermission", "shouldShowOverlayTutorial", "shouldShowOverlayTutorialForOpenGL", "shouldShowRequestPermissionRationale", "showToast", "stringId", "startActivityForResult", "intent", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class ImplView implements Contract.IView {
        private final ActivityResultLauncher<String> _launcherRequestPermission;
        private final ActivityResultLauncher<Intent> _launcherStartActivity;

        public ImplView() {
            PermissionForMirroringStateView.this = r4;
            ActivityResultLauncher<Intent> registerForActivityResult = r4._activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.PermissionForMirroringStateView$ImplView$$ExternalSyntheticLambda0
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(Object obj) {
                    PermissionForMirroringStateView.ImplView._launcherStartActivity$lambda$0(r1, (ActivityResult) obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(registerForActivityResult, "_activity.registerForAct…yResult(result)\n        }");
            this._launcherStartActivity = registerForActivityResult;
            ActivityResultLauncher<String> registerForActivityResult2 = r4._activity.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback() { // from class: com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.PermissionForMirroringStateView$ImplView$$ExternalSyntheticLambda1
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(Object obj) {
                    PermissionForMirroringStateView.ImplView._launcherRequestPermission$lambda$1(r1, (Boolean) obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(registerForActivityResult2, "_activity.registerForAct…sult(isGranted)\n        }");
            this._launcherRequestPermission = registerForActivityResult2;
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public void startActivityForResult(Intent intent) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            PermissionForMirroringStateView.this._impl.onActivityChanged();
            this._launcherStartActivity.launch(intent);
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public void requestPermission(String permission) {
            Intrinsics.checkNotNullParameter(permission, "permission");
            PermissionForMirroringStateView.this._impl.onActivityChanged();
            this._launcherRequestPermission.launch(permission);
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public Intent createIntent(Class<?> cls) {
            Intrinsics.checkNotNullParameter(cls, "cls");
            return new Intent(PermissionForMirroringStateView.this._activity, cls);
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public Intent createCaptureIntent() {
            Object systemService = PermissionForMirroringStateView.this._activity.getSystemService("media_projection");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
            Intent createScreenCaptureIntent = ((MediaProjectionManager) systemService).createScreenCaptureIntent();
            Intrinsics.checkNotNullExpressionValue(createScreenCaptureIntent, "mpm.createScreenCaptureIntent()");
            return createScreenCaptureIntent;
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public void showToast(int i) {
            Toast.makeText(PermissionForMirroringStateView.this._activity, PermissionForMirroringStateView.this._activity.getString(i), 1).show();
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public boolean shouldShowRequestPermissionRationale(String permission) {
            Intrinsics.checkNotNullParameter(permission, "permission");
            return ActivityCompat.shouldShowRequestPermissionRationale(PermissionForMirroringStateView.this._activity, permission);
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public int checkSelfPermission(String permission) {
            Intrinsics.checkNotNullParameter(permission, "permission");
            return ContextCompat.checkSelfPermission(PermissionForMirroringStateView.this._activity, permission);
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public boolean shouldGetAudioPermission() {
            return PermissionAudioPresenter.Companion.shouldGetPermission(PermissionForMirroringStateView.this._activity);
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public boolean shouldShowOverlayTutorial() {
            return OverlayUtils.Companion.shouldShowOverlayTutorial(PermissionForMirroringStateView.this._activity);
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public boolean shouldShowOverlayTutorialForOpenGL() {
            return OverlayUtils.Companion.shouldShowOverlayTutorialForOpenGL(PermissionForMirroringStateView.this._activity);
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public String getPackageName() {
            String packageName = PermissionForMirroringStateView.this._activity.getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "_activity.packageName");
            return packageName;
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public boolean canDrawOverlays() {
            return Settings.canDrawOverlays(PermissionForMirroringStateView.this._activity);
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IView
        public boolean isCaptureIntentHolderEmpty() {
            return CaptureIntentHolder.INSTANCE.getCaptureIntent() == null;
        }

        public static final void _launcherStartActivity$lambda$0(PermissionForMirroringStateView this$0, ActivityResult result) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            State state = this$0._state;
            if (state == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_state");
                state = null;
            }
            Intrinsics.checkNotNullExpressionValue(result, "result");
            state.onActivityResult(result);
        }

        public static final void _launcherRequestPermission$lambda$1(PermissionForMirroringStateView this$0, Boolean isGranted) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            State state = this$0._state;
            if (state == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_state");
                state = null;
            }
            Intrinsics.checkNotNullExpressionValue(isGranted, "isGranted");
            state.onRequestPermissionResult(isGranted.booleanValue());
        }
    }
}
