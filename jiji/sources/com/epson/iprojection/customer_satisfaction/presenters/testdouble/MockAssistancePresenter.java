package com.epson.iprojection.customer_satisfaction.presenters.testdouble;

import com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface;
import com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MockAssistancePresenter.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0004H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/presenters/testdouble/MockAssistancePresenter;", "Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;", "()V", "_callback", "Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$OnSatisfactionFeedbackFinishedListener;", "isThanked", "", "()Z", "setThanked", "(Z)V", "feedbackAboutResponseSatisfaction", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "setCallback", "onSatisfactionFeedbackFinishedListener", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MockAssistancePresenter implements CSAssistanceInterface {
    private CSUsecaseInterface.OnSatisfactionFeedbackFinishedListener _callback;
    private boolean isThanked;

    public final boolean isThanked() {
        return this.isThanked;
    }

    public final void setThanked(boolean z) {
        this.isThanked = z;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface
    public void feedbackAboutResponseSatisfaction(CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter(coroutineContext, "coroutineContext");
        this.isThanked = true;
        CSUsecaseInterface.OnSatisfactionFeedbackFinishedListener onSatisfactionFeedbackFinishedListener = this._callback;
        if (onSatisfactionFeedbackFinishedListener != null) {
            onSatisfactionFeedbackFinishedListener.onSatisfactionFeedbackFinished();
        }
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface
    public void setCallback(CSUsecaseInterface.OnSatisfactionFeedbackFinishedListener onSatisfactionFeedbackFinishedListener) {
        Intrinsics.checkNotNullParameter(onSatisfactionFeedbackFinishedListener, "onSatisfactionFeedbackFinishedListener");
        this._callback = onSatisfactionFeedbackFinishedListener;
    }
}
