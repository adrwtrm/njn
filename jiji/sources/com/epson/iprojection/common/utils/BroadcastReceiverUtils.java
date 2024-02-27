package com.epson.iprojection.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import com.epson.iprojection.common.IntentDefine;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BroadcastReceiverUtils.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/common/utils/BroadcastReceiverUtils;", "", "()V", "registerMirroingOff", "", "context", "Landroid/content/Context;", "receiver", "Landroid/content/BroadcastReceiver;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class BroadcastReceiverUtils {
    public static final BroadcastReceiverUtils INSTANCE = new BroadcastReceiverUtils();

    private BroadcastReceiverUtils() {
    }

    public final void registerMirroingOff(Context context, BroadcastReceiver receiver) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentDefine.INTENT_ACTION_MIRRORING_OFF);
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(receiver, intentFilter, 4);
        } else {
            context.registerReceiver(receiver, intentFilter);
        }
    }
}
