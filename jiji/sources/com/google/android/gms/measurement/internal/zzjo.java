package com.google.android.gms.measurement.internal;

import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzjo extends zzkh {
    public final zzes zza;
    public final zzes zzb;
    public final zzes zzc;
    public final zzes zzd;
    public final zzes zze;
    private final Map zzg;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjo(zzkt zzktVar) {
        super(zzktVar);
        this.zzg = new HashMap();
        zzew zzm = this.zzt.zzm();
        zzm.getClass();
        this.zza = new zzes(zzm, "last_delete_stale", 0L);
        zzew zzm2 = this.zzt.zzm();
        zzm2.getClass();
        this.zzb = new zzes(zzm2, "backoff", 0L);
        zzew zzm3 = this.zzt.zzm();
        zzm3.getClass();
        this.zzc = new zzes(zzm3, "last_upload", 0L);
        zzew zzm4 = this.zzt.zzm();
        zzm4.getClass();
        this.zzd = new zzes(zzm4, "last_upload_attempt", 0L);
        zzew zzm5 = this.zzt.zzm();
        zzm5.getClass();
        this.zze = new zzes(zzm5, "midnight_offset", 0L);
    }

    @Deprecated
    final Pair zza(String str) {
        zzjn zzjnVar;
        AdvertisingIdClient.Info advertisingIdInfo;
        zzg();
        long elapsedRealtime = this.zzt.zzav().elapsedRealtime();
        zzjn zzjnVar2 = (zzjn) this.zzg.get(str);
        if (zzjnVar2 == null || elapsedRealtime >= zzjnVar2.zzc) {
            AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
            long zzi = elapsedRealtime + this.zzt.zzf().zzi(str, zzdu.zza);
            try {
                advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.zzt.zzau());
            } catch (Exception e) {
                this.zzt.zzay().zzc().zzb("Unable to get advertising id", e);
                zzjnVar = new zzjn("", false, zzi);
            }
            if (advertisingIdInfo == null) {
                return new Pair("", false);
            }
            String id = advertisingIdInfo.getId();
            if (id != null) {
                zzjnVar = new zzjn(id, advertisingIdInfo.isLimitAdTrackingEnabled(), zzi);
            } else {
                zzjnVar = new zzjn("", advertisingIdInfo.isLimitAdTrackingEnabled(), zzi);
            }
            this.zzg.put(str, zzjnVar);
            AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
            return new Pair(zzjnVar.zza, Boolean.valueOf(zzjnVar.zzb));
        }
        return new Pair(zzjnVar2.zza, Boolean.valueOf(zzjnVar2.zzb));
    }

    @Override // com.google.android.gms.measurement.internal.zzkh
    protected final boolean zzb() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Pair zzd(String str, zzai zzaiVar) {
        if (zzaiVar.zzi(zzah.AD_STORAGE)) {
            return zza(str);
        }
        return new Pair("", false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Deprecated
    public final String zzf(String str, boolean z) {
        zzg();
        String str2 = z ? (String) zza(str).first : "00000000-0000-0000-0000-000000000000";
        MessageDigest zzF = zzlb.zzF();
        if (zzF == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, zzF.digest(str2.getBytes())));
    }
}
