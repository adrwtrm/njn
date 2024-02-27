package com.epson.iprojection.ui.activities.web;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.ui.common.ScaledImage;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.engine_wrapper.ContentRectHolder;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class CustomWebView extends WebView {
    private static final int SEND_CYCLE_TIME = 3000;
    private final Handler _handler;
    private Runnable runnableDelaySend;

    public CustomWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._handler = new Handler();
    }

    public void Initialize() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        setScrollBarStyle(0);
        super.requestFocus();
        MethodUtil.compatSetDrawingCacheEnabled(this, true);
    }

    public Bitmap capture() {
        MethodUtil.compatSetDrawingCacheEnabled(this, false);
        MethodUtil.compatSetDrawingCacheEnabled(this, true);
        Bitmap compatGetDrawingCache = MethodUtil.compatGetDrawingCache(this);
        if (compatGetDrawingCache != null) {
            return compatGetDrawingCache.copy(Bitmap.Config.RGB_565, true);
        }
        try {
            return BitmapUtils.createWhiteBitmap();
        } catch (BitmapMemoryException unused) {
            return null;
        }
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        try {
            if (!isFocused()) {
                focus();
            }
            int action = motionEvent.getAction();
            if (action == 0) {
                stopCycleSendImage();
            } else if (action == 1) {
                startCycleSendImage();
            }
            return super.onTouchEvent(motionEvent);
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
            return false;
        }
    }

    boolean isSendable() {
        try {
            return ((Activity_Web) getContext()).isSendable();
        } catch (Exception unused) {
            Lg.e("Invalid the type of context. Activity_Web is expected.");
            return true;
        }
    }

    public void sendImage() throws BitmapMemoryException {
        if (!isSendable()) {
            Lg.d("sendImage cancel");
        } else if (!Pj.getIns().isConnected()) {
            ScaledImage scaledImage = new ScaledImage((Activity) getContext());
            if (scaledImage.read() == null) {
                scaledImage.save(capture());
            }
        } else {
            Bitmap capture = capture();
            if (capture == null) {
                capture = BitmapUtils.createWhiteBitmap();
                Lg.d("capture null");
            } else {
                Lg.d("captured w=" + capture.getWidth() + " h=" + capture.getHeight());
            }
            Pj.getIns().sendImage(capture, null);
            Lg.d("送信");
            MethodUtil.compatSetDrawingCacheEnabled(this, false);
        }
    }

    public void startCycleSendImage() throws BitmapMemoryException {
        if (isSendable()) {
            Runnable runnable = new Runnable() { // from class: com.epson.iprojection.ui.activities.web.CustomWebView$$ExternalSyntheticLambda0
                {
                    CustomWebView.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    CustomWebView.this.m189xd92f2e1c();
                }
            };
            this.runnableDelaySend = runnable;
            postDelayed(runnable, 1L);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$startCycleSendImage$1$com-epson-iprojection-ui-activities-web-CustomWebView  reason: not valid java name */
    public /* synthetic */ void m189xd92f2e1c() {
        this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.web.CustomWebView$$ExternalSyntheticLambda1
            {
                CustomWebView.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                CustomWebView.this.m188xafdad8db();
            }
        });
        postDelayed(this.runnableDelaySend, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$startCycleSendImage$0$com-epson-iprojection-ui-activities-web-CustomWebView  reason: not valid java name */
    public /* synthetic */ void m188xafdad8db() {
        try {
            sendImage();
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    public void stopCycleSendImage() {
        Runnable runnable = this.runnableDelaySend;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
    }

    @Override // android.webkit.WebView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void focus() {
        onWindowFocusChanged(true);
        requestFocus();
    }

    public void unfocus() {
        onWindowFocusChanged(false);
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
            return;
        }
        ContentRectHolder.INSTANCE.setContentRect(i, i2);
    }
}
