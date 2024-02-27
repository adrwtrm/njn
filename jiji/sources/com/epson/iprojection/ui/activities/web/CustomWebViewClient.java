package com.epson.iprojection.ui.activities.web;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.epson.iprojection.ui.activities.web.interfaces.IOnRenderingEventListener;

/* loaded from: classes.dex */
public class CustomWebViewClient extends WebViewClient {
    private final IOnRenderingEventListener _impl;
    private boolean _isRendering = false;

    public CustomWebViewClient(IOnRenderingEventListener iOnRenderingEventListener) {
        this._impl = iOnRenderingEventListener;
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        this._impl.onRenderingStart(str);
        this._isRendering = true;
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        this._impl.onRenderingEnd(str);
        this._isRendering = false;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        this._impl.onRenderingError();
        this._isRendering = false;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        if (webResourceRequest.isForMainFrame()) {
            onReceivedError(webView, webResourceError.getErrorCode(), webResourceError.getDescription().toString(), webResourceRequest.getUrl().toString());
        }
    }

    public boolean isRendering() {
        return this._isRendering;
    }
}
