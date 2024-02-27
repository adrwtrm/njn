package com.epson.iprojection.ui.common.activity;

import android.view.View;
import android.widget.ImageButton;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public abstract class ProjectableActivity extends PjConnectableActivity implements View.OnClickListener {
    protected ImageButton _btnProj;

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        updateProjBtn();
    }

    private boolean isEnable() {
        if (Pj.getIns().isConnected() && Pj.getIns().isMpp()) {
            return Pj.getIns().isEnableProjection();
        }
        return false;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity
    public void updateActionBar() {
        super.updateActionBar();
        updateProjBtn();
    }

    public void updateProjBtn() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_projection);
        this._btnProj = imageButton;
        if (imageButton == null) {
            return;
        }
        imageButton.setOnClickListener(this);
        if (isEnable()) {
            enable();
        } else {
            disable();
        }
    }

    private void enable() {
        this._btnProj.setImageResource(R.drawable.projection);
        this._btnProj.setClickable(true);
        this._btnProj.setEnabled(true);
    }

    private void disable() {
        this._btnProj.setImageResource(R.drawable.projection_disable);
        this._btnProj.setClickable(false);
        this._btnProj.setEnabled(false);
    }

    public void onClick(View view) {
        if (view == this._btnProj) {
            Pj.getIns().projectionMyself();
            ToastMgr.getIns().show(this, ToastMgr.Type.ProjectMe);
        }
    }
}
