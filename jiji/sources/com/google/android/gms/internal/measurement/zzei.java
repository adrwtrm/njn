package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzei extends zzkf implements zzln {
    private static final zzei zza;
    private int zzd;
    private int zze;
    private zzkm zzf = zzbE();
    private zzkm zzg = zzbE();
    private boolean zzh;
    private boolean zzi;

    static {
        zzei zzeiVar = new zzei();
        zza = zzeiVar;
        zzkf.zzbL(zzei.class, zzeiVar);
    }

    private zzei() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzi(zzei zzeiVar, int i, zzet zzetVar) {
        zzetVar.getClass();
        zzkm zzkmVar = zzeiVar.zzf;
        if (!zzkmVar.zzc()) {
            zzeiVar.zzf = zzkf.zzbF(zzkmVar);
        }
        zzeiVar.zzf.set(i, zzetVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzj(zzei zzeiVar, int i, zzek zzekVar) {
        zzekVar.getClass();
        zzkm zzkmVar = zzeiVar.zzg;
        if (!zzkmVar.zzc()) {
            zzeiVar.zzg = zzkf.zzbF(zzkmVar);
        }
        zzeiVar.zzg.set(i, zzekVar);
    }

    public final int zza() {
        return this.zze;
    }

    public final int zzb() {
        return this.zzg.size();
    }

    public final int zzc() {
        return this.zzf.size();
    }

    public final zzek zze(int i) {
        return (zzek) this.zzg.get(i);
    }

    public final zzet zzf(int i) {
        return (zzet) this.zzf.get(i);
    }

    public final List zzg() {
        return this.zzg;
    }

    public final List zzh() {
        return this.zzf;
    }

    public final boolean zzk() {
        return (this.zzd & 1) != 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzkf
    public final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 != 0) {
            if (i2 == 2) {
                return zzbI(zza, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001င\u0000\u0002\u001b\u0003\u001b\u0004ဇ\u0001\u0005ဇ\u0002", new Object[]{"zzd", "zze", "zzf", zzet.class, "zzg", zzek.class, "zzh", "zzi"});
            } else if (i2 != 3) {
                if (i2 != 4) {
                    if (i2 != 5) {
                        return null;
                    }
                    return zza;
                }
                return new zzeh(null);
            } else {
                return new zzei();
            }
        }
        return (byte) 1;
    }
}
