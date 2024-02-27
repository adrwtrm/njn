package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
public final class zzlu {
    private static final zzlu zza = new zzlu();
    private final ConcurrentMap zzc = new ConcurrentHashMap();
    private final zzly zzb = new zzle();

    private zzlu() {
    }

    public static zzlu zza() {
        return zza;
    }

    public final zzlx zzb(Class cls) {
        zzkn.zzf(cls, "messageType");
        zzlx zzlxVar = (zzlx) this.zzc.get(cls);
        if (zzlxVar == null) {
            zzlxVar = this.zzb.zza(cls);
            zzkn.zzf(cls, "messageType");
            zzkn.zzf(zzlxVar, "schema");
            zzlx zzlxVar2 = (zzlx) this.zzc.putIfAbsent(cls, zzlxVar);
            if (zzlxVar2 != null) {
                return zzlxVar2;
            }
        }
        return zzlxVar;
    }
}
