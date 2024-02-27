package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public final class zzis implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ com.google.android.gms.internal.measurement.zzcf zzb;
    final /* synthetic */ zzjm zzc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzis(zzjm zzjmVar, zzq zzqVar, com.google.android.gms.internal.measurement.zzcf zzcfVar) {
        this.zzc = zzjmVar;
        this.zza = zzqVar;
        this.zzb = zzcfVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfr zzfrVar;
        zzdx zzdxVar;
        String str = null;
        try {
            try {
                if (this.zzc.zzt.zzm().zzc().zzi(zzah.ANALYTICS_STORAGE)) {
                    zzjm zzjmVar = this.zzc;
                    zzdxVar = zzjmVar.zzb;
                    if (zzdxVar == null) {
                        zzjmVar.zzt.zzay().zzd().zza("Failed to get app instance id");
                        zzfrVar = this.zzc.zzt;
                    } else {
                        Preconditions.checkNotNull(this.zza);
                        str = zzdxVar.zzd(this.zza);
                        if (str != null) {
                            this.zzc.zzt.zzq().zzO(str);
                            this.zzc.zzt.zzm().zze.zzb(str);
                        }
                        this.zzc.zzQ();
                        zzfrVar = this.zzc.zzt;
                    }
                } else {
                    this.zzc.zzt.zzay().zzl().zza("Analytics storage consent denied; will not get app instance id");
                    this.zzc.zzt.zzq().zzO(null);
                    this.zzc.zzt.zzm().zze.zzb(null);
                    zzfrVar = this.zzc.zzt;
                }
            } catch (RemoteException e) {
                this.zzc.zzt.zzay().zzd().zzb("Failed to get app instance id", e);
                zzfrVar = this.zzc.zzt;
            }
            zzfrVar.zzv().zzV(this.zzb, str);
        } catch (Throwable th) {
            this.zzc.zzt.zzv().zzV(this.zzb, null);
            throw th;
        }
    }
}
