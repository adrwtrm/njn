package com.epson.iprojection.customer_satisfaction.gateways.testdouble;

import com.epson.iprojection.customer_satisfaction.entities.ECollectingStatus;
import com.epson.iprojection.customer_satisfaction.entities.ECustomerSatisfaction;
import com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MockAnalytics.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\u0004H\u0016J\u0018\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u001f\u001a\u00020\u0004H\u0016J\b\u0010#\u001a\u00020\u0004H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0006\"\u0004\b\u0010\u0010\bR\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016R\u001a\u0010\u001a\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0014\"\u0004\b\u001c\u0010\u0016¨\u0006$"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/gateways/testdouble/MockAnalytics;", "Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;", "()V", "analyticsFlag", "", "getAnalyticsFlag", "()Z", "setAnalyticsFlag", "(Z)V", "cs", "Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;", "getCs", "()Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;", "setCs", "(Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;)V", "isLaunchForSatisfaction", "setLaunchForSatisfaction", "satisfactionCancelCount", "", "getSatisfactionCancelCount", "()I", "setSatisfactionCancelCount", "(I)V", "satisfactionTryCount", "getSatisfactionTryCount", "setSatisfactionTryCount", "storeTryCount", "getStoreTryCount", "setStoreTryCount", "analyzeSatisfactionLevel", "", "isLaunch", "analyzeStatusOfSatisfaction", "eCollectingStatus", "Lcom/epson/iprojection/customer_satisfaction/entities/ECollectingStatus;", "isAnalyticsEnable", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MockAnalytics implements CSAnalyticsInterface {
    private boolean analyticsFlag;
    private ECustomerSatisfaction cs = ECustomerSatisfaction.AWESOME;
    private boolean isLaunchForSatisfaction;
    private int satisfactionCancelCount;
    private int satisfactionTryCount;
    private int storeTryCount;

    public final boolean getAnalyticsFlag() {
        return this.analyticsFlag;
    }

    public final void setAnalyticsFlag(boolean z) {
        this.analyticsFlag = z;
    }

    public final ECustomerSatisfaction getCs() {
        return this.cs;
    }

    public final void setCs(ECustomerSatisfaction eCustomerSatisfaction) {
        Intrinsics.checkNotNullParameter(eCustomerSatisfaction, "<set-?>");
        this.cs = eCustomerSatisfaction;
    }

    public final boolean isLaunchForSatisfaction() {
        return this.isLaunchForSatisfaction;
    }

    public final void setLaunchForSatisfaction(boolean z) {
        this.isLaunchForSatisfaction = z;
    }

    public final int getSatisfactionTryCount() {
        return this.satisfactionTryCount;
    }

    public final void setSatisfactionTryCount(int i) {
        this.satisfactionTryCount = i;
    }

    public final int getSatisfactionCancelCount() {
        return this.satisfactionCancelCount;
    }

    public final void setSatisfactionCancelCount(int i) {
        this.satisfactionCancelCount = i;
    }

    public final int getStoreTryCount() {
        return this.storeTryCount;
    }

    public final void setStoreTryCount(int i) {
        this.storeTryCount = i;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface
    public boolean isAnalyticsEnable() {
        return this.analyticsFlag;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface
    public void analyzeStatusOfSatisfaction(ECollectingStatus eCollectingStatus, boolean z) {
        Intrinsics.checkNotNullParameter(eCollectingStatus, "eCollectingStatus");
        this.isLaunchForSatisfaction = z;
        if (eCollectingStatus == ECollectingStatus.TRY) {
            this.satisfactionTryCount++;
        } else {
            this.satisfactionCancelCount++;
        }
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface
    public void analyzeSatisfactionLevel(ECustomerSatisfaction cs, boolean z) {
        Intrinsics.checkNotNullParameter(cs, "cs");
        this.cs = cs;
    }
}
