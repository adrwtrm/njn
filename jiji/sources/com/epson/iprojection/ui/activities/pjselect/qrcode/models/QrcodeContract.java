package com.epson.iprojection.ui.activities.pjselect.qrcode.models;

import android.app.Activity;
import android.content.Intent;
import kotlin.Metadata;

/* compiled from: QrcodeContract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/qrcode/models/QrcodeContract;", "", "IPresenter", "IView", "IViewModel", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface QrcodeContract {

    /* compiled from: QrcodeContract.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\u0007H&J\b\u0010\t\u001a\u00020\u0003H&J\b\u0010\n\u001a\u00020\u0003H&¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/qrcode/models/QrcodeContract$IPresenter;", "", "changeZoomLevel", "", "progressValue", "", "getPlayingState", "", "isAvailableZoom", "onPause", "onResume", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IPresenter {
        void changeZoomLevel(int i);

        boolean getPlayingState();

        boolean isAvailableZoom();

        void onPause();

        void onResume();
    }

    /* compiled from: QrcodeContract.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\nH&J\b\u0010\u0010\u001a\u00020\u0003H&¨\u0006\u0011"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/qrcode/models/QrcodeContract$IView;", "", "destroy", "", "getActivityForIntent", "Landroid/app/Activity;", "goNextActivity", "intent", "Landroid/content/Intent;", "requestCode", "", "onQREvent", "qrData", "", "showActivityFinishDialog", "stringId", "showInformationDialog", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IView {
        void destroy();

        Activity getActivityForIntent();

        void goNextActivity(Intent intent, int i);

        void onQREvent(byte[] bArr);

        void showActivityFinishDialog(int i);

        void showInformationDialog();
    }

    /* compiled from: QrcodeContract.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/qrcode/models/QrcodeContract$IViewModel;", "", "onPause", "", "onResume", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IViewModel {
        void onPause();

        void onResume();
    }
}
