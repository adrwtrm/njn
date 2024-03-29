package com.google.android.gms.internal.measurement;

import com.serenegiant.app.PendingIntentCompat;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzgd extends zzkf implements zzln {
    public static final /* synthetic */ int zza = 0;
    private static final zzgd zzd;
    private long zzB;
    private int zzC;
    private boolean zzF;
    private int zzI;
    private int zzJ;
    private int zzK;
    private long zzM;
    private long zzN;
    private int zzQ;
    private zzgg zzS;
    private long zzU;
    private long zzV;
    private int zzY;
    private boolean zzZ;
    private boolean zzab;
    private zzfz zzac;
    private int zze;
    private int zzf;
    private int zzg;
    private long zzj;
    private long zzk;
    private long zzl;
    private long zzm;
    private long zzn;
    private int zzs;
    private long zzw;
    private long zzx;
    private boolean zzz;
    private zzkm zzh = zzbE();
    private zzkm zzi = zzbE();
    private String zzo = "";
    private String zzp = "";
    private String zzq = "";
    private String zzr = "";
    private String zzt = "";
    private String zzu = "";
    private String zzv = "";
    private String zzy = "";
    private String zzA = "";
    private String zzD = "";
    private String zzE = "";
    private zzkm zzG = zzbE();
    private String zzH = "";
    private String zzL = "";
    private String zzO = "";
    private String zzP = "";
    private String zzR = "";
    private zzkk zzT = zzbB();
    private String zzW = "";
    private String zzX = "";
    private String zzaa = "";
    private String zzad = "";
    private zzkm zzae = zzkf.zzbE();
    private String zzaf = "";

    static {
        zzgd zzgdVar = new zzgd();
        zzd = zzgdVar;
        zzkf.zzbL(zzgd.class, zzgdVar);
    }

    private zzgd() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzP(zzgd zzgdVar) {
        zzgdVar.zze &= Integer.MAX_VALUE;
        zzgdVar.zzO = zzd.zzO;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzQ(zzgd zzgdVar, int i) {
        zzgdVar.zzf |= 2;
        zzgdVar.zzQ = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzR(zzgd zzgdVar, int i, zzft zzftVar) {
        zzftVar.getClass();
        zzgdVar.zzbP();
        zzgdVar.zzh.set(i, zzftVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzS(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zzf |= 4;
        zzgdVar.zzR = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzT(zzgd zzgdVar, zzgg zzggVar) {
        zzggVar.getClass();
        zzgdVar.zzS = zzggVar;
        zzgdVar.zzf |= 8;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzU(zzgd zzgdVar, Iterable iterable) {
        zzkk zzkkVar = zzgdVar.zzT;
        if (!zzkkVar.zzc()) {
            int size = zzkkVar.size();
            zzgdVar.zzT = zzkkVar.zzg(size == 0 ? 10 : size + size);
        }
        zzio.zzbt(iterable, zzgdVar.zzT);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzV(zzgd zzgdVar, zzft zzftVar) {
        zzftVar.getClass();
        zzgdVar.zzbP();
        zzgdVar.zzh.add(zzftVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzW(zzgd zzgdVar, long j) {
        zzgdVar.zzf |= 16;
        zzgdVar.zzU = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzX(zzgd zzgdVar, long j) {
        zzgdVar.zzf |= 32;
        zzgdVar.zzV = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzY(zzgd zzgdVar, String str) {
        zzgdVar.zzf |= 128;
        zzgdVar.zzX = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzZ(zzgd zzgdVar, Iterable iterable) {
        zzgdVar.zzbP();
        zzio.zzbt(iterable, zzgdVar.zzh);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaA(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 8192;
        zzgdVar.zzv = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaB(zzgd zzgdVar, long j) {
        zzgdVar.zze |= 16384;
        zzgdVar.zzw = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaC(zzgd zzgdVar, long j) {
        zzgdVar.zze |= 32768;
        zzgdVar.zzx = 74029L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaD(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 65536;
        zzgdVar.zzy = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaE(zzgd zzgdVar) {
        zzgdVar.zze &= -65537;
        zzgdVar.zzy = zzd.zzy;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaF(zzgd zzgdVar, boolean z) {
        zzgdVar.zze |= 131072;
        zzgdVar.zzz = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaG(zzgd zzgdVar) {
        zzgdVar.zze &= -131073;
        zzgdVar.zzz = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaH(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 262144;
        zzgdVar.zzA = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaI(zzgd zzgdVar) {
        zzgdVar.zze &= -262145;
        zzgdVar.zzA = zzd.zzA;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaJ(zzgd zzgdVar, long j) {
        zzgdVar.zze |= 524288;
        zzgdVar.zzB = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaK(zzgd zzgdVar, int i) {
        zzgdVar.zze |= 1048576;
        zzgdVar.zzC = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaL(zzgd zzgdVar, String str) {
        zzgdVar.zze |= 2097152;
        zzgdVar.zzD = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaM(zzgd zzgdVar) {
        zzgdVar.zze &= -2097153;
        zzgdVar.zzD = zzd.zzD;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaN(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 4194304;
        zzgdVar.zzE = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaO(zzgd zzgdVar, boolean z) {
        zzgdVar.zze |= 8388608;
        zzgdVar.zzF = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaP(zzgd zzgdVar, Iterable iterable) {
        zzkm zzkmVar = zzgdVar.zzG;
        if (!zzkmVar.zzc()) {
            zzgdVar.zzG = zzkf.zzbF(zzkmVar);
        }
        zzio.zzbt(iterable, zzgdVar.zzG);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaR(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 16777216;
        zzgdVar.zzH = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaS(zzgd zzgdVar, int i) {
        zzgdVar.zze |= PendingIntentCompat.FLAG_MUTABLE;
        zzgdVar.zzI = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaT(zzgd zzgdVar, int i) {
        zzgdVar.zze |= 1;
        zzgdVar.zzg = 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaU(zzgd zzgdVar) {
        zzgdVar.zze &= -268435457;
        zzgdVar.zzL = zzd.zzL;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaV(zzgd zzgdVar, long j) {
        zzgdVar.zze |= 536870912;
        zzgdVar.zzM = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaa(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zzf |= 8192;
        zzgdVar.zzad = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzab(zzgd zzgdVar) {
        zzgdVar.zzf &= -8193;
        zzgdVar.zzad = zzd.zzad;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzac(zzgd zzgdVar, Iterable iterable) {
        zzkm zzkmVar = zzgdVar.zzae;
        if (!zzkmVar.zzc()) {
            zzgdVar.zzae = zzkf.zzbF(zzkmVar);
        }
        zzio.zzbt(iterable, zzgdVar.zzae);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzae(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zzf |= 16384;
        zzgdVar.zzaf = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaf(zzgd zzgdVar, int i) {
        zzgdVar.zzbP();
        zzgdVar.zzh.remove(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzag(zzgd zzgdVar, int i, zzgm zzgmVar) {
        zzgmVar.getClass();
        zzgdVar.zzbQ();
        zzgdVar.zzi.set(i, zzgmVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzah(zzgd zzgdVar, zzgm zzgmVar) {
        zzgmVar.getClass();
        zzgdVar.zzbQ();
        zzgdVar.zzi.add(zzgmVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzai(zzgd zzgdVar, Iterable iterable) {
        zzgdVar.zzbQ();
        zzio.zzbt(iterable, zzgdVar.zzi);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaj(zzgd zzgdVar, int i) {
        zzgdVar.zzbQ();
        zzgdVar.zzi.remove(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzak(zzgd zzgdVar, long j) {
        zzgdVar.zze |= 2;
        zzgdVar.zzj = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzal(zzgd zzgdVar, long j) {
        zzgdVar.zze |= 4;
        zzgdVar.zzk = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzam(zzgd zzgdVar, long j) {
        zzgdVar.zze |= 8;
        zzgdVar.zzl = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzan(zzgd zzgdVar, long j) {
        zzgdVar.zze |= 16;
        zzgdVar.zzm = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzao(zzgd zzgdVar) {
        zzgdVar.zze &= -17;
        zzgdVar.zzm = 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzap(zzgd zzgdVar, long j) {
        zzgdVar.zze |= 32;
        zzgdVar.zzn = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaq(zzgd zzgdVar) {
        zzgdVar.zze &= -33;
        zzgdVar.zzn = 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzar(zzgd zzgdVar, String str) {
        zzgdVar.zze |= 64;
        zzgdVar.zzo = "android";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzas(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 128;
        zzgdVar.zzp = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzat(zzgd zzgdVar) {
        zzgdVar.zze &= -129;
        zzgdVar.zzp = zzd.zzp;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzau(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 256;
        zzgdVar.zzq = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzav(zzgd zzgdVar) {
        zzgdVar.zze &= -257;
        zzgdVar.zzq = zzd.zzq;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaw(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 512;
        zzgdVar.zzr = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzax(zzgd zzgdVar, int i) {
        zzgdVar.zze |= 1024;
        zzgdVar.zzs = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzay(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 2048;
        zzgdVar.zzt = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void zzaz(zzgd zzgdVar, String str) {
        str.getClass();
        zzgdVar.zze |= 4096;
        zzgdVar.zzu = str;
    }

    private final void zzbP() {
        zzkm zzkmVar = this.zzh;
        if (zzkmVar.zzc()) {
            return;
        }
        this.zzh = zzkf.zzbF(zzkmVar);
    }

    private final void zzbQ() {
        zzkm zzkmVar = this.zzi;
        if (zzkmVar.zzc()) {
            return;
        }
        this.zzi = zzkf.zzbF(zzkmVar);
    }

    public static zzgc zzt() {
        return (zzgc) zzd.zzbx();
    }

    public final String zzA() {
        return this.zzv;
    }

    public final String zzB() {
        return this.zzX;
    }

    public final String zzC() {
        return this.zzq;
    }

    public final String zzD() {
        return this.zzO;
    }

    public final String zzE() {
        return this.zzH;
    }

    public final String zzF() {
        return this.zzE;
    }

    public final String zzG() {
        return this.zzD;
    }

    public final String zzH() {
        return this.zzp;
    }

    public final String zzI() {
        return this.zzo;
    }

    public final String zzJ() {
        return this.zzy;
    }

    public final String zzK() {
        return this.zzad;
    }

    public final String zzL() {
        return this.zzr;
    }

    public final List zzM() {
        return this.zzG;
    }

    public final List zzN() {
        return this.zzh;
    }

    public final List zzO() {
        return this.zzi;
    }

    public final int zza() {
        return this.zzI;
    }

    public final boolean zzaW() {
        return this.zzz;
    }

    public final boolean zzaX() {
        return this.zzF;
    }

    public final boolean zzaY() {
        return (this.zze & PendingIntentCompat.FLAG_MUTABLE) != 0;
    }

    public final boolean zzaZ() {
        return (this.zze & 1048576) != 0;
    }

    public final int zzb() {
        return this.zzC;
    }

    public final boolean zzba() {
        return (this.zze & 536870912) != 0;
    }

    public final boolean zzbb() {
        return (this.zzf & 128) != 0;
    }

    public final boolean zzbc() {
        return (this.zze & 524288) != 0;
    }

    public final boolean zzbd() {
        return (this.zzf & 16) != 0;
    }

    public final boolean zzbe() {
        return (this.zze & 8) != 0;
    }

    public final boolean zzbf() {
        return (this.zze & 16384) != 0;
    }

    public final boolean zzbg() {
        return (this.zze & 131072) != 0;
    }

    public final boolean zzbh() {
        return (this.zze & 32) != 0;
    }

    public final boolean zzbi() {
        return (this.zze & 16) != 0;
    }

    public final boolean zzbj() {
        return (this.zze & 1) != 0;
    }

    public final boolean zzbk() {
        return (this.zzf & 2) != 0;
    }

    public final boolean zzbl() {
        return (this.zze & 8388608) != 0;
    }

    public final boolean zzbm() {
        return (this.zzf & 8192) != 0;
    }

    public final boolean zzbn() {
        return (this.zze & 4) != 0;
    }

    public final boolean zzbo() {
        return (this.zze & 1024) != 0;
    }

    public final boolean zzbp() {
        return (this.zze & 2) != 0;
    }

    public final boolean zzbq() {
        return (this.zze & 32768) != 0;
    }

    public final int zzc() {
        return this.zzh.size();
    }

    public final int zzd() {
        return this.zzg;
    }

    public final int zze() {
        return this.zzQ;
    }

    public final int zzf() {
        return this.zzs;
    }

    public final int zzg() {
        return this.zzi.size();
    }

    public final long zzh() {
        return this.zzM;
    }

    public final long zzi() {
        return this.zzB;
    }

    public final long zzj() {
        return this.zzU;
    }

    public final long zzk() {
        return this.zzl;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzkf
    public final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 != 0) {
            if (i2 == 2) {
                return zzbI(zzd, "\u00014\u0000\u0002\u0001A4\u0000\u0005\u0000\u0001င\u0000\u0002\u001b\u0003\u001b\u0004ဂ\u0001\u0005ဂ\u0002\u0006ဂ\u0003\u0007ဂ\u0005\bဈ\u0006\tဈ\u0007\nဈ\b\u000bဈ\t\fင\n\rဈ\u000b\u000eဈ\f\u0010ဈ\r\u0011ဂ\u000e\u0012ဂ\u000f\u0013ဈ\u0010\u0014ဇ\u0011\u0015ဈ\u0012\u0016ဂ\u0013\u0017င\u0014\u0018ဈ\u0015\u0019ဈ\u0016\u001aဂ\u0004\u001cဇ\u0017\u001d\u001b\u001eဈ\u0018\u001fင\u0019 င\u001a!င\u001b\"ဈ\u001c#ဂ\u001d$ဂ\u001e%ဈ\u001f&ဈ 'င!)ဈ\",ဉ#-\u001d.ဂ$/ဂ%2ဈ&4ဈ'5ဌ(7ဇ)9ဈ*:ဇ+;ဉ,?ဈ-@\u001aAဈ.", new Object[]{"zze", "zzf", "zzg", "zzh", zzft.class, "zzi", zzgm.class, "zzj", "zzk", "zzl", "zzn", "zzo", "zzp", "zzq", "zzr", "zzs", "zzt", "zzu", "zzv", "zzw", "zzx", "zzy", "zzz", "zzA", "zzB", "zzC", "zzD", "zzE", "zzm", "zzF", "zzG", zzfp.class, "zzH", "zzI", "zzJ", "zzK", "zzL", "zzM", "zzN", "zzO", "zzP", "zzQ", "zzR", "zzS", "zzT", "zzU", "zzV", "zzW", "zzX", "zzY", zzfl.zza, "zzZ", "zzaa", "zzab", "zzac", "zzad", "zzae", "zzaf"});
            } else if (i2 != 3) {
                if (i2 != 4) {
                    if (i2 != 5) {
                        return null;
                    }
                    return zzd;
                }
                return new zzgc(null);
            } else {
                return new zzgd();
            }
        }
        return (byte) 1;
    }

    public final long zzm() {
        return this.zzw;
    }

    public final long zzn() {
        return this.zzn;
    }

    public final long zzo() {
        return this.zzm;
    }

    public final long zzp() {
        return this.zzk;
    }

    public final long zzq() {
        return this.zzj;
    }

    public final long zzr() {
        return this.zzx;
    }

    public final zzft zzs(int i) {
        return (zzft) this.zzh.get(i);
    }

    public final zzgm zzv(int i) {
        return (zzgm) this.zzi.get(i);
    }

    public final String zzw() {
        return this.zzR;
    }

    public final String zzx() {
        return this.zzu;
    }

    public final String zzy() {
        return this.zzA;
    }

    public final String zzz() {
        return this.zzt;
    }
}
