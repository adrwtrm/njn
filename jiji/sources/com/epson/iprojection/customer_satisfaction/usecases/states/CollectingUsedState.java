package com.epson.iprojection.customer_satisfaction.usecases.states;

import com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface;
import com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.StoreReviewCollectInterface;
import com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: CollectingUsedState.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\b\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/states/CollectingUsedState;", "Lcom/epson/iprojection/customer_satisfaction/usecases/states/CollectingState;", "storeReviewPresenter", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;", "satisfactionPresenter", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;", "repository", "Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;", "analytics", "Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;", "assistance", "Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "(Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;Lkotlinx/coroutines/CoroutineScope;)V", "isLaunch", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CollectingUsedState extends CollectingState {
    @Override // com.epson.iprojection.customer_satisfaction.usecases.states.CollectingState
    public boolean isLaunch() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollectingUsedState(StoreReviewCollectInterface storeReviewPresenter, SatisfactionCollectInterface satisfactionPresenter, CSStoreInterface repository, CSAnalyticsInterface analytics, CSAssistanceInterface assistance, CoroutineScope coroutineScope) {
        super(storeReviewPresenter, satisfactionPresenter, repository, analytics, assistance, coroutineScope);
        Intrinsics.checkNotNullParameter(storeReviewPresenter, "storeReviewPresenter");
        Intrinsics.checkNotNullParameter(satisfactionPresenter, "satisfactionPresenter");
        Intrinsics.checkNotNullParameter(repository, "repository");
        Intrinsics.checkNotNullParameter(analytics, "analytics");
        Intrinsics.checkNotNullParameter(assistance, "assistance");
        Intrinsics.checkNotNullParameter(coroutineScope, "coroutineScope");
    }
}
