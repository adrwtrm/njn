package com.google.common.util.concurrent;

import java.time.Duration;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
final class Internal {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static long toNanosSaturated(Duration duration) {
        try {
            return duration.toNanos();
        } catch (ArithmeticException unused) {
            return duration.isNegative() ? Long.MIN_VALUE : Long.MAX_VALUE;
        }
    }

    private Internal() {
    }
}
