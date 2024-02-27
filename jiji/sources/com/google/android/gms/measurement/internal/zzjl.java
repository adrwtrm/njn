package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public final class zzjl implements ServiceConnection, BaseGmsClient.BaseConnectionCallbacks, BaseGmsClient.BaseOnConnectionFailedListener {
    final /* synthetic */ zzjm zza;
    private volatile boolean zzb;
    private volatile zzed zzc;

    /* JADX INFO: Access modifiers changed from: protected */
    public zzjl(zzjm zzjmVar) {
        this.zza = zzjmVar;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnected(Bundle bundle) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                Preconditions.checkNotNull(this.zzc);
                this.zza.zzt.zzaz().zzp(new zzji(this, (zzdx) this.zzc.getService()));
            } catch (DeadObjectException | IllegalStateException unused) {
                this.zzc = null;
                this.zzb = false;
            }
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionFailed");
        zzeh zzl = this.zza.zzt.zzl();
        if (zzl != null) {
            zzl.zzk().zzb("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzb = false;
            this.zzc = null;
        }
        this.zza.zzt.zzaz().zzp(new zzjk(this));
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnectionSuspended(int i) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionSuspended");
        this.zza.zzt.zzay().zzc().zza("Service connection suspended");
        this.zza.zzt.zzaz().zzp(new zzjj(this));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzjl zzjlVar;
        zzdx zzdvVar;
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceConnected");
        synchronized (this) {
            if (iBinder != null) {
                zzdx zzdxVar = null;
                try {
                    String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                    if ("com.google.android.gms.measurement.internal.IMeasurementService".equals(interfaceDescriptor)) {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
                        if (queryLocalInterface instanceof zzdx) {
                            zzdvVar = (zzdx) queryLocalInterface;
                        } else {
                            zzdvVar = new zzdv(iBinder);
                        }
                        zzdxVar = zzdvVar;
                        this.zza.zzt.zzay().zzj().zza("Bound to IMeasurementService interface");
                    } else {
                        this.zza.zzt.zzay().zzd().zzb("Got binder with a wrong descriptor", interfaceDescriptor);
                    }
                } catch (RemoteException unused) {
                    this.zza.zzt.zzay().zzd().zza("Service connect failed to get IMeasurementService");
                }
                if (zzdxVar == null) {
                    this.zzb = false;
                    try {
                        ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
                        Context zzau = this.zza.zzt.zzau();
                        zzjlVar = this.zza.zza;
                        connectionTracker.unbindService(zzau, zzjlVar);
                    } catch (IllegalArgumentException unused2) {
                    }
                } else {
                    this.zza.zzt.zzaz().zzp(new zzjg(this, zzdxVar));
                }
                return;
            }
            this.zzb = false;
            this.zza.zzt.zzay().zzd().zza("Service connected with null binder");
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceDisconnected");
        this.zza.zzt.zzay().zzc().zza("Service disconnected");
        this.zza.zzt.zzaz().zzp(new zzjh(this, componentName));
    }

    public final void zzb(Intent intent) {
        zzjl zzjlVar;
        this.zza.zzg();
        Context zzau = this.zza.zzt.zzau();
        ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
        synchronized (this) {
            if (this.zzb) {
                this.zza.zzt.zzay().zzj().zza("Connection attempt already in progress");
                return;
            }
            this.zza.zzt.zzay().zzj().zza("Using local app measurement service");
            this.zzb = true;
            zzjlVar = this.zza.zza;
            connectionTracker.bindService(zzau, intent, zzjlVar, 129);
        }
    }

    public final void zzc() {
        this.zza.zzg();
        Context zzau = this.zza.zzt.zzau();
        synchronized (this) {
            if (this.zzb) {
                this.zza.zzt.zzay().zzj().zza("Connection attempt already in progress");
            } else if (this.zzc == null || (!this.zzc.isConnecting() && !this.zzc.isConnected())) {
                this.zzc = new zzed(zzau, Looper.getMainLooper(), this, this);
                this.zza.zzt.zzay().zzj().zza("Connecting to remote service");
                this.zzb = true;
                Preconditions.checkNotNull(this.zzc);
                this.zzc.checkAvailabilityAndConnect();
            } else {
                this.zza.zzt.zzay().zzj().zza("Already awaiting connection attempt");
            }
        }
    }

    public final void zzd() {
        if (this.zzc != null && (this.zzc.isConnected() || this.zzc.isConnecting())) {
            this.zzc.disconnect();
        }
        this.zzc = null;
    }
}
