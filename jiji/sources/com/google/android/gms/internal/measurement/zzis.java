package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
public final class zzis {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(byte[] bArr, int i, zzir zzirVar) throws zzkp {
        int zzj = zzj(bArr, i, zzirVar);
        int i2 = zzirVar.zza;
        if (i2 < 0) {
            throw zzkp.zzd();
        }
        if (i2 <= bArr.length - zzj) {
            if (i2 == 0) {
                zzirVar.zzc = zzje.zzb;
                return zzj;
            }
            zzirVar.zzc = zzje.zzl(bArr, zzj, i2);
            return zzj + i2;
        }
        throw zzkp.zzf();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzb(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzc(zzlx zzlxVar, byte[] bArr, int i, int i2, int i3, zzir zzirVar) throws IOException {
        Object zze = zzlxVar.zze();
        int zzn = zzn(zze, zzlxVar, bArr, i, i2, i3, zzirVar);
        zzlxVar.zzf(zze);
        zzirVar.zzc = zze;
        return zzn;
    }

    static int zzd(zzlx zzlxVar, byte[] bArr, int i, int i2, zzir zzirVar) throws IOException {
        Object zze = zzlxVar.zze();
        int zzo = zzo(zze, zzlxVar, bArr, i, i2, zzirVar);
        zzlxVar.zzf(zze);
        zzirVar.zzc = zze;
        return zzo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zze(zzlx zzlxVar, int i, byte[] bArr, int i2, int i3, zzkm zzkmVar, zzir zzirVar) throws IOException {
        int zzd = zzd(zzlxVar, bArr, i2, i3, zzirVar);
        zzkmVar.add(zzirVar.zzc);
        while (zzd < i3) {
            int zzj = zzj(bArr, zzd, zzirVar);
            if (i != zzirVar.zza) {
                break;
            }
            zzd = zzd(zzlxVar, bArr, zzj, i3, zzirVar);
            zzkmVar.add(zzirVar.zzc);
        }
        return zzd;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzf(byte[] bArr, int i, zzkm zzkmVar, zzir zzirVar) throws IOException {
        zzkg zzkgVar = (zzkg) zzkmVar;
        int zzj = zzj(bArr, i, zzirVar);
        int i2 = zzirVar.zza + zzj;
        while (zzj < i2) {
            zzj = zzj(bArr, zzj, zzirVar);
            zzkgVar.zzh(zzirVar.zza);
        }
        if (zzj == i2) {
            return zzj;
        }
        throw zzkp.zzf();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzg(byte[] bArr, int i, zzir zzirVar) throws zzkp {
        int zzj = zzj(bArr, i, zzirVar);
        int i2 = zzirVar.zza;
        if (i2 >= 0) {
            if (i2 == 0) {
                zzirVar.zzc = "";
                return zzj;
            }
            zzirVar.zzc = new String(bArr, zzj, i2, zzkn.zzb);
            return zzj + i2;
        }
        throw zzkp.zzd();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzh(byte[] bArr, int i, zzir zzirVar) throws zzkp {
        int zzj = zzj(bArr, i, zzirVar);
        int i2 = zzirVar.zza;
        if (i2 >= 0) {
            if (i2 == 0) {
                zzirVar.zzc = "";
                return zzj;
            }
            zzirVar.zzc = zznd.zzd(bArr, zzj, i2);
            return zzj + i2;
        }
        throw zzkp.zzd();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzi(int i, byte[] bArr, int i2, int i3, zzmp zzmpVar, zzir zzirVar) throws zzkp {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                int zzm = zzm(bArr, i2, zzirVar);
                zzmpVar.zzj(i, Long.valueOf(zzirVar.zzb));
                return zzm;
            } else if (i4 == 1) {
                zzmpVar.zzj(i, Long.valueOf(zzp(bArr, i2)));
                return i2 + 8;
            } else if (i4 == 2) {
                int zzj = zzj(bArr, i2, zzirVar);
                int i5 = zzirVar.zza;
                if (i5 < 0) {
                    throw zzkp.zzd();
                }
                if (i5 <= bArr.length - zzj) {
                    if (i5 == 0) {
                        zzmpVar.zzj(i, zzje.zzb);
                    } else {
                        zzmpVar.zzj(i, zzje.zzl(bArr, zzj, i5));
                    }
                    return zzj + i5;
                }
                throw zzkp.zzf();
            } else if (i4 != 3) {
                if (i4 == 5) {
                    zzmpVar.zzj(i, Integer.valueOf(zzb(bArr, i2)));
                    return i2 + 4;
                }
                throw zzkp.zzb();
            } else {
                int i6 = (i & (-8)) | 4;
                zzmp zzf = zzmp.zzf();
                int i7 = 0;
                while (true) {
                    if (i2 >= i3) {
                        break;
                    }
                    int zzj2 = zzj(bArr, i2, zzirVar);
                    int i8 = zzirVar.zza;
                    if (i8 == i6) {
                        i7 = i8;
                        i2 = zzj2;
                        break;
                    }
                    i7 = i8;
                    i2 = zzi(i8, bArr, zzj2, i3, zzf, zzirVar);
                }
                if (i2 > i3 || i7 != i6) {
                    throw zzkp.zze();
                }
                zzmpVar.zzj(i, zzf);
                return i2;
            }
        }
        throw zzkp.zzb();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzj(byte[] bArr, int i, zzir zzirVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b >= 0) {
            zzirVar.zza = b;
            return i2;
        }
        return zzk(b, bArr, i2, zzirVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzk(int i, byte[] bArr, int i2, zzir zzirVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzirVar.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzirVar.zza = i5 | (b2 << Ascii.SO);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzirVar.zza = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzirVar.zza = i9 | (b4 << Ascii.FS);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzirVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzl(int i, byte[] bArr, int i2, int i3, zzkm zzkmVar, zzir zzirVar) {
        zzkg zzkgVar = (zzkg) zzkmVar;
        int zzj = zzj(bArr, i2, zzirVar);
        zzkgVar.zzh(zzirVar.zza);
        while (zzj < i3) {
            int zzj2 = zzj(bArr, zzj, zzirVar);
            if (i != zzirVar.zza) {
                break;
            }
            zzj = zzj(bArr, zzj2, zzirVar);
            zzkgVar.zzh(zzirVar.zza);
        }
        return zzj;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzm(byte[] bArr, int i, zzir zzirVar) {
        byte b;
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzirVar.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b2 = bArr[i2];
        long j2 = (j & 127) | ((b2 & Byte.MAX_VALUE) << 7);
        int i4 = 7;
        while (b2 < 0) {
            int i5 = i3 + 1;
            i4 += 7;
            j2 |= (b & Byte.MAX_VALUE) << i4;
            b2 = bArr[i3];
            i3 = i5;
        }
        zzirVar.zzb = j2;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzn(Object obj, zzlx zzlxVar, byte[] bArr, int i, int i2, int i3, zzir zzirVar) throws IOException {
        int zzc = ((zzlp) zzlxVar).zzc(obj, bArr, i, i2, i3, zzirVar);
        zzirVar.zzc = obj;
        return zzc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzo(Object obj, zzlx zzlxVar, byte[] bArr, int i, int i2, zzir zzirVar) throws IOException {
        int i3 = i + 1;
        int i4 = bArr[i];
        if (i4 < 0) {
            i3 = zzk(i4, bArr, i3, zzirVar);
            i4 = zzirVar.zza;
        }
        int i5 = i3;
        if (i4 < 0 || i4 > i2 - i5) {
            throw zzkp.zzf();
        }
        int i6 = i4 + i5;
        zzlxVar.zzh(obj, bArr, i5, i6, zzirVar);
        zzirVar.zzc = obj;
        return i6;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long zzp(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }
}
