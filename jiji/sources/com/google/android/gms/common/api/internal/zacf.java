package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zacf implements Runnable {
    private final /* synthetic */ zace zakk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zacf(zace zaceVar) {
        this.zakk = zaceVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zach zachVar;
        zachVar = this.zakk.zakj;
        zachVar.zag(new ConnectionResult(4));
    }
}
