package com.epson.iprojection.ui.activities.whiteboard;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/* loaded from: classes.dex */
public class WBWebView extends WebView {
    public WBWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void Initialize() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        setScrollBarStyle(0);
        super.requestFocus();
    }
}
