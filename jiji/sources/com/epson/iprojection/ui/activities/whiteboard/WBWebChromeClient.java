package com.epson.iprojection.ui.activities.whiteboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.JsResult;
import android.webkit.WebView;
import com.epson.iprojection.ui.common.uiparts.CustomWebChromeClient;
import com.epson.iprojection.ui.common.uiparts.IWebChromeClient;

/* loaded from: classes.dex */
public class WBWebChromeClient extends CustomWebChromeClient {
    public WBWebChromeClient(Activity activity, IWebChromeClient iWebChromeClient) {
        super(activity, -1, iWebChromeClient);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
        new AlertDialog.Builder(webView.getContext()).setMessage(str2).setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.whiteboard.WBWebChromeClient$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                WBWebChromeClient.lambda$onJsConfirm$0(jsResult, dialogInterface, i);
            }
        }).setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.whiteboard.WBWebChromeClient$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                WBWebChromeClient.lambda$onJsConfirm$1(jsResult, dialogInterface, i);
            }
        }).create().show();
        return true;
    }

    public static /* synthetic */ void lambda$onJsConfirm$0(JsResult jsResult, DialogInterface dialogInterface, int i) {
        jsResult.confirm();
    }

    public static /* synthetic */ void lambda$onJsConfirm$1(JsResult jsResult, DialogInterface dialogInterface, int i) {
        jsResult.cancel();
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        jsResult.confirm();
        return true;
    }
}
