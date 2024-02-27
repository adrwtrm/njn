package com.epson.iprojection.ui.activities.remote;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.net.HttpHeaders;
import com.serenegiant.utils.HashUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import org.objectweb.asm.signature.SignatureVisitor;

/* loaded from: classes.dex */
public class HttpDigestAuth {
    private static final String HEX_LOOKUP = "0123456789abcdef";

    public HttpURLConnection tryAuth(HttpURLConnection httpURLConnection, String str, String str2) throws IOException {
        if (httpURLConnection.getResponseCode() == 401 && (httpURLConnection = tryDigestAuthentication(httpURLConnection, str, str2)) == null) {
            throw new AuthenticationException();
        }
        return httpURLConnection;
    }

    public static HttpURLConnection tryDigestAuthentication(HttpURLConnection httpURLConnection, String str, String str2) {
        String headerField = httpURLConnection.getHeaderField(HttpHeaders.WWW_AUTHENTICATE);
        if (headerField != null && headerField.startsWith("Digest ")) {
            HashMap<String, String> splitAuthFields = splitAuthFields(headerField.substring(7));
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(HashUtils.HASH_ALGORITHM_MD5);
                Joiner on = Joiner.on(':');
                messageDigest.reset();
                messageDigest.update(on.join(str, splitAuthFields.get("realm"), str2).getBytes(StandardCharsets.ISO_8859_1));
                String bytesToHexString = bytesToHexString(messageDigest.digest());
                messageDigest.reset();
                messageDigest.update(on.join(httpURLConnection.getRequestMethod(), httpURLConnection.getURL().getPath(), new Object[0]).getBytes(StandardCharsets.ISO_8859_1));
                String bytesToHexString2 = bytesToHexString(messageDigest.digest());
                messageDigest.reset();
                messageDigest.update(on.join(bytesToHexString, splitAuthFields.get("nonce"), bytesToHexString2).getBytes(StandardCharsets.ISO_8859_1));
                String bytesToHexString3 = bytesToHexString(messageDigest.digest());
                StringBuilder sb = new StringBuilder(128);
                sb.append("Digest username=\"");
                sb.append(str).append("\",realm=\"");
                sb.append(splitAuthFields.get("realm")).append("\",nonce=\"");
                sb.append(splitAuthFields.get("nonce")).append("\",uri=\"");
                sb.append(httpURLConnection.getURL().getPath()).append("\",response=\"");
                sb.append(bytesToHexString3).append("\"");
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) httpURLConnection.getURL().openConnection();
                httpURLConnection2.addRequestProperty(HttpHeaders.AUTHORIZATION, sb.toString());
                return httpURLConnection2;
            } catch (IOException | NoSuchAlgorithmException unused) {
            }
        }
        return null;
    }

    private static HashMap<String, String> splitAuthFields(String str) {
        HashMap<String, String> newHashMap = Maps.newHashMap();
        CharMatcher anyOf = CharMatcher.anyOf("\"\t ");
        Splitter omitEmptyStrings = Splitter.on(',').trimResults().omitEmptyStrings();
        Splitter limit = Splitter.on((char) SignatureVisitor.INSTANCEOF).trimResults(anyOf).limit(2);
        for (String str2 : omitEmptyStrings.split(str)) {
            String[] strArr = (String[]) Iterables.toArray(limit.split(str2), String.class);
            newHashMap.put(strArr[0], strArr[1]);
        }
        return newHashMap;
    }

    private static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(HEX_LOOKUP.charAt((b & 240) >> 4));
            sb.append(HEX_LOOKUP.charAt(b & Ascii.SI));
        }
        return sb.toString();
    }

    /* loaded from: classes.dex */
    public static class AuthenticationException extends IOException {
        private static final long serialVersionUID = 1;

        public AuthenticationException() {
            super("Problems authenticating");
        }
    }
}
