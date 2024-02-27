package com.google.android.gms.oss.licenses;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes.dex */
final class zzj extends TaskApiCall<zzn, String> {
    private final /* synthetic */ String zzt;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzj(zzh zzhVar, String str) {
        this.zzt = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    public final /* synthetic */ void doExecute(zzn zznVar, TaskCompletionSource<String> taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(zznVar.zzb(this.zzt));
    }
}
