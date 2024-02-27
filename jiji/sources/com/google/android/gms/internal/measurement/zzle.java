package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
public final class zzle implements zzly {
    private static final zzlk zza = new zzlc();
    private final zzlk zzb;

    public zzle() {
        zzlk zzlkVar;
        zzlk[] zzlkVarArr = new zzlk[2];
        zzlkVarArr[0] = zzka.zza();
        try {
            zzlkVar = (zzlk) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            zzlkVar = zza;
        }
        zzlkVarArr[1] = zzlkVar;
        zzld zzldVar = new zzld(zzlkVarArr);
        zzkn.zzf(zzldVar, "messageInfoFactory");
        this.zzb = zzldVar;
    }

    private static boolean zzb(zzlj zzljVar) {
        return zzljVar.zzc() == 1;
    }

    @Override // com.google.android.gms.internal.measurement.zzly
    public final zzlx zza(Class cls) {
        zzlz.zzG(cls);
        zzlj zzb = this.zzb.zzb(cls);
        if (!zzb.zzb()) {
            if (zzkf.class.isAssignableFrom(cls)) {
                if (zzb(zzb)) {
                    return zzlp.zzl(cls, zzb, zzls.zzb(), zzla.zzd(), zzlz.zzB(), zzju.zzb(), zzli.zzb());
                }
                return zzlp.zzl(cls, zzb, zzls.zzb(), zzla.zzd(), zzlz.zzB(), null, zzli.zzb());
            } else if (zzb(zzb)) {
                return zzlp.zzl(cls, zzb, zzls.zza(), zzla.zzc(), zzlz.zzz(), zzju.zza(), zzli.zza());
            } else {
                return zzlp.zzl(cls, zzb, zzls.zza(), zzla.zzc(), zzlz.zzA(), null, zzli.zza());
            }
        } else if (zzkf.class.isAssignableFrom(cls)) {
            return zzlq.zzc(zzlz.zzB(), zzju.zzb(), zzb.zza());
        } else {
            return zzlq.zzc(zzlz.zzz(), zzju.zza(), zzb.zza());
        }
    }
}
