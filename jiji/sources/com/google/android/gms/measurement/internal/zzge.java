package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzpd;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import kotlinx.coroutines.DebugKt;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
final class zzge implements Callable {
    final /* synthetic */ zzaw zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzgj zzc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzge(zzgj zzgjVar, zzaw zzawVar, String str) {
        this.zzc = zzgjVar;
        this.zza = zzawVar;
        this.zzb = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzkt zzktVar;
        zzkt zzktVar2;
        zzky zzkyVar;
        zzh zzhVar;
        com.google.android.gms.internal.measurement.zzga zzgaVar;
        String str;
        Bundle bundle;
        com.google.android.gms.internal.measurement.zzgc zzgcVar;
        String str2;
        zzas zzc;
        long j;
        String str3;
        byte[] bArr;
        zzkt zzktVar3;
        zzktVar = this.zzc.zza;
        zzktVar.zzA();
        zzktVar2 = this.zzc.zza;
        zzic zzr = zzktVar2.zzr();
        zzaw zzawVar = this.zza;
        String str4 = this.zzb;
        zzr.zzg();
        zzfr.zzO();
        Preconditions.checkNotNull(zzawVar);
        Preconditions.checkNotEmpty(str4);
        if (!zzr.zzt.zzf().zzs(str4, zzdu.zzS)) {
            zzr.zzt.zzay().zzc().zzb("Generating ScionPayload disabled. packageName", str4);
            return new byte[0];
        } else if (!"_iap".equals(zzawVar.zza) && !"_iapx".equals(zzawVar.zza)) {
            zzr.zzt.zzay().zzc().zzc("Generating a payload for this event is not available. package_name, event_name", str4, zzawVar.zza);
            return null;
        } else {
            com.google.android.gms.internal.measurement.zzga zza = com.google.android.gms.internal.measurement.zzgb.zza();
            zzr.zzf.zzi().zzw();
            try {
                zzh zzj = zzr.zzf.zzi().zzj(str4);
                if (zzj == null) {
                    zzr.zzt.zzay().zzc().zzb("Log and bundle not available. package_name", str4);
                    bArr = new byte[0];
                    zzktVar3 = zzr.zzf;
                } else if (zzj.zzai()) {
                    com.google.android.gms.internal.measurement.zzgc zzt = com.google.android.gms.internal.measurement.zzgd.zzt();
                    zzt.zzad(1);
                    zzt.zzZ("android");
                    if (!TextUtils.isEmpty(zzj.zzt())) {
                        zzt.zzD(zzj.zzt());
                    }
                    if (!TextUtils.isEmpty(zzj.zzv())) {
                        zzt.zzF((String) Preconditions.checkNotNull(zzj.zzv()));
                    }
                    if (!TextUtils.isEmpty(zzj.zzw())) {
                        zzt.zzG((String) Preconditions.checkNotNull(zzj.zzw()));
                    }
                    if (zzj.zzb() != -2147483648L) {
                        zzt.zzH((int) zzj.zzb());
                    }
                    zzt.zzV(zzj.zzm());
                    zzt.zzP(zzj.zzk());
                    String zzy = zzj.zzy();
                    String zzr2 = zzj.zzr();
                    if (!TextUtils.isEmpty(zzy)) {
                        zzt.zzU(zzy);
                    } else if (!TextUtils.isEmpty(zzr2)) {
                        zzt.zzC(zzr2);
                    }
                    zzai zzh = zzr.zzf.zzh(str4);
                    zzt.zzM(zzj.zzj());
                    if (zzr.zzt.zzJ() && zzr.zzt.zzf().zzt(zzt.zzap()) && zzh.zzi(zzah.AD_STORAGE) && !TextUtils.isEmpty(null)) {
                        zzt.zzO(null);
                    }
                    zzt.zzL(zzh.zzh());
                    if (zzh.zzi(zzah.AD_STORAGE) && zzj.zzah()) {
                        Pair zzd = zzr.zzf.zzs().zzd(zzj.zzt(), zzh);
                        if (zzj.zzah() && !TextUtils.isEmpty((CharSequence) zzd.first)) {
                            try {
                                zzt.zzae(zzic.zza((String) zzd.first, Long.toString(zzawVar.zzd)));
                                if (zzd.second != null) {
                                    zzt.zzX(((Boolean) zzd.second).booleanValue());
                                }
                            } catch (SecurityException e) {
                                zzr.zzt.zzay().zzc().zzb("Resettable device id encryption failed", e.getMessage());
                                bArr = new byte[0];
                                zzktVar3 = zzr.zzf;
                            }
                        }
                    }
                    zzr.zzt.zzg().zzu();
                    zzt.zzN(Build.MODEL);
                    zzr.zzt.zzg().zzu();
                    zzt.zzY(Build.VERSION.RELEASE);
                    zzt.zzaj((int) zzr.zzt.zzg().zzb());
                    zzt.zzan(zzr.zzt.zzg().zzc());
                    try {
                        if (zzh.zzi(zzah.ANALYTICS_STORAGE) && zzj.zzu() != null) {
                            zzt.zzE(zzic.zza((String) Preconditions.checkNotNull(zzj.zzu()), Long.toString(zzawVar.zzd)));
                        }
                        if (!TextUtils.isEmpty(zzj.zzx())) {
                            zzt.zzT((String) Preconditions.checkNotNull(zzj.zzx()));
                        }
                        String zzt2 = zzj.zzt();
                        List zzu = zzr.zzf.zzi().zzu(zzt2);
                        Iterator it = zzu.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                zzkyVar = null;
                                break;
                            }
                            zzkyVar = (zzky) it.next();
                            if ("_lte".equals(zzkyVar.zzc)) {
                                break;
                            }
                        }
                        if (zzkyVar == null || zzkyVar.zze == null) {
                            zzky zzkyVar2 = new zzky(zzt2, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lte", zzr.zzt.zzav().currentTimeMillis(), 0L);
                            zzu.add(zzkyVar2);
                            zzr.zzf.zzi().zzL(zzkyVar2);
                        }
                        zzkv zzu2 = zzr.zzf.zzu();
                        zzu2.zzt.zzay().zzj().zza("Checking account type status for ad personalization signals");
                        if (zzu2.zzt.zzg().zze()) {
                            String zzt3 = zzj.zzt();
                            Preconditions.checkNotNull(zzt3);
                            if (zzj.zzah() && zzu2.zzf.zzo().zzn(zzt3)) {
                                zzu2.zzt.zzay().zzc().zza("Turning off ad personalization due to account type");
                                Iterator it2 = zzu.iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        break;
                                    } else if ("_npa".equals(((zzky) it2.next()).zzc)) {
                                        it2.remove();
                                        break;
                                    }
                                }
                                zzu.add(new zzky(zzt3, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_npa", zzu2.zzt.zzav().currentTimeMillis(), 1L));
                            }
                        }
                        com.google.android.gms.internal.measurement.zzgm[] zzgmVarArr = new com.google.android.gms.internal.measurement.zzgm[zzu.size()];
                        for (int i = 0; i < zzu.size(); i++) {
                            com.google.android.gms.internal.measurement.zzgl zzd2 = com.google.android.gms.internal.measurement.zzgm.zzd();
                            zzd2.zzf(((zzky) zzu.get(i)).zzc);
                            zzd2.zzg(((zzky) zzu.get(i)).zzd);
                            zzr.zzf.zzu().zzu(zzd2, ((zzky) zzu.get(i)).zze);
                            zzgmVarArr[i] = (com.google.android.gms.internal.measurement.zzgm) zzd2.zzaC();
                        }
                        zzt.zzj(Arrays.asList(zzgmVarArr));
                        zzei zzb = zzei.zzb(zzawVar);
                        zzr.zzt.zzv().zzL(zzb.zzd, zzr.zzf.zzi().zzi(str4));
                        zzr.zzt.zzv().zzM(zzb, zzr.zzt.zzf().zzd(str4));
                        Bundle bundle2 = zzb.zzd;
                        bundle2.putLong("_c", 1L);
                        zzr.zzt.zzay().zzc().zza("Marking in-app purchase as real-time");
                        bundle2.putLong("_r", 1L);
                        bundle2.putString("_o", zzawVar.zzc);
                        if (zzr.zzt.zzv().zzae(zzt.zzap())) {
                            zzr.zzt.zzv().zzO(bundle2, "_dbg", 1L);
                            zzr.zzt.zzv().zzO(bundle2, "_r", 1L);
                        }
                        zzas zzn = zzr.zzf.zzi().zzn(str4, zzawVar.zza);
                        if (zzn == null) {
                            zzgcVar = zzt;
                            zzhVar = zzj;
                            zzgaVar = zza;
                            str = str4;
                            bundle = bundle2;
                            str2 = null;
                            zzc = new zzas(str4, zzawVar.zza, 0L, 0L, 0L, zzawVar.zzd, 0L, null, null, null, null);
                            j = 0;
                        } else {
                            zzhVar = zzj;
                            zzgaVar = zza;
                            str = str4;
                            bundle = bundle2;
                            zzgcVar = zzt;
                            str2 = null;
                            long j2 = zzn.zzf;
                            zzc = zzn.zzc(zzawVar.zzd);
                            j = j2;
                        }
                        zzr.zzf.zzi().zzE(zzc);
                        zzar zzarVar = new zzar(zzr.zzt, zzawVar.zzc, str, zzawVar.zza, zzawVar.zzd, j, bundle);
                        com.google.android.gms.internal.measurement.zzfs zze = com.google.android.gms.internal.measurement.zzft.zze();
                        zze.zzm(zzarVar.zzd);
                        zze.zzi(zzarVar.zzb);
                        zze.zzl(zzarVar.zze);
                        zzat zzatVar = new zzat(zzarVar.zzf);
                        while (zzatVar.hasNext()) {
                            String next = zzatVar.next();
                            com.google.android.gms.internal.measurement.zzfw zze2 = com.google.android.gms.internal.measurement.zzfx.zze();
                            zze2.zzj(next);
                            Object zzf = zzarVar.zzf.zzf(next);
                            if (zzf != null) {
                                zzr.zzf.zzu().zzt(zze2, zzf);
                                zze.zze(zze2);
                            }
                        }
                        com.google.android.gms.internal.measurement.zzgc zzgcVar2 = zzgcVar;
                        zzgcVar2.zzk(zze);
                        com.google.android.gms.internal.measurement.zzge zza2 = com.google.android.gms.internal.measurement.zzgg.zza();
                        com.google.android.gms.internal.measurement.zzfu zza3 = com.google.android.gms.internal.measurement.zzfv.zza();
                        zza3.zza(zzc.zzc);
                        zza3.zzb(zzawVar.zza);
                        zza2.zza(zza3);
                        zzgcVar2.zzaa(zza2);
                        zzgcVar2.zzf(zzr.zzf.zzf().zza(zzhVar.zzt(), Collections.emptyList(), zzgcVar2.zzat(), Long.valueOf(zze.zzc()), Long.valueOf(zze.zzc())));
                        if (zze.zzq()) {
                            zzgcVar2.zzai(zze.zzc());
                            zzgcVar2.zzQ(zze.zzc());
                        }
                        long zzn2 = zzhVar.zzn();
                        int i2 = (zzn2 > 0L ? 1 : (zzn2 == 0L ? 0 : -1));
                        if (i2 != 0) {
                            zzgcVar2.zzab(zzn2);
                        }
                        long zzp = zzhVar.zzp();
                        if (zzp != 0) {
                            zzgcVar2.zzac(zzp);
                        } else if (i2 != 0) {
                            zzgcVar2.zzac(zzn2);
                        }
                        String zzB = zzhVar.zzB();
                        zzpd.zzc();
                        if (zzr.zzt.zzf().zzs(str2, zzdu.zzal)) {
                            str3 = str;
                            if (zzr.zzt.zzf().zzs(str3, zzdu.zzan) && zzB != null) {
                                zzgcVar2.zzah(zzB);
                            }
                        } else {
                            str3 = str;
                        }
                        zzhVar.zzE();
                        zzgcVar2.zzI((int) zzhVar.zzo());
                        zzr.zzt.zzf().zzh();
                        zzgcVar2.zzal(74029L);
                        zzgcVar2.zzak(zzr.zzt.zzav().currentTimeMillis());
                        zzgcVar2.zzag(Boolean.TRUE.booleanValue());
                        if (zzr.zzt.zzf().zzs(str2, zzdu.zzar)) {
                            zzr.zzf.zzC(zzgcVar2.zzap(), zzgcVar2);
                        }
                        com.google.android.gms.internal.measurement.zzga zzgaVar2 = zzgaVar;
                        zzgaVar2.zza(zzgcVar2);
                        zzh zzhVar2 = zzhVar;
                        zzhVar2.zzab(zzgcVar2.zzd());
                        zzhVar2.zzZ(zzgcVar2.zzc());
                        zzr.zzf.zzi().zzD(zzhVar2);
                        zzr.zzf.zzi().zzC();
                        zzr.zzf.zzi().zzx();
                        try {
                            return zzr.zzf.zzu().zzy(((com.google.android.gms.internal.measurement.zzgb) zzgaVar2.zzaC()).zzbu());
                        } catch (IOException e2) {
                            zzr.zzt.zzay().zzd().zzc("Data loss. Failed to bundle and serialize. appId", zzeh.zzn(str3), e2);
                            return str2;
                        }
                    } catch (SecurityException e3) {
                        zzr.zzt.zzay().zzc().zzb("app instance id encryption failed", e3.getMessage());
                        byte[] bArr2 = new byte[0];
                        zzr.zzf.zzi().zzx();
                        return bArr2;
                    }
                } else {
                    zzr.zzt.zzay().zzc().zzb("Log and bundle disabled. package_name", str4);
                    bArr = new byte[0];
                    zzktVar3 = zzr.zzf;
                }
                zzktVar3.zzi().zzx();
                return bArr;
            } catch (Throwable th) {
                zzr.zzf.zzi().zzx();
                throw th;
            }
        }
    }
}
