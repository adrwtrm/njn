package com.epson.iprojection.customer_satisfaction.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CSLogic.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004J\u0016\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/entities/CSLogic;", "", "()V", "isDeadlineToResetPassed", "", "collectedDate", "Ljava/time/LocalDate;", "shouldCollectSatisfaction", "isCollected", "usedCount", "", "isAnalyticsEnable", "shouldCollectStoreReview", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CSLogic {
    public static final CSLogic INSTANCE = new CSLogic();

    private CSLogic() {
    }

    public final boolean shouldCollectSatisfaction(boolean z, int i, boolean z2) {
        return z2 && !z && i >= EContractNumber.USED_COUNT.getNumber();
    }

    public final boolean shouldCollectStoreReview(boolean z, int i) {
        return !z && i >= EContractNumber.USED_COUNT.getNumber();
    }

    public final boolean isDeadlineToResetPassed(LocalDate collectedDate) {
        Intrinsics.checkNotNullParameter(collectedDate, "collectedDate");
        return ChronoUnit.DAYS.between(collectedDate, LocalDate.now()) >= ((long) EContractNumber.ELAPSED_DAYS.getNumber());
    }
}
