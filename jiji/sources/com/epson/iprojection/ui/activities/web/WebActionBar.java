package com.epson.iprojection.ui.activities.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.epson.iprojection.ui.activities.web.interfaces.IOnRenderingEventListener;
import com.epson.iprojection.ui.common.RenderedImageFile;
import com.epson.iprojection.ui.common.actionbar.ActionBarSendable;
import com.epson.iprojection.ui.common.actionbar.base.IOnClickAppIconButton;
import com.epson.iprojection.ui.common.activity.funcs.IntentCalledState;
import com.epson.iprojection.ui.common.interfaces.Capturable;

/* loaded from: classes.dex */
public class WebActionBar extends ActionBarSendable {
    private static final int DISABLE_ALPHA = 64;
    private final Context _context;
    private boolean _isRenderingNow;

    public WebActionBar(IOnRenderingEventListener iOnRenderingEventListener, AppCompatActivity appCompatActivity, Capturable capturable, IOnClickAppIconButton iOnClickAppIconButton, ImageButton imageButton, boolean z, IntentCalledState intentCalledState, Context context) {
        super(appCompatActivity, capturable, iOnClickAppIconButton, imageButton, z, intentCalledState, false);
        this._isRenderingNow = false;
        this._context = context;
    }

    public void onLoadStart() {
        this._isRenderingNow = true;
        this._btnPaint.setImageAlpha(64);
        this._btnPaint.setEnabled(false);
    }

    public void onLoadEnd() {
        this._isRenderingNow = false;
        this._btnPaint.setImageAlpha(255);
        this._btnPaint.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.actionbar.ActionBarSendable
    public void onClickMarkerButton() {
        if (this._isRenderingNow) {
            return;
        }
        super.onClickMarkerButton();
    }

    @Override // com.epson.iprojection.ui.common.actionbar.ActionBarSendable
    protected void procCapturedImgWhenPjBtn(Bitmap bitmap) {
        new RenderedImageFile().save(this._context, bitmap);
    }
}
