package com.epson.iprojection.customer_satisfaction.gateways;

import com.epson.iprojection.customer_satisfaction.entities.ECollectingStatus;
import com.epson.iprojection.customer_satisfaction.entities.ECustomerSatisfaction;
import com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CSAnalytics.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\f\u001a\u00020\bH\u0016¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/gateways/CSAnalytics;", "Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;", "()V", "analyzeSatisfactionLevel", "", "cs", "Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;", "isLaunch", "", "analyzeStatusOfSatisfaction", "eCollectingStatus", "Lcom/epson/iprojection/customer_satisfaction/entities/ECollectingStatus;", "isAnalyticsEnable", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CSAnalytics implements CSAnalyticsInterface {

    /* compiled from: CSAnalytics.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ECollectingStatus.values().length];
            try {
                iArr[ECollectingStatus.TRY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ECollectingStatus.CANCEL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface
    public boolean isAnalyticsEnable() {
        return Analytics.getIns().isGoogleAnalyticsCooperate();
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface
    public void analyzeStatusOfSatisfaction(ECollectingStatus eCollectingStatus, boolean z) {
        eCustomEvent ecustomevent;
        Intrinsics.checkNotNullParameter(eCollectingStatus, "eCollectingStatus");
        int i = WhenMappings.$EnumSwitchMapping$0[eCollectingStatus.ordinal()];
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (z) {
                ecustomevent = eCustomEvent.SATISFACTION_UI_CANCEL_LAUNCH;
            } else {
                ecustomevent = eCustomEvent.SATISFACTION_UI_CANCEL_OPERATION_END;
            }
        } else if (z) {
            ecustomevent = eCustomEvent.SATISFACTION_UI_DISPLAY_LAUNCH;
        } else {
            ecustomevent = eCustomEvent.SATISFACTION_UI_DISPLAY_OPERATION_END;
        }
        Analytics ins = Analytics.getIns();
        ins.setSatisfactionEventType(ecustomevent);
        ins.sendEvent(ecustomevent);
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface
    public void analyzeSatisfactionLevel(ECustomerSatisfaction cs, boolean z) {
        eCustomEvent ecustomevent;
        Intrinsics.checkNotNullParameter(cs, "cs");
        if (z) {
            ecustomevent = eCustomEvent.SATISFACTION_UI_SEND_LAUNCH;
        } else {
            ecustomevent = eCustomEvent.SATISFACTION_UI_SEND_OPERATION_END;
        }
        Analytics ins = Analytics.getIns();
        ins.setSatisfactionResult(cs.ordinal() + 1);
        ins.setSatisfactionEventType(ecustomevent);
        ins.sendEvent(ecustomevent);
    }
}
