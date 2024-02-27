package com.epson.iprojection.ui.activities.marker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.DisplayInfoUtils;
import com.epson.iprojection.common.utils.PermissionUtilsKt;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import com.epson.iprojection.ui.activities.marker.ImageSaver;
import com.epson.iprojection.ui.activities.marker.config.BaseDialogToolConfig;
import com.epson.iprojection.ui.activities.marker.config.DialogToolConfigFactory;
import com.epson.iprojection.ui.activities.marker.config.IUpdateConfigListener;
import com.epson.iprojection.ui.activities.marker.permission.PermissionStrageActivity;
import com.epson.iprojection.ui.activities.support.intro.moderator.CustomOkCancelDialog;
import com.epson.iprojection.ui.common.actionbar.CustomActionBar;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activity.ProjectableActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class Activity_Marker extends ProjectableActivity implements IPaintButtonClickListener, IPaintMenuButtonClickListener, IOnSendEvent, ImageSaver.ISaveFinishCallback, IUpdateConfigListener, IGettableUpdateUndoRedoButton {
    public static final String IntentMsg_ActivityRotation = "IntentMsg_ActivityRotation";
    public static final String IntentMsg_CameraViewMode = "IntentMsg_CameraViewMode";
    public static final String IntentMsg_FullScreenMode = "IntentMsg_FullScreenMode";
    public static final String IntentMsg_ISDELIVERY = "IntentMsg_IsDelivery";
    public static final String IntentTagMarker = "IntentTagMarker";
    public static final String IntentTagRefresh = "IntentTagRefresh";
    private PaintBackImageView _backImgView;
    private BaseDialogToolConfig _configDialog;
    protected CustomOkCancelDialog _deliverDialog;
    private String _deliverImagePath;
    private D_DeliveryPermission _deliverImagePermission;
    private MenuButton _menuButton;
    private PaintView _paintView;
    protected ImageSaver _saver;
    private boolean _isFullScreen = false;
    private boolean _isCameraView = false;
    private ButtonMgr _btnMgr = null;
    protected final Intent _intent = new Intent();
    private final ActivityResultLauncher<Intent> permissionStrageActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.epson.iprojection.ui.activities.marker.Activity_Marker$$ExternalSyntheticLambda0
        @Override // androidx.activity.result.ActivityResultCallback
        public final void onActivityResult(Object obj) {
            Activity_Marker.this.m90xcb66dd9((ActivityResult) obj);
        }
    });

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._baseActionBar = new CustomActionBar(this);
        setContentView(R.layout.main_marker);
        configure();
        this._menuButton = new MenuButton(this, this);
        this._backImgView = (PaintBackImageView) findViewById(R.id.img_painter_main_layerLower);
        PaintView paintView = (PaintView) findViewById(R.id.img_painter_main_layerUpper);
        this._paintView = paintView;
        paintView.setFullScreenMode(this._isFullScreen);
        this._paintView.setUpdateUndoRedoButtonImpl(this);
        if (this._isCameraView) {
            this._paintView.setCameraViewMode(true, this._backImgView.getDrawRect());
        }
        ButtonMgr buttonMgr = new ButtonMgr(this, this);
        this._btnMgr = buttonMgr;
        buttonMgr.setGettableUndoRedoImpl(this._paintView.getGettableRedoUndoImpl());
        this._btnMgr.updateUndoRedoButton();
        resetTool();
        if (Build.VERSION.SDK_INT >= 29) {
            this._saver = new ImageSaver10orMore(this);
        } else {
            this._saver = new ImageSaver9orLess(this);
        }
        this._saver.registerCallback(this);
        this._intent.putExtra(IntentTagRefresh, false);
        setResult(-1, this._intent);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarMarker));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        overridePendingTransition(0, 0);
    }

    @Override // com.epson.iprojection.ui.common.activity.ProjectableActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this._backImgView.getImageBitmap() == null) {
            finish();
        }
        try {
            sendImage();
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            destroy();
        }
    }

    private void destroy() {
        setRequestedOrientation(-1);
        this._saver.unregisterCallback();
        this._paintView.destroy();
        this._backImgView.destroy();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Analytics.getIns().sendEvent(eCustomEvent.PEN_USAGE_SITUATION);
    }

    private void resetTool() {
        this._btnMgr.initialSelectedToolButtonId(this);
        try {
            this._paintView.Initialize(this, this._btnMgr.getToolConfig());
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
        updateButton();
    }

    @Override // com.epson.iprojection.ui.activities.marker.IPaintButtonClickListener
    public void onClickToolButton(boolean z) {
        updateButton();
        if (z) {
            BaseDialogToolConfig create = DialogToolConfigFactory.create(this, this._btnMgr.getToolConfig(), this);
            this._configDialog = create;
            create.show();
        }
    }

    protected void updateButton() {
        try {
            this._btnMgr.setBackgroundColorUpdate();
            this._paintView.setTool(this._btnMgr.getToolConfig());
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        this._menuButton.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean onPrepareOptionsMenu = this._menuButton.onPrepareOptionsMenu(menu);
        super.onPrepareOptionsMenu(menu);
        return onPrepareOptionsMenu;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        this._menuButton.onOptionsItemSelected(menuItem);
        return super.onOptionsItemSelected(menuItem);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Bundle extras;
        Lg.d("[paint] onActivityResult()");
        super.onActivityResult(i, i2, intent);
        if (i == 2003 && i2 == 0) {
            finish();
        }
        boolean z = true;
        if (intent != null && (extras = intent.getExtras()) != null) {
            z = extras.getBoolean(IntentTagRefresh, true);
        }
        this._intent.putExtra(IntentTagRefresh, z);
    }

    private void configure() {
        if (getIntent().getStringExtra(IntentMsg_FullScreenMode) != null) {
            this._isFullScreen = true;
            Lg.d("FullScreen mode");
        } else {
            Lg.d("not FullScreen mode");
        }
        if (getIntent().getBooleanExtra(IntentMsg_CameraViewMode, false)) {
            this._isCameraView = true;
            Lg.d("CameraView mode");
        } else {
            Lg.d("not CameraView mode");
        }
        int intExtra = getIntent().getIntExtra(IntentMsg_ActivityRotation, -1);
        if (intExtra >= 0) {
            setRequestedOrientation(intExtra);
        } else {
            setRequestedOrientation(DisplayInfoUtils.getOrientationType(this));
        }
    }

    @Override // com.epson.iprojection.ui.activities.marker.IOnSendEvent
    public void onSendEvent() throws BitmapMemoryException {
        sendImage();
    }

    private void sendImage() throws BitmapMemoryException {
        if (Pj.getIns().isConnected()) {
            Lg.d("sendImage");
            Bitmap imageBitmap = this._paintView.getImageBitmap();
            Pj.getIns().sendImage(this._backImgView.getImageBitmap(), imageBitmap);
            if (imageBitmap != null) {
                imageBitmap.recycle();
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IUpdateConfigListener
    public void onClickClearAllButton() {
        this._paintView.clear();
        this._btnMgr.selectLastSelectPenButton();
        try {
            sendImage();
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    protected void saveImage(Bitmap bitmap, boolean z) {
        this._saver.save(bitmap, false);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDeliverImage(String str, D_DeliveryPermission d_DeliveryPermission) {
        if (Pj.getIns().isModerator()) {
            return;
        }
        this._deliverImagePath = str;
        this._deliverImagePermission = d_DeliveryPermission;
        if (PrefUtils.readInt(this, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG) == 1) {
            showDeliverDialog();
        }
    }

    private void showDeliverDialog() {
        CustomOkCancelDialog customOkCancelDialog = this._deliverDialog;
        if (customOkCancelDialog != null && customOkCancelDialog.isShowing()) {
            this._deliverDialog.dismiss();
        }
        this._deliverDialog = new CustomOkCancelDialog(this, getString(R.string._AutoDisplayReceivedImageWantOpen_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.marker.Activity_Marker$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                Activity_Marker.this.m91x8305d08a(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$showDeliverDialog$0$com-epson-iprojection-ui-activities-marker-Activity_Marker  reason: not valid java name */
    public /* synthetic */ void m91x8305d08a(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            this._intent.putExtra(IntentTagRefresh, true);
            startDeliveryActivity(this._deliverImagePath, this._deliverImagePermission);
        } else if (i == -2) {
            this._intent.putExtra(IntentTagRefresh, false);
        }
        super.updateScreenLockView();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onChangeScreenLockStatus(boolean z) {
        CustomOkCancelDialog customOkCancelDialog = this._deliverDialog;
        if (customOkCancelDialog == null || !customOkCancelDialog.isShowing()) {
            super.onChangeScreenLockStatus(z);
        }
        this._paintView.lock(z);
    }

    @Override // com.epson.iprojection.ui.activities.marker.ImageSaver.ISaveFinishCallback
    public void callbackSaveSucceed() {
        invalidateSaveMenu(true);
    }

    @Override // com.epson.iprojection.ui.activities.marker.ImageSaver.ISaveFinishCallback
    public void callbackSaveFail() {
        invalidateSaveMenu(true);
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IUpdateConfigListener
    public void onUpdateConfig() {
        updateButton();
    }

    @Override // com.epson.iprojection.ui.activities.marker.IPaintButtonClickListener
    public void onClickUndoButton() {
        this._paintView.undoRedo(true);
    }

    @Override // com.epson.iprojection.ui.activities.marker.IPaintButtonClickListener
    public void onClickRedoButton() {
        this._paintView.undoRedo(false);
    }

    @Override // com.epson.iprojection.ui.activities.marker.IGettableUpdateUndoRedoButton
    public void updateUndoRedoButton() {
        this._btnMgr.updateUndoRedoButton();
    }

    @Override // com.epson.iprojection.ui.activities.marker.IGettableUpdateUndoRedoButton
    public void errorUndoRedoBitmap() {
        finish();
    }

    @Override // com.epson.iprojection.ui.activities.marker.IPaintMenuButtonClickListener
    public void onClickMenuSaveButton() {
        if (Build.VERSION.SDK_INT >= 29 || PermissionUtilsKt.isUsablePermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            save();
            return;
        }
        this.permissionStrageActivityLauncher.launch(new Intent(this, PermissionStrageActivity.class));
    }

    private void save() {
        try {
            Bitmap imageBitmap = this._backImgView.getImageBitmap();
            Bitmap imageBitmap2 = this._paintView.getImageBitmap();
            Bitmap createBitmap = Bitmap.createBitmap(imageBitmap.getWidth(), imageBitmap.getHeight(), Bitmap.Config.RGB_565);
            BitmapUtils.drawBitmapFitWithIn(imageBitmap, createBitmap);
            BitmapUtils.drawBitmapFitWithIn(imageBitmap2, createBitmap);
            saveImage(createBitmap, false);
            invalidateSaveMenu(false);
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$1$com-epson-iprojection-ui-activities-marker-Activity_Marker  reason: not valid java name */
    public /* synthetic */ void m90xcb66dd9(ActivityResult activityResult) {
        if (PermissionUtilsKt.isUsablePermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            save();
        } else {
            this._saver.showSaveFailed();
        }
    }

    private void invalidateSaveMenu(boolean z) {
        this._menuButton.setSaveMenuEnabled(z);
        invalidateOptionsMenu();
    }
}
