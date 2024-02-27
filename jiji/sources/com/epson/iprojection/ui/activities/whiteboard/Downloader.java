package com.epson.iprojection.ui.activities.whiteboard;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.whiteboard.WhiteboardActivity;
import java.io.OutputStream;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: Downloader.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0019B\u001b\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005R\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0015\u0010\u0004\u001a\u00060\u0005R\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/epson/iprojection/ui/activities/whiteboard/Downloader;", "", "_context", "Landroid/content/Context;", "_impl", "Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardActivity$ImplOnDownloadCompleted;", "Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardActivity;", "(Landroid/content/Context;Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardActivity$ImplOnDownloadCompleted;)V", "get_context", "()Landroid/content/Context;", "_downloadedData", "", "_fileName", "", "get_impl", "()Lcom/epson/iprojection/ui/activities/whiteboard/WhiteboardActivity$ImplOnDownloadCompleted;", "_mimeType", "save", "", "context", "uri", "Landroid/net/Uri;", "setListener", "webView", "Landroid/webkit/WebView;", "JavaScriptInterface", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class Downloader {
    private final Context _context;
    private byte[] _downloadedData;
    private String _fileName;
    private final WhiteboardActivity.ImplOnDownloadCompleted _impl;
    private String _mimeType;

    public static /* synthetic */ void $r8$lambda$XQkC5wtS4SrGerg5mrvoAyaFaqU(Downloader downloader, WebView webView, String str, String str2, String str3, String str4, long j) {
        setListener$lambda$0(downloader, webView, str, str2, str3, str4, j);
    }

    public Downloader(Context _context, WhiteboardActivity.ImplOnDownloadCompleted _impl) {
        Intrinsics.checkNotNullParameter(_context, "_context");
        Intrinsics.checkNotNullParameter(_impl, "_impl");
        this._context = _context;
        this._impl = _impl;
        this._fileName = "";
        this._mimeType = "";
    }

    public final Context get_context() {
        return this._context;
    }

    public final WhiteboardActivity.ImplOnDownloadCompleted get_impl() {
        return this._impl;
    }

    public final void setListener(final WebView webView) {
        Intrinsics.checkNotNullParameter(webView, "webView");
        webView.addJavascriptInterface(new JavaScriptInterface(), "android");
        webView.setDownloadListener(new DownloadListener() { // from class: com.epson.iprojection.ui.activities.whiteboard.Downloader$$ExternalSyntheticLambda0
            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                Downloader.$r8$lambda$XQkC5wtS4SrGerg5mrvoAyaFaqU(Downloader.this, webView, str, str2, str3, str4, j);
            }
        });
    }

    public static final void setListener$lambda$0(Downloader this$0, WebView webView, String str, String str2, String contentDisposition, String mimeType, long j) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(webView, "$webView");
        Intrinsics.checkNotNullExpressionValue(mimeType, "mimeType");
        this$0._mimeType = mimeType;
        Intrinsics.checkNotNullExpressionValue(contentDisposition, "contentDisposition");
        List split$default = StringsKt.split$default((CharSequence) contentDisposition, new String[]{"="}, false, 0, 6, (Object) null);
        if (split$default.size() >= 2) {
            this$0._fileName = (String) split$default.get(1);
        } else {
            Lg.e("共有WBのダウンロードファイル名が取得できませんでした");
        }
        Lg.d("userAgent=[" + str2 + "] contentDisposition=[" + contentDisposition + "] mimeType=[" + mimeType + "] contentLength=[" + j + ']');
        webView.loadUrl("javascript: var xhr = new XMLHttpRequest(); xhr.open('GET', '" + str + "', true); xhr.setRequestHeader('Content-type','" + this$0._mimeType + "'); xhr.responseType = 'blob'; xhr.onload = function(e) {   if (this.status == 200) {       var blobUrl = this.response;       var reader = new FileReader();       reader.readAsDataURL(blobUrl);       reader.onloadend = function() {           base64data = reader.result;           android.base64ToFile(base64data);       }   }};xhr.send();");
    }

    /* compiled from: Downloader.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/whiteboard/Downloader$JavaScriptInterface;", "", "(Lcom/epson/iprojection/ui/activities/whiteboard/Downloader;)V", "base64ToFile", "", "base64", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public final class JavaScriptInterface {
        public JavaScriptInterface() {
            Downloader.this = r1;
        }

        @JavascriptInterface
        public final void base64ToFile(String base64) {
            Intrinsics.checkNotNullParameter(base64, "base64");
            Downloader.this._downloadedData = Base64.decode(new Regex("^data:" + Downloader.this._mimeType + ";base64,").replaceFirst(base64, ""), 0);
            Downloader.this.get_impl().onDownloadComplited(Downloader.this._fileName, Downloader.this._mimeType);
        }
    }

    public final void save(Context context, Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        try {
            OutputStream openOutputStream = context.getContentResolver().openOutputStream(uri);
            OutputStream outputStream = openOutputStream;
            if (outputStream != null) {
                outputStream.write(this._downloadedData);
                Unit unit = Unit.INSTANCE;
            }
            CloseableKt.closeFinally(openOutputStream, null);
        } catch (Exception e) {
            Lg.e("Exception!! : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
