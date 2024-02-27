package com.serenegiant.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.MessageDigest;

/* loaded from: classes2.dex */
public class HashUtils {
    private static final boolean DEBUG = false;
    public static final String HASH_ALGORITHM_MD2 = "MD2";
    public static final String HASH_ALGORITHM_MD5 = "MD5";
    public static final String HASH_ALGORITHM_SHA1 = "SHA-1";
    public static final String HASH_ALGORITHM_SHA224 = "SHA-224";
    public static final String HASH_ALGORITHM_SHA256 = "SHA-256";
    public static final String HASH_ALGORITHM_SHA384 = "SHA-384";
    public static final String HASH_ALGORITHM_SHA3_224 = "SHA3-224";
    public static final String HASH_ALGORITHM_SHA3_256 = "SHA3-256";
    public static final String HASH_ALGORITHM_SHA3_384 = "SHA3-384";
    public static final String HASH_ALGORITHM_SHA3_512 = "SHA3-512";
    public static final String HASH_ALGORITHM_SHA512_224 = "SHA-512/224";
    public static final String HASH_ALGORITHM_SHA512_256 = "SHA-512/256";
    private static final String TAG = "HashUtils";

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface HashAlgorithm {
    }

    private HashUtils() {
    }

    public static byte[] getDigest(String str, byte[] bArr) {
        try {
            return MessageDigest.getInstance(str).digest(bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getDigestString(String str, byte[] bArr) {
        try {
            return BufferHelper.toHexString(getDigest(str, bArr));
        } catch (Exception unused) {
            return null;
        }
    }
}
