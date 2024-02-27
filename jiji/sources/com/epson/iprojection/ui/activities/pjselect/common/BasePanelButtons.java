package com.epson.iprojection.ui.activities.pjselect.common;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.ui.activities.drawermenu.DrawerList;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public abstract class BasePanelButtons implements View.OnClickListener {
    protected Activity _activity;
    protected Button _btnCamera;
    protected Button _btnMirroring;
    protected Button _btnMultiprojection;
    protected Button _btnPdf;
    protected Button _btnPhoto;
    protected Button _btnRemote;
    protected Button _btnWeb;
    protected DrawerList _drawerList;
    protected FrameLayout _panelCamera;
    protected FrameLayout _panelMirroring;
    protected FrameLayout _panelMulti;
    protected FrameLayout _panelWeb;

    public BasePanelButtons(Activity activity, View view, DrawerList drawerList) {
        this._activity = activity;
        Button button = (Button) view.findViewById(R.id.button_remote);
        this._btnRemote = button;
        if (button != null) {
            button.setOnClickListener(this);
        }
        Button button2 = (Button) view.findViewById(R.id.button_photo);
        this._btnPhoto = button2;
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
        Button button3 = (Button) view.findViewById(R.id.button_document);
        this._btnPdf = button3;
        if (button3 != null) {
            button3.setOnClickListener(this);
        }
        Button button4 = (Button) view.findViewById(R.id.button_web);
        this._btnWeb = button4;
        if (button4 != null) {
            button4.setOnClickListener(this);
        }
        Button button5 = (Button) view.findViewById(R.id.button_camera);
        this._btnCamera = button5;
        if (button5 != null) {
            button5.setOnClickListener(this);
        }
        Button button6 = (Button) view.findViewById(R.id.button_mirroring);
        this._btnMirroring = button6;
        if (button6 != null) {
            button6.setOnClickListener(this);
        }
        Button button7 = (Button) view.findViewById(R.id.button_multi);
        this._btnMultiprojection = button7;
        if (button7 != null) {
            button7.setOnClickListener(this);
        }
        update(view);
        this._drawerList = drawerList;
    }

    public void update(View view) {
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.panel_camera);
        this._panelCamera = frameLayout;
        if (frameLayout != null) {
            if (this._activity.getPackageManager().hasSystemFeature("android.hardware.camera") || this._activity.getPackageManager().hasSystemFeature("android.hardware.camera.front")) {
                this._panelCamera.setVisibility(0);
            } else {
                this._panelCamera.setVisibility(8);
            }
        }
        FrameLayout frameLayout2 = (FrameLayout) view.findViewById(R.id.panel_web);
        this._panelWeb = frameLayout2;
        if (frameLayout2 != null) {
            if (!ChromeOSUtils.INSTANCE.isChromeOS(view.getContext())) {
                this._panelWeb.setVisibility(0);
            } else {
                this._panelWeb.setVisibility(8);
            }
        }
        FrameLayout frameLayout3 = (FrameLayout) view.findViewById(R.id.panel_multiprojection);
        this._panelMulti = frameLayout3;
        if (frameLayout3 != null) {
            if (Pj.getIns().isMpp()) {
                this._panelMulti.setVisibility(0);
            } else {
                this._panelMulti.setVisibility(8);
            }
        }
        FrameLayout frameLayout4 = (FrameLayout) view.findViewById(R.id.panel_mirroring);
        this._panelMirroring = frameLayout4;
        if (frameLayout4 != null) {
            this._panelMirroring.setVisibility(0);
        }
    }
}
