package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzaa extends zzkh {
    private String zza;
    private Set zzb;
    private Map zzc;
    private Long zzd;
    private Long zze;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzaa(zzkt zzktVar) {
        super(zzktVar);
    }

    private final zzu zzd(Integer num) {
        if (this.zzc.containsKey(num)) {
            return (zzu) this.zzc.get(num);
        }
        zzu zzuVar = new zzu(this, this.zza, null);
        this.zzc.put(num, zzuVar);
        return zzuVar;
    }

    private final boolean zzf(int i, int i2) {
        zzu zzuVar = (zzu) this.zzc.get(Integer.valueOf(i));
        if (zzuVar == null) {
            return false;
        }
        return zzu.zzb(zzuVar).get(i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't wrap try/catch for region: R(11:1|(2:2|(2:4|(2:6|7))(2:550|551))|8|(3:10|11|12)|16|(15:(6:19|20|21|22|23|(20:(7:25|26|27|28|(1:30)(3:526|(1:528)(1:530)|529)|31|(1:34)(1:33))|(1:36)|37|38|39|40|41|42|(3:44|(1:46)|47)(4:490|(6:491|492|493|494|495|(1:498)(1:497))|(1:500)|501)|48|(1:50)(6:299|(6:301|302|303|304|305|(2:(3:307|(1:309)|310)|312)(1:475))(1:489)|319|(10:322|(3:326|(4:329|(5:331|332|(1:334)(1:338)|335|336)(1:339)|337|327)|340)|341|(3:345|(4:348|(3:353|354|355)|356|346)|359)|360|(3:362|(6:365|(2:367|(3:369|370|371))(1:374)|372|373|371|363)|375)|376|(3:385|(8:388|(1:390)|391|(1:393)|394|(3:396|397|398)(1:400)|399|386)|401)|402|320)|408|409)|51|(3:184|(4:187|(10:189|190|(1:192)(1:296)|193|(13:195|196|197|198|199|200|201|202|203|204|205|(4:207|(11:208|209|210|211|212|213|214|(3:216|217|218)(1:268)|219|220|(1:223)(1:222))|(1:225)|226)(3:274|275|(1:277))|227)(1:295)|228|(4:231|(3:249|250|251)(4:233|234|(2:235|(2:237|(1:239)(2:240|241))(1:248))|(3:243|244|245)(1:247))|246|229)|252|253|254)(1:297)|255|185)|298)|53|54|(3:56|(6:59|(12:61|62|63|64|65|66|67|68|69|70|(4:(9:72|73|74|75|76|77|(1:79)|80|81)|83|(1:85)|86)(2:138|139)|87)(1:159)|88|(2:89|(2:91|(3:125|126|127)(8:93|(2:94|(4:96|(3:98|(1:100)(1:102)|101)|103|(1:1)(2:107|(1:109)(2:110|111)))(1:124))|118|(1:120)(1:122)|121|113|114|115))(0))|128|57)|160)|161|(9:164|165|166|167|168|169|(2:171|172)(1:174)|173|162)|182|183)(1:534))|41|42|(0)(0)|48|(0)(0)|51|(0)|53|54|(0)|161|(1:162)|182|183)|549|38|39|40|(4:(0)|(0)|(0)|(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(15:(6:19|20|21|22|23|(20:(7:25|26|27|28|(1:30)(3:526|(1:528)(1:530)|529)|31|(1:34)(1:33))|(1:36)|37|38|39|40|41|42|(3:44|(1:46)|47)(4:490|(6:491|492|493|494|495|(1:498)(1:497))|(1:500)|501)|48|(1:50)(6:299|(6:301|302|303|304|305|(2:(3:307|(1:309)|310)|312)(1:475))(1:489)|319|(10:322|(3:326|(4:329|(5:331|332|(1:334)(1:338)|335|336)(1:339)|337|327)|340)|341|(3:345|(4:348|(3:353|354|355)|356|346)|359)|360|(3:362|(6:365|(2:367|(3:369|370|371))(1:374)|372|373|371|363)|375)|376|(3:385|(8:388|(1:390)|391|(1:393)|394|(3:396|397|398)(1:400)|399|386)|401)|402|320)|408|409)|51|(3:184|(4:187|(10:189|190|(1:192)(1:296)|193|(13:195|196|197|198|199|200|201|202|203|204|205|(4:207|(11:208|209|210|211|212|213|214|(3:216|217|218)(1:268)|219|220|(1:223)(1:222))|(1:225)|226)(3:274|275|(1:277))|227)(1:295)|228|(4:231|(3:249|250|251)(4:233|234|(2:235|(2:237|(1:239)(2:240|241))(1:248))|(3:243|244|245)(1:247))|246|229)|252|253|254)(1:297)|255|185)|298)|53|54|(3:56|(6:59|(12:61|62|63|64|65|66|67|68|69|70|(4:(9:72|73|74|75|76|77|(1:79)|80|81)|83|(1:85)|86)(2:138|139)|87)(1:159)|88|(2:89|(2:91|(3:125|126|127)(8:93|(2:94|(4:96|(3:98|(1:100)(1:102)|101)|103|(1:1)(2:107|(1:109)(2:110|111)))(1:124))|118|(1:120)(1:122)|121|113|114|115))(0))|128|57)|160)|161|(9:164|165|166|167|168|169|(2:171|172)(1:174)|173|162)|182|183)(1:534))|41|42|(0)(0)|48|(0)(0)|51|(0)|53|54|(0)|161|(1:162)|182|183) */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x02bb, code lost:
        if (r5 != null) goto L314;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x02bd, code lost:
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x02c5, code lost:
        if (r5 != null) goto L314;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x02ea, code lost:
        if (r5 == null) goto L315;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x02ed, code lost:
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r1);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r13);
        r1 = new androidx.collection.ArrayMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x02fc, code lost:
        if (r13.isEmpty() == false) goto L410;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x02fe, code lost:
        r21 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0302, code lost:
        r3 = r13.keySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x030e, code lost:
        if (r3.hasNext() == false) goto L474;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0310, code lost:
        r4 = ((java.lang.Integer) r3.next()).intValue();
        r5 = java.lang.Integer.valueOf(r4);
        r6 = (com.google.android.gms.internal.measurement.zzgi) r13.get(r5);
        r7 = (java.util.List) r0.get(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x032a, code lost:
        if (r7 == null) goto L473;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0330, code lost:
        if (r7.isEmpty() == false) goto L417;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0334, code lost:
        r17 = r0;
        r0 = r63.zzf.zzu().zzq(r6.zzk(), r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0348, code lost:
        if (r0.isEmpty() != false) goto L419;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x034a, code lost:
        r5 = (com.google.android.gms.internal.measurement.zzgh) r6.zzby();
        r5.zzf();
        r5.zzb(r0);
        r19 = r3;
        r0 = r63.zzf.zzu().zzq(r6.zzn(), r7);
        r5.zzh();
        r5.zzd(r0);
        com.google.android.gms.internal.measurement.zzoc.zzc();
        r21 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x037e, code lost:
        if (r63.zzt.zzf().zzs(null, com.google.android.gms.measurement.internal.zzdu.zzas) == false) goto L451;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0380, code lost:
        r0 = new java.util.ArrayList();
        r3 = r6.zzj().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0391, code lost:
        if (r3.hasNext() == false) goto L435;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0393, code lost:
        r8 = (com.google.android.gms.internal.measurement.zzfr) r3.next();
        r23 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x03a9, code lost:
        if (r7.contains(java.lang.Integer.valueOf(r8.zza())) != false) goto L434;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x03ab, code lost:
        r0.add(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x03ae, code lost:
        r3 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x03b2, code lost:
        r5.zze();
        r5.zza(r0);
        r0 = new java.util.ArrayList();
        r3 = r6.zzm().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x03c9, code lost:
        if (r3.hasNext() == false) goto L446;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x03cb, code lost:
        r6 = (com.google.android.gms.internal.measurement.zzgk) r3.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x03dd, code lost:
        if (r7.contains(java.lang.Integer.valueOf(r6.zzb())) != false) goto L445;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x03df, code lost:
        r0.add(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x03e3, code lost:
        r5.zzg();
        r5.zzc(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x03ea, code lost:
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x03ef, code lost:
        if (r0 >= r6.zza()) goto L460;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0401, code lost:
        if (r7.contains(java.lang.Integer.valueOf(r6.zze(r0).zza())) == false) goto L459;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0403, code lost:
        r5.zzi(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0406, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0409, code lost:
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x040e, code lost:
        if (r0 >= r6.zzc()) goto L470;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0420, code lost:
        if (r7.contains(java.lang.Integer.valueOf(r6.zzi(r0).zzb())) == false) goto L469;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0422, code lost:
        r5.zzj(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0425, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0428, code lost:
        r1.put(java.lang.Integer.valueOf(r4), (com.google.android.gms.internal.measurement.zzgi) r5.zzaC());
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0436, code lost:
        r0 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x043a, code lost:
        r17 = r0;
        r19 = r3;
        r21 = r8;
        r1.put(r5, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0443, code lost:
        r0 = r17;
        r3 = r19;
        r8 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x044b, code lost:
        r0 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:364:0x0927, code lost:
        if (r9 != null) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:365:0x0929, code lost:
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:378:0x0950, code lost:
        if (r9 != null) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:417:0x0a6f, code lost:
        if (r8 != false) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0154, code lost:
        if (r5 != null) goto L536;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0156, code lost:
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0178, code lost:
        if (r5 == null) goto L549;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0224, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0225, code lost:
        r20 = "audience_id";
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0228, code lost:
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0229, code lost:
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x022c, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x022d, code lost:
        r20 = "audience_id";
        r4 = null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:172:0x044f  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0602  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x07b5  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x07bf  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x07d9  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x086c  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0a97  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01b9 A[Catch: SQLiteException -> 0x0224, all -> 0x0b2b, TRY_LEAVE, TryCatch #4 {all -> 0x0b2b, blocks: (B:61:0x01b3, B:63:0x01b9, B:67:0x01c7, B:68:0x01cc, B:69:0x01d6, B:70:0x01e6, B:75:0x020e, B:72:0x01f3, B:74:0x0207, B:89:0x0230), top: B:448:0x01b3 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01c7 A[Catch: SQLiteException -> 0x0224, all -> 0x0b2b, TRY_ENTER, TryCatch #4 {all -> 0x0b2b, blocks: (B:61:0x01b3, B:63:0x01b9, B:67:0x01c7, B:68:0x01cc, B:69:0x01d6, B:70:0x01e6, B:75:0x020e, B:72:0x01f3, B:74:0x0207, B:89:0x0230), top: B:448:0x01b3 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0259  */
    /* JADX WARN: Type inference failed for: r5v5, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v63, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r5v64 */
    /* JADX WARN: Type inference failed for: r5v65, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List zza(java.lang.String r64, java.util.List r65, java.util.List r66, java.lang.Long r67, java.lang.Long r68) {
        /*
            Method dump skipped, instructions count: 2867
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzaa.zza(java.lang.String, java.util.List, java.util.List, java.lang.Long, java.lang.Long):java.util.List");
    }

    @Override // com.google.android.gms.measurement.internal.zzkh
    protected final boolean zzb() {
        return false;
    }
}
