package com.epson.iprojection.ui.common.activity.base;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.MemoryUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.customer_satisfaction.controllers.CSController;
import com.epson.iprojection.customer_satisfaction.datastore.CSDataStore;
import com.epson.iprojection.customer_satisfaction.entities.EState;
import com.epson.iprojection.customer_satisfaction.gateways.CSAnalytics;
import com.epson.iprojection.customer_satisfaction.gateways.CSRepository;
import com.epson.iprojection.customer_satisfaction.presenters.AssistancePresenter;
import com.epson.iprojection.customer_satisfaction.presenters.SatisfactionPresenter;
import com.epson.iprojection.customer_satisfaction.presenters.StoreReviewPresenter;
import com.epson.iprojection.engine.common.D_DeliveryError;
import com.epson.iprojection.engine.common.D_ImageProcTime;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.engine.common.D_ThumbnailError;
import com.epson.iprojection.engine.common.D_ThumbnailInfo;
import com.epson.iprojection.ui.activities.camera.views.activities.Activity_Camera;
import com.epson.iprojection.ui.activities.delivery.Activity_PresenDelivery;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import com.epson.iprojection.ui.activities.drawermenu.DrawerList;
import com.epson.iprojection.ui.activities.drawermenu.DrawerSelectStatus;
import com.epson.iprojection.ui.activities.drawermenu.IOnChangeMirroringSwitchListener;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.moderator.MultiProjectionDisplaySettings;
import com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect;
import com.epson.iprojection.ui.activities.pjselect.dialogs.QueryDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.activities.presen.Activity_Presen;
import com.epson.iprojection.ui.activities.start.Activity_StartFromImplicitIntent;
import com.epson.iprojection.ui.activities.web.Activity_Web;
import com.epson.iprojection.ui.activities.whiteboard.WhiteboardActivity;
import com.epson.iprojection.ui.common.AppStatus;
import com.epson.iprojection.ui.common.Initializer;
import com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar;
import com.epson.iprojection.ui.common.actionbar.base.IOnClickAppIconButton;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activity.funcs.EndControler;
import com.epson.iprojection.ui.common.activity.funcs.IntentCalledState;
import com.epson.iprojection.ui.common.activitystatus.ActivityKillStatus;
import com.epson.iprojection.ui.common.activitystatus.ContentsSelectStatus;
import com.epson.iprojection.ui.common.activitystatus.NextCallIntentHolder;
import com.epson.iprojection.ui.common.activitystatus.NextCallType;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.analytics.event.screenview.ScreenNameUtils;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.interfaces.Backable;
import com.epson.iprojection.ui.common.singleton.AppKiller;
import com.epson.iprojection.ui.common.singleton.ProhibitFinalizingOnDestroyFlag;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.common.singleton.ServiceMessageReceiver;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.common.uiparts.RestoreWifiNotification;
import com.epson.iprojection.ui.engine_wrapper.DisconReason;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import com.google.android.gms.common.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class PjConnectableActivity extends IproBaseActivity implements IOnConnectListener, IOnClickAppIconButton, IOnDialogEventListener, IOnChangeMirroringSwitchListener {
    protected long _contentsActivityID;
    protected CSController _csController;
    protected DrawerList _drawerList;
    private AlertDialog _lockDialog;
    protected long _pushedDrawerStatusID;
    protected BaseCustomActionBar _baseActionBar = null;
    protected EndControler _endControler = null;
    protected final IntentCalledState _intentCalled = new IntentCalledState();
    protected boolean _isRoot = false;
    private boolean _isForeGround = false;
    private boolean _isKilledMyself = false;
    private boolean _isBackable = true;

    protected boolean isConnectEventSettable() {
        return true;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onChangeDeliveryPermission(boolean z, boolean z2, boolean z3) {
    }

    public void onChangeMPPLayout(ArrayList<D_MppLayoutInfo> arrayList) {
    }

    public void onChangeMirroringSwitch() {
    }

    public void onChangedScreenLockByMe(boolean z) {
    }

    public void onClickDialogNG(BaseDialog.ResultAction resultAction) {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDeliveryError(D_DeliveryError d_DeliveryError) {
    }

    public void onDialogCanceled() {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onEndDelivery() {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onFinishDelivery() {
    }

    protected void onNextCalllerTypeMirroring(Intent intent) {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onNotifyImageProcTime(D_ImageProcTime d_ImageProcTime) {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onStartDelivery() {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onThumbnailError(D_ThumbnailError d_ThumbnailError) {
    }

    public void onThumbnailInfo(D_ThumbnailInfo d_ThumbnailInfo) {
    }

    public void onUnregistered() {
    }

    public void onUpdateMPPUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2) {
    }

    protected boolean shouldDisplaySatisfactionViewWhenDisconnect() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Bundle extras;
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent != null && (extras = intent.getExtras()) != null && extras.getString(IntentTagDefine.ROOT_TAG) != null) {
            if (!AppStatus.getIns().isActivated) {
                onAppActivated();
            }
            this._isRoot = true;
        }
        this._endControler = new EndControler(this, this._isRoot);
        ActivityGetter.getIns().set(this);
        this._csController = new CSController(new SatisfactionPresenter(this, getSupportFragmentManager(), getLayoutInflater()), new StoreReviewPresenter(this), new CSRepository(CSDataStore.Companion.provideDataStore(this)), new CSAnalytics(), new AssistancePresenter(this));
        getLifecycle().addObserver(this._csController);
        if (!Pj.getIns().isAlreadyInited()) {
            Initializer.initialize(this);
            Pj.getIns().initialize(this);
            new Runnable() { // from class: com.epson.iprojection.ui.common.activity.base.PjConnectableActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    PjConnectableActivity.this.m193x6fb40c2b();
                }
            }.run();
        }
        this._drawerList = new DrawerList(this, this);
        if (this._isRoot) {
            ContentsSelectStatus.getIns().clearForce();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$0$com-epson-iprojection-ui-common-activity-base-PjConnectableActivity  reason: not valid java name */
    public /* synthetic */ void m193x6fb40c2b() {
        this._csController.onChangeState(EState.LAUNCH);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        AppKiller.getIns().onRestart(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        ActivityGetter.getIns().set(this);
        this._isForeGround = true;
        Pj.getIns().onActivityStart();
        this._intentCalled.clear();
        if (!AppStatus.getIns().isActivated) {
            onAppActivated();
        }
        if (!this._drawerList.isAlreadyCreated()) {
            this._drawerList.create();
        }
        if (ActivityKillStatus.getIns().isKilling()) {
            if (this._isRoot) {
                ActivityKillStatus.getIns().stopKill();
                if (NextCallIntentHolder.getIns().exists()) {
                    startActivityForResult(NextCallIntentHolder.getIns().get(), CommonDefine.REQUEST_CODE_DRAWER);
                    clearNextCallInfo();
                }
            } else if (ActivityKillStatus.getIns().isTillContents()) {
                if (((this instanceof Activity_Presen) || (this instanceof Activity_Web) || (this instanceof Activity_Camera)) && NextCallType.getIns().get() == ContentsSelectStatus.getIns().get()) {
                    ActivityKillStatus.getIns().stopKill();
                    ActivityKillStatus.getIns().setTillContents(false);
                    clearNextCallInfo();
                    return;
                }
                finish();
            } else {
                finish();
            }
        }
    }

    private void clearNextCallInfo() {
        NextCallIntentHolder.getIns().clear();
        NextCallType.getIns().set(eContentsType.None);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        Lg.d("onResume");
        this._drawerList.refresh();
        AppStatus.getIns()._isAppForeground = true;
        this._intentCalled.clear();
        if (Pj.getIns().isNeedRestart()) {
            Pj.getIns().finalizeEngine();
            Pj.getIns().initialize(this);
        }
        MemoryUtils.show(this);
        if (isConnectEventSettable()) {
            Pj.getIns().setOnConnectListener(this);
        }
        ActivityGetter.getIns().set(this);
        if (Pj.getIns().isConnected()) {
            getWindow().addFlags(128);
        }
        updateActionBar();
        updateScreenLockView();
        if (ServiceMessageReceiver.getIns().isReceivedDisconnectMsg()) {
            if (!Pj.getIns().isMppLockedByModerator()) {
                QueryDialog queryDialog = new QueryDialog(this, QueryDialog.MessageType.Disconnect, this, BaseDialog.ResultAction.DISCONNECT, null);
                queryDialog.create(this);
                queryDialog.show();
            }
            ServiceMessageReceiver.getIns().receiveDisconnectMsg(false);
        }
        ToastMgr.getIns().showDelay(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        if (isTaskLaunched() && AppStatus.getIns().isActivated) {
            onAppDisactivated();
        }
        AlertDialog alertDialog = this._lockDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this._lockDialog = null;
        }
        super.onPause();
        if (isConnectEventSettable()) {
            Pj.getIns().disableOnConnectListener();
        }
        AppStatus.getIns()._isAppForeground = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        this._isForeGround = false;
        this._endControler.reset();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (isTaskRoot() && isTopActivity() && AppStatus.getIns().isActivated) {
            onAppDisactivated();
        }
        AppKiller.getIns().onDestroy(this._isRoot);
        if (isRootActivity() && Pj.getIns().isConnected()) {
            if (ProhibitFinalizingOnDestroyFlag.INSTANCE.isEnabled()) {
                ProhibitFinalizingOnDestroyFlag.INSTANCE.disable();
            } else {
                Pj.getIns().onAppFinished();
                if (Build.VERSION.SDK_INT < 29 && Pj.getIns().isWifiChanged()) {
                    Pj.getIns().restoreWifi();
                }
            }
        }
        this._drawerList.destroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this._intentCalled.clear();
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        this._isKilledMyself = true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        Toast.makeText(this, "onLowMemory()", 1).show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void recreateDrawerList() {
        createDrawerList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void pushDrawerStatus(eDrawerMenuItem edrawermenuitem) {
        if (edrawermenuitem != DrawerSelectStatus.getIns().get() || edrawermenuitem == eDrawerMenuItem.UserCtl || edrawermenuitem == eDrawerMenuItem.Delivery) {
            this._pushedDrawerStatusID = System.nanoTime();
            DrawerSelectStatus.getIns().push(this._pushedDrawerStatusID, edrawermenuitem);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void popDrawerStatus() {
        DrawerSelectStatus.getIns().pop(this._pushedDrawerStatusID);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setContentsSelectStatus(eContentsType econtentstype) {
        this._contentsActivityID = System.nanoTime();
        ContentsSelectStatus.getIns().set(this._contentsActivityID, econtentstype);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void clearContentsSelectStatus() {
        ContentsSelectStatus.getIns().clear(this._contentsActivityID);
    }

    public void updateActionBar() {
        updatePjButtonState();
        ImageView imageView = (ImageView) findViewById(R.id.img_userlock);
        if (imageView != null) {
            if (Pj.getIns().isModerator() && Pj.getIns().isSetMppScreenLock()) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.img_moderator);
        if (imageView2 != null) {
            if (Pj.getIns().isModerator()) {
                imageView2.setVisibility(0);
            } else {
                imageView2.setVisibility(8);
            }
        }
        BaseCustomActionBar baseCustomActionBar = this._baseActionBar;
        if (baseCustomActionBar != null) {
            baseCustomActionBar.setOnClickAppIconButton(this);
        }
        invalidateOptionsMenu();
    }

    public void updateForcedStateChangeModLckIcons(int i) {
        ImageView imageView = (ImageView) findViewById(R.id.img_userlock);
        if (imageView != null) {
            imageView.setVisibility(i);
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.img_moderator);
        if (imageView2 != null) {
            imageView2.setVisibility(i);
        }
    }

    public void createDrawerList() {
        this._drawerList.create();
    }

    public void enableDrawerList() {
        this._drawerList.enable();
    }

    public void disableDrawerList() {
        this._drawerList.disable();
    }

    public void updatePjButtonState() {
        if (Pj.getIns().isNeedRestart()) {
            Pj.getIns().finalizeEngine();
            Pj.getIns().initialize(this);
            ActivityGetter.getIns().set(this);
        }
        BaseCustomActionBar baseCustomActionBar = this._baseActionBar;
        if (baseCustomActionBar != null) {
            baseCustomActionBar.update();
            if (Pj.getIns().isConnected()) {
                this._baseActionBar.setColorConnected();
            } else {
                this._baseActionBar.setColorDisconnected();
            }
        }
    }

    public void onRegisterSucceed() {
        updatePjButtonState();
        this._drawerList.create();
        invalidateOptionsMenu();
        Analytics.getIns().sendEvent(eCustomEvent.REGISTERED);
    }

    public void onConnectSucceed() {
        updatePjButtonState();
        this._drawerList.create();
        invalidateOptionsMenu();
        if (this._csController != null) {
            new Runnable() { // from class: com.epson.iprojection.ui.common.activity.base.PjConnectableActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PjConnectableActivity.this.m192xedd613a8();
                }
            }.run();
        }
        this._screenName = ScreenNameUtils.Companion.createScreenName((String) Objects.requireNonNull(getClass().getCanonicalName()));
        Analytics.getIns().sendScreenEvent(this._screenName);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onConnectSucceed$1$com-epson-iprojection-ui-common-activity-base-PjConnectableActivity  reason: not valid java name */
    public /* synthetic */ void m192xedd613a8() {
        this._csController.onChangeState(EState.USING);
    }

    public void onConnectFail(int i, IOnConnectListener.FailReason failReason) {
        updatePjButtonState();
    }

    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason, boolean z) {
        updateScreenLockView();
        updateActionBar();
        updatePjButtonState();
        this._drawerList.close();
        this._drawerList.create();
        invalidateOptionsMenu();
        MultiProjectionDisplaySettings.INSTANCE.clear();
        AppKiller.getIns().onDisconnect(this);
        if (disconedReason != IOnConnectListener.DisconedReason.Default || this._csController == null || !shouldDisplaySatisfactionViewWhenDisconnect() || z) {
            return;
        }
        new Runnable() { // from class: com.epson.iprojection.ui.common.activity.base.PjConnectableActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PjConnectableActivity.this.m194xa4084ccd();
            }
        }.run();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onDisconnect$2$com-epson-iprojection-ui-common-activity-base-PjConnectableActivity  reason: not valid java name */
    public /* synthetic */ void m194xa4084ccd() {
        this._csController.onChangeState(EState.COLLECTING_USED);
    }

    public void onDisconnectOne(int i, IOnConnectListener.DisconedReason disconedReason) {
        updatePjButtonState();
    }

    public void onConnectCanceled() {
        updatePjButtonState();
    }

    public void onChangeMPPControlMode(IOnConnectListener.MppControlMode mppControlMode) {
        if (mppControlMode != IOnConnectListener.MppControlMode.ModeratorEntry) {
            updateScreenLockView();
        }
        if (mppControlMode != IOnConnectListener.MppControlMode.ModeratorAdmin) {
            MultiProjectionDisplaySettings.INSTANCE.setThumb(false);
        }
        updateActionBar();
    }

    public void onChangeScreenLockStatus(boolean z) {
        updateScreenLockView();
    }

    public void startActivityForConnect() {
        Intent intent = new Intent(this, Activity_PjSelect.class);
        intent.putExtra(Activity_PjSelect.SEARCH_AND_CONNECT, Activity_PjSelect.TRUE);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateScreenLockView() {
        if (Pj.getIns().isMppLockedByModerator()) {
            AlertDialog alertDialog = this._lockDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(LayoutInflater.from(this).inflate(R.layout.dialog_userlock, (ViewGroup) null));
                builder.setCancelable(false);
                AlertDialog create = builder.create();
                this._lockDialog = create;
                create.getWindow().setFlags(0, 2);
                this._lockDialog.show();
                return;
            }
            return;
        }
        AlertDialog alertDialog2 = this._lockDialog;
        if (alertDialog2 != null) {
            alertDialog2.dismiss();
            this._lockDialog = null;
        }
    }

    public void onDeliverImage(String str, D_DeliveryPermission d_DeliveryPermission) {
        if (!Pj.getIns().isModerator() && PrefUtils.readInt(this, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG) == 1) {
            if (Pj.getIns().isMppLockedByModerator()) {
                RegisteredDialog.getIns().dismiss();
                Pj.getIns().clearAllDialog();
            }
            startDeliveryActivity(str, d_DeliveryPermission);
        }
    }

    public void startDeliveryActivity(String str, D_DeliveryPermission d_DeliveryPermission) {
        Intent intent = new Intent(this, Activity_PresenDelivery.class);
        intent.putExtra(Activity_Presen.INTENT_TAG_PATH, str);
        if (d_DeliveryPermission != null) {
            intent.putExtra(D_DeliveryPermission.INTENT_TAG_DELIVERY_PARMISSION, d_DeliveryPermission);
        }
        if (!this._isRoot) {
            super.finish();
            ActivityKillStatus.getIns().startKill();
            NextCallIntentHolder.getIns().set(intent);
        } else {
            startActivity(intent);
        }
        NextCallType.getIns().set(eContentsType.Delivery);
    }

    public void onAppActivated() {
        AppStatus.getIns().isActivated = true;
        RestoreWifiNotification.getIns().hide();
    }

    public void onAppDisactivated() {
        AppStatus.getIns().isActivated = false;
    }

    public boolean isForeGroundThisActivity() {
        return this._isForeGround;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isImplicitIntent() {
        return getIntent().getStringExtra(Activity_StartFromImplicitIntent.TAG_IMPLICIT) != null;
    }

    List<ActivityManager.RunningTaskInfo> getRunningTaskInfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService("activity");
        if (activityManager == null) {
            return null;
        }
        return activityManager.getRunningTasks(3);
    }

    protected ComponentName getComponentNameOfBase() {
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = getRunningTaskInfo();
        if (CollectionUtils.isEmpty(runningTaskInfo)) {
            return null;
        }
        return runningTaskInfo.get(0).baseActivity;
    }

    protected ComponentName getComponentNameOfTop() {
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = getRunningTaskInfo();
        if (CollectionUtils.isEmpty(runningTaskInfo)) {
            return null;
        }
        return runningTaskInfo.get(0).topActivity;
    }

    protected int compareToTopPackage() {
        ComponentName componentNameOfTop = getComponentNameOfTop();
        if (componentNameOfTop == null) {
            Lg.e("can not get componentName");
            return -1;
        }
        Lg.d("compareToTopPackage top:" + componentNameOfTop + " this:" + getComponentName().toString());
        return componentNameOfTop.getPackageName().compareTo(getComponentName().getPackageName());
    }

    protected boolean isTopActivity() {
        return isTopActivity(getComponentNameOfTop());
    }

    protected boolean isTopActivity(ComponentName componentName) {
        if (componentName != null && componentName.compareTo(getComponentName()) == 0) {
            Lg.d("isTopActivity = true, " + componentName);
            return true;
        } else if (componentName != null) {
            Lg.d("isTopActivity = false, cn=" + componentName + " this=" + getComponentName().toString());
            return false;
        } else {
            return false;
        }
    }

    protected boolean isTaskLaunched() {
        ComponentName componentNameOfBase = getComponentNameOfBase();
        return (componentNameOfBase == null || componentNameOfBase.getPackageName() == null || getPackageName() == null || componentNameOfBase.getPackageName().equals(getPackageName())) ? false : true;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this._drawerList.isOpend()) {
            this._drawerList.close();
            return true;
        }
        if ((this instanceof Activity_Web) || (this instanceof WhiteboardActivity)) {
            Backable backable = (Backable) this;
            if (backable.canGoBack()) {
                backable.goBack();
                return true;
            }
        }
        if (this._isBackable && !this._endControler.onKeyDown(i, keyEvent) && this._intentCalled.isCallable()) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4 || this._intentCalled.isCallable()) {
            if (i == 4 && isFinishing()) {
                this._intentCalled._backed = true;
            }
            return super.onKeyUp(i, keyEvent);
        }
        return true;
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        if (this._intentCalled.isCallable()) {
            if (this._isKilledMyself || !isFinishing()) {
                super.startActivity(intent);
                this._intentCalled._called = true;
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void startActivityForResult(Intent intent, int i) {
        if (!this._intentCalled.isCallable()) {
            Lg.e("startActivity したけども _intentCelled.isCallable() 判定でリターン！！");
        } else if (!this._isKilledMyself && isFinishing()) {
            Lg.e("startActivity したけども  !_isKilledMyself && isFinishing() 判定でリターン！！");
        } else {
            super.startActivityForResult(intent, i);
        }
    }

    public boolean isRootActivity() {
        return this._isRoot;
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.IOnClickAppIconButton
    public void onClickAppIconButton() {
        this._drawerList.changeDrawerStatus();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (Pj.getIns().isMppLockedByModerator() && keyEvent != null && keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 4) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public void onClickDialogOK(String str, BaseDialog.ResultAction resultAction) {
        if (resultAction == BaseDialog.ResultAction.DISCONNECT && Pj.getIns().isConnected()) {
            Pj.getIns().disconnect(DisconReason.UserAction);
        }
    }

    protected void disableBackKey() {
        this._isBackable = false;
    }
}
