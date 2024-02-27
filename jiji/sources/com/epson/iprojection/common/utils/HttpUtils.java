package com.epson.iprojection.common.utils;

import com.epson.iprojection.common.Lg;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: HttpUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/common/utils/HttpUtils;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class HttpUtils {
    public static final Companion Companion = new Companion(null);

    @JvmStatic
    public static final boolean post(String str, byte[] bArr) {
        return Companion.post(str, bArr);
    }

    /* compiled from: HttpUtils.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/common/utils/HttpUtils$Companion;", "", "()V", "post", "", ImagesContract.URL, "", "data", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final boolean post(String url, byte[] data) {
            Intrinsics.checkNotNullParameter(url, "url");
            Intrinsics.checkNotNullParameter(data, "data");
            Lg.d("http post to -> " + url);
            HttpURLConnection httpURLConnection = null;
            try {
                try {
                    URLConnection openConnection = new URL(url).openConnection();
                    Intrinsics.checkNotNull(openConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) openConnection;
                    try {
                        httpURLConnection2.setRequestMethod("POST");
                        httpURLConnection2.setInstanceFollowRedirects(false);
                        httpURLConnection2.setDoOutput(true);
                        httpURLConnection2.setReadTimeout(10000);
                        httpURLConnection2.setConnectTimeout(20000);
                        httpURLConnection2.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
                        httpURLConnection2.setRequestProperty(HttpHeaders.CONTENT_ENCODING, "gzip");
                        httpURLConnection2.connect();
                        OutputStream outputStream = httpURLConnection2.getOutputStream();
                        try {
                            OutputStream outputStream2 = outputStream;
                            outputStream2.write(data);
                            outputStream2.flush();
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(outputStream, null);
                            Lg.d("http post response code = " + httpURLConnection2.getResponseCode());
                            httpURLConnection2.disconnect();
                            return true;
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(outputStream, th);
                                throw th2;
                            }
                        }
                    } catch (IOException e) {
                        e = e;
                        httpURLConnection = httpURLConnection2;
                        Lg.e("post IOException : " + e.getMessage());
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                            return false;
                        }
                        return false;
                    } catch (Throwable th3) {
                        th = th3;
                        httpURLConnection = httpURLConnection2;
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }
}
