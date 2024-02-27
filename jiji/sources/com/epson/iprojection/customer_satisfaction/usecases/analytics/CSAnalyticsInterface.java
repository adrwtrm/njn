package com.epson.iprojection.customer_satisfaction.usecases.analytics;

import com.epson.iprojection.customer_satisfaction.entities.ECollectingStatus;
import com.epson.iprojection.customer_satisfaction.entities.ECustomerSatisfaction;
import kotlin.Metadata;

/* compiled from: CSAnalyticsInterface.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\u000b\u001a\u00020\u0007H&¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;", "", "analyzeSatisfactionLevel", "", "cs", "Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;", "isLaunch", "", "analyzeStatusOfSatisfaction", "eCollectingStatus", "Lcom/epson/iprojection/customer_satisfaction/entities/ECollectingStatus;", "isAnalyticsEnable", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface CSAnalyticsInterface {
    void analyzeSatisfactionLevel(ECustomerSatisfaction eCustomerSatisfaction, boolean z);

    void analyzeStatusOfSatisfaction(ECollectingStatus eCollectingStatus, boolean z);

    boolean isAnalyticsEnable();
}
