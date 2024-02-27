package com.epson.iprojection.ui.engine_wrapper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebViewDatabase;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.AppInfoUtils;
import com.epson.iprojection.common.utils.AudioUtils;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.engine.Engine;
import com.epson.iprojection.engine.common.D_AddFixedSearchInfo;
import com.epson.iprojection.engine.common.D_ClientResolutionInfo;
import com.epson.iprojection.engine.common.D_ConnectPjInfo;
import com.epson.iprojection.engine.common.D_DeliveryError;
import com.epson.iprojection.engine.common.D_DeliveryInfo;
import com.epson.iprojection.engine.common.D_ImageProcTime;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.engine.common.D_ThumbnailError;
import com.epson.iprojection.engine.common.D_ThumbnailInfo;
import com.epson.iprojection.engine.common.OnFindPjListener;
import com.epson.iprojection.engine.common.OnPjEventListener;
import com.epson.iprojection.engine.common.ProtocolType;
import com.epson.iprojection.engine.common.eBandWidth;
import com.epson.iprojection.engine.common.eSrcType;
import com.epson.iprojection.service.mirroring.MirroringNotification;
import com.epson.iprojection.service.webrtc.WebRTCEntrance;
import com.epson.iprojection.service.webrtc.utils.WebRTCUtils;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import com.epson.iprojection.ui.activities.delivery.DeliveryFileIO;
import com.epson.iprojection.ui.activities.pjselect.ConnectConfig;
import com.epson.iprojection.ui.activities.pjselect.D_ListItem;
import com.epson.iprojection.ui.activities.pjselect.WifiConnector;
import com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.QueryDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.SpoilerDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.activities.pjselect.permission.audio.PermissionAudioPresenter;
import com.epson.iprojection.ui.activities.presen.Aspect;
import com.epson.iprojection.ui.activities.remote.RemotePasswordPrefUtils;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import com.epson.iprojection.ui.common.RenderedImageFile;
import com.epson.iprojection.ui.common.ScaledImage;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activitystatus.ContentsSelectStatus;
import com.epson.iprojection.ui.common.activitystatus.PresenAspect;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eAudioTransferDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eConnectAsModeratorDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eFirstTimeProjectionDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eOpenedContentsDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eSearchRouteDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.common.exception.UnknownSsidException;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import com.epson.iprojection.ui.common.singleton.WifiChanger;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.StateMachine;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnMinConnectListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnScreenEventListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnSearchThreadEndListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnShutdownEventListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener;
import com.epson.iprojection.ui.engine_wrapper.utils.ErrCodeDemuxer;
import com.epson.iprojection.ui.engine_wrapper.utils.MsgGetterUtils;
import com.epson.iprojection.ui.engine_wrapper.utils.PjLogUtils;
import com.epson.iprojection.ui.engine_wrapper.utils.UserListUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class Pj extends StateMachine implements IPj, OnPjEventListener, IOnSearchThreadEndListener, IOnScreenEventListener, IOnShutdownEventListener, IPjManualSearchResultListener {
    public static final String ESCVP_COMMAND_ENTER = "KEY 49";
    public static final String ESCVP_COMMAND_ESC = "KEY 3D";
    public static final String ESCVP_COMMAND_MENU = "KEY 3C";
    public static final String ESCVP_COMMAND_MVPT_DOWN = "KEY 59";
    public static final String ESCVP_COMMAND_MVPT_LEFT = "KEY 5A";
    public static final String ESCVP_COMMAND_MVPT_LEFTDOWN = "KEY 5F";
    public static final String ESCVP_COMMAND_MVPT_LEFTUP = "KEY 5D";
    public static final String ESCVP_COMMAND_MVPT_RIGHT = "KEY 5B";
    public static final String ESCVP_COMMAND_MVPT_RIGHTDOWN = "KEY 5E";
    public static final String ESCVP_COMMAND_MVPT_RIGHTUP = "KEY 5C";
    public static final String ESCVP_COMMAND_MVPT_UP = "KEY 58";
    public static final String ESCVP_COMMAND_POINTER = "KEY 50";
    public static final String ESCVP_COMMAND_POWER = "KEY 01";
    public static final String ESCVP_COMMAND_SCOMPORT_WIRED = "SCOMPORT 01";
    public static final String ESCVP_COMMAND_SCOMPORT_WIRELESS = "SCOMPORT 02";
    public static final String ESCVP_COMMAND_SPOWEROFF = "SPOWER OFF";
    public static final String ESCVP_COMMAND_SPOWERON = "SPOWER ON";
    public static final String ESCVP_COMMAND_ZOOM_DOWN = "KEY 29";
    public static final String ESCVP_COMMAND_ZOOM_UP = "KEY 28";
    public static final String TAG_SSID_SAVE_PREF = "ssid_save_pref_tag";
    private PjCyclicSearchThreadForRegisted _cyclicSearchForRegisted;
    private Long _myUniqueId;
    private OneManualSearcher _oneManualSearcher;
    private int _reconnectCounter;
    private ConnectPjInfo _reconnectPjInfo;
    private static final Map<Integer, Boolean> _hasModeratorPasswordMap = new HashMap();
    private static final LinkedList<Byte> _audioBuffer = new LinkedList<>();
    private static final Pj _inst = new Pj();
    private Activity _activity = null;
    private final OperateInfoStacker _operateInfoStacker = new OperateInfoStacker();
    private Engine _engine = null;
    private ConnectionControler _conCtrlr = null;
    private Resolution _res = null;
    private PjFinder _pjFinder = null;
    private ImageSender _imgSender = null;
    private ScreenStatusWatcher _screenWatcher = null;
    private ShutdownWatcher _shutdownWatcher = null;
    private final LockedPjInfo _lockedPjInfo = new LockedPjInfo();
    private final ArrayList<ConnectPjInfo> _aSelectPjInfo = new ArrayList<>();
    private Handler _handler = null;
    private boolean _bSelfProjection = false;
    private final ArrayList<ConnectPjInfo> _aSelectPjFromHistory = new ArrayList<>();
    private boolean _needManualSearchBeforeConnect = false;
    private boolean _bTryMirroringConnect = false;
    private ArrayList<D_MppUserInfo> _mppUserList = null;
    private ArrayList<D_MppLayoutInfo> _mppLayout = null;
    private IOnConnectListener.MppControlMode _mppControlMode = IOnConnectListener.MppControlMode.NoUse;
    private boolean _isLinkageDataSearching = false;
    private boolean _isNeverConnected = true;
    private D_MppUserInfo _moderatorUserInfo = null;
    private boolean _isMppLockedByModerator = false;
    private boolean _isMppLockedByMe = false;
    private boolean _isNoInterrupt = false;
    private boolean _isWhiteboardConnect = false;
    private boolean _shouldSelectProjection = true;
    private boolean _isTryModeratorWhenConnect = false;
    private boolean _isCallSetMppControlMode = false;
    private final int _callbackIntervalImageProcTime = 0;
    private WifiConnector _wifiConnector = null;
    private boolean _isConnectingByLikageData = false;
    private boolean _isWifiChanged = false;
    private ArrayList<ConnectPjInfo> _aRegisteredPjInf = new ArrayList<>();
    private DisconReason _disconReason = DisconReason.NoSet;
    private int _projectionMode = 1;
    private boolean _isPassiveDisconnectionDuringConnecting = false;
    private final Runnable _runnableDisconnect = new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.Pj$$ExternalSyntheticLambda1
        {
            Pj.this = this;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Pj.this.m207lambda$new$0$comepsoniprojectionuiengine_wrapperPj();
        }
    };
    private boolean _canProjectMySelf = true;
    private Handler _handlerEnablerProjectionMySelf = new Handler(Looper.getMainLooper());
    private Runnable _enablerProjectionMySelf = new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.Pj$$ExternalSyntheticLambda2
        {
            Pj.this = this;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Pj.this.m208lambda$new$4$comepsoniprojectionuiengine_wrapperPj();
        }
    };

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onEndSearchPj() {
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
    }

    public void initialize(Context context) {
        Lg.i("********** Pj Initialize **********");
        if (this._connectState != StateMachine.ConnectState.NotInitYet && this._connectState != StateMachine.ConnectState.NeedRestart) {
            Lg.i("初期化不要の状態");
        } else if (context.getApplicationContext() == null) {
            Lg.w("アプリケーションコンテキストがNULL");
        } else {
            this._context = context;
            this._handler = new Handler(Looper.getMainLooper());
            this._engine = Engine.Initialize(context.getApplicationContext(), PjLogUtils.INSTANCE.shouldSendToTestServer(context), AppInfoUtils.Companion.extractFrontNumber(AppInfoUtils.Companion.getAppVersion(context)));
            this._isNeverConnected = true;
            this._conCtrlr = new ConnectionControler(context);
            this._res = new Resolution();
            this._imgSender = new ImageSender(this._engine, this._res, this._context);
            this._screenWatcher = new ScreenStatusWatcher(context, this);
            this._shutdownWatcher = new ShutdownWatcher(context, this);
            this._aRegisteredPjInf = RegisteredPjDataForFile.read(this._context).get(this._context);
            setRegisterState(getRegisterState());
            WebRTCEntrance.INSTANCE.setContext(context.getApplicationContext());
            super.setConnectState(StateMachine.ConnectState.Default);
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void finalizeEngine() {
        try {
            if (this._pjFinder != null) {
                this._pjFinder = null;
            }
            ImageSender imageSender = this._imgSender;
            if (imageSender != null) {
                imageSender.delete();
                this._imgSender = null;
            }
            Engine engine = this._engine;
            if (engine != null) {
                engine.Destroy();
                this._engine = null;
            }
        } catch (Exception e) {
            Lg.e("Fail to finalizeEngine.  " + e);
        }
        this._context = null;
        super.setConnectState(StateMachine.ConnectState.NotInitYet);
    }

    public Context getContext() {
        return this._context;
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onConnectRet(int i, int i2, int i3) {
        boolean z = this._isWhiteboardConnect;
        boolean z2 = false;
        setWhiteboardConnect(false);
        Lg.i("← onConnectRet() pjID:" + i2 + ", | " + MsgGetterUtils.getErrMsg(i3));
        if (this._connectState == StateMachine.ConnectState.NotInitYet || this._connectState == StateMachine.ConnectState.NeedRestart) {
            return;
        }
        if (i != 1) {
            if (i == 3) {
                IOnConnectListener.DisconedReason reason = ErrCodeDemuxer.getReason(i3);
                int nowConnectingPjNum = this._lockedPjInfo.getNowConnectingPjNum();
                if (!this._lockedPjInfo.isNowMpp() && !this._engine.isMpp() && 1 < nowConnectingPjNum) {
                    for (int i4 = 0; i4 < nowConnectingPjNum; i4++) {
                        if (this._lockedPjInfo.getNowConnectingPjID(i4) == i2) {
                            D_PjInfo nowConnectingPjInfo = this._lockedPjInfo.getNowConnectingPjInfo(i4);
                            this._lockedPjInfo.removeNowConnectingPjID(i2);
                            this._conCtrlr.onErrorDisconnect(nowConnectingPjInfo);
                            PjFinder pjFinder = this._pjFinder;
                            if (pjFinder != null) {
                                pjFinder.onDisconnected(i2);
                            }
                            this._engine.Destroy(i2);
                            return;
                        }
                    }
                }
                Lg.d("* 投射中に異常を検出しました。自主的に切断します *");
                if (reason == IOnConnectListener.DisconedReason.Interrupt) {
                    disconnect(DisconReason.Interrupt, true);
                } else if (reason == IOnConnectListener.DisconedReason.DisconnOther) {
                    disconnect(DisconReason.DisconnOther, true);
                } else if (i3 == 0) {
                    disconnect(DisconReason.Error, false);
                } else {
                    disconnect(DisconReason.Error, true);
                }
                onDisconnect(i2, reason);
            } else if (i == 5) {
                if (i3 == 0) {
                    Lg.d("* 切断成功 *");
                } else {
                    Lg.w("* 切断失敗 *");
                }
                onDisconnect(i2, IOnConnectListener.DisconedReason.Default);
            } else {
                Lg.e("* 想定外の戻り値 *");
            }
        } else if (this._connectState != StateMachine.ConnectState.TryConnecting && this._connectState != StateMachine.ConnectState.NowConnecting) {
            Lg.w("接続中ではないのにonConnectRetを受信した");
            return;
        } else if (i3 == 0) {
            Lg.d("* 接続成功 *");
            onConnect(false, z);
        } else if (i3 == 2) {
            Lg.d("* 接続成功 *(選択UI表示)");
            onConnect(true, z);
        } else {
            Lg.w("* 接続失敗 * : " + i3);
            if (i3 == -419 || i3 == -405) {
                this._conCtrlr.onConnectFail(i2, IOnConnectListener.FailReason.NpVersionError);
                this._conCtrlr.onConnectFailForGA(IOnConnectListener.FailReason.NpVersionError);
                disconnect(DisconReason.NpVersionError);
                Lg.e("ERROR_APPLICATION_VERSION");
            } else if (i3 != -402) {
                if (i3 == -416) {
                    this._conCtrlr.onConnectFail(i2, IOnConnectListener.FailReason.DiffCombiPj);
                    this._conCtrlr.onConnectFailForGA(IOnConnectListener.FailReason.DiffCombiPj);
                    disconnect(DisconReason.DiffCombiPj);
                    Lg.e("ENGINE_ERROR_MIRRORING");
                } else if (i3 == -415) {
                    this._conCtrlr.onConnectFail(i2, IOnConnectListener.FailReason.MppMaxUser);
                    this._conCtrlr.onConnectFailForGA(IOnConnectListener.FailReason.MppMaxUser);
                    disconnect(DisconReason.MppMaxUser);
                    Lg.e("ENGINE_ERROR_CONNECT_MAX");
                } else {
                    if (this._needManualSearchBeforeConnect) {
                        if (this._bTryMirroringConnect && i3 != -421) {
                            this._conCtrlr.onConnectFail(i2, IOnConnectListener.FailReason.DiffCombiPj);
                            this._conCtrlr.onConnectFailForGA(IOnConnectListener.FailReason.DiffCombiPj);
                            disconnect(DisconReason.DiffCombiPj);
                        } else {
                            this._conCtrlr.onConnectFail(i2, getFailReason());
                            this._conCtrlr.onConnectFailForGA(getFailReason());
                            disconnect(DisconReason.ConnectFailed);
                        }
                    } else {
                        if (!shouldReconnect()) {
                            this._conCtrlr.onConnectFail(i2, getFailReason());
                            this._conCtrlr.onConnectFailForGA(getFailReason());
                        } else {
                            this._conCtrlr.onConnectFailForGA(IOnConnectListener.FailReason.Default);
                        }
                        disconnect(DisconReason.ConnectFailed);
                        if (shouldReconnect()) {
                            Lg.d("shouldReconnect");
                            super.setConnectState(StateMachine.ConnectState.Default);
                            ArrayList<ConnectPjInfo> arrayList = new ArrayList<>();
                            arrayList.add(this._reconnectPjInfo);
                            this._reconnectPjInfo = null;
                            this._reconnectCounter++;
                            setWhiteboardConnect(z);
                            connect(arrayList);
                            z2 = true;
                        } else {
                            Lg.d("not shouldReconnect");
                            resetReconnect();
                        }
                    }
                    Lg.e("ENGINE_ERROR_DEFAULT");
                }
            } else if (this._conCtrlr.hasAgainKeyword()) {
                return;
            } else {
                this._conCtrlr.onConnectFail(i2, IOnConnectListener.FailReason.IlligalKeyword);
                this._conCtrlr.onConnectFailForGA(IOnConnectListener.FailReason.IlligalKeyword);
                disconnect(DisconReason.IllegalKeyword);
                Lg.e("ERROR_ILLEGAL_PROJECTORKEYWORD");
            }
            if (!z2) {
                onDisconnect(i2, IOnConnectListener.DisconedReason.FailedToConnect);
            }
        }
        this._shouldSelectProjection = true;
    }

    private IOnConnectListener.FailReason getFailReason() {
        Iterator<ConnectPjInfo> it = this._aRegisteredPjInf.iterator();
        boolean z = false;
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (next.getStatus() == ConnectPjInfo.eStatus.unavailable) {
                return IOnConnectListener.FailReason.IncludeUnavailable;
            }
            if (next.getPjInfo().isBusy()) {
                return IOnConnectListener.FailReason.IncludeUnavailable;
            }
            if (next.getStatus() == ConnectPjInfo.eStatus.standby) {
                z = true;
            }
        }
        if (z) {
            return IOnConnectListener.FailReason.Standby;
        }
        return IOnConnectListener.FailReason.Default;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnSearchThreadEndListener
    public void onSearchThreadEnd() {
        Lg.d("onSearchThreadEnd");
        OneManualSearcher oneManualSearcher = this._oneManualSearcher;
        if (oneManualSearcher != null) {
            oneManualSearcher.onSearchEnd();
        }
        if (this._operateInfoStacker.isConnectInfo()) {
            Lg.d("スタックしていた情報を使って接続します");
            this._conCtrlr.connect(this._operateInfoStacker.getConnectPjArray());
        } else if (this._operateInfoStacker.isDisconnectInfo()) {
            Lg.d("スタックしていた情報を使って切断します");
            this._conCtrlr.disconnect(this._operateInfoStacker.getDisconReason());
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnScreenEventListener
    public void onScreenOff() {
        if (isConnected()) {
            this._imgSender.notifyToSleep();
            this._handler.postDelayed(this._runnableDisconnect, 60000L);
            this._imgSender.sendImageForWarningSleep(this._context);
            this._imgSender.lock();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnScreenEventListener
    public void onScreenOn() {
        try {
            this._imgSender.notifyToWakeUp();
            this._imgSender.unlock();
            this._handler.removeCallbacks(this._runnableDisconnect);
            if (isConnected()) {
                this._imgSender.resendImage();
            }
        } catch (Exception unused) {
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IOnShutdownEventListener
    public void onShutdown() {
        disconnect(DisconReason.ScreenOff);
    }

    /* renamed from: lambda$new$0$com-epson-iprojection-ui-engine_wrapper-Pj */
    public /* synthetic */ void m207lambda$new$0$comepsoniprojectionuiengine_wrapperPj() {
        if (isConnected()) {
            disconnect(DisconReason.ScreenOff);
        }
    }

    public void startSearch(IPjSearchStatusListener iPjSearchStatusListener) {
        Lg.d("検索を開始します");
        setupPjFinder(iPjSearchStatusListener);
        setNotInterruptUI();
        if (isConnected()) {
            return;
        }
        this._pjFinder.startSearch();
    }

    public void endSearch() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.endSearch();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public boolean autoSearch(OnFindPjListener onFindPjListener) {
        int StartSearchPj = this._engine.StartSearchPj(0, onFindPjListener);
        if (StartSearchPj != 0) {
            Lg.e("autoSearch失敗 理由=" + MsgGetterUtils.getErrMsg(StartSearchPj));
            return false;
        }
        return true;
    }

    public boolean endSearchDirect() {
        int EndSearchPj = this._engine.EndSearchPj();
        if (EndSearchPj != 0) {
            Lg.e("endSearch失敗 理由=" + MsgGetterUtils.getErrMsg(EndSearchPj));
            return false;
        }
        return true;
    }

    public int manualSearch(IPjManualSearchResultListener iPjManualSearchResultListener, String str) {
        return manualSearch(iPjManualSearchResultListener, str, true);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int manualSearch(IPjManualSearchResultListener iPjManualSearchResultListener, List<String> list, boolean z) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            return pjFinder.manualSearch(iPjManualSearchResultListener, list, z);
        }
        Lg.e("_pjFinder is null!!");
        return -1;
    }

    public int manualSearchNoDialog(IPjManualSearchResultListener iPjManualSearchResultListener, String str) {
        return manualSearch(iPjManualSearchResultListener, str, false);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int manualSearch(IPjManualSearchResultListener iPjManualSearchResultListener, String str, boolean z) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null) {
            return -1;
        }
        int manualSearch = pjFinder.manualSearch(iPjManualSearchResultListener, str, false);
        if (z && manualSearch == 0) {
            this._conCtrlr.showSearchingDialog();
        }
        return manualSearch;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void stopSearch() {
        this._engine.EndSearchPj();
    }

    public void endManualSearch() {
        this._conCtrlr.clearSearchingDialog();
    }

    public void cancelSearch() {
        Lg.d("cancelSearch");
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.pauseSearch();
        }
    }

    public boolean addManualSearchPj(IPjManualSearchResultListener iPjManualSearchResultListener, D_PjInfo d_PjInfo) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            return pjFinder.addManualSearchPj(iPjManualSearchResultListener, d_PjInfo);
        }
        return false;
    }

    public void deleteManualSearchPj(D_PjInfo d_PjInfo) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.deleteManualSearchPj(d_PjInfo);
        }
    }

    public void clearManualSearchPj() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.clearManualSearchPj();
        }
    }

    public boolean isManualSearchPj(D_PjInfo d_PjInfo) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            return pjFinder.isManualSearchPj(d_PjInfo);
        }
        return false;
    }

    public boolean hasManualSearchPj() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            return pjFinder.hasManualSearchPj();
        }
        return false;
    }

    public void startManualSearchPj(IPjManualSearchResultListener iPjManualSearchResultListener) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null || pjFinder.startManualSearchPj(iPjManualSearchResultListener) != 0) {
            return;
        }
        this._conCtrlr.showSearchingDialog();
    }

    public void onSearchInterrupted() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.onSearchInterrupted();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void setupPjFinder(IPjSearchStatusListener iPjSearchStatusListener) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null) {
            this._pjFinder = new PjFinder(this._engine, iPjSearchStatusListener, this, this._lockedPjInfo, this._aSelectPjInfo, this._aSelectPjFromHistory, this._isLinkageDataSearching, this._context);
        } else {
            pjFinder.setPjSearchStatusListener(iPjSearchStatusListener);
        }
    }

    public void startUpdatePjStatusOfRegisted() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.startUpdatePjStatusOfRegisted();
        }
    }

    public void stopUpdatePjStatusOfRegisted() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.stopUpdatePjStatusOfRegisted();
        }
    }

    public void onClickConnectEventButton() {
        onClickConnectEventButton(false, false);
    }

    public void onClickConnectEventButton(boolean z, boolean z2) {
        if (this._pjFinder == null) {
            return;
        }
        movePjListFromRegisteredToSelect();
        if (isLinkageDataSearching() || this._pjFinder.getSelectPjNum() != 0) {
            this._pjFinder.mergeSelectPjList();
            if (!z2 && isRegistered()) {
                OneManualSearcher oneManualSearcher = new OneManualSearcher(this._pjFinder.getSelectPjList(), z, getIns().stopCyclicSearchForRegisted());
                this._oneManualSearcher = oneManualSearcher;
                oneManualSearcher.search();
                return;
            }
            this._needManualSearchBeforeConnect = false;
            this._conCtrlr.tryConnect(new ArrayList<>(this._pjFinder.getSelectPjList()), z);
        }
    }

    /* loaded from: classes.dex */
    public class OneManualSearcher implements IPjManualSearchResultListener {
        private final boolean _forceThrough;
        private int _foundN;
        private boolean _isCalledEndSearch;
        private final boolean _isDoneEndSearchBefore;
        private final ArrayList<ConnectPjInfo> _pjList;

        public OneManualSearcher(ArrayList<ConnectPjInfo> arrayList, boolean z, boolean z2) {
            Pj.this = r1;
            this._pjList = arrayList;
            this._forceThrough = z;
            this._isDoneEndSearchBefore = z2;
        }

        public void search() {
            Pj.this._conCtrlr.showSearchingDialog();
            new Thread(new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.Pj$OneManualSearcher$$ExternalSyntheticLambda0
                {
                    Pj.OneManualSearcher.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    Pj.OneManualSearcher.this.m211xecba41cf();
                }
            }).start();
        }

        /* renamed from: lambda$search$0$com-epson-iprojection-ui-engine_wrapper-Pj$OneManualSearcher */
        public /* synthetic */ void m211xecba41cf() {
            if (this._isDoneEndSearchBefore) {
                int i = 50;
                while (!this._isCalledEndSearch && i > 0) {
                    i--;
                    Sleeper.sleep(100L);
                }
            }
            ArrayList arrayList = new ArrayList();
            Iterator<ConnectPjInfo> it = this._pjList.iterator();
            while (it.hasNext()) {
                arrayList.add(NetUtils.cvtIPAddr(it.next().getPjInfo().IPAddr));
            }
            Pj.this._pjFinder.manualSearch((IPjManualSearchResultListener) this, (List<String>) arrayList, false);
        }

        @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
        public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
            merge(d_PjInfo);
            this._foundN++;
        }

        @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
        public void onEndSearchPj() {
            if (this._foundN != this._pjList.size()) {
                Lg.e("全ての指定検索は受け取れませんでした。[" + this._foundN + "/" + this._pjList.size() + "]");
            }
            Pj.this._conCtrlr.clearSearchingDialog();
            Pj.getIns().onClickConnectEventButton(this._forceThrough, true);
        }

        public void onSearchEnd() {
            this._isCalledEndSearch = true;
        }

        private void merge(D_PjInfo d_PjInfo) {
            Iterator<ConnectPjInfo> it = this._pjList.iterator();
            while (it.hasNext()) {
                ConnectPjInfo next = it.next();
                if (Arrays.equals(next.getPjInfo().UniqInfo, d_PjInfo.UniqInfo)) {
                    next.setPjInfo(d_PjInfo);
                    return;
                }
            }
        }
    }

    public void onClickRegisterEventButton() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null) {
            return;
        }
        pjFinder.mergeSelectPjListNoMirroring();
        this._conCtrlr.tryRegister(new ArrayList<>(this._pjFinder.getSelectPjList()));
    }

    public void movePjListFromRegisteredToSelect() {
        if (this._pjFinder != null && getState() == StateMachine.State.Registered) {
            this._pjFinder.removeAllSelectPJ();
            Iterator<ConnectPjInfo> it = this._aRegisteredPjInf.iterator();
            while (it.hasNext()) {
                this._pjFinder.addSelectPj(it.next());
            }
        }
    }

    public void onClickConnectEventButtonFromHistory() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null || pjFinder.getConnPjNumFromHistory() == 0) {
            return;
        }
        this._needManualSearchBeforeConnect = true;
        this._conCtrlr.tryConnect(new ArrayList<>(this._pjFinder.getConnPjFromHistory()), false);
    }

    public boolean hasUniqueInfo(ConnectPjInfo connectPjInfo) {
        return !Arrays.equals(connectPjInfo.getPjInfo().UniqInfo, D_PjInfo.e_null_unique_info);
    }

    private void holdConnectInfo(ArrayList<ConnectPjInfo> arrayList) {
        Lg.d("検索中なので接続を保留にします");
        this._operateInfoStacker.setConnectPjInfo(arrayList);
        this._conCtrlr.showConnectingDialog();
    }

    private void holdDisconnectInfo(DisconReason disconReason) {
        Lg.d("検索中なので切断を保留にします");
        this._operateInfoStacker.setDisconInfo(disconReason);
        this._conCtrlr.showDisconnectingDialog(disconReason);
    }

    public boolean selectConnPJ(ConnectPjInfo connectPjInfo) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null) {
            return false;
        }
        return pjFinder.addSelectPj(connectPjInfo);
    }

    public void removeConnPJ(ConnectPjInfo connectPjInfo) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.removeSelectPj(connectPjInfo);
        }
    }

    public void removeAllConnPJ() {
        PjFinder pjFinder;
        if (this._connectState == StateMachine.ConnectState.NowConnecting || (pjFinder = this._pjFinder) == null) {
            return;
        }
        pjFinder.removeAllSelectPJ();
    }

    public boolean addConnPjFromHistory(ConnectPjInfo connectPjInfo) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null) {
            return false;
        }
        return pjFinder.addConnPjFromHistory(connectPjInfo);
    }

    public ArrayList<ConnectPjInfo> getConnPjFromHistory() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null) {
            return new ArrayList<>();
        }
        return pjFinder.getConnPjFromHistory();
    }

    public void removeConnPjFromHistory(ConnectPjInfo connectPjInfo) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.removeConnPjFromHistory(connectPjInfo);
        }
    }

    public void clearConnPjFromHistory() {
        PjFinder pjFinder;
        if (this._connectState == StateMachine.ConnectState.NowConnecting || (pjFinder = this._pjFinder) == null) {
            return;
        }
        pjFinder.clearConnPjFromHistory();
    }

    public int getNumSelectedPjFromHistory() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            return pjFinder.getConnPjNumFromHistory();
        }
        return 0;
    }

    public boolean isSearchingInEngine() {
        return this._engine.IsSearching();
    }

    public void clearAllSelectingPJ() {
        this._aSelectPjInfo.clear();
        this._aSelectPjFromHistory.clear();
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.clearManualSearchPj();
        }
        this._engine.ClearManualSearchFlag();
    }

    public boolean isConnPjFromHistory(String str, String str2) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null) {
            return false;
        }
        return pjFinder.isConnPjFromHistory(str, str2);
    }

    public void setWhiteboardConnect(boolean z) {
        Lg.d("setWhiteboardConnect:" + z);
        this._isWhiteboardConnect = z;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void disableNeedSelectProjection() {
        this._shouldSelectProjection = false;
    }

    public void resetReconnect() {
        this._reconnectCounter = 0;
        this._reconnectPjInfo = null;
    }

    private boolean shouldReconnect() {
        return this._reconnectCounter == 0 && this._reconnectPjInfo != null;
    }

    public void changeProtocol(ProtocolType protocolType) {
        this._engine.ChangeProtocol(protocolType);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void connect(ArrayList<ConnectPjInfo> arrayList) {
        connect(arrayList, false);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void connect(ArrayList<ConnectPjInfo> arrayList, boolean z) {
        Lg.d("connect");
        unregisterHasModeratorPassword();
        if (this._pjFinder == null) {
            return;
        }
        this._isNoInterrupt = false;
        this._isMppLockedByMe = false;
        this._lockedPjInfo.setTryConnectingPjInfo(arrayList);
        this._pjFinder.noSearch();
        if (this._pjFinder.isSearching()) {
            Lg.d("接続に先立ち、検索をストップする。");
            holdConnectInfo(arrayList);
            this._pjFinder.endSearch();
        } else if (this._engine.IsSearching()) {
            this._engine.EndSearchPj();
        } else {
            int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[this._connectState.ordinal()];
            boolean z2 = true;
            if (i == 1) {
                Lg.i("接続中です。一旦切って接続しなおすため、接続を保留にします");
                disconnect(DisconReason.Reconnect);
                holdConnectInfo(arrayList);
            } else if (i == 2) {
                Lg.i("切断中です。接続を保留にします");
                holdConnectInfo(arrayList);
            } else if (i != 3) {
                Lg.e("準備ができていません。 _connectState=" + getStateMachineStatusMsg(this._connectState));
            } else {
                this._operateInfoStacker.clear();
                if (isRegistered()) {
                    Analytics.getIns().setConnectEvent(eSearchRouteDimension.registered, null, null, null);
                }
                Analytics.getIns().sendEvent(eCustomEvent.CONNECT);
                PjConnector pjConnector = new PjConnector(arrayList);
                if (!isRegistered() && !z) {
                    z2 = false;
                }
                if (pjConnector.connect(z2)) {
                    this._conCtrlr.showConnectingDialog();
                    MessageDialog.setIsModeratorMenu(false);
                    return;
                }
                if (arrayList.size() != 0) {
                    Iterator<ConnectPjInfo> it = arrayList.iterator();
                    while (it.hasNext()) {
                        onDisconnect(it.next().getPjInfo().ProjectorID, IOnConnectListener.DisconedReason.FailedToConnect);
                    }
                } else {
                    onDisconnect(-1, IOnConnectListener.DisconedReason.FailedToConnect);
                }
                setWhiteboardConnect(false);
            }
        }
    }

    public boolean doConnect(ArrayList<ConnectPjInfo> arrayList) {
        int i;
        Lg.d("enter Pj#doConnect");
        this._engine.ChangeProtocol(ProtocolType.JPEG);
        String read = PrefUtils.read(this._context, PrefTagDefine.conPJ_CONFIG_USERNAME_TAG);
        ArrayList<D_ConnectPjInfo> arrayList2 = new ArrayList<>();
        super.setConnectState(StateMachine.ConnectState.TryConnecting);
        this._bTryMirroringConnect = false;
        this._isTryModeratorWhenConnect = false;
        boolean z = false;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            D_PjInfo pjInfo = arrayList.get(i2).getPjInfo();
            if (!pjInfo.bSupportedMPP) {
                this._isTryModeratorWhenConnect = false;
            } else {
                if (pjInfo.Status == 3 || arrayList.get(i2).isNoInterrupt()) {
                    if (pjInfo.isEnableModerator()) {
                        this._isTryModeratorWhenConnect = true;
                    }
                } else if (pjInfo.Status == 2 && !pjInfo.bEnableMPPInterruptToNP) {
                    this._isTryModeratorWhenConnect = false;
                }
                z = true;
            }
            z = false;
        }
        if (!z) {
            read = null;
        }
        int[] iArr = {-1, -1};
        int i3 = -1;
        int i4 = -1;
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            D_ConnectPjInfo d_ConnectPjInfo = new D_ConnectPjInfo();
            iArr[0] = -1;
            iArr[1] = -1;
            D_PjInfo pjInfo2 = arrayList.get(i5).getPjInfo();
            if (pjInfo2.isMppMirror()) {
                this._bTryMirroringConnect = true;
            }
            d_ConnectPjInfo.ProjectorID = pjInfo2.ProjectorID;
            d_ConnectPjInfo.PrjKeyWord = arrayList.get(i5).getKeyword();
            d_ConnectPjInfo.nBPP = 32;
            d_ConnectPjInfo.bCompulsionResolution = true;
            d_ConnectPjInfo.bNotBreak = !this._isTryModeratorWhenConnect && arrayList.get(i5).isNoInterrupt();
            if (!z && d_ConnectPjInfo.bNotBreak) {
                this._isNoInterrupt = true;
            }
            arrayList2.add(d_ConnectPjInfo);
            if (this._engine.IsJNI()) {
                if (this._engine.GetSendImageBufferSize(arrayList2, iArr) > 0) {
                    i3 = iArr[0];
                    i4 = iArr[1];
                } else {
                    Lg.e("failed to get buffer size and minimum rect.");
                    ConnectionControler connectionControler = this._conCtrlr;
                    if (connectionControler != null) {
                        connectionControler.onConnectFail(-1, getFailReason());
                        this._conCtrlr.onConnectFailForGA(getFailReason());
                    }
                    super.setConnectState(StateMachine.ConnectState.Default);
                    return false;
                }
            } else {
                int i6 = iArr[0];
                if (i6 != -1 && (i = iArr[1]) != -1) {
                    if (i3 == -1 || i6 < i3) {
                        i3 = i6;
                    }
                    if (i4 == -1 || i < i4) {
                        i4 = i;
                    }
                }
            }
        }
        this._res.setResolutionOnConnect(i3, i4);
        if (arrayList.size() == 1) {
            this._reconnectPjInfo = arrayList.get(0);
        }
        this._engine.SetCommonRect(i3, i4);
        int Connect = this._engine.Connect(arrayList2, this, read);
        if (Connect != 0) {
            Lg.e("→ connect() エラー = " + MsgGetterUtils.getErrMsg(Connect));
            ConnectionControler connectionControler2 = this._conCtrlr;
            if (connectionControler2 != null) {
                connectionControler2.onConnectFail(-1, getFailReason());
                this._conCtrlr.onConnectFailForGA(getFailReason());
            }
            super.setConnectState(StateMachine.ConnectState.Default);
        }
        return Connect == 0;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int disconnect(DisconReason disconReason) {
        return disconnect(disconReason, false);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int disconnect(final DisconReason disconReason, boolean z) {
        int Disconnect;
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.noSearch();
        }
        PjFinder pjFinder2 = this._pjFinder;
        if (pjFinder2 != null && pjFinder2.isSearching()) {
            Lg.d("切断に先立ち、検索をストップする。");
            holdDisconnectInfo(disconReason);
            this._pjFinder.endSearch();
            return 0;
        }
        this._disconReason = disconReason;
        this._isPassiveDisconnectionDuringConnecting = z;
        if (disconReason == DisconReason.TerminateUI) {
            Disconnect = this._engine.TerminateSession();
        } else {
            Disconnect = this._engine.Disconnect();
        }
        if (Disconnect == 0) {
            Lg.i("→ disconnect() 成功");
            super.setConnectState(StateMachine.ConnectState.TryDisconnecting);
            if (!shouldReconnect()) {
                this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.Pj$$ExternalSyntheticLambda0
                    {
                        Pj.this = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        Pj.this.m206lambda$disconnect$1$comepsoniprojectionuiengine_wrapperPj(disconReason);
                    }
                });
                new Thread(new DisconnectChecker()).start();
            }
        } else {
            Lg.e("→ disconnect() エラー = " + MsgGetterUtils.getErrMsg(Disconnect));
            this._lockedPjInfo.clear();
        }
        this._operateInfoStacker.clear();
        unregisterHasModeratorPassword();
        return 0;
    }

    /* renamed from: lambda$disconnect$1$com-epson-iprojection-ui-engine_wrapper-Pj */
    public /* synthetic */ void m206lambda$disconnect$1$comepsoniprojectionuiengine_wrapperPj(DisconReason disconReason) {
        this._conCtrlr.showDisconnectingDialog(disconReason);
    }

    public void sendImage(Bitmap bitmap, Bitmap bitmap2) throws BitmapMemoryException {
        if (MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
            return;
        }
        if (this._connectState != StateMachine.ConnectState.NowConnecting) {
            Lg.w("未接続のため送信できません");
        } else {
            this._imgSender.sendImage(bitmap, bitmap2);
        }
    }

    public void sendMirroringImage(Bitmap bitmap) {
        if (MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
            if (this._connectState != StateMachine.ConnectState.NowConnecting) {
                Lg.w("未接続のため送信できません");
            } else {
                this._imgSender.sendImageWithConvertingBitmapConfig(bitmap);
            }
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void sendWaitImage() {
        if (this._connectState != StateMachine.ConnectState.NowConnecting) {
            Lg.w("未接続のため送信できません");
            return;
        }
        try {
            this._imgSender.sendWaitImage(this._context);
        } catch (BitmapMemoryException unused) {
        }
    }

    public void sendWaitImageWhenConnected() throws BitmapMemoryException {
        if (this._connectState != StateMachine.ConnectState.NowConnecting) {
            Lg.w("未接続のため送信できません");
        } else {
            this._imgSender.sendWaitImageWhenConnected(this._context);
        }
    }

    public void setIsConnectingByLinkageData(boolean z) {
        this._isConnectingByLikageData = z;
    }

    public boolean isConnectingByLinkageData() {
        return this._isConnectingByLikageData;
    }

    public boolean isShowingSpinDialog() {
        return this._conCtrlr.isShowingSpinDialog();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public boolean isConnected() {
        return this._connectState == StateMachine.ConnectState.NowConnecting;
    }

    public int getPjResWidth() {
        return this._res.getWidth();
    }

    public int getPjResHeight() {
        return this._res.getHeight();
    }

    public ArrayList<ConnectPjInfo> getSelectedPj() {
        return this._aSelectPjInfo;
    }

    public boolean isAlreadyInited() {
        return this._connectState != StateMachine.ConnectState.NotInitYet;
    }

    public boolean isNeedRestart() {
        return this._connectState == StateMachine.ConnectState.NeedRestart;
    }

    public int getShadowResWidth() {
        return this._res.getWidth();
    }

    public int getShadowResHeight() {
        return this._res.getHeight();
    }

    public boolean isAspectRatioUltraWide() {
        return isConnected() && this._res.isAspectRatioUltraWide();
    }

    public void disablePjListStatusListener() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.disablePjListStatusListener();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void disableManualSearchResultListener() {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.disableManualSearchListener();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void setOnConnectListener(IOnConnectListener iOnConnectListener) {
        ConnectionControler connectionControler = this._conCtrlr;
        if (connectionControler != null) {
            connectionControler.setOnConnectListener(iOnConnectListener);
        }
    }

    public void disableOnConnectListener() {
        ConnectionControler connectionControler = this._conCtrlr;
        if (connectionControler != null) {
            connectionControler.disableOnConnectListener();
            this._conCtrlr.clearSearchingDialog();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void setOnMinConnectListener(IOnMinConnectListener iOnMinConnectListener) {
        ConnectionControler connectionControler = this._conCtrlr;
        if (connectionControler != null) {
            connectionControler.setOnMinConnectListener(iOnMinConnectListener);
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void clearOnMinConnectListener() {
        ConnectionControler connectionControler = this._conCtrlr;
        if (connectionControler != null) {
            connectionControler.clearOnMinConnectListener();
        }
    }

    public void setActivity(Activity activity) {
        if (this._conCtrlr == null) {
            return;
        }
        this._context = activity.getApplicationContext();
        this._conCtrlr.setContext(activity);
        this._activity = activity;
        setNotInterruptUI();
    }

    private void setNotInterruptUI() {
        int readInt;
        if (this._pjFinder == null || (readInt = PrefUtils.readInt(this._context, PrefTagDefine.conPJ_CONFIG_NOINTURRUPT_TAG)) == Integer.MIN_VALUE) {
            return;
        }
        this._pjFinder.setNotInterruptUI(readInt == 1);
    }

    public synchronized void setLinkageDataSearchingMode(boolean z) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.setLinkageDataSearchingMode(z);
        }
        this._isLinkageDataSearching = z;
    }

    public boolean isLinkageDataSearching() {
        return this._isLinkageDataSearching;
    }

    public boolean isLinkageDataConnected() {
        return isConnected() && LinkageDataInfoStacker.getIns().get() != null;
    }

    public boolean isAvailablePjFinder() {
        return this._pjFinder != null;
    }

    public boolean isAlivePj() {
        return this._engine.IsAlivePj();
    }

    public void onActivityStart() {
        ConnectionControler connectionControler = this._conCtrlr;
        if (connectionControler == null) {
            return;
        }
        connectionControler.onActivityStart(this._activity);
    }

    public void setNotInterruptUI(boolean z) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.setNotInterruptUI(z);
        }
    }

    public boolean isSelfProjection() {
        return this._bSelfProjection;
    }

    public boolean isMpp() {
        Engine engine = this._engine;
        if (engine == null) {
            return false;
        }
        return engine.isMpp();
    }

    public boolean isEnablePJControl() {
        return this._engine.isEnablePJControl();
    }

    public boolean hasMppThumbnail() {
        ArrayList<ConnectPjInfo> nowConnectingPJList = getNowConnectingPJList();
        if (nowConnectingPJList == null || nowConnectingPJList.size() != 1) {
            return false;
        }
        return nowConnectingPJList.get(0).getPjInfo().isMppThumbnail;
    }

    public boolean isEnableMppDelivery() {
        ArrayList<ConnectPjInfo> nowConnectingPJList = getNowConnectingPJList();
        if (nowConnectingPJList == null || nowConnectingPJList.size() != 1) {
            return false;
        }
        return nowConnectingPJList.get(0).getPjInfo().isMppDelivery;
    }

    public boolean isRegistedPjs5Over() {
        return getIns().getRegisteredPjList().size() >= 5;
    }

    public D_PjInfo getPjInfoByIp(String str) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null) {
            Lg.w("PjFinderがいない");
            return null;
        }
        return pjFinder.getPJByIP(str);
    }

    public D_PjInfo getPjInfoByIpAndName(String str, String str2) {
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder == null) {
            Lg.w("PjFinderがいない");
            return null;
        }
        return pjFinder.getPJByIPandName(str, str2);
    }

    public void showQueryDialog(QueryDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        this._conCtrlr.showQueryDialog(messageType, iOnDialogEventListener, resultAction);
    }

    public void showMsgDialog(MessageDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        this._conCtrlr.showMsgDialog(messageType, iOnDialogEventListener, resultAction);
    }

    public void showSpoilerDialog(SpoilerDialog.MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        this._conCtrlr.showSpoilerDialog(messageType, iOnDialogEventListener, resultAction);
    }

    public void clearDialog() {
        this._conCtrlr.clearDialog();
    }

    public void clearAllDialog() {
        this._conCtrlr.clearAllDialog();
    }

    public void setWifiConnecter(WifiConnector wifiConnector) {
        this._wifiConnector = wifiConnector;
        if (wifiConnector == null) {
            this._isWifiChanged = false;
        }
    }

    public boolean isWifiChanged() {
        WifiConnector wifiConnector = this._wifiConnector;
        if (wifiConnector != null) {
            return wifiConnector.isWifiChanged();
        }
        return this._isWifiChanged;
    }

    public void restoreWifi() {
        new Thread(new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.Pj$$ExternalSyntheticLambda4
            {
                Pj.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                Pj.this.m210lambda$restoreWifi$2$comepsoniprojectionuiengine_wrapperPj();
            }
        }).start();
        this._isWifiChanged = false;
    }

    /* renamed from: lambda$restoreWifi$2$com-epson-iprojection-ui-engine_wrapper-Pj */
    public /* synthetic */ void m210lambda$restoreWifi$2$comepsoniprojectionuiengine_wrapperPj() {
        WifiConnector wifiConnector = this._wifiConnector;
        if (wifiConnector != null) {
            wifiConnector.revert();
            this._wifiConnector = null;
        }
    }

    public void removeWifiProfile() {
        new Thread(new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.Pj$$ExternalSyntheticLambda3
            {
                Pj.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                Pj.this.m209x662e5c1();
            }
        }).start();
    }

    /* renamed from: lambda$removeWifiProfile$3$com-epson-iprojection-ui-engine_wrapper-Pj */
    public /* synthetic */ void m209x662e5c1() {
        WifiManager wifiManager = (WifiManager) this._context.getSystemService("wifi");
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        String ssid = connectionInfo != null ? connectionInfo.getSSID() : null;
        if (ssid == null || configuredNetworks == null) {
            return;
        }
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (wifiConfiguration.SSID.equals(ssid)) {
                wifiManager.removeNetwork(wifiConfiguration.networkId);
                wifiManager.saveConfiguration();
                return;
            }
        }
    }

    public D_PjInfo.PjListType getPjListType() {
        return D_PjInfo.getPjListType(getPjList());
    }

    public boolean isAllPjTypeBusiness() {
        return D_PjInfo.isPjListTypeBusiness(getPjList());
    }

    public boolean isAllPjTypeHome() {
        return D_PjInfo.isPjListTypeHome(getPjList());
    }

    public boolean isAllPjTypeSignage() {
        return D_PjInfo.isPjListTypeSignage(getPjList());
    }

    public boolean isAllPjTypeBusinessSelectHome() {
        return D_PjInfo.isPjListTypeBusiness(getPjListSelectHome());
    }

    public boolean isAllPjTypeHomeSelectHome() {
        return D_PjInfo.isPjListTypeHome(getPjListSelectHome());
    }

    public boolean isAllPjTypeSignageSelectHome() {
        return D_PjInfo.isPjListTypeSignage(getPjListSelectHome());
    }

    public ArrayList<String> getNowConnecingUniqeInfoList() {
        return ConnectPjInfo.createUniqeInfoList(this._lockedPjInfo.getNowConnectingPjArray());
    }

    public ArrayList<String> getNowRegisteredUniqeInfoList() {
        return ConnectPjInfo.createUniqeInfoList(getRegisteredPjList());
    }

    private ArrayList<D_PjInfo> getPjList() {
        ArrayList<ConnectPjInfo> nowConnectingPjArray;
        if (this._aRegisteredPjInf.size() > 0) {
            nowConnectingPjArray = this._aRegisteredPjInf;
        } else {
            nowConnectingPjArray = this._lockedPjInfo.getNowConnectingPjArray();
        }
        return ConnectPjInfo.createPjInfoList(nowConnectingPjArray);
    }

    private ArrayList<D_PjInfo> getPjListSelectHome() {
        ArrayList<ConnectPjInfo> arrayList = new ArrayList<>();
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            arrayList = pjFinder.getSelectPjList();
        }
        return ConnectPjInfo.createPjInfoList(arrayList);
    }

    public boolean cannotConnectPjInSelectHome() {
        Iterator<D_PjInfo> it = getPjListSelectHome().iterator();
        while (it.hasNext()) {
            if (!D_ListItem.isSelectable(it.next().nDispStatus)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ConnectPjInfo> getNowConnectingPJList() {
        return this._lockedPjInfo.getNowConnectingPjArray();
    }

    public ArrayList<ConnectPjInfo> getRegisteredPjList() {
        return this._aRegisteredPjInf;
    }

    public boolean isSelectedPJ() {
        PjFinder pjFinder = this._pjFinder;
        return pjFinder != null && pjFinder.getSelectPjNum() > 0;
    }

    public boolean isSelectedPJFromHistory() {
        PjFinder pjFinder = this._pjFinder;
        return pjFinder != null && pjFinder.getConnPjNumFromHistory() > 0;
    }

    public int pjcontrol_changeSourceGeneric(int i, eSrcType esrctype) {
        return this._engine.Ctl_ChangeSource(i, esrctype);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int pjcontrol_spoweron(D_PjInfo d_PjInfo) {
        if (d_PjInfo.isSupportedSecuredEscvp) {
            return this._engine.SendU2UCommandNWStandbyON(d_PjInfo.ProjectorID);
        }
        if (isConnected()) {
            return this._engine.SendEscvpCommand(d_PjInfo.ProjectorID, ESCVP_COMMAND_SPOWERON);
        }
        return this._engine.SendEscvpCommandWithIp(d_PjInfo.IPAddr, ESCVP_COMMAND_SPOWERON);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int pjcontrol_scomport(D_PjInfo d_PjInfo) {
        if (isConnected()) {
            if (d_PjInfo.isNetworkPathWireless) {
                return this._engine.SendEscvpCommand(d_PjInfo.ProjectorID, ESCVP_COMMAND_SCOMPORT_WIRELESS);
            }
            return this._engine.SendEscvpCommand(d_PjInfo.ProjectorID, ESCVP_COMMAND_SCOMPORT_WIRED);
        } else if (d_PjInfo.isNetworkPathWireless) {
            return this._engine.SendEscvpCommandWithIp(d_PjInfo.IPAddr, ESCVP_COMMAND_SCOMPORT_WIRELESS);
        } else {
            return this._engine.SendEscvpCommandWithIp(d_PjInfo.IPAddr, ESCVP_COMMAND_SCOMPORT_WIRED);
        }
    }

    public int pjcontrol_power(int i) {
        return this._engine.SendEscvpCommand(i, ESCVP_COMMAND_POWER);
    }

    public int pjcontrol_changeSourceComputer(int i) {
        return pjcontrol_changeSourceGeneric(i, eSrcType.eSourceType_Computer);
    }

    public int pjcontrol_changeSourceVideo(int i) {
        return pjcontrol_changeSourceGeneric(i, eSrcType.eSourceType_Video);
    }

    public int pjcontrol_changeSourceLAN(int i) {
        return pjcontrol_changeSourceGeneric(i, eSrcType.eSourceType_LAN);
    }

    public int pjcontrol_freeze(int i) {
        return this._engine.Ctl_Freeze(i);
    }

    public int pjcontrol_avMute(int i) {
        return this._engine.Ctl_AVMute(i);
    }

    public int pjcontrol_volumeDown(int i) {
        return this._engine.Ctl_VolumeDown(i);
    }

    public int pjcontrol_volumeUp(int i) {
        return this._engine.Ctl_VolumeUP(i);
    }

    public int pjcontrol_displayQRCode(int i) {
        return this._engine.Ctl_DispQRCode(i);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int sendU2UCommandKeyEmulation(String str, int i) {
        return this._engine.SendU2UCommandKeyEmulation(str, i);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int sendDigestEscvp(String str, byte[] bArr, String str2) {
        return this._engine.SendEscvp(bArr, str, str2);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int sendOpenEscvp(String str, byte[] bArr) {
        return this._engine.SendEscvpCommandWithIp(bArr, str);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public synchronized int sendAndReceiveEscvpCommandWithIp(byte[] bArr, String str, StringBuffer stringBuffer, boolean z) {
        int SendAndReceiveEscvpCommandWithIp;
        char[] cArr = new char[33];
        SendAndReceiveEscvpCommandWithIp = this._engine.SendAndReceiveEscvpCommandWithIp(bArr, str, cArr, z);
        String[] split = createString(cArr).split("\r:");
        if (stringBuffer != null) {
            stringBuffer.setLength(0);
        }
        stringBuffer.append(split[0]);
        Lg.d("Command : " + str + " , Response : " + ((Object) stringBuffer));
        return SendAndReceiveEscvpCommandWithIp;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int sendU2UCommandNWStandbyON(int i) {
        return this._engine.SendU2UCommandNWStandbyON(i);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void projectionMyself() {
        if (this._connectState != StateMachine.ConnectState.NowConnecting) {
            Lg.w("未接続のため要求出来ません");
        } else if (this._canProjectMySelf) {
            if (UserListUtils.INSTANCE.isMyScreenProjectedOn1Screen(this._mppLayout, this._myUniqueId.longValue())) {
                Lg.d("自分投写する必要ない");
                return;
            }
            Lg.d("自分投写コマンドをPJへ送信");
            this._canProjectMySelf = false;
            this._engine.ProjectionMyself();
            this._handlerEnablerProjectionMySelf.removeCallbacks(this._enablerProjectionMySelf);
            this._handlerEnablerProjectionMySelf.postDelayed(this._enablerProjectionMySelf, 1000L);
        }
    }

    /* renamed from: lambda$new$4$com-epson-iprojection-ui-engine_wrapper-Pj */
    public /* synthetic */ void m208lambda$new$4$comepsoniprojectionuiengine_wrapperPj() {
        this._canProjectMySelf = true;
    }

    public boolean isEnableChangeMppControlMode() {
        return this._engine.IsEnableChangeMppControlMode();
    }

    public boolean hasMppModeratorPassword(ArrayList<D_PjInfo> arrayList) {
        ArrayList<ConnectPjInfo> nowConnectingPJList;
        arrayList.clear();
        if (!isConnected()) {
            nowConnectingPJList = this._lockedPjInfo.getTryConnectingPjInfo();
        } else {
            nowConnectingPJList = getNowConnectingPJList();
        }
        boolean z = false;
        if (nowConnectingPJList == null) {
            return false;
        }
        Iterator<ConnectPjInfo> it = nowConnectingPJList.iterator();
        while (it.hasNext()) {
            D_PjInfo pjInfo = it.next().getPjInfo();
            if (pjInfo.hasModeratorPassword) {
                if (nowConnectingPJList.size() > 1) {
                    arrayList.add(pjInfo);
                }
                z = true;
            }
        }
        return z;
    }

    public int setMppControlMode(int i) {
        return setMppControlMode(i, null);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int setMppControlMode(int i, byte[] bArr) {
        this._isMppLockedByMe = false;
        int SetMppControlMode = this._engine.SetMppControlMode(i, bArr);
        if (SetMppControlMode == 0) {
            this._isCallSetMppControlMode = true;
        }
        return SetMppControlMode;
    }

    public ArrayList<D_MppUserInfo> getMppUserList() {
        return this._mppUserList;
    }

    public D_MppUserInfo getMyUserInfo() {
        ArrayList<D_MppUserInfo> arrayList = this._mppUserList;
        if (arrayList != null) {
            return arrayList.get(0);
        }
        return null;
    }

    public String getMppUserNameByUniqueID(long j) {
        ArrayList<D_MppUserInfo> arrayList = this._mppUserList;
        if (arrayList != null) {
            Iterator<D_MppUserInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                D_MppUserInfo next = it.next();
                if (next.uniqueId == j) {
                    return next.userName;
                }
                if (((int) next.uniqueId) == ((int) j)) {
                    return next.userName;
                }
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.epson.iprojection.engine.common.D_MppUserInfo getMppUserInfoByUniqueID(long r5) {
        /*
            r4 = this;
            java.util.ArrayList<com.epson.iprojection.engine.common.D_MppUserInfo> r0 = r4._mppUserList
            if (r0 == 0) goto L22
            java.util.Iterator r0 = r0.iterator()
        L8:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L22
            java.lang.Object r1 = r0.next()
            com.epson.iprojection.engine.common.D_MppUserInfo r1 = (com.epson.iprojection.engine.common.D_MppUserInfo) r1
            long r2 = r1.uniqueId
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 != 0) goto L1b
            return r1
        L1b:
            long r2 = r1.uniqueId
            int r2 = (int) r2
            int r3 = (int) r5
            if (r2 != r3) goto L8
            return r1
        L22:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.engine_wrapper.Pj.getMppUserInfoByUniqueID(long):com.epson.iprojection.engine.common.D_MppUserInfo");
    }

    public ArrayList<D_MppLayoutInfo> getMppLayout() {
        return this._mppLayout;
    }

    public void removeMppUserFromLayout(D_MppLayoutInfo d_MppLayoutInfo) {
        int i = 0;
        while (true) {
            if (i >= this._mppLayout.size()) {
                break;
            }
            D_MppLayoutInfo d_MppLayoutInfo2 = this._mppLayout.get(i);
            if (d_MppLayoutInfo2.uniqueId == d_MppLayoutInfo.uniqueId) {
                d_MppLayoutInfo2.uniqueId = 0L;
                d_MppLayoutInfo2.empty = true;
                break;
            }
            i++;
        }
        updateMppLayout(this._mppLayout);
    }

    public void exchangeMppLayout(int i, int i2) {
        int size = this._mppLayout.size();
        if (i >= size || i2 >= size) {
            return;
        }
        boolean z = this._mppLayout.get(i).active;
        boolean z2 = this._mppLayout.get(i2).active;
        ArrayList<D_MppLayoutInfo> arrayList = this._mppLayout;
        arrayList.set(i2, arrayList.get(i));
        this._mppLayout.set(i, this._mppLayout.get(i2));
        this._mppLayout.get(i).active = z;
        this._mppLayout.get(i2).active = z2;
        updateMppLayout(this._mppLayout);
    }

    public int getMppWindowDivNum() {
        ArrayList<D_MppLayoutInfo> mppLayout = getMppLayout();
        int i = 0;
        if (mppLayout == null) {
            Lg.e("MppLayout is null.");
            return 0;
        }
        Iterator<D_MppLayoutInfo> it = mppLayout.iterator();
        while (it.hasNext()) {
            if (it.next().active) {
                i++;
            }
        }
        return i;
    }

    public int controlMppOtherUser(long j, int i) {
        return this._engine.ControlMppOtherUser(j, i);
    }

    public int updateMppLayout(ArrayList<D_MppLayoutInfo> arrayList) {
        return this._engine.UpdateMppLayout(arrayList);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public int setMppScreenLock(boolean z) {
        this._isMppLockedByMe = z;
        ConnectionControler connectionControler = this._conCtrlr;
        if (connectionControler != null) {
            connectionControler.onChangedScreenLockByMe(z);
        }
        return this._engine.SetScreenLockStatus(z);
    }

    public boolean isSetMppScreenLock() {
        return this._engine.IsSetScreenLock();
    }

    public boolean isMppLockedByMe() {
        return this._isMppLockedByMe;
    }

    public boolean isMppLockedByModerator() {
        return this._isMppLockedByModerator;
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public boolean isModerator() {
        return this._mppControlMode == IOnConnectListener.MppControlMode.ModeratorAdmin;
    }

    public boolean isMppClient() {
        return this._mppControlMode == IOnConnectListener.MppControlMode.ModeratorEntry;
    }

    public boolean isCollaboration() {
        return this._mppControlMode == IOnConnectListener.MppControlMode.Collaboration || this._mppControlMode == IOnConnectListener.MppControlMode.CollaborationOld;
    }

    public boolean existsOtherModerator() {
        return this._mppControlMode == IOnConnectListener.MppControlMode.ModeratorEntry;
    }

    public boolean isEnableModeration() {
        return (this._mppControlMode == IOnConnectListener.MppControlMode.NoUse || this._mppControlMode == IOnConnectListener.MppControlMode.CollaborationOld) ? false : true;
    }

    public boolean isEnableProjection() {
        return this._mppControlMode == IOnConnectListener.MppControlMode.Collaboration || this._mppControlMode == IOnConnectListener.MppControlMode.CollaborationOld || this._mppControlMode == IOnConnectListener.MppControlMode.ModeratorAdmin;
    }

    public boolean isEnableUserLock() {
        return this._lockedPjInfo != null && this._engine.isMpp() && this._lockedPjInfo.getMPPVersion() >= 2;
    }

    public IOnConnectListener.MppControlMode getMppControlMode() {
        return this._mppControlMode;
    }

    public D_MppUserInfo getModeratorUserInfo() {
        return this._moderatorUserInfo;
    }

    public void setProjectionMode(int i) {
        this._projectionMode = i;
        this._engine.SetProjectionMode(i);
    }

    public int getProjectionMode() {
        return this._engine.GetProjectionMode();
    }

    public boolean isNoInterrupt() {
        return this._isNoInterrupt;
    }

    public void requestDelivery(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this._engine.RequestDelivery(z, z2, z3, z4);
        if (z5) {
            this._conCtrlr.createDeliveringDialog();
        }
    }

    public void requestDeliveryWhitePaper() {
        this._engine.RequestDeliveryWhitePaper(false, false, false, false);
        this._conCtrlr.createDeliveringDialog();
    }

    public void createDeliveryDialog() {
        this._conCtrlr.createDeliveryDialog();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void clearDeliveredImagePath() {
        this._conCtrlr.clearDeliveredImagePath();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public String getDeliveredImagePath() {
        return this._conCtrlr.getDeliveredImagePath();
    }

    public boolean requestThumbnail(int i, int i2, int i3) {
        return isModerator() && this._engine.RequestThumbnail(i, i2, i3) == 0;
    }

    public boolean stopThumbnail() {
        return isModerator() && this._engine.StopThumbnail() == 0;
    }

    public int setProjectorLogUpload(boolean z) {
        return this._engine.SetProjectorLogUpload(z);
    }

    public int setBandWidth(eBandWidth ebandwidth) {
        return this._engine.SetBandWidth(ebandwidth);
    }

    public int enableAudioTransfer(int i) {
        clearAudioData();
        if (i == -1) {
            if (getNowConnectingPJList() == null) {
                return 1;
            }
            i = getNowConnectingPJList().size();
        }
        if (WebRTCEntrance.INSTANCE.isWebRTCProcessing()) {
            return 1;
        }
        int readInt = PrefUtils.readInt(this._context, PrefTagDefine.conPJ_BAND_WIDTH);
        int readInt2 = PrefUtils.readInt(this._context, PrefTagDefine.conPJ_AUDIO_TRANSFER_TAG);
        if (readInt != Integer.MIN_VALUE && readInt2 != Integer.MIN_VALUE && i == 1 && Build.VERSION.SDK_INT >= 29 && this._context.checkSelfPermission(PermissionAudioPresenter.permission) == 0 && MirroringEntrance.INSTANCE.isMirroringSwitchOn() && AudioUtils.Companion.isBandCapableOfUsingAudio(readInt) && readInt2 == 1 && !MirroringNotification.INSTANCE.get_isPaused() && this._projectionMode == 1) {
            Lg.d("enableAudioTransfer()");
            MirroringEntrance.INSTANCE.onChangeAudioSettings(true);
            return this._engine.SetAudioTransfer(true);
        }
        return 1;
    }

    public int disableAudioTransfer() {
        if (!WebRTCEntrance.INSTANCE.isWebRTCProcessing() && Build.VERSION.SDK_INT >= 29 && MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
            Lg.d("disableAudioTransfer()");
            MirroringEntrance.INSTANCE.onChangeAudioSettings(false);
            return this._engine.SetAudioTransfer(false);
        }
        return 1;
    }

    public int enableAudioTransferForWebRTC(int i) {
        if (i == -1) {
            if (getNowConnectingPJList() == null) {
                return 1;
            }
            getNowConnectingPJList().size();
        }
        return this._engine.SetAudioTransfer(true);
    }

    public int disableAudioTransferForWebRTC() {
        return this._engine.SetAudioTransfer(false);
    }

    public void createStartModeratorDialog() {
        this._conCtrlr.createStartModeratorDialog();
    }

    public void createModeratorPassInputDialog() {
        this._conCtrlr.createModeratorPassInputDialog(false);
    }

    public void requestDisplayKeyword(int i) {
        this._engine.RequestDisplayKeyword(i);
    }

    public void addRegisteredPjInf(ArrayList<ConnectPjInfo> arrayList) {
        this._aRegisteredPjInf.clear();
        if (!D_PjInfo.isPjListTypeBusiness(ConnectPjInfo.createPjInfoList(arrayList))) {
            this._aRegisteredPjInf.addAll(arrayList);
        }
        new RegisteredPjDataForFile().save(this._context, this._aRegisteredPjInf);
        saveSsidDataWhenRegistered();
        if (this._aRegisteredPjInf.size() > 0) {
            setRegisterState(StateMachine.RegisterState.Registered);
        }
        PjFinder pjFinder = this._pjFinder;
        if (pjFinder != null) {
            pjFinder.removeAllSelectPJ();
            this._pjFinder.clearConnPjFromHistory();
        }
    }

    public void saveSsidDataWhenRegistered() {
        String ssid = ((WifiManager) this._context.getSystemService("wifi")).getConnectionInfo().getSSID();
        if (ssid == null || ssid.equals("") || ssid.equals(CommonDefine.UNKNOWN_SSID)) {
            PrefUtils.delete(this._context, TAG_SSID_SAVE_PREF, (SharedPreferences.Editor) null);
        } else {
            PrefUtils.write(this._context, TAG_SSID_SAVE_PREF, ssid);
        }
    }

    public boolean isSameSsidWhenRegistered() throws UnknownSsidException {
        String ssid = ((WifiManager) this._context.getSystemService("wifi")).getConnectionInfo().getSSID();
        String read = PrefUtils.read(this._context, TAG_SSID_SAVE_PREF);
        if (read != null) {
            return ssid != null && ssid.compareTo(read) == 0;
        }
        throw new UnknownSsidException("Unknown SSID.");
    }

    public String getSsidWhenRegistered() {
        return PrefUtils.read(this._context, TAG_SSID_SAVE_PREF);
    }

    public void clearRegisteredPjInf() {
        this._aRegisteredPjInf.clear();
        new RegisteredPjDataForFile().remove(this._context);
        setRegisterState(StateMachine.RegisterState.NoRegister);
        this._conCtrlr.onUnregistered();
        WebViewDatabase.getInstance(this._context).clearHttpAuthUsernamePassword();
        RemotePrefUtils.deleteAll(this._context);
        RemotePasswordPrefUtils.deleteAll(this._context);
        PrefUtils.deleteTags(this._context, RemotePrefUtils.PREF_TAG_REMOTE_PASS);
    }

    public void resetWaitMode() {
        this._imgSender.resetWaitMode();
    }

    private void onConnect(boolean z, boolean z2) {
        try {
            this._projectionMode = 1;
            this._isNeverConnected = false;
            super.setConnectState(StateMachine.ConnectState.NowConnecting);
            this._lockedPjInfo.setNowConnectingPjInfo();
            PjFinder pjFinder = this._pjFinder;
            if (pjFinder != null) {
                pjFinder.removeAllSelectPJ();
                this._pjFinder.clearConnPjFromHistory();
            }
            this._imgSender.resetWaitMode();
            this._imgSender.create(this._context);
            ContentRectHolder.INSTANCE.update();
            this._conCtrlr.onConnectSucceed(z, z2, this._shouldSelectProjection);
            sendImageWhenConnected();
            if (Build.VERSION.SDK_INT < 29 || !LinkageDataInfoStacker.getIns().isEasyConnect()) {
                if (!isRegistered()) {
                    if (!isAllPjTypeBusiness()) {
                        addRegisteredPjInf(this._lockedPjInfo.getNowConnectingPjArray());
                        this._conCtrlr.onRegistered();
                    }
                } else {
                    Analytics.getIns().setConnectCompleteEvent(eSearchRouteDimension.registered);
                }
            }
            ArrayList<ConnectPjInfo> nowConnectingPjArray = this._lockedPjInfo.getNowConnectingPjArray();
            for (int i = 0; i < nowConnectingPjArray.size(); i++) {
                ConnectPjInfo connectPjInfo = nowConnectingPjArray.get(i);
                if (connectPjInfo.isNoInterrupt()) {
                    connectPjInfo.getPjInfo().setNoInterruptStatus();
                }
                PjFinder pjFinder2 = this._pjFinder;
                if (pjFinder2 != null) {
                    D_PjInfo pjInfoFromID = pjFinder2.getPjInfoFromID(nowConnectingPjArray.get(i).getPjInfo().ProjectorID);
                    if (pjInfoFromID == null) {
                        pjInfoFromID = nowConnectingPjArray.get(i).getPjInfo();
                    }
                    this._pjFinder.onConnected(pjInfoFromID);
                    this._pjFinder.addManualSearchPj(this, pjInfoFromID);
                }
            }
            PjFinder pjFinder3 = this._pjFinder;
            if (pjFinder3 != null) {
                pjFinder3.startManualSearchPj(null);
            }
            this._isMppLockedByModerator = false;
            this._screenWatcher.register();
            this._shutdownWatcher.register();
            Activity activity = this._activity;
            if (activity != null) {
                activity.getWindow().addFlags(128);
            }
            sendAnalyticsEventWhenConnected();
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    private void sendAnalyticsEventWhenConnected() {
        eOpenedContentsDimension eopenedcontentsdimension;
        Analytics.getIns().setConnectCompleteEvent(new ConnectConfig(this._context).getInterruptSetting() ? eConnectAsModeratorDimension.on : eConnectAsModeratorDimension.off);
        SetAnalyticsUseBandwidthWhenConnected();
        SetAnalyticsAudioTransferWhenConnected();
        Analytics.getIns().setOperationDimension(eCustomDimension.TIME_FROM_DISCONNECTED_TO_CONNECT);
        Analytics.getIns().sendEvent(eCustomEvent.OPERATION_TIME);
        Analytics.getIns().setOperationDimension(eCustomDimension.LAUNCH_CONNECTING_TIME);
        Analytics.getIns().sendEvent(eCustomEvent.OPERATION_TIME);
        Analytics.getIns().resetConnectingTime();
        eContentsType econtentstype = ContentsSelectStatus.getIns().get();
        if (econtentstype == null) {
            Analytics.getIns().setConnectCompleteEvent(eOpenedContentsDimension.none);
        } else {
            int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[econtentstype.ordinal()];
            if (i == 1) {
                eopenedcontentsdimension = eOpenedContentsDimension.photo;
            } else if (i == 2) {
                eopenedcontentsdimension = eOpenedContentsDimension.document;
            } else if (i == 3) {
                eopenedcontentsdimension = eOpenedContentsDimension.web;
            } else if (i == 4) {
                eopenedcontentsdimension = eOpenedContentsDimension.camera;
            } else if (i == 5) {
                eopenedcontentsdimension = eOpenedContentsDimension.deliver;
            } else {
                eopenedcontentsdimension = eOpenedContentsDimension.none;
            }
            Analytics.getIns().setConnectCompleteEvent(eopenedcontentsdimension);
        }
        Analytics.getIns().updateUnchangeableInfoDuringConnect();
        Analytics.getIns().sendEvent(eCustomEvent.CONNECT_COMPLETE);
        Analytics.getIns().sendEvent(eCustomEvent.CONNECT_ERROR_COUNT);
        Analytics.getIns().sendEvent(eCustomEvent.PROJECTOR_HEADER);
        eFirstTimeProjectionDimension efirsttimeprojectiondimension = eFirstTimeProjectionDimension.def;
        eContentsType econtentstype2 = ContentsSelectStatus.getIns().get();
        if (econtentstype2 != null) {
            int i2 = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[econtentstype2.ordinal()];
            if (i2 == 1) {
                efirsttimeprojectiondimension = eFirstTimeProjectionDimension.photo;
            } else if (i2 == 2) {
                efirsttimeprojectiondimension = eFirstTimeProjectionDimension.document;
            } else if (i2 == 3) {
                efirsttimeprojectiondimension = eFirstTimeProjectionDimension.web;
            } else if (i2 == 4) {
                efirsttimeprojectiondimension = eFirstTimeProjectionDimension.camera;
            } else if (i2 == 5) {
                efirsttimeprojectiondimension = eFirstTimeProjectionDimension.receivedImage;
            }
            if (efirsttimeprojectiondimension != eFirstTimeProjectionDimension.def) {
                Analytics.getIns().setFirstTimeProjectionEvent(efirsttimeprojectiondimension);
                Analytics.getIns().sendEvent(eCustomEvent.FIRST_TIME_PROJECTION);
            }
        }
    }

    /* renamed from: com.epson.iprojection.ui.engine_wrapper.Pj$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState;

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
            try {
                $SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[eContentsType.None.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[StateMachine.ConnectState.values().length];
            $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState = iArr2;
            try {
                iArr2[StateMachine.ConnectState.NowConnecting.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[StateMachine.ConnectState.TryDisconnecting.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[StateMachine.ConnectState.Default.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    private void sendAnalyticsEventWhenDisconnected() {
        Analytics.getIns().endContentsForce();
        Analytics.getIns().sendEvent(eCustomEvent.DISCONNECT);
        Analytics.getIns().setFirstTimeProjectionEvent(eFirstTimeProjectionDimension.def);
        Analytics.getIns().updateUnchangeableInfoDuringConnect();
        Analytics.getIns().setOperationDimension(eCustomDimension.CONNECTING_TIME);
        Analytics.getIns().sendEvent(eCustomEvent.OPERATION_TIME);
        Analytics.getIns().resetDisconnectedTime();
        Analytics.getIns().resetContentsCount();
    }

    private void SetAnalyticsUseBandwidthWhenConnected() {
        int readInt = PrefUtils.readInt(this._context, PrefTagDefine.conPJ_BAND_WIDTH);
        if (readInt < 0 || readInt >= eBandWidth.values().length) {
            Lg.w("範囲外:" + readInt);
        } else {
            Analytics.getIns().setConnectCompleteEvent(readInt);
        }
    }

    public void SetAnalyticsAudioTransferWhenConnected() {
        int readInt = PrefUtils.readInt(this._context, PrefTagDefine.conPJ_AUDIO_TRANSFER_TAG);
        if (readInt < 0 || readInt > 1) {
            Lg.w("範囲外:" + readInt);
        } else if (readInt == 0) {
            Analytics.getIns().setConnectCompleteEvent(eAudioTransferDimension.off);
        } else {
            Analytics.getIns().setConnectCompleteEvent(eAudioTransferDimension.on);
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public boolean sendImageWhenConnected() {
        Bitmap sendImage = getSendImage();
        if (sendImage == null) {
            try {
                sendWaitImageWhenConnected();
                return false;
            } catch (BitmapMemoryException unused) {
                ActivityGetter.getIns().killMyProcess();
                return false;
            }
        }
        try {
            sendImage(sendImage, null);
            return false;
        } catch (BitmapMemoryException unused2) {
            ActivityGetter.getIns().killMyProcess();
            return false;
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.IPj
    public void sendImageOfMirroringOff() {
        if (this._connectState != StateMachine.ConnectState.NowConnecting) {
            Lg.w("未接続のため送信できません");
            return;
        }
        eContentsType econtentstype = ContentsSelectStatus.getIns().get();
        if (econtentstype == null || this._activity == null) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[econtentstype.ordinal()];
        if (i != 4) {
            if (i != 6) {
                return;
            }
            sendWaitImage();
            return;
        }
        try {
            this._imgSender.sendImage(new RenderedImageFile().load(this._activity), null);
        } catch (BitmapMemoryException unused) {
        }
    }

    private Bitmap getSendImage() {
        try {
            eContentsType econtentstype = ContentsSelectStatus.getIns().get();
            if (econtentstype != null && this._activity != null) {
                int w = PresenAspect.getIns().getW(this._activity.getTaskId());
                int h = PresenAspect.getIns().getH(this._activity.getTaskId());
                if (w == -1 || h == -1) {
                    w = this._res.getDefaultWidth();
                    h = this._res.getDefaultHeight();
                }
                int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$activitystatus$eContentsType[econtentstype.ordinal()];
                if (i != 1 && i != 2) {
                    if (i == 3) {
                        return new ScaledImage(this._activity).read();
                    }
                    if (i == 4) {
                        return new RenderedImageFile().load(this._activity);
                    }
                    if (i != 5) {
                        return null;
                    }
                }
                if (Aspect.isAspectChanged(w, h, getShadowResWidth(), getShadowResHeight())) {
                    return new RenderedImageFile().load(this._activity);
                }
                Bitmap read = new ScaledImage(this._activity).read();
                return read != null ? read : new RenderedImageFile().load(this._activity);
            }
            return null;
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
            return null;
        }
    }

    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason) {
        PjFinder pjFinder;
        Lg.d("onDisconnect reason:" + disconedReason);
        if (disconedReason == IOnConnectListener.DisconedReason.EngineError) {
            super.setConnectState(StateMachine.ConnectState.NeedRestart);
            disconedReason = IOnConnectListener.DisconedReason.Default;
        } else {
            super.setConnectState(StateMachine.ConnectState.Default);
        }
        if (Build.VERSION.SDK_INT >= 29 && this._disconReason != DisconReason.TemporaryForMirroring && disconedReason != IOnConnectListener.DisconedReason.FailedToConnect) {
            Lg.d("restore Wifi する");
            WifiChanger.INSTANCE.restoreWifi(this._context);
        } else {
            Lg.d("restore Wifi しない");
        }
        this._bSelfProjection = false;
        this._mppControlMode = IOnConnectListener.MppControlMode.NoUse;
        this._isMppLockedByModerator = false;
        this._projectionMode = 1;
        this._imgSender.delete();
        this._imgSender.notifyToWakeUp();
        this._res.setResolutionOnDisonnect();
        PjFinder pjFinder2 = this._pjFinder;
        if (pjFinder2 != null) {
            pjFinder2.onDisconnected(i);
        }
        this._lockedPjInfo.clear();
        LinkageDataInfoStacker.getIns().clear();
        sendAnalyticsEventWhenDisconnected();
        if (!this._conCtrlr.hasAgainKeyword()) {
            if (disconedReason != IOnConnectListener.DisconedReason.Nothing) {
                this._conCtrlr.onDisconnect(i, disconedReason, this._disconReason == DisconReason.TemporaryForMirroring, this._isPassiveDisconnectionDuringConnecting);
                setIsConnectingByLinkageData(false);
            }
            PjFinder pjFinder3 = this._pjFinder;
            if (pjFinder3 != null) {
                pjFinder3.removeAllSelectPJ();
                this._pjFinder.clearConnPjFromHistory();
            }
        }
        this._isWifiChanged = false;
        if (this._conCtrlr.hasAgainKeyword()) {
            if (this._engine.IsAlivePj()) {
                return;
            }
            this._conCtrlr.showKeywordAgainDialog();
            PjFinder pjFinder4 = this._pjFinder;
            if (pjFinder4 == null || !pjFinder4.isUsableListener()) {
                return;
            }
            this._pjFinder.startSearch();
            return;
        }
        if (this._operateInfoStacker.isConnectInfo()) {
            Lg.d("スタックしていた情報を使って接続します");
            this._conCtrlr.connect(this._operateInfoStacker.getConnectPjArray());
        } else if (!this._operateInfoStacker.isDisconnectInfo() && (pjFinder = this._pjFinder) != null && pjFinder.isUsableListener()) {
            this._pjFinder.startSearch();
        }
        this._screenWatcher.unregister();
        this._shutdownWatcher.unregister();
        Activity activity = this._activity;
        if (activity != null) {
            activity.getWindow().clearFlags(128);
        }
    }

    public void onAppFinished() {
        Lg.i("アプリケーション終了！");
        if (isConnected()) {
            disconnect(DisconReason.AppFinished);
        }
        this._conCtrlr.onAppFinished();
        ActivityGetter.getIns().finishedApp();
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onRejectClientResolution(int i, ArrayList<D_ClientResolutionInfo> arrayList) {
        Lg.d("onRejectClientResolutionが呼ばれました。");
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onAcceptClientResolution(int i) {
        Lg.d("onAcceptClientResolutionが呼ばれました。");
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onChangeMppControlMode(int i, D_MppUserInfo d_MppUserInfo) {
        Lg.d("onChangeMppControlModeが呼ばれました モード:" + i);
        this._bSelfProjection = i == 2 || i == 1 || i == 4;
        this._moderatorUserInfo = null;
        if (this._conCtrlr != null) {
            if (i == 2) {
                this._isMppLockedByModerator = false;
                this._mppControlMode = IOnConnectListener.MppControlMode.Collaboration;
            } else if (i == 3) {
                this._mppControlMode = IOnConnectListener.MppControlMode.ModeratorEntry;
                this._moderatorUserInfo = d_MppUserInfo;
            } else if (i == 4) {
                this._mppControlMode = IOnConnectListener.MppControlMode.ModeratorAdmin;
            } else {
                this._mppControlMode = IOnConnectListener.MppControlMode.CollaborationOld;
            }
            this._conCtrlr.onChangeMppControlMode(this._mppControlMode);
        }
        if (this._isTryModeratorWhenConnect) {
            if (this._mppControlMode == IOnConnectListener.MppControlMode.Collaboration) {
                if (hasMppModeratorPassword(new ArrayList<>())) {
                    this._conCtrlr.createModeratorPassInputDialog(true);
                } else if (setMppControlMode(1) != 0) {
                    this._conCtrlr.createAlreadyModeratorDialog();
                }
            } else if (this._mppControlMode == IOnConnectListener.MppControlMode.ModeratorEntry) {
                this._conCtrlr.createAlreadyModeratorDialog();
            }
            this._isTryModeratorWhenConnect = false;
        } else if (this._isCallSetMppControlMode) {
            if (this._mppControlMode == IOnConnectListener.MppControlMode.ModeratorEntry) {
                this._conCtrlr.createAlreadyModeratorDialog();
            } else {
                this._conCtrlr.showStackDialog();
            }
            this._isCallSetMppControlMode = false;
        }
        MirroringEntrance.INSTANCE.onChangeMPPControlMode();
        this._projectionMode = 1;
        enableAudioTransfer(-1);
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onUpdateMppUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2) {
        if (arrayList != null && arrayList.size() >= 1) {
            this._myUniqueId = Long.valueOf(arrayList.get(0).uniqueId);
        }
        this._mppUserList = arrayList;
        this._mppLayout = arrayList2;
        this._conCtrlr.onUpdateMppUserList(arrayList, arrayList2);
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onChangeMppLayout(ArrayList<D_MppLayoutInfo> arrayList) {
        this._mppLayout = arrayList;
        this._conCtrlr.onChangeMppLayout(arrayList);
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onDisconnectFromAdmin() {
        Lg.d("onDisconnectFromAdminが呼ばれました。");
        LockedPjInfo lockedPjInfo = this._lockedPjInfo;
        onConnectRet(3, lockedPjInfo == null ? -1 : lockedPjInfo.getNowConnectingPjID(), Engine.ENGINE_INFO_DISCONNECTED_ADMIN);
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onChangeScreenLockStatus(boolean z) {
        this._isMppLockedByModerator = z;
        this._conCtrlr.onChangeScreenLockStatus(z);
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onReceiveDelivery(D_DeliveryInfo d_DeliveryInfo) {
        int i = d_DeliveryInfo.command;
        if (i == 1) {
            String createWhitePaper = DeliveryFileIO.getIns().createWhitePaper(this._context, d_DeliveryInfo.whitePaperWidth, d_DeliveryInfo.whitePaperHeight);
            if (createWhitePaper == null) {
                this._conCtrlr.clearDeliveringDialog();
                return;
            }
            this._conCtrlr.onDeliverImage(createWhitePaper, new D_DeliveryPermission(d_DeliveryInfo));
        } else if (i == 2) {
            if (d_DeliveryInfo.validControlParmission) {
                this._conCtrlr.onChangeDeliveryPermission(d_DeliveryInfo.enableWirte, d_DeliveryInfo.enableSave, d_DeliveryInfo.enableChangeUI);
            }
        } else if (i == 3) {
            this._conCtrlr.onFinishDelivery();
        } else if (i == 8) {
            String save = DeliveryFileIO.getIns().save(this._context, d_DeliveryInfo);
            if (save == null) {
                this._conCtrlr.clearDeliveringDialog();
                return;
            }
            this._conCtrlr.onDeliverImage(save, new D_DeliveryPermission(d_DeliveryInfo));
            d_DeliveryInfo.buffer = null;
        } else {
            Lg.w("unknown command:" + d_DeliveryInfo.command);
        }
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onReceiveDeliveryError(D_DeliveryError d_DeliveryError) {
        try {
            this._conCtrlr.onDeliveryError(d_DeliveryError);
        } catch (NullPointerException unused) {
            Lg.e("connect controller is null.");
        }
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onStartDelivery() {
        try {
            this._conCtrlr.onStartDelivery();
        } catch (NullPointerException unused) {
            Lg.e("connect controller is null.");
        }
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onEndDelivery() {
        try {
            this._conCtrlr.onEndDelivery();
        } catch (NullPointerException unused) {
            Lg.e("connect controller is null.");
        }
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onReceiveThumbnail(D_ThumbnailInfo d_ThumbnailInfo) {
        this._conCtrlr.onThumbnailInfo(d_ThumbnailInfo);
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onReceiveThumbnailError(D_ThumbnailError d_ThumbnailError) {
        this._conCtrlr.onThumbnailError(d_ThumbnailError);
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onNotifySharedWbPin(int i, byte[] bArr) {
        ArrayList<ConnectPjInfo> tryConnectingPjInfo;
        LockedPjInfo lockedPjInfo = this._lockedPjInfo;
        if (lockedPjInfo == null || (tryConnectingPjInfo = lockedPjInfo.getTryConnectingPjInfo()) == null) {
            return;
        }
        Iterator<ConnectPjInfo> it = tryConnectingPjInfo.iterator();
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (next.getPjInfo().ProjectorID == i) {
                next.getPjInfo().sharedWbPin = null;
                if (bArr != null) {
                    next.getPjInfo().sharedWbPin = new byte[bArr.length];
                    System.arraycopy(bArr, 0, next.getPjInfo().sharedWbPin, 0, bArr.length);
                }
            }
        }
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onNotifyModeratorPassword(int i, boolean z) {
        ArrayList<ConnectPjInfo> tryConnectingPjInfo;
        LockedPjInfo lockedPjInfo = this._lockedPjInfo;
        if (lockedPjInfo == null || (tryConnectingPjInfo = lockedPjInfo.getTryConnectingPjInfo()) == null) {
            return;
        }
        Iterator<ConnectPjInfo> it = tryConnectingPjInfo.iterator();
        while (it.hasNext()) {
            ConnectPjInfo next = it.next();
            if (next.getPjInfo().ProjectorID == i) {
                registerHasModeratorPassword(i, z);
                next.getPjInfo().hasModeratorPassword = z;
            }
        }
    }

    private void registerHasModeratorPassword(int i, boolean z) {
        _hasModeratorPasswordMap.put(Integer.valueOf(i), Boolean.valueOf(z));
    }

    private void unregisterHasModeratorPassword() {
        Map<Integer, Boolean> map = _hasModeratorPasswordMap;
        if (map.isEmpty()) {
            return;
        }
        map.clear();
    }

    public boolean hasModeratorPassword(int i) {
        Map<Integer, Boolean> map = _hasModeratorPasswordMap;
        if (map.containsKey(Integer.valueOf(i))) {
            return map.get(Integer.valueOf(i)).booleanValue();
        }
        return false;
    }

    @Override // com.epson.iprojection.engine.common.OnPjEventListener
    public void onNotifyImageProcTime(D_ImageProcTime d_ImageProcTime) {
        this._conCtrlr.onNotifyImageProcTime(d_ImageProcTime);
    }

    /* loaded from: classes.dex */
    public class PjConnector implements IPjManualSearchResultListener {
        final ArrayList<ConnectPjInfo> _connectPjInfo;

        public PjConnector(ArrayList<ConnectPjInfo> arrayList) {
            Pj.this = r1;
            ArrayList<ConnectPjInfo> arrayList2 = new ArrayList<>();
            this._connectPjInfo = arrayList2;
            arrayList2.addAll(arrayList);
        }

        public boolean connect(boolean z) {
            int readInt;
            Lg.i("PJ size:" + this._connectPjInfo.size());
            Pj.this._engine.SetEncryption(PrefUtils.readInt(Pj.this._context, PrefTagDefine.conPJ_PROJECTION_WITH_ENCRYPT_TAG) == 1);
            int readInt2 = PrefUtils.readInt(Pj.this._context, PrefTagDefine.conPJ_UPLOAD_PJ_LOG);
            if (readInt2 != Integer.MIN_VALUE) {
                Pj.this._engine.SetProjectorLogUpload(readInt2 == 1);
            }
            int readInt3 = PrefUtils.readInt(Pj.this._context, PrefTagDefine.conPJ_BAND_WIDTH);
            if (readInt3 != Integer.MIN_VALUE) {
                Pj.this._engine.SetBandWidth(eBandWidth.values()[readInt3]);
                if (!WebRTCUtils.INSTANCE.shouldUseWebRTC(Pj.this._context, Pj.getIns()._lockedPjInfo._tryConnectingPjInf)) {
                    Pj.this._engine.SetAudioTransfer(false);
                    if (!AudioUtils.Companion.isBandCapableOfUsingAudio(readInt3)) {
                        Pj.this.disableAudioTransfer();
                    } else {
                        int readInt4 = PrefUtils.readInt(Pj.this._context, PrefTagDefine.conPJ_AUDIO_TRANSFER_TAG);
                        if (readInt4 != Integer.MIN_VALUE) {
                            if (readInt4 == 1) {
                                Pj.this.enableAudioTransfer(this._connectPjInfo.size());
                            } else {
                                Pj.this.disableAudioTransfer();
                            }
                        }
                    }
                }
            }
            if (WebRTCUtils.INSTANCE.shouldUseWebRTC(Pj.this._context, Pj.getIns()._lockedPjInfo._tryConnectingPjInf) && (readInt = PrefUtils.readInt(Pj.this._context, PrefTagDefine.conPJ_AUDIO_TRANSFER_TAG)) != Integer.MIN_VALUE) {
                if (readInt == 1) {
                    Pj.this.enableAudioTransferForWebRTC(Pj.getIns()._lockedPjInfo._tryConnectingPjInf.size());
                } else {
                    Pj.this.disableAudioTransferForWebRTC();
                }
            }
            ArrayList<D_AddFixedSearchInfo> arrayList = new ArrayList<>();
            for (int i = 0; i < this._connectPjInfo.size(); i++) {
                ConnectPjInfo connectPjInfo = this._connectPjInfo.get(i);
                if (connectPjInfo.getPjInfo().isSupportedScreenType || !Pj.this.hasUniqueInfo(connectPjInfo) || Pj.this._needManualSearchBeforeConnect) {
                    Lg.d("スクリーンタイプに対応しているかユニークインフォがないか履歴検索なので、まず指定検索します");
                    if (Pj.this._pjFinder != null) {
                        Pj.this._pjFinder.addToAddFixedSearchInfoArray(arrayList, connectPjInfo.getPjInfo().IPAddr, connectPjInfo.getPjInfo().ProjectorID);
                    }
                }
            }
            if (!arrayList.isEmpty() && !z) {
                Pj.super.setConnectState(StateMachine.ConnectState.PreTryConnectManualSearching);
                Lg.d("スクリーンタイプに対応しているから、直前に一度検索します。");
                if (Pj.this._pjFinder != null) {
                    Lg.d("manualSearchWithFixedInfo");
                    Pj.this._pjFinder.manualSearchWithFixedInfo(this, arrayList, true);
                }
                return true;
            }
            Lg.d("スクリーンタイプに対応していないので、すぐに接続します");
            return Pj.this.doConnect(this._connectPjInfo);
        }

        @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
        public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
            Lg.d("onFindSearchPj : " + d_PjInfo.PrjName);
            if (z) {
                Pj.this._engine.EndSearchPj();
            }
            for (int i = 0; i < this._connectPjInfo.size(); i++) {
                ConnectPjInfo connectPjInfo = this._connectPjInfo.get(i);
                if (Arrays.equals(connectPjInfo.getPjInfo().IPAddr, d_PjInfo.IPAddr)) {
                    connectPjInfo.setPjInfo(d_PjInfo);
                }
            }
            if (z) {
                Lg.d("bAllEnd is true");
                if (Pj.this._pjFinder != null) {
                    Pj.this._pjFinder.disableManualSearchListener();
                }
                Lg.d("doConnect");
                Pj.this.doConnect(this._connectPjInfo);
            }
        }

        @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
        public void onEndSearchPj() {
            Lg.d("onEndSearchPj");
            Pj.this._engine.EndSearchPj();
            Pj.this._conCtrlr.onConnectFail(-1, IOnConnectListener.FailReason.Default);
            Pj.this._conCtrlr.onConnectFailForGA(IOnConnectListener.FailReason.Default);
            Pj.super.setConnectState(StateMachine.ConnectState.Default);
            if (Pj.this._pjFinder == null || !Pj.this._pjFinder.isUsableListener()) {
                return;
            }
            Pj.this._pjFinder.startSearch();
        }
    }

    /* loaded from: classes.dex */
    public class DisconnectChecker implements Runnable {
        DisconnectChecker() {
            Pj.this = r1;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = 200;
            while (Pj.this._connectState == StateMachine.ConnectState.TryDisconnecting && i > 0) {
                Sleeper.sleep(100L);
                i--;
                if (i <= 0) {
                    Pj.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.Pj$DisconnectChecker$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Pj.getIns().onDisconnect(-1, IOnConnectListener.DisconedReason.EngineError);
                        }
                    });
                    return;
                }
            }
        }
    }

    public void startCyclicSeachForRegisted() {
        PjCyclicSearchThreadForRegisted pjCyclicSearchThreadForRegisted = this._cyclicSearchForRegisted;
        if (pjCyclicSearchThreadForRegisted != null) {
            pjCyclicSearchThreadForRegisted.finish();
        }
        PjCyclicSearchThreadForRegisted pjCyclicSearchThreadForRegisted2 = new PjCyclicSearchThreadForRegisted();
        this._cyclicSearchForRegisted = pjCyclicSearchThreadForRegisted2;
        pjCyclicSearchThreadForRegisted2.start();
    }

    public boolean stopCyclicSearchForRegisted() {
        PjCyclicSearchThreadForRegisted pjCyclicSearchThreadForRegisted = this._cyclicSearchForRegisted;
        if (pjCyclicSearchThreadForRegisted != null) {
            boolean finish = pjCyclicSearchThreadForRegisted.finish();
            this._cyclicSearchForRegisted = null;
            return finish;
        }
        return false;
    }

    public boolean isEqualFindPjListener(OnFindPjListener onFindPjListener) {
        return this._engine.isEqualFindPjListener(onFindPjListener);
    }

    private String createString(char[] cArr) {
        StringBuilder sb = new StringBuilder();
        for (char c : cArr) {
            if (c == 0) {
                return sb.toString();
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public void addAudioData(byte[] bArr) {
        synchronized (_audioBuffer) {
            for (byte b : bArr) {
                _audioBuffer.add(Byte.valueOf(b));
            }
        }
    }

    public void clearAudioData() {
        LinkedList<Byte> linkedList = _audioBuffer;
        synchronized (linkedList) {
            linkedList.clear();
        }
    }

    public static byte[] getAudioData(int i) {
        LinkedList<Byte> linkedList = _audioBuffer;
        synchronized (linkedList) {
            try {
                try {
                    if (linkedList.size() < i) {
                        return null;
                    }
                    byte[] bArr = new byte[i];
                    for (int i2 = 0; i2 < i; i2++) {
                        bArr[i2] = _audioBuffer.pop().byteValue();
                    }
                    return bArr;
                } catch (Exception unused) {
                    Lg.e("getAudioDataのpopでException発生！！");
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private Pj() {
    }

    public static Pj getIns() {
        return _inst;
    }
}
