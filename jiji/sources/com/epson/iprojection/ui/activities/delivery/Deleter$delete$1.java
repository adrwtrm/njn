package com.epson.iprojection.ui.activities.delivery;

import android.os.Build;
import com.epson.iprojection.ui.activities.presen.thumbnails.ThumbMgr;
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
@DebugMetadata(c = "com.epson.iprojection.ui.activities.delivery.Deleter$delete$1", f = "Deleter.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class Deleter$delete$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ Deleter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Deleter$delete$1(Deleter deleter, Continuation<? super Deleter$delete$1> continuation) {
        super(2, continuation);
        this.this$0 = deleter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new Deleter$delete$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((Deleter$delete$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ThumbMgr thumbMgr;
        int i;
        ThumbMgr thumbMgr2;
        int i2;
        int i3;
        boolean delete10orMore;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            thumbMgr = this.this$0._thumMgr;
            int thumbListSize = thumbMgr.getThumbListSize();
            for (i = this.this$0._processIndex; i < thumbListSize; i++) {
                thumbMgr2 = this.this$0._thumMgr;
                if (thumbMgr2.getThumbCheckBox(i).isChecked()) {
                    Deleter deleter = this.this$0;
                    i3 = deleter._checkedN;
                    deleter._checkedN = i3 + 1;
                    if (Build.VERSION.SDK_INT >= 29) {
                        delete10orMore = this.this$0.delete10orMore(i);
                        if (!delete10orMore) {
                            return Unit.INSTANCE;
                        }
                    } else {
                        this.this$0.delete9orLess(i);
                    }
                }
                Deleter deleter2 = this.this$0;
                i2 = deleter2._processIndex;
                deleter2._processIndex = i2 + 1;
            }
            this.this$0.finish(false);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
