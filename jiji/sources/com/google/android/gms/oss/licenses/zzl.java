package com.google.android.gms.oss.licenses;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes.dex */
final class zzl extends TaskApiCall<zzn, String> {
    private final /* synthetic */ com.google.android.gms.internal.oss_licenses.zzc zzu;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzl(zzh zzhVar, com.google.android.gms.internal.oss_licenses.zzc zzcVar) {
        this.zzu = zzcVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    public final /* synthetic */ void doExecute(zzn zznVar, TaskCompletionSource<String> taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(zznVar.zza(this.zzu));
    }
}
