package com.epson.iprojection.ui.activities.drawermenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.IntentDefine;
import com.epson.iprojection.common.utils.BroadcastReceiverUtils;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.PathGetter;
import com.epson.iprojection.common.utils.PjUtils;
import com.epson.iprojection.ui.activities.camera.permission.camera.PermissionCameraActivity;
import com.epson.iprojection.ui.activities.camera.views.activities.Activity_Camera;
import com.epson.iprojection.ui.activities.delivery.Activity_PresenDelivery;
import com.epson.iprojection.ui.activities.delivery.DeliveryFileIO;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.IPermissionForMirroringStateView;
import com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.PermissionForMirroringStateView;
import com.epson.iprojection.ui.activities.fileselect.FileSelectActivity;
import com.epson.iprojection.ui.activities.fileselect.ImageFileSelectPresenter;
import com.epson.iprojection.ui.activities.fileselect.PdfFileSelectPresenter;
import com.epson.iprojection.ui.activities.moderator.Activity_Moderator;
import com.epson.iprojection.ui.activities.moderator.Activity_Moderator_Thumbnail;
import com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect;
import com.epson.iprojection.ui.activities.pjselect.dialogs.QueryDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.activities.pjselect.settings.Activity_PjSettings;
import com.epson.iprojection.ui.activities.presen.Activity_Presen;
import com.epson.iprojection.ui.activities.remote.Activity_Remote;
import com.epson.iprojection.ui.activities.support.Activity_SupportEntrance;
import com.epson.iprojection.ui.activities.web.Activity_Web;
import com.epson.iprojection.ui.activities.web.WebUtils;
import com.epson.iprojection.ui.activities.whiteboard.WhiteboardActivity;
import com.epson.iprojection.ui.activities.whiteboard.WhiteboardUtils;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.activitystatus.ActivityKillStatus;
import com.epson.iprojection.ui.common.activitystatus.ContentsSelectStatus;
import com.epson.iprojection.ui.common.activitystatus.NextCallIntentHolder;
import com.epson.iprojection.ui.common.activitystatus.NextCallType;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eFirstTimeProjectionDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.dialogs.ConnectWhenOpenContentsDialog;
import com.epson.iprojection.ui.common.singleton.mirroring.CaptureIntentHolder;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.common.uiparts.OKDialog;
import com.epson.iprojection.ui.engine_wrapper.DisconReason;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class DrawerList implements AdapterView.OnItemClickListener, View.OnClickListener, IOnDialogEventListener {
    public static final int ID_CAMERA = 11;
    public static final int ID_CONNECT = 0;
    public static final int ID_CONTENTS = 7;
    public static final int ID_DELIVERY = 12;
    public static final int ID_MIRRORING = 6;
    public static final int ID_MIRRORING_DIVIDER = 5;
    public static final int ID_PDF = 9;
    public static final int ID_PHOTOS = 8;
    public static final int ID_PROJECTOR = 1;
    public static final int ID_REMOCON = 2;
    public static final int ID_SETTING = 14;
    public static final int ID_SPACE = 13;
    public static final int ID_SUPPORT = 15;
    public static final int ID_USERCTL = 3;
    public static final int ID_WEB = 10;
    public static final int ID_WHITEBOARD = 4;
    private final AppCompatActivity _activity;
    private InflaterListAdapter _adapter;
    private ConnectWhenOpenContentsDialog _connectDialog;
    private DrawerLayout _drawer;
    private ActionBarDrawerToggle _drawerToggle;
    private final IOnChangeMirroringSwitchListener _implOnChangeMirroringSwitch;
    private boolean _isAlreadyCreated;
    private ListView _listMenuView;
    private final IPermissionForMirroringStateView _permissionForMirroringStateManager;
    private final BroadcastReceiver _receiver;
    private int _topMargin = 0;
    private boolean _shouldCloseOnSelected = true;
    private boolean _isMirroringSwitchClickable = true;

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogNG(BaseDialog.ResultAction resultAction) {
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onDialogCanceled() {
    }

    public DrawerList(AppCompatActivity appCompatActivity, IOnChangeMirroringSwitchListener iOnChangeMirroringSwitchListener) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.epson.iprojection.ui.activities.drawermenu.DrawerList.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                try {
                    if (!Objects.equals(intent.getAction(), IntentDefine.INTENT_ACTION_MIRRORING_OFF) || DrawerList.this._adapter == null) {
                        return;
                    }
                    DrawerList.this._adapter.switchOffMirroring();
                } catch (Exception unused) {
                }
            }
        };
        this._receiver = broadcastReceiver;
        this._activity = appCompatActivity;
        this._implOnChangeMirroringSwitch = iOnChangeMirroringSwitchListener;
        this._permissionForMirroringStateManager = new PermissionForMirroringStateView(appCompatActivity, new ImplPermissionForMirroringCompletedCallback());
        BroadcastReceiverUtils.INSTANCE.registerMirroingOff(appCompatActivity, broadcastReceiver);
    }

    public void setTopMargin(int i) {
        this._topMargin = i;
    }

    public void refresh() {
        InflaterListAdapter inflaterListAdapter = this._adapter;
        if (inflaterListAdapter != null) {
            inflaterListAdapter.notifyDataSetChanged();
            if (this._activity instanceof Activity_Remote) {
                replace();
            }
            this._adapter.notifyDataSetChanged();
        }
    }

    public void create() {
        ListView listView = (ListView) this._activity.findViewById(R.id.list_drawermenu);
        this._listMenuView = listView;
        if (listView == null) {
            return;
        }
        listView.setOnItemClickListener(this);
        if (this._topMargin > 0) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this._listMenuView.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, this._topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            this._topMargin = 0;
        }
        ArrayList arrayList = new ArrayList();
        boolean isAvailableExternalStorage = PathGetter.getIns().isAvailableExternalStorage(this._activity);
        int i = ChromeOSUtils.INSTANCE.isChromeOS(this._activity) ? R.drawable.icon_main_mirroring_chromebook : R.drawable.icon_main_mirroring;
        this._adapter = new InflaterListAdapter(this._activity, arrayList, isAvailableExternalStorage, this, createDrawerDataSet());
        arrayList.add(new D_Inflater(this._activity.getString(R.string._Home_), R.drawable.icon_main_home));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._Projector_), -1));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._Remote_), R.drawable.icon_main_remocon));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._MultiDeviceProjection_), R.drawable.icon_main_multiproj));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._WhiteBoard_), R.drawable.icon_main_whiteboard));
        arrayList.add(new D_Inflater("", -1));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._DeviceScreen_), i));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._Contents_), -1));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._Photos_), R.drawable.icon_main_photo));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._PDF_), R.drawable.icon_main_doc));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._Web_), R.drawable.icon_main_web));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._Camera_), R.drawable.icon_main_camera));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._SharedImages_), R.drawable.icon_main_receiveimage));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._Utility_), -1));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._AppSettings_), R.drawable.icon_main_setting));
        arrayList.add(new D_Inflater(this._activity.getString(R.string._Support_), R.drawable.icon_main_support));
        this._listMenuView.setAdapter((ListAdapter) this._adapter);
        this._isAlreadyCreated = true;
    }

    public void destroy() {
        try {
            this._activity.unregisterReceiver(this._receiver);
        } catch (Exception unused) {
        }
    }

    public void enableDrawerToggleButton(Toolbar toolbar) {
        this._activity.setSupportActionBar(toolbar);
        this._drawer = (DrawerLayout) this._activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this._activity, this._drawer, toolbar, R.string._DrawerOpen_, R.string._DrawerClose_) { // from class: com.epson.iprojection.ui.activities.drawermenu.DrawerList.1
            @Override // androidx.appcompat.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerClosed(View view) {
            }

            @Override // androidx.appcompat.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerOpened(View view) {
            }

            @Override // androidx.appcompat.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerSlide(View view, float f) {
            }

            @Override // androidx.appcompat.app.ActionBarDrawerToggle, androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerStateChanged(int i) {
                DrawerList.this._adapter.updateMirroringSwitch();
            }
        };
        this._drawerToggle = actionBarDrawerToggle;
        this._drawer.addDrawerListener(actionBarDrawerToggle);
        this._drawerToggle.syncState();
        create();
    }

    public void setDrawerToggleButtonEnabled(boolean z) {
        ActionBarDrawerToggle actionBarDrawerToggle = this._drawerToggle;
        if (actionBarDrawerToggle != null) {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(z);
        }
    }

    private void replace() {
        ListView listView = (ListView) this._activity.findViewById(R.id.list_drawermenu);
        this._listMenuView = listView;
        if (listView == null) {
            return;
        }
        listView.setOnItemClickListener(this);
        this._drawer = (DrawerLayout) this._activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this._activity, this._drawer, R.string._DrawerOpen_, R.string._DrawerClose_) { // from class: com.epson.iprojection.ui.activities.drawermenu.DrawerList.2
        };
        this._drawerToggle = actionBarDrawerToggle;
        this._drawer.addDrawerListener(actionBarDrawerToggle);
        if (this._topMargin > 0) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this._listMenuView.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, this._topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            this._topMargin = 0;
        }
        this._listMenuView.setAdapter((ListAdapter) this._adapter);
    }

    private DrawerDataSet createDrawerDataSet() {
        return new DrawerDataSet(DrawerSelectStatus.getIns().get(), ContentsSelectStatus.getIns().get());
    }

    public boolean isAlreadyCreated() {
        return this._isAlreadyCreated;
    }

    public void changeDrawerStatus() {
        DrawerLayout drawerLayout = this._drawer;
        if (drawerLayout == null) {
            return;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this._drawer.closeDrawers();
        } else {
            this._drawer.openDrawer(GravityCompat.START);
        }
    }

    public void enable() {
        DrawerLayout drawerLayout = this._drawer;
        if (drawerLayout == null) {
            return;
        }
        drawerLayout.setDrawerLockMode(0);
    }

    public void disable() {
        DrawerLayout drawerLayout = this._drawer;
        if (drawerLayout == null) {
            return;
        }
        drawerLayout.setDrawerLockMode(1);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (askToConnect(i) || askToConnect(i)) {
            return;
        }
        this._shouldCloseOnSelected = true;
        callNextActivity(i);
        if (this._shouldCloseOnSelected) {
            this._drawer.closeDrawers();
        }
    }

    private boolean isRootActivity() {
        AppCompatActivity appCompatActivity = this._activity;
        if (appCompatActivity instanceof PjConnectableActivity) {
            return ((PjConnectableActivity) appCompatActivity).isRootActivity();
        }
        return false;
    }

    public void callNextActivity(int i) {
        Intent intent;
        NextCallType.getIns().set(eContentsType.None);
        eDrawerMenuItem edrawermenuitem = DrawerSelectStatus.getIns().get();
        if (edrawermenuitem == eDrawerMenuItem.UserCtl && i != 3 && i != 4 && i != 1 && i != 7 && i != 13 && i != 5 && i != 6) {
            DrawerSelectStatus.getIns().popForce();
            edrawermenuitem = DrawerSelectStatus.getIns().get();
            this._activity.finish();
        }
        Intent intent2 = null;
        switch (i) {
            case 0:
                if (edrawermenuitem != eDrawerMenuItem.Connect) {
                    intent2 = new Intent(this._activity, Activity_PjSelect.class);
                    break;
                } else {
                    return;
                }
            case 1:
            case 5:
            case 7:
            case 13:
            default:
                return;
            case 2:
                if (edrawermenuitem != eDrawerMenuItem.Remote) {
                    intent2 = new Intent(this._activity, Activity_Remote.class);
                    break;
                } else {
                    return;
                }
            case 3:
                if (edrawermenuitem != eDrawerMenuItem.UserCtl) {
                    if (Pj.getIns().isMpp()) {
                        AppCompatActivity appCompatActivity = this._activity;
                        if (!(appCompatActivity instanceof Activity_Moderator) && !(appCompatActivity instanceof Activity_Moderator_Thumbnail)) {
                            intent2 = new Intent(this._activity, Activity_Moderator.class);
                            break;
                        }
                    }
                } else {
                    return;
                }
                break;
            case 4:
                goWhiteBoard();
                break;
            case 6:
                if (MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
                    if (this._isMirroringSwitchClickable) {
                        MirroringEntrance.INSTANCE.finish(this._activity);
                        beUnclickableForAWhile();
                    }
                    this._shouldCloseOnSelected = false;
                } else if (!Pj.getIns().isConnected()) {
                    intent2 = new Intent(this._activity, NotAvailableMirroringActivity.class);
                    break;
                } else {
                    this._permissionForMirroringStateManager.start();
                    this._shouldCloseOnSelected = false;
                }
                this._adapter.updateMirroringSwitch();
                this._implOnChangeMirroringSwitch.onChangeMirroringSwitch();
                break;
            case 8:
                if (edrawermenuitem != eDrawerMenuItem.Photo) {
                    if (!PathGetter.getIns().isAvailableExternalStorage(this._activity)) {
                        ToastMgr.getIns().show(this._activity, ToastMgr.Type.InsertSDCard);
                        return;
                    }
                    intent = new Intent(this._activity, FileSelectActivity.class);
                    intent.putExtra(ImageFileSelectPresenter.TAG_IMAGE, "hoge");
                    killAllActivity(intent);
                    NextCallType.getIns().set(eContentsType.Photo);
                    if (ContentsSelectStatus.getIns().get() == eContentsType.Photo) {
                        ActivityKillStatus.getIns().setTillContents(true);
                        break;
                    }
                    intent2 = intent;
                    break;
                } else {
                    return;
                }
            case 9:
                if (edrawermenuitem != eDrawerMenuItem.Document) {
                    if (!PathGetter.getIns().isAvailableExternalStorage(this._activity)) {
                        ToastMgr.getIns().show(this._activity, ToastMgr.Type.InsertSDCard);
                        return;
                    }
                    intent = new Intent(this._activity, FileSelectActivity.class);
                    intent.putExtra(PdfFileSelectPresenter.TAG_PDF, "hoge");
                    killAllActivity(intent);
                    NextCallType.getIns().set(eContentsType.Pdf);
                    if (ContentsSelectStatus.getIns().get() == eContentsType.Pdf) {
                        ActivityKillStatus.getIns().setTillContents(true);
                        break;
                    }
                    intent2 = intent;
                    break;
                } else {
                    return;
                }
            case 10:
                if (edrawermenuitem != eDrawerMenuItem.Web) {
                    intent = new Intent(this._activity, Activity_Web.class);
                    killAllActivity(intent);
                    NextCallType.getIns().set(eContentsType.Web);
                    if (ContentsSelectStatus.getIns().get() == eContentsType.Web) {
                        ActivityKillStatus.getIns().setTillContents(true);
                        break;
                    }
                    intent2 = intent;
                    break;
                } else {
                    return;
                }
            case 11:
                if (edrawermenuitem != eDrawerMenuItem.Camera) {
                    intent = new Intent(this._activity, PermissionCameraActivity.class);
                    killAllActivity(intent);
                    NextCallType.getIns().set(eContentsType.Camera);
                    if (ContentsSelectStatus.getIns().get() == eContentsType.Camera) {
                        ActivityKillStatus.getIns().setTillContents(true);
                        break;
                    }
                    intent2 = intent;
                    break;
                } else {
                    return;
                }
            case 12:
                if (edrawermenuitem != eDrawerMenuItem.Delivery) {
                    String newestFileName = DeliveryFileIO.getIns().getNewestFileName(this._activity);
                    Intent intent3 = new Intent(this._activity, Activity_PresenDelivery.class);
                    intent3.putExtra(Activity_Presen.INTENT_TAG_PATH, newestFileName);
                    killAllActivity(intent3);
                    NextCallType.getIns().set(eContentsType.Delivery);
                    if (ContentsSelectStatus.getIns().get() != eContentsType.Delivery) {
                        intent2 = intent3;
                        break;
                    } else {
                        ActivityKillStatus.getIns().setTillContents(true);
                        break;
                    }
                } else {
                    return;
                }
            case 14:
                if (edrawermenuitem != eDrawerMenuItem.AppSettings) {
                    intent2 = new Intent(this._activity, Activity_PjSettings.class);
                    break;
                } else {
                    return;
                }
            case 15:
                if (edrawermenuitem != eDrawerMenuItem.Support) {
                    intent2 = new Intent(this._activity, Activity_SupportEntrance.class);
                    break;
                } else {
                    return;
                }
        }
        if (ActivityKillStatus.getIns().isKilling()) {
            AppCompatActivity appCompatActivity2 = this._activity;
            if (((appCompatActivity2 instanceof Activity_Presen) || (appCompatActivity2 instanceof Activity_Web) || (appCompatActivity2 instanceof Activity_Camera)) && NextCallType.getIns().get() == ContentsSelectStatus.getIns().get()) {
                ActivityKillStatus.getIns().stopKill();
                ActivityKillStatus.getIns().setTillContents(false);
            } else if (!isRootActivity()) {
                this._activity.finish();
            }
        }
        if (intent2 == null || NextCallIntentHolder.getIns().exists()) {
            return;
        }
        this._activity.startActivityForResult(intent2, CommonDefine.REQUEST_CODE_DRAWER);
    }

    public boolean askToConnect(int i) {
        if (!Pj.getIns().isConnected() && Pj.getIns().isRegistered()) {
            ConnectWhenOpenContentsDialog connectWhenOpenContentsDialog = this._connectDialog;
            if (connectWhenOpenContentsDialog == null || !connectWhenOpenContentsDialog.isShowing()) {
                eDrawerMenuItem edrawermenuitem = DrawerSelectStatus.getIns().get();
                switch (i) {
                    case 8:
                        if (edrawermenuitem == eDrawerMenuItem.Photo) {
                            return false;
                        }
                        this._connectDialog = new ConnectWhenOpenContentsDialog(this._activity, this, ConnectWhenOpenContentsDialog.ID_PHOTO);
                        return true;
                    case 9:
                        if (edrawermenuitem == eDrawerMenuItem.Document) {
                            return false;
                        }
                        this._connectDialog = new ConnectWhenOpenContentsDialog(this._activity, this, ConnectWhenOpenContentsDialog.ID_PDF);
                        return true;
                    case 10:
                        if (edrawermenuitem == eDrawerMenuItem.Web) {
                            return false;
                        }
                        this._connectDialog = new ConnectWhenOpenContentsDialog(this._activity, this, ConnectWhenOpenContentsDialog.ID_WEB);
                        return true;
                    case 11:
                        if (edrawermenuitem == eDrawerMenuItem.Camera) {
                            return false;
                        }
                        this._connectDialog = new ConnectWhenOpenContentsDialog(this._activity, this, ConnectWhenOpenContentsDialog.ID_CAMERA);
                        return true;
                    case 12:
                        if (edrawermenuitem == eDrawerMenuItem.Delivery) {
                            return false;
                        }
                        this._connectDialog = new ConnectWhenOpenContentsDialog(this._activity, this, ConnectWhenOpenContentsDialog.ID_DELIVER);
                        return true;
                    default:
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void beUnclickableForAWhile() {
        this._isMirroringSwitchClickable = false;
        new Handler().postDelayed(new Runnable() { // from class: com.epson.iprojection.ui.activities.drawermenu.DrawerList$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DrawerList.this.m89xfcff4716();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$beUnclickableForAWhile$0$com-epson-iprojection-ui-activities-drawermenu-DrawerList  reason: not valid java name */
    public /* synthetic */ void m89xfcff4716() {
        this._isMirroringSwitchClickable = true;
    }

    private void killAllActivity(Intent intent) {
        if (isRootActivity()) {
            return;
        }
        ActivityKillStatus.getIns().startKill();
        NextCallIntentHolder.getIns().set(intent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (Pj.getIns().isConnected()) {
            QueryDialog queryDialog = new QueryDialog(this._activity, QueryDialog.MessageType.Disconnect, this, BaseDialog.ResultAction.DISCONNECT, null);
            queryDialog.create(this._activity);
            queryDialog.show();
        } else if (Pj.getIns().isRegistedPjs5Over()) {
            AppCompatActivity appCompatActivity = this._activity;
            new OKDialog(appCompatActivity, appCompatActivity.getString(R.string._Exceeded4PjConnect_));
        } else if (Pj.getIns().isRegistedPjs5Over()) {
            AppCompatActivity appCompatActivity2 = this._activity;
            new OKDialog(appCompatActivity2, appCompatActivity2.getString(R.string._Exceeded4PjConnect_));
        } else {
            startHomeActivityToConnect();
        }
        close();
    }

    private void startHomeActivityToConnect() {
        Intent intent = new Intent(this._activity, Activity_PjSelect.class);
        intent.putExtra(Activity_PjSelect.TAG_KILL_SELF_WHEN_CONNECTED, "hoge");
        this._activity.startActivity(intent);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogOK(String str, BaseDialog.ResultAction resultAction) {
        if (Pj.getIns().isConnected()) {
            Pj.getIns().disconnect(DisconReason.UserAction);
        }
    }

    public boolean isOpend() {
        DrawerLayout drawerLayout = this._drawer;
        if (drawerLayout == null) {
            return false;
        }
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void close() {
        DrawerLayout drawerLayout = this._drawer;
        if (drawerLayout == null) {
            return;
        }
        drawerLayout.closeDrawers();
    }

    private void goWhiteBoard() {
        if (PjUtils.isAvailableWhiteboard()) {
            Uri parse = Uri.parse(WebUtils.URL_PREF_HTTP + NetUtils.cvtIPAddr(Pj.getIns().getNowConnectingPJList().get(0).getPjInfo().IPAddr) + "/wb");
            if (WhiteboardUtils.Companion.shouldOpenInMyApp()) {
                this._activity.startActivity(new Intent(this._activity, WhiteboardActivity.class));
                return;
            }
            this._activity.startActivity(new Intent("android.intent.action.VIEW", parse));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.drawermenu.DrawerList$4  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType;

        static {
            int[] iArr = new int[eContentsType.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType = iArr;
            try {
                iArr[eContentsType.Photo.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[eContentsType.Pdf.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[eContentsType.Web.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[eContentsType.Camera.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[eContentsType.Delivery.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public void startNextActivity(eContentsType econtentstype) {
        int i;
        int i2 = AnonymousClass4.$SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[econtentstype.ordinal()];
        if (i2 == 1) {
            i = 8;
        } else if (i2 == 2) {
            i = 9;
        } else if (i2 == 3) {
            i = 10;
        } else if (i2 == 4) {
            i = 11;
        } else if (i2 != 5) {
            return;
        } else {
            i = 12;
        }
        if (askToConnect(i)) {
            return;
        }
        callNextActivity(i);
    }

    public static eContentsType convertType(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2139330642:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_PDF)) {
                    c = 0;
                    break;
                }
                break;
            case -2139323888:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_WEB)) {
                    c = 1;
                    break;
                }
                break;
            case -154122551:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_CAMERA)) {
                    c = 2;
                    break;
                }
                break;
            case 518388193:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_DELIVER)) {
                    c = 3;
                    break;
                }
                break;
            case 1097128073:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_NOTHING)) {
                    c = 4;
                    break;
                }
                break;
            case 1392718318:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_PHOTO)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return eContentsType.Pdf;
            case 1:
                return eContentsType.Web;
            case 2:
                return eContentsType.Camera;
            case 3:
                return eContentsType.Delivery;
            case 4:
                return eContentsType.None;
            case 5:
                return eContentsType.Photo;
            default:
                return null;
        }
    }

    public static int convertTypeToInt(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2139330642:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_PDF)) {
                    c = 0;
                    break;
                }
                break;
            case -2139323888:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_WEB)) {
                    c = 1;
                    break;
                }
                break;
            case -154122551:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_CAMERA)) {
                    c = 2;
                    break;
                }
                break;
            case 518388193:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_DELIVER)) {
                    c = 3;
                    break;
                }
                break;
            case 530566527:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_MIRRORING)) {
                    c = 4;
                    break;
                }
                break;
            case 1392718318:
                if (str.equals(ConnectWhenOpenContentsDialog.ID_PHOTO)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return 9;
            case 1:
                return 10;
            case 2:
                return 11;
            case 3:
                return 12;
            case 4:
                return 6;
            case 5:
                return 8;
            default:
                return 0;
        }
    }

    /* loaded from: classes.dex */
    private class ImplPermissionForMirroringCompletedCallback implements Contract.IPermissionForMirroringCompletedCallback {
        private ImplPermissionForMirroringCompletedCallback() {
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IPermissionForMirroringCompletedCallback
        public void onFinished(boolean z) {
            if (z && DrawerList.this._isMirroringSwitchClickable) {
                startMirroring();
                DrawerList.this.beUnclickableForAWhile();
            }
        }

        @Override // com.epson.iprojection.ui.activities.drawermenu.prepare_mirroring.Contract.IPermissionForMirroringCompletedCallback
        public void onActivityChanged() {
            DrawerList.this._drawer.closeDrawers();
        }

        private void startMirroring() {
            Intent captureIntent = CaptureIntentHolder.INSTANCE.getCaptureIntent();
            if (!Pj.getIns().isConnected() || captureIntent == null) {
                return;
            }
            MirroringEntrance.INSTANCE.start(DrawerList.this._activity, captureIntent);
            Analytics.getIns().setFirstTimeProjectionEvent(eFirstTimeProjectionDimension.mirroring);
            Analytics.getIns().sendEvent(eCustomEvent.FIRST_TIME_PROJECTION);
        }
    }
}
