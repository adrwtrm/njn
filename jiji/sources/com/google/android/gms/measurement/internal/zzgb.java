package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
final class zzgb implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzgj zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgb(zzgj zzgjVar, zzq zzqVar) {
        this.zzb = zzgjVar;
        this.zza = zzqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzkt zzktVar;
        zzkt zzktVar2;
        zzktVar = this.zzb.zza;
        zzktVar.zzA();
        zzktVar2 = this.zzb.zza;
        zzq zzqVar = this.zza;
        zzktVar2.zzaz().zzg();
        zzktVar2.zzB();
        Preconditions.checkNotEmpty(zzqVar.zza);
        zzai zzb = zzai.zzb(zzqVar.zzv);
        zzai zzh = zzktVar2.zzh(zzqVar.zza);
        zzktVar2.zzay().zzj().zzc("Setting consent, package, consent", zzqVar.zza, zzb);
        zzktVar2.zzV(zzqVar.zza, zzb);
        if (zzb.zzk(zzh)) {
            zzktVar2.zzQ(zzqVar);
        }
    }
}
