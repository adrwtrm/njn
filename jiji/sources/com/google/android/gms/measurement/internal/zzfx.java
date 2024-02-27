package com.google.android.gms.measurement.internal;

import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
final class zzfx implements Callable {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ zzgj zzd;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfx(zzgj zzgjVar, String str, String str2, String str3) {
        this.zzd = zzgjVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzkt zzktVar;
        zzkt zzktVar2;
        zzktVar = this.zzd.zza;
        zzktVar.zzA();
        zzktVar2 = this.zzd.zza;
        return zzktVar2.zzi().zzs(this.zza, this.zzb, this.zzc);
    }
}
