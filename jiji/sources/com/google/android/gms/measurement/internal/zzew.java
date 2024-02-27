package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.internal.Preconditions;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public final class zzew extends zzgl {
    static final Pair zza = new Pair("", 0L);
    public zzeu zzb;
    public final zzes zzc;
    public final zzes zzd;
    public final zzev zze;
    public final zzes zzf;
    public final zzeq zzg;
    public final zzev zzh;
    public final zzeq zzi;
    public final zzes zzj;
    public final zzes zzk;
    public boolean zzl;
    public final zzeq zzm;
    public final zzeq zzn;
    public final zzes zzo;
    public final zzev zzp;
    public final zzev zzq;
    public final zzes zzr;
    public final zzer zzs;
    private SharedPreferences zzu;
    private String zzv;
    private boolean zzw;
    private long zzx;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzew(zzfr zzfrVar) {
        super(zzfrVar);
        this.zzf = new zzes(this, "session_timeout", 1800000L);
        this.zzg = new zzeq(this, "start_new_session", true);
        this.zzj = new zzes(this, "last_pause_time", 0L);
        this.zzk = new zzes(this, "session_id", 0L);
        this.zzh = new zzev(this, "non_personalized_ads", null);
        this.zzi = new zzeq(this, "allow_remote_dynamite", false);
        this.zzc = new zzes(this, "first_open_time", 0L);
        this.zzd = new zzes(this, "app_install_time", 0L);
        this.zze = new zzev(this, "app_instance_id", null);
        this.zzm = new zzeq(this, "app_backgrounded", false);
        this.zzn = new zzeq(this, "deep_link_retrieval_complete", false);
        this.zzo = new zzes(this, "deep_link_retrieval_attempts", 0L);
        this.zzp = new zzev(this, "firebase_feature_rollouts", null);
        this.zzq = new zzev(this, "deferred_attribution_cache", null);
        this.zzr = new zzes(this, "deferred_attribution_cache_timestamp", 0L);
        this.zzs = new zzer(this, "default_event_parameters", null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final SharedPreferences zza() {
        zzg();
        zzu();
        Preconditions.checkNotNull(this.zzu);
        return this.zzu;
    }

    @Override // com.google.android.gms.measurement.internal.zzgl
    @EnsuresNonNull.List({@EnsuresNonNull({"this.preferences"}), @EnsuresNonNull({"this.monitoringSample"})})
    protected final void zzaA() {
        SharedPreferences sharedPreferences = this.zzt.zzau().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzu = sharedPreferences;
        boolean z = sharedPreferences.getBoolean("has_been_opened", false);
        this.zzl = z;
        if (!z) {
            SharedPreferences.Editor edit = this.zzu.edit();
            edit.putBoolean("has_been_opened", true);
            edit.apply();
        }
        this.zzt.zzf();
        this.zzb = new zzeu(this, "health_monitor", Math.max(0L, ((Long) zzdu.zzb.zza(null)).longValue()), null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Pair zzb(String str) {
        zzg();
        long elapsedRealtime = this.zzt.zzav().elapsedRealtime();
        String str2 = this.zzv;
        if (str2 == null || elapsedRealtime >= this.zzx) {
            this.zzx = elapsedRealtime + this.zzt.zzf().zzi(str, zzdu.zza);
            AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
            try {
                AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.zzt.zzau());
                this.zzv = "";
                String id = advertisingIdInfo.getId();
                if (id != null) {
                    this.zzv = id;
                }
                this.zzw = advertisingIdInfo.isLimitAdTrackingEnabled();
            } catch (Exception e) {
                this.zzt.zzay().zzc().zzb("Unable to get advertising id", e);
                this.zzv = "";
            }
            AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
            return new Pair(this.zzv, Boolean.valueOf(this.zzw));
        }
        return new Pair(str2, Boolean.valueOf(this.zzw));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzai zzc() {
        zzg();
        return zzai.zzb(zza().getString("consent_settings", "G1"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Boolean zzd() {
        zzg();
        if (zza().contains("measurement_enabled")) {
            return Boolean.valueOf(zza().getBoolean("measurement_enabled", true));
        }
        return null;
    }

    @Override // com.google.android.gms.measurement.internal.zzgl
    protected final boolean zzf() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzh(Boolean bool) {
        zzg();
        SharedPreferences.Editor edit = zza().edit();
        if (bool != null) {
            edit.putBoolean("measurement_enabled", bool.booleanValue());
        } else {
            edit.remove("measurement_enabled");
        }
        edit.apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzi(boolean z) {
        zzg();
        this.zzt.zzay().zzj().zzb("App measurement setting deferred collection", Boolean.valueOf(z));
        SharedPreferences.Editor edit = zza().edit();
        edit.putBoolean("deferred_analytics_collection", z);
        edit.apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzj() {
        SharedPreferences sharedPreferences = this.zzu;
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.contains("deferred_analytics_collection");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzk(long j) {
        return j - this.zzf.zza() > this.zzj.zza();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzl(int i) {
        return zzai.zzj(i, zza().getInt("consent_source", 100));
    }
}
