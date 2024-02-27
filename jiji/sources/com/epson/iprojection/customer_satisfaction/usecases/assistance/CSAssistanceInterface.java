package com.epson.iprojection.customer_satisfaction.usecases.assistance;

import com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;

/* compiled from: CSAssistanceInterface.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;", "", "feedbackAboutResponseSatisfaction", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "setCallback", "onSatisfactionFeedbackFinishedListener", "Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$OnSatisfactionFeedbackFinishedListener;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface CSAssistanceInterface {
    void feedbackAboutResponseSatisfaction(CoroutineContext coroutineContext);

    void setCallback(CSUsecaseInterface.OnSatisfactionFeedbackFinishedListener onSatisfactionFeedbackFinishedListener);
}
