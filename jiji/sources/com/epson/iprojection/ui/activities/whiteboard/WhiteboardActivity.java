package com.epson.iprojection.ui.activities.whiteboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.ALPFUtils;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.web.WebUtils;
import com.epson.iprojection.ui.activities.whiteboard.Contract;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.interfaces.Backable;
import com.epson.iprojection.ui.common.uiparts.IWebChromeClient;
import com.epson.iprojection.ui.common.uiparts.WebViewJavaScriptInterface;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WhiteboardActivity.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 '2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002'(B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u001a\u0010\u0016\u001a\u0014\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000b\u0018\u00010\nH\u0016J\u0012\u0010\u0017\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\nH\u0016J\n\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u000fH\u0016J\"\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\u0012\u0010\u001d\u001a\u00020\u000f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\b\u0010 \u001a\u00020\u000fH\u0014J \u0010!\u001a\u00020\u000f2\u0016\u0010\"\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000b0\nH\u0016J\u0018\u0010#\u001a\u00020\u000f2\u000e\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\nH\u0016J\u0010\u0010%\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u0019H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\t\u001a\u0014\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000b\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\r\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardActivity;", "Lcom/epson/iprojection/ui/common/activity/base/PjConnectableActivity;", "Lcom/epson/iprojection/ui/common/uiparts/IWebChromeClient;", "Lcom/epson/iprojection/ui/common/interfaces/Backable;", "()V", "_downloader", "Lcom/epson/iprojection/ui/activities/whiteboard/Downloader;", "_webView", "Lcom/epson/iprojection/ui/activities/whiteboard/WBWebView;", "mFilePathCallback", "Landroid/webkit/ValueCallback;", "", "Landroid/net/Uri;", "mUploadMessage", "callbackFilePath", "", "resultCode", "", "data", "Landroid/content/Intent;", "canGoBack", "", "getFilePathCallback", "getUploadMessage", "getWBUrl", "", "goBack", "onActivityResult", "requestCode", "onCreate", "b", "Landroid/os/Bundle;", "onDestroy", "setFilePathCallback", "filePathCallback", "setUploadMessage", "uploadMessage", "setupWebView", ImagesContract.URL, "Companion", "ImplOnDownloadCompleted", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WhiteboardActivity extends PjConnectableActivity implements IWebChromeClient, Backable {
    public static final Companion Companion = new Companion(null);
    public static final String INTENT_INCLUDE_PINCODE = "intent_include_pincode";
    private final Downloader _downloader = new Downloader(this, new ImplOnDownloadCompleted());
    private WBWebView _webView;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ValueCallback<Uri> mUploadMessage;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_whiteboard);
        String wBUrl = getWBUrl();
        if (wBUrl == null) {
            finish();
        } else {
            setupWebView(wBUrl);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        WBWebView wBWebView = this._webView;
        Intrinsics.checkNotNull(wBWebView);
        wBWebView.stopLoading();
        WBWebView wBWebView2 = this._webView;
        Intrinsics.checkNotNull(wBWebView2);
        wBWebView2.setWebChromeClient(null);
        WBWebView wBWebView3 = this._webView;
        Intrinsics.checkNotNull(wBWebView3);
        wBWebView3.destroy();
        this._webView = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1032) {
            if (this.mFilePathCallback == null) {
                super.onActivityResult(i, i2, intent);
                return;
            }
            Intrinsics.checkNotNull(intent);
            callbackFilePath(i2, intent);
        } else if (i != 1033) {
        } else {
            if (i2 != -1 || intent == null || intent.getData() == null) {
                super.onActivityResult(i, i2, intent);
                Lg.e("保存できずにreturn");
                return;
            }
            Uri data = intent.getData();
            Intrinsics.checkNotNull(data);
            this._downloader.save(this, data);
        }
    }

    @Override // com.epson.iprojection.ui.common.interfaces.Backable
    public void goBack() {
        WBWebView wBWebView = this._webView;
        Intrinsics.checkNotNull(wBWebView);
        wBWebView.goBack();
    }

    @Override // com.epson.iprojection.ui.common.interfaces.Backable
    public boolean canGoBack() {
        WBWebView wBWebView = this._webView;
        Intrinsics.checkNotNull(wBWebView);
        return wBWebView.canGoBack();
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public void setUploadMessage(ValueCallback<Uri> uploadMessage) {
        Intrinsics.checkNotNullParameter(uploadMessage, "uploadMessage");
        this.mUploadMessage = uploadMessage;
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public ValueCallback<Uri> getUploadMessage() {
        return this.mUploadMessage;
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public void setFilePathCallback(ValueCallback<Uri[]> filePathCallback) {
        Intrinsics.checkNotNullParameter(filePathCallback, "filePathCallback");
        this.mFilePathCallback = filePathCallback;
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public ValueCallback<Uri[]> getFilePathCallback() {
        return this.mFilePathCallback;
    }

    private final void setupWebView(String str) {
        WBWebView wBWebView = (WBWebView) findViewById(R.id.webview);
        this._webView = wBWebView;
        Intrinsics.checkNotNull(wBWebView);
        wBWebView.Initialize();
        WBWebView wBWebView2 = this._webView;
        Intrinsics.checkNotNull(wBWebView2);
        wBWebView2.setWebViewClient(new WBWebViewClient());
        WBWebView wBWebView3 = this._webView;
        Intrinsics.checkNotNull(wBWebView3);
        WhiteboardActivity whiteboardActivity = this;
        wBWebView3.setWebChromeClient(new WBWebChromeClient(whiteboardActivity, this));
        WBWebView wBWebView4 = this._webView;
        Intrinsics.checkNotNull(wBWebView4);
        wBWebView4.addJavascriptInterface(new WebViewJavaScriptInterface(whiteboardActivity), "Native");
        Downloader downloader = this._downloader;
        WBWebView wBWebView5 = this._webView;
        Intrinsics.checkNotNull(wBWebView5);
        downloader.setListener(wBWebView5);
        WBWebView wBWebView6 = this._webView;
        Intrinsics.checkNotNull(wBWebView6);
        wBWebView6.loadUrl(str);
    }

    private final void callbackFilePath(int i, Intent intent) {
        String dataString;
        Uri[] uriArr = (i != -1 || (dataString = intent.getDataString()) == null) ? null : new Uri[]{Uri.parse(dataString)};
        ValueCallback<Uri[]> valueCallback = this.mFilePathCallback;
        Intrinsics.checkNotNull(valueCallback);
        valueCallback.onReceiveValue(uriArr);
        this.mFilePathCallback = null;
    }

    private final String getWBUrl() {
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        if (nowConnectingPJList == null) {
            return null;
        }
        D_PjInfo pjInfo = nowConnectingPJList.get(0).getPjInfo();
        String cvtIPAddr = NetUtils.cvtIPAddr(pjInfo.IPAddr);
        Intrinsics.checkNotNullExpressionValue(cvtIPAddr, "cvtIPAddr(pjinfo.IPAddr)");
        String convert = ALPFUtils.convert(pjInfo.sharedWbPin);
        if (getIntent().getStringExtra(INTENT_INCLUDE_PINCODE) == null) {
            return WebUtils.URL_PREF_HTTP + cvtIPAddr + "/wb";
        }
        return WebUtils.URL_PREF_HTTP + cvtIPAddr + "/wb?ALPF=" + convert;
    }

    /* compiled from: WhiteboardActivity.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardActivity$ImplOnDownloadCompleted;", "Lcom/epson/iprojection/ui/activities/whiteboard/Contract$IDownloadComplitedListener;", "(Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardActivity;)V", "onDownloadComplited", "", "fileName", "", "mimeType", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class ImplOnDownloadCompleted implements Contract.IDownloadComplitedListener {
        public ImplOnDownloadCompleted() {
        }

        @Override // com.epson.iprojection.ui.activities.whiteboard.Contract.IDownloadComplitedListener
        public void onDownloadComplited(String fileName, String mimeType) {
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            Intrinsics.checkNotNullParameter(mimeType, "mimeType");
            Lg.d("SAFの呼び出し fileName:" + fileName + " mimeType:" + mimeType);
            Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
            intent.setType(mimeType);
            intent.putExtra("android.intent.extra.TITLE", fileName);
            WhiteboardActivity.this.startActivityForResult(intent, CommonDefine.REQUEST_CODE_WEB_SAF);
        }
    }

    /* compiled from: WhiteboardActivity.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardActivity$Companion;", "", "()V", "INTENT_INCLUDE_PINCODE", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
