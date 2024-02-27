package com.google.android.gms.measurement.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzks {
    final String zza;
    long zzb;

    private zzks(zzkt zzktVar, String str) {
        this.zza = str;
        this.zzb = zzktVar.zzav().elapsedRealtime();
    }
}
