package com.google.android.gms.oss.licenses;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zzb extends com.google.android.gms.internal.oss_licenses.zza implements zza {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.oss.licenses.IOSSLicenseService");
    }

    @Override // com.google.android.gms.oss.licenses.zza
    public final String zza(String str) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        Parcel zza2 = zza(2, zza);
        String readString = zza2.readString();
        zza2.recycle();
        return readString;
    }

    @Override // com.google.android.gms.oss.licenses.zza
    public final String zzb(String str) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        Parcel zza2 = zza(3, zza);
        String readString = zza2.readString();
        zza2.recycle();
        return readString;
    }

    @Override // com.google.android.gms.oss.licenses.zza
    public final String zzc(String str) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        Parcel zza2 = zza(4, zza);
        String readString = zza2.readString();
        zza2.recycle();
        return readString;
    }

    @Override // com.google.android.gms.oss.licenses.zza
    public final List<com.google.android.gms.internal.oss_licenses.zzc> zza(List<com.google.android.gms.internal.oss_licenses.zzc> list) throws RemoteException {
        Parcel zza = zza();
        zza.writeList(list);
        Parcel zza2 = zza(5, zza);
        ArrayList zza3 = com.google.android.gms.internal.oss_licenses.zzb.zza(zza2);
        zza2.recycle();
        return zza3;
    }
}
