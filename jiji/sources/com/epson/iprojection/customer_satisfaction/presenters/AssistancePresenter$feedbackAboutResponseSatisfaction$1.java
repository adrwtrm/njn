package com.epson.iprojection.customer_satisfaction.presenters;

import android.content.Context;
import android.widget.Toast;
import com.epson.iprojection.R;
import com.epson.iprojection.customer_satisfaction.usecases.CSUsecaseInterface;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: AssistancePresenter.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.presenters.AssistancePresenter$feedbackAboutResponseSatisfaction$1", f = "AssistancePresenter.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class AssistancePresenter$feedbackAboutResponseSatisfaction$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ AssistancePresenter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AssistancePresenter$feedbackAboutResponseSatisfaction$1(AssistancePresenter assistancePresenter, Continuation<? super AssistancePresenter$feedbackAboutResponseSatisfaction$1> continuation) {
        super(2, continuation);
        this.this$0 = assistancePresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AssistancePresenter$feedbackAboutResponseSatisfaction$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((AssistancePresenter$feedbackAboutResponseSatisfaction$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Context context;
        CSUsecaseInterface.OnSatisfactionFeedbackFinishedListener onSatisfactionFeedbackFinishedListener;
        Context context2;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            context = this.this$0.context;
            if (context != null) {
                context2 = this.this$0.context;
                Toast.makeText(context2, (int) R.string._ThankYouForFeedback_, 0).show();
            }
            onSatisfactionFeedbackFinishedListener = this.this$0._callback;
            if (onSatisfactionFeedbackFinishedListener != null) {
                onSatisfactionFeedbackFinishedListener.onSatisfactionFeedbackFinished();
            }
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
