package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzfx extends zzkf implements zzln {
    private static final zzfx zza;
    private int zzd;
    private long zzg;
    private float zzh;
    private double zzi;
    private String zze = "";
    private String zzf = "";
    private zzkm zzj = zzbE();

    static {
        zzfx zzfxVar = new zzfx();
        zza = zzfxVar;
        zzkf.zzbL(zzfx.class, zzfxVar);
    }

    private zzfx() {
    }

    public static zzfw zze() {
        return (zzfw) zza.zzbx();
    }

    public static /* synthetic */ void zzj(zzfx zzfxVar, String str) {
        str.getClass();
        zzfxVar.zzd |= 1;
        zzfxVar.zze = str;
    }

    public static /* synthetic */ void zzk(zzfx zzfxVar, String str) {
        str.getClass();
        zzfxVar.zzd |= 2;
        zzfxVar.zzf = str;
    }

    public static /* synthetic */ void zzm(zzfx zzfxVar) {
        zzfxVar.zzd &= -3;
        zzfxVar.zzf = zza.zzf;
    }

    public static /* synthetic */ void zzn(zzfx zzfxVar, long j) {
        zzfxVar.zzd |= 4;
        zzfxVar.zzg = j;
    }

    public static /* synthetic */ void zzo(zzfx zzfxVar) {
        zzfxVar.zzd &= -5;
        zzfxVar.zzg = 0L;
    }

    public static /* synthetic */ void zzp(zzfx zzfxVar, double d) {
        zzfxVar.zzd |= 16;
        zzfxVar.zzi = d;
    }

    public static /* synthetic */ void zzq(zzfx zzfxVar) {
        zzfxVar.zzd &= -17;
        zzfxVar.zzi = 0.0d;
    }

    public static /* synthetic */ void zzr(zzfx zzfxVar, zzfx zzfxVar2) {
        zzfxVar2.getClass();
        zzfxVar.zzz();
        zzfxVar.zzj.add(zzfxVar2);
    }

    public static /* synthetic */ void zzs(zzfx zzfxVar, Iterable iterable) {
        zzfxVar.zzz();
        zzio.zzbt(iterable, zzfxVar.zzj);
    }

    private final void zzz() {
        zzkm zzkmVar = this.zzj;
        if (zzkmVar.zzc()) {
            return;
        }
        this.zzj = zzkf.zzbF(zzkmVar);
    }

    public final double zza() {
        return this.zzi;
    }

    public final float zzb() {
        return this.zzh;
    }

    public final int zzc() {
        return this.zzj.size();
    }

    public final long zzd() {
        return this.zzg;
    }

    public final String zzg() {
        return this.zze;
    }

    public final String zzh() {
        return this.zzf;
    }

    public final List zzi() {
        return this.zzj;
    }

    @Override // com.google.android.gms.internal.measurement.zzkf
    public final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 != 0) {
            if (i2 == 2) {
                return zzbI(zza, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0001\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဂ\u0002\u0004ခ\u0003\u0005က\u0004\u0006\u001b", new Object[]{"zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", zzfx.class});
            } else if (i2 != 3) {
                if (i2 != 4) {
                    if (i2 != 5) {
                        return null;
                    }
                    return zza;
                }
                return new zzfw(null);
            } else {
                return new zzfx();
            }
        }
        return (byte) 1;
    }

    public final boolean zzu() {
        return (this.zzd & 16) != 0;
    }

    public final boolean zzv() {
        return (this.zzd & 8) != 0;
    }

    public final boolean zzw() {
        return (this.zzd & 4) != 0;
    }

    public final boolean zzx() {
        return (this.zzd & 1) != 0;
    }

    public final boolean zzy() {
        return (this.zzd & 2) != 0;
    }
}
