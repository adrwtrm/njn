package com.epson.iprojection.ui.activities.pjselect;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.common.actionbar.CustomActionBar;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eManualSearchDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRegisteredDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eSearchRouteDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;

/* loaded from: classes.dex */
public class Activity_Other extends PjConnectableActivity implements TextView.OnEditorActionListener, IOnDialogEventListener, IPjManualSearchResultListener {
    private InputMethodManager _inputMethodManager;
    private EditText _editText = null;
    private boolean _bFoundPj = false;

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CustomActionBar customActionBar = new CustomActionBar(this);
        setContentView(R.layout.main_conpj_other);
        this._inputMethodManager = (InputMethodManager) getSystemService("input_method");
        EditText editText = (EditText) findViewById(R.id.edit_home_pjselect_ipaddress);
        this._editText = editText;
        editText.setOnEditorActionListener(this);
        this._editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.epson.iprojection.ui.activities.pjselect.Activity_Other$$ExternalSyntheticLambda0
            {
                Activity_Other.this = this;
            }

            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                Activity_Other.this.m109x4476a4c9(view, z);
            }
        });
        this._editText.setFocusable(true);
        this._editText.setFocusableInTouchMode(true);
        setTitle(getString(R.string._ManualSearch_));
        this._baseActionBar = customActionBar;
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarOther));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    /* renamed from: lambda$onCreate$0$com-epson-iprojection-ui-activities-pjselect-Activity_Other */
    public /* synthetic */ void m109x4476a4c9(View view, boolean z) {
        if (z) {
            if (this._editText.isFocused()) {
                showSoftkeyborad();
            } else {
                hideSoftkeyborad();
            }
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            hideSoftkeyborad();
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this._editText.requestFocus();
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(1);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        hideSoftkeyborad();
        Pj.getIns().cancelSearch();
        Pj.getIns().disableManualSearchResultListener();
        this._bFoundPj = false;
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(0);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        hideSoftkeyborad();
        super.onDestroy();
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
        if (z) {
            Analytics.getIns().setConnectEvent(eSearchRouteDimension.ip, d_PjInfo.UniqInfo, null, null);
            Analytics.getIns().setRegisteredEvent(eRegisteredDimension.ip);
            Analytics.getIns().setManualSearchEvent(eManualSearchDimension.succeeded);
            Analytics.getIns().sendEvent(eCustomEvent.MANUAL_SEARCH);
            finish();
            this._bFoundPj = true;
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onEndSearchPj() {
        Lg.d("指定検索：終わりました");
        Pj.getIns().endManualSearch();
        if (this._bFoundPj) {
            this._bFoundPj = false;
        } else {
            showMessageDialog(MessageDialog.MessageType.NotFound);
            Analytics.getIns().setManualSearchEvent(eManualSearchDimension.failed);
        }
        Analytics.getIns().sendEvent(eCustomEvent.MANUAL_SEARCH);
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == 3 || keyEvent.getKeyCode() == 66) {
            hideSoftkeyborad();
            String inputIPAddress = getInputIPAddress();
            if (inputIPAddress == null) {
                return true;
            }
            this._bFoundPj = false;
            if (Pj.getIns().manualSearch(this, inputIPAddress) == -1) {
                finish();
                return false;
            }
            return true;
        }
        return false;
    }

    private String getInputIPAddress() {
        String obj = this._editText.getText().toString();
        if (NetUtils.isAvailableIPAddress(obj)) {
            return obj;
        }
        showMessageDialog(MessageDialog.MessageType.IlligalIP);
        return null;
    }

    private void showMessageDialog(MessageDialog.MessageType messageType) {
        Pj.getIns().showMsgDialog(messageType, this, BaseDialog.ResultAction.NOACTION);
    }

    private void showSoftkeyborad() {
        this._inputMethodManager.toggleSoftInput(2, 0);
    }

    private void hideSoftkeyborad() {
        this._inputMethodManager.hideSoftInputFromWindow(this._editText.getWindowToken(), 0);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogOK(String str, BaseDialog.ResultAction resultAction) {
        Pj.getIns().clearDialog();
        showSoftkeyborad();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogNG(BaseDialog.ResultAction resultAction) {
        Pj.getIns().clearDialog();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onDialogCanceled() {
        Pj.getIns().clearDialog();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
            return false;
        }
        return false;
    }
}
