package com.serenegiant.net;

import android.util.Log;
import java.util.Arrays;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

/* loaded from: classes2.dex */
public class SSLSessionUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "SSLSessionUtils";

    private SSLSessionUtils() {
    }

    public static void dump(SSLSession sSLSession) {
        String str = TAG;
        Log.i(str, "id=" + Arrays.toString(sSLSession.getId()));
        Log.i(str, "context=" + sSLSession.getSessionContext());
        Log.i(str, "creationTime=" + sSLSession.getCreationTime());
        Log.i(str, "lastAccessedTime=" + sSLSession.getLastAccessedTime());
        Log.i(str, "isValid=" + sSLSession.isValid());
        String[] valueNames = sSLSession.getValueNames();
        if (valueNames != null && valueNames.length > 0) {
            for (String str2 : valueNames) {
                Log.i(TAG, "values[" + str2 + "]=" + sSLSession.getValue(str2));
            }
        }
        try {
            Log.i(TAG, "peerCertificates=" + Arrays.toString(sSLSession.getPeerCertificates()));
        } catch (SSLPeerUnverifiedException unused) {
        }
        String str3 = TAG;
        Log.i(str3, "localCertificates=" + Arrays.toString(sSLSession.getLocalCertificates()));
        try {
            Log.i(str3, "peerCertificateChain=" + Arrays.toString(sSLSession.getPeerCertificateChain()));
        } catch (SSLPeerUnverifiedException unused2) {
        }
        try {
            Log.i(TAG, "peerPrincipal=" + sSLSession.getPeerPrincipal());
        } catch (SSLPeerUnverifiedException unused3) {
        }
        String str4 = TAG;
        Log.i(str4, "localPrincipal=" + sSLSession.getLocalPrincipal());
        Log.i(str4, "cipherSuite=" + sSLSession.getCipherSuite());
        Log.i(str4, "protocol=" + sSLSession.getProtocol());
        Log.i(str4, "peerHost=" + sSLSession.getPeerHost());
        Log.i(str4, "peerPort=" + sSLSession.getPeerPort());
        Log.i(str4, "packetBufferSize=" + sSLSession.getPacketBufferSize());
        Log.i(str4, "applicationBufferSize=" + sSLSession.getApplicationBufferSize());
    }
}
