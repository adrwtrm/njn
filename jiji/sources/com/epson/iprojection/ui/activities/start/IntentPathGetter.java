package com.epson.iprojection.ui.activities.start;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.web.WebUtils;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class IntentPathGetter {
    private final Activity _activity;
    private final Intent _intent;

    public IntentPathGetter(Activity activity, Intent intent) {
        this._activity = activity;
        this._intent = intent;
    }

    public String get() {
        Intent intent = this._intent;
        if (intent == null) {
            Lg.e("intent が取得できませんでした");
            return null;
        } else if ("android.intent.action.SEND".equals(intent.getAction())) {
            return getPath_FromActionSend(this._intent);
        } else {
            if ("android.intent.action.VIEW".equals(this._intent.getAction())) {
                return getPath_FromActionView(this._intent);
            }
            Lg.e("想定していないIntentメッセージ");
            return null;
        }
    }

    private String getPath_FromActionSend(Intent intent) {
        Lg.i("ACTION_SEND");
        Lg.d("「共有」から来ました");
        Bundle extras = intent.getExtras();
        if (extras == null) {
            Lg.e("extra is null");
            return null;
        }
        CharSequence charSequence = extras.getCharSequence("android.intent.extra.TEXT");
        if (charSequence != null) {
            String obj = charSequence.toString();
            if (WebUtils.isUrlAvailable(obj)) {
                Lg.d("ウェブのリンクが共有されました");
                Lg.d("[" + obj + "]");
                return obj;
            }
        }
        Uri uri = (Uri) extras.get("android.intent.extra.STREAM");
        if (uri == null) {
            Lg.e("uri is null");
            return null;
        }
        return getPathFromUri(uri);
    }

    private String getPath_FromActionView(Intent intent) {
        Lg.d("ACTION_VIEW");
        Uri data = intent.getData();
        if (data == null) {
            Lg.e("uri is null");
            return null;
        }
        return getPathFromUri(data);
    }

    private String getPathFromUri(Uri uri) {
        Lg.i("uri : " + uri.toString());
        String scheme = uri.getScheme();
        scheme.hashCode();
        char c = 65535;
        switch (scheme.hashCode()) {
            case 3143036:
                if (scheme.equals("file")) {
                    c = 0;
                    break;
                }
                break;
            case 3213448:
                if (scheme.equals("http")) {
                    c = 1;
                    break;
                }
                break;
            case 99617003:
                if (scheme.equals("https")) {
                    c = 2;
                    break;
                }
                break;
            case 951530617:
                if (scheme.equals(FirebaseAnalytics.Param.CONTENT)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                Lg.d("file://として渡されました");
                return uri.getPath();
            case 1:
                Lg.d("httpとして渡されました");
                return uri.toString();
            case 2:
                Lg.d("httpsとして渡されました");
                return uri.toString();
            case 3:
                Lg.d("content://として渡されました");
                return IntentStreamFile.getStreamFilePath(this._activity, this._intent, uri);
            default:
                Lg.e("Unhandled scheme " + uri.getScheme());
                return null;
        }
    }
}
