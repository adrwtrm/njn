package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import org.checkerframework.dataflow.qual.Pure;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public class zzgk implements zzgm {
    protected final zzfr zzt;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgk(zzfr zzfrVar) {
        Preconditions.checkNotNull(zzfrVar);
        this.zzt = zzfrVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzgm
    @Pure
    public final Context zzau() {
        throw null;
    }

    @Override // com.google.android.gms.measurement.internal.zzgm
    @Pure
    public final Clock zzav() {
        throw null;
    }

    @Override // com.google.android.gms.measurement.internal.zzgm
    @Pure
    public final zzab zzaw() {
        throw null;
    }

    public void zzax() {
        this.zzt.zzaz().zzax();
    }

    @Override // com.google.android.gms.measurement.internal.zzgm
    @Pure
    public final zzeh zzay() {
        throw null;
    }

    @Override // com.google.android.gms.measurement.internal.zzgm
    @Pure
    public final zzfo zzaz() {
        throw null;
    }

    public void zzg() {
        this.zzt.zzaz().zzg();
    }
}
