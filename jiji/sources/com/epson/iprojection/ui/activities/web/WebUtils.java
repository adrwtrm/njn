package com.epson.iprojection.ui.activities.web;

/* loaded from: classes.dex */
public class WebUtils {
    public static final int READ_TIMEOUT = 5000;
    public static final int REQUEST_OK = 200;
    public static final String URL_PREF_HTTP = "http://";
    public static final String URL_PREF_HTTPS = "https://";

    /* loaded from: classes.dex */
    public enum uriScheme {
        HTTP,
        HTTPS,
        OTHER
    }

    public static boolean isUrlAvailable(String str) {
        if (str == null) {
            return false;
        }
        return str.startsWith(URL_PREF_HTTP) || str.startsWith(URL_PREF_HTTPS);
    }

    public static uriScheme getUriType(String str) {
        if (!isUrlAvailable(str)) {
            return uriScheme.OTHER;
        }
        if (str.startsWith(URL_PREF_HTTPS)) {
            return uriScheme.HTTPS;
        }
        if (str.startsWith(URL_PREF_HTTP)) {
            return uriScheme.HTTP;
        }
        return uriScheme.OTHER;
    }
}
