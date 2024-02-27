package com.epson.iprojection.ui.activities.pjselect.history;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.ConnectConfig;
import com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar;
import com.epson.iprojection.ui.common.actionbar.base.IOnClickAppIconButton;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class ActionBarHistory extends BaseCustomActionBar implements View.OnClickListener {
    private Button _btnConnect;

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void disable() {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void enable() {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setFlag_sendsImgWhenConnect() {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setOnClickAppIconButton(IOnClickAppIconButton iOnClickAppIconButton) {
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void updateTopBarGroup() {
    }

    public ActionBarHistory(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        this._btnConnect = null;
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void layout(int i) {
        super.layout(i);
        setListener();
        update();
    }

    private void setListener() {
        Button button = (Button) this._activity.findViewById(R.id.btn_home_pjselect_connectHistory);
        this._btnConnect = button;
        button.setOnClickListener(this);
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void update() {
        updateConnectButton();
    }

    private void updateConnectButton() {
        this._btnConnect.setEnabled(Pj.getIns().isSelectedPJFromHistory());
        if (!new ConnectConfig(this._activity).isSelectMultiple()) {
            this._btnConnect.setVisibility(8);
        } else if (Pj.getIns().getConnPjFromHistory().size() == 0) {
            this._btnConnect.setVisibility(8);
        } else {
            this._btnConnect.setVisibility(0);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btn_home_pjselect_connectHistory) {
            onClickConnectButton();
        }
    }

    private void onClickConnectButton() {
        Pj.getIns().onClickConnectEventButtonFromHistory();
    }
}
