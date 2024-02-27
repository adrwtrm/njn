package com.epson.iprojection.customer_satisfaction.controllers;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.epson.iprojection.customer_satisfaction.entities.EState;
import com.epson.iprojection.customer_satisfaction.usecases.CSUsecase;
import com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface;
import com.epson.iprojection.customer_satisfaction.usecases.analytics.CSAnalyticsInterface;
import com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.StoreReviewCollectInterface;
import com.epson.iprojection.customer_satisfaction.usecases.store.CSStoreInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: CSController.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/controllers/CSController;", "Landroidx/lifecycle/DefaultLifecycleObserver;", "satisfactionPresenter", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;", "storeReviewPresenter", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;", "repository", "Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;", "analytics", "Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;", "assistance", "Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;", "(Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/store/CSStoreInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/analytics/CSAnalyticsInterface;Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;)V", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "usecase", "Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$Controller;", "onChangeState", "", "state", "Lcom/epson/iprojection/customer_satisfaction/entities/EState;", "onDestroy", "owner", "Landroidx/lifecycle/LifecycleOwner;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class CSController implements DefaultLifecycleObserver {
    private final CoroutineScope coroutineScope;
    private final CSUsecaseInterface.Controller usecase;

    public CSController(SatisfactionCollectInterface satisfactionPresenter, StoreReviewCollectInterface storeReviewPresenter, CSStoreInterface repository, CSAnalyticsInterface analytics, CSAssistanceInterface assistance) {
        Intrinsics.checkNotNullParameter(satisfactionPresenter, "satisfactionPresenter");
        Intrinsics.checkNotNullParameter(storeReviewPresenter, "storeReviewPresenter");
        Intrinsics.checkNotNullParameter(repository, "repository");
        Intrinsics.checkNotNullParameter(analytics, "analytics");
        Intrinsics.checkNotNullParameter(assistance, "assistance");
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
        this.coroutineScope = CoroutineScope;
        this.usecase = new CSUsecase(satisfactionPresenter, storeReviewPresenter, repository, analytics, assistance, CoroutineScope);
    }

    public final void onChangeState(EState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this.usecase.onChangeState(state);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        super.onDestroy(owner);
        CoroutineScopeKt.cancel$default(this.coroutineScope, null, 1, null);
    }
}
