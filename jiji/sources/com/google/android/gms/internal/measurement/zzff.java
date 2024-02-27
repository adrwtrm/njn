package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzff extends zzkf implements zzln {
    private static final zzff zza;
    private int zzd;
    private long zze;
    private int zzg;
    private boolean zzl;
    private String zzf = "";
    private zzkm zzh = zzbE();
    private zzkm zzi = zzbE();
    private zzkm zzj = zzbE();
    private String zzk = "";
    private zzkm zzm = zzbE();
    private zzkm zzn = zzbE();
    private String zzo = "";

    static {
        zzff zzffVar = new zzff();
        zza = zzffVar;
        zzkf.zzbL(zzff.class, zzffVar);
    }

    private zzff() {
    }

    public static zzfe zze() {
        return (zzfe) zza.zzbx();
    }

    public static zzff zzg() {
        return zza;
    }

    public static /* synthetic */ void zzo(zzff zzffVar, int i, zzfd zzfdVar) {
        zzfdVar.getClass();
        zzkm zzkmVar = zzffVar.zzi;
        if (!zzkmVar.zzc()) {
            zzffVar.zzi = zzkf.zzbF(zzkmVar);
        }
        zzffVar.zzi.set(i, zzfdVar);
    }

    public final int zza() {
        return this.zzm.size();
    }

    public final int zzb() {
        return this.zzi.size();
    }

    public final long zzc() {
        return this.zze;
    }

    public final zzfd zzd(int i) {
        return (zzfd) this.zzi.get(i);
    }

    public final String zzh() {
        return this.zzf;
    }

    public final String zzi() {
        return this.zzo;
    }

    public final List zzj() {
        return this.zzj;
    }

    public final List zzk() {
        return this.zzn;
    }

    @Override // com.google.android.gms.internal.measurement.zzkf
    public final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 != 0) {
            if (i2 == 2) {
                return zzbI(zza, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0005\u0000\u0001ဂ\u0000\u0002ဈ\u0001\u0003င\u0002\u0004\u001b\u0005\u001b\u0006\u001b\u0007ဈ\u0003\bဇ\u0004\t\u001b\n\u001b\u000bဈ\u0005", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", zzfj.class, "zzi", zzfd.class, "zzj", zzei.class, "zzk", "zzl", "zzm", zzgt.class, "zzn", zzfb.class, "zzo"});
            } else if (i2 != 3) {
                if (i2 != 4) {
                    if (i2 != 5) {
                        return null;
                    }
                    return zza;
                }
                return new zzfe(null);
            } else {
                return new zzff();
            }
        }
        return (byte) 1;
    }

    public final List zzm() {
        return this.zzm;
    }

    public final List zzn() {
        return this.zzh;
    }

    public final boolean zzq() {
        return this.zzl;
    }

    public final boolean zzr() {
        return (this.zzd & 2) != 0;
    }

    public final boolean zzs() {
        return (this.zzd & 1) != 0;
    }
}
