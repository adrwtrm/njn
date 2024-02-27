package com.epson.iprojection.customer_satisfaction.usecases.states;

import com.epson.iprojection.customer_satisfaction.entities.ECollectingStatus;
import com.epson.iprojection.customer_satisfaction.entities.ECustomerSatisfaction;
import com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface;
import com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface;
import com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.StoreReviewCollectInterface;
import com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: CollectingState.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B5\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\b\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\u0010\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0016H\u0016J\b\u0010\u001c\u001a\u00020\u0016H\u0016J\b\u0010\u001d\u001a\u00020\u0016H\u0002R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/states/CollectingState;", "Lcom/epson/iprojection/customer_satisfaction/usecases/states/State;", "Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$CollectResultListener;", "Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$OnSatisfactionFeedbackFinishedListener;", "storeReviewPresenter", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;", "satisfactionPresenter", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;", "repository", "Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;", "analytics", "Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;", "assistance", "Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "(Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;Lkotlinx/coroutines/CoroutineScope;)V", "_usedCount", "", "isLaunch", "", "onCollect", "", "onCollectCanceled", "onCollectSucceed", "cs", "Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;", "onSatisfactionFeedbackFinished", "run", "runStoreReview", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class CollectingState implements State, CSUsecaseInterface.CollectResultListener, CSUsecaseInterface.OnSatisfactionFeedbackFinishedListener {
    private int _usedCount;
    private final CSAnalyticsInterface analytics;
    private final CSAssistanceInterface assistance;
    private final CoroutineScope coroutineScope;
    private final CSStoreInterface repository;
    private final SatisfactionCollectInterface satisfactionPresenter;
    private final StoreReviewCollectInterface storeReviewPresenter;

    public abstract boolean isLaunch();

    public CollectingState(StoreReviewCollectInterface storeReviewPresenter, SatisfactionCollectInterface satisfactionPresenter, CSStoreInterface repository, CSAnalyticsInterface analytics, CSAssistanceInterface assistance, CoroutineScope coroutineScope) {
        Intrinsics.checkNotNullParameter(storeReviewPresenter, "storeReviewPresenter");
        Intrinsics.checkNotNullParameter(satisfactionPresenter, "satisfactionPresenter");
        Intrinsics.checkNotNullParameter(repository, "repository");
        Intrinsics.checkNotNullParameter(analytics, "analytics");
        Intrinsics.checkNotNullParameter(assistance, "assistance");
        Intrinsics.checkNotNullParameter(coroutineScope, "coroutineScope");
        this.storeReviewPresenter = storeReviewPresenter;
        this.satisfactionPresenter = satisfactionPresenter;
        this.repository = repository;
        this.analytics = analytics;
        this.assistance = assistance;
        this.coroutineScope = coroutineScope;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.states.State
    public void run() {
        this.satisfactionPresenter.setCallback(this);
        this.assistance.setCallback(this);
        BuildersKt__Builders_commonKt.launch$default(this.coroutineScope, null, null, new CollectingState$run$1(this, null), 3, null);
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface.CollectResultListener
    public void onCollectSucceed(ECustomerSatisfaction cs) {
        Intrinsics.checkNotNullParameter(cs, "cs");
        BuildersKt__Builders_commonKt.launch$default(this.coroutineScope, null, null, new CollectingState$onCollectSucceed$1(this, null), 3, null);
        this.analytics.analyzeSatisfactionLevel(cs, isLaunch());
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface.OnSatisfactionFeedbackFinishedListener
    public void onSatisfactionFeedbackFinished() {
        runStoreReview();
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface.CollectResultListener
    public void onCollectCanceled() {
        this.analytics.analyzeStatusOfSatisfaction(ECollectingStatus.CANCEL, isLaunch());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onCollect() {
        BuildersKt__Builders_commonKt.launch$default(this.coroutineScope, null, null, new CollectingState$onCollect$1(this, null), 3, null);
        this.analytics.analyzeStatusOfSatisfaction(ECollectingStatus.TRY, isLaunch());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void runStoreReview() {
        BuildersKt__Builders_commonKt.launch$default(this.coroutineScope, null, null, new CollectingState$runStoreReview$1(this, null), 3, null);
    }
}
