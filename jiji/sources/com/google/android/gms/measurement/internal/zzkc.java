package com.google.android.gms.measurement.internal;

import android.os.Handler;
import android.os.Looper;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public final class zzkc extends zzf {
    protected final zzkb zza;
    protected final zzka zzb;
    protected final zzjy zzc;
    private Handler zzd;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzkc(zzfr zzfrVar) {
        super(zzfrVar);
        this.zza = new zzkb(this);
        this.zzb = new zzka(this);
        this.zzc = new zzjy(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ void zzj(zzkc zzkcVar, long j) {
        zzkcVar.zzg();
        zzkcVar.zzm();
        zzkcVar.zzt.zzay().zzj().zzb("Activity paused, time", Long.valueOf(j));
        zzkcVar.zzc.zza(j);
        if (zzkcVar.zzt.zzf().zzu()) {
            zzkcVar.zzb.zzb(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ void zzl(zzkc zzkcVar, long j) {
        zzkcVar.zzg();
        zzkcVar.zzm();
        zzkcVar.zzt.zzay().zzj().zzb("Activity resumed, time", Long.valueOf(j));
        if (zzkcVar.zzt.zzf().zzu() || zzkcVar.zzt.zzm().zzm.zzb()) {
            zzkcVar.zzb.zzc(j);
        }
        zzkcVar.zzc.zzb();
        zzkb zzkbVar = zzkcVar.zza;
        zzkbVar.zza.zzg();
        if (zzkbVar.zza.zzt.zzJ()) {
            zzkbVar.zzb(zzkbVar.zza.zzt.zzav().currentTimeMillis(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzm() {
        zzg();
        if (this.zzd == null) {
            this.zzd = new com.google.android.gms.internal.measurement.zzby(Looper.getMainLooper());
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    protected final boolean zzf() {
        return false;
    }
}
