package com.epson.iprojection.ui.activities.remote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import androidx.appcompat.widget.Toolbar;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.customer_satisfaction.entities.EState;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.FragmentPjControlNG;
import com.epson.iprojection.ui.activities.remote.views.IgnoreSwipeViewPager;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.common.uiparts.IWebChromeClient;
import com.epson.iprojection.ui.common.uiparts.MakeUnpressableLayer;
import com.epson.iprojection.ui.common.uiparts.ProgressDialogInfinityType;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Activity_Remote extends PjConnectableActivity implements IFragmentPjControlListener, IDigestAuthResultListener, IWebPageListener, IOnClickRemoteActionBar, IWebLoadRetryListener, IWebChromeClient, IOnLockBatchAuthenticationListener, ICurrentFragmentChecker {
    public static final String TAG_AFTER_DISCONNECT_USE = "tag_use_remocon_after";
    private static final String TAG_ALREADY_SHOWED_BALLOON_DIALOG = "tag_already_showed_balloon_dialog";
    private ActionBarRemote _actionBar;
    private WebviewFragmentAdapter _adapter;
    private Dialog _balloonDialog;
    private byte[] _beforeMacAddr;
    private int _currentPosition;
    private DigestAuthThread _digestAuthThread;
    private Handler _handler;
    private boolean _isAllPjAuthenticating;
    private boolean _isHeadPjAuthenticating;
    private boolean _isLockedBatchAuthentication;
    private boolean _keepBefore;
    private IgnoreSwipeViewPager _mVPager;
    private D_HistoryInfo.PasswordType _passwordType;
    private ProgressDialogInfinityType _progressDialog;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ValueCallback<Uri> mUploadMessage;
    private ArrayList<D_HistoryInfo> _pjList = new ArrayList<>();
    private ArrayList<String> _connectingPJList = new ArrayList<>();
    private ArrayList<String> _registeredPjList = new ArrayList<>();
    private String _registeredTags = null;

    @Override // com.epson.iprojection.ui.activities.remote.IFragmentPjControlListener
    public void onFragmentResume() {
    }

    @Override // com.epson.iprojection.ui.activities.remote.IFragmentPjControlListener
    public void onFragmentViewCreated() {
    }

    @Override // com.epson.iprojection.ui.activities.remote.IWebPageListener
    public void onStartLoad(WebView webView, D_HistoryInfo d_HistoryInfo, String str) {
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_conpj_pjcontrol);
        pushDrawerStatus(eDrawerMenuItem.Remote);
        initialize();
        if (getIntent().getStringExtra(IntentTagDefine.LAUNCH_ROUTE_TAG) != null) {
            new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.Activity_Remote$$ExternalSyntheticLambda4
                {
                    Activity_Remote.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    Activity_Remote.this.m171x9bd9cdfd();
                }
            }.run();
        }
    }

    /* renamed from: lambda$onCreate$0$com-epson-iprojection-ui-activities-remote-Activity_Remote */
    public /* synthetic */ void m171x9bd9cdfd() {
        this._csController.onChangeState(EState.USING);
        this._csController.onChangeState(EState.COLLECTING_LAUNCH);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, android.app.Activity
    public void onRestart() {
        super.onRestart();
        if (isPjStateChanged()) {
            dismissAllAuthDialog();
            initializeMember();
            initialize();
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        FragmentPjControlBase item = this._adapter.getItem(getCurrentIndex());
        if (item != null) {
            item.resume();
        }
        ((MakeUnpressableLayer) findViewById(R.id.layout_make_unpressable)).update(this, Pj.getIns().getMppControlMode(), Pj.getIns().getModeratorUserInfo());
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        FragmentPjControlBase item = this._adapter.getItem(getCurrentIndex());
        if (item != null) {
            item.pause();
        }
        this._connectingPJList = Pj.getIns().getNowConnecingUniqeInfoList();
        this._registeredPjList = Pj.getIns().getNowRegisteredUniqeInfoList();
        this._registeredTags = RemotePrefUtils.getAuthenticatedData(this);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        popDrawerStatus();
        removeFragmentAll();
        super.onDestroy();
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void initialize() {
        this._handler = new Handler();
        this._mVPager = (IgnoreSwipeViewPager) findViewById(R.id.main_conpj_remote_pager);
        WebviewFragmentAdapter webviewFragmentAdapter = new WebviewFragmentAdapter(getSupportFragmentManager());
        this._adapter = webviewFragmentAdapter;
        this._mVPager.setAdapter(webviewFragmentAdapter);
        this._mVPager.setOffscreenPageLimit(17);
        createToolBar();
        this._actionBar.invisible();
        setPjList();
        if ((!Pj.getIns().isConnected() && !Pj.getIns().isRegistered()) || this._pjList.isEmpty()) {
            this._adapter.addFragment(FragmentPjControlNG.newInstance(null, FragmentPjControlNG.MessageType.TYPE_NO_SELECT));
            updateActionBar();
            return;
        }
        createInitialScreenForFragment(this._pjList);
        createFragmentPjControl(this._pjList.get(0));
    }

    private void initializeMember() {
        this._mVPager = null;
        this._adapter = null;
        this._currentPosition = 0;
        this._pjList = new ArrayList<>();
        this._handler = null;
        this._progressDialog = null;
        this._isAllPjAuthenticating = false;
        this._isHeadPjAuthenticating = false;
        this._passwordType = null;
        this._keepBefore = false;
        this._beforeMacAddr = null;
        this.mUploadMessage = null;
        this.mFilePathCallback = null;
        this._isLockedBatchAuthentication = false;
        D_PjInfo.PjListType pjListType = D_PjInfo.PjListType.LIST_PJ_TYPE_NONE;
    }

    private void dismissAllAuthDialog() {
        WebviewFragmentAdapter webviewFragmentAdapter = this._adapter;
        if (webviewFragmentAdapter == null) {
            return;
        }
        Iterator<FragmentPjControlBase> it = webviewFragmentAdapter.getFragmentList().iterator();
        while (it.hasNext()) {
            FragmentPjControlBase next = it.next();
            if (next instanceof FragmentPjControlWeb) {
                ((FragmentPjControlWeb) next).dismissAuthDialog();
            }
        }
    }

    private boolean isPjStateChanged() {
        if (this._connectingPJList.equals(Pj.getIns().getNowConnecingUniqeInfoList()) && this._registeredPjList.equals(Pj.getIns().getNowRegisteredUniqeInfoList())) {
            String str = this._registeredTags;
            if (str != null) {
                return !str.equals(RemotePrefUtils.getAuthenticatedData(this));
            }
            return false;
        }
        return true;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDisconnectOne(int i, IOnConnectListener.DisconedReason disconedReason) {
        updateView();
        super.onDisconnectOne(i, disconedReason);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason, boolean z) {
        updateView();
        super.onDisconnect(i, disconedReason, z);
        onChangeMPPControlMode(Pj.getIns().getMppControlMode());
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onChangeMPPControlMode(IOnConnectListener.MppControlMode mppControlMode) {
        super.onChangeMPPControlMode(mppControlMode);
        setEnableControl(mppControlMode != IOnConnectListener.MppControlMode.ModeratorEntry);
        ((MakeUnpressableLayer) findViewById(R.id.layout_make_unpressable)).update(this, Pj.getIns().getMppControlMode(), Pj.getIns().getModeratorUserInfo());
    }

    private void setEnableControl(boolean z) {
        if (z) {
            this._actionBar.enable();
            this._adapter.getItem(getCurrentIndex()).enable();
            return;
        }
        this._actionBar.disable();
        this._adapter.getItem(getCurrentIndex()).disable();
    }

    private void createInitialScreenForFragment(ArrayList<D_HistoryInfo> arrayList) {
        Iterator<D_HistoryInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            createFragmentProgress(it.next());
        }
    }

    private void resetInitialScreenForFragment(ArrayList<D_HistoryInfo> arrayList) {
        Iterator<D_HistoryInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            D_HistoryInfo next = it.next();
            if (this._adapter.findPjOnFragmentList(next) instanceof FragmentPjControlNG) {
                createFragmentProgress(next);
            }
        }
    }

    private void addFragment(FragmentPjControlBase fragmentPjControlBase) {
        if (fragmentPjControlBase instanceof FragmentPjControlBatch) {
            if (this._adapter.isExistOnFragmentList(fragmentPjControlBase)) {
                return;
            }
            this._adapter.addFragment(fragmentPjControlBase);
            endSinglePjAuthentication();
            this._currentPosition = this._adapter.getIndex(fragmentPjControlBase);
            return;
        }
        FragmentPjControlBase findPjOnFragmentList = this._adapter.findPjOnFragmentList(fragmentPjControlBase.getPjInfo());
        if (findPjOnFragmentList == null) {
            this._adapter.addFragment(fragmentPjControlBase);
        } else if (Arrays.equals(findPjOnFragmentList.getPjInfo().macAddr, fragmentPjControlBase.getPjInfo().macAddr)) {
            WebviewFragmentAdapter webviewFragmentAdapter = this._adapter;
            webviewFragmentAdapter.updateFragment(fragmentPjControlBase, webviewFragmentAdapter.getIndex(findPjOnFragmentList));
        }
    }

    private void changeFragment(FragmentPjControlBase fragmentPjControlBase) {
        if (this._adapter.getCount() != 0) {
            this._adapter.notifyDataSetChanged();
            setCurrentItem(getCurrentIndex());
        }
    }

    private void removeFragment(FragmentPjControlBase fragmentPjControlBase) {
        this._adapter.removeFragment(fragmentPjControlBase);
    }

    private void removeFragmentAll() {
        IgnoreSwipeViewPager ignoreSwipeViewPager;
        WebviewFragmentAdapter webviewFragmentAdapter = this._adapter;
        if (webviewFragmentAdapter == null || (ignoreSwipeViewPager = this._mVPager) == null) {
            return;
        }
        webviewFragmentAdapter.removeAllFragment(ignoreSwipeViewPager);
    }

    private void createFragmentPjControlWeb(D_HistoryInfo d_HistoryInfo, boolean z, boolean z2) {
        if (this._isAllPjAuthenticating) {
            onLockedBatchAuthentication();
        }
        FragmentPjControlWeb newInstance = FragmentPjControlWeb.newInstance(d_HistoryInfo, z, z2);
        newInstance.setOnLockBatchAuthenticationListener(this);
        addFragment(newInstance);
    }

    private void createFragmentPjControlOld(D_HistoryInfo d_HistoryInfo) {
        addFragment(FragmentPjControlOld.newInstance(d_HistoryInfo));
    }

    private void createFragmentPjControlNG(D_HistoryInfo d_HistoryInfo, FragmentPjControlNG.MessageType messageType) {
        addFragment(FragmentPjControlNG.newInstance(d_HistoryInfo, messageType));
    }

    private void createFragmentProgress(D_HistoryInfo d_HistoryInfo) {
        addFragment(FragmentPjControlProgress.newInstance(d_HistoryInfo));
    }

    private void createFragmentPjControlBatch() {
        addFragment(FragmentPjControlBatch.newInstance(null, this._pjList));
    }

    private void createFragmentPjControl(D_HistoryInfo d_HistoryInfo) {
        if (d_HistoryInfo.isSupportRemote()) {
            if (this._isAllPjAuthenticating) {
                createFragmentProgress(d_HistoryInfo);
                return;
            } else {
                m168xaca46444(d_HistoryInfo);
                return;
            }
        }
        createFragmentPjControlOld(d_HistoryInfo);
    }

    private void createToolBar() {
        if (this._actionBar == null) {
            ActionBarRemote actionBarRemote = new ActionBarRemote(this);
            this._actionBar = actionBarRemote;
            actionBarRemote.setOnClickRemoteActionBar(this);
            this._baseActionBar = this._actionBar;
            this._actionBar.layout(R.layout.toolbar_remote);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRemote);
        if (!Pj.getIns().isAllPjTypeSignage()) {
            this._drawerList.enableDrawerToggleButton(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            return;
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            this._drawerList.setDrawerToggleButtonEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private void setPjList() {
        int size = this._pjList.size();
        this._pjList.clear();
        setPjListFromRegisteredPjList();
        if (this._pjList.size() == 0) {
            setPjListFromNowConnectingPJList();
        }
        if (size == 0 || size != this._pjList.size()) {
            ArrayList arrayList = new ArrayList();
            Iterator<D_HistoryInfo> it = this._pjList.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().pjName);
            }
            this._actionBar.setPjList(arrayList);
        }
    }

    private boolean isThisBatch(int i) {
        return i == this._pjList.size() && needBatchFragment();
    }

    private boolean needBatchFragment() {
        return !Pj.getIns().isAllPjTypeBusiness() && Pj.getIns().getRegisteredPjList().size() > 1;
    }

    private void setPjListFromRegisteredPjList() {
        Iterator<ConnectPjInfo> it = Pj.getIns().getRegisteredPjList().iterator();
        while (it.hasNext()) {
            this._pjList.add(D_HistoryInfo.convertFromPjInfo(it.next().getPjInfo()));
        }
    }

    private void setPjListFromNowConnectingPJList() {
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        if (nowConnectingPJList != null) {
            Iterator<ConnectPjInfo> it = nowConnectingPJList.iterator();
            while (it.hasNext()) {
                this._pjList.add(D_HistoryInfo.convertFromPjInfo(it.next().getPjInfo()));
            }
        }
    }

    private void updateView() {
        if (this._isAllPjAuthenticating) {
            return;
        }
        setPjList();
        showRemote();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity
    public void updateActionBar() {
        super.updateActionBar();
        FragmentPjControlBase item = this._adapter.getItem(this._currentPosition);
        this._actionBar.setVisibilityTouchPadButtonState((item == null || !item.isAuthenticated() || Pj.getIns().getMppControlMode() == IOnConnectListener.MppControlMode.ModeratorEntry) ? false : true);
        int spinnerPjListPosition = this._actionBar.getSpinnerPjListPosition();
        if (spinnerPjListPosition == this._currentPosition || spinnerPjListPosition == -1) {
            this._actionBar.initializeTouchPadState();
            if (Pj.getIns().isAllPjTypeSignage()) {
                this._drawerList.disable();
            }
            if (!isAlreadyBalloonDialogShowed()) {
                if (this._actionBar.isButtonTouchPadEnable()) {
                    showBalloonDialog();
                }
            } else if (D_PjInfo.PjListType.LIST_PJ_TYPE_NONE == Pj.getIns().getPjListType() || !this._actionBar.isButtonTouchPadEnable()) {
                dismissBalloonDialog();
            }
        }
    }

    private void showRemote() {
        if (!Pj.getIns().isConnected() && !Pj.getIns().isRegistered()) {
            removeFragmentAll();
            createFragmentPjControlNG(null, FragmentPjControlNG.MessageType.TYPE_NO_SELECT);
            updateActionBar();
            return;
        }
        int spinnerPjListPosition = this._actionBar.getSpinnerPjListPosition();
        int i = 0;
        if (spinnerPjListPosition < 0) {
            this._actionBar.setSpinnerPjListPosition(0);
            setCurrentItem(0);
            return;
        }
        if (isThisBatch(spinnerPjListPosition)) {
            if (isCompleteAuthentication() || isAllAuthenticated()) {
                if (this._adapter.getItem(spinnerPjListPosition) == null) {
                    createFragmentPjControlBatch();
                }
                setCurrentItem(getCurrentIndex());
            } else {
                while (true) {
                    if (i >= this._pjList.size()) {
                        break;
                    } else if (Arrays.equals(this._beforeMacAddr, this._pjList.get(i).macAddr)) {
                        this._keepBefore = true;
                        break;
                    } else {
                        i++;
                    }
                }
            }
        } else {
            D_HistoryInfo d_HistoryInfo = this._pjList.get(spinnerPjListPosition);
            FragmentPjControlBase findPjOnFragmentList = this._adapter.findPjOnFragmentList(d_HistoryInfo);
            if (findPjOnFragmentList != null && findPjOnFragmentList.getPjInfo() != null && Arrays.equals(findPjOnFragmentList.getPjInfo().macAddr, d_HistoryInfo.macAddr)) {
                if (findPjOnFragmentList instanceof FragmentPjControlProgress) {
                    createFragmentPjControl(d_HistoryInfo);
                } else {
                    changeFragment(findPjOnFragmentList);
                }
            }
        }
        updateActionBar();
    }

    @Override // com.epson.iprojection.ui.activities.remote.IOnClickRemoteActionBar
    public boolean canChangeTouchPad() {
        if (isThisBatch(this._currentPosition)) {
            this._actionBar.disableTouchPadButton();
            return false;
        }
        FragmentPjControlBase item = this._adapter.getItem(getCurrentIndex());
        if (item == null) {
            return false;
        }
        return item.haveFlipper();
    }

    @Override // com.epson.iprojection.ui.activities.remote.IOnClickRemoteActionBar
    public void onClickTouchPadButton(boolean z) {
        FragmentPjControlBase item = this._adapter.getItem(getCurrentIndex());
        if (item != null) {
            item.changeFlipper(z, false);
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.IOnClickRemoteActionBar
    public void onSpinnerPjListItemSelected(int i) {
        if (this._currentPosition == i) {
            return;
        }
        if (isThisBatch(i)) {
            FragmentPjControlBase item = this._adapter.getItem(getCurrentIndex());
            if (item != null && item.getPjInfo() != null) {
                this._beforeMacAddr = item.getPjInfo().macAddr;
            }
            this._currentPosition = i;
            FragmentPjControlBase item2 = this._adapter.getItem(i);
            if (item2 instanceof FragmentPjControlBatch) {
                if (isAllAuthenticated()) {
                    changeFragment(item2);
                    showRemote();
                    return;
                }
                resetInitialScreenForFragment(this._pjList);
                setCurrentItem(this._currentPosition);
                startAllPjAuthentication();
                return;
            } else if (this._isAllPjAuthenticating) {
                showAuthenticationDialog();
                return;
            } else if (isAllAuthenticated()) {
                createFragmentPjControlBatch();
                setCurrentItem(this._currentPosition);
                showRemote();
                return;
            } else {
                resetInitialScreenForFragment(this._pjList);
                setCurrentItem(this._currentPosition);
                startAllPjAuthentication();
                return;
            }
        }
        this._currentPosition = i;
        if (!this._keepBefore) {
            D_HistoryInfo d_HistoryInfo = this._pjList.get(i);
            FragmentPjControlBase findPjOnFragmentList = this._adapter.findPjOnFragmentList(d_HistoryInfo);
            if (findPjOnFragmentList != null) {
                if (!findPjOnFragmentList.canPjControl() && ((findPjOnFragmentList instanceof FragmentPjControlProgress) || (findPjOnFragmentList instanceof FragmentPjControlNG))) {
                    setCurrentItem(this._currentPosition);
                    createFragmentPjControl(d_HistoryInfo);
                }
            } else if (Pj.getIns().isAllPjTypeBusiness()) {
                createFragmentPjControl(d_HistoryInfo);
            } else {
                createFragmentProgress(d_HistoryInfo);
            }
        }
        this._beforeMacAddr = null;
        this._keepBefore = false;
        showRemote();
    }

    private void startAllPjAuthentication() {
        this._isAllPjAuthenticating = true;
        this._isHeadPjAuthenticating = true;
        if (!needAllPjAuthentication()) {
            endAllPjAuthentication();
            return;
        }
        showAuthenticationDialog();
        nextAllPjAuthentication();
    }

    private void nextAllPjAuthentication() {
        if (this._isAllPjAuthenticating) {
            D_HistoryInfo nextAuthenticationPjInfo = getNextAuthenticationPjInfo();
            if (nextAuthenticationPjInfo == null) {
                clearAuthenticationInfo();
                nextAuthenticationPjInfo = getNextAuthenticationPjInfo();
                if (nextAuthenticationPjInfo == null) {
                    endAllPjAuthentication();
                    return;
                }
            }
            if (this._isHeadPjAuthenticating) {
                setCurrentItem(this._adapter.getIndex(nextAuthenticationPjInfo.macAddr));
            }
            if (nextAuthenticationPjInfo.isSupportRemote()) {
                doNextAuthenticate(nextAuthenticationPjInfo);
            } else {
                createFragmentPjControlOld(nextAuthenticationPjInfo);
            }
        }
    }

    private void doNextAuthenticate(final D_HistoryInfo d_HistoryInfo) {
        final Handler handler = new Handler();
        new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.Activity_Remote$$ExternalSyntheticLambda1
            {
                Activity_Remote.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                Activity_Remote.this.m169xb2a82fa3(handler, d_HistoryInfo);
            }
        }).start();
    }

    /* renamed from: lambda$doNextAuthenticate$2$com-epson-iprojection-ui-activities-remote-Activity_Remote */
    public /* synthetic */ void m169xb2a82fa3(Handler handler, final D_HistoryInfo d_HistoryInfo) {
        while (this._isAllPjAuthenticating) {
            if (!this._isLockedBatchAuthentication) {
                handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.Activity_Remote$$ExternalSyntheticLambda3
                    {
                        Activity_Remote.this = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        Activity_Remote.this.m168xaca46444(d_HistoryInfo);
                    }
                });
                return;
            }
            Sleeper.sleep(1000L);
        }
    }

    private D_HistoryInfo getNextAuthenticationPjInfo() {
        if (isNowShowingFragmentProgress()) {
            D_HistoryInfo pjInfo = this._adapter.getItem(getCurrentIndex()).getPjInfo();
            if (this._passwordType != pjInfo.getPasswordType()) {
                clearAuthenticationInfo();
                this._passwordType = pjInfo.getPasswordType();
            }
            return pjInfo;
        }
        Iterator<D_HistoryInfo> it = this._pjList.iterator();
        while (it.hasNext()) {
            D_HistoryInfo next = it.next();
            D_HistoryInfo.PasswordType passwordType = this._passwordType;
            if (passwordType == null || passwordType == next.getPasswordType()) {
                FragmentPjControlBase findPjOnFragmentList = this._adapter.findPjOnFragmentList(next);
                if (!((findPjOnFragmentList == null || findPjOnFragmentList.getPjInfo() == null) ? false : findPjOnFragmentList.isAuthenticated())) {
                    this._passwordType = next.getPasswordType();
                    return next;
                }
            }
        }
        return null;
    }

    private boolean isNowShowingFragmentProgress() {
        if (this._currentPosition >= this._pjList.size()) {
            return false;
        }
        return this._adapter.getItem(getCurrentIndex()) instanceof FragmentPjControlProgress;
    }

    private void endAllPjAuthentication() {
        if (!this._isAllPjAuthenticating) {
            dismissAuthenticationDialog();
            return;
        }
        this._isAllPjAuthenticating = false;
        this._isHeadPjAuthenticating = false;
        clearAuthenticationInfo();
        dismissAuthenticationDialog();
        updateView();
    }

    private void endSinglePjAuthentication() {
        this._isHeadPjAuthenticating = false;
        if (!isThisBatch(this._currentPosition)) {
            dismissAuthenticationDialog();
        }
        updateView();
    }

    /* renamed from: authentication */
    public void m168xaca46444(D_HistoryInfo d_HistoryInfo) {
        DigestAuthThread digestAuthThread = this._digestAuthThread;
        if (digestAuthThread == null) {
            DigestAuthThread digestAuthThread2 = new DigestAuthThread(d_HistoryInfo, this);
            this._digestAuthThread = digestAuthThread2;
            digestAuthThread2.start();
            return;
        }
        if (Arrays.equals(d_HistoryInfo.macAddr, digestAuthThread.getPjInfo().macAddr)) {
            if (this._digestAuthThread.isWorking()) {
                return;
            }
            DigestAuthThread digestAuthThread3 = new DigestAuthThread(d_HistoryInfo, this);
            this._digestAuthThread = digestAuthThread3;
            digestAuthThread3.start();
            return;
        }
        DigestAuthThread digestAuthThread4 = new DigestAuthThread(d_HistoryInfo, this);
        this._digestAuthThread = digestAuthThread4;
        digestAuthThread4.start();
    }

    @Override // com.epson.iprojection.ui.activities.remote.IDigestAuthResultListener
    public void callbackDigestAuthResult(final D_HistoryInfo d_HistoryInfo, final boolean z, final boolean z2) {
        this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.Activity_Remote$$ExternalSyntheticLambda0
            {
                Activity_Remote.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                Activity_Remote.this.m167xdd98387(z2, d_HistoryInfo, z);
            }
        });
    }

    /* renamed from: lambda$callbackDigestAuthResult$3$com-epson-iprojection-ui-activities-remote-Activity_Remote */
    public /* synthetic */ void m167xdd98387(boolean z, D_HistoryInfo d_HistoryInfo, boolean z2) {
        if (isFinishing()) {
            return;
        }
        if (!z) {
            cancelWhenConnectionFails(d_HistoryInfo);
        } else {
            createFragmentPjControlWeb(d_HistoryInfo, z2, this._isAllPjAuthenticating);
        }
    }

    private boolean needAllPjAuthentication() {
        if (this._pjList.size() <= 1) {
            return false;
        }
        return !Pj.getIns().isAllPjTypeBusiness();
    }

    private boolean isCompleteAuthentication() {
        Iterator<FragmentPjControlBase> it = this._adapter.getFragmentList().iterator();
        while (it.hasNext()) {
            FragmentPjControlBase next = it.next();
            if (!isThisBatch(this._adapter.getIndex(next)) && (!next.isAuthenticated() || !next.canPjControl())) {
                return false;
            }
        }
        return true;
    }

    private void clearAuthenticationInfo() {
        this._passwordType = null;
    }

    private boolean isAllAuthenticated() {
        Iterator<D_HistoryInfo> it = this._pjList.iterator();
        while (it.hasNext()) {
            if (!RemotePrefUtils.isAuthenticated(this, it.next().macAddr)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.epson.iprojection.ui.activities.remote.IWebPageListener
    public void onFinishLoad(D_HistoryInfo d_HistoryInfo) {
        Iterator<FragmentPjControlBase> it = this._adapter.getFragmentList().iterator();
        while (it.hasNext()) {
            FragmentPjControlBase next = it.next();
            if (this._adapter.getIndex(next) == getCurrentIndex() && !isThisBatch(this._currentPosition)) {
                if (this._isHeadPjAuthenticating) {
                    endSinglePjAuthentication();
                } else {
                    changeFragment(next);
                }
            }
            if (next == this._adapter.findPjOnFragmentList(d_HistoryInfo)) {
                next.authenticated();
                this._registeredTags = RemotePrefUtils.getAuthenticatedData(this);
            }
        }
        updateActionBar();
        nextAllPjAuthentication();
    }

    @Override // com.epson.iprojection.ui.activities.remote.IWebPageListener
    public void onErrorLoad(WebView webView, D_HistoryInfo d_HistoryInfo, int i, String str, String str2) {
        createFragmentPjControlNG(d_HistoryInfo, FragmentPjControlNG.MessageType.TYPE_ERR_CONNECT);
        this._adapter.findPjOnFragmentList(d_HistoryInfo);
        if (this._isAllPjAuthenticating) {
            showConnectErrorDialog(d_HistoryInfo);
        }
        endAllPjAuthentication();
    }

    @Override // com.epson.iprojection.ui.activities.remote.IWebPageListener
    public void onAuthCanceled(final D_HistoryInfo d_HistoryInfo, final boolean z) {
        this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.Activity_Remote$$ExternalSyntheticLambda2
            {
                Activity_Remote.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                Activity_Remote.this.m170xed1e3534(d_HistoryInfo, z);
            }
        });
    }

    /* renamed from: lambda$onAuthCanceled$4$com-epson-iprojection-ui-activities-remote-Activity_Remote */
    public /* synthetic */ void m170xed1e3534(D_HistoryInfo d_HistoryInfo, boolean z) {
        endAllPjAuthentication();
        if (!isFinishing()) {
            createFragmentPjControlNG(d_HistoryInfo, FragmentPjControlNG.MessageType.TYPE_ERR_AUTH);
        }
        if (z) {
            WebviewFragmentAdapter webviewFragmentAdapter = this._adapter;
            this._currentPosition = webviewFragmentAdapter.getIndex(webviewFragmentAdapter.findPjOnFragmentList(d_HistoryInfo));
            setCurrentItem(getCurrentIndex());
            this._actionBar.setSpinnerPjListPosition(getCurrentIndex());
        }
        updateActionBar();
    }

    public void cancelWhenConnectionFails(D_HistoryInfo d_HistoryInfo) {
        if (this._isAllPjAuthenticating) {
            showConnectErrorDialog(d_HistoryInfo);
        }
        createFragmentPjControlNG(d_HistoryInfo, FragmentPjControlNG.MessageType.TYPE_ERR_CONNECT);
        endAllPjAuthentication();
        WebviewFragmentAdapter webviewFragmentAdapter = this._adapter;
        this._currentPosition = webviewFragmentAdapter.getIndex(webviewFragmentAdapter.findPjOnFragmentList(d_HistoryInfo));
        setCurrentItem(getCurrentIndex());
        this._actionBar.setSpinnerPjListPosition(getCurrentIndex());
        updateActionBar();
    }

    @Override // com.epson.iprojection.ui.activities.remote.IWebLoadRetryListener
    public void onWebLoadRetry(D_HistoryInfo d_HistoryInfo) {
        FragmentPjControlBase findPjOnFragmentList = this._adapter.findPjOnFragmentList(d_HistoryInfo);
        if (findPjOnFragmentList != null) {
            createFragmentPjControl(findPjOnFragmentList.getPjInfo());
        }
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public void setUploadMessage(ValueCallback<Uri> valueCallback) {
        this.mUploadMessage = valueCallback;
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public ValueCallback<Uri> getUploadMessage() {
        return this.mUploadMessage;
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public void setFilePathCallback(ValueCallback<Uri[]> valueCallback) {
        this.mFilePathCallback = valueCallback;
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public ValueCallback<Uri[]> getFilePathCallback() {
        return this.mFilePathCallback;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        String dataString;
        if (i != 1032) {
            super.onActivityResult(i, i2, intent);
        } else if (this.mFilePathCallback == null) {
            super.onActivityResult(i, i2, intent);
        } else {
            this.mFilePathCallback.onReceiveValue((i2 != -1 || (dataString = intent.getDataString()) == null) ? null : new Uri[]{Uri.parse(dataString)});
            this.mFilePathCallback = null;
        }
    }

    private void showConnectErrorDialog(D_HistoryInfo d_HistoryInfo) {
        if (isActivityAlive()) {
            AlertDialog create = new AlertDialog.Builder(this).setMessage(getString(R.string._PjNameColon_) + d_HistoryInfo.pjName + "\n\n" + getString(R.string._ErrorNetworkConnection_)).setPositiveButton(getString(R.string._OK_), (DialogInterface.OnClickListener) null).create();
            create.show();
            RegisteredDialog.getIns().setDialog(create);
        }
    }

    private void showBalloonDialog() {
        if (isActivityAlive()) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_remote, (ViewGroup) null);
            DisappearableDialog disappearableDialog = new DisappearableDialog(this, R.style.TransparentDialogTheme);
            this._balloonDialog = disappearableDialog;
            disappearableDialog.setContentView(inflate);
            this._balloonDialog.setCancelable(true);
            this._balloonDialog.show();
            RegisteredDialog.getIns().setDialog(this._balloonDialog);
            recordAlreadyBalloonShowDialog();
        }
    }

    private void dismissBalloonDialog() {
        Dialog dialog = this._balloonDialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        this._balloonDialog.dismiss();
    }

    private boolean isAlreadyBalloonDialogShowed() {
        return PrefUtils.read(this, TAG_ALREADY_SHOWED_BALLOON_DIALOG) != null;
    }

    private void recordAlreadyBalloonShowDialog() {
        PrefUtils.write(this, TAG_ALREADY_SHOWED_BALLOON_DIALOG, "empty message", (SharedPreferences.Editor) null);
    }

    private void showAuthenticationDialog() {
        ProgressDialogInfinityType progressDialogInfinityType = this._progressDialog;
        if ((progressDialogInfinityType == null || !progressDialogInfinityType.isShowing()) && isActivityAlive()) {
            ProgressDialogInfinityType progressDialogInfinityType2 = new ProgressDialogInfinityType(this);
            this._progressDialog = progressDialogInfinityType2;
            progressDialogInfinityType2.setMessage(getString(R.string._Authenticating_));
            this._progressDialog.show();
        }
    }

    private void dismissAuthenticationDialog() {
        ProgressDialogInfinityType progressDialogInfinityType = this._progressDialog;
        if (progressDialogInfinityType == null || !progressDialogInfinityType.isShowing()) {
            return;
        }
        this._progressDialog.dismiss();
    }

    private int getCurrentIndex() {
        if (isThisBatch(this._currentPosition)) {
            return this._adapter.getIndexOfBatch();
        }
        if (this._currentPosition >= this._pjList.size()) {
            return 0;
        }
        return this._adapter.getIndex(this._pjList.get(this._currentPosition).macAddr);
    }

    private boolean isActivityAlive() {
        return !isFinishing();
    }

    @Override // com.epson.iprojection.ui.activities.remote.IOnLockBatchAuthenticationListener
    public void onLockedBatchAuthentication() {
        this._isLockedBatchAuthentication = true;
    }

    @Override // com.epson.iprojection.ui.activities.remote.IOnLockBatchAuthenticationListener
    public void onUnlockedBatchAuthentication() {
        this._isLockedBatchAuthentication = false;
    }

    @Override // com.epson.iprojection.ui.activities.remote.ICurrentFragmentChecker
    public boolean isCurrentFragmentBatch() {
        return isThisBatch(this._currentPosition);
    }

    private void setCurrentItem(int i) {
        int currentItem = this._mVPager.getCurrentItem();
        if (currentItem == i) {
            return;
        }
        FragmentPjControlBase item = this._adapter.getItem(currentItem);
        if (item != null) {
            item.pause();
        }
        FragmentPjControlBase item2 = this._adapter.getItem(i);
        if (item2 != null) {
            item2.resume();
        }
        this._mVPager.setCurrentItem(i, false);
    }
}
