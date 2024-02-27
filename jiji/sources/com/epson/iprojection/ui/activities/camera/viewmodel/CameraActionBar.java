package com.epson.iprojection.ui.activities.camera.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.actionbar.CustomActionBar;

/* loaded from: classes.dex */
public class CameraActionBar extends CustomActionBar {
    public CameraActionBar(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setColorConnected() {
        setColorConnected(R.color.action_bar_background_connect_transparent);
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setColorDisconnected() {
        setColorDisconnected(R.color.action_bar_background_transparent);
    }
}
