package com.epson.iprojection.common.utils;

import android.util.Base64;
import java.nio.charset.StandardCharsets;

/* loaded from: classes.dex */
public final class ALPFUtils {
    private static final int PREFIX = 90000000;
    private static final int XORVALUE = 47252107;

    private ALPFUtils() {
    }

    public static String convert(byte[] bArr) {
        if (bArr != null && bArr.length == 7) {
            int i = 0;
            for (int i2 = 0; i2 < bArr.length; i2++) {
                byte b = bArr[i2];
                if (b < 0 && b > 9) {
                    return null;
                }
                i += b * ((int) Math.pow(10.0d, (bArr.length - i2) - 1));
            }
            byte[] encode = Base64.encode(String.valueOf(XORVALUE ^ (i + PREFIX)).getBytes(), 2);
            if (encode == null) {
                return null;
            }
            try {
                return new String(encode, StandardCharsets.UTF_8);
            } catch (Exception unused) {
            }
        }
        return null;
    }
}
