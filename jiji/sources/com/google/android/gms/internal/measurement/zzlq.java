package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
final class zzlq implements zzlx {
    private final zzlm zza;
    private final zzmo zzb;
    private final boolean zzc;
    private final zzjs zzd;

    private zzlq(zzmo zzmoVar, zzjs zzjsVar, zzlm zzlmVar) {
        this.zzb = zzmoVar;
        this.zzc = zzjsVar.zzc(zzlmVar);
        this.zzd = zzjsVar;
        this.zza = zzlmVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzlq zzc(zzmo zzmoVar, zzjs zzjsVar, zzlm zzlmVar) {
        return new zzlq(zzmoVar, zzjsVar, zzlmVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzlx
    public final int zza(Object obj) {
        zzmo zzmoVar = this.zzb;
        int zzb = zzmoVar.zzb(zzmoVar.zzd(obj));
        if (this.zzc) {
            this.zzd.zza(obj);
            throw null;
        }
        return zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzlx
    public final int zzb(Object obj) {
        int hashCode = this.zzb.zzd(obj).hashCode();
        if (this.zzc) {
            this.zzd.zza(obj);
            throw null;
        }
        return hashCode;
    }

    @Override // com.google.android.gms.internal.measurement.zzlx
    public final Object zze() {
        zzlm zzlmVar = this.zza;
        if (zzlmVar instanceof zzkf) {
            return ((zzkf) zzlmVar).zzbA();
        }
        return zzlmVar.zzbG().zzaE();
    }

    @Override // com.google.android.gms.internal.measurement.zzlx
    public final void zzf(Object obj) {
        this.zzb.zzg(obj);
        this.zzd.zzb(obj);
    }

    @Override // com.google.android.gms.internal.measurement.zzlx
    public final void zzg(Object obj, Object obj2) {
        zzlz.zzF(this.zzb, obj, obj2);
        if (this.zzc) {
            zzlz.zzE(this.zzd, obj, obj2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzlx
    public final void zzh(Object obj, byte[] bArr, int i, int i2, zzir zzirVar) throws IOException {
        zzkf zzkfVar = (zzkf) obj;
        if (zzkfVar.zzc == zzmp.zzc()) {
            zzkfVar.zzc = zzmp.zzf();
        }
        zzkc zzkcVar = (zzkc) obj;
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlx
    public final void zzi(Object obj, zzng zzngVar) throws IOException {
        this.zzd.zza(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlx
    public final boolean zzj(Object obj, Object obj2) {
        if (this.zzb.zzd(obj).equals(this.zzb.zzd(obj2))) {
            if (this.zzc) {
                this.zzd.zza(obj);
                this.zzd.zza(obj2);
                throw null;
            }
            return true;
        }
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzlx
    public final boolean zzk(Object obj) {
        this.zzd.zza(obj);
        throw null;
    }
}
