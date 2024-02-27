package com.epson.iprojection.ui.common.uiparts;

import android.net.Uri;
import android.webkit.ValueCallback;

/* loaded from: classes.dex */
public interface IWebChromeClient {
    ValueCallback<Uri[]> getFilePathCallback();

    ValueCallback<Uri> getUploadMessage();

    void setFilePathCallback(ValueCallback<Uri[]> valueCallback);

    void setUploadMessage(ValueCallback<Uri> valueCallback);
}
