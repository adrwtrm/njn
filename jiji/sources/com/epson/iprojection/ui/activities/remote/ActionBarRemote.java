package com.epson.iprojection.ui.activities.remote;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar;
import com.epson.iprojection.ui.common.actionbar.base.IOnClickAppIconButton;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ActionBarRemote extends BaseCustomActionBar implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final int DISABLE_ALPHA = 90;
    private ImageButton _btnTouchPad;
    private IOnClickRemoteActionBar _implActionBar;
    private boolean _nowDisplayTouchPad;
    private Spinner _spinnerPjList;

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
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

    public void setOnClickRemoteActionBar(IOnClickRemoteActionBar iOnClickRemoteActionBar) {
        this._implActionBar = iOnClickRemoteActionBar;
    }

    public ActionBarRemote(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        this._nowDisplayTouchPad = false;
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void layout(int i) {
        super.layout(i);
        createButtonTouchPad(this._activity);
        createSpinnerPjList(this._activity);
        update();
    }

    private void createButtonTouchPad(Activity activity) {
        ImageButton imageButton = (ImageButton) activity.findViewById(R.id.btn_remote_actionbar_gesture);
        this._btnTouchPad = imageButton;
        if (imageButton != null) {
            imageButton.setOnClickListener(this);
        }
    }

    private void createSpinnerPjList(Activity activity) {
        Spinner spinner = (Spinner) activity.findViewById(R.id.proj_spinner);
        this._spinnerPjList = spinner;
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void update() {
        updateButtonTouchPad();
        updateSpinnerPjList();
    }

    public void disableTouchPadButton() {
        this._btnTouchPad.setVisibility(4);
    }

    public void initializeTouchPadState() {
        changeTouchPadButton(this._nowDisplayTouchPad);
        this._implActionBar.onClickTouchPadButton(this._nowDisplayTouchPad);
    }

    public void setVisibilityTouchPadButtonState(boolean z) {
        this._btnTouchPad.setEnabled(z);
    }

    private void updateButtonTouchPad() {
        ImageButton imageButton = this._btnTouchPad;
        if (imageButton == null) {
            return;
        }
        boolean z = false;
        imageButton.setVisibility(0);
        IOnClickRemoteActionBar iOnClickRemoteActionBar = this._implActionBar;
        if (iOnClickRemoteActionBar != null && !iOnClickRemoteActionBar.canChangeTouchPad()) {
            this._btnTouchPad.setEnabled(false);
        } else if (this._spinnerPjList.getCount() == 0) {
            this._btnTouchPad.setBackgroundResource(R.drawable.selector_button_touchpad);
            this._btnTouchPad.setEnabled(false);
            this._nowDisplayTouchPad = false;
        } else {
            this._btnTouchPad.setEnabled((!Pj.getIns().isConnected() || Pj.getIns().isEnablePJControl()) ? true : true);
        }
    }

    private void updateSpinnerPjList() {
        Drawable drawable;
        Spinner spinner = this._spinnerPjList;
        if (spinner == null) {
            return;
        }
        spinner.setVisibility(0);
        if ((Pj.getIns().isConnected() && !Pj.getIns().isEnablePJControl()) || this._spinnerPjList.getCount() <= 1) {
            this._spinnerPjList.setEnabled(false);
            drawable = this._activity.getDrawable(R.drawable.btn_dropdown_custom_disable);
            if (drawable != null) {
                drawable.setAlpha(90);
            }
        } else {
            this._spinnerPjList.setEnabled(true);
            drawable = this._activity.getDrawable(R.drawable.btn_dropdown_custom);
        }
        this._spinnerPjList.setBackground(drawable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setPjList(List<String> list) {
        Spinner spinner = this._spinnerPjList;
        if (spinner == null) {
            return;
        }
        spinner.setOnItemSelectedListener(null);
        ArrayList arrayList = new ArrayList(list);
        if (list.size() >= 2 && !Pj.getIns().isAllPjTypeBusiness()) {
            arrayList.add(this._activity.getString(R.string._BatchOperation_));
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this._activity, 17367048, arrayList);
        arrayAdapter.setDropDownViewResource(17367049);
        this._spinnerPjList.setAdapter((SpinnerAdapter) arrayAdapter);
        this._spinnerPjList.setOnItemSelectedListener(this);
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void disable() {
        this._btnTouchPad.setEnabled(false);
        this._spinnerPjList.setEnabled(false);
        if (this._nowDisplayTouchPad) {
            this._btnTouchPad.setBackgroundResource(R.drawable.selector_button_touchpad);
            this._nowDisplayTouchPad = false;
            this._implActionBar.onClickTouchPadButton(false);
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void enable() {
        update();
    }

    public void invisible() {
        ImageButton imageButton = this._btnTouchPad;
        if (imageButton != null) {
            imageButton.setVisibility(4);
        }
        Spinner spinner = this._spinnerPjList;
        if (spinner != null) {
            spinner.setVisibility(4);
        }
        ImageView imageView = (ImageView) this._activity.findViewById(R.id.img_userlock);
        if (imageView != null) {
            imageView.setVisibility(4);
        }
        ImageView imageView2 = (ImageView) this._activity.findViewById(R.id.img_moderator);
        if (imageView2 != null) {
            imageView2.setVisibility(4);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this._btnTouchPad) {
            boolean z = !this._nowDisplayTouchPad;
            this._nowDisplayTouchPad = z;
            changeTouchPadButton(z);
            this._implActionBar.onClickTouchPadButton(this._nowDisplayTouchPad);
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (adapterView == null || view == null) {
            return;
        }
        ((TextView) adapterView.getChildAt(0)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this._implActionBar.onSpinnerPjListItemSelected(i);
    }

    public int getSpinnerPjListPosition() {
        Spinner spinner = this._spinnerPjList;
        if (spinner == null) {
            return 0;
        }
        return spinner.getSelectedItemPosition();
    }

    public void setSpinnerPjListPosition(int i) {
        Spinner spinner = this._spinnerPjList;
        if (spinner == null) {
            return;
        }
        spinner.setSelection(i);
    }

    public boolean isButtonTouchPadEnable() {
        return this._btnTouchPad.getVisibility() == 0 && this._btnTouchPad.isEnabled();
    }

    public boolean getNowShowTouchPad() {
        return this._nowDisplayTouchPad;
    }

    private void changeTouchPadButton(boolean z) {
        if (z) {
            this._btnTouchPad.setBackgroundResource(R.drawable.selector_button_remote);
        } else {
            this._btnTouchPad.setBackgroundResource(R.drawable.selector_button_touchpad);
        }
    }
}
