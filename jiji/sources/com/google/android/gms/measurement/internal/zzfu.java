package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
final class zzfu implements Runnable {
    final /* synthetic */ zzac zza;
    final /* synthetic */ zzgj zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfu(zzgj zzgjVar, zzac zzacVar) {
        this.zzb = zzgjVar;
        this.zza = zzacVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzkt zzktVar;
        zzkt zzktVar2;
        zzkt zzktVar3;
        zzktVar = this.zzb.zza;
        zzktVar.zzA();
        if (this.zza.zzc.zza() == null) {
            zzktVar3 = this.zzb.zza;
            zzktVar3.zzN(this.zza);
            return;
        }
        zzktVar2 = this.zzb.zza;
        zzktVar2.zzT(this.zza);
    }
}
