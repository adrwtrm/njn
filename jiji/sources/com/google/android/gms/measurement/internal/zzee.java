package com.google.android.gms.measurement.internal;

import android.util.Log;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public final class zzee implements Runnable {
    final /* synthetic */ int zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ Object zzc;
    final /* synthetic */ Object zzd;
    final /* synthetic */ Object zze;
    final /* synthetic */ zzeh zzf;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzee(zzeh zzehVar, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzf = zzehVar;
        this.zza = i;
        this.zzb = str;
        this.zzc = obj;
        this.zzd = obj2;
        this.zze = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        char c;
        long j;
        char c2;
        long j2;
        zzew zzm = this.zzf.zzt.zzm();
        if (zzm.zzx()) {
            zzeh zzehVar = this.zzf;
            c = zzehVar.zza;
            if (c == 0) {
                if (zzehVar.zzt.zzf().zzy()) {
                    zzeh zzehVar2 = this.zzf;
                    zzehVar2.zzt.zzaw();
                    zzehVar2.zza = 'C';
                } else {
                    zzeh zzehVar3 = this.zzf;
                    zzehVar3.zzt.zzaw();
                    zzehVar3.zza = 'c';
                }
            }
            zzeh zzehVar4 = this.zzf;
            j = zzehVar4.zzb;
            if (j < 0) {
                zzehVar4.zzt.zzf().zzh();
                zzehVar4.zzb = 74029L;
            }
            char charAt = "01VDIWEA?".charAt(this.zza);
            zzeh zzehVar5 = this.zzf;
            c2 = zzehVar5.zza;
            j2 = zzehVar5.zzb;
            String str = "2" + charAt + c2 + j2 + ":" + zzeh.zzo(true, this.zzb, this.zzc, this.zzd, this.zze);
            if (str.length() > 1024) {
                str = this.zzb.substring(0, 1024);
            }
            zzeu zzeuVar = zzm.zzb;
            if (zzeuVar != null) {
                zzeuVar.zzb(str, 1L);
                return;
            }
            return;
        }
        Log.println(6, this.zzf.zzq(), "Persisted config not initialized. Not logging error/warn");
    }
}
