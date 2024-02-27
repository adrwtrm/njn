package com.epson.iprojection.ui.activities.fileselect;

import com.epson.iprojection.ui.activities.fileselect.FileSelectContract;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: FileSelectPresenter.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.ui.activities.fileselect.FileSelectPresenter$onUpdatedReceiveDataProgress$1", f = "FileSelectPresenter.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class FileSelectPresenter$onUpdatedReceiveDataProgress$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $receivedNum;
    final /* synthetic */ int $totalNum;
    int label;
    final /* synthetic */ FileSelectPresenter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileSelectPresenter$onUpdatedReceiveDataProgress$1(FileSelectPresenter fileSelectPresenter, int i, int i2, Continuation<? super FileSelectPresenter$onUpdatedReceiveDataProgress$1> continuation) {
        super(2, continuation);
        this.this$0 = fileSelectPresenter;
        this.$receivedNum = i;
        this.$totalNum = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FileSelectPresenter$onUpdatedReceiveDataProgress$1(this.this$0, this.$receivedNum, this.$totalNum, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((FileSelectPresenter$onUpdatedReceiveDataProgress$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        FileSelectContract.View view;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            view = this.this$0.mView;
            view.updateProgressText(new StringBuilder().append(this.$receivedNum).append('/').append(this.$totalNum).toString());
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
