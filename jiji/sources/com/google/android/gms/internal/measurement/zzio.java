package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzin;
import com.google.android.gms.internal.measurement.zzio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
public abstract class zzio<MessageType extends zzio<MessageType, BuilderType>, BuilderType extends zzin<MessageType, BuilderType>> implements zzlm {
    protected int zzb = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    public static void zzbt(Iterable iterable, List list) {
        zzkn.zze(iterable);
        if (iterable instanceof zzku) {
            List zzh = ((zzku) iterable).zzh();
            zzku zzkuVar = (zzku) list;
            int size = list.size();
            for (Object obj : zzh) {
                if (obj != null) {
                    if (obj instanceof zzje) {
                        zzkuVar.zzi((zzje) obj);
                    } else {
                        zzkuVar.add((String) obj);
                    }
                } else {
                    int size2 = zzkuVar.size();
                    String str = "Element at index " + (size2 - size) + " is null.";
                    int size3 = zzkuVar.size();
                    while (true) {
                        size3--;
                        if (size3 < size) {
                            break;
                        }
                        zzkuVar.remove(size3);
                    }
                    throw new NullPointerException(str);
                }
            }
        } else if (!(iterable instanceof zzlt)) {
            if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
                ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
            }
            int size4 = list.size();
            for (Object obj2 : iterable) {
                if (obj2 != null) {
                    list.add(obj2);
                } else {
                    int size5 = list.size();
                    String str2 = "Element at index " + (size5 - size4) + " is null.";
                    int size6 = list.size();
                    while (true) {
                        size6--;
                        if (size6 < size4) {
                            break;
                        }
                        list.remove(size6);
                    }
                    throw new NullPointerException(str2);
                }
            }
        } else {
            list.addAll((Collection) iterable);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int zzbr(zzlx zzlxVar) {
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlm
    public final zzje zzbs() {
        try {
            int zzbw = zzbw();
            zzje zzjeVar = zzje.zzb;
            byte[] bArr = new byte[zzbw];
            zzjm zzC = zzjm.zzC(bArr);
            zzbN(zzC);
            zzC.zzD();
            return new zzjb(bArr);
        } catch (IOException e) {
            String name = getClass().getName();
            throw new RuntimeException("Serializing " + name + " to a ByteString threw an IOException (should never happen).", e);
        }
    }

    public final byte[] zzbu() {
        try {
            byte[] bArr = new byte[zzbw()];
            zzjm zzC = zzjm.zzC(bArr);
            zzbN(zzC);
            zzC.zzD();
            return bArr;
        } catch (IOException e) {
            String name = getClass().getName();
            throw new RuntimeException("Serializing " + name + " to a byte array threw an IOException (should never happen).", e);
        }
    }
}
