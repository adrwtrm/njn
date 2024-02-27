package com.epson.iprojection.service.webrtc.thumbnail;

import android.os.Build;
import com.epson.iprojection.service.webrtc.WebRTCEntrance;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ThumbnailCapturerEntrance.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.service.webrtc.thumbnail.ThumbnailCapturerEntrance$createThumbnailFromVirtualDisplayIfWebRTCMirroring$1", f = "ThumbnailCapturerEntrance.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class ThumbnailCapturerEntrance$createThumbnailFromVirtualDisplayIfWebRTCMirroring$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ThumbnailCapturerEntrance$createThumbnailFromVirtualDisplayIfWebRTCMirroring$1(Continuation<? super ThumbnailCapturerEntrance$createThumbnailFromVirtualDisplayIfWebRTCMirroring$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThumbnailCapturerEntrance$createThumbnailFromVirtualDisplayIfWebRTCMirroring$1(continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ThumbnailCapturerEntrance$createThumbnailFromVirtualDisplayIfWebRTCMirroring$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (Build.VERSION.SDK_INT >= 33) {
            WebRTCEntrance.INSTANCE.recreateVirtualDisplay();
        }
        return Unit.INSTANCE;
    }
}
