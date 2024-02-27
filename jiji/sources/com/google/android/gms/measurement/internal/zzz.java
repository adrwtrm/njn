package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zznz;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzz extends zzy {
    final /* synthetic */ zzaa zza;
    private final com.google.android.gms.internal.measurement.zzet zzh;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzz(zzaa zzaaVar, String str, int i, com.google.android.gms.internal.measurement.zzet zzetVar) {
        super(str, i);
        this.zza = zzaaVar;
        this.zzh = zzetVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.measurement.internal.zzy
    public final int zza() {
        return this.zzh.zza();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.measurement.internal.zzy
    public final boolean zzb() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.measurement.internal.zzy
    public final boolean zzc() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzd(Long l, Long l2, com.google.android.gms.internal.measurement.zzgm zzgmVar, boolean z) {
        zznz.zzc();
        boolean zzs = this.zza.zzt.zzf().zzs(this.zzb, zzdu.zzU);
        boolean zzg = this.zzh.zzg();
        boolean zzh = this.zzh.zzh();
        boolean zzi = this.zzh.zzi();
        byte b = (zzg || zzh || zzi) ? (byte) 1 : (byte) 0;
        Boolean bool = null;
        bool = null;
        bool = null;
        bool = null;
        bool = null;
        if (!z || b != 0) {
            com.google.android.gms.internal.measurement.zzem zzb = this.zzh.zzb();
            boolean zzg2 = zzb.zzg();
            if (zzgmVar.zzr()) {
                if (!zzb.zzi()) {
                    this.zza.zzt.zzay().zzk().zzb("No number filter for long property. property", this.zza.zzt.zzj().zzf(zzgmVar.zzf()));
                } else {
                    bool = zzj(zzh(zzgmVar.zzb(), zzb.zzc()), zzg2);
                }
            } else if (zzgmVar.zzq()) {
                if (!zzb.zzi()) {
                    this.zza.zzt.zzay().zzk().zzb("No number filter for double property. property", this.zza.zzt.zzj().zzf(zzgmVar.zzf()));
                } else {
                    bool = zzj(zzg(zzgmVar.zza(), zzb.zzc()), zzg2);
                }
            } else if (zzgmVar.zzt()) {
                if (!zzb.zzk()) {
                    if (!zzb.zzi()) {
                        this.zza.zzt.zzay().zzk().zzb("No string or number filter defined. property", this.zza.zzt.zzj().zzf(zzgmVar.zzf()));
                    } else if (zzkv.zzx(zzgmVar.zzg())) {
                        bool = zzj(zzi(zzgmVar.zzg(), zzb.zzc()), zzg2);
                    } else {
                        this.zza.zzt.zzay().zzk().zzc("Invalid user property value for Numeric number filter. property, value", this.zza.zzt.zzj().zzf(zzgmVar.zzf()), zzgmVar.zzg());
                    }
                } else {
                    bool = zzj(zzf(zzgmVar.zzg(), zzb.zzd(), this.zza.zzt.zzay()), zzg2);
                }
            } else {
                this.zza.zzt.zzay().zzk().zzb("User property has no value, property", this.zza.zzt.zzj().zzf(zzgmVar.zzf()));
            }
            this.zza.zzt.zzay().zzj().zzb("Property filter result", bool == null ? "null" : bool);
            if (bool == null) {
                return false;
            }
            this.zzd = true;
            if (!zzi || bool.booleanValue()) {
                if (!z || this.zzh.zzg()) {
                    this.zze = bool;
                }
                if (bool.booleanValue() && b != 0 && zzgmVar.zzs()) {
                    long zzc = zzgmVar.zzc();
                    if (l != null) {
                        zzc = l.longValue();
                    }
                    if (zzs && this.zzh.zzg() && !this.zzh.zzh() && l2 != null) {
                        zzc = l2.longValue();
                    }
                    if (this.zzh.zzh()) {
                        this.zzg = Long.valueOf(zzc);
                    } else {
                        this.zzf = Long.valueOf(zzc);
                    }
                }
                return true;
            }
            return true;
        }
        this.zza.zzt.zzay().zzj().zzc("Property filter already evaluated true and it is not associated with an enhanced audience. audience ID, filter ID", Integer.valueOf(this.zzc), this.zzh.zzj() ? Integer.valueOf(this.zzh.zza()) : null);
        return true;
    }
}
