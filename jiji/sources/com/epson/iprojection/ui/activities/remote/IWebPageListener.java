package com.epson.iprojection.ui.activities.remote;

import android.webkit.WebView;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;

/* loaded from: classes.dex */
public interface IWebPageListener {
    void onAuthCanceled(D_HistoryInfo d_HistoryInfo, boolean z);

    void onErrorLoad(WebView webView, D_HistoryInfo d_HistoryInfo, int i, String str, String str2);

    void onFinishLoad(D_HistoryInfo d_HistoryInfo);

    void onStartLoad(WebView webView, D_HistoryInfo d_HistoryInfo, String str);
}
