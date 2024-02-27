package com.epson.iprojection.ui.activities.pjselect;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.common.utils.DisplayInfoUtils;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.dialogs.ProjectorDetailDialog;
import com.epson.iprojection.ui.activities.pjselect.history.Activity_PjHistory;
import com.epson.iprojection.ui.activities.pjselect.nfc.NfcDataManager;
import com.epson.iprojection.ui.activities.pjselect.profile.Activity_Profile;
import com.epson.iprojection.ui.activities.pjselect.qrcode.QRDataHolder;
import com.epson.iprojection.ui.activities.pjselect.qrcode.permission.PermissionQrCameraActivity;
import com.epson.iprojection.ui.activities.pjselect.qrcode.views.activities.Activity_QrCamera;
import com.epson.iprojection.ui.activities.support.Activity_SupportEntrance;
import com.epson.iprojection.ui.activities.support.intro.moderator.Activity_IntroModerator;
import com.epson.iprojection.ui.activities.support.intro.notfindpj.Activity_IntroNotFindPj;
import com.epson.iprojection.ui.common.Initializer;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener;

/* loaded from: classes.dex */
public class FragmentHomeUnselected extends FragmentHomeBase implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, IPjSearchStatusListener, IProjListListener, ILinkageDataConnectorListener, INextActivityCallable, IStartableSearch {
    private static final int ID_BTN_HISTORY = 2131230914;
    private static final int ID_BTN_IP = 2131230915;
    private static final int ID_BTN_QR = 2131230921;
    private static final int ID_SWT_MULTI = 2131230960;
    private static final int ID_VIEW_NOT_FIND_PJ = 2131231662;
    private static final int ID_VIEW_NO_PJ_LIST = 2131231661;
    private static final int ID_VIEW_SUPPORT_INFORMATION = 2131231665;
    private ConnectConfig _connectConfig;
    private LinkageDataConnector _connector;
    private Context _context;
    private ProjectorDetailDialog _detailDialog;
    private HandlerThread _hThread;
    private Handler _handler;
    private ProgressBar _prgBar;
    private ProjList _projList;
    private boolean _resumeByLinkageDataRead = false;
    private Switch _switchMulti;
    private OkCancelDialog _useLocationdialog;
    private View _viewNoPjList;

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    protected int getLayoutId() {
        return R.layout.main_home_unselected;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickFolder(int i) {
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickProfile(XmlUtils.mplistType mplisttype) {
    }

    public static FragmentHomeBase newInstance() {
        FragmentHomeUnselected fragmentHomeUnselected = new FragmentHomeUnselected();
        fragmentHomeUnselected.setArguments(new Bundle());
        return fragmentHomeUnselected;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this._connectConfig = new ConnectConfig(context);
        this._context = context;
        this._connector = null;
        nfcConnectionAsNecessary();
    }

    private void nfcConnectionAsNecessary() {
        if (NfcDataManager.getIns().hasNoConnectData()) {
            if (!isOnLocationSetting()) {
                useLocationDialog();
                return;
            }
            LinkageDataConnector linkageDataConnector = new LinkageDataConnector(getActivity(), this, this, this);
            this._connector = linkageDataConnector;
            linkageDataConnector.connectByNfcData(true);
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initializeView();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        importSharedProfile();
        ProjList projList = this._projList;
        if (projList != null) {
            projList.clear();
        }
        this._switchMulti.setChecked(this._connectConfig.isSelectMultiple());
        LinkageDataConnector linkageDataConnector = this._connector;
        if (linkageDataConnector != null) {
            linkageDataConnector.onActivityResumed();
        }
        if (!Pj.getIns().isLinkageDataSearching() && !this._implFragmentHomeListener.prohibitStartSearch()) {
            startSearchByResume();
        }
        clearLinkageDataInfo();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        stopSearchByPause();
        stopHttpConnectableThread();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        nfcConnectionAsNecessary();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        LinkageDataConnector linkageDataConnector = this._connector;
        if (linkageDataConnector != null) {
            linkageDataConnector.cancelLinkageConnect();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        LinkageDataConnector linkageDataConnector;
        super.onActivityResult(i, i2, intent);
        if (i != 1010) {
            if (i != 1013) {
                if (i == 1040 && (linkageDataConnector = this._connector) != null) {
                    linkageDataConnector.restartConnectByLinkageData();
                    return;
                }
                return;
            } else if (!isOnLocationSetting()) {
                useLocationDialog();
                return;
            } else {
                nfcConnectionAsNecessary();
            }
        }
        byte[] qrRowData = QRDataHolder.INSTANCE.getQrRowData();
        if (qrRowData != null) {
            if (isOnLocationSetting()) {
                uncheckAll();
                LinkageDataConnector linkageDataConnector2 = new LinkageDataConnector(getActivity(), this, this, this);
                this._connector = linkageDataConnector2;
                linkageDataConnector2.connectByQrCode(qrRowData);
                QRDataHolder.INSTANCE.setQrRowData(null);
                return;
            }
            useLocationDialog();
        }
    }

    private boolean isOnLocationSetting() {
        return MethodUtil.compatGetLocationMode(this._context) != 0;
    }

    public void useLocationDialog() {
        OkCancelDialog okCancelDialog = this._useLocationdialog;
        if (okCancelDialog == null || !okCancelDialog.isShowing()) {
            this._useLocationdialog = new OkCancelDialog(getActivity(), getActivity().getString(R.string._NoticeUseLocation_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeUnselected$$ExternalSyntheticLambda0
                {
                    FragmentHomeUnselected.this = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    FragmentHomeUnselected.this.m117x608eaadb(dialogInterface, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$useLocationDialog$0$com-epson-iprojection-ui-activities-pjselect-FragmentHomeUnselected  reason: not valid java name */
    public /* synthetic */ void m117x608eaadb(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            ((Activity) this._context).startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1013);
            return;
        }
        NfcDataManager.getIns().clearBinaryData();
        showToastOnRefused();
    }

    private void showToastOnRefused() {
        Toast.makeText(getActivity(), (int) R.string._ToastNoticeUseLocation_, 1).show();
    }

    private void startHttpConnectableThread() {
        if (this._hThread == null) {
            HandlerThread handlerThread = new HandlerThread("HttpConnectableThread");
            this._hThread = handlerThread;
            handlerThread.setPriority(1);
            this._hThread.start();
        }
        this._handler = new Handler(this._hThread.getLooper());
    }

    private void stopHttpConnectableThread() {
        Handler handler = this._handler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeUnselected$$ExternalSyntheticLambda2
                {
                    FragmentHomeUnselected.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    FragmentHomeUnselected.this.m116x525b1e7c();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$stopHttpConnectableThread$1$com-epson-iprojection-ui-activities-pjselect-FragmentHomeUnselected  reason: not valid java name */
    public /* synthetic */ void m116x525b1e7c() {
        this._hThread.quit();
        this._hThread = null;
    }

    private void importSharedProfile() {
        startHttpConnectableThread();
        this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeUnselected$$ExternalSyntheticLambda1
            {
                FragmentHomeUnselected.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                FragmentHomeUnselected.this.m115x6f6fa7f8();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$importSharedProfile$2$com-epson-iprojection-ui-activities-pjselect-FragmentHomeUnselected  reason: not valid java name */
    public /* synthetic */ void m115x6f6fa7f8() {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        Initializer.importSharedProfile(activity);
        updateView();
    }

    private void startSearchByResume() {
        if (!getResumeByLinkageDataRead()) {
            if (this._connectConfig.isSelectMultiple()) {
                Pj.getIns().startSearch(this);
            } else {
                rebuildPjList();
            }
        } else {
            Pj.getIns().setupPjFinder(this);
        }
        setResumeByLinkageDataRead(false);
    }

    private void stopSearchByPause() {
        Pj.getIns().endSearch();
        Pj.getIns().onSearchInterrupted();
        Pj.getIns().disablePjListStatusListener();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.ILinkageDataConnectorListener
    public void setResumeByLinkageDataRead(boolean z) {
        this._resumeByLinkageDataRead = z;
    }

    public boolean getResumeByLinkageDataRead() {
        return this._resumeByLinkageDataRead;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_history /* 2131230914 */:
                startPjHistory();
                return;
            case R.id.button_ip /* 2131230915 */:
                startIpAddressSarch();
                return;
            case R.id.button_qr /* 2131230921 */:
                startQRCamera();
                return;
            case R.id.view_notfindpj /* 2131231662 */:
                startIntroNotFindPj();
                return;
            case R.id.view_support_information /* 2131231665 */:
                startSupportActivity();
                return;
            default:
                return;
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickItem(D_PjInfo d_PjInfo, View view) {
        ConnectPjInfo connectPjInfo = new ConnectPjInfo(d_PjInfo, this._connectConfig.getInterruptSetting());
        if (this._connectConfig.isSelectMultiple()) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.ckb_list);
            checkBox.setChecked(!checkBox.isChecked());
            if (checkBox.isChecked()) {
                if (!Pj.getIns().selectConnPJ(connectPjInfo)) {
                    checkBox.setChecked(!checkBox.isChecked());
                }
            } else {
                Pj.getIns().removeConnPJ(connectPjInfo);
            }
        } else {
            Pj.getIns().selectConnPJ(connectPjInfo);
        }
        this._projList.redrawList();
        if (this._implFragmentHomeListener != null) {
            if (!this._connectConfig.isSelectMultiple()) {
                this._implFragmentHomeListener.actionConnectByPjListItem();
            }
            this._implFragmentHomeListener.updateHomeActionBar();
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public boolean onClickProjectorDetail(D_PjInfo d_PjInfo) {
        ProjectorDetailDialog projectorDetailDialog = this._detailDialog;
        if (projectorDetailDialog == null || !projectorDetailDialog.isShowing()) {
            ProjectorDetailDialog projectorDetailDialog2 = new ProjectorDetailDialog(getActivity());
            this._detailDialog = projectorDetailDialog2;
            projectorDetailDialog2.show(d_PjInfo);
            return true;
        }
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void onClickProfile() {
        this._implFragmentHomeListener.startActivityFromMyFragment(new Intent(getActivity(), Activity_Profile.class), 1001);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IProjListListener
    public void updateList(boolean z) {
        if (z) {
            this._viewNoPjList.setVisibility(0);
        } else {
            this._viewNoPjList.setVisibility(8);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton.getId() == R.id.check_select_multi) {
            changeSelectMultiple(z);
        }
    }

    private void changeSelectMultiple(boolean z) {
        this._connectConfig.setSelectMultiple(z);
        uncheckAll();
        if (this._implFragmentHomeListener != null) {
            this._implFragmentHomeListener.updateHomeActionBar();
        }
    }

    private void initializeView() {
        initButton();
        initList();
        initHelp();
        initProgressBar();
    }

    private void initButton() {
        Switch r0 = (Switch) this._view.findViewById(R.id.check_select_multi);
        this._switchMulti = r0;
        r0.setChecked(this._connectConfig.isSelectMultiple());
        this._switchMulti.setOnCheckedChangeListener(this);
        Button button = (Button) this._view.findViewById(R.id.button_qr);
        FrameLayout frameLayout = (FrameLayout) this._view.findViewById(R.id.frame_qr);
        if (ChromeOSUtils.INSTANCE.isChromeOS(this._context)) {
            frameLayout.setVisibility(8);
        } else if (requireContext().getPackageManager().hasSystemFeature("android.hardware.camera")) {
            button.setOnClickListener(this);
        } else {
            button.setClickable(false);
            button.setAlpha(0.38f);
            this._view.findViewById(R.id.img_qr).setAlpha(0.38f);
            this._view.findViewById(R.id.text_qr).setAlpha(0.38f);
        }
        ((Button) this._view.findViewById(R.id.button_history)).setOnClickListener(this);
        ((Button) this._view.findViewById(R.id.button_ip)).setOnClickListener(this);
    }

    private void initList() {
        this._viewNoPjList = this._view.findViewById(R.id.view_no_pjlist);
        ProjList projList = new ProjList(requireContext(), (ListView) this._view.findViewById(R.id.list_Pj_SelectPj), this);
        this._projList = projList;
        projList.clear();
    }

    private void initHelp() {
        this._view.findViewById(R.id.view_notfindpj).setOnClickListener(this);
        this._view.findViewById(R.id.view_support_information).setOnClickListener(this);
    }

    private void initProgressBar() {
        ProgressBar progressBar = (ProgressBar) this._view.findViewById(R.id.prg_nowloading);
        this._prgBar = progressBar;
        progressBar.setVisibility(4);
    }

    private void rebuildPjList() {
        initPjList();
        Pj.getIns().startSearch(this);
    }

    private void initPjList() {
        Pj.getIns().endSearch();
        Pj.getIns().disablePjListStatusListener();
        Pj.getIns().removeAllConnPJ();
        ProjList projList = this._projList;
        if (projList != null) {
            projList.clear();
            this._projList.uncheckAll();
        }
        if (this._implFragmentHomeListener != null) {
            this._implFragmentHomeListener.restartEngine();
        }
    }

    private void updateView() {
        ProjList projList = this._projList;
        if (projList != null) {
            projList.displayProfileListUIThread();
        }
    }

    private void uncheckAll() {
        Pj.getIns().removeAllConnPJ();
        this._projList.uncheckAll();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onPjFind(D_PjInfo d_PjInfo, boolean z) {
        this._projList.addItem(d_PjInfo, z);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onPjStatusChanged(D_PjInfo d_PjInfo, boolean z) {
        if (d_PjInfo != null) {
            this._projList.updateItem(d_PjInfo, z);
        }
        if (this._implFragmentHomeListener != null) {
            this._implFragmentHomeListener.updateHomeActionBar();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onPjLose(int i) {
        this._projList.deleteItem(i);
        if (this._implFragmentHomeListener != null) {
            this._implFragmentHomeListener.updateHomeActionBar();
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onSearchStart() {
        this._prgBar.setVisibility(0);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener
    public void onSearchPause() {
        this._prgBar.setVisibility(4);
        if (this._implFragmentHomeListener != null) {
            this._implFragmentHomeListener.updateHomeActionBar();
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onConnectSucceed() {
        super.onConnectSucceed();
        setWifiConnector();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onConnectFail(int i, IOnConnectListener.FailReason failReason) {
        super.onConnectFail(i, failReason);
        clearLinkageDataInfo();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason) {
        super.onDisconnect(i, disconedReason);
        clearLinkageDataInfo();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onConnectCanceled() {
        super.onConnectCanceled();
        clearLinkageDataInfo();
        if (this._connectConfig.isSelectMultiple()) {
            return;
        }
        uncheckAll();
    }

    private void clearLinkageDataInfo() {
        if (Pj.getIns().isConnected()) {
            return;
        }
        LinkageDataInfoStacker.getIns().clear();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void setWifiConnector() {
        LinkageDataConnector linkageDataConnector = this._connector;
        Pj.getIns().setWifiConnecter(linkageDataConnector != null ? linkageDataConnector.getWifiConnector() : null);
    }

    private void startQRCamera() {
        FragmentActivity activity = getActivity();
        if (Pj.getIns().isConnected() || Pj.getIns().isLinkageDataSearching()) {
            return;
        }
        QRDataHolder.INSTANCE.setQrRowData(null);
        Intent intent = new Intent(activity, PermissionQrCameraActivity.class);
        intent.putExtra(Activity_QrCamera.INTENT_ORIENTATION, DisplayInfoUtils.getOrientationType(getActivity()));
        startActivityForResult(intent, 1010);
    }

    private void startPjHistory() {
        this._implFragmentHomeListener.startActivityFromMyFragment(new Intent(getActivity(), Activity_PjHistory.class), 1011);
    }

    private void startIpAddressSarch() {
        this._implFragmentHomeListener.startActivityFromMyFragment(new Intent(getActivity(), Activity_Other.class), 1012);
    }

    private void startIntroNotFindPj() {
        this._implFragmentHomeListener.startActivityFromMyFragment(new Intent(getActivity(), Activity_IntroNotFindPj.class), 1020);
    }

    private void startIntroModerator() {
        this._implFragmentHomeListener.startActivityFromMyFragment(new Intent(getActivity(), Activity_IntroModerator.class), 1021);
    }

    private void startSupportActivity() {
        Intent intent = new Intent(getActivity(), Activity_SupportEntrance.class);
        intent.putExtra(IntentTagDefine.INTRO_SUPPORT_INFOMATION_TAG, "empty_message");
        this._implFragmentHomeListener.startActivityFromMyFragment(intent, CommonDefine.REQUEST_CODE_HOME_INTRO_SUPPORT_INFOMATION);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.INextActivityCallable
    public void startActivityForResultFragmentVer(Intent intent, int i) {
        startActivityForResult(intent, i);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.IStartableSearch
    public void requestStartSearch() {
        Pj.getIns().startSearch(this);
    }
}
