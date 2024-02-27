package com.epson.iprojection.ui.common.uiparts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.epson.iprojection.common.CommonDefine;

/* loaded from: classes.dex */
public class CustomWebChromeClient extends WebChromeClient {
    private static final int ANIMATION_TIME = 1000;
    private static final int COMPLETED = 100;
    private static final String TYPE_IMAGE = "*/*";
    private final Activity _activity;
    private final IWebChromeClient _impl;
    private final int _progressBarID;

    public CustomWebChromeClient(Activity activity, int i, IWebChromeClient iWebChromeClient) {
        this._activity = activity;
        this._progressBarID = i;
        this._impl = iWebChromeClient;
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(WebView webView, int i) {
        ProgressBar progressBar = (ProgressBar) this._activity.findViewById(this._progressBarID);
        if (progressBar != null) {
            progressBar.setProgress(i);
            if (i == 100) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation.setDuration(1000L);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.epson.iprojection.ui.common.uiparts.CustomWebChromeClient.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        ((ProgressBar) CustomWebChromeClient.this._activity.findViewById(CustomWebChromeClient.this._progressBarID)).setVisibility(8);
                    }
                });
                progressBar.startAnimation(alphaAnimation);
                return;
            }
            progressBar.setVisibility(0);
        }
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str) {
        openFileChooser(valueCallback, str, "");
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
        IWebChromeClient iWebChromeClient = this._impl;
        if (iWebChromeClient == null) {
            return;
        }
        ValueCallback<Uri> uploadMessage = iWebChromeClient.getUploadMessage();
        if (uploadMessage != null) {
            uploadMessage.onReceiveValue(null);
        }
        this._impl.setUploadMessage(valueCallback);
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(TYPE_IMAGE);
        this._activity.startActivityForResult(intent, CommonDefine.REQUEST_CODE_WEB_INPUT_FILE);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        IWebChromeClient iWebChromeClient = this._impl;
        if (iWebChromeClient == null) {
            return super.onShowFileChooser(webView, valueCallback, fileChooserParams);
        }
        ValueCallback<Uri[]> filePathCallback = iWebChromeClient.getFilePathCallback();
        if (filePathCallback != null) {
            filePathCallback.onReceiveValue(null);
        }
        this._impl.setFilePathCallback(valueCallback);
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(TYPE_IMAGE);
        this._activity.startActivityForResult(intent, CommonDefine.REQUEST_CODE_WEB_INPUT_FILE);
        return true;
    }
}
