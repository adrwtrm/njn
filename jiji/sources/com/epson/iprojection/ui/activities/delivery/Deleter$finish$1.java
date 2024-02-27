package com.epson.iprojection.ui.activities.delivery;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Deleter.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.ui.activities.delivery.Deleter$finish$1", f = "Deleter.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class Deleter$finish$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ boolean $isCanceled;
    int label;
    final /* synthetic */ Deleter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Deleter$finish$1(boolean z, Deleter deleter, Continuation<? super Deleter$finish$1> continuation) {
        super(2, continuation);
        this.$isCanceled = z;
        this.this$0 = deleter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new Deleter$finish$1(this.$isCanceled, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((Deleter$finish$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        int i2;
        IDeleteCallback iDeleteCallback;
        IDeleteCallback iDeleteCallback2;
        IDeleteCallback iDeleteCallback3;
        IDeleteCallback iDeleteCallback4;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.$isCanceled) {
            iDeleteCallback4 = this.this$0._impl;
            iDeleteCallback4.onDeleteFinished(eDeletedStatus.eCanceled);
            return Unit.INSTANCE;
        }
        i = this.this$0._deletedN;
        if (i == 0) {
            iDeleteCallback3 = this.this$0._impl;
            iDeleteCallback3.onDeleteFinished(eDeletedStatus.eCouldnotDelete);
        } else {
            i2 = this.this$0._checkedN;
            if (i == i2) {
                iDeleteCallback2 = this.this$0._impl;
                iDeleteCallback2.onDeleteFinished(eDeletedStatus.eDeleted);
            } else {
                iDeleteCallback = this.this$0._impl;
                iDeleteCallback.onDeleteFinished(eDeletedStatus.eCouldnotDeleteSome);
            }
        }
        return Unit.INSTANCE;
    }
}
