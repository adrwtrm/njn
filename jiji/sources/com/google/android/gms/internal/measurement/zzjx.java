package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
public enum zzjx {
    DOUBLE(0, 1, zzkq.DOUBLE),
    FLOAT(1, 1, zzkq.FLOAT),
    INT64(2, 1, zzkq.LONG),
    UINT64(3, 1, zzkq.LONG),
    INT32(4, 1, zzkq.INT),
    FIXED64(5, 1, zzkq.LONG),
    FIXED32(6, 1, zzkq.INT),
    BOOL(7, 1, zzkq.BOOLEAN),
    STRING(8, 1, zzkq.STRING),
    MESSAGE(9, 1, zzkq.MESSAGE),
    BYTES(10, 1, zzkq.BYTE_STRING),
    UINT32(11, 1, zzkq.INT),
    ENUM(12, 1, zzkq.ENUM),
    SFIXED32(13, 1, zzkq.INT),
    SFIXED64(14, 1, zzkq.LONG),
    SINT32(15, 1, zzkq.INT),
    SINT64(16, 1, zzkq.LONG),
    GROUP(17, 1, zzkq.MESSAGE),
    DOUBLE_LIST(18, 2, zzkq.DOUBLE),
    FLOAT_LIST(19, 2, zzkq.FLOAT),
    INT64_LIST(20, 2, zzkq.LONG),
    UINT64_LIST(21, 2, zzkq.LONG),
    INT32_LIST(22, 2, zzkq.INT),
    FIXED64_LIST(23, 2, zzkq.LONG),
    FIXED32_LIST(24, 2, zzkq.INT),
    BOOL_LIST(25, 2, zzkq.BOOLEAN),
    STRING_LIST(26, 2, zzkq.STRING),
    MESSAGE_LIST(27, 2, zzkq.MESSAGE),
    BYTES_LIST(28, 2, zzkq.BYTE_STRING),
    UINT32_LIST(29, 2, zzkq.INT),
    ENUM_LIST(30, 2, zzkq.ENUM),
    SFIXED32_LIST(31, 2, zzkq.INT),
    SFIXED64_LIST(32, 2, zzkq.LONG),
    SINT32_LIST(33, 2, zzkq.INT),
    SINT64_LIST(34, 2, zzkq.LONG),
    DOUBLE_LIST_PACKED(35, 3, zzkq.DOUBLE),
    FLOAT_LIST_PACKED(36, 3, zzkq.FLOAT),
    INT64_LIST_PACKED(37, 3, zzkq.LONG),
    UINT64_LIST_PACKED(38, 3, zzkq.LONG),
    INT32_LIST_PACKED(39, 3, zzkq.INT),
    FIXED64_LIST_PACKED(40, 3, zzkq.LONG),
    FIXED32_LIST_PACKED(41, 3, zzkq.INT),
    BOOL_LIST_PACKED(42, 3, zzkq.BOOLEAN),
    UINT32_LIST_PACKED(43, 3, zzkq.INT),
    ENUM_LIST_PACKED(44, 3, zzkq.ENUM),
    SFIXED32_LIST_PACKED(45, 3, zzkq.INT),
    SFIXED64_LIST_PACKED(46, 3, zzkq.LONG),
    SINT32_LIST_PACKED(47, 3, zzkq.INT),
    SINT64_LIST_PACKED(48, 3, zzkq.LONG),
    GROUP_LIST(49, 2, zzkq.MESSAGE),
    MAP(50, 4, zzkq.VOID);
    
    private static final zzjx[] zzZ;
    private final zzkq zzab;
    private final int zzac;
    private final Class zzad;

    static {
        zzjx[] values = values();
        zzZ = new zzjx[values.length];
        for (zzjx zzjxVar : values) {
            zzZ[zzjxVar.zzac] = zzjxVar;
        }
    }

    zzjx(int i, int i2, zzkq zzkqVar) {
        this.zzac = i;
        this.zzab = zzkqVar;
        zzkq zzkqVar2 = zzkq.VOID;
        int i3 = i2 - 1;
        if (i3 == 1) {
            this.zzad = zzkqVar.zza();
        } else if (i3 != 3) {
            this.zzad = null;
        } else {
            this.zzad = zzkqVar.zza();
        }
        if (i2 == 1) {
            zzkqVar.ordinal();
        }
    }

    public final int zza() {
        return this.zzac;
    }
}
