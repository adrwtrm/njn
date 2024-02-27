package com.epson.iprojection.ui.common.singleton;

import kotlin.Metadata;

/* compiled from: ProhibitFinalizingOnDestroyFlag.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bR\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/ui/common/singleton/ProhibitFinalizingOnDestroyFlag;", "", "()V", "<set-?>", "", "isEnabled", "()Z", "disable", "", "enable", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ProhibitFinalizingOnDestroyFlag {
    public static final ProhibitFinalizingOnDestroyFlag INSTANCE = new ProhibitFinalizingOnDestroyFlag();
    private static boolean isEnabled;

    private ProhibitFinalizingOnDestroyFlag() {
    }

    public final boolean isEnabled() {
        return isEnabled;
    }

    public final void enable() {
        isEnabled = true;
    }

    public final void disable() {
        isEnabled = false;
    }
}
