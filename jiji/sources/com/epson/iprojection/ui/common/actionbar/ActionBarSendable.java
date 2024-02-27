package com.epson.iprojection.ui.common.actionbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.ui.activities.delivery.Activity_MarkerDelivery;
import com.epson.iprojection.ui.activities.marker.Activity_Marker;
import com.epson.iprojection.ui.activities.marker.BmpHolder;
import com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar;
import com.epson.iprojection.ui.common.actionbar.base.IOnClickAppIconButton;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activity.funcs.IntentCalledState;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.common.interfaces.Capturable;

/* loaded from: classes.dex */
public class ActionBarSendable extends BaseCustomActionBar implements View.OnClickListener {
    protected ImageButton _btnPaint;
    protected Capturable _impl;
    private IOnClickAppIconButton _implIcon;
    protected IntentCalledState _intentCalled;
    private boolean _isButtonEanbled;
    private final boolean _isDelivery;
    protected boolean _isFullScreen;
    protected boolean _sendsImgWhenConnect;

    protected void procCapturedImgWhenPjBtn(Bitmap bitmap) {
    }

    public ActionBarSendable(AppCompatActivity appCompatActivity, Capturable capturable, IOnClickAppIconButton iOnClickAppIconButton, ImageButton imageButton, boolean z, IntentCalledState intentCalledState, boolean z2) {
        super(appCompatActivity);
        this._sendsImgWhenConnect = false;
        this._isButtonEanbled = false;
        this._impl = capturable;
        this._implIcon = iOnClickAppIconButton;
        this._btnPaint = imageButton;
        this._isFullScreen = z;
        this._intentCalled = intentCalledState;
        this._isDelivery = z2;
        if (imageButton != null) {
            imageButton.setOnClickListener(this);
        }
    }

    public static void layout(AppCompatActivity appCompatActivity, int i) {
        ActionBar supportActionBar = appCompatActivity.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayOptions(16, 16);
            supportActionBar.setDisplayShowHomeEnabled(false);
            supportActionBar.setCustomView(i);
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setFlag_sendsImgWhenConnect() {
        this._sendsImgWhenConnect = true;
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void setOnClickAppIconButton(IOnClickAppIconButton iOnClickAppIconButton) {
        this._implIcon = iOnClickAppIconButton;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this._btnPaint == null || view.getId() != this._btnPaint.getId()) {
            return;
        }
        onClickMarkerButton();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onClickMarkerButton() {
        Intent intent;
        try {
            if (this._intentCalled.isCallable()) {
                if (this._isDelivery) {
                    intent = new Intent(this._activity.getApplicationContext(), Activity_MarkerDelivery.class);
                    intent.putExtra(Activity_Marker.IntentMsg_ISDELIVERY, "Empty Message");
                } else {
                    intent = new Intent(this._activity.getApplicationContext(), Activity_Marker.class);
                }
                Bitmap capture = this._impl.capture();
                if (capture == null) {
                    return;
                }
                BmpHolder.ins().set(capture);
                if (this._isFullScreen) {
                    intent.putExtra(Activity_Marker.IntentMsg_FullScreenMode, "Empty Message");
                }
                this._activity.startActivityForResult(intent, 0);
                this._intentCalled._called = true;
            }
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void enable() {
        ImageButton imageButton = this._btnPaint;
        if (imageButton != null) {
            imageButton.setImageAlpha(255);
            this._btnPaint.setEnabled(true);
            this._btnPaint.setClickable(true);
        }
        this._isButtonEanbled = true;
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void disable() {
        ImageButton imageButton = this._btnPaint;
        if (imageButton != null) {
            imageButton.setImageAlpha(64);
            this._btnPaint.setEnabled(false);
            this._btnPaint.setClickable(false);
        }
        this._isButtonEanbled = false;
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void updateTopBarGroup() {
        if (this._isButtonEanbled) {
            enable();
        } else {
            disable();
        }
    }

    @Override // com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar
    public void update() {
        this._activity.invalidateOptionsMenu();
    }
}
