package com.epson.iprojection.customer_satisfaction.presenters.testdouble;

import com.epson.iprojection.customer_satisfaction.usecases.collecters.StoreReviewCollectInterface;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MockStoreReviewPresenter.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/presenters/testdouble/MockStoreReviewPresenter;", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/StoreReviewCollectInterface;", "()V", "isTryCollected", "", "()Z", "setTryCollected", "(Z)V", "collectStoreReviews", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MockStoreReviewPresenter implements StoreReviewCollectInterface {
    private boolean isTryCollected;

    public final boolean isTryCollected() {
        return this.isTryCollected;
    }

    public final void setTryCollected(boolean z) {
        this.isTryCollected = z;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.collecters.StoreReviewCollectInterface
    public void collectStoreReviews(CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter(coroutineContext, "coroutineContext");
        this.isTryCollected = true;
    }
}
