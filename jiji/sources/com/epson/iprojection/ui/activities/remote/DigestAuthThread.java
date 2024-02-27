package com.epson.iprojection.ui.activities.remote;

import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;

/* loaded from: classes.dex */
public class DigestAuthThread extends Thread {
    private final IDigestAuthResultListener _impl;
    private boolean _isWorking = true;
    private final D_HistoryInfo _pjInfo;

    public DigestAuthThread(D_HistoryInfo d_HistoryInfo, IDigestAuthResultListener iDigestAuthResultListener) {
        this._pjInfo = d_HistoryInfo;
        this._impl = iDigestAuthResultListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r8 = this;
            java.lang.String r0 = "Other :"
            java.lang.String r1 = "Timeout :"
            com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo r2 = r8._pjInfo
            com.epson.iprojection.ui.activities.remote.AuthInfo r2 = com.epson.iprojection.ui.activities.remote.AuthInfo.createAuthInfo(r2)
            r3 = 0
            r4 = 1
            r5 = 0
            java.net.URL r6 = new java.net.URL     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5b java.net.SocketTimeoutException -> L76 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L99 java.net.MalformedURLException -> L9b
            java.lang.String r7 = r2.getUrl()     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5b java.net.SocketTimeoutException -> L76 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L99 java.net.MalformedURLException -> L9b
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5b java.net.SocketTimeoutException -> L76 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L99 java.net.MalformedURLException -> L9b
            java.net.URLConnection r6 = r6.openConnection()     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5b java.net.SocketTimeoutException -> L76 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L99 java.net.MalformedURLException -> L9b
            java.net.HttpURLConnection r6 = (java.net.HttpURLConnection) r6     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5b java.net.SocketTimeoutException -> L76 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L99 java.net.MalformedURLException -> L9b
            r5 = 10000(0x2710, float:1.4013E-41)
            r6.setReadTimeout(r5)     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4e java.net.SocketTimeoutException -> L51 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L54 java.net.MalformedURLException -> L56
            r6.setConnectTimeout(r5)     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4e java.net.SocketTimeoutException -> L51 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L54 java.net.MalformedURLException -> L56
            com.epson.iprojection.ui.activities.remote.HttpDigestAuth r5 = new com.epson.iprojection.ui.activities.remote.HttpDigestAuth     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4e java.net.SocketTimeoutException -> L51 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L54 java.net.MalformedURLException -> L56
            r5.<init>()     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4e java.net.SocketTimeoutException -> L51 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L54 java.net.MalformedURLException -> L56
            java.lang.String r7 = r2.getUsername()     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4e java.net.SocketTimeoutException -> L51 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L54 java.net.MalformedURLException -> L56
            java.lang.String r2 = r2.getDefaultPassword()     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4e java.net.SocketTimeoutException -> L51 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L54 java.net.MalformedURLException -> L56
            java.net.HttpURLConnection r0 = r5.tryAuth(r6, r7, r2)     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4e java.net.SocketTimeoutException -> L51 com.epson.iprojection.ui.activities.remote.HttpDigestAuth.AuthenticationException -> L54 java.net.MalformedURLException -> L56
            if (r0 == 0) goto L39
            r1 = r4
            goto L3a
        L39:
            r1 = r3
        L3a:
            if (r0 == 0) goto L3f
            r0.disconnect()
        L3f:
            com.epson.iprojection.ui.activities.remote.IDigestAuthResultListener r0 = r8._impl
            if (r0 == 0) goto Lb3
            com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo r2 = r8._pjInfo
            r0.callbackDigestAuthResult(r2, r1, r4)
            goto Lb1
        L4a:
            r0 = move-exception
            r5 = r6
            goto Lb4
        L4e:
            r1 = move-exception
            r5 = r6
            goto L5c
        L51:
            r0 = move-exception
            r5 = r6
            goto L77
        L54:
            r0 = move-exception
            goto L57
        L56:
            r0 = move-exception
        L57:
            r5 = r6
            goto L9c
        L59:
            r0 = move-exception
            goto Lb4
        L5b:
            r1 = move-exception
        L5c:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L96
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L96
            java.lang.StringBuilder r0 = r2.append(r1)     // Catch: java.lang.Throwable -> L96
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L96
            com.epson.iprojection.common.Lg.e(r0)     // Catch: java.lang.Throwable -> L96
            if (r5 == 0) goto L71
            r5.disconnect()
        L71:
            com.epson.iprojection.ui.activities.remote.IDigestAuthResultListener r0 = r8._impl
            if (r0 == 0) goto Lb3
            goto L90
        L76:
            r0 = move-exception
        L77:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L96
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L96
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch: java.lang.Throwable -> L96
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L96
            com.epson.iprojection.common.Lg.e(r0)     // Catch: java.lang.Throwable -> L96
            if (r5 == 0) goto L8c
            r5.disconnect()
        L8c:
            com.epson.iprojection.ui.activities.remote.IDigestAuthResultListener r0 = r8._impl
            if (r0 == 0) goto Lb3
        L90:
            com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo r1 = r8._pjInfo
            r0.callbackDigestAuthResult(r1, r3, r3)
            goto Lb1
        L96:
            r0 = move-exception
            r4 = r3
            goto Lb4
        L99:
            r0 = move-exception
            goto L9c
        L9b:
            r0 = move-exception
        L9c:
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L59
            com.epson.iprojection.common.Lg.e(r0)     // Catch: java.lang.Throwable -> L59
            if (r5 == 0) goto La8
            r5.disconnect()
        La8:
            com.epson.iprojection.ui.activities.remote.IDigestAuthResultListener r0 = r8._impl
            if (r0 == 0) goto Lb3
            com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo r1 = r8._pjInfo
            r0.callbackDigestAuthResult(r1, r3, r4)
        Lb1:
            r8._isWorking = r3
        Lb3:
            return
        Lb4:
            if (r5 == 0) goto Lb9
            r5.disconnect()
        Lb9:
            com.epson.iprojection.ui.activities.remote.IDigestAuthResultListener r1 = r8._impl
            if (r1 == 0) goto Lc4
            com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo r2 = r8._pjInfo
            r1.callbackDigestAuthResult(r2, r3, r4)
            r8._isWorking = r3
        Lc4:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.remote.DigestAuthThread.run():void");
    }

    public boolean isWorking() {
        return this._isWorking;
    }

    public D_HistoryInfo getPjInfo() {
        return this._pjInfo;
    }
}
