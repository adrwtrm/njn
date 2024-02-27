package com.google.android.gms.oss.licenses;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

/* loaded from: classes.dex */
final class zzm extends TaskApiCall<zzn, List<com.google.android.gms.internal.oss_licenses.zzc>> {
    private final /* synthetic */ List zzv;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzm(zzh zzhVar, List list) {
        this.zzv = list;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    public final /* synthetic */ void doExecute(zzn zznVar, TaskCompletionSource<List<com.google.android.gms.internal.oss_licenses.zzc>> taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(zznVar.zza(this.zzv));
    }
}
