package com.serenegiant.security;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.serenegiant.system.BuildCheck;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class SignatureHelper {
    private SignatureHelper() {
    }

    public static boolean checkSignature(Context context, String str) throws IllegalArgumentException, PackageManager.NameNotFoundException {
        Signature[] signatureArr;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("context or key is null");
        }
        Signature signature = new Signature(str);
        PackageManager packageManager = context.getPackageManager();
        if (BuildCheck.isPie()) {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 134217728);
            if (packageInfo.signingInfo.hasMultipleSigners()) {
                signatureArr = packageInfo.signingInfo.getApkContentsSigners();
            } else {
                signatureArr = packageInfo.signingInfo.getSigningCertificateHistory();
            }
        } else {
            signatureArr = packageManager.getPackageInfo(context.getPackageName(), 64).signatures;
        }
        boolean z = true;
        for (Signature signature2 : signatureArr) {
            z &= signature.equals(signature2);
        }
        return z;
    }

    public static String getSignature(Context context) throws PackageManager.NameNotFoundException {
        Signature[] signatureArr;
        PackageManager packageManager = context.getPackageManager();
        if (BuildCheck.isPie()) {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 134217728);
            if (packageInfo.signingInfo.hasMultipleSigners()) {
                signatureArr = packageInfo.signingInfo.getApkContentsSigners();
            } else {
                signatureArr = packageInfo.signingInfo.getSigningCertificateHistory();
            }
        } else {
            signatureArr = packageManager.getPackageInfo(context.getPackageName(), 64).signatures;
        }
        StringBuilder sb = new StringBuilder();
        for (Signature signature : signatureArr) {
            if (signature != null) {
                sb.append(signature.toCharsString());
            }
        }
        return sb.toString();
    }

    public static byte[] getSignatureBytes(Context context) throws PackageManager.NameNotFoundException {
        Signature[] signatureArr;
        PackageManager packageManager = context.getPackageManager();
        if (BuildCheck.isPie()) {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 134217728);
            if (packageInfo.signingInfo.hasMultipleSigners()) {
                signatureArr = packageInfo.signingInfo.getApkContentsSigners();
            } else {
                signatureArr = packageInfo.signingInfo.getSigningCertificateHistory();
            }
        } else {
            signatureArr = packageManager.getPackageInfo(context.getPackageName(), 64).signatures;
        }
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        for (Signature signature : signatureArr) {
            if (signature != null) {
                byte[] byteArray = signature.toByteArray();
                int length = byteArray != null ? byteArray.length : 0;
                if (length > 0) {
                    if (length > allocate.remaining()) {
                        allocate.flip();
                        ByteBuffer allocate2 = ByteBuffer.allocate(allocate.capacity() + (length * 2));
                        allocate2.put(allocate);
                        allocate = allocate2;
                    }
                    allocate.put(byteArray);
                }
            }
        }
        allocate.flip();
        int limit = allocate.limit();
        if (limit > 0) {
            byte[] bArr = new byte[limit];
            allocate.get(bArr);
            return bArr;
        }
        return null;
    }

    public static byte[] getSignaturesDigest(Context context, String str) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        Signature[] signatureArr;
        PackageManager packageManager = context.getPackageManager();
        if (BuildCheck.isPie()) {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 134217728);
            if (packageInfo.signingInfo.hasMultipleSigners()) {
                signatureArr = packageInfo.signingInfo.getApkContentsSigners();
            } else {
                signatureArr = packageInfo.signingInfo.getSigningCertificateHistory();
            }
        } else {
            signatureArr = packageManager.getPackageInfo(context.getPackageName(), 64).signatures;
        }
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        for (Signature signature : signatureArr) {
            if (signature != null) {
                messageDigest.update(signature.toByteArray());
            }
        }
        return messageDigest.digest();
    }
}
