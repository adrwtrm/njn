package com.google.android.gms.oss.licenses;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/* loaded from: classes.dex */
public final class zzc {
    private static zzc zzd;
    private zzh zze;
    private final Context zzf;

    public static zzc zza(Context context) {
        if (zzd == null) {
            zzc zzcVar = new zzc(context);
            zzd = zzcVar;
            zzcVar.zze = new zzh(zzcVar.zzf);
        }
        return zzd;
    }

    private zzc(Context context) {
        this.zzf = context.getApplicationContext();
    }

    public static zze zza(Context context, String str) {
        try {
            return new zze(context.getPackageManager().getResourcesForApplication(str), str);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("OssLicenses", new StringBuilder(String.valueOf(str).length() + 52).append("Unable to get resources for ").append(str).append(", using local resources.").toString());
            return new zze(context.getResources(), context.getPackageName());
        }
    }

    public static int zza(zze zzeVar) {
        return zzeVar.zzg.getIdentifier("libraries_social_licenses_license", "layout", zzeVar.packageName);
    }

    public static int zzb(zze zzeVar) {
        return zzeVar.zzg.getIdentifier("license", "id", zzeVar.packageName);
    }

    public final zzh zzb() {
        return this.zze;
    }
}
