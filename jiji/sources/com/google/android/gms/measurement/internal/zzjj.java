package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
final class zzjj implements Runnable {
    final /* synthetic */ zzjl zza;

    public zzjj(zzjl zzjlVar) {
        this.zza = zzjlVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjm zzjmVar = this.zza.zza;
        Context zzau = zzjmVar.zzt.zzau();
        this.zza.zza.zzt.zzaw();
        zzjm.zzo(zzjmVar, new ComponentName(zzau, "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
