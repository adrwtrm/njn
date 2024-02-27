package com.epson.iprojection.customer_satisfaction.presenters;

import android.content.Context;
import com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface;
import com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: AssistancePresenter.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0006H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/presenters/AssistancePresenter;", "Lcom/epson/iprojection/customer_satisfaction/usecases/assistance/CSAssistanceInterface;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_callback", "Lcom/epson/iprojection/customer_satisfaction/usecases/CSUsecaseInterface$OnSatisfactionFeedbackFinishedListener;", "feedbackAboutResponseSatisfaction", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "setCallback", "onSatisfactionFeedbackFinishedListener", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class AssistancePresenter implements CSAssistanceInterface {
    private CSUsecaseInterface.OnSatisfactionFeedbackFinishedListener _callback;
    private final Context context;

    public AssistancePresenter(Context context) {
        this.context = context;
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface
    public void feedbackAboutResponseSatisfaction(CoroutineContext coroutineContext) {
        Intrinsics.checkNotNullParameter(coroutineContext, "coroutineContext");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(coroutineContext.plus(Dispatchers.getMain())), null, null, new AssistancePresenter$feedbackAboutResponseSatisfaction$1(this, null), 3, null);
    }

    @Override // com.epson.iprojection.customer_satisfaction.usecases.assistance.CSAssistanceInterface
    public void setCallback(CSUsecaseInterface.OnSatisfactionFeedbackFinishedListener onSatisfactionFeedbackFinishedListener) {
        Intrinsics.checkNotNullParameter(onSatisfactionFeedbackFinishedListener, "onSatisfactionFeedbackFinishedListener");
        this._callback = onSatisfactionFeedbackFinishedListener;
    }
}
