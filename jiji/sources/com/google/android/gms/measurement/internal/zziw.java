package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public final class zziw extends zzap {
    final /* synthetic */ zzjm zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zziw(zzjm zzjmVar, zzgm zzgmVar) {
        super(zzgmVar);
        this.zza = zzjmVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzap
    public final void zzc() {
        zzjm zzjmVar = this.zza;
        zzjmVar.zzg();
        if (zzjmVar.zzL()) {
            zzjmVar.zzt.zzay().zzj().zza("Inactivity, disconnecting from the service");
            zzjmVar.zzs();
        }
    }
}
