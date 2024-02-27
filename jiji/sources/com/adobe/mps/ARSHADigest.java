package com.adobe.mps;

import com.serenegiant.utils.HashUtils;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class ARSHADigest {
    public byte[] Digest(byte[] bArr, int i, int i2) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(i2 != 20 ? i2 != 48 ? i2 != 64 ? HashUtils.HASH_ALGORITHM_SHA256 : "SHA-512" : HashUtils.HASH_ALGORITHM_SHA384 : HashUtils.HASH_ALGORITHM_SHA1);
            messageDigest.update(bArr, 0, i);
            return messageDigest.digest();
        } catch (Exception unused) {
            return null;
        }
    }
}
