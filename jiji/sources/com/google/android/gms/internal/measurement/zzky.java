package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
final class zzky extends zzla {
    private zzky() {
        super(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzky(zzkx zzkxVar) {
        super(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzla
    public final void zza(Object obj, long j) {
        ((zzkm) zzmy.zzf(obj, j)).zzb();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzla
    public final void zzb(Object obj, Object obj2, long j) {
        zzkm zzkmVar = (zzkm) zzmy.zzf(obj, j);
        zzkm zzkmVar2 = (zzkm) zzmy.zzf(obj2, j);
        int size = zzkmVar.size();
        int size2 = zzkmVar2.size();
        if (size > 0 && size2 > 0) {
            if (!zzkmVar.zzc()) {
                zzkmVar = zzkmVar.zzd(size2 + size);
            }
            zzkmVar.addAll(zzkmVar2);
        }
        if (size > 0) {
            zzkmVar2 = zzkmVar;
        }
        zzmy.zzs(obj, j, zzkmVar2);
    }
}
