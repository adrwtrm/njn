package com.epson.iprojection.ui.activities.delivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.marker.Activity_Marker;
import com.epson.iprojection.ui.activities.presen.Activity_Presen;
import com.epson.iprojection.ui.activities.presen.eType;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.activities.presen.img_filer.ImageFiler;
import com.epson.iprojection.ui.activities.presen.img_filer.ImageFilerDelivery10orMore;
import com.epson.iprojection.ui.activities.support.intro.delivery.Activity_IntroDelivery;
import com.epson.iprojection.ui.common.actionbar.ActionBarSendable;
import com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.common.uiparts.OKDialog;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.common.uiparts.WaitDialog;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class Activity_PresenDelivery extends Activity_Presen implements IDeleteCallback {
    public static final String INTENT_TAG_CANCELED = "intent_tag_canceled";
    public static final String INTENT_TAG_COULDNOTDELETEDSOME = "intent_tag_couldnotdeletedsome";
    public static final String INTENT_TAG_COULDNOT_DELETED = "intent_tag_could_not_deleted";
    public static final String INTENT_TAG_DELETED = "intent_tag_deleted";
    public static final String TAG_ALREADY_SEEN_PRESEN_DELIVERY = "tag_already_seen_presen_delivery";
    private static boolean _isDeleteProcessing = false;
    private OkCancelDialog _deleteDialog;
    private Toolbar _toolBar;
    private WaitDialog _waitDialog;
    private D_DeliveryPermission _permission = null;
    private boolean _isKillMyself = false;
    private boolean _shouldRefreshFromMarker = true;
    private Deleter _deleter = null;

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen
    protected boolean isFileAvailable(String str) {
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this._isFailedCreation) {
            return;
        }
        D_DeliveryPermission d_DeliveryPermission = (D_DeliveryPermission) getIntent().getSerializableExtra(D_DeliveryPermission.INTENT_TAG_DELIVERY_PARMISSION);
        this._permission = d_DeliveryPermission;
        if (d_DeliveryPermission == null) {
            this._permission = new D_DeliveryPermission();
        }
        if (getIntent().getExtras().getString(INTENT_TAG_COULDNOT_DELETED) != null) {
            new OKDialog(this, getString(R.string._FailedDeliveryImagesDeleting_));
        }
        if (getIntent().getExtras().getString(INTENT_TAG_COULDNOTDELETEDSOME) != null) {
            new OKDialog(this, getString(R.string._FailedSomeDeliveryImagesDeleting_));
        }
        if (getIntent().getExtras().getString(INTENT_TAG_DELETED) != null) {
            Toast.makeText(this, (int) R.string._SuccessDeliveryImagesDelete_, 1).show();
        }
        if (getIntent().getExtras().getString(INTENT_TAG_CANCELED) != null) {
            Toast.makeText(this, (int) R.string._CanceledSearching_, 1).show();
        }
        if (!isAlreadySeen()) {
            startActivity(new Intent(this, Activity_IntroDelivery.class));
            setAlreadySeen();
        }
        this._waitDialog = new WaitDialog(this);
        updateToolbarLayout();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, android.app.Activity
    public void onRestart() {
        super.onRestart();
        if (isDeleteProcessing()) {
            return;
        }
        ImageFiler imageFiler = (ImageFiler) this._filer;
        if (this._filer == null) {
            return;
        }
        String newestFileName = DeliveryFileIO.getIns().getNewestFileName(this);
        int refreshedTotalPages = imageFiler.getRefreshedTotalPages(newestFileName);
        this._isChangedFile = imageFiler.isFileChanged(newestFileName);
        if (this._isChangedFile && refreshedTotalPages == 0) {
            this._isEmpty = true;
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen, com.epson.iprojection.ui.common.activity.ProjectableActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (isDeleteProcessing()) {
            return;
        }
        if (this._isEmpty) {
            String newestFileName = DeliveryFileIO.getIns().getNewestFileName(this);
            if (newestFileName != null) {
                m86xf6356058(newestFileName, this._permission);
                return;
            } else if (this._isChangedFile) {
                m86xf6356058(null, this._permission);
                return;
            } else {
                super.setTitle(getString(R.string._SharedImages_));
            }
        }
        if (this._isChangedFile) {
            refresh(DeliveryFileIO.getIns().getNewestFileName(this), null);
        }
        this._isChangedFile = false;
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isDeleteProcessing()) {
            return;
        }
        OkCancelDialog okCancelDialog = this._deleteDialog;
        if (okCancelDialog != null) {
            okCancelDialog.dismiss();
            this._deleteDialog = null;
            onClickStopEdit();
            this._thumbMgr.stopEditMode();
        }
        if (isFinishing() && this._isKillMyself) {
            overridePendingTransition(0, 0);
        }
        this._shouldRefreshFromMarker = true;
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen
    protected boolean isDeleteProcessing() {
        return _isDeleteProcessing;
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen
    protected void updateSelectStatusAndFiler() {
        this._eType = eType.DELIVER;
        if (Build.VERSION.SDK_INT >= 29) {
            this._filer = new ImageFilerDelivery10orMore(this, true);
        } else {
            this._filer = new ImageFiler(this, true);
        }
        this._eContentsType = eContentsType.Delivery;
        this._eDrawerMenuItem = eDrawerMenuItem.Delivery;
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen
    protected ImageFiler createImageFiler(boolean z) {
        if (Build.VERSION.SDK_INT >= 29) {
            return new ImageFilerDelivery10orMore(this, z);
        }
        return new ImageFiler(this, z);
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        if (this._isKillMyself) {
            overridePendingTransition(0, 0);
        }
        super.onConfigurationChanged(configuration);
        updateToolbarLayout();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Bundle extras;
        super.onActivityResult(i, i2, intent);
        if (intent != null && (extras = intent.getExtras()) != null) {
            this._shouldRefreshFromMarker = extras.getBoolean(Activity_Marker.IntentTagRefresh, true);
        }
        if (i == 997) {
            this._deleter.onActivityResult(i2, intent);
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen
    protected BaseCustomActionBar createToolBar() {
        return new ActionBarSendable(this, this, this, (ImageButton) findViewById(R.id.btn_presen_actionbar_marker), false, this._intentCalled, true);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDeliverImage(final String str, final D_DeliveryPermission d_DeliveryPermission) {
        if (str == null) {
            Lg.e("パスがnullです");
        } else if ((PrefUtils.readInt(this, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG) == 1 && !Pj.getIns().isModerator()) || this._isEmpty) {
            if (Pj.getIns().isMppLockedByModerator()) {
                startDeliveryActivity(str, d_DeliveryPermission);
            } else {
                new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.delivery.Activity_PresenDelivery$$ExternalSyntheticLambda0
                    {
                        Activity_PresenDelivery.this = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        Activity_PresenDelivery.this.m87x2f15c0f7(str, d_DeliveryPermission);
                    }
                }).start();
            }
        } else {
            super.initThumb();
            if (DeliveryFileIO.getIns().getFileNum(this) == 2) {
                this._seekButtonMgr.beVisible();
            }
        }
    }

    /* renamed from: lambda$onDeliverImage$1$com-epson-iprojection-ui-activities-delivery-Activity_PresenDelivery */
    public /* synthetic */ void m87x2f15c0f7(final String str, final D_DeliveryPermission d_DeliveryPermission) {
        while (true) {
            if (RegisteredDialog.getIns().isShowing() || _isDeleteProcessing) {
                Sleeper.sleep(100L);
            } else {
                this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.delivery.Activity_PresenDelivery$$ExternalSyntheticLambda2
                    {
                        Activity_PresenDelivery.this = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        Activity_PresenDelivery.this.m86xf6356058(str, d_DeliveryPermission);
                    }
                });
                return;
            }
        }
    }

    private void refresh(String str, D_DeliveryPermission d_DeliveryPermission) {
        if ((PrefUtils.readInt(this, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG) == 1 && !Pj.getIns().isModerator() && this._shouldRefreshFromMarker) || this._filer == null) {
            m86xf6356058(str, d_DeliveryPermission);
        } else {
            super.initThumb();
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen, com.epson.iprojection.ui.activities.presen.interfaces.IOnClickMenuButtonListener
    public void onClickStartEdit() {
        super.disableDrawerList();
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.toolbarPresen);
        constraintLayout.removeAllViews();
        getLayoutInflater().inflate(R.layout.toolbar_presen_edit, constraintLayout);
        this._baseActionBar = new ActionBarSendable(this, this, this, (ImageButton) findViewById(R.id.btn_presen_actionbar_marker), false, this._intentCalled, true);
        ((ImageButton) findViewById(R.id.btnTitleDelete)).setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.ui.activities.delivery.Activity_PresenDelivery$$ExternalSyntheticLambda1
            {
                Activity_PresenDelivery.this = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Activity_PresenDelivery.this.m85x9186a7c7(view);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpresenedit);
        this._toolBar = toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        try {
            setTitle(this._thumbMgr.getFileName());
        } catch (UnavailableException unused) {
        }
        this._baseActionBar.updateTopBarGroup();
        super.updatePjButtonState();
    }

    /* renamed from: lambda$onClickStartEdit$3$com-epson-iprojection-ui-activities-delivery-Activity_PresenDelivery */
    public /* synthetic */ void m85x9186a7c7(View view) {
        if (this._thumbMgr.existsChecked()) {
            this._deleteDialog = new OkCancelDialog(this, getString(R.string._DeleteDeliveryImages_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.delivery.Activity_PresenDelivery$$ExternalSyntheticLambda3
                {
                    Activity_PresenDelivery.this = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    Activity_PresenDelivery.this.m84x58a64728(dialogInterface, i);
                }
            });
        }
    }

    /* renamed from: lambda$onClickStartEdit$2$com-epson-iprojection-ui-activities-delivery-Activity_PresenDelivery */
    public /* synthetic */ void m84x58a64728(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            onClickDeleteOK();
        }
    }

    private void onClickDeleteOK() {
        RegisteredDialog.getIns().dismiss();
        this._waitDialog.show();
        _isDeleteProcessing = true;
        Deleter deleter = new Deleter(this, this._thumbMgr, this._filer, this);
        this._deleter = deleter;
        deleter.delete();
    }

    /* renamed from: com.epson.iprojection.ui.activities.delivery.Activity_PresenDelivery$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$delivery$eDeletedStatus;

        static {
            int[] iArr = new int[eDeletedStatus.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$delivery$eDeletedStatus = iArr;
            try {
                iArr[eDeletedStatus.eCouldnotDelete.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$delivery$eDeletedStatus[eDeletedStatus.eCouldnotDeleteSome.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$delivery$eDeletedStatus[eDeletedStatus.eDeleted.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$delivery$eDeletedStatus[eDeletedStatus.eCanceled.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.delivery.IDeleteCallback
    public void onDeleteFinished(eDeletedStatus edeletedstatus) {
        this._waitDialog.dismiss();
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$delivery$eDeletedStatus[edeletedstatus.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            restartActivityWithNoEffect(edeletedstatus);
        }
        _isDeleteProcessing = false;
        onClickStopEdit();
        this._thumbMgr.stopEditMode();
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen
    /* renamed from: restartActivity */
    public void m86xf6356058(String str, D_DeliveryPermission d_DeliveryPermission) {
        Intent intent = new Intent(this, Activity_PresenDelivery.class);
        intent.putExtra(Activity_Presen.INTENT_TAG_PATH, str);
        intent.putExtra(D_DeliveryPermission.INTENT_TAG_DELIVERY_PARMISSION, d_DeliveryPermission);
        startActivity(intent);
        finish();
    }

    private void restartActivityWithNoEffect(eDeletedStatus edeletedstatus) {
        finish();
        this._isKillMyself = true;
        String newestFileName = DeliveryFileIO.getIns().getNewestFileName(this);
        Intent intent = new Intent(getApplicationContext(), Activity_PresenDelivery.class);
        intent.putExtra(Activity_Presen.INTENT_TAG_PATH, newestFileName);
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$delivery$eDeletedStatus[edeletedstatus.ordinal()];
        intent.putExtra(i != 1 ? i != 2 ? i != 3 ? i != 4 ? "" : INTENT_TAG_CANCELED : INTENT_TAG_DELETED : INTENT_TAG_COULDNOTDELETEDSOME : INTENT_TAG_COULDNOT_DELETED, "empty message");
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen, com.epson.iprojection.ui.activities.presen.interfaces.IOnClickMenuButtonListener
    public void onClickStopEdit() {
        super.enableDrawerList();
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.toolbarPresen);
        constraintLayout.removeAllViews();
        getLayoutInflater().inflate(R.layout.toolbar_presen, constraintLayout);
        this._baseActionBar = new ActionBarSendable(this, this, this, (ImageButton) findViewById(R.id.btn_presen_actionbar_marker), false, this._intentCalled, true);
        this._toolBar = (Toolbar) findViewById(R.id.toolbarpresen);
        this._drawerList.enableDrawerToggleButton(this._toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        try {
            setTitle(this._thumbMgr.getFileName());
        } catch (UnavailableException unused) {
        }
        this._baseActionBar.enable();
        this._baseActionBar.updateTopBarGroup();
        super.updateActionBar();
    }

    private void updateToolbarLayout() {
        if (this._isEmpty) {
            this._toolBar = (Toolbar) findViewById(R.id.toolbarpresenempty);
        } else {
            Toolbar toolbar = this._toolBar;
            if (toolbar != null && toolbar.getId() == R.id.toolbarpresenedit) {
                onClickStartEdit();
                return;
            }
            this._toolBar = (Toolbar) findViewById(R.id.toolbarpresen);
        }
        this._drawerList.enableDrawerToggleButton(this._toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        this._baseActionBar.updateTopBarGroup();
        super.updateActionBar();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (_isDeleteProcessing && i == 4) {
            Lg.w("抑制");
            return true;
        } else if (this._thumbMgr != null && this._thumbMgr.isEditMode() && i == 4) {
            this._thumbMgr.stopEditMode();
            onClickStopEdit();
            return true;
        } else {
            return super.onKeyDown(i, keyEvent);
        }
    }

    private boolean isAlreadySeen() {
        return PrefUtils.read(this, TAG_ALREADY_SEEN_PRESEN_DELIVERY) != null;
    }

    private void setAlreadySeen() {
        PrefUtils.write(this, TAG_ALREADY_SEEN_PRESEN_DELIVERY, "empty_message", (SharedPreferences.Editor) null);
    }

    @Override // com.epson.iprojection.ui.activities.presen.Activity_Presen
    protected boolean isKillMyself() {
        return this._isKillMyself;
    }
}
