package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
public enum zzne {
    DOUBLE(zznf.DOUBLE, 1),
    FLOAT(zznf.FLOAT, 5),
    INT64(zznf.LONG, 0),
    UINT64(zznf.LONG, 0),
    INT32(zznf.INT, 0),
    FIXED64(zznf.LONG, 1),
    FIXED32(zznf.INT, 5),
    BOOL(zznf.BOOLEAN, 0),
    STRING(zznf.STRING, 2),
    GROUP(zznf.MESSAGE, 3),
    MESSAGE(zznf.MESSAGE, 2),
    BYTES(zznf.BYTE_STRING, 2),
    UINT32(zznf.INT, 0),
    ENUM(zznf.ENUM, 0),
    SFIXED32(zznf.INT, 5),
    SFIXED64(zznf.LONG, 1),
    SINT32(zznf.INT, 0),
    SINT64(zznf.LONG, 0);
    
    private final zznf zzt;

    zzne(zznf zznfVar, int i) {
        this.zzt = zznfVar;
    }

    public final zznf zza() {
        return this.zzt;
    }
}
