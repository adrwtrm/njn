package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.Callable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzkm implements Callable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzkt zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzkm(zzkt zzktVar, zzq zzqVar) {
        this.zzb = zzktVar;
        this.zza = zzqVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        if (!this.zzb.zzh((String) Preconditions.checkNotNull(this.zza.zza)).zzi(zzah.ANALYTICS_STORAGE) || !zzai.zzb(this.zza.zzv).zzi(zzah.ANALYTICS_STORAGE)) {
            this.zzb.zzay().zzj().zza("Analytics storage consent denied. Returning null app instance id");
            return null;
        }
        return this.zzb.zzd(this.zza).zzu();
    }
}
