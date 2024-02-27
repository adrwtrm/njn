package com.google.android.gms.internal.oss_licenses;

import android.content.Context;
import android.content.res.Resources;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/* loaded from: classes.dex */
public final class zze {
    public static ArrayList<zzc> zzb(Context context) {
        String[] split = zza(context.getApplicationContext(), "third_party_license_metadata", 0L, -1).split("\n");
        ArrayList<zzc> arrayList = new ArrayList<>(split.length);
        for (String str : split) {
            int indexOf = str.indexOf(32);
            String[] split2 = str.substring(0, indexOf).split(":");
            boolean z = split2.length == 2 && indexOf > 0;
            String valueOf = String.valueOf(str);
            String concat = valueOf.length() != 0 ? "Invalid license meta-data line:\n".concat(valueOf) : new String("Invalid license meta-data line:\n");
            if (!z) {
                throw new IllegalStateException(String.valueOf(concat));
            }
            arrayList.add(zzc.zza(str.substring(indexOf + 1), Long.parseLong(split2[0]), Integer.parseInt(split2[1]), ""));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public static String zza(Context context, zzc zzcVar) {
        long zze = zzcVar.zze();
        int zzf = zzcVar.zzf();
        String path = zzcVar.getPath();
        if (path.isEmpty()) {
            return zza(context, "third_party_licenses", zze, zzf);
        }
        String zza = zza("res/raw/third_party_licenses", path, zze, zzf);
        if (zza != null) {
            return zza;
        }
        throw new RuntimeException(new StringBuilder(String.valueOf(path).length() + 46).append(path).append(" does not contain res/raw/third_party_licenses").toString());
    }

    private static String zza(Context context, String str, long j, int i) {
        Resources resources = context.getApplicationContext().getResources();
        return zza(resources.openRawResource(resources.getIdentifier(str, "raw", resources.getResourcePackageName(zzf.dummy_placeholder))), j, i);
    }

    private static String zza(String str, String str2, long j, int i) {
        JarFile jarFile = null;
        try {
            try {
                JarFile jarFile2 = new JarFile(str2);
                try {
                    JarEntry jarEntry = jarFile2.getJarEntry(str);
                    if (jarEntry == null) {
                        try {
                            jarFile2.close();
                        } catch (IOException unused) {
                        }
                        return null;
                    }
                    String zza = zza(jarFile2.getInputStream(jarEntry), j, i);
                    try {
                        jarFile2.close();
                    } catch (IOException unused2) {
                    }
                    return zza;
                } catch (IOException e) {
                    e = e;
                    throw new RuntimeException("Failed to read license text.", e);
                } catch (Throwable th) {
                    th = th;
                    jarFile = jarFile2;
                    if (jarFile != null) {
                        try {
                            jarFile.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String zza(InputStream inputStream, long j, int i) {
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            inputStream.skip(j);
            if (i <= 0) {
                i = Integer.MAX_VALUE;
            }
            while (i > 0) {
                int read = inputStream.read(bArr, 0, Math.min(i, 1024));
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
                i -= read;
            }
            inputStream.close();
            try {
                return byteArrayOutputStream.toString("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Unsupported encoding UTF8. This should always be supported.", e);
            }
        } catch (IOException e2) {
            throw new RuntimeException("Failed to read license or metadata text.", e2);
        }
    }
}
