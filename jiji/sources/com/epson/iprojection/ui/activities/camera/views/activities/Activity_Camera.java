package com.epson.iprojection.ui.activities.camera.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.PermissionUtilsKt;
import com.epson.iprojection.databinding.MainCameraBinding;
import com.epson.iprojection.ui.activities.camera.models.CameraContract;
import com.epson.iprojection.ui.activities.camera.viewmodel.CameraActionBar;
import com.epson.iprojection.ui.activities.camera.viewmodel.CameraViewModel;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.marker.ImageSaver;
import com.epson.iprojection.ui.activities.marker.permission.PermissionStrageActivity;
import com.epson.iprojection.ui.activities.moderator.ModeratorMenuMgr;
import com.epson.iprojection.ui.common.actionbar.CustomActionBar;
import com.epson.iprojection.ui.common.activity.ProjectableActivity;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eFirstTimeProjectionDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class Activity_Camera extends ProjectableActivity implements View.OnClickListener, ImageSaver.ISaveFinishCallback, CameraContract.IView {
    static final String CAMERA_FACING = "saved_camera_facing";
    static final String CAMERA_PLAY = "saved_camera_playing";
    static final String ORIENTATIONNUM = "saved_orientation_num";
    private CameraContract.IViewModel _contractViewModel;
    private ModeratorMenuMgr _menuMgr = null;
    private final ActivityResultLauncher<Intent> permissionStrageActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.epson.iprojection.ui.activities.camera.views.activities.Activity_Camera$$ExternalSyntheticLambda0
        {
            Activity_Camera.this = this;
        }

        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            Activity_Camera.this.m82x8c14331a((ActivityResult) obj);
        }
    });

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IView
    public Activity getActivityForIntent() {
        return this;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        boolean z;
        int i;
        String str;
        Window window = getWindow();
        window.setAttributes(window.getAttributes());
        setContentView(R.layout.main_camera);
        if (bundle != null) {
            i = bundle.getInt(ORIENTATIONNUM);
            str = bundle.getString(CAMERA_FACING);
            z = bundle.getBoolean(CAMERA_PLAY);
        } else {
            z = false;
            i = -1;
            str = null;
        }
        MainCameraBinding mainCameraBinding = (MainCameraBinding) DataBindingUtil.setContentView(this, R.layout.main_camera);
        CameraViewModel cameraViewModel = new CameraViewModel(this, mainCameraBinding, i, str, z);
        mainCameraBinding.setViewmodel(cameraViewModel);
        this._contractViewModel = cameraViewModel;
        setContentsSelectStatus(eContentsType.Camera);
        pushDrawerStatus(eDrawerMenuItem.Camera);
        this._baseActionBar = new CameraActionBar(this);
        super.onCreate(bundle);
        this._drawerList.enableDrawerToggleButton(mainCameraBinding.toolbarCamera.toolbarCamera);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        this._menuMgr = new ModeratorMenuMgr(this);
        super.updatePjButtonState();
        Analytics.getIns().setFirstTimeProjectionEvent(eFirstTimeProjectionDimension.camera);
        if (Pj.getIns().isConnected()) {
            Analytics.getIns().sendEvent(eCustomEvent.FIRST_TIME_PROJECTION);
        }
        Analytics.getIns().startContents(eCustomEvent.CAMERA_DISPLAY_START);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.ProjectableActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this._contractViewModel.onResume();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this._contractViewModel.onPause();
        super.onPause();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Analytics.getIns().endContents(eCustomEvent.CAMERA_DISPLAY_END);
        this._contractViewModel.onDestroy();
        popDrawerStatus();
        clearContentsSelectStatus();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IView
    public void destroy() {
        finish();
    }

    @Override // com.epson.iprojection.ui.common.activity.ProjectableActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity
    public void updateActionBar() {
        super.updateActionBar();
        this._contractViewModel.updateToolBar();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IView
    public void onClickNavigationDrawer(View view) {
        ((CustomActionBar) this._baseActionBar).onClick(view);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        ModeratorMenuMgr moderatorMenuMgr = this._menuMgr;
        if (moderatorMenuMgr != null) {
            moderatorMenuMgr.onCreateOptionsMenu(this, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        ModeratorMenuMgr moderatorMenuMgr = this._menuMgr;
        boolean onPrepareOptionsMenu = moderatorMenuMgr != null ? moderatorMenuMgr.onPrepareOptionsMenu(menu) : false;
        super.onPrepareOptionsMenu(menu);
        return onPrepareOptionsMenu;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        this._menuMgr.onOptionsItemSelected(menuItem);
        super.onOptionsItemSelected(menuItem);
        return true;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(ORIENTATIONNUM, this._contractViewModel.getLastOrientationNum());
        bundle.putString(CAMERA_FACING, this._contractViewModel.getLastCameraId());
        bundle.putBoolean(CAMERA_PLAY, this._contractViewModel.getPlayingState());
    }

    @Override // com.epson.iprojection.ui.activities.marker.ImageSaver.ISaveFinishCallback
    public void callbackSaveSucceed() {
        this._contractViewModel.setSaveButtonEnable(true, 255);
    }

    @Override // com.epson.iprojection.ui.activities.marker.ImageSaver.ISaveFinishCallback
    public void callbackSaveFail() {
        this._contractViewModel.setSaveButtonEnable(true, 255);
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IView
    public void showActivityFinishDialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(i));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.camera.views.activities.Activity_Camera$$ExternalSyntheticLambda1
            {
                Activity_Camera.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                Activity_Camera.this.m83xd4c5c8da(dialogInterface, i2);
            }
        });
        builder.create().show();
    }

    /* renamed from: lambda$showActivityFinishDialog$0$com-epson-iprojection-ui-activities-camera-views-activities-Activity_Camera */
    public /* synthetic */ void m83xd4c5c8da(DialogInterface dialogInterface, int i) {
        finish();
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IView
    public Drawable getDrawableById(int i) {
        return getDrawable(i);
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IView
    public void goNextActivity(Intent intent, int i) {
        startActivityForResult(intent, i);
    }

    @Override // com.epson.iprojection.ui.activities.camera.models.CameraContract.IView
    public void goStragePermissionActivity() {
        this.permissionStrageActivityLauncher.launch(new Intent(this, PermissionStrageActivity.class));
    }

    /* renamed from: lambda$new$1$com-epson-iprojection-ui-activities-camera-views-activities-Activity_Camera */
    public /* synthetic */ void m82x8c14331a(ActivityResult activityResult) {
        if (PermissionUtilsKt.isUsablePermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            this._contractViewModel.save();
            return;
        }
        this._contractViewModel.showSaveFailed();
        callbackSaveFail();
    }
}
