package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public final class zzfo extends zzgl {
    private static final AtomicLong zza = new AtomicLong(Long.MIN_VALUE);
    private zzfn zzb;
    private zzfn zzc;
    private final PriorityBlockingQueue zzd;
    private final BlockingQueue zze;
    private final Thread.UncaughtExceptionHandler zzf;
    private final Thread.UncaughtExceptionHandler zzg;
    private final Object zzh;
    private final Semaphore zzi;
    private volatile boolean zzj;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfo(zzfr zzfrVar) {
        super(zzfrVar);
        this.zzh = new Object();
        this.zzi = new Semaphore(2);
        this.zzd = new PriorityBlockingQueue();
        this.zze = new LinkedBlockingQueue();
        this.zzf = new zzfl(this, "Thread death: Uncaught exception on worker thread");
        this.zzg = new zzfl(this, "Thread death: Uncaught exception on network thread");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ boolean zzr(zzfo zzfoVar) {
        boolean z = zzfoVar.zzj;
        return false;
    }

    private final void zzt(zzfm zzfmVar) {
        synchronized (this.zzh) {
            this.zzd.add(zzfmVar);
            zzfn zzfnVar = this.zzb;
            if (zzfnVar != null) {
                zzfnVar.zza();
            } else {
                zzfn zzfnVar2 = new zzfn(this, "Measurement Worker", this.zzd);
                this.zzb = zzfnVar2;
                zzfnVar2.setUncaughtExceptionHandler(this.zzf);
                this.zzb.start();
            }
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzgk
    public final void zzax() {
        if (Thread.currentThread() != this.zzc) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Object zzd(AtomicReference atomicReference, long j, String str, Runnable runnable) {
        synchronized (atomicReference) {
            this.zzt.zzaz().zzp(runnable);
            try {
                atomicReference.wait(j);
            } catch (InterruptedException unused) {
                zzef zzk = this.zzt.zzay().zzk();
                zzk.zza("Interrupted waiting for " + str);
                return null;
            }
        }
        Object obj = atomicReference.get();
        if (obj == null) {
            this.zzt.zzay().zzk().zza("Timed out waiting for ".concat(str));
        }
        return obj;
    }

    @Override // com.google.android.gms.measurement.internal.zzgl
    protected final boolean zzf() {
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzgk
    public final void zzg() {
        if (Thread.currentThread() != this.zzb) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final Future zzh(Callable callable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(callable);
        zzfm zzfmVar = new zzfm(this, callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            if (!this.zzd.isEmpty()) {
                this.zzt.zzay().zzk().zza("Callable skipped the worker queue.");
            }
            zzfmVar.run();
        } else {
            zzt(zzfmVar);
        }
        return zzfmVar;
    }

    public final Future zzi(Callable callable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(callable);
        zzfm zzfmVar = new zzfm(this, callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            zzfmVar.run();
        } else {
            zzt(zzfmVar);
        }
        return zzfmVar;
    }

    public final void zzo(Runnable runnable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(runnable);
        zzfm zzfmVar = new zzfm(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzh) {
            this.zze.add(zzfmVar);
            zzfn zzfnVar = this.zzc;
            if (zzfnVar != null) {
                zzfnVar.zza();
            } else {
                zzfn zzfnVar2 = new zzfn(this, "Measurement Network", this.zze);
                this.zzc = zzfnVar2;
                zzfnVar2.setUncaughtExceptionHandler(this.zzg);
                this.zzc.start();
            }
        }
    }

    public final void zzp(Runnable runnable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(runnable);
        zzt(new zzfm(this, runnable, false, "Task exception on worker thread"));
    }

    public final void zzq(Runnable runnable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(runnable);
        zzt(new zzfm(this, runnable, true, "Task exception on worker thread"));
    }

    public final boolean zzs() {
        return Thread.currentThread() == this.zzb;
    }
}
