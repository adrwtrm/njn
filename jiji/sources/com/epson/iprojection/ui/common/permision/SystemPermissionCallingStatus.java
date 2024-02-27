package com.epson.iprojection.ui.common.permision;

import kotlin.Metadata;

/* compiled from: SystemPermissionCallingStatus.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\tR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/common/permision/SystemPermissionCallingStatus;", "", "()V", "isCalling", "", "()Z", "setCalling", "(Z)V", "onReturned", "", "onStarted", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class SystemPermissionCallingStatus {
    public static final SystemPermissionCallingStatus INSTANCE = new SystemPermissionCallingStatus();
    private static boolean isCalling;

    private SystemPermissionCallingStatus() {
    }

    public final boolean isCalling() {
        return isCalling;
    }

    public final void setCalling(boolean z) {
        isCalling = z;
    }

    public final void onStarted() {
        isCalling = true;
    }

    public final void onReturned() {
        isCalling = false;
    }
}
