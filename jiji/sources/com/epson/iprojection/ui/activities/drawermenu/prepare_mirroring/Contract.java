package com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring;

import android.content.Intent;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.state.State;
import kotlin.Metadata;

/* compiled from: Contract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract;", "", "ICallback", "IPermissionForMirroringCompletedCallback", "IView", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface Contract {

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$ICallback;", "", "changeState", "", "state", "Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/state/State;", "onFinished", "isStartingMirroringPossible", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface ICallback {
        void changeState(State state);

        void onFinished(boolean z);
    }

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$IPermissionForMirroringCompletedCallback;", "", "onActivityChanged", "", "onFinished", "isStartingMirroringPossible", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IPermissionForMirroringCompletedCallback {
        void onActivityChanged();

        void onFinished(boolean z);
    }

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\u0014\u0010\n\u001a\u00020\t2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH&J\b\u0010\r\u001a\u00020\u0007H&J\b\u0010\u000e\u001a\u00020\u0003H&J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\u0011\u001a\u00020\u0003H&J\b\u0010\u0012\u001a\u00020\u0003H&J\b\u0010\u0013\u001a\u00020\u0003H&J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0005H&J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\tH&¨\u0006\u0019"}, d2 = {"Lcom/epson/iprojection/ui/activities/drawermenu/prepare_mirroring/Contract$IView;", "", "canDrawOverlays", "", "checkSelfPermission", "", "permission", "", "createCaptureIntent", "Landroid/content/Intent;", "createIntent", "cls", "Ljava/lang/Class;", "getPackageName", "isCaptureIntentHolderEmpty", "requestPermission", "", "shouldGetAudioPermission", "shouldShowOverlayTutorial", "shouldShowOverlayTutorialForOpenGL", "shouldShowRequestPermissionRationale", "showToast", "strId", "startActivityForResult", "intent", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IView {
        boolean canDrawOverlays();

        int checkSelfPermission(String str);

        Intent createCaptureIntent();

        Intent createIntent(Class<?> cls);

        String getPackageName();

        boolean isCaptureIntentHolderEmpty();

        void requestPermission(String str);

        boolean shouldGetAudioPermission();

        boolean shouldShowOverlayTutorial();

        boolean shouldShowOverlayTutorialForOpenGL();

        boolean shouldShowRequestPermissionRationale(String str);

        void showToast(int i);

        void startActivityForResult(Intent intent);
    }
}
