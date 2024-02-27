package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
final class zzld implements zzlk {
    private final zzlk[] zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzld(zzlk... zzlkVarArr) {
        this.zza = zzlkVarArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzlk
    public final zzlj zzb(Class cls) {
        zzlk[] zzlkVarArr = this.zza;
        for (int i = 0; i < 2; i++) {
            zzlk zzlkVar = zzlkVarArr[i];
            if (zzlkVar.zzc(cls)) {
                return zzlkVar.zzb(cls);
            }
        }
        throw new UnsupportedOperationException("No factory is available for message type: ".concat(String.valueOf(cls.getName())));
    }

    @Override // com.google.android.gms.internal.measurement.zzlk
    public final boolean zzc(Class cls) {
        zzlk[] zzlkVarArr = this.zza;
        for (int i = 0; i < 2; i++) {
            if (zzlkVarArr[i].zzc(cls)) {
                return true;
            }
        }
        return false;
    }
}
