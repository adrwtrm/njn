package com.epson.iprojection.ui.activities.pjselect.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.ConnectConfig;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRegisteredDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eSearchRouteDimension;
import com.epson.iprojection.ui.common.editableList.BaseEditableListActivity;
import com.epson.iprojection.ui.common.editableList.SaveData;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.Objects;

/* loaded from: classes.dex */
public class Activity_PjHistory extends BaseEditableListActivity implements CompoundButton.OnCheckedChangeListener, IUpdatableActionbar {
    private static final int ID_SWT_MULTI = 2131230960;
    public static final String TAG_INTENT_RESULT = "tag_intent_result";
    private ConnectConfig _connectConfig;
    private PjHistory _history = null;

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableListActivity
    public String getSendString(SaveData saveData) {
        return null;
    }

    @Override // com.epson.iprojection.ui.common.editableList.BaseEditableListActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBarHistory actionBarHistory = new ActionBarHistory(this);
        this._baseActionBar = actionBarHistory;
        setContentView(R.layout.main_conpj_history);
        this._connectConfig = new ConnectConfig(this);
        Switch r0 = (Switch) findViewById(R.id.check_select_multi);
        r0.setChecked(this._connectConfig.isSelectMultiple());
        r0.setOnCheckedChangeListener(this);
        actionBarHistory.layout(R.layout.toolbar_history);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarHistory));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(1);
        super.onResume();
        if (!Pj.getIns().isAvailablePjFinder()) {
            finish();
            return;
        }
        this._history = new PjHistory(this).initialize(this, this, true);
        if (this._connectConfig.isSelectMultiple()) {
            return;
        }
        clearSelectPJ();
        super.updatePjButtonState();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(0);
        super.onPause();
        Lg.d("onPause");
    }

    @Override // com.epson.iprojection.ui.common.editableList.IOnSelected
    public void onSelected(SaveData saveData, View view, int i) {
        super.onSelected(saveData);
        Lg.i("onSelected on Activity_PjHistory.");
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.ckb_history_list);
        Pj ins = Pj.getIns();
        Objects.requireNonNull(saveData);
        String str = saveData.get(1);
        Objects.requireNonNull(saveData);
        D_PjInfo pjInfoByIpAndName = ins.getPjInfoByIpAndName(str, saveData.get(0));
        if (pjInfoByIpAndName == null) {
            pjInfoByIpAndName = new D_PjInfo();
            Objects.requireNonNull(saveData);
            pjInfoByIpAndName.IPAddr = NetUtils.convertIpStringToBytes(saveData.get(1));
            Objects.requireNonNull(saveData);
            pjInfoByIpAndName.PrjName = saveData.get(0);
        }
        Analytics.getIns().setConnectEvent(eSearchRouteDimension.history, null, pjInfoByIpAndName.IPAddr, null);
        Analytics.getIns().setRegisteredEvent(eRegisteredDimension.history);
        ConnectPjInfo connectInfo = getConnectInfo(pjInfoByIpAndName);
        if (connectInfo != null) {
            if (checkBox.isChecked()) {
                Pj.getIns().removeConnPjFromHistory(connectInfo);
            } else {
                this._history.update();
                if (!Pj.getIns().addConnPjFromHistory(connectInfo)) {
                    return;
                }
            }
            this._history.update();
            checkBox.setChecked(!checkBox.isChecked());
            super.updatePjButtonState();
        }
        if (this._connectConfig.isSelectMultiple()) {
            return;
        }
        Pj.getIns().onClickConnectEventButtonFromHistory();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onConnectSucceed() {
        Lg.d("onConnectSucceed");
        super.onConnectSucceed();
        this._history.updateConnectedHistory(this);
        this._history.setvisivility(false);
        this._history.recreate();
        finish();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onConnectFail(int i, IOnConnectListener.FailReason failReason) {
        Lg.d("onConnectFail");
        super.onConnectFail(i, failReason);
        clearSelectPJ();
        super.updatePjButtonState();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason, boolean z) {
        Lg.d("onDisconnect");
        super.onDisconnect(i, disconedReason, z);
        clearSelectPJ();
        super.updatePjButtonState();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDisconnectOne(int i, IOnConnectListener.DisconedReason disconedReason) {
        super.onDisconnectOne(i, disconedReason);
        PjHistory pjHistory = this._history;
        if (pjHistory != null) {
            pjHistory.recreate();
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onConnectCanceled() {
        if (!this._connectConfig.isSelectMultiple()) {
            clearSelectPJ();
        }
        super.onConnectCanceled();
    }

    private ConnectPjInfo getConnectInfo(D_PjInfo d_PjInfo) {
        ConnectPjInfo connectPjInfo = new ConnectPjInfo(d_PjInfo, this._connectConfig.getInterruptSetting());
        connectPjInfo.setPjInfo(d_PjInfo);
        return connectPjInfo;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
            return false;
        }
        return false;
    }

    public void changeSelectMultiple(boolean z) {
        this._connectConfig.setSelectMultiple(z);
        clearSelectPJ();
        super.updatePjButtonState();
    }

    private void clearSelectPJ() {
        Pj.getIns().clearConnPjFromHistory();
        if (this._history != null) {
            if (!Pj.getIns().isAlivePj()) {
                this._history.setvisivility(true);
            }
            this._history.recreate();
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton.getId() == R.id.check_select_multi) {
            changeSelectMultiple(z);
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onRegisterSucceed() {
        super.onRegisterSucceed();
        setResult(3000, new Intent());
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.history.IUpdatableActionbar
    public void updateActionbar() {
        updatePjButtonState();
    }
}
