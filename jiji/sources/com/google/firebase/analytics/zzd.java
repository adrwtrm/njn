package com.google.firebase.analytics;

import android.os.Bundle;
import com.google.android.gms.internal.measurement.zzef;
import com.google.android.gms.measurement.internal.zzgr;
import com.google.android.gms.measurement.internal.zzgs;
import com.google.android.gms.measurement.internal.zzhy;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.2.0 */
/* loaded from: classes2.dex */
final class zzd implements zzhy {
    final /* synthetic */ zzef zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzd(zzef zzefVar) {
        this.zza = zzefVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final int zza(String str) {
        return this.zza.zza(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final long zzb() {
        return this.zza.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final Object zzg(int i) {
        return this.zza.zzi(i);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final String zzh() {
        return this.zza.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final String zzi() {
        return this.zza.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final String zzj() {
        return this.zza.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final String zzk() {
        return this.zza.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final List zzm(String str, String str2) {
        return this.zza.zzq(str, str2);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final Map zzo(String str, String str2, boolean z) {
        return this.zza.zzr(str, str2, z);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final void zzp(String str) {
        this.zza.zzv(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final void zzq(String str, String str2, Bundle bundle) {
        this.zza.zzw(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final void zzr(String str) {
        this.zza.zzx(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final void zzs(String str, String str2, Bundle bundle) {
        this.zza.zzz(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final void zzt(String str, String str2, Bundle bundle, long j) {
        this.zza.zzA(str, str2, bundle, j);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final void zzu(zzgs zzgsVar) {
        this.zza.zzC(zzgsVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final void zzv(Bundle bundle) {
        this.zza.zzE(bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final void zzw(zzgr zzgrVar) {
        this.zza.zzK(zzgrVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzhy
    public final void zzx(zzgs zzgsVar) {
        this.zza.zzP(zzgsVar);
    }
}
