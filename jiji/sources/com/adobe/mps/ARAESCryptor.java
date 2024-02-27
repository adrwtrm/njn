package com.adobe.mps;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class ARAESCryptor {
    private static final int AES_BLOCK_SIZE = 16;
    private static final String CIPHER_ALGORITHM = "AES";
    private static final String DECRYPTOR_TRANSFORMATION = "AES/ECB/NoPadding";
    private static final String ENCRYPTOR_TRANSFORMATION = "AES/CBC/NoPadding";
    private static final String ENCRYPTOR_TRANSFORMATION_PADDING = "AES/CBC/PKCS5Padding";
    private Cipher mDecryptor;
    private Cipher mEncryptor;

    public boolean Init_Decryptor(byte[] bArr) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, CIPHER_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DECRYPTOR_TRANSFORMATION);
            this.mDecryptor = cipher;
            cipher.init(2, secretKeySpec);
            return 16 == this.mDecryptor.getBlockSize();
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean Init_Encryptor(byte[] bArr, byte[] bArr2, boolean z) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, CIPHER_ALGORITHM);
            if (z) {
                this.mEncryptor = Cipher.getInstance(ENCRYPTOR_TRANSFORMATION_PADDING);
            } else {
                this.mEncryptor = Cipher.getInstance(ENCRYPTOR_TRANSFORMATION);
            }
            this.mEncryptor.init(1, secretKeySpec, new IvParameterSpec(bArr2));
            return 16 == this.mEncryptor.getBlockSize();
        } catch (Exception unused) {
            return false;
        }
    }

    public void Decrypt(byte[] bArr, int i, byte[] bArr2) {
        try {
            this.mDecryptor.update(bArr, 0, i, bArr2);
        } catch (Exception unused) {
        }
    }

    public int Encrypt(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (i2 < this.mEncryptor.getOutputSize(i)) {
            return -1;
        }
        try {
            return this.mEncryptor.doFinal(bArr, 0, i, bArr2);
        } catch (Exception unused) {
            return -1;
        }
    }
}
