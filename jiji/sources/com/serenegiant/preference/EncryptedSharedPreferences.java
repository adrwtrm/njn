package com.serenegiant.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import android.util.Base64;
import com.serenegiant.nio.CharsetsUtils;
import com.serenegiant.security.ObfuscatorException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes2.dex */
public class EncryptedSharedPreferences implements SharedPreferences {
    private static final boolean DEBUG = false;
    private static final String TAG = "EncryptedSharedPreferences";
    final Obfuscator mObfuscator;
    private final SharedPreferences mSharedPreferences;

    /* loaded from: classes2.dex */
    public interface Obfuscator {
        String decrypt(String str, String str2) throws ObfuscatorException;

        String encrypt(String str, String str2);
    }

    public EncryptedSharedPreferences(SharedPreferences sharedPreferences, Obfuscator obfuscator) {
        this.mSharedPreferences = sharedPreferences;
        this.mObfuscator = obfuscator;
    }

    public Set<String> keySet() {
        return new HashSet(this.mSharedPreferences.getAll().keySet());
    }

    @Override // android.content.SharedPreferences
    public Map<String, ?> getAll() {
        throw new UnsupportedOperationException("#getAll is not available");
    }

    @Override // android.content.SharedPreferences
    public String getString(String str, String str2) {
        try {
            return unobfuscate(str, str2);
        } catch (ObfuscatorException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override // android.content.SharedPreferences
    public Set<String> getStringSet(String str, Set<String> set) {
        throw new UnsupportedOperationException("#getStringSet is not available");
    }

    @Override // android.content.SharedPreferences
    public int getInt(String str, int i) {
        try {
            return Integer.parseInt(unobfuscate(str, Integer.toString(i)));
        } catch (ObfuscatorException e) {
            throw new IllegalArgumentException(e);
        } catch (NumberFormatException unused) {
            throw new ClassCastException();
        }
    }

    @Override // android.content.SharedPreferences
    public long getLong(String str, long j) {
        try {
            return Long.parseLong(unobfuscate(str, Long.toString(j)));
        } catch (ObfuscatorException e) {
            throw new IllegalArgumentException(e);
        } catch (NumberFormatException unused) {
            throw new ClassCastException();
        }
    }

    @Override // android.content.SharedPreferences
    public float getFloat(String str, float f) {
        try {
            return Float.parseFloat(unobfuscate(str, Float.toString(f)));
        } catch (ObfuscatorException e) {
            throw new IllegalArgumentException(e);
        } catch (NumberFormatException unused) {
            throw new ClassCastException();
        }
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(String str, boolean z) {
        try {
            return Boolean.parseBoolean(unobfuscate(str, Boolean.toString(z)));
        } catch (ObfuscatorException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override // android.content.SharedPreferences
    public boolean contains(String str) {
        return this.mSharedPreferences.contains(str);
    }

    @Override // android.content.SharedPreferences
    public SharedPreferences.Editor edit() {
        return new EncryptedEditor(this.mSharedPreferences.edit(), this.mObfuscator);
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.mSharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.mSharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    private String unobfuscate(String str, String str2) throws ObfuscatorException {
        String string = this.mSharedPreferences.getString(str, null);
        return string != null ? this.mObfuscator.decrypt(str, string) : str2;
    }

    /* loaded from: classes2.dex */
    private static class EncryptedEditor implements SharedPreferences.Editor {
        private final SharedPreferences.Editor mEditor;
        final Obfuscator mObfuscator;

        public EncryptedEditor(SharedPreferences.Editor editor, Obfuscator obfuscator) {
            this.mEditor = editor;
            this.mObfuscator = obfuscator;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putString(String str, String str2) {
            if (!TextUtils.isEmpty(str2)) {
                this.mEditor.putString(str, obfuscate(str, str2));
            } else {
                remove(str);
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putStringSet(String str, Set<String> set) {
            throw new UnsupportedOperationException("#putStringSet is not available");
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putInt(String str, int i) {
            putString(str, Integer.toString(i));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putLong(String str, long j) {
            putString(str, Long.toString(j));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putFloat(String str, float f) {
            putString(str, Float.toString(f));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putBoolean(String str, boolean z) {
            putString(str, Boolean.toString(z));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor remove(String str) {
            this.mEditor.remove(str);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor clear() {
            this.mEditor.clear();
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public boolean commit() {
            return this.mEditor.commit();
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
            this.mEditor.apply();
        }

        private String obfuscate(String str, String str2) {
            return this.mObfuscator.encrypt(str, str2);
        }
    }

    /* loaded from: classes2.dex */
    public static class AESObfuscator implements Obfuscator {
        private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
        private static final String DEFAULT_HEADER = "com.serenegiant.AESObfuscator-1|";
        private static final String KEYGEN_ALGORITHM = "PBEWITHSHAAND256BITAES-CBC-BC";
        private final String header;
        private final Cipher mDecryptor;
        private final Cipher mEncryptor;

        public AESObfuscator(char[] cArr, byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
            this(null, cArr, bArr, 1024, 256, bArr2);
        }

        public AESObfuscator(String str, char[] cArr, byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
            this(str, cArr, bArr, 1024, 256, bArr2);
        }

        public AESObfuscator(String str, char[] cArr, byte[] bArr, int i, int i2, byte[] bArr2) throws GeneralSecurityException {
            this.header = TextUtils.isEmpty(str) ? DEFAULT_HEADER : str;
            SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance(KEYGEN_ALGORITHM).generateSecret(new PBEKeySpec(cArr, bArr, i, i2)).getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            this.mEncryptor = cipher;
            cipher.init(1, secretKeySpec, new IvParameterSpec(bArr2));
            Cipher cipher2 = Cipher.getInstance(CIPHER_ALGORITHM);
            this.mDecryptor = cipher2;
            cipher2.init(2, secretKeySpec, new IvParameterSpec(bArr2));
        }

        @Override // com.serenegiant.preference.EncryptedSharedPreferences.Obfuscator
        public String encrypt(String str, String str2) {
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            try {
                return new String(Base64.encode(this.mEncryptor.doFinal((this.header + str + str2).getBytes(CharsetsUtils.UTF8)), 2), CharsetsUtils.UTF8);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException("Invalid environment", e);
            }
        }

        @Override // com.serenegiant.preference.EncryptedSharedPreferences.Obfuscator
        public String decrypt(String str, String str2) throws ObfuscatorException {
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            try {
                String str3 = new String(this.mDecryptor.doFinal(Base64.decode(str2, 2)), CharsetsUtils.UTF8);
                if (str3.indexOf(this.header + str) != 0) {
                    throw new ObfuscatorException("Header not found (invalid data or key)");
                }
                return str3.substring(this.header.length() + str.length());
            } catch (IllegalArgumentException e) {
                throw new ObfuscatorException(e.getMessage() + "");
            } catch (BadPaddingException e2) {
                throw new ObfuscatorException(e2.getMessage() + "");
            } catch (IllegalBlockSizeException e3) {
                throw new ObfuscatorException(e3.getMessage() + "");
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class KeyStoreObfuscator implements Obfuscator {
        private static final String ALGORITHM_RSA = "RSA";
        private static final String CIPHER_TRANSFORMATION_AES = "AES/CBC/PKCS7Padding";
        private static final String CIPHER_TRANSFORMATION_RSA = "RSA/ECB/PKCS1Padding";
        private static final String KEY_STORE_TYPE = "AndroidKeyStore";
        private static final String header = "com.serenegiant.KeyStoreObfuscator-1|";
        private final String alias;
        private final Context mContext;

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

        public KeyStoreObfuscator(Context context, String str) throws GeneralSecurityException {
            this.mContext = context;
            this.alias = context.getPackageName() + (!TextUtils.isEmpty(str) ? ":" + str : "");
            try {
                if (getKey() != null) {
                    return;
                }
                throw new GeneralSecurityException("key entry not found");
            } catch (IOException e) {
                throw new GeneralSecurityException(e);
            }
        }

        @Override // com.serenegiant.preference.EncryptedSharedPreferences.Obfuscator
        public String encrypt(String str, String str2) {
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            return encryptAES(str, str2);
        }

        @Override // com.serenegiant.preference.EncryptedSharedPreferences.Obfuscator
        public String decrypt(String str, String str2) throws ObfuscatorException {
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            return decryptAES(str, str2);
        }

        private String encryptRSA(String str, String str2) {
            try {
                PublicKey publicKey = ((KeyStore.PrivateKeyEntry) getKey()).getCertificate().getPublicKey();
                Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
                cipher.init(1, publicKey);
                return base64Encode(cipher.doFinal((header + str + str2).getBytes(CharsetsUtils.UTF8)));
            } catch (IOException | GeneralSecurityException e) {
                throw new RuntimeException("Invalid environment", e);
            }
        }

        private String decryptRSA(String str, String str2) throws ObfuscatorException {
            try {
                PrivateKey privateKey = ((KeyStore.PrivateKeyEntry) getKey()).getPrivateKey();
                Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
                cipher.init(2, privateKey);
                String str3 = new String(cipher.doFinal(base64decode(str2)), CharsetsUtils.UTF8);
                if (str3.indexOf(header + str) != 0) {
                    throw new ObfuscatorException("Header not found (invalid data or key)");
                }
                return str3.substring(37 + str.length());
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

        private String encryptAES(String str, String str2) {
            try {
                SecretKey secretKey = ((KeyStore.SecretKeyEntry) getKey()).getSecretKey();
                Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_AES);
                cipher.init(1, secretKey);
                return base64Encode(cipher.getIV()) + "|" + base64Encode(cipher.doFinal((header + str + str2).getBytes(CharsetsUtils.UTF8)));
            } catch (IOException | GeneralSecurityException e) {
                throw new RuntimeException("Invalid environment", e);
            }
        }

        private String decryptAES(String str, String str2) throws ObfuscatorException {
            String[] split = str2.split("\\|");
            if (split.length < 2) {
                throw new ObfuscatorException("Unexpected encrypted format");
            }
            try {
                SecretKey secretKey = ((KeyStore.SecretKeyEntry) getKey()).getSecretKey();
                Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_AES);
                cipher.init(2, secretKey, new IvParameterSpec(base64decode(split[0])));
                String str3 = new String(cipher.doFinal(base64decode(split[1])), CharsetsUtils.UTF8);
                if (str3.indexOf(header + str) != 0) {
                    throw new ObfuscatorException("Header not found (invalid data or key)");
                }
                return str3.substring(37 + str.length());
            } catch (IOException e) {
                throw new ObfuscatorException(e.getMessage() + "");
            } catch (IllegalArgumentException e2) {
                throw new ObfuscatorException(e2.getMessage() + "");
            } catch (GeneralSecurityException e3) {
                throw new ObfuscatorException(e3.getMessage() + "");
            }
        }

        private KeyStore.Entry getKey() throws GeneralSecurityException, IOException {
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
            keyStore.load(null);
            boolean containsAlias = keyStore.containsAlias(this.alias);
            if (containsAlias && ALGORITHM_RSA.equals(keyStore.getKey(this.alias, null).getAlgorithm())) {
                keyStore.deleteEntry(this.alias);
                containsAlias = false;
            }
            if (!containsAlias) {
                createKey();
            }
            return keyStore.getEntry(this.alias, null);
        }

        private void createKey() throws GeneralSecurityException {
            Calendar.getInstance(Locale.ENGLISH);
            Calendar.getInstance(Locale.ENGLISH).add(1, 1);
            KeyGenParameterSpec build = new KeyGenParameterSpec.Builder(this.alias, 3).setCertificateSubject(new X500Principal("CN=" + this.alias + " O=" + this.mContext.getPackageName())).setCertificateSerialNumber(BigInteger.ONE).setBlockModes("CBC").setEncryptionPaddings("PKCS7Padding").build();
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
}
