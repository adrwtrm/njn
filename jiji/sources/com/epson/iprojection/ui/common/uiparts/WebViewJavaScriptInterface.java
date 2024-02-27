package com.epson.iprojection.ui.common.uiparts;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

/* loaded from: classes.dex */
public class WebViewJavaScriptInterface {
    private static final int REQUEST_CODE_FROM_JS = 2;
    private static final String TYPE_IMAGE = "image/*";
    private final Activity _activity;

    public WebViewJavaScriptInterface(Activity activity) {
        this._activity = activity;
    }

    @JavascriptInterface
    public void onShowFileChooser() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(TYPE_IMAGE);
        this._activity.startActivityForResult(intent, 2);
    }
}
