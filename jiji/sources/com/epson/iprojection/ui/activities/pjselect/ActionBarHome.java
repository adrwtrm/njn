package com.epson.iprojection.ui.activities.pjselect;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.dialogs.QueryDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.common.actionbar.CustomActionBar;
import com.epson.iprojection.ui.engine_wrapper.DisconReason;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.StateMachine;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ActionBarHome extends CustomActionBar implements IOnDialogEventListener {
    private static final int ID_BTN_DISCON = 2131230846;
    private Button _btnDiscon;
    private IActionBarHomeListener _implActionBarHome;

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogNG(BaseDialog.ResultAction resultAction) {
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onDialogCanceled() {
    }

    public ActionBarHome(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        this._btnDiscon = (Button) this._activity.findViewById(R.id.btn_home_pjselect_connect);
        layout(R.layout.toolbar_select);
        if (appCompatActivity instanceof IActionBarHomeListener) {
            this._implActionBarHome = (IActionBarHomeListener) appCompatActivity;
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.CustomActionBar
    public void setListener() {
        super.setListener();
        Button button = (Button) this._activity.findViewById(R.id.btn_home_pjselect_connect);
        this._btnDiscon = button;
        if (button != null) {
            button.setOnClickListener(this);
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.CustomActionBar, com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void update() {
        super.update();
        updateDisconButton();
        updateTitleText();
    }

    private void updateDisconButton() {
        if (Pj.getIns().isConnected()) {
            setButtonDisconnect();
        } else {
            setButtonConnect();
        }
    }

    private void updateTitleText() {
        TextView textView = (TextView) this._activity.findViewById(R.id.txt_titlebar_filename);
        if (Pj.getIns().isConnected() || Pj.getIns().isRegistered()) {
            textView.setVisibility(4);
        } else {
            textView.setVisibility(0);
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.CustomActionBar, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btn_home_pjselect_connect) {
            onClickDisconButton();
        } else {
            super.onClick(view);
        }
    }

    private void onClickDisconButton() {
        if (Pj.getIns().isConnected()) {
            onClickDisconnect();
        } else {
            onClickConnect();
        }
    }

    private void onClickConnect() {
        this._implActionBarHome.actionConnectByActionBar();
    }

    private void onClickDisconnect() {
        QueryDialog queryDialog = new QueryDialog(this._activity, QueryDialog.MessageType.Disconnect, this, BaseDialog.ResultAction.DISCONNECT, null);
        queryDialog.create(this._activity);
        queryDialog.show();
    }

    private void setButtonConnect() {
        if (this._btnDiscon == null) {
            return;
        }
        int dimensionPixelSize = this._activity.getResources().getDimensionPixelSize(R.dimen.TopBarButtonPadSize);
        this._btnDiscon.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        this._btnDiscon.setBackgroundResource(R.drawable.selector_connect_btn_pressed);
        if (Pj.getIns().isRegistered()) {
            this._btnDiscon.setText(R.string._Connect_);
            this._btnDiscon.setVisibility(0);
        } else {
            if (Pj.getIns().isAllPjTypeBusinessSelectHome()) {
                this._btnDiscon.setText(R.string._Connect_);
            } else {
                this._btnDiscon.setText(R.string._Register_);
            }
            if (!new ConnectConfig(this._activity).isSelectMultiple()) {
                this._btnDiscon.setVisibility(8);
            } else if (Pj.getIns().isSelectedPJ()) {
                this._btnDiscon.setVisibility(0);
            } else {
                this._btnDiscon.setVisibility(8);
            }
        }
        this._btnDiscon.invalidate();
    }

    private void setButtonDisconnect() {
        int dimensionPixelSize = this._activity.getResources().getDimensionPixelSize(R.dimen.TopBarButtonPadSize);
        this._btnDiscon.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        this._btnDiscon.setText(R.string._Disconnect_);
        this._btnDiscon.setBackgroundResource(R.drawable.selector_discon_btn_pressed);
        this._btnDiscon.setVisibility(0);
        this._btnDiscon.invalidate();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogOK(String str, BaseDialog.ResultAction resultAction) {
        if (resultAction == BaseDialog.ResultAction.DISCONNECT && Pj.getIns().isConnected()) {
            Pj.getIns().disconnect(DisconReason.UserAction);
        }
    }

    public void setVisibilityDisconButtonUnselected(boolean z) {
        Button button;
        if (Pj.getIns().getState() == StateMachine.State.Unselected && (button = this._btnDiscon) != null) {
            button.setVisibility(z ? 0 : 8);
        }
    }
}
