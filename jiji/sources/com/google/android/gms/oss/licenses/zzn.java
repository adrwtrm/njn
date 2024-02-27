package com.google.android.gms.oss.licenses;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import java.util.List;

/* loaded from: classes.dex */
public final class zzn extends GmsClient<zza> {
    public zzn(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 185, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final boolean enableLocalFallback() {
        return true;
    }

    @Override // com.google.android.gms.common.internal.GmsClient, com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12600000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.oss.licenses.IOSSLicenseService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getStartServiceAction() {
        return "com.google.android.gms.oss.licenses.service.START";
    }

    private final zza zzc() {
        try {
            return (zza) super.getService();
        } catch (DeadObjectException | IllegalStateException unused) {
            return null;
        }
    }

    public final synchronized String zzb(String str) throws RemoteException {
        zza zzc;
        zzc = zzc();
        if (zzc != null) {
        } else {
            throw new RemoteException("no service for getLicenseLayoutPackage call");
        }
        return zzc.zzb(str);
    }

    public final synchronized String zza(String str) throws RemoteException {
        zza zzc;
        zzc = zzc();
        if (zzc != null) {
        } else {
            throw new RemoteException("no service for getListLayoutPackage call");
        }
        return zzc.zza(str);
    }

    public final synchronized String zza(com.google.android.gms.internal.oss_licenses.zzc zzcVar) throws RemoteException {
        zza zzc;
        zzc = zzc();
        if (zzc != null) {
        } else {
            throw new RemoteException("no service for getLicenseDetail call");
        }
        return zzc.zzc(zzcVar.toString());
    }

    public final synchronized List<com.google.android.gms.internal.oss_licenses.zzc> zza(List<com.google.android.gms.internal.oss_licenses.zzc> list) throws RemoteException {
        zza zzc;
        zzc = zzc();
        if (zzc != null) {
        } else {
            throw new RemoteException("no service for getLicenseList call");
        }
        return zzc.zza(list);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.oss.licenses.IOSSLicenseService");
        if (queryLocalInterface instanceof zza) {
            return (zza) queryLocalInterface;
        }
        return new zzb(iBinder);
    }
}
