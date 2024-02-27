package com.serenegiant.security;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import android.util.Base64;
import com.serenegiant.nio.CharsetsUtils;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes2.dex */
public class KeyStoreUtils {
    private static final String ALGORITHM_RSA = "RSA";
    private static final String CIPHER_TRANSFORMATION_AES = "AES/CBC/PKCS7Padding";
    private static final String CIPHER_TRANSFORMATION_RSA = "RSA/ECB/PKCS1Padding";
    private static final boolean DEBUG = false;
    private static final String KEY_STORE_TYPE = "AndroidKeyStore";
    private static final String TAG = "KeyStoreUtils";

    private KeyStoreUtils() {
    }

    public static void deleteKey(Context context, String str) {
        String str2 = context.getPackageName() + (!TextUtils.isEmpty(str) ? ":" + str : "");
        try {
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
            keyStore.load(null);
            if (keyStore.containsAlias(str2)) {
                keyStore.deleteEntry(str2);
            }
        } catch (IOException | GeneralSecurityException unused) {
        }
    }

    public static String encrypt(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        return encryptAES(context, str, str2);
    }

    public static String decrypt(Context context, String str, String str2) throws ObfuscatorException {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        return decryptAES(context, str, str2);
    }

    private static String encryptRSA(Context context, String str, String str2) {
        try {
            PublicKey publicKey = ((KeyStore.PrivateKeyEntry) getKey(context, str)).getCertificate().getPublicKey();
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(1, publicKey);
            return base64Encode(cipher.doFinal(str2.getBytes(CharsetsUtils.UTF8)));
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Invalid environment", e);
        }
    }

    private static String decryptRSA(Context context, String str, String str2) throws ObfuscatorException {
        try {
            PrivateKey privateKey = ((KeyStore.PrivateKeyEntry) getKey(context, str)).getPrivateKey();
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(2, privateKey);
            return new String(cipher.doFinal(base64decode(str2)), CharsetsUtils.UTF8);
        } catch (IOException e) {
            e = e;
            throw new ObfuscatorException(e.getMessage() + "");
        } catch (IllegalArgumentException e2) {
            throw new ObfuscatorException(e2.getMessage() + "");
        } catch (GeneralSecurityException e3) {
            e = e3;
            throw new ObfuscatorException(e.getMessage() + "");
        }
    }

    private static String encryptAES(Context context, String str, String str2) {
        try {
            SecretKey secretKey = ((KeyStore.SecretKeyEntry) getKey(context, str)).getSecretKey();
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_AES);
            cipher.init(1, secretKey);
            return base64Encode(cipher.getIV()) + "|" + base64Encode(cipher.doFinal(str2.getBytes(CharsetsUtils.UTF8)));
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Invalid environment", e);
        }
    }

    private static String decryptAES(Context context, String str, String str2) throws ObfuscatorException {
        String[] split = str2.split("\\|");
        if (split.length < 2) {
            throw new ObfuscatorException("Unexpected encrypted format");
        }
        try {
            SecretKey secretKey = ((KeyStore.SecretKeyEntry) getKey(context, str)).getSecretKey();
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_AES);
            cipher.init(2, secretKey, new IvParameterSpec(base64decode(split[0])));
            return new String(cipher.doFinal(base64decode(split[1])), CharsetsUtils.UTF8);
        } catch (IOException e) {
            throw new ObfuscatorException(e.getMessage() + "");
        } catch (IllegalArgumentException e2) {
            throw new ObfuscatorException(e2.getMessage() + "");
        } catch (GeneralSecurityException e3) {
            throw new ObfuscatorException(e3.getMessage() + "");
        }
    }

    private static KeyStore.Entry getKey(Context context, String str) throws GeneralSecurityException, IOException {
        String str2 = context.getPackageName() + (!TextUtils.isEmpty(str) ? ":" + str : "");
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        keyStore.load(null);
        boolean containsAlias = keyStore.containsAlias(str2);
        if (containsAlias && ALGORITHM_RSA.equals(keyStore.getKey(str2, null).getAlgorithm())) {
            keyStore.deleteEntry(str2);
            containsAlias = false;
        }
        if (!containsAlias) {
            createKey(context, str2);
        }
        return keyStore.getEntry(str2, null);
    }

    private static void createKey(Context context, String str) throws GeneralSecurityException {
        Calendar.getInstance(Locale.ENGLISH);
        Calendar.getInstance(Locale.ENGLISH).add(1, 1);
        KeyGenParameterSpec build = new KeyGenParameterSpec.Builder(str, 3).setCertificateSubject(new X500Principal("CN=" + str + " O=" + context.getPackageName())).setCertificateSerialNumber(BigInteger.ONE).setBlockModes("CBC").setEncryptionPaddings("PKCS7Padding").build();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", KEY_STORE_TYPE);
        keyGenerator.init(build);
        keyGenerator.generateKey();
    }

    private static String base64Encode(byte[] bArr) {
        return new String(Base64.encode(bArr, 2), CharsetsUtils.UTF8);
    }

    private static byte[] base64decode(String str) {
        return Base64.decode(str, 2);
    }
}
