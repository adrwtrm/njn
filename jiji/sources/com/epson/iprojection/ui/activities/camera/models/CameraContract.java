package com.epson.iprojection.ui.activities.camera.models;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import kotlin.Metadata;

/* compiled from: CameraContract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/camera/models/CameraContract;", "", "IPresenter", "IView", "IViewModel", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface CameraContract {

    /* compiled from: CameraContract.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\u0005H&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\nH&J\b\u0010\f\u001a\u00020\u0005H&J\b\u0010\r\u001a\u00020\nH&J\b\u0010\u000e\u001a\u00020\nH&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u0003H&J\b\u0010\u0013\u001a\u00020\u0003H&J\b\u0010\u0014\u001a\u00020\u0003H&J\b\u0010\u0015\u001a\u00020\u0003H&J\b\u0010\u0016\u001a\u00020\u0003H&¨\u0006\u0017"}, d2 = {"Lcom/epson/iprojection/ui/activities/camera/models/CameraContract$IPresenter;", "", "changeZoomLevel", "", "progressValue", "", "getLastCameraId", "", "getLastOrientationNum", "getPlayingState", "", "hasFlash", "hasSomeCameras", "isAvailableZoom", "isNowCameraFacingBack", "onClickEvent", "view", "Landroid/view/View;", "onDestroy", "onPause", "onResume", "save", "showSaveFailed", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IPresenter {
        void changeZoomLevel(int i);

        String getLastCameraId();

        int getLastOrientationNum();

        boolean getPlayingState();

        boolean hasFlash();

        int hasSomeCameras();

        boolean isAvailableZoom();

        boolean isNowCameraFacingBack();

        void onClickEvent(View view);

        void onDestroy();

        void onPause();

        void onResume();

        void save();

        void showSaveFailed();
    }

    /* compiled from: CameraContract.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH&J\b\u0010\u000e\u001a\u00020\u0003H&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\tH&¨\u0006\u0015"}, d2 = {"Lcom/epson/iprojection/ui/activities/camera/models/CameraContract$IView;", "", "destroy", "", "getActivityForIntent", "Landroid/app/Activity;", "getDrawableById", "Landroid/graphics/drawable/Drawable;", "drawableId", "", "goNextActivity", "intent", "Landroid/content/Intent;", "requestCode", "goStragePermissionActivity", "onClick", "view", "Landroid/view/View;", "onClickNavigationDrawer", "showActivityFinishDialog", "stringId", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IView {
        void destroy();

        Activity getActivityForIntent();

        Drawable getDrawableById(int i);

        void goNextActivity(Intent intent, int i);

        void goStragePermissionActivity();

        void onClick(View view);

        void onClickNavigationDrawer(View view);

        void showActivityFinishDialog(int i);
    }

    /* compiled from: CameraContract.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\tH&J\b\u0010\u000b\u001a\u00020\tH&J\b\u0010\f\u001a\u00020\tH&J\u0018\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0005H&J\b\u0010\u0010\u001a\u00020\tH&J\b\u0010\u0011\u001a\u00020\tH&¨\u0006\u0012"}, d2 = {"Lcom/epson/iprojection/ui/activities/camera/models/CameraContract$IViewModel;", "", "getLastCameraId", "", "getLastOrientationNum", "", "getPlayingState", "", "onDestroy", "", "onPause", "onResume", "save", "setSaveButtonEnable", "enabled", "value", "showSaveFailed", "updateToolBar", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IViewModel {
        String getLastCameraId();

        int getLastOrientationNum();

        boolean getPlayingState();

        void onDestroy();

        void onPause();

        void onResume();

        void save();

        void setSaveButtonEnable(boolean z, int i);

        void showSaveFailed();

        void updateToolBar();
    }
}
