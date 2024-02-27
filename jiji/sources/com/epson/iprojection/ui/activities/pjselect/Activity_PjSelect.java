package com.epson.iprojection.ui.activities.pjselect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.DefLoader;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.PermissionUtilsKt;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.ui.activities.drawermenu.DrawerList;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.pjselect.dialogs.WifiDialog;
import com.epson.iprojection.ui.activities.pjselect.history.PjHistory;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract;
import com.epson.iprojection.ui.activities.pjselect.networkstandby.NetworkStandbyEnabler;
import com.epson.iprojection.ui.activities.pjselect.permission.registration.PermissionLocationActivity;
import com.epson.iprojection.ui.activities.pjselect.qrcode.QRDataHolder;
import com.epson.iprojection.ui.activities.remote.Activity_Remote;
import com.epson.iprojection.ui.common.Initializer;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activity.ProjectableActivity;
import com.epson.iprojection.ui.common.activitystatus.NextCallType;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.analytics.event.screenview.ScreenNameUtils;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import com.epson.iprojection.ui.common.dialogs.ConnectWhenOpenContentsDialog;
import com.epson.iprojection.ui.common.singleton.AppStartActivityManager;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.common.singleton.ServiceMessageReceiver;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.common.uiparts.OKDialog;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.StateMachine;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes.dex */
public class Activity_PjSelect extends ProjectableActivity implements IFragmentHomeListener, IActionBarHomeListener, Contract.IOnFinishedToEnableNetworkStandby {
    public static final String SEARCH_AND_CONNECT = "SEARCH_AND_CONNECT";
    public static final String TAG_KILL_SELF_WHEN_CONNECTED = "tag_kill_self_when_connected";
    public static final String TAG_NFC_CONNECT = "tag_nfc_connect";
    public static final String TAG_PREF_LOCATION_CHECK_REGISTERED = "tag_pref_location_check_registered";
    public static final String TAG_PREF_LOCATION_REQUEST_FOR_REGISTERED_FIRST = "tag_pref_location_request_for_registrered_first";
    public static final String TRUE = "TRUE";
    private ActionBarHome _actionBar;
    private eContentsType _contentsType;
    private WifiDialog _dialogWiFiSetting;
    private FragmentHomeBase _fragment;
    private ArrayList<ConnectPjInfo> _lastConnectedPjList;
    private PjSelectMenuMgr _menuMgr;
    private NetworkStandbyEnabler _networkStandbyEnabler;
    private boolean _shouldNotifyLaunchRoute;
    private StateMachine.State _fragmentState = StateMachine.State.Unselected;
    private boolean _shouldKillSelfWhenConnected = false;
    private boolean _isProhibitedStartSeach = false;
    private boolean _isMirroringProcessing = false;
    private boolean _shouldCallRemoteActivity = false;
    private boolean _isRequiredLocationPermissionOnRegisterDialog = false;

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Lg.d("Activity_PjSelect::onCreate()");
        if (isRootActivity() && !isTaskRoot()) {
            finish();
        }
        QRDataHolder.INSTANCE.setQrRowData(null);
        pushDrawerStatus(eDrawerMenuItem.Connect);
        Pj.getIns().setupPjFinder(null);
        createView();
        createProcessHandler();
        String intentExtraOpenContentsID = getIntentExtraOpenContentsID();
        if (intentExtraOpenContentsID != null) {
            this._isProhibitedStartSeach = true;
            startSearchAndConnect(intentExtraOpenContentsID);
        } else if (getIntent().getStringExtra(TAG_KILL_SELF_WHEN_CONNECTED) != null) {
            this._isProhibitedStartSeach = true;
            this._shouldKillSelfWhenConnected = true;
            Pj.getIns().onClickConnectEventButton(true, false);
        } else if (getIntent().getStringExtra(TAG_NFC_CONNECT) != null) {
            this._shouldKillSelfWhenConnected = true;
        }
        Intent intent = new Intent(this, Activity_Remote.class);
        intent.putExtra(IntentTagDefine.LAUNCH_ROUTE_TAG, getIntent().getStringExtra(IntentTagDefine.LAUNCH_ROUTE_TAG));
        if (isRootActivity()) {
            if (Pj.getIns().isAllPjTypeSignage()) {
                startActivity(intent);
            } else if (isLaunchRoute()) {
                this._shouldNotifyLaunchRoute = true;
            }
        }
    }

    private boolean isLaunchRoute() {
        return getIntent().getStringExtra(IntentTagDefine.LAUNCH_ROUTE_TAG) != null;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, android.app.Activity
    public void onRestart() {
        super.onRestart();
        this._screenName = ScreenNameUtils.Companion.createScreenName(eDrawerMenuItem.Connect.getMenuName(), (String) Objects.requireNonNull(getClass().getCanonicalName()));
        this._isMirroringProcessing = false;
    }

    @Override // com.epson.iprojection.ui.common.activity.ProjectableActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        importFiles();
        changeFragment();
        sendImageOnResume();
        this._actionBar.setVisibilityDisconButtonUnselected(false);
        if (this._shouldCallRemoteActivity) {
            if (Pj.getIns().isAllPjTypeSignage()) {
                startActivity(new Intent(this, Activity_Remote.class));
            }
            this._shouldCallRemoteActivity = false;
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this._lastConnectedPjList = Pj.getIns().getNowConnectingPJList();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        popDrawerStatus();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        PjSelectMenuMgr pjSelectMenuMgr = this._menuMgr;
        if (pjSelectMenuMgr != null) {
            pjSelectMenuMgr.onCreateOptionsMenu(this, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        PjSelectMenuMgr pjSelectMenuMgr = this._menuMgr;
        boolean onPrepareOptionsMenu = pjSelectMenuMgr != null ? pjSelectMenuMgr.onPrepareOptionsMenu(menu) : false;
        super.onPrepareOptionsMenu(menu);
        return onPrepareOptionsMenu;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        PjSelectMenuMgr pjSelectMenuMgr = this._menuMgr;
        if (pjSelectMenuMgr != null) {
            pjSelectMenuMgr.onOptionsItemSelected(menuItem);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1011) {
            if (i2 == 3000) {
                showRegisterSucceedDialog();
            }
        } else if (i == 1013) {
            FragmentHomeBase fragmentHomeBase = this._fragment;
            if (fragmentHomeBase != null) {
                fragmentHomeBase.onActivityResult(i, i2, intent);
            }
        } else if (i == 1041) {
            PrefUtils.writeBoolean(this, TAG_PREF_LOCATION_REQUEST_FOR_REGISTERED_FIRST, false);
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                Pj.getIns().saveSsidDataWhenRegistered();
            }
            this._shouldCallRemoteActivity = true;
        } else if (i != 1042) {
        } else {
            if (isOnLocationSetting()) {
                this._intentCalled.clear();
                startActivityForResult(new Intent(this, PermissionLocationActivity.class), CommonDefine.REQUEST_CODE_LOCATION_PERMISSION_FOR_REGISTRATION);
                return;
            }
            useLocationDialog();
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity
    protected void onNextCalllerTypeMirroring(Intent intent) {
        startActivityForResult(intent, NextCallType.getIns().getRequestCode());
        NextCallType.getIns().set(eContentsType.None);
    }

    public DrawerList getDrawerList() {
        return this._drawerList;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.activities.drawermenu.IOnChangeMirroringSwitchListener
    public void onChangeMirroringSwitch() {
        this._fragment.onChangeMirroringSwitch();
    }

    public void startSearchAndConnect(String str) {
        this._contentsType = DrawerList.convertType(str);
        Pj.getIns().onClickConnectEventButton(true, false);
    }

    private String getIntentExtraOpenContentsID() {
        Intent intent = getIntent();
        if (intent.getStringExtra(SEARCH_AND_CONNECT) != null) {
            return intent.getStringExtra(ConnectWhenOpenContentsDialog.ID);
        }
        return null;
    }

    private void createView() {
        setContentView(R.layout.main_home);
        createToolBar();
        createMenu();
    }

    private void createToolBar() {
        ActionBarHome actionBarHome = new ActionBarHome(this);
        this._actionBar = actionBarHome;
        this._baseActionBar = actionBarHome;
        this._drawerList.enableDrawerToggleButton((Toolbar) findViewById(R.id.toolbarPjSelect));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void createMenu() {
        this._menuMgr = new PjSelectMenuMgr(this);
    }

    private void createProcessHandler() {
        procServiceIntent();
        AppStartActivityManager.getIns().doStartActivity(this);
    }

    private void procServiceIntent() {
        Intent intent = getIntent();
        if (intent.getBooleanExtra(CommonDefine.INTENT_EXTRA_DISCONNECT, false)) {
            ServiceMessageReceiver.getIns().receiveDisconnectMsg(true);
        }
        if (intent.getBooleanExtra(CommonDefine.INTENT_EXTRA_MIRRORING, false)) {
            finish();
        }
    }

    private void importFiles() {
        Initializer.importFiles(this);
        DefLoader.LoadSettings(this);
    }

    private void sendImageOnResume() {
        if (!isRootActivity() || this._intentCalled._called || MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
            return;
        }
        Pj.getIns().sendWaitImage();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IFragmentHomeListener
    public void onResumeFragment() {
        showWifiOffDialog();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IFragmentHomeListener
    public void updateHomeActionBar() {
        updateActionBar();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IFragmentHomeListener
    public void actionConnectByPjListItem() {
        actionConnect();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IFragmentHomeListener
    public void restartEngine() {
        if (Pj.getIns().isNeedRestart()) {
            Pj.getIns().finalizeEngine();
            Pj.getIns().initialize(this);
            Pj.getIns().setOnConnectListener(this);
            ActivityGetter.getIns().set(this);
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IFragmentHomeListener
    public boolean prohibitStartSearch() {
        return this._isProhibitedStartSeach || this._isMirroringProcessing;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IFragmentHomeListener
    public void startActivityFromMyFragment(Intent intent, int i) {
        startActivityForResult(intent, i);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IActionBarHomeListener
    public void actionConnectByActionBar() {
        actionConnect();
    }

    private void changeFragment() {
        StateMachine.State state = Pj.getIns().getState();
        if (this._fragment == null || state != this._fragmentState || isUpdatePjList()) {
            int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$State[state.ordinal()];
            if (i == 1) {
                changeFragment(FragmentHomeUnselected.newInstance());
            } else if (i == 2) {
                changeFragment(FragmentHomeRegistered.newInstance());
            } else if (i == 3) {
                changeFragment(FragmentHomeConnected.newInstance());
            }
            this._fragmentState = state;
            updateActionBar();
        }
    }

    /* renamed from: com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$State;

        static {
            int[] iArr = new int[StateMachine.State.values().length];
            $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$State = iArr;
            try {
                iArr[StateMachine.State.Unselected.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$State[StateMachine.State.Registered.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$State[StateMachine.State.Connected.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void changeFragment(FragmentHomeBase fragmentHomeBase) {
        if (this._shouldNotifyLaunchRoute) {
            fragmentHomeBase._shouldNotifyLaunchRoute = true;
            this._shouldNotifyLaunchRoute = false;
        }
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, fragmentHomeBase);
        beginTransaction.commit();
        this._fragment = fragmentHomeBase;
    }

    private boolean shouldStartRemoteActivity() {
        return Pj.getIns().isAllPjTypeSignage();
    }

    private boolean isUpdatePjList() {
        ArrayList<ConnectPjInfo> arrayList;
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        if (nowConnectingPJList == null || (arrayList = this._lastConnectedPjList) == null || arrayList.equals(nowConnectingPJList)) {
            return false;
        }
        this._lastConnectedPjList = nowConnectingPJList;
        return true;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onRegisterSucceed() {
        super.onRegisterSucceed();
        this._fragment.setWifiConnector();
        this._fragment.onRegisterSucceed();
        changeFragment();
        showRegisterSucceedDialog();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onConnectSucceed() {
        super.onConnectSucceed();
        this._isProhibitedStartSeach = false;
        this._fragment.onConnectSucceed();
        new PjHistory().initialize(this, null, false).updateConnectedHistory(this);
        changeFragment();
        if (this._contentsType != null) {
            this._drawerList.startNextActivity(this._contentsType);
            this._contentsType = null;
        }
        if (this._shouldKillSelfWhenConnected) {
            if (!isRootActivity()) {
                finish();
            }
        } else {
            this._shouldKillSelfWhenConnected = false;
        }
        this._btnProj.setVisibility(0);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onConnectFail(int i, IOnConnectListener.FailReason failReason) {
        super.onConnectFail(i, failReason);
        this._isProhibitedStartSeach = false;
        this._fragment.onConnectFail(i, failReason);
        changeFragment();
        this._contentsType = null;
        this._shouldKillSelfWhenConnected = false;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason, boolean z) {
        super.onDisconnect(i, disconedReason, z);
        this._fragment.onDisconnect(i, disconedReason);
        changeFragment();
        this._btnProj.setVisibility(8);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDisconnectOne(int i, IOnConnectListener.DisconedReason disconedReason) {
        super.onDisconnectOne(i, disconedReason);
        this._fragment.onDisconnectOne(i, disconedReason);
        changeFragment();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onConnectCanceled() {
        super.onConnectCanceled();
        this._fragment.onConnectCanceled();
        changeFragment();
        this._shouldKillSelfWhenConnected = false;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onUnregistered() {
        changeFragment();
        this._drawerList.create();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onUpdateMPPUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2) {
        super.onUpdateMPPUserList(arrayList, arrayList2);
        this._fragment.onUpdateMPPUserList(arrayList, arrayList2);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onChangeMPPControlMode(IOnConnectListener.MppControlMode mppControlMode) {
        super.onChangeMPPControlMode(mppControlMode);
        this._fragment.onChangeMPPControlMode(mppControlMode);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onChangedScreenLockByMe(boolean z) {
        super.onChangedScreenLockByMe(z);
        this._fragment.onChangedScreenLockByMe(z);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.networkstandby.Contract.IOnFinishedToEnableNetworkStandby
    public void onEnabledNetworkStandby() {
        onClickRegisterDialogOkAfterNetworkStandbyProcess();
    }

    public void actionConnect() {
        if (!Pj.getIns().isRegistered() && !Pj.getIns().isAllPjTypeBusinessSelectHome()) {
            registerPj();
        } else if (Pj.getIns().isRegistedPjs5Over()) {
            new OKDialog(this, getString(R.string._Exceeded4PjConnect_));
        } else {
            connectPj();
        }
    }

    private void registerPj() {
        Pj.getIns().onClickRegisterEventButton();
    }

    private void connectPj() {
        if (Pj.getIns().isConnected()) {
            return;
        }
        Pj.getIns().onClickConnectEventButton();
    }

    private void showWifiOffDialog() {
        if ((Pj.getIns().isConnected() || !Pj.getIns().isConnectingByLinkageData()) && NetUtils.isWifiOff(this) && this._dialogWiFiSetting == null) {
            WifiDialog wifiDialog = new WifiDialog(this);
            this._dialogWiFiSetting = wifiDialog;
            wifiDialog.show();
        }
    }

    private void showRegisterSucceedDialog() {
        this._isRequiredLocationPermissionOnRegisterDialog = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_registered, (ViewGroup) null);
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.check_nw_standby);
        TextView textView = (TextView) inflate.findViewById(R.id.text_nw_standby);
        final CheckBox checkBox2 = (CheckBox) inflate.findViewById(R.id.check_permission);
        TextView textView2 = (TextView) inflate.findViewById(R.id.text_permission);
        if (!isStandbySettingOff()) {
            Lg.d("スタンバイオフのPJがない");
            checkBox.setVisibility(8);
            textView.setVisibility(8);
            checkBox.setChecked(false);
        } else {
            Lg.d("スタンバイオフのPJがある");
        }
        if ((!isGrantedLocationPermission() || !isOnLocationSetting()) && !isCheckedDontAskAgain()) {
            checkBox2.setChecked(PrefUtils.readBoolean(this, TAG_PREF_LOCATION_CHECK_REGISTERED, true));
        } else {
            checkBox2.setVisibility(8);
            textView2.setVisibility(8);
        }
        builder.setView(inflate);
        builder.setPositiveButton(getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect$$ExternalSyntheticLambda1
            {
                Activity_PjSelect.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                Activity_PjSelect.this.m110xd729af28(checkBox2, checkBox, dialogInterface, i);
            }
        });
        builder.setCancelable(false);
        AlertDialog create = builder.create();
        create.show();
        RegisteredDialog.getIns().setDialog(create);
    }

    /* renamed from: lambda$showRegisterSucceedDialog$0$com-epson-iprojection-ui-activities-pjselect-Activity_PjSelect */
    public /* synthetic */ void m110xd729af28(CheckBox checkBox, CheckBox checkBox2, DialogInterface dialogInterface, int i) {
        this._isRequiredLocationPermissionOnRegisterDialog = checkBox.isChecked();
        if (checkBox2.isChecked()) {
            NetworkStandbyEnabler networkStandbyEnabler = new NetworkStandbyEnabler(this, Pj.getIns(), this, Pj.getIns().getRegisteredPjList());
            this._networkStandbyEnabler = networkStandbyEnabler;
            networkStandbyEnabler.enableNetworkStandby();
            return;
        }
        onClickRegisterDialogOkAfterNetworkStandbyProcess();
    }

    private void onClickRegisterDialogOkAfterNetworkStandbyProcess() {
        boolean z = false;
        if (!isCheckedDontAskAgain()) {
            if (this._isRequiredLocationPermissionOnRegisterDialog) {
                PrefUtils.writeBoolean(this, TAG_PREF_LOCATION_CHECK_REGISTERED, true);
                if (isOnLocationSetting()) {
                    startActivityForResult(new Intent(this, PermissionLocationActivity.class), CommonDefine.REQUEST_CODE_LOCATION_PERMISSION_FOR_REGISTRATION);
                    z = true;
                } else {
                    useLocationDialog();
                }
            } else {
                PrefUtils.writeBoolean(this, TAG_PREF_LOCATION_CHECK_REGISTERED, false);
            }
        }
        if (z || !Pj.getIns().isAllPjTypeSignage()) {
            return;
        }
        startActivity(new Intent(this, Activity_Remote.class));
    }

    public void useLocationDialog() {
        new OkCancelDialog(this, getString(R.string._NoticeUseLocationRegistered_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect$$ExternalSyntheticLambda0
            {
                Activity_PjSelect.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                Activity_PjSelect.this.m111x1d842dcd(dialogInterface, i);
            }
        });
    }

    /* renamed from: lambda$useLocationDialog$1$com-epson-iprojection-ui-activities-pjselect-Activity_PjSelect */
    public /* synthetic */ void m111x1d842dcd(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), CommonDefine.REQUEST_CODE_LOCATION_SETTING_FOR_REGISTRATION);
        } else {
            showToastOnRefused();
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.ProjectableActivity
    public void updateProjBtn() {
        super.updateProjBtn();
        if (Pj.getIns().isConnected()) {
            this._btnProj.setVisibility(0);
        } else {
            this._btnProj.setVisibility(8);
        }
    }

    private boolean isOnLocationSetting() {
        return MethodUtil.compatGetLocationMode(this) != 0;
    }

    private void showToastOnRefused() {
        Toast.makeText(this, (int) R.string._ToastNoticeUseLocationRegistered_, 1).show();
    }

    private boolean isStandbySettingOff() {
        Iterator<ConnectPjInfo> it = Pj.getIns().getRegisteredPjList().iterator();
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            Lg.d(next.getPjInfo().PrjName + "のネットワークスタンバイ : " + next.getPjInfo().isStandbySetting);
            if (!next.getPjInfo().isStandbySetting) {
                return true;
            }
        }
        return false;
    }

    private boolean isGrantedLocationPermission() {
        if (PermissionUtilsKt.needGetLocationPermissionForWifi() && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            PrefUtils.readBoolean(this, TAG_PREF_LOCATION_REQUEST_FOR_REGISTERED_FIRST, true);
            return false;
        }
        return true;
    }

    private boolean isCheckedDontAskAgain() {
        if (PermissionUtilsKt.needGetLocationPermissionForWifi()) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || PrefUtils.readBoolean(this, TAG_PREF_LOCATION_REQUEST_FOR_REGISTERED_FIRST, true)) {
                return false;
            }
            return !ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION");
        }
        return true;
    }
}
