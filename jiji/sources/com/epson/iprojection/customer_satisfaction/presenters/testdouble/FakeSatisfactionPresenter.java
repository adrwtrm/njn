package com.epson.iprojection.customer_satisfaction.presenters.testdouble;

import com.epson.iprojection.customer_satisfaction.entities.ECustomerSatisfaction;
import com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface;
import com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FakeSatisfactionPresenter.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u0004H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u000f¨\u0006\u0017"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/presenters/testdouble/FakeSatisfactionPresenter;", "Lcom/epson/iprojection/customer_satisfaction/usecases/collecters/SatisfactionCollectInterface;", "()V", "callback", "Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$CollectResultListener;", "cs", "Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;", "getCs", "()Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;", "setCs", "(Lcom/epson/iprojection/customer_satisfaction/entities/ECustomerSatisfaction;)V", "isSend", "", "()Z", "setSend", "(Z)V", "isTryCollected", "setTryCollected", "collectSatisfaction", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "setCallback", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class FakeSatisfactionPresenter implements SatisfactionCollectInterface {
    private CSUsecaseInterface.CollectResultListener callback;
    private ECustomerSatisfaction cs = ECustomerSatisfaction.AWESOME;
    private boolean isSend;
    private boolean isTryCollected;

    public final boolean isSend() {
        return this.isSend;
    }

    public final void setSend(boolean z) {
        this.isSend = z;
    }

    public final boolean isTryCollected() {
        return this.isTryCollected;
    }

    public final void setTryCollected(boolean z) {
        this.isTryCollected = z;
    }

    public final ECustomerSatisfaction getCs() {
        return this.cs;
    }

    public final void setCs(ECustomerSatisfaction eCustomerSatisfaction) {
        Intrinsics.checkNotNullParameter(eCustomerSatisfaction, "<set-?>");
        this.cs = eCustomerSatisfaction;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface
    public void collectSatisfaction(CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter(coroutineContext, "coroutineContext");
        this.isTryCollected = true;
        if (this.isSend) {
            CSUsecaseInterface.CollectResultListener collectResultListener = this.callback;
            if (collectResultListener != null) {
                collectResultListener.onCollectSucceed(this.cs);
                return;
            }
            return;
        }
        CSUsecaseInterface.CollectResultListener collectResultListener2 = this.callback;
        if (collectResultListener2 != null) {
            collectResultListener2.onCollectCanceled();
        }
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.collecters.SatisfactionCollectInterface
    public void setCallback(CSUsecaseInterface.CollectResultListener callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
    }
}
