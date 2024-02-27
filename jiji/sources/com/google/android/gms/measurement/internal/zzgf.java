package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
final class zzgf implements Runnable {
    final /* synthetic */ zzkw zza;
    final /* synthetic */ zzq zzb;
    final /* synthetic */ zzgj zzc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgf(zzgj zzgjVar, zzkw zzkwVar, zzq zzqVar) {
        this.zzc = zzgjVar;
        this.zza = zzkwVar;
        this.zzb = zzqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzkt zzktVar;
        zzkt zzktVar2;
        zzkt zzktVar3;
        zzktVar = this.zzc.zza;
        zzktVar.zzA();
        if (this.zza.zza() == null) {
            zzktVar3 = this.zzc.zza;
            zzktVar3.zzP(this.zza, this.zzb);
            return;
        }
        zzktVar2 = this.zzc.zza;
        zzktVar2.zzW(this.zza, this.zzb);
    }
}
