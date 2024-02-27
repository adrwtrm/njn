package com.epson.iprojection.ui.activities.pjselect.settings;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebViewDatabase;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.AudioUtils;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.common.utils.PathGetter;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.engine.common.EBandWidthKt;
import com.epson.iprojection.engine.common.eBandWidth;
import com.epson.iprojection.engine.common.eBandWidthForUI;
import com.epson.iprojection.service.webrtc.WebRTCEntrance;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.remote.RemotePasswordPrefUtils;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import com.epson.iprojection.ui.common.actionbar.CustomActionBar;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.AppKiller;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.io.File;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Activity_PjSettings extends PjConnectableActivity implements View.OnClickListener, View.OnFocusChangeListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    public static final int USERNAME_INPUT_LIMIT = 32;
    private Button _btnClearRemotePass;
    private Switch _checkBoxAudioTransfer;
    private Switch _checkBoxDelivery;
    private Switch _checkBoxEnc;
    private Switch _checkBoxFloatingDeliveryButton;
    private Switch _checkBoxUsedAnalytics;
    private Switch _checkBoxUsedProjectorLog;
    private Switch _checkOptimiseUltraWideSPS2;
    private Spinner _spinnerBandWidth;
    private Button _btnReset = null;
    private EditText _usernameText = null;
    private EditText _sharedProfilePathText = null;
    private boolean _isResumed = false;

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        pushDrawerStatus(eDrawerMenuItem.AppSettings);
        this._isResumed = false;
        this._baseActionBar = new CustomActionBar(this);
        setContentView(R.layout.main_conpj_settings);
        Button button = (Button) findViewById(R.id.ResetButton);
        this._btnReset = button;
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.btn_clearRemotePass);
        this._btnClearRemotePass = button2;
        button2.setOnClickListener(this);
        EditText editText = (EditText) findViewById(R.id.edit_username);
        this._usernameText = editText;
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32)});
        this._usernameText.setOnFocusChangeListener(this);
        EditText editText2 = (EditText) findViewById(R.id.edit_sharedprofilepath);
        this._sharedProfilePathText = editText2;
        editText2.setOnFocusChangeListener(this);
        this._spinnerBandWidth = (Spinner) findViewById(R.id.spinnerBandWidth);
        findViewById(R.id.button_other_settings).setOnClickListener(this);
        this._drawerList.enableDrawerToggleButton((Toolbar) findViewById(R.id.toolbarPjSetting));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        super.getWindow().setSoftInputMode(3);
        if (Build.VERSION.SDK_INT <= 28) {
            findViewById(R.id.check_used_AudioTransfer).setVisibility(8);
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        loadPrefData();
        updateView();
        this._isResumed = true;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        hideSoftkeyborad(null);
        savePrefData();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        popDrawerStatus();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this._btnReset) {
            hideSoftkeyborad(null);
            new OkCancelDialog(this, getString(R.string._ResetProfileMsg_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.settings.Activity_PjSettings$$ExternalSyntheticLambda0
                {
                    Activity_PjSettings.this = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    Activity_PjSettings.this.m155xa03c2f00(dialogInterface, i);
                }
            });
        } else if (view == this._btnClearRemotePass) {
            new OkCancelDialog(this, getString(R.string._DeleteRemotePassword_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.settings.Activity_PjSettings$$ExternalSyntheticLambda1
                {
                    Activity_PjSettings.this = this;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    Activity_PjSettings.this.m156xd3ea59c1(dialogInterface, i);
                }
            });
        } else if (view.getId() == R.id.button_other_settings) {
            findViewById(R.id.button_other_settings).setVisibility(8);
            this._btnReset.setVisibility(0);
            this._btnClearRemotePass.setVisibility(0);
        }
    }

    /* renamed from: lambda$onClick$0$com-epson-iprojection-ui-activities-pjselect-settings-Activity_PjSettings */
    public /* synthetic */ void m155xa03c2f00(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            resetProfile();
        }
    }

    /* renamed from: lambda$onClick$1$com-epson-iprojection-ui-activities-pjselect-settings-Activity_PjSettings */
    public /* synthetic */ void m156xd3ea59c1(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            WebViewDatabase.getInstance(getApplicationContext()).clearHttpAuthUsernamePassword();
            RemotePrefUtils.deleteAll(this);
            RemotePasswordPrefUtils.deleteAll(this);
            PrefUtils.deleteTags(this, RemotePrefUtils.PREF_TAG_REMOTE_PASS);
            AppKiller.getIns().start(this);
        }
    }

    private void updateView() {
        Switch r0 = (Switch) findViewById(R.id.check_enc);
        this._checkBoxEnc = r0;
        r0.setOnCheckedChangeListener(this);
        this._checkBoxEnc.setChecked(PrefUtils.readInt(this, PrefTagDefine.conPJ_PROJECTION_WITH_ENCRYPT_TAG) == 1);
        Switch r02 = (Switch) findViewById(R.id.check_delivery);
        this._checkBoxDelivery = r02;
        r02.setOnCheckedChangeListener(this);
        this._checkBoxDelivery.setChecked(PrefUtils.readInt(this, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG) == 1);
        Switch r03 = (Switch) findViewById(R.id.check_floatingDeliveryButton);
        this._checkBoxFloatingDeliveryButton = r03;
        r03.setOnCheckedChangeListener(this);
        Switch r04 = (Switch) findViewById(R.id.check_used_AudioTransfer);
        this._checkBoxAudioTransfer = r04;
        r04.setOnCheckedChangeListener(this);
        if (ChromeOSUtils.INSTANCE.isChromeOS(this)) {
            this._checkBoxAudioTransfer.setText(getString(R.string._SoundSupport_) + "\n" + getString(R.string._OnlyAndroidAppAudio_));
        }
        this._checkBoxAudioTransfer.setChecked(PrefUtils.readInt(this, PrefTagDefine.conPJ_AUDIO_TRANSFER_TAG) == 1);
        this._checkBoxFloatingDeliveryButton.setChecked(PrefUtils.readInt(this, PrefTagDefine.conPJ_DISPLAY_DELIVERY_BUTTON_TAG) == 1);
        if (MirroringEntrance.INSTANCE.isMirroringSwitchOn() && Pj.getIns().isConnected()) {
            this._checkBoxFloatingDeliveryButton.setEnabled(false);
            this._checkBoxFloatingDeliveryButton.setTextColor(MethodUtil.compatGetColor(getApplicationContext(), R.color.GrayOut));
        } else {
            this._checkBoxFloatingDeliveryButton.setEnabled(true);
            this._checkBoxFloatingDeliveryButton.setTextColor(MethodUtil.compatGetColor(getApplicationContext(), R.color.Font));
        }
        Switch r05 = (Switch) findViewById(R.id.check_used_pj_log);
        this._checkBoxUsedProjectorLog = r05;
        r05.setOnCheckedChangeListener(this);
        this._checkBoxUsedProjectorLog.setChecked(PrefUtils.readInt(this, PrefTagDefine.conPJ_UPLOAD_PJ_LOG) == 1);
        if (Pj.getIns().isConnected()) {
            this._checkBoxEnc.setEnabled(false);
            this._checkBoxEnc.setTextColor(MethodUtil.compatGetColor(getApplicationContext(), R.color.GrayOut));
            this._usernameText.setEnabled(false);
        } else {
            this._checkBoxEnc.setEnabled(true);
            this._checkBoxEnc.setTextColor(MethodUtil.compatGetColor(getApplicationContext(), R.color.Font));
            this._usernameText.setEnabled(true);
        }
        setSpinners();
        Switch r06 = (Switch) findViewById(R.id.check_used_analytics);
        this._checkBoxUsedAnalytics = r06;
        r06.setOnCheckedChangeListener(this);
        this._checkBoxUsedAnalytics.setChecked(PrefUtils.readInt(this, PrefTagDefine.conPJ_USED_ANALYTICS) == 1);
        Switch r07 = (Switch) findViewById(R.id.check_optimiseUltraWideSPS2);
        this._checkOptimiseUltraWideSPS2 = r07;
        r07.setOnCheckedChangeListener(this);
        this._checkOptimiseUltraWideSPS2.setChecked(PrefUtils.readInt(this, PrefTagDefine.conPJ_OPTIMASE_ULTRAWIDE_ASPECT_SPS2) == 1);
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        EditText editText = this._usernameText;
        if (view == editText || !z) {
            hideSoftkeyborad(editText);
            return;
        }
        EditText editText2 = this._sharedProfilePathText;
        if (view == editText2) {
            hideSoftkeyborad(editText2);
        }
    }

    private void savePrefData() {
        String obj = this._usernameText.getText().toString();
        if (obj.equals("")) {
            obj = Build.MODEL;
        }
        String obj2 = this._sharedProfilePathText.getText().toString();
        if (obj2.equals("")) {
            XmlUtils.deleteProfileMasterData(XmlUtils.filterType.SHMPLIST);
        }
        PrefUtils.write(this, PrefTagDefine.conPJ_CONFIG_USERNAME_TAG, obj, (SharedPreferences.Editor) null);
        PrefUtils.write(this, PrefTagDefine.conPJ_CONFIG_SHAREDPROFILEPATH_TAG, obj2, (SharedPreferences.Editor) null);
    }

    private void loadPrefData() {
        this._usernameText.setText(PrefUtils.read(this, PrefTagDefine.conPJ_CONFIG_USERNAME_TAG));
        this._sharedProfilePathText.setText(PrefUtils.read(this, PrefTagDefine.conPJ_CONFIG_SHAREDPROFILEPATH_TAG));
    }

    private void resetProfile() {
        File[] listFilter = !FileUtils.mkDirectory(PathGetter.getIns().getAppsDirPath()) ? XmlUtils.listFilter(XmlUtils.filterType.MASTER, XmlUtils.place.APPS) : null;
        if (listFilter != null) {
            for (File file : listFilter) {
                file.delete();
            }
        }
        PrefUtils.writeInt(this, PrefTagDefine.conPJ_PROFILE_MAX_TAG, 0, (SharedPreferences.Editor) null);
        this._sharedProfilePathText.setText("");
    }

    private void hideSoftkeyborad(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        if (editText != null) {
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            return;
        }
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this._isResumed) {
            if (compoundButton == this._checkBoxEnc) {
                PrefUtils.writeInt(this, PrefTagDefine.conPJ_PROJECTION_WITH_ENCRYPT_TAG, z ? 1 : 0, (SharedPreferences.Editor) null);
            } else if (compoundButton == this._checkBoxDelivery) {
                PrefUtils.writeInt(this, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG, z ? 1 : 0, (SharedPreferences.Editor) null);
            } else if (compoundButton == this._checkBoxFloatingDeliveryButton) {
                PrefUtils.writeInt(this, PrefTagDefine.conPJ_DISPLAY_DELIVERY_BUTTON_TAG, z ? 1 : 0, (SharedPreferences.Editor) null);
            } else if (compoundButton == this._checkBoxAudioTransfer) {
                PrefUtils.writeInt(this, PrefTagDefine.conPJ_AUDIO_TRANSFER_TAG, z ? 1 : 0, (SharedPreferences.Editor) null);
                if (z) {
                    if (Pj.getIns().isConnected()) {
                        Pj.getIns().SetAnalyticsAudioTransferWhenConnected();
                    }
                    if (WebRTCEntrance.INSTANCE.isWebRTCProcessing()) {
                        Pj.getIns().enableAudioTransferForWebRTC(-1);
                        return;
                    } else {
                        Pj.getIns().enableAudioTransfer(-1);
                        return;
                    }
                }
                if (Pj.getIns().isConnected()) {
                    Pj.getIns().SetAnalyticsAudioTransferWhenConnected();
                }
                if (WebRTCEntrance.INSTANCE.isWebRTCProcessing()) {
                    Pj.getIns().disableAudioTransferForWebRTC();
                } else {
                    Pj.getIns().disableAudioTransfer();
                }
            } else if (compoundButton == this._checkBoxUsedAnalytics) {
                PrefUtils.writeInt(this, PrefTagDefine.conPJ_USED_ANALYTICS, z ? 1 : 0, (SharedPreferences.Editor) null);
                Analytics.getIns().updateAnalyticsCollectionEnabled();
            } else if (compoundButton == this._checkBoxUsedProjectorLog) {
                PrefUtils.writeInt(this, PrefTagDefine.conPJ_UPLOAD_PJ_LOG, z ? 1 : 0, (SharedPreferences.Editor) null);
                Pj.getIns().setProjectorLogUpload(z);
            } else if (compoundButton == this._checkOptimiseUltraWideSPS2) {
                PrefUtils.writeInt(this, PrefTagDefine.conPJ_OPTIMASE_ULTRAWIDE_ASPECT_SPS2, z ? 1 : 0, (SharedPreferences.Editor) null);
            }
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.activities.drawermenu.IOnChangeMirroringSwitchListener
    public void onChangeMirroringSwitch() {
        updateView();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason, boolean z) {
        super.onDisconnect(i, disconedReason, z);
        updateView();
        this._checkBoxEnc.setEnabled(true);
        this._checkBoxEnc.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this._usernameText.setEnabled(true);
        setSpinners();
    }

    protected void setSpinners() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string._NoControl_));
        arrayList.add(getString(R.string._25Mbps_));
        arrayList.add(getString(R.string._15Mbps_));
        arrayList.add(getString(R.string._7Mbps_));
        arrayList.add(getString(R.string._4Mbps_));
        arrayList.add(getString(R.string._2Mbps_));
        arrayList.add(getString(R.string._1Mbps_));
        arrayList.add(getString(R.string._512Kbps_));
        arrayList.add(getString(R.string._256Kbps_));
        if (this._spinnerBandWidth == null) {
            this._spinnerBandWidth = (Spinner) findViewById(R.id.spinnerBandWidth);
        }
        Spinner spinner = this._spinnerBandWidth;
        if (spinner == null) {
            return;
        }
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048, arrayList);
        arrayAdapter.setDropDownViewResource(17367049);
        this._spinnerBandWidth.setAdapter((SpinnerAdapter) arrayAdapter);
        this._spinnerBandWidth.setEnabled(true);
        int readInt = PrefUtils.readInt(this, PrefTagDefine.conPJ_BAND_WIDTH);
        if (isInRangeBandWidth(readInt)) {
            this._spinnerBandWidth.setSelection(EBandWidthKt.convertBandWidthDataForUi(readInt));
        } else {
            this._spinnerBandWidth.setSelection(eBandWidthForUI.BW_15Mbps.ordinal());
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (adapterView == null || view == null || adapterView != this._spinnerBandWidth) {
            return;
        }
        if (isInRangeBandWidth(i)) {
            int convertBandWidthDataForEngine = EBandWidthKt.convertBandWidthDataForEngine(i);
            if (convertBandWidthDataForEngine == PrefUtils.readInt(this, PrefTagDefine.conPJ_BAND_WIDTH)) {
                Lg.d("設定が変わっていないので何もしない");
                return;
            }
            PrefUtils.writeInt(this, PrefTagDefine.conPJ_BAND_WIDTH, convertBandWidthDataForEngine, (SharedPreferences.Editor) null);
            if (Pj.getIns().isConnected()) {
                Pj.getIns().setBandWidth(eBandWidth.values()[convertBandWidthDataForEngine]);
                if (AudioUtils.Companion.isBandCapableOfUsingAudio(convertBandWidthDataForEngine)) {
                    Pj.getIns().enableAudioTransfer(-1);
                    return;
                } else {
                    Pj.getIns().disableAudioTransfer();
                    return;
                }
            }
            return;
        }
        Lg.e("out of range:" + i);
    }

    private boolean isInRangeBandWidth(int i) {
        return i >= eBandWidth.eNoControl.ordinal() && i <= eBandWidth.values().length - 1;
    }
}
