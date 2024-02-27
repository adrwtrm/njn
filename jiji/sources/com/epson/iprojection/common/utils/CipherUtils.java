package com.epson.iprojection.common.utils;

import com.epson.iprojection.common.Lg;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public final class CipherUtils {
    private CipherUtils() {
    }

    public static byte[] encrypt(byte[] bArr, int i, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(1, secretKeySpec);
            return cipher.doFinal(bArr, 0, i);
        } catch (Exception e) {
            Lg.e("ecvrypt Exception : " + e.getMessage());
            return null;
        }
    }

    public static byte[] encryptAESEPCTR(byte[] bArr, int i, byte[] bArr2, long j, long j2, long j3, long[] jArr) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(1, secretKeySpec);
            byte[] bArr3 = new byte[8];
            byte[] array = ByteBuffer.allocate(8).putLong(j2).array();
            byte[] array2 = ByteBuffer.allocate(8).putLong(0, j3 ^ j).array();
            byte[] bArr4 = new byte[16];
            int i2 = i;
            byte[] bArr5 = new byte[Math.max(i2, 16)];
            System.arraycopy(array2, 0, bArr4, 0, 8);
            int i3 = 0;
            while (i2 > 0) {
                for (int i4 = 0; i4 < 8; i4++) {
                    bArr3[7 - i4] = (byte) ((jArr[0] >> (i4 * 8)) & 255);
                }
                for (int i5 = 0; i5 < 8; i5++) {
                    bArr4[i5 + 8] = (byte) (bArr3[i5] ^ array[i5]);
                }
                jArr[0] = jArr[0] + 1;
                try {
                    byte[] doFinal = cipher.doFinal(bArr4, 0, 16);
                    if (doFinal == null) {
                        Lg.e("途中でencryptエラー発生");
                        return bArr5;
                    }
                    int length = doFinal.length;
                    if (length != 16) {
                        Lg.e("size%16==0じゃないので途中で抜ける");
                        return bArr5;
                    }
                    for (int i6 = 0; i6 < 16; i6++) {
                        int i7 = i3 + i6;
                        bArr5[i7] = (byte) (doFinal[i6] ^ bArr[i7]);
                    }
                    i2 -= length;
                    i3 += length;
                } catch (Exception e) {
                    Lg.e("encrypt error : " + e.getMessage());
                    return null;
                }
            }
            return bArr5;
        } catch (Exception e2) {
            Lg.e("Exception! : " + e2.getMessage());
            return null;
        }
    }

    public static byte[] decrypt(byte[] bArr, int i, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(2, secretKeySpec);
            return cipher.doFinal(bArr, 0, i);
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[][] getPrivateAndPublicKey() {
        byte[][] bArr = new byte[2];
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
            byte[] bArr2 = {Ascii.CR, 10};
            bArr[0] = ("-----BEGIN PRIVATE KEY-----\r\n" + Base64.getMimeEncoder(64, bArr2).encodeToString(generateKeyPair.getPrivate().getEncoded()) + "\r\n-----END PRIVATE KEY-----").getBytes(StandardCharsets.US_ASCII);
            bArr[1] = ("-----BEGIN PUBLIC KEY-----\r\n" + Base64.getMimeEncoder(64, bArr2).encodeToString(generateKeyPair.getPublic().getEncoded()) + "\r\n-----END PUBLIC KEY-----").getBytes(StandardCharsets.US_ASCII);
        } catch (Exception unused) {
        }
        return bArr;
    }

    public static byte[] getSignature(byte[] bArr, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "HMacSHA256");
            Mac mac = Mac.getInstance("HMacSHA256");
            mac.init(secretKeySpec);
            return mac.doFinal(bArr2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] decryptRSA(byte[] bArr, int i, byte[] bArr2) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(new String(bArr2).replaceAll("\\r\\n", "").replaceAll("\\n", "").replaceAll("-----BEGIN PRIVATE KEY-----", "").replaceAll("-----END PRIVATE KEY-----", ""))));
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(2, generatePrivate);
            return cipher.doFinal(bArr, 0, i);
        } catch (Exception unused) {
            return null;
        }
    }
}
