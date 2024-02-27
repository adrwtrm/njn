package com.google.android.gms.internal.measurement;

import android.os.Build;
import com.serenegiant.app.PendingIntentCompat;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzbs {
    public static final int zza;

    static {
        zza = Build.VERSION.SDK_INT >= 31 ? PendingIntentCompat.FLAG_MUTABLE : 0;
    }
}
