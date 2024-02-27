package com.google.android.gms.common.api.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zacq implements zacs {
    private final /* synthetic */ zacp zalb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zacq(zacp zacpVar) {
        this.zalb = zacpVar;
    }

    @Override // com.google.android.gms.common.api.internal.zacs
    public final void zac(BasePendingResult<?> basePendingResult) {
        this.zalb.zakz.remove(basePendingResult);
    }
}
