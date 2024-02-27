package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zabk implements Runnable {
    private final /* synthetic */ GoogleApiManager.zaa zaiy;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zabk(GoogleApiManager.zaa zaaVar) {
        this.zaiy = zaaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zaiy.zabh();
    }
}
