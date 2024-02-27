package com.google.android.gms.oss.licenses;

import android.content.Context;
import android.util.Log;
import androidx.loader.content.AsyncTaskLoader;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* loaded from: classes.dex */
final class zzo extends AsyncTaskLoader<List<com.google.android.gms.internal.oss_licenses.zzc>> {
    private final zzc zzo;
    private List<com.google.android.gms.internal.oss_licenses.zzc> zzw;

    public zzo(Context context, zzc zzcVar) {
        super(context.getApplicationContext());
        this.zzo = zzcVar;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    /* renamed from: zzd */
    public final List<com.google.android.gms.internal.oss_licenses.zzc> loadInBackground() {
        ArrayList<com.google.android.gms.internal.oss_licenses.zzc> zzb = com.google.android.gms.internal.oss_licenses.zze.zzb(getContext());
        zzh zzb2 = this.zzo.zzb();
        Task<TResult> doRead = zzb2.doRead(new zzm(zzb2, zzb));
        try {
            Tasks.await(doRead);
            if (doRead.isSuccessful()) {
                return (List) doRead.getResult();
            }
        } catch (InterruptedException | ExecutionException e) {
            String valueOf = String.valueOf(e.getMessage());
            Log.w("OssLicensesLoader", valueOf.length() != 0 ? "Error getting license list from service: ".concat(valueOf) : new String("Error getting license list from service: "));
        }
        return zzb;
    }

    @Override // androidx.loader.content.Loader
    /* renamed from: zzb */
    public final void deliverResult(List<com.google.android.gms.internal.oss_licenses.zzc> list) {
        this.zzw = list;
        super.deliverResult(list);
    }

    @Override // androidx.loader.content.Loader
    protected final void onStartLoading() {
        List<com.google.android.gms.internal.oss_licenses.zzc> list = this.zzw;
        if (list != null) {
            deliverResult(list);
        } else {
            forceLoad();
        }
    }

    @Override // androidx.loader.content.Loader
    protected final void onStopLoading() {
        cancelLoad();
    }
}
