package com.epson.iprojection.ui.activities.delivery;

import android.app.Activity;
import android.app.RecoverableSecurityException;
import android.content.IntentSender;
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

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Deleter.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.ui.activities.delivery.Deleter$delete10orMore$1", f = "Deleter.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class Deleter$delete10orMore$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ RecoverableSecurityException $e;
    int label;
    final /* synthetic */ Deleter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Deleter$delete10orMore$1(RecoverableSecurityException recoverableSecurityException, Deleter deleter, Continuation<? super Deleter$delete10orMore$1> continuation) {
        super(2, continuation);
        this.$e = recoverableSecurityException;
        this.this$0 = deleter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new Deleter$delete10orMore$1(this.$e, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((Deleter$delete10orMore$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Activity activity;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        IntentSender intentSender = this.$e.getUserAction().getActionIntent().getIntentSender();
        Intrinsics.checkNotNullExpressionValue(intentSender, "e.userAction.actionIntent.intentSender");
        activity = this.this$0._activity;
        activity.startIntentSenderForResult(intentSender, Deleter.REQUESTCODE_DELETE, null, 0, 0, 0, null);
        return Unit.INSTANCE;
    }
}
