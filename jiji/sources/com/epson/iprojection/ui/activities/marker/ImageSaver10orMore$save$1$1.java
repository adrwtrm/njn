package com.epson.iprojection.ui.activities.marker;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ImageSaver10orMore.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.ui.activities.marker.ImageSaver10orMore$save$1$1", f = "ImageSaver10orMore.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class ImageSaver10orMore$save$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $fileName;
    final /* synthetic */ Ref.ObjectRef<String> $relativePath;
    final /* synthetic */ boolean $ret;
    int label;
    final /* synthetic */ ImageSaver10orMore this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageSaver10orMore$save$1$1(boolean z, ImageSaver10orMore imageSaver10orMore, Ref.ObjectRef<String> objectRef, String str, Continuation<? super ImageSaver10orMore$save$1$1> continuation) {
        super(2, continuation);
        this.$ret = z;
        this.this$0 = imageSaver10orMore;
        this.$relativePath = objectRef;
        this.$fileName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ImageSaver10orMore$save$1$1(this.$ret, this.this$0, this.$relativePath, this.$fileName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ImageSaver10orMore$save$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.$ret) {
            this.this$0.showSaveSucceed("~/" + this.$relativePath.element + '/' + this.$fileName);
            this.this$0._saveFinishCallback.callbackSaveSucceed();
        } else {
            this.this$0.showSaveFailed();
            this.this$0._saveFinishCallback.callbackSaveFail();
        }
        return Unit.INSTANCE;
    }
}
