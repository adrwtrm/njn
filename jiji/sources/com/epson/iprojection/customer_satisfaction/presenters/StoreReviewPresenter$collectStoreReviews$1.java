package com.epson.iprojection.customer_satisfaction.presenters;

import android.app.Activity;
import android.content.Context;
import com.epson.iprojection.common.Lg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: StoreReviewPresenter.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.presenters.StoreReviewPresenter$collectStoreReviews$1", f = "StoreReviewPresenter.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class StoreReviewPresenter$collectStoreReviews$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ StoreReviewPresenter this$0;

    /* renamed from: $r8$lambda$aUOEDF1Zb9-24mSIPnrkHPwTnnI */
    public static /* synthetic */ void m56$r8$lambda$aUOEDF1Zb924mSIPnrkHPwTnnI(Task task) {
        invokeSuspend$lambda$1$lambda$0(task);
    }

    public static /* synthetic */ void $r8$lambda$xQlODWhmxPy6sCx4MWsK1D8LA0k(Task task, ReviewManager reviewManager, StoreReviewPresenter storeReviewPresenter, Task task2) {
        invokeSuspend$lambda$1(task, reviewManager, storeReviewPresenter, task2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StoreReviewPresenter$collectStoreReviews$1(StoreReviewPresenter storeReviewPresenter, Continuation<? super StoreReviewPresenter$collectStoreReviews$1> continuation) {
        super(2, continuation);
        this.this$0 = storeReviewPresenter;
    }

    public static final void invokeSuspend$lambda$1$lambda$0(Task task) {
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new StoreReviewPresenter$collectStoreReviews$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((StoreReviewPresenter$collectStoreReviews$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Context context;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            context = this.this$0.context;
            final ReviewManager create = ReviewManagerFactory.create(context);
            Intrinsics.checkNotNullExpressionValue(create, "create(context)");
            final Task<ReviewInfo> requestReviewFlow = create.requestReviewFlow();
            Intrinsics.checkNotNullExpressionValue(requestReviewFlow, "manager.requestReviewFlow()");
            final StoreReviewPresenter storeReviewPresenter = this.this$0;
            requestReviewFlow.addOnCompleteListener(new OnCompleteListener() { // from class: com.epson.iprojection.customer_satisfaction.presenters.StoreReviewPresenter$collectStoreReviews$1$$ExternalSyntheticLambda1
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    StoreReviewPresenter$collectStoreReviews$1.$r8$lambda$xQlODWhmxPy6sCx4MWsK1D8LA0k(Task.this, create, storeReviewPresenter, task);
                }
            });
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static final void invokeSuspend$lambda$1(Task task, ReviewManager reviewManager, StoreReviewPresenter storeReviewPresenter, Task task2) {
        Context context;
        if (task2.isSuccessful()) {
            Lg.d("task.result = " + task2.getResult());
            Lg.d("request.result = " + task.getResult());
            context = storeReviewPresenter.context;
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
            Task<Void> launchReviewFlow = reviewManager.launchReviewFlow((Activity) context, (ReviewInfo) task.getResult());
            Intrinsics.checkNotNullExpressionValue(launchReviewFlow, "manager.launchReviewFlow… as Activity, reviewInfo)");
            launchReviewFlow.addOnCompleteListener(new OnCompleteListener() { // from class: com.epson.iprojection.customer_satisfaction.presenters.StoreReviewPresenter$collectStoreReviews$1$$ExternalSyntheticLambda0
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task3) {
                    StoreReviewPresenter$collectStoreReviews$1.m56$r8$lambda$aUOEDF1Zb924mSIPnrkHPwTnnI(task3);
                }
            });
            return;
        }
        Lg.d("errorCode");
    }
}
