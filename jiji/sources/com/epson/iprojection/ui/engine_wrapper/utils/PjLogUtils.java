package com.epson.iprojection.ui.engine_wrapper.utils;

import android.content.Context;
import com.epson.iprojection.common.utils.AppInfoUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PjLogUtils.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/engine_wrapper/utils/PjLogUtils;", "", "()V", "shouldSendToTestServer", "", "context", "Landroid/content/Context;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PjLogUtils {
    public static final PjLogUtils INSTANCE = new PjLogUtils();

    private PjLogUtils() {
    }

    public final boolean shouldSendToTestServer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (AppInfoUtils.Companion.isThisVersionFC(context)) {
            return PrefUtils.readBoolean(context, PrefTagDefine.SEND_PJLOG_TO_TESTSERVER, false);
        }
        return true;
    }
}
