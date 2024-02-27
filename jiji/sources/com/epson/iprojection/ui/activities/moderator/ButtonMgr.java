package com.epson.iprojection.ui.activities.moderator;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickButtonListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickWindowButtonListener;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.google.android.material.button.MaterialButton;

/* loaded from: classes.dex */
public class ButtonMgr implements View.OnClickListener {
    public static final int BTN_MPP1 = 0;
    public static final int BTN_MPP2 = 1;
    public static final int BTN_MPP4 = 2;
    private final DraggableButton _btnMe;
    private final MaterialButton _buttonMeOnly;
    private final ImageButton[] _buttons;
    protected IOnClickButtonListener _implBtn;
    private final IOnClickWindowButtonListener _implWin;

    public ButtonMgr(Activity activity, IOnClickWindowButtonListener iOnClickWindowButtonListener, IOnClickButtonListener iOnClickButtonListener, boolean z) {
        this._buttons = r6;
        this._implWin = iOnClickWindowButtonListener;
        this._implBtn = iOnClickButtonListener;
        MaterialButton materialButton = (MaterialButton) activity.findViewById(R.id.btn_multictrl_meOnly);
        this._buttonMeOnly = materialButton;
        ImageButton[] imageButtonArr = {(ImageButton) activity.findViewById(R.id.btn_multictrl_mpp1), (ImageButton) activity.findViewById(R.id.btn_multictrl_mpp2), (ImageButton) activity.findViewById(R.id.btn_multictrl_mpp4)};
        materialButton.setOnClickListener(this);
        DraggableButton draggableButton = (DraggableButton) activity.findViewById(R.id.btn_multictrl_me);
        this._btnMe = draggableButton;
        if (draggableButton != null) {
            draggableButton.setOnClickListener(this);
        }
        for (ImageButton imageButton : imageButtonArr) {
            imageButton.setOnClickListener(this);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        ImageButton[] imageButtonArr = this._buttons;
        if (view == imageButtonArr[0]) {
            this._implWin.onClickWindowButton(0);
            Analytics.getIns().setMultiProjectionEventType(eCustomEvent.PARTITION1);
            Analytics.getIns().sendEvent(eCustomEvent.PARTITION1);
        } else if (view == imageButtonArr[1]) {
            this._implWin.onClickWindowButton(1);
            Analytics.getIns().setMultiProjectionEventType(eCustomEvent.PARTITION2);
            Analytics.getIns().sendEvent(eCustomEvent.PARTITION2);
        } else if (view == imageButtonArr[2]) {
            this._implWin.onClickWindowButton(2);
            Analytics.getIns().setMultiProjectionEventType(eCustomEvent.PARTITION4);
            Analytics.getIns().sendEvent(eCustomEvent.PARTITION4);
        } else if (view == this._buttonMeOnly) {
            this._implBtn.onClickMeOnlyButton();
            Analytics.getIns().setMultiProjectionEventType(eCustomEvent.PROJECT_ME);
            Analytics.getIns().sendEvent(eCustomEvent.PROJECT_ME);
        } else if (view == this._btnMe) {
            this._implBtn.onClickMe();
            Analytics.getIns().setMultiProjectionEventType(eCustomEvent.USER_OPERATION);
            Analytics.getIns().sendEvent(eCustomEvent.USER_OPERATION);
        }
    }

    public void setMeName(String str) {
        DraggableButton draggableButton = this._btnMe;
        if (draggableButton != null) {
            draggableButton.setText(str);
        }
    }
}
