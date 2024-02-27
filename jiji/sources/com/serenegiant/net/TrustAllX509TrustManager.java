package com.serenegiant.net;

import android.text.TextUtils;
import android.util.Log;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes2.dex */
public class TrustAllX509TrustManager implements X509TrustManager {
    private final boolean debug;
    private final String tag;

    public TrustAllX509TrustManager() {
        this(false, null);
    }

    public TrustAllX509TrustManager(boolean z) {
        this(z, null);
    }

    public TrustAllX509TrustManager(boolean z, String str) {
        this.debug = z;
        this.tag = TextUtils.isEmpty(str) ? "TrustAllX509TrustManager" : str;
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (this.debug) {
            Log.v(this.tag, "checkClientTrusted:" + str + RemotePrefUtils.SEPARATOR + Arrays.toString(x509CertificateArr));
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (this.debug) {
            Log.v(this.tag, "checkServerTrusted:" + str + RemotePrefUtils.SEPARATOR + Arrays.toString(x509CertificateArr));
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        if (this.debug) {
            Log.v(this.tag, "getAcceptedIssuers:");
        }
        return new X509Certificate[0];
    }
}
