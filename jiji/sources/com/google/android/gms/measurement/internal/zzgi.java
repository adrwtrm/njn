package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
final class zzgi implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ zzgj zze;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgi(zzgj zzgjVar, String str, String str2, String str3, long j) {
        this.zze = zzgjVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzkt zzktVar;
        zzkt zzktVar2;
        String str = this.zza;
        if (str == null) {
            zzktVar2 = this.zze.zza;
            zzktVar2.zzR(this.zzb, null);
            return;
        }
        zzie zzieVar = new zzie(this.zzc, str, this.zzd);
        zzktVar = this.zze.zza;
        zzktVar.zzR(this.zzb, zzieVar);
    }
}
